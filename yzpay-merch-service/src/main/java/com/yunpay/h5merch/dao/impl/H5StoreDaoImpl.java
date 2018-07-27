package com.yunpay.h5merch.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yunpay.h5merch.permission.dao.H5StoreDao;
import com.yunpay.h5merch.permission.entity.H5StoreEntity;
import com.yunpay.permission.dao.impl.PermissionBaseDaoImpl;
import com.yunpay.permission.entity.Merchant;
/**
 * 
 * 类名称                     门店DAO实现类
 * 文件名称:     StoreDaoImpl.java
 * 内容摘要: 
 * @author:   Zhao Xiaoman
 * @version:  1.0  
 * @Date:     2017年7月12日上午9:50:17
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年7月12日   Zhao Xiaoman       1.0       新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
@SuppressWarnings("rawtypes")
@Repository("h5StoreDao")
public class H5StoreDaoImpl extends PermissionBaseDaoImpl implements H5StoreDao
{

	@Override
	public List<H5StoreEntity> getStoreList(H5StoreEntity storeEntity)
	{
		return super.getSqlSession().selectList(getStatement("getStoreList"), storeEntity);
	}
	@Override
	public H5StoreEntity selectByStoreNo(String storeNo)
	{
		return super.getSqlSession().selectOne(getStatement("selectByStoreNo"), storeNo);
	}

	@Override
	public int deleteByStoreNo(String storeNo)
	{
		return super.getSqlSession().delete(getStatement("deleteByStoreNo"), storeNo);
	}

	@Override
	public int updateStatus(Map<String, Object> paramMap)
	{
		return super.getSqlSession().update(getStatement("updateStatus"), paramMap);
	}

	@Override
	public int updateInfo(H5StoreEntity storeEntity)
	{
		return super.getSqlSession().update(getStatement("updateInfo"), storeEntity);
	}

	@Override
	public int addInfo(H5StoreEntity storeEntity)
	{
		return super.getSqlSession().insert(getStatement("addInfo"), storeEntity);
	}
	
	@Override
	public Merchant getIdByMerchNo(String merchantNo)
	{
		return super.getSqlSession().selectOne(getStatement("getIdByMerchNo"), merchantNo);
	}

	@Override
	public H5StoreEntity getStoreByStoreName(H5StoreEntity storeEntity)
	{
		return super.getSqlSession().selectOne(getStatement("getStoreByStoreName"), storeEntity);
	}
	@Override
	public String getStoreNoMax(Integer merId)
	{
		return super.getSqlSession().selectOne(getStatement("getStoreNoMax"), merId);
	}
}
