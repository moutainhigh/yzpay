package com.yunpay.serv.service.pay.impl;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.yunpay.activemq.busi.CouponNotifyService;
import com.yunpay.common.utils.AmountUtil;
import com.yunpay.common.utils.CommonUtil;
import com.yunpay.common.utils.DateUtil;
import com.yunpay.common.utils.EnumUtil.PayStatus;
import com.yunpay.common.utils.EnumUtil.RefundStatus;
import com.yunpay.common.utils.EnumUtil.Route;
import com.yunpay.common.utils.HttpUtil;
import com.yunpay.common.utils.Log;
import com.yunpay.common.utils.EnumUtil.PayChannel;
import com.yunpay.common.utils.EnumUtil.SubChannel;
import com.yunpay.common.utils.ResultEnum.ErrorCode;
import com.yunpay.common.utils.ResultEnum.ResultCode;
import com.yunpay.serv.config.PayConfig;
import com.yunpay.serv.dao.WechatConfigDao;
import com.yunpay.serv.model.Merchant;
import com.yunpay.serv.model.PayTranLs;
import com.yunpay.serv.model.RefundTranLs;
import com.yunpay.serv.model.WechatConfigKey;
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
import com.yunpay.wx.rep.pay.WechatAppRep;
import com.yunpay.wx.rep.pay.WechatAppletRep;
import com.yunpay.wx.rep.pay.WechatBarRep;
import com.yunpay.wx.rep.pay.WechatCloseRep;
import com.yunpay.wx.rep.pay.WechatQueryRep;
import com.yunpay.wx.rep.pay.WechatRefundQueryRep;
import com.yunpay.wx.rep.pay.WechatRefundRep;
import com.yunpay.wx.rep.pay.WechatReverseRep;
import com.yunpay.wx.rep.pay.WechatScanRep;
import com.yunpay.wx.rep.pay.WechatWapRep;
import com.yunpay.wx.req.pay.WechatAppReq;
import com.yunpay.wx.req.pay.WechatAppletReq;
import com.yunpay.wx.req.pay.WechatBarReq;
import com.yunpay.wx.req.pay.WechatCloseReq;
import com.yunpay.wx.req.pay.WechatQueryReq;
import com.yunpay.wx.req.pay.WechatRefundQueryReq;
import com.yunpay.wx.req.pay.WechatRefundReq;
import com.yunpay.wx.req.pay.WechatReverseReq;
import com.yunpay.wx.req.pay.WechatScanReq;
import com.yunpay.wx.req.pay.WechatWapReq;
import com.yunpay.wx.service.WechatQrPayService;

