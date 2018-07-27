package com.yunpay.wx.req.card;

/**
 * 文件名称:    CardReq.java
 * 内容摘要: 	     微信卡券接口请求参数封装类
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2018年3月1日上午11:07:09 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2018年3月1日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2018
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class CardReq {
	//优惠券类型
	private String card_type;
	
	public String getCard_type() {
		return card_type;
	}
	public void setCard_type(String card_type) {
		this.card_type = card_type;
	}
	
	private String card_id;

	public String getCard_id() {
		return card_id;
	}
	public void setCard_id(String card_id) {
		this.card_id = card_id;
	}
	
	
}
