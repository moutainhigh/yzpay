package com.yunpay.h5merch.permission.entity;

import java.util.Date;

/**
 * 类名称		       门店实体类
 * 文件名称:     Store.java
 * 内容摘要: 	  
 * @author:   Zhao Xiaoman
 * @version:  1.0  
 * @Date:     2017年6月14日上午11:32:33
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年6月14日   Zhao Xiaoman       1.0       新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */

public class H5StoreEntity{
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1035280495160680854L;
	private Integer id;//门店Id
	private Integer merId;//关联商户Id
	private String storeNo;//门店编号
	private String storeName;//主店店名(支付宝main_shop_name，微信business_name)
	private String prov;//省份编码，国标码，详见国家统计局数据
	private String provName;//省份名称
	private String city;//城市编码，国标码，详见国家统计局数据
	private String cityName;//城市名称
	private String area;//区县编码，国标码，详见国家统计局数据
	private String areaName;//区县名称
	private String address;//地址
	private String contactTel;//门店电话号码，支持座机和手机，在客户端对用户展现，支持多个电话，以英文逗号分隔
	private String longitude;//经度（高德坐标系）
	private String latitude;//纬度（高德坐标系）
	private Integer status;//门店状态，0：停用，1：启用
	private Date createdAt;//创建时间
	private Date updatedAt;//更新时间
	private String orgNo;  // 组织机构编码
	private String infoFrom;  //信息来源，0：平台系统，1：商户系统
	private String remark;  //备注
	private String contactMan;  //联系人
	private String registerName;  //商户名称
	  
	  
		
	public Integer getMerId()
	{
		return merId;
	}
	public void setMerId(Integer merId)
	{
		this.merId = merId;
	}
	public String getStoreNo()
	{
		return storeNo;
	}
	public void setStoreNo(String storeNo)
	{
		this.storeNo = storeNo;
	}
	public String getStoreName()
	{
		return storeName;
	}
	public void setStoreName(String storeName)
	{
		this.storeName = storeName;
	}
	public String getProv()
	{
		return prov;
	}
	public void setProv(String prov)
	{
		this.prov = prov;
	}
	public String getCity()
	{
		return city;
	}
	public void setCity(String city)
	{
		this.city = city;
	}
	public String getArea()
	{
		return area;
	}
	public void setArea(String area)
	{
		this.area = area;
	}
	public String getAddress()
	{
		return address;
	}
	public void setAddress(String address)
	{
		this.address = address;
	}
	public String getContactTel()
	{
		return contactTel;
	}
	public void setContactTel(String contactTel)
	{
		this.contactTel = contactTel;
	}
	public String getLongitude()
	{
		return longitude;
	}
	public void setLongitude(String longitude)
	{
		this.longitude = longitude;
	}
	public String getLatitude()
	{
		return latitude;
	}
	public void setLatitude(String latitude)
	{
		this.latitude = latitude;
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
	public Date getUpdatedAt()
	{
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt)
	{
		this.updatedAt = updatedAt;
	}
	public String getOrgNo()
	{
		return orgNo;
	}
	public void setOrgNo(String orgNo)
	{
		this.orgNo = orgNo;
	}
	public String getInfoFrom()
	{
		return infoFrom;
	}
	public void setInfoFrom(String infoFrom)
	{
		this.infoFrom = infoFrom;
	}
	public String getRemark()
	{
		return remark;
	}
	public void setRemark(String remark)
	{
		this.remark = remark;
	}
	public Integer getId()
	{
		return id;
	}
	public void setId(Integer id)
	{
		this.id = id;
	}
	public String getContactMan()
	{
		return contactMan;
	}
	public void setContactMan(String contactMan)
	{
		this.contactMan = contactMan;
	}
	public String getRegisterName()
	{
		return registerName;
	}
	public void setRegisterName(String registerName)
	{
		this.registerName = registerName;
	}
	public String getProvName()
	{
		return provName;
	}
	public void setProvName(String provName)
	{
		this.provName = provName;
	}
	public String getCityName()
	{
		return cityName;
	}
	public void setCityName(String cityName)
	{
		this.cityName = cityName;
	}
	public String getAreaName()
	{
		return areaName;
	}
	public void setAreaName(String areaName)
	{
		this.areaName = areaName;
	}
}
