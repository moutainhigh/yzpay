package com.yunpay.union.rep;

/**
 * 文件名称:    UnionQrRep.java
 * 内容摘要:    银联二维码支付返回结果基类
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年7月5日下午1:37:06 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年7月5日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class UnionQrRep {
	//版本号 全渠道默认值
	private String version;
	//签名
	private String signature;
	//字符集编码 可以使用UTF-8,GBK两种方式
	private String encoding;
	//签名方法
	private String signMethod;
	//交易类型 01:消费
	private String txnType = "01";
	//交易子类 07：申请消费二维码
	private String txnSubType = "07" ;
	//填写000000
	private String bizType = "000000";
	//渠道类型 08手机
	private String channelType = "08";
	//接入类型，商户接入填0 ，不需修改（0：直连商户， 1： 收单机构 2：平台商户）
	private String accessType = "0";
	//境内商户固定 156 人民币
	private String currencyCode = "156";
	//附加信息
	private String reqReserved;
	//响应码
	private String respCode;
	//应答信息
	private String respMsg;
	
	public UnionQrRep(){
		
	}
	
	public UnionQrRep(String respCode,String respMsg){
		this.respCode = respCode;
		this.respMsg = respMsg;
	}
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getEncoding() {
		return encoding;
	}
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	public String getSignMethod() {
		return signMethod;
	}
	public void setSignMethod(String signMethod) {
		this.signMethod = signMethod;
	}
	public String getTxnType() {
		return txnType;
	}
	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}
	public String getTxnSubType() {
		return txnSubType;
	}
	public void setTxnSubType(String txnSubType) {
		this.txnSubType = txnSubType;
	}
	public String getBizType() {
		return bizType;
	}
	public void setBizType(String bizType) {
		this.bizType = bizType;
	}
	public String getChannelType() {
		return channelType;
	}
	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}
	public String getAccessType() {
		return accessType;
	}
	public void setAccessType(String accessType) {
		this.accessType = accessType;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public String getReqReserved() {
		return reqReserved;
	}
	public void setReqReserved(String reqReserved) {
		this.reqReserved = reqReserved;
	}
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getRespMsg() {
		return respMsg;
	}
	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}
	
	
}
