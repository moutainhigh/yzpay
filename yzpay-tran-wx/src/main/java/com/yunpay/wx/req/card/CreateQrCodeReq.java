package com.yunpay.wx.req.card;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * 文件名称:     CreateQrCodeReq.java
 * 内容摘要:    创建卡券领取二维码接口请求参数封装类
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2018年3月1日上午11:07:51 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2018年3月1日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2018
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class CreateQrCodeReq {
	
	private String action_name="QR_CARD";
	//二维码有效期
	private String expire_seconds;
	//投放信息
	private JSONObject action_info;
	
	
	
	public String getAction_name() {
		return action_name;
	}
	public void setAction_name(String action_name) {
		this.action_name = action_name;
	}
	public String getExpire_seconds() {
		return expire_seconds;
	}
	public void setExpire_seconds(String expire_seconds) {
		this.expire_seconds = expire_seconds;
	}
	public JSONObject getAction_info() {
		return action_info;
	}
	
	/**
	 * @Description: 设置卡券投放详情
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月2日下午2:26:34 
	 * @param cardId  卡券id
	 * @param outId 场景值
	 * @param isUniqueCode  是否只能
	 */
	public void setAction_info(String cardId) {
		JSONObject card = new JSONObject();
		card.put("card_id", cardId);
		//投放场景值
		card.put("outer_str", "qrCode");
		this.action_info = new JSONObject();
		this.action_info.put("card", card);
	}
	
	/**
	 * @Description: 设置卡券投放详情
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月2日下午2:26:34 
	 * @param cardId  卡券id
	 * @param outId 场景值
	 * @param isUniqueCode  是否只能
	 */
	public void setAction_info(String cardId,String storeNo) {
		JSONObject card = new JSONObject();
		card.put("card_id", cardId);
		//投放场景值
		if(StringUtils.isNotBlank(storeNo)){
			card.put("outer_str", storeNo);
		}
		this.action_info = new JSONObject();
		this.action_info.put("card", card);
	}
	
	
	
}
