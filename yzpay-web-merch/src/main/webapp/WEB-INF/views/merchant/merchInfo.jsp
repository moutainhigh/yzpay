<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../common/taglib/taglib.jsp"%>  
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width,initial-scale=1.0"/>
	<meta name="format-detection" content="telephone=no,email=no,date=no,address=no">
	<title>商户信息</title>
	<script src="${baseURL}/bootstrap/js/jquery-1.11.2.min.js"></script> 
    <link rel="stylesheet" href="${baseURL}/common/css/bootstrap-select.css">
	<script src="${baseURL}/common/js/plugin/jsBridge.js?v=<%=System.currentTimeMillis() %>"></script>
    <script src="${baseURL}/common/js/plugin/jsFunction.js?v=<%=System.currentTimeMillis() %>"></script>
    <script src="${baseURL}/static/js/biz-js/merchInfo.js?v=<%=System.currentTimeMillis() %>"></script>
    <script src="${baseURL}/common/js/common.js?v=<%=System.currentTimeMillis()%> "></script> 
	<link rel="stylesheet" type="text/css" href="${baseURL }/common/css/aui.css" />
	<link rel="stylesheet" type="text/css" href="${baseURL }/common/css/style.css?v=<%=System.currentTimeMillis() %>" />
	<link rel="stylesheet" type="text/css" href="${baseURL }/common/js/plugin/viewer/viewer.min.css" />
	<script src="${baseURL }/common/js/plugin/viewer/viewer-jquery.min.js"></script>
	<style type="text/css">
		i{color: red}
	</style>
</head>
<body>
	<header class="aui-bar aui-bar-nav">
    	<a class="aui-pull-left aui-btn" 
    	<c:if test="${merchUser != null}"> href='${baseURL}/sys/index/index' 	</c:if>
    	<c:if test="${merchUser == null}">  href='javascript:void(0)' onclick='exitWebview()' </c:if>>
        	<span class="aui-iconfont aui-icon-left"></span>首页
    	</a>
	</header>
	<div class="aui-tab si-aui-tab" id="tab">
    	<div class="aui-tab-item"><span class="aui-active">基本信息</span></div>
    	<div class="aui-tab-item"><span>结算信息</span></div>
    	<div class="aui-tab-item"><span>证件信息</span></div>
	</div>
<input type="hidden" id="duplicateLogin" autocomplete="off" value="${duplicateLogin}">
<form  method="post" action="${baseURL}/sys/merchContr/toUpdate?type=2">
	<input type="hidden" value="${merch.merchantNo }" id="merchantNo" name="merchantNo">
   	<input type="hidden" value="${merch.id }" id="merId" name="merId">
   	<input type="hidden" name="tabActive" value="" id="tabActive"/>
   	<input type="hidden" id="appToken" name="appToken" autocomplete="off">
