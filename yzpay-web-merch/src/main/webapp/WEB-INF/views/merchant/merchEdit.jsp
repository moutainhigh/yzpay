<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../common/taglib/taglib.jsp"%>  
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width,initial-scale=1.0"/>
	<meta name="format-detection" content="telephone=no,email=no,date=no,address=no">
	<title>更改商户信息</title>
	<script src="${baseURL}/bootstrap/js/jquery-1.11.2.min.js"></script> 
	<script src="${baseURL}/common/js/plugin/jsBridge.js?v=<%=System.currentTimeMillis() %>"></script>
    <script src="${baseURL}/common/js/plugin/jsFunction.js?v=<%=System.currentTimeMillis() %>"></script>
    <script src="${baseURL}/static/js/biz-js/merchInfo.js?v=<%=System.currentTimeMillis() %>"></script>
    <script src="${baseURL}/common/js/common.js?v=<%=System.currentTimeMillis()%> "></script>
     <link rel="stylesheet" href="${baseURL}/common/css/bootstrap-select.css">
	<link rel="stylesheet" type="text/css" href="${baseURL }/common/css/aui.css" />
	<link rel="stylesheet" type="text/css" href="${baseURL }/common/css/style.css?v=<%=System.currentTimeMillis() %>" />
	<style type="text/css">
		i{color: red}
	</style>
</head>
<body>
	<header class="aui-bar aui-bar-nav">
    	<a class="aui-pull-left aui-btn" href='javascript:history.go(-1)'	>
        	<span class="aui-iconfont aui-icon-left"></span>商家信息
    	</a>
    	
    
    	
	</header>
	<div class="aui-tab si-aui-tab" id="tab">
    	<div class="aui-tab-item"><span class="aui-active">基本信息</span></div>
    	<div class="aui-tab-item"><span>结算信息</span></div>
    	<div class="aui-tab-item"><span>证件信息</span></div>
	</div>
	
<form  method="post" action="${baseURL}/sys/merchContr/update" enctype="multipart/form-data">
<input type="hidden" value="${merch.merchantNo }" id="merchantNo" name="merchantNo">
   	<input type="hidden" value="${merch.id }" id="merId" name="merId"/>
   	<input type="hidden" value="${tabActive }" id="tabActive"/>
   	<input type="hidden" id="duplicateLogin" autocomplete="off" value="${duplicateLogin}">
   	<input type="hidden" id="appToken" name="appToken" autocomplete="off">

