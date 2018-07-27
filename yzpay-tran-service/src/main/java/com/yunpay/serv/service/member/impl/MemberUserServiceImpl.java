package com.yunpay.serv.service.member.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yunpay.activemq.busi.CouponNotifyService;
import com.yunpay.common.exception.YunPayException;
import com.yunpay.common.utils.IdWorker;
import com.yunpay.common.utils.Log;
import com.yunpay.common.utils.ResultEnum.ErrorCode;
import com.yunpay.common.utils.ResultEnum.ResultCode;
import com.yunpay.serv.common.TokenService;
import com.yunpay.serv.dao.MemBonusLsDao;
import com.yunpay.serv.dao.MemBonusRuleDao;
import com.yunpay.serv.dao.WechatConfigDao;
import com.yunpay.serv.model.MemberBonusLs;
import com.yunpay.serv.model.MemberBonusRule;
import com.yunpay.serv.model.MemberConsumLs;
import com.yunpay.serv.model.MemberRechargeLs;
import com.yunpay.serv.model.Membercard;
import com.yunpay.serv.model.WechatConfigKey;
import com.yunpay.serv.rep.Message;
import com.yunpay.serv.rep.member.MemberChargeRepDto;
import com.yunpay.serv.rep.member.MemberConsumRepDto;
import com.yunpay.serv.req.member.MemberChargeReqDto;
import com.yunpay.serv.req.member.MemberConsumReqDto;
import com.yunpay.serv.service.busi.MemConsumLsService;
import com.yunpay.serv.service.busi.MemRechargeLsService;
import com.yunpay.serv.service.busi.MembercardService;
import com.yunpay.serv.service.member.MemberUserService;
import com.yunpay.wx.rep.member.MemberUpdRep;
import com.yunpay.wx.req.member.MemberUpdReq;
import com.yunpay.wx.service.WechatMemberService;

