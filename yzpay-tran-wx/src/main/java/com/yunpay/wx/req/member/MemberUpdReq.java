package com.yunpay.wx.req.member;

/**
 * 修改会员信息
 * 文件名称:     MemberUpdReq.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年10月13日下午1:56:33 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年10月13日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class MemberUpdReq {
	private String code;
	private String card_id;
	private Integer bonus;
	private Integer add_bonus;
	private String record_bonus;
	private Integer balance;
	private Integer add_balance;
	private String record_balance;
	private String custom_field_value1;
	private String custom_field_value2;
	private String custom_field_value3;
	
	public MemberUpdReq(){}
	
	/**
	 * @param code
	 * @param cardId
	 * @param bonus
	 * @param addBonus
	 * @param recordBonus
	 * @param balance
	 * @param addBalance
	 * @param recordBalance
	 */
	public MemberUpdReq(String code,String cardId,Integer bonus,
			Integer addBonus,String recordBonus){
		this.setCode(code);
		this.setCard_id(cardId);
		this.setBonus(bonus);
		this.setAdd_bonus(addBonus);
		this.setRecord_bonus(recordBonus);
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCard_id() {
		return card_id;
	}
	public void setCard_id(String card_id) {
		this.card_id = card_id;
	}
	public Integer getBonus() {
		return bonus;
	}
	public void setBonus(Integer bonus) {
		this.bonus = bonus;
	}
	public Integer getAdd_bonus() {
		return add_bonus;
	}
	public void setAdd_bonus(Integer add_bonus) {
		this.add_bonus = add_bonus;
	}
	public String getRecord_bonus() {
		return record_bonus;
	}
	public void setRecord_bonus(String record_bonus) {
		this.record_bonus = record_bonus;
	}
	public Integer getBalance() {
		return balance;
	}
	public void setBalance(Integer balance) {
		this.balance = balance;
	}
	public Integer getAdd_balance() {
		return add_balance;
	}
	public void setAdd_balance(Integer add_balance) {
		this.add_balance = add_balance;
	}
	public String getRecord_balance() {
		return record_balance;
	}
	public void setRecord_balance(String record_balance) {
		this.record_balance = record_balance;
	}
	public String getCustom_field_value1() {
		return custom_field_value1;
	}
	public void setCustom_field_value1(String custom_field_value1) {
		this.custom_field_value1 = custom_field_value1;
	}
	public String getCustom_field_value2() {
		return custom_field_value2;
	}
	public void setCustom_field_value2(String custom_field_value2) {
		this.custom_field_value2 = custom_field_value2;
	}
	public String getCustom_field_value3() {
		return custom_field_value3;
	}
	public void setCustom_field_value3(String custom_field_value3) {
		this.custom_field_value3 = custom_field_value3;
	}
	
	
}
