package com.yunpay.ali.req.member;
public class TemplateBaseInfo {
	//请求id，需保证唯一(必选)
	private String request_id;
	//卡类型(必选)
	private String card_type="OUT_MEMBER_CARD";
	//业务卡号前缀(可选)
	private String biz_no_prefix;
	//业务卡号后缀长度(必选)
	private String biz_no_suffix_len;
	//展示码类型(必选)
	private String write_off_type;
	//卡模板样式信息(必选)
	private TemplateStyleInfo template_style_info;
	//会员权益信息(可选)
	private TemplateBenefitInfo[] template_benefit_info;
	//栏位信息(必选)
	private TemplateColumnInfo[] column_info_list;
	//字段规则列表，会员卡开卡过程中会员卡信息的生成规则(必选)
	private TemplateFieldRule[] field_rule_list;
	
	public TemplateBaseInfo(String requestId,String cardType,String bizNoPrefix,String bizNoSuffixLen,
			String writeOffType,TemplateStyleInfo templateStyleInfo,TemplateBenefitInfo[] templateBenefitInfo,
			TemplateColumnInfo[] columnInfoList,TemplateFieldRule[] fieldRuleList){
		setRequest_id(requestId);
		setCard_type(cardType);
		setBiz_no_prefix(bizNoPrefix);
		setBiz_no_suffix_len(bizNoSuffixLen);
		setWrite_off_type(writeOffType);
		setTemplate_style_info(templateStyleInfo);
		setTemplate_benefit_info(templateBenefitInfo);
		setColumn_info_list(columnInfoList);
		setField_rule_list(fieldRuleList);
	}
	
	public String getRequest_id() {
		return request_id;
	}
	public void setRequest_id(String request_id) {
		this.request_id = request_id;
	}
	public String getCard_type() {
		return card_type;
	}
	public void setCard_type(String card_type) {
		this.card_type = card_type;
	}
	public String getBiz_no_prefix() {
		return biz_no_prefix;
	}
	public void setBiz_no_prefix(String biz_no_prefix) {
		this.biz_no_prefix = biz_no_prefix;
	}
	public String getBiz_no_suffix_len() {
		return biz_no_suffix_len;
	}
	public void setBiz_no_suffix_len(String biz_no_suffix_len) {
		this.biz_no_suffix_len = biz_no_suffix_len;
	}
	public String getWrite_off_type() {
		return write_off_type;
	}
	public void setWrite_off_type(String write_off_type) {
		this.write_off_type = write_off_type;
	}
	public TemplateStyleInfo getTemplate_style_info() {
		return template_style_info;
	}
	
	
	public TemplateBenefitInfo[] getTemplate_benefit_info() {
		return template_benefit_info;
	}

	public void setTemplate_benefit_info(TemplateBenefitInfo[] template_benefit_info) {
		this.template_benefit_info = template_benefit_info;
	}

	public void setTemplate_style_info(TemplateStyleInfo template_style_info) {
		this.template_style_info = template_style_info;
	}
	public TemplateColumnInfo[] getColumn_info_list() {
		return column_info_list;
	}

	public void setColumn_info_list(TemplateColumnInfo[] column_info_list) {
		this.column_info_list = column_info_list;
	}

	public TemplateFieldRule[] getField_rule_list() {
		return field_rule_list;
	}

	public void setField_rule_list(TemplateFieldRule[] field_rule_list) {
		this.field_rule_list = field_rule_list;
	}
	
}
