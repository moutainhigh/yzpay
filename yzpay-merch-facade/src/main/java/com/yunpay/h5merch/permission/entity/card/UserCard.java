package com.yunpay.h5merch.permission.entity.card;

import java.util.Date;
/**
 * 
 * 类名称                        会员的卡信息类
 * 文件名称:     MemberCard.java
 * 内容摘要: 
 * @author:   Zhao Xiaoman
 * @version:  1.0  
 * @Date:     2017年10月17日上午9:25:56
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年10月17日   Zhao Xiaoman       1.0       新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */

public class UserCard{
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1035280495160680854L;
	//关联会员的卡信息表t_membercard
	private Integer id;
	private Integer template_id;//会员卡模板id
	private String card_id;//渠道返回的会员卡id
	private Integer channelType;//会员渠道1：支付宝；2：微信
	private String userCardCode;//微信会员卡卡号
	private String openId;//微信会员卡openId
	private String memberId;//领取会员卡的会员ID
	private String number;//云铺会员卡卡号
	private Integer bonus;//积分 
	private Double balance;//余额
	private String payPass;//会员卡交易密码
	private Integer status;//状态，0：已领取，1：已激活，2：已失效，3：已删除 , 4：与新卡绑定后作废
	private Date createdAt;//创建时间(领取时间)
	private Date updatedAt;//更新时间
	private String nickname;//用户昵称
	private String sex;//用户性别
	private String mobile;//手机号
	private String name;//姓名
	private String birthday;//生日
	private String idcard;//身份证
	private String email;//邮箱
	private String location;//详细地址
	private String education;//教育背景
	private String career;//职业
	private String industry;//行业
	private String income;//收入
	private String habit;//兴趣爱好
	private String aliUserCardCode;//支付宝会员卡卡号
	private String orgNo;//商户组织结构编码
	private Integer totalBonus;//累计积分
	private String storeNo;//门店号
	private String merchantNo;//商户号
	private String sourceScene;//领取的场景值
	public Integer getId()
	{
		return id;
	}
	public void setId(Integer id)
	{
		this.id = id;
	}
	public Integer getTemplate_id()
	{
		return template_id;
	}
	public void setTemplate_id(Integer template_id)
	{
		this.template_id = template_id;
	}
	public String getCard_id()
	{
		return card_id;
	}
	public void setCard_id(String card_id)
	{
		this.card_id = card_id;
	}
	public Integer getChannelType()
	{
		return channelType;
	}
	public void setChannelType(Integer channelType)
	{
		this.channelType = channelType;
	}
	public String getUserCardCode()
	{
		return userCardCode;
	}
	public void setUserCardCode(String userCardCode)
	{
		this.userCardCode = userCardCode;
	}
	public String getOpenId()
	{
		return openId;
	}
	public void setOpenId(String openId)
	{
		this.openId = openId;
	}
	public String getMemberId()
	{
		return memberId;
	}
	public void setMemberId(String memberId)
	{
		this.memberId = memberId;
	}
	public String getNumber()
	{
		return number;
	}
	public void setNumber(String number)
	{
		this.number = number;
	}
	public Integer getBonus()
	{
		return bonus;
	}
	public void setBonus(Integer bonus)
	{
		this.bonus = bonus;
	}
	public Double getBalance()
	{
		return balance;
	}
	public void setBalance(Double balance)
	{
		this.balance = balance;
	}
	public String getPayPass()
	{
		return payPass;
	}
	public void setPayPass(String payPass)
	{
		this.payPass = payPass;
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
	public String getNickname()
	{
		return nickname;
	}
	public void setNickname(String nickname)
	{
		this.nickname = nickname;
	}
	public String getSex()
	{
		return sex;
	}
	public void setSex(String sex)
	{
		this.sex = sex;
	}
	public String getMobile()
	{
		return mobile;
	}
	public void setMobile(String mobile)
	{
		this.mobile = mobile;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getBirthday()
	{
		return birthday;
	}
	public void setBirthday(String birthday)
	{
		this.birthday = birthday;
	}
	public String getIdcard()
	{
		return idcard;
	}
	public void setIdcard(String idcard)
	{
		this.idcard = idcard;
	}
	public String getEmail()
	{
		return email;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}
	public String getLocation()
	{
		return location;
	}
	public void setLocation(String location)
	{
		this.location = location;
	}
	public String getEducation()
	{
		return education;
	}
	public void setEducation(String education)
	{
		this.education = education;
	}
	public String getCareer()
	{
		return career;
	}
	public void setCareer(String career)
	{
		this.career = career;
	}
	public String getIndustry()
	{
		return industry;
	}
	public void setIndustry(String industry)
	{
		this.industry = industry;
	}
	public String getIncome()
	{
		return income;
	}
	public void setIncome(String income)
	{
		this.income = income;
	}
	public String getHabit()
	{
		return habit;
	}
	public void setHabit(String habit)
	{
		this.habit = habit;
	}
	public String getAliUserCardCode()
	{
		return aliUserCardCode;
	}
	public void setAliUserCardCode(String aliUserCardCode)
	{
		this.aliUserCardCode = aliUserCardCode;
	}
	public String getOrgNo()
	{
		return orgNo;
	}
	public void setOrgNo(String orgNo)
	{
		this.orgNo = orgNo;
	}
	public Integer getTotalBonus()
	{
		return totalBonus;
	}
	public void setTotalBonus(Integer totalBonus)
	{
		this.totalBonus = totalBonus;
	}
	public String getStoreNo()
	{
		return storeNo;
	}
	public void setStoreNo(String storeNo)
	{
		this.storeNo = storeNo;
	}
	public String getMerchantNo()
	{
		return merchantNo;
	}
	public void setMerchantNo(String merchantNo)
	{
		this.merchantNo = merchantNo;
	}
	public String getSourceScene()
	{
		return sourceScene;
	}
	public void setSourceScene(String sourceScene)
	{
		this.sourceScene = sourceScene;
	}
	
	
	
	
	
	
	
}
