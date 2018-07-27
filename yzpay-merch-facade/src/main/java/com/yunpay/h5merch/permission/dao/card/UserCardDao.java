package com.yunpay.h5merch.permission.dao.card;

import java.util.List;
import java.util.Map;

import com.yunpay.h5merch.permission.entity.card.UserCard;
import com.yunpay.h5merch.permission.entity.card.UserCardNum;
import com.yunpay.permission.dao.PermissionBaseDao;
/**
 * 
 * 类名称                     会员的卡信息DAO
 * 文件名称:     MemberDao.java
 * 内容摘要: 
 * @author:   Zhao Xiaoman
 * @version:  1.0  
 * @Date:     2017年10月16日下午7:30:39
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
public interface UserCardDao extends PermissionBaseDao {
	/**
	 * 根据id查询会员的卡的基本信息
	 * @param id
	 * @return
	 */
	public UserCard getUserCard(Integer id);
	/**
	 * 根据商户ID查询会员所有会员的卡信息
	 * @param merchantNo
	 * @return
	 */
	public List<UserCard> getUserCardList(String merchantNo);
	/**
	 * 根据商户ID查询会员所有会员的卡信息
	 * @param merchantNo
	 * @return
	 */
	public List<UserCard> pageUserCardList(Map<String, Object> map);
	/**
	 * 根据商户ID查询会员的卡数量
	 * @param merchantId
	 * @return
	 */
	public UserCardNum getUserCardNum(String merchantNo);	
	/**
	 * 根据商户ID查询会员的卡 总数量
	 * @param merchantId
	 * @return
	 */
	public Integer getUserCardTotal(String merchantNo);	
	/**
	 * 根据用户信息查询会员的卡 总数量
	 * @param data
	 * @return
	 */
	public UserCard getUserCardByUser(Map<String, String> data);	
}
