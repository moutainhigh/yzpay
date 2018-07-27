package com.yunpay.ali.rep.member;

import com.yunpay.ali.rep.pay.AliQrRep;

public class AccessTokenRep  extends AliQrRep{
	private String accessToken;
	private String userId;
	
	public AccessTokenRep(){}
	
	public AccessTokenRep(String code,String msg,String sub_code,String sub_msg){
		super(code,msg,sub_code,sub_msg);
	}
	
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
