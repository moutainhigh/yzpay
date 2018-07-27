package com.yunpay.serv.service.member.impl;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.yunpay.common.exception.YunPayException;
import com.yunpay.common.utils.AmountUtil;
import com.yunpay.common.utils.CommonUtil;
import com.yunpay.common.utils.EnumUtil.CardDateType;
import com.yunpay.common.utils.EnumUtil.MemberActive;
import com.yunpay.common.utils.ResultEnum.ErrorCode;
import com.yunpay.common.utils.ResultEnum.ResultCode;
import com.yunpay.serv.common.TokenService;
import com.yunpay.serv.dao.WechatConfigDao;
import com.yunpay.serv.model.WechatConfigKey;
import com.yunpay.serv.rep.Message;
import com.yunpay.serv.req.member.MemActivateReqDto;
import com.yunpay.serv.req.member.MemCardUpdReqDto;
import com.yunpay.serv.req.member.MemQrcodeReqDto;
import com.yunpay.serv.req.member.MemberCardReqDto;
import com.yunpay.serv.service.member.MemberCardService;
import com.yunpay.wx.rep.card.AppBaseRep;
import com.yunpay.wx.rep.card.CreateCardRep;
import com.yunpay.wx.rep.card.CreateQrCodeRep;
import com.yunpay.wx.rep.card.UpdCardRep;
import com.yunpay.wx.req.card.CreateQrCodeReq;
import com.yunpay.wx.req.member.ActivateFieldReq;
import com.yunpay.wx.req.member.BonusRule;
import com.yunpay.wx.req.member.MemCardUpdReq;
import com.yunpay.wx.req.member.MemberBaseInfo;
import com.yunpay.wx.req.member.MemberCardReq;
import com.yunpay.wx.service.WechatCardService;
import com.yunpay.wx.service.WechatMemberService;

