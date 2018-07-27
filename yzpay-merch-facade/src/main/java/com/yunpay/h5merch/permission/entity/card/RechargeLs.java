package com.yunpay.h5merch.permission.entity.card;

import java.util.Date;

/**
 * 
 * 类名称                      会员充值记录
 * 文件名称:     RechargeLs.java
 * 内容摘要: 
 * @author:   Zhao Xiaoman
 * @version:  1.0  
 * @Date:     2017年10月17日上午9:56:00
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年10月17日   Zhao Xiaoman       1.0       新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */

public class RechargeLs{
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1035280495160680854L;
	//关联会员充值记录表t_member_recharge_ls
	private Integer id;
	private String source;//充值记录来源（pos，H5）
	private String userCardCode;//会员的卡号
	private String memberName;//会员姓名
	private String rechargeOrderNo;//充值订单号
	private String transNum;//交易流水号（平台）
	private Double rechargeAmt;//充值金额（小数点后两位）
	private Double giveAmt;//赠送金额
	private Double beforeAmt;//充前余额
	private Double afterAmt;//充后余额
	private Integer payType;//充值渠道类型，0：线上微信，1：线上支付宝，2：线下微信，3：线下支付宝，4：商家充值
	private Date createdAt;//充值时间
	private Integer rechargeStatus;//交易状态，0：交易失败，1：交易中，2：交易成功
	private String payNum;//支付流水号
	private String merchantNo;//商户号
	private String termNo;//终端号
	private String cashierNo;//收银员号
	private String info;//备注
	private String orgNo;//组织机构编码
	public Integer getId()
	{
		return id;
	}
	public void setId(Integer id)
	{
		this.id = id;
	}
	public String getSource()
	{
		return source;
	}
	public void setSource(String source)
	{
		this.source = source;
	}
	public String getUserCardCode()
	{
		return userCardCode;
	}
	public void setUserCardCode(String userCardCode)
	{
		this.userCardCode = userCardCode;
	}
	public String getMemberName()
	{
		return memberName;
	}
	public void setMemberName(String memberName)
	{
		this.memberName = memberName;
	}
	public String getRechargeOrderNo()
	{
		return rechargeOrderNo;
	}
	public void setRechargeOrderNo(String rechargeOrderNo)
	{
		this.rechargeOrderNo = rechargeOrderNo;
	}
	public String getTransNum()
	{
		return transNum;
	}
	public void setTransNum(String transNum)
	{
		this.transNum = transNum;
	}
	public Double getRechargeAmt()
	{
		return rechargeAmt;
	}
	public void setRechargeAmt(Double rechargeAmt)
	{
		this.rechargeAmt = rechargeAmt;
	}
	public Double getGiveAmt()
	{
		return giveAmt;
	}
	public void setGiveAmt(Double giveAmt)
	{
		this.giveAmt = giveAmt;
	}
	public Double getBeforeAmt()
	{
		return beforeAmt;
	}
	public void setBeforeAmt(Double beforeAmt)
	{
		this.beforeAmt = beforeAmt;
	}
	public Double getAfterAmt()
	{
		return afterAmt;
	}
	public void setAfterAmt(Double afterAmt)
	{
		this.afterAmt = afterAmt;
	}
	public Integer getPayType()
	{
		return payType;
	}
	public void setPayType(Integer payType)
	{
		this.payType = payType;
	}
	public Date getCreatedAt()
	{
		return createdAt;
	}
	public void setCreatedAt(Date createdAt)
	{
		this.createdAt = createdAt;
	}
	public Integer getRechargeStatus()
	{
		return rechargeStatus;
	}
	public void setRechargeStatus(Integer rechargeStatus)
	{
		this.rechargeStatus = rechargeStatus;
	}
	public String getPayNum()
	{
		return payNum;
	}
	public void setPayNum(String payNum)
	{
		this.payNum = payNum;
	}
	public String getMerchantNo()
	{
		return merchantNo;
	}
	public void setMerchantNo(String merchantNo)
	{
		this.merchantNo = merchantNo;
	}
	public String getTermNo()
	{
		return termNo;
	}
	public void setTermNo(String termNo)
	{
		this.termNo = termNo;
	}
	public String getCashierNo()
	{
		return cashierNo;
	}
	public void setCashierNo(String cashierNo)
	{
		this.cashierNo = cashierNo;
	}
	public String getInfo()
	{
		return info;
	}
	public void setInfo(String info)
	{
		this.info = info;
	}
	public String getOrgNo()
	{
		return orgNo;
	}
	public void setOrgNo(String orgNo)
	{
		this.orgNo = orgNo;
	}

}
