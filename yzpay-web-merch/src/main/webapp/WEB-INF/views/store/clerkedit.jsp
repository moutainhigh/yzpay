<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../common/taglib/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN"> 
<head>
	<title>修改店员</title>
	<link rel="stylesheet" type="text/css" href="${baseURL}/common/css/new/aui.css" />
	<link rel="stylesheet" type="text/css" href="${baseURL}/common/css/new/style.css?v=<%=System.currentTimeMillis() %>" />
	<script src="${baseURL}/common/js/plugin/jquery-2.1.4.min.js"></script> 
    <script src="${baseURL}/static/js/biz-js/clerk.js?v=<%=System.currentTimeMillis() %>"></script>
    <script src="${baseURL}/common/js/new/api.js"></script>
    <script src="${baseURL}/static/js/biz-js/common.js?v=<%=System.currentTimeMillis()%>"></script>
    <script src="${baseURL}/common/js/common.js?v=<%=System.currentTimeMillis()%> "></script>
    <script src="${baseURL}/common/js/plugin/jsFunction.js?v=<%=System.currentTimeMillis() %>"></script>
    <script src="${baseURL}/common/js/plugin/jsBridge.js?v=<%=System.currentTimeMillis() %>"></script>
</head>
<body>
	<header class="aui-bar aui-bar-nav">
    	<a class="aui-pull-left aui-btn" onclick="$('#loginPwd').val('');$('#pwd').val('');window.location.href='${baseURL }/sys/store/clerklist?pageIndex=0&type=0&storeNo=${merchUser.storeNo}&merchant=${merchant}'">
        	<span class="aui-iconfont aui-icon-left"></span>店员管理
    	</a>
    	<div class="aui-title">修改店员</div>
	</header>
	<input type="hidden" value="${baseURL}" id="basePath" />
	<input type="hidden" id="merchant" value="${merchant}">
	<input type="hidden" id="duplicateLogin" autocomplete="off" value="${duplicateLogin}">
    <form id="clerkedit" name="clerkedit" class="form-horizontal" role="form" action="${baseURL}/sys/store/clerkedit" method="post">
	<div class="aui-content aui-margin-b-15">
		<ul class="aui-list aui-form-list register-form">
			<li class="aui-list-item">
        		<div class="aui-list-item-inner ">
            		<div class="aui-list-item-label">店员职位<i>*</i></div>
            		<div class="aui-list-item-input">
            			<select name="userType" id="userType" style="width: 140px;margin-left: -2px;" >
            				<option value=2 <c:if test="${merchUser.userType=='2'}">selected</c:if>>店长</option>
    			  			<option value=3 <c:if test="${merchUser.userType=='3'}">selected</c:if>>店员</option>
            			</select>
            		</div>
        		</div>
    		</li>
    		<li class="aui-list-item">
        		<div class="aui-list-item-inner">
            		<div class="aui-list-item-label">店员姓名<i>*</i></div>
            		<div class="aui-list-item-input">
            			<input type="text" id="userName" name="userName" value="${merchUser.userName}" placeholder="中文4位或英文8位以内" onblur="check(this,1)" onkeydown="removeInfo(this)" maxlength="8">
            		</div>
        		</div>
    		</li>
    		<li class="aui-list-item">
        		<div class="aui-list-item-inner">
            		<div class="aui-list-item-label">店员账号<i>*</i></div>
            		<div class="aui-list-item-input">
            			<input type="hidden" id="phone1" value="${merchUser.loginName}">
            			<input type="tel"  id="loginPhone" name="loginPhone" placeholder="请输入店员手机号码" value="${merchUser.loginName}" onblur="checkPhone(this)" onkeydown="removeInfo(this)" maxlength="11" readonly="readonly">
            		</div>
        		</div>
    		</li>
    		<li class="aui-list-item">
        		<div class="aui-list-item-inner">
            		<div class="aui-list-item-label">登录密码<i>*</i></div>
            		<div class="aui-list-item-input">
            			<input type="hidden" id="loginPwd" name="loginPwd" autocomplete="off" >
            			<input type="password" autocomplete="off" id="pwd" name="loginPwd" placeholder="不修改密码则不填写" onblur="check(this,0)" onkeydown="removeInfo(this)" maxlength="15">
            			<img src="${baseURL}/common/images/new/password-off.png" alt="" class="password_sh" data-password="0" onclick="pwdShow(this)" >
            		</div>
        		</div>
    		</li>
		</ul>
		<input type="hidden" id="storeNo" name="storeNo" class="ipt input-lg" value="${merchUser.storeNo}" >
        <input type="hidden" id="id" name="id" class="ipt input-lg" value="${merchUser.id}">
		<p id="message" style="margin-top: 10px;height: 15px;color: #fd4142;margin-left: 12px;font-size:16px; " ></p>
		<div class="aui-content-padded" style="margin-top:20px;">
			<button type="button" class="aui-btn aui-btn-success aui-btn-block" id="clerkaddbtn" onclick="saveForm('clerkedit')">确认修改</button>
		</div>
	</div>
	</form>
	<div class="pop-success">
		<div class="pop-success-content">
			<div class="pop-content" style="height: 7rem;padding-top: 1rem;">
				<div><img src="${baseURL}/common/images/new/ic_success_01.png" alt=""></div>
				<div class="">修改成功！</div>
			</div>
			<div class="pop-one-btn" onclick="window.location.href='${baseURL }/sys/store/clerklist?pageIndex=0&merchant=${merchant}&type=0&storeNo='+$('#storeNo').val()" style="margin-top: -20px;">
                <button class="aui-btn aui-btn-success aui-btn-block" id="edit-finish">返回店员</button>
            </div>
		</div>
	</div>
	<div class="pop-ajax" id="timeover">
		<div class="pop-ajax-content" >
			<%-- <img src='${baseURL}/common/css/images/ajax-loader.gif' alt="正在加载..."> --%>
		</div> 
	</div>
</body>
<script>
	session();
</script>
</html>
