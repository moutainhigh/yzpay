package com.yunpay.serv.model;

import java.util.Date;

/**
 * 会员消费流水表
 * 文件名称:     MemberConsumLs.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年9月21日上午9:21:56 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年9月21日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class MemberConsumLs {
	//流水id
	private Integer id;
	//消费渠道
	private String source;
	//用户会员卡号
	private String userCardCode;
	//会员姓名
	private String memberName;
	//充值订单号
	private String userOrderNo;
	//渠道交易流水号
	private String transNum;
	//充值金额
	private Float tranAmt;
	//优惠金额
	private Float disAmt;
	//积分抵扣金额
	private Float bonusAmt;
	//充前金额
	private Float beforeAmt;
	//充后金额
	private Float afterAmt;
	//创建时间
	private Date createdAt;
	//消费状态
	private Integer tranStatus;
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
	private String couponCode;
	private String couponNotify;
	private Integer notifyStatus;
	
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
	public String getUserOrderNo() {
		return userOrderNo;
	}
	public void setUserOrderNo(String userOrderNo) {
		this.userOrderNo = userOrderNo;
	}
	public String getTransNum() {
		return transNum;
	}
	public void setTransNum(String transNum) {
		this.transNum = transNum;
	}
	public Float getTranAmt() {
		return tranAmt;
	}
	public void setTranAmt(Float tranAmt) {
		this.tranAmt = tranAmt;
	}
	public Float getDisAmt() {
		return disAmt;
	}
	public void setDisAmt(Float disAmt) {
		this.disAmt = disAmt;
	}
	public Float getBonusAmt() {
		return bonusAmt;
	}
	public void setBonusAmt(Float bonusAmt) {
		this.bonusAmt = bonusAmt;
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
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Integer getTranStatus() {
		return tranStatus;
	}
	public void setTranStatus(Integer tranStatus) {
		this.tranStatus = tranStatus;
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
	public String getCouponCode() {
		return couponCode;
	}
	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}
	
	public String getCouponNotify() {
		return couponNotify;
	}
	public void setCouponNotify(String couponNotify) {
		this.couponNotify = couponNotify;
	}
	public Integer getNotifyStatus() {
		return notifyStatus;
	}
	public void setNotifyStatus(Integer notifyStatus) {
		this.notifyStatus = notifyStatus;
	}
	
	
}
