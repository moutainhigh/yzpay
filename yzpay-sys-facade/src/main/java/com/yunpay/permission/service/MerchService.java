package com.yunpay.permission.service;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yunpay.common.core.page.PageBean;
import com.yunpay.common.core.page.PageParam;
import com.yunpay.permission.entity.Address;
import com.yunpay.permission.entity.MerchAttach;
import com.yunpay.permission.entity.MerchInfo;
import com.yunpay.permission.entity.Merchant;
import com.yunpay.permission.entity.MerchantAccount;
import com.yunpay.permission.entity.StoreEntity;
import com.yunpay.permission.entity.SysAttach;
import com.yunpay.permission.entity.WechatConfig;

@Service("MerchService")
public interface MerchService {
	
	/**
	 * 查询商户信息
	 * @param merchId
	 * @return
	 */
	Merchant queryMerchById(String merchId);
	
	/**
	 * 分页查询
	 * @param pageParam
	 * @param merchant
	 * @return
	 */
	PageBean<Merchant> listPage(PageParam pageParam,Map<String,Object> map);
	
	/**
	 * 根据条件查询
	* 
	* @param 
	* @return List<Merchant>
	* @throws
	 */
	List<Merchant> listBy(Map<String,Object> map);
	/**
	 * 获取所有的省市区数据
	 * @return
	 */
	List<Address> getAllProv();
	
	/**
	 * 修改商户信息
	* 
	* @param 
	* @return void
	* @throws
	 */
	void updateMerch(Merchant merch);
	
	/**
	 * 修改商户状态码
	 * 
	 * @param 
	 * @return int
	 * @throws
	 */
	int updateStatus(Merchant merchant);
	
	/**
	 * 修改商户银行账户信息
	* 
	* @param 
	* @return void
	* @throws
	 */
	void updateAccount(MerchantAccount account);
	/**
	 * 根据商户号查找商户附件
	* 
	* @param 
	* @return MerchAttach
	* @throws
	 */
	MerchAttach getMerchAttach(Integer merId);
	
	
	/**
	 * 获取省
	 * @param aratId：
	 * @return
	 */
	List<Address> getProvince();
	
	/**
	 * 获取市
	 * @param aratId：
	 * @return
	 */
	List<Address> getCity(int id);
	
	/**
	 * 获取区
	 * @param aratId ：区号
	 * @return
	 */
	List<Address> getArea(int id);
	
	/**
	 * 查询商户是否 进行了支付宝、微信配置
	 * * @param merchantNo：商户号
	 * @param confType:配置类型：微信、支付宝
	 * @return
	 */
	Object isConfig(String merchantNo,String confType);
	

	/**
	 * 根据商户id查询微信、支付宝配置信息
	 * @param merchId
	 * @return
	 */
	Object queryConfigById(String merchId,String confType);
	
	/**
	 * 保存支付宝配置数据
	 * @return
	 */
	int savePayConfig(Object obj);
	
	/**
	 * 更新微信配置数据
	 * @return
	 */
	int updatePayConfig(Object obj);
	/**
	 * 查询商户 根据组织机构
	* 
	* @param 
	* @return Merchant
	* @throws
	 */
	Merchant selectByOrgNo(String orgNo);
	/**
	 * 查询门店 根据组织机构
	* 
	* @param 
	* @return StoreEntity
	* @throws
	 */
	StoreEntity selectStoreByOrgNo(String orgNo);
	
	/**
	 * 新增商户信息
	* 
	* @param 
	* @return void
	* @throws
	 */
	int addMerch(Merchant merch);
		
	/**
	 * 新增商户银行账户信息
	* 
	* @param 
	* @return void
	* @throws
	 */

	void addMerchAcc(MerchantAccount account);
	

	/**
	 * 新增商户附件信息
	* 
	* @param 
	* @return void
	* @throws
	 */
	 void addMerchAttach(MerchAttach merchAttach);
	 
	 /**
	  * 更新商户附件信息
	 * 
	 * @param 
	 * @return void
	 * @throws
	  */
	 void updateMerchAttach(MerchAttach merchAttach);
	/**
	 * 新增系统附件信息
	* 
	* @param 
	* @return void
	* @throws
	 */
	int addSysAttach(SysAttach sysAttach);
	
	/**
	 * 获取 商户基本信息、计算信息、证件信息
	* 
	* @param 
	* @return MerchInfo
	* @throws
	 */
	MerchInfo getMerchInfo(String merchantNo);
	
	/**
	 * 查询商户明细数据
	* 
	* @param 
	* @return Map<String,Object>
	* @throws
	 */
	public Map<String,Object> merchDetail(Map<String,Object> params);
	/**
	 * 根据mchType查询微信的服务商
	 * 
	 * @param 
	 * @return List<WechatConfig>
	 * @throws
	 */
	public List<WechatConfig> getParent(Integer mchType);
	
	
}
