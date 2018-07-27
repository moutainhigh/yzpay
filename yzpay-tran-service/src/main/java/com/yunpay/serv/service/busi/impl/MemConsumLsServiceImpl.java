package com.yunpay.serv.service.busi.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunpay.serv.dao.MemConsumLsDao;
import com.yunpay.serv.model.MemberConsumLs;
import com.yunpay.serv.service.busi.MemConsumLsService;

/**
 * 会员消费流水数据处理实现类
 * 文件名称:     MemConsumLsServiceImpl.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年9月21日上午9:30:06 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年9月21日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
@Service
public class MemConsumLsServiceImpl implements MemConsumLsService{
	
	@Autowired
	private MemConsumLsDao memConsumLsDao;

	/**
	 * @Description:添加会员消费流水信息
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月21日上午9:30:28 
	 * @param memberConsumLs
	 * @return
	 */
	@Override
	public boolean createMemConsumLs(MemberConsumLs memberConsumLs) {
		// TODO Auto-generated method stub
		return memConsumLsDao.insert(memberConsumLs)>0;
	}

}
