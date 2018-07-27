package com.yunpay.serv.service.card.impl;


import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.yunpay.common.exception.YunPayException;
import com.yunpay.common.utils.AmountUtil;
import com.yunpay.common.utils.CommonUtil;
import com.yunpay.common.utils.DateUtil;
import com.yunpay.common.utils.EnumUtil.CardDateType;
import com.yunpay.common.utils.EnumUtil.CardStatus;
import com.yunpay.common.utils.EnumUtil.CardType;
import com.yunpay.common.utils.EnumUtil.PayChannel;
import com.yunpay.common.utils.ResultEnum.ErrorCode;
import com.yunpay.common.utils.ResultEnum.ResultCode;
import com.yunpay.serv.common.TokenService;
import com.yunpay.serv.dao.WechatConfigDao;
import com.yunpay.serv.model.CardInfo;
import com.yunpay.serv.model.WechatConfigKey;
import com.yunpay.serv.rep.Message;
import com.yunpay.serv.rep.card.CardRepDto;
import com.yunpay.serv.req.card.CardReqDto;
import com.yunpay.serv.service.busi.CardCouponService;
import com.yunpay.serv.service.busi.CardInfoService;
import com.yunpay.serv.service.card.CardService;
import com.yunpay.wx.rep.card.AppBaseRep;
import com.yunpay.wx.rep.card.CardConsumeRep;
import com.yunpay.wx.rep.card.CreateCardRep;
import com.yunpay.wx.rep.card.CreateQrCodeRep;
import com.yunpay.wx.rep.card.QrCardCodeRep;
import com.yunpay.wx.rep.card.UpdCardRep;
import com.yunpay.wx.req.card.CardBaseInfo;
import com.yunpay.wx.req.card.CardReq;
import com.yunpay.wx.req.card.CashCardReq;
import com.yunpay.wx.req.card.CreateQrCodeReq;
import com.yunpay.wx.req.card.DisCardReq;
import com.yunpay.wx.req.card.GeneralCardReq;
import com.yunpay.wx.req.card.GiftCardReq;
import com.yunpay.wx.req.card.GrouponCardReq;
import com.yunpay.wx.req.card.QrCardCodeReq;
import com.yunpay.wx.service.WechatCardService;

