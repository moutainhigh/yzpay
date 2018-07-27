package com.yunpay.h5merch.permission.entity.card;

import java.util.Date;

/**
 * 
 * 类名称                     会员卡基础信息表
 * 文件名称:     CardBaseInfo.java
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

public class BaseInfo{
	private static final long serialVersionUID = 1035280495160680854L;
	//关联会员卡模板信息表t_membercard_baseinfo
	private Integer id; //会员卡基础信息id
	private String logoUrl; //卡券的商户logo网络存储地址
	private String logoLocalUrl; //卡券的商户logo本地存储地址
	private String codeType; //Code展示类型，"CODE_TYPE_TEXT"，文本；"CODE_TYPE_BARCODE"，一维码 ；"CODE_TYPE_QRCODE"，二维码；"CODE_TYPE_ONLY_QRCODE",二维码无code显示；"CODE_TYPE_ONLY_BARCODE",一维码无code显示；
	private String brandName; //商户名字,字数上限为12个汉字
	private String title; //卡券名
	private String subTitle; //卡券副标题
	private String color; //卡券颜色
	private String notice; //会员卡使用提醒
	private String description; //卡券使用说明
	private Integer skuQuantity; //卡券投放数量
	private String dateInfoType; //有效期，有效期的信息,json格式，如{"type": DATE_TYPE_FIX_TIME_RANGE }
	private Date beginTimes; //起用时间
	private Date endTimes; //表示结束时间
	private Integer fixedTerm; //表示自领取后多少天内有效，领取后当天有效填写0
	private Integer fixedBeginTerm; //表示自领取后多少天开始生效
	private Integer useCustomCode; //是否自定义Code码。填写0或1，默认为0
	private Integer bindOpenid; //是否指定用户领取，填写1或0。默认为0
	private String servicePhone;//客服电话
	private String locationIdList;//门店位置ID,json格式："location_id_list": [123,12321,345345];
	private String source;//第三方来源名，例如同程旅游、大众点评
	private String customUrlName;//自定义跳转外链的入口名字
	private String customUrl;//自定义跳转的URL
	private String customUrlSubTitle;//显示在入口右侧的提示语
	private String promotionUrlName;//营销场景的自定义入口名称
	private String promotionUrl;//入口跳转外链的地址链接
	private String promotionUrlSubTitle;//显示在营销入口右侧的提示语
	private Integer getLimit;//每人可领券的数量限制
	private Integer canShare;//卡券领取页面是否可分享,1:可分享，0：不可分享
	private Integer canGiveFriend;//卡券是否可转赠，1:可转赠，0：不可转赠
	private Integer needPushOnView;//填写true为用户点击进入会员卡时推送事件，默认为0
	//getter and setter
	public Integer getId()
	{
		return id;
	}
	public void setId(Integer id)
	{
		this.id = id;
	}
	public String getLogoUrl()
	{
		return logoUrl;
	}
	public void setLogoUrl(String logoUrl)
	{
		this.logoUrl = logoUrl;
	}
	public String getCodeType()
	{
		return codeType;
	}
	public void setCodeType(String codeType)
	{
		this.codeType = codeType;
	}
	public String getBrandName()
	{
		return brandName;
	}
	public void setBrandName(String brandName)
	{
		this.brandName = brandName;
	}
	public String getTitle()
	{
		return title;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}
	public String getSubTitle()
	{
		return subTitle;
	}
	public void setSubTitle(String subTitle)
	{
		this.subTitle = subTitle;
	}
	public String getColor()
	{
		return color;
	}
	public void setColor(String color)
	{
		this.color = color;
	}
	public String getNotice()
	{
		return notice;
	}
	public void setNotice(String notice)
	{
		this.notice = notice;
	}
	public String getDescription()
	{
		return description;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}
	public Integer getSkuQuantity()
	{
		return skuQuantity;
	}
	public void setSkuQuantity(Integer skuQuantity)
	{
		this.skuQuantity = skuQuantity;
	}
	public String getDateInfoType()
	{
		return dateInfoType;
	}
	public void setDateInfoType(String dateInfoType)
	{
		this.dateInfoType = dateInfoType;
	}
	public Date getBeginTimes()
	{
		return beginTimes;
	}
	public void setBeginTimes(Date beginTimes)
	{
		this.beginTimes = beginTimes;
	}
	public Date getEndTimes()
	{
		return endTimes;
	}
	public void setEndTimes(Date endTimes)
	{
		this.endTimes = endTimes;
	}
	public Integer getFixedTerm()
	{
		return fixedTerm;
	}
	public void setFixedTerm(Integer fixedTerm)
	{
		this.fixedTerm = fixedTerm;
	}
	public Integer getFixedBeginTerm()
	{
		return fixedBeginTerm;
	}
	public void setFixedBeginTerm(Integer fixedBeginTerm)
	{
		this.fixedBeginTerm = fixedBeginTerm;
	}
	public Integer getUseCustomCode()
	{
		return useCustomCode;
	}
	public void setUseCustomCode(Integer useCustomCode)
	{
		this.useCustomCode = useCustomCode;
	}
	public Integer getBindOpenid()
	{
		return bindOpenid;
	}
	public void setBindOpenid(Integer bindOpenid)
	{
		this.bindOpenid = bindOpenid;
	}
	public String getServicePhone()
	{
		return servicePhone;
	}
	public void setServicePhone(String servicePhone)
	{
		this.servicePhone = servicePhone;
	}
	public String getLocationIdList()
	{
		return locationIdList;
	}
	public void setLocationIdList(String locationIdList)
	{
		this.locationIdList = locationIdList;
	}
	public String getSource()
	{
		return source;
	}
	public void setSource(String source)
	{
		this.source = source;
	}
	public String getCustomUrlName()
	{
		return customUrlName;
	}
	public void setCustomUrlName(String customUrlName)
	{
		this.customUrlName = customUrlName;
	}
	public String getCustomUrl()
	{
		return customUrl;
	}
	public void setCustomUrl(String customUrl)
	{
		this.customUrl = customUrl;
	}
	public String getCustomUrlSubTitle()
	{
		return customUrlSubTitle;
	}
	public void setCustomUrlSubTitle(String customUrlSubTitle)
	{
		this.customUrlSubTitle = customUrlSubTitle;
	}
	public String getPromotionUrlName()
	{
		return promotionUrlName;
	}
	public void setPromotionUrlName(String promotionUrlName)
	{
		this.promotionUrlName = promotionUrlName;
	}
	public String getPromotionUrl()
	{
		return promotionUrl;
	}
	public void setPromotionUrl(String promotionUrl)
	{
		this.promotionUrl = promotionUrl;
	}
	public String getPromotionUrlSubTitle()
	{
		return promotionUrlSubTitle;
	}
	public void setPromotionUrlSubTitle(String promotionUrlSubTitle)
	{
		this.promotionUrlSubTitle = promotionUrlSubTitle;
	}
	public Integer getGetLimit()
	{
		return getLimit;
	}
	public void setGetLimit(Integer getLimit)
	{
		this.getLimit = getLimit;
	}
	public Integer getCanShare()
	{
		return canShare;
	}
	public void setCanShare(Integer canShare)
	{
		this.canShare = canShare;
	}
	public Integer getCanGiveFriend()
	{
		return canGiveFriend;
	}
	public void setCanGiveFriend(Integer canGiveFriend)
	{
		this.canGiveFriend = canGiveFriend;
	}
	public Integer getNeedPushOnView()
	{
		return needPushOnView;
	}
	public void setNeedPushOnView(Integer needPushOnView)
	{
		this.needPushOnView = needPushOnView;
	}
	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}
	public String getLogoLocalUrl()
	{
		return logoLocalUrl;
	}
	public void setLogoLocalUrl(String logoLocalUrl)
	{
		this.logoLocalUrl = logoLocalUrl;
	}
}
