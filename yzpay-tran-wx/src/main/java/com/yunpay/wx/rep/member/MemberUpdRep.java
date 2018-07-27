package com.yunpay.wx.rep.member;

import com.yunpay.common.utils.ResultEnum.ResultCode;
import com.yunpay.wx.rep.card.AppBaseRep;

/**
 * 文件名称:    MemberUpdRep.java
 * 内容摘要:    修改会员信息结果返回封装类
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年9月28日下午5:07:50 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年9月28日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class MemberUpdRep extends AppBaseRep{
	
	private Integer result_bonus;
	private Integer result_balance;
	private String openid;
	
	public MemberUpdRep(String errCode,String errMsg){
		super(errCode,errMsg);
	}
	
	public MemberUpdRep(Integer resultBonus,Integer resultBalance,String openId){
		super.setReturn_code(ResultCode.SUCCESS.getCode());
		this.setResult_bonus(resultBonus);
		this.setResult_balance(resultBalance);
		this.setOpenid(openId);
	}
	
	public Integer getResult_bonus() {
		return result_bonus;
	}
	public void setResult_bonus(Integer result_bonus) {
		this.result_bonus = result_bonus;
	}
	public Integer getResult_balance() {
		return result_balance;
	}
	public void setResult_balance(Integer result_balance) {
		this.result_balance = result_balance;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
	
	
	
	
}
