package com.yunpay.serv.rep.member;

public class MemberConsumRepDto {
	
	private String member_card_code;//会员卡号
	private float tran_amt;	//消费金额
	private float dis_amt;	//优惠金额
	private float bonus_amt;	//积分抵扣金额
	private int	use_bonus;	//抵扣的积分数
	private int give_bonus;	//当前消费赠送的积分数
	private float member_balance;	//会员余额
	private int member_bonus;	//会员剩余积分
	
	/**
	 * @param memberCardCode
	 * @param tranAmt
	 * @param disAmt
	 * @param scoreAmt
	 * @param useScore
	 * @param giveScore
	 * @param memberBalance
	 * @param memberScore
	 */
	public MemberConsumRepDto(String memberCardCode,float tranAmt,float disAmt,float bonusAmt,
			int useBonus,int giveBonus,float memberBalance,int memberBonus){
		this.setMember_card_code(memberCardCode);
		this.setTran_amt(tranAmt);
		this.setDis_amt(disAmt);
		this.setBonus_amt(bonusAmt);
		this.setUse_bonus(useBonus);
		this.setGive_bonus(giveBonus);
		this.setMember_balance(memberBalance);
		this.setMember_bonus(memberBonus);
	}
	
	public String getMember_card_code() {
		return member_card_code;
	}
	public void setMember_card_code(String member_card_code) {
		this.member_card_code = member_card_code;
	}
	public float getTran_amt() {
		return tran_amt;
	}
	public void setTran_amt(float tran_amt) {
		this.tran_amt = tran_amt;
	}
	public float getDis_amt() {
		return dis_amt;
	}
	public void setDis_amt(float dis_amt) {
		this.dis_amt = dis_amt;
	}
	public float getBonus_amt() {
		return bonus_amt;
	}
	public void setBonus_amt(float bonus_amt) {
		this.bonus_amt = bonus_amt;
	}
	public int getUse_bonus() {
		return use_bonus;
	}
	public void setUse_bonus(int use_bonus) {
		this.use_bonus = use_bonus;
	}
	public int getGive_bonus() {
		return give_bonus;
	}
	public void setGive_bonus(int give_bonus) {
		this.give_bonus = give_bonus;
	}
	public int getMember_bonus() {
		return member_bonus;
	}
	public void setMember_bonus(int member_bonus) {
		this.member_bonus = member_bonus;
	}
	public float getMember_balance() {
		return member_balance;
	}
	public void setMember_balance(float member_balance) {
		this.member_balance = member_balance;
	}
}
