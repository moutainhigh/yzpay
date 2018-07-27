package com.yunpay.serv.service.pay.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunpay.activemq.busi.CouponNotifyService;
import com.yunpay.ali.rep.pay.AliBarRep;
import com.yunpay.ali.rep.pay.AliCancelRep;
import com.yunpay.ali.rep.pay.AliCloseRep;
import com.yunpay.ali.rep.pay.AliQueryRep;
import com.yunpay.ali.rep.pay.AliRefundQueryRep;
import com.yunpay.ali.rep.pay.AliRefundRep;
import com.yunpay.ali.rep.pay.AliScanRep;
import com.yunpay.ali.req.pay.AliAppReq;
import com.yunpay.ali.req.pay.AliBarReq;
import com.yunpay.ali.req.pay.AliCancelReq;
import com.yunpay.ali.req.pay.AliCloseReq;
import com.yunpay.ali.req.pay.AliQueryReq;
import com.yunpay.ali.req.pay.AliRefundQueryReq;
import com.yunpay.ali.req.pay.AliRefundReq;
import com.yunpay.ali.req.pay.AliScanReq;
import com.yunpay.ali.req.pay.AliWapReq;
import com.yunpay.ali.service.AliQrPayService;
import com.yunpay.common.utils.AmountUtil;
import com.yunpay.common.utils.CommonUtil;
import com.yunpay.common.utils.DateUtil;
import com.yunpay.common.utils.Log;
import com.yunpay.common.utils.EnumUtil.PayChannel;
import com.yunpay.common.utils.EnumUtil.PayStatus;
import com.yunpay.common.utils.EnumUtil.RefundStatus;
import com.yunpay.common.utils.EnumUtil.Route;
import com.yunpay.common.utils.EnumUtil.SubChannel;
import com.yunpay.common.utils.ResultEnum.ErrorCode;
import com.yunpay.serv.config.PayConfig;
import com.yunpay.serv.dao.AlipayConfigDao;
import com.yunpay.serv.model.AlipayConfigKey;
import com.yunpay.serv.model.Merchant;
import com.yunpay.serv.model.PayTranLs;
import com.yunpay.serv.model.RefundTranLs;
import com.yunpay.serv.rep.BarPayRepDto;
import com.yunpay.serv.rep.Message;
import com.yunpay.serv.rep.RefundQueryRepDto;
import com.yunpay.serv.rep.RefundRepDto;
import com.yunpay.serv.rep.ScanPayRepDto;
import com.yunpay.serv.req.pay.AppPayReqDto;
import com.yunpay.serv.req.pay.AppletReqDto;
import com.yunpay.serv.req.pay.BarPayReqDto;
import com.yunpay.serv.req.pay.QrRefundReqDto;
import com.yunpay.serv.req.pay.ScanPayReqDto;
import com.yunpay.serv.req.pay.WapPayReqDto;
import com.yunpay.serv.service.busi.PayTranLsService;
import com.yunpay.serv.service.busi.RefundTranLsService;
import com.yunpay.serv.service.pay.QrPayService;

