package com.yunpay.serv.model;

/**
 * 商户积分规则信息
 * 文件名称:     MemberIntegralRule.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年9月21日下午2:22:10 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年9月21日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class MemberBonusRule {
	//规则id
	private Integer id;
	//商户号
	private String merchantNo;
	//初始积分(激活赠送)
	private Integer init_increase_bonus;
	//消费金额(元)
	private Float cost_money_unit;
	//每次消费达到条件值后赠送的积分数
	private Integer increase_bonus;
	//单次获得的积分上限
	private Integer max_increase_bonus;
	//每使用多少积分
	private Integer cost_bonus_unit;
	//抵扣的金额
	private Float reduce_money;
	//抵扣条件(满xx元可用)
	private Float least_money_to_use_bonus;
	//单笔最多使用xx积分
	private Integer max_reduce_bonus;
	private String orgNo;
	//状态
	private Integer status;
	//折扣
	private Integer discount;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMerchantNo() {
		return merchantNo;
	}
	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}
	public Integer getInit_increase_bonus() {
		return init_increase_bonus;
	}
	public void setInit_increase_bonus(Integer init_increase_bonus) {
		this.init_increase_bonus = init_increase_bonus;
	}
	public Float getCost_money_unit() {
		return cost_money_unit;
	}
	public void setCost_money_unit(Float cost_money_unit) {
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
	public Integer getCost_bonus_unit() {
		return cost_bonus_unit;
	}
	public void setCost_bonus_unit(Integer cost_bonus_unit) {
		this.cost_bonus_unit = cost_bonus_unit;
	}
	public Float getReduce_money() {
		return reduce_money;
	}
	public void setReduce_money(Float reduce_money) {
		this.reduce_money = reduce_money;
	}
	public Float getLeast_money_to_use_bonus() {
		return least_money_to_use_bonus;
	}
	public void setLeast_money_to_use_bonus(Float least_money_to_use_bonus) {
		this.least_money_to_use_bonus = least_money_to_use_bonus;
	}
	public Integer getMax_reduce_bonus() {
		return max_reduce_bonus;
	}
	public void setMax_reduce_bonus(Integer max_reduce_bonus) {
		this.max_reduce_bonus = max_reduce_bonus;
	}
	public String getOrgNo() {
		return orgNo;
	}
	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getDiscount() {
		return discount;
	}
	public void setDiscount(Integer discount) {
		this.discount = discount;
	}
	
	
}
