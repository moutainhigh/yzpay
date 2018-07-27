<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>使用支付宝账号登录</title>
</head>
<body>
    <!-- 手机中要在支付宝客户端中登录   支付宝用户对商户授权，授权成功后,商户可以获取到支付宝用户的开放信息，包括用户ID、昵称、性别、省份、城市、用户头像、用户类型、用户状态、是否实名认证、是否是学生信息 --> 
	<%String merchantAppId = "2017122101037891";  // 支付宝商户的appid %>
	<%String callbackUrl =  "http://218.17.109.74:8082/yunpay/sys/alipayUserInfo/callback/userInfo";   // 授权回调地址  %>    
	<a href="https://openauth.alipay.com/oauth2/publicAppAuthorize.htm?app_id=<%=merchantAppId %>&scope=auth_user,auth_base&redirect_uri=<%=callbackUrl %>&state=init">使用支付宝账号登录</a>
</body>
</html>