<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../common/taglib/taglib.jsp"%>  
<!DOCTYPE html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width,initial-scale=1.0"/>
	<meta name="format-detection" content="telephone=no,email=no,date=no,address=no">
	<title>完善商户资料</title>
	<script src="${baseURL}/bootstrap/js/jquery-1.11.2.min.js"></script> 
    <link rel="stylesheet" href="${baseURL}/common/css/bootstrap-select.css">
	<link rel="stylesheet" type="text/css" href="${baseURL }/common/css/aui.css" />
	<link rel="stylesheet" type="text/css" href="${baseURL }/common/css/style.css?v=<%=System.currentTimeMillis() %>" />
	<script src="${baseURL}/common/js/plugin/jsBridge.js?v=<%=System.currentTimeMillis() %>"></script>
    <script src="${baseURL}/common/js/plugin/jsFunction.js?v=<%=System.currentTimeMillis() %>"></script>
    <script src="${baseURL}/common/js/plugin/localResizeIMG/lrz.bundle.js?v=<%=System.currentTimeMillis() %>"></script>
	<script src="${baseURL}/static/js/biz-js/merchForm.js?v=<%=System.currentTimeMillis() %>"></script>
	<script src="${baseURL}/common/js/common.js?v=<%=System.currentTimeMillis()%> "></script>
	<link rel="stylesheet" type="text/css" href="${baseURL }/common/js/plugin/viewer/viewer.min.css" />
	<script src="${baseURL }/common/js/plugin/viewer/viewer-jquery.min.js"></script>
	
	<!-- 百度地图
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=134db1b9cf1f1f2b4427210932b34dcb"></script>     -->
	<style type="text/css">
	i{color: red}
	/*加载弹窗*/
	.pop-ajax{
		position: fixed;
		top: 70%;
		left: 50%;
		background-color: rgba(0,0,0,.0);
		z-index: 100;
		display: none;
	}
	</style>
