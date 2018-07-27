package com.yunpay.permission.utils.phonecore;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * 类名称                      腾讯短信平台接口
 * 文件名称:     SmsPhone.java
 * 内容摘要: 	        用于发送短信验证码
 * @author:   Zhao Xiaoman
 * @version:  1.0  
 * @Date:     2017年12月4日上午9:43:33
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年12月4日   Zhao Xiaoman       1.0       新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class SmsPhone
{
	int appid = 1400043655;
	String appkey = "6935cf2a60482a436d1f231d9aec7591";
    String url = "https://yun.tim.qq.com/v5/tlssmssvr/sendsms";
    String nationCode = "86";
    String sign = "";
    String extend = "";
    String ext = "";
    Integer templId;
    ArrayList<String> params = new ArrayList<>();
    String overtime = "1.5";
    Integer flag = 0;
    
	SmsSenderUtil util = new SmsSenderUtil();

	public void SmsSingleSender(int appid, String appkey) throws Exception {
		this.appid = appid;
		this.appkey = appkey;
	}
	
	/**
	 * 指定模板单发
	 * @param nationCode 国家码，如 86 为中国
	 * @param phoneNumber 不带国家码的手机号
	 * @param templId 信息内容
	 * @param params 模板参数列表，如模板 {1}...{2}...{3}，那么需要带三个参数
	 * @param sign 签名，如果填空，系统会使用默认签名
	 * @param extend 扩展码，可填空
	 * @param ext 服务端原样返回的参数，可填空
	 * @return {@link}SmsSingleSenderResult
	 * @throws Exception
	 */
	public Integer sendWithParam(String phoneNumber,int type,String phoneCode) throws Exception {
/*
请求包体
{
    "tel": {
        "nationcode": "86", 
        "mobile": "13788888888"
    }, 
    "sign": "腾讯云", 
    "tpl_id": 19, 
    "params": [
        "验证码", 
        "1234", 
        "4"
    ], 
    "sig": "fdba654e05bc0d15796713a1a1a2318c",
    "time": 1479888540,
    "extend": "", 
    "ext": ""
}
应答包体
{
    "result": 0,
    "errmsg": "OK", 
    "ext": "", 
    "sid": "xxxxxxx", 
    "fee": 1
}
*/
		if (type == 1)
		{
			//修改密码短信验证ID
			templId = 44807;
		} else
		{
			//注册短信验证ID
			templId = 44806;
		}
		params.add(phoneCode);
		params.add(overtime);
		
		long random = util.getRandom();
		long curTime = System.currentTimeMillis()/1000;

		JSONObject data = new JSONObject();

        JSONObject tel = new JSONObject();
        tel.put("nationcode", nationCode);
        tel.put("mobile", phoneNumber);

        data.put("tel", tel);
        data.put("sig", util.calculateSigForTempl(appkey, random, curTime, phoneNumber));
        data.put("tpl_id", templId);
        data.put("params", util.smsParamsToJSONArray(params));
        data.put("sign", sign);
        data.put("time", curTime);
        data.put("extend", extend);
        data.put("ext", ext);
        //调用腾讯短信平台接口
		String wholeUrl = String.format("%s?sdkappid=%d&random=%d", url, appid, random);
        HttpURLConnection conn = util.getPostHttpConn(wholeUrl);

        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream(), "utf-8");
        wr.write(data.toString());
        wr.flush();

        // 显示 POST 请求返回的内容
        StringBuilder sb = new StringBuilder();
        int httpRspCode = conn.getResponseCode();
        SmsSingleSenderResult result;
        if (httpRspCode == HttpURLConnection.HTTP_OK) {
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            result = JSON.parseObject(sb.toString(), SmsSingleSenderResult.class);
            /*JSONObject json = new JSONObject(sb.toString());
            result = util.jsonToSmsSingleSenderResult(json);*/
        } else {
        	result = new SmsSingleSenderResult();
        	result.result = httpRspCode;
        	result.errMsg = "http error " + httpRspCode + " " + conn.getResponseMessage();
        }
        if (result.getResult() == 0)
		{
        	flag = 1;
        	System.out.println("短信发送：成功");
        	System.out.println(result.toString());
		} else if(result.result == 1025)
		{
			flag = 2;
			System.out.println("短信发送：失败");
        	System.out.println(result.toString());
		}else{
			flag = 0;
			System.out.println("短信发送：失败");
        	System.out.println(result.toString());
		}
        return flag;
	}
}
