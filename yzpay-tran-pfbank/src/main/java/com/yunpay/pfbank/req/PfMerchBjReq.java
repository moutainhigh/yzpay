package com.yunpay.pfbank.req;


/**
 * 商户 报件请求参数
 * 文件名称:     PfMerchAddReq.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年11月17日上午10:09:08 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年11月17日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class PfMerchBjReq {
	private String requestNo;
	private String version="V1.1";
	private String transId="25";
	private String agentId;
	private String parentSpCode;
	private String name;
	private String nameAlias;
	private String mccValue;
	private String legalPerson;
	private String idcardNo;
	private String mobile;
	private String email;
	private String cityId;
	private String registerAddress;
	private String regNo;
	private String regMoney;
	private String regAddress;
	private String spIcp;
	private String remarks;
	private String rateSchema;
	private String cardNoCipher;
	private String cardName;
	private String cardBankNo;
	private String expireTime;
	private String isCompany;
	private String business;
	private String signature;
	
	/**
	 * 构造商户报件接口请求参数
	 * @param agentId
	 * @param name
	 * @param nameAlias
	 * @param mccValue
	 * @param legalPerson
	 * @param idcardNo
	 * @param mobile
	 * @param email
	 * @param regNo
	 * @param regMoney
	 * @param registerAddress
	 * @param cardNoCipher
	 * @param cardName
	 * @param cardBankNo
	 * @param expireTime
	 * @param isCompany
	 * @param business
	 */
	public PfMerchBjReq(String agentId,String name,String nameAlias,
			String mccValue,String legalPerson,String idcardNo,String mobile,
			String email,String regNo,String regMoney,String registerAddress,String cardNoCipher,
			String cardName,String cardBankNo,String expireTime,String isCompany,String business){
		this.setAgentId(agentId);
		this.setName(name);
		this.setNameAlias(nameAlias);
		this.setMccValue(mccValue);
		this.setLegalPerson(legalPerson);
		this.setIdcardNo(idcardNo);
		this.setMobile(mobile);
		this.setEmail(email);
		this.setRegNo(regNo);
		this.setRegMoney(regMoney);
		this.setRegisterAddress(registerAddress);
		this.setCardNoCipher(cardNoCipher);
		this.setCardName(cardName);
		this.setCardBankNo(cardBankNo);
		this.setExpireTime(expireTime);
		this.setIsCompany(isCompany);
		this.setBusiness(business);
		
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
	public String getParentSpCode() {
		return parentSpCode;
	}
	public void setParentSpCode(String parentSpCode) {
		this.parentSpCode = parentSpCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNameAlias() {
		return nameAlias;
	}
	public void setNameAlias(String nameAlias) {
		this.nameAlias = nameAlias;
	}
	public String getMccValue() {
		return mccValue;
	}
	public void setMccValue(String mccValue) {
		this.mccValue = mccValue;
	}
	
	public String getLegalPerson() {
		return legalPerson;
	}


	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}


	public String getIdcardNo() {
		return idcardNo;
	}
	public void setIdcardNo(String idcardNo) {
		this.idcardNo = idcardNo;
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
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getRegisterAddress() {
		return registerAddress;
	}
	public void setRegisterAddress(String registerAddress) {
		this.registerAddress = registerAddress;
	}
	public String getRegNo() {
		return regNo;
	}
	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}
	public String getRegMoney() {
		return regMoney;
	}
	public void setRegMoney(String regMoney) {
		this.regMoney = regMoney;
	}
	public String getRegAddress() {
		return regAddress;
	}
	public void setRegAddress(String regAddress) {
		this.regAddress = regAddress;
	}
	public String getSpIcp() {
		return spIcp;
	}
	public void setSpIcp(String spIcp) {
		this.spIcp = spIcp;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getRateSchema() {
		return rateSchema;
	}
	public void setRateSchema(String rateSchema) {
		this.rateSchema = rateSchema;
	}
	public String getCardNoCipher() {
		return cardNoCipher;
	}
	public void setCardNoCipher(String cardNoCipher) {
		this.cardNoCipher = cardNoCipher;
	}
	public String getCardName() {
		return cardName;
	}
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	public String getCardBankNo() {
		return cardBankNo;
	}
	public void setCardBankNo(String cardBankNo) {
		this.cardBankNo = cardBankNo;
	}
	public String getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}
	public String getIsCompany() {
		return isCompany;
	}
	public void setIsCompany(String isCompany) {
		this.isCompany = isCompany;
	}
	public String getBusiness() {
		return business;
	}
	public void setBusiness(String business) {
		this.business = business;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	
	
	
}
