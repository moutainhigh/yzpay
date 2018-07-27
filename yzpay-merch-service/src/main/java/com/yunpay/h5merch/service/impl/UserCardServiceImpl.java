package com.yunpay.h5merch.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunpay.h5merch.permission.dao.card.ConsumLsDao;
import com.yunpay.h5merch.permission.dao.card.RechargeLsDao;
import com.yunpay.h5merch.permission.dao.card.UserCardDao;
import com.yunpay.h5merch.permission.entity.card.UserCard;
import com.yunpay.h5merch.permission.entity.card.UserCardNum;
import com.yunpay.h5merch.permission.entity.card.UserReAndCon;
import com.yunpay.h5merch.permission.service.UserCardService;
/**
 * 
 * 类名称                     会员卡业务接口实现类
 * 文件名称:     MemberCardServiceImpl.java
 * 内容摘要: 
 * @author:   Zhao Xiaoman
 * @version:  1.0  
 * @Date:     2017年9月7日下午3:28:55
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年9月7日   Zhao Xiaoman       1.0       新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
@Service("userCardService")
public class UserCardServiceImpl implements UserCardService {

	@Autowired
	UserCardDao userCardDao;
	@Autowired
	ConsumLsDao consumLsDao;
	@Autowired
	RechargeLsDao rechargeLsDao;
	
	@Override
	public List<UserCard> findUserCardList(String merchantNo, Integer page)
	{
		Integer pageIndex = page*10;
		Map<String, Object> map = new HashMap<>();
		map.put("merchantNo", merchantNo);
		map.put("pageIndex", pageIndex);
		map.put("pageSize", 10);
		if (userCardDao.getUserCardTotal(merchantNo)>pageIndex)
		{
			return userCardDao.pageUserCardList(map);
		} else
		{
			return null;
		}
	}
	@Override
	public UserCardNum findUserCardNum(String merchantNo)
	{
		
		return userCardDao.getUserCardNum(merchantNo);
	}
	@Override
	public Map<String, Double> findOther(Map<String, String> core)
	{
		Map<String, Double> map = new HashMap<>();
		map.put("out", consumLsDao.getTotal(core));
		map.put("in", rechargeLsDao.getTotal(core));
		map.put("send", rechargeLsDao.getSendTotal(core));
		return map;
	}

	@Override
	public UserCard findUserCard(Integer id)
	{
		return userCardDao.getUserCard(id);
	}
	@Override
	public UserCard findUserCardByUser(Map<String, String> data)
	{
		return userCardDao.getUserCardByUser(data);
	}
	@Override
	public List<UserReAndCon> findUserReAndCon(Map<String, String> data)
	{
		return consumLsDao.getReAndConList(data);
	}
	
	
}
