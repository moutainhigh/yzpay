<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../../common/taglib/taglib.jsp"%> 
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width,initial-scale=1.0"/>
	<meta name="format-detection" content="telephone=no,email=no,date=no,address=no">
	<title>查看会员</title>
	<link rel="stylesheet" type="text/css" href="${baseURL}/common/css/new/aui.css" />
	<link rel="stylesheet" type="text/css" href="${baseURL}/common/css/new/style.css?v=<%=System.currentTimeMillis() %>" />
	<script src="${baseURL}/common/js/plugin/jquery-2.1.4.min.js"></script> 
	<script src="${baseURL}/static/js/biz-js/usercard.js?v=<%=System.currentTimeMillis() %>"></script>
	<script src="${baseURL}/common/js/new/api.js"></script>
	<script src="${baseURL}/common/js/common.js?v=<%=System.currentTimeMillis()%> "></script>
	<script src="${baseURL}/common/js/plugin/jsFunction.js?v=<%=System.currentTimeMillis() %>"></script>
	<script src="${baseURL}/common/js/plugin/jsBridge.js?v=<%=System.currentTimeMillis() %>"></script>
</head>
<body>
	<header class="aui-bar aui-bar-nav">
    	<a class="aui-pull-left aui-btn" onclick="location.href='${baseURL}/sys/membercard/userlist?page=0&type=0&merchant=${merchant}'">
        	<span class="aui-iconfont aui-icon-left"></span>会员管理
    	</a>
    	<div class="aui-title">查看会员</div>
	</header>
	<input type="hidden" id="merchant" value="${merchant}">
	<input type="hidden" id="duplicateLogin" autocomplete="off" value="${duplicateLogin}">
	<div class="Vip-Statistics">
		<div class="Vip-Statistics-item">
			<div>
				<p class="title">余额</p>
				<c:if test="${userCard.balance == null}">0</c:if>
				<c:if test="${userCard.balance != null}">
					<p class="text"><fmt:formatNumber maxFractionDigits="2" value="${userCard.balance}"/></p>
				</c:if>
			</div>
		</div>
		<div class="Vip-Statistics-item">
			<div>
				<p class="title">积分</p>
				<c:if test="${userCard.bonus == null}">0</c:if>
				<c:if test="${userCard.bonus != null}">
					<p class="text">${userCard.bonus}</p>
				</c:if>
			</div>
		</div>
		<div class="Vip-Statistics-item">
			<div>
				<p class="title">累计充值</p>
				<c:if test="${userOther.in == null}">0</c:if>
				<c:if test="${userOther.in != null}">
					<p class="text"><fmt:formatNumber maxFractionDigits="2" value="${userOther.in}"/></p>
				</c:if>
			</div>
		</div>
		<div class="Vip-Statistics-item">
			<div>
				<p class="title">累计消费</p>
				<c:if test="${userOther.out == null}">0</c:if>
				<c:if test="${userOther.out != null}">
					<p class="text"><fmt:formatNumber maxFractionDigits="2" value="${userOther.out}"/></p>
				</c:if>
			</div>
		</div>
	</div>
	<div class="aui-content aui-margin-b-15">
		<ul class="aui-list aui-list-in si-aui-list">
			<li class="aui-list-item">
        		<div class="aui-list-item-inner">
            		<div class="aui-list-item-title">姓名</div>
            		<div class="aui-list-item-right">
            			<span>${userCard.name}</span>
            		</div>
        		</div>
    		</li>
			<li class="aui-list-item" >
        		<div class="aui-list-item-inner">
            		<div class="aui-list-item-title">卡号</div>
            		<div class="aui-list-item-right">
            			<c:if test="${userCard.userCardCode != null}">
							${userCard.userCardCode}
						</c:if>
            			<c:if test="${userCard.aliUserCardCode != null}">
							${userCard.aliUserCardCode}
						</c:if>
            		</div>
        		</div>
    		</li>
			<li class="aui-list-item" >
        		<div class="aui-list-item-inner">
            		<div class="aui-list-item-title">手机</div>
            		<div class="aui-list-item-right">
            			<span>${userCard.mobile}</span>
            		</div>
        		</div>
    		</li>
			<li class="aui-list-item">
        		<div class="aui-list-item-inner">
            		<div class="aui-list-item-title">性别</div>
            		<div class="aui-list-item-right">
						${userCard.sex}
            		</div>
        		</div>
    		</li>
			<li class="aui-list-item" >
        		<div class="aui-list-item-inner">
            		<div class="aui-list-item-title">生日</div>
            		<div class="aui-list-item-right">
            			<span>${userCard.birthday}</span>
            		</div>
        		</div>
    		</li>
			<li class="aui-list-item" >
        		<div class="aui-list-item-inner">
            		<div class="aui-list-item-title">激活时间</div>
            		<div class="aui-list-item-right">
            			<c:if test="${userCard.mobile == null}"><span>未激活</span></c:if>
            			<c:if test="${userCard.mobile != null}">
            				<span><fmt:formatDate value="${userCard.createdAt}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
            			</c:if>
            		</div>
        		</div>
    		</li>
		</ul>
	</div>
</body>
<script>
	session();
</script>
</html>