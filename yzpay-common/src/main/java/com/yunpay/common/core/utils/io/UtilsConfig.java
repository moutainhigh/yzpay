package com.yunpay.common.core.utils.io;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import com.yunpay.common.core.exception.BizException;
import com.yunpay.common.core.utils.DateUtils;

public final class UtilsConfig {

	private static String dirs = "";  
	
	public static String getDirs(String key,String merchantNo){	
		
		String today = DateUtils.getReqDate();
		try {
			dirs = getConfig(key)+merchantNo+"/"+today;
			File fileDir = new File(dirs);
			if(!fileDir.exists()){
				fileDir.mkdirs();
			}
		} catch (Exception e) {
			throw new BizException("创建附件目录时发生异常",e);
		}
		return dirs;
	}
	
	public static String getConfig(String key){
		Properties p = new Properties();
		InputStream stream = UtilsConfig.class.getClassLoader().getResourceAsStream("utils.properties");
		try {
			p.load(stream);
			String value = p.getProperty(key);
			return value;
		} catch (IOException e) {
			throw new BizException("配置文件读取失败",e);
		}
	}
	
	public static String getConfig(String key,String file){
		Properties p = new Properties();
		String fileName = file+".properties";
		InputStream stream = UtilsConfig.class.getClassLoader().getResourceAsStream(fileName);
		try {
			p.load(stream);
			String value = p.getProperty(key);
			return value;
		} catch (IOException e) {
			throw new BizException("配置文件读取失败",e);
		}
	}
	
	//删除文件 
	 public static void delFile(String path){
         File file=new File(path);
         if(file.exists()&&file.isFile())
             file.delete();
     }
}
