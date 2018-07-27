package com.yunpay.pfbank.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.http.message.BasicNameValuePair;

public class SignUtils {

    public static String signData(List<BasicNameValuePair> nvps) throws Exception {
        TreeMap<String, String> tempMap = new TreeMap<String, String>();
        for (BasicNameValuePair pair : nvps) {
            if (StringUtils.isNotBlank(pair.getValue())) {
                tempMap.put(pair.getName(), pair.getValue());
            }
        }
        StringBuffer buf = new StringBuffer();
        for (String key : tempMap.keySet()) {
            buf.append(key).append("=").append((String) tempMap.get(key)).append("&");
        }
        String signatureStr = buf.substring(0, buf.length() - 1);
        /*KeyInfo keyInfo = RSAUtil.getPFXPrivateKey(ConfigUtils.getProperty("private_key_pfx_path"),
                                                   ConfigUtils.getProperty("private_key_pwd"));
        String signData = RSAUtil.signByPrivate(signatureStr, keyInfo.getPrivateKey(), "UTF-8");*/
        String signData = RSAUtil.signByPrivate(signatureStr, RSAUtil.readFile(ConfigUtils.AGENT_PRIVATE_KEY_PATH, "UTF-8"), "UTF-8");
//        if(tempMap.get("agentId")==null){
//            signData = RSAUtil.signByPrivate(signatureStr, RSAUtil.readFile(ConfigUtils.getProperty("private_key_path"), "UTF-8"), "UTF-8");
//        }else{
//            signData = RSAUtil.signByPrivate(signatureStr, RSAUtil.readFile(ConfigUtils.getProperty("agent_private_key_path"), "UTF-8"), "UTF-8");
//        }
        System.out.println("请求数据：" +signatureStr+ "&signature=" + signData );
        return signData;
    }

    public static boolean verferSignData(String str) {
        System.out.println("响应数据：" + str);
        String data[] = str.split("&");
        StringBuffer buf = new StringBuffer();
        String signature = "";
        for (int i = 0; i < data.length; i++) {
            String tmp[] = data[i].split("=", 2);
            if ("signature".equals(tmp[0])) {
                signature = tmp[1];
            } else {
                buf.append(tmp[0]).append("=").append(tmp[1]).append("&");
            }
        }
        String signatureStr = buf.substring(0, buf.length() - 1);
        System.out.println("验签数据：" + signatureStr);
        
        return RSAUtil.verifyByKeyPath(signatureStr, signature, ConfigUtils.AGENT_PUBLIC_KEY_PATH, "UTF-8");
    }
    
    /**
     * @Description: 将&连接的参数结果转为map
     * @author:   Zeng Dongcheng
     * @Date:     2017年10月9日下午4:03:10 
     * @param str
     * @return
     */
    public static Map<String,String> getMapFromStr(String str){
    	Map<String,String> map = new HashMap<String,String>();
    	String data[] = str.split("&");
    	for(String column : data){
    		String tmp[] = column.split("=",2);
    		map.put(tmp[0], tmp[1]);
    	}
    	return map;
    }
    
}
