package com.yunpay.sdk.ctrl;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunpay.common.utils.CommonUtil;
import com.yunpay.common.utils.Log;
import com.yunpay.common.utils.EnumUtil.PayChannel;
import com.yunpay.common.utils.ResultEnum.ErrorCode;
import com.yunpay.serv.config.PayConfig;
import com.yunpay.serv.model.Merchant;
import com.yunpay.serv.rep.Message;
import com.yunpay.serv.req.pay.WapPayReqDto;
import com.yunpay.serv.route.PayFactory;
import com.yunpay.serv.service.busi.MerchantService;
import com.yunpay.serv.service.pay.QrPayService;

/**
 * 公众号或网页支付
 * 文件名称:     WapPayCtrl.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年7月20日下午2:30:04 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年7月20日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class WapPayCtrl extends BaseCtrl{
	
	@Autowired
	private MerchantService merchantService;
	@Autowired
	private PayFactory payFactory;
	
	/**
	 * @Description: 获取用户授权码code
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年7月19日下午5:18:46 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/pay/doAuthCode")
	public Object doAuthCode(HttpServletRequest request, HttpServletResponse response) throws IOException{
		Map<String,String> reqParamMap = this.getRequestParams(request);
		String[] barPara = {"return_url","merchant_num"};
		String valiString = CommonUtil.paramValidate(reqParamMap,barPara);
		if(StringUtils.isNotEmpty(valiString)){
			return new Message(ErrorCode.PARAM_NULL_ERROR.getCode(),
					String.format(ErrorCode.PARAM_NULL_ERROR.getDes(),valiString));
		}
		StringBuilder designStr = new StringBuilder();
		designStr.append(reqParamMap.get("merchant_num")).append("|");
		designStr.append(reqParamMap.get("return_url"));
		String requestUrl = PayConfig.WECHAT_AUTHCODE_URL.replace("APPID", PayConfig.WECHAT_APPID).
				replace("REDIRECT_URI", PayConfig.WECHAT_REDIRECT_SEND_URL).replace("STATE", designStr);
		Log.info("------------requestUrl="+requestUrl);
		response.sendRedirect(requestUrl);
		return null;
	}
	
	/**
	 * @Description: 接收授权码并转发至调用方平台
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年7月19日下午5:26:08 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/pay/recvAuthSend")
	public void recvAuthSend(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		Map<String,String> reqParamMap = this.getRequestParams(request);
		Log.info("微信授权码回调接收参数：" + reqParamMap);
		String designStr[] = reqParamMap.get("state").split("\\|");
		String merchantNum = designStr[0];
		String returnUrl = designStr[1];
		response.sendRedirect(returnUrl+"?code="+reqParamMap.get("code")+"&state="+merchantNum);		
	}
	
	/**
	 * @Description: 微信wap下单
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年7月4日下午2:34:01 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/pay/wechat/wappay",method=RequestMethod.POST)
	@ResponseBody
	public Object doWechatwap(HttpServletRequest request, HttpServletResponse response){
		Map<String,String> reqParamMap = this.getRequestParams(request);
		String[] wapParam = {"sign","sign_type","total_fee","merchant_num","user_order_no","open_id"};
		String valiString = CommonUtil.paramValidate(reqParamMap,wapParam);
		//判断必要参数是否为空
		if(StringUtils.isNotEmpty(valiString)){
			return new Message(ErrorCode.PARAM_NULL_ERROR.getCode(),
					String.format(ErrorCode.PARAM_NULL_ERROR.getDes(),valiString));
		}
		try {
			WapPayReqDto wapPayReq = (WapPayReqDto) CommonUtil.convertMap(WapPayReqDto.class, reqParamMap);
			//获取商户信息
			Merchant merchant = merchantService.queryMerchInfo(wapPayReq.getMerchant_num());
			if (merchant == null) {
				return new Message(ErrorCode.MERCHANT_NOT_EXIST.getCode(), ErrorCode.MERCHANT_NOT_EXIST.getDes());			
			}
			if(StringUtils.isBlank(reqParamMap.get("body"))){
				wapPayReq.setBody("wechat wappay");
			}
			//获取微信支付业务类
			QrPayService payService = payFactory.getServiceByChannel(PayChannel.WECHAT.getValue());
			return payService.doWapPay(wapPayReq, merchant);
		} catch (Exception e) {
			Log.error("支付出现异常："+e);
			return new Message(ErrorCode.SYSTEM_EXCEPTION.name(), ErrorCode.SYSTEM_EXCEPTION.getDes());
		}
	}
	
	/**
	 * @Description: 支付宝wap下单
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年7月4日下午2:34:15 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/pay/ali/wappay",method=RequestMethod.POST)
	@ResponseBody
	public Object doAliwap(HttpServletRequest request, HttpServletResponse response) throws IOException{
		Map<String,String> reqParamMap = this.getRequestParams(request);
		try {
			String[] wapParam = {"sign","sign_type","total_fee","merchant_num","user_order_no"};
			//判断必要参数是否为空
			String valiString = CommonUtil.paramValidate(reqParamMap,wapParam);
			if(StringUtils.isNotEmpty(valiString)){
				return new Message(ErrorCode.PARAM_NULL_ERROR.getCode(),
						String.format(ErrorCode.PARAM_NULL_ERROR.getDes(),valiString));
			}
			//对象转换
			WapPayReqDto wapPayReq = (WapPayReqDto) CommonUtil.convertMap(WapPayReqDto.class, reqParamMap);
			if(StringUtils.isBlank(reqParamMap.get("body"))){
				wapPayReq.setBody("alipay");
			}
			//获取商户信息
			Merchant merchant = merchantService.queryMerchInfo(wapPayReq.getMerchant_num());
			if (merchant == null) {
				return new Message(ErrorCode.MERCHANT_NOT_EXIST.getCode(), ErrorCode.MERCHANT_NOT_EXIST.getDes());
			}
			QrPayService payService = payFactory.getServiceByChannel(PayChannel.ALIPAY.getValue());
			return payService.doWapPay(wapPayReq, merchant);
		} catch (Exception e) {
			Log.error("支付出现异常："+e);
			return new Message(ErrorCode.SYSTEM_EXCEPTION.name(), ErrorCode.SYSTEM_EXCEPTION.getDes());
		}
	}
}
