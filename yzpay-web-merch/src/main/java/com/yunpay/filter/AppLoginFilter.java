package com.yunpay.filter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.yunpay.common.core.utils.io.UtilsConfig;
import com.yunpay.permission.shiro.filter.MySessionContext;
import com.yunpay.permission.utils.AppDupLog;
/**
 * 
 * 类名称                      App限制一个号同时登录两个设备拦截器
 * 文件名称:     AppLoginFilter.java
 * 内容摘要: 	       通过APP传递过来的cookie实现一个号同时登录两台设备的拦截，不区分服务器重启导致的服务器session清空
 * @author:   Zhao Xiaoman
 * @version:  1.0  
 * @Date:     2017年11月13日下午3:28:10
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年11月13日   Zhao Xiaoman       1.0       新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class AppLoginFilter implements Filter {
	String contextPath  = null;
    public AppLoginFilter() {}
	public void destroy() {}
	
	/**用于拦截处理APP同时登录（主方法）
	 * @param request
	 * @param response
	 * @param chain
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse rep = (HttpServletResponse)response;
		String url  = req.getRequestURI();  // 工程名 
		System.out.println("url=="+url);
		HttpSession session = req.getSession();
		System.out.println("session=="+session.getId());
		System.out.println("cookie=="+req.getHeader("Cookie"));
		String userAgent = req.getHeader("user-agent");
		//登录时记录同账户其他同时登录的sessionId
		if (req.getQueryString() == null&&("/merch/cashierContr/mobile/login".equals(url)))
		{
			this.saveOther(req,rep);
		}else{
			//判断是否是APP的浏览器
			if(userAgent.indexOf("SiecomWebview") > -1)
			{
				//取得放行地址的配置
				String[] releaseList = UtilsConfig.getConfig("apploginfilter.release", "sys").split(",");
				//判断请求地址是否在自动放行列表中
				Boolean bool = false;
				for(int i = 0;i<releaseList.length;i++){
					bool = bool||url.indexOf(releaseList[i])>-1;
					if (bool)
					{
						//当bool为true时，跳出循环
						break;
					}
				}
				//当请求地址不在放行列表中,进行同时登陆控制
				if (!bool)
				{
					this.redirect(req, rep, chain);
				}
			}
		}
		chain.doFilter(req, rep);
	}
	
	/**
	 * 记录有同时登录的sessionId
	 * @param req
	 * @param rep
	 */
	@SuppressWarnings("unchecked")
	public void saveOther(HttpServletRequest req,HttpServletResponse rep){
		String loginName = req.getParameter("userName");
		String channel = req.getParameter("channel");
		//取得保存登录的用户的session的MAP
		HashMap<String, HttpSession> appmap = MySessionContext.getAppmap();
		String sesId = req.getSession().getId();
		if(appmap.size()>0){
			for(String sessionId:appmap.keySet()){
				if (appmap.get(sessionId) != null&& !sessionId.equals(sesId))
				{
					HttpSession session = appmap.get(sessionId);
					//对比登录渠道和用户名，若相同，则修改前一个登录的session的登录状态
					if (session != null)
					{
						Map<String, String> duplicate = (Map<String, String>)session.getAttribute("duplicate")==null?null:(Map<String, String>)session.getAttribute("duplicate");
						if (duplicate != null&&loginName !=null&&loginName.equals(duplicate.get("loginName"))&&channel != null&&channel.equals(duplicate.get("channel")))
						{
							MySessionContext.DelAppSession(session);
							break;
						}
					}
				}
			}
		}
	}
	
	/**
	 * 跳转到登录
	 * @param req
	 * @param rep
	 * @param chain
	 * @throws IOException
	 * @throws ServletException
	 */
	public void redirect(HttpServletRequest req,HttpServletResponse rep,FilterChain chain) throws IOException, ServletException
	{
		//取得请求头的请求类型
		String type = req.getHeader("X-Requested-With")==null?"":req.getHeader("X-Requested-With");
		//未取得session则按状态丢失/同时登录处理
		if (AppDupLog.checkDup(req) == 1)
		{
			HttpSession session = req.getSession();
			session.invalidate();
			if (StringUtils.equals("XMLHttpRequest", type))
			{
				// 处理ajax请求
				rep.setHeader("REDIRECT", "REDIRECT");//告诉ajax这是重定向
				return;
			}else{
				//向前端传送标识符，转登录界面
				req.setAttribute("duplicateLogin", "duplicateLogin");
			}
		}
	}
	
	public void init(FilterConfig fConfig) throws ServletException {
		contextPath = fConfig.getServletContext().getContextPath();
	}
	
}
