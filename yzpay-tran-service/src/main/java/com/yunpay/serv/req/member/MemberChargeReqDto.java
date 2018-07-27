package com.yunpay.serv.req.member;

import com.yunpay.serv.validate.annotation.DataValidate;
import com.yunpay.serv.validate.enums.RegexType;

/**
 * 会员充值请求参数
 * 文件名称:     MemberChargeReqDto.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年9月20日上午10:13:46 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年9月20日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class MemberChargeReqDto {
	
	@DataValidate(allowNull=false,regexType=RegexType.NUMBER,maxLength=20)
	private String merchant_num ; 		//商户号（非空）
	@DataValidate(allowNull=false,maxLength=30)
	private String member_card_code;		//会员卡号
	@DataValidate(allowNull=false,regexType=RegexType.DECIMAL)
	private String recharge_amt ; 		//充值金额
	@DataValidate(allowNull=false,regexType=RegexType.NUMBER,maxLength=30)
	private String recharge_order_no; 		//充值单号
	@DataValidate(maxLength=20)
	private String terminal_unique_no ; // 终端号
	@DataValidate(allowNull=false,maxLength=10)
	private String client_type ; 		// 客户端类型(PC、Web、POS、DLL、SDK)
	@DataValidate(maxLength=10)
	private String title ; 				//订单标题
	@DataValidate(regexType=RegexType.NUMBER,maxLength=20)
	private String cashier_no;			//收银员编号
	
	@DataValidate(allowNull=false)
	private String sign;				//签名（非空）
	@DataValidate(allowNull=false)
	private String sign_type;			//签名类型（非空）
	
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
	public String getRecharge_amt() {
		return recharge_amt;
	}
	public void setRecharge_amt(String recharge_amt) {
		this.recharge_amt = recharge_amt;
	}
	public String getMerchant_num() {
		return merchant_num;
	}
	public void setMerchant_num(String merchant_num) {
		this.merchant_num = merchant_num;
	}
	public String getRecharge_order_no() {
		return recharge_order_no;
	}
	public void setRecharge_order_no(String recharge_order_no) {
		this.recharge_order_no = recharge_order_no;
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
	public String getMember_card_code() {
		return member_card_code;
	}
	public void setMember_card_code(String member_card_code) {
		this.member_card_code = member_card_code;
	}
	
	
}
