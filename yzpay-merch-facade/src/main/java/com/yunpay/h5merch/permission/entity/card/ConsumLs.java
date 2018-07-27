package com.yunpay.h5merch.permission.entity.card;

import java.util.Date;

/**
 * 
 * 类名称                     会员卡储值消费
 * 文件名称:     ConsumLs.java
 * 内容摘要: 
 * @author:   Zhao Xiaoman
 * @version:  1.0  
 * @Date:     2017年10月17日上午9:54:54
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年10月17日   Zhao Xiaoman       1.0       新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */

public class ConsumLs{
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1035280495160680854L;
	//关联会员卡储值消费记录表t_member_consum_ls
	private Integer id;
	private String source;//充值记录来源（pos，H5）
	private String userCardCode;//会员的卡号
	private String memberName;//会员姓名
	private String userOrderNo;//消费订单号
	private String transNum;//交易流水号（平台）
	private Double tranAmt;//消费金额（小数点后两位）
	private Double disAmt;//优惠金额
	private Double bonusAmt;//积分抵扣金额
	private Double beforeAmt;//消费前余额
	private Double afterAmt;//消费后余额
	private Date createdAt;//消费时间
	private Integer tranStatus;//交易状态，0：交易失败，1：交易中，2：交易成功
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
	public String getUserOrderNo()
	{
		return userOrderNo;
	}
	public void setUserOrderNo(String userOrderNo)
	{
		this.userOrderNo = userOrderNo;
	}
	public String getTransNum()
	{
		return transNum;
	}
	public void setTransNum(String transNum)
	{
		this.transNum = transNum;
	}
	public Double getTranAmt()
	{
		return tranAmt;
	}
	public void setTranAmt(Double tranAmt)
	{
		this.tranAmt = tranAmt;
	}
	public Double getDisAmt()
	{
		return disAmt;
	}
	public void setDisAmt(Double disAmt)
	{
		this.disAmt = disAmt;
	}
	public Double getBonusAmt()
	{
		return bonusAmt;
	}
	public void setBonusAmt(Double bonusAmt)
	{
		this.bonusAmt = bonusAmt;
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
	public Date getCreatedAt()
	{
		return createdAt;
	}
	public void setCreatedAt(Date createdAt)
	{
		this.createdAt = createdAt;
	}
	public Integer getTranStatus()
	{
		return tranStatus;
	}
	public void setTranStatus(Integer tranStatus)
	{
		this.tranStatus = tranStatus;
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
