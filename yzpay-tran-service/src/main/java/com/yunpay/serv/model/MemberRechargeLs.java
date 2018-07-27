package com.yunpay.serv.model;

import java.util.Date;

/**
 * 充值流水表
 * 文件名称:     MemberRechargeLs.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年9月20日下午2:09:15 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年9月20日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class MemberRechargeLs {
	//流水id
	private Integer id;
	//充值渠道
	private String source;
	//用户会员卡号
	private String userCardCode;
	//会员姓名
	private String memberName;
	//充值订单号
	private String rechargeOrderNo;
	//渠道交易流水号
	private String transNum;
	//充值金额
	private Float rechargeAmt;
	//赠送金额
	private Float giveAmt;
	//充前金额
	private Float beforeAmt;
	//充后金额
	private Float afterAmt;
	//付款类型(0：线上微信，1：线上支付宝，2：线下微信，3：线下支付宝，4：商家充值)
	private Integer payType;
	//创建时间
	private Date createdAt;
	//充值状态
	private Integer rechargeStatus;
	//支付流水号(如用微信或支付宝充值时需要)
	private String payNum;
	//商户号
	private String merchantNo;
	//终端号
	private String termNo;
	//收银员号
	private String cashierNo;
	//备注信息
	private String info;
	//组织机构号
	private String orgNo;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getUserCardCode() {
		return userCardCode;
	}
	public void setUserCardCode(String userCardCode) {
		this.userCardCode = userCardCode;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getRechargeOrderNo() {
		return rechargeOrderNo;
	}
	public void setRechargeOrderNo(String rechargeOrderNo) {
		this.rechargeOrderNo = rechargeOrderNo;
	}
	public String getTransNum() {
		return transNum;
	}
	public void setTransNum(String transNum) {
		this.transNum = transNum;
	}
	public Float getRechargeAmt() {
		return rechargeAmt;
	}
	public void setRechargeAmt(Float rechargeAmt) {
		this.rechargeAmt = rechargeAmt;
	}
	public Float getGiveAmt() {
		return giveAmt;
	}
	public void setGiveAmt(Float giveAmt) {
		this.giveAmt = giveAmt;
	}
	public Float getBeforeAmt() {
		return beforeAmt;
	}
	public void setBeforeAmt(Float beforeAmt) {
		this.beforeAmt = beforeAmt;
	}
	public Float getAfterAmt() {
		return afterAmt;
	}
	public void setAfterAmt(Float afterAmt) {
		this.afterAmt = afterAmt;
	}
	public Integer getPayType() {
		return payType;
	}
	public void setPayType(Integer payType) {
		this.payType = payType;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Integer getRechargeStatus() {
		return rechargeStatus;
	}
	public void setRechargeStatus(Integer rechargeStatus) {
		this.rechargeStatus = rechargeStatus;
	}
	public String getPayNum() {
		return payNum;
	}
	public void setPayNum(String payNum) {
		this.payNum = payNum;
	}
	public String getMerchantNo() {
		return merchantNo;
	}
	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}
	public String getTermNo() {
		return termNo;
	}
	public void setTermNo(String termNo) {
		this.termNo = termNo;
	}
	public String getCashierNo() {
		return cashierNo;
	}
	public void setCashierNo(String cashierNo) {
		this.cashierNo = cashierNo;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getOrgNo() {
		return orgNo;
	}
	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	
	 
}
