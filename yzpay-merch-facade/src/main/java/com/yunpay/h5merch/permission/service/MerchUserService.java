package com.yunpay.h5merch.permission.service;


import java.util.List;
import java.util.Map;

import com.yunpay.h5merch.permission.entity.MerchUser;
import com.yunpay.h5merch.permission.entity.ReciveMsg;
/**
 * 
 * 类名称                     商户用户service接口
 * 文件名称:     MerchUserService.java
 * 内容摘要: 
 * @author:   Zhao Xiaoman
 * @version:  1.0  
 * @Date:     2017年7月25日上午10:25:04
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年7月25日   Zhao Xiaoman       1.0       新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public interface MerchUserService {

	/**
	 * 商户注册
	 * @param reqMap
	 * @return ReciveMsg<Map<String, Object>>
	 */
	ReciveMsg<Map<String, Object>> addUser(Map<String, String> reqMap);
	/**
	 * 删除店员用户
	 * @param id
	 */
	ReciveMsg<String> deleteClerkById(Integer id);
	/**
	 * 根据id获取数据MerchUser
	 * 
	 * @param id
	 * @return
	 */
	MerchUser findMerchUserById(Integer id);
	
	/**
	 * 根据openId获取数据MerchUser
	 * 
	 * @param openId
	 * @return
	 */
	MerchUser findMerchUserByOpenId(String openId);

	/**
	 * 查询所有的商户用户
	 * @return
	 */
	List<MerchUser> listAllMerchUser();
	
	/**
	 * 按条件查询用户
	 * @return
	 */
	List<MerchUser> listByEntity(MerchUser merchUser);
	
	/**
	 * 按门店号分页查询用户
	 * @return
	 */
	Map<String, Object> listByStoreNo(String storeNo,Integer pageIndex);
	
	/**
	 * 根据登录名取得商户用户对象
	 * @param loginName
	 * @return
	 */
	MerchUser findMerchUserByLoginName(String loginName);
	
	/**
	 * 修改信息
	 * @param merchUser
	 * @return ReciveMsg<String>
	 */
	ReciveMsg<String> updateMsg(MerchUser merchUser);
	
	/**
	 * 登录验证
	 * @param Map<String, String>
	 * @return ReciveMsg<Map<String, Object>>
	 */
	ReciveMsg<Map<String, Object>> loginValidate(Map<String, String> reqMap);
	/**
	 * 找回密码验证
	 * @param map
	 * @return ReciveMsg<Map<String,Object>>
	 */
	ReciveMsg<Map<String,Object>> foundPwdValidate(Map<String, String> reqMap);
	/**
	 * 更改密码
	 * @param Map<String, Object>
	 * @return ReciveMsg<Map<String,Object>>
	 */
	ReciveMsg<Map<String,Object>> changePwd(Map<String, Object> reqMap);

}
