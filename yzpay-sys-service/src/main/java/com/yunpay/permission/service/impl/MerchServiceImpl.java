package com.yunpay.permission.service.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunpay.common.core.page.PageBean;
import com.yunpay.common.core.page.PageParam;
import com.yunpay.common.core.utils.StringUtil;
import com.yunpay.permission.dao.MerchDao;
import com.yunpay.permission.entity.Address;
import com.yunpay.permission.entity.MerchAttach;
import com.yunpay.permission.entity.MerchInfo;
import com.yunpay.permission.entity.Merchant;
import com.yunpay.permission.entity.MerchantAccount;
import com.yunpay.permission.entity.StoreEntity;
import com.yunpay.permission.entity.SysAttach;
import com.yunpay.permission.entity.WechatConfig;
import com.yunpay.permission.service.MerchService;
/**
 * 商户信息 业务类
 * @author duan zhang quan
 *
 */
@Service("merchService")
public class MerchServiceImpl implements MerchService{
	@Autowired
	private MerchDao merchDao;
	

	/**
	 * 分页查询商户数据
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageBean<Merchant> listPage(PageParam pageParam, Map<String,Object> map) {
		
		return merchDao.listPage(pageParam, map);
	}
	
	/**
	 * 根据条件查询
	* 
	* @param 
	* @return List<Merchant>
	* @throws
	 */
	@Override
	public List<Merchant> listBy(Map<String,Object> map){
		return this.merchDao.listBy(map);
	}
	/**
	 * 修改商户信息
	* 
	* @param 
	* @return void
	* @throws
	 */
	public void updateMerch(Merchant merch){
		this.merchDao.update(merch);
	}
	
	/**
	 * 修改商户状态码
	* 
	* @param 
	* @return int
	* @throws
	 */
	public int updateStatus(Merchant merchant){
		return this.merchDao.updateStatus(merchant);
	}
	
	/**
	 * 修改商户银行账户信息
	* 
	* @param 
	* @return void
	* @throws
	 */
	public void updateAccount(MerchantAccount account){
		this.merchDao.updateAccount(account);
	}
	
	/**
	 * 根据商户号查找商户附件
	* 
	* @param 
	* @return MerchAttach
	* @throws
	 */
	public MerchAttach getMerchAttach(Integer merId){
		return this.merchDao.getMerchAttach(merId);
	}


	/**
	 * 查询商户详细信息
	 * 
	 */
	@Override
	public Merchant queryMerchById(String merchId) {
		return merchDao.queryMerchById(merchId);
	}
	
	/**
	 * 获取所有的省市区数据
	 * @return
	 */
	@Override
	public List<Address> getAllProv(){
		List <Address> address = merchDao.getAllProv();
		return address;
	}
	

	@Override
	public List<Address> getProvince() {
		return merchDao.getProvince();
	}

	/**
	 * 获取市
	 * @param aratId：市区号
	 * @return
	 */
	@Override
	public List<Address> getCity(int id){
		return merchDao.getCity(id);
	}
	
	/**
	 * 获取区
	 * @param aratId ：区号
	 * @return
	 */
	@Override
	public List<Address> getArea(int id){
		return this.merchDao.getArea(id);
	}

	/**
	 * 查询商户是否 进行了支付宝、微信配置
	 * @param merchId
	 * @return
	 */
	@Override
	public Object isConfig(String merchantNo,String confType) {
		return this.merchDao.isConfig(merchantNo, confType);
	}

	/**
	 * 根据商户id查询微信、支付宝配置信息
	 * @param merchId
	 * @return
	 */
	@Override
	public Object queryConfigById(String merchId,String confType) {
		return this.merchDao.queryConfigById(merchId, confType);
	}

	/**
	 * 保存支付宝配置数据
	 * @return
	 */
	@Override
	public int savePayConfig(Object obj){
		return this.merchDao.savePayConfig(obj);
	}
	
	/**
	 * 更新微信配置数据
	 * @return
	 */
	@Override
	public int updatePayConfig(Object obj){
		return this.merchDao.updatePayConfig(obj);
	}
	
	public Merchant selectByOrgNo(String orgNo){
		return this.merchDao.selectByOrgNo(orgNo);
	}
	
	public StoreEntity selectStoreByOrgNo(String orgNo) {
		return this.merchDao.selectStoreByOrgNo(orgNo);
	}

	@Override
	public int addMerch(Merchant merch) {
		return this.merchDao.addMerch(merch);
		
	}

	@Override
	public void addMerchAcc(MerchantAccount account) {
		this.merchDao.addMerchAcc(account);
	}

	@Override
	public void addMerchAttach(MerchAttach merchAttach) {
		this.merchDao.addMerchAttach(merchAttach);
	}
	
	 /**
	  * 更新商户附件信息
	 * @param 
	 * @return void
	 * @throws
	  */
	 public void updateMerchAttach(MerchAttach merchAttach){
		 this.merchDao.updateMerchAttach(merchAttach);
	 }

	@Override
	public int addSysAttach(SysAttach sysAttach) {
		return this.merchDao.addSysAttach(sysAttach);
		
	}

	/**
	 * 查询商户基本信息、账户信息、证件信息
	* @param 
	* @return Map<String,Object>
	* @throws
	 */
	@Override
	public MerchInfo getMerchInfo(String merchantNo) {
		return this.merchDao.getMerchInfo(merchantNo);
	}

	/**
	 * 查询商户明细数据
	 */
	public Map<String,Object> merchDetail(Map<String,Object> params){
		Map<String,Object> data = new HashMap<String,Object>();
		MerchInfo merchInfo = this.getMerchInfo(params.get("merchantNo") == null ? null :(String) params.get("merchantNo"));
		Map<String,String> provMap = new HashMap<String,String>();
		List<Address> list = this.getAllProv();
		for(int i=0; i<list.size(); i++){
			provMap.put(list.get(i).getId().toString(), list.get(i).getName());
		}
		// 设置省市区数据
		String prov = StringUtil.isEmpty(provMap.get(merchInfo.getProv())) == true ? null : provMap.get(merchInfo.getProv());
		String city = StringUtil.isEmpty(provMap.get(merchInfo.getCity())) == true ? null : provMap.get(merchInfo.getCity());
		String area = StringUtil.isEmpty(provMap.get(merchInfo.getArea())) == true ? null :provMap.get(merchInfo.getArea());
		String str = null;
		if(prov!= null && city!= null && area!= null){
			if(prov.equals(city)){
				str =  prov+"-"+area;
			}else{
				str =  prov+"-"+city+"-"+area;
			}
		}
		merchInfo.setProv(str);
		String accProv = StringUtil.isEmpty(provMap.get(merchInfo.getAccProv())) == true ? null : provMap.get(merchInfo.getAccProv());
		String accCity = StringUtil.isEmpty(provMap.get(merchInfo.getAccCity())) == true ? null : provMap.get(merchInfo.getAccCity());
		String _accProv = null;
		if(accProv != null && accCity != null){
			if(accProv.equals(accCity)){
				_accProv = accProv;
			}else{
				_accProv = accProv + "-"+accCity;
			}
		}
		merchInfo.setAccProv(_accProv);
		data.put("merch", merchInfo);
		return data;
	}
	
	@Override
	public List<WechatConfig> getParent(Integer mchType){
		return merchDao.getParent(mchType);
	}

	

	

}
