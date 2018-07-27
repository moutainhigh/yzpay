package com.yunpay.h5merch.permission.entity.card;

/**
 * 
 * 类名称                     会员卡积分规则表
 * 文件名称:     IntegralRule.java
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

public class IntegralRule{
	private static final long serialVersionUID = 1035280495160680854L;
	//关联会员卡积分规则表 t_member_integral_rule
	private Integer id;//积分规则表Id
	private String merchantNo;// 所属商户ID
	private Integer templateId;//会员卡模板ID
	private Integer initIncreaseBonus;// 初始积分
	private Integer costMoneyUnit;// 消费金额
	private Integer increaseBonus;// 每次消费达到条件值后赠送的积分数
	private Integer maxIncreaseBonus;// 单次获得的积分上限
	private Integer costBonusUnit;// 每次使用多少积分
	private Integer reduceMoney;// 抵扣的金额
	private Integer leastMoneyToUseBonus;// 抵扣条件(满xx元可用)
	private Integer maxReduceBonus;// 单笔最多使用xx积分
	private String orgNo;// 组织机构编码
	private Integer status;// 积分规则状态1：启用，2：停用
	public Integer getId()
	{
		return id;
	}
	public void setId(Integer id)
	{
		this.id = id;
	}
	public String getMerchantNo()
	{
		return merchantNo;
	}
	public void setMerchantNo(String merchantNo)
	{
		this.merchantNo = merchantNo;
	}
	public Integer getTemplateId()
	{
		return templateId;
	}
	public void setTemplateId(Integer templateId)
	{
		this.templateId = templateId;
	}
	public Integer getInitIncreaseBonus()
	{
		return initIncreaseBonus;
	}
	public void setInitIncreaseBonus(Integer initIncreaseBonus)
	{
		this.initIncreaseBonus = initIncreaseBonus;
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
	public String getOrgNo()
	{
		return orgNo;
	}
	public void setOrgNo(String orgNo)
	{
		this.orgNo = orgNo;
	}
	public Integer getStatus()
	{
		return status;
	}
	public void setStatus(Integer status)
	{
		this.status = status;
	}
	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}
	
}
