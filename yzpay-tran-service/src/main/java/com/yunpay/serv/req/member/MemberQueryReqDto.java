package com.yunpay.serv.req.member;

import com.yunpay.serv.validate.annotation.DataValidate;
import com.yunpay.serv.validate.enums.RegexType;

public class MemberQueryReqDto {
	@DataValidate(allowNull=false,regexType=RegexType.NUMBER,maxLength=20)
	private String merchant_num ; 		//商户号（非空）
	@DataValidate(allowNull=false,maxLength=30)
	private String member_card_code;		//会员卡号
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
	
	
}
