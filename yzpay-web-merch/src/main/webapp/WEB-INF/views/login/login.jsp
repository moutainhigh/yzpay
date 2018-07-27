<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../common/taglib/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>商家登录</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width,initial-scale=1.0"/>
	<meta name="format-detection" content="telephone=no,email=no,date=no,address=no">
	<link rel="stylesheet" type="text/css" href="${baseURL}/common/css/new/aui.css" />
	<link rel="stylesheet" type="text/css" href="${baseURL}/common/css/new/style.css?v=<%=System.currentTimeMillis() %>" />
	<script src="${baseURL}/common/js/plugin/jquery-2.1.4.min.js"></script> 
	<script src="${baseURL}/common/js/new/api.js"></script>
	<script src="${baseURL}/static/js/biz-js/login.js?v=<%=System.currentTimeMillis()%>"></script>
	<script src="${baseURL}/common/js/common.js?v=<%=System.currentTimeMillis()%> "></script>
	<style>
		body,html{
			background-color: #fff;
		}
	</style>
</head>
<body>
	<div class="login-img">
		<img src="${baseURL}/common/images/new/yc_logo1.png" alt="">
	</div>
	<div class="login-form">
	<input type="hidden" value="${baseURL}" id="basePath" />
    <form  id="login" name="login" role="form" action="${baseURL}/login" method="post">
	<ul class="aui-list aui-form-list">
		<li class="aui-list-item login-list-item">
            <div class="aui-list-item-inner">
                <div class="aui-list-item-label">
                 	   账户
                </div>
                <div class="aui-list-item-input">
                    <input type="tel" id="loginName" name="loginName" placeholder="注册时填写的手机号码" onblur="checkLoginName(this)" onkeydown="removeInfo(this);" maxlength="11">
                </div>
            </div>
        </li>
        <li class="aui-list-item login-list-item">
            <div class="aui-list-item-inner">
                <div class="aui-list-item-label">
                 	   密码
                </div>
                <div class="aui-list-item-input">
                	<input type="hidden" id="loginPwd" name="loginPwd" autocomplete="off" >
                    <input type="password" id="pwd" name="loginPwd" placeholder="6-15位数字+字母" onblur="checkLoginPwd(this)" onkeydown="removeInfo(this);" maxlength="15">
            		<img src="${baseURL}/common/images/new/password-off.png" alt="" class="password_sh" data-password="0" onclick="pwdShow(this)">
                </div>
            </div>
        </li>
        <li class="aui-list-item login-list-item">
            <div class="aui-list-item-inner">
                <div class="aui-list-item-label">
                   	 验证码
                </div>
                <div class="aui-list-item-input">
                    <input type="tel" id="userCaptchaCode" name="userCaptchaCode" placeholder="请输入验证码" onblur=" checkCode(this)" onkeydown="removeInfo(this)" maxlength="4" >
                    <img id="captcha_img" src="${baseURL}/rcCaptcha.jpg" alt="" class="login-yan-img">
                    <img id="reimg" alt="更换验证码" title="更换验证码" src="${baseURL}/common/images/new/login_ic_refresh.png" alt="" class="login-yan-refurbish" onclick="changeCode()">
                </div>
            </div>
        </li>
        <li class="aui-list-item login-list-item">
        	<p id="message" style="margin-top: 0px;height: 15px;" ></p>
        	<div style="margin-top: 10px;">
        		<input type="checkbox" class="aui-checkbox" id="autoLogin" checked="checked" name="autoLogin"><label for="autoLogin">自动登录</label>
        	</div>
        	<button  type="button" class="aui-btn aui-btn-success aui-btn-block" id="login-btn" onclick="loginTo('login')" style="border: none;margin-top: 15px;">登录</button>
        </li>
	</ul>
	<div class="login-exp">
		<span id="forget-password" onclick="$('#loginPwd').val('');$('#pwd').val('');window.location.href='${baseURL}/login/passwordfound'">忘记密码？</span>
		<span id="register" onclick="$('#loginPwd').val('');$('#pwd').val('');window.location.href='${baseURL}/merchantregister'">注册新账号</span><br>	
	</div>
	</form>
	</div>
	<div class="pop-ajax" id="timeover">
		<div class="pop-ajax-content" >
			<%-- <img src='${baseURL}/common/css/images/ajax-loader.gif' alt="正在加载..."> --%>
		</div> 
	</div>
	<input type="hidden" id="deleteFlag" autocomplete="off" value="${deleteFlag}"> 
	<div class="pop-success" id="mess">
		<div class="pop-success-content">
			<div class="pop-content" style="height: 7rem;padding-top: 2rem;">
				<div style="font-size:17px;width: 90%;margin: auto;">您的账号正在其他地方登录使用,若非本人操作，请及时更改密码！</div>
			</div>
			<div class="pop-one-btn" style="margin-top: -1rem;">
                <button type="button" class="aui-btn aui-btn-success aui-btn-block" id="edit-finish" onclick="$('.pop-success').hide();">返回</button>
            </div>
		</div>
	</div>
</body>
<script type="text/javascript">
	$(document).ready(function () {
		if ($("#deleteFlag").val() == "1") {
			$('#mess').show();
		}
	});
</script>
</html>

