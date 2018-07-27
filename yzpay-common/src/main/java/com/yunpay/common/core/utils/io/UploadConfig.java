package com.yunpay.common.core.utils.io;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import com.yunpay.common.core.exception.BizException;
import com.yunpay.common.core.utils.DateUtils;
/**
 * 
 * 文件名称:
 * 内容摘要: 上传附件需要的配置项
 * @author:   duan zhang quan
 * @version:  1.0  
 * @Date:     2017年4月14日上午9:56:12
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年4月14日     duan zhang quan   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public final class UploadConfig {
	private static String dirs = ""; 
	private static String appDirs = "";
	/**
	 * 获取附件上传目录,如果目录不存在时新建该目录
	* @param 
	* @return String
	* @throws
	 */
	public static String getDirs(){
		Properties p = new Properties();
		InputStream stream = UploadConfig.class.getClassLoader().getResourceAsStream("attach.properties");
		String today = DateUtils.getReqDate();
		try {
			p.load(stream);
			dirs = p.getProperty("upload.file.dirs")+today;
			File fileDir = new File(dirs);
			if(!fileDir.exists()){
				fileDir.mkdirs();
			}
		} catch (IOException e) {
			throw new BizException("创建附件目录时发生异常",e);
		
		}
		return dirs;
	}
	
	/**
	 * 获取apk安装包的上传目录,如果目录不存在时新建该目录
	* 
	* @param 
	* @return String
	* @throws
	 */
	public static String getAppDirs(){
		Properties p = new Properties();
		InputStream stream = UploadConfig.class.getClassLoader().getResourceAsStream("attach.properties");
		try {
			p.load(stream);
			appDirs = p.getProperty("apk.file.dirs");
			File fileDir = new File(appDirs);
			if(!fileDir.exists()){
				fileDir.mkdirs();
			}
		} catch (IOException e) {
			throw new BizException("创建附件目录时发生异常",e);
		
		}
		return appDirs;
	}
	
	
	
	
}
