package com.yunpay.h5merch.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yunpay.h5merch.minidao.SysAppReleaseDao;
import com.yunpay.permission.entity.SysAppRelease;

@Service("sysAppReleaseService")
public  class SysAppReleaseServiceImpl{
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
	@Autowired
	private SysAppReleaseDao sysAppReleaseDao;
	
	
	public SysAppRelease findByAppCode(String appCode) {
		return sysAppReleaseDao.findByAppCode(appCode);
	}
	
	/**
	 * 更新app版本
	* 
	* @param 
	* @return void
	* @throws
	 */
	public void updateVersion(String verName,String verCode,String appLink,String appCode){
		sysAppReleaseDao.updateVersion(verName,verCode,appLink,appCode);
	}

	
	
	
	
}
