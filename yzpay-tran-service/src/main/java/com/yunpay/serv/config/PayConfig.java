package com.yunpay.serv.config;

import java.util.ResourceBundle;

/**
 * 文件名称:    PayConfig.java
 * 内容摘要: 	      支付相关的配置信息，主要有支付回调地址、公众号配置等
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2018年3月1日下午4:56:25 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2018年3月1日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2018
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class PayConfig {
	//支付网关
	public static String PAY_GATEWAY = "";
	
	public static String WECHAT_APPID = "";
	public static String WECHAT_MERCHID="";
	public static String WECHAT_APISECRET="";
	public static String WECHAT_APPSECRET="";
	public static String WECHAT_CERTPATH="";
	public static String WECHAT_CERTPWD="";
	
	//微信相关扫码支付回调地址
	public static String WECHAT_SCAN_NOTIFY_URL = "";
	//wap支付回调地址
	public static String WECHAT_WAP_NOTIFY_URL = "";
	//静态一码付接收code
	public static String WECHAT_REDIRECT_URL = "";
	//动态一码付接收code
	public static String WECHAT_ONE_REDIRECT_URL="";
	//接收code并转发给调用方
	public static String WECHAT_REDIRECT_SEND_URL = "";
	//微信wap支付获取authcode地址
	public static String WECHAT_AUTHCODE_URL = "";
	//微信wap支付获取openid地址
	public static String WECHAT_OPENID_URL = "";
	
	
	//支付宝扫码支付回调地址
	public static String ALI_SCAN_NOTIFY_URL = "";
	//支付宝wap支付回调地址
	public static String ALI_WAP_NOTIFY_URL = "";
	//支付宝前端回调地址
	public static String ALI_WAP_RETURN_URL = "";
	
	//银联扫码支付回调地址
	public static String UNION_SCAN_NOTIFY_URL = "";
	//银联wap支付回调地址
	public static String UNION_WAP_NOTIFY_URL = "";
	//银联前端回调地址
	public static String UNION_WAP_RETURN_URL = "";
	
	//浦发银行渠道支付回调地址
	public static String PFBANK_PAY_NOTIFY_URL="";
	//浦发银行渠道退款回调地址
	public static String PFBANK_REFUND_NOTIFY_URL="";
	//浦发银行渠道撤销回调地址
	public static String PFBANK_REVERSE_NOTIFY_URL="";
	
	
	//卡券logo保存路径
	public static String CARD_IMG_PATH = "";
	
//	public static String PF_TRANS_URL= "";
//	public static String AGENT_MERCHANT_NO= "";
//	public static String AGENT_PUBLIC_KEY_PATH= "";
//	public static String AGENT_PRIVATE_KEY_PATH= "";
//	public static String AGENT_PRIVATE_KEY_PFX_PATH= "";
//	public static String AGENT_PRIVATE_KEY_PWD= "";
	
	
	
	static {
		//微信相关
		ResourceBundle rb = ResourceBundle.getBundle("payconfig/payconfig");
		PayConfig.PAY_GATEWAY = rb.getString("PAY_GATEWAY").trim();
		
		PayConfig.WECHAT_APPID = rb.getString("WECHAT_APPID").trim();
		PayConfig.WECHAT_MERCHID= rb.getString("WECHAT_MERCHID").trim();
		PayConfig.WECHAT_APISECRET= rb.getString("WECHAT_APISECRET").trim();
		PayConfig.WECHAT_APPSECRET= rb.getString("WECHAT_APPSECRET").trim();
		PayConfig.WECHAT_CERTPATH= rb.getString("WECHAT_CERTPATH").trim();
		PayConfig.WECHAT_CERTPWD= rb.getString("WECHAT_CERTPWD").trim();
		
		PayConfig.WECHAT_SCAN_NOTIFY_URL = PAY_GATEWAY + rb.getString("WECHAT_SCAN_NOTIFY_URL").trim();
		PayConfig.WECHAT_WAP_NOTIFY_URL = PAY_GATEWAY + rb.getString("WECHAT_WAP_NOTIFY_URL").trim();
		PayConfig.WECHAT_REDIRECT_URL = PAY_GATEWAY + rb.getString("WECHAT_REDIRECT_URL").trim();
		PayConfig.WECHAT_ONE_REDIRECT_URL = PAY_GATEWAY + rb.getString("WECHAT_ONE_REDIRECT_URL").trim();
		PayConfig.WECHAT_REDIRECT_SEND_URL = PAY_GATEWAY + rb.getString("WECHAT_REDIRECT_SEND_URL").trim();
		PayConfig.WECHAT_AUTHCODE_URL = rb.getString("WECHAT_AUTHCODE_URL").trim();
		PayConfig.WECHAT_OPENID_URL = rb.getString("WECHAT_OPENID_URL").trim();
		//支付宝相关
		PayConfig.ALI_SCAN_NOTIFY_URL = PAY_GATEWAY + rb.getString("ALI_SCAN_NOTIFY_URL").trim();
		PayConfig.ALI_WAP_NOTIFY_URL = PAY_GATEWAY + rb.getString("ALI_WAP_NOTIFY_URL").trim();
		PayConfig.ALI_WAP_RETURN_URL = PAY_GATEWAY + rb.getString("ALI_WAP_RETURN_URL").trim();
		
		PayConfig.UNION_SCAN_NOTIFY_URL = PAY_GATEWAY + rb.getString("UNION_SCAN_NOTIFY_URL").trim();
		PayConfig.UNION_WAP_NOTIFY_URL = PAY_GATEWAY + rb.getString("UNION_WAP_NOTIFY_URL").trim();
		PayConfig.UNION_WAP_RETURN_URL = PAY_GATEWAY + rb.getString("UNION_WAP_RETURN_URL").trim();
		
		PayConfig.PFBANK_PAY_NOTIFY_URL = PAY_GATEWAY + rb.getString("PFBANK_PAY_NOTIFY_URL").trim();
		PayConfig.PFBANK_REFUND_NOTIFY_URL = PAY_GATEWAY + rb.getString("PFBANK_REFUND_NOTIFY_URL").trim();
		PayConfig.PFBANK_REVERSE_NOTIFY_URL = PAY_GATEWAY + rb.getString("PFBANK_REVERSE_NOTIFY_URL").trim();
		
		PayConfig.CARD_IMG_PATH = rb.getString("CARD_IMG_PATH").trim();
		
//		PayConfig.PF_TRANS_URL=rb.getString("PF_TRANS_URL").trim();
//		PayConfig.AGENT_MERCHANT_NO= rb.getString("AGENT_MERCHANT_NO").trim();
//		PayConfig.AGENT_PUBLIC_KEY_PATH= rb.getString("AGENT_PUBLIC_KEY_PATH").trim();
//		PayConfig.AGENT_PRIVATE_KEY_PATH= rb.getString("AGENT_PRIVATE_KEY_PATH").trim();
//		PayConfig.AGENT_PRIVATE_KEY_PFX_PATH= rb.getString("AGENT_PRIVATE_KEY_PFX_PATH").trim();
//		PayConfig.AGENT_PRIVATE_KEY_PWD= rb.getString("AGENT_PRIVATE_KEY_PWD").trim();
	}
}
