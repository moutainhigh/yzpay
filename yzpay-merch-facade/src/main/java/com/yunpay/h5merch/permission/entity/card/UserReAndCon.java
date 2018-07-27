package com.yunpay.h5merch.permission.entity.card;

import java.util.Date;

/**
 * 
 * 类名称                      查看余额
 * 文件名称:     UserReAndCon.java
 * 内容摘要: 
 * @author:   Zhao Xiaoman
 * @version:  1.0  
 * @Date:     2017年10月18日下午6:58:57
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年10月18日   Zhao Xiaoman       1.0       新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */

public class UserReAndCon{
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1035280495160680854L;
	//关联会员充值记录表t_member_recharge_ls
	private Double rechargeAmt;//充值金额（小数点后两位）
	private Double giveAmt;//赠送金额
	private Double tranAmt;//消费余额
	private Date createdAt;//产生时间
	public Double getRechargeAmt()
	{
		return rechargeAmt;
	}
	public void setRechargeAmt(Double rechargeAmt)
	{
		this.rechargeAmt = rechargeAmt;
	}
	public Double getGiveAmt()
	{
		return giveAmt;
	}
	public void setGiveAmt(Double giveAmt)
	{
		this.giveAmt = giveAmt;
	}
	public Double getTranAmt()
	{
		return tranAmt;
	}
	public void setTranAmt(Double tranAmt)
	{
		this.tranAmt = tranAmt;
	}
	public Date getCreatedAt()
	{
		return createdAt;
	}
	public void setCreatedAt(Date createdAt)
	{
		this.createdAt = createdAt;
	}
	
}
