package com.yunpay.ali.req.member;

public class TemplateStyleInfo {
	//会员卡背面显示名称
	private String card_show_name;
	//logo图片id
	private String logo_id;
	//背景图片id
	private String background_id;
	//背景颜色
	private String bg_color;
	
	public TemplateStyleInfo(String cardShowName,String logoId,String backgroundId,String bgColor){
		setCard_show_name(cardShowName);
		setLogo_id(logoId);
		setBackground_id(backgroundId);
		setBg_color(bgColor);
	}
	
	public String getCard_show_name() {
		return card_show_name;
	}
	public void setCard_show_name(String card_show_name) {
		this.card_show_name = card_show_name;
	}
	public String getLogo_id() {
		return logo_id;
	}
	public void setLogo_id(String logo_id) {
		this.logo_id = logo_id;
	}
	public String getBackground_id() {
		return background_id;
	}
	public void setBackground_id(String background_id) {
		this.background_id = background_id;
	}
	public String getBg_color() {
		return bg_color;
	}
	public void setBg_color(String bg_color) {
		this.bg_color = bg_color;
	}
}
