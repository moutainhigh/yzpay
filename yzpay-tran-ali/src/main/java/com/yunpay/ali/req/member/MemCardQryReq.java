package com.yunpay.ali.req.member;

import com.yunpay.ali.req.card.CardCommonReq;
/**
 * 文件名称:    MemCardQryReq.java
 * 内容摘要: 	     会员卡模板查询请求参数封装类
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2018年3月6日上午10:44:03 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2018年3月6日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2018
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class MemCardQryReq extends CardCommonReq{
	
	//卡券模板id
	private String template_id;
	
	public MemCardQryReq(String appId,String privateKey,String alipayPublicKey,String templateId){
		setApp_id(appId);
		setPrivateKey(privateKey);
		setAlipayPublicKey(alipayPublicKey);
		setTemplate_id(templateId);
	}

	public String getTemplate_id() {
		return template_id;
	}

	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}
	
}
