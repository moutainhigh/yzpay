package com.yunpay.permission.service;

import java.util.List;

import com.yunpay.common.core.page.PageBean;
import com.yunpay.common.core.page.PageParam;
import com.yunpay.permission.entity.Merchant;
import com.yunpay.permission.entity.StoreEntity;

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

public interface StoreService {
	
    
    /**
     * 分页查询
     * @param pageParam
     * @param StoreEntity
     * @return
     */
    @SuppressWarnings("rawtypes")
    PageBean listPage(PageParam pageParam, StoreEntity storeEntity);
    /**
     * 根据条件查询门店列表
     * @param 
     * @param StoreEntity
     * @return
     */
    List<StoreEntity> getStoreList( StoreEntity storeEntity);
    
     /**
     * 修改状态码（停用/启用）
     * @param id
     * @return
     */
    public int updateStatus(Integer id,Integer status);
    
    /**
     * 修改门店的信息
     */
    public int updateInfo(StoreEntity storeEntity);
    
    /**
     * 新增门店的信息
     */
    public int addInfo(StoreEntity storeEntity);
    
    /**
     * 根据id查询StroeEntity列表
     * @param storeNo
     * @return
     */
    public StoreEntity selectByStoreNo(String storeNo);
    
    /**
     * 根据门店编码删除门店
     * @param storeNo
     * @return
     */
    public int deleteByStoreNo(String storeNo);	
	/**
	 * 根据商户编号查询商户id
	 * @param merchantNo
	 * @return
	 */
	public Merchant getIdByMerchNo(String merchantNo);
	/**
	 * 根据商户id查询门店
	 * @param merId
	 * @return
	 */
	public List<StoreEntity> getStoreByMerId(String merchantNo);
}
