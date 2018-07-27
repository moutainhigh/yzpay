package com.yunpay.ali.req.member;

public class TemplateBenefitInfo {
	//权益描述
	private String title;
	//权益描述信息
	private String[] benefit_desc;
	//开始时间
	private String start_date;
	//权益结束时间
	private String end_date;
	
	public TemplateBenefitInfo(String title,String benefitDesc,String startDate,String endDate){
		setTitle(title);
		benefit_desc = new String[1];
		benefit_desc[0]=benefitDesc;
		setStart_date(startDate);
		setEnd_date(endDate);
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String[] getBenefit_desc() {
		return benefit_desc;
	}

	public void setBenefit_desc(String[] benefit_desc) {
		this.benefit_desc = benefit_desc;
	}

	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
}
