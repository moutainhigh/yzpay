package com.yunpay.h5merch.permission.dao.card;

import java.util.List;
import com.yunpay.h5merch.permission.entity.card.Commonfield;
import com.yunpay.permission.dao.PermissionBaseDao;
/**
 * 
 * 类名称                     会员卡激活字段Dao
 * 文件名称:     CommonfieldDao
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
public interface CommonfieldDao extends PermissionBaseDao {
	/**
	 * 根据templateId查询激活字段
	 * @param templateId
	 * @return
	 */
	public List<Commonfield> getCommonfield(Integer templateId);
	/**
	 * 增加会员卡激活字段
	 * @param commonfieldList
	 * @return
	 */
	public int insertCommonfield(List<Commonfield>  commonfieldList);

	/**
	 * 根据会员卡templateId删除激活字段
	 * @param templateId
	 * @return
	 */
	public int deleteCommonfield(Integer templateId);
}
