package com.yunpay.permission.shiro.filter;

import java.util.HashMap;
import javax.servlet.http.HttpSession;
/**
 * 
 * 类名称		       自动登录和同时登录session管理类
 * 文件名称:     MySessionContext.java
 * 内容摘要: 	      保存和 管理自动登录、同时登录的session
 * @author:   Zhao Xiaoman
 * @version:  1.0  
 * @Date:     2017年12月1日下午5:17:48
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年12月1日   Zhao Xiaoman       1.0       新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class MySessionContext
{
	 private static HashMap<String,HttpSession> mymap = new HashMap<>();
	 private static HashMap<String,HttpSession> appmap = new HashMap<>();
	 /*private static HashMap<String,HttpSession> inforMap = new HashMap<>();*/

	    public static HashMap<String, HttpSession> getMymap()
	{
		return mymap;
	}
	    public static HashMap<String, HttpSession> getAppmap()
	    {
	    	if (appmap==null)
			{
				return null;
			} else
			{
				return appmap;
			}
	    	
	    }

		public static synchronized void AddSession(HttpSession session) {
	        if (session != null) {
	            mymap.put(session.getId(), session);
	        }
	    }
		public static synchronized void AddAppSession(HttpSession session) {
			if (session != null) {
				appmap.put(session.getId(), session);
			}
		}

	    public static synchronized void DelSession(HttpSession session) {
	        if (session != null) {
	            mymap.remove(session.getId());
	        }
	    }
	    public static synchronized void DelAppSession(HttpSession session) {
	    	if (session != null) {
	    		appmap.remove(session.getId());
	    	}
	    }

	    public static synchronized HttpSession getSession(String session_id) {
	        if (session_id == null) 
	        return null;
	        return (HttpSession) mymap.get(session_id);
	    }
	    public static synchronized HttpSession getAppSession(String session_id) {
	    	if (session_id == null) 
	    		return null;
	    	return (HttpSession) appmap.get(session_id);
	    }
	    
	    /*public static HttpSession getInforSession(String sessionId)
		{
	    	if (sessionId == null){
	    		return null;
	    	}
			return inforMap.get(sessionId);
		}
		public static void delInforSession(HttpSession session)
		{
			if (session != null) {
				inforMap.remove(session.getId());
			}
		}
		public static synchronized void AddInforSession(HttpSession session) {
			if (session != null) {
				inforMap.put(session.getId(), session);
			}
		}*/
}
