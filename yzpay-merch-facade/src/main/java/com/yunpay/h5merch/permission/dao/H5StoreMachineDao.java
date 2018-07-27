package com.yunpay.h5merch.permission.dao;

import java.util.List;

import com.yunpay.h5merch.permission.entity.StoreMachine;
import com.yunpay.permission.dao.PermissionBaseDao;

@SuppressWarnings("rawtypes")
public interface H5StoreMachineDao extends PermissionBaseDao {
	
	/**
	 * 查询门店机器码
	 * @param storeNo
	 * @return
	 */
	public List<StoreMachine> getMachineByStoreNo(String storeNo);
    /**
     * 根据storeNo删除门店机器号
     * @param storeNo
     * @return
     */
    public int deleteByStoreNo(String storeNo);
}
