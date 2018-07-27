<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.text.*" import="java.util.*"  %>
    <%
	    String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; 
    %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
    <title>接口测试</title>
	<link rel="stylesheet" href="static/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="static/bootstrap/css/bootstrapValidator.min.css">
	<script src="static/bootstrap/js/jquery-1.11.2.min.js"></script>
	<script src="static/bootstrap/js/bootstrap.min.js"></script>
	<script src="static/bootstrap/js/bootstrapValidator.min.js"></script>
	<script type="text/javascript" src="http://cdn.staticfile.org/jquery.qrcode/1.0/jquery.qrcode.min.js"></script>
	<style>
		.icp{
			position: fixed; /*or前面的是absolute就可以用*/  
       		bottom: 10px;  
       		left:50%;
		}
	</style>
	<script>
		function forward(){
			var forwardUrl = $("#forward_url").val();
			window.location=forwardUrl;
		}
	</script>
</head>
<body>
	<div class="container">
		<div class="panel panel-default">
		    <div class="panel-heading">
		        <h4 class="panel-title">支付宝支付</h4>
		    </div>
		    <div class="panel-body">
		        <ul class="list-group">
				    <li class="list-group-item"> <input type="text" class="form-control" id="forward_url" name="forward_url" value="" data-bv-notempty data-bv-notempty-message="跳转地址"/></li>
				</ul>
				<div class="form-group">
		  			<button type="button" class="btn btn-primary btn-lg btn-block" onclick="forward();">跳转付款</button>
		  		</div>
		    </div>
		</div>
	</div>
</body>
</html>