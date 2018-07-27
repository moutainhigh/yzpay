package com.yunpay.serv.model;

public class PfbankConfig {
	private Integer id;
	//平台商户号
	private String merchantNo;
	//浦发渠道商户号
	private String channelMerNo;
	//浦发渠道微信商户号
	private String wxMchId;
	//浦发渠道支付宝商户号
	private String aliMchId;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMerchantNo() {
		return merchantNo;
	}
	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}
	public String getChannelMerNo() {
		return channelMerNo;
	}
	public void setChannelMerNo(String channelMerNo) {
		this.channelMerNo = channelMerNo;
	}
	public String getWxMchId() {
		return wxMchId;
	}
	public void setWxMchId(String wxMchId) {
		this.wxMchId = wxMchId;
	}
	public String getAliMchId() {
		return aliMchId;
	}
	public void setAliMchId(String aliMchId) {
		this.aliMchId = aliMchId;
	}
	
	
}
