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
    <script src="${baseURL}/common/js/common.js?v=<%=System.currentTimeMillis()%> "></script>
    <script src="${baseURL}/common/js/plugin/localResizeIMG/lrz.bundle.js?v=<%=System.currentTimeMillis() %>"></script>
    <script src="${baseURL}/static/js/biz-js/merchInfo.js?v=<%=System.currentTimeMillis() %>"></script>
    <link rel="stylesheet" href="${baseURL}/common/css/bootstrap-select.css">
	<link rel="stylesheet" type="text/css" href="${baseURL }/common/css/aui.css" />
	<link rel="stylesheet" type="text/css" href="${baseURL }/common/css/style.css?v=<%=System.currentTimeMillis() %>" />
	<style type="text/css">
		i{color: red}
	</style>
</head>
<body>
	<header class="aui-bar aui-bar-nav">
    	<a class="aui-pull-left aui-btn" href='javascript:history.go(-1);'>
        	<span class="aui-iconfont aui-icon-left"></span>商家信息
    	</a>
	</header>
	<div class="aui-tab si-aui-tab" id="tab">
    	<div class="aui-tab-item"><span class="aui-active">基本信息</span></div>
    	<div class="aui-tab-item"><span>结算信息</span></div>
    	<div class="aui-tab-item"><span>证件信息</span></div>
	</div>
