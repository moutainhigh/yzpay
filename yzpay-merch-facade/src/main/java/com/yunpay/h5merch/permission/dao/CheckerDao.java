package com.yunpay.h5merch.permission.dao;
import java.util.List;
import java.util.Map;

import com.yunpay.common.core.page.PageBean;
import com.yunpay.common.core.page.PageParam;
import com.yunpay.h5merch.permission.entity.SumTransLs;
import com.yunpay.permission.dao.PermissionBaseDao;
import com.yunpay.permission.entity.LoginFailRecord;
@SuppressWarnings("rawtypes")
public interface CheckerDao extends PermissionBaseDao  {

	public PageBean<SumTransLs> listReport(PageParam pageParam, Map<String, Object> paramMap);
	
	
	public List<SumTransLs> reportDetail(Map<String,Object> map);
	
	/**
	 * 
	* 输出日交易图形报表数据:按时间段
	* @param 
	* @return List<SumTransLs>
	* @throws
	 */
	List<SumTransLs> printDayReport(Map<String,Object> map);
	
	/**
	 * 
	* 查询某个商户的总交易笔数、总交易金额:按时间段
	* @param 
	* @return SumTransLs
	* @throws
	 */
	SumTransLs sumTrans(Map<String,Object> map);
	
	
	
	/**
	 * 按日统计某个商户的总交易笔数和总交易金额 
	* 
	* @param 
	* @return SumTransLs
	* @throws
	 */
	public SumTransLs sumTransByDay(Map<String,Object> map);
	
		
	
	
	/**
	 * 按周统计某个商户的总交易笔数和总交易金额 
	* 
	* @param 
	* @return SumTransLs
	* @throws
	 */
	public SumTransLs sumTransByWeek(Map<String,Object> map);
	
	
	
	/**
	 * 按月统计某个商户的总交易笔数和总交易金额 
	* 
	* @param 
	* @return SumTransLs
	* @throws
	 */
	public SumTransLs sumTransByMonth(Map<String,Object> map);
	/**
	 * 输出 按日交易的报表 
	* 
	* @param 
	* @return SumTransLs
	* @throws
	 */
	List<SumTransLs> printReportByDay(Map<String,Object> map);
	/**
	 * 输出 按周交易的报表
	* 
	* @param 
	* @return List<SumTransLs>
	* @throws
	 */
	List<SumTransLs> printReportByWeek(Map<String,Object> map);
	/**
	 * 输出 按月交易的报表 
	* 
	* @param 
	* @return SumTransLs
	* @throws
	 */
	List<SumTransLs> printReportByMonth(Map<String,Object> map);
	/**
	 * 同步招手猫app数据到商户用户表中
	* 
	* @param 
	* @return void
	* @throws
	 */
	public void syncUser(Map<String,Object> m);
	
	
	/**
	 * 查询账号是否锁定
	 */
	public Map<Object,Object> isLock(Map<String,Object> m);
	
	/**
	 * 更新用户的账号登录失败记录
	* 
	* @param 
	* @return void
	* @throws
	 */
	public void updateLoginRecord(Map<String,Object> m);
	
	/**
	 * 保存用户的账号登录失败记录
	* 
	* @param 
	* @return void
	* @throws
	 */
	public void saveErrorRecord(Map<String,Object> m);
	
	/**
	 *  获取用户登录失败的记录,包括(userid,登录日期、失败次数)
	* 
	* @param 
	* @return LoginFailRecord
	* @throws
	 */
	public LoginFailRecord getUserLoginFailRecord(Map<String,Object> map);
	
	/**
	 * 删除用户的账号登录失败记录
	* 
	* @param 
	* @return void
	* @throws
	 */
	public void delLoginRecord(String loginName);
}
