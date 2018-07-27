package com.yunpay.pfbank.req;

import java.util.HashMap;
import java.util.Map;
import com.alibaba.fastjson.JSONObject;

/**
 * 商户报备请求参数
 * 文件名称:     PfMerchBbReq.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年11月21日下午3:56:36 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年11月21日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class PfMerchBbReq {
	
	private String requestNo;
	private String version;
	private String transId;
	private String agentId;
	private String payWay;
	private String merNo;
	private String subMerchantName;
	private String business;
	private String subMerchantShorName;
	private String contact;
	private String contactPhone;
	private String contactEmail;
	private String merchantRemark;
	private String servicePhone;
	
	/*****支付宝必传项******/
	private String businessLicenseType;
	private String businessLicense;
	private String contactInfo; 
	private String addressInfo;
	private String bankCardInfo;
	
	/**
	 * 构造微信报备接口参数
	 * @param agentId
	 * @param merNo  平台商户好
	 * @param subMerchantName 商户名称
	 * @param business  经营类目
	 * @param subMerchantShortName 商户简称
	 * @param contact  联系人
	 * @param contactPhone 手机号码
	 * @param contactEmail 联系邮箱
	 * @param merchantRemark 商户备注
	 * @param servicePhone 联系电话
	 */
	public PfMerchBbReq(String agentId,String merNo,String subMerchantName,String business,
			String subMerchantShortName,String contact,String contactPhone,String contactEmail,
			String merchantRemark,String servicePhone){
		this.setAgentId(agentId);
		this.setPayWay("WX");
		this.setMerNo(merNo);
		this.setSubMerchantName(subMerchantName);
		this.setBusiness(business);
		this.setSubMerchantShorName(subMerchantShortName);
		this.setContact(contact);
		this.setContactPhone(contactPhone);
		this.setContactEmail(contactEmail);
		this.setMerchantRemark(merchantRemark);
		this.setServicePhone(servicePhone);
	}
	
	/**
	 * 构造支付宝报备接口参数
	 * @param agentId
	 * @param merNo
	 * @param subMerchantName
	 * @param business
	 * @param subMerchantShortName
	 * @param contact
	 * @param contactPhone
	 * @param contactEmail
	 * @param merchantRemark
	 * @param servicePhone
	 * @param businessLicenseType 营业执照类别
	 * @param businessLicense   营业执照号码
	 */
	public PfMerchBbReq(String agentId,String merNo,String subMerchantName,String business,
			String subMerchantShortName,String contact,String contactPhone,String contactEmail,
			String merchantRemark,String servicePhone,String businessLicenseType,String businessLicense){
		this.setAgentId(agentId);
		this.setPayWay("ALIPAY");
		this.setMerNo(merNo);
		this.setSubMerchantName(subMerchantName);
		this.setBusiness(business);
		this.setSubMerchantShorName(subMerchantShortName);
		this.setContact(contact);
		this.setContactPhone(contactPhone);
		this.setContactEmail(contactEmail);
		this.setMerchantRemark(merchantRemark);
		this.setServicePhone(servicePhone);
		this.setBusinessLicense(businessLicense);
		this.setBusinessLicenseType(businessLicenseType);
	}
	
	
	public PfMerchBbReq(){
		
	}

	public String getRequestNo() {
		return requestNo;
	}

	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getTransId() {
		return transId;
	}

	public void setTransId(String transId) {
		this.transId = transId;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getPayWay() {
		return payWay;
	}

	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}

	public String getMerNo() {
		return merNo;
	}

	public void setMerNo(String merNo) {
		this.merNo = merNo;
	}

	public String getSubMerchantName() {
		return subMerchantName;
	}

	public void setSubMerchantName(String subMerchantName) {
		this.subMerchantName = subMerchantName;
	}

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	public String getSubMerchantShorName() {
		return subMerchantShorName;
	}

	public void setSubMerchantShorName(String subMerchantShorName) {
		this.subMerchantShorName = subMerchantShorName;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getMerchantRemark() {
		return merchantRemark;
	}

	public void setMerchantRemark(String merchantRemark) {
		this.merchantRemark = merchantRemark;
	}

	public String getServicePhone() {
		return servicePhone;
	}

	public void setServicePhone(String servicePhone) {
		this.servicePhone = servicePhone;
	}

	public String getBusinessLicenseType() {
		return businessLicenseType;
	}

	public void setBusinessLicenseType(String businessLicenseType) {
		this.businessLicenseType = businessLicenseType;
	}

	public String getBusinessLicense() {
		return businessLicense;
	}

	public void setBusinessLicense(String businessLicense) {
		this.businessLicense = businessLicense;
	}

	public String getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(String name,String email,String type,String id_card_no) {
		Map<String,String> contact = new HashMap<>();
        contact.put("name", name);//姓名  必填
//        contact.put("phone", phone);//电话 选填
//        contact.put("mobile", phone);//手机 选填
        contact.put("email", email);//邮件 选填
        contact.put("type", type);//联系人类型 取值范围：LEGAL_PERSON：法人；CONTROLLER：实际控制人；AGENT：代理人；OTHER：其他   必填
        contact.put("id_card_no", id_card_no);//身份证号 必填
		this.contactInfo = JSONObject.toJSONString(contact);
	}

	public String getAddressInfo() {
		return addressInfo;
	}

	public void setAddressInfo(String province_code,String city_code,String district_code,String address) {
		Map<String,String> addressMap = new HashMap<>();
		addressMap.put("province_code", province_code);//省级编码 必填
		addressMap.put("city_code", city_code);//市级编码 必填
		addressMap.put("district_code", district_code);//区县编码 必填
		addressMap.put("address", address);//详细地址 必填
		this.addressInfo = JSONObject.toJSONString(addressMap);
	}

	public String getBankCardInfo() {
		return bankCardInfo;
	}

	public void setBankCardInfo(String card_no,String card_name) {
		Map<String,String> cardInfo = new HashMap<>();
        cardInfo.put("card_no", card_no);//卡号 必填
        cardInfo.put("card_name", card_name);//户名 必填
        this.bankCardInfo = JSONObject.toJSONString(cardInfo);
	}

	
}
