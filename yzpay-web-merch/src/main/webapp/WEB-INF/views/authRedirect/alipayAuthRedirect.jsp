<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="java.util.*"%>
<%@ page import="java.util.Map"%>
<%@ page import="com.alipay.api.*"%>
<%@ page import="com.alipay.api.request.*"%>
<%@ page import="com.alipay.api.response.*"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>支付宝页面跳转同步通知页面</title>
</head>
<body>
<%   
    //开发者的appid
	String app_id = "2017122101037891";
    //支付宝返回的 当次授权的授权码
    String auth_code = request.getParameter("auth_code");
    out.write(auth_code + "\n");
    
    // 开发者 应用私钥
    String app_private_key = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALJR8t0SjDQepj0TzQf+D72lEtuzMW+zBoFzWv8XrOWimFvopcBpoJZO6QKXzOeJ3dbUkh4FE28ymOcR8QkFCRkiuGIiPbxUccFX8v1Del4KCatdMv7JqSTYIZNg8f9zd5SXgWqVQRhejMZ6YCC0Dc3+OXx6ql0kM9O4Vn3FAhZfAgMBAAECgYAETfIc4Au+XlcI9mdmn/1lSIzR/NtepOWtTkmOCtZDnM8suMr3gBU+S51YUK3DkRJn0y3Lx7eWYZRLT6WP4C0+Ab0f4D9t5fdy7kpuk+tdxHC/WPn/DZhAAbejrRMw6r16cBmDsgcC6ewfrOvB723+Bb0XAKeYhhoZBOUeZKFmQQJBAOfEaVnjzUKPGgLAk/gPslaD5UAcJQMas+5GZimXAWI/bQAwl/CeVoMfjSR8l7sPbClIl0s7SrcocFS9rybeDpECQQDE9vJpUDN0s5SajyfcvZLP625zm4nnCISpL2vjEYb6gCLOBMzkR1aUtH10Zx5NeccsypXvQZJQI820zvhocy3vAkEA0+VhJIv/iBDpiQakwjEnra2dFYYl2La7NugqU2/6FedDMt86qwU4t11LX8aBusaY7w2tNV0aLGbOfMuHrZNr4QJBAI2vtyHa96jzpeqpIFvCY2H+Ui4HrWPs1MF/w3RMn3SDyIW7Hkj4qGfAjp61ry68c3LdKI479SyBFPEEEd3RPTECQHoZIQhrtZ2ZREJQu8RdExf2+JYxiwsMKNyhEVrVFNzC43Pc65RceavmGfFmMSVBNw8qa2UfN5VHpcdkv1ggw48=";
    // 开发者 应用公钥 
    String app_public_key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCyUfLdEow0HqY9E80H/g+9pRLbszFvswaBc1r/F6zlophb6KXAaaCWTukCl8znid3W1JIeBRNvMpjnEfEJBQkZIrhiIj28VHHBV/L9Q3peCgmrXTL+yakk2CGTYPH/c3eUl4FqlUEYXozGemAgtA3N/jl8eqpdJDPTuFZ9xQIWXwIDAQAB";
    // 支付宝公钥 
    String alipay_public_key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
    
    //通过app_auth_code换取app_auth_token、授权商户的userId以及授权商户AppId。
     AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipaydev.com/gateway.do",app_id,app_private_key,"json","UTF-8",alipay_public_key,"RSA2"); 
    
  	 AlipaySystemOauthTokenRequest alipayRequest = new AlipaySystemOauthTokenRequest();
  
  	alipayRequest.setCode(auth_code);
  	alipayRequest.setGrantType("authorization_code");
  	try{
  		// 第三方授权
  		AlipaySystemOauthTokenResponse oauthTokenResponse = alipayClient.execute(alipayRequest);
  		if(oauthTokenResponse.isSuccess()){
  			out.write("<br/>调用成功" + "\n");
  			out.write("accessToken:"+oauthTokenResponse.getAccessToken());
  			out.write("userId:"+oauthTokenResponse.getUserId());
  		}else{
  			out.write("<br/>调用失败");
  		}
  	}catch(AlipayApiException  e){
  	  out.write("<br/>调用失败" + "\n"+e);
  	  e.printStackTrace();
  	}
%>
</body>
</html>