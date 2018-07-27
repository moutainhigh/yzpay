package com.yunpay.serv.rep.card;

import com.yunpay.serv.req.card.CardReqDto;

public class CardRepDto extends CardReqDto{
	//背景色值
	private String back_ground;
	//卡券状态
	private String card_status;

	public String getBack_ground() {
		return back_ground;
	}
	public void setBack_ground(String back_ground) {
		this.back_ground = back_ground;
	}
	public String getCard_status() {
		return card_status;
	}
	public void setCard_status(String card_status) {
		this.card_status = card_status;
	}
	
	
}
