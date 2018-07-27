<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.text.*" import="java.util.*" import="com.yunpay.common.utils.*" %>
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
$(document).ready(function() {
	$("#cardform #card_type").change(function(){
		var selectVal = $(this).val();
	    $("#cardform div[id^='TAG']").attr("class","form-group hidden");  
	    if(selectVal=='1' || selectVal=='4' || selectVal=='5'){
	    	$("#cardform #TAG_145").attr("class","form-group");
	    }else{
	    	$("#cardform #TAG_"+selectVal).attr("class","form-group");
	    }
	});
	
	$("#logoresult").hide();
	$("#cardresult").hide();
	$("#qrcoderesult").hide();
	$("#pushresult").hide();
	$("#consumeresult").hide();
	$("#deleteresult").hide();
	$("#updcardresult").hide();
	
	/*******************卡券logo上传**********************/
    $('#logoform').bootstrapValidator({
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
	    var formData = new FormData($("#logoform")[0]);
	    $.ajax({  
	          url: $form.attr('action') ,  
	          type: 'POST',  
	          data: formData,  
	          async: false,  
	          cache: false,  
	          contentType: false,  
	          processData: false,  
	          success: function (result) {  
        	  	if(result.result_code=="SUCCESS"){
	  	       		//alert("支付成功|"+JSON.stringify(result.data));
	  	       		$("#logoresult").text("上传成功|"+JSON.stringify(result.data));
	  	       		$("#logoresult").attr("class","alert-success");
	  	       	}else{
	  	       		$("#logoresult").text("错误码："+result.err_code+"错误原因"+result.err_code_des);
	  	       		$("#logoresult").attr("class","alert-danger");
	  	       	}
	  	       	$("#logoresult").show();  
	          },  
	          error: function (result) {  
	              alert(result);  
	          }  
	     });
	});
    /*******************创建卡券**********************/
    $('#cardform').bootstrapValidator({
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
          		$("#cardresult").text("成功|"+JSON.stringify(result.data));
          		$("#cardresult").attr("class","alert-success");
          	}else{
          		$("#cardresult").text("错误码："+result.err_code+"错误原因"+result.err_code_des);
          		$("#cardresult").attr("class","alert-danger");
          	}
          	$("#cardresult").show();
       }, 'json');
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
          		$("#qrcoderesult").text("错误码："+result.err_code+"错误原因"+result.err_code_des);
          		$("#qrcoderesult").attr("class","alert-danger");
          	}
          	$("#qrcoderesult").show();
       }, 'json');
   });
   /*******************卡券推送**********************/
   $('#pushform').bootstrapValidator({
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
         		$("#pushresult").text("成功|"+JSON.stringify(result.data));
         		$("#pushresult").attr("class","alert-success");
         	}else{
         		$("#pushresult").text("错误码："+result.err_code+"错误原因"+result.err_code_des);
         		$("#pushresult").attr("class","alert-danger");
         	}
         	$("#pushresult").show();
      }, 'json');
  });
   /*******************卡券核销**********************/
   $('#consumeform').bootstrapValidator({
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
          		$("#consumeresult").text("成功|"+JSON.stringify(result.data));
          		$("#consumeresult").attr("class","alert-success");
          	}else{
          		$("#consumeresult").text("错误码："+result.err_code+"错误原因"+result.err_code_des);
          		$("#consumeresult").attr("class","alert-danger");
          	}
          	$("#consumeresult").show();
       }, 'json');
   });
   /*******************卡券删除**********************/
   $('#deleteform').bootstrapValidator({
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
          		$("#deleteresult").text("成功|"+JSON.stringify(result.data));
          		$("#deleteresult").attr("class","alert-success");
          	}else{
          		$("#deleteresult").text("错误码："+result.err_code+"错误原因"+result.err_code_des);
          		$("#deleteresult").attr("class","alert-danger");
          	}
          	$("#deleteresult").show();
       }, 'json');
   });
   /*******************卡券修改**********************/
   $('#updcardform').bootstrapValidator({
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
          		$("#updcardresult").text("成功|"+JSON.stringify(result.data));
          		$("#updcardresult").attr("class","alert-success");
          	}else{
          		$("#updcardresult").text("错误码："+result.err_code+"错误原因"+result.err_code_des);
          		$("#updcardresult").attr("class","alert-danger");
          	}
          	$("#updcardresult").show();
       }, 'json');
   });
});
function imgview(obj,id) {
    //FileReader
    if(window.FileReader){//验证当前的浏览器是否支持图片预览
        var reader=new FileReader();
        var file=obj.files[0];
        var regexImage=/^image\//;//js正则表达式，匹配是否拥有image
        if(regexImage.test(file.type)){
            //设置读取结束的回调函数
            reader.onload=function(data){
            var img=document.getElementById(id);
            img.src=data.target.result;//将结果数据显示到img标签上

            };
            //开始读取上传的文件内容
            reader.readAsDataURL(file);
        }else{
            alert("亲，看清楚是图片预览");
            return;
        }
    }else{
        alert("亲，浏览器该升级了");
        return;
    }
}

