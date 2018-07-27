package com.yunpay.ali.req.member;

import com.alibaba.fastjson.JSONObject;
import com.yunpay.ali.req.card.CardCommonReq;

/**
 * 
 * 文件名称:    MemberFieldReq.java
 * 内容摘要:    会员卡开卡字段设置
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年9月12日下午4:34:53 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年9月12日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class MemCardFieldReq extends CardCommonReq{
	
	private String bizContent;
	/**
	 * 初始化请求参数
	 * @param appId
	 * @param privateKey
	 * @param alipayPublicKey
	 * @param templateId
	 * @param requireFields
	 * @param commonFields
	 */
	public MemCardFieldReq(String appId,String privateKey,String alipayPublicKey,
			String templateId,String[] requiredFields,String[] commonFields){
		setApp_id(appId);
		setPrivateKey(privateKey);
		setAlipayPublicKey(alipayPublicKey);
		JSONObject reqCommonFields = new JSONObject();
		JSONObject optCommonFields = new JSONObject();
		reqCommonFields.put("common_fields", requiredFields);
		optCommonFields.put("common_fields", commonFields);
		JSONObject fields = new JSONObject();
		fields.put("required", reqCommonFields);
		fields.put("optional", optCommonFields);
		JSONObject jsonBiz = new JSONObject();
		jsonBiz.put("template_id", templateId);
		jsonBiz.put("fields", fields);
		setBizContent(jsonBiz.toString());
	}
	public String getBizContent() {
		return bizContent;
	}
	public void setBizContent(String bizContent) {
		this.bizContent = bizContent;
	}
	
	
	
	
}
