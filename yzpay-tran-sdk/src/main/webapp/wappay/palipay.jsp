<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<title>一码付-支付宝</title>
<meta charset="utf-8" />
<meta name="viewport" content="initial-scale=1.0, width=device-width, user-scalable=no" />
<link rel="stylesheet" href="<%=basePath%>static/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=basePath%>static/bootstrap/css/bootstrapValidator.min.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>/static/css/wxzf.css">
</head>
<!-- 
 * 在微信浏览器里面打开H5网页中执行JS调起支付。接口输入输出数据格式为JSON。
 * 成功调起支付需要三个步骤：
 * 步骤1：网页授权获取用户openid
 * 步骤2：使用统一支付接口，获取prepay_id
 * 步骤3：使用jsapi调起支付
 -->
<body>
	<br>
	<form id="alipayForm" method="post" action="<%=basePath%>pay/one/alipay">
		<div class="container">
			<div class="panel panel-default">
			    <div class="panel-heading">
			        <h4 class="panel-title">支付宝支付</h4>
			    </div>
			    <div class="panel-body">
			        <ul class="list-group">
					    <li class="list-group-item"><label class="control-label" style="width:100px;">收款方：</label><label>${merchantName}</label></li>
					    <li class="list-group-item"><label class="control-label" style="width:100px;">付款金额：</label><label>${transAmt}</label></li>						
					</ul>
					<div class="form-group">
						<input type="hidden" name="t" value="${transNum}" />
			  			<button type="submit" class="btn btn-primary btn-lg btn-block">付款</button>
			  		</div>
			    </div>
			</div>
		</div>
	</form>
	<div class="js-footer" style="min-height: 1px;">
		<div>
			<div class="footer">
				<div class="copyright">
					<div class="ft-links">
					  	<span class="pf-copyright" >由<span style="color:#f57c00;">盈承信息</span>提供技术服务</span>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>