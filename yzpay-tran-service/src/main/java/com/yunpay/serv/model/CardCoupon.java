package com.yunpay.serv.model;

import java.util.Date;

public class CardCoupon {
	
	private Integer id;

    private String appidUserId;

    private String title;

    private String source;

    private Integer type;

    private Integer subType;

    private Double price;

    private Double discount;

    private Integer score;

    private Integer pagesPer;

    private String info;
    //卡券兑换码
    private String sn;

    private Date startTime;

    private Date endTime;

    private Double quota;

    private Date createdAt;

    private Date updatedAt;

    private String appidCardId;

    private Integer status;
    private Date useTime;
    private String orgNo;				//组织结构编码

	private String  serialNumber;      //支付宝卡券码
	//赠送者微信openId
	private String friendUserName;
	//赠送者卡券code
	private String oldUserCardCode;
    
  
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppidUserId() {
        return appidUserId;
    }

    public void setAppidUserId(String appidUserId) {
        this.appidUserId = appidUserId == null ? null : appidUserId.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getSubType() {
        return subType;
    }

    public void setSubType(Integer subType) {
        this.subType = subType;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getPagesPer() {
        return pagesPer;
    }

    public void setPagesPer(Integer pagesPer) {
        this.pagesPer = pagesPer;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info == null ? null : info.trim();
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn == null ? null : sn.trim();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Double getQuota() {
        return quota;
    }

    public void setQuota(Double quota) {
        this.quota = quota;
    }
    
    public Date getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getAppidCardId() {
        return appidCardId;
    }

    public void setAppidCardId(String appidCardId) {
        this.appidCardId = appidCardId == null ? null : appidCardId.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    public Date getUseTime() {
        return useTime;
    }
    public void setUseTime(Date useTime) {
        this.useTime = useTime;
    }
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getOrgNo() {
		return orgNo;
	}
	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public String getFriendUserName() {
		return friendUserName;
	}

	public void setFriendUserName(String friendUserName) {
		this.friendUserName = friendUserName;
	}

	public String getOldUserCardCode() {
		return oldUserCardCode;
	}

	public void setOldUserCardCode(String oldUserCardCode) {
		this.oldUserCardCode = oldUserCardCode;
	}
	
}
