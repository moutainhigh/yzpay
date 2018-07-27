package com.yunpay.wx.req.member;

/**
 * 会员卡激活参数设置
 * 文件名称:     ActivateFieldReq.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年10月13日下午1:55:53 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年10月13日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class ActivateFieldReq {
	
	//卡券id
	private String card_id;
	//必填参数
	private String [] require_fields;
	//选填参数
	private String [] common_fields;
	//必填参数是否支持修改
	private boolean require_can_modify = false;
	//选填参数是否支持修改
	private boolean common_can_modify = true;
	
	public ActivateFieldReq(){
		
	}
	
	public ActivateFieldReq(String cardId,String[] requireFields,String[]commonFields){
		this.setCard_id(cardId);
		this.setRequire_fields(requireFields);
		this.setCommon_fields(commonFields);
	}
	
	public String getCard_id() {
		return card_id;
	}
	public void setCard_id(String card_id) {
		this.card_id = card_id;
	}
	public String[] getRequire_fields() {
		return require_fields;
	}
	public void setRequire_fields(String[] require_fields) {
		this.require_fields = require_fields;
	}
	public String[] getCommon_fields() {
		return common_fields;
	}
	public void setCommon_fields(String[] common_fields) {
		this.common_fields = common_fields;
	}
	public boolean isRequire_can_modify() {
		return require_can_modify;
	}
	public void setRequire_can_modify(boolean require_can_modify) {
		this.require_can_modify = require_can_modify;
	}
	public boolean isCommon_can_modify() {
		return common_can_modify;
	}
	public void setCommon_can_modify(boolean common_can_modify) {
		this.common_can_modify = common_can_modify;
	}
	
	
}
