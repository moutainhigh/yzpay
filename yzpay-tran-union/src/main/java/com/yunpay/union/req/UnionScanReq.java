package com.yunpay.union.req;

import com.yunpay.union.config.SDKConfig;

/**
 * 文件名称:    UnionScanReq.java
 * 内容摘要:    银联扫码支付请求参数类
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年7月5日上午10:34:38 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年7月5日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class UnionScanReq extends UnionQrReq{
	//商户号码
	private String merId;
	//商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则
	private String orderId;
	//订单发送时间，取系统时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
	private String txnTime;
	//交易金额 单位为分，不能带小数点
	private String txnAmt;
	//回调通知地址
	private String backUrl;
	//终端号
	private String termId;
	
	public UnionScanReq(){
		
	}
	
	
	public UnionScanReq(String merId,String orderId,String txnTime,
			String txnAmt,String backUrl,String termId){
		setVersion(SDKConfig.getConfig().getVersion());
		setSignMethod(SDKConfig.getConfig().getSignMethod());
		setTxnType("01");
		setTxnSubType("07");
		this.merId = merId;
		this.orderId = orderId;
		this.txnTime = txnTime;
		this.txnAmt = txnAmt;
		this.backUrl = backUrl;
		this.termId = termId;
	}
	
	public String getMerId() {
		return merId;
	}
	public void setMerId(String merId) {
		this.merId = merId;
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
	public String getTxnAmt() {
		return txnAmt;
	}
	public void setTxnAmt(String txnAmt) {
		this.txnAmt = txnAmt;
	}
	
	public String getBackUrl() {
		return backUrl;
	}
	public void setBackUrl(String backUrl) {
		this.backUrl = backUrl;
	}


	public String getTermId() {
		return termId;
	}


	public void setTermId(String termId) {
		this.termId = termId;
	}
	
}
