package com.yunpay.permission.utils;

import java.util.Random;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
/**
 * 
 * 类名称		        短信接口（暂不用）
 * 文件名称:     SmsUtils.java
 * 内容摘要: 	       用于发送短信验证码
 * @author:   Zhao Xiaoman
 * @version:  1.0  
 * @Date:     2017年12月4日上午9:40:12
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年12月4日   Zhao Xiaoman       1.0       新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class SmsUtils {
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(SmsUtils.class);
	
	private static final String url = "http://www.qybor.com:8500/shortMessage";
	//账号
	private static final String account = "gdylkjhy";
	//密码
	private static final String password = "gdylkjhy";
	
	public static boolean sendSmsCode(String phone){
		String smsCode = getSmsCode();
		return sendSmsCode(phone, smsCode);
	}	
	@SuppressWarnings("unused")
	public static boolean sendSmsCode(String phone, String smsCode){
		boolean flag = false;
//		String smsCode = getSmsCode();
//		Long validTime = DateUtil.getAfterTwoMinute();
//		String content = "您的验证码是：" + smsCode + " 请不要把验证码泄露给其他人【至高通信商户】";
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(url);
		method.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
		String content = "您的验证码是：" + smsCode + " 请不要把验证码泄露给其他人【杭州盈承商户】";
		NameValuePair[] data = { // 提交短信
				new NameValuePair("username", account),
				new NameValuePair("passwd", password),
				new NameValuePair("phone", phone),
				new NameValuePair("msg", content), 
				new NameValuePair("needstatus", "true"),
				new NameValuePair("port",""),
				new NameValuePair("sendtime","")};
		try {
			method.setRequestBody(data);
			client.executeMethod(method);
			Header[] headers = method.getResponseHeaders();
	        int statusCode = method.getStatusCode();        
	        String result = new String(method.getResponseBodyAsString().getBytes()); 
	        System.out.println(result); //打印返回消息状态        
	        method.releaseConnection();  
	        ResultObj group2 = JSON.parseObject(result, ResultObj.class);
	        //这里就是返回结果
	        String batchno = group2.getBatchno();
	        String respcode = group2.getRespcode();
	        String respdesc = group2.getRespdesc();
	        System.out.println(batchno);
	        System.out.println(respcode);
	        System.out.println(respdesc);        

			if ("0".equals(respcode)) {
				flag = true;
				System.out.println("短信提交成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return flag;
	}

	
	
	
	public static String getSmsCode(){
		String code = "";
		for (int i = 0; i < 4; i++) {
			code += randomChar();
		}
		return code;
	}

	
	private static char randomChar() {
		Random r = new Random();
		//String s = "ABCDEFGHJKLMNPRSTUVWXYZ0123456789";
		String s = "0123456789";
		return s.charAt(r.nextInt(s.length()));
	}
	
	
	public static void main(String[] args){
		sendSmsCode("15889358195", "您的验证码是：123456  请不要把验证码泄露给其他人【至高通信商户】");
	}
			

}

