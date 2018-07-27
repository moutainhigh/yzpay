package com.yunpay.serv.req.pay;
import com.yunpay.serv.validate.annotation.DataValidate;
import com.yunpay.serv.validate.enums.RegexType;
/**
 * 条码支付请求参数
 * 文件名称:     BarPayReqDto.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2018年1月5日上午9:32:50 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2018年1月5日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2018
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class BarPayReqDto {
	@DataValidate(allowNull=false,maxLength=20,regexType=RegexType.NOT_CHINESE)
	private String merchant_num ; 		//商户号
	@DataValidate(maxLength=30,regexType=RegexType.NOT_CHINESE)
	private String store_num;	//门店号
	@DataValidate(maxLength=20,regexType=RegexType.NOT_CHINESE)
	private String terminal_unique_no ; // 终端号
	@DataValidate(allowNull=false,maxLength=30,regexType=RegexType.NOT_CHINESE)
	private String user_order_no; 		//商户支付单号
	@DataValidate(allowNull=false,maxLength=50,regexType=RegexType.NOT_CHINESE)
	private String dynamic_id; 			//支付码
	@DataValidate(maxLength=100)
	private String body ; 				//订单标题
	@DataValidate(allowNull=false,maxLength=10,regexType=RegexType.DECIMAL)
	private String total_fee ; 			//支付金额（非空）
	@DataValidate(maxLength=10,regexType=RegexType.DECIMAL)
	private String discount_fee;		//优惠券优惠金额
	@DataValidate(maxLength=20)
	private String cashier_no;	//收银员编号
	@DataValidate(maxLength=100)
	private String coupon_code; 		// 核销码
	@DataValidate(maxLength=100)
	private String attach;				//附加信息
	@DataValidate(maxLength=500)
	private String notify_url;		//通知地址
	@DataValidate(allowNull=false,maxLength=10)
	private String sign_type;			//签名类型（非空）
	@DataValidate(allowNull=false,maxLength=50)
	private String sign;				//签名（非空）
	
	
	public String getMerchant_num() {
		return merchant_num;
	}
	public void setMerchant_num(String merchant_num) {
		this.merchant_num = merchant_num;
	}
	
	public String getStore_num() {
		return store_num;
	}
	public void setStore_num(String store_num) {
		this.store_num = store_num;
	}
	public String getNotify_url() {
		return notify_url;
	}
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	public String getTerminal_unique_no() {
		return terminal_unique_no;
	}
	public void setTerminal_unique_no(String terminal_unique_no) {
		this.terminal_unique_no = terminal_unique_no;
	}
	public String getUser_order_no() {
		return user_order_no;
	}
	public void setUser_order_no(String user_order_no) {
		this.user_order_no = user_order_no;
	}
	public String getDynamic_id() {
		return dynamic_id;
	}
	public void setDynamic_id(String dynamic_id) {
		this.dynamic_id = dynamic_id;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}
	public String getCashier_no() {
		return cashier_no;
	}
	public void setCashier_no(String cashier_no) {
		this.cashier_no = cashier_no;
	}
	public String getCoupon_code() {
		return coupon_code;
	}
	public void setCoupon_code(String coupon_code) {
		this.coupon_code = coupon_code;
	}
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
//	public String getClient_type() {
//		return client_type;
//	}
//	public void setClient_type(String client_type) {
//		this.client_type = client_type;
//	}
	public String getSign_type() {
		return sign_type;
	}
	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getDiscount_fee() {
		return discount_fee;
	}
	public void setDiscount_fee(String discount_fee) {
		this.discount_fee = discount_fee;
	}
	
	
	
	
}
