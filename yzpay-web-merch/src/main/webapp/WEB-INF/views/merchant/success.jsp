<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../common/taglib/taglib.jsp"%> 
<!DOCTYPE html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width,initial-scale=1.0"/>
	<meta name="format-detection" content="telephone=no,email=no,date=no,address=no">
	<title>完善商户资料</title>
	<link rel="stylesheet" type="text/css" href="${baseURL }/common/css/aui.css" />
	<link rel="stylesheet" type="text/css" href="${baseURL }/common/css/style.css?v=<%=System.currentTimeMillis() %>" />
	<script src="${baseURL}/bootstrap/js/jquery-1.11.2.min.js"></script> 
	<script src="${baseURL}/common/js/plugin/jsBridge.js?v=<%=System.currentTimeMillis() %>"></script>
	<script src="${baseURL}/common/js/plugin/jsFunction.js?v=<%=System.currentTimeMillis() %>"></script>
	<script src="${baseURL}/common/js/common.js?v=<%=System.currentTimeMillis()%> "></script>
	<style>
		body,html{
			background-color: #fff;
		}
		.dow-exp{
			text-align: center;
			color: #666;
			padding: 25px;
		}
		.dow-exp span{
			color: #abcdef;
			display: inline-block;
			padding: 0 10px;
		}
		.dow-btn{
			width: 40%;
    		margin: 0 auto;
		}
	</style>
</head>
<body>
	<input type="hidden" id="merchUser" value="${merchUser}">
	<input type="hidden" id="duplicateLogin" autocomplete="off" value="${duplicateLogin}">
	<div class="dow-exp">商户资料提交成功，页面将在<span>5</span>秒后自动关闭</div>
	<button class="aui-btn aui-btn-block aui-btn-outlined dow-btn" 
	<c:if test="${merchUser != null}">  onclick="window.location.href='${baseURL}/sys/index/index' " 	</c:if>
    	<c:if test="${merchUser == null}">  onclick="exitWebview()"  </c:if> >立即返回</button>
		<script type="text/javascript">
		 (function(){
			var n = 5;
			var timer = setInterval(function(){
				n = n-1;
				$(".dow-exp").html("商户资料提交成功，页面将在<span>"+n+"</span>秒后自动关闭");
				if(n <1){
					clearInterval(timer);
					var merchUser = $("#merchUser").val();
					if(merchUser != ''){
						window.location.href = "/merch/sys/index/index";
					}else{
						exitWebview();
					}
				}
				
			}, 1000);
		})();
	</script>
</body>
</html>