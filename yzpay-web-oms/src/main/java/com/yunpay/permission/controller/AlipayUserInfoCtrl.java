package com.yunpay.permission.controller;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;

/**
 * 
 * 文件名称:
 * 内容摘要: 获取支付宝返回的用户公开信息
 * 参考资料：蚂蚁金服开发文档 https://docs.open.alipay.com/263/105808/
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
@Controller
@RequestMapping("sys/alipayUserInfo/callback")
public class AlipayUserInfoCtrl {               
	
	@SuppressWarnings("deprecation")    
	@RequestMapping("/userInfo")
	public String getUserInfo(HttpServletRequest req,HttpServletResponse rep){
		 //开发者的appid
		String app_id = "2017122101037891";
	    //支付宝返回的 当次授权的授权码
	    String auth_code = req.getParameter("auth_code");
	    // 开发者 应用私钥
	    String app_private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCo/DDgM8m4mWzrCD5QmcJClG0CIuFowwnaN0a+LZX6BlvJv1IBxatC4bTLbdLqMgDDGsUQzoPZO1h8HgSwddTZJP8lA6W9WW2toQqGeBuD0yoRKn96+7D+"
	    		+ "GTu3FJ1JkfHzGSIKfqEC5+lo3VSyu5YIlgbVxZpHC6NkDw8lNwbImT2aUf+742CVa3QvaR39TgC1RdHgZoqb8i22nwPXaHvq3A02UiggorqwNoPOWq1LhnTwif7JaNH6Z0gqWwU6xUwQaAnoNGun+bmTnamiQprBlS9KXXUJ3IpTGf6qHy9py/"
	    		+ "tx2N45P81NXoYGLKH7FkgrsHJfNvqt93Yeab8yU1NVAgMBAAECggEAevzhTawjepkfroeXYu4ldnO0SsO8LpUVDJ8Ce9ONviT2lQUL2IJXPRwsi4po4U53Q4iuOKc1SttocR0f6JY5zbihgfz/maoGq0Hz+R5dWDpN+aRml8mrbaRL09nLCAqSf4"
	    		+ "2hOuYipYzIUiwUQulILwOSAfAJcv/DcUWMjldYNpIwJzUXLRLz4K2MhhvA35SaY3orLWFXTxa3ODszHtesMc3dB1QgVX6cuzXZgdZF1QPeQd9WG/8vY7vDbPoyFw9uie0eImiweNv3N2lvyMT7jbzUI9rAMCB75wKeujGNlE/FYfVwDPP3RvvsL"
	    		+ "jdy6HK1nMnwDont3pe1xfCZsIhsAQKBgQDrpMjQ6pF04R8dmvU9WzckgwlRcVuIXfNxP2WS6q0anxPPWlshOj9nFPzlIrnpz4wJhtVnSP1yZWRl8pfJYuYDzzCkN3w4brm4ApRc9f+zS3YjKRT9dGtMoJi9389rG08xKXME/NDRB28F"
	    		+ "SigzaJ7FRIr6GNd2w03uiHAZgkeR1QKBgQC3lUPOQHuTNqhNaitMWr3qrR5Vx0k98YkDNbNXmj7/TmlpRc8IetvAnPX9j/0UXA5pG0BXKhaIlPDP0L+fEEA0GW/x1q6Wl7KrIJpP6dxeivRN8A/djdFE3tWX1x2xZ4XqdyQRv+JuOO4UZ3SL"
	    		+ "bR0mhu1pSZJs6RNo64vWm6v7gQKBgCbZqzYuhj7D4vJpmuc7w7KkXAPucY3hF4LjVebMBDAwa/6ywfWPeeaP4kYQ3J/81PS5IzP9J7QVnWDlhzrvsEaOgVGByFwxfTMmUbml4Tt5RIf1fP1wlUQz8hrT7ImXHg5X7Bu1ZuQfmqE/obMgG5UggN1M/TEacB3Ups692PjlAoGBAIej65NqpWNh0PxR42fo68WG35D1S7JO0b"
	    		+ "LjbHzfZIhyQQQdJhjX3aH01vPf/VebpLIYBnPgjwGfOkXxsIMwGJ/l44ZF3n053DMARcOhwMbB2Dv1kWNbMzG1iw9DS5cWLCXOnYHXys8xlQbSYZParN0YeDWAvIVnuIWe85U/M9UBAoGBAI3I7sPPR7dSx5dfL1gLuAT361H112XbYGuAmK8gCvlPuixYy1NprOMeKtF6V8anRdRyA4Q2wVsw2J8TTkNVNB812Da+TTkT"
	    		+ "JJwAQOoheYamaIUQN3T56V+7szVUoB+4tgJtUuXl/gE65dfKSZndoDpi345VEa9gMTmfkgIkNStM";
	    // 开发者 应用公钥 
	    String app_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqPww4DPJuJls6wg+UJnCQpRtAiLhaMMJ2jdGvi2V+gZbyb9SAcWrQuG0y23S6jIAwxrFEM6D2TtYfB4EsHXU2ST/JQOlvVltraEKhngbg9MqESp/evuw/hk7tx"
	    		+ "SdSZHx8xkiCn6hAufpaN1UsruWCJYG1cWaRwujZA8PJTcGyJk9mlH/"
	    		+ "u+NglWt0L2kd/U4AtUXR4GaKm/Ittp8D12h76twNNlIoIKK6sDaDzlqtS4Z08In+yWjR+mdIKlsFOsVMEGgJ6DRrp/m5k52pokKawZUvSl11CdyKUxn+qh8vacv7cdjeOT/NTV6GBiyh+xZIK7ByXzb6rfd2Hmm/MlNTVQIDAQAB";
	    // 支付宝公钥 
	    String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAs60ZKFURuCtDA0McpUV3dYHz7MsLB4tG2wFR9knnFeomx7J2CsuPcDE22+GnYP5zKaBiZ1PaXIN22eLJ4kp9QfEFQ2SQSIfzqBdLpL5Pl6LAfD8yuX6eqH9LUZLn"
	    		+ "b7r5KFy6WLTzXiaBFQym8COc3mz6aaAGZWIncm9KimPvUceaUwCaA81xiGslXBEHVYVJ+L2TnPnRHO4Y+TQdvkYXNS2mLklSCFi16qX2v1mzC5bX1e8L3ESGg+Sxf8IHWNRwNijWPrWDe40xfgVIeTAb4p27KT/0td/qEHI/ygot8XetpT9n+vzR1ejM7b1yN2gOgQ0H8ptt1LMK23zczKRFnwIDAQAB";
	    //通过app_auth_code换取app_auth_token、授权商户的userId以及授权商户AppId。
	    AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",app_id,app_private_key,"json","UTF-8",alipay_public_key,"RSA2"); 
	  	AlipaySystemOauthTokenRequest alipayRequest = new AlipaySystemOauthTokenRequest();
	  	alipayRequest.setCode(auth_code);
	  	alipayRequest.setGrantType("authorization_code");
	  	AlipayUserInfoShareResponse userInfo = null;
	  	try{
	  		// 调用 换取授权访问令牌接口  alipay.system.oauth.token，使用auth_code换取access_token及用户userId
	  		AlipaySystemOauthTokenResponse oauthTokenResponse = alipayClient.execute(alipayRequest);
	  		if(oauthTokenResponse.isSuccess()){
	  			String accessToken = oauthTokenResponse.getAccessToken();
	  		 	// 使用 access_token 调用会员授权信息查询接口  alipay.user.info.share
	  		 	AlipayUserInfoShareRequest userInfoRequest = new AlipayUserInfoShareRequest();
	  			AlipayUserInfoShareResponse userInfoResponse = alipayClient.execute(userInfoRequest, accessToken);
	  			if(userInfoResponse.isSuccess()){
	  				userInfo = userInfoResponse;
	  				req.getSession().setAttribute("userInfo", userInfo);
	  			}else{
	  				System.out.println("获取用户信息失败");
	  			}
	  		}else{
	  			System.out.println("授权失败,errorCode:"+oauthTokenResponse.getErrorCode());
	  			System.out.println("msg:"+oauthTokenResponse.getMsg());
	  		}
	  	}catch(AlipayApiException  e){
	  	  System.out.println("授权失败,errCode:"+e.getErrCode());
	  	  System.out.println("msg:"+e.getErrMsg());
	  	  e.printStackTrace();
	  	}
	  	return "modules/alipayUser/userInfo";
	  	
	}
}
