package com.yunpay.permission.controller.mobile.sdk;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 
 * 文件名称:
 * 内容摘要: 支付宝授权回调
 * @author:   duan zhang quan
 * @version:  1.0  
 * @Date:     2017年4月14日上午9:56:12 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年4月14日     duan zhang quan   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
@Controller  
@RequestMapping("/alipayAuth")
public class AlipayAuthRedirect {

	@RequestMapping("/redirect")
	public String authRedirect(HttpServletRequest req,HttpServletResponse rep){
		return "authRedirect/alipayAuthRedirect";
	}
}