<div class="classify_div">
		<!-- 基本信息 -->
	<div class="si-tad_div" id="basic-info">
		<div class="aui-content aui-margin-b-15">
			<ul class="aui-list aui-form-list register-form">
				<li class="aui-list-item">
            		<div class="aui-list-item-inner">
                		<div class="aui-list-item-label">商户名称<i>*</i></div>
                		
                		<div class="aui-list-item-input" >
                			<input type="text" value="${merch.registerName }"  style="color: #ADADAD; margin-left: 25px;"  readonly="readonly"/>
                		</div>
            		</div>
        		</li>
        		
        		
        		<li class="aui-list-item">
            		<div class="aui-list-item-inner">
                		<div class="aui-list-item-label">品牌名称<i>*</i></div>
                		<div class="aui-list-item-input" >
                			<input type="text" id="merchantName" name="merchantName"  style="margin-left: 25px;" maxlength="8"  placeholder="8位字符以内" autocomplete="off" value="${merch.merchantName }"
                			   onblur="checkInput(this,'merchantName_msg')" onkeyup="$('#merchantName_msg').hide()"/>
                		</div>
            		</div>
        		</li>
        		<li class="aui-list-item" id="merchantName_msg" style="color: red;font-size: 17px; display: none;">请输入品牌名称</li>
        		
        	
        		<li class="aui-list-item">
        		<div class="aui-list-item-inner">
            		<div class="aui-list-item-label">省<i>*</i></div>
            		<input type="hidden" id="provValue" value="${merch.prov}">
            		<div class="aui-list-item-input"><input type="text" id="prov" style="margin-left:25px; color: #ADADAD;" readonly="readonly" /> </div>
        		</div>
    			</li>
    			
    			<li class="aui-list-item">
        		<div class="aui-list-item-inner">
            		<div class="aui-list-item-label">市<i>*</i></div>
            		<input type="hidden" id="cityValue" value="${merch.city}">
            		<div class="aui-list-item-input"><input type="text" id="city" style="margin-left:25px; color: #ADADAD;" readonly="readonly" /></div>
        		</div>
    			</li>
    			
    			<li class="aui-list-item">
        		<div class="aui-list-item-inner">
            		<div class="aui-list-item-label">区<i>*</i></div>
            		<input type="hidden" id="areaValue" value="${merch.area}">
                    <div class="aui-list-item-input"><input type="text" id="area" style="margin-left:25px; color: #ADADAD;" readonly="readonly" /></div>
        		</div>
    			</li>
    			
      
        		<li class="aui-list-item">
            		<div class="aui-list-item-inner">
                		<div class="aui-list-item-label">详细地址<i>*</i></div>
                		<div class="aui-list-item-input">
                			 <textarea rows="3" cols="15" placeholder="30位字符以内"  id="address" name="address"  maxlength="30"  autocomplete="off" oninput="checkInput(this,'address_msg')" 
                			 style="margin-left:25px;"  onblur="checkInput(this,'address_msg')" onkeyup="$('#address_msg').hide()" >${merch.address }</textarea>
                		</div>
            		</div>
        		</li>
        		<li class="aui-list-item"  id="address_msg" style="color: red;font-size: 17px; display: none;">请输入详细地址</li>
        		
        	
			</ul>
			<ul class="aui-list aui-form-list register-form">
				<li class="aui-list-item">
            		<div class="aui-list-item-inner">
                		<div class="aui-list-item-label">法人姓名<i>*</i></div>
                		<div class="aui-list-item-input">
                			<input type="text" value="${merch.cardName }"  style="color: #ADADAD; margin-left: 25px;"  readonly="readonly" />
                		</div>
            		</div>
        		</li>
        		
				<li class="aui-list-item">
            		<div class="aui-list-item-inner">
                		<div class="aui-list-item-label">身份证<i>*</i></div>
                		<div class="aui-list-item-input">
                			<input type="text"   value="${merch.cardNo }"  style="color: #ADADAD; margin-left: 25px;"  readonly="readonly"/>
                		</div>
            		</div>
        		</li>
        		
        		<li class="aui-list-item">
                <div class="aui-list-item-inner">
                    <div class="aui-list-item-label">行业<i>*</i></div>
                    <div class="aui-list-item-input">
                        <input type="hidden" id="industryValue" value="${merch.industry}"/>
                        <select  style="margin-left: 25px; color: #ADADAD; "  disabled="disabled" name="industryTypeId" id="industryTypeId" >
    						
    					</select>
                    </div>
                </div>
            	</li>
        		
			</ul>
			<ul class="aui-list aui-form-list register-form">
				
				<li class="aui-list-item">
            		<div class="aui-list-item-inner ">
                		<div class="aui-list-item-label">座机号码</div>
                		<div class="aui-list-item-input">
                			<input type="text" name="tel"  class="ipt input-lg"  placeholder="区号-7至8位固定座机号"  style=" margin-left: 25px;" id="tel" maxlength="13" value="${merch.tel }" autocomplete="off"   
                			onblur="checkTel2(this,'tel_msg')"  onkeyup="$('#tel_msg').hide()"
                			/>
                		</div>
            		</div>
        		</li>
        		<li class="aui-list-item"  id="tel_msg" style="color: red;font-size: 17px; display: none;">格式:4位区号-7至8位固定座机号</li>
				<li class="aui-list-item">
            		<div class="aui-list-item-inner ">
                		<div class="aui-list-item-label">邮箱</div>
                		<div class="aui-list-item-input">
                			<input type="text" name="email" maxlength="30" style=" margin-left: 25px;" autocomplete="off"  placeholder="30位以内,后缀名为com或cn" class="ipt input-lg" value="${merch.email }" 
                			
                			id="email" oninput="isEmail(this,'email_msg')" onblur="isEmail2(this,'email_msg')" onkeyup="$('#email_msg').hide()"/>
                		</div>
            		</div>
            		
        		</li>
        		<li class="aui-list-item" id="email_msg" style="color: red;font-size: 17px; display: none;">请输入邮箱,后缀名为com或cn</li>
        		
        	
			</ul>
		</div>
	</div>

	<!-- 结算信息 -->
	<div class="si-tad_div" id="balance-info">
		<div class="aui-content aui-margin-b-15">
			<ul class="aui-list aui-form-list register-form">
				<li class="aui-list-item">
            		<div class="aui-list-item-inner">
                		<div class="aui-list-item-label">开户银行<i>*</i></div>
                		<div class="aui-list-item-input">
                			<input type="text"  value="${merch.accBank }" style="color: #ADADAD; margin-left: 25px;"  readonly="readonly" />
                		</div>
            		</div>
        		</li>
        		
				<li class="aui-list-item">
        		<div class="aui-list-item-inner">
            		<div class="aui-list-item-label">开户省份<i>*</i></div>
            		<input type="hidden" id="accProvValue" value="${merch.accProv }"/>
            		<div class="aui-list-item-input"><input type="text" id="accProv" style="margin-left:25px; color: #ADADAD;" readonly="readonly" /> </div>
        		</div>
    		</li>
    		<li class="aui-list-item">
        		<div class="aui-list-item-inner">
            		<div class="aui-list-item-label">开户市<i>*</i></div>
            		<input type="hidden" id="accCityValue" value="${merch.accCity }"/>
            		<div class="aui-list-item-input"><input type="text" id="accCity" style="margin-left:25px; color: #ADADAD;" readonly="readonly" /></div>
        		</div>
    		</li>
				<li class="aui-list-item">
            		<div class="aui-list-item-inner">
                		<div class="aui-list-item-label">开户支行<i>*</i></div>
                		<div class="aui-list-item-input">
                			<input type="text" value="${merch.accSubBank }"  style="color: #ADADAD; margin-left: 25px;"  readonly="readonly"/>
                		</div>
            		</div>
        		</li>
        		
        		
				<li class="aui-list-item">
            		<div class="aui-list-item-inner">
                		<div class="aui-list-item-label">开户人姓名<i>*</i></div>
                		<div class="aui-list-item-input">
                			<input type="text" value="${merch.accName }"  style="color: #ADADAD; margin-left: 25px;"  readonly="readonly" />
                		</div>
            		</div>
        		</li>
        		
        		
				<li class="aui-list-item">
            		<div class="aui-list-item-inner">
                		<div class="aui-list-item-label">银行账号<i>*</i></div>
                		<div class="aui-list-item-input">
                			<input type="tel"  value="${merch.accNo }" style="color: #ADADAD; margin-left: 25px;"  readonly="readonly" />
                		
                		</div>
            		</div>
        		</li>
        		
			</ul>
			<div class="si-aui-list-exp">注：若要修改银行账户信息，请联系客服进行更改！客服电话：400-6297958</div>
		</div>
	</div>

	<!-- 证件信息 -->
	<div class="aui-content-padded si-tad_div" id="paper-info">
		<div class="paper-info-list" >
			<div class="paper-info-img" >
				<img  src="${baseURL }/cashierContr/showAttach?id=${merch.attach1}" style="opacity:0.5;"/>
			</div>
			<div class="paper-info-title">身份证正面<i>*</i></div>
		</div>
		
		<div class="paper-info-list">
			<div class="paper-info-img" >
				<img src="${baseURL }/cashierContr/showAttach?id=${merch.attach2}"  style="opacity:0.5;"/>
			</div>
			<div class="paper-info-title">身份证反面<i>*</i></div>
		</div>
		
		<div class="paper-info-list">
			<div class="paper-info-img" >
				<img src="${baseURL }/cashierContr/showAttach?id=${merch.attach3}" style="opacity:0.5;" />
			</div>
			<div class="paper-info-title">营业执照<i>*</i></div>
		</div>
		
		<div class="paper-info-list">
			<div class="paper-info-img" >
				<img src="${baseURL }/cashierContr/showAttach?id=${merch.attach4}" style="opacity:0.5;"/>
			</div>
			<div class="paper-info-title">餐饮许可证<i>*</i></div>
		</div>
		
		<div class="paper-info-list">
			<div class="paper-info-img" >
				<img src="${baseURL }/cashierContr/showAttach?id=${merch.attach5}" style="opacity:0.5;"/>
			</div>
			<div class="paper-info-title">店铺LOGO<i>*</i></div>
		</div>
	</div>
	<div style="margin-left:14px; margin-bottom: 15px;">
		<button type="button" id="btn_submit" class="aui-btn aui-btn-success aui-btn-block" onclick="$('#alertDiv1').show();" style="background-color:#4768F3; width:95%;border: none;">保存</button>
	</div>
	
	<div class="pop-success" id="alertDiv1">
		<div class="pop-success-content">
			<input type="hidden" id="msgset" value="">
			<div class="pop-content" style="padding-top:50px;">
				<div class="">确定要提交商户资料吗？</div>
			</div>
			<div class="pop-footer">
				<button id="KeepOn" onclick="checkMerchForm();">确定</button>
				<button id="Return-index" onclick="$('#alertDiv1').hide();return false;">取消</button>
			</div>
		</div>
	</div>
