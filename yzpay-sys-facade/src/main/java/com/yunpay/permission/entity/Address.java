package com.yunpay.permission.entity;

public class Address {
	/**
	 * 
	 * 文件名称:
	 * 内容摘要: 用于显示省市区级联数据的实体类,关联表：t_region
	 * @author:   duan zhang quan
	 * @version:  1.0  
	 * @Date:     2017年4月14日上午9:56:12 
	 * 修改历史:  
	 * 修改日期             修改人员            版本	     修改内容  
	 * ----------------------------------------------  
	 * 2017年6月12日     duan zhang quan   1.0     新建
	 *
	 * 版权:   版权所有(C)2017
	 * 公司:   深圳市至高通信技术发展有限公司
	 */
	private Integer id;  // 主键
	private String name;  // 省市区名称
	private Integer pid;  // 父id
	private Integer type;   // 层级
	public Integer getId()
	{
		return id;
	}
	public void setId(Integer id)
	{
		this.id = id;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public Integer getPid()
	{
		return pid;
	}
	public void setPid(Integer pid)
	{
		this.pid = pid;
	}
	public Integer getType()
	{
		return type;
	}
	public void setType(Integer type)
	{
		this.type = type;
	}
	
}
