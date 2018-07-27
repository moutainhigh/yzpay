<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../../common/taglib/taglib.jsp"%> 
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width,initial-scale=1.0"/>
	<meta name="format-detection" content="telephone=no,email=no,date=no,address=no">
	<title>会员列表</title>
	<link rel="stylesheet" type="text/css" href="${baseURL}/common/css/new/aui.css" />
	<link rel="stylesheet" type="text/css" href="${baseURL}/common/css/new/style.css?v=<%=System.currentTimeMillis() %>" />
	<link rel="stylesheet" href="${baseURL}/common/css/mescroll.css">
    <link rel="stylesheet" href="${baseURL}/common/css/mescroll-option.css">
	<script src="${baseURL}/common/js/plugin/jquery-2.1.4.min.js?v=<%=System.currentTimeMillis() %>"></script>
    <script src="${baseURL}/common/js/plugin/mescroll.js" type="text/javascript" charset="utf-8"></script>
	<script src="${baseURL}/common/js/plugin/mescroll-option.js?v=<%=System.currentTimeMillis() %>" type="text/javascript" charset="utf-8"></script>
	<script src="${baseURL}/static/js/biz-js/usercard.js?v=<%=System.currentTimeMillis() %>"></script>
	<script src="${baseURL}/common/js/new/api.js"></script>
	<script src="${baseURL}/common/js/plugin/jsBridge.js?v=<%=System.currentTimeMillis() %>"></script>
    <script src="${baseURL}/common/js/plugin/jsFunction.js?v=<%=System.currentTimeMillis() %>"></script>
    <script src="${baseURL}/common/js/common.js?v=<%=System.currentTimeMillis()%> "></script>
    <script src="${baseURL}/common/js/plugin/jsFunction.js?v=<%=System.currentTimeMillis() %>"></script>
    <style type="text/css">
	.r{
		margin-top: -2rem;
	}		
    </style>
</head>
<body>
	<div class="aui-bar aui-bar-nav" style="position: fixed;">
    	<a class="aui-pull-left aui-btn" 
    	<c:if test="${merchUser!=null}"> onclick="location.href='${baseURL }/sys/membercard/show'"</c:if>
    	<c:if test="${merchUser==null}"> onclick="exitWebview()" </c:if>>
    	<span class="aui-iconfont aui-icon-left"></span>
    	<c:if test="${merchUser!=null}">会员管理</c:if>
    	<c:if test="${merchUser==null}">返回 </c:if>
    	</a>
    	<div class="aui-title">会员列表</div>
	</div>
	
	<!-- 滑动区域 -->
<div id="mescroll" class="mescroll" style="width: 100%;height: 90%;">
	<div id="content">
	<div class="Vip-Statistics aui-bar" style="width: 100%;">
		<div class="Vip-Statistics-item">
			<div>
				<p class="title">会员总数</p>
				<c:if test="${userCardNum.totalNum == null}">0</c:if>
				<c:if test="${userCardNum.totalNum != null}">
					<p class="text">${userCardNum.totalNum}</p>
				</c:if>
			</div>
		</div>
		<div class="Vip-Statistics-item">
			<div>
				<p class="title">微信数量</p>
				<c:if test="${userCardNum.wxNum == null}">0</c:if>
				<c:if test="${userCardNum.wxNum != null}">
					<p class="text">${userCardNum.wxNum}</p>
				</c:if>
			</div>
		</div>
		<div class="Vip-Statistics-item">
			<div>
				<p class="title">支付宝数量</p>
				<c:if test="${userCardNum.aliNum == null}">0</c:if>
				<c:if test="${userCardNum.aliNum != null}">
					<p class="text">${userCardNum.aliNum}</p>
				</c:if>
			</div>
		</div>
	</div>  
	<input type="hidden" autocomplete="off" id="page" value="${page}">
    <input type="hidden" autocomplete="off" id="merchant" value="${merchant}">
    <input type="hidden" autocomplete="off" id="count" value="${fn:length(userCardList)}">
    <input type="hidden" id="duplicateLogin" autocomplete="off" value="${duplicateLogin}">
	<div class="aui-content aui-margin-b-15">
		<ul class="aui-list aui-list-in si-aui-list" id="user_ul">
		<c:if test="${requestScope.userCardList == null}">
				<li class="aui-list-item" >
        			<div class="aui-list-item-inner aui-list-item-arrow">
            			<div class="aui-list-item-title">会员卡目前暂无领用信息</div>
            			<div class="aui-list-item-right">
            				<span></span>
            			</div>
        			</div>
    			</li>
    	</c:if>
		<c:if test="${requestScope.userCardList != null}">
			<c:forEach var="item" items="${requestScope.userCardList}" varStatus="i">
				<li class="aui-list-item" onclick="location.href='${baseURL }/sys/membercard/usershow?id=${item.id}&merchant=${merchant}'">
        			<div class="aui-list-item-inner aui-list-item-arrow" >
            			<div class="aui-list-item-title">
            				<c:if test="${item.name == null}">匿名</c:if>
            				<c:if test="${item.name != null}">${item.name}</c:if>
            			</div>
            			<div class="aui-list-item-right">
            				<c:if test="${item.mobile == null}"><span>未激活</span></c:if>
            				<c:if test="${item.mobile != null}"><span>${item.mobile}</span></c:if>
            			</div>
        			</div>
        			<div>${deleteFlag}</div>
    			</li>
    		</c:forEach>
    	</c:if>
		</ul>
	</div>
</div>
</div>
<div style="position: fixed;bottom: 0rem;z-index: 110;height: 1rem;width: 100%;"></div>
</body>
</html>