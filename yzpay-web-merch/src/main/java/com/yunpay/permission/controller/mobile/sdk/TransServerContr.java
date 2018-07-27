package com.yunpay.permission.controller.mobile.sdk;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;
import com.yunpay.common.core.utils.io.UtilsConfig;
import com.yunpay.h5merch.permission.entity.ReciveMsg;
import com.yunpay.permission.utils.AppDupLog;

/**
 * 
 * 类名称                      APP交易业务转发
 * 文件名称:     TransServerContr.java
 * 内容摘要: 
 * @author:   Zhao Xiaoman
 * @version:  1.0  
 * @Date:     2017年11月6日上午11:35:43
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年11月6日   Zhao Xiaoman       1.0       新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
@Controller
@RequestMapping("/trans")
public class TransServerContr
{
	@SuppressWarnings("unused")
	@RequestMapping("/receive")
	public @ResponseBody ReciveMsg<Map<String, Object>> receiveReq(HttpServletRequest req,HttpServletResponse rep) 
	{
		String message = null;
		String param = null;
		String duplicateLogin = req.getParameter("duplicateLogin") == null?null:req.getParameter("duplicateLogin");
		if (AppDupLog.checkDup(req) == 1)
		{
			//APP同时登录控制
			message = "The user account has been logged on to another machine";
			ReciveMsg<Map<String, Object>> reciveMsg = new ReciveMsg<Map<String, Object>>(0, "6020", message, null);
			return reciveMsg;
		} 
		String type = req.getParameter("type")==null?null:req.getParameter("type");
		if (type == null)
		{
			message = "The type is null";
			ReciveMsg<Map<String, Object>> reciveMsg = new ReciveMsg<Map<String, Object>>(0, "6021", message, null);
			return reciveMsg;
		}else if(UtilsConfig.getConfig("trans.url."+type) == null){
			message = "Not found the type";
			ReciveMsg<Map<String, Object>> reciveMsg = new ReciveMsg<Map<String, Object>>(0, "6022", message, null);
			return reciveMsg;
		}else {
			// 取得对应接口地址
			String url = UtilsConfig.getConfig("trans.url."+type);
			try
			{
				//对接收的参数进行UTF-8转码
				//param = URLDecoder.decode(param,"UTF-8");
				param = getRequestParams(req);
			} catch (Exception e)
			{
				message = "Chinese parameter transcoding failed";
				System.out.println("请求中文参数转码失败：" + e);
				e.printStackTrace();
				ReciveMsg<Map<String, Object>> reciveMsg = new ReciveMsg<Map<String, Object>>(0, "6023", message, null);
				return reciveMsg;
			}
			System.out.println("string==="+param);
			
			try {
				System.out.println("string1==="+ URLEncoder.encode(param, "utf-8"));
				url = url+"?"+param;
					//创建接口连接
					URLConnection conn = this.getPostHttpConn(url);
					BufferedReader in = null;
					String result = ""; 
					PrintWriter out = null; 
					out = new PrintWriter(conn.getOutputStream());
					// 发送请求参数  
					out.print(URLEncoder.encode("","utf-8"));  
					// flush输出流的缓冲  
					out.flush();  
					// 定义BufferedReader输入流来读取URL的响应  
					in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));  
					String line;  
					while ((line = in.readLine()) != null) {
						result += line;  
					}  
					@SuppressWarnings("unchecked")
					ReciveMsg<Map<String, Object>> reciveMsg = JSON.parseObject(result, ReciveMsg.class);
					//这里就是返回结果
					String result_code = reciveMsg.getResult_code();
					String result_msg = reciveMsg.getResult_msg();
					String err_code = reciveMsg.getErr_code();
					String err_code_des = reciveMsg.getErr_code_des();
					Map<String, Object> datar = reciveMsg.getData();       
					if ("SUCCESS".equals(result_code)) {
						System.out.println("成功");
					}else{
						System.out.println("失败："+ err_code_des);
					}
					return reciveMsg;
			} catch (Exception e) { 
				message = "System exception";
				System.out.println("发送 POST 请求出现异常：" + e);  
				e.printStackTrace();
				ReciveMsg<Map<String, Object>> reciveMsg = new ReciveMsg<Map<String, Object>>(0, "6024", message, null);
				return reciveMsg;
			}
		}
	}
	/**
	 * 打开接口连接
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public URLConnection getPostHttpConn(String url) throws Exception {
        URL object = new URL(url);
        URLConnection conn;
        conn = object.openConnection();
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("accept", "application/json");
        conn.setRequestProperty("user-agent",  
                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)"); 
        return conn;
	}
	
	/**
	 * @Description: 从request获取请求参数封装到Map<String,String>中
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年6月22日下午1:59:56 
	 * @param request
	 * @return
	 */
	public String getRequestParams(HttpServletRequest request)throws Exception {
		String param = null;
		@SuppressWarnings("unchecked")
		Map<String,String[]> requestParams = request.getParameterMap();
		StringBuilder str = new StringBuilder();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			System.out.println(name+"="+valueStr);
			if (valueStr.contains("&"))
			{
				valueStr =  URLEncoder.encode(valueStr, "utf-8");
			}			
			System.out.println(name+"1="+valueStr);
			str.append(name).append("=").append(valueStr).append("&");
			//params.put(name, valueStr);
		}
		param = str.substring(0, str.length()-1);
		return param;
	}
}
