<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../../common/taglib/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width,initial-scale=1.0"/>
	<meta name="format-detection" content="telephone=no,email=no,date=no,address=no">
	<title>店员管理</title>
	<link rel="stylesheet" type="text/css" href="${baseURL}/common/css/new/aui.css" />
	<link rel="stylesheet" type="text/css" href="${baseURL}/common/css/new/style.css?v=<%=System.currentTimeMillis() %>" />
	<link rel="stylesheet" href="${baseURL}/common/css/mescroll.css">
    <link rel="stylesheet" href="${baseURL}/common/css/mescroll-option.css"> 
	<script src="${baseURL}/common/js/plugin/jquery-2.1.4.min.js"></script>
    <script src="${baseURL}/common/js/plugin/mescroll.js" type="text/javascript" charset="utf-8"></script>
	<script src="${baseURL}/common/js/plugin/mescroll-option.js?v=<%=System.currentTimeMillis() %>" type="text/javascript" charset="utf-8"></script>
    <script src="${baseURL}/static/js/biz-js/clerk.js?v=<%=System.currentTimeMillis() %>"></script>
    <script src="${baseURL}/common/js/new/api.js"></script>
    <script src="${baseURL}/common/js/common.js?v=<%=System.currentTimeMillis()%> "></script>
    <script src="${baseURL}/common/js/plugin/jsFunction.js?v=<%=System.currentTimeMillis() %>"></script>
    <script src="${baseURL}/common/js/plugin/jsBridge.js?v=<%=System.currentTimeMillis() %>"></script>
</head>
<body>
	<div class="aui-bar aui-bar-nav" style="position: fixed;">
    	<a class="aui-pull-left aui-btn" onclick="location.href='${baseURL}/sys/store/list?pageIndex=0&type=0&merchant=${merchant}'">
        	<span class="aui-iconfont aui-icon-left"></span>商铺管理
    	</a>
    	<div class="aui-title">店员管理</div>
	</div>
	<div data-role="content" style="margin-top:-35px;margin-bottom: -35px;">
  	<div id="mescroll" class="mescroll" >
	<div id="content">  
    <div class="container-fluid" data-role="page" id="clerkdiv" style="background-color: #FFFFFF;" >
    <input type="hidden" id="pageindex" value="${pageIndex}">
    <input type="hidden" id="storeNo" value="${storeNo}">
    <input type="hidden" id="total" value="${total}">
    <input type="hidden" id="merchant" value="${merchant}">
    <input type="hidden" id="count" value="${fn:length(clerkList)}">
    <input type="hidden" id="duplicateLogin" autocomplete="off" value="${duplicateLogin}">
	<div class="aui-content aui-margin-b-15">
		<ul class="aui-list aui-list-in si-aui-list">
			<li class="aui-list-item">
            	<div class="aui-list-item-inner list-BigTitle">
                	<div class="aui-list-item-title bd-ShopName">${storeName}</div>
                	<div class="aui-list-item-right cm-AddClerk" onclick="window.location.href='${baseURL}/sys/store/goclerkadd?storeNo=${storeNo}&merchant=${merchant}'">
                	<img src="${baseURL}/common/images/new/ic_add_01.png" alt="">添加店员
                	</div>
            	</div>
        	</li>
        	<c:if test="${clerkList.size() < 1}">
    			<li class="" >
					<div class="" style="text-align: center;height: 30px;margin-top: 10px;">
						尚未添加店员...
					</div>
				</li>
   			</c:if>
        	<c:forEach var="item" items="${requestScope.clerkList}" varStatus="i">
    		<li class="aui-list-item clerk-list" >
        		<div class="aui-list-item-inner">
            		<div class="aui-list-item-title cm-list-item-title" onclick="window.location.href='${baseURL }/sys/store/goclerkedit?id=${item.id}&merchant=${merchant}'">
            			<div><c:if test="${item.userType==2}">店长:</c:if> <c:if test="${item.userType==3}">店员:</c:if> ${item.userName}</div>
            			<!-- <div>张三</div> -->
            			<div style="margin-left: -35px;">账号：${item.loginName}</div>
            		</div>
            		<div class="aui-list-item-right" onclick="$('#alertDiv1').show();$('#msgset').val('in'+${i.count});">
            			<input type="hidden" value="${item.id}" id="in${i.count}">
            			<img src="${baseURL}/common/images/new/ic_delete01.png" alt="删除店员" >
            		</div>
        		</div>
    		</li>
    		</c:forEach>
		</ul>
	</div>
	</div>
	</div>
	</div>
	
	<div class="pop-success" id="alertDiv1">
		<div class="pop-success-content">
			<input type="hidden" id="msgset" value="">
			<div class="pop-content" style="padding-top:50px;">
				<div class="">确定要删除该店员？</div>
			</div>
			<div class="pop-footer">
				<button id="KeepOn" onclick=" deleteObj('clerkdelete',$('#msgset').val())">确定删除</button>
				<button id="Return-index" onclick="$('#alertDiv1').hide();">返回店员</button>
			</div>
		</div>
	</div>
	<div class="pop-success" id="alertDiv2">
		<div class="pop-success-content">
			<div class="pop-content" style="height:120px;padding-top:20px;" id="deletemsg">
				<div><img src="${baseURL}/common/images/new/ic_success_01.png" alt=""></div>
				<div class="">删除成功！</div>
			</div>
			<div class="pop-one-btn">
                <button class="aui-btn aui-btn-success aui-btn-block" id="edit-finish" 
                onclick="window.location.href='${baseURL}/sys/store/clerklist?type=0&merchant=${merchant}&pageIndex='+$('#pageindex').val()+'&storeNo='+$('#storeNo').val()">
                	返回店员
                </button>
            </div>
		</div>
	</div>
	</div>
	<div class="pop-ajax" id="timeover">
		<div class="pop-ajax-content" >
			<img src='${baseURL}/common/css/images/ajax-loader.gif' alt="正在加载...">
		</div> 
	</div>	
</body>
</html>