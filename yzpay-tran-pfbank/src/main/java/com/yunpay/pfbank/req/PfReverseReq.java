package com.yunpay.pfbank.req;

/**
 * 订单撤销请求参数
 * 文件名称:     PfReverseReq.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年11月21日下午4:07:33 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年11月21日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class PfReverseReq extends PfBaseReq{
	//原订单号
	private String origOrderNo;
	
	/**
	 * 构造撤销请求参数
	 * @param agentId
	 * @param merNo
	 * @param orderDate
	 * @param orderNo
	 * @param origOrderNo
	 * @param transAmt
	 * @param notifyUrl
	 */
	public PfReverseReq(String agentId,String merNo,String orderDate,String orderNo,
		String origOrderNo,String transAmt,String notifyUrl){
		super.setAgentId(agentId);
		super.setMerNo(merNo);
		super.setOrderDate(orderDate);
		super.setOrderNo(orderNo);
		this.setOrigOrderNo(origOrderNo);
		super.setTransAmt(transAmt);
		super.setNotifyUrl(notifyUrl);
		super.setReturnUrl(notifyUrl);
	}

	public String getOrigOrderNo() {
		return origOrderNo;
	}

	public void setOrigOrderNo(String origOrderNo) {
		this.origOrderNo = origOrderNo;
	}
	
	
}
