package com.yunpay.h5merch.dao.impl;

import org.springframework.stereotype.Repository;

import com.yunpay.h5merch.permission.dao.MemberConsumLsDao;
import com.yunpay.h5merch.permission.entity.MemberConsumLs;
import com.yunpay.permission.dao.impl.PermissionBaseDaoImpl;

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
@Repository("memberConsumLsDao")
public class MemberConsumLsDaoImpl extends  PermissionBaseDaoImpl<MemberConsumLs> implements  MemberConsumLsDao{
	
}
