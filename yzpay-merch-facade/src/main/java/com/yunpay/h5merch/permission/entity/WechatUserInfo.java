package com.yunpay.h5merch.permission.entity;

import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringUtils;

public class WechatUserInfo
{
	private Integer id;
	private String openId;
	private String nickName;
	private Integer sex;
	private String province;
	private String city;
	private String country;
	private String headImgUrl;
	private String privilege;
	private String unionId;
	private List<String> priviList;
	private Date createTime;
	private Date updateTime;
	public Integer getId()
	{
		return id;
	}
	public void setId(Integer id)
	{
		this.id = id;
	}
	public String getOpenId()
	{
		return openId;
	}
	public void setOpenId(String openId)
	{
		this.openId = openId;
	}
	public String getNickName()
	{
		return nickName;
	}
	public void setNickName(String nickName)
	{
		this.nickName = nickName;
	}
	public Integer getSex()
	{
		return sex;
	}
	public void setSex(Integer sex)
	{
		this.sex = sex;
	}
	public String getProvince()
	{
		return province;
	}
	public void setProvince(String province)
	{
		this.province = province;
	}
	public String getCity()
	{
		return city;
	}
	public void setCity(String city)
	{
		this.city = city;
	}
	public String getCountry()
	{
		return country;
	}
	public void setCountry(String country)
	{
		this.country = country;
	}
	public String getHeadImgUrl()
	{
		return headImgUrl;
	}
	public void setHeadImgUrl(String headImgUrl)
	{
		this.headImgUrl = headImgUrl;
	}
	public String getPrivilege()
	{
		return privilege;
	}
	public void setPrivilege(String privilege)
	{
		this.privilege = privilege;
		this.priviList.clear();
		if (StringUtils.isNotBlank(privilege))
		{
			String[] array = privilege.split(",");
			for (int i = 0; i < array.length; i++)
			{
				this.priviList.add(array[i]);
			}
		}
	}
	public List<String> getPriviList()
	{
		return priviList;
	}
	public void setPriviList(List<String> priviList)
	{
		this.priviList = priviList;
		this.privilege = null;		
		if (priviList.size()>0)
		{
			String string = null;
			for (String str : priviList)
			{
				string = string+str+",";
			}
			this.privilege = string.substring(0, string.length()-1);
		}		
	}
	public String getUnionId()
	{
		return unionId;
	}
	public void setUnionId(String unionId)
	{
		this.unionId = unionId;
	}
	public Date getCreateTime()
	{
		return createTime;
	}
	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}
	public Date getUpdateTime()
	{
		return updateTime;
	}
	public void setUpdateTime(Date updateTime)
	{
		this.updateTime = updateTime;
	}
	
}
