package com.yunpay.wx.rep.card;

import com.yunpay.common.utils.ResultEnum.ResultCode;
/**
 * 文件名称:    CardConsumRep.java
 * 内容摘要:    微信卡券核销结果返回封装类
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年8月10日下午5:04:40 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年8月10日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class CardConsumeRep extends AppBaseRep{
	private String card_id;
	private String openid;
	
	public CardConsumeRep(String errCode,String errCodeDes){
		super(errCode,errCodeDes);
	}
	
	public CardConsumeRep(String cardId,String openid,String returnCode){
		super.setReturn_code(ResultCode.SUCCESS.getCode());
		setCard_id(cardId);
		setOpenid(openid);
	}

	public String getCard_id() {
		return card_id;
	}

	public void setCard_id(String card_id) {
		this.card_id = card_id;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
	
}
