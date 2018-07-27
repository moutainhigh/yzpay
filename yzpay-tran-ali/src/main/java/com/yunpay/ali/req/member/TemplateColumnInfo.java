package com.yunpay.ali.req.member;

import com.alibaba.fastjson.JSONObject;

public class TemplateColumnInfo {
	//标准栏位：行为由支付宝统一定，同时已经分配标准Code 
	//BALANCE：会员卡余额 
	//POINT：积分 
	//LEVEL：等级 
	//TELEPHONE：联系方式 
	//自定义栏位：行为由商户定义，自定义Code码（只要无重复）
	private String code;
	//1、openNative：打开二级页面，展现 more中descs 
	//2、openWeb：打开URL 
	//3、staticinfo：静态信息 
	//注意： 
	//不填则默认staticinfo； 
	//标准code尽量使用staticinfo，例如TELEPHONE商家电话栏位就只支持staticinfo；
	private String operate_type="openWeb";
	//栏目的标题
	private String title;
	//卡包详情页面，卡栏位右边展现的值 
	private String value;
	
	private JSONObject more_info;
	
	public TemplateColumnInfo(String code,String operateType,String title,String value){
		setCode(code);
		setOperate_type(operateType);
		setTitle(title);
		setValue(value);
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getOperate_type() {
		return operate_type;
	}
	public void setOperate_type(String operate_type) {
		this.operate_type = operate_type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public JSONObject getMore_info() {
		return more_info;
	}
	
	/**
	 * @Description: 设置扩展信息
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月12日下午3:49:31 
	 * @param title 二级页面标题
	 * @param url  超链接(选择openweb的时候必须填写url参数内容)
	 * @param params 扩展参数(需要URL地址回带的值)
	 * @param descs  选择opennative的时候必须填写descs的内容
	 */
	public void setMore_info(String title,String url,String params,String desc) {
		this.more_info = new JSONObject();
		this.more_info.put("title", title);
		this.more_info.put("url", url);
		this.more_info.put("params", params);
		String[] descs = new String[1];
		descs[0] = desc;
		this.more_info.put("descs", descs);
	}
	
}
