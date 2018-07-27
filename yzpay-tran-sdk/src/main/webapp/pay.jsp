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
	var Time = 60,t;
	var c = Time;
	function timedCount() {
        $('#timecount').text("支付倒计时(" + c + ")");
        //等10秒后每隔2秒查询一次交易结果
        if(c<50 && c%2==0){
        	orderQuery();
        }
        c = c - 1;
        t = setTimeout(function() {
            timedCount();
        }, 1000);
        if (c < 0) {
            c = Time;
            stopCount();
            $("#scanresult").attr("class","alert-danger");
            $("#scanresult").text("支付超时");
        }
    }

	//停止计数
    function stopCount() {
        clearTimeout(t);
        $('#qrcode').text("");
        $('#timecount').text("");
    }
    
	//订单状态查询
    function orderQuery(){
    	var merchNo = $("#scanpay #merchant_num").val();
    	var orderNo = $("#scanpay #user_order_no").val();
    	$.ajax({
			type:'POST',
			url:"pay/test",
			data:{'url':"<%=basePath%>pay/order/query",'merchant_num' : merchNo,'user_order_no':orderNo},
			cache: false,
			success: function(result) {
				if(result.result_code=="SUCCESS"){
					if(result.data.trade_state=="2"){
						stopCount();
						$("#scanresult").text("支付成功|"+JSON.stringify(result.data));
	               		$("#scanresult").attr("class","alert-success");
					}else if(result.data.trade_state=="1" || result.data.trade_state=="0"){
						$("#scanresult").text("支付中|"+JSON.stringify(result.data));
	               		$("#scanresult").attr("class","alert-success");
					}else{
						//支付失败
						stopCount();
						$("#scanresult").text("错误码："+result.err_code+"错误原因"+result.err_code_des);
						$("#scanresult").attr("class","alert-danger");
					}
				}else{
					//订单查询失败
					stopCount();
					$("#scanresult").text("订单查询失败，错误码："+result.err_code+"错误原因"+result.err_code_des);
					$("#scanresult").attr("class","alert-danger");
				}
			}
		});
    }
    Date.prototype.Format = function(formatStr)
    {
	    var str = formatStr;
	    var Week = ['日','一','二','三','四','五','六'];
	
	    str=str.replace(/yyyy|YYYY/,this.getFullYear());
	    str=str.replace(/yy|YY/,(this.getYear() % 100)>9?(this.getYear() % 100).toString():'0' + (this.getYear() % 100));
	
	    str=str.replace(/MM/,this.getMonth()>9?this.getMonth().toString():'0' + this.getMonth());
	    str=str.replace(/M/g,this.getMonth());
	
	    str=str.replace(/w|W/g,Week[this.getDay()]);
	
	    str=str.replace(/dd|DD/,this.getDate()>9?this.getDate().toString():'0' + this.getDate());
	    str=str.replace(/d|D/g,this.getDate());
	
	    str=str.replace(/hh|HH/,this.getHours()>9?this.getHours().toString():'0' + this.getHours());
	    str=str.replace(/h|H/g,this.getHours());
	    str=str.replace(/mm/,this.getMinutes()>9?this.getMinutes().toString():'0' + this.getMinutes());
	    str=str.replace(/m/g,this.getMinutes());
	
	    str=str.replace(/ss|SS/,this.getSeconds()>9?this.getSeconds().toString():'0' + this.getSeconds());
	    str=str.replace(/s|S/g,this.getSeconds());
	
	    return str;
    }
    function getCurrDate(currBtn){
		var mydate = new Date();
		var orderNo=mydate.Format("yyyyMMddHHmmss"); //获取日期与时间
		var currOrder = $(currBtn).parents("form")[0];
		$(currOrder).find("#user_order_no").val(orderNo);
		
	}
	$(document).ready(function() {
		$("#barresult").hide();
		$("#scanresult").hide();
		$("#oneresult").hide();
		$("#appletresult").hide();
		$("#appresult").hide();
		$("#queryresult").hide();
		$("#reverseresult").hide();
		$("#refund_queryresult").hide();
		$("#refundresult").hide();
		$("#closeresult").hide();
		/*******************条码支付测试提交**********************/
	    $('#barform').bootstrapValidator({
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
               		//alert("支付成功|"+JSON.stringify(result.data));
               		$("#barresult").text("支付成功|"+JSON.stringify(result.data));
               		$("#barresult").attr("class","alert-success");
               		
               	}else{
               		$("#barresult").text("错误码："+result.err_code+"错误原因"+result.err_code_des);
               		$("#barresult").attr("class","alert-danger");
               	}
               	$("#barresult").show();
            }, 'json');
        });
	    /*******************扫码支付测试提交**********************/
	    $('#scanform').bootstrapValidator({
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
               		$('#qrcode').qrcode(result.data.code_url);
					$("#scanresult").text("下单成功|"+JSON.stringify(result.data));
               		$("#scanresult").attr("class","alert-success");
               		//启动计时器
               		timedCount();
               	}else{
               		$("#scanresult").text("错误码："+result.err_code+"错误原因"+result.err_code_des);
               		$("#scanresult").attr("class","alert-danger");
               	}
               	$("#scanresult").show();
            }, 'json');
        });
	    /*******************一码付测试提交**********************/
	    $('#oneform').bootstrapValidator({
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
               		$('#onecode').qrcode(result.data.code_url);
					$("#oneresult").text("下单成功|"+JSON.stringify(result.data));
               		$("#oneresult").attr("class","alert-success");
               		
               	}else{
               		$("#oneresult").text("错误码："+result.err_code+"错误原因"+result.err_code_des);
               		$("#oneresult").attr("class","alert-danger");
               	}
               	$("#oneresult").show();
            }, 'json');
        });
	    /*******************小程序支付测试提交**********************/
	    $('#appletform').bootstrapValidator({
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
					$("#appletresult").text("下单成功|"+JSON.stringify(result.data));
               		$("#appletresult").attr("class","alert-success");
               		//启动计时器
               		timedCount();
               	}else{
               		$("#appletresult").text("错误码："+result.err_code+"错误原因"+result.err_code_des);
               		$("#appletresult").attr("class","alert-danger");
               	}
               	$("#appletresult").show();
            }, 'json');
        });
	    /*******************条码支付测试提交**********************/
	    $('#appform').bootstrapValidator({
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
               		//alert("支付成功|"+JSON.stringify(result.data));
               		$("#appresult").text("下单成功|"+JSON.stringify(result.data));
               		$("#appresult").attr("class","alert-success");
               		
               	}else{
               		$("#appresult").text("错误码："+result.err_code+"错误原因"+result.err_code_des);
               		$("#appresult").attr("class","alert-danger");
               	}
               	$("#appresult").show();
            }, 'json');
        });
	  	/*******************订单查询测试提交**********************/
	    $('#queryform').bootstrapValidator({
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
               		$("#queryresult").text("查询成功|"+JSON.stringify(result.data));
               		$("#queryresult").attr("class","alert-success");
               	}else{
               		$("#queryresult").text("错误码："+result.err_code+"错误原因"+result.err_code_des);
               		$("#queryresult").attr("class","alert-danger");
               	}
               	$("#queryresult").show();
               	$('#queryform').bootstrapValidator('disableSubmitButtons', false);
            }, 'json');
        });
	    /*******************订单关闭测试提交**********************/
	    $('#closeform').bootstrapValidator({
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
               		$("#closeresult").text("关闭成功|"+JSON.stringify(result.data));
               		$("#closeresult").attr("class","alert-success");
               	}else{
               		$("#closeresult").text("错误码："+result.err_code+"错误原因"+result.err_code_des);
               		$("#closeresult").attr("class","alert-danger");
               	}
               	$("#closeresult").show();
            }, 'json');
        });
	  	/*******************订单撤销测试提交**********************/
	    $('#reverseform').bootstrapValidator({
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
               		$("#reverseresult").text("撤销成功|"+JSON.stringify(result.data));
               		$("#reverseresult").attr("class","alert-success");
               	}else{
               		$("#reverseresult").text("错误码："+result.err_code+"错误原因"+result.err_code_des);
               		$("#reverseresult").attr("class","alert-danger");
               	}
               	$("#reverseresult").show();
            }, 'json');
        });
	    /*******************退款申请测试提交**********************/
	    $('#refundform').bootstrapValidator({
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
               		$("#refundresult").text("退款申请提交成功|"+JSON.stringify(result.data));
               		$("#refundresult").attr("class","alert-success");
               	}else{
               		$("#refundresult").text("错误码："+result.err_code+"错误原因"+result.err_code_des);
               		$("#refundresult").attr("class","alert-danger");
               	}
               	$("#refundresult").show();
            }, 'json');
        });
	    /*******************退款查询测试提交**********************/
	    $('#refundqueryform').bootstrapValidator({
            message: '输入的信息未通过有效验证',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: { }
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
               		$("#refund_queryresult").text("查询成功|"+JSON.stringify(result.data));
               		$("#refund_queryresult").attr("class","alert-success");
               	}else{
               		$("#refund_queryresult").text("错误码："+result.err_code+"错误原因"+result.err_code_des);
               		$("#refund_queryresult").attr("class","alert-danger");
               	}
               	$("#refund_queryresult").show();
            }, 'json');
        });
	});
	</script>
