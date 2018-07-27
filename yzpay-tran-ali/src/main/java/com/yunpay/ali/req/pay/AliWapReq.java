package com.yunpay.ali.req.pay;

/**
 * 文件名称:    AliWapReq.java
 * 内容摘要:    支付宝wap支付请求参数
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2018年3月1日下午4:43:55 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2018年3月1日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2018
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class AliWapReq extends AliQrReq{
	//后台结果通知地址(必需)
	private String notify_url;
	//前端结果通知地址
	private String return_url;
	//产品码(固定QUICK_WAP_WAY)
	private String product_code="QUICK_WAP_WAY";
	
	
	/**
	 * 构造支付宝扫码支付请求参数
	 * @param appId
	 * @param notify_url
	 * @param privateKey
	 * @param alipayPublicKey
	 * @param out_trade_no
	 * @param total_amount
	 * @param subject
	 * @param body
	 * @param terminal_id
	 * @param sys_service_provider_id
	 */
	public AliWapReq(String appId,String notify_url,String return_url,String privateKey,String alipayPublicKey,String out_trade_no,
			String total_amount,String subject,String body,String sys_service_provider_id){
		setAppId(appId);
		setNotify_url(notify_url);
		setReturn_url(return_url);
		setPrivateKey(privateKey);
		setAlipayPublicKey(alipayPublicKey);
		setOut_trade_no(out_trade_no);
		setTotal_amount(total_amount);
		setSubject(subject);
		setBody(body);
		setSys_service_provider_id(sys_service_provider_id);
	}


	public String getNotify_url() {
		return notify_url;
	}


	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}


	public String getReturn_url() {
		return return_url;
	}


	public void setReturn_url(String return_url) {
		this.return_url = return_url;
	}


	public String getProduct_code() {
		return product_code;
	}


	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}
	
	
}