/**
 * 
 * 文件名称:    WechatPayServiceImpl.java
 * 内容摘要:    微信支付服务实现类
 * 封装微信官方渠道支付业务核心处理（主要方法：条码支付、扫码支付、网页/wap支付、
 * app支付、小程序支付、订单查询、订单撤销、订单关闭等）
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年6月22日上午11:35:58 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年6月22日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
@Service("wechatPayService")
public class WechatPayServiceImpl extends QrPayService{
	
	@Autowired
	private WechatConfigDao wechatConfigDao;
	@Autowired
	private PayTranLsService payTranLsService;
	@Autowired
	private RefundTranLsService refundTranLsService;
	@Autowired
	private WechatQrPayService wechatQrPayService;
	@Autowired
	private CouponNotifyService couponNotifyService;
	
	/**
	 * @Description:	微信条码支付
	 * @author:   		Zeng Dongcheng
	 * @Date:     		2017年6月22日上午11:35:05 
	 * @param qrPayReq  条码支付请求参数
	 * @param merchant  商户信息
	 * @return
	 */
	@Override
	public Message doBarPay(BarPayReqDto barPayReq,Merchant merchant) {
		Message payMsg = null;
		try {
			//子商户配置
			WechatConfigKey wechatConfigSub = wechatConfigDao.findConfigByMerNo(barPayReq.getMerchant_num());
			if (wechatConfigSub == null) {
				return new Message(ErrorCode.ILLEGAL_PAYCONFIG.getCode(),ErrorCode.ILLEGAL_PAYCONFIG.getDes());
			}
			//主商户配置
			if(StringUtils.isEmpty(wechatConfigSub.getParentMchNo())){
				return new Message(ErrorCode.ILLEGAL_PAYCONFIG.getCode(),ErrorCode.ILLEGAL_PAYCONFIG.getDes()); 
			}
			WechatConfigKey wechatConfigPar =wechatConfigDao.findConfigByMerNo(wechatConfigSub.getParentMchNo());
			//创建订单流水
			PayTranLs payTranLs = payTranLsService.createBarTranLs(barPayReq, merchant, 
					SubChannel.WECHAT_BAR,Route.NATIVE);
			//金额转换(元转为分)
			int totalFee = Integer.valueOf(AmountUtil.changeY2F(payTranLs.getTransPrice().toString()));
			//获取本地ip
			String ip = InetAddress.getLocalHost().getHostAddress();
			//构造支付请求参数
			WechatBarReq wechatBarReq = new WechatBarReq(wechatConfigPar.getAppId(),wechatConfigPar.getMchId(),
					wechatConfigSub.getMchId(),payTranLs.getTransNum(),barPayReq.getDynamic_id(),totalFee,
					barPayReq.getBody(),barPayReq.getTerminal_unique_no(),ip,null,barPayReq.getAttach());
			//签名
			String sign = CommonUtil.getSign(CommonUtil.toMap(wechatBarReq),wechatConfigPar.getApiSecret());
			wechatBarReq.setSign(sign);
			//发起微信支付
			WechatBarRep wechatBarRep = wechatQrPayService.doWechatBarPay(wechatBarReq);
			if(wechatBarRep!=null){
				if(ResultCode.FAIL.getCode().equals(wechatBarRep.getReturn_code())){
					//微信return_code为Fail
					payMsg = new Message(ResultCode.FAIL.getCode(),wechatBarRep.getReturn_msg());
				}else{
					//微信return_code和result_code同时为SUCCESS
					if(ResultCode.SUCCESS.getCode().equals(wechatBarRep.getResult_code())){
						Log.info("微信条码支付成功！");
						BarPayRepDto payResData = new BarPayRepDto(wechatBarRep,payTranLs);
						//payTranLs.setTransPrice(Float.valueOf(payResData.getTrans_amount()));
						payTranLs.setStatus(PayStatus.PAYED.getValue());
						payTranLs.setTrade_no(wechatBarRep.getTransaction_id());
						//设置用户openid
						payTranLs.setTransCardNum(payResData.getTrans_card_num());
						payTranLs.setOpenid(payResData.getTrans_card_num());
						//如果存在卡券，则发送核销通知到activemq
						if(StringUtils.isNotBlank(payTranLs.getCouponCode()) 
								&& StringUtils.isNotBlank(payTranLs.getAsynNotify())){
							//发送卡券核销通知到消息队列activemq
							if(couponNotifyService.sendCouponNotify(payTranLs.getAsynNotify())){
								payTranLs.setNotifyStatus(1);
							}
						}
						//更新订单流水
						payTranLsService.updatePayTranLs(payTranLs);
						payMsg = new Message(payResData); 
					}else{
						if("USERPAYING".equals(wechatBarRep.getErr_code())){
							//正在支付
							payTranLs.setStatus(PayStatus.PAYING.getValue());
							payTranLsService.updTranStatus(payTranLs.getId(), PayStatus.PAYING.getValue());
							payMsg = new Message(ErrorCode.ORDER_PAYING.getCode(),ErrorCode.ORDER_PAYING.getDes());
						}else{
							//支付失败，将微信返回的错误信息封装到结果包中
							payMsg = new Message(wechatBarRep.getErr_code(),wechatBarRep.getErr_code_des());
							payTranLsService.updTranStatus(payTranLs.getId(), PayStatus.FAILPAY.getValue());
						}
					}
				}
			}else{
				//返回结果为空
				payMsg = new Message(ErrorCode.CHANNEL_PAY_ERROR.getCode(),ErrorCode.CHANNEL_PAY_ERROR.getDes());
				payTranLsService.updTranStatus(payTranLs.getId(), PayStatus.FAILPAY.getValue());
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			Log.error(e.toString());
			return new Message(ErrorCode.SYSTEM_EXCEPTION.getCode(), ErrorCode.SYSTEM_EXCEPTION.getDes());
		}
		return payMsg;
	}

	/**
	 * @Description:微信扫码支付
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年6月22日上午11:36:21 
	 * @param barPayReq
	 * @param sysMerchant
	 * @return
	 */
	@Override
	public Message doScanPay(ScanPayReqDto scanPayReq,Merchant merchant) {
		Message payMsg = null;
		try {
			//子商户配置
			WechatConfigKey wechatConfigSub = wechatConfigDao.findConfigByMerNo(scanPayReq.getMerchant_num());
			if (wechatConfigSub == null) {
				return new Message(ErrorCode.ILLEGAL_PAYCONFIG.getCode(),ErrorCode.ILLEGAL_PAYCONFIG.getDes());
			}
			//主商户配置
			if(StringUtils.isEmpty(wechatConfigSub.getParentMchNo())){
				return new Message(ErrorCode.ILLEGAL_PAYCONFIG.getCode(),ErrorCode.ILLEGAL_PAYCONFIG.getDes()); 
			}
			WechatConfigKey wechatConfigPar =wechatConfigDao.findConfigByMerNo(wechatConfigSub.getParentMchNo());
			//创建订单流水
			PayTranLs payTranLs = payTranLsService.createScanTranLs(scanPayReq, merchant, 
					SubChannel.WECHAT_SCAN,Route.NATIVE);
			//金额转换为分
			int totalFee = Integer.valueOf(AmountUtil.changeY2F(payTranLs.getTransPrice().toString()));
			//获取本地ip
			String ip = InetAddress.getLocalHost().getHostAddress();
			//订单发起时间
			String timeStart = DateUtil.getNow("yyyyMMddHHmmss");
			//订单失效时间(半个小时)
			String timeExpire = DateUtil.getMiAfter(DateUtil.getNow("yyyyMMddHHmmss"), "yyyyMMddHHmmss", 30);
			//构造微信扫码支付请求参数
			WechatScanReq wechatScanReq = new WechatScanReq(wechatConfigPar.getAppId(),wechatConfigPar.getMchId(),
					wechatConfigSub.getMchId(),payTranLs.getTransNum(),
					totalFee,scanPayReq.getBody(),scanPayReq.getTerminal_unique_no(),ip,
					null,scanPayReq.getAttach(),PayConfig.WECHAT_SCAN_NOTIFY_URL,
					 timeStart,timeExpire);
			//签名
			String sign = CommonUtil.getSign(CommonUtil.toMap(wechatScanReq),wechatConfigPar.getApiSecret());
			wechatScanReq.setSign(sign);
			//发起微信扫码支付
			WechatScanRep wechatScanRep = wechatQrPayService.doWechatScanPay(wechatScanReq);
			if(wechatScanRep!=null){
				if(ResultCode.FAIL.getCode().equals(wechatScanRep.getReturn_code())){
					//微信return_code为Fail
					payMsg = new Message(ResultCode.FAIL.getCode(),wechatScanRep.getReturn_msg());
				}else{
					//微信return_code和result_code同时为SUCCESS
					if(ResultCode.SUCCESS.getCode().equals(wechatScanRep.getResult_code())){
						Log.info("微信扫码支付下单成功！");
						ScanPayRepDto payResData = new ScanPayRepDto(wechatScanRep,payTranLs);
						payMsg = new Message(payResData); 
					}else{
						//将微信返回的错误信息封装到结果包中
						payMsg = new Message(wechatScanRep.getErr_code(),wechatScanRep.getErr_code_des());
						payTranLsService.updTranStatus(payTranLs.getId(), PayStatus.FAILPAY.getValue());
					}
				}
			}else{
				//返回结果为空
				payMsg = new Message(ErrorCode.CHANNEL_PAY_ERROR.getCode(),ErrorCode.CHANNEL_PAY_ERROR.getDes());
				payTranLsService.updTranStatus(payTranLs.getId(), PayStatus.FAILPAY.getValue());
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			Log.error(e.toString());
			return new Message(ErrorCode.SYSTEM_EXCEPTION.getCode(), ErrorCode.SYSTEM_EXCEPTION.getDes());
		}
		return payMsg;
	}
	
	/**
	 * @Description:微信wap支付
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年6月27日下午2:06:38 
	 * @param qrPayReq
	 * @param merchant
	 * @return
	 */
	@Override
	public Message doWapPay(WapPayReqDto wapPayReq, Merchant merchant) {
		Message payMsg = null;
		try {
			//子商户配置
			WechatConfigKey wechatConfigSub = wechatConfigDao.findConfigByMerNo(wapPayReq.getMerchant_num());
			if (wechatConfigSub == null) {
				return new Message(ErrorCode.ILLEGAL_PAYCONFIG.getCode(),ErrorCode.ILLEGAL_PAYCONFIG.getDes());
			}
			//主商户配置
			if(StringUtils.isEmpty(wechatConfigSub.getParentMchNo())){
				return new Message(ErrorCode.ILLEGAL_PAYCONFIG.getCode(),ErrorCode.ILLEGAL_PAYCONFIG.getDes()); 
			}
			WechatConfigKey wechatConfigPar =wechatConfigDao.findConfigByMerNo(wechatConfigSub.getParentMchNo());
			if(StringUtils.isBlank(wapPayReq.getOpen_id())){
				//如果open_id为空，用auth_code获取open_id
				String reqUrl = PayConfig.WECHAT_OPENID_URL.replace("APPID", wechatConfigPar.getAppId())
						.replaceAll("SECRET", wechatConfigPar.getAppSecret())
						.replaceAll("CODE", wapPayReq.getAuth_code());
				//获取openId
				String recvJsonMsg = HttpUtil.doGet(reqUrl, null, "UTF-8", false);
				Log.info("------------获取openId返回内容"+recvJsonMsg);
				JSONObject obj = JSONObject.parseObject(recvJsonMsg);
				String openId = obj.getString("openid");
				if(StringUtils.isEmpty(openId)){
					return new Message("open id error", "openid获取失败!");
				}
				wapPayReq.setOpen_id(openId);
			}
			//创建订单流水
			PayTranLs payTranLs = payTranLsService.createWapTranLs(wapPayReq, merchant, PayChannel.WECHAT);
			//金额转换为分
			int totalFee = Integer.valueOf(AmountUtil.changeY2F(payTranLs.getTransPrice().toString()));
			//获取本地ip
			String ip = InetAddress.getLocalHost().getHostAddress();
			//订单发起时间
			String timeStart = DateUtil.getNow("yyyyMMddHHmmss");
			//订单失效时间
			String timeExpire = DateUtil.getDateAfter(DateUtil.getNow("yyyyMMddHHmmss"), "yyyyMMddHHmmss", 1);
			//构造微信扫码支付请求参数
			WechatWapReq wechatWapReq = new WechatWapReq(wechatConfigPar.getAppId(),wechatConfigPar.getMchId(),
					wechatConfigSub.getMchId(),payTranLs.getTransNum(),
					totalFee,wapPayReq.getBody(),ip,
					null,wapPayReq.getAttach(),PayConfig.WECHAT_SCAN_NOTIFY_URL,
					 timeStart,timeExpire,wapPayReq.getOpen_id());
			//签名
			String sign = CommonUtil.getSign(CommonUtil.toMap(wechatWapReq),wechatConfigPar.getApiSecret());
			wechatWapReq.setSign(sign);
			//发起微信wap支付
			WechatWapRep wechatWapRep = wechatQrPayService.doWechatWapPay(wechatWapReq);
			if(wechatWapRep!=null){
				if(ResultCode.FAIL.getCode().equals(wechatWapRep.getReturn_code())){
					//微信return_code为Fail
					payMsg = new Message(ResultCode.FAIL.getCode(),wechatWapRep.getReturn_msg());
				}else{
					//微信return_code和result_code同时为SUCCESS
					if(ResultCode.SUCCESS.getCode().equals(wechatWapRep.getResult_code())){
						Log.info("微信wap下单成功！");
						//封装wap支付返回参数
						Map<String,String> wxWapMap = new HashMap<String,String>(); 
						String prepayId = "prepay_id="+wechatWapRep.getPrepay_id();
						wxWapMap.put("appId", wechatWapRep.getAppid());
						wxWapMap.put("timeStamp",CommonUtil.getTimeStamp() );		
						wxWapMap.put("nonceStr", wechatWapRep.getNonce_str());
						wxWapMap.put("package", prepayId);
						wxWapMap.put("signType", "MD5");
						String paySign=  CommonUtil.getSign(wxWapMap, wechatConfigPar.getApiSecret());
						wxWapMap.put("paySign", paySign);
						wxWapMap.put("packages", prepayId);
						payMsg = new Message(wxWapMap);
					}else{
						//将微信返回的错误信息封装到结果包中
						payMsg = new Message(wechatWapRep.getErr_code(),wechatWapRep.getErr_code_des());
					}
				}
			}else{
				//返回结果为空
				payMsg = new Message(ErrorCode.CHANNEL_PAY_ERROR.getCode(),ErrorCode.CHANNEL_PAY_ERROR.getDes());
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			Log.error(e.toString());
			return new Message(ErrorCode.SYSTEM_EXCEPTION.getCode(), ErrorCode.SYSTEM_EXCEPTION.getDes());
		}
		return payMsg;
	}
	
	/**
	 * @Description:一码付发起微信交易请求
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月7日上午8:50:26 
	 * @param payTranLs
	 * @param authCode
	 * @return
	 */
	@Override
	public Message doOneCodePay(PayTranLs payTranLs,String authCode) {
		Message payMsg = null;
		try {
			//子商户配置
			WechatConfigKey wechatConfigSub = wechatConfigDao.findConfigByMerNo(payTranLs.getMerchantNo());
			if (wechatConfigSub == null) {
				return new Message(ErrorCode.ILLEGAL_PAYCONFIG.getCode(),ErrorCode.ILLEGAL_PAYCONFIG.getDes());
			}
			//主商户配置
			if(StringUtils.isEmpty(wechatConfigSub.getParentMchNo())){
				return new Message(ErrorCode.ILLEGAL_PAYCONFIG.getCode(),ErrorCode.ILLEGAL_PAYCONFIG.getDes()); 
			}
			WechatConfigKey wechatConfigPar =wechatConfigDao.findConfigByMerNo(wechatConfigSub.getParentMchNo());
			//如果open_id为空，用auth_code获取open_id
			String reqUrl = PayConfig.WECHAT_OPENID_URL.replace("APPID", wechatConfigPar.getAppId())
					.replaceAll("SECRET", wechatConfigPar.getAppSecret())
					.replaceAll("CODE", authCode);
			//获取openId
			String recvJsonMsg = HttpUtil.doGet(reqUrl, null, "UTF-8", false);
			Log.info("------------获取openId返回内容"+recvJsonMsg);
			JSONObject obj = JSONObject.parseObject(recvJsonMsg);
			String openId = obj.getString("openid");
			if(StringUtils.isEmpty(openId)){
				return new Message("openid error", "openid获取失败!");
			}
			//金额转换为分
			int totalFee = Integer.valueOf(AmountUtil.changeY2F(payTranLs.getTransPrice().toString()));
			//获取本地ip
			String ip = InetAddress.getLocalHost().getHostAddress();
			//订单发起时间
			String timeStart = DateUtil.getNow("yyyyMMddHHmmss");
			//订单失效时间
			String timeExpire = DateUtil.getDateAfter(DateUtil.getNow("yyyyMMddHHmmss"), "yyyyMMddHHmmss", 1);
			//构造微信扫码支付请求参数
			WechatWapReq wechatWapReq = new WechatWapReq(wechatConfigPar.getAppId(),wechatConfigPar.getMchId(),
					wechatConfigSub.getMchId(),payTranLs.getTransNum(),
					totalFee,payTranLs.getSubject(),ip,
					null,payTranLs.getAttach(),PayConfig.WECHAT_SCAN_NOTIFY_URL,
					 timeStart,timeExpire,openId);
			//签名
			String sign = CommonUtil.getSign(CommonUtil.toMap(wechatWapReq),wechatConfigPar.getApiSecret());
			wechatWapReq.setSign(sign);
			//发起微信wap支付
			WechatWapRep wechatWapRep = wechatQrPayService.doWechatWapPay(wechatWapReq);
			if(wechatWapRep!=null){
				if(ResultCode.FAIL.getCode().equals(wechatWapRep.getReturn_code())){
					//微信return_code为Fail
					payMsg = new Message(ResultCode.FAIL.getCode(),wechatWapRep.getReturn_msg());
					payTranLs.setStatus(PayStatus.FAILPAY.getValue());
				}else{
					//微信return_code和result_code同时为SUCCESS
					if(ResultCode.SUCCESS.getCode().equals(wechatWapRep.getResult_code())){
						Log.info("微信wap下单成功！");
						payTranLs.setStatus(PayStatus.PAYING.getValue());
						//封装wap支付返回参数
						Map<String,String> wxWapMap = new HashMap<String,String>(); 
						String prepayId = "prepay_id="+wechatWapRep.getPrepay_id();
						wxWapMap.put("appId", wechatWapRep.getAppid());
						wxWapMap.put("timeStamp",CommonUtil.getTimeStamp() );		
						wxWapMap.put("nonceStr", wechatWapRep.getNonce_str());
						wxWapMap.put("package", prepayId);
						wxWapMap.put("signType", "MD5");
						String paySign=  CommonUtil.getSign(wxWapMap, wechatConfigPar.getApiSecret());
						wxWapMap.put("paySign", paySign);
						wxWapMap.put("packages", prepayId);
						payMsg = new Message(wxWapMap);
					}else{
						//将微信返回的错误信息封装到结果包中
						payMsg = new Message(wechatWapRep.getErr_code(),wechatWapRep.getErr_code_des());
						payTranLs.setStatus(PayStatus.FAILPAY.getValue());
					}
				}
			}else{
				//返回结果为空
				payMsg = new Message(ErrorCode.CHANNEL_PAY_ERROR.getCode(),ErrorCode.CHANNEL_PAY_ERROR.getDes());
				payTranLs.setStatus(PayStatus.FAILPAY.getValue());
			}
			//修改预付流水,补充交易类型等信息
			payTranLsService.updateOneCodeTranLs(payTranLs, PayChannel.WECHAT);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			Log.error(e.toString());
			return new Message(ErrorCode.SYSTEM_EXCEPTION.getCode(), ErrorCode.SYSTEM_EXCEPTION.getDes());
		}
		return payMsg;
	}

	
	/**
	 * @Description:微信小程序支付
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月15日下午4:42:29 
	 * @param appletReq
	 * @param merchant
	 * @return
	 */
	@Override
	public Message doAppletPay(AppletReqDto appletReq, Merchant merchant) {
		Message payMsg = null;
		try {
			//子商户配置
			WechatConfigKey wechatConfigSub = wechatConfigDao.findConfigByMerNo(appletReq.getMerchant_num());
			if (wechatConfigSub == null) {
				return new Message(ErrorCode.ILLEGAL_PAYCONFIG.getCode(),ErrorCode.ILLEGAL_PAYCONFIG.getDes());
			}
			//主商户配置
			if(StringUtils.isEmpty(wechatConfigSub.getParentMchNo())){
				return new Message(ErrorCode.ILLEGAL_PAYCONFIG.getCode(),ErrorCode.ILLEGAL_PAYCONFIG.getDes()); 
			}
			WechatConfigKey wechatConfigPar =wechatConfigDao.findConfigByMerNo(wechatConfigSub.getParentMchNo());
			
			//创建订单流水
			PayTranLs payTranLs = payTranLsService.createAppletTranLs(appletReq, merchant);
			//金额转换为分
			int totalFee = Integer.valueOf(AmountUtil.changeY2F(payTranLs.getTransPrice().toString()));
			//获取本地ip
			String ip= InetAddress.getLocalHost().getHostAddress();
			//订单发起时间
			String timeStart = DateUtil.getNow("yyyyMMddHHmmss");
			//订单失效时间
			String timeExpire = DateUtil.getDateAfter(DateUtil.getNow("yyyyMMddHHmmss"), "yyyyMMddHHmmss", 1);
			//构造微信扫码支付请求参数
			WechatAppletReq wechatAppletReq = new WechatAppletReq(wechatConfigPar.getAppId(),wechatConfigPar.getMchId(),
					wechatConfigSub.getMchId(),wechatConfigSub.getWxAppAppId(),payTranLs.getTransNum(),
					totalFee,appletReq.getBody(),ip,
					null,appletReq.getAttach(),PayConfig.WECHAT_SCAN_NOTIFY_URL,
					 timeStart,timeExpire,appletReq.getOpen_id());
			//签名
			String sign = CommonUtil.getSign(CommonUtil.toMap(wechatAppletReq),wechatConfigPar.getApiSecret());
			wechatAppletReq.setSign(sign);
			//发起微信wap支付
			WechatAppletRep wechatAppletRep = wechatQrPayService.doWechatAppletPay(wechatAppletReq);
			if(wechatAppletRep!=null){
				if(ResultCode.FAIL.getCode().equals(wechatAppletRep.getReturn_code())){
					//微信return_code为Fail
					payMsg = new Message(ResultCode.FAIL.getCode(),wechatAppletRep.getReturn_msg());
				}else{
					//微信return_code和result_code同时为SUCCESS
					if(ResultCode.SUCCESS.getCode().equals(wechatAppletRep.getResult_code())){
						Log.info("微信applet下单成功！");
						//封装wap支付返回参数
						Map<String,String> wxAppletMap = new HashMap<String,String>(); 
						String prepayId = "prepay_id="+wechatAppletRep.getPrepay_id();
						wxAppletMap.put("appId", wechatAppletRep.getAppid());
						wxAppletMap.put("timeStamp",CommonUtil.getTimeStamp() );		
						wxAppletMap.put("nonceStr", wechatAppletRep.getNonce_str());
						wxAppletMap.put("package", prepayId);
						wxAppletMap.put("signType", "MD5");
						String paySign=  CommonUtil.getSign(wxAppletMap, wechatConfigPar.getApiSecret());
						wxAppletMap.put("paySign", paySign);
						wxAppletMap.put("packages", prepayId);
						payMsg = new Message(wxAppletMap);
					}else{
						//将微信返回的错误信息封装到结果包中
						payMsg = new Message(wechatAppletRep.getErr_code(),wechatAppletRep.getErr_code_des());
					}
				}
			}else{
				//返回结果为空
				payMsg = new Message(ErrorCode.CHANNEL_PAY_ERROR.getCode(),ErrorCode.CHANNEL_PAY_ERROR.getDes());
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			Log.error(e.toString());
			return new Message(ErrorCode.SYSTEM_EXCEPTION.getCode(), ErrorCode.SYSTEM_EXCEPTION.getDes());
		}
		return payMsg;
	}

	/**
	 * @Description:微信app支付
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年11月1日上午10:17:44 
	 * @param appPayReq
	 * @param merchant
	 * @return
	 */
	@Override
	public Message doAppPay(AppPayReqDto appPayReq, Merchant merchant) {
		Message payMsg = null;
		try {
			//子商户配置
			WechatConfigKey wechatConfigSub = wechatConfigDao.findConfigByMerNo(appPayReq.getMerchant_num());
			if (wechatConfigSub == null) {
				return new Message(ErrorCode.ILLEGAL_PAYCONFIG.getCode(),ErrorCode.ILLEGAL_PAYCONFIG.getDes());
			}
			//主商户配置
			if(StringUtils.isEmpty(wechatConfigSub.getParentMchNo())){
				return new Message(ErrorCode.ILLEGAL_PAYCONFIG.getCode(),ErrorCode.ILLEGAL_PAYCONFIG.getDes()); 
			}
			WechatConfigKey wechatConfigPar =wechatConfigDao.findConfigByMerNo(wechatConfigSub.getParentMchNo());
			
			//创建订单流水
			PayTranLs payTranLs = payTranLsService.createAppTranLs(appPayReq, merchant,PayChannel.WECHAT);
			//金额转换为分
			int totalFee = Integer.valueOf(AmountUtil.changeY2F(payTranLs.getTransPrice().toString()));
			//获取本地ip
			String ip= InetAddress.getLocalHost().getHostAddress();
			//构造微app支付请求参数
			WechatAppReq wechatAppReq = new WechatAppReq(wechatConfigPar.getAppId(),wechatConfigPar.getMchId(),
					wechatConfigSub.getMchId(),wechatConfigSub.getWxAppAppId(),payTranLs.getTransNum(),
					totalFee,appPayReq.getBody(),ip,"",appPayReq.getAttach(),PayConfig.WECHAT_SCAN_NOTIFY_URL);
			//签名
			String sign = CommonUtil.getSign(CommonUtil.toMap(wechatAppReq),wechatConfigPar.getApiSecret());
			wechatAppReq.setSign(sign);
			//发起微信App支付下单
			WechatAppRep wechatAppRep = wechatQrPayService.doWechatAppPay(wechatAppReq);
			if(wechatAppRep!=null){
				if(ResultCode.FAIL.getCode().equals(wechatAppRep.getReturn_code())){
					//微信return_code为Fail
					payMsg = new Message(ResultCode.FAIL.getCode(),wechatAppRep.getReturn_msg());
				}else{
					//微信return_code和result_code同时为SUCCESS
					if(ResultCode.SUCCESS.getCode().equals(wechatAppRep.getResult_code())){
						Log.info("微信app下单成功！");
						//封装wap支付返回参数
						Map<String,String> wxMap = new HashMap<String,String>(); 
						wxMap.put("appid", wechatAppRep.getSub_appid());
						wxMap.put("partnerid", wechatAppRep.getSub_mch_id());
						wxMap.put("prepayid", wechatAppRep.getPrepay_id());
						//根据微信文档要求，填固定值
						wxMap.put("package", "Sign=WXPay");
						wxMap.put("noncestr", wechatAppRep.getNonce_str());
						wxMap.put("timestamp",CommonUtil.getTimeStamp());
						String paySign=  CommonUtil.getSign(wxMap, wechatConfigPar.getApiSecret());
						wxMap.put("sign", paySign);
						payMsg = new Message(wxMap);
					}else{
						//将微信返回的错误信息封装到结果包中
						payMsg = new Message(wechatAppRep.getErr_code(),wechatAppRep.getErr_code_des());
					}
				}
			}else{
				//返回结果为空
				payMsg = new Message(ErrorCode.CHANNEL_PAY_ERROR.getCode(),ErrorCode.CHANNEL_PAY_ERROR.getDes());
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			Log.error(e.toString());
			return new Message(ErrorCode.SYSTEM_EXCEPTION.getCode(), ErrorCode.SYSTEM_EXCEPTION.getDes());
		}
		return payMsg;
	}
	
	/**
	 * @Description:微信订单查询
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年6月23日下午3:25:20 
	 * @param payTranLs
	 * @return
	 */
	@Override
	public Message doOrderQuery(PayTranLs payTranLs) {
		Message payMsg = null;
		try{
			//子商户配置
			WechatConfigKey wechatConfigSub = wechatConfigDao.findConfigByMerNo(payTranLs.getMerchantNo());
			if (wechatConfigSub == null) {
				return new Message(ErrorCode.ILLEGAL_PAYCONFIG.getCode(),ErrorCode.ILLEGAL_PAYCONFIG.getDes());
			}
			//主商户配置
			if(StringUtils.isEmpty(wechatConfigSub.getParentMchNo())){
				return new Message(ErrorCode.ILLEGAL_PAYCONFIG.getCode(),ErrorCode.ILLEGAL_PAYCONFIG.getDes()); 
			}
			WechatConfigKey wechatConfigPar =wechatConfigDao.findConfigByMerNo(wechatConfigSub.getParentMchNo());
			//构造订单查询请求参数
			WechatQueryReq wechatQueryReq = new WechatQueryReq(wechatConfigPar.getAppId(),wechatConfigPar.getMchId(),
					wechatConfigSub.getMchId(),payTranLs.getTrade_no(),payTranLs.getTransNum());
			//签名
			String sign = CommonUtil.getSign(CommonUtil.toMap(wechatQueryReq),wechatConfigPar.getApiSecret());
			wechatQueryReq.setSign(sign);
			//发起订单查询
			WechatQueryRep wechatQueryRep = wechatQrPayService.doWechatQuery(wechatQueryReq);
			if(wechatQueryRep!=null){
				if(ResultCode.FAIL.getCode().equals(wechatQueryRep.getReturn_code())){
					//微信return_code为Fail
					payMsg = new Message(ResultCode.FAIL.getCode(),wechatQueryRep.getReturn_msg());
				}else{
					//微信return_code和result_code同时为SUCCESS
					if(ResultCode.SUCCESS.getCode().equals(wechatQueryRep.getResult_code())){
						//微信返回订单状态
						String trade_state = wechatQueryRep.getTrade_state();
						//订单状态转换
						PayStatus payStatus = CommonUtil.convertWxStatus(trade_state);
						payTranLs.setStatus(payStatus.getValue());
						//支付成功,将扩展信息保存至流水表中
						if(payStatus==PayStatus.PAYED){
							Log.info("订单支付成功");
//							try {
//								//微信返回实际支付金额
//								payTranLs.setTransPrice(Float.valueOf(AmountUtil.changeF2Y(wechatQueryRep.getCash_fee())));
//							}  catch (Exception e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//								Log.error("cash_fee 转换异常");
//							}
							payTranLs.setTrade_no(wechatQueryRep.getTransaction_id());
							//设置用户openid
							payTranLs.setTransCardNum(wechatQueryRep.getOpenid());
							payTranLs.setOpenid(wechatQueryRep.getOpenid());
							//如果存在卡券，则发送核销通知到activemq
							if(StringUtils.isNotBlank(payTranLs.getCouponCode()) 
									&& StringUtils.isNotBlank(payTranLs.getAsynNotify())
									&& payTranLs.getNotifyStatus()==0){
								//发送卡券核销通知到消息队列activemq
								if(couponNotifyService.sendCouponNotify(payTranLs.getAsynNotify())){
									payTranLs.setNotifyStatus(1);
								}
							}
						}
						//修改订单
						payTranLsService.updatePayTranLs(payTranLs);
						//封装平台返回参数
						BarPayRepDto queryResData = new BarPayRepDto(payTranLs);
						payMsg = new Message(queryResData); 
					}else{
						//将微信返回的错误信息封装到结果包中
						Log.info("微信查询失败，err_code_desc="+wechatQueryRep.getErr_code_des());
						payMsg = new Message(wechatQueryRep.getErr_code(),wechatQueryRep.getErr_code_des());
					}
				}
			}else{
				//返回结果为空
				payMsg = new Message(ErrorCode.CHANNEL_PAY_ERROR.getCode(),ErrorCode.CHANNEL_PAY_ERROR.getDes());
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			Log.error(e.toString());
			return new Message(ErrorCode.SYSTEM_EXCEPTION.getCode(), ErrorCode.SYSTEM_EXCEPTION.getDes());
		}
		return payMsg;
	}

	/**
	 * @Description:微信订单撤销
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年6月23日下午4:40:16 
	 * @param payTranLs
	 * @return
	 */
	@Override
	public Message doOrderReverse(PayTranLs payTranLs) {
		Message payMsg = null;
		try{
			//子商户配置
			WechatConfigKey wechatConfigSub = wechatConfigDao.findConfigByMerNo(payTranLs.getMerchantNo());
			if (wechatConfigSub == null) {
				return new Message(ErrorCode.ILLEGAL_PAYCONFIG.getCode(),ErrorCode.ILLEGAL_PAYCONFIG.getDes());
			}
			//主商户配置
			if(StringUtils.isEmpty(wechatConfigSub.getParentMchNo())){
				return new Message(ErrorCode.ILLEGAL_PAYCONFIG.getCode(),ErrorCode.ILLEGAL_PAYCONFIG.getDes()); 
			}
			WechatConfigKey wechatConfigPar =wechatConfigDao.findConfigByMerNo(wechatConfigSub.getParentMchNo());
			//构造订单查询请求参数
			WechatReverseReq wechatReverseReq = new WechatReverseReq(wechatConfigPar.getAppId(),wechatConfigPar.getMchId(),
					wechatConfigSub.getMchId(),payTranLs.getTrade_no(),payTranLs.getTransNum());
			//签名
			String sign = CommonUtil.getSign(CommonUtil.toMap(wechatReverseReq),wechatConfigPar.getApiSecret());
			wechatReverseReq.setSign(sign);
			//发起订单查询
			WechatReverseRep wechatReverseRep = wechatQrPayService.doWechatReverse(wechatReverseReq,
					wechatConfigPar.getCertLocalPath(),wechatConfigPar.getCertPassword());
			if(wechatReverseRep!=null){
				if(ResultCode.FAIL.getCode().equals(wechatReverseRep.getReturn_code())){
					//微信return_code为Fail
					payMsg = new Message(ResultCode.FAIL.getCode(),wechatReverseRep.getReturn_msg());
				}else{
					//微信return_code和result_code同时为SUCCESS
					if(ResultCode.SUCCESS.getCode().equals(wechatReverseRep.getResult_code())){
						Log.info("订单撤销成功！");
						//微信返回订单状态
						payTranLs.setStatus(PayStatus.CANCEL.getValue());
						//修改订单状态
						payTranLsService.updTranStatus(payTranLs.getId(), PayStatus.CANCEL.getValue());
						//封装平台返回参数
						BarPayRepDto queryResData = new BarPayRepDto(payTranLs);
						payMsg = new Message(queryResData); 
					}else{
						//将微信返回的错误信息封装到结果包中
						payMsg = new Message(wechatReverseRep.getErr_code(),wechatReverseRep.getErr_code_des());
					}
				}
			}else{
				//返回结果为空
				payMsg = new Message(ErrorCode.CHANNEL_PAY_ERROR.getCode(),ErrorCode.CHANNEL_PAY_ERROR.getDes());
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			Log.error(e.toString());
			return new Message(ErrorCode.SYSTEM_EXCEPTION.getCode(), ErrorCode.SYSTEM_EXCEPTION.getDes());
		}
		return payMsg;
	}

	
	/**
	 * @Description:微信退款申请
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年7月10日下午4:40:43 
	 * @param qrRefundReq
	 * @param payTranLs
	 * @return
	 */
	@Override
	public Message doOrderRefund(QrRefundReqDto qrRefundReq,PayTranLs payTranLs) {
		Message payMsg = null;
		try {
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
			//子商户配置
			WechatConfigKey wechatConfigSub = wechatConfigDao.findConfigByMerNo(qrRefundReq.getMerchant_num());
			if (wechatConfigSub == null) {
				return new Message(ErrorCode.ILLEGAL_PAYCONFIG.getCode(),ErrorCode.ILLEGAL_PAYCONFIG.getDes());
			}
			//主商户配置
			if(StringUtils.isEmpty(wechatConfigSub.getParentMchNo())){
				return new Message(ErrorCode.ILLEGAL_PAYCONFIG.getCode(),ErrorCode.ILLEGAL_PAYCONFIG.getDes()); 
			}
			WechatConfigKey wechatConfigPar =wechatConfigDao.findConfigByMerNo(wechatConfigSub.getParentMchNo());		
			//构造支付请求参数
			WechatRefundReq wechatRefundReq = new WechatRefundReq(wechatConfigPar.getAppId(),wechatConfigPar.getMchId(),
					wechatConfigSub.getMchId(),payTranLs.getTrade_no(),payTranLs.getTransNum(),refundTranLs.getTransNum(),
					transFee,refundFee,qrRefundReq.getRefund_desc());
			//签名
			String sign = CommonUtil.getSign(CommonUtil.toMap(wechatRefundReq),wechatConfigPar.getApiSecret());
			wechatRefundReq.setSign(sign);
			//发起微信支付
			WechatRefundRep wechantRefundRep = wechatQrPayService.doWechatRefund(wechatRefundReq,
					wechatConfigPar.getCertLocalPath(),wechatConfigPar.getCertPassword());
			if(wechantRefundRep!=null){
				if(ResultCode.FAIL.getCode().equals(wechantRefundRep.getReturn_code())){
					//微信return_code为Fail
					payMsg = new Message(ResultCode.FAIL.getCode(),wechantRefundRep.getReturn_msg());
				}else{
					//微信return_code和result_code同时为SUCCESS
					if(ResultCode.SUCCESS.getCode().equals(wechantRefundRep.getResult_code())){
						Log.info("微信退款申请成功！");
						refundTranLs.setStatus(RefundStatus.REFUNDING.getValue());
						refundTranLs.setTradeNo(wechantRefundRep.getRefund_id());
						refundTranLsService.updateRefundTranLs(refundTranLs);
						RefundRepDto refundRep = new RefundRepDto(refundTranLs);
						//update by 2017-09-18 19:39
						//增加对原订单的退款信息进行修改(暂时认为退款申请成功就是退款成功，此流程后续需优化)
						if((refundFee+refundedFee)==transFee){
							//如果退款累计金额==付款金额，退款金额累加并修改状态为已退款
							payTranLsService.updTranLsRefund(payTranLs.getId(),PayStatus.REFUNDED.getValue(),Float.valueOf(qrRefundReq.getRefund_fee()));
						}else{
							//累加退款金额
							payTranLsService.updTranLsRefund(payTranLs.getId(),payTranLs.getStatus(),Float.valueOf(qrRefundReq.getRefund_fee()));
						}
						payMsg = new Message(refundRep);
					}else{
						Log.info("微信退款申请失败，err_code_desc="+wechantRefundRep.getErr_code_des());
						refundTranLsService.updTranStatus(refundTranLs.getId(), RefundStatus.FAILREFUND.getValue());
						payMsg = new Message(wechantRefundRep.getErr_code(),wechantRefundRep.getErr_code_des());
					}
				}
			}else{
				//返回结果为空
				payMsg = new Message(ErrorCode.CHANNEL_PAY_ERROR.getCode(),ErrorCode.CHANNEL_PAY_ERROR.getDes());
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			Log.error(e.toString());
			return new Message(ErrorCode.SYSTEM_EXCEPTION.getCode(), ErrorCode.SYSTEM_EXCEPTION.getDes());
		}
		return payMsg;
	}
	
	
	/**
	 * @Description:微信退款查询
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年7月10日下午4:40:43 
	 * @param qrRefundReq
	 * @param payTranLs
	 * @return
	 */
	@Override
	public Message doRefundQuery(RefundTranLs refundTranLs) {
		Message payMsg = null;
		try {
			//子商户配置
			WechatConfigKey wechatConfigSub = wechatConfigDao.findConfigByMerNo(refundTranLs.getMerchantNo());
			if (wechatConfigSub == null) {
				return new Message(ErrorCode.ILLEGAL_PAYCONFIG.getCode(),ErrorCode.ILLEGAL_PAYCONFIG.getDes());
			}
			//主商户配置
			if(StringUtils.isEmpty(wechatConfigSub.getParentMchNo())){
				return new Message(ErrorCode.ILLEGAL_PAYCONFIG.getCode(),ErrorCode.ILLEGAL_PAYCONFIG.getDes()); 
			}
			WechatConfigKey wechatConfigPar =wechatConfigDao.findConfigByMerNo(wechatConfigSub.getParentMchNo());
			
			//构造支付请求参数
			WechatRefundQueryReq refundQueryReq = new WechatRefundQueryReq(wechatConfigPar.getAppId(),wechatConfigPar.getMchId(),
					wechatConfigSub.getMchId(),refundTranLs.getTradeNo(),refundTranLs.getTransNum());
			//签名
			String sign = CommonUtil.getSign(CommonUtil.toMap(refundQueryReq),wechatConfigPar.getApiSecret());
			refundQueryReq.setSign(sign);
			//发起微信支付
			WechatRefundQueryRep refundQueryRep = wechatQrPayService.doWechatRefundQuery(refundQueryReq);
			if(refundQueryRep!=null){
				if(ResultCode.FAIL.getCode().equals(refundQueryRep.getReturn_code())){
					//微信return_code为Fail
					payMsg = new Message(ResultCode.FAIL.getCode(),refundQueryRep.getReturn_msg());
				}else{
					//微信return_code和result_code同时为SUCCESS
					if(ResultCode.SUCCESS.getCode().equals(refundQueryRep.getResult_code())){
						
						RefundStatus refundStatus = CommonUtil.convertWxRefundStatus(refundQueryRep.getRefund_status_0());
						refundTranLs.setStatus(refundStatus.getValue());
						//如果退款状态为成功
						if(refundStatus==RefundStatus.REFUNDED){
							Log.info("微信退款成功！");
							refundTranLs.setRefundAccount(refundQueryRep.getRefund_account_0());
							refundTranLs.setRefundChannel(refundQueryRep.getRefund_channel_0());
							refundTranLs.setTradeNo(refundQueryRep.getRefund_id_0());
							refundTranLs.setRefundRecvAccout(refundQueryRep.getRefund_recv_accout_0());
							refundTranLs.setRefundSuccessTime(refundQueryRep.getRefund_success_time_0());
							refundTranLsService.updateRefundTranLs(refundTranLs);
						}else{
							refundTranLsService.updTranStatus(refundTranLs.getId(), refundStatus.getValue());
						}
						RefundQueryRepDto rep = new RefundQueryRepDto(refundTranLs);
						payMsg = new Message(rep);
					}else{
						Log.info("微信退款查询失败，err_code_desc="+refundQueryRep.getErr_code_des());
						payMsg = new Message(refundQueryRep.getErr_code(),refundQueryRep.getErr_code_des());
					}
				}
			}else{
				//返回结果为空
				payMsg = new Message(ErrorCode.CHANNEL_PAY_ERROR.getCode(),ErrorCode.CHANNEL_PAY_ERROR.getDes());
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			Log.error(e.toString());
			return new Message(ErrorCode.SYSTEM_EXCEPTION.getCode(), ErrorCode.SYSTEM_EXCEPTION.getDes());
		}
		return payMsg;
	}

	/**
	 * @Description:订单关闭
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年7月12日下午5:59:38 
	 * @param payTranLs
	 * @return
	 */
	@Override
	public Message doOrderClose(PayTranLs payTranLs) {
		Message payMsg = null;
		//子商户配置
		WechatConfigKey wechatConfigSub = wechatConfigDao.findConfigByMerNo(payTranLs.getMerchantNo());
		if (wechatConfigSub == null) {
			return new Message(ErrorCode.ILLEGAL_PAYCONFIG.getCode(),ErrorCode.ILLEGAL_PAYCONFIG.getDes());
		}
		//主商户配置
		if(StringUtils.isEmpty(wechatConfigSub.getParentMchNo())){
			return new Message(ErrorCode.ILLEGAL_PAYCONFIG.getCode(),ErrorCode.ILLEGAL_PAYCONFIG.getDes()); 
		}
		WechatConfigKey wechatConfigPar =wechatConfigDao.findConfigByMerNo(wechatConfigSub.getParentMchNo());
		//构造订单查询请求参数
		WechatCloseReq wechatCloseReq = new WechatCloseReq(wechatConfigPar.getAppId(),wechatConfigPar.getMchId(),
				wechatConfigSub.getMchId(),payTranLs.getTransNum());
		//签名
		String sign = CommonUtil.getSign(CommonUtil.toMap(wechatCloseReq),wechatConfigPar.getApiSecret());
		wechatCloseReq.setSign(sign);
		//发起订单查询
		WechatCloseRep wechatCloseRep = wechatQrPayService.doWechatClose(wechatCloseReq);
		if(wechatCloseRep!=null){
			if(ResultCode.FAIL.getCode().equals(wechatCloseRep.getReturn_code())){
				//微信return_code为Fail
				payMsg = new Message(ResultCode.FAIL.getCode(),wechatCloseRep.getReturn_msg());
			}else{
				//微信return_code和result_code同时为SUCCESS
				if(ResultCode.SUCCESS.getCode().equals(wechatCloseRep.getResult_code())){
					Log.info("订单关闭成功！");
					//微信返回订单状态
					payTranLs.setStatus(PayStatus.CLOSED.getValue());
					//修改订单状态
					payTranLsService.updTranStatus(payTranLs.getId(), PayStatus.CLOSED.getValue());
					//封装平台返回参数
					BarPayRepDto queryResData = new BarPayRepDto(payTranLs);
					payMsg = new Message(ResultCode.SUCCESS.name(), "订单关闭成功", queryResData); 
				}else{
					//将微信返回的错误信息封装到结果包中
					payMsg = new Message(wechatCloseRep.getErr_code(),wechatCloseRep.getErr_code_des());
				}
			}
		}else{
			//返回结果为空
			payMsg = new Message(ErrorCode.CHANNEL_PAY_ERROR.getCode(),ErrorCode.CHANNEL_PAY_ERROR.getDes());
		}
		return payMsg;
	}

}