</head>
<body>

<div class="container">
  <ul class="nav nav-tabs">
    <li class="active"><a data-toggle="tab" href="#barpay">条码测试</a></li>
    <li><a data-toggle="tab" href="#scanpay">扫码测试</a></li>
    <li><a data-toggle="tab" href="#onepay">一码付下单</a></li>
    <li><a data-toggle="tab" href="#appletpay">小程序下单测试</a></li>
    <li><a data-toggle="tab" href="#apppay">app下单测试</a></li>
    <li><a data-toggle="tab" href="#query">订单查询</a></li>
     <li><a data-toggle="tab" href="#close">订单关闭</a></li>
    <li><a data-toggle="tab" href="#reverse">订单撤销</a></li>
    <li><a data-toggle="tab" href="#refund">退款申请</a></li>
    <li><a data-toggle="tab" href="#refundquery">退款结果查询</a></li>
  </ul>

  <div class="tab-content">
  	<!-- 条码支付 -->
    <div id="barpay" class="tab-pane fade in active">
    	<div class="panel panel-default">
			<div class="panel-body">
				<form  id="barform" class="form-horizontal" action="pay/test">
					<div class="form-group">
	                    <label class="col-lg-3 control-label">商户号*</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="merchant_num" name="merchant_num" value="999910031001993593" data-bv-notempty data-bv-notempty-message="商户号"/>
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-lg-3 control-label">接口地址*</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="url" name="url" value="<%=basePath %>pay/micropay" data-bv-notempty data-bv-notempty-message="接口地址"/>
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-lg-3 control-label">交易流水号*</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="user_order_no" name="user_order_no" value="<%=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) %>" data-bv-notempty data-bv-notempty-message="交易流水号"/>                       	
	                    </div>
	                    <div class="col-lg-3">
	                    	<button type="button" id="btnOrderNo" class="btn btn-primary" onclick="getCurrDate(this)">刷新</button>
	                    </div>
	                </div>
			       <div class="form-group">
	                    <label class="col-lg-3 control-label">付款码*</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="dynamic_id" name="dynamic_id" data-bv-notempty data-bv-notempty-message="付款码"/>
	                       
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-lg-3 control-label">交易金额(元)*</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="total_fee" name="total_fee"  value="0.01" data-bv-notempty data-bv-notempty-message="交易金额"/>
	                       
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-lg-3 control-label">订单标题</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="body" name="body" />
	                    </div>
	                </div>
			        <div class="form-group">
	                    <div class="col-lg-9 col-lg-offset-3">
	                        <button type="submit" class="btn btn-success">提交</button>
	                    </div>
	                </div>
	                <div id="barresult" class="alert alert-success" ></div>
			    </form>
			</div>
		</div>
    </div>
    <!-- 扫码支付 -->
    <div id="scanpay" class="tab-pane fade">
    	<div class="panel panel-default">
			<div class="panel-body">
				<form  id="scanform" class="form-horizontal" action="pay/test">
					<div class="form-group">
	                    <label class="col-lg-3 control-label">商户号*</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="merchant_num" name="merchant_num" value="999910031001993593" data-bv-notempty data-bv-notempty-message="商户号"/>
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-lg-3 control-label">接口地址*</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="url" name="url" value="<%=basePath %>pay/unifiedorder" data-bv-notempty data-bv-notempty-message="接口地址"/>
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-lg-3 control-label">交易流水号*</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="user_order_no" name="user_order_no" value="<%=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) %>" data-bv-notempty data-bv-notempty-message="交易流水号"/>
	                    </div>
	                    <div class="col-lg-3">
	                    	<button type="button" id="btnOrderNo" class="btn btn-primary"  onclick="getCurrDate(this)">刷新</button>
	                    </div>
	                </div>
			       <div class="form-group">
	                    <label class="col-lg-3 control-label">渠道</label>
	                    <div class="col-xs-6">
			            	<select class="form-control" name="pay_channel" id="pay_channel" data-bv-notempty data-bv-notempty-message="请选择渠道">	
				       			<option value="2" selected="selected" >微信</option>
				       			<option value="1">支付宝</option>
				       			<option value="3">银联</option>
			             	</select>
		                </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-lg-3 control-label">交易金额(元)*</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="total_fee" name="total_fee"  value="0.01" data-bv-notempty data-bv-notempty-message="交易金额"/>       
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-lg-3 control-label">订单标题</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="body" name="body" />
	                       
	                    </div>
	                </div>
			        <div class="form-group">
	                    <div class="col-lg-9 col-lg-offset-3">
	                        <button type="submit" class="btn btn-success">提交</button>
	                    </div>
	                </div>
	                <div class="form-group">
	                	<div id="qrcode"></div>
	                	<div id="timecount"></div>
	                </div>
	                <div id="scanresult" class="alert alert-success" ></div>
			    </form>
			</div>
		</div>
    </div>
    <!-- 一码付下单 -->
    <div id="onepay" class="tab-pane fade">
    	<div class="panel panel-default">
			<div class="panel-body">
				<form  id="oneform" class="form-horizontal" action="pay/test">
					<div class="form-group">
	                    <label class="col-lg-3 control-label">商户号*</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="merchant_num" name="merchant_num" value="999910031001993593" data-bv-notempty data-bv-notempty-message="商户号"/>
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-lg-3 control-label">接口地址*</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="url" name="url" value="<%=basePath %>pay/one/qrcode" data-bv-notempty data-bv-notempty-message="接口地址"/>
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-lg-3 control-label">交易流水号*</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="user_order_no" name="user_order_no" value="<%=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) %>" data-bv-notempty data-bv-notempty-message="交易流水号"/>
	                    </div>
	                    <div class="col-lg-3">
	                    	<button type="button" id="btnOrderNo" class="btn btn-primary"  onclick="getCurrDate(this)">刷新</button>
	                    </div>
	                </div>   
	                <div class="form-group">
	                    <label class="col-lg-3 control-label">交易金额(元)*</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="total_fee" name="total_fee"  value="0.01" data-bv-notempty data-bv-notempty-message="交易金额"/>       
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-lg-3 control-label">订单标题</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="body" name="body" />
	                    </div>
	                </div>
			        <div class="form-group">
	                    <div class="col-lg-9 col-lg-offset-3">
	                        <button type="submit" class="btn btn-success">提交</button>
	                    </div>
	                </div>
	                <div class="form-group">
	                	<div id="onecode"></div>
	                </div>
	                <div id="oneresult" class="alert alert-success" ></div>
			    </form>
			</div>
		</div>
    </div>
    <!-- 小程序下单 -->
    <div id="appletpay" class="tab-pane fade">
    	<div class="panel panel-default">
			<div class="panel-body">
				<form  id="appletform" class="form-horizontal" action="pay/test">
					<div class="form-group">
	                    <label class="col-lg-3 control-label">商户号*</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="merchant_num" name="merchant_num" value="999910031001993593" data-bv-notempty data-bv-notempty-message="商户号"/>
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-lg-3 control-label">接口地址*</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="url" name="url" value="<%=basePath %>pay/applet" data-bv-notempty data-bv-notempty-message="接口地址"/>
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-lg-3 control-label">交易流水号*</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="user_order_no" name="user_order_no" value="<%=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) %>" data-bv-notempty data-bv-notempty-message="交易流水号"/>
	                    </div>
	                    <div class="col-lg-3">
	                    	<button type="button" id="btnOrderNo" class="btn btn-primary"  onclick="getCurrDate(this)">刷新</button>
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-lg-3 control-label">交易金额(元)*</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="total_fee" name="total_fee"  value="0.01" data-bv-notempty data-bv-notempty-message="交易金额"/>       
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-lg-3 control-label">openId*</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="open_id" name="open_id" value="omdJDv8cWHulYihiPCaVWavuv71c" data-bv-notempty data-bv-notempty-message="openId"/>
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-lg-3 control-label">订单标题</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="body" name="body" />
	                       
	                    </div>
	                </div>
			        <div class="form-group">
	                    <div class="col-lg-9 col-lg-offset-3">
	                        <button type="submit" class="btn btn-success">提交</button>
	                    </div>
	                </div>
	                <div id="appletresult" class="alert alert-success" ></div>
			    </form>
			</div>
		</div>
    </div>
    <!-- app支付下单 -->
    <div id="apppay" class="tab-pane fade">
    	<div class="panel panel-default">
			<div class="panel-body">
				<form  id="appform" class="form-horizontal" action="pay/test">
					<div class="form-group">
	                    <label class="col-lg-3 control-label">商户号*</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="merchant_num" name="merchant_num" value="999910031001993593" data-bv-notempty data-bv-notempty-message="商户号"/>
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-lg-3 control-label">接口地址*</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="url" name="url" value="<%=basePath %>pay/app" data-bv-notempty data-bv-notempty-message="接口地址"/>
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-lg-3 control-label">交易流水号*</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="user_order_no" name="user_order_no" value="<%=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) %>" data-bv-notempty data-bv-notempty-message="交易流水号"/>
	                    </div>
	                    <div class="col-lg-3">
	                    	<button type="button" id="btnOrderNo" class="btn btn-primary"  onclick="getCurrDate(this)">刷新</button>
	                    </div>
	                </div>
			       <div class="form-group">
	                    <label class="col-lg-3 control-label">渠道</label>
	                    <div class="col-xs-6">
			            	<select class="form-control" name="pay_channel" id="pay_channel" data-bv-notempty data-bv-notempty-message="请选择渠道">	
				       			<option value="2" selected="selected" >微信</option>
				       			<option value="1">支付宝</option>
			             	</select>
		                </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-lg-3 control-label">交易金额(元)*</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="total_fee" name="total_fee"  value="0.01" data-bv-notempty data-bv-notempty-message="交易金额"/>       
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-lg-3 control-label">订单标题</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="body" name="body" />
	                       
	                    </div>
	                </div>
			        <div class="form-group">
	                    <div class="col-lg-9 col-lg-offset-3">
	                        <button type="submit" class="btn btn-success">提交</button>
	                    </div>
	                </div>
	                <div class="form-group">
	                	<div id="qrcode"></div>
	                	<div id="timecount"></div>
	                </div>
	                <div id="appresult" class="alert alert-success" ></div>
			    </form>
			</div>
		</div>
    </div>
    <!-- 订单查询 -->
    <div id="query" class="tab-pane fade">
    	<div class="panel panel-default">
			<div class="panel-body">
				<form  id="queryform" class="form-horizontal" action="pay/test">
					<div class="form-group">
	                    <label class="col-lg-3 control-label">商户号*</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="merchant_num" name="merchant_num" value="999910031001993593" data-bv-notempty data-bv-notempty-message="商户号"/>
	                    </div>
	                </div>
	                 <div class="form-group">
	                    <label class="col-lg-3 control-label">接口地址*</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="url" name="url" value="<%=basePath %>pay/order/query" data-bv-notempty data-bv-notempty-message="接口地址"/>
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-lg-3 control-label">交易流水号</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="user_order_no" name="user_order_no" value=""/>
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-lg-3 control-label">平台流水号</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="trace_num" name="trace_num" value=""/>
	                    </div>
	                </div>
			        <div class="form-group">
	                    <div class="col-lg-9 col-lg-offset-3">
	                        <button type="submit" class="btn btn-success">提交</button>
	                    </div>
	                </div>
	                <div id="queryresult" class="alert alert-success" ></div>
			    </form>
			</div>
		</div>
    </div>
    <!-- 订单关闭 -->
    <div id="close" class="tab-pane fade">
    	<div class="panel panel-default">
			<div class="panel-body">
				<form  id="closeform" class="form-horizontal" action="pay/test">
					<div class="form-group">
	                    <label class="col-lg-3 control-label">商户号*</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="merchant_num" name="merchant_num" value="999910031001993593" data-bv-notempty data-bv-notempty-message="商户号"/>
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-lg-3 control-label">接口地址*</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="url" name="url" value="<%=basePath %>pay/order/close" data-bv-notempty data-bv-notempty-message="接口地址"/>
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-lg-3 control-label">交易流水号</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="user_order_no" name="user_order_no" value=""/>
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-lg-3 control-label">平台流水号</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="trace_num" name="trace_num" value=""/>
	                    </div>
	                </div>
			        <div class="form-group">
	                    <div class="col-lg-9 col-lg-offset-3">
	                        <button type="submit" class="btn btn-success">提交</button>
	                    </div>
	                </div>
	                <div id="closeresult" class="alert alert-success" ></div>
			    </form>
			</div>
		</div>
    </div>
    <!-- 订单撤销 -->
    <div id="reverse" class="tab-pane fade">
    	<div class="panel panel-default">
			<div class="panel-body">
				<form  id="reverseform" class="form-horizontal" action="pay/test">
					<div class="form-group">
	                    <label class="col-lg-3 control-label">商户号*</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="merchant_num" name="merchant_num" value="999910031001993593" data-bv-notempty data-bv-notempty-message="商户号"/>
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-lg-3 control-label">接口地址*</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="url" name="url" value="<%=basePath %>pay/order/reverse" data-bv-notempty data-bv-notempty-message="接口地址"/>
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-lg-3 control-label">交易流水号</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="user_order_no" name="user_order_no" value=""/>
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-lg-3 control-label">平台流水号</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="trace_num" name="trace_num" value=""/>
	                    </div>
	                </div>
			        <div class="form-group">
	                    <div class="col-lg-9 col-lg-offset-3">
	                        <button type="submit" class="btn btn-success">提交</button>
	                    </div>
	                </div>
	                <div id="reverseresult" class="alert alert-success" ></div>
			    </form>
			</div>
		</div>
    </div>
    <!-- 退款申请 -->
    <div id="refund" class="tab-pane fade">
    	<div class="panel panel-default">
			<div class="panel-body">
				<form  id="refundform" class="form-horizontal" action="pay/test">
					<div class="form-group">
	                    <label class="col-lg-3 control-label">商户号*</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="merchant_num" name="merchant_num" value="999910031001993593" data-bv-notempty data-bv-notempty-message="商户号"/>
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-lg-3 control-label">接口地址*</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="url" name="url" value="<%=basePath %>pay/order/refund" data-bv-notempty data-bv-notempty-message="接口地址"/>
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-lg-3 control-label">退款流水号</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="user_refund_no" name="user_refund_no" 
	                        value="R<%=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) %>" 
	                        data-bv-notempty data-bv-notempty-message="退款流水号" />
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-lg-3 control-label">退款金额(元)*</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="refund_fee" name="refund_fee"  value="0.01" 
	                        data-bv-notempty data-bv-notempty-message="退款金额"/>       
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-lg-3 control-label">原交易流水号</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="user_order_no" name="user_order_no" value=""/>
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-lg-3 control-label">原平台流水号</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="trace_num" name="trace_num" value=""/>
	                    </div>
	                </div>
			        <div class="form-group">
	                    <div class="col-lg-9 col-lg-offset-3">
	                        <button type="submit" class="btn btn-success">提交</button>
	                    </div>
	                </div>
	                <div id="refundresult" class="alert alert-success" ></div>
			    </form>
			</div>
		</div>
    </div>
    <!-- 退款查询 -->
    <div id="refundquery" class="tab-pane fade">
    	<div class="panel panel-default">
			<div class="panel-body">
				<form  id="refundqueryform" class="form-horizontal" action="pay/test">
					<div class="form-group">
	                    <label class="col-lg-3 control-label">商户号*</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="merchant_num" name="merchant_num" value="999910031001993593" data-bv-notempty data-bv-notempty-message="商户号"/>
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-lg-3 control-label">接口地址*</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="url" name="url" value="<%=basePath %>pay/refund/query" data-bv-notempty data-bv-notempty-message="接口地址"/>
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-lg-3 control-label">退款流水号</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="user_refund_no" name="user_refund_no" value="" />
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-lg-3 control-label">退款平台流水号</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="trace_refund_num" name="trace_refund_num" value=""/>
	                    </div>
	                </div>
			        <div class="form-group">
	                    <div class="col-lg-9 col-lg-offset-3">
	                        <button type="submit" class="btn btn-success">提交</button>
	                    </div>
	                </div>
	                <div id="refund_queryresult" class="alert alert-success" ></div>
			    </form>
			</div>
		</div>
    </div>
  </div>
</div>
</body>
</html>