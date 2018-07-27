package com.yunpay.permission.entity;

import java.util.Date;
/**
 * 类名称		       商户卡券领取信息实体类
 * 文件名称:     Card.java
 * 内容摘要: 
 * @author:   Zhao Xiaoman
 * @version:  1.0  
 * @Date:     2017年6月20日上午10:01:56
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年6月20日   Zhao Xiaoman       1.0       新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class CardReceive {
	  private Integer id;//所领取的每张卡券id
	  private String appidUserId;//领取该卡券的会员Id
	  private String title;//卡券名称
	  private String source;//卡券来源1:微信;2:支付宝
	  private Integer type;//卡券类型 1、通用券 2、折扣券 3、代金券 4、礼品券 5、团购券
	  private Integer subType;//子类型：0：机票，1：电影票，2：门票，3：团购，4：其他
	  private Double price;//可抵用的金额
	  private Double discount;//卡券的折扣
	  private Integer score;//兑换礼品所需积分
	  private Integer pagesPer;//每个用户可以获得的张数
	  private String info;//使用说明
	  private String sn;//验证码或兑换码
	  private Date startTime;//有效期开始时间
	  private Date endTime;//有效期结束时间
	  private Double quota;//使用的消费额度限制
	  private Date createdAt;//产生时间
	  private Date updatedAt;//修改时间
	  private String appidCardId;//外部系统中的优惠券ID
	  private Integer status;//状态，0：未使用，1：已使用，2：已过期,3:已删除
	  private Date useTime;//使用时间
	  private String serialNumber;//支付宝卡券码
	  private String orgNo;//组织结构编码
	  private String friendUserName;//赠送者微信openId
	  private String oldUserCardCode;//赠送者卡券code
	  
	  //以下两个变量非数据库字段，仅用于查询时接受数据
	  private String date_begin;//查询开始时间
	  private String date_end;//查询结束时间
	public Integer getId()
	{
		return id;
	}
	public void setId(Integer id)
	{
		this.id = id;
	}
	public String getAppidUserId()
	{
		return appidUserId;
	}
	public void setAppidUserId(String appidUserId)
	{
		this.appidUserId = appidUserId;
	}
	public String getTitle()
	{
		return title;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}
	public String getSource()
	{
		return source;
	}
	public void setSource(String source)
	{
		this.source = source;
	}
	public Integer getType()
	{
		return type;
	}
	public void setType(Integer type)
	{
		this.type = type;
	}
	public Integer getSubType()
	{
		return subType;
	}
	public void setSubType(Integer subType)
	{
		this.subType = subType;
	}
	public Double getPrice()
	{
		return price;
	}
	public void setPrice(Double price)
	{
		this.price = price;
	}
	public Double getDiscount()
	{
		return discount;
	}
	public void setDiscount(Double discount)
	{
		this.discount = discount;
	}
	public Integer getScore()
	{
		return score;
	}
	public void setScore(Integer score)
	{
		this.score = score;
	}
	public Integer getPagesPer()
	{
		return pagesPer;
	}
	public void setPagesPer(Integer pagesPer)
	{
		this.pagesPer = pagesPer;
	}
	public String getInfo()
	{
		return info;
	}
	public void setInfo(String info)
	{
		this.info = info;
	}
	public String getSn()
	{
		return sn;
	}
	public void setSn(String sn)
	{
		this.sn = sn;
	}
	public Date getStartTime()
	{
		return startTime;
	}
	public void setStartTime(Date startTime)
	{
		this.startTime = startTime;
	}
	public Date getEndTime()
	{
		return endTime;
	}
	public void setEndTime(Date endTime)
	{
		this.endTime = endTime;
	}
	public Double getQuota()
	{
		return quota;
	}
	public void setQuota(Double quota)
	{
		this.quota = quota;
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
	public String getAppidCardId()
	{
		return appidCardId;
	}
	public void setAppidCardId(String appidCardId)
	{
		this.appidCardId = appidCardId;
	}
	public Integer getStatus()
	{
		return status;
	}
	public void setStatus(Integer status)
	{
		this.status = status;
	}
	public Date getUseTime()
	{
		return useTime;
	}
	public void setUseTime(Date useTime)
	{
		this.useTime = useTime;
	}
	public String getSerialNumber()
	{
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber)
	{
		this.serialNumber = serialNumber;
	}
	public String getOrgNo()
	{
		return orgNo;
	}
	public void setOrgNo(String orgNo)
	{
		this.orgNo = orgNo;
	}
	public String getFriendUserName()
	{
		return friendUserName;
	}
	public void setFriendUserName(String friendUserName)
	{
		this.friendUserName = friendUserName;
	}
	public String getOldUserCardCode()
	{
		return oldUserCardCode;
	}
	public void setOldUserCardCode(String oldUserCardCode)
	{
		this.oldUserCardCode = oldUserCardCode;
	}
	public String getDate_begin()
	{
		return date_begin;
	}
	public void setDate_begin(String date_begin)
	{
		this.date_begin = date_begin;
	}
	public String getDate_end()
	{
		return date_end;
	}
	public void setDate_end(String date_end)
	{
		this.date_end = date_end;
	}

}
