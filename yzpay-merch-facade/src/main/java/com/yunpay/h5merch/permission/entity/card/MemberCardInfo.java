package com.yunpay.h5merch.permission.entity.card;

import java.util.Date;
import java.util.List;

/**
 * 
 * 类名称                     会员卡所有信息实体类
 * 文件名称:     MemberCardInfo.java
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

public class MemberCardInfo{
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1035280495160680854L;
	//关联 t_membercard_template
	private Integer templateId; //模板信息表Id x
	private String merchantNo; //商户编号 x
	private String cardType; //会员卡类型
	private Integer baseInfoId;//关联的会员卡基本信息 x
	private String prerogative;//会员卡特权说明
	private String autoActivate;//设置为1时用户领取会员卡后系统自动将其激活，无需调用激活接口
	private Integer wxActivate;//设置为1时会员卡支持一键开卡，不允许同时传入activate_url字段，否则设置wx_activate失效
	private Integer supplyBonus;//是否显示积分，填写1或0，如填写1，积分相关字段均为必填
	private String bonusUrl;//设置跳转外链查看积分详情
	private Integer supplyBalance;//是否支持储值，填写1或0
	private String balanceUrl;//设置跳转外链查看余额详情
	private String customField1NameType;//自定义会员信息类目的name_type
	private String customField1Url;//自定义会员信息类目的url
	private String customField2NameType;//自定义会员信息类目的name_type
	private String customField2Url;//自定义会员信息类目的url
	private String customField3NameType;//自定义会员信息类目的name_type
	private String customField3Url;//自定义会员信息类目的url
	private String bonusCleared;//积分清零规则
	private String bonusRules;//积分规则
	private String balanceRules;//储值说明
	private String activateUrl;//激活会员卡的url
	private String customCell1Name;//自定义会员信息类目入口名称
	private String customCell1Tips;//自定义会员信息类目入口右侧提示语
	private String customCell1Url;//自定义会员信息类目入口跳转链接
	private String bonusRule;//积分规则,json格式："bonus_rule": {"cost_money_unit": 100,"increase_bonus": 1,"max_increase_bonus": 200,"init_increase_bonus": 10}，用于微信买单功能。
	private Integer costMoneyUnit;//消费金额。以分为单位。
	private Integer increaseBonus;//对应增加的积分
	private Integer maxIncreaseBonus;//积分上限
	private Integer initIncreaseBonus;//初始设置积分
	private Integer discount;//折扣，该会员卡享受的折扣优惠
	private String cardId;//微信卡券ID
	private Integer status;//0,待审核；1,审核失败；2，通过审核；3，卡券被用户删除；4，在公众平台投放过的卡券
	private Integer throwinStatus;//投放状态，0：未投放，1：已投放
	private String showQrcodeUrl;//二维码显示地址
	private String ticket;//二维码ticket
	private Integer getNum;//领取人数
	private Integer activeNum;//激活人数
	private Integer quantity;//卡券库存数量
	private String posterUrl;//海报
	private Integer isWeixinMemberCardTemplate;//是否微信会员卡，0：不是，1：是
	private Integer isAlipassMemberCardTemplate;//是否支付宝会员卡，0：不是，1：是
	private String alipassCardId;//支付宝卡券ID
	private String showAlipassQrcodeUrl;//支付宝二维码显示地址
	private String appId;//支付宝appID
	private Integer defaultSend;//支付宝设置关注默认发送
	private String cardLogo;//会员卡logo，建议像素为300*300
	private String titleColor;//标题颜色 x
	private String cardnoColor;//卡号颜色
	private String url;//二维码地址
	private String wxCardHtmlUrl;//微信html5卡券货架地址
	private String wxCardHtmlContent;//微信群发嵌入HTML	
	//关联t_membercard_baseinfo
	private String logoUrl; //卡券的商户logo，建议像素为300*300
	private String codeType; //Code展示类型，"CODE_TYPE_TEXT"，文本；"CODE_TYPE_BARCODE"，一维码 ；"CODE_TYPE_QRCODE"，二维码；"CODE_TYPE_ONLY_QRCODE",二维码无code显示；"CODE_TYPE_ONLY_BARCODE",一维码无code显示；
	private String brandName; //商户名字,字数上限为12个汉字 x
	private String title; //卡券名
	private String subTitle; //卡券副标题   x
	private String color; //卡券颜色 x
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
	private String servicePhone;//客服电话 x
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
	/*//关联 t_membercard_grade 
	private Integer gradeId;//会员卡等级信息表 Id
	private String orgNo; //关联的组织机构编码
	private String name;//会员等级名称
	private Integer sort;//会员卡等级排序
	private Integer gradeStatus;//状态，0：正常，1：删除
	private Date createdAt;//创建时间
	private String createdBy;//创建时间
	private Date updatedAt;//更新时间
	private String updatedBy;//更新时间
