package com.yunpay.serv.req.pay;

/**
 * 二维码支付请求参数
 * 文件名称:     QrPayReqDto.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年6月21日上午11:54:30 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年6月21日     Zeng Dongcheng   1.0     新建
 * 2017年8月30日     Zeng Dongcheng   1.1	  增加cashier_no属性
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class QrPayReqDto{
	private String sign;				//签名（非空）
	private String sign_type;			//签名类型（非空）
	private String total_fee ; 			//支付金额（非空）
	private String dynamic_id; 			//支付码（非空）
	private String merchant_num ; 		//商户号（非空）
	private String user_order_no; 		//商户支付单号（非空）
	private String terminal_unique_no ; // 终端号
	private String coupon_code; 		// 核销码
	private String client_type ; 		// 客户端类型（PC、Web、POS、DLL、SDK
	private String body ; 				//订单标题
	private String attach;				//附加信息
	private String pay_channel;	//支付渠道
	private String notify_url;
	
	private String cashier_no;	//收银员编号
	
	
	
	public String toString(){
		StringBuilder str = new StringBuilder();
		str.append("body=").append(body);
		str.append("&client_type=").append(client_type);
		str.append("&coupon_code=").append(coupon_code);
		str.append("&dynamic_id=").append(dynamic_id);
		str.append("&merchant_num=").append(merchant_num);
		str.append("&terminal_unique_no=").append(terminal_unique_no);
		str.append("&total_fee=").append(total_fee);
		str.append("&user_order_no=").append(user_order_no);
		str.append("&sign=").append(sign);
		str.append("&sign_type=").append(sign_type);
		str.append("&attach=").append(attach);
		return str.toString();
	}
	

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
	public String getDynamic_id() {
		return dynamic_id;
	}
	public void setDynamic_id(String dynamic_id) {
		this.dynamic_id = dynamic_id;
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
	public String getTerminal_unique_no() {
		return terminal_unique_no;
	}
	public void setTerminal_unique_no(String terminal_unique_no) {
		this.terminal_unique_no = terminal_unique_no;
	}
	public String getCoupon_code() {
		return coupon_code;
	}
	public void setCoupon_code(String coupon_code) {
		this.coupon_code = coupon_code;
	}
	public String getClient_type() {
		return client_type;
	}
	public void setClient_type(String client_type) {
		this.client_type = client_type;
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

	public String getPay_channel() {
		return pay_channel;
	}
	public void setPay_channel(String pay_channel) {
		this.pay_channel = pay_channel;
	}
	public String getNotify_url() {
		return notify_url;
	}
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	public String getCashier_no() {
		return cashier_no;
	}
	public void setCashier_no(String cashier_no) {
		this.cashier_no = cashier_no;
	}
	
	
}
