package com.yunpay.wx.req.member;

import com.alibaba.fastjson.JSONObject;
import com.yunpay.common.utils.EnumUtil.CardDateType;

/**
 * 会员卡基本信息
 * 文件名称:     MemberBaseInfo.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年10月13日下午1:56:08 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年10月13日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class MemberBaseInfo {
	/*************必填项****************/
	//卡券的商户logo，建议像素为300*300
	private String logo_url;
	//卡券展示码类型
	private String code_type="CODE_TYPE_BARCODE";
	//支付功能结构体
	private JSONObject pay_info;
	//商户名字,字数上限为12个汉字
	private String brand_name;
	//卡券名，字数上限为9个汉字
	private String title;
	//券颜色。按色彩规范标注填写Color010-Color100
	private String color="Color010";
	//卡券使用提醒，字数上限为16个汉字
	private String notice;
	//卡券使用说明，字数上限为1024个汉字
	private String description;
	//商品信息
	private JSONObject sku;
	//使用日期，有效期的信息
	private JSONObject date_info;
	/*************选填项****************/
	//客服电话
	private String service_phone;
	//设置本卡券支持全部门店
	private Boolean use_all_locations=true;
	//卡券顶部居中的按钮显示内容
	private String center_title;
	//显示在入口下方的提示语
	private String center_sub_title;
	//顶部居中的url
	private String center_url;
	//每人可领券的数量限
	private Integer get_limit=1;
	
	//卡券领取页面是否可分享
	private Boolean can_share;
	//卡券是否可转赠
	private Boolean can_give_friend;
	//填写true为用户点击进入会员卡时推送事件，默认为false
	private Boolean need_push_on_view=false;
	//是否设置该会员卡中部的按钮同时支持微信支付刷卡和会员卡二维码
	private Boolean is_pay_and_qrcode = true;
	
	public MemberBaseInfo(){}
	
	/**
	 * 会员卡基本信息初始化(新增)
	 * @param logoUrl
	 * @param codeType
	 * @param brandName
	 * @param title
	 * @param color
	 * @param notice
	 * @param description
	 * @param quantity
	 * @param dateType
	 * @param beginTimestamp
	 * @param endTimestamp
	 * @param servicePhone
	 * @param getLimit
	 * @param canShare
	 * @param canGiveFirend
	 */
	public MemberBaseInfo(String logoUrl,String codeType,
			String brandName,String title,String color,String notice,String description,
			Integer quantity,String dateType,String servicePhone,Boolean canShare,Boolean canGiveFirend){
		this.setLogo_url(logoUrl);
		this.setCode_type(codeType);
		//设置会员卡支持拉起微信支付
		this.setPay_info(true);
		this.setBrand_name(brandName);
		this.setTitle(title);
		this.setColor(color);
		this.setNotice(notice);
		this.setDescription(description);
		this.setSku(quantity);
		this.setDate_info(dateType);
		this.setService_phone(servicePhone);
		this.setCan_share(canShare);
		this.setCan_give_friend(canGiveFirend);
	}
	
	/**
	 * 会员卡基本信息初始化(修改)
	 * @param logoUrl
	 * @param codeType
	 * @param brandName
	 * @param title
	 * @param color
	 * @param notice
	 * @param description
	 * @param servicePhone
	 * @param canShare
	 * @param canGiveFirend
	 */
	public MemberBaseInfo(String logoUrl,String codeType,String title,String color,String notice,
			String description,String servicePhone,Boolean canShare,Boolean canGiveFirend){
		this.setLogo_url(logoUrl);
		this.setCode_type(codeType);
		this.setTitle(title);
		this.setColor(color);
		this.setNotice(notice);
		this.setDescription(description);
		this.setService_phone(servicePhone);
		this.setCan_share(canShare);
		this.setCan_give_friend(canGiveFirend);
	}
	
	public String getLogo_url() {
		return logo_url;
	}
	public void setLogo_url(String logo_url) {
		this.logo_url = logo_url;
	}
	public String getCode_type() {
		return code_type;
	}
	public void setCode_type(String code_type) {
		this.code_type = code_type;
	}
	public String getBrand_name() {
		return brand_name;
	}
	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getNotice() {
		return notice;
	}
	public void setNotice(String notice) {
		this.notice = notice;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public JSONObject getSku() {
		return sku;
	}
	
	public void setSku(Integer quantity) {
		this.sku = new JSONObject();
		this.sku.put("quantity", quantity);
	}
	
	public JSONObject getDate_info() {
		return date_info;
	}
	
	/**
	 * @Description:设置会员卡有效期 
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月1日下午4:39:46 
	 * @param type
	 * @param begin_timestamp 卡券生效时间(起)
	 * @param end_timestamp 卡券生效时间(止)
	 * @param fixed_term 领取后多少天内有效
	 * @param fixed_begin_term 领取后多少天开始生效
	 */
	public void setDate_info(String beginTimestamp,String endEimestamp) {
		this.date_info = new JSONObject();
		this.date_info.put("type", CardDateType.FIX_TIME_RANGE.getCode());
		//卡券生效时间(起)
		this.date_info.put("begin_timestamp", beginTimestamp);
		//卡券生效时间(止)
		this.date_info.put("end_timestamp", endEimestamp);
	}
	
	public void setDate_info(String type){
		this.date_info = new JSONObject();
		this.date_info.put("type", type);
	}
	
	public Boolean isUse_all_locations() {
		return use_all_locations;
	}
	public void setUse_all_locations(Boolean use_all_locations) {
		this.use_all_locations = use_all_locations;
	}
	public Integer getGet_limit() {
		return get_limit;
	}
	public void setGet_limit(Integer get_limit) {
		this.get_limit = get_limit;
	}
	public Boolean isCan_share() {
		return can_share;
	}
	public void setCan_share(Boolean can_share) {
		this.can_share = can_share;
	}
	public Boolean isCan_give_friend() {
		return can_give_friend;
	}
	public void setCan_give_friend(Boolean can_give_friend) {
		this.can_give_friend = can_give_friend;
	}
	public String getService_phone() {
		return service_phone;
	}
	public void setService_phone(String service_phone) {
		this.service_phone = service_phone;
	}
	public String getCenter_title() {
		return center_title;
	}
	public void setCenter_title(String center_title) {
		this.center_title = center_title;
	}
	public String getCenter_sub_title() {
		return center_sub_title;
	}
	public void setCenter_sub_title(String center_sub_title) {
		this.center_sub_title = center_sub_title;
	}
	public String getCenter_url() {
		return center_url;
	}
	public void setCenter_url(String center_url) {
		this.center_url = center_url;
	}
	
	public JSONObject getPay_info() {
		return pay_info;
	}
	//设置是否支持会员卡拉起微信支付
	public void setPay_info(Boolean isSwipeCard) {
		JSONObject jsonObj = new JSONObject();
		//是否设置该会员卡支持拉出微信支付刷卡界面
		jsonObj.put("is_swipe_card", isSwipeCard);
		this.pay_info = new JSONObject();
		this.pay_info.put("swipe_card", jsonObj);
	}
	
	public Boolean getNeed_push_on_view() {
		return need_push_on_view;
	}
	public void setNeed_push_on_view(Boolean need_push_on_view) {
		this.need_push_on_view = need_push_on_view;
	}

	public Boolean getIs_pay_and_qrcode() {
		return is_pay_and_qrcode;
	}

	public void setIs_pay_and_qrcode(Boolean is_pay_and_qrcode) {
		this.is_pay_and_qrcode = is_pay_and_qrcode;
	}
	
	
}
