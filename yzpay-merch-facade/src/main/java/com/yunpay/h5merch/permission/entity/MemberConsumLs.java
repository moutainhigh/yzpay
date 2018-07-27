package com.yunpay.h5merch.permission.entity;

import java.util.Date;

import com.yunpay.common.core.utils.annotation.Table;
import com.yunpay.permission.entity.PermissionBaseEntity;

/**
 * 
 * 文件名称:
 * 内容摘要: 会员消费记录
 * @author:   duan zhang quan
 * @version:  1.0  
 * @Date:     2017年4月14日上午9:56:12 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年4月14日     duan zhang quan   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
@SuppressWarnings("serial")
@Table("t_member_consum_ls")
public class MemberConsumLs extends PermissionBaseEntity{
	private int id;  // 主键
	private String source;  // 消费记录来源（pos，H5）
	private String userCardCode; // 会员卡号
	private String memberName;  // 会员姓名
	private  String userOrderNo; // 消费订单号
	private String transNum ; // 交易流水号（平台）
	private float tranAmt; // 消费金额
	private float disAmt;  //优惠金额
	private float bonusAmt; // 积分抵扣金额
	private float beforeAmt; // 消费前余额
	private float afterAmt ; // 消费后余额
	private Date createdAt; // 充值时间
	private byte tranStatus = 1; // 交易状态
	private String merchantNo; // 商户号
	private String storeName;  // 门店号
	private String termNo; // 终端号
	private String cashierNo ; //收银员号
	private String cashier; // 收银员名称
	private String info; // 备注
	private String orgNo ; // 组织机构编码
	public int getId() {
		return id;
	}
	public void setId(int id) {
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
	public float getTranAmt() {
		return tranAmt;
	}
	public void setTranAmt(float tranAmt) {
		this.tranAmt = tranAmt;
	}
	public float getDisAmt() {
		return disAmt;
	}
	public void setDisAmt(float disAmt) {
		this.disAmt = disAmt;
	}
	public float getBonusAmt() {
		return bonusAmt;
	}
	public void setBonusAmt(float bonusAmt) {
		this.bonusAmt = bonusAmt;
	}
	public float getBeforeAmt() {
		return beforeAmt;
	}
	public void setBeforeAmt(float beforeAmt) {
		this.beforeAmt = beforeAmt;
	}
	public float getAfterAmt() {
		return afterAmt;
	}
	public void setAfterAmt(float afterAmt) {
		this.afterAmt = afterAmt;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public byte getTranStatus() {
		return tranStatus;
	}
	public void setTranStatus(byte tranStatus) {
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
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getCashier() {
		return cashier;
	}
	public void setCashier(String cashier) {
		this.cashier = cashier;
	}
	
	
	
}
