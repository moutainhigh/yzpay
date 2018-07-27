package com.yunpay.permission.utils;

import java.util.HashMap;
import java.util.Map;
import com.alibaba.fastjson.JSON;
import com.yunpay.common.core.utils.io.UtilsConfig;

/**
 * 
 * 类名称		       公众号微信登陆接口类
 * 文件名称:     WxHttpReq.java
 * 内容摘要: 	       微信公众号登陆接口的调用，取得access_token接口、刷新access_token接口、验证access_token有效性接口、取得微信用户信息接口
 * @author:   Zhao Xiaoman
 * @version:  1.0  
 * @Date:     2018年1月11日上午11:37:38
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2018年1月11日   Zhao Xiaoman       1.0       新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */

public class WxHttpReq
{
	/**
	 * 取得access和openId
	 * @param code
	 * @return
	 */
	public static Map<String, Object> getAccess (String code)
	{
		String grantType = "authorization_code";
		//取得配置文件配置的接口地址和参数信息
		String url = UtilsConfig.getConfig("wxlogin.access","sys");
		String appId = UtilsConfig.getConfig("wxlogin.appId","sys");
		String appSecret = UtilsConfig.getConfig("wxlogin.appSecret","sys");
		System.out.println("appId=="+appId);
		
		//组装接口参数
		String param = "appid="+appId+"&secret="+appSecret+"&code="+code+"&grant_type="+grantType;
		
		//调用接口
		String result = HttpRequest.sendGet(url, param);		
		System.out.println(result);
		
		//处理并返回数据
		return doResult(result);
	}
	
	/**
	 * 刷新access_token
	 * @param refreshToken
	 * @return
	 */
	public static Map<String, Object> getRefresh (String refreshToken)
	{
		String grantType = "authorization_code";
		//取得配置文件配置的接口地址和参数信息
		String url = UtilsConfig.getConfig("wxlogin.refresh","sys");
		String appId = UtilsConfig.getConfig("wxlogin.appId","sys");
		
		//组装接口参数
		String param = "appid="+appId+"&grant_type="+grantType+"&refresh_token="+refreshToken;
		
		//调用接口
		String result = HttpRequest.sendGet(url, param);		
		System.out.println(result);
		
		//处理并返回数据
		return doResult(result);
	}
	
	/**
	 * 取得用户信息
	 * @param openId 用户openId
	 * @param accessToken
	 * @return
	 */
	public static Map<String, Object> getUserInfo (String openId,String accessToken)
	{
		String lang = "zh_CN";
		//取得配置文件配置的接口地址
		String url = UtilsConfig.getConfig("wxlogin.userinfo","sys");

		//组装接口参数
		String param = "access_token="+accessToken+"&openid="+openId+"&lang="+lang;
		
		//调用接口
		String result = HttpRequest.sendGet(url, param);		
		System.out.println(result);
		
		//处理并返回数据
		return doResult(result);
	}
	
	/**
	 * 验证access_token是否有效
	 * @param openId
	 * @param accessToken
	 * @return
	 */
	public static Map<String, Object> getAccessTerm (String openId,String accessToken)
	{
		//取得配置文件配置的接口地址
		String url = UtilsConfig.getConfig("wxlogin.auth","sys");
		
		//组装接口参数
		String param = "access_token="+accessToken+"&openid="+openId;
		
		//调用接口
		String result = HttpRequest.sendGet(url, param);		
		System.out.println(result);
		
		//处理并返回数据
		return doResult(result);
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String, Object> doResult(String result)
	{
		String message = "FAIL";
		Map<String, Object> map = new HashMap<>();
		if (result.indexOf("errcode")<0)
		{
			message = "SUCCESS";
			map = (Map<String, Object>)JSON.parse(result);
			for (String key : map.keySet()){  
	            System.out.println(key+"===========" + map.get(key));  
	        }  
		} 		
		map.put("message", message);
		return map;
	}
}
