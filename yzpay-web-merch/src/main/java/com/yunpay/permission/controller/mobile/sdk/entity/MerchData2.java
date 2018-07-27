package com.yunpay.permission.controller.mobile.sdk.entity;
/**
 * 
 * 文件名称:
 * 内容摘要: 调用招手猫商户数据同步接口返回的json数据模型,通过手机号获取商户数据接口
 * @author:   duan zhang quan
 * @version:  1.0  
 * @Date:     2017年4月14日上午9:56:12 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年4月14日     duan zhang quan   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class MerchData2 {
	private String phone;  // 手机号
	private String pass;   // md5加密过后的密码
	private String lon;   // 经度
	private String lat;  // 纬度
	private String address; // 地址
	private String store_phone; // 门店电话
	private String logo; //店铺log
	private String license; // 营业执照地址
	private String smart_key;  // 商户密码约定加密密钥,string(32),随机字母数字字符串
	private String device_num; // 设备号，设备唯一标识,与商户手机号一对一绑定
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getLon() {
		return lon;
	}
	public void setLon(String lon) {
		this.lon = lon;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getStore_phone() {
		return store_phone;
	}
	public void setStore_phone(String store_phone) {
		this.store_phone = store_phone;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getLicense() {
		return license;
	}
	public void setLicense(String license) {
		this.license = license;
	}
	public String getSmart_key() {
		return smart_key;
	}
	public void setSmart_key(String smart_key) {
		this.smart_key = smart_key;
	}
	public String getDevice_num() {
		return device_num;
	}
	public void setDevice_num(String device_num) {
		this.device_num = device_num;
	}
	
	
	
}
