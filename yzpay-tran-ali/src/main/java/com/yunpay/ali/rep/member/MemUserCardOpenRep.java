package com.yunpay.ali.rep.member;

import com.yunpay.ali.rep.pay.AliQrRep;

public class MemUserCardOpenRep extends AliQrRep{
	
	private String bizCardNo;
	private String externalCardNo;
	
	public MemUserCardOpenRep(){}
	
	public MemUserCardOpenRep(String code,String msg,String sub_code,String sub_msg){
		super(code,msg,sub_code,sub_msg);
	}
	
	public String getBizCardNo() {
		return bizCardNo;
	}
	public void setBizCardNo(String bizCardNo) {
		this.bizCardNo = bizCardNo;
	}
	public String getExternalCardNo() {
		return externalCardNo;
	}
	public void setExternalCardNo(String externalCardNo) {
		this.externalCardNo = externalCardNo;
	}
	
}
