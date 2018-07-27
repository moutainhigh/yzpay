package com.yunpay.h5merch.permission.dao.card;

import java.util.List;
import java.util.Map;

import com.yunpay.h5merch.permission.entity.card.ConsumLs;
import com.yunpay.h5merch.permission.entity.card.UserReAndCon;
import com.yunpay.permission.dao.PermissionBaseDao;
/**
 * 
 * 类名称                     会员消费记录
 * 文件名称:     ConsumLsDao.java
 * 内容摘要: 
 * @author:   Zhao Xiaoman
 * @version:  1.0  
 * @Date:     2017年10月17日上午10:41:01
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年10月17日   Zhao Xiaoman       1.0       新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
@SuppressWarnings("rawtypes")
public interface ConsumLsDao extends PermissionBaseDao {
	/**
	 * 根据卡号查询消费记录
	 * @param Map<String, String> data
	 * @return
	 */
	public List<ConsumLs> getConsumLs(Map<String, String> data);
	/**
	 * 根据卡号查询消费记录总和
	 * @param Map<String, String> data
	 * @return
	 */
	public Double getTotal(Map<String, String> data);	
	/**
	 * 根据卡号查询消费记录总和
	 * @param Map<String, String> data
	 * @return
	 */
	public List<UserReAndCon> getReAndConList(Map<String, String> data);	
}
