package com.yunpay.permission.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yunpay.common.core.page.PageBean;
import com.yunpay.common.core.page.PageParam;
import com.yunpay.permission.dao.AddressDao;
import com.yunpay.permission.dao.StoreDao;
import com.yunpay.permission.entity.Merchant;
import com.yunpay.permission.entity.StoreEntity;
import com.yunpay.permission.service.StoreService;
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
@Service("storeService")
public class StroeServiceImpl implements StoreService 
{
	@Autowired
	StoreDao storeDao;
	@Autowired
	AddressDao addressDao;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public PageBean listPage(PageParam pageParam, StoreEntity storeEntity)
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

	public int updateInfo(StoreEntity storeEntity)
	{
		return storeDao.updateInfo(storeEntity);
	}

	public int addInfo(StoreEntity storeEntity)
	{
		storeEntity.setStoreNo(this.doStoreNo(storeEntity));
		storeEntity.setInfoFrom("0");
		storeEntity.setStatus(1);
		
		return storeDao.addInfo(storeEntity);
	}

	@Override
	public StoreEntity selectByStoreNo(String storeNo)
	{
		return storeDao.selectByStoreNo(storeNo);
	}

	@Transactional
	@Override
	public int deleteByStoreNo(String storeNo)
	{
		try
		{
			Integer flag = storeDao.deleteClerk(storeNo);
			if (flag>0)
			{
				return storeDao.deleteByStoreNo(storeNo);
			}else{
				return flag;
			}
			
		} catch (Exception e)
		{
			throw e;
		}	
	}
	
	@Override
	public Merchant getIdByMerchNo(String merchantNo)
	{
		return this.storeDao.getIdByMerchNo(merchantNo);
	}

	@Override
	public List<StoreEntity> getStoreByMerId(String merchantNo)
	{
		StoreEntity storeEntity = new StoreEntity();
		
		storeEntity.setMerId(this.storeDao.getIdByMerchNo(merchantNo).getId());
		return this.storeDao.getStoreList(storeEntity);
	}
	
	/**
	 * 生成门店号
	 * @param storeEntity
	 * @return
	 */
	public String doStoreNo(StoreEntity storeEntity) {
		String storeNo = storeEntity.getStoreNo();
		List<String> list = storeDao.getStoreNoMax(storeEntity.getMerId());
		if (list.size()==0)
		{
			storeNo+="001";
		} else
		{
			String[] arrayNo = new String[list.size()];
			arrayNo = list.toArray(arrayNo);
			Integer num = 0;
			Integer max = Integer.parseInt(arrayNo[0].substring(arrayNo[0].length()-3, arrayNo[0].length()));
			if(max>arrayNo.length){
				for(int i=arrayNo.length-2;i>=0;i--){
					int j = Integer.parseInt(arrayNo[i].substring(arrayNo[i].length()-3, arrayNo[i].length()));
					if(j!=i+1){
						num = i+1;
						break;
					}
				}
			}else{
				num = max+1;
			}
			String numStr = num<10?"00"+num:num<100?"0"+num:num.toString();
			storeNo += numStr;
		}
		return storeNo;
	}

	@Override
	public List<StoreEntity> getStoreList(StoreEntity storeEntity)
	{
		return storeDao.getStoreList(storeEntity);
	}
	

}
