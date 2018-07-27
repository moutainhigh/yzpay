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
public class MerchData {
	private String code;  // 状态码
	private String message;  // 状态描述
	private MerchData2 data; // 数据主体
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public MerchData2 getData() {
		return data;
	}
	public void setData(MerchData2 data) {
		this.data = data;
	}
	
	
}
