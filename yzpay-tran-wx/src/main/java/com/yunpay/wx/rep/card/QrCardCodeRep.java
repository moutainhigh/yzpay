package com.yunpay.wx.rep.card;

import com.yunpay.common.utils.ResultEnum.ResultCode;

/**
 * 文件名称:     QrCardCodeRep.java
 * 内容摘要: 	     微信卡券查询结果返回封装类
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年8月10日下午4:48:59 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年8月10日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class QrCardCodeRep extends AppBaseRep{
	
	private String card_id;
	private String begin_time;
	private String end_time; 
	private String openid;
	private boolean can_consum;
	private String user_card_status;
	
	/**
	 * 返回失败
	 * @param errCode
	 * @param errCodeDes
	 */
	public QrCardCodeRep(String errCode,String errCodeDes){
		super(errCode,errCodeDes);
	}
	
	/**
	 * 返回成功过
	 * @param cardId  	卡券id
	 * @param openId  	用户openid
	 * @param canConsum	能否核销
	 * @param userCardStatus 卡券状态
	 */
	public QrCardCodeRep(String cardId,String openid,boolean canConsum,String userCardStatus){
		setReturn_code(ResultCode.SUCCESS.getCode());
		setCard_id(cardId);
		setOpenid(openid);
		setCan_consum(canConsum);
		setUser_card_status(userCardStatus);
	}
	
	public String getCard_id() {
		return card_id;
	}
	public void setCard_id(String card_id) {
		this.card_id = card_id;
	}
	public String getBegin_time() {
		return begin_time;
	}
	public void setBegin_time(String begin_time) {
		this.begin_time = begin_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public boolean isCan_consum() {
		return can_consum;
	}
	public void setCan_consum(boolean can_consum) {
		this.can_consum = can_consum;
	}
	public String getUser_card_status() {
		return user_card_status;
	}
	public void setUser_card_status(String user_card_status) {
		this.user_card_status = user_card_status;
	}
	
	
}
