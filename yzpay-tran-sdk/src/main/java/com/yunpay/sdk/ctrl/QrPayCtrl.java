package com.yunpay.sdk.ctrl;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunpay.common.utils.CommonUtil;
import com.yunpay.common.utils.EnumUtil.PayChannel;
import com.yunpay.common.utils.EnumUtil.PayStatus;
import com.yunpay.common.utils.EnumUtil.RefundStatus;
import com.yunpay.common.utils.EnumUtil.Route;
import com.yunpay.common.utils.EnumUtil.SubChannel;
import com.yunpay.common.utils.Log;
import com.yunpay.common.utils.MD5;
import com.yunpay.common.utils.ResultEnum.ErrorCode;
import com.yunpay.serv.model.Merchant;
import com.yunpay.serv.model.PayTranLs;
import com.yunpay.serv.model.RefundTranLs;
import com.yunpay.serv.rep.BarPayRepDto;
import com.yunpay.serv.rep.Message;
import com.yunpay.serv.rep.RefundQueryRepDto;
import com.yunpay.serv.req.pay.AppPayReqDto;
import com.yunpay.serv.req.pay.AppletReqDto;
import com.yunpay.serv.req.pay.BarPayReqDto;
import com.yunpay.serv.req.pay.OrderQrcReqDto;
import com.yunpay.serv.req.pay.QrRefundReqDto;
import com.yunpay.serv.req.pay.ScanPayReqDto;
import com.yunpay.serv.route.PayFactory;
import com.yunpay.serv.service.busi.MerchantService;
import com.yunpay.serv.service.busi.PayTranLsService;
import com.yunpay.serv.service.busi.RefundTranLsService;
import com.yunpay.serv.service.pay.QrPayService;
import com.yunpay.serv.validate.DataValidateUtil;
import com.yunpay.serv.validate.exception.ValidateException;

