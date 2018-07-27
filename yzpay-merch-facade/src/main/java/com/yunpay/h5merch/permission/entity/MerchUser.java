package com.yunpay.h5merch.permission.entity;


import java.util.Date;
/**
 * 
 * 类名称                      商户登录实体类
 * 文件名称:     MerchUser.java
 * 内容摘要: 
 * @author:   Zhao Xiaoman
 * @version:  1.0  
 * @Date:     2017年7月25日上午9:14:22
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年7月25日   Zhao Xiaoman       1.0       新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class MerchUser {
	  private Integer id;//用户Id
	  private String openId;//用户微信唯一标识
	  private String userName;//用户姓名
	  private String loginName;//登录账号
	  private String loginPwd;//登录密码
	  private String phone;//手机号码
	  private String userType;//用户类型
	  private String merchantNo;//商户号
	  private String storeNo;//门店号
	  private String userStatus;//用户状态：1：启用;2：停用
	  private Date createTime=new Date();//创建时间
	  private String orgNo;//机构编号(扩展用，后续支持代理商需要用到)	  
	  private String pwdSalt;//md5加密的盐  
	public Integer getId()
	{
		return id;
	}
	public void setId(Integer id)
	{
		this.id = id;
	}
	public String getUserName()
	{
		return userName;
	}
	public void setUserName(String userName)
	{
		this.userName = userName;
	}
	public String getLoginName()
	{
		return loginName;
	}
	public void setLoginName(String loginName)
	{
		this.loginName = loginName;
	}
	public String getLoginPwd()
	{
		return loginPwd;
	}
	public void setLoginPwd(String loginPwd)
	{
		this.loginPwd = loginPwd;
	}
	public String getPhone()
	{
		return phone;
	}
	public void setPhone(String phone)
	{
		this.phone = phone;
	}
	public String getUserType()
	{
		return userType;
	}
	public void setUserType(String userType)
	{
		this.userType = userType;
	}
	public String getMerchantNo()
	{
		return merchantNo;
	}
	public void setMerchantNo(String merchantNo)
	{
		this.merchantNo = merchantNo;
	}
	public String getStoreNo()
	{
		return storeNo;
	}
	public void setStoreNo(String storeNo)
	{
		this.storeNo = storeNo;
	}
	public String getUserStatus()
	{
		return userStatus;
	}
	public void setUserStatus(String userStatus)
	{
		this.userStatus = userStatus;
	}
	public Date getCreateTime()
	{
		return createTime;
	}
	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}
	public String getOrgNo()
	{
		return orgNo;
	}
	public void setOrgNo(String orgNo)
	{
		this.orgNo = orgNo;
	}
	public String getPwdSalt()
	{
		return pwdSalt;
	}
	public void setPwdSalt(String pwdSalt)
	{
		this.pwdSalt = pwdSalt;
	}
	public String getOpenId()
	{
		return openId;
	}
	public void setOpenId(String openId)
	{
		this.openId = openId;
	}	  
}
