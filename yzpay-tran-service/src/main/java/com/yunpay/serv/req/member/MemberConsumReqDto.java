package com.yunpay.serv.req.member;

import com.yunpay.serv.validate.annotation.DataValidate;
import com.yunpay.serv.validate.enums.RegexType;

/**
 * 会员消费请求参数
 * 文件名称:     MemberConsumReqDto.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年9月21日上午9:34:08 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年9月21日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class MemberConsumReqDto {
	@DataValidate(allowNull=false,regexType=RegexType.NUMBER,maxLength=20)
	private String merchant_num ; 		//商户号（非空）
	@DataValidate(allowNull=false,maxLength=30)
	private String member_card_code;		//会员卡号
	@DataValidate(allowNull=false,maxLength=10,regexType=RegexType.DECIMAL)
	private String tran_amt ; 		//消费金额
	@DataValidate(maxLength=10,regexType=RegexType.DECIMAL)
	private String dis_amt;			//优惠金额（招手猫优惠券）
	@DataValidate(allowNull=false,maxLength=8,regexType=RegexType.NUMBER)
	private String use_bonus;	//本次消费使用的积分数
	@DataValidate(allowNull=false,regexType=RegexType.NUMBER,maxLength=30)
	private String user_order_no; 		//消费订单号
	@DataValidate(maxLength=20)
	private String terminal_unique_no ; // 终端号
	private String title ; 				//订单标题
	@DataValidate(regexType=RegexType.NUMBER,maxLength=20)
	private String cashier_no;			//收银员编号
	@DataValidate(maxLength=100)
	private String coupon_code; 		// 核销码
	@DataValidate(maxLength=300)
	private String notify_url;		//通知地址
	@DataValidate(allowNull=false,maxLength=10)
	private String client_type ; 		// 客户端类型(PC、Web、POS、DLL、SDK)
	@DataValidate(allowNull=false)
	private String sign;				//签名（非空）
	@DataValidate(allowNull=false)
	private String sign_type;			//签名类型（非空）
	
	public String getMerchant_num() {
		return merchant_num;
	}
	public void setMerchant_num(String merchant_num) {
		this.merchant_num = merchant_num;
	}
	public String getMember_card_code() {
		return member_card_code;
	}
	public void setMember_card_code(String member_card_code) {
		this.member_card_code = member_card_code;
	}
	public String getTran_amt() {
		return tran_amt;
	}
	public void setTran_amt(String tran_amt) {
		this.tran_amt = tran_amt;
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCashier_no() {
		return cashier_no;
	}
	public void setCashier_no(String cashier_no) {
		this.cashier_no = cashier_no;
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
	public String getClient_type() {
		return client_type;
	}
	public void setClient_type(String client_type) {
		this.client_type = client_type;
	}
	public String getUse_bonus() {
		return use_bonus;
	}
	public void setUse_bonus(String use_bonus) {
		this.use_bonus = use_bonus;
	}
	public String getCoupon_code() {
		return coupon_code;
	}
	public void setCoupon_code(String coupon_code) {
		this.coupon_code = coupon_code;
	}
	public String getNotify_url() {
		return notify_url;
	}
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	public String getDis_amt() {
		return dis_amt;
	}
	public void setDis_amt(String dis_amt) {
		this.dis_amt = dis_amt;
	}
	
	
}
