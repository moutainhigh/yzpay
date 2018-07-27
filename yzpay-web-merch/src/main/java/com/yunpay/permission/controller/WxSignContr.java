/*package com.yunpay.permission.controller;
import java.util.UUID;
import java.util.Map;
import java.util.HashMap;
import java.util.Formatter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.UnsupportedEncodingException;  

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunpay.common.core.exception.BizException;
import com.yunpay.permission.utils.WxTicketThread;


	*//**
	 * 
	 * 文件名称:
	 * 内容摘要: 微信公众号调用JS-SDK 时需要的签名
	 * @author:   duan zhang quan
	 * @version:  1.0  
	 * @date:     2017年4月14日上午9:56:12 
	 * 
	 * 修改历史:  
	 * 修改日期                     修改人员                                   版本	            修改内容  
	 * ----------------------------------------------  
	 * 2017年4月14日     DUAN ZHANG QUAN   1.0     新建
	 *
	 * 版权:   版权所有(C)2017
	 * 公司:   深圳市至高通信技术发展有限公司
	 *//*
	 @Controller
	 @RequestMapping("/sys/wxSignContr")
	 public class WxSignContr {
		private static final String APPID = "wx898f34bb7993ea5a";  // 公众号的唯一标识
		 
	    *//**
	     * 
	    * 进行签名
	    * @param 
	    * @return Map<String,String>
	    * @throws
	     *//*
	    @RequestMapping("/sign")
	    public @ResponseBody Map<String, String> sign(HttpServletRequest req) {
	        Map<String, String> ret = new HashMap<String, String>();
	        String nonce_str = createNonceStr();
	        String timestamp = createTimeStamp();
	        String string1;
	        String signature = "";
	        //注意这里参数名必须全部小写，且必须有序
	        string1 = "jsapi_ticket=" + WxTicketThread.jsapiTicket.getTicket() +
	                  "&noncestr=" + nonce_str +
	                  "&timestamp=" + timestamp +
	                  "&url=" + req.getParameter("url");
	        System.out.println(string1);
	        try{
	            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
	            crypt.reset();
	            crypt.update(string1.getBytes("UTF-8"));
	            signature = byteToHex(crypt.digest());
	            ret.put("url",req.getParameter("url"));
		        ret.put("jsapi_ticket", WxTicketThread.jsapiTicket.getTicket());
		        ret.put("nonceStr", nonce_str);
		        ret.put("timestamp", timestamp);
		        ret.put("signature", signature);
		        ret.put("appId", APPID);
	        }catch (NoSuchAlgorithmException e){
	        	ret.put("exception", "noSuchAlgorithmException");
	        	throw new BizException("进行签名算法时发生异常", e);
	        }catch (UnsupportedEncodingException e){
	        	ret.put("exception", "unsupportedEncodingException");
	        	throw new BizException("进行签名算法时发生异常", e);
	        }
	        return ret;
	    }

	    *//**
	     * 
	    * 转换成16进制
	    * @param 
	    * @return String
	    * @throws
	     *//*
	    private static String byteToHex(final byte[] hash) {
	        Formatter formatter = new Formatter();
	        for (byte b : hash){
	        	formatter.format("%02x", b);
	        }
	        String result = formatter.toString();
	        formatter.close();
	        return result;
	    }
	    
	    *//**
	     * 
	    * 生成签名的随机串
	    * @param 
	    * @return String
	    * @throws
	     *//*
	    private static String createNonceStr() {
	        return UUID.randomUUID().toString();
	    }

	    *//**
	     * 
	    * 生成签名的时间戳
	    * @param 
	    * @return String
	    * @throws
	     *//*
	    private static String createTimeStamp() {
	        return Long.toString(System.currentTimeMillis() / 1000);
	    }
}
*/