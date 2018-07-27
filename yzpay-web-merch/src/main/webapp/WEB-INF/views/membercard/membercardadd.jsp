<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../../common/taglib/taglib.jsp"%>
<!DOCTYPE HTML>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width,initial-scale=1.0"/>
	<meta name="format-detection" content="telephone=no,email=no,date=no,address=no">
	<title>设置会员卡</title>
	<link rel="stylesheet" type="text/css" href="${baseURL}/common/css/new/aui.css" />
	<link rel="stylesheet" type="text/css" href="${baseURL}/common/css/new/style.css?v=<%=System.currentTimeMillis() %>" />
	<script src="${baseURL}/common/js/plugin/jquery-2.1.4.min.js?v=<%=System.currentTimeMillis() %>"></script> 
	<script src="${baseURL}/common/js/new/api.js"></script>
	<script src="${baseURL}/common/js/plugin/jsFunction.js?v=<%=System.currentTimeMillis() %>"></script>
	<script src="${baseURL}/static/js/biz-js/membercardadd.js?v=<%=System.currentTimeMillis() %>"></script>
	<script src="${baseURL}/common/js/common.js?v=<%=System.currentTimeMillis()%> "></script>
	<script src="${baseURL}/common/js/plugin/exif.js?v=<%=System.currentTimeMillis() %>"></script>
	<!-- <style type="text/css">
	.opdiv{
		width: 100%;
		height: 9.09%;
	}
	.inw{
		width: 5rem;
	}
	</style> -->
