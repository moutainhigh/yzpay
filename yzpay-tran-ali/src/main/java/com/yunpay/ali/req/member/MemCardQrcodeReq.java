package com.yunpay.ali.req.member;

import com.yunpay.ali.req.card.CardCommonReq;

/**
 * 文件名称:    MemCardQrcodeReq.java
 * 内容摘要: 	     获取会员卡投放链接(领取二维码)
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2018年3月6日下午2:03:00 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2018年3月6日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2018
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class MemCardQrcodeReq extends CardCommonReq{
	//卡券模板id(必输)
	private String template_id;
	//扩展信息，会员领卡完成后将此参数原样带回商户页面。
	private String out_string;
	//会员卡开卡表单提交后回调地址(必输)
	private String callback;
	//需要关注的生活号AppId
	private String follow_app_id;
	
	/**
	 * @param templateId  
	 * @param outString
	 * @param callback
	 * @param followAppId
	 */
	public MemCardQrcodeReq(String appId,String privateKey,String alipayPublicKey,
			String templateId,String outString,String callback,String followAppId){
		setApp_id(appId);
		setPrivateKey(privateKey);
		setAlipayPublicKey(alipayPublicKey);
		setTemplate_id(templateId);
		setOut_string(outString);
		setCallback(callback);
		setFollow_app_id(follow_app_id);
	}
	
	public String getTemplate_id() {
		return template_id;
	}
	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}
	public String getOut_string() {
		return out_string;
	}
	public void setOut_string(String out_string) {
		this.out_string = out_string;
	}
	public String getCallback() {
		return callback;
	}
	public void setCallback(String callback) {
		this.callback = callback;
	}
	public String getFollow_app_id() {
		return follow_app_id;
	}
	public void setFollow_app_id(String follow_app_id) {
		this.follow_app_id = follow_app_id;
	}
	
	
}
