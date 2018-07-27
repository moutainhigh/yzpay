package com.yunpay.h5merch.minidao;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import com.yunpay.h5merch.permission.entity.StoreMachine;
import com.yunpay.permission.entity.Merchant;
import com.yunpay.permission.entity.PayTranLs;
import com.yunpay.permission.entity.StoreEntity;
import com.yunpay.permission.entity.SysCashier;
import com.yunpay.permission.entity.SysUser;
@Repository("sysCashierDao")
public interface SysCashierDao{

	@Select("select * from t_merchant_user where userStatus = '1' order by id")
	List<SysCashier> findAll();
	
	@Select("select * from t_merchant_user where id=#{cashierNo} and userStatus = '1'")
	SysCashier serlectByNo(String cashierNo);
	
	@Select("select * from t_store t where t.storeNo =#{orgNo}")
	public StoreEntity selectStoreByOrgNo(String orgNo);
	
	@Select("select * from t_merchant m where m.merchantNo = #{merchantNo}")
	public Merchant selectMerchByOrgNo(String merchantNo);
		
	@Select("select * from t_merchant_user where phone=#{phone} and userStatus = '1' ")
	SysCashier serlectByPhone(String phone);
	
	@Select("select * from t_merchant_user where userStatus = '1' and orgNo like CONCAT(#{orgNo},'%')")
	List<SysCashier> selectDataByOrgNo(String orgNo);
	
	@Select("select id from t_merchant_user where id = (select max(id) from t_cashier where orgNo = #{orgNo})")
	String findMaxCashierNo(String orgNo);
	
	@Select("select * from t_merchant_user where loginName=#{param}  and userStatus = '1'")
	SysCashier serlectByParam(String param);
	
	@Select("select * from t_merchant_user u where u.merchantNo = #{merchantNo} and u.userType = '1'")
	SysCashier getMerchUser(String merchantNo);
	
	@Select("select * from t_store_machine where machineNo=#{machineNo} and status = 1")
	StoreMachine serlectStoreMachine(String machineNo);
	
	@Select("select count(1) from t_store_machine where machineNo=#{machineNo} and status = 1")
	int isBind(String machineNo);
	
	@Select("insert into t_store_machine(storeNo,machineNo,bindTime,status) values(#{storeNo},#{machineNo},#{bindTime},#{status})")
	void saveStoreMachineNo(@Param("storeNo")String storeNo,@Param("machineNo")String machineNo,@Param("bindTime")Date bindTime, @Param("status")int status);
	
	@Select("select * from t_merchant_user where id=#{id}")
	SysCashier findById(String id);
	
	@Select("select * from t_sys_user where id=#{id}")
	SysUser selectByPrimaryKey(Integer id);
	
	@Select("update  t_merchant_user set createTime =STR_TO_DATE(#{_date},'%Y-%m-%d %H:%i:%s') where id=#{_id}")
	void updateCashier(@Param ("_id")Integer _id,@Param("_date") String _date);
	
	// 查询某个商户下的所有门店
	@Select("select * from t_store t where t.merId = #{merId}")
	public List<StoreEntity> selectStoreByMerId(int merId);
	
	/**
	 * 根据手机号获取商户数据
	* 
	* @param 
	* @return Merchant
	* @throws
	 */
	Merchant selectMerchByPhone(String phone);
	
	/**
	 * 根据手机号修改用户密码
	* 
	* @param 
	* @return void
	* @throws
	 */
	@Select("update t_merchant_user set loginPwd = #{pwd} where phone=#{phone}")
	void updatePwdByPhone(@Param ("phone")String phone,@Param ("pwd")String pwd);
	
	/**
	 * 根据交易单号查询交易流水
	* 
	* @param 
	* @return PayTranLs
	* @throws
	 */
	@Select("select * from t_pay_tran_ls t where t.transNum=#{transNum}")
	public PayTranLs getLsByTransNum(String transNum);
	
		

	
	
}