package com.yunpay.wx.req.member;

/**
 * 会员卡信息修改
 * 文件名称:     MemCardUpdReq.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年10月13日下午2:20:58 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年10月13日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class MemCardUpdReq {
	private String card_id;
	private MemberCardReq member_card;
	 
	
	public MemCardUpdReq(String cardId,MemberCardReq memberCard){
		this.setCard_id(cardId);
		this.setMember_card(memberCard);
	}
	
	public String getCard_id() {
		return card_id;
	}
	public void setCard_id(String card_id) {
		this.card_id = card_id;
	}
	public MemberCardReq getMember_card() {
		return member_card;
	}
	public void setMember_card(MemberCardReq member_card) {
		this.member_card = member_card;
	}
	
	
}
