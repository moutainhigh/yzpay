<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../common/taglib/taglib.jsp"%> 
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width,initial-scale=1.0"/>
	<meta name="format-detection" content="telephone=no,email=no,date=no,address=no">
	<title>异常操作</title>
	<script src="${baseURL}/bootstrap/js/jquery-1.11.2.min.js"></script> 
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
	</style>
</head>
<body>
	<input type="hidden" id="message" value="${message}">
	<div class="dow-exp">${message}</div>
		<script type="text/javascript">
		 (function(){
			var message = $("#message").val();
			var n = 6;
			var ua = window.navigator.userAgent.toLowerCase(); 
			var timer = setInterval(function(){
				n = n-1;
				if(ua.match(/MicroMessenger/i) == 'micromessenger'){
					$(".dow-exp").html(message+"，页面将在<span>"+n+"</span>秒后自动关闭");
				}else {
					$(".dow-exp").html(message);
				}
				if(n <1){
					WeixinJSBridge.call('closeWindow');
					clearInterval(timer);
				}
			}, 1000);			
		})();
	</script>
</body>
</html>