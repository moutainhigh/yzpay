package com.yunpay.pfbank.rep;

/**
 * 订单查询返回参数
 * 文件名称:     PfQueryRep.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年11月21日下午4:16:15 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年11月21日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class PfQueryRep extends PfBaseRep{
	private String transactionId;
	private String orderId;
	private String timeEnd;
	private String origRespCode;
	private String origRespDesc;
	private String tranAmt;
	private String refundAmt;
	
	public PfQueryRep(){}
	
	public PfQueryRep(String respCode,String repsDesc){
		super.setRespCode(respCode);
		super.setRespDesc(repsDesc);
	}
	
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getTimeEnd() {
		return timeEnd;
	}
	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}
	public String getOrigRespCode() {
		return origRespCode;
	}
	public void setOrigRespCode(String origRespCode) {
		this.origRespCode = origRespCode;
	}
	public String getOrigRespDesc() {
		return origRespDesc;
	}
	public void setOrigRespDesc(String origRespDesc) {
		this.origRespDesc = origRespDesc;
	}
	public String getTranAmt() {
		return tranAmt;
	}
	public void setTranAmt(String tranAmt) {
		this.tranAmt = tranAmt;
	}
	public String getRefundAmt() {
		return refundAmt;
	}
	public void setRefundAmt(String refundAmt) {
		this.refundAmt = refundAmt;
	}
	
	
}
