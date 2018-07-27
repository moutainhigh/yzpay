package com.yunpay.h5merch.permission.dao;


import java.util.List;
import com.yunpay.h5merch.permission.entity.WechatUserInfo;
import com.yunpay.permission.dao.PermissionBaseDao;

/**
 * 
 * 类名称                      微信登录Dao接口
 * 文件名称:     WechatUserInfoDao.java
 * 内容摘要: 
 * @author:   Zhao Xiaoman
 * @version:  1.0  
 * @Date:     2018年1月12日下午3:23:24
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
public interface WechatUserInfoDao extends PermissionBaseDao {

	/**
	 * 根据手机号码获取用户信息.
	 * 
	 * @param unionId
	 *            .
	 * @return WechatUserInfo
	 */
	WechatUserInfo findByUnionId(String unionId);
	/**
	 * 根据登录名获取用户信息.
	 * 
	 * @param openId
	 *            .
	 * @return WechatUserInfo .
	 */
	WechatUserInfo findByOpenId(String openId);
	/**
	 * 根据id获取用户信息.
	 * 
	 * @param id
	 *            .
	 * @return WechatUserInfo .
	 */
	WechatUserInfo findById(Integer id);
	/**
	 * 根据用户信息找到用户列表.
	 * 
	 * @param wechatUserInfo
	 * @return list
	 */
	List<WechatUserInfo> listByEntity(WechatUserInfo wechatUserInfo);

	/**
	 * 查询所有的用户
	 * @return
	 */
	public List<WechatUserInfo> listAllWechatUser();
	
	/**
	 * 修改信息
	 * @param wechatUserInfo
	 * @return
	 */
	int updateMsg(WechatUserInfo wechatUserInfo);
	
	/**
	 * 增加微信用户
	 * @param wechatUserInfo
	 * @return
	 */
	int insert(WechatUserInfo wechatUserInfo);
	
	/**
	 * 根据id删除微信用户
	 * @param id
	 * @return
	 */
	int deleteById(Integer id);
	/**
	 * 根据openId删除微信用户
	 * @param openId
	 * @return
	 */
	int deleteByOpenId(String openId);
}