</div>
</form>	
</body>

<script>
	$('#tab div').click(function(){ 
		$("#tab").find("span").removeClass('aui-active');
		$(this).find('span').addClass("aui-active").siblings();
		$(".classify_div > .si-tad_div").hide().eq($('#tab div').index(this)).show(); 
	});
	
    // 获取上一个页面中当前活动的tab页的tab 索引 
	var tabIndex = $("#tabActive").val();
    var spanArr = $("#tab").find("span");
    if(tabIndex == '1'){ // 上一个页面点击的是基本信息页面
    	$(spanArr[0]).addClass("aui-active");
    	$(spanArr[1]).removeClass("aui-active");
    	$(spanArr[2]).removeClass("aui-active");
    	$("#basic-info").show();
    	$("#balance-info").hide();
    	$("#paper-info").hide();
    }
    else if(tabIndex == '2'){  // 上一个页面点击的是结算信息页面
    	$(spanArr[1]).addClass("aui-active");
    	$(spanArr[0]).removeClass("aui-active");
    	$(spanArr[2]).removeClass("aui-active");
    	$("#balance-info").show();
    	$("#basic-info").hide();
    	$("#paper-info").hide();
    }
    else if(tabIndex == '3'){  // 上一个页面点击的是证件信息页面 
    	$(spanArr[2]).addClass("aui-active");
    	$(spanArr[1]).removeClass("aui-active");
    	$(spanArr[0]).removeClass("aui-active");
    	$("#paper-info").show();
    	$("#basic-info").hide();
    	$("#balance-info").hide();
    }
    else{
    	$(spanArr[0]).addClass("aui-active");
    	$(spanArr[1]).removeClass("aui-active");
    	$(spanArr[2]).removeClass("aui-active");
    	$("#basic-info").show();
    	$("#balance-info").hide();
    	$("#paper-info").hide();
    }
    initJsBridge();
	
</script>
</html>