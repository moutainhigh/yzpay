package com.yunpay.sdk.ctrl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yunpay.common.utils.CommonUtil;
import com.yunpay.common.utils.Log;
import com.yunpay.serv.service.pay.PayNotifyService;


/**
 * 支付业务异步通知接收类
 * 文件名称:     NotifyCtrl.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年6月27日下午4:42:54 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年6月27日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
@Controller
public class PayNotifyCtrl extends BaseCtrl{
	
	private static final String WXOK = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
	
	private static final String WXERR = "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[FAIL]]></return_msg></xml>";
	
	@Autowired
	private PayNotifyService payNotifyService;
	
	/**
	 * @Description: 微信扫码支付异步结果通知
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年6月27日下午6:10:23 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/pay/wechat/scanpay/notify")
	public void wechatScanNotify(HttpServletRequest request, HttpServletResponse response) {
		Log.info("recv wechat scanpay notify begin------");
		try {
			String recvInfo = CommonUtil.inputStreamToString(request.getInputStream());
			Log.info("------wechat scanpay recv info:"+recvInfo);
			if(payNotifyService.wechatScanNotify(recvInfo)){
				super.Print(response, WXOK);
			}else{
				super.Print(response, WXERR);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @Description: 微信wap支付异步通知
	 * 虽当前处理流程同微信扫码支付异步通知流程一致，
	 * 但还是另外提供一个入口，主要考虑后续可能有业务上的变化
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年7月4日下午2:47:58 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/pay/wechat/wappay/notify")
	public void wechatWapNotify(HttpServletRequest request, HttpServletResponse response) {
		Log.info("recv wechat wappay notify begin------");
		try {
			String recvInfo = CommonUtil.inputStreamToString(request.getInputStream());
			Log.info("------wechat wappay recv info:"+recvInfo);
			if(payNotifyService.wechatScanNotify(recvInfo)){
				super.Print(response, WXOK);
			}else{
				super.Print(response, WXERR);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @Description: 微信退款回调通知
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月8日下午3:06:22 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/pay/wechat/refund/notify")
	public void wechatRefundNotify(HttpServletRequest request, HttpServletResponse response){
		Log.info("recv wechat refund notify begin------");
		try {
			String recvInfo = CommonUtil.inputStreamToString(request.getInputStream());
			Log.info("------wechat refund recv info:"+recvInfo);
			if(payNotifyService.wechatRefundNotify(recvInfo)){
				super.Print(response, WXOK);
			}else{
				super.Print(response, WXERR);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @Description: 支付宝扫码支付异步结果通知
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年7月4日下午2:29:08 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/pay/alipay/scanpay/notify")
	public void aliScanNotify(HttpServletRequest request, HttpServletResponse response){
		Log.info("recv alipay scanpay notify begin------");
		try {
			// 获取支付宝POST过来反馈信息
			Map<String, String> recvParam = getAllRequestParam(request,null);
			//参数为空，返回空
			if(recvParam==null ||recvParam.isEmpty()){
				Log.error("支付宝scan支付异步通知返回信息为空");
				return;
			}
			Log.info("------alipay scanpay notify param："+recvParam.toString());
			if(payNotifyService.alipayScanNotify(recvParam)){
				super.Print(response, "success");
			}else{
				super.Print(response, "fail");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @Description: 支付宝wap支付异步结果通知
	 * 虽当前处理流程同支付宝扫码支付异步通知流程一致，
	 * 但还是另外提供一个入口，主要考虑后续可能有业务上的变化
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年7月4日下午2:29:08 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/pay/alipay/wappay/notify")
	public void aliWapNotify(HttpServletRequest request, HttpServletResponse response){
		Log.info("recv alipay wappay notify begin------");
		try {
			// 获取支付宝POST过来反馈信息
			Map<String, String> recvParam = getAllRequestParam(request,null);
			//参数为空，返回空
			if(recvParam==null ||recvParam.isEmpty()){
				Log.error("支付宝wap支付异步通知返回信息为空");
				return;
			}
			Log.info("------alipay wappay notify param："+recvParam.toString());
			if(payNotifyService.alipayScanNotify(recvParam)){
				super.Print(response, "success");
			}else{
				super.Print(response, "fail");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @Description: 银联扫码支付异步通知
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年7月7日上午8:43:26 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/pay/union/scanpay/notify")
	public void unionScanNotify(HttpServletRequest request, HttpServletResponse response){
		// 获取支付宝POST过来反馈信息
		try {
			Map<String, String> recvParam = getAllRequestParam(request,null);
			//参数为空，返回空
			if(recvParam==null ||recvParam.isEmpty()){
				Log.error("银联scan支付异步通知返回信息为空");
				return;
			}
			Log.info("------union scanpay notify param："+recvParam.toString());
			if(payNotifyService.unionScanNotify(recvParam)){
				super.Print(response, "ok");
			}else{
				super.Print(response, "fail");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 获取请求参数中所有的信息
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static Map<String, String> getAllRequestParam(final HttpServletRequest request,String encode) 
			throws UnsupportedEncodingException {
		Map<String, String> res = new HashMap<String, String>();
		Enumeration<?> temp = request.getParameterNames();
		if (null != temp) {
			while (temp.hasMoreElements()) {
				String en = (String) temp.nextElement();
				String value = request.getParameter(en);
				//在报文上送时，如果字段的值为空，则不上送<下面的处理为在获取所有参数数据时，判断若值为空，则删除这个字段>
				//System.out.println("ServletUtil类247行  temp数据的键=="+en+"     值==="+value);
				if (null == value || "".equals(value)) {
					continue;
				}
				if("UTF-8".equals(encode)){
					value = new String (value.getBytes("ISO-8859-1"),"UTF-8");
				}else if("GBK".equals(encode)){
					value = new String (value.getBytes("ISO-8859-1"),"GBK");
				}
				res.put(en, value);
			}
		}
		return res;
	}
}
