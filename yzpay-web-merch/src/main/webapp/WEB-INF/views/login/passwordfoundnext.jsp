<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<jsp:include page="../../../common/taglib/taglib.jsp" />
<head>
 	<title>重置密码</title> 
	<meta charset="UTF-8">
	<meta name="viewport" content="maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width,initial-scale=1.0"/>
	<meta name="format-detection" content="telephone=no,email=no,date=no,address=no">
	<link rel="stylesheet" type="text/css" href="${baseURL}/common/css/new/aui.css" />
	<link rel="stylesheet" type="text/css" href="${baseURL}/common/css/new/style.css?v=<%=System.currentTimeMillis() %>" />
	<script src="${baseURL}/common/js/plugin/jquery-2.1.4.min.js"></script> 
	<script src="${baseURL}/common/js/new/api.js"></script>
	<script src="${baseURL}/static/js/biz-js/passwordfoundnext.js?v=<%=System.currentTimeMillis()%>"></script>
	<script src="${baseURL}/static/js/biz-js/common.js?v=<%=System.currentTimeMillis()%>"></script>
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
    	<div class="aui-title">重置密码</div>
	</header>
	<input type="hidden" value="${baseURL}" id="basePath" />
	<input type="hidden" value="${phone}" id="phone" />
    <form  role="form"  id="passwordfoundnext" action="${baseURL}/passwordfoundnext" style="width: 100%">
    <div class="">
	<ul class="aui-list aui-form-list register-form">
        <li class="aui-list-item login-list-item">
            <div class="aui-list-item-inner">
                <div class="aui-list-item-label">
                	    新密码
                </div>
                <div class="aui-list-item-input">
                    <input type="password" autocomplete="off" id="loginPwd" name="loginPwd"  placeholder="6-15位数字+字母"  onblur="checkPwd(this)" onkeydown="removeInfo(this)" maxlength="15">
            		<img src="${baseURL}/common/images/new/password-off.png" alt="" class="password_sh" data-password="0" id="fir" onclick="pwdShow(this)">
                </div>
            </div>
        </li>
        <li class="aui-list-item login-list-item">
            <div class="aui-list-item-inner">
                <div class="aui-list-item-label">
                 	   确认密码
                </div>
                <div class="aui-list-item-input">
                    <input type="password" autocomplete="off" id="password" name="password"  placeholder="请再次输入密码"  onblur="checkPassword(this)" onkeydown="removeInfo(this)" maxlength="15">
                    <img src="${baseURL}/common/images/new/password-off.png" alt="" class="password_sh" data-password="0" id="sen" onclick="pwdShow(this)">
                </div>
            </div>
        </li>
        <li class="aui-list-item login-list-item">
        	<p id="message" style="margin-top: 10px;height: 15px;" ></p>
        	<button type="button" id="nextBtn" class="aui-btn aui-btn-success aui-btn-block"  style="border: none;margin-top: 10px;" onclick="nextTo('passwordfoundnext')">完成</button>
        </li>
	</ul>
	</div>
	</form>
	<div class="pop-success">
		<div class="pop-success-content">
			<div class="pop-content" style="height: 7rem;padding-top: 1rem;">
				<div><img src="${baseURL}/common/images/new/ic_success_01.png" alt=""></div>
				<div class="">修改成功！</div>
			</div>
			<div class="pop-one-btn" style="margin-top: -20px;">
                <button class="aui-btn aui-btn-success aui-btn-block" id="edit-finish" onclick="window.location.href='${baseURL}/'">返回</button>
            </div>
		</div>
	</div>
	<div class="pop-ajax" id="timeover">
		<div class="pop-ajax-content" >
			<%-- <img src='${baseURL}/common/css/images/ajax-loader.gif' alt="正在加载..."> --%>
		</div> 
	</div>
</body>
<script type="text/javascript">
</script>
</html>