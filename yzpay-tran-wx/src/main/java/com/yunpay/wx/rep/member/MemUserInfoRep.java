package com.yunpay.wx.rep.member;

import java.util.Map;
import com.yunpay.wx.rep.card.AppBaseRep;

/**
 * 文件名称:    MemUserInfoRep.java
 * 内容摘要:    获取会员信息结果返回封装类
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年9月19日下午5:51:37 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年9月19日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class MemUserInfoRep extends AppBaseRep{
	//用户在公众号内的openid
	private String openid;
	//昵称
	private String nickname;
	//积分
	private Integer bonus;
	//余额
	private String balance;
	//性别
	private String sex;
	//会员状态
	private String user_card_status;
	//会员信息类目
	private Map<String,String> common_field_list;
	
	public MemUserInfoRep(){}
	
	/**
	 * 封装错误信息
	 * @param errCode
	 * @param errMsg
	 */
	public MemUserInfoRep(String errCode,String errMsg){
		super(errCode,errMsg);
	}
	
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public Integer getBonus() {
		return bonus;
	}
	public void setBonus(Integer bonus) {
		this.bonus = bonus;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getUser_card_status() {
		return user_card_status;
	}
	public void setUser_card_status(String user_card_status) {
		this.user_card_status = user_card_status;
	}

	public Map<String, String> getCommon_field_list() {
		return common_field_list;
	}

	public void setCommon_field_list(Map<String, String> common_field_list) {
		this.common_field_list = common_field_list;
	}
	
	
}
