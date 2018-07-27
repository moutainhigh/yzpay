<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../../common/taglib/taglib.jsp"%>
<!DOCTYPE HTML>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width,initial-scale=1.0"/>
	<meta name="format-detection" content="telephone=no,email=no,date=no,address=no">
	<title>充值设置</title>
	<link rel="stylesheet" type="text/css" href="${baseURL}/common/css/new/aui.css" />
	<link rel="stylesheet" type="text/css" href="${baseURL}/common/css/new/style.css?v=<%=System.currentTimeMillis() %>" />
	<script src="${baseURL}/common/js/plugin/jquery-2.1.4.min.js?v=<%=System.currentTimeMillis() %>"></script> 
	<script src="${baseURL}/common/js/new/api.js"></script>
	<script src="${baseURL}/common/js/plugin/jsFunction.js?v=<%=System.currentTimeMillis() %>"></script>
	<script src="${baseURL}/static/js/biz-js/recharge.js?v=<%=System.currentTimeMillis() %>"></script>
	<script src="${baseURL}/common/js/common.js?v=<%=System.currentTimeMillis()%> "></script>
</head>
<body>
	<header class="aui-bar aui-bar-nav">
    	<a class="aui-pull-left aui-btn" onclick="location.href='${baseURL}/sys/membercard/goRechargeList'">
        	<span class="aui-iconfont aui-icon-left"></span>规则列表
    	</a>
    	<div class="aui-title">充值设置</div>
	</header>
	<div class="aui-content aui-margin-b-15">
	<form  class="form-horizontal"  role="form" id="recharge"  method="post"  action="${baseURL}/sys/membercard/setRecharge">
		<ul class="aui-list aui-form-list register-form">
			<li class="aui-list-item">
        		<div class="aui-list-item-inner">
            		<div class="aui-list-item-label">充值金额<i>*</i></div>
            		<div class="aui-list-item-right settingPay-item-right">
            			<input type="tel" id="charge"  name="charge"  placeholder="设置范围为1-99999" maxlength="5" onblur="check(this,1)" onkeydown="removeInfo(this)" ><label>元</label>
            		</div>
        		</div>
    		</li>
			<li class="aui-list-item">
        		<div class="aui-list-item-inner">
            		<div class="aui-list-item-label">赠送金额<i>*</i></div>
            		<div class="aui-list-item-right settingPay-item-right">
            			<input type="tel" id="send" name="send"  placeholder="设置范围为1-99999" maxlength="5" onblur="check(this,1)" onkeydown="removeInfo(this)" ><label>元</label>
            		</div>
        		</div>
    		</li>
    		<li></li>
		</ul>
		<p id="message" style="margin-top: 10px;height: 15px;color: #fd4142;margin-left: 12px;font-size:16px; " ></p>
		<div class="aui-content-padded" style="margin-top:20px;">
			<button type="button" class="aui-btn aui-btn-success aui-btn-block" id="setrecharge-btn" onclick="saveForm('recharge')">添加</button>
		</div>
		</form>
	</div>
	<div class="pop-success">
		<div class="pop-success-content">
			<div class="pop-content" style="padding-top:28px;">
				<div><img src="${baseURL}/common/images/new/ic_success_01.png" alt=""></div>
				<div class="">添加成功</div>
			</div>
			<div class="pop-footer">
				<button id="KeepOn" onclick="location.href='${baseURL}/sys/membercard/gosetRecharge'">继续添加</button>
				<button id="Return-index" onclick="location.href='${baseURL}/sys/membercard/goRechargeList'">返回</button>
			</div>
		</div>
	</div>
	<div class="pop-ajax" id="timeover">
		<div class="pop-ajax-content" >
			<%-- <img src='${baseURL}/common/css/images/ajax-loader.gif' alt="正在加载..."> --%>
		</div> 
	</div>
</body>
</html>