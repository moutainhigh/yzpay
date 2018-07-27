package com.yunpay.h5merch.service.impl;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunpay.common.core.page.PageBean;
import com.yunpay.common.core.page.PageParam;
import com.yunpay.h5merch.permission.dao.MemberConsumLsDao;
import com.yunpay.h5merch.permission.entity.MemberConsumLs;
import com.yunpay.h5merch.permission.service.MemberConsumLsService;
/**
 * 
 * 文件名称:
 * 内容摘要: 会员消费记录
 * @author:   duan zhang quan
 * @version:  1.0  
 * @Date:     2017年4月14日上午9:56:12 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年4月14日     duan zhang quan   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
@Service("memberConsumLsService")
public class MemberConsumLsServiceImpl implements MemberConsumLsService {
	@Autowired
	private MemberConsumLsDao memberConsumLsDao;

	/**
	 * 分页查询会员消费记录
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageBean<MemberConsumLs> list(PageParam pageParam,Map<String, Object> map) {
		return this.memberConsumLsDao.listPage(pageParam, map);
	}
	

	/**
	 * 根据交易单号查询单条会员流水
	* 
	* @param 
	* @return PayTranLs
	* @throws
	 */
	public MemberConsumLs getMemberLsByTransNum(String transNum){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("transNum", transNum);
		return this.memberConsumLsDao.getBy(map);
	}
	
	
	

}
