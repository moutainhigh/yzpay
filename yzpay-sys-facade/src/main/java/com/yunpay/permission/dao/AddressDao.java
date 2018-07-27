package com.yunpay.permission.dao;

import java.util.List;
import com.yunpay.permission.entity.Address;
import com.yunpay.permission.entity.Merchant;
/**
 * 
 * 类名称                     行政区域DAO接口
 * 文件名称:     AddressDao.java
 * 内容摘要: 
 * @author:   Zhao Xiaoman
 * @version:  1.0  
 * @Date:     2017年7月12日上午9:58:58
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年7月12日   Zhao Xiaoman       1.0       新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public interface AddressDao extends PermissionBaseDao<Merchant>
{
	/**
	 * 获取所有的省市区数据
	 * 
	 * @return
	 */
	List<Address> getAllProv();

	/**
	 * 获取省
	 * 
	 * @return
	 */
	List<Address> getProvList();

	/**
	 * 根据省id获取下一层级
	 * 
	 * @param id：
	 * @param
	 * @return
	 */
	List<Address> getNextList(Integer id);

	/**
	 * 根据区域id获取区域
	 * 
	 * @param id
	 * @return
	 */
	Address getById(Integer id);
}
