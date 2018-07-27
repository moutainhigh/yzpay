package com.yunpay.wx.rep.card;

import com.yunpay.common.utils.ResultEnum.ResultCode;

/**
 * 文件名称:     CreateCardRep.java
 * 内容摘要: 	     微信卡券创建结果返回封装类
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2018年3月1日上午10:07:09 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2018年3月1日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2018
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class CreateCardRep extends AppBaseRep{
	//卡券Id
	private String card_id;
	
	public CreateCardRep(String cardId){
		setReturn_code(ResultCode.SUCCESS.getCode());
		setCard_id(cardId);
	}
	
	public CreateCardRep(String errCode,String errCodeDes){
		super(errCode,errCodeDes);
	}
	

	public String getCard_id() {
		return card_id;
	}

	public void setCard_id(String card_id) {
		this.card_id = card_id;
	}
	
	
}
