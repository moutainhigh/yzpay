package com.yunpay.serv.model;

public class MemberCardTemplate {
	
	private Integer id;

    private String merchantNo;

    private String card_type;
    
    private String base_info_id;

    private String card_id;

    private Integer status;
    
    private String alipass_card_id;
    
    private String appId;  //支付宝appID
    
    private Integer defaultSend;   //0：不投放，1投放  支付宝设置关注默认投放

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

	public String getCard_type() {
		return card_type;
	}

	public void setCard_type(String card_type) {
		this.card_type = card_type;
	}
	
	public String getCard_id() {
		return card_id;
	}

	public void setCard_id(String card_id) {
		this.card_id = card_id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getAlipass_card_id() {
		return alipass_card_id;
	}

	public void setAlipass_card_id(String alipass_card_id) {
		this.alipass_card_id = alipass_card_id;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public Integer getDefaultSend() {
		return defaultSend;
	}

	public void setDefaultSend(Integer defaultSend) {
		this.defaultSend = defaultSend;
	}

	public String getBase_info_id() {
		return base_info_id;
	}

	public void setBase_info_id(String base_info_id) {
		this.base_info_id = base_info_id;
	}
    
    
    

}
