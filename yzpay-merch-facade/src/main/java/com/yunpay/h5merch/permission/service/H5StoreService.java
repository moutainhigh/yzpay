package com.yunpay.h5merch.permission.service;

import java.util.List;
import java.util.Map;

import com.yunpay.common.core.page.PageBean;
import com.yunpay.common.core.page.PageParam;
import com.yunpay.h5merch.permission.entity.H5StoreEntity;
import com.yunpay.h5merch.permission.entity.MerchUser;
import com.yunpay.h5merch.permission.entity.ReciveMsg;
import com.yunpay.permission.entity.Merchant;

/**
 * 类名称		        门店业务接口
 * 文件名称:     StoreService.java
 * 内容摘要: 
 * @author:   Zhao Xiaoman
 * @version:  1.0  
 * @Date:     2017年6月14日下午5:30:21
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年6月14日   Zhao Xiaoman       1.0       新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */

public interface H5StoreService {
	
    
    /**
     * 分页查询
     * @param pageParam
     * @param H5StoreEntity
     * @return
     */
    @SuppressWarnings("rawtypes")
    PageBean listPage(PageParam pageParam, H5StoreEntity stroeEntity);
    
     /**
     * 修改状态码（停用/启用）
     * @param id
     * @return
     */
    public int updateStatus(Integer id,Integer status);
    
    /**
     * 修改门店的信息
     */
    public ReciveMsg<String> updateInfo(H5StoreEntity storeEntity);
    
    /**
     * 新增门店的信息
     */
    public ReciveMsg<String> addInfo(H5StoreEntity storeEntity);
    
    /**
     * 根据门店编号查询门店
     * @param storeNo
     * @return
     */
    public H5StoreEntity getByStoreNo(String storeNo);
    
    /**
     * 根据storeNo删除StroeEntity
     * @param storeNo
     * @return ReciveMsg<String>
     */
    public ReciveMsg<String> deleteByStoreNo(String storeNo);	
	/**
	 * 根据商户编号查询商户id
	 * @param merchantNo
	 * @return
	 */
	public Merchant getIdByMerchNo(String merchantNo);
	/**
	 * 根据商户编号,门店名称查询门店
	 * @param storeName,merchId
	 * @return
	 */
	public H5StoreEntity getStoreByStoreName(String storeName,Integer merchId);
	/**
	 * 根据商户编码查询门店
	 * @param merchantNo
	 * @return
	 */
	public List<H5StoreEntity> getStoreByMerId(String merchantNo);
	/**
	 * 根据商户编码查询门店(分页)
	 * @param merchantNo
	 * @return
	 */
	public Map<String, Object> getStoreByMerId(String merchantNo,Integer pageIndex);
	/**
	 * 添加店员
	 * @param 
	 * @return ReciveMsg<String>
	 */
	public ReciveMsg<String> addClerk(MerchUser merchUser);
}