/**
 * 文件名称:    AliPayServiceImpl.java
 * 内容摘要:    支付宝支付业务逻辑核心处理类
 * 封装支付宝官方渠道支付业务核心处理（主要方法：条码支付、扫码支付、网页/wap支付、
 * 订单查询、订单撤销、订单关闭等）
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年6月23日下午5:57:26 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年6月23日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
@Service("aliPayService")
public class AliPayServiceImpl extends QrPayService{
	
	@Autowired
	private AlipayConfigDao alipayConfigDao;
	@Autowired
	private PayTranLsService payTranLsService;
	@Autowired
	private RefundTranLsService refundTranLsService;
	@Autowired
	private AliQrPayService aliQrPayService;
	@Autowired
	private CouponNotifyService couponNotifyService;

	/**
	 * @Description:支付宝条码支付
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年6月23日下午5:57:49 
	 * @param qrPayReq
	 * @param merchant
	 * @return
	 */
	@Override
	public Message doBarPay(BarPayReqDto barPayReq,Merchant merchant) {
		Message retMsg = null;
		//获取支付配置信息
		AlipayConfigKey alipayConfig = alipayConfigDao.findConfigByMerNo(merchant.getMerchantNo());
		if(alipayConfig == null){
			return new Message(ErrorCode.ILLEGAL_PAYCONFIG.getCode(),ErrorCode.ILLEGAL_PAYCONFIG.getDes());
		}
		//创建交易流水
		PayTranLs payTranLs = payTranLsService.createBarTranLs(barPayReq, merchant, 
				SubChannel.ALIPAY_BAR,Route.NATIVE);
		//构造请求参数
		AliBarReq aliBarReq = new AliBarReq(alipayConfig.getAppId(),barPayReq.getDynamic_id(),
				alipayConfig.getMerchantPrivateKey(),alipayConfig.getAlipayPublicKey(),payTranLs.getTransNum(),
				String.valueOf(payTranLs.getTransPrice()),barPayReq.getBody(),barPayReq.getAttach(),barPayReq.getTerminal_unique_no(),
				alipayConfig.getPid(),"30m");
		AliBarRep aliBarRep = aliQrPayService.doAliBarPay(aliBarReq);
		if(null==aliBarRep){
			return new Message(ErrorCode.CHANNEL_PAY_ERROR.getCode(),ErrorCode.CHANNEL_PAY_ERROR.getDes());
		}
		//交易成功
		if("10000".equals(aliBarRep.getCode())){
			//支付宝平台返回交易流水号
			payTranLs.setTrade_no(aliBarRep.getTrade_no());
			//支付状态改为已完成
			payTranLs.setStatus(PayStatus.PAYED.getValue());
			//实际金额
			//payTranLs.setTransPrice(Float.valueOf(aliBarRep.getReceipt_amount()));
			//买家支付宝账号
			payTranLs.setTransCardNum(aliBarRep.getBuyer_logon_id());
			//买家支付宝Id
			payTranLs.setOpenid(aliBarRep.getBuyer_user_id());
			//如果存在卡券，则发送核销通知到activemq
			if(StringUtils.isNotBlank(payTranLs.getCouponCode()) 
					&& StringUtils.isNotBlank(payTranLs.getAsynNotify())){
				//发送卡券核销通知到消息队列activemq
				if(couponNotifyService.sendCouponNotify(payTranLs.getAsynNotify())){
					payTranLs.setNotifyStatus(1);
				}
			}
			//更新流水
			payTranLsService.updatePayTranLs(payTranLs);
			//封装平台返回参数
			BarPayRepDto rep = new BarPayRepDto(aliBarRep,payTranLs);
			retMsg = new Message(rep);
		}else if ("10003".equals(aliBarRep.getCode()) 
				|| "20000".equals(aliBarRep.getCode())){
			//10003等待用户付款
			//20000网络超时或支付宝系统异常
			payTranLs.setStatus(PayStatus.PAYING.getValue());
			payTranLsService.updTranStatus(payTranLs.getId(), PayStatus.PAYING.getValue());
			retMsg = new Message(ErrorCode.ORDER_PAYING.getCode(),ErrorCode.ORDER_PAYING.getDes());
		}else if("40004".equals(aliBarRep.getCode())){
			//支付失败
			retMsg = new Message(aliBarRep.getSub_code(),aliBarRep.getSub_msg());
			payTranLsService.updTranStatus(payTranLs.getId(), PayStatus.FAILPAY.getValue());
		}else{
			//状态未知
			retMsg = new Message(aliBarRep.getSub_code(),aliBarRep.getSub_msg());
			payTranLsService.updTranStatus(payTranLs.getId(), PayStatus.UNKNOWN.getValue());
		}
		return retMsg;
	}

	/**
	 * @Description:支付宝扫码支付
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年6月26日上午9:07:05 
	 * @param qrPayReq
	 * @param merchant
	 * @return
	 */
	@Override
	public Message doScanPay(ScanPayReqDto scanPayReq,Merchant merchant) {
		Message retMsg = null;
		//获取支付配置信息
		AlipayConfigKey alipayConfig = alipayConfigDao.findConfigByMerNo(merchant.getMerchantNo());
		if(alipayConfig == null){
			return new Message(ErrorCode.ILLEGAL_PAYCONFIG.getCode(),ErrorCode.ILLEGAL_PAYCONFIG.getDes());
		}
		//创建交易流水
		PayTranLs payTranLs = payTranLsService.createScanTranLs(scanPayReq, merchant,
				SubChannel.ALIPAY_SCAN,Route.NATIVE);
		//封装支付请求参数
		AliScanReq aliScanReq = new AliScanReq(alipayConfig.getAppId(),PayConfig.ALI_SCAN_NOTIFY_URL,
				alipayConfig.getMerchantPrivateKey(),alipayConfig.getAlipayPublicKey(),payTranLs.getTransNum(),
				String.valueOf(payTranLs.getTransPrice()),scanPayReq.getBody(),scanPayReq.getAttach(),scanPayReq.getTerminal_unique_no(),
				alipayConfig.getPid(),"30m");
		AliScanRep aliScanRep = aliQrPayService.doAliScanPay(aliScanReq);
		if(null==aliScanRep){
			payTranLsService.updTranStatus(payTranLs.getId(), PayStatus.FAILPAY.getValue());
			return new Message(ErrorCode.CHANNEL_PAY_ERROR.getCode(),ErrorCode.CHANNEL_PAY_ERROR.getDes());
		}
		if(aliScanRep.getCode().equals("10000")){
			//下单成功
			//修改订单状态为支付中
			//payTranLsService.updTranStatus(payTranLs.getId(), PayStatus.PAYING.getValue());
			ScanPayRepDto scanPayRep = new ScanPayRepDto(aliScanRep,payTranLs);
			retMsg = new Message(scanPayRep);
		}else{
			//下单失败
			retMsg = new Message(aliScanRep.getSub_code(),aliScanRep.getSub_msg());
			payTranLsService.updTranStatus(payTranLs.getId(), PayStatus.FAILPAY.getValue());
		}
		return retMsg;
	}

	/**
	 * @Description:订单查询
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年6月26日上午11:09:15 
	 * @param payTranLs
	 * @return
	 */
	@Override
	public Message doOrderQuery(PayTranLs payTranLs) {
		Message retMsg = null;
		//获取支付配置信息
		AlipayConfigKey alipayConfig = alipayConfigDao.findConfigByMerNo(payTranLs.getMerchantNo());
		if(alipayConfig == null){
			return new Message(ErrorCode.ILLEGAL_PAYCONFIG.getCode(),ErrorCode.ILLEGAL_PAYCONFIG.getDes());
		}
		
		AliQueryReq aliQueryReq = new AliQueryReq(alipayConfig.getAppId(),
				alipayConfig.getMerchantPrivateKey(),alipayConfig.getAlipayPublicKey(),
				payTranLs.getTransNum(),payTranLs.getTrade_no());
		AliQueryRep aliQueryRep = aliQrPayService.doAliQuery(aliQueryReq);
		if(null==aliQueryRep){
			return new Message(ErrorCode.CHANNEL_PAY_ERROR.getCode(),ErrorCode.CHANNEL_PAY_ERROR.getDes());
		}
		//查询成功
		if(aliQueryRep.getCode().equals("10000")){
			//支付宝平台返回交易流水号
			payTranLs.setTrade_no(aliQueryRep.getTrade_no());
			//支付状态改为已完成
			PayStatus payStatus = CommonUtil.convertAliStatus(aliQueryRep.getTrade_status());
			payTranLs.setStatus(payStatus.getValue());
			//实际金额
			//测试中发现有查询结果中的receipt_amount并不是每次都有值，有时候为0，故先将此处的金额改为取total_amount
			//payTranLs.setTransPrice(Float.valueOf(aliQueryRep.getTotal_amount()));
			//买家支付宝账号
			payTranLs.setTransCardNum(aliQueryRep.getBuyer_logon_id());
			//买家支付宝Id
			payTranLs.setOpenid(aliQueryRep.getBuyer_user_id());
			//如果存在卡券，则发送核销通知到activemq
			if(StringUtils.isNotBlank(payTranLs.getCouponCode()) 
					&& StringUtils.isNotBlank(payTranLs.getAsynNotify())
					&& payTranLs.getNotifyStatus()==0){
				//发送卡券核销通知到消息队列activemq
				if(couponNotifyService.sendCouponNotify(payTranLs.getAsynNotify())){
					payTranLs.setNotifyStatus(1);
				}
			}
			//更新流水
			payTranLsService.updatePayTranLs(payTranLs);
			//封装平台返回参数
			BarPayRepDto rep = new BarPayRepDto(payTranLs);
			retMsg = new Message(rep);
		}else if ("10003".equals(aliQueryRep.getCode())
				|| "20000".equals(aliQueryRep.getCode())){
			//10003该结果码只有在条码支付请求 API 时才返回，代表付款还在进行中
			//兼容USERPAYING
			payTranLs.setStatus(PayStatus.PAYING.getValue());
			payTranLsService.updTranStatus(payTranLs.getId(), PayStatus.PAYING.getValue());
			BarPayRepDto rep = new BarPayRepDto(payTranLs);
			retMsg = new Message(rep);
		}else if(payTranLs.getSubChannel()==SubChannel.ALIPAY_SCAN.getValue() || 
				payTranLs.getSubChannel()==SubChannel.ALIPAY_WAP.getValue()
				//&&payTranLs.getStatus()==PayStatus.PAYING.getValue()
				&& "ACQ.TRADE_NOT_EXIST".equals(aliQueryRep.getSub_code())){
			//扫码支付生成二维码后，如果用户没有用支付宝扫码，此时调用支付宝查询订单接口会返回订单不存在
			//所以这里返回订单状态为支付中
			BarPayRepDto rep = new BarPayRepDto(payTranLs);
			retMsg = new Message(rep);
		}else{
			//具体失败原因参见接口返回的错误码
			retMsg = new Message(aliQueryRep.getSub_code(),aliQueryRep.getSub_msg());
		}
		return retMsg;
		
	}
	
	
	/**
	 * @Description:撤销成功
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年6月26日上午11:18:58 
	 * @param payTranLs
	 * @return
	 */
	@Override
	public Message doOrderReverse(PayTranLs payTranLs) {
		Message retMsg = null;
		//获取支付配置信息
		AlipayConfigKey alipayConfig = alipayConfigDao.findConfigByMerNo(payTranLs.getMerchantNo());
		if(alipayConfig == null){
			return new Message(ErrorCode.ILLEGAL_PAYCONFIG.getCode(),ErrorCode.ILLEGAL_PAYCONFIG.getDes());
		}
		AliCancelReq aliCancelReq = new AliCancelReq(alipayConfig.getAppId(),
				alipayConfig.getMerchantPrivateKey(),alipayConfig.getAlipayPublicKey(),
				payTranLs.getTransNum(),payTranLs.getTrade_no());
		AliCancelRep aliCancelRep = aliQrPayService.doAliCancel(aliCancelReq);
		if(null==aliCancelRep){
			return new Message(ErrorCode.CHANNEL_PAY_ERROR.getCode(),ErrorCode.CHANNEL_PAY_ERROR.getDes());
		}
		//撤销成功
		if(aliCancelRep.getCode().equals("10000")){
			payTranLs.setStatus(PayStatus.CANCEL.getValue());
			payTranLsService.updTranStatus(payTranLs.getId(), PayStatus.CANCEL.getValue());
			//封装平台返回参数
			BarPayRepDto rep = new BarPayRepDto(payTranLs);
			retMsg = new Message(rep);
		}else{//具体失败原因参见接口返回的错误码
			retMsg = new Message(aliCancelRep.getSub_code(),aliCancelRep.getSub_msg());
		}
		return retMsg;
	}

	/**
	 * @Description:支付宝wap支付
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年6月27日下午2:15:30 
	 * @param qrPayReq
	 * @param merchant
	 * @return
	 */
	@Override
	public Message doWapPay(WapPayReqDto wapPayReq, Merchant merchant) {
		//获取支付配置信息
		AlipayConfigKey alipayConfig = alipayConfigDao.findConfigByMerNo(merchant.getMerchantNo());
		if(alipayConfig == null){
			return new Message(ErrorCode.ILLEGAL_PAYCONFIG.getCode(),ErrorCode.ILLEGAL_PAYCONFIG.getDes());
		}
		//创建交易流水
		PayTranLs payTranLs = payTranLsService.createWapTranLs(wapPayReq, merchant,PayChannel.ALIPAY);
		AliWapReq aliWapReq = new AliWapReq(alipayConfig.getAppId(),PayConfig.ALI_WAP_NOTIFY_URL,
				PayConfig.ALI_WAP_RETURN_URL,alipayConfig.getMerchantPrivateKey(),alipayConfig.getAlipayPublicKey(),
				payTranLs.getTransNum(),String.valueOf(payTranLs.getTransPrice()),wapPayReq.getBody(),wapPayReq.getAttach(),
				alipayConfig.getPid());
		String retForm = aliQrPayService.doAliWapPay(aliWapReq);
		if(retForm==null){
			return new Message(ErrorCode.CHANNEL_PAY_ERROR.getCode(),ErrorCode.CHANNEL_PAY_ERROR.getDes());
		}
		return new Message(retForm);
	}
	
	/**
	 * @Description:一码付发起支付宝支付请求
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月7日上午9:11:00 
	 * @param payTranLs
	 * @param authCode
	 * @return
	 */
	@Override
	public Message doOneCodePay(PayTranLs payTranLs,String authCode) {
		//获取支付配置信息
		Message message;
		AlipayConfigKey alipayConfig = alipayConfigDao.findConfigByMerNo(payTranLs.getMerchantNo());
		if(alipayConfig == null){
			return new Message(ErrorCode.ILLEGAL_PAYCONFIG.getCode(),ErrorCode.ILLEGAL_PAYCONFIG.getDes());
		}
		AliWapReq aliWapReq = new AliWapReq(alipayConfig.getAppId(),PayConfig.ALI_WAP_NOTIFY_URL,
				PayConfig.ALI_WAP_RETURN_URL,alipayConfig.getMerchantPrivateKey(),alipayConfig.getAlipayPublicKey(),
				payTranLs.getTransNum(),String.valueOf(payTranLs.getTransPrice()),payTranLs.getSubject(),payTranLs.getAttach(),
				alipayConfig.getPid());
		String retForm = aliQrPayService.doAliWapPay(aliWapReq);
		if(retForm==null){
			payTranLs.setStatus(PayStatus.FAILPAY.getValue());
			message = new Message(ErrorCode.CHANNEL_PAY_ERROR.getCode(),ErrorCode.CHANNEL_PAY_ERROR.getDes());
		}else{
			payTranLs.setStatus(PayStatus.PAYING.getValue());
			payTranLs.setSyncNotify(PayConfig.ALI_WAP_RETURN_URL);
			message = new Message(retForm);
		}
		//修改预付流水信息
		payTranLsService.updateOneCodeTranLs(payTranLs, PayChannel.ALIPAY);
		return message;
	}
	
	/**
	 * @Description:小程序支付
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年11月1日上午10:22:02 
	 * @param appReqDto
	 * @param merchant
	 * @return
	 */
	@Override
	public Message doAppPay(AppPayReqDto appPayReq, Merchant merchant) {
		//获取支付配置信息
		AlipayConfigKey alipayConfig = alipayConfigDao.findConfigByMerNo(merchant.getMerchantNo());
		if(alipayConfig == null){
			return new Message(ErrorCode.ILLEGAL_PAYCONFIG.getCode(),ErrorCode.ILLEGAL_PAYCONFIG.getDes());
		}
		//创建交易流水
		PayTranLs payTranLs = payTranLsService.createAppTranLs(appPayReq, merchant,PayChannel.ALIPAY);
		
		AliAppReq aliAppReq = new AliAppReq(alipayConfig.getAppId(),PayConfig.ALI_SCAN_NOTIFY_URL,
				alipayConfig.getMerchantPrivateKey(),alipayConfig.getAlipayPublicKey(),
				payTranLs.getTransNum(),appPayReq.getTotal_fee(),appPayReq.getBody(),appPayReq.getAttach(),
				alipayConfig.getPid());
		String orderStr = aliQrPayService.doAliAppPay(aliAppReq);
		if(orderStr==null){
			return new Message(ErrorCode.CHANNEL_PAY_ERROR.getCode(),ErrorCode.CHANNEL_PAY_ERROR.getDes());
		}
		return new Message(orderStr);
	}

	/**
	 * @Description:支付宝退款请求
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年7月11日下午5:15:42 
	 * @param qrRefundReq
	 * @param payTranLs
	 * @return
	 */
	@Override
	public Message doOrderRefund(QrRefundReqDto qrRefundReq,PayTranLs payTranLs) {
		Message retMsg = null;
		//退款金额(转换为分)
		int refundFee = Integer.valueOf(AmountUtil.changeY2F(qrRefundReq.getRefund_fee()));
		//原订单总额(转换为分)
		int transFee = Integer.valueOf(AmountUtil.changeY2F(payTranLs.getTransPrice().toString()));
		//已退款金额
		int refundedFee = Integer.valueOf(AmountUtil.changeY2F(payTranLs.getRefundPrice().toString()));
		//如果当前退款金额+已退款金额>实际交易金额，直接返回错误提示
		if((refundFee+refundedFee)>transFee){
			return new Message(ErrorCode.ORDER_REFUND_FEE.getCode(),ErrorCode.ORDER_REFUND_FEE.getDes());
		}
		//验证退款单号是否存在
		RefundTranLs refundTranLs = refundTranLsService.findRefundLsByOrderNo(
				qrRefundReq.getUser_refund_no(), qrRefundReq.getMerchant_num()); 
		if(refundTranLs!=null){
			//如果退款单状态为未退款、退款中、已退款状态，提示用户退款单已存在
			//此处流程表示如果退款失败，可用原退款单号发起重复退款请求(微信和支付宝建议如此)
			if(refundTranLs.getStatus()!= RefundStatus.FAILREFUND.getValue()){
				return new Message(ErrorCode.REFUND_ORDER_EXIST.getCode(), ErrorCode.REFUND_ORDER_EXIST.getDes());
			}
		}else{
			//创建退款流水
			refundTranLs = refundTranLsService.createRefundTranLs(qrRefundReq,payTranLs);
		}
		//获取支付配置信息
		AlipayConfigKey alipayConfig = alipayConfigDao.findConfigByMerNo(qrRefundReq.getMerchant_num());
		if(alipayConfig == null){
			return new Message(ErrorCode.ILLEGAL_PAYCONFIG.getCode(),ErrorCode.ILLEGAL_PAYCONFIG.getDes());
		}
		//构造请求参数
		AliRefundReq refundReq = new AliRefundReq(alipayConfig.getAppId(),
				alipayConfig.getMerchantPrivateKey(),alipayConfig.getAlipayPublicKey(),
				payTranLs.getTransNum(),payTranLs.getTrade_no(),refundTranLs.getTransNum(),
				qrRefundReq.getRefund_fee(),qrRefundReq.getRefund_desc());
		AliRefundRep refundRep = aliQrPayService.doAliRefund(refundReq);
		if(null==refundRep){
			return new Message(ErrorCode.CHANNEL_PAY_ERROR.getCode(),ErrorCode.CHANNEL_PAY_ERROR.getDes());
		}
		if(refundRep.getCode().equals("10000")){
			//退款成功
			//此处流程同微信退款有区别，微信退款需通过查询退款接口确定退款结果，而支付宝直接返回退款结果
			refundTranLs.setStatus(RefundStatus.REFUNDED.getValue());
			//设置退款成功时间
			refundTranLs.setRefundSuccessTime(DateUtil.format(refundRep.getGmt_refund_pay(),"yyyy-MM-dd HH:mm:ss"));
			refundTranLsService.updateRefundTranLs(refundTranLs);
			
			//update by 2017-09-18 19:39
			//增加对原订单的退款信息进行修改(暂时认为退款申请成功就是退款成功，此流程后续需优化)
			if((refundFee+refundedFee)==transFee){
				//如果退款累计金额==付款金额，退款金额累加并修改状态为已退款
				payTranLsService.updTranLsRefund(payTranLs.getId(),PayStatus.REFUNDED.getValue(),Float.valueOf(qrRefundReq.getRefund_fee()));
			}else{
				//累加退款金额
				payTranLsService.updTranLsRefund(payTranLs.getId(),payTranLs.getStatus(),Float.valueOf(qrRefundReq.getRefund_fee()));
			}
			RefundRepDto rep = new RefundRepDto(refundTranLs);
			retMsg = new Message(rep);
		}else{
			//退款失败
			Log.error("支付宝退款请求失败，err_msg:"+refundRep.getSub_msg());
			refundTranLs.setStatus(RefundStatus.FAILREFUND.getValue());
			refundTranLsService.updTranStatus(refundTranLs.getId(), RefundStatus.FAILREFUND.getValue());
			retMsg = new Message(refundRep.getSub_code(),refundRep.getSub_msg());
		}
		return retMsg;
	}

	
	/**
	 * @Description:支付宝退款查询
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年7月11日下午5:15:57 
	 * @param refundTranLs
	 * @return
	 */
	@Override
	public Message doRefundQuery(RefundTranLs refundTranLs) {
		Message retMsg = null;
		AlipayConfigKey alipayConfig = alipayConfigDao.findConfigByMerNo(refundTranLs.getMerchantNo());
		if(alipayConfig == null){
			return new Message(ErrorCode.ILLEGAL_PAYCONFIG.getCode(),ErrorCode.ILLEGAL_PAYCONFIG.getDes());
		}
		AliRefundQueryReq refundQueryReq = new AliRefundQueryReq(alipayConfig.getAppId(),
				alipayConfig.getMerchantPrivateKey(),alipayConfig.getAlipayPublicKey(),
				refundTranLs.getOldTransNum(),"",refundTranLs.getTransNum());
		AliRefundQueryRep refundQueryRep = aliQrPayService.doAliRefundQuery(refundQueryReq);
		if(null==refundQueryRep){
			return new Message(ErrorCode.CHANNEL_PAY_ERROR.getCode(),ErrorCode.CHANNEL_PAY_ERROR.getDes());
		}
		if(refundQueryRep.getCode().equals("10000")){
			if(StringUtils.isNotBlank(refundQueryRep.getRefund_amount())){
				//查询成功，且退款成功
				refundTranLs.setStatus(RefundStatus.REFUNDED.getValue());
				refundTranLsService.updTranStatus(refundTranLs.getId(), RefundStatus.REFUNDED.getValue());
				RefundQueryRepDto rep = new RefundQueryRepDto(refundTranLs);
				retMsg = new Message(rep);
			}else{
				//查询成功，退款未成功(设置退款单状态为退款中)
				refundTranLs.setStatus(RefundStatus.REFUNDING.getValue());
				refundTranLsService.updTranStatus(refundTranLs.getId(), RefundStatus.REFUNDING.getValue());
				RefundQueryRepDto rep = new RefundQueryRepDto(refundTranLs);
				retMsg = new Message(rep);
			}
		}else{
			//查询失败
			Log.error("支付宝退款请求失败，err_msg:"+refundQueryRep.getSub_msg());
			retMsg = new Message(refundQueryRep.getSub_code(),refundQueryRep.getSub_msg());
		}
		return retMsg;
	}

	/**
	 * @Description:订单关闭
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年7月12日下午6:01:36 
	 * @param payTranLs
	 * @return
	 */
	@Override
	public Message doOrderClose(PayTranLs payTranLs) {
		Message retMsg = null;
		//获取支付配置信息
		AlipayConfigKey alipayConfig = alipayConfigDao.findConfigByMerNo(payTranLs.getMerchantNo());
		if(alipayConfig == null){
			return new Message(ErrorCode.ILLEGAL_PAYCONFIG.getCode(),ErrorCode.ILLEGAL_PAYCONFIG.getDes());
		}
		AliCloseReq aliCloseReq = new AliCloseReq(alipayConfig.getAppId(),
				alipayConfig.getMerchantPrivateKey(),alipayConfig.getAlipayPublicKey(),
				payTranLs.getTransNum(),payTranLs.getTrade_no());
		AliCloseRep aliCloseRep = aliQrPayService.doAliClose(aliCloseReq);
		if(null==aliCloseRep){
			return new Message(ErrorCode.CHANNEL_PAY_ERROR.getCode(),ErrorCode.CHANNEL_PAY_ERROR.getDes());
		}
		//关闭成功或者提示订单不存在
		if(aliCloseRep.getCode().equals("10000") 
				|| "ACQ.TRADE_NOT_EXIST".equals(aliCloseRep.getSub_code())){
			payTranLs.setStatus(PayStatus.CLOSED.getValue());
			payTranLsService.updTranStatus(payTranLs.getId(), PayStatus.CLOSED.getValue());
			//封装平台返回参数
			BarPayRepDto rep = new BarPayRepDto(payTranLs);
			retMsg = new Message(rep);
		}else{
			//具体失败原因参见接口返回的错误码
			retMsg = new Message(aliCloseRep.getSub_code(),aliCloseRep.getSub_msg());
		}
		return retMsg;
	}

	@Override
	public Message doAppletPay(AppletReqDto appletReq, Merchant merchant) {
		// TODO Auto-generated method stub
		return null;
	}

}