*/	
	//关联 t_member_integral_rule
	private Integer ruleId;//积分规则表Id
	private Integer ruleInitIncreaseBonus;// 初始积分
	private Integer ruleCostMoneyUnit;// 消费金额
	private Integer ruleIncreaseBonus;// 每次消费达到条件值后赠送的积分数
	private Integer ruleMaxIncreaseBonus;// 单次获得的积分上限
	private Integer costBonusUnit;// 每次使用多少积分
	private Integer reduceMoney;// 抵扣的金额
	private Integer leastMoneyToUseBonus;// 抵扣条件(满xx元可用)
	private Integer maxReduceBonus;// 单笔最多使用xx积分
	private Integer ruleStatus;// 积分规则状态1：启用，2：停用
	private String ruleOrgNo;
	//关联 t_membercard_template_commonfield
	private List<String> fieldy;
	private List<String> fieldn;
	public Integer getTemplateId()
	{
		return templateId;
	}
	public void setTemplateId(Integer templateId)
	{
		this.templateId = templateId;
	}
	public String getMerchantNo()
	{
		return merchantNo;
	}
	public void setMerchantNo(String merchantNo)
	{
		this.merchantNo = merchantNo;
	}
	public String getCardType()
	{
		return cardType;
	}
	public void setCardType(String cardType)
	{
		this.cardType = cardType;
	}
	public Integer getBaseInfoId()
	{
		return baseInfoId;
	}
	public void setBaseInfoId(Integer baseInfoId)
	{
		this.baseInfoId = baseInfoId;
	}
	public String getPrerogative()
	{
		return prerogative;
	}
	public void setPrerogative(String prerogative)
	{
		this.prerogative = prerogative;
	}
	public String getAutoActivate()
	{
		return autoActivate;
	}
	public void setAutoActivate(String autoActivate)
	{
		this.autoActivate = autoActivate;
	}
	public Integer getWxActivate()
	{
		return wxActivate;
	}
	public void setWxActivate(Integer wxActivate)
	{
		this.wxActivate = wxActivate;
	}
	public Integer getSupplyBonus()
	{
		return supplyBonus;
	}
	public void setSupplyBonus(Integer supplyBonus)
	{
		this.supplyBonus = supplyBonus;
	}
	public String getBonusUrl()
	{
		return bonusUrl;
	}
	public void setBonusUrl(String bonusUrl)
	{
		this.bonusUrl = bonusUrl;
	}
	public Integer getSupplyBalance()
	{
		return supplyBalance;
	}
	public void setSupplyBalance(Integer supplyBalance)
	{
		this.supplyBalance = supplyBalance;
	}
	public String getBalanceUrl()
	{
		return balanceUrl;
	}
	public void setBalanceUrl(String balanceUrl)
	{
		this.balanceUrl = balanceUrl;
	}
	public String getCustomField1NameType()
	{
		return customField1NameType;
	}
	public void setCustomField1NameType(String customField1NameType)
	{
		this.customField1NameType = customField1NameType;
	}
	public String getCustomField1Url()
	{
		return customField1Url;
	}
	public void setCustomField1Url(String customField1Url)
	{
		this.customField1Url = customField1Url;
	}
	public String getCustomField2NameType()
	{
		return customField2NameType;
	}
	public void setCustomField2NameType(String customField2NameType)
	{
		this.customField2NameType = customField2NameType;
	}
	public String getCustomField2Url()
	{
		return customField2Url;
	}
	public void setCustomField2Url(String customField2Url)
	{
		this.customField2Url = customField2Url;
	}
	public String getCustomField3NameType()
	{
		return customField3NameType;
	}
	public void setCustomField3NameType(String customField3NameType)
	{
		this.customField3NameType = customField3NameType;
	}
	public String getCustomField3Url()
	{
		return customField3Url;
	}
	public void setCustomField3Url(String customField3Url)
	{
		this.customField3Url = customField3Url;
	}
	public String getBonusCleared()
	{
		return bonusCleared;
	}
	public void setBonusCleared(String bonusCleared)
	{
		this.bonusCleared = bonusCleared;
	}
	public String getBonusRules()
	{
		return bonusRules;
	}
	public void setBonusRules(String bonusRules)
	{
		this.bonusRules = bonusRules;
	}
	public String getBalanceRules()
	{
		return balanceRules;
	}
	public void setBalanceRules(String balanceRules)
	{
		this.balanceRules = balanceRules;
	}
	public String getActivateUrl()
	{
		return activateUrl;
	}
	public void setActivateUrl(String activateUrl)
	{
		this.activateUrl = activateUrl;
	}
	public String getCustomCell1Name()
	{
		return customCell1Name;
	}
	public void setCustomCell1Name(String customCell1Name)
	{
		this.customCell1Name = customCell1Name;
	}
	public String getCustomCell1Tips()
	{
		return customCell1Tips;
	}
	public void setCustomCell1Tips(String customCell1Tips)
	{
		this.customCell1Tips = customCell1Tips;
	}
	public String getCustomCell1Url()
	{
		return customCell1Url;
	}
	public void setCustomCell1Url(String customCell1Url)
	{
		this.customCell1Url = customCell1Url;
	}
	public String getBonusRule()
	{
		return bonusRule;
	}
	public void setBonusRule(String bonusRule)
	{
		this.bonusRule = bonusRule;
	}
	public Integer getCostMoneyUnit()
	{
		return costMoneyUnit;
	}
	public void setCostMoneyUnit(Integer costMoneyUnit)
	{
		this.costMoneyUnit = costMoneyUnit;
	}
	public Integer getIncreaseBonus()
	{
		return increaseBonus;
	}
	public void setIncreaseBonus(Integer increaseBonus)
	{
		this.increaseBonus = increaseBonus;
	}
	public Integer getMaxIncreaseBonus()
	{
		return maxIncreaseBonus;
	}
	public void setMaxIncreaseBonus(Integer maxIncreaseBonus)
	{
		this.maxIncreaseBonus = maxIncreaseBonus;
	}
	public Integer getInitIncreaseBonus()
	{
		return initIncreaseBonus;
	}
	public void setInitIncreaseBonus(Integer initIncreaseBonus)
	{
		this.initIncreaseBonus = initIncreaseBonus;
	}
	public Integer getDiscount()
	{
		return discount;
	}
	public void setDiscount(Integer discount)
	{
		this.discount = discount;
	}
	public String getCardId()
	{
		return cardId;
	}
	public void setCardId(String cardId)
	{
		this.cardId = cardId;
	}
	public Integer getStatus()
	{
		return status;
	}
	public void setStatus(Integer status)
	{
		this.status = status;
	}
	public Integer getThrowinStatus()
	{
		return throwinStatus;
	}
	public void setThrowinStatus(Integer throwinStatus)
	{
		this.throwinStatus = throwinStatus;
	}
	public String getShowQrcodeUrl()
	{
		return showQrcodeUrl;
	}
	public void setShowQrcodeUrl(String showQrcodeUrl)
	{
		this.showQrcodeUrl = showQrcodeUrl;
	}
	public String getTicket()
	{
		return ticket;
	}
	public void setTicket(String ticket)
	{
		this.ticket = ticket;
	}
	public Integer getGetNum()
	{
		return getNum;
	}
	public void setGetNum(Integer getNum)
	{
		this.getNum = getNum;
	}
	public Integer getActiveNum()
	{
		return activeNum;
	}
	public void setActiveNum(Integer activeNum)
	{
		this.activeNum = activeNum;
	}
	public Integer getQuantity()
	{
		return quantity;
	}
	public void setQuantity(Integer quantity)
	{
		this.quantity = quantity;
	}
	public String getPosterUrl()
	{
		return posterUrl;
	}
	public void setPosterUrl(String posterUrl)
	{
		this.posterUrl = posterUrl;
	}
	public Integer getIsWeixinMemberCardTemplate()
	{
		return isWeixinMemberCardTemplate;
	}
	public void setIsWeixinMemberCardTemplate(Integer isWeixinMemberCardTemplate)
	{
		this.isWeixinMemberCardTemplate = isWeixinMemberCardTemplate;
	}
	public Integer getIsAlipassMemberCardTemplate()
	{
		return isAlipassMemberCardTemplate;
	}
	public void setIsAlipassMemberCardTemplate(Integer isAlipassMemberCardTemplate)
	{
		this.isAlipassMemberCardTemplate = isAlipassMemberCardTemplate;
	}
	public String getAlipassCardId()
	{
		return alipassCardId;
	}
	public void setAlipassCardId(String alipassCardId)
	{
		this.alipassCardId = alipassCardId;
	}
	public String getShowAlipassQrcodeUrl()
	{
		return showAlipassQrcodeUrl;
	}
	public void setShowAlipassQrcodeUrl(String showAlipassQrcodeUrl)
	{
		this.showAlipassQrcodeUrl = showAlipassQrcodeUrl;
	}
	public String getAppId()
	{
		return appId;
	}
	public void setAppId(String appId)
	{
		this.appId = appId;
	}
	public Integer getDefaultSend()
	{
		return defaultSend;
	}
	public void setDefaultSend(Integer defaultSend)
	{
		this.defaultSend = defaultSend;
	}
	public String getCardLogo()
	{
		return cardLogo;
	}
	public void setCardLogo(String cardLogo)
	{
		this.cardLogo = cardLogo;
	}
	public String getTitleColor()
	{
		return titleColor;
	}
	public void setTitleColor(String titleColor)
	{
		this.titleColor = titleColor;
	}
	public String getCardnoColor()
	{
		return cardnoColor;
	}
	public void setCardnoColor(String cardnoColor)
	{
		this.cardnoColor = cardnoColor;
	}
	public String getUrl()
	{
		return url;
	}
	public void setUrl(String url)
	{
		this.url = url;
	}
	public String getWxCardHtmlUrl()
	{
		return wxCardHtmlUrl;
	}
	public void setWxCardHtmlUrl(String wxCardHtmlUrl)
	{
		this.wxCardHtmlUrl = wxCardHtmlUrl;
	}
	public String getWxCardHtmlContent()
	{
		return wxCardHtmlContent;
	}
	public void setWxCardHtmlContent(String wxCardHtmlContent)
	{
		this.wxCardHtmlContent = wxCardHtmlContent;
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
	public Integer getRuleId()
	{
		return ruleId;
	}
	public void setRuleId(Integer ruleId)
	{
		this.ruleId = ruleId;
	}
	public Integer getRuleInitIncreaseBonus()
	{
		return ruleInitIncreaseBonus;
	}
	public void setRuleInitIncreaseBonus(Integer ruleInitIncreaseBonus)
	{
		this.ruleInitIncreaseBonus = ruleInitIncreaseBonus;
	}
	public Integer getRuleCostMoneyUnit()
	{
		return ruleCostMoneyUnit;
	}
	public void setRuleCostMoneyUnit(Integer ruleCostMoneyUnit)
	{
		this.ruleCostMoneyUnit = ruleCostMoneyUnit;
	}
	public Integer getRuleIncreaseBonus()
	{
		return ruleIncreaseBonus;
	}
	public void setRuleIncreaseBonus(Integer ruleIncreaseBonus)
	{
		this.ruleIncreaseBonus = ruleIncreaseBonus;
	}
	public Integer getRuleMaxIncreaseBonus()
	{
		return ruleMaxIncreaseBonus;
	}
	public void setRuleMaxIncreaseBonus(Integer ruleMaxIncreaseBonus)
	{
		this.ruleMaxIncreaseBonus = ruleMaxIncreaseBonus;
	}
	public Integer getCostBonusUnit()
	{
		return costBonusUnit;
	}
	public void setCostBonusUnit(Integer costBonusUnit)
	{
		this.costBonusUnit = costBonusUnit;
	}
	public Integer getReduceMoney()
	{
		return reduceMoney;
	}
	public void setReduceMoney(Integer reduceMoney)
	{
		this.reduceMoney = reduceMoney;
	}
	public Integer getLeastMoneyToUseBonus()
	{
		return leastMoneyToUseBonus;
	}
	public void setLeastMoneyToUseBonus(Integer leastMoneyToUseBonus)
	{
		this.leastMoneyToUseBonus = leastMoneyToUseBonus;
	}
	public Integer getMaxReduceBonus()
	{
		return maxReduceBonus;
	}
	public void setMaxReduceBonus(Integer maxReduceBonus)
	{
		this.maxReduceBonus = maxReduceBonus;
	}
	public Integer getRuleStatus()
	{
		return ruleStatus;
	}
	public void setRuleStatus(Integer ruleStatus)
	{
		this.ruleStatus = ruleStatus;
	}
	public String getRuleOrgNo()
	{
		return ruleOrgNo;
	}
	public void setRuleOrgNo(String ruleOrgNo)
	{
		this.ruleOrgNo = ruleOrgNo;
	}
	public List<String> getFieldy()
	{
		return fieldy;
	}
	public void setFieldy(List<String> fieldy)
	{
		this.fieldy = fieldy;
	}
	public List<String> getFieldn()
	{
		return fieldn;
	}
	public void setFieldn(List<String> fieldn)
	{
		this.fieldn = fieldn;
	}
}
