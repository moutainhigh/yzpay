package com.yunpay.pfbank.rep;

/**
 * 报件查询返回参数
 * 文件名称:     PfMerchBjQryRep.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年11月21日下午4:16:07 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年11月21日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class PfMerchBjQryRep {
	private String requestNo;
	private String version;
	private String transId;
	private String agentId;
	private String merchantList;
	private String signature;
	private String respCode;
	private String respDesc;
	
	public PfMerchBjQryRep(){}
	
	public PfMerchBjQryRep(String respCode,String repsDesc){
		this.setRespCode(respCode);
		this.setRespDesc(repsDesc);
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
	public String getMerchantList() {
		return merchantList;
	}
	public void setMerchantList(String merchantList) {
		this.merchantList = merchantList;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getRespDesc() {
		return respDesc;
	}
	public void setRespDesc(String respDesc) {
		this.respDesc = respDesc;
	}
	
	
	
}
