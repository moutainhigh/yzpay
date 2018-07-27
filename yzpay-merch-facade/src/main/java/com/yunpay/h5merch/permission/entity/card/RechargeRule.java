package com.yunpay.h5merch.permission.entity.card;

import java.util.Date;

/**
 * 
 * 类名称                     会员卡储值规则表
 * 文件名称:     rechargeRule.java
 * 内容摘要: 
 * @author:   Zhao Xiaoman
 * @version:  1.0  
 * @Date:     2017年9月6日上午9:56:22
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年9月6日   Zhao Xiaoman       1.0       新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */

public class RechargeRule{
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1035280495160680854L;
	//关联会员卡积分规则表 t_member_recharge
	private Integer id;//储值规则表Id
	private String gradeId; //会员等级ID
	private Integer charge; //冲多少金额
	private Double send; //送多少金额，小数点后两位
	private Integer status;//状态，0：正常，1：停用
	private Date createdAt;//创建时间
	private String createdBy;//创建人
	private Date updatedAt;//更新时间
	private String updatedBy;//更新人
	private String merchantNo;//商户编码
	public Integer getId()
	{
		return id;
	}
	public void setId(Integer id)
	{
		this.id = id;
	}
	public String getGradeId()
	{
		return gradeId;
	}
	public void setGradeId(String gradeId)
	{
		this.gradeId = gradeId;
	}
	public Integer getCharge()
	{
		return charge;
	}
	public void setCharge(Integer charge)
	{
		this.charge = charge;
	}
	public Double getSend()
	{
		return send;
	}
	public void setSend(Double send)
	{
		this.send = send;
	}
	public Integer getStatus()
	{
		return status;
	}
	public void setStatus(Integer status)
	{
		this.status = status;
	}
	public Date getCreatedAt()
	{
		return createdAt;
	}
	public void setCreatedAt(Date createdAt)
	{
		this.createdAt = createdAt;
	}
	public String getCreatedBy()
	{
		return createdBy;
	}
	public void setCreatedBy(String createdBy)
	{
		this.createdBy = createdBy;
	}
	public Date getUpdatedAt()
	{
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt)
	{
		this.updatedAt = updatedAt;
	}
	public String getUpdatedBy()
	{
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy)
	{
		this.updatedBy = updatedBy;
	}
	public String getMerchantNo()
	{
		return merchantNo;
	}
	public void setMerchantNo(String merchantNo)
	{
		this.merchantNo = merchantNo;
	}
}
