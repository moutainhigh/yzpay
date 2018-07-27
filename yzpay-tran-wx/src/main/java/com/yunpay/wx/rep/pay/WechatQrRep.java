package com.yunpay.wx.rep.pay;

/**
 * 文件名称:    WechatRepData.java
 * 内容摘要:    微信二维码支付(扫码+条码)返回结果公共类
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年6月19日下午2:39:43 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年6月19日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class WechatQrRep {
	//返回状态码(必须)
	private String return_code;
	//返回信息
	private String return_msg;
	/***return_code为SUCCESS时返回下列参数***/
	//服务商appid(必须)
	private String appid;
	//子商户appid
	private String sub_appid;
	//服务商商户号(必须)
	private String mch_id;
	//子商户商户号(必须)
	private String sub_mch_id;
	//设备号
	private String device_info;
	//(必须)
	private String nonce_str;
	//(必须)
	private String sign;
	//业务结果
	private String result_code;
	//错误代码
	private String err_code;
	//错误代码描述
	private String err_code_des;
	
	
	public WechatQrRep(){
		
	}
	
	/**
	 * 用于构造系统中返回的错误信息
	 * @param return_code
	 * @param return_msg
	 * @param err_code
	 * @param err_code_des
	 */
	public WechatQrRep(String return_code,String return_msg,String err_code,String err_code_des){
		this.return_code = return_code;
		this.return_msg = return_msg;
		this.err_code = err_code;
		this.err_code_des = err_code_des;
	}
	
	public String getReturn_code() {
		return return_code;
	}
	public void setReturn_code(String return_code) {
		this.return_code = return_code;
	}
	public String getReturn_msg() {
		return return_msg;
	}
	public void setReturn_msg(String return_msg) {
		this.return_msg = return_msg;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getSub_appid() {
		return sub_appid;
	}
	public void setSub_appid(String sub_appid) {
		this.sub_appid = sub_appid;
	}
	public String getMch_id() {
		return mch_id;
	}
	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}
	public String getSub_mch_id() {
		return sub_mch_id;
	}
	public void setSub_mch_id(String sub_mch_id) {
		this.sub_mch_id = sub_mch_id;
	}
	public String getDevice_info() {
		return device_info;
	}
	public void setDevice_info(String device_info) {
		this.device_info = device_info;
	}
	public String getNonce_str() {
		return nonce_str;
	}
	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getResult_code() {
		return result_code;
	}
	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}
	public String getErr_code() {
		return err_code;
	}
	public void setErr_code(String err_code) {
		this.err_code = err_code;
	}
	public String getErr_code_des() {
		return err_code_des;
	}
	public void setErr_code_des(String err_code_des) {
		this.err_code_des = err_code_des;
	}
	
	
	
	
}
