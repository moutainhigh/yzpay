package com.yunpay.h5merch.service.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.yunpay.common.core.page.PageBean;
import com.yunpay.common.core.page.PageParam;
import com.yunpay.h5merch.permission.dao.H5StoreDao;
import com.yunpay.h5merch.permission.dao.H5StoreMachineDao;
import com.yunpay.h5merch.permission.dao.MerchUserDao;
import com.yunpay.h5merch.permission.entity.H5StoreEntity;
import com.yunpay.h5merch.permission.entity.MerchUser;
import com.yunpay.h5merch.permission.entity.ReciveMsg;
import com.yunpay.h5merch.permission.service.H5StoreService;
import com.yunpay.permission.dao.AddressDao;
import com.yunpay.permission.entity.Merchant;
import com.yunpay.permission.service.MerchService;
/**
 * 
 * 类名称                      门店业务接口实现类
 * 文件名称:     StroeServiceImpl.java
 * 内容摘要: 
 * @author:   Zhao Xiaoman
 * @version:  1.0  
 * @Date:     2017年7月12日上午9:57:46
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年7月12日   Zhao Xiaoman       1.0       新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
@Service("h5StoreService")
public class H5StroeServiceImpl implements H5StoreService
{
	@Autowired
	H5StoreDao storeDao;
	@Autowired
	AddressDao addressDao;
	@Autowired
	MerchUserDao merchUserDao;
	@Autowired
	MerchService merchService;
	@Autowired
	H5StoreMachineDao storeMachineDao;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public PageBean listPage(PageParam pageParam, H5StoreEntity storeEntity)
	{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("storeName", storeEntity.getStoreName()); // 门店名称（模糊查询）
		paramMap.put("registerName", storeEntity.getRegisterName()); // 商户名称（模糊查询）
		paramMap.put("contactMan", storeEntity.getContactMan()); // 联系人（模糊查询）
		paramMap.put("contactTel", storeEntity.getContactTel()); // 手机（模糊查询）
		paramMap.put("prov", storeEntity.getProv()); // 省（精确查询）
		paramMap.put("city", storeEntity.getCity()); // 市（精确查询）
		paramMap.put("area", storeEntity.getArea()); // 区（精确查询）
		return storeDao.listPage(pageParam, paramMap);
	}

	@Override
	public int updateStatus(Integer id, Integer status)
	{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		paramMap.put("status", status);
		return storeDao.updateStatus(paramMap);
	}

	public ReciveMsg<String> updateInfo(H5StoreEntity storeEntity)
	{
		Integer flag = 0;
		String error = null;
		H5StoreEntity store = this.storeDao.getStoreByStoreName(storeEntity);
		if(store != null && !store.getStoreNo().equals(storeEntity.getStoreNo())){
			error="该名称与您的其他商铺重名";
		}else{
			Integer result=storeDao.updateInfo(storeEntity);
			if (result>0)
			{
				flag = 1;
			} else
			{
				error="商铺修改失败";
			}
		}
		return new ReciveMsg<String>(flag, "", error, null);
	}

	public ReciveMsg<String> addInfo(H5StoreEntity storeEntity)
	{
		String error = null;
		Integer flag = 0;
		H5StoreEntity store = storeDao.getStoreByStoreName(storeEntity);
		if(store !=null){
			error="该名称与您的其他商铺重名";
		}else{
			storeEntity.setInfoFrom("1");
			storeEntity.setStatus(1);
			storeEntity.setStoreNo(this.doStoreNo(storeEntity));
			Integer result=storeDao.addInfo(storeEntity);
			if (result>0)
			{
				flag = 1;
			} else
			{
				error="添加商铺失败";
			}
		}
		return new ReciveMsg<String>(flag,"",error,null);
	}

	@Override
	public H5StoreEntity getByStoreNo(String storeNo)
	{
		return storeDao.selectByStoreNo(storeNo);
	}

	@Override
	@Transactional
	public ReciveMsg<String> deleteByStoreNo(String storeNo)
	{
		String error = null;
		Integer flag = 0;
		MerchUser merchUser = new MerchUser();
		merchUser.setStoreNo(storeNo);
		//删除店员、删除绑定机器号、删除门店
		if (storeNo != null && merchUserDao.deleteByStoreNo(storeNo) >=0 
				&& storeMachineDao.deleteByStoreNo(storeNo) >=0 && storeDao.deleteByStoreNo(storeNo)>=0){
			flag = 1;
		}else{
			error = "删除失败，请再次尝试";
		}
		return new ReciveMsg<String>(flag, "", error, null);
	}
	
	@Override
	public Merchant getIdByMerchNo(String merchantNo)
	{
		return this.storeDao.getIdByMerchNo(merchantNo);
	}
	@Override
	public H5StoreEntity getStoreByStoreName(String storeName,Integer merchId)
	{
		H5StoreEntity storeEntity = new H5StoreEntity();
		storeEntity.setStoreName(storeName);
		storeEntity.setMerId(merchId);
		H5StoreEntity store = this.storeDao.getStoreByStoreName(storeEntity);
		if (store != null  )
		{
			return store;
		}else{
			return null;
		}
	}

	@Override
	public List<H5StoreEntity> getStoreByMerId(String merchantNo)
	{
		H5StoreEntity storeEntity = new H5StoreEntity();
		storeEntity.setMerId(this.storeDao.getIdByMerchNo(merchantNo).getId());
		return this.storeDao.getStoreList(storeEntity);
	}
	@Override
	public Map<String, Object> getStoreByMerId(String merchantNo,Integer pageIndex)
	{
		Map<String, Object> map = new HashMap<>();
		H5StoreEntity storeEntity = new H5StoreEntity();
		storeEntity.setMerId(this.storeDao.getIdByMerchNo(merchantNo).getId());
		List<H5StoreEntity> list = this.storeDao.getStoreList(storeEntity);
		map.put("total", list.size());
		if (list.size()>0)
		{
			H5StoreEntity[] array = new H5StoreEntity[list.size()];
			array = list.toArray(array);
			list.clear();
			for(int i=0;i<(pageIndex+1)*4&&i<array.length;i++){
				list.add(array[i]);
			}
		} 
		map.put("storeList", list);
		return map;
	}

	@Override
	public ReciveMsg<String> addClerk(MerchUser merchUser)
	{
		String error = null;
		Integer flag = 0;
		MerchUser user = merchUserDao.findByLoginName(merchUser.getLoginName());
		if(user != null){
			error= user.getUserType() == "1" ?"该号码为已注册的商户号码" : 
				user.getStoreNo() == merchUser.getStoreNo()?"您已添加过该号码，可以直接使用":"该号码目前为其他商户店员使用，不可添加";
		}else{			
			String salt = (new SecureRandomNumberGenerator()).nextBytes().toHex();			
			merchUser.setUserStatus("1");
			merchUser.setLoginPwd(md5Pwd(merchUser.getLoginPwd()+salt));
			merchUser.setPwdSalt(salt);
			merchUser.setLoginName(merchUser.getPhone());
			Integer result = merchUserDao.insert(merchUser);
			if (result>0)
			{
				flag = 1;
			} else
			{
				error = "添加店员失败";
			}
		}
		return new ReciveMsg<String>(flag, "", error, null);
	}
	
    /**
     * 
    * 将制定字符串md5加密
    * @param plainText:密码明文
    * @return String
    * @throws
     */
    public static String md5Pwd(String plainText) {
        String re_md5 = new String();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++){
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            re_md5 = buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return re_md5;
    }
	
	
	/**
	 * 密码MD5加密
	 * @param loginPwd
	 * @return
	 */
	/*public String getPwd(String loginPwd,String pwdSalt) {	
		int hashIterations = Integer.valueOf("2");
		String newPassword = new SimpleHash("md5", loginPwd, ByteSource.Util.bytes(pwdSalt), hashIterations).toHex();	
		return newPassword;
	}*/
	/**
	 * 生成门店号
	 * @param storeEntity
	 * @return
	 */
	public String doStoreNo(H5StoreEntity storeEntity) {
		String storeNo = storeEntity.getStoreNo();
		String maxNo = storeDao.getStoreNoMax(storeEntity.getMerId());
		if (maxNo == null)
		{
			storeNo+="001";
		} else
		{
			Integer num = 0;
			Integer max = Integer.parseInt(maxNo.substring(maxNo.length()-3, maxNo.length()));
			num = max+1;
			String numStr = num<10?"00"+num:num<100?"0"+num:num.toString();
			storeNo += numStr;
		}
		return storeNo;
	}
	
}
