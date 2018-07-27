package com.yunpay.wx.req.member;

/**
 * 会员积分规则参数类
 * 文件名称:     BonusRule.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年8月25日上午10:18:06 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年8月25日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class BonusRule {
	//消费金额(分为单位)
	private Integer cost_money_unit;
	//对应增加的积分
	private Integer increase_bonus;
	//单次获得的积分上限
	private Integer max_increase_bonus;
	//初始积分
	private Integer init_increase_bonus;
	//每使用积分数
	private Integer cost_bonus_unit;
	//抵扣金额
	private Integer reduce_money;
	//抵扣条件(满xx元可用)
	private Integer least_money_to_use_bonus;
	//单笔最多使用xx积分
	private Integer max_reduce_bonus;
	
	
	public BonusRule(){}
	
	/**
	 * 会员积分规则初始化
	 * @param cost_money_unit
	 * @param increase_bonus
	 * @param max_increase_bonus
	 * @param init_increase_bonus
	 * @param cost_bonus_unit
	 * @param reduce_money
	 * @param least_money_to_use_bonus
	 * @param max_reduce_bonus
	 * @param discount
	 */
	public BonusRule(Integer costMoneyUnit,Integer increaseBonus,Integer maxIncreaseBonus,
			Integer initIncreaseBonus,Integer costBonusUnit,Integer reduceMoney,
			Integer leastMoneyToUseBonus,Integer maxReduceBonus){
		this.setCost_money_unit(costMoneyUnit);
		this.setIncrease_bonus(increaseBonus);
		this.setMax_increase_bonus(maxIncreaseBonus);
		this.setInit_increase_bonus(initIncreaseBonus);
		this.setCost_bonus_unit(costBonusUnit);
		this.setReduce_money(reduceMoney);
		this.setLeast_money_to_use_bonus(leastMoneyToUseBonus);
		this.setMax_reduce_bonus(maxReduceBonus);
		
	}
	
	public Integer getCost_money_unit() {
		return cost_money_unit;
	}
	public void setCost_money_unit(Integer cost_money_unit) {
		this.cost_money_unit = cost_money_unit;
	}
	public Integer getIncrease_bonus() {
		return increase_bonus;
	}
	public void setIncrease_bonus(Integer increase_bonus) {
		this.increase_bonus = increase_bonus;
	}
	public Integer getMax_increase_bonus() {
		return max_increase_bonus;
	}
	public void setMax_increase_bonus(Integer max_increase_bonus) {
		this.max_increase_bonus = max_increase_bonus;
	}
	public Integer getInit_increase_bonus() {
		return init_increase_bonus;
	}
	public void setInit_increase_bonus(Integer init_increase_bonus) {
		this.init_increase_bonus = init_increase_bonus;
	}
	public Integer getCost_bonus_unit() {
		return cost_bonus_unit;
	}
	public void setCost_bonus_unit(Integer cost_bonus_unit) {
		this.cost_bonus_unit = cost_bonus_unit;
	}
	public Integer getReduce_money() {
		return reduce_money;
	}
	public void setReduce_money(Integer reduce_money) {
		this.reduce_money = reduce_money;
	}
	public Integer getLeast_money_to_use_bonus() {
		return least_money_to_use_bonus;
	}
	public void setLeast_money_to_use_bonus(Integer least_money_to_use_bonus) {
		this.least_money_to_use_bonus = least_money_to_use_bonus;
	}
	public Integer getMax_reduce_bonus() {
		return max_reduce_bonus;
	}
	public void setMax_reduce_bonus(Integer max_reduce_bonus) {
		this.max_reduce_bonus = max_reduce_bonus;
	}
	
	
}
