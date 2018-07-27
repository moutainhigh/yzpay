package com.yunpay.pfbank.req;


/**
 * 订单退款请求参数
 * 文件名称:     PfRefundReq.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年11月21日下午4:02:36 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年11月21日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class PfRefundReq extends PfBaseReq{
	//原订单号
	private String origOrderNo;
	//原订单日期
	private String origOrderDate;
	//退货原因
	private String refundReson;
	
	/**
	 * 构造退款请求参数
	 * @param agentId
	 * @param merNo
	 * @param orderDate
	 * @param orderNo
	 * @param origOrderDate
	 * @param origOrderNo
	 * @param transAmt
	 * @param refundReason
	 * @param notifyUrl
	 */
	public PfRefundReq(String agentId,String merNo,String orderDate,String orderNo,
			String origOrderDate,String origOrderNo,String transAmt,String refundReason,
			String notifyUrl){
		super.setAgentId(agentId);
		super.setMerNo(merNo);
		super.setOrderDate(orderDate);
		super.setOrderNo(orderNo);
		this.setOrigOrderDate(origOrderDate);
		this.setOrigOrderNo(origOrderNo);
		super.setTransAmt(transAmt);
		this.setRefundReson(refundReason);
		super.setNotifyUrl(notifyUrl);
		super.setReturnUrl(notifyUrl);
	}
	
	public String getOrigOrderNo() {
		return origOrderNo;
	}
	public void setOrigOrderNo(String origOrderNo) {
		this.origOrderNo = origOrderNo;
	}
	public String getOrigOrderDate() {
		return origOrderDate;
	}
	public void setOrigOrderDate(String origOrderDate) {
		this.origOrderDate = origOrderDate;
	}
	public String getRefundReson() {
		return refundReson;
	}
	public void setRefundReson(String refundReson) {
		this.refundReson = refundReson;
	}
	
	
	
}
