package com.yunpay.permission.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunpay.permission.dao.AppDao;
import com.yunpay.permission.entity.SysAppRelease;
import com.yunpay.permission.service.AppService;
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
@Service("appService")
public class AppServiceImpl implements AppService {
	
	@Autowired
	private AppDao appDao;

	@Override
	public List<SysAppRelease> appVersion() {
		return this.appDao.appVersion();
	}

	
	@Override
	public void updateVersion(SysAppRelease sysAppRelease) {
		this.appDao.updateVersion(sysAppRelease);
	}
	
	/**
	 * 根据appCode查询app版本信息
	* 
	* @param 
	* @return SysAppRelease
	* @throws
	 */
	public SysAppRelease findByAppCode(String appCode){
		return this.appDao.findByAppCode(appCode);
	}
	
	
	
}
