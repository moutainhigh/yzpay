package com.yunpay.h5merch.dao.impl.card;

import org.springframework.stereotype.Repository;
import com.yunpay.h5merch.permission.dao.card.BaseInfoDao;
import com.yunpay.h5merch.permission.entity.card.BaseInfo;
import com.yunpay.permission.dao.impl.PermissionBaseDaoImpl;
/**
 *            积分规则DAO实现类
 * 类名称
 * 文件名称:     MemberCardDaoImpl.java
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
@Repository("baseInfoDao")
public class BaseInfoDaoImpl extends PermissionBaseDaoImpl implements BaseInfoDao  {

	@Override
	public BaseInfo getBaseInfo(Integer id)
	{
		return super.getSqlSession().selectOne(getStatement("getBaseInfo"), id);
	}

	@Override
	public int insertBaseInfo(BaseInfo baseInfo)
	{
		return super.getSqlSession().insert(getStatement("insertBaseInfo"), baseInfo);
	}

	@Override
	public int deleteBaseInfo(Integer id)
	{
		return super.getSqlSession().delete(getStatement("deleteBaseInfo"), id);
	}

	@Override
	public int updateBaseInfo(BaseInfo baseInfo)
	{
		return super.getSqlSession().update(getStatement("updateBaseInfo"), baseInfo);
	}
}
