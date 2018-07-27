package com.yunpay.serv.req.pay;

import com.yunpay.serv.validate.annotation.DataValidate;
import com.yunpay.serv.validate.enums.RegexType;

/**
 * 微信小程序支付请求参数
 * 文件名称:     AppletReqDto.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年8月15日下午4:13:57 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年8月15日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class AppletReqDto {
	@DataValidate(allowNull=false,maxLength=20,regexType=RegexType.NOT_CHINESE)
	private String merchant_num ; 		//商户号
	@DataValidate(allowNull=false,maxLength=30,regexType=RegexType.NOT_CHINESE)
	private String user_order_no; 		//商户支付单号
	@DataValidate(allowNull=false,maxLength=50)
	private String open_id; 		//微信授权码
	@DataValidate(maxLength=100)
	private String body ; 				//订单标题
	@DataValidate(allowNull=false,maxLength=10,regexType=RegexType.DECIMAL)
	private String total_fee ; 			//支付金额（非空）
	@DataValidate(maxLength=100)
	private String attach;				//附加信息
	@DataValidate(maxLength=200)
	private String notify_url;
	@DataValidate(allowNull=false,maxLength=10)
	private String sign_type;			//签名类型（非空）
	@DataValidate(allowNull=false,maxLength=50)
	private String sign;				//签名（非空）
	
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getSign_type() {
		return sign_type;
	}
	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}
	public String getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}
	public String getMerchant_num() {
		return merchant_num;
	}
	public void setMerchant_num(String merchant_num) {
		this.merchant_num = merchant_num;
	}
	public String getUser_order_no() {
		return user_order_no;
	}
	public void setUser_order_no(String user_order_no) {
		this.user_order_no = user_order_no;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	public String getOpen_id() {
		return open_id;
	}
	public void setOpen_id(String open_id) {
		this.open_id = open_id;
	}
	public String getNotify_url() {
		return notify_url;
	}
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	
	
}
