package com.yunpay.pfbank.req;

/**
 * 商户报件查询
 * 文件名称:     PfMerchBjQryReq.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年11月21日下午3:57:07 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年11月21日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class PfMerchBjQryReq extends PfBaseReq{
	private String merNo;
	private String name;
	private String nameAlias;
	
	/**
	 * 构造商户报件查询接口请求参数
	 * @param agentId  代理商Id
	 * @param merNo    浦发平台商户号
	 * @param name	       商户名 
	 * @param nameAlias 商户简称
	 */
	public PfMerchBjQryReq(String agentId,String merNo,String name,String nameAlias){
		this.setAgentId(agentId);
		this.setMerNo(merNo);
		this.setName(name);
		this.setNameAlias(nameAlias);
	}
	
	public String getMerNo() {
		return merNo;
	}
	public void setMerNo(String merNo) {
		this.merNo = merNo;
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
	
	
}
