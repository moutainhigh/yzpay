package com.yunpay.wx.rep.pay;

/**
 * 文件名称:    WechatAppletRep.java
 * 内容摘要:    微信小程序支付返回结果封装类
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2018年3月1日上午10:32:00 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2018年3月1日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2018
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class WechatAppletRep extends WechatQrRep{
	//交易类型(必需)
	private String trade_type;
	//预支付交易会话标识(必需)
	private String prepay_id;
	
	public WechatAppletRep(){
		
	}
	
	/**
	 * 用于构造系统中返回的错误信息
	 * @param return_code
	 * @param return_msg
	 * @param err_code
	 * @param err_code_des
	 */
	public WechatAppletRep(String return_code,String return_msg,String err_code,String err_code_des){
		super( return_code, return_msg, err_code, err_code_des);
	}
	
	public String getTrade_type() {
		return trade_type;
	}
	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}
	public String getPrepay_id() {
		return prepay_id;
	}
	public void setPrepay_id(String prepay_id) {
		this.prepay_id = prepay_id;
	}
	
	
}
