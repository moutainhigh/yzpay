package com.yunpay.permission.dao.impl;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.yunpay.permission.dao.MerchDao;
import com.yunpay.permission.entity.Address;
import com.yunpay.permission.entity.AlipayConfig;
import com.yunpay.permission.entity.MerchAttach;
import com.yunpay.permission.entity.MerchInfo;
import com.yunpay.permission.entity.Merchant;
import com.yunpay.permission.entity.MerchantAccount;
import com.yunpay.permission.entity.StoreEntity;
import com.yunpay.permission.entity.SysAttach;
import com.yunpay.permission.entity.WechatConfig;

@Repository("merchDao")
public class MerchDaoImpl extends PermissionBaseDaoImpl<Merchant> implements MerchDao{
	
	@Override
	public Merchant queryMerchById(String merchId) {
		return super.getSqlSession().selectOne(getStatement("queryMerchById"), merchId);
	}

	public int updateStstus(Merchant merchant){
		return super.getSqlSession().update(getStatement("updateStstus"), merchant);
	}
	
	/**
	 * 更新商户银行账户信息
	* 
	* @param 
	* @return void
	* @throws
	 */
	public void updateAccount(MerchantAccount account){
		super.getSqlSession().update("updateAccount", account);
	}
	
	/**
	 * 查询商户附件信息
	 */
	public MerchAttach getMerchAttach(Integer merId){
		MerchAttach attach = (MerchAttach)super.getSqlSession().selectOne("getMerchAttach", merId);
		return attach;
	}
	

	/**
	 * 获取所有的省市区数据
	 * @return
	 */
	@Override
	/**
	 * 获取所有的省市区数据
	 * @return
	 */
	public List<Address> getAllProv(){
		return super.getSqlSession().selectList(getStatement("getAllProv"));
	}

	@Override
	public List<Address> getProvince() {
		return super.getSqlSession().selectList(getStatement("getProvince"));
	}

	@Override
	public List<Address> getCity(int id) {
		
		return super.getSqlSession().selectList(getStatement("getCity"),id);
	}

	@Override
	public List<Address> getArea(int id) {
		
		return super.getSqlSession().selectList(getStatement("getArea"),id);
	}
	
	/**
	 * 查询是否进行了支付宝或微信配置
	 */
	public Object isConfig(String merchId,String confType){
		Object obj = null;
		if(confType.equals("wechat")){
			obj = super.getSqlSession().selectOne(getStatement("isConfigWechat"), merchId);
			if(obj == null){
				return new WechatConfig();
			}else{
				return obj;
			}
				
		}else if(confType.equals("alipay")){
			obj = super.getSqlSession().selectOne(getStatement("isConfigAlipay"), merchId);
			if(obj == null){
				return new AlipayConfig();
			}else{
				return obj;
			}
		}else{
			return null;
		}
	}
		
	/**
	 * 查询微信或支付宝配置
	 */
	public Object queryConfigById(String merchId,String confType){
		if(confType.equals("wechat")){
			return super.getSqlSession().selectOne(getStatement("queryWechatConfig"), merchId);
		}else if(confType.equals("alipay")){
			return super.getSqlSession().selectOne(getStatement("queryAlipayConfig"), merchId);
		}else{
			return null;
		}
	}
	
	/**
	 * 保存微信或者支付宝配置数据
	 * @return
	 */
	public int savePayConfig(Object obj){
		 if(obj instanceof AlipayConfig){
			 AlipayConfig alipay = (AlipayConfig)obj;
			 return super.getSqlSession().insert("saveAlipayConfig",alipay);
		 }
		 else if(obj instanceof WechatConfig){
			 WechatConfig wechat = (WechatConfig)obj;
			 return super.getSqlSession().insert("saveWechatConfig",wechat); 
		 }
		 else{
			 return 0;
		 }
	}
	
	/**
	 * 更新微信或者支付宝配置
	 * @param obj
	 * @return
	 */
	public int updatePayConfig(Object obj){
		 if(obj instanceof AlipayConfig){
			 AlipayConfig alipay = (AlipayConfig)obj;
			 return super.getSqlSession().update("updateAlipayConfig",alipay);
		 }
		 else if(obj instanceof WechatConfig){
			 WechatConfig wechat = (WechatConfig)obj;
			 return super.getSqlSession().update("updateWechatConfig",wechat); 
		 }
		 else{
			 return 0;
		 }
	}
	
	public Merchant selectByOrgNo (String orgNo){
		 return super.getSqlSession().selectOne("selectByOrgNo",orgNo); 
	}
	
	public StoreEntity selectStoreByOrgNo(String orgNo){
		 return super.getSqlSession().selectOne("selectStoreByOrgNo",orgNo); 
	}



	@Override
	public int addMerch(Merchant merch) {
		return super.getSqlSession().insert(getStatement("addMerch"),merch);
		
	}



	@Override
	public void addMerchAcc(MerchantAccount account) {
		super.getSqlSession().insert(getStatement("addMerchAcc"),account);
	}
	
	/**
	 * 更新商户附件信息
	* 
	* @param 
	* @return void
	* @throws
	 */
	public void updateMerchAttach(MerchAttach merchAttach){
		super.getSqlSession().update(getStatement("updateMerchAttach"), merchAttach);
	}

	@Override
	public int updateStatus(Merchant merchant){
		return super.getSqlSession().insert(getStatement("updateStatus"),merchant);
	}

	@Override
	public void addMerchAttach(MerchAttach merchAttach) {
		super.getSqlSession().insert(getStatement("addMerchAttach"),merchAttach);
		
	}
	/**
	 * 新增系统附件信息
	* 
	* @param 
	* @return void
	* @throws
	 */
	public int addSysAttach(SysAttach sysAttach){
		return super.getSqlSession().insert(getStatement("addSysAttach"),sysAttach);
	}



	@Override
	public MerchInfo getMerchInfo(String merchantNo) {
		return super.getSqlSession().selectOne(getStatement("getMerchInfo"),merchantNo);
	}
	@Override
	public List<WechatConfig> getParent(Integer mchType) {
		return super.getSqlSession().selectList(getStatement("getParent"),mchType);
	}


}
