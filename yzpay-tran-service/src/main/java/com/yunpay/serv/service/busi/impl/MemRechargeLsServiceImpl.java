package com.yunpay.serv.service.busi.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunpay.serv.dao.MemRechargeLsDao;
import com.yunpay.serv.model.MemberRechargeLs;
import com.yunpay.serv.service.busi.MemRechargeLsService;

/**
 * 会员充值流水数据处理实现类
 * 文件名称:     MemRechargeLsServiceImpl.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年9月20日下午5:27:47 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年9月20日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
@Service
public class MemRechargeLsServiceImpl implements MemRechargeLsService{
	@Autowired
	private MemRechargeLsDao memRechargeLsDao;

	/**
	 * @Description:创建充值流水
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月20日下午5:30:40 
	 * @param memberRecharge
	 * @return
	 */
	@Override
	public boolean createMemRechargeLs(MemberRechargeLs memberRecharge) {
		// TODO Auto-generated method stub
		return memRechargeLsDao.insert(memberRecharge)>0;
	}

}
