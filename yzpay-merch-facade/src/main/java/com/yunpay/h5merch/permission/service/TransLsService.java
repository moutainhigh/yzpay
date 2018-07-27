package com.yunpay.h5merch.permission.service;
import java.util.List;
import java.util.Map;
import com.yunpay.common.core.page.PageBean;
import com.yunpay.common.core.page.PageParam;
import com.yunpay.h5merch.permission.entity.SumTransLs;
public interface TransLsService {
	/**
	 * 
	 * 文件名称:
	 * 内容摘要: 
	 * @author:   duan zhang quan
	 * @version:  1.0  
	 * @Date:     2017年4月14日上午9:56:12 
	 * 
	 * 修改历史:  
	 * 修改日期                     修改人员                                   版本	            修改内容  
	 * ----------------------------------------------  
	 * 2017年4月14日     duan zhang quan   1.0     新建
	 *
	 * 版权:   版权所有(C)2017
	 * 公司:   深圳市至高通信技术发展有限公司
	 */
	
	
	/**
	 * 查询营业日报
	* 
	* @param 
	* @return List<SumTransLs>
	* @throws
	 */
	PageBean<SumTransLs> dayReport(PageParam pageParam,Map<String,Object> param);
	
	
	/**
	 * 
	* 输出日交易图形报表数据(按时间段)
	* @param 
	* @return List<SumTransLs>
	* @throws
	 */
	List<SumTransLs> printDayReport(Map<String,Object> map);
	
	/**
	 * 
	* 查询某个商户的总交易笔数、总交易金额
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
	* @return SumTransLs
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
	 * 查询营业日报明细
	* 
	* @param 
	* @return SumTransLs
	* @throws
	 */
	List<SumTransLs> reportDetail(Map<String,Object> param);
	
	/**
	 * 我的账单
	* 
	* @param 
	* @return PageBean<SumTransLs>
	* @throws
	 */
	PageBean<SumTransLs> bill(PageParam pageParam,Map<String, Object> param);
}