<div class="classify_div">
		<!-- 基本信息 -->
	<div class="si-tad_div" id="basic-info">
		<div class="aui-content aui-margin-b-15">
			<ul class="aui-list aui-form-list register-form">
				<li class="aui-list-item">
            		<div class="aui-list-item-inner">
                		<div class="aui-list-item-label">商户名称<i>*</i></div>
                		<div class="aui-list-item-input" ><input type="text" value="${merch.registerName }" style="color: #ADADAD; margin-left: 25px;"  readonly="readonly" /></div>
            		</div>
        		</li>
        		
        		<li class="aui-list-item">
            		<div class="aui-list-item-inner">
                		<div class="aui-list-item-label">品牌名称<i>*</i></div>
                		<div class="aui-list-item-input"><input type="text" value="${merch.merchantName }"  style="margin-left:25px; color: #ADADAD;" readonly="readonly" /></div>
            		</div>
        		</li>
        		
        		<li class="aui-list-item">
        		<div class="aui-list-item-inner">
            		<div class="aui-list-item-label">省<i>*</i></div>
            		<input type="hidden" id="provValue" value="${merch.prov}">
            		<div class="aui-list-item-input" ><input type="text" id="prov" style="margin-left:25px; color: #ADADAD;" readonly="readonly" /></div>
        		</div>
    			</li>
    			
    			<li class="aui-list-item">
        		<div class="aui-list-item-inner">
            		<div class="aui-list-item-label">市<i>*</i></div>
            		<input type="hidden" id="cityValue" value="${merch.city}" >
            		<div class="aui-list-item-input"><input type="text" id="city" style="margin-left:25px; color: #ADADAD;" readonly="readonly" /></div>
        		</div>
    			</li>
    			
    			<li class="aui-list-item">
        		<div class="aui-list-item-inner">
            		<div class="aui-list-item-label">区<i>*</i></div>
            		<input type="hidden" id="areaValue" value="${merch.area}">
                    <div class="aui-list-item-input" ><input type="text" id="area" style="margin-left:25px; color: #ADADAD;" readonly="readonly" />  </div>
        		</div>
    			</li>
        		
        		<li class="aui-list-item">
            		<div class="aui-list-item-inner">
                		<div class="aui-list-item-label">详细地址<i>*</i></div>
                		<div class="aui-list-item-input">
                			<textarea rows="3" cols="15"  maxlength="30" style="margin-left:25px; color: #ADADAD;"  readonly="readonly" >${merch.address }</textarea>
                		
                		</div>
            		</div>
        		</li>
        		
			</ul>
			<ul class="aui-list aui-form-list register-form">
				<li class="aui-list-item">
            		<div class="aui-list-item-inner">
                		<div class="aui-list-item-label">法人姓名<i>*</i></div>
                		<div class="aui-list-item-input">
                			<input type="text" value="${merch.cardName }" style="color: #ADADAD; margin-left:25px;"  readonly="readonly" /> 
                		</div>
            		</div>
        		</li>
        		
				<li class="aui-list-item">
            		<div class="aui-list-item-inner">
                		<div class="aui-list-item-label">身份证<i>*</i></div>
                		<div class="aui-list-item-input">
                		<input type="text" value="${merch.cardNo }" style="color: #ADADAD; margin-left:25px;"  readonly="readonly" />
                			
                		</div>
            		</div>
        		</li>
        		
        		 <li class="aui-list-item">
                <div class="aui-list-item-inner">
                    <div class="aui-list-item-label">行业<i>*</i></div>
                    <div class="aui-list-item-input">
                    	<input type="hidden" id="industryValue" value="${merch.industry}"/>
                        <select   style="margin-left: 25px; color: #ADADAD;" id="industryTypeId"  disabled="disabled">
    						
    					</select>
                    </div>
                </div>
            	</li>
        	
			</ul>
			<ul class="aui-list aui-form-list register-form">
				<li class="aui-list-item">
            		<div class="aui-list-item-inner ">
                		<div class="aui-list-item-label">电话</div>
                		<div class="aui-list-item-input">
                		<input type="text" value="${merch.tel }" readonly="readonly" style="margin-left:25px; color: #ADADAD;"/>
                			
                		</div>
            		</div>
        		</li>
        		
				<li class="aui-list-item">
            		<div class="aui-list-item-inner ">
                		<div class="aui-list-item-label">邮箱</div>
                		<div class="aui-list-item-input">
                			
                			<input type="text" value="${merch.email }" readonly="readonly" style="margin-left:25px; color: #ADADAD;"/>
                		</div>
            		</div>
        		</li>
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
                		<input type="text" value="${merch.accBank }" style="color: #ADADAD;margin-left: 25px;"  readonly="readonly" />
                			
                		</div>
            		</div>
        		</li>
        		
			
        		<li class="aui-list-item">
        		<div class="aui-list-item-inner">
            		<div class="aui-list-item-label">开户省份<i>*</i></div>
            		<input type="hidden" id="accProvValue" value="${merch.accProv }"/>
            		<div class="aui-list-item-input" > <input type="text" id="accProv" style="margin-left:25px; color: #ADADAD;" readonly="readonly" />	</div>
        		</div>
    		</li>
    		<li class="aui-list-item">
        		<div class="aui-list-item-inner">
            		<div class="aui-list-item-label">开户市<i>*</i></div>
            		<input type="hidden" id="accCityValue" value="${merch.accCity }"/>
            		<div class="aui-list-item-input" ><input type="text" id="accCity" style="margin-left:25px; color: #ADADAD;" readonly="readonly" /> </div>
        		</div>
    		</li>
    		
    		
				<li class="aui-list-item">
            		<div class="aui-list-item-inner">
                		<div class="aui-list-item-label">开户支行<i>*</i></div>  
                		
                		<div class="aui-list-item-input">
                		<input type="text" value="${merch.accSubBank }" style="color: #ADADAD; margin-left: 25px;"  readonly="readonly"/>
                			
                		</div>
            		</div>
        		</li>
        		
        		
				<li class="aui-list-item">
            		<div class="aui-list-item-inner">
                		<div class="aui-list-item-label">开户人姓名<i>*</i></div>
                		<div class="aui-list-item-input">
                		<input type="text" value="${merch.accName }" style="color: #ADADAD; margin-left: 25px;"  readonly="readonly" />
                		
                		</div>
            		</div>
        		</li>
        		
        		
				<li class="aui-list-item">
            		<div class="aui-list-item-inner">
                		<div class="aui-list-item-label">银行账号<i>*</i></div>
                		<div class="aui-list-item-input">
                		<input type="text" value="${merch.accNo }" style="color: #ADADAD; margin-left: 25px;"  readonly="readonly" />
                		
                		
                		</div>
            		</div>
        		</li>
			</ul>
		
		</div>
	</div>

	<!-- 证件信息 -->
	<div class="aui-content-padded si-tad_div" id="paper-info">
		<div class="paper-info-list">
			<div class="paper-info-img" >
				<!-- data-original="${baseURL }/cashierContr/showAttach?id=${merch.attach1}" -->
				<img  src="${baseURL }/cashierContr/showAttach?id=${merch.attach1}" style="opacity:0.5;" />
			</div>
			<div class="paper-info-title">身份证正面<i>*</i></div>
		</div>
		<div class="paper-info-list" >
			<div class="paper-info-img" >
				<img  src="${baseURL }/cashierContr/showAttach?id=${merch.attach2}" style="opacity:0.5;"  />
			</div>
			<div class="paper-info-title">身份证反面<i>*</i></div>
		</div>
		
		<div class="paper-info-list">
			<div class="paper-info-img" >
				<img  src="${baseURL }/cashierContr/showAttach?id=${merch.attach3}" style="opacity:0.5;"  />
			</div>
			<div class="paper-info-title">营业执照<i>*</i></div>
		</div>
		
		<div class="paper-info-list">
			<div class="paper-info-img" >
				<img  src="${baseURL }/cashierContr/showAttach?id=${merch.attach4}" style="opacity:0.5;"   />
			</div>
			<div class="paper-info-title">餐饮许可证</div>
		</div>
		
		<div class="paper-info-list">
			<div class="paper-info-img" >
				<img src="${baseURL }/cashierContr/showAttach?id=${merch.attach5}" style="opacity:0.5;"  />
			</div>
			<div class="paper-info-title">店铺LOGO</div>
		</div>
	</div>
<!-- 	<script >
	$('#paper-info').viewer({
		url: 'data-original'
	});
	</script> -->
	<div style="margin-top: 20px;margin-left:14px; margin-bottom: 15px;" id="success_btn">
		<button type="submit" onclick="getTabIndex()" class="aui-btn aui-btn-success aui-btn-block"  style="background-color:#4768F3; width:95%;border: none;">更改商户资料</button>
	</div>
</div>
</form>	
</body>
<script>
	//表单提交前 获取当前tab页的索引 
	function getTabIndex(){
		var spanArr = $("#tab").find("span");
		for(var i=0; i<$(spanArr).length; i++){
			if($(spanArr[i]).hasClass('aui-active')){
				var html = $(spanArr[i]).html();
				$("#tabActive").val(i+1);
				break;
			}
		}
	}
	$('#tab div').click(function(){ 
		$("#tab").find("span").removeClass('aui-active');
		$(this).find('span').addClass("aui-active").siblings();
		$(".classify_div > .si-tad_div").hide().eq($('#tab div').index(this)).show(); 
	});
	initJsBridge();
</script>
</html>