<form  method="post" action="${baseURL}/sys/merchContr/updateAll"  id="merchAllEditForm" enctype="multipart/form-data">
<input type="hidden" value="${merch.merchantNo }" id="merchantNo" name="merchantNo"/>
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
                		
                		<div class="aui-list-item-input"  >
                			<input type="text" id="registerName" name="registerName" autocomplete="off" maxlength="30" placeholder="30位字符以内" 
                			style="margin-left: 25px;" value="${merch.registerName }" onblur="checkInput(this,'registerName_msg')"  
                			onkeyup="$('#registerName_msg').hide() "/>
                		</div>
            		</div>
        		</li>
        		<li class="aui-list-item" id="registerName_msg"  style="color: red;font-size: 17px; display: none;">请输入商户名称</li>
        		
        		<li class="aui-list-item">
            		<div class="aui-list-item-inner">
                		<div class="aui-list-item-label">品牌名称<i>*</i></div>
                		<div class="aui-list-item-input"  >
                			<input type="text" id="merchantName" name="merchantName"   autocomplete="off" maxlength="8" placeholder="8位字符以内"  
                			value="${merch.merchantName }" style="margin-left: 25px;" onblur="checkInput(this,'merchantName_msg')" 
                			onkeyup="$('#merchantName_msg').hide()"/>
                		</div>
            		</div>
        		</li>
        		<li class="aui-list-item" id="merchantName_msg" style="color: red;font-size: 17px; display: none;">请输入品牌名称</li>
        		
        	
        		<li class="aui-list-item"  id="prov_tr">
        		<div class="aui-list-item-inner">
            		<div class="aui-list-item-label">省<i>*</i></div>
            		<input type="hidden" id="provValue" value="${merch.prov}">
            		<div class="aui-list-item-input">
            			<select   style="margin-left: 25px;"  name="prov" id="prov" onchange="getCity(this,'city')">    
    						
        				</select>
            		</div>
        		</div>
    			</li>
    			
    			<li class="aui-list-item" id="city_tr">
        		<div class="aui-list-item-inner">
            		<div class="aui-list-item-label">市<i>*</i></div>
            		<input type="hidden" id="cityValue" value="${merch.city}">
            		<div class="aui-list-item-input">
                        <select  style="margin-left: 25px;"  name="city" id="city" onchange="getArea(this)">
    						
    					</select>
                    </div>
        		</div>
    			</li>
    			
    			<li class="aui-list-item" id="area_tr">
        		<div class="aui-list-item-inner">
            		<div class="aui-list-item-label">区<i>*</i></div>
            		<input type="hidden" id="areaValue" value="${merch.area}">
                    <div class="aui-list-item-input">
                        <select  style="margin-left: 25px;" name="area" id="area" >
    						
    					</select>
                    </div>
        		</div>
    			</li>
    			
        		<li class="aui-list-item">
            		<div class="aui-list-item-inner">
                		<div class="aui-list-item-label">详细地址<i>*</i></div>
                		<div class="aui-list-item-input" >
                			<textarea  id="address" name="address"  style="margin-left: 25px;" maxlength="30" onblur="checkInput(this,'address_msg')"
                			placeholder="30位字符以内" autocomplete="off"  onkeyup="$('#address_msg').hide()"> ${merch.address } </textarea>
                			
                			
                		</div>
            		</div>
        		</li>
        		<li class="aui-list-item"  id="address_msg" style="color: red;font-size: 17px; display: none;">请输入详细地址</li>
        		
        	
			</ul>
			<ul class="aui-list aui-form-list register-form">
				<li class="aui-list-item">
            		<div class="aui-list-item-inner">
                		<div class="aui-list-item-label">法人姓名<i>*</i></div>
                		<div class="aui-list-item-input" >
                			<input type="text" id="cardName" name="cardName"  style="margin-left: 25px;" maxlength="12" 
                			 placeholder="12位字符以内" autocomplete="off" value="${merch.cardName }"  onblur="checkInput(this,'cardName_msg')"   onkeyup="$('#cardName_msg').hide() " />
                		</div>
            		</div>
        		</li>
        		<li class="aui-list-item" id="cardName_msg" style="color: red;font-size: 17px; display: none;">请输入法人姓名</li>
				<li class="aui-list-item">
            		<div class="aui-list-item-inner">
                		<div class="aui-list-item-label">法人身份证<i>*</i></div>
                		<div class="aui-list-item-input" >
                			<input type="text" id="cardNo" name="cardNo"  maxlength="18" style="margin-left: 25px;" autocomplete="off" 
                			placeholder="18位居民身份证号码" value="${merch.cardNo }"  onblur="checkCardNo(this,'cardNo_msg')"  onkeyup="checkCardNoAndRemoveMsg(this)"/>
                		</div>   
            		</div>
            		<script >
            			/**
            				自定义事件 
            			
            			$('#cardNo').bind("cardNoBlur",function(){ 13509250677  
            				checkCardNo(this,'cardNo_msg') ;
            			});
            			**/
            		
            		</script>
        		</li>
        		<li class="aui-list-item"  id="cardNo_msg" style="color: red;font-size: 17px; display: none;">请输入18位身份证</li>
        			<li class="aui-list-item">
                <div class="aui-list-item-inner">
                    <div class="aui-list-item-label">行业<i>*</i></div>
                    <div class="aui-list-item-input">
                    	<input type="hidden" id="industryValue" value="${merch.industry}"/>
                        <select  style="margin-left: 25px;" name="industryTypeId" id="industryTypeId">
    						
    					</select>
                    </div>
                </div>
            	</li>
			</ul>
			<ul class="aui-list aui-form-list register-form">
        		
				<li class="aui-list-item">
            		<div class="aui-list-item-inner ">
                		<div class="aui-list-item-label">座机号码</div>  
                		<div class="aui-list-item-input" >
                			<input type="text" name="tel"  class="ipt input-lg" id="tel"   placeholder="区号-7至8位固定座机号" autocomplete="off" maxlength="13" style="margin-left: 25px;" value="${merch.tel }"  	
                			onblur="checkTel2(this,'tel_msg')"  onkeyup="$('#tel_msg').hide()"/>
                		</div>
            		</div>
        		</li>
        		<li class="aui-list-item"  id="tel_msg" style="color: red;font-size: 17px; display: none;">格式:4位区号-7至8位固定座机号</li>
				<li class="aui-list-item">
            		<div class="aui-list-item-inner ">
                		<div class="aui-list-item-label">邮箱</div>
                		<div class="aui-list-item-input" >
                			<input type="text" name="email" maxlength="30" style="margin-left: 25px;" autocomplete="off" placeholder="30位以内,后缀名为com或cn" class="ipt input-lg" 
                			
                			value="${merch.email }" id="email"  onblur="isEmail2(this,'email_msg') " onkeyup="$('#email_msg').hide()"/>
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
                		<div class="aui-list-item-input" >
                			<input type="text" name="accBank"  maxlength="10" id="accBank" style="margin-left: 25px;" 
                			
                			placeholder="10位字符以内" autocomplete="off" value="${merch.accBank }"  onblur="checkInput(this,'accBank_msg')" onkeyup="$('#accBank_msg').hide()" />
                		</div>
            		</div>
        		</li>
        		<li class="aui-list-item" id="accBank_msg" style="color: red;font-size: 17px; display: none;">请输入开户银行</li>
				
        	<li class="aui-list-item" id="acc_prov_tr">
        		<div class="aui-list-item-inner">
            		<div class="aui-list-item-label">开户省份<i>*</i></div>
            		<input type="hidden" id="accProvValue" value="${merch.accProv}">
            		<div class="aui-list-item-input">
            			<select style="margin-left: 25px;" name="accProv" id="accProv" onchange="getAccCity(this,'accCity')">
    						
    					</select>
            		</div>
        		</div>
    		</li>
    		<li class="aui-list-item" id="acc_city_tr">
        		<div class="aui-list-item-inner">
            		<div class="aui-list-item-label">开户市<i>*</i></div>
            		<input type="hidden" id="accCityValue" value="${merch.accCity}">
            		<div class="aui-list-item-input">
            			<select  style="margin-left: 25px;" name="accCity" id="accCity">
            				
            			</select>
            		</div>
        		</div>
    		</li>
    			<script>
            		/**
            		 * 显示该商户的省市区数据到页面(重写merchInfo.js文件中的showRegion方法)
            		 */
            		function showRegion(){
            			var provValue = $("#provValue").val();
            			var cityValue = $("#cityValue").val();
            			var areaValue = $("#areaValue").val();
            			var accProvValue = $("#accProvValue").val();
            			var accCityValue = $("#accCityValue").val();
            			var cacheData = localStorage.getItem("cacheAddress");
            			// 不存在该缓存,则从数据库中查询数据,然后再添加缓存
            			if(cacheData == null){
            				$.ajax({
            				    url:allUrl+'sys/formContr/getAllProv',
            				    type:'POST',
            				    async:false,  
            				    timeout:5000,   
            				    dataType:'json',   
            				    beforeSend:function(xhr){},
            				    success:function(data,textStatus,jqXHR){
            				    	for(var i=0; i<data.address.length; i++){
            				    		var id = data.address[i].id;
            				    		var name = data.address[i].name;
            				    		var type = data.address[i].type;
            				    		// type == 0 获取的是省份数据
            				    		if(type == 0){
            				    			$("#prov").append("<option value='"+id+"'>"+name+"</option>"); 
            				    			$("#accProv").append("<option value='"+id+"'>"+name+"</option>"); 
            				    		}
            				    	}
            				    	
            				    	 // 缓存所有的行政地区数据到客户端
            				    	var str = JSON.stringify(data);
            				    	localStorage.setItem("cacheAddress",str);
            				    },
            				    error:function(xhr,textStatus){
            				    }
            				});
            			}
            			// 获取缓存数据
            			else{
            				var jsonData = JSON.parse(cacheData);
            				for(var i=0; i<jsonData.address.length; i++){
            					var id = jsonData.address[i].id;
    				    		var name = jsonData.address[i].name;
    				    		var type = jsonData.address[i].type;
    				    		// type == 0 获取的是省份数据
    				    		if(type == 0){
    				    			$("#prov").append("<option value='"+id+"'>"+name+"</option>"); 
    				    			$("#accProv").append("<option value='"+id+"'>"+name+"</option>"); 
    				    		}
            				}
            			}
            			$("#prov option[value='"+provValue+"']").attr("selected",true);
            			getCity($('#prov'),'city');
				    	$("#city option[value='"+cityValue+"']").attr("selected",true);
				    	getArea($('#city'));
				    	$("#area option[value='"+areaValue+"']").attr("selected",true);
				    	
				    	$("#accProv option[value='"+accProvValue+"']").attr("selected",true);
				    	getAccCity($('#accProv'),'accCity');
				    	$("#accCity option[value='"+accCityValue+"']").attr("selected",true);
            		}
            		showRegion();
            		</script>
    		
			<li class="aui-list-item">
            		<div class="aui-list-item-inner">
                		<div class="aui-list-item-label">开户支行<i>*</i></div>
                		<div class="aui-list-item-input" >
                			<input type="text" name="accSubBank" maxlength="30" placeholder="30位字符以内" style="margin-left: 25px;" 
                			value="${merch.accSubBank }"  autocomplete="off" id="accSubBank" onblur="checkInput(this,'accSubBank_msg')" onkeyup="$('#accSubBank_msg').hide()" />
                		</div>
            		</div>
        		</li>
        		<li class="aui-list-item"  id="accSubBank_msg" style="color: red;font-size: 17px; display: none;">请输入开户支行</li>
        		
				<li class="aui-list-item">
            		<div class="aui-list-item-inner">
                		<div class="aui-list-item-label">开户人姓名<i>*</i></div>
                		<div class="aui-list-item-input" >
                			<input type="text" name="accName" id="accName"  style="margin-left: 25px;" maxlength="12" placeholder="12位字符以内" value="${merch.accName }"  
                			
                			autocomplete="off"  onblur="checkInput(this,'accName_msg')"  onkeyup="$('#accName_msg').hide()"/>
                		</div>
            		</div>
        		</li>
        		<li class="aui-list-item"  id="accName_msg" style="color: red;font-size: 17px; display: none;">请输入开户人姓名</li>
        		
				<li class="aui-list-item">
            		<div class="aui-list-item-inner">
                		<div class="aui-list-item-label">银行账号<i>*</i></div>
                		<div class="aui-list-item-input" >
                			<input type="tel" name="accNo" id="accNo" style="margin-left: 25px;" oncontextmenu="return false;" autocomplete="off" 
                				maxlength="23" value="${merch.accNo }" placeholder="16-19位银行卡号"
                				onkeyup="this.value = this.value.replace(/\s/g,'').replace(/[^\d]/g,'').replace(/(\d{4})(?=\d)/g,'$1 ');  $('#accNo_msg').hide(); $('#btn_submit').css('background-color','#4768F3');" 
                				onblur="checkAccountNumber2(this,'accNo_msg')" />
                		
                		</div>   
            		</div>
        		</li>
        		<li class="aui-list-item"  id="accNo_msg" style="color: red;font-size: 17px; display: none;">请输入银行账号,16-19位数字</li>
			</ul>
			
		</div>
	</div>

	<!-- 证件信息 -->
	<div class="aui-content-padded si-tad_div" id="paper-info">
		<div class="paper-info-list" >
			<div class="paper-info-img" onclick="openCamera('hiddenfile1','base64Input1','showImg1')">
				<c:if test="${merch.attach1 == 0}">
					<input type="hidden" id="showImg1_input" value="noImg"/>
				</c:if>
				<img  src="${baseURL }/cashierContr/showAttach?id=${merch.attach1}" id="showImg1"  />
			</div>
			<input id="hiddenfile1" name="files" type="file" accept="image/*" class="hidden" onchange="takePhoto(this.files,'showImg1','base64Input1')"/> 
    		<!-- 存放app端上传的文件,并以base64格式保存 -->
    		<input id="base64Input1" name="base64Input" type="text"  class="hidden" style="display: none;"/>
			<div class="paper-info-title">身份证正面<i>*</i></div>
		</div>
		<div class="paper-info-list" >
			<div class="paper-info-img" onclick="openCamera('hiddenfile2','base64Input2','showImg2')">
				<c:if test="${merch.attach2 == 0}">
					<input type="hidden" id="showImg2_input" value="noImg"/>
				</c:if>
				
				<img src="${baseURL }/cashierContr/showAttach?id=${merch.attach2}" id="showImg2" />
			</div>
			<input id="hiddenfile2" name="files" type="file" accept="image/*" class="hidden" onchange="takePhoto(this.files,'showImg2','base64Input2')" />
    		<input id="base64Input2" name="base64Input" type="text"  class="hidden" style="display: none;"/>
			<div class="paper-info-title">身份证反面<i>*</i></div>
		</div>
		
		<div class="paper-info-list">
			<div class="paper-info-img" onclick="openCamera('hiddenfile3','base64Input3','showImg3')">
				<c:if test="${merch.attach3 == 0}">
					<input type="hidden" id="showImg3_input" value="noImg"/>
				</c:if>
				<img src="${baseURL }/cashierContr/showAttach?id=${merch.attach3}" id="showImg3" />
			</div>
			<input id="hiddenfile3" name="files" type="file" accept="image/*" class="hidden" onchange="takePhoto(this.files,'showImg3','base64Input3')" />
    		<input id="base64Input3" name="base64Input" type="text"   class="hidden" style="display: none;"/>
			<div class="paper-info-title">营业执照<i>*</i></div>
		</div>
		
		<div class="paper-info-list">
			<div class="paper-info-img" onclick="openCamera('hiddenfile4','base64Input4','showImg4')">
				<img src="${baseURL }/cashierContr/showAttach?id=${merch.attach4}" id="showImg4"  />
			</div>
			<input id="hiddenfile4" name="files" type="file" accept="image/*" class="hidden" onchange="takePhoto(this.files,'showImg4','base64Input4')" />
    		<input id="base64Input4" name="base64Input" type="text"  class="hidden" style="display: none;"/>
			<div class="paper-info-title">餐饮许可证</div>
		</div>
		
		<div class="paper-info-list">
			<div class="paper-info-img" onclick="openCamera('hiddenfile5','base64Input5','showImg5')">
				<img src="${baseURL }/cashierContr/showAttach?id=${merch.attach5}" id="showImg5" />
			</div>
			<input id="hiddenfile5" name="files" type="file" accept="image/*" class="hidden" onchange="takePhoto(this.files,'showImg5','base64Input5')" />
    		<input id="base64Input5" name="base64Input" type="text"  class="hidden" style="display: none;"/>
			<div class="paper-info-title">店铺LOGO</div>
		</div>
	</div>    
	<div style="margin-left:14px; margin-bottom: 15px;">    
		<button type="button" id="btn_submit" class="aui-btn aui-btn-success aui-btn-block" 
		onclick="$('#alertDiv1').show()" style="background-color:#4768F3; width:95%;border: none;">保存</button>
	</div>
	
	<div class="pop-success" id="alertDiv1">
		<div class="pop-success-content">
			<input type="hidden" id="msgset" value="">
			<div class="pop-content" style="padding-top:50px;">
				<div class="">确定要提交商户资料吗？</div>
			</div>
			<div class="pop-footer">
				<button id="KeepOn" onclick="submitForm();">确定</button>
				<button id="Return-index" onclick="$('#alertDiv1').hide();return false;">取消</button>
			</div>
		</div>
	</div>
	
	<div class="pop-success" id="alertDiv2">
		<div class="pop-success-content">
			<div class="pop-content" style="height:120px;padding-top:20px;">
				<div class="" id="msgTitle"></div>
			</div>
			<div class="pop-one-btn">
                <button type="button" class="aui-btn aui-btn-success aui-btn-block" id="edit-finish" onclick="$('#alertDiv2').hide();return false;">确定</button>
            </div>
		</div>
	</div>
	
	<div class="pop-success" id="alertDiv3">
		<div class="pop-success-content">
			<div class="pop-content" style="padding-top:50px;">
				<div class="">正在上传商户资料...</div>
			</div>
			<div class="pop-footer">
                <button id="uploadBtn" style="color: #666;border-right: 1px solid #ddd;"  onclick="return false;">
                  <img src='${baseURL}/common/css/images/ajax-loader.gif' style="margin:0 auto;">
                </button>
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