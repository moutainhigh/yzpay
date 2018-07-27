package com.yunpay.permission.dao.impl;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.yunpay.permission.dao.AppDao;
import com.yunpay.permission.entity.SysAppRelease;
/**
 * 
 * 文件名称:
 * 内容摘要: 
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
@Repository("appDao")
public class AppDaoImpl extends PermissionBaseDaoImpl<SysAppRelease> implements AppDao{

	/**
	 * 查询所有的app版本信息
	* @param 
	* @return List<SysAppRelease>
	* @throws
	 */
	public List<SysAppRelease> appVersion(){
		return super.getSqlSession().selectList(super.getStatement("appVersion"));
	}
	
	/**
	 * 更新app版本
	* 
	* @param 
	* @return void
	* @throws
	 */
	
	public void updateVersion(SysAppRelease sysAppRelease){
		super.getSqlSession().update(super.getStatement("updateVersion"), sysAppRelease);
	}

	@Override
	public SysAppRelease findByAppCode(String appCode) {
		return super.getSqlSession().selectOne(super.getStatement("findByAppCode"), appCode);
	}
}
