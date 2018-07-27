package com.yunpay.ali.req.member;

import com.alibaba.fastjson.JSONObject;
import com.yunpay.ali.req.card.CardCommonReq;

/**
 * 为用户开卡
 * 文件名称:     MemUserCardOpenReq.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2018年3月7日下午2:28:19 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2018年3月7日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2018
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class MemUserCardOpenReq extends CardCommonReq{
	
	private String accessToken;
	private String bizContent;
	
	public MemUserCardOpenReq(String appId,String privateKey,String alipayPublicKey,
			String accessToken,String outSerialNo,String cardTemplateId,
			String userUniId,String externalCardNo,String openDate,String validDate,
			String level,String point,String balance){
		setApp_id(appId);
		setPrivateKey(privateKey);
		setAlipayPublicKey(alipayPublicKey);
		setAccessToken(accessToken);
		
		JSONObject cardUserInfo = new JSONObject();
		cardUserInfo.put("user_uni_id", userUniId);
		cardUserInfo.put("user_uni_id_type", "UID");
		
		JSONObject cardExtInfo = new JSONObject();
		cardExtInfo.put("external_card_no", externalCardNo);
		cardExtInfo.put("open_date", openDate);
		cardExtInfo.put("valid_date", validDate);
		cardExtInfo.put("level", level);
		cardExtInfo.put("point", point);
		cardExtInfo.put("balance", balance);
		
		JSONObject reqBiz = new JSONObject();
		reqBiz.put("out_serial_no", outSerialNo);
		reqBiz.put("card_template_id", cardTemplateId);
		reqBiz.put("card_user_info", cardUserInfo);
		reqBiz.put("card_ext_info", cardExtInfo);
		setBizContent(reqBiz.toJSONString());
	}
	
	

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getBizContent() {
		return bizContent;
	}

	public void setBizContent(String bizContent) {
		this.bizContent = bizContent;
	}
	
}
