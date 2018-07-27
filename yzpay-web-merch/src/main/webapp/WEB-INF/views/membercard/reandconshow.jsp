<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../../common/taglib/taglib.jsp"%> 
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width,initial-scale=1.0"/>
	<meta name="format-detection" content="telephone=no,email=no,date=no,address=no">
	<title>余额</title>
	<link rel="stylesheet" type="text/css" href="${baseURL}/common/css/new/aui.css" />
	<link rel="stylesheet" type="text/css" href="${baseURL}/common/css/new/style.css?v=<%=System.currentTimeMillis() %>" />
	<link rel="stylesheet" href="${baseURL}/common/css/mescroll.css">
    <link rel="stylesheet" href="${baseURL}/common/css/mescroll-option.css"> 
	<script src="${baseURL}/common/js/plugin/jquery-2.1.4.min.js"></script> 
    <script src="${baseURL}/common/js/plugin/mescroll.js" type="text/javascript" charset="utf-8"></script>
	<script src="${baseURL}/common/js/plugin/mescroll-option.js?v=<%=System.currentTimeMillis() %>" type="text/javascript" charset="utf-8"></script>
	<script src="${baseURL}/static/js/biz-js/usercard.js?v=<%=System.currentTimeMillis() %>"></script>
	<script src="${baseURL}/common/js/new/api.js"></script>
	<script src="${baseURL}/common/js/common.js?v=<%=System.currentTimeMillis()%> "></script>
</head>
<body>
	<div class="Vip-Statistics">
		<div class="Vip-Statistics-item">
			<div>
				<p class="title">累计充值</p>
				<c:if test="${userOther.in == null}">0元</c:if>
				<c:if test="${userOther.in != null}">
					<p class="text"><fmt:formatNumber maxFractionDigits="2" value="${userOther.in}"/>元</p>
				</c:if>
			</div>
		</div>
		<div class="Vip-Statistics-item">
			<div>
				<p class="title">累计赠送</p>
				<c:if test="${userOther.send == null}">0元</c:if>
				<c:if test="${userOther.send != null}">
					<p class="text">${userOther.send}元</p>
				</c:if>
			</div>
		</div>
		<div class="Vip-Statistics-item">
			<div>
				<p class="title">累计消费</p>
				<c:if test="${userOther.out == null}">0元</c:if>
				<c:if test="${userOther.out != null}">
					<p class="text"><fmt:formatNumber maxFractionDigits="2" value="${userOther.out}"/>元</p>
				</c:if>
			</div>
		</div>
		<div class="Vip-Statistics-item">
			<div>
				<p class="title">余额</p>
				<c:if test="${userCard.balance == null}">0元</c:if>
				<c:if test="${userCard.balance != null}">
					<p class="text"><fmt:formatNumber maxFractionDigits="2" value="${userCard.balance}"/>元</p>
				</c:if>
			</div>
		</div>
	</div>
	<div class="aui-content aui-margin-b-15">
		<ul class="aui-list aui-list-in si-aui-list">
			<c:if test="${reandconList.size() <1}">
				<li class="aui-list-item" >
        			<div class="aui-list-item-inner aui-list-item-arrow">
            			<div class="aui-list-item-title">您目前暂无充值和消费记录</div>
            			<div class="aui-list-item-right">
            				<span></span>
            			</div>
        			</div>
    			</li>
    		</c:if>
    		<c:if test="${reandconList.size() >0}">
			<c:forEach var="item" items="${requestScope.reandconList}" varStatus="i">
				<li class="aui-list-item">
        			<div class="aui-list-item-inner "  style="height: 3.5rem;">
            			<div class="aui-list-item-title" style="float: left;margin-top: -1.5rem;width: 10rem;">
            				<c:if test="${item.tranAmt != 0}">消费</c:if>
            				<c:if test="${item.tranAmt == 0}">
            					充<fmt:formatNumber maxFractionDigits="2" value="${item.rechargeAmt}"/>元，
            					送<fmt:formatNumber maxFractionDigits="2" value="${item.giveAmt}"/>元
            				</c:if>
            			</div>
            			<%-- <div class="aui-list-item-title" style="float: left;margin-left: -10rem;margin-top: 1.5rem;">
            				<fmt:formatDate value="${item.createdAt}" pattern="yyyy-MM-dd HH:mm:ss"/>
            			</div> --%>
            			<p style="float: left;margin-left: -15rem;margin-top: 1.5rem;"><fmt:formatDate value="${item.createdAt}" pattern="yyyy-MM-dd HH:mm:ss"/></p>
            			<div class="aui-list-item-right" style="width: 4rem;">
            				<c:if test="${item.tranAmt != 0}"><span>- ${item.tranAmt}元 </span> </c:if>
            				<c:if test="${item.tranAmt == 0}"><span>+ <fmt:formatNumber maxFractionDigits="2" value="${item.rechargeAmt+item.giveAmt}"/>元</span></c:if>
            			</div>
        			</div>
    			</li>
    		</c:forEach>
    		</c:if>
		</ul>
	</div>
</body>
</html>