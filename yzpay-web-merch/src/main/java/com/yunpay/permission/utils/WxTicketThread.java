package com.yunpay.permission.utils;
import com.yunpay.h5merch.permission.entity.AccessToken;
import com.yunpay.h5merch.permission.entity.JsapiTicket;

/**
 * 
 * 文件名称:
 * 内容摘要: 定期获取并存储微信 access_token,JsapiTicket
 * @author:   duan zhang quan
 * @version:  1.0  
 * @Date:     2017年4月14日上午9:56:12 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年4月14日     duan zhang quan   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */

public class WxTicketThread implements Runnable{
	// 第三方用户唯一凭证
	public static String appid = "";
	// 第三方用户唯一凭证密钥
	public static String appsecret = "";  
	public static AccessToken accessToken = null;
	public static JsapiTicket jsapiTicket = null;

	@Override
	public void run() {   
        while(true){  
            try{  
                accessToken = WeixinUtil.getAccessToken(appid, appsecret);  
                if(null != accessToken){  
                    System.out.println("获取access_token成功,有效时长"+ accessToken.getExpiresIn()+"秒 token:"+ accessToken.getToken());  
                    try{
                    	jsapiTicket = WeixinUtil.getJsapiTicket(accessToken.getToken());
                        if(jsapiTicket!=null){
                        	System.out.println("获取jsapiTicket成功，有效时长"+ jsapiTicket.getExpiresIn()+"秒 ticket:"+ jsapiTicket.getTicket());  
                        }
                    }catch(Exception e){
                    	// 如果jsapiTicket为null，60秒后再获取  
                        Thread.sleep(60 * 1000);  
                    }
                    // 休眠7000秒  
                    Thread.sleep((accessToken.getExpiresIn() - 200) * 1000);  
                }else{  
                    // 如果access_token为null，60秒后再获取  
                    Thread.sleep(60 * 1000);  
                }  
            }catch(InterruptedException e){  
                try {  
                    Thread.sleep(60 * 1000);  
                } catch (InterruptedException e1) {  
                	  System.out.println(e1);  
                }  
                System.out.println(e);  
            }  
        }  
    }
	
}
