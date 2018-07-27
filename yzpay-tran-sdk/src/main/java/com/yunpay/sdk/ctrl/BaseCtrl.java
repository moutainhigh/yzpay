package com.yunpay.sdk.ctrl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 服务控制器基类
 * 文件名称:     BaseController.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年6月22日下午1:57:39 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年6月22日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class BaseCtrl {
	
	/**
	 * @Description: 从request获取请求参数封装到Map<String,String>中
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年6月22日下午1:59:56 
	 * @param request
	 * @return
	 */
	public Map<String,String> getRequestParams(HttpServletRequest request){
		Map<String,String> params = new HashMap<String, String>();
		Map<String,String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			params.put(name, valueStr);
		}
		return params;
	}
	
	/**
	 * @Description: 打印输出
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年6月22日下午2:10:10 
	 * @param response
	 * @param s
	 * @throws IOException 
	 * @throws Exception
	 */
	public void Print(HttpServletResponse response,String s) throws IOException{
		PrintWriter writer = response.getWriter();
		writer.write(s);
		writer.flush();
		writer.close();
	}
	
	
	
}
