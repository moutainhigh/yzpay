package com.yunpay.h5merch.permission.entity.card;

import java.util.Date;

/**
 * 
 * 类名称                     会员信息类
 * 文件名称:     Member.java
 * 内容摘要: 
 * @author:   Zhao Xiaoman
 * @version:  1.0  
 * @Date:     2017年10月16日下午7:14:36
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年10月16日   Zhao Xiaoman       1.0       新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */

public class Member{
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1035280495160680854L;
	//关联会员信息表t_member
	private Integer id;
	private String trueName; //会员姓名
	private String nickName; //会员昵称
	private String portrait; //会员头像
	private String tel; //联系电话
	private Integer sex; //性别
	private String bornYear; //出生年
	private String bornMonth; //出生月
	private String bornDay; //出生日
	private String prov; //省
	private String city; //市
	private String area; //区
	private String address; //住址
	private String postcode; //邮编
	private String qq; //QQ号码
	private Date createdBy;//创建时间
	private Date updatedBy;//更新时间
	private String info;//备注
	private String openId;//会员的微信OpenId
	private Integer sourceType;//会员的来源类型，0：微信公众号关注，1：支付宝服务窗关注，3：系统注册 4：其它，
	private String sourceId;//会员来源的业务标识ID，如微信公众号业务Id，支付宝服务窗业务Id等
	private String appid_userId;//外部系统中的会员ID
	private String connect_userId;//关联的会员ID（比如：推荐的会员）
	private Integer merchantId;//所属商户ID
	private String merchantName;//所属商户名称
	private String aliopenId;//会员的支付宝OpenId
	private String orgNo;//商户组织结构编码
	private String gradeId;//会员等级
	private Integer status;//会员状态，0：启用，1：停用
	
	public Integer getId()
	{
		return id;
	}
	public void setId(Integer id)
	{
		this.id = id;
	}
	public String getTrueName()
	{
		return trueName;
	}
	public void setTrueName(String trueName)
	{
		this.trueName = trueName;
	}
	public String getNickName()
	{
		return nickName;
	}
	public void setNickName(String nickName)
	{
		this.nickName = nickName;
	}
	public String getPortrait()
	{
		return portrait;
	}
	public void setPortrait(String portrait)
	{
		this.portrait = portrait;
	}
	public String getTel()
	{
		return tel;
	}
	public void setTel(String tel)
	{
		this.tel = tel;
	}
	public Integer getSex()
	{
		return sex;
	}
	public void setSex(Integer sex)
	{
		this.sex = sex;
	}
	public String getBornYear()
	{
		return bornYear;
	}
	public void setBornYear(String bornYear)
	{
		this.bornYear = bornYear;
	}
	public String getBornMonth()
	{
		return bornMonth;
	}
	public void setBornMonth(String bornMonth)
	{
		this.bornMonth = bornMonth;
	}
	public String getBornDay()
	{
		return bornDay;
	}
	public void setBornDay(String bornDay)
	{
		this.bornDay = bornDay;
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
	public String getPostcode()
	{
		return postcode;
	}
	public void setPostcode(String postcode)
	{
		this.postcode = postcode;
	}
	public String getQq()
	{
		return qq;
	}
	public void setQq(String qq)
	{
		this.qq = qq;
	}
	public Date getCreatedBy()
	{
		return createdBy;
	}
	public void setCreatedBy(Date createdBy)
	{
		this.createdBy = createdBy;
	}
	public Date getUpdatedBy()
	{
		return updatedBy;
	}
	public void setUpdatedBy(Date updatedBy)
	{
		this.updatedBy = updatedBy;
	}
	public String getInfo()
	{
		return info;
	}
	public void setInfo(String info)
	{
		this.info = info;
	}
	public String getOpenId()
	{
		return openId;
	}
	public void setOpenId(String openId)
	{
		this.openId = openId;
	}
	public Integer getSourceType()
	{
		return sourceType;
	}
	public void setSourceType(Integer sourceType)
	{
		this.sourceType = sourceType;
	}
	public String getSourceId()
	{
		return sourceId;
	}
	public void setSourceId(String sourceId)
	{
		this.sourceId = sourceId;
	}
	public String getAppid_userId()
	{
		return appid_userId;
	}
	public void setAppid_userId(String appid_userId)
	{
		this.appid_userId = appid_userId;
	}
	public String getConnect_userId()
	{
		return connect_userId;
	}
	public void setConnect_userId(String connect_userId)
	{
		this.connect_userId = connect_userId;
	}
	public Integer getMerchantId()
	{
		return merchantId;
	}
	public void setMerchantId(Integer merchantId)
	{
		this.merchantId = merchantId;
	}
	public String getMerchantName()
	{
		return merchantName;
	}
	public void setMerchantName(String merchantName)
	{
		this.merchantName = merchantName;
	}
	public String getAliopenId()
	{
		return aliopenId;
	}
	public void setAliopenId(String aliopenId)
	{
		this.aliopenId = aliopenId;
	}
	public String getOrgNo()
	{
		return orgNo;
	}
	public void setOrgNo(String orgNo)
	{
		this.orgNo = orgNo;
	}
	public String getGradeId()
	{
		return gradeId;
	}
	public void setGradeId(String gradeId)
	{
		this.gradeId = gradeId;
	}
	public Integer getStatus()
	{
		return status;
	}
	public void setStatus(Integer status)
	{
		this.status = status;
	}
}
