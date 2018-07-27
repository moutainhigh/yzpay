package com.yunpay.h5merch.dao.impl;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.yunpay.h5merch.permission.dao.H5StoreMachineDao;
import com.yunpay.h5merch.permission.entity.StoreMachine;
import com.yunpay.permission.dao.impl.PermissionBaseDaoImpl;
/**
 * 
 * 类名称                     门店DAO实现类
 * 文件名称:     StoreDaoImpl.java
 * 内容摘要: 
 * @author:   Zhao Xiaoman
 * @version:  1.0  
 * @Date:     2017年7月12日上午9:50:17
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年7月12日   Zhao Xiaoman       1.0       新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
@SuppressWarnings("rawtypes")
@Repository("h5StoreMachineDao")
public class H5StoreMachineDaoImpl extends PermissionBaseDaoImpl implements H5StoreMachineDao
{
	@Override
	public List<StoreMachine> getMachineByStoreNo(String storeNo)
	{
		return super.getSqlSession().selectList(getStatement("getMachineByStoreNo"), storeNo);
	}

	@Override
	public int deleteByStoreNo(String storeNo)
	{
		return super.getSqlSession().delete(getStatement("deleteByStoreNo"), storeNo);
	}
}
