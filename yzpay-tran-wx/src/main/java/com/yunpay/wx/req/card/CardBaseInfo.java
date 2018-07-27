package com.yunpay.wx.req.card;

import com.alibaba.fastjson.JSONObject;
import com.yunpay.common.utils.EnumUtil.CardDateType;

/**
 * 文件名称:     CardBaseInfo.java
 * 内容摘要:    微信卡券基础信息参数封装类
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2018年3月1日上午11:06:13 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2018年3月1日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2018
 * 公司:   深圳市至高通信技术发展有限公司
 */

public class CardBaseInfo {
	/*************必填项****************/
	//卡券的商户logo，建议像素为300*300
	private String logo_url;
	//卡券展示码类型
	private String code_type="CODE_TYPE_BARCODE";
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
	//使用时间的类型
	//private String type;
	//type为DATE_TYPE_FIX_TIME_RANGE时专用，表示起用时间
	//private String begin_timestamp;
	//表示结束时间
	//private String end_timestamp;
	//type为DATE_TYPE_FIX_TERM时专用，表示自领取后多少天内有效，不支持填写0
	//private String fixed_term;
	//type为DATE_TYPE_FIX_TERM时专用，表示自领取后多少天开始生效，领取后当天生效填写0
	//private String fixed_begin_term;
	
	/*************选填项****************/
	//客服电话
	private String service_phone;
	//设置本卡券支持全部门店
	private boolean use_all_locations=true;
	//卡券顶部居中的按钮显示内容
	private String center_title;
	//显示在入口下方的提示语
	private String center_sub_title;
	//顶部居中的url
	private String center_url;
	//每人可领券的数量限制,不填写默认为50
	private Integer get_limit;
	//每人可核销的数量限制,不填写默认为50
	private Integer use_limit;
	//卡券领取页面是否可分享
	private boolean can_share;
	//卡券是否可转赠
	private boolean can_give_friend;
	
	
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
	 * 
	 * @Description: 
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月1日下午4:39:46 
	 * @param type
	 * @param begin_timestamp 卡券生效时间(起)
	 * @param end_timestamp 卡券生效时间(止)
	 * @param fixed_term 领取后多少天内有效
	 * @param fixed_begin_term 领取后多少天开始生效
	 */
	public void setDate_info(String type,String begin_timestamp,
			String end_timestamp,Integer fixed_term,Integer fixed_begin_term) {
		this.date_info = new JSONObject();
		this.date_info.put("type", type);
		if(type.equals(CardDateType.FIX_TIME_RANGE.getCode())){
			//卡券生效时间(起)
			this.date_info.put("begin_timestamp", begin_timestamp);
			//卡券生效时间(止)
			this.date_info.put("end_timestamp", end_timestamp);
		}else{
			//表示自领取后多少天内有效(不支持0)
			this.date_info.put("fixed_term", fixed_term);
			//表示自领取后多少天开始生效，0表示领取后当天生效
			this.date_info.put("fixed_begin_term", fixed_begin_term);
			//卡券过期时间
			this.date_info.put("end_timestamp", end_timestamp);
		}
	}
	
	public boolean isUse_all_locations() {
		return use_all_locations;
	}
	public void setUse_all_locations(boolean use_all_locations) {
		this.use_all_locations = use_all_locations;
	}
	public Integer getGet_limit() {
		return get_limit;
	}
	public void setGet_limit(Integer get_limit) {
		this.get_limit = get_limit;
	}
	public Integer getUse_limit() {
		return use_limit;
	}
	public void setUse_limit(Integer use_limit) {
		this.use_limit = use_limit;
	}
	public boolean isCan_share() {
		return can_share;
	}
	public void setCan_share(boolean can_share) {
		this.can_share = can_share;
	}
	public boolean isCan_give_friend() {
		return can_give_friend;
	}
	public void setCan_give_friend(boolean can_give_friend) {
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
	public void setSku(JSONObject sku) {
		this.sku = sku;
	}
	public void setDate_info(JSONObject date_info) {
		this.date_info = date_info;
	}
	
	
	
}