/**
 * 文件名称:    MemberCardServiceImpl.java
 * 内容摘要:    微信会员卡业务核心处理类
 * 封装微信会员卡业务逻辑的核心处理（主要方法：会员卡创建、修改、领取码等）。
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年8月28日上午9:40:46 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年8月28日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
@Service("memberCardService")
public class MemberCardServiceImpl extends MemberCardService{
	@Autowired
	private WechatMemberService wechatMemberService;
	@Autowired
	private WechatConfigDao wechatConfigDao;
	@Autowired
	private TokenService tokenService;
	@Autowired
	private WechatCardService wechatCardService;
	
	/**
	 * 创建微信会员卡
	 * @Description:
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月4日下午3:34:03 
	 * @param memberCardReq
	 * @param merchantNum
	 * @return
	 */
	@Override
	public Message createMemberCard(MemberCardReqDto memberCardDto) {
		WechatConfigKey wechatConfig = wechatConfigDao.findConfigByMerNo(memberCardDto.getMerchant_num());
		if (wechatConfig == null) {
			return new Message(ErrorCode.ILLEGAL_PAYCONFIG.getCode(),ErrorCode.ILLEGAL_PAYCONFIG.getDes());
		}
		try{
			//获取accessToken
			String accessToken = tokenService.recvAccessToken(wechatConfig.getAppId(), wechatConfig.getAppSecret());
			//将接收的信息转化为微信请求信息
			MemberCardReq memberCardReq = this.covtToMemCardReq(memberCardDto);
			//调用微信会员卡创建接口添加会员卡
			CreateCardRep rep = wechatMemberService.doCreateMemberCard(memberCardReq, accessToken);
			if(ResultCode.SUCCESS.getCode().equals(rep.getReturn_code())){
				//会员卡创建成功
				JSONObject ret = new JSONObject();
				ret.put("card_id", rep.getCard_id());
				//如果激活方式为微信一键激活
				if(memberCardDto.getActivate_type()==MemberActive.WX_ACTIVE.getCode()){
					ActivateFieldReq activateReq = new ActivateFieldReq(rep.getCard_id(),
							memberCardDto.getActivate_fields().getRequire_fields(),
							memberCardDto.getActivate_fields().getCommon_fields());
					//调用激活参数设置接口，设置激活参数
					AppBaseRep activateRep = wechatMemberService.doActivateSet(activateReq, accessToken);
					if(ResultCode.SUCCESS.getCode().equals(activateRep.getReturn_code())){
						//会员卡创建成功且激活参数设置成功
						return new Message(ret);
					}else{
						//会员卡创建成功，激活参数设置失败
						return new Message(rep.getErr_code(),rep.getErr_code_des());
					}
				}
				return new Message(ret);
			}else{
				return new Message(rep.getErr_code(),rep.getErr_code_des());
			}
		}catch (YunPayException e) {
			e.printStackTrace();
			return new Message(e.getErrCode(),e.getErrMsg());
		}
	}
	
	/**
	 * 将接收的信息转化为微信接口请求信息(新增)
	 * @Description: 
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月5日上午11:35:23 
	 * @param memberCardDto
	 * @return
	 */
	private MemberCardReq covtToMemCardReq(MemberCardReqDto memberCardDto){
		//是否支持分享
		boolean canShare = "1".equals(memberCardDto.getCan_share());
		//是否支持赠送
		boolean canGiveFriend = "1".equals(memberCardDto.getCan_give_friend());
		//卡券基本信息
		MemberBaseInfo memberBaseInfo = new MemberBaseInfo(memberCardDto.getLogo_url(),memberCardDto.getCode_type(),
				memberCardDto.getMerchant_name(),memberCardDto.getTitle(),CommonUtil.getFinalColor(0, memberCardDto.getColor()),
				memberCardDto.getNotice(),memberCardDto.getDescription(),Integer.parseInt(memberCardDto.getQuantity()),
				CardDateType.PERMANENT.getCode(),memberCardDto.getService_phone(),canShare,canGiveFriend);
		//积分规则信息
		BonusRule bonusRule =null;
		//卡券积分规则
		if(memberCardDto.getBonus_rule()!=null){
			bonusRule = new BonusRule(AmountUtil.changeIY2F(memberCardDto.getBonus_rule().getCost_money_unit()),
				memberCardDto.getBonus_rule().getIncrease_bonus(),memberCardDto.getBonus_rule().getMax_increase_bonus(),
				memberCardDto.getBonus_rule().getInit_increase_bonus(),
				memberCardDto.getBonus_rule().getCost_bonus_unit(),AmountUtil.changeIY2F(memberCardDto.getBonus_rule().getReduce_money()),
				AmountUtil.changeIY2F(memberCardDto.getBonus_rule().getLeast_money_to_use_bonus()),
				memberCardDto.getBonus_rule().getMax_reduce_bonus());
		}
		//是否显示积分
		boolean supplyBonus = "1".equals(memberCardDto.getSupply_bonus());
		//会员卡专属信息
		MemberCardReq memberCardReq = new MemberCardReq(memberCardDto.getBackground_pic_url(),memberBaseInfo,
				memberCardDto.getPrerogative(),memberCardDto.getActivate_type(),supplyBonus,memberCardDto.getBonus_url(),
				memberCardDto.getBonus_cleared(),memberCardDto.getBonus_rules(),bonusRule,
				100-AmountUtil.changeIY2F(memberCardDto.getDiscount()));
		//设置自定义属性栏目
		if(memberCardDto.getCustom_field1()!=null){
			memberCardReq.setCustom_field1(memberCardDto.getCustom_field1().getName(), 
					memberCardDto.getCustom_field1().getUrl());
		}
		if(memberCardDto.getCustom_field2()!=null){
			memberCardReq.setCustom_field2(memberCardDto.getCustom_field2().getName(), 
					memberCardDto.getCustom_field2().getUrl());
		}
		if(memberCardDto.getCustom_field3()!=null){
			memberCardReq.setCustom_field3(memberCardDto.getCustom_field3().getName(), 
					memberCardDto.getCustom_field3().getUrl());
		}
		//设置自定义菜单栏目
		if(memberCardDto.getCustom_cell1()!=null){
			memberCardReq.setCustom_cell1(memberCardDto.getCustom_cell1().getName(), 
					memberCardDto.getCustom_cell1().getTips(), memberCardDto.getCustom_cell1().getUrl());
		}
		return memberCardReq;
	}
	
	/**
	 * 将接收的信息转化为微信接口请求信息(修改)
	 * @Description: 
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月5日上午11:35:23 
	 * @param memberCardDto
	 * @return
	 */
	private MemCardUpdReq covtToMemCardUpdReq(MemCardUpdReqDto memberCardDto){
		//是否支持分享
		boolean canShare = "1".equals(memberCardDto.getCan_share());
		//是否支持赠送
		boolean canGiveFriend = "1".equals(memberCardDto.getCan_give_friend());
		//卡券基本信息
		MemberBaseInfo memberBaseInfo = new MemberBaseInfo(memberCardDto.getLogo_url(),memberCardDto.getCode_type(),
				memberCardDto.getTitle(),CommonUtil.getFinalColor(0, memberCardDto.getColor()),
				memberCardDto.getNotice(),memberCardDto.getDescription(),memberCardDto.getService_phone(),canShare,canGiveFriend);
		//积分规则信息
		BonusRule bonusRule =null;
		//卡券积分规则
		if(memberCardDto.getBonus_rule()!=null){
			bonusRule = new BonusRule(AmountUtil.changeIY2F(memberCardDto.getBonus_rule().getCost_money_unit()),
				memberCardDto.getBonus_rule().getIncrease_bonus(),memberCardDto.getBonus_rule().getMax_increase_bonus(),
				memberCardDto.getBonus_rule().getInit_increase_bonus(),
				memberCardDto.getBonus_rule().getCost_bonus_unit(),AmountUtil.changeIY2F(memberCardDto.getBonus_rule().getReduce_money()),
				AmountUtil.changeIY2F(memberCardDto.getBonus_rule().getLeast_money_to_use_bonus()),
				memberCardDto.getBonus_rule().getMax_reduce_bonus());
		}
		//是否显示积分
		boolean supplyBonus = "1".equals(memberCardDto.getSupply_bonus());
		//会员卡专属信息
		MemberCardReq memberCardReq = new MemberCardReq(memberCardDto.getBackground_pic_url(),memberBaseInfo,
				memberCardDto.getPrerogative(),memberCardDto.getActivate_type(),supplyBonus,memberCardDto.getBonus_url(),
				memberCardDto.getBonus_cleared(),memberCardDto.getBonus_rules(),bonusRule,
				100-AmountUtil.changeIY2F(memberCardDto.getDiscount()));
		MemCardUpdReq memCardUpdReq = new MemCardUpdReq(memberCardDto.getCard_id(),memberCardReq);
		return memCardUpdReq;
	}
	
	
	
	/**
	 * 修改微信会员卡
	 * @Description:
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月4日下午3:34:03 
	 * @param memberCardReq
	 * @param merchantNum
	 * @return
	 */
	@Override
	public Message updMemberCard(MemCardUpdReqDto memberCardDto) {
		WechatConfigKey wechatConfig = wechatConfigDao.findConfigByMerNo(memberCardDto.getMerchant_num());
		if (wechatConfig == null) {
			return new Message(ErrorCode.ILLEGAL_PAYCONFIG.getCode(),ErrorCode.ILLEGAL_PAYCONFIG.getDes());
		}
		try{
			//获取accessToken
			String accessToken = tokenService.recvAccessToken(wechatConfig.getAppId(), wechatConfig.getAppSecret());
			//将接收的信息转化为微信请求信息
			MemCardUpdReq memCardUpdReq = this.covtToMemCardUpdReq(memberCardDto);
			//调用微信会员卡创建接口添加会员卡
			UpdCardRep rep = wechatMemberService.doUpdMemberCard(memCardUpdReq, accessToken);
			if(ResultCode.SUCCESS.getCode().equals(rep.getReturn_code())){
				//会员卡修改成功
				return new Message(null);
			}else{
				return new Message(rep.getErr_code(),rep.getErr_code_des());
			}
		}catch (YunPayException e) {
			e.printStackTrace();
			return new Message(e.getErrCode(),e.getErrMsg());
		}
	}
	
	

	/**
	 * @Description:设置会员卡一键激活参数
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月5日下午2:27:46 
	 * @param memActivateDto
	 * @return
	 */
	@Override
	public Message createActivateParam(MemActivateReqDto memActivateDto) {
		try{
			WechatConfigKey wechatConfig = wechatConfigDao.findConfigByMerNo(memActivateDto.getMerchant_num());
			if (wechatConfig == null) {
				return new Message(ErrorCode.ILLEGAL_PAYCONFIG.getCode(),ErrorCode.ILLEGAL_PAYCONFIG.getDes());
			}
			//获取accessToken
			String accessToken = tokenService.recvAccessToken(wechatConfig.getAppId(), wechatConfig.getAppSecret());
			//封装激活参数请求实体
			ActivateFieldReq req = new ActivateFieldReq(memActivateDto.getCard_id(),
					memActivateDto.getRequire_fields(),memActivateDto.getCommon_fields());
			//调用微信激活参数设置接口
			AppBaseRep rep = wechatMemberService.doActivateSet(req, accessToken);
			if(ResultCode.SUCCESS.getCode().equals(rep.getReturn_code())){
				return new Message(null);
			}else{
				return new Message(rep.getErr_code(),rep.getErr_code_des());
			}
		}catch (YunPayException e) {
			e.printStackTrace();
			return new Message(e.getErrCode(),e.getErrMsg());
		}
	}

	/**
	 * @Description:创建会员卡领取二维码
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月5日下午3:53:19 
	 * @param merchantNum
	 * @param cardId
	 * @return
	 */
	@Override
	public Message qrCodeCreate(MemQrcodeReqDto memQrcodeReq) {
		WechatConfigKey wechatConfig = wechatConfigDao.findConfigByMerNo(memQrcodeReq.getMerchant_num());
		if (wechatConfig == null) {
			return new Message(ErrorCode.ILLEGAL_PAYCONFIG.getCode(),ErrorCode.ILLEGAL_PAYCONFIG.getDes());
		}
		try{
			String accessToken = tokenService.recvAccessToken(wechatConfig.getAppId(), wechatConfig.getAppSecret());
			//保存微信卡券信息
			CreateQrCodeReq qrCodeReq = new CreateQrCodeReq();
			qrCodeReq.setAction_name("QR_CARD");
			qrCodeReq.setAction_info(memQrcodeReq.getCard_id(),memQrcodeReq.getStore_num());
			//发送卡券信息至微信平台
			CreateQrCodeRep rep = wechatCardService.doCreateQrCode(qrCodeReq, accessToken);
			if(ResultCode.SUCCESS.getCode().equals(rep.getReturn_code())){
				Map<String,String> retMap = new HashMap<String,String>();
				retMap.put("url", rep.getUrl());
				retMap.put("show_qrcode_url", rep.getShow_qrcode_url());
				return new Message(retMap);
			}else{
				return new Message(rep.getErr_code(),rep.getErr_code_des());
			}
		}catch (YunPayException e) {
			e.printStackTrace();
			return new Message(e.getErrCode(),e.getErrMsg());
		}
	}

	

}
