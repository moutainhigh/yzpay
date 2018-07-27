package com.yunpay.serv.req.pay;

import com.yunpay.serv.validate.annotation.DataValidate;
import com.yunpay.serv.validate.enums.RegexType;

/**
 * 商户报件接收参数
 * 文件名称:     PfMerBjReqDto.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年11月22日上午10:52:16 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年11月22日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class PfMerBjReqDto {
	//商户名称
	@DataValidate(allowNull=false,maxLength=30)
	private String merchant_name;
	//商户简称
	@DataValidate(allowNull=false,maxLength=30)
	private String name_alias;
	//所属行业类别
	@DataValidate(allowNull=false,maxLength=32)
	private String mcc_value;
	//法人姓名
	@DataValidate(allowNull=false,maxLength=30)
	private String legal_person;
	//法人身份证
	@DataValidate(allowNull=false,maxLength=30)
	private String idcard_no;
	//手机号
	@DataValidate(allowNull=false,maxLength=30)
	private String mobile;
	//邮箱
	@DataValidate(allowNull=false,maxLength=30)
	private String email;
	//社会信用代码
	@DataValidate(allowNull=false,maxLength=20)
	private String reg_no;
	//注册资金(单位分)
	@DataValidate(allowNull=false,regexType=RegexType.NUMBER,maxLength=10)
	private String reg_money;
	//详细地址
	@DataValidate(allowNull=false,maxLength=50)
	private String register_address;
	//银行账号
	@DataValidate(allowNull=false,maxLength=19)
	private String card_no_cipher;
	//开户名
	@DataValidate(allowNull=false,maxLength=50)
	private String card_name;
	//联行号
	@DataValidate(allowNull=false,maxLength=12)
	private String card_bank_no;
	//营业执照过期日期
	@DataValidate(allowNull=false,maxLength=10)
	private String expire_time;
	//账户类型(0:对私 1:对公)
	@DataValidate(allowNull=false,regexType=RegexType.NUMBER,maxLength=1)
	private String is_company;
	//经营类目
	@DataValidate(allowNull=false,maxLength=50)
	private String business;
	
	
	public String getMerchant_name() {
		return merchant_name;
	}
	public void setMerchant_name(String merchant_name) {
		this.merchant_name = merchant_name;
	}
	public String getName_alias() {
		return name_alias;
	}
	public void setName_alias(String name_alias) {
		this.name_alias = name_alias;
	}
	public String getMcc_value() {
		return mcc_value;
	}
	public void setMcc_value(String mcc_value) {
		this.mcc_value = mcc_value;
	}
	public String getLegal_person() {
		return legal_person;
	}
	public void setLegal_person(String legal_person) {
		this.legal_person = legal_person;
	}
	public String getIdcard_no() {
		return idcard_no;
	}
	public void setIdcard_no(String idcard_no) {
		this.idcard_no = idcard_no;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getReg_no() {
		return reg_no;
	}
	public void setReg_no(String reg_no) {
		this.reg_no = reg_no;
	}
	
	public String getReg_money() {
		return reg_money;
	}
	public void setReg_money(String reg_money) {
		this.reg_money = reg_money;
	}
	public String getRegister_address() {
		return register_address;
	}
	public void setRegister_address(String register_address) {
		this.register_address = register_address;
	}
	public String getCard_no_cipher() {
		return card_no_cipher;
	}
	public void setCard_no_cipher(String card_no_cipher) {
		this.card_no_cipher = card_no_cipher;
	}
	public String getCard_name() {
		return card_name;
	}
	public void setCard_name(String card_name) {
		this.card_name = card_name;
	}
	public String getCard_bank_no() {
		return card_bank_no;
	}
	public void setCard_bank_no(String card_bank_no) {
		this.card_bank_no = card_bank_no;
	}
	public String getExpire_time() {
		return expire_time;
	}
	public void setExpire_time(String expire_time) {
		this.expire_time = expire_time;
	}
	
	public String getIs_company() {
		return is_company;
	}
	public void setIs_company(String is_company) {
		this.is_company = is_company;
	}
	public String getBusiness() {
		return business;
	}
	public void setBusiness(String business) {
		this.business = business;
	}
	
	
	
	
}
