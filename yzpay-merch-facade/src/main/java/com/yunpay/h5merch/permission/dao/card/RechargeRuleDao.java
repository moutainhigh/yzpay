package com.yunpay.h5merch.permission.dao.card;

import java.util.List;

import com.yunpay.h5merch.permission.entity.card.RechargeRule;
import com.yunpay.permission.dao.PermissionBaseDao;
/**
 * 
 * 类名称                     会员卡储值规则Dao
 * 文件名称:     RechargeRuleDao
 * 内容摘要: 
 * @author:   Zhao Xiaoman
 * @version:  1.0  
 * @Date:     2017年9月6日下午2:40:10
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
public interface RechargeRuleDao extends PermissionBaseDao {
	/**
	 * 根据商户号查询储值规则
	 * @param merchantNo
	 * @return
	 */
	public List<RechargeRule> getRechargeRule(String merchantNo);
	/**
	 * 根据id查询储值规则
	 * @param id
	 * @return
	 */
	public RechargeRule getRechargeRuleById(Integer id);
	/**
	 * 增加会员卡储值规则
	 * @param rechargeRule
	 * @return
	 */
	public int insertRechargeRule(RechargeRule rechargeRule);

	/**
	 * 根据会员卡商户号删除储值规则
	 * @param merchantNo
	 * @return
	 */
	public int deleteRechargeRule(String merchantNo);
	/**
	 * 根据id删除储值规则
	 * @param id
	 * @return
	 */
	public int deleteRechargeRuleById(Integer id);
	/**
	 * 修改会员卡储值规则
	 * @param rechargeRule
	 * @return
	 */
	public int updateRechargeRule(RechargeRule rechargeRule);	
}
