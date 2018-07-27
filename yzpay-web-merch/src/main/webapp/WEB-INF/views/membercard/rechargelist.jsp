<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../../common/taglib/taglib.jsp"%>
<!DOCTYPE HTML>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width,initial-scale=1.0"/>
	<meta name="format-detection" content="telephone=no,email=no,date=no,address=no">
	<title>规则列表</title>
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
    	<a class="aui-pull-left aui-btn" onclick="location.href='${baseURL }/sys/membercard/show'">
        	<span class="aui-iconfont aui-icon-left"></span>会员管理
    	</a>
    	<div class="aui-title">规则列表</div>
    	<a class="aui-pull-right aui-btn" onclick="location.href='${baseURL }/sys/membercard/gosetRecharge'">
        	<span class="aui-iconfont aui-icon-plus"></span>
    	</a>
	</header>
	<div class="aui-content aui-margin-b-15">
		<ul class="aui-list aui-list-in si-aui-list">
			<c:if test="${rechargeRuleList.size() < 1}">
    			<li class="" >
					<div class="" style="text-align: center;color:#666666; height: 3rem;"><br>
						尚未添加储值规则...
					</div>
				</li>
   			</c:if>
        	<c:forEach var="item" items="${requestScope.rechargeRuleList}" varStatus="i">
				<li class="aui-list-item">
					<input type="hidden" value="${item.id}" id="in${i.count}">
        			<div class="aui-list-item-inner">
            			<div class="aui-list-item-title">充${item.charge}，送<fmt:formatNumber maxFractionDigits="0" value="${item.send}"/> </div>
            			<div class="aui-list-item-right">
            				<img class="rl-delete-list" src="${baseURL}/common/images/new/ic_delete02.png" alt="" onclick="$('#alertDiv1').show();$('#msgset').val('in'+${i.count});"> 
            			</div>
        			</div>
    			</li>
    		</c:forEach>
		</ul>
	</div>
		<div class="pop-success" id="alertDiv1">
		<div class="pop-success-content">
			<input type="hidden" id="msgset" value="">
			<div class="pop-content" style="padding-top:3rem;">
				<div class="">确定要删除该信息？</div>
			</div>
			<div class="pop-footer">
				<button id="KeepOn" onclick=" deleteObj('delRecharge',$('#msgset').val())">是</button>
				<button id="Return-index" onclick="$('#alertDiv1').hide();">否</button>
			</div>
		</div>
	</div>
	<div class="pop-success" id="alertDiv2">
		<div class="pop-success-content">
			<div class="pop-content" style="height:120px;padding-top:20px;" id="deletemsg">
				<div><img src="${baseURL}/common/images/new/ic_success_01.png" alt=""></div>
				<div class="">删除成功！</div>
			</div>
			<div class="pop-one-btn" style="margin-top: 0rem;">
                <button type="button" class="aui-btn aui-btn-success aui-btn-block" id="edit-finish" onclick="returnpage()">返回列表</button>
            </div>
		</div>
	</div>
</body>
</html>