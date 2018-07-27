package com.yunpay.h5merch.dao.impl.card;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yunpay.h5merch.permission.dao.card.RechargeLsDao;
import com.yunpay.h5merch.permission.entity.card.RechargeLs;
import com.yunpay.permission.dao.impl.PermissionBaseDaoImpl;
/**
 * 
 * 类名称                      会员消费记录DAO
 * 文件名称:     ConsumLsDaoImpl.java
 * 内容摘要: 
 * @author:   Zhao Xiaoman
 * @version:  1.0  
 * @Date:     2017年10月17日上午11:17:00
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
@Repository("rechargeLsDao")
public class RechargeLsDaoImpl extends PermissionBaseDaoImpl implements RechargeLsDao  {


	@Override
	public List<RechargeLs> getRechargeLs(Map<String, String> data)
	{
		return super.getSqlSession().selectList(getStatement("getRechargeLs"), data);
	}

	@Override
	public Double getTotal(Map<String, String> data)
	{
		return super.getSqlSession().selectOne(getStatement("getTotal"), data);
	}
	@Override
	public Double getSendTotal(Map<String, String> data)
	{
		return super.getSqlSession().selectOne(getStatement("getSendTotal"), data);
	}
}
