package com.yunpay.filter;
import java.io.IOException;
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
import com.yunpay.h5merch.permission.entity.MerchUser;
/**
 * 
 * 类名称		       微信登陆拦截器类
 * 文件名称:     WxLoginFilter.java
 * 内容摘要: 	       用于处理页面放置超时，非法链接
 * @author:   Zhao Xiaoman
 * @version:  1.0  
 * @Date:     2018年1月15日上午11:46:21
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2018年1月15日   Zhao Xiaoman       1.0       新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class WxLoginFilter implements Filter {
	String contextPath  = null;
    public WxLoginFilter() {}
	public void destroy() {}
	/**
	 * 用于拦截处理公众号同时登录（主方法）
	 * @param request
	 * @param response
	 * @param chain
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse rep = (HttpServletResponse)response;
		//取得请求的浏览器类型
		String userAgent = req.getHeader("user-agent");
		//取得请求路径
		String url  = req.getRequestURI();

		//取得放行地址的配置
		String[] releaseList = UtilsConfig.getConfig("autologinfilter.release", "sys").split(",");
		Boolean bool = false;
		
		//判断请求地址是否在自动放行列表中
		for(int i = 0;i<releaseList.length;i++){
			bool = bool||url.indexOf(releaseList[i])>-1;
			if (bool)
			{
				break;
			}
		}
		
		//当不是APP浏览器，并不在直接放行列表中，拦截处理
		if (!bool&&(userAgent.indexOf("SiecomWebview") <= -1))
		{
			//当请求路径包含sys时，进行同时登录控制
			if (url.indexOf("sys")>-1)
			{
				this.sessionCtr(req,rep,chain);
			} else {
				chain.doFilter(req, rep);
			}
		}else{
			chain.doFilter(req, rep);
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
		contextPath = fConfig.getServletContext().getContextPath();
	}
	
	/**
	 * 未登录或页面超时直接关闭页面
	 * @param req
	 * @param rep
	 * @param chain
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void sessionCtr(HttpServletRequest req,HttpServletResponse rep,FilterChain chain) throws ServletException, IOException{
		HttpSession session = req.getSession();
		MerchUser user = (MerchUser)session.getAttribute("merchUser");
		if(user != null ){
			chain.doFilter(req, rep);
		}else{
			// 转发到商户登录页面
			String type = req.getHeader("X-Requested-With")==null?"":req.getHeader("X-Requested-With");// XMLHttpRequest 
			if (StringUtils.equals("XMLHttpRequest", type))
			{
				// 处理ajax请求，直接返回，通知前端跳转登录界面
		        rep.setHeader("REDIRECT", "REDIRECT");//告诉ajax这是重定向
		        return;
			}else{
				//非ajax请求，通知前端重定向
				rep.sendRedirect(contextPath+"/wxerror?flag=1");
			}
		}
	}
}