</head>
<body>
	<header class="aui-bar aui-bar-nav">
    	<a class="aui-pull-left aui-btn" onclick="location.href='${baseURL }/sys/membercard/show'">
        	<span class="aui-iconfont aui-icon-left"></span>会员管理
    	</a>
    	<div class="aui-title">设置会员卡</div>
	</header>
	<div class="aui-content aui-margin-b-15">
	<form  class="form-horizontal"  role="form" id="membercard"  method="post" enctype="multipart/form-data" action="${baseURL}/sys/membercard/add">
		<input type="hidden"  id="skuQuantity" name="skuQuantity" value="99999" >
		<ul class="aui-list aui-form-list register-form">
			<li class="aui-list-item">
        		<div class="aui-list-item-inner">
            		<div class="aui-list-item-label" style="width: 7rem;">会员卡logo<i>*</i></div>
            		<div class="aui-list-item-input" style="margin-left: 1rem;">
            			<img class="select-card-logo" id="showImg"  alt="" src="${baseURL}/common/images/cardlogo/logo.png?v=<%=System.currentTimeMillis() %>"  onclick="$('#logo').click();removeInfo(this);$('#tishi').remove();"  >
            			<div style="color: #666666;" id="tishi">上传图片大小必须在8M以内</div>
            			<input type="file" id="logo" name="logo" accept="image/*" value="localLogo" placeholder="" onchange="takePhoto(this.files,'showImg')" 
            			hidden="hedden" >
            			<input type="hidden" id="forlogo" value="localLogo">
            			<input type="hidden" id="baselogo" name="baselogo" value="">
            		</div>
        		</div>
    		</li>
			<li class="aui-list-item">
        		<div class="aui-list-item-inner aui-list-item-arrow">
            		<div class="aui-list-item-label" style="width: 7rem;">会员卡颜色<i>*</i></div>
            		<div class="aui-list-item-input" >
            			<div style="width: 80%;height: 1.5rem; color: #666666;margin-left: 0.8rem;" onclick="$('.color-pop').show();removeInfo(this);" id="showDiv" >请选择</div>
            			<input type="hidden" name="color" id="color" value="">
            		</div>
        		</div>
    		</li>
    		<li class="aui-list-item">
        		<div class="aui-list-item-inner">
            		<div class="aui-list-item-label" style="width: 7rem;">会员卡标题<i>*</i></div>
            		<div class="aui-list-item-input" style="margin-left: 1rem;">
            			<input type="text" id="title" name="title" value="" placeholder="2-9个汉字" maxlength="9" onblur="check(this,1)" onkeydown="removeInfo(this)" >
            		</div>
        		</div>
    		</li>
    		
    		<!-- <li class="aui-list-item">
        		<div class="aui-list-item-inner">
            		<div class="aui-list-item-label" style="width: 7rem;">投放数量<i>*</i></div>
            		<div class="aui-list-item-input" style="margin-left: 1rem;">
            			<input type="tel"  id="skuQuantity" name="skuQuantity" value="99999" placeholder="会员卡数量为1-99999张" onkeyup="lengthCtl(this,5);$(this).val($(this).val().replace(/[^\d]/g,''))" onblur="check(this,1)" onkeydown="removeInfo(this)"  >
            		</div>
        		</div>
    		</li> -->
    		<li class="aui-list-item">
        		<div class="aui-list-item-inner">
            		<div class="aui-list-item-label" style="width: 7rem;">使用说明<i>*</i></div>
            		<div class="aui-list-item-input" style="margin-left: 1rem;">
            			<!-- <input type="text"id="description" name="description" value="" placeholder="2-15个字符" onkeyup="lengthCtl(this,15)" onblur="check(this,1)" onkeydown="removeInfo(this)" > -->
            			<textarea rows="5" cols="20" id="description" name="description"   placeholder="2-100个字符,不能输入特殊字符,首尾空白字符无效" maxlength="100" 
						 onkeydown="removeInfo(this)" onblur="check(this,1)"></textarea>
            		</div>
        		</div>
    		<!-- </li>
        		<div class="aui-list-item-inner">
            		<div class="aui-list-item-label" style="width: 7rem;">制卡渠道<i>*</i></div>
            		<div class="aui-list-item-input select-makeCard" style="margin-left: 1rem;">
            			<input type="checkbox" class="aui-checkbox" checked id="wechat" name="wechat" value="1"><label for="wechat">微信</label>
            			<input type="checkbox" class="aui-checkbox" id="alipay" name="alipay" value="1" style="margin-left: 1rem;"><label for="alipay">支付宝</label>
            		</div>
        		</div>
    		</li>  -->
		</ul>
		<p id="message" style="margin-top: 10px;height: 15px;color: #fd4142;margin-left: 12px;font-size:16px; " ></p>
		<div class="aui-content-padded" style="margin-top:20px;">
			<button type="button" class="aui-btn aui-btn-success aui-btn-block" id="addVipcard-btn" onclick="saveForm('membercard')">保存</button>
		</div>
		</form>
	</div>
	<div class="pop-success" id="recultAlert">
		<div class="pop-success-content">
			<div class="pop-content" style="height: 7rem;padding-top: 1rem;">
				<div><img src="${baseURL}/common/images/new/ic_success_01.png" alt=""></div>
				<div class="">保存成功！</div>
			</div>
			<div class="pop-one-btn" style="margin-top: -1rem;">
                <button type="button" class="aui-btn aui-btn-success aui-btn-block" id="edit-finish" onclick="location.href='${baseURL }/sys/membercard/show'" >返回</button>
            </div>
		</div>
	</div>
	<div class="color-pop">
        <div class="color-pop-content">
            <div style="background-color:#55BD47; " onclick="selectColor('#55BD47')"></div>
            <div style="background-color: #35A4DE;" onclick="selectColor('#35A4DE')"></div>
            <div style="background-color: #3D78DA;" onclick="selectColor('#3D78DA')"></div>
            <div style="background-color: #9058CB;" onclick="selectColor('#9058CB')"></div>
            <div style="background-color: #DE9C33;" onclick="selectColor('#DE9C33')"></div>
            <div style="background-color: #EBAC16;" onclick="selectColor('#EBAC16')"></div>
            <div style="background-color: #F9861F;" onclick="selectColor('#F9861F')"></div>
            <div style="background-color: #F08500;" onclick="selectColor('#F08500')"></div>
            <div style="background-color: #E75735;" onclick="selectColor('#E75735')"></div>
            <div style="background-color: #D54036;" onclick="selectColor('#D54036')"></div>
        </div>
   	</div>
   	<div class="pop-ajax" id="timeover">
		<div class="pop-ajax-content" >
			<img src='${baseURL}/common/css/images/ajax-loader.gif' alt="正在加载...">
		</div> 
	</div>
</body>
</html>