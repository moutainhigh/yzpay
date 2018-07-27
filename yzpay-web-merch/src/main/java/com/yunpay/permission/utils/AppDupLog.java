package com.yunpay.permission.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.yunpay.permission.shiro.filter.MySessionContext;
/**
 * 
 * 类名称                      判断APP访问时，连接的session是否存在
 * 文件名称:     AppDupLog.java
 * 内容摘要: 
 * @author:   Zhao Xiaoman
 * @version:  1.0  
 * @Date:     2017年11月27日下午4:17:07
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年11月27日   Zhao Xiaoman       1.0       新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class AppDupLog
{
	/**
	 * 判断APP访问时，连接的session是否存在,存在返回0，不存在返回1
	 * @param req
	 * @return
	 */
	public static Integer checkDup(HttpServletRequest req)
	{
		String token = req.getHeader("Cookie");
		if (token != null)
		{
			String [] strings = token.split(";");
			String [] str = strings[0].split("=");
			if (str.length>=2)
			{
				String sessionId = str[1];
				HttpSession session = MySessionContext.getAppSession(sessionId);
				if (session != null)
				{
					return 0;
				} else
				{
					return 1;
				}
			} else
			{
				return 1;
			}
		} else
		{
			return 1;
		}
	}
}
