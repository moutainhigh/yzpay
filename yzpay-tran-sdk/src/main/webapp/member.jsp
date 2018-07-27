<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.text.*" import="java.util.*" import="com.yunpay.common.utils.*" %>
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
<script src="static/js/json2.js"></script>
<script type="text/javascript" src="http://cdn.staticfile.org/jquery.qrcode/1.0/jquery.qrcode.min.js"></script>
<script>
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
	$(currOrder).find("#recharge_order_no").val(orderNo);
	$(currOrder).find("#user_order_no").val(orderNo);
}

$(document).ready(function() {
	$("#createresult").hide();
	$("#updateresult").hide();
	$("#qrcoderesult").hide();
	$("#rechargeresult").hide();
	$("#consumresult").hide();
	$("#queryresult").hide();
    /*******************创建卡券**********************/
    $('#createform').bootstrapValidator({
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
       var memberCardReq = new Object();
       //会员卡基本信息
       memberCardReq.merchant_num = $form.find("#merchant_num").val();
       memberCardReq.title = $form.find("#title").val();
       memberCardReq.logo_url = $form.find("#logo_url").val();
       memberCardReq.merchant_name = $form.find("#merchant_name").val();
       memberCardReq.notice = $form.find("#notice").val();
       memberCardReq.description = $form.find("#description").val();
       memberCardReq.color = $form.find("#color").val();
       memberCardReq.code_type = $form.find("#code_type").val();
       memberCardReq.quantity = $form.find("#quantity").val();
       memberCardReq.service_phone = $form.find("#service_phone").val();
       memberCardReq.can_share=0;
       if ($form.find("#can_share").is(':checked')) {
    	   memberCardReq.can_share=1;
	   }
       memberCardReq.can_give_friend=0;
       if ($form.find("#can_give_friend").is(':checked')) {
    	   memberCardReq.can_give_friend=1;
	   }
       memberCardReq.background_pic_url = $form.find("#background_pic_url").val();
       memberCardReq.activate_type = $form.find("#activate_type").val();
       memberCardReq.discount = $form.find("#discount").val();
       //是否显示积分
       memberCardReq.supply_bonus=0;
       if ($form.find("#supply_bonus").is(':checked')) {
    	   memberCardReq.supply_bonus=1;
	   }
       memberCardReq.bonus_cleared= $form.find("#bonus_cleared").val();
       memberCardReq.bonus_rules= $form.find("#bonus_rules").val();
       memberCardReq.prerogative= $form.find("#prerogative").val();
       //自定义属性栏目
       var custField1 = new Object();
       custField1.name = $form.find("#custfield_name1").val();
       custField1.url = $form.find("#custfield_url1").val();
       memberCardReq.custom_field1=custField1;
       //var custField2 = new Object();
       //custField2.name = $form.find("#custfield_name2").val();
       //custField2.url = $form.find("#custfield_url2").val();
       //memberCardReq.custom_field2 = custField2;
       //自定义连接栏目
       var custCell1 = new Object();
       custCell1.name = $form.find("#custcell_name1").val();
       custCell1.tips = $form.find("#custcell_tips1").val();
       custCell1.url = $form.find("#custcell_url1").val();
       memberCardReq.custom_cell1 = custCell1;
       //积分规则参数
       var bonusRule = new Object();
       bonusRule.init_increase_bonus  = $form.find("#init_increase_bonus").val();
       bonusRule.cost_money_unit  = $form.find("#cost_money_unit").val();
       bonusRule.increase_bonus  = $form.find("#increase_bonus").val();
       bonusRule.max_increase_bonus  = $form.find("#max_increase_bonus").val();
       bonusRule.least_money_to_use_bonus  = $form.find("#least_money_to_use_bonus").val();
       bonusRule.cost_bonus_unit  = $form.find("#cost_bonus_unit").val();
       bonusRule.reduce_money  = $form.find("#reduce_money").val();
       bonusRule.max_reduce_bonus  = $form.find("#max_reduce_bonus").val();
       memberCardReq.bonus_rule = bonusRule;
       //一键激活参数
       var activateFields = new Object();
       //必填项
       var require_array=new Array();  
       $('input[name="require_param"]:checked').each(function(){ 
    	 	//向数组中添加元素
    	   require_array.push($(this).val());  
       });  
       //将数组元素连接起来以构建一个字符串  
       activateFields.require_fields = require_array;
       //选填项
       var common_array=new Array();  
       $('input[name="common_param"]:checked').each(function(){  
    	   common_array.push($(this).val());
       });  
       activateFields.common_fields = common_array;
       //设置一键激活参数
       memberCardReq.activate_fields = activateFields;
       $.ajax({  
          url: $form.attr('action') ,  
          type: 'POST',  
          data: JSON.stringify(memberCardReq),  
          async: false,  
          cache: false,  
          contentType: false,  
          processData: false,  
          success: function (result) {  
    	  	  if(result.result_code=="SUCCESS"){
  	       		  //alert("支付成功|"+JSON.stringify(result.data));
  	       		  $("#createresult").text("上传成功|"+JSON.stringify(result.data));
  	       		  $("#createresult").attr("class","alert-success");
  	       	  }else{
  	       		  $("#createresult").text("错误码："+result.err_code+"，错误原因："+result.err_code_des);
  	       		  $("#createresult").attr("class","alert-danger");
  	       	  }
  	       	  $("#createresult").show();  
          },  
          error: function (result) {  
              alert(result);  
          }  
     });
   });
    /*******************修改卡券**********************/
    $('#updateform').bootstrapValidator({
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
       var memberCardReq = new Object();
       //会员卡基本信息
       memberCardReq.card_id = $form.find("#card_id").val();
       memberCardReq.merchant_num = $form.find("#merchant_num").val();
       memberCardReq.title = $form.find("#title").val();
       memberCardReq.logo_url = $form.find("#logo_url").val();
       memberCardReq.merchant_name = $form.find("#merchant_name").val();
       memberCardReq.notice = $form.find("#notice").val();
       memberCardReq.description = $form.find("#description").val();
       memberCardReq.color = $form.find("#color").val();
       memberCardReq.code_type = $form.find("#code_type").val();
       memberCardReq.service_phone = $form.find("#service_phone").val();
       memberCardReq.can_share=0;
       if ($form.find("#can_share").is(':checked')) {
    	   memberCardReq.can_share=1;
	   }
       memberCardReq.can_give_friend=0;
       if ($form.find("#can_give_friend").is(':checked')) {
    	   memberCardReq.can_give_friend=1;
	   }
       memberCardReq.background_pic_url = $form.find("#background_pic_url").val();
       memberCardReq.activate_type = $form.find("#activate_type").val();
       memberCardReq.discount = $form.find("#discount").val();
       //是否显示积分
       memberCardReq.supply_bonus=0;
       if ($form.find("#supply_bonus").is(':checked')) {
    	   memberCardReq.supply_bonus=1;
	   }
       memberCardReq.bonus_cleared= $form.find("#bonus_cleared").val();
       memberCardReq.bonus_rules= $form.find("#bonus_rules").val();
       memberCardReq.prerogative= $form.find("#prerogative").val();
       //自定义属性栏目
       var custField1 = new Object();
       custField1.name = $form.find("#custfield_name1").val();
       custField1.url = $form.find("#custfield_url1").val();
       memberCardReq.custom_field1=custField1;
       //var custField2 = new Object();
       //custField2.name = $form.find("#custfield_name2").val();
       //custField2.url = $form.find("#custfield_url2").val();
       //memberCardReq.custom_field2 = custField2;
       //自定义连接栏目
       var custCell1 = new Object();
       custCell1.name = $form.find("#custcell_name1").val();
       custCell1.tips = $form.find("#custcell_tips1").val();
       custCell1.url = $form.find("#custcell_url1").val();
       memberCardReq.custom_cell1 = custCell1;
       //积分规则参数
       var bonusRule = new Object();
       bonusRule.init_increase_bonus  = $form.find("#init_increase_bonus").val();
       bonusRule.cost_money_unit  = $form.find("#cost_money_unit").val();
       bonusRule.increase_bonus  = $form.find("#increase_bonus").val();
       bonusRule.max_increase_bonus  = $form.find("#max_increase_bonus").val();
       bonusRule.least_money_to_use_bonus  = $form.find("#least_money_to_use_bonus").val();
       bonusRule.cost_bonus_unit  = $form.find("#cost_bonus_unit").val();
       bonusRule.reduce_money  = $form.find("#reduce_money").val();
       bonusRule.max_reduce_bonus  = $form.find("#max_reduce_bonus").val();
       memberCardReq.bonus_rule = bonusRule;
       
       $.ajax({  
          url: $form.attr('action') ,  
          type: 'POST',  
          data: JSON.stringify(memberCardReq),  
          async: false,  
          cache: false,  
          contentType: false,  
          processData: false,  
          success: function (result) {  
    	  	  if(result.result_code=="SUCCESS"){
  	       		  //alert("支付成功|"+JSON.stringify(result.data));
  	       		  $("#updateresult").text("上传成功|"+JSON.stringify(result.data));
  	       		  $("#updateresult").attr("class","alert-success");
  	       	  }else{
  	       		  $("#updateresult").text("错误码："+result.err_code+"，错误原因："+result.err_code_des);
  	       		  $("#updateresult").attr("class","alert-danger");
  	       	  }
  	       	  $("#updateresult").show();  
          },  
          error: function (result) {  
              alert(result);  
          }  
     });
   });
   /*******************获取卡券二维码**********************/
   $('#qrcodeform').bootstrapValidator({
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
          		$('#qrcode').qrcode(result.data.url);
          		$("#qrcoderesult").text("成功|"+JSON.stringify(result.data));
          		$("#qrcoderesult").attr("class","alert-success");
          	}else{
          		$("#qrcoderesult").text("错误码："+result.err_code+"，错误原因："+result.err_code_des);
          		$("#qrcoderesult").attr("class","alert-danger");
          	}
          	$("#qrcoderesult").show();
       }, 'json');
   });
   /*******************会员查询**********************/
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
          		$("#queryresult").text("成功|"+JSON.stringify(result.data));
          		$("#queryresult").attr("class","alert-success");
          	}else{
          		$("#queryresult").text("错误码："+result.err_code+"，错误原因："+result.err_code_des);
          		$("#queryresult").attr("class","alert-danger");
          	}
          	$("#queryresult").show();
       }, 'json');
   });
   /*******************会员消费**********************/
   $('#consumform').bootstrapValidator({
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
          		$("#consumresult").text("成功|"+JSON.stringify(result.data));
          		$("#consumresult").attr("class","alert-success");
          	}else{
          		$("#consumresult").text("错误码："+result.err_code+"，错误原因："+result.err_code_des);
          		$("#consumresult").attr("class","alert-danger");
          	}
          	$("#consumresult").show();
       }, 'json');
   });
   /*******************会员充值**********************/
   $('#rechargeform').bootstrapValidator({
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
          		$("#rechargeresult").text("成功|"+JSON.stringify(result.data));
          		$("#rechargeresult").attr("class","alert-success");
          	}else{
          		$("#rechargeresult").text("错误码："+result.err_code+"，错误原因："+result.err_code_des);
          		$("#rechargeresult").attr("class","alert-danger");
          	}
          	$("#rechargeresult").show();
       }, 'json');
   });
   
});
</script>
</head>
<body>

