<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="../../../../common/taglib/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>支付宝用户信息</title>
</head>
<body>
  <img alt="" src="${userInfo.avatar}">
    <address>userId:${userInfo.userId}</address>
  	<address>昵称:${userInfo.nickName}</address>
  	<address>城市:${userInfo.city}</address>
  	<address>性别:
  		<c:if test="${userInfo.gender == 'm' }">男</c:if>
  		<c:if test="${userInfo.gender == 'f' }">女</c:if>
  	</address>
  	<address>用户类型：
  		<c:if test="${userInfo.userType == '1' }">公司账户</c:if>
  		<c:if test="${userInfo.userType == '2' }">个人账户</c:if>
  	</address>
  	<address>用户状态：
  		<c:if test="${userInfo.userStatus == 'q' }">快速注册用户</c:if>
  		<c:if test="${userInfo.userStatus == 't' }">已认证用户</c:if>
  		<c:if test="${userInfo.userStatus == 'b' }">被冻结</c:if>
  		<c:if test="${userInfo.userStatus == 'w' }">已注册，未激活</c:if>
  	</address>
  	<address>是否实名认证：
  		<c:if test="${userInfo.isCertified  == 't' }">已实名认证</c:if>
  		<c:if test="${userInfo.isCertified  == 'f' }">未 实名认证</c:if>
  	</address>
  	<address>是否是学生：
  		<c:if test="${userInfo.isStudentCertified  == 't' }">学生</c:if>
  		<c:if test="${userInfo.isStudentCertified  == 'f' }">不是学生</c:if>
  	</address>
  
</body>
</html>