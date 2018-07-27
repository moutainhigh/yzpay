package com.yunpay.permission.shiro.filter;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * 
 * 类名称                      session监听器
 * 文件名称:     MySessionListener.java
 * 内容摘要: 	       监听session的销毁，销毁时，将该session从全局mymap、appmap中删除
 * @author:   Zhao Xiaoman
 * @version:  1.0  
 * @Date:     2017年12月1日下午5:19:49
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年12月1日   Zhao Xiaoman       1.0       新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class MySessionListener  implements HttpSessionListener
{
	public void sessionCreated(HttpSessionEvent httpSessionEvent) {
	    }

	    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
	        HttpSession session = httpSessionEvent.getSession();
	        MySessionContext.DelSession(session);
	        MySessionContext.DelAppSession(session);
	    }
}