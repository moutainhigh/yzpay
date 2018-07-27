<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../../common/taglib/taglib.jsp"%> 
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width,initial-scale=1.0"/>
	<meta name="format-detection" content="telephone=no,email=no,date=no,address=no">
	<title>商铺管理</title>
	<link rel="stylesheet" href="${baseURL}/common/css/new/aui.css" />
	<link rel="stylesheet" href="${baseURL}/common/css/new/style.css?v=<%=System.currentTimeMillis() %>" />
	<link rel="stylesheet" href="${baseURL}/common/css/mescroll.css">
    <link rel="stylesheet" href="${baseURL}/common/css/mescroll-option.css"> 
	<script src="${baseURL}/common/js/plugin/jquery-2.1.4.min.js"></script> 
	<script src="${baseURL}/common/js/common.js?v=<%=System.currentTimeMillis()%> "></script> 
    <script src="${baseURL}/common/js/plugin/mescroll.js" type="text/javascript" charset="utf-8"></script>
	<script src="${baseURL}/common/js/plugin/mescroll-option.js?v=<%=System.currentTimeMillis() %>" type="text/javascript" charset="utf-8"></script>
    <script src="${baseURL}/static/js/biz-js/store.js?v=<%=System.currentTimeMillis() %>"></script>
    <script src="${baseURL}/common/js/new/api.js"></script>
    <script src="${baseURL}/common/js/plugin/jsBridge.js?v=<%=System.currentTimeMillis() %>"></script>
    <script src="${baseURL}/common/js/plugin/jsFunction.js?v=<%=System.currentTimeMillis() %>"></script>
    <script src="${baseURL}/common/js/common.js?v=<%=System.currentTimeMillis()%> "></script>
</head>
<body >
	<div class="aui-bar aui-bar-nav" style="position: fixed;">
    	<a class="aui-pull-left aui-btn" 
    	<c:if test="${merchUser!=null}"> onclick="location.href='${baseURL }/sys/index/index'"</c:if>
    	<c:if test="${merchUser==null}"> onclick="exitWebview()" </c:if>>
    	<span class="aui-iconfont aui-icon-left"></span>
    	<c:if test="${merchUser!=null}">首页</c:if>
    	<c:if test="${merchUser==null}">我的 </c:if>
    	</a>
    	<div class="aui-title">商铺管理</div>
    	<button type="button" class="aui-btn aui-btn-success " id="add-clerk" style="float: right;font-size: 18px;" onclick="window.location.href='${baseURL}/sys/store/goadd?merchant=${merchant}'">+&nbsp;添加</button>
	</div>
	<!-- <div data-role="content" style="margin-top:-35px;margin-bottom: -35px;" id="con"> -->
	<div id="mescroll" class="mescroll" style="height: 90%;">
	<div>
	<div id="content">  
    <input type="hidden" id="pageindex" value="${pageIndex}">
    <input type="hidden" id="total" value="${total}">
    <input type="hidden" id="merchant" value="${merchant}">
    <input type="hidden" id="count" value="${fn:length(storeList)}">
    <input type="hidden" id="duplicateLogin" autocomplete="off" value="${duplicateLogin}">
    <c:if test="${storeList.size() < 1}">
    	<ul class="aui-list aui-list-in MyShop-list" >
			<li class="MyShop-item">
				<div class="MyShop-text" align="center" style="height: 60px; padding-top: 15px;">
					<div align="center" >尚未添加商铺...</div>
				</div>
			</li>
		</ul>
    </c:if>
	<div class="aui-content">
	<c:forEach var="item" items="${requestScope.storeList}" varStatus="i">
		<ul class="aui-list aui-list-in MyShop-list" >
			<li class="MyShop-item">
				<div class="MyShop-text" onclick="window.location.href='${baseURL }/sys/store/goedit?storeNo=${item.storeNo}&merchant=${merchant}'">
					<div class="MyShop-shopName">${item.storeName}</div>
					<div class="MyShop-shopInfo">
						<span>联系人：${item.contactMan}</span>
						<span>电话：${item.contactTel}</span>
					</div>
				</div>
				<div class="MyShop-footer">
					<input type="hidden" value="${item.storeNo}" id="in${i.count}">
					<div class="MyShop-footer-option" onclick="$('#alertDiv1').show();$('#msgset').val('in'+${i.count});">
						<img src="${baseURL}/common/images/new/ic_delete01.png" alt="">
						<span>删除</span>
					</div>
					<div class="MyShop-footer-option" onclick="window.location.href='${baseURL }/sys/store/clerklist?storeNo=${item.storeNo}&pageIndex=0&type=0&merchant=${merchant}'">
						<img src="${baseURL}/common/images/new/ic_set-up.png" alt="">
						<span>店员设置</span>
					</div>
				</div>
			</li>
		</ul>
	</c:forEach>
	</div>
	</div>
	</div>
	</div>
	<div style="position: fixed;bottom: 0rem;z-index: 110;height: 1rem;width: 100%;"></div>
	<div class="pop-success" id="alertDiv1">
		<div class="pop-success-content">
			<input type="hidden" id="msgset" value="">
			<div class="pop-content" style="padding-top:50px;">
				<div class="">确定要删除该商铺？</div>
			</div>
			<div class="pop-footer">
				<button id="KeepOn" onclick=" deleteObj('delete',$('#msgset').val())">确定删除</button>
				<button id="Return-index" onclick="$('#alertDiv1').hide();">返回商铺</button>
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
                <button type="button" class="aui-btn aui-btn-success aui-btn-block" id="edit-finish" onclick="window.location.href='${baseURL}/sys/store/list?type=0&merchant=${merchant}&pageIndex='+$('#pageindex').val()">返回商铺</button>
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