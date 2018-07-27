package com.yunpay.h5merch.dao.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yunpay.common.core.page.PageBean;
import com.yunpay.common.core.page.PageParam;
import com.yunpay.h5merch.permission.dao.CheckerDao;
import com.yunpay.h5merch.permission.entity.SumTransLs;
import com.yunpay.permission.dao.impl.PermissionBaseDaoImpl;
import com.yunpay.permission.entity.LoginFailRecord;
@SuppressWarnings("rawtypes")
@Repository("checkerDao")
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
public class CheckerDaoImpl extends PermissionBaseDaoImpl implements CheckerDao {
	
	//分页查询营业日报
	@SuppressWarnings("unchecked")
	public PageBean<SumTransLs> listReport(PageParam pageParam,Map<String, Object> paramMap){
		if(pageParam == null){
	 		return new PageBean<SumTransLs>();
	 	}else{
	 	     if (paramMap == null) {
		            paramMap = new HashMap<String, Object>();
		        }
		        // 统计总记录数
		        int totalCount = super.getSqlSession().selectOne(getStatement("countReport"), paramMap);
		        // 校验当前页数
		        int currentPage = PageBean.checkCurrentPage(totalCount, pageParam.getPageSize(), pageParam.getPageCurrent());
		        pageParam.setPageCurrent(currentPage); // 为当前页重新设值
		        // 校验页面输入的每页记录数numPerPage是否合法
		        int numPerPage = PageBean.checkNumPerPage(pageParam.getPageSize()); // 校验每页记录数
		        pageParam.setPageSize(numPerPage); // 重新设值
		     // 根据页面传来的分页参数构造SQL分页参数
		        paramMap.put("pageIndex", (pageParam.getPageCurrent() - 1) * pageParam.getPageSize());      //当前页数
		        paramMap.put("pageSize", pageParam.getPageSize());            //每页显示多少条数据
		        // 获取分页数据集
		        List<Object> list = super.getSqlSession().selectList(getStatement("listReport"), paramMap);
		        // 构造分页对象
	            return new PageBean(pageParam.getPageCurrent(), pageParam.getPageSize(), totalCount, list);
		    }
	}

	/**
	 * 查询营业日报明细
	 */
	public List<SumTransLs> reportDetail(Map<String,Object> map){
		return super.getSqlSession().selectList(getStatement("reportDetail"), map);
	}
	
	
	 /**
	  * 输出日交易图形报表数据 ：按时间段
	  */
	@Override
	public List<SumTransLs> printDayReport(Map<String, Object> map) {
		return super.getSqlSession().selectList(getStatement("printDayReport"), map);
	}


	/**
	 *  查询某个商户的总交易金额、总交易笔数：按时间段
	 */
	public SumTransLs sumTrans(Map<String, Object> map){
		return super.getSqlSession().selectOne(getStatement("sumTrans"), map);
	}
	
	/**
	 * 按日统计某个商户的总交易笔数和总交易金额 
	* 
	* @param 
	* @return SumTransLs
	* @throws
	 */
	public SumTransLs sumTransByDay(Map<String,Object> map){
		return super.getSqlSession().selectOne(getStatement("sumTransByDay"), map);
	}
	
		
	
	
	/**
	 * 按周统计某个商户的总交易笔数和总交易金额 
	* 
	* @param 
	* @return SumTransLs
	* @throws
	 */
	public SumTransLs sumTransByWeek(Map<String,Object> map){
		return super.getSqlSession().selectOne(getStatement("sumTransByWeek"), map);
	}
	
	
	
	/**
	 * 按月统计某个商户的总交易笔数和总交易金额 
	* 
	* @param 
	* @return SumTransLs
	* @throws
	 */
	public SumTransLs sumTransByMonth(Map<String,Object> map){
		return super.getSqlSession().selectOne(getStatement("sumTransByMonth"), map);
	}
	
	
	/**
	 * 输出 按日交易的报表 
	* 
	* @param 
	* @return SumTransLs
	* @throws
	 */
	public List<SumTransLs> printReportByDay(Map<String,Object> map){
		return super.getSqlSession().selectList(getStatement("printReportByDay"), map);
	}
	
	/**
	 * 输出 按周交易的报表
	* 
	* @param 
	* @return List<SumTransLs>
	* @throws
	 */
	public List<SumTransLs> printReportByWeek(Map<String,Object> map){
		return super.getSqlSession().selectList(getStatement("printReportByWeek"), map);
	}
	
	/**
	 * 输出 按月交易的报表 
	* 
	* @param 
	* @return SumTransLs
	* @throws
	 */
	public List<SumTransLs> printReportByMonth(Map<String,Object> map){
		return super.getSqlSession().selectList(getStatement("printReportByMonth"), map);
	}
	
	/**
	 * 
	* 获取招手猫app商户数据,并同步到商户用户表中
	* @param 
	* @return void
	* @throws
	 */
	public void syncUser(Map<String,Object> map){
		super.getSqlSession().insert(super.getStatement("syncUser"),map);
	}
	
	/**
	 * 查询账号是否锁定
	 */
	public Map<Object,Object> isLock(Map<String,Object> m){
		return super.getSqlSession().selectMap(super.getStatement("isLock"), m, "loginFailCount");
		
	}
	

	
	/**
	 * 更新用户的账号登录失败记录
	* 
	* @param 
	* @return void
	* @throws
	 */
	public void updateLoginRecord(Map<String,Object> m){
		super.getSqlSession().insert(super.getStatement("updateLoginRecord"),m);
	}
	
	/**
	 * 保存用户的账号登录失败记录
	* 
	* @param 
	* @return void
	* @throws
	 */
	public void saveErrorRecord(Map<String,Object> m){
		super.getSqlSession().insert(super.getStatement("saveErrorRecord"),m);
	}
	
	/**
	 *  获取用户登录失败的记录,包括(userid,登录日期、失败次数)
	* 
	* @param 
	* @return LoginFailRecord
	* @throws
	 */
	public LoginFailRecord getUserLoginFailRecord(Map<String,Object> map) {
		
		return super.getSqlSession().selectOne(super.getStatement("getUserLoginFailRecord"), map);
	}
	
	
	/**
	 * 删除用户的账号登录失败记录
	* 
	* @param 
	* @return void
	* @throws
	 */
	public void delLoginRecord(String loginName){
		super.getSqlSession().insert(super.getStatement("delLoginRecord"),loginName);
	}
}
