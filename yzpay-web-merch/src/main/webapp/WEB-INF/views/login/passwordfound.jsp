<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
  <jsp:include page="../../../common/taglib/taglib.jsp" />
   <head>
 	<title>忘记密码</title> 
	<meta charset="UTF-8">
	<meta name="viewport" content="maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width,initial-scale=1.0"/>
	<meta name="format-detection" content="telephone=no,email=no,date=no,address=no">
	<link rel="stylesheet" type="text/css" href="${baseURL}/common/css/new/aui.css" />
	<link rel="stylesheet" type="text/css" href="${baseURL}/common/css/new/style.css?v=<%=System.currentTimeMillis() %>" />
	<script src="${baseURL}/common/js/plugin/jquery-2.1.4.min.js"></script> 
	<script src="${baseURL}/common/js/new/api.js"></script>
	<script src="${baseURL}/static/js/biz-js/passwordfound.js?v=<%=System.currentTimeMillis() %>"></script>
	<script src="${baseURL}/common/js/common.js?v=<%=System.currentTimeMillis()%> "></script>
	<style>
		body,html{
			background-color: #fff;
		}
	</style>
</head>
<body>
	<header class="aui-bar aui-bar-nav">
    	<a class="aui-pull-left aui-btn" onclick="location.href='${baseURL}/'">
        	<span class="aui-iconfont aui-icon-left"></span>登录
    	</a>
    	<div class="aui-title">忘记密码</div>
	</header>
	<div class="">
	<input type="hidden" value="${baseURL}" id="basePath" />
	<input type="hidden" value="${sendCodeTime}" id="sendCodeTime" />
    <form id="passwordfound"  role="form"  action="${baseURL}/passwordfound" method="post" style="width: 100%">
	<ul class="aui-list aui-form-list register-form">
		
		<li class="aui-list-item login-list-item">
            <div class="aui-list-item-inner">
                <div class="aui-list-item-label">
                   	 手机号
                </div>
                <div class="aui-list-item-input">
                    <input type="tel" id="phone" name="phone" placeholder="请输入手机号码" value="${phone}" onblur="checkPhone(this)" onkeydown="removeInfo(this)" maxlength="11">
                </div>
            </div>
        </li>
        <li class="aui-list-item login-list-item">
            <div class="aui-list-item-inner">
                <div class="aui-list-item-label">
              	      验证码
                </div>
                <div class="aui-list-item-input">
                    <input type="tel" id="phoneCode" name="phoneCode" placeholder="请输入手机验证码" onblur="check(this,2)" onkeydown="removeInfo(this)" maxlength="4">
            		<button type="button"  class="aui-btn aui-btn-info aui-btn-block aui-btn-outlined aui-btn-sm" id="obtain-ma" onclick="getPhone()" style="width: 95px;height: 40px;margin-top: -8px;">获取验证码</button>
                </div>
            </div>
        </li>
        <li class="aui-list-item login-list-item">
        	<p id="message" style="margin-top: 10px;height: 15px;" > </p>
        	<button type="button" id="foundBtn" class="aui-btn aui-btn-success aui-btn-block" id="register-btn" style="border: none;margin-top: 10px;" onclick="foundTo('passwordfound')">下一步</button>
        </li>
	</ul>
	</form>
	</div>
	<div class="pop-ajax" id="timeover">
		<div class="pop-ajax-content" >
			<%-- <img src='${baseURL}/common/css/images/ajax-loader.gif' alt="正在加载..."> --%>
		</div> 
	</div>
</body> 
</html>