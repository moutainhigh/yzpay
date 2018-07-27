<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.text.*" import="java.util.*" %>
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
</head>
<body>
<iframe name="frame_menu" src="menu.jsp" style="border:0;" width="200" height="900"></iframe>
<iframe name="frame_body" src="activemq.jsp" style="border:0;" width="1600" height="900"/></iframe>

<div class="icp">
	<a href="http://www.miitbeian.gov.cn/">粤ICP备17070748号</a>
</div>
</body>
</html>