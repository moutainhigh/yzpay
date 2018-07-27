package com.yunpay.serv.service.card.impl;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.yunpay.common.utils.Log;
import com.yunpay.common.utils.EnumUtil.CardStatus;
import com.yunpay.common.utils.EnumUtil.CouponStatus;
import com.yunpay.common.utils.EnumUtil.MembercardStatus;
import com.yunpay.common.utils.ResultEnum.ResultCode;
import com.yunpay.serv.common.TokenService;
import com.yunpay.serv.dao.MemBonusLsDao;
import com.yunpay.serv.dao.MemBonusRuleDao;
import com.yunpay.serv.dao.WechatConfigDao;
import com.yunpay.serv.model.CardCoupon;
import com.yunpay.serv.model.CardInfo;
import com.yunpay.serv.model.MemberBonusLs;
import com.yunpay.serv.model.MemberBonusRule;
import com.yunpay.serv.model.MemberCardTemplate;
import com.yunpay.serv.model.Membercard;
import com.yunpay.serv.model.WechatConfigKey;
import com.yunpay.serv.service.busi.CardCouponService;
import com.yunpay.serv.service.busi.CardInfoService;
import com.yunpay.serv.service.busi.MemCardTemplateService;
import com.yunpay.serv.service.busi.MembercardService;
import com.yunpay.serv.service.card.CardNotifyService;
import com.yunpay.wx.rep.member.MemUserInfoRep;
import com.yunpay.wx.service.WechatMemberService;

