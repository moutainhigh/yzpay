package com.yunpay.ali.rep.member;

import com.yunpay.ali.rep.pay.AliQrRep;

public class MemUserFieldQryRep  extends AliQrRep{
	private String infos;
	
	public MemUserFieldQryRep(){}
	
	public MemUserFieldQryRep(String code,String msg,String sub_code,String sub_msg){
		super(code,msg,sub_code,sub_msg);
	}

	public String getInfos() {
		return infos;
	}

	public void setInfos(String infos) {
		this.infos = infos;
	}
	
	
}
