package com.yunpay.h5merch.permission.entity.card;

/**
 * 
 * 类名称                     会员数量实体类
 * 文件名称:     MemberNum.java
 * 内容摘要: 
 * @author:   Zhao Xiaoman
 * @version:  1.0  
 * @Date:     2017年10月16日下午7:14:36
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年10月16日   Zhao Xiaoman       1.0       新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */

public class UserCardNum{
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1035280495160680854L;
	//关联会员信息表t_member
	private Integer totalNum;//总的会员数量
	private Integer aliNum;//ali会员数量
	private Integer wxNum;//wx会员数量
	public Integer getTotalNum()
	{
		return totalNum;
	}
	public void setTotalNum(Integer totalNum)
	{
		this.totalNum = totalNum;
	}
	public Integer getAliNum()
	{
		return aliNum;
	}
	public void setAliNum(Integer aliNum)
	{
		this.aliNum = aliNum;
	}
	public Integer getWxNum()
	{
		return wxNum;
	}
	public void setWxNum(Integer wxNum)
	{
		this.wxNum = wxNum;
	}
}
