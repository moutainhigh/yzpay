package com.yunpay.serv.rep.member;

import com.yunpay.common.utils.DateUtil;
import com.yunpay.serv.model.Membercard;

/**
 * 会员信息查询返回参数
 * 文件名称:     MemberQueryRepDto.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年9月28日下午2:01:05 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年9月28日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class MemberQueryRepDto {
	
	private String member_card_code;
	private String mobile;
	private Integer bonus;
	private float balance;
	private String nickname;
	private String name;
	private String birthday;
	private String activate_time;
	private Integer status;
	
	
	public MemberQueryRepDto(Membercard membercard){
		this.setMember_card_code(membercard.getUserCardCode());
		this.setMobile(membercard.getMobile());
		this.setBonus(membercard.getBonus());
		this.setBalance(membercard.getBalance());
		this.setNickname(membercard.getNickname());
		this.setName(membercard.getName());
		this.setBirthday(membercard.getBirthday());
		this.setActivate_time(DateUtil.getDateTime(membercard.getCreatedAt()));
		this.setStatus(membercard.getStatus());
		
	}
	
	public String getMember_card_code() {
		return member_card_code;
	}
	public void setMember_card_code(String member_card_code) {
		this.member_card_code = member_card_code;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Integer getBonus() {
		return bonus;
	}
	public void setBonus(Integer bonus) {
		this.bonus = bonus;
	}
	public float getBalance() {
		return balance;
	}
	public void setBalance(float balance) {
		this.balance = balance;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getActivate_time() {
		return activate_time;
	}
	public void setActivate_time(String activate_time) {
		this.activate_time = activate_time;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}