function getCardInfo(){
	var cardId = $("#updcardform #card_id").val();
	if(cardId==null || cardId.length==0){
		alert("请输入卡券id");
		return;
	}
	$.ajax({  
    	type : "post",  
        url : "card/query",  
        data : "card_id=" + cardId,  
        success : function(result){  
            if(result.result_code=="SUCCESS"){
        	    $("#updcardresult").text("成功|"+JSON.stringify(result.data));
          		$("#updcardresult").attr("class","alert-success");
          		var cardInfo = result.data;
          		<!--select-->
          		$("#updcardform #card_type").val(cardInfo.card_type);
          		$("#updcardform #card_type_text").val(cardInfo.card_type);
          		$("#updcardform #put_channel").val(cardInfo.put_channel);
          		$("#updcardform div[id^='TAG']").attr("class","form-group hidden");  
        	    if(cardInfo.card_type=='1' || cardInfo.card_type=='4' || cardInfo.card_type=='5'){
        	    	$("#updcardform #TAG_145").attr("class","form-group");
        	    }else{
        	    	$("#updcardform #TAG_"+cardInfo.card_type).attr("class","form-group");
        	    }
          		$("#updcardform #title").val(cardInfo.title);
          		$("#updcardform #merchant_name").val(cardInfo.merchant_name);
          		$("#updcardform #merchant_num").val(cardInfo.merchant_num);
          		$("#updcardform #logo_url").val(cardInfo.logo_url);
          		$("#updcardform #notice").val(cardInfo.notice);
          		$("#updcardform #description").val(cardInfo.description);
          		<!--select-->
          		$("#updcardform #color").val(cardInfo.color);
          		$("#updcardform #code_type").val(cardInfo.code_type);
          		$("#updcardform #quantity").val(cardInfo.quantity);
          		$("#updcardform #begin_timestamp").val(cardInfo.begin_timestamp);
          		$("#updcardform #end_timestamp").val(cardInfo.end_timestamp);
          		$("#updcardform #default_detail").val(cardInfo.default_detail);
          		$("#updcardform #least_cost").val(cardInfo.least_cost);
          		$("#updcardform #reduce_cost").val(cardInfo.reduce_cost);
          		$("#updcardform #discount").val(cardInfo.discount);
          		$("#updcardform #service_phone").val(cardInfo.service_phone);
          		$("#updcardform #get_limit").val(cardInfo.get_limit);
          		$("#updcardform #use_limit").val(cardInfo.use_limit);
          		<!--checkbox-->
          		if(cardInfo.can_share=="1"){
          			$("#updcardform #can_share").attr("checked", true);
          		}else{
          			$("#updcardform #can_share").attr("checked", false);
          		}
          		if(cardInfo.can_give_friend=="1"){
          			$("#updcardform #can_give_friend").attr("checked", true);
          		}else{
          			$("#updcardform #can_give_friend").attr("checked", false);
          		}
        	}
        	else{
        		$("#updcardresult").text("错误码："+result.err_code+"错误原因"+result.err_code_des);
          		$("#updcardresult").attr("class","alert-danger");
        	}
        	$("#updcardresult").show();
         }  
    }); 
}
</script>
</head>
<body>

