package com.yunpay.pfbank.rep;

/**
 * 公众号支付返回参数
 * 文件名称:     PfWikiPayRep.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年11月21日下午4:16:31 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年11月21日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class PfWikiPayRep extends PfBaseRep{
	private String payInfo;
	private String formfield;
	
	public PfWikiPayRep(){}
	
	public PfWikiPayRep(String respCode,String repsDesc){
		super.setRespCode(respCode);
		super.setRespDesc(repsDesc);
	}
	
	public String getPayInfo() {
		return payInfo;
	}
	public void setPayInfo(String payInfo) {
		this.payInfo = payInfo;
	}
	public String getFormfield() {
		return formfield;
	}
	public void setFormfield(String formfield) {
		this.formfield = formfield;
	}
	
	
}
