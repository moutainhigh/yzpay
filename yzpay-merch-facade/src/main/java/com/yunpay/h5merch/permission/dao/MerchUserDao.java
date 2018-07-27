package com.yunpay.h5merch.permission.dao;


import java.util.List;

import com.yunpay.h5merch.permission.entity.MerchUser;
import com.yunpay.permission.dao.PermissionBaseDao;

/**
 *           
 * 类名称                      商户登录Dao接口
 * 文件名称:     MerchUserDao.java
 * 内容摘要: 
 * @author:   Zhao Xiaoman
 * @version:  1.0  
 * @Date:     2017年7月25日上午9:22:29
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
public interface MerchUserDao extends PermissionBaseDao {

	/**
	 * 根据手机号码获取用户信息.
	 * 
	 * @param phone
	 *            .
	 * @return merchUser .
	 */
	MerchUser findByPhone(String phone);
	/**
	 * 根据登录名获取用户信息.
	 * 
	 * @param loginName
	 *            .
	 * @return user .
	 */
	MerchUser findByLoginName(String loginName);

	/**
	 * 根据id获取用户信息.
	 * 
	 * @param id
	 *            .
	 * @return user .
	 */
	MerchUser findById(Integer id);
	
	/**
	 * 根据id获取用户信息.
	 * 
	 * @param openId
	 *            .
	 * @return merchUser .
	 */
	MerchUser findByOpenId(String openId);
	
	/**
	 * 根据用户信息找到用户列表.
	 * 
	 * @param merchUser
	 * @return list
	 */
	List<MerchUser> listByEntity(MerchUser merchUser);

	/**
	 * 查询所有的用户
	 * @return
	 */
	public List<MerchUser> listAllMerchUser();
	
	/**
	 * 修改密码
	 * @param merchUser
	 * @return
	 */
	int updateMsg(MerchUser merchUser);
	
	/**
	 * 增加商户用户
	 * @param merchUser
	 * @return
	 */
	int insert(MerchUser merchUser);
	
	/**
	 * 根据id删除商户用户
	 * @param id
	 * @return
	 */
	int deleteById(Integer id);
	/**
	 * 根据门店编码删除商户用户
	 * @param id
	 * @return
	 */
	int deleteByStoreNo(String storeNo);
	
	/**
	 * 根据门店编码删除商户用户
	 * @param openId
	 * @return
	 */
	int deleteByOpenId(String openId);
}
