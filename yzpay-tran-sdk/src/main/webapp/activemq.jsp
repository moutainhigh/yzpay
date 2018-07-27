<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.text.*" import="java.util.*" import="com.yunpay.common.utils.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>activemq测试</title>
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
$(document).ready(function() {
	$('#activemqform').bootstrapValidator({
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
	       		$("#send_result").text("成功|"+JSON.stringify(result.data));
	       		$("#send_result").attr("class","alert-success");
	       	}else{
	       		$("#send_result").text("错误码："+result.err_code+"错误原因"+result.err_code_des);
	       		$("#send_result").attr("class","alert-danger");
	       	}
	       	$("#send_result").show();
	    }, 'json');
	});
});
</script>
</head>
<body>
<div class="container">
    <!-- 发送消息 -->
    <div id="activemq_sdmsg" class="tab-pane fade in active">
    	<div class="panel panel-default">
			<div class="panel-body">
				<form  id="activemqform" class="form-horizontal" action="test/activemq">
					<div class="form-group">
	                    <label class="col-lg-2 control-label">目标地址*</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="dest_name" name="dest_name" value="yzpay.coupon.notify" maxlength="20"
	                        placeholder="请输入发送的目标地址名称" data-bv-notempty data-bv-notempty-message="目标地址"/>
	                    </div>
	                </div>
					<div class="form-group">
	                    <label class="col-lg-2 control-label">发送内容*</label>
	                    <div class="col-lg-3">
	                        <input type="text" class="form-control" id="send_msg" name="send_msg" value=""
	                        placeholder="请输入发送内容" data-bv-notempty data-bv-notempty-message="发送内容"/>
	                    </div>
	                </div>
			        <div class="form-group">
	                    <div class="col-lg-9 col-lg-offset-3">
	                        <button type="submit" class="btn btn-success">提交</button>
	                    </div>
	                </div>
	                <div id="send_result" class="alert alert-success" ></div>
			    </form>
			</div>
		</div>
    </div>
</div>
</body>
</html>