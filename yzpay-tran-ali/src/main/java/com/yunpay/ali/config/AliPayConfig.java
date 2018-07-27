package com.yunpay.ali.config;

import java.util.ResourceBundle;

/**
 * 文件名称:    AliPayConfig.java
 * 内容摘要: 	     支付宝支付全局配置
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2018年3月1日下午2:04:24 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2018年3月1日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2018
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class AliPayConfig {
	public static String BAR_PAY_API="";
	public static String SCAN_PAY_API="";
	
	static {
		ResourceBundle rb = ResourceBundle.getBundle("payurl/alipay");
		AliPayConfig.BAR_PAY_API = rb.getString("BAR_PAY_API").trim();
		AliPayConfig.SCAN_PAY_API = rb.getString("SCAN_PAY_API").trim();
	}
}
