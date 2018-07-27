package com.yunpay.ali.req.member;

import com.yunpay.ali.req.card.CardCommonReq;

public class AccessTokenReq extends CardCommonReq{
	
	public AccessTokenReq(String appId,String privateKey,String alipayPublicKey,String authCode){
		setApp_id(appId);
		setPrivateKey(privateKey);
		setAlipayPublicKey(alipayPublicKey);
		setAuthCode(authCode);
	}
	
	private String authCode;

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	
	
}
