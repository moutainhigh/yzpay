package com.yunpay.wx.service.impl;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.yunpay.common.utils.CommonUtil;
import com.yunpay.common.utils.Log;
import com.yunpay.common.utils.ResultEnum.ErrorCode;
import com.yunpay.common.utils.ResultEnum.ResultCode;
import com.yunpay.common.utils.XmlUtil;
import com.yunpay.wx.common.HttpsRequest;
import com.yunpay.wx.config.WechatPayConfig;
import com.yunpay.wx.rep.pay.WechatAppRep;
import com.yunpay.wx.rep.pay.WechatAppletRep;
import com.yunpay.wx.rep.pay.WechatBarRep;
import com.yunpay.wx.rep.pay.WechatCloseRep;
import com.yunpay.wx.rep.pay.WechatNotifyRep;
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
 * 文件名称:    WechatQrPayServiceImpl.java
 * 内容摘要:    微信二维码支付业务相关接口封装类,封装了微信条码、扫码、公众号、app、小程序等和支付相关的接口
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年6月19日下午4:47:02 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年6月19日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
@Service("wechatQrPayService")
public class WechatQrPayServiceImpl implements WechatQrPayService{
	
	/**
	 * @Description: 微信条码支付
	 * @author:      Zeng Dongcheng
	 * @Date:        2017年6月20日上午10:35:54 
	 * @param wechatBarReq
	 * @return
	 */
	@Override
	public WechatBarRep doWechatBarPay(WechatBarReq wechatBarReq) {
		//获取初始时间
		long costTimeStart = System.currentTimeMillis();
		
		WechatBarRep rep;	
		try {
			HttpsRequest serviceRequest = new HttpsRequest();
			//发送交易请求
			String payServiceResponse = serviceRequest.sendPost(
					WechatPayConfig.BAR_PAY_API, wechatBarReq);
			//获取结束时间
			long costTimeEnd = System.currentTimeMillis();
			//计算接口调用花费时间
			long totalTimeCost = costTimeEnd - costTimeStart;
			//打印调用时间
			Log.info("api请求总耗时：" + totalTimeCost + "ms");
			//打印后台返回报文
			Log.debug("微信条码支付返回结果：" + payServiceResponse);
			//将结果转为map
			if(StringUtils.isNotEmpty(payServiceResponse)){
				//将微信返回的结果转为map集合
				Map<String, String> responseMap = XmlUtil.getMapFromXML(payServiceResponse);
				//将map结果封装为对象
				rep =(WechatBarRep)CommonUtil.convertMap(WechatBarRep.class, responseMap);
				return rep;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.error(e.toString());
		} 
		rep = new WechatBarRep(ResultCode.FAIL.getCode(),ResultCode.FAIL.getDes(),
				ErrorCode.SYSTEM_EXCEPTION.getCode(),ErrorCode.SYSTEM_EXCEPTION.getDes());
		return rep;
	}

	/**
	 * @Description:微信扫码支付
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年6月20日上午10:36:23 
	 * @param wechatScanReq
	 * @return
	 */
	@Override
	public WechatScanRep doWechatScanPay(WechatScanReq wechatScanReq) {
		//获取初始时间
		long costTimeStart = System.currentTimeMillis();
		//将结果转为map
		WechatScanRep rep;
		try {
			HttpsRequest serviceRequest = new HttpsRequest();
			//发送交易请求
			String payServiceResponse = serviceRequest.sendPost(
					WechatPayConfig.SCAN_PAY_API, wechatScanReq);
			//获取结束时间
			long costTimeEnd = System.currentTimeMillis();
			//计算接口调用花费时间
			long totalTimeCost = costTimeEnd - costTimeStart;
			//打印调用时间
			Log.info("api请求总耗时：" + totalTimeCost + "ms");
			//打印后台返回报文
			Log.debug("微信扫码支付返回结果：" + payServiceResponse);
			if(StringUtils.isNotEmpty(payServiceResponse)){
				//将微信返回的结果转为map集合
				Map<String, String> responseMap = XmlUtil.getMapFromXML(payServiceResponse);
				//将map结果封装为对象
				rep =(WechatScanRep)CommonUtil.convertMap(WechatScanRep.class, responseMap);
				return rep;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.error(e.toString());
		} 
		rep = new WechatScanRep(ResultCode.FAIL.getCode(),ResultCode.FAIL.getDes(),
				ErrorCode.SYSTEM_EXCEPTION.getCode(),ErrorCode.SYSTEM_EXCEPTION.getDes());
		return rep;
	}
	
	/**
	 * @Description:微信wap支付
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年6月27日上午9:56:18 
	 * @param whecatWapReq
	 * @param certPath
	 * @param certPwd
	 * @return
	 */
	@Override
	public WechatWapRep doWechatWapPay(WechatWapReq whecatWapReq) {
		//获取初始时间
		long costTimeStart = System.currentTimeMillis();
		
		//将结果转为map
		WechatWapRep rep;
		try {
			HttpsRequest serviceRequest = new HttpsRequest();
			//发送交易请求
			String payServiceResponse = serviceRequest.sendPost(
					WechatPayConfig.SCAN_PAY_API, whecatWapReq);
			//获取结束时间
			long costTimeEnd = System.currentTimeMillis();
			//计算接口调用花费时间
			long totalTimeCost = costTimeEnd - costTimeStart;
			//打印调用时间
			Log.info("api请求总耗时：" + totalTimeCost + "ms");
			//打印后台返回报文
			Log.debug("微信wap支付返回结果：" + payServiceResponse);
			if(StringUtils.isNotEmpty(payServiceResponse)){
				//将微信返回的结果转为map集合
				Map<String, String> responseMap = XmlUtil.getMapFromXML(payServiceResponse);
				//将map结果封装为对象
				rep =(WechatWapRep)CommonUtil.convertMap(WechatWapRep.class, responseMap);
				return rep;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.error(e.toString());
		} 
		rep = new WechatWapRep(ResultCode.FAIL.getCode(),ResultCode.FAIL.getDes(),
				ErrorCode.SYSTEM_EXCEPTION.getCode(),ErrorCode.SYSTEM_EXCEPTION.getDes());
		return rep;
	}
	
	/**
	 * @Description:微信小程序支付
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月15日下午4:40:50 
	 * @param wechatAppletReq
	 * @return
	 */
	@Override
	public WechatAppletRep doWechatAppletPay(WechatAppletReq wechatAppletReq) {
		//获取初始时间
		long costTimeStart = System.currentTimeMillis();
		//将结果转为map
		WechatAppletRep rep;
		try {
			HttpsRequest serviceRequest = new HttpsRequest();
			//发送交易请求
			String payServiceResponse = serviceRequest.sendPost(
					WechatPayConfig.SCAN_PAY_API, wechatAppletReq);
			//获取结束时间
			long costTimeEnd = System.currentTimeMillis();
			//计算接口调用花费时间
			long totalTimeCost = costTimeEnd - costTimeStart;
			//打印调用时间
			Log.info("api请求总耗时：" + totalTimeCost + "ms");
			//打印后台返回报文
			Log.debug("微信applet支付返回结果：" + payServiceResponse);
			if(StringUtils.isNotEmpty(payServiceResponse)){
				//将微信返回的结果转为map集合
				Map<String, String> responseMap = XmlUtil.getMapFromXML(payServiceResponse);
				//将map结果封装为对象
				rep =(WechatAppletRep)CommonUtil.convertMap(WechatAppletRep.class, responseMap);
				return rep;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.error(e.toString());
		} 
		rep = new WechatAppletRep(ResultCode.FAIL.getCode(),ResultCode.FAIL.getDes(),
				ErrorCode.SYSTEM_EXCEPTION.getCode(),ErrorCode.SYSTEM_EXCEPTION.getDes());
		return rep;
	}
	
	/**
	 * @Description:微信app支付
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年10月31日下午3:29:06 
	 * @param wechatAppReq
	 * @return
	 */
	@Override
	public WechatAppRep doWechatAppPay(WechatAppReq wechatAppReq) {
		//获取初始时间
		long costTimeStart = System.currentTimeMillis();
		//将结果转为map
		WechatAppRep rep;
		try {
			HttpsRequest serviceRequest = new HttpsRequest();
			//发送交易请求
			String payServiceResponse = serviceRequest.sendPost(
					WechatPayConfig.SCAN_PAY_API, wechatAppReq);
			//获取结束时间
			long costTimeEnd = System.currentTimeMillis();
			//计算接口调用花费时间
			long totalTimeCost = costTimeEnd - costTimeStart;
			//打印调用时间
			Log.info("api请求总耗时：" + totalTimeCost + "ms");
			//打印后台返回报文
			Log.debug("微信app支付返回结果：" + payServiceResponse);
			if(StringUtils.isNotEmpty(payServiceResponse)){
				//将微信返回的结果转为map集合
				Map<String, String> responseMap = XmlUtil.getMapFromXML(payServiceResponse);
				//将map结果封装为对象
				rep =(WechatAppRep)CommonUtil.convertMap(WechatAppRep.class, responseMap);
				return rep;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.error(e.toString());
		} 
		rep = new WechatAppRep(ResultCode.FAIL.getCode(),ResultCode.FAIL.getDes(),
				ErrorCode.SYSTEM_EXCEPTION.getCode(),ErrorCode.SYSTEM_EXCEPTION.getDes());
		return rep;
	}

	

	
	/**
	 * @Description:微信订单查询
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年6月23日下午2:28:35 
	 * @param wechatQueryReq
	 * @param certPath
	 * @param certPwd
	 * @return
	 */
	@Override
	public WechatQueryRep doWechatQuery(WechatQueryReq wechatQueryReq) {
		WechatQueryRep rep;
		try {
			//发送交易请求
			HttpsRequest serviceRequest = new HttpsRequest();
			String payServiceResponse = serviceRequest.sendPost(
					WechatPayConfig.ORDER_QUERY_API, wechatQueryReq);
			//打印后台返回报文
			Log.debug("微信订单查询返回结果：" + payServiceResponse);
			//将结果转为map
			if(StringUtils.isNotEmpty(payServiceResponse)){
				//将微信返回的结果转为map集合
				Map<String, String> responseMap = XmlUtil.getMapFromXML(payServiceResponse);
				//将map结果封装为对象
				rep =(WechatQueryRep)CommonUtil.convertMap(WechatQueryRep.class, responseMap);
				return rep;
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.error(e.toString());
		} 
		rep = new WechatQueryRep(ResultCode.FAIL.getCode(),ResultCode.FAIL.getDes(),
				ErrorCode.SYSTEM_EXCEPTION.getCode(),ErrorCode.SYSTEM_EXCEPTION.getDes());
		return rep;
	}

	/**
	 * @Description:微信订单撤销
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年6月23日下午2:37:28 
	 * @param wechatReverseReq
	 * @param certPath
	 * @param certPwd
	 * @return
	 */
	@Override
	public WechatReverseRep doWechatReverse(WechatReverseReq wechatReverseReq,
			String certPath, String certPwd) {
		WechatReverseRep rep;
		try {
			HttpsRequest serviceRequest = new HttpsRequest(certPath,certPwd);
			//发送交易请求
			String payServiceResponse = serviceRequest.sendPost(
					WechatPayConfig.ORDER_REVERSE_API, wechatReverseReq);
			//打印后台返回报文
			Log.debug("微信订单撤销返回结果：" + payServiceResponse);
			//将结果转为map
			if(StringUtils.isNotEmpty(payServiceResponse)){
				//将微信返回的结果转为map集合
				Map<String, String> responseMap = XmlUtil.getMapFromXML(payServiceResponse);
				//将map结果封装为对象
				rep =(WechatReverseRep)CommonUtil.convertMap(WechatReverseRep.class, responseMap);
				return rep;
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.error(e.toString());
		} 
		rep = new WechatReverseRep(ResultCode.FAIL.getCode(),ResultCode.FAIL.getDes(),
				ErrorCode.SYSTEM_EXCEPTION.getCode(),ErrorCode.SYSTEM_EXCEPTION.getDes());
		return rep;
	}
	
	
	/**
	 * @Description:微信订单关闭
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年7月7日下午1:54:03 
	 * @param wechatCloseReq
	 * @return
	 */
	@Override
	public WechatCloseRep doWechatClose(WechatCloseReq wechatCloseReq) {
		WechatCloseRep rep;
		try {
			HttpsRequest serviceRequest = new HttpsRequest();
			//发送交易请求
			String payServiceResponse = serviceRequest.sendPost(
					WechatPayConfig.ORDER_CLOSE_API, wechatCloseReq);
			//打印后台返回报文
			Log.debug("微信订单关闭返回结果：" + payServiceResponse);
			//将结果转为map
			if(StringUtils.isNotEmpty(payServiceResponse)){
				//将微信返回的结果转为map集合
				Map<String, String> responseMap = XmlUtil.getMapFromXML(payServiceResponse);
				//将map结果封装为对象
				rep =(WechatCloseRep)CommonUtil.convertMap(WechatCloseRep.class, responseMap);
				return rep;
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.error(e.toString());
		} 
		rep = new WechatCloseRep(ResultCode.FAIL.getCode(),ResultCode.FAIL.getDes(),
				ErrorCode.SYSTEM_EXCEPTION.getCode(),ErrorCode.SYSTEM_EXCEPTION.getDes());
		return rep;
	}

	/**
	 * @Description:微信订单退款申请
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年7月7日下午2:19:55 
	 * @param wechatRefundReq
	 * @param certPath
	 * @param certPwd
	 * @return
	 */
	@Override
	public WechatRefundRep doWechatRefund(WechatRefundReq wechatRefundReq,
			String certPath, String certPwd) {
		WechatRefundRep rep;
		try {
			HttpsRequest serviceRequest = new HttpsRequest(certPath,certPwd);
			//发送交易请求
			String payServiceResponse = serviceRequest.sendPost(WechatPayConfig.ORDER_REFUND_API, 
					wechatRefundReq);
			//打印后台返回报文
			Log.debug("微信退款申请返回结果：" + payServiceResponse);
			//将结果转为map
			if(StringUtils.isNotEmpty(payServiceResponse)){
				//将微信返回的结果转为map集合
				Map<String, String> responseMap = XmlUtil.getMapFromXML(payServiceResponse);
				//将map结果封装为对象
				rep =(WechatRefundRep)CommonUtil.convertMap(WechatRefundRep.class, responseMap);
				return rep;
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.error(e.toString());
		} 
		rep = new WechatRefundRep(ResultCode.FAIL.getCode(),ResultCode.FAIL.getDes(),
				ErrorCode.SYSTEM_EXCEPTION.getCode(),ErrorCode.SYSTEM_EXCEPTION.getDes());
		return rep;
	}
	
	/**
	 * @Description:微信退款查询
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年7月7日下午2:40:57 
	 * @param wechatRefundQeuryReq
	 * @return
	 */
	@Override
	public WechatRefundQueryRep doWechatRefundQuery(WechatRefundQueryReq wechatRefundQeuryReq) {
		HttpsRequest serviceRequest = new HttpsRequest();
		WechatRefundQueryRep rep;
		try {
			//发送交易请求
			String payServiceResponse = serviceRequest.sendPost(WechatPayConfig.ORDER_REFUND_QUERY_API, 
					wechatRefundQeuryReq);
			//打印后台返回报文
			Log.debug("微信退款查询返回结果：" + payServiceResponse);
			//将结果转为map
			if(StringUtils.isNotEmpty(payServiceResponse)){
				//将微信返回的结果转为map集合
				Map<String, String> responseMap = XmlUtil.getMapFromXML(payServiceResponse);
				//将map结果封装为对象
				rep =(WechatRefundQueryRep)CommonUtil.convertMap(WechatRefundQueryRep.class, responseMap);
				return rep;
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.error(e.toString());
		} 
		rep = new WechatRefundQueryRep(ResultCode.FAIL.getCode(),ResultCode.FAIL.getDes(),
				ErrorCode.SYSTEM_EXCEPTION.getCode(),ErrorCode.SYSTEM_EXCEPTION.getDes());
		return rep;
	}

	/**
	 * @Description:微信支付异步回调接收结果解析
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年6月27日下午5:20:40 
	 * @param recvInfo
	 * @return
	 */
	@Override
	public WechatNotifyRep analysisWechatNotify(String recvInfo) {
		if(StringUtils.isNotEmpty(recvInfo)){
			try {
				//将微信返回的结果转为map集合
				Map<String, String> responseMap = XmlUtil.getMapFromXML(recvInfo);
				//将map结果封装为对象
				WechatNotifyRep rep =(WechatNotifyRep)CommonUtil.convertMap(WechatNotifyRep.class, responseMap);
				return rep;
			}catch(Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.error(e.toString());
			} 
		}
		return null;
	}

	
	/**
	 * @Description: 交易耗时上报
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年6月20日上午10:36:43 
	 * @param serviceRequest
	 * @param wechatQrReq
	 * @param responseMap
	 * @param totalTimeCost
	 */
//	private void tranCostTimSend(HttpsRequest serviceRequest,WechatQrReq wechatQrReq,Map<String, String> responseMap,long totalTimeCost){
//		ReportReqData reportReqData = new ReportReqData(wechatQrReq.getAppid(),wechatQrReq.getMch_id(),
//				wechatQrReq.getDevice_info(), WechatPayConfig.BAR_PAY_API,(int) (totalTimeCost),
//				responseMap.get("return_code"), responseMap.get("return_msg"), responseMap.get("result_code"),
//				responseMap.get("err_code"), responseMap.get("err_code_des"), wechatQrReq.getOut_trade_no(),
//				wechatQrReq.getSpbill_create_ip(),wechatQrReq.getNonce_str());
//		serviceRequest.sendPost(WechatPayConfig.REPORT_API, reportReqData);
//	}

}
