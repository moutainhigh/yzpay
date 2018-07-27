package com.yunpay.serv.req.pay;
public class WapPayReqDto {
	
	private String sign;				//签名（非空）
	private String sign_type;			//签名类型（非空）
	private String total_fee ; 			//支付金额（非空）
	private String discount_fee;		//优惠券优惠金额
	private String merchant_num ; 		//商户号（非空）
	private String store_num;			//门店号
	private String user_order_no; 		//商户支付单号（非空）
	private String coupon_code; 		// 核销码
	private String body ; 				//订单标题
	private String attach;				//附加信息
	//微信授权码
	private String auth_code;
	//openId
	private String open_id;
	//异步通知地址
	private String notify_url;
	//前端跳转地址(支付宝用到)
	private String return_url;
	private String pay_channel;	//支付渠道
	private String cashier_no;	//收银员编号
	private String terminal_unique_no ; // 终端号
	private String client_type ; 		// 客户端类型（PC、Web、POS、DLL、SDK
	
	
	public String getStore_num() {
		return store_num;
	}
	public void setStore_num(String store_num) {
		this.store_num = store_num;
	}
	public String getPay_channel() {
		return pay_channel;
	}
	public void setPay_channel(String pay_channel) {
		this.pay_channel = pay_channel;
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
	public String getCoupon_code() {
		return coupon_code;
	}
	public void setCoupon_code(String coupon_code) {
		this.coupon_code = coupon_code;
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
	public String getReturn_url() {
		return return_url;
	}
	public void setReturn_url(String return_url) {
		this.return_url = return_url;
	}
	public String getAuth_code() {
		return auth_code;
	}
	public void setAuth_code(String auth_code) {
		this.auth_code = auth_code;
	}
	public String getCashier_no() {
		return cashier_no;
	}
	public void setCashier_no(String cashier_no) {
		this.cashier_no = cashier_no;
	}
	public String getTerminal_unique_no() {
		return terminal_unique_no;
	}
	public void setTerminal_unique_no(String terminal_unique_no) {
		this.terminal_unique_no = terminal_unique_no;
	}
	public String getClient_type() {
		return client_type;
	}
	public void setClient_type(String client_type) {
		this.client_type = client_type;
	}
	public String getDiscount_fee() {
		return discount_fee;
	}
	public void setDiscount_fee(String discount_fee) {
		this.discount_fee = discount_fee;
	}
	
	
}