/**
 * 文件名称:    CardNotifyServiceImpl.java
 * 内容摘要:    卡券通知事件业务处理类
 * 主要处理微信或支付宝平台在卡券相关的业务流程中发起的回调通知，
 * 该业务类封装了不同的通知对应的业务处理方法，比如：卡券审核通知、
 * 用户领卡、转让、激活、核销等通知。
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年8月9日上午11:55:28 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年8月9日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
@Service("cardNotifyService")
public class CardNotifyServiceImpl implements CardNotifyService{
	
	@Autowired
	private CardInfoService cardInfoService;
	@Autowired
	private CardCouponService cardCouponService;
	@Autowired
	private MemCardTemplateService memCardTemplateService;
	@Autowired
	private MembercardService membercardService;
	@Autowired
	private TokenService tokenService;
	@Autowired
	private WechatMemberService wechatMemberService;
	@Autowired
	private WechatConfigDao wechatConfigDao;
	@Autowired
	private MemBonusRuleDao memBonusRuleDao;
	@Autowired
	private MemBonusLsDao memBonusLsDao;

	/**
	 * @Description:卡券审核事件处理
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月9日下午1:48:38 
	 * @param recvMap
	 * @return
	 */
	@Override
	public boolean cardCheck(Map<String, String> recvMap,int count) {
		//微信审核状态 2：未通过，3：审核通过
		int status=CardStatus.CHECK_NOTOK.getValue();
		if("card_pass_check".equals(recvMap.get("Event"))){
			status=CardStatus.CHECK_OK.getValue();
		}
		//修改卡券状态为审核通过
		if(cardInfoService.updCardStatus(recvMap.get("CardId"), status)){
			return true;
		}
		//修改会员卡状态为审核通过
		if(memCardTemplateService.updMemCardStatus(recvMap.get("CardId"), status)){
			return true;
		}
		//如都未修改到信息记录，则表示卡券或会员卡信息还未添加到数据库
		//注，此处卡券或会员卡信息还未添加到数据库的原因是调用微信创建会员卡接口时，有可能创建接口还未返回结果，但是审核事件已推送
		if(count<4){
			//最多重复4次，每次等待1秒
			try {
				Thread.sleep(1000);
				cardCheck(recvMap, ++count);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * @Description:用户领取卡券事件处理
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月9日下午4:25:36 
	 * @param recvMap
	 * @return
	 */
	@Override
	@Transactional
	public boolean userGetCard(Map<String, String> recvMap) {
		String cardId = recvMap.get("CardId");
		CardInfo cardInfo = cardInfoService.findCardByCardId(cardId); 
		try{
			if(null!=cardInfo){
				//保存用户领取的卡券信息
				saveCardCoupon(recvMap,cardInfo);
			}else{
				//保存用户领取的会员卡信息
				MemberCardTemplate memberCardTemplate = memCardTemplateService.findMemCardTempByWxCardId(cardId);
				if(memberCardTemplate!=null){
					Membercard membercard = membercardService.queryMembercard(recvMap.get("CardId"), recvMap.get("UserCardCode"));
					if(membercard==null){
						//如果为空则保存会员信息
						saveMembercard(recvMap, memberCardTemplate);
					}
					else{
						//如果不为空(用户删除会员卡后重新领取的情况)，则修改会员状态为已激活
						membercardService.updMembercardStatus(recvMap.get("CardId"), 
								recvMap.get("UserCardCode"), MembercardStatus.ACTIVE.getValue());
					}
				}
			}
		}catch(Exception e){
			Log.error("卡券领取事件异常"+e.toString());
			return false;
		}
		return true;
	}
	
	
	/**
	 * @Description: 保存用户领取的会员卡信息
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月19日下午2:43:42 
	 * @param recvMap
	 * @return
	 */
	private boolean saveMembercard(Map<String, String> recvMap,MemberCardTemplate memberCardTemplate){
		Membercard membercard = new Membercard();
		membercard.setTemplate_id(memberCardTemplate.getId());
		membercard.setCard_id(recvMap.get("CardId"));//对应会员卡卡券模板ID
		membercard.setUserCardCode(recvMap.get("UserCardCode"));//会员卡Code码
		membercard.setMerchantNo(memberCardTemplate.getMerchantNo());
		membercard.setStatus(MembercardStatus.GETED.getValue());//已领取
		membercard.setBonus(0);// 积分为0
		membercard.setBalance(Float.parseFloat("0.00"));
		membercard.setCreatedAt(new Date());
		//用户领取渠道(1：支付宝；2：微信)
		membercard.setChannelType(2);
		//投放二维码时，将门店号放置在场景值里面，用于记录用户是从哪个门店领取的
		if(StringUtils.isNotBlank(recvMap.get("OuterStr"))){
			membercard.setStoreNo(recvMap.get("OuterStr"));
		}
		if(StringUtils.isNotBlank(recvMap.get("SourceScene"))){
			//微信官方设置的场景值
			membercard.setSourceScene(recvMap.get("SourceScene"));
		}
		membercardService.saveMembercard(membercard);
		return true;
	}
	
	/**
	 * @Description: 保存用户领取的卡券信息
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月19日下午2:43:30 
	 * @param recvMap
	 * @param cardInfo
	 * @return
	 */
	private boolean saveCardCoupon(Map<String, String> recvMap,CardInfo cardInfo){
		CardCoupon cardCoupon = new CardCoupon();
		//封装卡券领取信息
		cardCoupon.setAppidCardId(recvMap.get("CardId"));
		cardCoupon.setSn(recvMap.get("UserCardCode").toString());
		cardCoupon.setAppidUserId(recvMap.get("FromUserName"));
		cardCoupon.setFriendUserName(recvMap.get("FriendUserName"));
		cardCoupon.setOldUserCardCode(recvMap.get("OldUserCardCode"));
		cardCoupon.setTitle(cardInfo.getTitle());
		cardCoupon.setSource(cardInfo.getPutchannel()+"");
		cardCoupon.setCreatedAt(new Date());
		cardCoupon.setStartTime(cardInfo.getStartDate());
		cardCoupon.setEndTime(cardInfo.getEndDate());
		cardCoupon.setType(cardInfo.getType());
		cardCoupon.setQuota(cardInfo.getLimitMoney());
		cardCoupon.setPrice(cardInfo.getDiscountMoney());
		cardCoupon.setDiscount(cardInfo.getDiscount());
		cardCoupon.setOrgNo(cardInfo.getOrgNo());
		cardCoupon.setPagesPer(cardInfo.getLimitNum());
		//用户领取场景值
		cardCoupon.setInfo(recvMap.get("OuterStr"));
		cardCoupon.setStatus(CouponStatus.NOT_USE.getValue());
		//添加卡券领取信息
		cardCouponService.createCardCoupon(cardCoupon);
		//如果为朋友赠送的卡券
		if("1".equals(recvMap.get("IsGiveByFriend"))){
			//修改赠送方领取的卡券状态为已赠送
			cardCouponService.updCardCouponStatus(recvMap.get("CardId"), 
					recvMap.get("OldUserCardCode"), CouponStatus.GIVED.getValue());
		}else{
			//修改卡券领取数量+1
			cardInfoService.updateCardRecvNum(recvMap.get("CardId"));
		}
		return true;
	}

	/**
	 * @Description:用户发起赠送事件处理
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月9日下午5:14:30 
	 * @param recvMap
	 * @return
	 */
	@Override
	public boolean userGivingCard(Map<String, String> recvMap) {
		//判断是否转赠退回，0代表不是，1代表是
		if("0".equals(recvMap.get("IsReturnBack"))){
			//修改卡券领取状态为赠送中
			return cardCouponService.updCardCouponStatus(recvMap.get("CardId"), 
					recvMap.get("UserCardCode"), CouponStatus.GIVING.getValue());
		}else{
			//修改卡券领取状态为未使用
			return cardCouponService.updCardCouponStatus(recvMap.get("CardId"), 
					recvMap.get("UserCardCode"), CouponStatus.NOT_USE.getValue());
		}
	}


	/**
	 * @Description:用户删除卡券事件处理
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月9日下午5:14:30 
	 * @param recvMap
	 * @return
	 */
	@Override
	public boolean userDelCard(Map<String, String> recvMap) {
		//修改卡券领取状态为删除
		boolean delStatus = cardCouponService.updCardCouponStatus(recvMap.get("CardId"), recvMap.get("UserCardCode"), 
				CouponStatus.DELED.getValue());
		if(!delStatus){
			//增加会员卡判断
			//MemberCardTemplate memberCardTemplate = memCardTemplateService.findMemCardTempByWxCardId(recvMap.get("CardId"));
			//if(memberCardTemplate!=null){
				return membercardService.updMembercardStatus(recvMap.get("CardId"), 
						recvMap.get("UserCardCode"), MembercardStatus.DELED.getValue());
			//}
		}
		return delStatus;
	}

	/**
	 * @Description:用户卡券核销事件处理
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月9日下午5:21:33 
	 * @param recvMap
	 * @return
	 */
	@Override
	public boolean userConsumCard(Map<String, String> recvMap) {
		return cardCouponService.cardCouponConsum(recvMap.get("CardId"), recvMap.get("UserCardCode"));
	}

	/**
	 * @Description:会员卡激活事件处理
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月19日下午5:39:19 
	 * @param recvMap
	 * @return
	 */
	@Override
	public boolean membercardActive(Map<String, String> recvMap) {
		//查询用户会员卡信息
		Membercard membercard = membercardService.queryMembercard(recvMap.get("CardId"), recvMap.get("UserCardCode")); 
		if(membercard!=null){
			//微信会员激活和信息修改都会进入该通知事件，且无区分标志，故通过会员信息表中的手机号字段来区分
			//如果会员手机号为空，表示为会员第一次激活，否则表示会员信息修改
			if(StringUtils.isBlank(membercard.getMobile())){
				//设置会员状态为激活
				membercard.setStatus(MembercardStatus.ACTIVE.getValue());
				//获取商户积分规则信息
				MemberBonusRule memberBonusRule = memBonusRuleDao.queryMerchBonusRule(membercard.getMerchantNo());
				if(memberBonusRule!=null){
					//初始赠送积分
					if(memberBonusRule.getInit_increase_bonus()>0){
						//如果设置了激活赠送积分，添加积分赠送记录
						membercard.setBonus(memberBonusRule.getInit_increase_bonus());
						MemberBonusLs memberBonusLs = createMemberBonusLs(membercard);
						memBonusLsDao.insert(memberBonusLs);
					}
				}
			}
			WechatConfigKey wechatConfig = wechatConfigDao.findConfigByMerNo(membercard.getMerchantNo());
			if (wechatConfig == null) {
				Log.error("商户缺少微信配置！");
				return false;
			}
			//获取accessToken
			String accessToken = tokenService.recvAccessToken(wechatConfig.getAppId(), wechatConfig.getAppSecret());
			//调用微信获取会员信息接口
			MemUserInfoRep memUserInfoRep = wechatMemberService.doGetUserInfo(recvMap.get("CardId"), 
					recvMap.get("UserCardCode"), accessToken);
			//会员信息获取成功
			if(memUserInfoRep.getReturn_code()==ResultCode.SUCCESS.getCode()){
				membercard.setOpenId(memUserInfoRep.getOpenid());
				//昵称
				membercard.setNickname(memUserInfoRep.getNickname());
				//会员积分
				//membercard.setBonus(0);
				//性别
				membercard.setSex(memUserInfoRep.getCommon_field_list().get("USER_FORM_INFO_FLAG_SEX"));
				//手机
				membercard.setMobile(memUserInfoRep.getCommon_field_list().get("USER_FORM_INFO_FLAG_MOBILE"));
				//姓名
				membercard.setName(memUserInfoRep.getCommon_field_list().get("USER_FORM_INFO_FLAG_NAME"));
				//生日
				membercard.setBirthday(memUserInfoRep.getCommon_field_list().get("USER_FORM_INFO_FLAG_BIRTHDAY"));
				//身份证
				membercard.setIdcard(memUserInfoRep.getCommon_field_list().get("USER_FORM_INFO_FLAG_IDCARD"));
				//邮箱
				membercard.setEmail(memUserInfoRep.getCommon_field_list().get("USER_FORM_INFO_FLAG_EMAIL"));
				//地址
				membercard.setLocation(memUserInfoRep.getCommon_field_list().get("USER_FORM_INFO_FLAG_LOCATION"));
				//爱好
				membercard.setHabit(memUserInfoRep.getCommon_field_list().get("USER_FORM_INFO_FLAG_HABIT"));
			}
			//修改会员信息
			membercardService.updMembercard(membercard);
			return true;
		}
		return false;
	}
	
	/**
	 * @Description: 构造激活赠送积分流水
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月25日上午11:43:24 
	 * @param membercard
	 * @return
	 */
	private MemberBonusLs createMemberBonusLs(Membercard membercard){
		MemberBonusLs memberBonusLs = new MemberBonusLs();
		//激活赠送
		memberBonusLs.setSource(4);
		memberBonusLs.setAfterBonus(membercard.getBonus());
		memberBonusLs.setInfo("激活赠送");
		memberBonusLs.setUserCardCode(membercard.getUserCardCode());
		memberBonusLs.setBeforeBonus(0);
		memberBonusLs.setChangeBonus(membercard.getBonus());
		memberBonusLs.setChangeType(0);
		memberBonusLs.setCreatedAt(new Date());
		memberBonusLs.setMerchantNo(membercard.getMerchantNo());
		return memberBonusLs;
	}

}
