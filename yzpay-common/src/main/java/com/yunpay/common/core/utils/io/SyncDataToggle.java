package com.yunpay.common.core.utils.io;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import com.yunpay.common.core.exception.BizException;

/**
 * 
 * 文件名称:
 * 内容摘要: 数据同步的开关,声明是否同步其它app的数据到我方数据库中
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
public class SyncDataToggle {
private static String isSyncAppData = "";  

	public static String getToggle(){
		Properties p = new Properties();
		InputStream stream = UploadConfig.class.getClassLoader().getResourceAsStream("configContants.properties");
		try {
			p.load(stream);
			isSyncAppData = p.getProperty("isSyncAppData");
		} catch (IOException e) {
			throw new BizException("获取configContants.properties文件时发生异常",e);
		
		}
		return isSyncAppData;
	}
	
}
