package com.yunpay.h5merch.permission.dao;
import java.util.List;
import java.util.Map;

import com.yunpay.common.core.page.PageBean;
import com.yunpay.common.core.page.PageParam;
import com.yunpay.h5merch.permission.entity.SumTransLs;
import com.yunpay.permission.dao.PermissionBaseDao;

public interface TransLsDao extends PermissionBaseDao<SumTransLs> {
	
	/**
	 * 我的账单
	* 
	* @param 
	* @return PageBean<SumTransLs>
	* @throws
	 */
	PageBean<SumTransLs> bill(PageParam pageParam,Map<String, Object> param);
	
	/**
	 * 输出日交易图形报表数据:按时间段
	* @param 
	* @return List<SumTransLs>
	* @throws
	 */
	List<SumTransLs> printDayReport(Map<String,Object> map);
	
	
	/**
	 *  查询某个商户的总交易金额、总交易笔数:按时间段
	 */
	public SumTransLs sumTrans(Map<String, Object> map);
	
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
	
	
	
	

}
