package com.yunpay.permission.utils.phonecore;

import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Random;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
/**
 * 
 * 类名称		        发送短信工具类
 * 文件名称:     SmsSenderUtil.java
 * 内容摘要: 
 * @author:   Zhao Xiaoman
 * @version:  1.0  
 * @Date:     2017年12月4日上午10:21:10
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年12月4日   Zhao Xiaoman       1.0       新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class SmsSenderUtil
{

    protected Random random = new Random();
    
    public String stringMD5(String input) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        byte[] inputByteArray = input.getBytes();
        messageDigest.update(inputByteArray);
        byte[] resultByteArray = messageDigest.digest();
        return byteArrayToHex(resultByteArray);
    }
    
    protected String strToHash(String str) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] inputByteArray = str.getBytes();
        messageDigest.update(inputByteArray);
        byte[] resultByteArray = messageDigest.digest();
        return byteArrayToHex(resultByteArray);
    }

    public String byteArrayToHex(byte[] byteArray) {
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] resultCharArray = new char[byteArray.length * 2];
        int index = 0;
        for (byte b : byteArray) {
            resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
            resultCharArray[index++] = hexDigits[b & 0xf];
        }
        return new String(resultCharArray);
    }
    
    /**
     * 取得随机数
     * @return
     */
    public int getRandom() {
    	return random.nextInt(999999)%900000+100000;
    }
    
    /**
     * 取得并设置接口连接
     * @param url
     * @return
     * @throws Exception
     */
    public HttpURLConnection getPostHttpConn(String url) throws Exception {
        URL object = new URL(url);
        HttpURLConnection conn;
        conn = (HttpURLConnection) object.openConnection();
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestMethod("POST");
        return conn;
	}
    
    public String calculateSig(
    		String appkey,
    		long random,
    		String msg,
    		long curTime,    		
    		ArrayList<String> phoneNumbers) throws NoSuchAlgorithmException {
        String phoneNumbersString = phoneNumbers.get(0);
        for (int i = 1; i < phoneNumbers.size(); i++) {
            phoneNumbersString += "," + phoneNumbers.get(i);
        }
        return strToHash(String.format(
        		"appkey=%s&random=%d&time=%d&mobile=%s",
        		appkey, random, curTime, phoneNumbersString));
    }
    
    public String calculateSigForTempl(
    		String appkey,
    		long random,
    		long curTime,    		
    		ArrayList<String> phoneNumbers) throws NoSuchAlgorithmException {
        String phoneNumbersString = phoneNumbers.get(0);
        for (int i = 1; i < phoneNumbers.size(); i++) {
            phoneNumbersString += "," + phoneNumbers.get(i);
        }
        return strToHash(String.format(
        		"appkey=%s&random=%d&time=%d&mobile=%s",
        		appkey, random, curTime, phoneNumbersString));
    }
    /**
     * 生成加密签名
     * @param appkey
     * @param random
     * @param curTime
     * @param phoneNumber
     * @return
     * @throws NoSuchAlgorithmException
     */
    public String calculateSigForTempl(
    		String appkey,
    		long random,
    		long curTime,    		
    		String phoneNumber) throws NoSuchAlgorithmException {
    	ArrayList<String> phoneNumbers = new ArrayList<>();
    	phoneNumbers.add(phoneNumber);
    	return calculateSigForTempl(appkey, random, curTime, phoneNumbers);
    }
    
    public JSONArray phoneNumbersToJSONArray(String nationCode, ArrayList<String> phoneNumbers) {
        JSONArray tel = new JSONArray();
        int i = 0;
        do {
            JSONObject telElement = new JSONObject();
            telElement.put("nationcode", nationCode);
            telElement.put("mobile", phoneNumbers.get(i));
            tel.add(telElement);
        } while (++i < phoneNumbers.size());

        return tel;
    }
    /**
     * 转化为JSONArray
     * @param params
     * @return
     */
    public JSONArray smsParamsToJSONArray(ArrayList<String> params) {
        JSONArray smsParams = new JSONArray();        
        for (int i = 0; i < params.size(); i++) {
        	smsParams.add(params.get(i));	
		}
        return smsParams;
    }
    
    public SmsSingleSenderResult jsonToSmsSingleSenderResult(JSONObject json) {
    	SmsSingleSenderResult result = new SmsSingleSenderResult();
    	
    	result.result = json.getIntValue("result");
    	result.errMsg = json.getString("errmsg");
    	if (0 == result.result) {
	    	result.ext = json.getString("ext");
	    	result.sid = json.getString("sid");
	    	result.fee = json.getIntValue("fee");
    	}
    	return result;
    }
}
