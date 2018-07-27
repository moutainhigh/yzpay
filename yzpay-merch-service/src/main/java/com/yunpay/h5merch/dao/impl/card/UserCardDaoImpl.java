package com.yunpay.h5merch.dao.impl.card;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yunpay.h5merch.permission.dao.card.UserCardDao;
import com.yunpay.h5merch.permission.entity.card.UserCard;
import com.yunpay.h5merch.permission.entity.card.UserCardNum;
import com.yunpay.permission.dao.impl.PermissionBaseDaoImpl;
/**
 * 
 * 类名称                      会员信息查询DAO实现类
 * 文件名称:     UserCardDaoImpl.java
 * 内容摘要: 
 * @author:   Zhao Xiaoman
 * @version:  1.0  
 * @Date:     2017年10月16日下午8:09:03
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年10月16日   Zhao Xiaoman       1.0       新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
@SuppressWarnings("rawtypes")
@Repository("userCardDao")
public class UserCardDaoImpl extends PermissionBaseDaoImpl implements UserCardDao  {

	@Override
	public UserCard getUserCard(Integer id)
	{
		return super.getSqlSession().selectOne(getStatement("getUserCard"), id);
	}

	@Override
	public List<UserCard> getUserCardList(String merchantNo)
	{
		return super.getSqlSession().selectList(getStatement("getUserCardList"), merchantNo);
	}

	@Override
	public UserCardNum getUserCardNum(String merchantNo)
	{
		return super.getSqlSession().selectOne(getStatement("getUserCardNum"), merchantNo);
	}

	@Override
	public List<UserCard> pageUserCardList(Map<String, Object> map)
	{
		return super.getSqlSession().selectList(getStatement("pageUserCardList"),map);
	}
	@Override
	public Integer getUserCardTotal(String merchantNo)
	{
		return super.getSqlSession().selectOne(getStatement("getUserCardTotal"),merchantNo);
	}

	@Override
	public UserCard getUserCardByUser(Map<String, String> data)
	{
		return super.getSqlSession().selectOne(getStatement("getUserCardByUser"),data);
	}

}
