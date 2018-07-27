package com.yunpay.ali.req.member;

import com.yunpay.ali.req.card.CardCommonReq;

/**
 * 文件名称:    MemUserFieldQryReq.java
 * 内容摘要:    查询支付宝会员用户填写的表单信息
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2018年3月7日下午1:42:50 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2018年3月7日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2018
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class MemUserFieldQryReq extends CardCommonReq{
	
	private String access_token;
	private String template_id;
	private String request_id;
	
	/**
	 * 构造请求参数
	 * @param appId
	 * @param privateKey
	 * @param alipayPublicKey
	 * @param accessToken
	 * @param bizType
	 * @param templateId
	 * @param requestId
	 */
	public MemUserFieldQryReq(String appId,String privateKey,String alipayPublicKey,
			String accessToken,String templateId,String requestId){
		setApp_id(appId);
		setPrivateKey(privateKey);
		setAlipayPublicKey(alipayPublicKey);
		setAccess_token(accessToken);
		setTemplate_id(templateId);
		setRequest_id(requestId);
	}
	
	
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	
	public String getTemplate_id() {
		return template_id;
	}
	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}
	public String getRequest_id() {
		return request_id;
	}
	public void setRequest_id(String request_id) {
		this.request_id = request_id;
	}
	
	
}
