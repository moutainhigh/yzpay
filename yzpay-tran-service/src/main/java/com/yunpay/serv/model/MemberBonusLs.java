package com.yunpay.serv.model;

import java.util.Date;

/**
 * 会员积分变动流水
 * 文件名称:     MemberIntegralLs.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年9月21日下午3:01:21 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年9月21日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class MemberBonusLs {
	
	private Integer id;
	private Integer source;
	private String userCardCode;
	private String sourceTransNum;
	private Integer beforeBonus;
	private Integer changeBonus;
	private Integer afterBonus;
	private Integer changeType;
	private Date createdAt;
	private String info;
	private float equalAmt;
	private float tranAmt;
	private String merchantNo;
	private String termNo;
	private String cashierNo;
	private String orgNo;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getSource() {
		return source;
	}
	public void setSource(Integer source) {
		this.source = source;
	}
	public String getUserCardCode() {
		return userCardCode;
	}
	public void setUserCardCode(String userCardCode) {
		this.userCardCode = userCardCode;
	}
	public String getSourceTransNum() {
		return sourceTransNum;
	}
	public void setSourceTransNum(String sourceTransNum) {
		this.sourceTransNum = sourceTransNum;
	}
	public Integer getBeforeBonus() {
		return beforeBonus;
	}
	public void setBeforeBonus(Integer beforeBonus) {
		this.beforeBonus = beforeBonus;
	}
	public Integer getChangeBonus() {
		return changeBonus;
	}
	public void setChangeBonus(Integer changeBonus) {
		this.changeBonus = changeBonus;
	}
	public Integer getAfterBonus() {
		return afterBonus;
	}
	public void setAfterBonus(Integer afterBonus) {
		this.afterBonus = afterBonus;
	}
	public Integer getChangeType() {
		return changeType;
	}
	public void setChangeType(Integer changeType) {
		this.changeType = changeType;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public float getEqualAmt() {
		return equalAmt;
	}
	public void setEqualAmt(float equalAmt) {
		this.equalAmt = equalAmt;
	}
	
	public float getTranAmt() {
		return tranAmt;
	}
	public void setTranAmt(float tranAmt) {
		this.tranAmt = tranAmt;
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
	public String getOrgNo() {
		return orgNo;
	}
	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}
	
	
	
}
