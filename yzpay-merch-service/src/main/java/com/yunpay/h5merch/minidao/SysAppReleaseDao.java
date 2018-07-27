package com.yunpay.h5merch.minidao;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import com.yunpay.permission.entity.SysAppRelease;
@Repository("sysAppReleaseDao")
public interface SysAppReleaseDao{
	/**
	 * 
	 * 文件名称:
	 * 内容摘要: 
	 * @author:   duan zhang quan
	 * @version:  1.0  
	 * @Date:     2017年7月21日上午9:56:12 
	 * 
	 * 修改历史:  
	 * 修改日期                     修改人员                                   版本	            修改内容  
	 * ----------------------------------------------  
	 * 2017年7月21日     duan zhang quan   1.0     修改
	 *
	 * 版权:   版权所有(C)2017
	 * 公司:   深圳市至高通信技术发展有限公司
	 */
	
	@Select("select * from t_yunpu_sys_app_release where appCode=#{appCode}")
	SysAppRelease findByAppCode(String appCode);
	
	/**
	 * 更新app版本
	* 
	* @param 
	* @return void
	* @throws
	 */
	@Select("update t_yunpu_sys_app_release t set t.verName=#{verName},t.verCode=#{verCode},t.appLink=#{appLink} where appCode=#{appCode}")
	void updateVersion(@Param("verName")String verName,@Param("verCode")String verCode,@Param("appLink") String appLink,@Param("appCode") String appCode);
}
