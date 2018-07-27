package com.yunpay.permission.dao;
import java.util.List;

import com.yunpay.permission.entity.Address;
import com.yunpay.permission.entity.MerchAttach;
import com.yunpay.permission.entity.MerchInfo;
import com.yunpay.permission.entity.Merchant;
import com.yunpay.permission.entity.MerchantAccount;
import com.yunpay.permission.entity.StoreEntity;
import com.yunpay.permission.entity.SysAttach;
import com.yunpay.permission.entity.WechatConfig;
public interface MerchDao extends PermissionBaseDao<Merchant>{
	
	/**
	 * 查询商户信息
	 * @param merchId
	 * @return
	 */
	Merchant queryMerchById(String merchId);
	
	/**
	 * 新增商户基本信息
	* 
	* @param 
	* @return void
	* @throws
	 */
	int addMerch(Merchant merch);
	/**
	 * 更新商户银行账户信息
	* 
	* @param 
	* @return void
	* @throws
	 */
	void updateAccount(MerchantAccount account);
	
	MerchAttach getMerchAttach(Integer merId);
	
	/**
	 * 新增商户 银行账户信息
	* 
	* @param 
	* @return void
	* @throws
	 */
	void addMerchAcc(MerchantAccount account);
	
	/**
	 * 更新商户附件信息
	* 
	* @param 
	* @return void
	* @throws
	 */
	void updateMerchAttach(MerchAttach merchAttach);
	

	/**
	 * 根据商户ID修改商户状态码
	* 
	* @param 
	* @return int
	* @throws
	 */
	int updateStatus(Merchant merchant);
	/**
	 * 新增商户 附件信息
	 * 
	 * @param 
	 * @return void
	 * @throws
	 */
	void addMerchAttach(MerchAttach merchAttach);
	
	
	/**
	 * 新增系统附件信息
	* 
	* @param 
	* @return void
	* @throws
	 */
	int addSysAttach(SysAttach sysAttach);
	
	/**
	 * 获取所有的省市区数据
	 * @return
	 */
	List<Address> getAllProv();
	
	/**
	 * 获取省
	 * @return
	 */
	List<Address> getProvince();
	
	/**
	 * 获取市
	 * @param id：市区号
	 * @param 
	 * @return
	 */
	List<Address> getCity(int id);
	
	/**
	 * 获取区
	 * @param id ：区号
	 * @return
	 */
	List<Address> getArea(int id);
	
	
	/**
	 * 查询商户是否 进行了支付宝、微信配置
	 * @param merchId
	 * @return
	 */
	Object isConfig(String merchId,String confType);
	
	/**
	 * 根据商户id查询微信支付宝配置信息
	 * @param merchId
	 * @return
	 */
	Object queryConfigById(String merchId,String confType);

	/**
	 * 保存微信或者支付宝配置数据
	 * @return
	 */
	public int savePayConfig(Object obj);
	
	/**
	 * 更新微信或者支付宝配置
	 * @param obj
	 * @return
	 */
	public int updatePayConfig(Object obj);
	/**
	 * 查询商户 根据组织机构
	* 
	* @param 
	* @return Merchant
	* @throws
	 */
	public Merchant selectByOrgNo (String orgNo);

	/**
	 * 查找门店 根据组织机构
	* 
	* @param 
	* @return StoreEntity
	* @throws
	 */
	public StoreEntity selectStoreByOrgNo(String orgNo);


	/**
	 * 获取 商户基本信息、计算信息、证件信息
	* 
	* @param 
	* @return MerchInfo
	* @throws
	 */

	public MerchInfo getMerchInfo(String merchantNo);
	/**
	 * 获取 商户基本信息、计算信息、证件信息
	 * 
	 * @param 
	 * @return List<WechatConfig>
	 * @throws
	 */
	
	public List<WechatConfig> getParent(Integer mchType);
	
	
}
