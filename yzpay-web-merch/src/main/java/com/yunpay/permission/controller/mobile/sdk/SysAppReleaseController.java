package com.yunpay.permission.controller.mobile.sdk;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import com.yunpay.common.core.exception.BizException;
import com.yunpay.common.core.utils.io.UploadConfig;
import com.yunpay.controller.common.BaseController;
import com.yunpay.h5merch.permission.entity.ReciveMsg;
import com.yunpay.h5merch.service.impl.SysAppReleaseServiceImpl;
import com.yunpay.permission.entity.SysAppRelease;
/**
 * 
 * 文件名称:
 * 内容摘要:  App更新的控制器
 * @version:  1.0  
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  

 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 **/
@Controller
@RequestMapping("/appReleaseContr")
public class SysAppReleaseController extends BaseController{
	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(SysAppReleaseController.class);
	@Autowired
	private  SysAppReleaseServiceImpl sysAppReleaseService;

	/**
	 * 返回app的版本信息
	* @param 
	* @return Object
	* @throws
	 */
	@RequestMapping(value = "/release/{appCode}")
	public @ResponseBody ReciveMsg<SysAppRelease> release(@PathVariable("appCode") String appCode,HttpServletRequest req,HttpServletResponse rep){
		if(StringUtils.isEmpty(appCode)){
			return new ReciveMsg<SysAppRelease>(0,"4000","appCode parameter cannot be empty",null); 
		}
		SysAppRelease app = sysAppReleaseService.findByAppCode(appCode);
		if(app == null){
			return new ReciveMsg<SysAppRelease>(0,"4003","the app is not exist",null);
		}
		app.setAppLink("http://siecompay.com/merch/appReleaseContr/apkDownload?appCode="+appCode);
		return new ReciveMsg<SysAppRelease>(1,"","",app);
	}
	
	
	/**
	 * apk文件下载
	* @param 
	* @return void
	* @throws
	 */
	@RequestMapping("/apkDownload")
	public void  apkDownload(HttpServletRequest req,HttpServletResponse rep){
		String appCode = req.getParameter("appCode");
		SysAppRelease app = sysAppReleaseService.findByAppCode(appCode);
		String appLink = app.getAppLink();
		rep.setContentType("application/octet-stream");
		try{
			ServletOutputStream servletOutputStream = rep.getOutputStream();
			//不在网页中打开，而是直接下载该文件，下载后的文件名为Example.pdf
			rep.setHeader("Content-disposition", "attachment;filename="+app.getAppCode()+"_v"+app.getVerName()+".apk");  
			File f = null;
			FileInputStream fis  = null;
			byte[] buffer = new byte[1024*1024]; 
			f = new File(appLink);
			if(!f.exists()){
				System.out.println("位于"+appLink+"的apk文件不存在");
				return;
			}
			rep.setContentLength((int)f.length());
			fis = new FileInputStream(f);  
			int readBytes = -1;
			double totalBytes = 0;
			while((readBytes = fis.read(buffer,0,1024*1024)) != -1){
				servletOutputStream.write(buffer,0,1024*1024);
				totalBytes = totalBytes + readBytes;
			}
			System.out.println("总字节数**"+totalBytes);
			servletOutputStream.flush();
			servletOutputStream.close();
			fis.close();
		}catch(FileNotFoundException e){
			throw new BizException("未找到apk文件", e);
		}catch(IOException e){
			throw new BizException("未找到apk文件", e);
		}	
	}
	
	
	
	/**
	 * apk文件上传
	* @param 
	* @return void
	* @throws    
	 */
	@RequestMapping(value="/apkUpload",method=RequestMethod.POST)
	public @ResponseBody String apkUpload(HttpServletRequest req,HttpServletResponse rep,@RequestParam("appApkFile") CommonsMultipartFile  file){
		String appCode = req.getParameter("appCode");
		String verName = req.getParameter("verName");
		String verCode = req.getParameter("verCode");
		// 允许ajax跨域
		rep.setHeader("Access-Control-Allow-Origin", "http://218.17.109.74:8082/yunpay,http://192.168.0.33:8082/yunpay");
		rep.setHeader("Access-Control-Allow-Methods", "POST");
		try{
			if(file != null){
				long fileSize = file.getSize();
				if(fileSize >0){
					Thread.sleep(2000);
					String OriginalFilename = file.getOriginalFilename();
					String savePath = UploadConfig.getAppDirs() ; 
					File newFile = new File(savePath+OriginalFilename);
					FileOutputStream fileOutputStream = new FileOutputStream(newFile);
					// 得到文件输入流
					InputStream  fileInputStream =(InputStream)file.getInputStream();
					int b = -1;
					byte [] buffer = new byte[1024 * 1024];  // 缓冲,单位为1MB
					// 以写字节的方式写文件
					while((b = fileInputStream.read(buffer, 0, 1024 * 1024)) != -1){
						fileOutputStream.write(buffer, 0, 1024 * 1024);
					}
					fileOutputStream.flush();
					fileOutputStream.close();
					fileInputStream.close();
					String appLink = savePath+OriginalFilename;
					this.sysAppReleaseService.updateVersion(verName,verCode,appLink,appCode);
				}
			}
			return "success";
		}catch(Exception e){
			e.printStackTrace();
			return "fail";
		}
	}
	
	
	/**
	 * @throws IOException 
	 * jsonp测试
	* 
	* @param 
	* @return String
	* @throws
	 */
	@RequestMapping(value="/jsonpTest",method=RequestMethod.GET)
	public void jsonp(HttpServletRequest req,HttpServletResponse rep) throws IOException{
		String callback = req.getParameter("callback");
		rep.setCharacterEncoding("utf-8");  
		rep.setContentType("text/html");
		PrintWriter writer = rep.getWriter(); 
		if(!StringUtils.isEmpty(callback)){
			String data = "this is jsonp sample";
			writer.write(callback+"('"+data+"')");
		}else{
			writer.write("error");
		}
		writer.flush();
		writer.close();
		
	}
	
	

}
