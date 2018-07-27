package com.yunpay.permission.utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.WritableResource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import com.alibaba.fastjson.JSON;
import com.yunpay.common.core.utils.io.UtilsConfig;
import com.yunpay.h5merch.permission.entity.ReciveMsg;
/**
 * 
 * 类名称		        图片上传到微信
 * 文件名称:     ImageSave.java
 * 内容摘要: 
 * @author:   Zhao Xiaoman
 * @version:  1.0  
 * @Date:     2017年10月9日下午8:29:06
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年10月9日   Zhao Xiaoman       1.0       新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class ImageSave {
	@SuppressWarnings("unused")
	private static final Log LOG = LogFactory.getLog(ImageSave.class);
	@SuppressWarnings("unused")
	/**
	 * 上传图片到微信
	 * @param imageUrl
	 * @param merchantNo
	 * @param salt
	 * @param channel
	 * @return
	 */
	public static Map<String, String> sendFile(String imageUrl, String merchantNo,String salt,Integer channel){
		String flag = null;
		String imgUrl = null;
		Map<String, String> map = new HashMap<>();
		//取得微信图片上传接口地址
		String url = UtilsConfig.getConfig("memberCard.images.url");
		//MD5加密生成签名
		String sign = getMD5(merchantNo,salt);
		//组装图片上传数据
		WritableResource resource = new FileSystemResource(new File(imageUrl));
		MultiValueMap<String,Object> data = new LinkedMultiValueMap<String,Object>();
		data.add("file", resource);
		data.add("merchant_num", merchantNo);
		data.add("sign", sign);
		data.add("sign_type", "MD5");
		data.add("put_channel", channel);
		RestTemplate restTemplate = new RestTemplate();
		try {
			String recive = restTemplate.postForObject(url, data, String.class);
	        System.out.println(recive); //打印返回消息状态        
			@SuppressWarnings("unchecked")
			ReciveMsg<Map<String, Object>> reciveMsg = JSON.parseObject(recive, ReciveMsg.class);
	        //这里就是返回结果
	        String result_code = reciveMsg.getResult_code();
	        String result_msg = reciveMsg.getResult_msg();
	        String err_code = reciveMsg.getErr_code();
	        String err_code_des = reciveMsg.getErr_code_des();
	        Map<String, Object> datar = reciveMsg.getData();      
			if ("SUCCESS".equals(result_code)) {
				imgUrl = (String)datar.get("img_url");
				flag = "SUCCESS";
			}else{
				flag = err_code_des;
				System.out.println("上传会员卡LOGO图片到微信/支付宝失败："+ err_code_des);
			}
		} catch (Exception e) {
			flag = "系统错误";
			e.printStackTrace();
		} 
		map.put("message", flag);
		map.put("imgUrl", imgUrl);
		return map;
	}

	/**
	 * 密码MD5加密
	 * @param loginPwd
	 * @return
	 */
	public static String getMD5(String loginPwd,String pwdSalt) {	
		int hashIterations = Integer.valueOf("2");
		String newPassword = new SimpleHash("md5", loginPwd, ByteSource.Util.bytes(pwdSalt), hashIterations).toHex();	
		return newPassword;
	}
}


