package com.yunpay.h5merch.dao.impl;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.yunpay.h5merch.permission.dao.WechatUserInfoDao;
import com.yunpay.h5merch.permission.entity.WechatUserInfo;
import com.yunpay.permission.dao.impl.PermissionBaseDaoImpl;

/**
 * 
 * 类名称		       微信登陆DAO接口实现类
 * 文件名称:     MerchUserDaoImpl.java
 * 内容摘要: 
 * @author:   Zhao Xiaoman
 * @version:  1.0  
 * @Date:     2018年1月12日下午3:55:49
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2018年1月12日   Zhao Xiaoman       1.0       新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
@SuppressWarnings("rawtypes")
@Repository("wechatUserInfoDao")
public class WechatUserInfoDaoImpl extends PermissionBaseDaoImpl implements WechatUserInfoDao {
	@Override
	public WechatUserInfo findById(Integer id) {
		return super.getSqlSession().selectOne(getStatement("findById"), id);
	}
	
	@Override
	public WechatUserInfo findByUnionId(String unionId)
	{
		return super.getSqlSession().selectOne(getStatement("findByUnionId"), unionId);
	}
	
	@Override
	public WechatUserInfo findByOpenId(String openId)
	{
		return super.getSqlSession().selectOne(getStatement("findByOpenId"), openId);
	}
	
	@Override
	public List<WechatUserInfo> listByEntity(WechatUserInfo wechatUserInfo) {
		return super.getSqlSession().selectList(getStatement("listByEntity"), wechatUserInfo);
	}
	
	@Override
	public List<WechatUserInfo> listAllWechatUser(){
		return super.getSqlSession().selectList(getStatement("listAllWechatUser"));
	}
	
	@Override
	public int updateMsg(WechatUserInfo wechatUserInfo) {
		return super.getSqlSession().update(getStatement("updateMsg"),wechatUserInfo);
	}
	
	@Override
	public int insert(WechatUserInfo wechatUserInfo)
	{
		return super.getSqlSession().insert(getStatement("insert"),wechatUserInfo);
	}
	
	@Override
	public int deleteById(Integer id)
	{
		return super.getSqlSession().delete(getStatement("deleteById"),id);
	}
	
	@Override
	public int deleteByOpenId(String openId)
	{
		return super.getSqlSession().delete(getStatement("deleteByOpenId"),openId);
	}
}
