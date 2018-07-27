package com.yunpay.permission.dao.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.yunpay.permission.dao.StoreDao;
import com.yunpay.permission.entity.Merchant;
import com.yunpay.permission.entity.StoreEntity;
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
@Repository("storeDao")
public class StoreDaoImpl extends PermissionBaseDaoImpl implements StoreDao
{

	@Override
	public List<StoreEntity> getStoreList(StoreEntity storeEntity)
	{
		return super.getSqlSession().selectList(getStatement("getStoreList"), storeEntity);
	}

	@Override
	public StoreEntity selectByStoreNo(String storeNo)
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
	public int updateInfo(StoreEntity storeEntity)
	{
		return super.getSqlSession().update(getStatement("updateInfo"), storeEntity);
	}

	@Override
	public int addInfo(StoreEntity storeEntity)
	{
		return super.getSqlSession().insert(getStatement("addInfo"), storeEntity);
	}
	
	@Override
	public Merchant getIdByMerchNo(String merchantNo)
	{
		return super.getSqlSession().selectOne(getStatement("getIdByMerchNo"), merchantNo);
	}

	@Override
	public List<String> getStoreNoMax(Integer merId)
	{
		return super.getSqlSession().selectList(getStatement("getStoreNoMax"), merId);
	}
	
	@Override
	public int deleteClerk(String storeNo){
		return super.getSqlSession().delete(getStatement("deleteClerk"), storeNo);
	}
}
