package com.yunpay.h5merch.permission.service;

import java.util.Map;

import com.yunpay.h5merch.permission.entity.WechatUserInfo;
/**
 * 
 * 类名称		       微信用户登陆业务接口
 * 文件名称:     WechatUserService.java
 * 内容摘要: 
 * @author:   Zhao Xiaoman
 * @version:  1.0  
 * @Date:     2018年1月12日下午4:17:48
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2018年1月12日   Zhao Xiaoman       1.0       新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public interface WechatUserService {

	/**
	 * 保存微信用户信息
	 * @param map
	 * @return Integer
	 */
	Integer addUser(Map<String, Object> map);
	
	/**
	 * 删除微信用户
	 * @param openId
	 */
	Integer deleteUserByOpenId(String openId);
	
	/**
	 * 根据openId获取数据WechatUser
	 * 
	 * @param openId
	 * @return WechatUserInfo
	 */
	WechatUserInfo findWechatUserByOpenId(String openId);
	
	/**
	 * 修改信息
	 * @param map
	 * @return Integer
	 */
	Integer updateMsg(Map<String, Object> map);

}
