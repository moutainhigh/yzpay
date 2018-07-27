package com.yunpay.serv.req.pay;

import com.yunpay.serv.validate.annotation.DataValidate;
import com.yunpay.serv.validate.enums.RegexType;

/**
 * 商户报备接收参数
 * 文件名称:     PfMerBbReqDto.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年11月22日上午10:52:31 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年11月22日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class PfMerBbReqDto {
	//报备渠道(WX,ALIPAY)
	@DataValidate(allowNull=false,maxLength=10)
	private String pay_way;
	//渠道商户号
	@DataValidate(allowNull=false,maxLength=30)
	private String mer_no;
	//商户名
	@DataValidate(allowNull=false,maxLength=30)
	private String merchant_name;
	//行业类别
	@DataValidate(allowNull=false,regexType=RegexType.NUMBER,maxLength=8)
	private String business;
	//商户简称
	@DataValidate(allowNull=false,maxLength=30)
	private String name_alias;
	//联系人
	@DataValidate(allowNull=false,maxLength=30)
	private String contact;
	//手机号码
	@DataValidate(allowNull=false,maxLength=30)
	private String contact_phone;
	//联系邮箱
	@DataValidate(allowNull=false,maxLength=30)
	private String contact_email;
	//商户备注
	@DataValidate(allowNull=false,maxLength=30)
	private String merchant_remark;
	//联系电话
	@DataValidate(allowNull=false,maxLength=30)
	private String service_phone;
	
	
	/*******支付宝报备专属部分**********/
	//营业执照号
	@DataValidate(maxLength=20)
	private String business_license;
	//营业执照类型
	@DataValidate(maxLength=32)
	private String business_license_type;
	
	//身份证号
	@DataValidate(maxLength=32)
	private String idcard_no;
	//法人
	@DataValidate(maxLength=32)
	private String legal_person;
	//法人类型
	@DataValidate(maxLength=50)
	private String name_type;
	
	
	//省
	@DataValidate(maxLength=10)
	private String province_code;
	//市
	@DataValidate(maxLength=10)
	private String city_code;
	//区
	@DataValidate(maxLength=10)
	private String district_code;
	//详细地址
	@DataValidate(maxLength=50)
	private String address;
	
	//银行账号
	@DataValidate(maxLength=20)
	private String card_no_cipher;
	//开户名
	@DataValidate(maxLength=50)
	private String card_name;
	
	
	public String getPay_way() {
		return pay_way;
	}
	public void setPay_way(String pay_way) {
		this.pay_way = pay_way;
	}
	public String getMer_no() {
		return mer_no;
	}
	public void setMer_no(String mer_no) {
		this.mer_no = mer_no;
	}
	public String getMerchant_name() {
		return merchant_name;
	}
	public void setMerchant_name(String merchant_name) {
		this.merchant_name = merchant_name;
	}
	public String getBusiness() {
		return business;
	}
	public void setBusiness(String business) {
		this.business = business;
	}
	public String getName_alias() {
		return name_alias;
	}
	public void setName_alias(String name_alias) {
		this.name_alias = name_alias;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getContact_phone() {
		return contact_phone;
	}
	public void setContact_phone(String contact_phone) {
		this.contact_phone = contact_phone;
	}
	public String getContact_email() {
		return contact_email;
	}
	public void setContact_email(String contact_email) {
		this.contact_email = contact_email;
	}
	public String getMerchant_remark() {
		return merchant_remark;
	}
	public void setMerchant_remark(String merchant_remark) {
		this.merchant_remark = merchant_remark;
	}
	public String getService_phone() {
		return service_phone;
	}
	public void setService_phone(String service_phone) {
		this.service_phone = service_phone;
	}
	public String getBusiness_license() {
		return business_license;
	}
	public void setBusiness_license(String business_license) {
		this.business_license = business_license;
	}
	public String getBusiness_license_type() {
		return business_license_type;
	}
	public void setBusiness_license_type(String business_license_type) {
		this.business_license_type = business_license_type;
	}
	public String getIdcard_no() {
		return idcard_no;
	}
	public void setIdcard_no(String idcard_no) {
		this.idcard_no = idcard_no;
	}
	public String getLegal_person() {
		return legal_person;
	}
	public void setLegal_person(String legal_person) {
		this.legal_person = legal_person;
	}
	public String getName_type() {
		return name_type;
	}
	public void setName_type(String name_type) {
		this.name_type = name_type;
	}
	public String getProvince_code() {
		return province_code;
	}
	public void setProvince_code(String province_code) {
		this.province_code = province_code;
	}
	public String getCity_code() {
		return city_code;
	}
	public void setCity_code(String city_code) {
		this.city_code = city_code;
	}
	public String getDistrict_code() {
		return district_code;
	}
	public void setDistrict_code(String district_code) {
		this.district_code = district_code;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
	
	
	
	
}
