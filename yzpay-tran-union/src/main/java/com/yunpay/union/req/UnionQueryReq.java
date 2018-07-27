package com.yunpay.union.req;

import com.yunpay.union.config.SDKConfig;

/**
 * 文件名称:    UnionQueryReq.java
 * 内容摘要:    银联订单查询接口请求参数封装类
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2018年3月1日上午11:15:34 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2018年3月1日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2018
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class UnionQueryReq extends UnionQrReq{
	//商户号码
	private String merId ;
	//
	private String queryId;
	//商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则
	private String orderId;
	//订单发送时间，取系统时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
	private String txnTime;
	
	public UnionQueryReq(){
		
	}
	
	/**
	 * 根据queryId查询交易流水
	 * @param merId
	 * @param queryId
	 */
	public UnionQueryReq(String merId,String queryId){
		setVersion(SDKConfig.getConfig().getVersion());
		setSignMethod(SDKConfig.getConfig().getSignMethod());
		setTxnType("01");
		setTxnSubType("02");
		setMerId(merId);
		setQueryId(queryId);
	}
	
	/**
	 * 根据orderId+txnTime查询交易流水
	 * @param merId
	 * @param orderId
	 * @param txnTime
	 */
	public UnionQueryReq(String merId,String orderId,String txnTime){
		setVersion(SDKConfig.getConfig().getVersion());
		setSignMethod(SDKConfig.getConfig().getSignMethod());
		setTxnType("00");
		setTxnSubType("00");
		setMerId(merId);
		setOrderId(orderId);
		setTxnTime(txnTime);
	}
	
	public String getMerId() {
		return merId;
	}
	public void setMerId(String merId) {
		this.merId = merId;
	}
	public String getQueryId() {
		return queryId;
	}
	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getTxnTime() {
		return txnTime;
	}
	public void setTxnTime(String txnTime) {
		this.txnTime = txnTime;
	}
	
	
}