/**
 * 
 * 文件名称:     QrPayCtrl.java
 * 内容摘要: 二维码支付外部接口服务类
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年6月22日下午1:56:37 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年6月22日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
@Controller
public class QrPayCtrl extends BaseCtrl{
	
	@Autowired
	private MerchantService merchantService;
	@Autowired
	private PayTranLsService payTranLsService;
	@Autowired
	private RefundTranLsService refundTranLsService;
	@Autowired
	private PayFactory payFactory;
	
	/**
	 * @Description: 条码支付
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年6月23日上午10:38:09 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/pay/micropay",method=RequestMethod.POST)
	@ResponseBody
	public Object doBarPay(HttpServletRequest request, HttpServletResponse response) {
		Map<String,String> reqParamMap = this.getRequestParams(request);
		Log.info("条码支付入口,接收参数：" + reqParamMap);
		Message payMsg = null;
		try {
			//类型转换
			BarPayReqDto barPayReq = (BarPayReqDto) CommonUtil.convertMap(BarPayReqDto.class, reqParamMap);
			//商品标题
			if(StringUtils.isEmpty(barPayReq.getBody())){
				barPayReq.setBody("barcode pay");
			}
			if(!DataValidateUtil.valid(barPayReq)){
				//基础信息验证不通过
				return new Message(ErrorCode.PARAM_FORMAT_ERROR.getCode(),ErrorCode.PARAM_FORMAT_ERROR.getDes());
			}
			//获取商户信息
			Merchant merchant = merchantService.queryMerchInfo(barPayReq.getMerchant_num());
			if (merchant == null) {
				return new Message(ErrorCode.MERCHANT_NOT_EXIST.getCode(), ErrorCode.MERCHANT_NOT_EXIST.getDes());			
			}
			//签名验证
			if(!MD5.verify(reqParamMap, barPayReq.getSign(), merchant.getMd5Key(), "utf-8")){
				return new Message(ErrorCode.PARAM_SIGN_ERROR.getCode(),ErrorCode.PARAM_SIGN_ERROR.getDes());
			}
			//支付流水是否存在验证
			PayTranLs payTranLs = payTranLsService.findTranLsByOrderNo(barPayReq.getUser_order_no(), barPayReq.getMerchant_num());
			if(payTranLs!=null){
				return new Message(ErrorCode.ORDER_EXIST.getCode(), ErrorCode.ORDER_EXIST.getDes());
			}
			String orderPrefix = barPayReq.getDynamic_id().substring(0,2);
			//构造支付服务类
			QrPayService payService = payFactory.getServiceByOrderfix(orderPrefix);
			if(payService==null){
				return new Message(ErrorCode.PAY_CODE_ERROR.getCode(), ErrorCode.PAY_CODE_ERROR.getDes());
			}
			//调用条码支付服务
			payMsg = payService.doBarPay(barPayReq, merchant);
		}catch (ValidateException e) {
			payMsg = new Message(e.getErrCode(),e.getErrMsg());
		}catch (Exception e) {
			Log.error("支付出现异常："+e);
			return new Message(ErrorCode.SYSTEM_EXCEPTION.name(), ErrorCode.SYSTEM_EXCEPTION.getDes());
		}
		return payMsg;
	}
		
	/**
	 * @Description:扫码支付 
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年6月23日上午10:40:59 
	 * @return
	 */
	@RequestMapping(value = "/pay/unifiedorder",method=RequestMethod.POST)
	@ResponseBody
	public Object doScanPay(HttpServletRequest request, HttpServletResponse response){
		Map<String,String> reqParamMap = this.getRequestParams(request);
		Log.info("条码支付入口,接收参数：" + reqParamMap);
		Message payMsg = null;
		try {
			//类型转换
			ScanPayReqDto scanPayReq = (ScanPayReqDto) CommonUtil.convertMap(ScanPayReqDto.class, reqParamMap);
			//商品标题
			if(StringUtils.isEmpty(scanPayReq.getBody())){
				scanPayReq.setBody("scan pay");
			}
			if(!DataValidateUtil.valid(scanPayReq)){
				//基础信息验证不通过
				return new Message(ErrorCode.PARAM_FORMAT_ERROR.getCode(),ErrorCode.PARAM_FORMAT_ERROR.getDes());
			}
			//获取商户信息
			Merchant merchant = merchantService.queryMerchInfo(scanPayReq.getMerchant_num());
			if (merchant == null) {
				return new Message(ErrorCode.MERCHANT_NOT_EXIST.getCode(), ErrorCode.MERCHANT_NOT_EXIST.getDes());			
			}
			//签名验证
			if(!MD5.verify(reqParamMap, scanPayReq.getSign(), merchant.getMd5Key(), "utf-8")){
				return new Message(ErrorCode.PARAM_SIGN_ERROR.getCode(),ErrorCode.PARAM_SIGN_ERROR.getDes());
			}
			//支付流水是否存在验证
			PayTranLs payTranLs = payTranLsService.findTranLsByOrderNo(scanPayReq.getUser_order_no(), scanPayReq.getMerchant_num());
			if(payTranLs!=null){
				return new Message(ErrorCode.ORDER_EXIST.getCode(), ErrorCode.ORDER_EXIST.getDes());
			}
			int payChannel = Integer.valueOf(scanPayReq.getPay_channel());
			//构造支付服务类
			QrPayService payService = payFactory.getServiceByChannel(payChannel);
			if(payService==null){
				return new Message(ErrorCode.PAY_CODE_ERROR.getCode(), ErrorCode.PAY_CODE_ERROR.getDes());
			}
			//调用条码支付服务
			payMsg = payService.doScanPay(scanPayReq, merchant);
		}catch (ValidateException e) {
			payMsg = new Message(e.getErrCode(),e.getErrMsg());
		}catch (Exception e) {
			Log.error("支付出现异常："+e);
			return new Message(ErrorCode.SYSTEM_EXCEPTION.name(), ErrorCode.SYSTEM_EXCEPTION.getDes());
		}
		return payMsg;
	}
	
	/**
	 * @Description:微信小程序支付
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年6月23日上午10:40:59 
	 * @return
	 */
	@RequestMapping(value = "/pay/applet",method=RequestMethod.POST)
	@ResponseBody
	public Object doAppletPay(HttpServletRequest request, HttpServletResponse response){
		Map<String,String> reqParamMap = this.getRequestParams(request);
		Log.info("小程序支付入口,接收参数：" + reqParamMap);
		Message payMsg = null;
		try {
			AppletReqDto appletReq = (AppletReqDto) CommonUtil.convertMap(AppletReqDto.class, reqParamMap);
			if(StringUtils.isBlank(reqParamMap.get("body"))){
				appletReq.setBody("applet pay");
			}
			if(!DataValidateUtil.valid(appletReq)){
				//基础信息验证不通过
				return new Message(ErrorCode.PARAM_FORMAT_ERROR.getCode(),ErrorCode.PARAM_FORMAT_ERROR.getDes());
			}
			//获取商户信息
			Merchant merchant = merchantService.queryMerchInfo(appletReq.getMerchant_num());
			if (merchant == null) {
				return new Message(ErrorCode.MERCHANT_NOT_EXIST.getCode(), ErrorCode.MERCHANT_NOT_EXIST.getDes());			
			}
			//签名验证
			if(!MD5.verify(reqParamMap, appletReq.getSign(), merchant.getMd5Key(), "utf-8")){
				return new Message(ErrorCode.PARAM_SIGN_ERROR.getCode(),ErrorCode.PARAM_SIGN_ERROR.getDes());
			}
			//支付流水是否存在验证
			PayTranLs payTranLs = payTranLsService.findTranLsByOrderNo(appletReq.getUser_order_no(), appletReq.getMerchant_num());
			if(payTranLs!=null){
				return new Message(ErrorCode.ORDER_EXIST.getCode(), ErrorCode.ORDER_EXIST.getDes());
			}
			//获取微信支付业务类
			QrPayService payService = payFactory.getServiceByChannel(PayChannel.WECHAT.getValue());
			payMsg = payService.doAppletPay(appletReq, merchant);
		}catch (ValidateException e) {
			payMsg = new Message(e.getErrCode(),e.getErrMsg());
		}catch (Exception e) {
			Log.error("支付出现异常："+e);
			payMsg = new Message(ErrorCode.SYSTEM_EXCEPTION.name(), ErrorCode.SYSTEM_EXCEPTION.getDes());
		}
		return payMsg;
	}
	
	/**
	 * @Description:app支付 
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年6月23日上午10:40:59 
	 * @return
	 */
	@RequestMapping(value = "/pay/app",method=RequestMethod.POST)
	@ResponseBody
	public Object doAppPay(HttpServletRequest request, HttpServletResponse response){
		Map<String,String> reqParamMap = this.getRequestParams(request);
		Log.info("app支付入口,接收参数：" + reqParamMap);
		Message payMsg = null;
		try {
			//类型转换
			AppPayReqDto appPayReq = (AppPayReqDto) CommonUtil.convertMap(AppPayReqDto.class, reqParamMap);
			//商品标题
			if(StringUtils.isEmpty(appPayReq.getBody())){
				appPayReq.setBody("app pay");
			}
			//数据校验，如未通过，错误信息将在异常中抛出
			if(!DataValidateUtil.valid(appPayReq)){
				//基础信息验证不通过
				return new Message(ErrorCode.PARAM_FORMAT_ERROR.getCode(),ErrorCode.PARAM_FORMAT_ERROR.getDes());
			}
			//获取商户信息
			Merchant merchant = merchantService.queryMerchInfo(appPayReq.getMerchant_num());
			if (merchant == null) {
				return new Message(ErrorCode.MERCHANT_NOT_EXIST.getCode(), ErrorCode.MERCHANT_NOT_EXIST.getDes());			
			}
			//签名验证
			if(!MD5.verify(reqParamMap, appPayReq.getSign(), merchant.getMd5Key(), "utf-8")){
				return new Message(ErrorCode.PARAM_SIGN_ERROR.getCode(),ErrorCode.PARAM_SIGN_ERROR.getDes());
			}
			//支付流水是否存在验证
			PayTranLs payTranLs = payTranLsService.findTranLsByOrderNo(appPayReq.getUser_order_no(), appPayReq.getMerchant_num());
			if(payTranLs!=null){
				return new Message(ErrorCode.ORDER_EXIST.getCode(), ErrorCode.ORDER_EXIST.getDes());
			}
			int payChannel = Integer.valueOf(appPayReq.getPay_channel());
			if(merchant.getRouteId()==Route.PF_BANK.getValue()){
				//暂不支持，返回系统错误
				return new Message(ErrorCode.SYSTEM_EXCEPTION.getCode(), ErrorCode.SYSTEM_EXCEPTION.getDes());
			}else{
				//构造支付服务类
				QrPayService payService = payFactory.getServiceByChannel(payChannel);
				if(payService==null){
					return new Message(ErrorCode.PAY_CODE_ERROR.getCode(), ErrorCode.PAY_CODE_ERROR.getDes());
				}
				//调用条码支付服务
				payMsg = payService.doAppPay(appPayReq, merchant);
			}
		}catch (ValidateException e) {
			payMsg = new Message(e.getErrCode(),e.getErrMsg());
		} catch (Exception e) {
			Log.error("支付出现异常："+e);
			payMsg = new Message(ErrorCode.SYSTEM_EXCEPTION.name(), ErrorCode.SYSTEM_EXCEPTION.getDes());
		}
		return payMsg;
	}
		
	/**
	 * @Description: 订单查询
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年6月26日上午11:42:46 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/pay/order/query")
	@ResponseBody
	public Object doOrderQuery(HttpServletRequest request, HttpServletResponse response){
		Map<String,String> reqParamMap = this.getRequestParams(request);
		Log.info("订单查询入口,接收参数：" + reqParamMap);
		Message payMsg = null;
		try {
			OrderQrcReqDto orderQueryReq = (OrderQrcReqDto) CommonUtil.convertMap(OrderQrcReqDto.class, reqParamMap);
			//数据校验，如未通过，错误信息将在异常中抛出
			if(!DataValidateUtil.valid(orderQueryReq)){
				//基础信息验证不通过
				return new Message(ErrorCode.PARAM_FORMAT_ERROR.getCode(),ErrorCode.PARAM_FORMAT_ERROR.getDes());
			}
			//获取商户信息
			Merchant merchant = merchantService.queryMerchInfo(orderQueryReq.getMerchant_num());
			if (merchant == null) {
				return new Message(ErrorCode.MERCHANT_NOT_EXIST.getCode(), ErrorCode.MERCHANT_NOT_EXIST.getDes());			
			}
			//签名验证
			if(!MD5.verify(reqParamMap, reqParamMap.get("sign"), merchant.getMd5Key(), "utf-8")){
				return new Message(ErrorCode.PARAM_SIGN_ERROR.getCode(),ErrorCode.PARAM_SIGN_ERROR.getDes());
			}
			PayTranLs payTranLs=null;
			if(StringUtils.isNotEmpty(orderQueryReq.getUser_order_no())){
				payTranLs = payTranLsService.findTranLsByOrderNo(
						reqParamMap.get("user_order_no"), reqParamMap.get("merchant_num"));
			}else if(StringUtils.isNotEmpty(orderQueryReq.getTrace_num())){
				payTranLs  = payTranLsService.findTranLsByTransNum(reqParamMap.get("trace_num"));
			}else{
				return new Message(ErrorCode.PARAM_NULL_ERROR.getCode(),"订单号不能为空");
			}
			//验证支付流水是否存在
			if(payTranLs==null){
				return new Message(ErrorCode.ORDER_NOT_EXIST.getCode(), ErrorCode.ORDER_NOT_EXIST.getDes());
			}
			//判断本地订单状态
			if(PayStatus.PAYING.getValue()==payTranLs.getStatus() 
					|| PayStatus.UNPAY.getValue()==payTranLs.getStatus()
					|| PayStatus.REFUNDING.getValue()==payTranLs.getStatus() 
					|| PayStatus.UNKNOWN.getValue()==payTranLs.getStatus()){
				//此处逻辑待后续优化
				//判断支付渠道，如果为null则为一码付创建的预付单(用户没扫码时还不知道是微信还是支付宝)，直接返回本地订单状态
				if(payTranLs.getChannel()==null){
					BarPayRepDto rep = new BarPayRepDto(payTranLs);
					payMsg = new Message(rep);
				}else{
					//如果订单状态为支付中或退款中，发起渠道订单查询
					//构造支付服务类
					QrPayService payService = payFactory.getServiceByChannel(payTranLs.getChannel());
					if(payService==null){
						return new Message(ErrorCode.PAY_CODE_ERROR.getCode(), ErrorCode.PAY_CODE_ERROR.getDes());
					}
					payMsg = payService.doOrderQuery(payTranLs);
				}
			}else{
				//返回订单信息
				BarPayRepDto rep = new BarPayRepDto(payTranLs);
				payMsg = new Message(rep);
			}
		}catch (ValidateException e) {
			payMsg = new Message(e.getErrCode(),e.getErrMsg());
		}catch (Exception e) {
			Log.error("订单查询出现异常："+e);
			payMsg = new Message(ErrorCode.SYSTEM_EXCEPTION.name(), ErrorCode.SYSTEM_EXCEPTION.getDes());
		}
		return payMsg;
	}
	
	/**
	 * @Description: 订单关闭
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年6月26日上午11:46:07 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/pay/order/close",method=RequestMethod.POST)
	@ResponseBody
	public Object doOrderClose(HttpServletRequest request, HttpServletResponse response){
		Map<String,String> reqParamMap = this.getRequestParams(request);
		Log.info("订单关闭入口,接收参数：" + reqParamMap);
		Message payMsg = null;
		try {
			OrderQrcReqDto orderQueryReq = (OrderQrcReqDto) CommonUtil.convertMap(OrderQrcReqDto.class, reqParamMap);
			//数据校验，如未通过，错误信息将在异常中抛出
			if(!DataValidateUtil.valid(orderQueryReq)){
				//基础信息验证不通过
				return new Message(ErrorCode.PARAM_FORMAT_ERROR.getCode(),ErrorCode.PARAM_FORMAT_ERROR.getDes());
			}
			//获取商户信息
			Merchant merchant = merchantService.queryMerchInfo(orderQueryReq.getMerchant_num());
			if (merchant == null) {
				return new Message(ErrorCode.MERCHANT_NOT_EXIST.getCode(), ErrorCode.MERCHANT_NOT_EXIST.getDes());			
			}
			//签名验证
			if(!MD5.verify(reqParamMap, reqParamMap.get("sign"), merchant.getMd5Key(), "utf-8")){
				return new Message(ErrorCode.PARAM_SIGN_ERROR.getCode(),ErrorCode.PARAM_SIGN_ERROR.getDes());
			}
			PayTranLs payTranLs=null;
			if(StringUtils.isNotEmpty(orderQueryReq.getUser_order_no())){
				payTranLs = payTranLsService.findTranLsByOrderNo(
						orderQueryReq.getUser_order_no(), orderQueryReq.getMerchant_num());
			}else if(StringUtils.isNotEmpty(orderQueryReq.getTrace_num())){
				payTranLs  = payTranLsService.findTranLsByTransNum(orderQueryReq.getTrace_num());
			}else{
				return new Message(ErrorCode.PARAM_NULL_ERROR.getCode(),"订单号不能为空");
			}
			//验证支付流水是否存在
			if(payTranLs==null){
				return new Message(ErrorCode.ORDER_NOT_EXIST.getCode(), ErrorCode.ORDER_NOT_EXIST.getDes());
			}
			//判断本地订单状态
			if(PayStatus.CLOSED.getValue()==payTranLs.getStatus()){
				//状态为已关闭！
				payMsg = new Message(ErrorCode.ORDER_CLOSED.getCode(), ErrorCode.ORDER_CLOSED.getDes());
			}else if(PayStatus.PAYED.getValue()==payTranLs.getStatus()){
				//订单已支付，不能关闭
				payMsg = new Message(ErrorCode.ORDER_PAIED.getCode(), ErrorCode.ORDER_PAIED.getDes());
			}else{
				//构造支付服务类
				QrPayService payService = payFactory.getServiceByChannel(payTranLs.getChannel());
				if(payService==null){
					return new Message(ErrorCode.PAY_CODE_ERROR.getCode(), ErrorCode.PAY_CODE_ERROR.getDes());
				}
				payMsg = payService.doOrderClose(payTranLs);
			}
		}catch (ValidateException e) {
			payMsg = new Message(e.getErrCode(),e.getErrMsg());
		}catch (Exception e) {
			Log.error("订单关闭出现异常："+e);
			payMsg = new Message(ErrorCode.SYSTEM_EXCEPTION.name(), ErrorCode.SYSTEM_EXCEPTION.getDes());
		}
		return payMsg;
	}
	
	/**
	 * @Description: 订单撤销
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年6月26日上午11:46:07 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/pay/order/reverse",method=RequestMethod.POST)
	@ResponseBody
	public Object doOrderReverse(HttpServletRequest request, HttpServletResponse response){
		Map<String,String> reqParamMap = this.getRequestParams(request);
		Log.info("订单撤销入口,接收参数：" + reqParamMap);
		Message payMsg = null;
		try {
			OrderQrcReqDto orderQueryReq = (OrderQrcReqDto) CommonUtil.convertMap(OrderQrcReqDto.class, reqParamMap);
			//数据校验，如未通过，错误信息将在异常中抛出
			if(!DataValidateUtil.valid(orderQueryReq)){
				//基础信息验证不通过
				return new Message(ErrorCode.PARAM_FORMAT_ERROR.getCode(),ErrorCode.PARAM_FORMAT_ERROR.getDes());
			}
			//获取商户信息
			Merchant merchant = merchantService.queryMerchInfo(orderQueryReq.getMerchant_num());
			if (merchant == null) {
				return new Message(ErrorCode.MERCHANT_NOT_EXIST.getCode(), ErrorCode.MERCHANT_NOT_EXIST.getDes());			
			}
			//签名验证
			if(!MD5.verify(reqParamMap, reqParamMap.get("sign"), merchant.getMd5Key(), "utf-8")){
				return new Message(ErrorCode.PARAM_SIGN_ERROR.getCode(),ErrorCode.PARAM_SIGN_ERROR.getDes());
			}
			PayTranLs payTranLs=null;
			if(StringUtils.isNotEmpty(orderQueryReq.getUser_order_no())){
				payTranLs = payTranLsService.findTranLsByOrderNo(
						orderQueryReq.getUser_order_no(), orderQueryReq.getMerchant_num());
			}else if(StringUtils.isNotEmpty(reqParamMap.get("trace_num"))){
				payTranLs  = payTranLsService.findTranLsByTransNum(orderQueryReq.getTrace_num());
			}else{
				return new Message(ErrorCode.PARAM_NULL_ERROR.getCode(),"订单号不能为空");
			}
			//验证支付流水是否存在
			if(payTranLs==null){
				return new Message(ErrorCode.ORDER_NOT_EXIST.getCode(), ErrorCode.ORDER_NOT_EXIST.getDes());
			}
			//微信扫码支付不能撤销
			if(payTranLs.getSubChannel()==SubChannel.WECHAT_SCAN.getValue()){
				return new Message(ErrorCode.ORDER_NOT_REVERSE.getCode(), ErrorCode.ORDER_NOT_REVERSE.getDes());
			}
			//判断本地订单状态
			if(PayStatus.CANCEL.getValue()==payTranLs.getStatus()){
				//状态为已撤销
				return new Message(ErrorCode.ORDER_REVERSED.getCode(), ErrorCode.ORDER_REVERSED.getDes());
			}else{
				//如果订单状态为支付中或退款中，发起渠道订单查询
				//构造支付服务类
				QrPayService payService = payFactory.getServiceByChannel(payTranLs.getChannel());
				if(payService==null){
					return new Message(ErrorCode.PAY_CODE_ERROR.getCode(), ErrorCode.PAY_CODE_ERROR.getDes());
				}
				payMsg = payService.doOrderReverse(payTranLs);
			}
		}catch (ValidateException e) {
			payMsg = new Message(e.getErrCode(),e.getErrMsg());
		}catch (Exception e) {
			Log.error("订单撤销出现异常："+e);
			payMsg = new Message(ErrorCode.SYSTEM_EXCEPTION.name(), ErrorCode.SYSTEM_EXCEPTION.getDes());
		}
		return payMsg;
	}
	
	/**
	 * @Description: 退款申请
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年6月26日上午11:46:07 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/pay/order/refund",method=RequestMethod.POST)
	@ResponseBody
	public Object doOrderRefund(HttpServletRequest request, HttpServletResponse response){
		Map<String,String> reqParamMap = this.getRequestParams(request);
		Log.info("申请退款入口,接收参数：" + reqParamMap);
		Message payMsg = null;
		try {
			QrRefundReqDto refundReq = (QrRefundReqDto) CommonUtil.convertMap(QrRefundReqDto.class, reqParamMap);
			//数据校验，如未通过，错误信息将在异常中抛出
			if(!DataValidateUtil.valid(refundReq)){
				//基础信息验证不通过
				return new Message(ErrorCode.PARAM_FORMAT_ERROR.getCode(),ErrorCode.PARAM_FORMAT_ERROR.getDes());
			}
			//获取商户信息
			Merchant merchant = merchantService.queryMerchInfo(reqParamMap.get("merchant_num"));
			if (merchant == null) {
				return new Message(ErrorCode.MERCHANT_NOT_EXIST.getCode(), ErrorCode.MERCHANT_NOT_EXIST.getDes());			
			}
			//签名验证
			if(!MD5.verify(reqParamMap, reqParamMap.get("sign"), merchant.getMd5Key(), "utf-8")){
				return new Message(ErrorCode.PARAM_SIGN_ERROR.getCode(),ErrorCode.PARAM_SIGN_ERROR.getDes());
			}
			PayTranLs payTranLs=null;
			if(StringUtils.isNotEmpty(refundReq.getUser_order_no())){
				payTranLs = payTranLsService.findTranLsByOrderNo(
						refundReq.getUser_order_no(), refundReq.getMerchant_num());
			}else if(StringUtils.isNotEmpty(reqParamMap.get("trace_num"))){
				payTranLs  = payTranLsService.findTranLsByTransNum(refundReq.getTrace_num());
			}else{
				return new Message(ErrorCode.PARAM_NULL_ERROR.getCode(),"订单号不能为空");
			}
			//验证原支付流水是否存在
			if(payTranLs==null){
				return new Message(ErrorCode.ORDER_NOT_EXIST.getCode(), ErrorCode.ORDER_NOT_EXIST.getDes());
			}
			//构造支付服务类
			QrPayService payService = payFactory.getServiceByChannel(payTranLs.getChannel());
			if(payService==null){
				return new Message(ErrorCode.PAY_CODE_ERROR.getCode(), ErrorCode.PAY_CODE_ERROR.getDes());
			}
			payMsg = payService.doOrderRefund(refundReq, payTranLs);
		}catch (ValidateException e) {
			payMsg = new Message(e.getErrCode(),e.getErrMsg());
		}catch (Exception e) {
			Log.error("退款申请出现异常："+e);
			return new Message(ErrorCode.SYSTEM_EXCEPTION.name(), ErrorCode.SYSTEM_EXCEPTION.getDes());
		}
		return payMsg;
	}
	
	/**
	 * @Description: 退款查询
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年7月11日下午2:22:24 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/pay/refund/query",method=RequestMethod.POST)
	@ResponseBody
	public Object doRefundQuery(HttpServletRequest request, HttpServletResponse response){
		Map<String,String> reqParamMap = this.getRequestParams(request);
		Log.info("退款查询入口,接收参数：" + reqParamMap);
		Message payMsg = null;
		try {
			//非空参数验证
			String[] queryPara = {"sign","sign_type","merchant_num"};
			String valiString = CommonUtil.paramValidate(reqParamMap,queryPara);
			if(StringUtils.isNotEmpty(valiString)){
				return new Message(ErrorCode.PARAM_NULL_ERROR.getCode(),String.format(ErrorCode.PARAM_NULL_ERROR.getDes(),valiString));
			}
			//获取商户信息
			Merchant merchant = merchantService.queryMerchInfo(reqParamMap.get("merchant_num"));
			if (merchant == null) {
				return new Message(ErrorCode.MERCHANT_NOT_EXIST.getCode(), ErrorCode.MERCHANT_NOT_EXIST.getDes());			
			}	
			//签名验证
			if(!MD5.verify(reqParamMap, reqParamMap.get("sign"), merchant.getMd5Key(), "utf-8")){
				return new Message(ErrorCode.PARAM_SIGN_ERROR.getCode(),ErrorCode.PARAM_SIGN_ERROR.getDes());
			}
			RefundTranLs refundTranLs=null;
			if(StringUtils.isNotEmpty(reqParamMap.get("user_refund_no"))){
				refundTranLs = refundTranLsService.findRefundLsByOrderNo(
						reqParamMap.get("user_refund_no"), reqParamMap.get("merchant_num"));
			}else if(StringUtils.isNotEmpty(reqParamMap.get("trace_refund_num"))){
				refundTranLs  = refundTranLsService.findRefundLsByTransNum(reqParamMap.get("trace_refund_num"));
			}else{
				return new Message(ErrorCode.PARAM_NULL_ERROR.getCode(),"订单号不能为空");
			}
			//退款单号不存在
			if(refundTranLs==null){
				return new Message(ErrorCode.REFUND_ORDER_NOT_EXIST.getCode(), ErrorCode.REFUND_ORDER_NOT_EXIST.getDes());
			}
			//判断本地订单状态
			if(RefundStatus.REFUNDING.getValue()==refundTranLs.getStatus() || 
					RefundStatus.UNREFUND.getValue()==refundTranLs.getStatus()){
				//如果订单状态为发起退款或退款中
				//构造支付服务类
				QrPayService payService = payFactory.getServiceByChannel(refundTranLs.getChannel());
				if(payService==null){
					return new Message(ErrorCode.PAY_CODE_ERROR.getCode(), ErrorCode.PAY_CODE_ERROR.getDes());
				}
				payMsg = payService.doRefundQuery(refundTranLs);
			}else{
				//返回订单信息
				RefundQueryRepDto rep = new RefundQueryRepDto(refundTranLs);
				payMsg = new Message(rep);
			}
			
		}catch (Exception e) {
			Log.error("退款查询出现异常："+e);
			return new Message(ErrorCode.SYSTEM_EXCEPTION.name(), ErrorCode.SYSTEM_EXCEPTION.getDes());
		}
		return payMsg;
	}
	
	
	public static void main(String args[]){
		
		String filepath = "E:/usr/local/yunpos/cert/1297474301/apiclient_cert.p12";
		File file = new File(filepath);
		if(file.exists()){
			System.out.println("yes");
		}
	}
}