<div class="container">
  <ul class="nav nav-tabs">
    <li><a data-toggle="tab" href="#upload_cardlog">图片资源上传</a></li>
    <li class="active"><a data-toggle="tab" href="#card_create">添加卡券</a></li>
    <li><a data-toggle="tab" href="#card_upd">卡券修改</a></li>
    <li><a data-toggle="tab" href="#card_qrcode">获取卡券二维码</a></li>
    <li><a data-toggle="tab" href="#card_push">推送至公众号</a></li>
    <li><a data-toggle="tab" href="#card_consume">卡券核销</a></li>
    <li><a data-toggle="tab" href="#card_del">卡券删除</a></li>
  </ul>

  <div class="tab-content">
  	<!-- 卡券logo上传 -->
    <div id="upload_cardlog" class="tab-pane fade">
    	<div class="panel panel-default">
			<div class="panel-body">
				<form  id="logoform" method="post" class="form-horizontal" action="card/uploadimg" enctype="multipart/form-data">
					<div class="form-group">
	                    <label class="col-lg-3 control-label">商户号*</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="merchant_num" name="merchant_num" value="999910031001993593" data-bv-notempty data-bv-notempty-message="商户号"/>
	                        <input type="hidden" name="sign" value="1" />
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-lg-3 control-label">渠道</label>
	                    <div class="col-xs-6">
			            	<select class="form-control" name="put_channel" id="put_channel" data-bv-notempty data-bv-notempty-message="请选择渠道">	
				       			<option value="2" selected="selected" >微信</option>
				       			<option value="1">支付宝</option>
			             	</select>
		                </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-lg-3 control-label">选择图片*</label>
	                    <div class="col-lg-5">
	                        <input type="file" name="file" onchange="imgview(this,'ylimg')" />
							<img alt="" id="ylimg" width="300px" height="300px"/>
	                    </div>
	                </div>
			        <div class="form-group">
	                    <div class="col-lg-9 col-lg-offset-3">
	                        <button type="submit" class="btn btn-success">提交</button>
	                    </div>
	                </div>
	                <div id="logoresult" class="alert alert-success" ></div>
			    </form>
			</div>
		</div>
    </div>
    <!-- 创建卡券 -->
    <div id="card_create" class="tab-pane fade in active">
    	<div class="panel panel-default">
			<div class="panel-body">
				<form  id="cardform" class="form-horizontal" action="card/create">
					<div class="form-group">
	                    <label class="col-lg-2 control-label">卡券渠道</label>
	                    <div class="col-xs-6">
			            	<select class="form-control" name="put_channel" id="put_channel" data-bv-notempty data-bv-notempty-message="卡券渠道">	
				       			<option value="1">支付宝</option>
				       			<option value="2" selected="selected">微信</option>
				       			
			             	</select>
		                </div>
	                </div>
					<div class="form-group">
	                    <label class="col-lg-2 control-label">卡券类型</label>
	                    <div class="col-xs-6">
			            	<select class="form-control" name="card_type" id="card_type" data-bv-notempty data-bv-notempty-message="卡券类型">	
				       			<option value="5" selected="selected" >团购券</option>
				       			<option value="3">现金券</option>
				       			<option value="2">折扣券</option>
				       			<option value="4">兑换券</option>
				       			<option value="1">优惠券</option>
			             	</select>
		                </div>
	                </div>
					<div class="form-group">
	                    <label class="col-lg-2 control-label">卡券标题*</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="title" name="title" value="" maxlength="10"
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
	                    <label class="col-lg-1 control-label">商户名称*</label>
	                    <div class="col-lg-3">
	                        <input type="text" class="form-control" id="merchant_name" name="merchant_name" value="盈承信息" 
	                        maxlength="12"  data-bv-notempty data-bv-notempty-message="商户名称"/>
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
	                    <label class="col-lg-2 control-label">使用提醒*</label>
	                    <div class="col-lg-3">
	                        <input type="text" class="form-control" id="notice" name="notice" value="使用时向服务员出示此券" 
	                        data-bv-notempty data-bv-notempty-message="使用提醒"/>
	                    </div>
	                    <label class="col-lg-1 control-label">使用说明*</label>
	                    <div class="col-lg-3">
	                        <input type="text" class="form-control" id="description" name="description" value="不可与其他优惠同享" 
	                        data-bv-notempty data-bv-notempty-message="使用说明"/>
	                    </div>
	                </div>
	                 <div class="form-group">
	                    <label class="col-lg-2 control-label">卡券颜色*</label>
	                    <div class="col-lg-5">
	                    	<select class="form-control" name="color" id="color" data-bv-notempty data-bv-notempty-message="请选择颜色">	
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
	                </div>
	                <div class="form-group">
	                    <label class="col-lg-2 control-label">投放码类型*</label>
	                    <div class="col-lg-5">
	                    	<select class="form-control" name="code_type" id="code_type" data-bv-notempty data-bv-notempty-message="请选择投放码">	
				       			<option value="CODE_TYPE_BARCODE" selected="selected" >一维码</option>
				       			<option value="CODE_TYPE_QRCODE">二维码</option>
				       			<option value="CODE_TYPE_ONLY_BARCODE">一维码无Code</option>
				       			<option value="CODE_TYPE_ONLY_QRCODE">二维码无Code</option>
			             	</select>
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-lg-2 control-label">投放数量*</label>
	                    <div class="col-lg-5">
	                        <input type="number" class="form-control" id="quantity" name="quantity"  value="1000" data-bv-notempty data-bv-notempty-message="投放数量"/>       
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-lg-2 control-label">有效时间起*</label>
	                    <div class="col-lg-3">
	                        <input type="text" class="form-control" id="begin_timestamp" name="begin_timestamp"  value="<%=new SimpleDateFormat("yyyy-MM-dd").format(new Date()) %>" data-bv-notempty data-bv-notempty-message="有效时间起"/>       
	                    </div>
	                    <label class="col-lg-2 control-label">有效时间止*</label>
	                    <div class="col-lg-3">
	                        <input type="text" class="form-control" id="end_timestamp" name="end_timestamp"  value="<%=new SimpleDateFormat("yyyy-MM-dd").format(DateUtil.getDateAdd(new Date(), "month", 1))%>" data-bv-notempty data-bv-notempty-message="有效时间止"/>       
	                    </div>
	                </div>
	                <!-- 团购券/兑换券/优惠券专属项 -->
	                <div id="TAG_145" class="form-group">
	                    <label class="col-lg-2 control-label">卡券详情*</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="default_detail" name="default_detail"  value="测试券" data-bv-notempty data-bv-notempty-message="卡券详情"/>       
	                    </div>
	                </div>
	                <!-- 现金券专属项 -->
	                <div id="TAG_3" class="form-group hidden">
	                    <label class="col-lg-2 control-label">最低消费额(分)*</label>
	                    <div class="col-lg-3">
	                        <input type="text" class="form-control" id="least_cost" name="least_cost"  value="0.02" data-bv-notempty data-bv-notempty-message="抵用金额"/>       
	                    </div>
	                    <label class="col-lg-2 control-label">减免金额(分)*</label>
	                    <div class="col-lg-3">
	                        <input type="text" class="form-control" id="reduce_cost" name="reduce_cost"  value="0.01" data-bv-notempty data-bv-notempty-message="减免金额"/>       
	                    </div>
	                </div>
	                <!-- 折扣券专属项 -->
	                 <div id="TAG_2" class="form-group hidden">
	                    <label class="col-lg-2 control-label">折扣(0.7表示7折)*</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="discount" name="discount"  value="0.7" data-bv-notempty data-bv-notempty-message="折扣"/>       
	                    </div>
	                </div>
	                
	                <div class="form-group">
	                    <label class="col-lg-2 control-label">客服电话</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="service_phone" name="service_phone" placeholder="请输入客服电话，如：0755-2233445"  value="" />       
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-lg-2 control-label">领取数量限制</label>
	                    <div class="col-lg-5">
	                        <input type="number" class="form-control" id="get_limit" name="get_limit"  value="5" />       
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-lg-2 control-label">支持分享</label>
	                    <div class="col-lg-3 checkbox" >
	                        <label><input type="checkbox" id="can_share" 
	                        name="can_share"  value="1" checked/>支持请打勾 </label>  
	                    </div>
	                    <label class="col-lg-2 control-label">支持转赠</label>
	                    <div class="col-lg-3 checkbox">
	                        <label><input type="checkbox"  id="can_give_friend" 
	                        name="can_give_friend" value="1" checked/>支持请打勾</label>
	                    </div>
	                </div>
			        <div class="form-group">
	                    <div class="col-lg-9 col-lg-offset-3">
	                        <button type="submit" class="btn btn-success">提交</button>
	                    </div>
	                </div>
	                <div id="cardresult" class="alert alert-success" ></div>
			    </form>
			</div>
		</div>
    </div>
    <!-- 修改卡券 -->
    <div id="card_upd" class="tab-pane fade">
    	<div class="panel panel-default">
			<div class="panel-body">
				<form  id="updcardform" class="form-horizontal" action="card/update">
					<div class="form-group">
	                    <label class="col-lg-2 control-label">卡券id*</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="card_id" name="card_id" value="" data-bv-notempty data-bv-notempty-message="卡券id"/>
	                        <input type="hidden" name="card_type" id="card_type" />
	                        <input type="hidden" name="put_channel" id="put_channel" />                         	
	                    </div>
	                    <div class="col-lg-3">
	                    	<button type="button" id="btnCardId" class="btn btn-primary" onclick="getCardInfo()">提取卡券信息</button>
	                    </div>
	                </div>
					<div class="form-group">
	                    <label class="col-lg-2 control-label">卡券类型</label>
	                    <div class="col-xs-5">
			            	<select class="form-control" name="card_type_text" disabled id="card_type_text" data-bv-notempty data-bv-notempty-message="卡券类型">	
				       			<option value="5" selected="selected" >团购券</option>
				       			<option value="3">现金券</option>
				       			<option value="2">折扣券</option>
				       			<option value="4">兑换券</option>
				       			<option value="1">优惠券</option>
			             	</select>
		                </div>
	                </div>
					<div class="form-group">
	                    <label class="col-lg-2 control-label">卡券标题*</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="title" name="title" value="" maxlength="10"
	                        placeholder="请输入卡券标题 ，如：1元现金券" data-bv-notempty data-bv-notempty-message="卡券标题"/>
	                    </div>
	                </div>
					<div class="form-group">
	                    <label class="col-lg-2 control-label">商户号*</label>
	                    <div class="col-lg-3">
	                        <input type="text" class="form-control" readonly id="merchant_num" name="merchant_num" value="999910031001993593"
	                        placeholder="请输入商户号，如：999910031001993593" data-bv-notempty data-bv-notempty-message="商户号"/>
	                        <input type="hidden" name="sign" value="1" />
	                    </div>
	                    <label class="col-lg-1 control-label">商户名称*</label>
	                    <div class="col-lg-3">
	                        <input type="text" class="form-control" readonly id="merchant_name" name="merchant_name" value="盈承信息" 
	                        maxlength="12"  data-bv-notempty data-bv-notempty-message="商户名称"/>
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-lg-2 control-label">卡券logo地址*</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="logo_url" name="logo_url" value="" 
	                        data-bv-notempty data-bv-notempty-message="卡券logo"/>
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-lg-2 control-label">使用提醒*</label>
	                    <div class="col-lg-3">
	                        <input type="text" class="form-control" id="notice" name="notice" value="" 
	                        data-bv-notempty data-bv-notempty-message="使用提醒"/>
	                    </div>
	                    <label class="col-lg-1 control-label">使用说明*</label>
	                    <div class="col-lg-3">
	                        <input type="text" class="form-control" id="description" name="description" value="" 
	                        data-bv-notempty data-bv-notempty-message="使用说明"/>
	                    </div>
	                </div>
	                 <div class="form-group">
	                    <label class="col-lg-2 control-label">卡券颜色*</label>
	                    <div class="col-lg-5">
	                    	<select class="form-control" name="color" id="color" data-bv-notempty data-bv-notempty-message="请选择颜色">
				       			<option value="#55BD47">Color010</option>
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
	                </div>
	                <div class="form-group">
	                    <label class="col-lg-2 control-label">投放码类型*</label>
	                    <div class="col-lg-5">
	                    	<select class="form-control" name="code_type" id="code_type" data-bv-notempty data-bv-notempty-message="请选择投放码">	
				       			<option value="CODE_TYPE_BARCODE">一维码</option>
				       			<option value="CODE_TYPE_QRCODE">二维码</option>
				       			<option value="CODE_TYPE_ONLY_BARCODE">一维码无Code</option>
				       			<option value="CODE_TYPE_ONLY_QRCODE">二维码无Code</option>
			             	</select>
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-lg-2 control-label">投放数量*</label>
	                    <div class="col-lg-5">
	                        <input type="number" class="form-control" readonly id="quantity" name="quantity"  value="" data-bv-notempty data-bv-notempty-message="投放数量"/>       
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-lg-2 control-label">有效时间起*</label>
	                    <div class="col-lg-3">
	                        <input type="text" class="form-control" id="begin_timestamp" name="begin_timestamp"  value="<%=new SimpleDateFormat("yyyy-MM-dd").format(new Date()) %>" data-bv-notempty data-bv-notempty-message="有效时间起"/>       
	                    </div>
	                    <label class="col-lg-2 control-label">有效时间止*</label>
	                    <div class="col-lg-3">
	                        <input type="text" class="form-control" id="end_timestamp" name="end_timestamp"  value="" data-bv-notempty data-bv-notempty-message="有效时间止"/>       
	                    </div>
	                </div>
	                <!-- 团购券/兑换券/优惠券专属项 -->
	                <div id="TAG_145" class="form-group">
	                    <label class="col-lg-2 control-label">卡券详情*</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="default_detail" name="default_detail"  value="" data-bv-notempty data-bv-notempty-message="卡券详情"/>       
	                    </div>
	                </div>
	                <!-- 现金券专属项 -->
	                <div id="TAG_3" class="form-group hidden">
	                    <label class="col-lg-2 control-label">最低消费额(分)*</label>
	                    <div class="col-lg-3">
	                        <input type="text" class="form-control" readonly id="least_cost" name="least_cost"  placeholder="0.02" value="" data-bv-notempty data-bv-notempty-message="抵用金额"/>       
	                    </div>
	                    <label class="col-lg-2 control-label">减免金额(分)*</label>
	                    <div class="col-lg-3">
	                        <input type="text" class="form-control" readonly id="reduce_cost" name="reduce_cost" placeholder="0.01"  value="" data-bv-notempty data-bv-notempty-message="减免金额"/>       
	                    </div>
	                </div>
	                <!-- 折扣券专属项 -->
	                 <div id="TAG_2" class="form-group hidden">
	                    <label class="col-lg-2 control-label">折扣(0.7表示7折)*</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" readonly id="discount" name="discount" placeholder="0.7" value="" data-bv-notempty data-bv-notempty-message="折扣"/>       
	                    </div>
	                </div>
	                
	                <div class="form-group">
	                    <label class="col-lg-2 control-label">客服电话</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="service_phone" name="service_phone" placeholder="请输入客服电话，如：0755-2233445"  value="" />       
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-lg-2 control-label">领取数量限制</label>
	                    <div class="col-lg-5">
	                        <input type="number" class="form-control" id="get_limit" name="get_limit"  value="" />       
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-lg-2 control-label">支持分享</label>
	                    <div class="col-lg-3 checkbox" >
	                        <label><input type="checkbox" id="can_share" 
	                        name="can_share"  value="1" checked/>支持请打勾 </label>  
	                    </div>
	                    <label class="col-lg-2 control-label">支持转赠</label>
	                    <div class="col-lg-3 checkbox">
	                        <label><input type="checkbox"  id="can_give_friend" 
	                        name="can_give_friend" value="1" checked/>支持请打勾</label>
	                    </div>
	                </div>
			        <div class="form-group">
	                    <div class="col-lg-9 col-lg-offset-3">
	                        <button type="submit" class="btn btn-success">提交</button>
	                    </div>
	                </div>
	                <div id="updcardresult" class="alert alert-success" ></div>
			    </form>
			</div>
		</div>
    </div>
  	<!-- 获取卡券二维码 -->
  	<div id="card_qrcode" class="tab-pane fade">
    	<div class="panel panel-default">
			<div class="panel-body">
				<form  id="qrcodeform" class="form-horizontal" action="card/qrcode">
					<div class="form-group">
	                    <label class="col-lg-3 control-label">商户号*</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="merchant_num" name="merchant_num" value="999910031001993593" data-bv-notempty data-bv-notempty-message="商户号"/>
	                        <input type="hidden" name="sign" value="1" />
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-lg-3 control-label">卡券Id</label>
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
    <!-- 卡券推送-->
  	<div id="card_push" class="tab-pane fade">
    	<div class="panel panel-default">
			<div class="panel-body">
				<form  id="pushform" class="form-horizontal" action="card/push">
					<div class="form-group">
	                    <label class="col-lg-3 control-label">商户号*</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="merchant_num" name="merchant_num" value="999910031001993593" data-bv-notempty data-bv-notempty-message="商户号"/>
	                        <input type="hidden" name="sign" value="1" />
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-lg-3 control-label">卡券Id</label>
	                    <div class="col-lg-5">
	                         <input type="text" class="form-control" id="card_id" name="card_id" value="" data-bv-notempty data-bv-notempty-message="卡券id"/>
	                    </div>
	                </div>
			        <div class="form-group">
	                    <div class="col-lg-9 col-lg-offset-3">
	                        <button type="submit" class="btn btn-success">提交</button>
	                    </div>
	                </div>
	                <div id="pushresult" class="alert alert-success" ></div>
			    </form>
			</div>
		</div>
    </div>
  	<!-- 卡券核销-->
  	<div id="card_consume" class="tab-pane fade">
    	<div class="panel panel-default">
			<div class="panel-body">
				<form  id="consumeform" class="form-horizontal" action="card/consume">
					<div class="form-group">
	                    <label class="col-lg-3 control-label">商户号*</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="merchant_num" name="merchant_num" value="999910031001993593" data-bv-notempty data-bv-notempty-message="商户号"/>
	                        <input type="hidden" name="sign" value="1" />
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-lg-3 control-label">卡券核销码</label>
	                    <div class="col-lg-5">
	                         <input type="text" class="form-control" id="card_code" name="card_code" value="" data-bv-notempty data-bv-notempty-message="卡券核销码"/>
	                    </div>
	                </div>
			        <div class="form-group">
	                    <div class="col-lg-9 col-lg-offset-3">
	                        <button type="submit" class="btn btn-success">提交</button>
	                    </div>
	                </div>
	                <div id="consumeresult" class="alert alert-success" ></div>
			    </form>
			</div>
		</div>
    </div>
    <!-- 卡券删除-->
  	<div id="card_del" class="tab-pane fade">
    	<div class="panel panel-default">
			<div class="panel-body">
				<form  id="deleteform" class="form-horizontal" action="card/delete">
					<div class="form-group">
	                    <label class="col-lg-3 control-label">商户号*</label>
	                    <div class="col-lg-5">
	                        <input type="text" class="form-control" id="merchant_num" name="merchant_num" value="999910031001993593" data-bv-notempty data-bv-notempty-message="商户号"/>
	                        <input type="hidden" name="sign" value="1" />
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-lg-3 control-label">卡券id</label>
	                    <div class="col-lg-5">
	                         <input type="text" class="form-control" id="card_id" name="card_id" value="" data-bv-notempty data-bv-notempty-message="卡券id"/>
	                    </div>
	                </div>
			        <div class="form-group">
	                    <div class="col-lg-9 col-lg-offset-3">
	                        <button type="submit" class="btn btn-success">提交</button>
	                    </div>
	                </div>
	                <div id="deleteresult" class="alert alert-success" ></div>
			    </form>
			</div>
		</div>
    </div>
  </div>
</div>
</body>
</html>