<div class="container">
  <ul class="nav nav-tabs">
    <li class="active"><a data-toggle="tab" href="#member_create">添加会员卡</a></li>
    <li><a data-toggle="tab" href="#member_update">修改会员卡</a></li>
    <li><a data-toggle="tab" href="#member_qrcode">会员卡二维码</a></li>
    <li><a data-toggle="tab" href="#member_query">会员查询</a></li>
    <li><a data-toggle="tab" href="#member_recharge">会员充值</a></li>
    <li><a data-toggle="tab" href="#member_consum">会员消费</a></li>
  </ul>
  <div class="tab-content">
    <!-- 创建卡券 -->
    <div id="member_create" class="tab-pane fade in active">
    	<div class="panel panel-default">
			<div class="panel-body">
				<form  id="createform" class="form-horizontal" action="memcard/create">
					<div class="panel panel-primary">
					    <div class="panel-heading">
					        <h3 class="panel-title">卡券基本信息</h3>
					    </div>
					    <div class="panel-body">
					        <div class="form-group">
			                    <label class="col-lg-2 control-label">会员卡标题*</label>
			                    <div class="col-lg-3">
			                        <input type="text" class="form-control" id="title" name="title" value="黄金会员卡" maxlength="10"
			                        placeholder="请输入卡券标题 ，如：1元现金券" data-bv-notempty data-bv-notempty-message="卡券标题"/>
			                    </div>
			                </div>
							<div class="form-group">
			                    <label class="col-lg-2 control-label">商户号*</label>
			                    <div class="col-lg-3">
			                        <input type="text" class="form-control" id="merchant_num" name="merchant_num" value="999910031001993593"
			                        placeholder="请输入商户号，如：999910031001993593" data-bv-notempty data-bv-notempty-message="商户号"/>
			                        <input type="hidden" name="sign" value="1" />
			                    </div>
			                    <label class="col-lg-2 control-label">商户名称*</label>
			                    <div class="col-lg-3">
			                        <input type="text" class="form-control" id="merchant_name" name="merchant_name" value="盈承信息" 
			                        maxlength="12" data-bv-notempty data-bv-notempty-message="商户名称"/>
			                    </div>
			                </div>
			                <div class="form-group">
			                    <label class="col-lg-2 control-label">卡券logo地址*</label>
			                    <div class="col-lg-5">
			                        <input type="text" class="form-control" id="logo_url" name="logo_url" value="http://mmbiz.qpic.cn/mmbiz_jpg/FVibS3ibBAISOneJBtFUMsRqENxGuLm2nvYpySoLnqQnsafbtrgVcYDFSBovVv8a0OiasmF3UoZ6hXrVgEqMZz0Zw/0" 
			                        data-bv-notempty data-bv-notempty-message="卡券logo"/>
			                    </div>
			                </div>
			                <div class="form-group">
			                	<label class="col-lg-2 control-label">卡券背景图地址</label>
			                    <div class="col-lg-5">
			                        <input type="text" class="form-control" id="background_pic_url" name="background_pic_url" value="" />
			                    </div>
			                </div>
			                <div class="form-group">
			                    <label class="col-lg-2 control-label">激活方式*</label>
			                    <div class="col-xs-3">
					            	<select class="form-control" name="activate_type" id="activate_type" 
					            	data-bv-notempty data-bv-notempty-message="激活方式">	
						       			<option value="1">自动激活</option>
						       			<option value="2" selected="selected">一键激活</option>
					             	</select>
				                </div>
				                <label class="col-lg-2 control-label">折扣</label>
			                    <div class="col-lg-3">
			                        <input type="text" class="form-control" id="discount" name="discount" value="0.7" 
			                        placeholder="请输入折扣，例如0.7表示7折" />       
			                    </div>
				            </div>
				            <div class="form-group">
			                    <label class="col-lg-2 control-label">激活参数(必填)</label>
			                    <div class="col-lg-8">
			                        <label><input type="checkbox" id="require_param" name="require_param" 
			                        value="USER_FORM_INFO_FLAG_MOBILE" checked/>手机号&nbsp;&nbsp;</label> 
			                        <label><input type="checkbox" id="require_param" name="require_param" 
			                        value="USER_FORM_INFO_FLAG_NAME" checked/>姓名&nbsp;&nbsp;</label>
			                        <label><input type="checkbox" id="require_param" name="require_param" 
			                        value="USER_FORM_INFO_FLAG_SEX"/>性别&nbsp;&nbsp;</label>
					            	<label><input type="checkbox" id="require_param" name="require_param" 
					            	value="USER_FORM_INFO_FLAG_BIRTHDAY"/>生日&nbsp;&nbsp;</label> 
			                        <label><input type="checkbox" id="require_param" name="require_param" 
			                        value="USER_FORM_INFO_FLAG_IDCARD"/>身份证&nbsp;&nbsp;</label>
					            	<label><input type="checkbox" id="require_param" name="require_param" 
					            	value="USER_FORM_INFO_FLAG_EMAIL"/>邮箱&nbsp;&nbsp;</label>
					            	<label><input type="checkbox" id="require_param" name="require_param" 
					            	value="USER_FORM_INFO_FLAG_LOCATION"/>详细地址&nbsp;&nbsp;</label> 
			                        <label><input type="checkbox" id="require_param" name="require_param" 
			                        value="USER_FORM_INFO_FLAG_EDUCATION_BACKGRO"/>教育背景&nbsp;&nbsp;</label>
					            	<label><input type="checkbox" id="require_param" name="require_param" 
					            	value="USER_FORM_INFO_FLAG_INDUSTRY"/>行业&nbsp;&nbsp;</label>
					            	<label><input type="checkbox" id="require_param" name="require_param" 
					            	value="USER_FORM_INFO_FLAG_INCOME"/>收入&nbsp;&nbsp;</label> 
			                        <label><input type="checkbox" id="require_param" name="require_param" 
			                        value="USER_FORM_INFO_FLAG_HABIT"/>兴趣爱好&nbsp;&nbsp;</label>
					            </div>
			                </div>
			                <div class="form-group">
			                    <label class="col-lg-2 control-label">激活参数(选填)</label>
			                    <div class="col-lg-8">
			                        <label><input type="checkbox" id="common_param" name="common_param" 
			                        value="USER_FORM_INFO_FLAG_MOBILE"/>手机号&nbsp;&nbsp;</label> 
			                        <label><input type="checkbox" id="common_param" name="common_param" 
			                        value="USER_FORM_INFO_FLAG_NAME"/>姓名&nbsp;&nbsp;</label>
			                        <label><input type="checkbox" id="common_param" name="common_param" 
			                        value="USER_FORM_INFO_FLAG_SEX" checked/>性别&nbsp;&nbsp;</label>
					            	<label><input type="checkbox" id="common_param" name="common_param" 
					            	value="USER_FORM_INFO_FLAG_BIRTHDAY" checked/>生日&nbsp;&nbsp;</label> 
			                        <label><input type="checkbox" id="common_param" name="common_param" 
			                        value="USER_FORM_INFO_FLAG_IDCARD"/>身份证&nbsp;&nbsp;</label>
					            	<label><input type="checkbox" id="common_param" name="common_param" 
					            	value="USER_FORM_INFO_FLAG_EMAIL" checked/>邮箱&nbsp;&nbsp;</label>
					            	<label><input type="checkbox" id="common_param" name="common_param" 
					            	value="USER_FORM_INFO_FLAG_LOCATION" checked/>详细地址&nbsp;&nbsp;</label> 
			                        <label><input type="checkbox" id="common_param" name="common_param" 
			                        value="USER_FORM_INFO_FLAG_EDUCATION_BACKGRO"/>教育背景&nbsp;&nbsp;</label>
					            	<label><input type="checkbox" id="common_param" name="common_param" 
					            	value="USER_FORM_INFO_FLAG_INDUSTRY"/>行业&nbsp;&nbsp;</label>
					            	<label><input type="checkbox" id="common_param" name="common_param" 
					            	value="USER_FORM_INFO_FLAG_INCOME"/>收入&nbsp;&nbsp;</label> 
			                        <label><input type="checkbox" id="common_param" name="common_param" 
			                        value="USER_FORM_INFO_FLAG_HABIT"/>兴趣爱好&nbsp;&nbsp;</label>
					            </div>
					        </div>
			                <div class="form-group">
			                    <label class="col-lg-2 control-label">使用提醒*</label>
			                    <div class="col-lg-3">
			                        <input type="text" class="form-control" id="notice" name="notice" value="使用时向服务员出示此券" 
			                        data-bv-notempty data-bv-notempty-message="使用提醒"/>
			                    </div>
			                    <label class="col-lg-2 control-label">使用说明*</label>
			                    <div class="col-lg-3">
			                        <input type="text" class="form-control" id="description" name="description" value="不可与其他优惠同享" 
			                        data-bv-notempty data-bv-notempty-message="使用说明"/>
			                    </div>
			                </div>
			                <div class="form-group">
			                    <label class="col-lg-2 control-label">卡券颜色*</label>
			                    <div class="col-lg-3">
			                    	<select class="form-control" name="color" id="color" 
			                    	data-bv-notempty data-bv-notempty-message="请选择颜色">	
						       			<option value="#55BD47" selected="selected">Color010</option>
						       			<option value="#10AD61">Color020</option>
						       			<option value="#35A4DE">Color030</option>
						       			<option value="#3D78DA">Color040</option>
						       			<option value="#9058CB">Color050</option>
						       			<option value="#DE9C33">Color060</option>
						       			<option value="#EBAC16">Color070</option>
						       			<option value="#F9861F">Color080</option>
						       			<option value="#F08500">Color081</option>
						       			<option value="#E75735">Color090</option>
						       			<option value="#D54036">Color100</option>
					             	</select>
			                    </div>
			                    <label class="col-lg-2 control-label">投放码类型*</label>
			                    <div class="col-lg-3">
			                    	<select class="form-control" name="code_type" id="code_type" 
			                    	data-bv-notempty data-bv-notempty-message="请选择投放码">	
						       			<option value="CODE_TYPE_BARCODE" selected="selected" >一维码</option>
						       			<option value="CODE_TYPE_QRCODE">二维码</option>
						       			<option value="CODE_TYPE_ONLY_BARCODE">一维码无Code</option>
						       			<option value="CODE_TYPE_ONLY_QRCODE">二维码无Code</option>
					             	</select>
			                    </div>
			                </div>
			                <div class="form-group">
			                    <label class="col-lg-2 control-label">投放数量*</label>
			                    <div class="col-lg-3">
			                        <input type="number" class="form-control" id="quantity" name="quantity" value="1000" 
			                        data-bv-notempty data-bv-notempty-message="投放数量"/>       
			                    </div>
			                     <label class="col-lg-2 control-label">客服电话</label>
			                    <div class="col-lg-3">
			                        <input type="text" class="form-control" id="service_phone" name="service_phone" 
			                        placeholder="请输入客服电话，如：0755-2233445"  value="" />       
			                    </div>
			                </div>
			                <div class="form-group">
			                    <label class="col-lg-2 control-label">支持分享</label>
			                    <div class="col-lg-2 checkbox" >
			                        <label><input type="checkbox" id="can_share" 
			                        name="can_share"  value="1" checked/>支持请打勾 </label>  
			                    </div>
			                    <label class="col-lg-1 control-label">支持转赠</label>
			                    <div class="col-lg-2 checkbox">
			                        <label><input type="checkbox"  id="can_give_friend" 
			                        name="can_give_friend" value="1"/>支持请打勾</label>
			                    </div>
			                    <label class="col-lg-1 control-label">显示积分</label>
			                    <div class="col-xs-2 checkbox">
					            	<label><input type="checkbox" id="supply_bonus" 
					            	name="supply_bonus" value="1" checked/>显示请打勾</label>
				                </div>
			                </div>
			                <div class="form-group">
			                    <label class="col-lg-2 control-label">积分清零规则</label>
			                    <div class="col-lg-3">
			                        <input type="text" class="form-control" id="bonus_cleared" name="bonus_cleared" value="每年12月31号清零" />
			                    </div>
			                    <label class="col-lg-2 control-label">积分规则</label>
			                    <div class="col-lg-3">
			                        <input type="text" class="form-control" id="bonus_rules" name="bonus_rules" value="激活即送50积分，每消费10元赠送1积分，每10积分兑换1元" />
			                    </div>
			                </div>
			                <div class="form-group">
			                	<label class="col-lg-2 control-label">会员特权说明*</label>
			                    <div class="col-lg-7">
			                    	<textarea class="form-control" id="prerogative" name="prerogative" rows="3" 
			                    	data-bv-notempty data-bv-notempty-message="会员卡特权说明">1、享受折扣优惠2、消费赠送积分</textarea>
			                    </div>
			                </div>
				            <div class="form-group">
			                    <label class="col-lg-2 control-label">自定义类目名称1</label>
			                    <div class="col-lg-3">
			                        <input type="text" class="form-control" id="custfield_name1" name="custfield_name1" value="余额" />
			                    </div>
			                    <label class="col-lg-2 control-label">跳转地址</label>
			                    <div class="col-lg-3">
			                        <input type="text" class="form-control" id="custfield_url1" name="custfield_url1" value="http://www.gnete.cn/yunpay/member/qrybalance" />
			                    </div>
			                </div>
					    </div>
					</div>
					<div class="panel panel-primary">
					    <div class="panel-heading">
					        <h3 class="panel-title">积分规则</h3>
					    </div>
					    <div class="panel-body" id="bonus_group">
					    	<div  class="form-group">
			                    <label class="col-lg-2 control-label">初始积分</label>
			                    <div class="col-lg-2">
			                        <input type="text" class="form-control" id="init_increase_bonus" name="init_increase_bonus" value="50" />
			                    </div>
			                    <label class="col-lg-1 control-label">每消费</label>
			                    <div class="col-lg-1" style="adding-left:0;padding-right:0;">
			                        <input type="text" class="form-control" id="cost_money_unit" name="cost_money_unit" value="10" />
			                    </div>
			                    <label class="col-lg-1 control-label" style="width:80px;padding-left:0;padding-right:0;">元，增加</label>
			                    <div class="col-lg-1">
			                        <input type="text" class="form-control" id="increase_bonus" name="increase_bonus" value="1" />
			                    </div>
			                    <label class="col-lg-1 control-label" style="width:130px;padding-left:0;padding-right:0;">积分，单笔最多奖励</label>
			                    <div class="col-lg-1" style="adding-left:0;padding-right:0;">
			                        <input type="text" class="form-control" id="max_increase_bonus" name="max_increase_bonus" value="10" />
			                    </div>
			                    <label class="col-lg-1 control-label" style="width:40px;padding-left:0;padding-right:0;">积分</label>
			                </div>
			                <div class="form-group">
			                    <label class="col-lg-2 control-label">满xx元可用</label>
			                    <div class="col-lg-2">
			                        <input type="text" class="form-control" id="least_money_to_use_bonus" name="least_money_to_use_bonus" value="10" />
			                    </div>
			                    <label class="col-lg-1 control-label">每使用</label>
			                    <div class="col-lg-1" style="adding-left:0;padding-right:0;">
			                        <input type="text" class="form-control" id="cost_bonus_unit" name="cost_bonus_unit" value="10" />
			                    </div>
			                    <label class="col-lg-1 control-label" style="width:80px;padding-left:0;padding-right:0;">积分，抵扣</label>
			                    <div class="col-lg-1">
			                        <input type="text" class="form-control" id="reduce_money" name="reduce_money" value="1" />
			                    </div>
			                    <label class="col-lg-1 control-label" style="width:130px;padding-left:0;padding-right:0;">元，单笔最多可使用</label>
			                    <div class="col-lg-1" style="adding-left:0;padding-right:0;">
			                        <input type="text" class="form-control" id="max_reduce_bonus" name="max_reduce_bonus" value="100" />
			                    </div>
			                    <label class="col-lg-1 control-label" style="width:40px;padding-left:0;padding-right:0;">积分</label>
			                </div>
					    </div>
					</div>
					<div class="panel panel-primary">
					    <div class="panel-heading">
					        <h3 class="panel-title">自定义信息</h3>
					    </div>
					    <div class="panel-body">
					    	<div id="custom_group" class="form-group">
			                    <label class="col-lg-2 control-label">自定义会员信息1</label>
			                    <div class="col-lg-2">
			                        <input type="text" class="form-control" id="custcell_name1" name="custcell_name1" value="" />
			                    </div>
			                    <label class="col-lg-1 control-label">提示语</label>
			                    <div class="col-lg-2">
			                        <input type="text" class="form-control" id="custcell_tips1" name="custcell_tips1" value="" />
			                    </div>
			                    <label class="col-lg-1 control-label">跳转url</label>
			                    <div class="col-lg-2">
			                        <input type="text" class="form-control" id="custcell_url1" name="custcell_url1" value="" />
			                    </div>
			                </div>
					    </div>
					</div>
	                <div class="form-group">
	                    <div class="col-lg-9 col-lg-offset-3">
	                        <button type="submit" class="btn btn-success">提交</button>
	                    </div>
	                </div>
	                <div id="createresult" class="alert alert-success" ></div>
			    </form>
			</div>
		</div>
    </div>
    <!-- 修改卡券 -->
    <div id="member_update" class="tab-pane fade">
    	<div class="panel panel-default">
			<div class="panel-body">
				<form  id="updateform" class="form-horizontal" action="memcard/update">
					<div class="panel panel-primary">
					    <div class="panel-heading">
					        <h3 class="panel-title">卡券基本信息</h3>
					    </div>
					    <div class="panel-body">
					        <div class="form-group">
					       	 	<label class="col-lg-2 control-label">cardId*</label>
			                    <div class="col-lg-3">
			                        <input type="text" class="form-control" id="card_id" name="card_id" value="" maxlength="50"
			                        placeholder="请输入创建时返回的card_id" data-bv-notempty data-bv-notempty-message="会员卡id"/>
			                    </div>
			                    <label class="col-lg-2 control-label">会员卡标题*</label>
			                    <div class="col-lg-3">
			                        <input type="text" class="form-control" id="title" name="title" value="黄金会员卡" maxlength="10"
			                        placeholder="请输入卡券标题 ，如：1元现金券" data-bv-notempty data-bv-notempty-message="卡券标题"/>
			                    </div>
			                </div>
							<div class="form-group">
			                    <label class="col-lg-2 control-label">商户号*</label>
			                    <div class="col-lg-3">
			                        <input type="text" class="form-control" id="merchant_num" name="merchant_num" value="999910031001993593"
			                        placeholder="请输入商户号，如：999910031001993593" data-bv-notempty data-bv-notempty-message="商户号"/>
			                        <input type="hidden" name="sign" value="1" />
			                    </div>
			                    <label class="col-lg-2 control-label">商户名称*</label>
			                    <div class="col-lg-3">
			                        <input type="text" class="form-control" id="merchant_name" name="merchant_name" value="盈承信息" 
			                        maxlength="12" data-bv-notempty data-bv-notempty-message="商户名称"/>
			                    </div>
			                </div>
			                <div class="form-group">
			                    <label class="col-lg-2 control-label">卡券logo地址</label>
			                    <div class="col-lg-5">
			                        <input type="text" class="form-control" id="logo_url" name="logo_url" value="http://mmbiz.qpic.cn/mmbiz_jpg/FVibS3ibBAISOneJBtFUMsRqENxGuLm2nvYpySoLnqQnsafbtrgVcYDFSBovVv8a0OiasmF3UoZ6hXrVgEqMZz0Zw/0" 
			                        data-bv-notempty-message="卡券logo"/>
			                    </div>
			                </div>
			                <div class="form-group">
			                	<label class="col-lg-2 control-label">卡券背景图地址</label>
			                    <div class="col-lg-5">
			                        <input type="text" class="form-control" id="background_pic_url" name="background_pic_url" value="" />
			                    </div>
			                </div>
			                <div class="form-group">
			                    <label class="col-lg-2 control-label">激活方式</label>
			                    <div class="col-xs-3">
					            	<select class="form-control" name="activate_type" id="activate_type" 
					            	data-bv-notempty-message="激活方式">
					            		<option value="">--请选择--</option>
						       			<option value="1">自动激活</option>
						       			<option value="2" selected="selected">一键激活</option>
					             	</select>
				                </div>
				                <label class="col-lg-2 control-label">折扣</label>
			                    <div class="col-lg-3">
			                        <input type="text" class="form-control" id="discount" name="discount" value="0.7" 
			                        placeholder="请输入折扣，例如0.7表示7折" />       
			                    </div>
				            </div>
			                <div class="form-group">
			                    <label class="col-lg-2 control-label">使用提醒</label>
			                    <div class="col-lg-3">
			                        <input type="text" class="form-control" id="notice" name="notice" value="使用时向服务员出示此券" 
			                        data-bv-notempty-message="使用提醒"/>
			                    </div>
			                    <label class="col-lg-2 control-label">使用说明*</label>
			                    <div class="col-lg-3">
			                        <input type="text" class="form-control" id="description" name="description" value="不可与其他优惠同享" 
			                        data-bv-notempty data-bv-notempty-message="使用说明"/>
			                    </div>
			                </div>
			                <div class="form-group">
			                    <label class="col-lg-2 control-label">卡券颜色</label>
			                    <div class="col-lg-3">
			                    	<select class="form-control" name="color" id="color" 
			                    	 data-bv-notempty-message="请选择颜色">	
						       			<option value="#55BD47" selected="selected">Color010</option>
						       			<option value="#10AD61">Color020</option>
						       			<option value="#35A4DE">Color030</option>
						       			<option value="#3D78DA">Color040</option>
						       			<option value="#9058CB">Color050</option>
						       			<option value="#DE9C33">Color060</option>
						       			<option value="#EBAC16">Color070</option>
						       			<option value="#F9861F">Color080</option>
						       			<option value="#F08500">Color081</option>
						       			<option value="#E75735">Color090</option>
						       			<option value="#D54036">Color100</option>
					             	</select>
			                    </div>
			                    <label class="col-lg-2 control-label">投放码类型</label>
			                    <div class="col-lg-3">
			                    	<select class="form-control" name="code_type" id="code_type" 
			                    	 data-bv-notempty-message="请选择投放码">	
			                    	 	<option value="">--请选择--</option>
						       			<option value="CODE_TYPE_BARCODE">一维码</option>
						       			<option value="CODE_TYPE_QRCODE">二维码</option>
						       			<option value="CODE_TYPE_ONLY_BARCODE">一维码无Code</option>
						       			<option value="CODE_TYPE_ONLY_QRCODE">二维码无Code</option>
					             	</select>
			                    </div>
			                </div>
			                <div class="form-group">
			                     <label class="col-lg-2 control-label">客服电话</label>
			                    <div class="col-lg-3">
			                        <input type="text" class="form-control" id="service_phone" name="service_phone" 
			                        placeholder="请输入客服电话，如：0755-2233445"  value="" />       
			                    </div>
			                </div>
			                <div class="form-group">
			                    <label class="col-lg-2 control-label">支持分享</label>
			                    <div class="col-lg-2 checkbox" >
			                        <label><input type="checkbox" id="can_share" 
			                        name="can_share"  value="1" checked/>支持请打勾 </label>  
			                    </div>
			                    <label class="col-lg-1 control-label">支持转赠</label>
			                    <div class="col-lg-2 checkbox">
			                        <label><input type="checkbox"  id="can_give_friend" 
			                        name="can_give_friend" value="1"/>支持请打勾</label>
			                    </div>
			                    <label class="col-lg-1 control-label">显示积分</label>
			                    <div class="col-xs-2 checkbox">
					            	<label><input type="checkbox" id="supply_bonus" 
					            	name="supply_bonus" value="1" checked/>显示请打勾</label>
				                </div>
			                </div>
			                <div class="form-group">
			                    <label class="col-lg-2 control-label">积分清零规则</label>
			                    <div class="col-lg-3">
			                        <input type="text" class="form-control" id="bonus_cleared" name="bonus_cleared" value="每年12月31号清零" />
			                    </div>
			                    <label class="col-lg-2 control-label">积分规则</label>
			                    <div class="col-lg-3">
			                        <input type="text" class="form-control" id="bonus_rules" name="bonus_rules" value="激活即送50积分，每消费10元赠送1积分，每10积分兑换1元" />
			                    </div>
			                </div>
			                <div class="form-group">
			                	<label class="col-lg-2 control-label">会员特权说明</label>
			                    <div class="col-lg-7">
			                    	<textarea class="form-control" id="prerogative" name="prerogative" rows="3" 
			                    	 data-bv-notempty-message="会员卡特权说明">1、享受折扣优惠2、消费赠送积分</textarea>
			                    </div>
			                </div>
				            <div class="form-group">
			                    <label class="col-lg-2 control-label">自定义类目名称1</label>
			                    <div class="col-lg-3">
			                        <input type="text" class="form-control" id="custfield_name1" name="custfield_name1" value="" />
			                    </div>
			                    <label class="col-lg-2 control-label">跳转地址</label>
			                    <div class="col-lg-3">
			                        <input type="text" class="form-control" id="custfield_url1" name="custfield_url1" value="" />
			                    </div>
			                </div>
					    </div>
					</div>
					<div class="panel panel-primary">
					    <div class="panel-heading">
					        <h3 class="panel-title">积分规则</h3>
					    </div>
					    <div class="panel-body" id="bonus_group">
					    	<div  class="form-group">
			                    <label class="col-lg-2 control-label">初始积分</label>
			                    <div class="col-lg-2">
			                        <input type="text" class="form-control" id="init_increase_bonus" name="init_increase_bonus" value="50" />
			                    </div>
			                    <label class="col-lg-1 control-label">每消费</label>
			                    <div class="col-lg-1" style="adding-left:0;padding-right:0;">
			                        <input type="text" class="form-control" id="cost_money_unit" name="cost_money_unit" value="10" />
			                    </div>
			                    <label class="col-lg-1 control-label" style="width:80px;padding-left:0;padding-right:0;">元，增加</label>
			                    <div class="col-lg-1">
			                        <input type="text" class="form-control" id="increase_bonus" name="increase_bonus" value="1" />
			                    </div>
			                    <label class="col-lg-1 control-label" style="width:130px;padding-left:0;padding-right:0;">积分，单笔最多奖励</label>
			                    <div class="col-lg-1" style="adding-left:0;padding-right:0;">
			                        <input type="text" class="form-control" id="max_increase_bonus" name="max_increase_bonus" value="10" />
			                    </div>
			                    <label class="col-lg-1 control-label" style="width:40px;padding-left:0;padding-right:0;">积分</label>
			                </div>
			                <div class="form-group">
			                    <label class="col-lg-2 control-label">满xx元可用</label>
			                    <div class="col-lg-2">
			                        <input type="text" class="form-control" id="least_money_to_use_bonus" name="least_money_to_use_bonus" value="10" />
			                    </div>
			                    <label class="col-lg-1 control-label">每使用</label>
			                    <div class="col-lg-1" style="adding-left:0;padding-right:0;">
			                        <input type="text" class="form-control" id="cost_bonus_unit" name="cost_bonus_unit" value="10" />
			                    </div>
			                    <label class="col-lg-1 control-label" style="width:80px;padding-left:0;padding-right:0;">积分，抵扣</label>
			                    <div class="col-lg-1">
			                        <input type="text" class="form-control" id="reduce_money" name="reduce_money" value="1" />
			                    </div>
			                    <label class="col-lg-1 control-label" style="width:130px;padding-left:0;padding-right:0;">元，单笔最多可使用</label>
			                    <div class="col-lg-1" style="adding-left:0;padding-right:0;">
			                        <input type="text" class="form-control" id="max_reduce_bonus" name="max_reduce_bonus" value="100" />
			                    </div>
			                    <label class="col-lg-1 control-label" style="width:40px;padding-left:0;padding-right:0;">积分</label>
			                </div>
					    </div>
					</div>
					<div class="panel panel-primary">
					    <div class="panel-heading">
					        <h3 class="panel-title">自定义信息</h3>
					    </div>
					    <div class="panel-body">
					    	<div id="custom_group" class="form-group">
			                    <label class="col-lg-2 control-label">自定义会员信息1</label>
			                    <div class="col-lg-2">
			                        <input type="text" class="form-control" id="custcell_name1" name="custcell_name1" value="" />
			                    </div>
			                    <label class="col-lg-1 control-label">提示语</label>
			                    <div class="col-lg-2">
			                        <input type="text" class="form-control" id="custcell_tips1" name="custcell_tips1" value="" />
			                    </div>
			                    <label class="col-lg-1 control-label">跳转url</label>
			                    <div class="col-lg-2">
			                        <input type="text" class="form-control" id="custcell_url1" name="custcell_url1" value="" />
			                    </div>
			                </div>
					    </div>
					</div>
	                <div class="form-group">
	                    <div class="col-lg-9 col-lg-offset-3">
	                        <button type="submit" class="btn btn-success">提交</button>
	                    </div>
	                </div>
	                <div id="updateresult" class="alert alert-success" ></div>
			    </form>
			</div>
		</div>
    </div>
  	<!-- 获取卡券二维码 -->
  	<div id="member_qrcode" class="tab-pane fade">
    	<div class="panel panel-default">
			<div class="panel-body">
				<form  id="qrcodeform" class="form-horizontal" action="member/qrcode">
					<div class="form-group">
	                    <label class="col-lg-3 control-label">商户号*</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="merchant_num" name="merchant_num" value="999910031001993593" data-bv-notempty data-bv-notempty-message="商户号"/>
	                        <input type="hidden" name="sign" value="1" />
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-lg-3 control-label">门店号</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="store_num" name="store_num" value="100200081002" />
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-lg-3 control-label">会员卡Id</label>
	                    <div class="col-lg-5">
	                         <input type="text" class="form-control" id="card_id" name="card_id" value="" data-bv-notempty data-bv-notempty-message="卡券id"/>
	                    </div>
	                </div>
			        <div class="form-group">
	                    <div class="col-lg-9 col-lg-offset-3">
	                        <button type="submit" class="btn btn-success">提交</button>
	                    </div>
	                </div>
	                <div class="form-group">
	                	<div id="qrcode"></div>
	                </div>
	                <div id="qrcoderesult" class="alert alert-success" ></div>
			    </form>
			</div>
		</div>
    </div>
     <!-- 会员查询 -->
  	<div id="member_query" class="tab-pane fade">
    	<div class="panel panel-default">
			<div class="panel-body">
				<form  id="queryform" class="form-horizontal" action="pay/test">
					<div class="form-group">
	                    <label class="col-lg-3 control-label">商户号*</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="merchant_num" name="merchant_num" value="999910031001993593" data-bv-notempty data-bv-notempty-message="商户号"/>
	                    	<input type="hidden" class="form-control" id="client_type" name="client_type" value="POS" />
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-lg-3 control-label">接口地址*</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="url" name="url" value="<%=basePath %>member/query" data-bv-notempty data-bv-notempty-message="接口地址"/>
	                    </div>
	                </div>
			       <div class="form-group">
	                    <label class="col-lg-3 control-label">会员卡号*</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="member_card_code" name="member_card_code" value="668353335438" 
	                        data-bv-notempty data-bv-notempty-message="会员卡号"/>
	                       
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
    <!-- 会员卡充值 -->
  	<div id="member_recharge" class="tab-pane fade">
    	<div class="panel panel-default">
			<div class="panel-body">
				<form  id="rechargeform" class="form-horizontal" action="pay/test">
					<div class="form-group">
	                    <label class="col-lg-3 control-label">商户号*</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="merchant_num" name="merchant_num" value="999910031001993593" data-bv-notempty data-bv-notempty-message="商户号"/>
	                        <input type="hidden" class="form-control" id="client_type" name="client_type" value="POS" />
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-lg-3 control-label">接口地址*</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="url" name="url" value="<%=basePath %>member/recharge" data-bv-notempty data-bv-notempty-message="接口地址"/>
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-lg-3 control-label">充值流水号*</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="recharge_order_no" name="recharge_order_no" value="<%=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) %>" data-bv-notempty data-bv-notempty-message="交易流水号"/>                       	
	                    </div>
	                    <div class="col-lg-3">
	                    	<button type="button" id="btnOrderNo" class="btn btn-primary" onclick="getCurrDate(this)">刷新</button>
	                    </div>
	                </div>
			       <div class="form-group">
	                    <label class="col-lg-3 control-label">会员卡号*</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="member_card_code" name="member_card_code" value="668353335438" 
	                        data-bv-notempty data-bv-notempty-message="会员卡号"/>
	                       
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-lg-3 control-label">交易金额(元)*</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="recharge_amt" name="recharge_amt"  value="100" data-bv-notempty data-bv-notempty-message="交易金额"/>
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-lg-3 control-label">订单标题</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="title" name="title" value="测试会员充值"/>
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-lg-3 control-label">终端号</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="terminal_unique_no" name="terminal_unique_no" value="123456"/>
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-lg-3 control-label">收银员号</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="cashier_no" name="cashier_no" value="15889358195"/>
	                    </div>
	                </div>
			        <div class="form-group">
	                    <div class="col-lg-9 col-lg-offset-3">
	                        <button type="submit" class="btn btn-success">提交</button>
	                    </div>
	                </div>
	                <div id="rechargeresult" class="alert alert-success" ></div>
			    </form>
			</div>
		</div>
    </div>
    <!-- 会员卡消费 -->
  	<div id="member_consum" class="tab-pane fade">
    	<div class="panel panel-default">
			<div class="panel-body">
				<form  id="consumform" class="form-horizontal" action="pay/test">
					<div class="form-group">
	                    <label class="col-lg-3 control-label">商户号*</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="merchant_num" name="merchant_num" value="999910031001993593" data-bv-notempty data-bv-notempty-message="商户号"/>
	                    	<input type="hidden" class="form-control" id="client_type" name="client_type" value="POS" />
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-lg-3 control-label">接口地址*</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="url" name="url" value="<%=basePath %>member/consum" data-bv-notempty data-bv-notempty-message="接口地址"/>
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-lg-3 control-label">消费流水号*</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="user_order_no" name="user_order_no" value="<%=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) %>" data-bv-notempty data-bv-notempty-message="消费流水号"/>                       	
	                    </div>
	                    <div class="col-lg-3">
	                    	<button type="button" id="btnOrderNo" class="btn btn-primary" onclick="getCurrDate(this)">刷新</button>
	                    </div>
	                </div>
			       <div class="form-group">
	                    <label class="col-lg-3 control-label">会员卡号*</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="member_card_code" name="member_card_code" value="668353335438" 
	                        data-bv-notempty data-bv-notempty-message="会员卡号"/>
	                       
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-lg-3 control-label">交易金额(元)*</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="tran_amt" name="tran_amt"  value="10" data-bv-notempty data-bv-notempty-message="交易金额"/>
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-lg-3 control-label">积分抵扣</label>
	                    <div class="col-lg-5">
	                        <input type="number" class="form-control" id="use_bonus" name="use_bonus"  value="0" />
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-lg-3 control-label">订单标题</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="body" name="body" value="测试会员消费"/>
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-lg-3 control-label">终端号</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="terminal_unique_no" name="terminal_unique_no" value="123456"/>
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-lg-3 control-label">收银员号</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="cashier_no" name="cashier_no" value="15889358195"/>
	                    </div>
	                </div>
			        <div class="form-group">
	                    <div class="col-lg-9 col-lg-offset-3">
	                        <button type="submit" class="btn btn-success">提交</button>
	                    </div>
	                </div>
	                <div id="consumresult" class="alert alert-success" ></div>
			    </form>
			</div>
		</div>
    </div>
  </div>
</div>
</body>
</html>