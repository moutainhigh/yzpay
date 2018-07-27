package com.yunpay.serv.dao;

import org.springframework.stereotype.Repository;

import com.yunpay.database.dao.BaseDao;
import com.yunpay.serv.model.MemberRechargeLs;

/**
 * 会员充值流水
 * 文件名称:     MemberChargeLsDao.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年9月20日下午2:25:57 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年9月20日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
@Repository("memRechargeLsDao")
public interface MemRechargeLsDao extends BaseDao<MemberRechargeLs>{
	
}
