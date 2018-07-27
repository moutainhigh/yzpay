package com.yunpay.h5merch.permission.dao;

import java.util.List;
import java.util.Map;
import com.yunpay.h5merch.permission.entity.H5StoreEntity;
import com.yunpay.permission.dao.PermissionBaseDao;
import com.yunpay.permission.entity.Merchant;

@SuppressWarnings("rawtypes")
public interface H5StoreDao extends PermissionBaseDao {
	
	/**
	 * 查询门店列表
	 * @param storeEntity
	 * @return
	 */
	List<H5StoreEntity> getStoreList(H5StoreEntity storeEntity);
     /**
     * 根据storeNo修改门店的状态
     * @param storeNo
     * @param status 状态
     * @return
     */
    public int updateStatus(Map<String, Object> paramMap);
    
    /**
     * 修改门店的信息
     */
    public int updateInfo(H5StoreEntity storeEntity);
    
    /**
     * 增加门店的信息
     */
    public int addInfo(H5StoreEntity storeEntity);
    
    /**
     * 根据storeNo查询StoreEntity
     * @param storeNo
     * @return
     */
    
    public H5StoreEntity selectByStoreNo(String storeNo);
    
    /**
     * 根据storeNo删除StoreEntity
     * @param storeNo
     * @return
     */
    
    public int deleteByStoreNo(String storeNo);
    /**
	 * 根据商户编号查询商户id
	 * @param merchNo
	 * @return
	 */
	public Merchant getIdByMerchNo(String merchNo);
	/**
	 * 根据门店名称查询门店数量
	 * @param merchNo
	 * @return
	 */
	public H5StoreEntity getStoreByStoreName(H5StoreEntity storeEntity);
	/**
	 * 根据商户ID查询门店编码
	 * @param merId
	 * @return
	 */
	public String getStoreNoMax(Integer merId);
}
