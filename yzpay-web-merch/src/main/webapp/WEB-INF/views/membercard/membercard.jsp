<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../../common/taglib/taglib.jsp"%> 
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width,initial-scale=1.0"/>
	<meta name="format-detection" content="telephone=no,email=no,date=no,address=no">
	<title>会员管理</title>
	<link rel="stylesheet" type="text/css" href="${baseURL}/common/css/new/aui.css" />
	<link rel="stylesheet" type="text/css" href="${baseURL}/common/css/new/style.css?v=<%=System.currentTimeMillis() %>" />
	<script src="${baseURL}/common/js/plugin/jquery-2.1.4.min.js"></script> 
	<script src="${baseURL}/common/js/new/api.js"></script>
	<script src="${baseURL}/common/js/common.js?v=<%=System.currentTimeMillis()%> "></script>
	<%-- <script src="${baseURL}/static/js/biz-js/login.js?v=<%=System.currentTimeMillis() %>"></script>
	<script src="${baseURL}/static/js/biz-js/common.js?v=<%=System.currentTimeMillis()%>"></script> --%>
</head>
<body>
	<header class="aui-bar aui-bar-nav">
    	<a class="aui-pull-left aui-btn" onclick="location.href='${baseURL }/sys/index/index'">
        	<span class="aui-iconfont aui-icon-left"></span>首页
    	</a>
    	<div class="aui-title">会员管理</div>
	</header>

	<!-- 未办卡 -->
	<c:if test="${baseInfo == null}">
	<div class="NoCard-card-div">
		<div class="NoCard-card" 
			<c:if test="${integralRule == null}"> onclick="window.location.href='${baseURL}/sys/membercard/gosetIntegral'"</c:if> 
			<c:if test="${integralRule != null}"> onclick="window.location.href='${baseURL}/sys/membercard/goadd'" </c:if>>
			<div class="vipC-list">
				<img class="vipC-logo" src="${baseURL}/common/images/new/c-logo.png" alt="">
				<img class="vipC-add" src="${baseURL}/common/images/new/ic_add_02.png" alt="" >
			</div>
			<div class="vipC-list" style="padding-top:0.8rem;">
				<span class="vipC-num">****&nbsp;****&nbsp;****</span>
				<%-- <img class="vipC-tip" src="${baseURL}/common/images/new/tip-icon-w.png" alt=""> --%>
			</div>
		</div>
	</div>
	</c:if>
	<!-- 正常卡片 -->
	<c:if test="${baseInfo != null}">
	<input type="hidden" id="color" value="${baseInfo.color}">
 	<div class="NormalCard-card-div"  id="normal">
		<div class="NormalCard-card" onclick="location.href='${baseURL}/sys/membercard/goedit'" style="background-color: ${baseInfo.color};">
			<div class="vipC-list" >
				<img class="vipC-logo" 
				<c:if test="${baseInfo.logoLocalUrl == 'local'}">src="${baseURL}/common/images/cardlogo/logo.png"</c:if>  
				<c:if test="${baseInfo.logoLocalUrl != 'local'}">src="${baseURL}/sys/membercard/showImg"</c:if>  
				alt="logo">
				<div class="vipC-info">
					<div class="name">${baseInfo.brandName}</div>
					<div class="leve">${baseInfo.title}</div>
				</div>
			</div>
			<div class="vipC-list" style="padding-top:0.8rem;">
				<span class="vipC-num">8888&nbsp;8888&nbsp;8888</span>
				<%-- <img class="vipC-tip" src="${baseURL}/common/images/new/tip-icon-b.png" alt=""> --%>
			</div>
		</div>
	</div> 
	</c:if>
	<div class="card-type">
		<%-- <div class="card-type-div-f">
			<img src="${baseURL}/common/images/new/weChat-icon.png" alt=""><span>微信投放</span>
		</div>
		<div class="card-type-div-r">
			<img src="${baseURL}/common/images/new/alipay-icon.png" alt=""><span>支付宝投放</span>
		</div> --%>
	</div>
	<ul class="aui-list aui-list-in si-aui-list">
		<li class="aui-list-item"  onclick="location.href='${baseURL}/sys/membercard/gosetIntegral'">
    		<div class="aui-list-item-inner aui-list-item-arrow">
    			<div class="aui-list-item-title">
        		消费设置
        		 <c:if test="${integralRule == null}">
        		 	<div style="color: #888888; float: right;margin-right: -180%;">未设置</div>
        		 </c:if>
				</div>
    		</div>
		</li>
		<li class="aui-list-item" onclick="location.href='${baseURL}/sys/membercard/goRechargeList'">
    		<div class="aui-list-item-inner aui-list-item-arrow">
        		<div class="aui-list-item-title">
        		充值设置
        		 <c:if test="${rechargeRule == null}">
        		 	<div style="color: #888888; float: right;margin-right: -180%;">未设置</div>
        		 </c:if>
				</div>
    		</div>
		</li>
		<li class="aui-list-item"  onclick="location.href='${baseURL}/sys/membercard/userlist?page=0&type=0&merchant='">
    		<div class="aui-list-item-inner aui-list-item-arrow">
        		<div class="aui-list-item-title">查看会员</div>
    		</div>
		</li>
	</ul>
</body>
<script type="text/javascript">
</script>
</html>