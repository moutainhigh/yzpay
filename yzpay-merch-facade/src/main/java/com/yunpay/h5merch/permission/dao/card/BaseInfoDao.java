package com.yunpay.h5merch.permission.dao.card;

import com.yunpay.h5merch.permission.entity.card.BaseInfo;
import com.yunpay.permission.dao.PermissionBaseDao;
/**
 * 
 * 类名称                     会员卡积分规则Dao
 * 文件名称:     IntegralRuleDao
 * 内容摘要: 
 * @author:   Zhao Xiaoman
 * @version:  1.0  
 * @Date:     2017年9月6日下午2:40:10
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年9月6日   Zhao Xiaoman       1.0       新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
@SuppressWarnings("rawtypes")
public interface BaseInfoDao extends PermissionBaseDao {
	/**
	 * 根据id查询会员卡基本信息
	 * @param id
	 * @return
	 */
	public BaseInfo getBaseInfo(Integer id);
	/**
	 * 增加会员卡基本信息
	 * @param BaseInfo
	 * @return
	 */
	public int insertBaseInfo(BaseInfo baseInfo);

	/**
	 * 根据会员卡基本信息表id删除
	 * @param id
	 * @return
	 */
	public int deleteBaseInfo(Integer id);
	/**
	 * 修改会员卡基本信息
	 * @param BaseInfo
	 * @return
	 */
	public int updateBaseInfo(BaseInfo baseInfo);	
}
