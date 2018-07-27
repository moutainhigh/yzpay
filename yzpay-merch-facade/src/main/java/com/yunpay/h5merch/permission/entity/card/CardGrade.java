package com.yunpay.h5merch.permission.entity.card;

import java.util.Date;

/**
 * 
 * 类名称                     会员卡等级信息表
 * 文件名称:     CardGrade.java
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

public class CardGrade{
	private static final long serialVersionUID = 1035280495160680854L;
	//关联会员卡等级信息表 t_membercard_grade
	private Integer gradeId;//会员卡等级信息表 Id
	private String orgNo;//关联的组织机构编码
	private String name;//会员等级名称
	private Integer discount;//折扣，该会员卡享受的折扣优惠
	private Integer sort;//会员卡等级排序
	private Integer status;//状态，0：正常，1：删除
	private Date createdAt;//创建时间
	private String createdBy;//创建时间
	private Date updatedAt;//更新时间
	private String updatedBy;//更新时间
	public Integer getGradeId()
	{
		return gradeId;
	}
	public void setGradeId(Integer gradeId)
	{
		this.gradeId = gradeId;
	}
	public String getOrgNo()
	{
		return orgNo;
	}
	public void setOrgNo(String orgNo)
	{
		this.orgNo = orgNo;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public Integer getDiscount()
	{
		return discount;
	}
	public void setDiscount(Integer discount)
	{
		this.discount = discount;
	}
	public Integer getSort()
	{
		return sort;
	}
	public void setSort(Integer sort)
	{
		this.sort = sort;
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
	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}

}