/**
 * 文件名称:    CardServiceImpl.java
 * 内容摘要:    微信卡券业务核心处理类
 * 封装微信卡券业务逻辑的核心处理方法（主要方法：卡券创建、修改、领取码、核销、删除等）。
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年8月28日上午9:39:42 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年8月28日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
@Service("cardService")
public class CardServiceImpl extends CardService{
	
	@Autowired
	private WechatCardService wechatCardService;
	@Autowired
	private WechatConfigDao wechatConfigDao;
	@Autowired
	private CardInfoService cardInfoService;
	@Autowired
	private TokenService tokenService;
	@Autowired
	private CardCouponService cardCouponService;
	
	
	/**
	 * @Description:创建卡券
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月1日下午5:17:01 
	 * @param cardReq
	 * @param accessToken
	 * @return
	 */
	@Override
	public Message cardCreate(CardReqDto req) {
		WechatConfigKey wechatConfig = wechatConfigDao.findConfigByMerNo(req.getMerchant_num());
		if (wechatConfig == null) {
			return new Message(ErrorCode.ILLEGAL_PAYCONFIG.getCode(),ErrorCode.ILLEGAL_PAYCONFIG.getDes());
		}
		try{
			//获取accessToken
			String accessToken = tokenService.recvAccessToken(wechatConfig.getAppId(), wechatConfig.getAppSecret());
			//发送卡券信息至微信平台
			CreateCardRep rep = wechatCardService.doCreateCard(parseCreateCardReq(req), accessToken);
			if(ResultCode.SUCCESS.getCode().equals(rep.getReturn_code())){
				//保存微信卡券信息
				req.setCard_id(rep.getCard_id());
				cardInfoService.createCardInfo(req);
				JSONObject ret = new JSONObject();
				ret.put("card_id", rep.getCard_id());
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
	 * @Description: 转换为微信接口参数
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月17日上午11:55:35 
	 * @param req
	 * @return
	 */
	public CardReq parseCreateCardReq(CardReqDto req){
		CardBaseInfo baseInfo = new CardBaseInfo();
		baseInfo.setLogo_url(req.getLogo_url());
		baseInfo.setCode_type(req.getCode_type());
		baseInfo.setBrand_name(req.getMerchant_name());
		baseInfo.setTitle(req.getTitle());
		//将颜色转为微信颜色值
		baseInfo.setColor(CommonUtil.getFinalColor(0, req.getColor()));
		baseInfo.setNotice(req.getNotice());
		baseInfo.setDescription(req.getDescription());
		baseInfo.setSku(Integer.valueOf(req.getQuantity()));
		//设置卡券有效期信息
		String beginDate = DateUtil.formatDate(req.getBegin_timestamp(),"yyyy-MM-dd").getTime()/1000+"";
		String endDate = DateUtil.formatDate(req.getEnd_timestamp(),"yyyy-MM-dd").getTime()/1000+"";
		baseInfo.setDate_info(CardDateType.FIX_TIME_RANGE.getCode(),beginDate ,endDate, null, null);
		//非必填项
		baseInfo.setService_phone(req.getService_phone());
		baseInfo.setGet_limit(Integer.valueOf(req.getGet_limit()));
		baseInfo.setUse_limit(Integer.valueOf(req.getGet_limit()));
		baseInfo.setCan_share("1".equals(req.getCan_share()));
		baseInfo.setCan_give_friend("1".equals(req.getCan_give_friend()));
		
		if(CardType.GROUPON.getValue().equals(req.getCard_type())){
			//团购券
			GrouponCardReq grouponReq = new GrouponCardReq();
			grouponReq.setCard_type("GROUPON");
			grouponReq.setGroupon(req.getDefault_detail(), baseInfo);
			return grouponReq;
		}else if(CardType.CASH.getValue().equals(req.getCard_type())){
			//现金券
			CashCardReq cashReq = new CashCardReq();
			cashReq.setCard_type("CASH");
			cashReq.setCash(Integer.valueOf(AmountUtil.changeY2F(req.getLeast_cost())), 
					Integer.valueOf(AmountUtil.changeY2F(req.getReduce_cost())), baseInfo);
			return cashReq;
		}else if(CardType.DISCOUNT.getValue().equals(req.getCard_type())){
			//折扣券
			DisCardReq disReq = new DisCardReq();
			disReq.setCard_type("DISCOUNT");
			disReq.setDiscount(100-Integer.valueOf(AmountUtil.changeY2F(req.getDiscount())),baseInfo);
			return disReq;
		}else if(CardType.GIFT.getValue().equals(req.getCard_type())){
			//兑换券
			GiftCardReq giftReq = new GiftCardReq();
			giftReq.setCard_type("GIFT");
			giftReq.setGift(req.getDefault_detail(), baseInfo);
			return giftReq;
		}else{
			//优惠券
			GeneralCardReq generalReq = new GeneralCardReq();
			generalReq.setCard_type("GENERAL_COUPON");
			generalReq.setGeneral_coupon(req.getDefault_detail(), baseInfo);
			return generalReq;
		}
	}
	
	/**
	 * @Description: 转换为微信接口参数
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月17日上午11:56:04 
	 * @param req
	 * @return
	 */
	public CardReq parseUpdCardReq(CardReqDto req){
		CardBaseInfo baseInfo = new CardBaseInfo();
		baseInfo.setLogo_url(req.getLogo_url());
		baseInfo.setCode_type(req.getCode_type());
		baseInfo.setTitle(req.getTitle());
		baseInfo.setColor(CommonUtil.getFinalColor(0, req.getColor()));
		baseInfo.setNotice(req.getNotice());
		baseInfo.setDescription(req.getDescription());
		//设置卡券有效期信息
		if(StringUtils.isNotBlank(req.getBegin_timestamp()) && 
				StringUtils.isNotBlank(req.getEnd_timestamp())){
			String beginDate = DateUtil.formatDate(req.getBegin_timestamp(),"yyyy-MM-dd").getTime()/1000+"";
			String endDate = DateUtil.formatDate(req.getEnd_timestamp(),"yyyy-MM-dd").getTime()/1000+"";
			baseInfo.setDate_info(CardDateType.FIX_TIME_RANGE.getCode(),beginDate ,endDate, null, null);
		}
		//非必填项
		baseInfo.setService_phone(req.getService_phone());
		baseInfo.setGet_limit(Integer.valueOf(req.getGet_limit()));
		baseInfo.setUse_limit(Integer.valueOf(req.getGet_limit()));
		baseInfo.setCan_share("1".equals(req.getCan_share()));
		baseInfo.setCan_give_friend("1".equals(req.getCan_give_friend()));
		
		if(CardType.GROUPON.getValue().equals(req.getCard_type())){
			//团购券
			GrouponCardReq grouponReq = new GrouponCardReq();
			grouponReq.setGroupon(req.getDefault_detail(), baseInfo);
			return grouponReq;
		}else if(CardType.CASH.getValue().equals(req.getCard_type())){
			//现金券
			CashCardReq cashReq = new CashCardReq();
			cashReq.setCash(baseInfo);
			return cashReq;
		}else if(CardType.DISCOUNT.getValue().equals(req.getCard_type())){
			//折扣券
			DisCardReq disReq = new DisCardReq();
			disReq.setDiscount(baseInfo);
			return disReq;
		}else if(CardType.GIFT.getValue().equals(req.getCard_type())){
			//兑换券
			GiftCardReq giftReq = new GiftCardReq();
			giftReq.setGift(req.getDefault_detail(), baseInfo);
			return giftReq;
		}else{
			//优惠券
			GeneralCardReq generalReq = new GeneralCardReq();
			generalReq.setGeneral_coupon(req.getDefault_detail(), baseInfo);
			return generalReq;
		}
	}
	
	/**
	 * @Description:修改微信卡券
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月17日上午10:04:11 
	 * @param req
	 * @return
	 */
	@Override
	public Message cardUpdate(CardReqDto req) {
		CardInfo cardInfo = cardInfoService.findCardByCardId(req.getCard_id());
		if(cardInfo==null || cardInfo.getStatus()==CardStatus.DEL.getValue()){
			//卡券为空或状态为已删除，提示卡券不存在
			return new Message(ErrorCode.CARD_NOT_EXIST.getCode(),ErrorCode.CARD_NOT_EXIST.getDes());
		}
		WechatConfigKey wechatConfig = wechatConfigDao.findConfigByMerNo(req.getMerchant_num());
		if (wechatConfig == null) {
			return new Message(ErrorCode.ILLEGAL_PAYCONFIG.getCode(),ErrorCode.ILLEGAL_PAYCONFIG.getDes());
		}
		try{
			//获取accessToken
			String accessToken = tokenService.recvAccessToken(wechatConfig.getAppId(), wechatConfig.getAppSecret());
			CardReq cardReq = parseUpdCardReq(req);
			cardReq.setCard_id(req.getCard_id());
			//发送卡券信息至微信平台
			UpdCardRep rep = wechatCardService.doUpdateCard(cardReq, accessToken);
			if(ResultCode.SUCCESS.getCode().equals(rep.getReturn_code())){
				//修改本地卡券信息
				cardInfoService.updateCardInfo(cardInfo,req);
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
	 * @Description:创建微信卡券领取码
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月2日下午2:55:09 
	 * @param merchantNum 商户号
	 * @param cardId 卡券id
	 * @return
	 */
	@Override
	public Message qrCodeCreate(String merchantNum,String cardId) {
		WechatConfigKey wechatConfig = wechatConfigDao.findConfigByMerNo(merchantNum);
		if (wechatConfig == null) {
			return new Message(ErrorCode.ILLEGAL_PAYCONFIG.getCode(),ErrorCode.ILLEGAL_PAYCONFIG.getDes());
		}
		CardInfo cardInfo = cardInfoService.findCardByCardId(cardId);
		if(cardInfo==null || cardInfo.getStatus()==CardStatus.DEL.getValue()){
			return new Message(ErrorCode.CARD_NOT_EXIST.getCode(),ErrorCode.CARD_NOT_EXIST.getDes());
		}
		try{
			String accessToken = tokenService.recvAccessToken(wechatConfig.getAppId(), wechatConfig.getAppSecret());
			//保存微信卡券信息
			CreateQrCodeReq qrCodeReq = new CreateQrCodeReq();
			qrCodeReq.setAction_name("QR_CARD");
			qrCodeReq.setAction_info(cardId);
			//发送卡券信息至微信平台
			CreateQrCodeRep rep = wechatCardService.doCreateQrCode(qrCodeReq, accessToken);
			if(ResultCode.SUCCESS.getCode().equals(rep.getReturn_code())){
				Map<String,String> retMap = new HashMap<String,String>();
				//retMap.put("ticket", rep.getTicket());
				//retMap.put("expireSeconds", rep.getExpire_seconds());
				retMap.put("url", rep.getUrl());
				retMap.put("show_qrcode_url", rep.getShow_qrcode_url());
				//设置卡券二维码信息
				cardInfoService.updCardQrCode(cardId, rep.getShow_qrcode_url(), rep.getUrl());
				return new Message(retMap);
			}else{
				return new Message(rep.getErr_code(),rep.getErr_code_des());
			}
		}catch (YunPayException e) {
			e.printStackTrace();
			return new Message(e.getErrCode(),e.getErrMsg());
		}
	}
		
	/**
	 * @Description: 查询卡券核销码是否可用
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月10日下午5:33:53 
	 * @param req
	 * @param accessToken
	 * @return
	 * @throws YunPayException
	 */
	public boolean queryCardCode(String cardCode,String accessToken) throws YunPayException{
		//构造查询请求类
		QrCardCodeReq req = new QrCardCodeReq(cardCode,true);
		//调用查询
		QrCardCodeRep rep = wechatCardService.doQueryCardCode(req, accessToken);
		if(ResultCode.SUCCESS.getCode().equals(rep.getReturn_code())){
			//卡券可用
			return true;
		}
		//卡券不可用时抛出异常，将微信返回的原因封装到自定义异常中
		throw new YunPayException(rep.getErr_code(),rep.getErr_code_des());
	}

	/**
	 * @Description:卡券核销
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月10日下午5:50:28 
	 * @param merchantNum
	 * @param cardCode
	 * @return
	 */
	@Override
	public Message cardConsume(String merchantNum,String cardCode) {
		//查询商户微信配置
		Message retMsg=null;
		WechatConfigKey wechatConfig = wechatConfigDao.findConfigByMerNo(merchantNum);
		if (wechatConfig == null) {
			return new Message(ErrorCode.ILLEGAL_PAYCONFIG.getCode(),ErrorCode.ILLEGAL_PAYCONFIG.getDes());
		}
		try{
			String accessToken = tokenService.recvAccessToken(wechatConfig.getAppId(), wechatConfig.getAppSecret());
			//构造查询请求类
			QrCardCodeReq req = new QrCardCodeReq(cardCode,true);
			//调用查询
			QrCardCodeRep codeRep = wechatCardService.doQueryCardCode(req, accessToken);
			if(ResultCode.SUCCESS.getCode().equals(codeRep.getReturn_code())){
				//卡券核销
				CardConsumeRep rep = wechatCardService.doCardConsume(cardCode, accessToken);
				if(ResultCode.SUCCESS.getCode().equals(rep.getReturn_code())){
					//卡券核销成功
					//将用户领取的卡券修改为已使用
					cardCouponService.cardCouponConsum(codeRep.getCard_id(), cardCode);
					retMsg = new Message(null);
				}else{
					//核销失败
					retMsg = new Message(rep.getErr_code(),rep.getErr_code_des());
				}
			}else{
				//待核销的卡券为不可用
				retMsg =  new Message(codeRep.getErr_code(),codeRep.getErr_code_des());
			}
		}catch (YunPayException e) {
			e.printStackTrace();
			retMsg = new Message(e.getErrCode(),e.getErrMsg());
		}
		return retMsg;
	}

	/**
	 * @Description:卡券删除
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月14日上午11:01:31 
	 * @param merchantNum
	 * @param cardId
	 * @return
	 */
	@Override
	public Message cardDelete(String merchantNum, String cardId) {
		Message retMsg=null;
		//查找商户微信配置
		WechatConfigKey wechatConfig = wechatConfigDao.findConfigByMerNo(merchantNum);
		if (wechatConfig == null) {
			return new Message(ErrorCode.ILLEGAL_PAYCONFIG.getCode(),ErrorCode.ILLEGAL_PAYCONFIG.getDes());
		}
		try{
			String accessToken = tokenService.recvAccessToken(wechatConfig.getAppId(), wechatConfig.getAppSecret());
			//调用卡券删除方法
			AppBaseRep appBaseRep = wechatCardService.doCardDelete(cardId, accessToken);
			if(ResultCode.SUCCESS.getCode().equals(appBaseRep.getReturn_code())){
				//修改卡券状态为已删除
				cardInfoService.updCardStatus(cardId, CardStatus.DEL.getValue());
				retMsg = new Message(null);
			}else{
				//待核销的卡券为不可用
				retMsg =  new Message(appBaseRep.getErr_code(),appBaseRep.getErr_code_des());
			}
		}catch (YunPayException e) {
			e.printStackTrace();
			retMsg = new Message(e.getErrCode(),e.getErrMsg());
		}
		return retMsg;
	}

	/**
	 * @Description:根据id查找卡券信息
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月16日下午3:12:28 
	 * @param cardId
	 * @return
	 */
	@Override
	public Message cardSearch(String cardId) {
		CardInfo cardInfo = cardInfoService.findCardByCardId(cardId);
		if(cardInfo!=null){
			CardRepDto cardRep = new CardRepDto();
			cardRep.setPut_channel(cardInfo.getPutchannel()+"");
			if(PayChannel.WECHAT.getValue() == cardInfo.getPutchannel()){
				cardRep.setCard_id(cardInfo.getWeixin_card_id());
			}else{
				cardRep.setCard_id(cardInfo.getAlipassTemplateId());
			}
			cardRep.setTitle(cardInfo.getTitle());
			cardRep.setColor(cardInfo.getCardColor());
			cardRep.setCard_type(cardInfo.getType()+"");
			cardRep.setQuantity((cardInfo.getInventory()-cardInfo.getNumber())+"");
			cardRep.setColor(cardInfo.getCardColor());
			cardRep.setLogo_url(cardInfo.getLogo());
			cardRep.setBegin_timestamp(DateUtil.format(cardInfo.getStartDate(),"yyyy-MM-dd"));
			cardRep.setEnd_timestamp(DateUtil.format(cardInfo.getEndDate(),"yyyy-MM-dd"));
			cardRep.setService_phone(cardInfo.getTel());
			cardRep.setDescription(cardInfo.getOperation());
			cardRep.setMerchant_name(cardInfo.getMerchantName());
			cardRep.setMerchant_num(cardInfo.getMerNo());
			
			if(CardType.DISCOUNT.getValue().equals(cardInfo.getType()+"")){
				cardRep.setDiscount(cardInfo.getDiscount()+"");
			}
			cardRep.setGet_limit(cardInfo.getLimitNum()+"");
			cardRep.setBack_ground(cardInfo.getBackground());
			cardRep.setCan_share(cardInfo.getCanShare());
			cardRep.setCan_give_friend(cardInfo.getCanGive());
			cardRep.setNotice(cardInfo.getNotice());
			cardRep.setCard_status(cardInfo.getStatus()+"");
			if(CardType.CASH.getValue().equals(cardInfo.getType()+"")){
				cardRep.setReduce_cost(cardInfo.getDiscountMoney()+"");
				cardRep.setLeast_cost(cardInfo.getLimitMoney()+"");
			}
			cardRep.setDefault_detail(cardInfo.getDetail());
			cardRep.setCode_type(cardInfo.getCodetype());
			return new Message(cardRep);
		}
		return new Message(ErrorCode.CARD_NOT_EXIST.getCode(),ErrorCode.CARD_NOT_EXIST.getDes());
	}


	/**
	 * @Description:卡券推送
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月24日下午5:27:08 
	 * @param merchantNum
	 * @param cardId
	 * @return
	 */
	@Override
	public Message cardPushToWxapp(String merchantNum,String cardId) {
		Message retMsg=null;
		//查找商户微信配置
		WechatConfigKey wechatConfig = wechatConfigDao.findConfigByMerNo(merchantNum);
		if (wechatConfig == null) {
			return new Message(ErrorCode.ILLEGAL_PAYCONFIG.getCode(),ErrorCode.ILLEGAL_PAYCONFIG.getDes());
		}
		try{
			String accessToken = tokenService.recvAccessToken(wechatConfig.getAppId(), wechatConfig.getAppSecret());
			//调用卡券推送
			AppBaseRep appBaseRep = wechatCardService.doCardPush(cardId, accessToken);
			if(ResultCode.SUCCESS.getCode().equals(appBaseRep.getReturn_code())){
				//修改卡券状态为已投放
				cardInfoService.updCardStatus(cardId, CardStatus.PUSH.getValue());
				retMsg = new Message(null);
			}else{
				//推送失败
				retMsg =  new Message(appBaseRep.getErr_code(),appBaseRep.getErr_code_des());
			}
		}catch (YunPayException e) {
			e.printStackTrace();
			retMsg = new Message(e.getErrCode(),e.getErrMsg());
		}
		return retMsg;
	}


	/**
	 * @Description:设置卡券支持快速买单功能
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月30日上午9:36:58 
	 * @param merchantNum
	 * @param cardId
	 * @return
	 */
	@Override
	public Message cardPaySet(String merchantNum, String cardId) {
		Message retMsg=null;
		//查找商户微信配置
		WechatConfigKey wechatConfig = wechatConfigDao.findConfigByMerNo(merchantNum);
		if (wechatConfig == null) {
			return new Message(ErrorCode.ILLEGAL_PAYCONFIG.getCode(),ErrorCode.ILLEGAL_PAYCONFIG.getDes());
		}
		try{
			String accessToken = tokenService.recvAccessToken(wechatConfig.getAppId(), wechatConfig.getAppSecret());
			AppBaseRep appBaseRep = wechatCardService.doCardPaySet(cardId, accessToken);
			if(ResultCode.SUCCESS.getCode().equals(appBaseRep.getReturn_code())){
				retMsg = new Message(null);
			}else{
				retMsg =  new Message(appBaseRep.getErr_code(),appBaseRep.getErr_code_des());
			}
		}catch (YunPayException e) {
			e.printStackTrace();
			retMsg = new Message(e.getErrCode(),e.getErrMsg());
		}
		return retMsg;
	}
}