</head>
<body>
	<header class="aui-bar aui-bar-nav">
    	<a class="aui-pull-left aui-btn" 
    		<c:if test="${merchUser != null and not empty merchUser.merchantNo}" >  href='${baseURL}/sys/index/index' 	</c:if> 
    		<c:if test="${merchUser != null and empty merchUser.merchantNo}" >  href='${baseURL}/sys/index/indexundo' </c:if>>
        	<span class="aui-iconfont aui-icon-left"></span>首页
    	</a>
    	<div class="aui-title" id="headerDiv">基本信息</div>
	</header>
	<form action="${baseURL}/sys/formContr/save" method="post">
	<!-- 接受app端登录的用户id -->
	<input type="hidden" name="merchUserId" value="${merchUserId}"/>
	<input type="hidden" id="userIp" value="${userIp}"/>
	<input type="hidden" id="duplicateLogin" autocomplete="off" value="${duplicateLogin}">
	<input type="hidden" id="userIp" value="${userIp }">
	  
	 <!-- 基本信息 -->
	<div id="table_1">
	<div class="aui-content aui-margin-b-15" id="basicInfo">
		<ul class="aui-list aui-form-list register-form">
            <li class="aui-list-item">
                <div class="aui-list-item-inner">
                    <div class="aui-list-item-label">商户名称<i>*</i></div>
                    <div class="aui-list-item-input">
                        <input type="text" id="registerName" name="registerName" maxlength="30" placeholder="30位字符以内" autocomplete="off" 
                        onblur="checkInput(this,'registerName_msg')"  style="margin-left: 25px;" onkeyup="$('#registerName_msg').hide()">    
                        
                        <!--  oninput="checkInput(this,'registerName_msg')" -->
                        
                    </div>
                </div>
            </li>
            <li class="aui-list-item" id="registerName_msg"  style="color: red;font-size: 17px; display: none;">请输入商户名称</li>
			<li class="aui-list-item">
        		<div class="aui-list-item-inner">
            		<div class="aui-list-item-label">品牌名称<i>*</i></div>
            		<div class="aui-list-item-input">
            			<input type="text" id="merchantName" name="merchantName" maxlength="8" placeholder="8位字符以内" autocomplete="off" 
            			onblur="checkInput(this,'merchantName_msg')" style="margin-left: 25px;" onkeyup="$('#merchantName_msg').hide()">
            			
            			<!-- oninput="checkInput(this,'merchantName_msg')"  -->
            		</div>
        		</div>
    		</li>
    		 <li class="aui-list-item" id="merchantName_msg"  style="color: red;font-size: 17px; display: none;">请输入品牌名称</li>
    		<li class="aui-list-item">
        		<div class="aui-list-item-inner">
            		<div class="aui-list-item-label">省<i>*</i></div>
            		<div class="aui-list-item-input">
            			<select  name="prov" id="prov" onchange="getCity(this,'city')" style="margin-left: 25px;">
    						
        				</select>
            		</div>
        		</div>
    		</li>
    		<li class="aui-list-item">
        		<div class="aui-list-item-inner">
            		<div class="aui-list-item-label">市<i>*</i></div>
            		<div class="aui-list-item-input">
                        <select  name="city" id="city" onchange="getArea(this);checkInput(this)" style="margin-left: 25px;">
    					
    					</select>
                    </div>
        		</div>
    		</li>
    		<li class="aui-list-item">
        		<div class="aui-list-item-inner">
            		<div class="aui-list-item-label">区<i>*</i></div>
                    <div class="aui-list-item-input">
                        <select  name="area" id="area" style="margin-left: 25px;">
    					
    					</select>
                    </div>
        		</div>
    		</li>
    		
    		
    		<li class="aui-list-item">
        		<div class="aui-list-item-inner">
            		<div class="aui-list-item-label">详细地址<i>*</i></div>
            		<div class="aui-list-item-input">
            			<textarea id="address" name="address" maxlength="30" placeholder="30位字符以内" autocomplete="off" 
            			onblur="checkInput(this,'address_msg')" style="margin-left: 25px;" onkeyup="$('#address_msg').hide()"></textarea>
            			<!-- <a href="javascript:void(0)" onclick="initBaiduMap();">定位</a>
            			
            			存放百度地图
            			<div style="position: absolute; z-index: 99999; display: none; width: 260px; height: 280px; margin-top: -270px;" id="mapContainer" ></div> 
            			 -->
            		</div>
        		</div>
    		</li>
    		 <li class="aui-list-item" id="address_msg"  style="color: red;font-size: 17px; display: none;">请输入详细地址</li>
    		<li class="aui-list-item">
        		<div class="aui-list-item-inner">
            		<div class="aui-list-item-label">法人姓名<i>*</i></div>
            		<div class="aui-list-item-input">
            			<input type="text" id="cardName" name="cardName" value="${merchUser.userName }"  autocomplete="off" maxlength="12" 
            			onblur="checkInput(this,'cardName_msg')" placeholder="12位字符以内" style="margin-left: 25px;" onkeyup="$('#cardName_msg').hide()" >  <!--  oninput="checkInput(this,'cardName_msg')" -->
            			  
            		</div>
        		</div>
    		</li>
    		<li class="aui-list-item" id="cardName_msg"  style="color: red;font-size: 17px; display: none;">请输入法人姓名</li>
            <li class="aui-list-item">
                <div class="aui-list-item-inner">
                    <div class="aui-list-item-label">法人身份证<i>*</i></div>  
                    <div class="aui-list-item-input">
                        <input type="text" id="cardNo" name="cardNo"  placeholder="18位居民身份证号码" maxlength="18" 
                        onkeyup="checkCardNoAndRemoveMsg(this)"  onblur="checkCardNo(this,'cardNo_msg')" 
                        autocomplete="off"   style="margin-left: 25px;">
                    </div>
                </div>
            </li>
            <li class="aui-list-item"  id="cardNo_msg" style="color: red;font-size: 17px; display: none;">请输入18位身份证</li>
            
                  <li class="aui-list-item">
                <div class="aui-list-item-inner">
                    <div class="aui-list-item-label">行业<i>*</i></div>
                    <div class="aui-list-item-input">
                        <select   name="industryTypeId" id="industryTypeId" style="margin-left: 25px;">
    						
    					</select>
                    </div>
                </div>
            </li>
       
            <li class="aui-list-item">
                <div class="aui-list-item-inner">
                    <div class="aui-list-item-label"><i></i>座机号码</div>
                    <div class="aui-list-item-input">
                        <input type="text" name="tel"  id="tel" maxlength="13" placeholder="区号-7至8位固定座机号"  autocomplete="off"  onkeyup="$('#tel_msg').hide()" onblur="checkTel2(this,'tel_msg')" style="margin-left: 25px;">
                        
                     
                    </div>
                </div>
            </li>
             <li class="aui-list-item"  id="tel_msg" style="color: red;font-size: 17px; display: none;">格式:区号-7至8位固定座机号</li>
            <li class="aui-list-item">
                <div class="aui-list-item-inner">
                    <div class="aui-list-item-label"><i></i>常用邮箱</div>
                    <div class="aui-list-item-input">
                        <input type="text" name="email" maxlength="30"  placeholder="30位以内,后缀名为com或cn" id="email" oninput="isEmail(this,'email_msg')"   
                        autocomplete="off" onblur="isEmail2(this,'email_msg')" style="margin-left: 25px;" onkeyup="$('#email_msg').hide()">
                     
                    </div>
                </div>
            </li>
            <li class="aui-list-item" id="email_msg" style="color: red;font-size: 17px; display: none;">请输入邮箱,后缀名为com或cn</li>
       
		</ul>
		<div class="bb-submit-div">
			<div class="aui-row-padded">
				<div class="aui-col-xs-6">

					<div class="aui-btn aui-btn-warning aui-btn-block" 
						<c:if test="${merchUser != null and not empty merchUser.merchantNo}"> 	onclick="window.location.href='${baseURL }/sys/index/index'" 	</c:if> 
    					<c:if test="${merchUser != null and empty merchUser.merchantNo}" >  	onclick="window.location.href='${baseURL }/sys/index/indexundo'" </c:if> >返回</div>	
				</div>
				<div class="aui-col-xs-6">
					<div class="aui-btn aui-btn-success aui-btn-block"  id="btn_1"  onclick="checkTable1(this)">下一步</div>
				</div>
			</div>
		</div>
	</div>
	</div>
	
	<!-- 银行账户信息 -->
	<div id="table_2" style="display: none;">
	<div class="aui-content aui-margin-b-15" id="accountInfo">
		<ul class="aui-list aui-form-list register-form">
			<li class="aui-list-item">
        		<div class="aui-list-item-inner">
            		<div class="aui-list-item-label">开户银行<i>*</i></div>
            		<div class="aui-list-item-input">
            			<input type="text" id="accBank" name="accBank" maxlength="10" autocomplete="off" placeholder="10位字符以内"  
            			onblur="checkInput(this,'accBank_msg')" onkeyup="$('#accBank_msg').hide()"
            			 style="margin-left: 25px;">  <!-- oninput="checkInput(this,'accBank_msg')" -->
            		</div>
        		</div>
    		</li>
    		 <li class="aui-list-item" id="accBank_msg" style="color: red;font-size: 17px; display: none;">请输入开户银行</li>
    		<li class="aui-list-item">
        		<div class="aui-list-item-inner aui-list-item-arrow">
            		<div class="aui-list-item-label">所在省份<i>*</i></div>
            		<div class="aui-list-item-input">
            			<select name="accProv" id="accProv" onchange="getAccCity(this,'accCity')" style="margin-left: 25px;"  >
    						
    					</select>
            		</div>
        		</div>
    		</li>
    		<li class="aui-list-item">
        		<div class="aui-list-item-inner aui-list-item-arrow">
            		<div class="aui-list-item-label">市<i>*</i></div>
            		<div class="aui-list-item-input">
            			<select name="accCity" id="accCity" style="margin-left: 25px;">
            		
            			</select>
            		</div>
        		</div>
    		</li>
    		<li class="aui-list-item">
        		<div class="aui-list-item-inner">
            		<div class="aui-list-item-label">开户支行<i>*</i></div>
            		<div class="aui-list-item-input">
            			<input type="text" name="accSubBank" id="accSubBank" maxlength="30" placeholder="30位字符以内"
            			 autocomplete="off"  onblur="checkInput(this,'accSubBank_msg')"  onkeyup="$('#accSubBank_msg').hide()"  style="margin-left: 25px;"><!-- oninput="checkInput(this,'accSubBank_msg')" -->
            		</div>
        		</div>
    		</li>
    		 <li class="aui-list-item" id="accSubBank_msg" style="color: red;font-size: 17px; display: none;">请输入开户银行</li>
    		<li class="aui-list-item">
        		<div class="aui-list-item-inner">
            		<div class="aui-list-item-label">开户人姓名<i>*</i></div>
            		<div class="aui-list-item-input">
            			<input type="text" name="accName" id="accName" maxlength="12" placeholder="12位字符以内" autocomplete="off" 
            			  onblur="checkInput(this,'accName_msg')" style="margin-left: 25px;" onkeyup="$('#accName_msg').hide()">
            			
            			<!-- oninput="checkInput(this,'accName_msg')" -->
            			
            		</div>
        		</div>
    		</li>
    		<li class="aui-list-item" id="accName_msg" style="color: red;font-size: 17px; display: none;">请输入开户人姓名</li>
    		<li class="aui-list-item">
        		<div class="aui-list-item-inner">
            		<div class="aui-list-item-label">银行账号<i>*</i></div>
            		<div class="aui-list-item-input">  
            			<input type="tel" name="accNo"   id="accNo"  maxlength="23" autocomplete="off"  placeholder="16-19位银行卡号"
    					onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\d]/g,'').replace(/(\d{4})(?=\d)/g,'$1 '); $('#accNo_msg').hide(); $('#btn_2').css('background','#4768F3')"  oncontextmenu="return false;"  
    					
    					onblur="checkAccountNumber2(this,'accNo_msg')" style="margin-left: 25px;">  <!-- oninput="checkAccountNumber(this,'accNo_msg')"   -->
            		</div>
        		</div>
    		</li>
    		<li class="aui-list-item" id="accNo_msg" style="color: red;font-size: 17px; display: none;">请输入银行账号,16-19位数字</li>
    		<li class="aui-list-item">
        		<div class="aui-list-item-inner">
            		<div class="aui-list-item-label">确认账号<i>*</i></div>
            		<div class="aui-list-item-input">
            			<input type="tel" id="confirmAccNo" name="confirmAccNo" autocomplete="off" placeholder="16-19位银行卡号"
            			onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\d]/g,'').replace(/(\d{4})(?=\d)/g,'$1 '); $('#confirmAccNo_msg').hide(); $('#btn_2').css('background','#4768F3')" 
            			onblur="checkConfirmAccNo2(this,'confirmAccNo_msg')"
    					maxlength="23" onpaste="return false;" style="margin-left: 25px;"  oncontextmenu="return false;">   <!-- oninput="checkConfirmAccNo(this,'confirmAccNo_msg')" -->
            		</div>
        		</div>
    		</li>
    		<li class="aui-list-item" id="confirmAccNo_msg" style="color: red;font-size: 17px; display: none;">两次输入的银行卡号不一样</li>
		</ul>
		<div class="bb-submit-div">
			<div class="aui-row-padded">
				<div class="aui-col-xs-6">
					<div class="aui-btn aui-btn-warning aui-btn-block"   onclick="up('table_2')">上一步</div>
				</div>
				<div class="aui-col-xs-6">
					<div class="aui-btn aui-btn-success aui-btn-block" id="btn_2"  onclick="checkTable2(this)">下一步</div>
				</div>
			</div>
		</div>
	</div>
	</div>
	
	<!-- 证件信息 -->
	<div id="table_3"  style="display:none;">
	<div id="attachInfo">
	<div class="aui-content-padded si-tad_div ui-page" >
        <div class="paper-info-list">
            <div class="paper-info-img" onclick="openCamera('hiddenfile1','base64Input1','showImg1')">
            	<img id="showImg1" >
            </div>
            <input id="hiddenfile1" name="files" type="file" accept="image/*" class="hidden" onchange="takePhoto(this.files,'showImg1','base64Input1')"/> 
            <!-- 存放压缩后的img -->
    		<input id="base64Input1" name="base64Input" type="text"  class="hidden" style="display: none;"/> 
            <div class="paper-info-title">身份证正面<i>*</i></div>
        </div>
        
        <div class="paper-info-list">
            <div class="paper-info-img" onclick="openCamera('hiddenfile2','base64Input2','showImg2')">
            	<img id="showImg2">
            </div>
             <input id="hiddenfile2" name="files" type="file" accept="image/*" class="hidden" onchange="takePhoto(this.files,'showImg2','base64Input2')"/> 
              <!-- 存放压缩后的img -->
    		 <input id="base64Input2" name="base64Input" type="text"  class="hidden" style="display: none;"/>
            <div class="paper-info-title">身份证反面<i>*</i></div>
        </div>
        
        <div class="paper-info-list">
            <div class="paper-info-img" onclick="openCamera('hiddenfile3','base64Input3','showImg3')">
            	<img id="showImg3">
            </div>
             <input id="hiddenfile3" name="files" type="file" accept="image/*" class="hidden" onchange="takePhoto(this.files,'showImg3','base64Input3')"/> 
              <!-- 存放压缩后的img -->
    	     <input id="base64Input3" name="base64Input" type="text"  class="hidden" style="display:none;"/>
            <div class="paper-info-title">营业执照<i>*</i></div>
        </div>
        
        <div class="paper-info-list">
            <div class="paper-info-img" onclick="openCamera('hiddenfile4','base64Input4','showImg4')">
            	<img  id="showImg4">
            </div>
             <input id="hiddenfile4" name="files" type="file" accept="image/*" class="hidden" onchange="takePhoto(this.files,'showImg4','base64Input4')"/> 
              <!-- 存放压缩后的img -->
    		 <input id="base64Input4" name="base64Input" type="text"  class="hidden" style="display: none;"/>
            <div class="paper-info-title">餐饮许可证&nbsp;&nbsp;</div>
        </div>
        
        <div class="paper-info-list">
            <div class="paper-info-img" onclick="openCamera('hiddenfile5','base64Input5','showImg5')">
            	<img id="showImg5">
            </div>
             <input id="hiddenfile5" name="files" type="file" accept="image/*" class="hidden" onchange="takePhoto(this.files,'showImg5','base64Input5')"/> 
              <!-- 存放压缩后的img -->
    		 <input id="base64Input5" name="base64Input" type="text"  class="hidden" style="display: none;"/>
            <div class="paper-info-title">店铺LOGO&nbsp;&nbsp;</div>
        </div>
        <p class="ui-exp">注：餐饮类商户需提交餐饮许可证</p>
    </div>
    <script >
   
	</script>
    <div class="aui-content">
	<div class="bb-submit-div">
		<div class="aui-row-padded">
			<div class="aui-col-xs-6" >
				<div class="aui-btn aui-btn-warning aui-btn-block" id="upDiv" onclick="up('table_3')" >上一步</div>
			</div>
			<div class="aui-col-xs-6" id="commitDiv">
				<div class="aui-btn aui-btn-success aui-btn-block" id="btn_3"  onclick="return checkAttach(); ">完成</div>
				
			</div>
		</div>
	</div>
    </div>
    <div class="pop-success" id="alertDiv1">
		<div class="pop-success-content">
			<div class="pop-content" style="padding-top:50px;">
				<div class="">确定要提交商户资料吗？</div>
			</div>
			<div class="pop-footer">
				<button id="KeepOn" onclick="checkForm();">确定</button>
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
                <img src='${baseURL}/common/css/images/ajax-loader.gif' style="margin:0 auto;"></button>
            </div>
		</div>
	</div>
	

	</div>
	
	</div>
	</div>
	
	</form>

</body>
</html>