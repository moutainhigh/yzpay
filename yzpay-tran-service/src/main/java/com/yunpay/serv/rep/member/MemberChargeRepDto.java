package com.yunpay.serv.rep.member;


public class MemberChargeRepDto {
	private String member_card_code;		//会员卡号
	private float recharge_amt;	//充值金额
	private float give_amt;	//赠送金额
	private float member_balance;	//充后余额
	private Integer member_bonus;
	
	
	/**
	 * 构造会员充值接口返回参数
	 * @param memberCardCode
	 * @param rechargeAmt
	 * @param giveAmt
	 * @param memberBalance
	 */
	public MemberChargeRepDto(String memberCardCode,float rechargeAmt,
			float giveAmt,float memberBalance,Integer member_bonus){
		setMember_card_code(memberCardCode);
		setRecharge_amt(rechargeAmt);
		setGive_amt(giveAmt);
		setMember_balance(memberBalance);
		setMember_bonus(member_bonus);
	}
	
	
	public String getMember_card_code() {
		return member_card_code;
	}
	public void setMember_card_code(String member_card_code) {
		this.member_card_code = member_card_code;
	}
	public float getMember_balance() {
		return member_balance;
	}
	public void setMember_balance(float member_balance) {
		this.member_balance = member_balance;
	}

	public float getRecharge_amt() {
		return recharge_amt;
	}

	public void setRecharge_amt(float recharge_amt) {
		this.recharge_amt = recharge_amt;
	}

	public float getGive_amt() {
		return give_amt;
	}

	public void setGive_amt(float give_amt) {
		this.give_amt = give_amt;
	}


	public Integer getMember_bonus() {
		return member_bonus;
	}

	public void setMember_bonus(Integer member_bonus) {
		this.member_bonus = member_bonus;
	}
	
	
}
