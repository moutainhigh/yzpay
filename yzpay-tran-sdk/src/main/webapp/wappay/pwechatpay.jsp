<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<title>一码付-微信支付</title>
<meta charset="utf-8" />
<meta name="viewport" content="initial-scale=1.0, width=device-width, user-scalable=no" />
<link rel="stylesheet" href="<%=basePath%>static/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=basePath%>static/bootstrap/css/bootstrapValidator.min.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>/static/css/wxzf.css">
<script src="<%=basePath%>static/bootstrap/js/jquery-1.11.2.min.js"></script>
<script src="<%=basePath%>static/bootstrap/js/bootstrap.min.js"></script>
<script src="<%=basePath%>static/bootstrap/js/bootstrapValidator.min.js"></script>

<script>
function onBrideReady(result) {
	WeixinJSBridge.invoke(
       'getBrandWCPayRequest', {
	   "appId" : result.data.appId ,     //公众号名称，由商户传入     
	   "timeStamp": result.data.timeStamp ,         //时间戳，自1970年以来的秒数     
	   "nonceStr": result.data.nonceStr, //随机串     
	   "package" : result.data.packages ,     
	   "signType": result.data.signType ,   //微信签名方式  
	   "paySign": result.data.paySign      //微信签名 
	   },
	   function(res){  
	       if(res.err_msg == "get_brand_wcpay_request:ok" ) {// 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。 
	    	   	//console.info("微信支付成功!");
	    	   	WeixinJSBridge.call("closeWindow");
			}else if(res.err_msg == "get_brand_wcpay_request:cancel"){  
				//console.info("用户取消支付!");  
				WeixinJSBridge.call("closeWindow");
			}else{  
				//console.info("支付失败!"); 
				WeixinJSBridge.call("closeWindow");
			}     
	   }
	); 		
}
$(document).ready(function() {
	$('#wechatForm').bootstrapValidator({
       message: '输入的信息未通过有效验证',
       feedbackIcons: {
           valid: 'glyphicon glyphicon-ok',
           invalid: 'glyphicon glyphicon-remove',
           validating: 'glyphicon glyphicon-refresh'
       },
       fields: {}
   }).on('success.form.bv', function(e) {
       // Prevent form submission
       e.preventDefault();
       // Get the form instance
       var $form = $(e.target);
       // Get the BootstrapValidator instance
       var bv = $form.data('bootstrapValidator');
       // Use Ajax to submit form data
       $.post($form.attr('action'), $form.serialize(), function(result) {
    	   if(result.result_code=="SUCCESS"){
				//在回调函数种执行交互流程
	   			if (typeof WeixinJSBridge == "undefined"){
	   			   if( document.addEventListener ){
	   			       document.addEventListener('WeixinJSBridgeReady', function(result) {onBrideReady(result);}, false);
	   			   }else if (document.attachEvent){
	   			       document.attachEvent('WeixinJSBridgeReady', function(result) {onBrideReady(result);}); 
	   			       document.attachEvent('onWeixinJSBridgeReady', function(result) {onBrideReady(result);});
	   			   }
	   			}else{
	   				onBrideReady(result);
	   			} 
			}
			else{
				alert(result.err_code_des);
			}
       }, 'json');
   });
});
</script>
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
	<form id="wechatForm" method="post" action="<%=basePath%>pay/one/wechat">
		<div class="container">
			<div class="panel panel-default">
			    <div class="panel-heading">
			        <h4 class="panel-title">微信支付</h4>
			    </div>
			    <div class="panel-body">
			        <ul class="list-group">
					    <li class="list-group-item"><label class="control-label" style="width:100px;">收款方：</label><label>${merchantName}</label></li>
					    <li class="list-group-item"><label class="control-label" style="width:100px;">付款金额：</label><label>${transAmt}</label></li>						
					</ul>
					<div class="form-group">
						<input type="hidden" name="t" value="${transNum}" />
						<input type="hidden" name="auth_code" value="${code}" />
			  			<button type="submit" class="btn btn-success btn-lg btn-block">付款</button>
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