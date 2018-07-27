package com.yunpay.h5merch.dao.impl.card;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yunpay.h5merch.permission.dao.card.RechargeRuleDao;
import com.yunpay.h5merch.permission.entity.card.RechargeRule;
import com.yunpay.permission.dao.impl.PermissionBaseDaoImpl;
/**
 *            储值规则DAO实现类
 * 类名称
 * 文件名称:     RechargeRuleDaoImpl.java
 * 内容摘要: 
 * @author:   Zhao Xiaoman
 * @version:  1.0  
 * @Date:     2017年9月6日下午4:13:20
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年9月6日   Zhao Xiaoman       1.0       新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
@SuppressWarnings("rawtypes")
@Repository("rechargeRuleDao")
public class RechargeRuleDaoImpl extends PermissionBaseDaoImpl implements RechargeRuleDao  {

	@Override
	public List<RechargeRule> getRechargeRule(String merchantNo)
	{
		return super.getSqlSession().selectList(getStatement("getRechargeRule"), merchantNo);
	}
	@Override
	public RechargeRule getRechargeRuleById(Integer id)
	{
		return super.getSqlSession().selectOne(getStatement("getRechargeRuleById"), id);
	}

	@Override
	public int insertRechargeRule(RechargeRule rechargeRule)
	{
		return super.getSqlSession().insert(getStatement("insertRechargeRule"), rechargeRule);
	}

	@Override
	public int deleteRechargeRule(String merchantNo)
	{
		return super.getSqlSession().delete(getStatement("deleteRechargeRule"), merchantNo);
	}
	@Override
	public int deleteRechargeRuleById(Integer id)
	{
		return super.getSqlSession().delete(getStatement("deleteRechargeRuleById"), id);
	}

	@Override
	public int updateRechargeRule(RechargeRule rechargeRule)
	{
		return super.getSqlSession().update(getStatement("updateRechargeRule"), rechargeRule);
	}
	

	
}