/**
 * 
 * 文件名称:    MemberUserServiceImpl.java
 * 内容摘要:    微信会员卡会员业务核心处理类
 * 封装微信会员卡会员业务逻辑的核心处理（主要方法：会员充值、消费等）。
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年9月20日下午5:41:23 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * 2018年3月2日       Zeng Dongcheng   1.0     修改
 * 修复当用户用手机号充值或消费时，通过公众号查询不到流水信息的问题
 * 在构建消费或充值流水时，userCardCode取值由原来的chargeReq.getMember_card_code()
 * 改为membercard.getUserCardCode()。
 * ----------------------------------------------  
 * 2017年9月20日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
@Service("memberUserService")
public class MemberUserServiceImpl implements MemberUserService{
	
	@Autowired
	private MembercardService membercardService;
	@Autowired
	private MemRechargeLsService memRechargeLsService;
	@Autowired
	private MemConsumLsService memConsumLsService;
	@Autowired
	private MemBonusRuleDao memBonusRuleDao;
	@Autowired
	private MemBonusLsDao memBonusLsDao;
	@Autowired
	private WechatMemberService wechatMemberService;
	@Autowired
	private WechatConfigDao wechatConfigDao;
	@Autowired
	private TokenService tokenService;
	@Autowired
	private CouponNotifyService couponNotifyService;
	
	/**
	 * 会员卡充值业务
	 * @Description:
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月20日上午10:28:22 
	 * @param memberChargeReq
	 * @return
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public Message memberCharge(MemberChargeReqDto memberChargeReq,Membercard membercard) throws YunPayException{
		//充值金额
		float rechargeAmt = Float.valueOf(memberChargeReq.getRecharge_amt());
		//获取会员充值赠送金额
		float giveAmt = membercardService.queryChargeGiveamt(memberChargeReq.getMerchant_num(),rechargeAmt);
		//构造会员充值流水实体
		MemberRechargeLs rechargeLs = createMemberRechargeLs(memberChargeReq,membercard,giveAmt);
		//创建充值流水
		if(memRechargeLsService.createMemRechargeLs(rechargeLs)){
			//如果创建充值流水成功
			float changeAmt = rechargeAmt+giveAmt;
			//修改会员当前余额
			if(membercardService.updMembercardBalance(membercard.getId(), changeAmt)){
				MemberChargeRepDto chargeRep = new MemberChargeRepDto(memberChargeReq.getMember_card_code(),
						rechargeAmt,giveAmt,rechargeLs.getAfterAmt(),membercard.getBonus());
				return new Message(chargeRep);
			}else{
				//抛出异常，事务回滚
				Log.error("修改会员余额失败，会员卡号："+memberChargeReq.getMember_card_code());
				throw new YunPayException(ErrorCode.SYS_DATA_EXCEPTION.getCode(), ErrorCode.SYS_DATA_EXCEPTION.getDes());
			}
		}else{
			//充值失败，返回系统异常
			Log.error("添加充值流水失败，会员卡号："+memberChargeReq.getMember_card_code());
			return new Message(ErrorCode.SYS_DATA_EXCEPTION.getCode(), ErrorCode.SYS_DATA_EXCEPTION.getDes());
		}
	}
	
	/**
	 * @Description: 构造充值流水实体
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月20日下午8:04:54 
	 * @param chargeReq
	 * @param membercard
	 * @param giveAmt
	 * @return
	 */
	private MemberRechargeLs createMemberRechargeLs(MemberChargeReqDto chargeReq,
			Membercard membercard,float giveAmt){
		MemberRechargeLs rechargeLs = new MemberRechargeLs();
		float rechargeAmt = Float.valueOf(chargeReq.getRecharge_amt());
		IdWorker iw = new IdWorker();
		String transNum = iw.getId() + "";
		rechargeLs.setSource(chargeReq.getClient_type());
		rechargeLs.setUserCardCode(membercard.getUserCardCode());
		rechargeLs.setMemberName(membercard.getName());
		rechargeLs.setRechargeOrderNo(chargeReq.getRecharge_order_no());
		rechargeLs.setTransNum(transNum);
		rechargeLs.setRechargeAmt(rechargeAmt);
		rechargeLs.setGiveAmt(giveAmt);
		rechargeLs.setBeforeAmt(membercard.getBalance());
		rechargeLs.setAfterAmt(rechargeAmt+giveAmt+membercard.getBalance());
		rechargeLs.setPayType(4);
		rechargeLs.setCreatedAt(new Date());
		//交易成功
		rechargeLs.setRechargeStatus(2);
		rechargeLs.setMerchantNo(chargeReq.getMerchant_num());
		rechargeLs.setTermNo(chargeReq.getTerminal_unique_no());
		rechargeLs.setCashierNo(chargeReq.getCashier_no());
		rechargeLs.setInfo(chargeReq.getTitle());
		return rechargeLs;
	}
	
	/**
	 * @Description: 构造消费流水实体
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月20日下午8:04:54 
	 * @param chargeReq
	 * @param membercard
	 * @param giveAmt
	 * @return
	 */
	private MemberConsumLs createMemberConsumLs(MemberConsumReqDto consumReq,
			Membercard membercard,float disAmt,float bonusAmt){
		MemberConsumLs consumLs = new MemberConsumLs();
		float tranAmt = Float.valueOf(consumReq.getTran_amt());
		IdWorker iw = new IdWorker();
		String transNum = iw.getId() + "";
		consumLs.setSource(consumReq.getClient_type());
		consumLs.setUserCardCode(membercard.getUserCardCode());
		consumLs.setMemberName(membercard.getName());
		consumLs.setUserOrderNo(consumReq.getUser_order_no());
		consumLs.setTransNum(transNum);
		//原始交易金额
		consumLs.setTranAmt(tranAmt);
		//折扣金额
		consumLs.setDisAmt(disAmt);
		//积分抵扣金额
		consumLs.setBonusAmt(bonusAmt);
		consumLs.setBeforeAmt(membercard.getBalance());
		//消费后余额=当前余额-优惠&抵扣后的支付金额
		consumLs.setAfterAmt(membercard.getBalance()-(tranAmt-disAmt-bonusAmt));
		consumLs.setCreatedAt(new Date());
		//交易成功
		consumLs.setTranStatus(2);
		consumLs.setMerchantNo(consumReq.getMerchant_num());
		consumLs.setTermNo(consumReq.getTerminal_unique_no());
		consumLs.setCashierNo(consumReq.getCashier_no());
		consumLs.setInfo(consumReq.getTitle());
		consumLs.setNotifyStatus(0);
		//增加招手猫优惠券判断
		if(StringUtils.isNotBlank(consumReq.getCoupon_code())){
			consumLs.setCouponCode(consumReq.getCoupon_code());
			consumLs.setCouponNotify(consumReq.getNotify_url());
			consumLs.setNotifyStatus(1);
		}
		return consumLs;
	}
	
	/**
	 * @Description:创建积分变动流水 
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月21日下午6:25:26 
	 * @param consumReq
	 * @param membercard
	 * @param changeType
	 * @return
	 */
	private MemberBonusLs createMemberBonusLs(MemberConsumReqDto consumReq,
			Membercard membercard,String transNum,int changeType,
			float bonusAmt,int changeBonus){
		MemberBonusLs memberBonusLs = new MemberBonusLs();
		if(changeType==1){
			//消费抵扣
			memberBonusLs.setSource(3);
			memberBonusLs.setAfterBonus(membercard.getBonus()-changeBonus);
			//积分抵扣的金额
			memberBonusLs.setEqualAmt(bonusAmt);
			memberBonusLs.setInfo("消费积分抵扣");
		}else{
			//消费赠送
			memberBonusLs.setSource(0);
			memberBonusLs.setAfterBonus(membercard.getBonus()+changeBonus);
			//原交易消费金额
			memberBonusLs.setTranAmt(bonusAmt);
			memberBonusLs.setInfo("消费赠送积分");
		}
		memberBonusLs.setUserCardCode(membercard.getUserCardCode());
		memberBonusLs.setSourceTransNum(transNum);
		memberBonusLs.setBeforeBonus(membercard.getBonus());
		memberBonusLs.setChangeBonus(changeBonus);
		memberBonusLs.setChangeType(changeType);
		memberBonusLs.setCreatedAt(new Date());
		
		memberBonusLs.setMerchantNo(consumReq.getMerchant_num());
		memberBonusLs.setTermNo(consumReq.getTerminal_unique_no());
		memberBonusLs.setCashierNo(consumReq.getCashier_no());
		return memberBonusLs;
	}


	/**
	 * @Description:会员消费
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月21日上午11:30:13 
	 * @param memberConsumReq
	 * @param membercard
	 * @return
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public Message memberConsum(MemberConsumReqDto memberConsumReq,
			Membercard membercard) throws YunPayException{
		//消费金额
		float tranAmt = Float.valueOf(memberConsumReq.getTran_amt());
		//抵扣的积分数
		int useBonus = Integer.valueOf(memberConsumReq.getUse_bonus());
		//查询商户的积分规则
		MemberBonusRule bonusRule = memBonusRuleDao.queryMerchBonusRule(memberConsumReq.getMerchant_num());
		//优惠金额
		float disAmt = 0;
		if(StringUtils.isNotBlank(memberConsumReq.getDis_amt())){
			//招手猫卡券的优惠金额
			disAmt = Float.valueOf(memberConsumReq.getDis_amt());
		}
		//积分抵扣金额
		float bonusAmt = 0;
		//当前消费赠送的积分
		int giveBonus = 0;
		if(bonusRule!=null){
			//计算折扣
			if(bonusRule.getDiscount()!=null && (bonusRule.getDiscount()>0 && bonusRule.getDiscount()<100)){
				//优惠金额=招手猫卡券的优惠金额+会员折扣金额
				disAmt += tranAmt-tranAmt*bonusRule.getDiscount()/100;
			}
			//判断是否存在积分抵扣
			if(useBonus>0){
				//如果有积分抵扣限制&&抵扣的积分大于积分抵扣限制
				if(bonusRule.getMax_reduce_bonus()!=null && useBonus>bonusRule.getMax_reduce_bonus()){
					//返回
					return new Message(ErrorCode.MEMBER_USEBONUS_MORE.getCode(),
							String.format(ErrorCode.MEMBER_USEBONUS_MORE.getDes(),bonusRule.getMax_reduce_bonus()));
				}
				//如果使用的积分数大于用户剩余的积分数，提示积分不够
				if(useBonus>membercard.getBonus()){
					//返回会员积分不够提示
					return new Message(ErrorCode.MEMBER_BONUS_LESS.getCode(),
							String.format(ErrorCode.MEMBER_BONUS_LESS.getDes(), membercard.getBonus()));
				}
				//如果交易金额小于积分抵扣设置的最低金额门槛
				if(bonusRule.getLeast_money_to_use_bonus()!=null && (tranAmt-disAmt)<bonusRule.getLeast_money_to_use_bonus()){
					//返回当前交易不支持使用积分抵扣
					return new Message(ErrorCode.MEMBER_TRANAMT_LESS.getCode(),
							String.format(ErrorCode.MEMBER_TRANAMT_LESS.getDes(), bonusRule.getLeast_money_to_use_bonus()));
				}
				//计算积分抵扣金额
				bonusAmt = (float)Math.round((float)useBonus/bonusRule.getCost_bonus_unit()*100)
						/100*bonusRule.getReduce_money();
			}
			//计算消费赠送积分(按原始金额计算)
			if((bonusRule.getCost_money_unit()!=null && bonusRule.getCost_money_unit()>0)
					&& (bonusRule.getIncrease_bonus()!=null && bonusRule.getIncrease_bonus()>0)){
				//如果消费金额与赠送积分都有值
				giveBonus =(int)Math.floor(tranAmt/bonusRule.getCost_money_unit()*bonusRule.getIncrease_bonus()) ;
			}else if((bonusRule.getCost_money_unit()!=null && bonusRule.getCost_money_unit()==0)
					&& (bonusRule.getIncrease_bonus()!=null && bonusRule.getIncrease_bonus()>0)){
				//如果消费金额为0，则表示每笔赠送固定的积分数
				giveBonus = bonusRule.getIncrease_bonus();
			}
			//如果设置了单次积分赠送上限且当次按赠送规则计算的赠送积分数大于上限数
			if(bonusRule.getMax_increase_bonus()!=null && bonusRule.getMax_increase_bonus()>0
					&& giveBonus>bonusRule.getMax_increase_bonus()){
				giveBonus = bonusRule.getMax_increase_bonus();
			}
		}
		//如果卡余额小于优惠后的交易金额，返回余额不足
		if(membercard.getBalance()<(tranAmt-disAmt-bonusAmt)){
			return new Message(ErrorCode.MEMBER_BALANCE_LESS.getCode(),ErrorCode.MEMBER_BALANCE_LESS.getDes());
		}
		//构造会员消费流水
		MemberConsumLs consumLs = createMemberConsumLs(memberConsumReq,membercard,disAmt,bonusAmt);
		//创建消费流水
		if(memConsumLsService.createMemConsumLs(consumLs)){
			if(useBonus>0){
				//构造积分抵扣流水
				MemberBonusLs useBnusLs = createMemberBonusLs(memberConsumReq,
						membercard, consumLs.getTransNum(),1,bonusAmt,useBonus);
				//添加积分抵扣记录
				if(memBonusLsDao.insert(useBnusLs)>0){
					//会员当前积分数=当前数-抵扣数
					membercard.setBonus(membercard.getBonus()-useBonus);
				}else{
					//抛出系统异常
					Log.error("会员消费积分抵扣记录添加异常，会员卡卡号："+memberConsumReq.getMember_card_code());
					throw new YunPayException(ErrorCode.SYS_DATA_EXCEPTION.getCode(), ErrorCode.SYS_DATA_EXCEPTION.getDes());
				}
			}
			if(giveBonus>0){
				//构造积分赠送流水
				MemberBonusLs giveBonusLs = createMemberBonusLs(memberConsumReq,
						membercard, consumLs.getTransNum(),0,tranAmt,giveBonus);
				//添加积分赠送记录
				if(memBonusLsDao.insert(giveBonusLs)>0){
					//会员当前积分数=当前数+赠送数
					membercard.setBonus(membercard.getBonus()+giveBonus);
				}else{
					//抛出系统异常
					Log.error("会员消费积分抵扣记录添加异常，会员卡卡号："+memberConsumReq.getMember_card_code());
					throw new YunPayException(ErrorCode.SYS_DATA_EXCEPTION.getCode(), ErrorCode.SYS_DATA_EXCEPTION.getDes());
				}
			}
			//会员此次需变更的余额=折扣金额+积分抵扣金额-交易金额
			float changeAmt = disAmt+bonusAmt-tranAmt;
			//此次会员需变更的积分=赠送积分-抵扣积分
			int changeBonus = giveBonus-useBonus;
			//修改会员当前余额和积分
			if(membercardService.updMemBalanceAndBonus(membercard.getId(),changeAmt,changeBonus)){
				if(changeBonus!=0){
					//如果改动的积分大于0，发送改动通知到微信
					MemberUpdReq memberUpdReq = new MemberUpdReq(membercard.getUserCardCode(), 
							membercard.getCard_id(), membercard.getBonus(),changeBonus, "消费赠送");
					WechatConfigKey wechatConfig = wechatConfigDao.findConfigByMerNo(memberConsumReq.getMerchant_num());
					if (wechatConfig == null) {
						throw new YunPayException(ErrorCode.ILLEGAL_PAYCONFIG.getCode(),ErrorCode.ILLEGAL_PAYCONFIG.getDes());
					}
					//获取accessToken
					String accessToken = tokenService.recvAccessToken(wechatConfig.getAppId(), wechatConfig.getAppSecret());
					MemberUpdRep memberUpdRep = wechatMemberService.doUpdMember(memberUpdReq, accessToken);
					if(!ResultCode.SUCCESS.getCode().equals(memberUpdRep.getReturn_code())){
						throw new YunPayException(memberUpdRep.getErr_code(),memberUpdRep.getErr_code());
					}
				}
				//增加招手猫卡券通知
				//如果存在卡券，则发送核销通知到activemq
				if(StringUtils.isNotBlank(memberConsumReq.getCoupon_code()) 
						&& StringUtils.isNotBlank(memberConsumReq.getNotify_url())){
					//发送卡券核销通知到消息队列activemq
					if(!couponNotifyService.sendCouponNotify(memberConsumReq.getNotify_url())){
						Log.error("发送招手猫卡券通知到消息队列出现异常："+memberConsumReq.getNotify_url());
						throw new YunPayException(ErrorCode.SYS_ACTIVEMQ_ERROR.getCode(), ErrorCode.SYS_ACTIVEMQ_ERROR.getDes());
					}
				}
				MemberConsumRepDto consumRep = new MemberConsumRepDto(memberConsumReq.getMember_card_code(),
						tranAmt, disAmt,bonusAmt,useBonus,giveBonus,consumLs.getAfterAmt(),membercard.getBonus());
				return new Message(consumRep);
			}else{
				//抛出异常，事务回滚
				Log.error("修改会员余额失败，会员卡号："+memberConsumReq.getMember_card_code());
				throw new YunPayException(ErrorCode.SYSTEM_EXCEPTION.getCode(), ErrorCode.SYSTEM_EXCEPTION.getDes());
			}
			
		}else{
			//充值失败，返回系统异常
			Log.error("添加充值流水失败，会员卡号："+memberConsumReq.getMember_card_code());
			return new Message(ErrorCode.SYSTEM_EXCEPTION.getCode(), ErrorCode.SYSTEM_EXCEPTION.getDes());
		}
	}
}
