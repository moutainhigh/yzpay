package com.yunpay.h5merch.permission.entity.card;

/**
 * 
 * 类名称                     会员卡激活信息表
 * 文件名称:     CardTemplateCommonfield.java
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

public class Commonfield{
	private static final long serialVersionUID = 1035280495160680854L;
	//关联会员卡激活信息表 t_membercard_template_commonfield
	private Integer id;//会员卡等级信息表 Id
	private Integer templateId;//关联的会员卡模板id
	private String field;//字段值
	private Integer value;//是否必填，0：选填，1：必填
	public Integer getId()
	{
		return id;
	}
	public void setId(Integer id)
	{
		this.id = id;
	}
	public Integer getTemplateId()
	{
		return templateId;
	}
	public void setTemplateId(Integer templateId)
	{
		this.templateId = templateId;
	}
	public String getField()
	{
		return field;
	}
	public void setField(String field)
	{
		this.field = field;
	}
	public Integer getValue()
	{
		return value;
	}
	public void setValue(Integer value)
	{
		this.value = value;
	}
	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}
	
}
