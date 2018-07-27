package com.yunpay.pfbank.req;

/**
 * 商户报备查询
 * 文件名称:     PfMerchBbQryReq.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年11月21日下午3:56:07 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年11月21日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class PfMerchBbQryReq extends PfBaseReq{
	
	private String payWay;
	private String merNo;
	private String subMchId;
	
	/**
	 * 构造微信报备查询接口请求参数
	 * @param agentId
	 * @param payWay  支付渠道(WX/ALIPAY)
	 * @param merNo
	 * @param subMchId
	 */
	public PfMerchBbQryReq(String agentId,String payWay,String merNo,String subMchId){
		this.setAgentId(agentId);
		this.setMerNo(merNo);
		this.setPayWay(payWay);
		this.setSubMchId(subMchId);
	}
	
	public String getPayWay() {
		return payWay;
	}
	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}
	public String getMerNo() {
		return merNo;
	}
	public void setMerNo(String merNo) {
		this.merNo = merNo;
	}
	public String getSubMchId() {
		return subMchId;
	}
	public void setSubMchId(String subMchId) {
		this.subMchId = subMchId;
	}
	
	
}
