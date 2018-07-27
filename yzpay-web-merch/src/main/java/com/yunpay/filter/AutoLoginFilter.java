package com.yunpay.filter;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;

import com.yunpay.common.core.utils.io.UtilsConfig;
import com.yunpay.h5merch.permission.entity.MerchUser;
import com.yunpay.permission.shiro.filter.MySessionContext;
/**
 * 
 * 类名称                     自动登录和公众号限制同时登录拦截器
 * 文件名称:     AutoLoginFilter.java
 * 内容摘要:     用于自动登录控制、同一账号不能同时同时在两个设备上使用
 * @author:   Zhao Xiaoman
 * @version:  1.0  
 * @Date:     2017年11月13日下午3:27:17
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年11月13日   Zhao Xiaoman       1.0       新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class AutoLoginFilter implements Filter {
	private static HashMap<String,String> userName = new HashMap<>();
	String contextPath  = null;
    public AutoLoginFilter() {}
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
			//登录时记录同账户其他同时登录的sessionId
			if (req.getQueryString() == null&&("/merch/login".equals(url)))
			{
				this.saveOther(req,rep);
			}
			//同时登录，先登录的有操作时退出
			this.delOther(req,rep);	
			//自动登录
			this.autoLog(req, rep);
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
	 * 记录有同时登录的sessionId
	 * @param req
	 * @param rep
	 */
	public void saveOther(HttpServletRequest req,HttpServletResponse rep){
		String loginName = req.getParameter("loginName");
		//取得保存session的map
		HashMap<String, HttpSession> mymap = MySessionContext.getMymap();
		//取得当前请求的sessionId
		String sesId = req.getSession().getId();
		if(mymap != null){
			//当mymap中存在与当前请求sessionId不相同的session，其保存的用户名，与当前登录请求参数中的用户名一样时，将该session的id和用户名保存在记录同时登录的userName中。
			//并将该session中mymap中移除
			for(String sessionId:mymap.keySet()){
				if (mymap.get(sessionId) != null&& !sessionId.equals(sesId))
				{
					HttpSession session = mymap.get(sessionId);
					MerchUser merchUser = (MerchUser)session.getAttribute("merchUser")==null?null:(MerchUser)session.getAttribute("merchUser");
					if (merchUser != null&&loginName !=null&&loginName.equals(merchUser.getLoginName()))
					{
						userName.put(sessionId, loginName);
						/*mymap.get(sessionId).invalidate();*/
						if (session.getAttribute("tokenLog") == null)
						{
							MySessionContext.DelSession(mymap.get(sessionId));
							break;
						}
					}
				}
			}
		}
	}
	/**
	 * 销毁先登录的session
	 * @param req
	 * @param rep
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void delOther(HttpServletRequest req,HttpServletResponse rep) throws ServletException, IOException{
		HttpSession session = req.getSession();
		String sessionId = session.getId();
		HashMap<String, HttpSession> mymap = MySessionContext.getMymap();
		//遍历cookie,取得auto字段
		Cookie[] cs = req.getCookies() == null?null:req.getCookies();
		String auto = null;
		if (cs != null)
		{
			for(Cookie c:cs){
				if(c.getName().equals("auto")){
				//如果存在自动登录的cookie
				auto =StringUtils.isBlank(c.getValue())?null:c.getValue();//用户token
				break;
				}
			}
		}
		if (auto != null)
		{
			String[] str = auto.split(",");
			sessionId = str[0];
		}
		if (userName.get(sessionId) != null)
		{
			//为自动登录的，将自动登录的session从mymap中取出并注销，将同时登录的记录从userName中移除
			//取得当前请求的session，在session中保存同时登录被踢出的标识
			//删除自动登录的cookie
			if (auto != null)
			{
				HttpSession oldSession = mymap.get(sessionId);
				MySessionContext.DelSession(oldSession);
				oldSession.invalidate();
				userName.remove(sessionId);	
				HttpSession newSession = req.getSession();
				newSession.setAttribute("flag", "1");
				//将自动登录的Cookie清空
				Cookie c = new Cookie("auto","");
			    c.setMaxAge(0);
			    c.setPath(req.getContextPath());
			     //保存cookie
			    rep.addCookie(c);
			    String url  = req.getRequestURI();  // 工程名 
			    if(req.getQueryString() == null&&("/merch".equals(url)||"/merch/".equals(url))){
			    	rep.sendRedirect(contextPath+"/?deleteFlag=1");
				}
			}else{
				//不是自动登录的，注销当前请求的session,将同时登录的记录从userName中移除
				//生成新session,并在session中保存同时登录被踢出的标识
				session.invalidate();
				userName.remove(sessionId);	
				HttpSession newSession = req.getSession();
				newSession.setAttribute("flag", "1");
			}
		}
	}
	/**
	 * 自动登录
	 * @param req
	 * @param rep
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void autoLog(HttpServletRequest req,HttpServletResponse rep) throws ServletException, IOException{
		String url  = req.getRequestURI();  // 工程名 
		//遍历cookie,自动登录的放行
		Cookie[] cs = req.getCookies() == null?null:req.getCookies();
		String auto = null;
		if (cs != null)
		{
			for(Cookie c:cs){
				if(c.getName().equals("auto")){
					//如果存在自动登录的cookie
					auto =StringUtils.isBlank(c.getValue())?null:c.getValue();//用户token
					break;
				}
			}
		}
		if (auto != null)
		{
			String[] str = auto.split("#");
			String sessionId = str[0];
			String tokenLog = str[1];
			//通过sessionId取得session
			HttpSession session = MySessionContext.getSession(sessionId);
			if (session != null)
			{
	 	 		String sessionToken = StringUtils.isBlank((String)session.getAttribute("tokenLog"))?null:(String)session.getAttribute("tokenLog");
	 	 		MerchUser merchUser = (MerchUser)session.getAttribute("merchUser")==null?null:(MerchUser)session.getAttribute("merchUser");
	 	 		if (tokenLog.equals(sessionToken) && merchUser != null)
	 	 		{
	 	 			HttpSession newSession = req.getSession();
	 	 			newSession.setAttribute("merchUser",merchUser); 
	 	 			newSession.setMaxInactiveInterval(24*60*60);
	 	 			/*MySessionContext.DelSession(jsSession);*/
	 	 			//判断请求参数是否为空 
	 	 			if(req.getQueryString() == null&&("/merch".equals(url)||"/merch/".equals(url))){
	 	 				// 转发到商户登录页面
	 	 				if (merchUser.getMerchantNo()!=null)
	 	 				{
	 	 					rep.sendRedirect(contextPath+"/sys/index/index");
	 	 				} else
	 	 				{
	 	 					rep.sendRedirect(contextPath+"/sys/index/indexundo");
	 	 				}
	 	 			}
	 	 		}
			}
		}
	}
	/**
	 * 登录的可以访问资源，未登录或同时登录被踢出的直接转发到登录界面
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
			String flag = (String)req.getSession().getAttribute("flag") == null?null:(String)req.getSession().getAttribute("flag");
			String type = req.getHeader("X-Requested-With")==null?"":req.getHeader("X-Requested-With");// XMLHttpRequest 
			if (StringUtils.equals("XMLHttpRequest", type))
			{
				// 处理ajax请求，直接返回，通知前端跳转登录界面
		        rep.setHeader("REDIRECT", "REDIRECT");//告诉ajax这是重定向
		        if("1".equals(flag)){
		        	rep.setHeader("DPLOGIN", "DPLOGIN");//告诉ajax这是重复登录
		        }
		        return;
			}else{
				//非ajax请求，通知前端重定向
				if ("1".equals(flag))
				{
					rep.sendRedirect(contextPath+"/?deleteFlag=1");
				}else{
					rep.sendRedirect(contextPath+"/");
				}
			}
		}
	}
}
