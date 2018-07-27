package com.yunpay.h5merch.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yunpay.h5merch.permission.dao.WechatUserInfoDao;
import com.yunpay.h5merch.permission.entity.WechatUserInfo;
import com.yunpay.h5merch.permission.service.WechatUserService;

/**
 * 
 * 类名称                      微信登陆业务接口实现类
 * 文件名称:     WechatUserServiceImpl.java
 * 内容摘要: 
 * @author:   Zhao Xiaoman
 * @version:  1.0  
 * @Date:     2018年1月12日下午4:31:33
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2018年1月12日   Zhao Xiaoman       1.0       新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */

@Service("wechatUserService")
public class WechatUserServiceImpl implements WechatUserService
{
	@Autowired
	private WechatUserInfoDao wechatUserInfoDao;
	
	@SuppressWarnings("unchecked")
	@Override
	public Integer addUser(Map<String, Object> map)
	{
		/*List<String> priviList = (List<String>)map.get("privilege");
		String privilege = null;
		if (priviList.size()>0)
		{
			for (String str : priviList)
			{
				privilege = privilege+str+",";
			}
			privilege = privilege.substring(0, privilege.length()-1);
		}*/
		WechatUserInfo wechatUserInfo = new WechatUserInfo();
		wechatUserInfo.setOpenId((String)map.get("openid"));
		wechatUserInfo.setNickName((String)map.get("nickname"));
		wechatUserInfo.setSex((Integer)map.get("sex"));
		wechatUserInfo.setCountry((String)map.get("country"));
		wechatUserInfo.setProvince((String)map.get("province"));
		wechatUserInfo.setCity((String)map.get("city"));
		wechatUserInfo.setPriviList((List<String>)map.get("privilege"));
		wechatUserInfo.setHeadImgUrl((String)map.get("headimgurl"));
		wechatUserInfo.setUnionId((String)map.get("unionid"));
		return wechatUserInfoDao.insert(wechatUserInfo);
	}

	@Override
	public Integer deleteUserByOpenId(String openId)
	{
		return wechatUserInfoDao.deleteByOpenId(openId);
	}

	@Override
	public WechatUserInfo findWechatUserByOpenId(String openId)
	{
		return wechatUserInfoDao.findByOpenId(openId);
	}

	@Override
	public Integer updateMsg(Map<String, Object> map)
	{
		WechatUserInfo wechatUserInfo = new WechatUserInfo();
		wechatUserInfo.setOpenId((String)map.get("openid"));
		wechatUserInfo.setNickName((String)map.get("nickname"));
		wechatUserInfo.setSex((Integer)map.get("sex"));
		wechatUserInfo.setCountry((String)map.get("country"));
		wechatUserInfo.setProvince((String)map.get("province"));
		wechatUserInfo.setCity((String)map.get("city"));
		wechatUserInfo.setPrivilege((String)map.get("privilege"));
		wechatUserInfo.setHeadImgUrl((String)map.get("headimgurl"));
		wechatUserInfo.setUnionId((String)map.get("unionid"));
		return wechatUserInfoDao.updateMsg(wechatUserInfo);
	}

}
