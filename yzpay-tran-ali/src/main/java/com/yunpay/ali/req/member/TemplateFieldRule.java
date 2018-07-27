package com.yunpay.ali.req.member;

public class TemplateFieldRule {
	//字段名称，现在支持如下几个Key（暂不支持自定义） 
	//Balance：金额 
	//Point：整数 
	//Level：任意字符串 
	//OpenDate：开卡日期 
	//ValidDate：过期日期
	private String field_name;
	//规则名
	private String rule_name;
	//规则值
	private String rule_value;
	
	public TemplateFieldRule(String fieldName,String ruleName,String ruleValue){
		setField_name(fieldName);
		setRule_name(ruleName);
		setRule_value(ruleValue);
	}
	
	public String getField_name() {
		return field_name;
	}
	public void setField_name(String field_name) {
		this.field_name = field_name;
	}
	public String getRule_name() {
		return rule_name;
	}
	public void setRule_name(String rule_name) {
		this.rule_name = rule_name;
	}
	public String getRule_value() {
		return rule_value;
	}
	public void setRule_value(String rule_value) {
		this.rule_value = rule_value;
	}
	
	
}
