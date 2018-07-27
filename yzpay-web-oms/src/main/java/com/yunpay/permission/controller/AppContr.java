package com.yunpay.permission.controller;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.yunpay.permission.entity.SysAppRelease;
import com.yunpay.permission.service.AppService;
/**
 * app应用管理的控制器
 * @author duan zhang quan
 *
 */
@Controller
@RequestMapping("/sys/app")
public class AppContr {
	
	@Autowired
	private AppService appService;
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
	
	@RequestMapping("/list")
	public String list(HttpServletRequest req,HttpServletResponse rep){
		List<SysAppRelease> list = this.appService.appVersion();
		for(int i=0; i<list.size(); i++){
			String appCode = list.get(i).getAppCode();
			list.get(i).setAppLink("http://siecompay.com/merch/appReleaseContr/apkDownload?appCode="+appCode);
		}
		req.setAttribute("list", list);
		return "modules/app/version";
	}
	
	
	/**
	* 转发到app版本更新的页面
	* 
	* @param 
	* @return String
	* @throws
	 */
	@RequestMapping("/appUpdate")
	public String appUpdate(HttpServletRequest req,HttpServletResponse rep){
		String appCode = req.getParameter("appCode");
		req.setAttribute("appCode", appCode);
		return "modules/app/appUpdate";
	}
	
	
	
	
	
	
}
