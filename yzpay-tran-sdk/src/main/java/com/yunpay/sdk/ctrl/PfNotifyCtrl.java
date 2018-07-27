package com.yunpay.sdk.ctrl;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yunpay.common.utils.Log;

/**
 * 浦发渠道异步通知接收类
 * 文件名称:     PfNotifyCtrl.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年10月12日上午11:32:00 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年10月12日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
@Controller
public class PfNotifyCtrl extends BaseCtrl{
	
	/**
	 * @Description:支付回结果通知
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年10月12日下午1:46:23 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/pay/pfbank/qrpay/notify")
	public void pfPayNotify(HttpServletRequest request, HttpServletResponse response) {
		try {
			// 获取支付宝POST过来反馈信息
			Map<String, String> recvParam = getRequestParams(request);
			//参数为空，返回空
			if(recvParam==null ||recvParam.isEmpty()){
				Log.error("浦发支付通知信息接收为空");
				super.Print(response, "fail");
				return;
			}
			Log.info("------pfbank pay notify param："+recvParam.toString());
			super.Print(response, "success");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @Description: 退款结果通知
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年10月12日下午1:46:35 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/pay/pfbank/refund/notify")
	public void pfRefundNotify(HttpServletRequest request, HttpServletResponse response) {
		try {
			// 获取支付宝POST过来反馈信息
			Map<String, String> recvParam = getRequestParams(request);
			//参数为空，返回空
			if(recvParam==null ||recvParam.isEmpty()){
				Log.error("浦发退款通知信息接收为空");
				super.Print(response, "fail");
				return;
			}
			Log.info("------pfbank refund notify param："+recvParam.toString());
			super.Print(response, "success");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @Description:撤销结果通知 
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年10月12日下午1:46:53 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/pay/pfbank/reverse/notify")
	public void pfReverseNotify(HttpServletRequest request, HttpServletResponse response) {
		try {
			// 获取支付宝POST过来反馈信息
			Map<String, String> recvParam = getRequestParams(request);
			//参数为空，返回空
			if(recvParam==null ||recvParam.isEmpty()){
				Log.error("浦发支付撤销通知信息接收为空");
				super.Print(response, "fail");
				return;
			}
			Log.info("------pfbank reverse notify param："+recvParam.toString());
			super.Print(response, "success");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
