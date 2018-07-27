package com.yunpay.h5merch.dao.impl;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.yunpay.h5merch.permission.dao.MerchUserDao;
import com.yunpay.h5merch.permission.entity.MerchUser;
import com.yunpay.permission.dao.impl.PermissionBaseDaoImpl;

/**
 * 
 * 类名称                      商户用户DAO接口实现类
 * 文件名称:     MerchUserDaoImpl.java
 * 内容摘要: 
 * @author:   Zhao Xiaoman
 * @version:  1.0  
 * @Date:     2017年7月25日上午10:15:54
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年7月25日   Zhao Xiaoman       1.0       新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
@SuppressWarnings("rawtypes")
@Repository("merchUserDao")
public class MerchUserDaoImpl extends PermissionBaseDaoImpl implements MerchUserDao {
	@Override
	public MerchUser findByLoginName(String loginName) {
		return super.getSqlSession().selectOne(getStatement("findByLoginName"), loginName);
	}
	@Override
	public MerchUser findById(Integer id) {
		return super.getSqlSession().selectOne(getStatement("findById"), id);
	}
	@Override
	public MerchUser findByOpenId(String openId) {
		return super.getSqlSession().selectOne(getStatement("findByOpenId"), openId);
	}

	@Override
	public List<MerchUser> listByEntity(MerchUser merchUser) {
		return super.getSqlSession().selectList(getStatement("listByEntity"), merchUser);
	}
	
	@Override
	public List<MerchUser> listAllMerchUser(){
		return super.getSqlSession().selectList(getStatement("listAllMerchUser"));
	}
	
	@Override
	public int updateMsg(MerchUser user) {
		return super.getSqlSession().update(getStatement("updateMsg"),user);
	}
	@Override
	public int insert(MerchUser user)
	{
		return super.getSqlSession().insert(getStatement("insert"),user);
	}
	@Override
	public MerchUser findByPhone(String phone)
	{
		return super.getSqlSession().selectOne(getStatement("findByPhone"),phone);
	}
	@Override
	public int deleteById(Integer id)
	{
		return super.getSqlSession().delete(getStatement("deleteById"),id);
	}
	@Override
	public int deleteByStoreNo(String storeNo)
	{
		return super.getSqlSession().delete(getStatement("deleteByStoreNo"),storeNo);
	}
	@Override
	public int deleteByOpenId(String openId)
	{
		return super.getSqlSession().delete(getStatement("deleteByOpenId"),openId);
	}
}
