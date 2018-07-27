<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../common/taglib/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN"> 
	<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width,initial-scale=1.0"/>
	<meta name="format-detection" content="telephone=no,email=no,date=no,address=no">
	<title>商铺录入</title>
	<link rel="stylesheet" type="text/css" href="${baseURL}/common/css/new/aui.css" />
	<link rel="stylesheet" type="text/css" href="${baseURL}/common/css/new/style.css?v=<%=System.currentTimeMillis() %>" />
	<script src="${baseURL}/common/js/plugin/jquery-2.1.4.min.js"></script> 
    <script src="${baseURL}/static/js/biz-js/store.js?v=<%=System.currentTimeMillis() %>"></script>
    <script src="${baseURL}/common/js/new/api.js"></script>
    <script src="${baseURL}/common/js/common.js?v=<%=System.currentTimeMillis()%> "></script>
    <script src="${baseURL}/common/js/plugin/jsFunction.js?v=<%=System.currentTimeMillis() %>"></script>
    <script src="${baseURL}/common/js/plugin/jsBridge.js"></script>
</head>
<body>
	<div class="aui-bar aui-bar-nav">
    	<a class="aui-pull-left aui-btn" onclick="location.href='${baseURL}/sys/store/list?pageIndex=0&type=0&merchant=${merchant}'">
        	<span class="aui-iconfont aui-icon-left"></span>商铺管理
    	</a>
    	<div class="aui-title">商铺录入</div>
	</div>
	<input type="hidden" id="duplicateLogin" autocomplete="off" value="${duplicateLogin}">
	<form id="storeadd" name="storeadd" class="form-horizontal" role="form" action="${baseURL}/sys/store/add?merchant=${merchant}" method="post">
	<input type="hidden" value="${baseURL}" id="basePath" />
	<input type="hidden" value="" id="storeName1" />
	<input type="hidden" value="${merchant}" id="merchant" />
	<div class="aui-content aui-margin-b-15">
		<ul class="aui-list aui-form-list register-form">
            <li class="aui-list-item">
                <div class="aui-list-item-inner">
                    <div class="aui-list-item-label">店铺名称<i>*</i></div>
                    <div class="aui-list-item-input">
                        <input type="text" id="storeName" name="storeName"  placeholder="建议用品牌名+**店命名" onblur="checkStore(this)" onkeydown="removeInfo(this)" maxlength="15">
                    </div>
                </div>
            </li>
			<li class="aui-list-item">
        		<div class="aui-list-item-inner">
            		<div class="aui-list-item-label">所在省份<i>*</i></div>
            		<div class="aui-list-item-input">
            			<select class="selectpicker" name="prov" id="prov" title="省" style="width: 200px;float: left;" onchange="getCity(this)" onblur="checkInput(this,'省')" onclick="removeInfo(this);" >
    			  			<option value="">选择省</option> 
    			  			<c:forEach var="item" items="${requestScope.provMap}" varStatus="i"  >
        		   				<option value="${item.key}">${item.value}</option>  
        		  			</c:forEach>
    					</select>
            		</div>
        		</div>
    		</li>
			<li class="aui-list-item">
        		<div class="aui-list-item-inner">
            		<div class="aui-list-item-label">所在城市<i>*</i></div>
            		<div class="aui-list-item-input">
    					<select class="selectpicker" name="city" id="city" title="市" style="width: 200px;float: left;;" onchange="getArea(this)" onblur="checkInput(this,'市')" onclick="removeInfo(this)" >
    			    		<option value="">选择市</option>
    					</select>
            		</div>
        		</div>
    		</li>
			<li class="aui-list-item">
        		<div class="aui-list-item-inner">
            		<div class="aui-list-item-label">所在区县<i>*</i></div>
            		<div class="aui-list-item-input">
    		    		<select class="selectpicker" title="区" id="area" name="area" id="area" style="width: 200px;float: left;" onblur="checkInput(this,'区')" onclick="removeInfo(this)">
    			    		<option value="">选择区</option>
    					</select>
            		</div>
        		</div>
    		</li>
    		<li class="aui-list-item">
        		<div class="aui-list-item-inner">
            		<div class="aui-list-item-label">详细地址<i>*</i></div>
            		<div class="aui-list-item-input">
            			<input type="text" id="address" name="address" placeholder="详细地址" onblur="check(this,1)" onkeydown="removeInfo(this)" maxlength="30">
            		</div>
        		</div>
    		</li>
    		<li class="aui-list-item">
        		<div class="aui-list-item-inner">
            		<div class="aui-list-item-label">联系人</div>
            		<div class="aui-list-item-input">
            			<input type="text" id="contactMan" name="contactMan" placeholder="中文4位或英文8位以内" onblur="check(this,0)" onkeydown="removeInfo(this)" maxlength="8">
            		</div>
        		</div>
    		</li>
            <li class="aui-list-item">
                <div class="aui-list-item-inner">
                    <div class="aui-list-item-label">联系电话</div>
                    <div class="aui-list-item-input">
                        <input type="text" id="contactTel" name="contactTel" placeholder="联系电话(座机或手机)" maxlength="18" onblur="check(this,0)" onkeydown="removeInfo(this)">
                    </div>
                </div>
            </li>
            <li class="aui-list-item">
                <div class="aui-list-item-inner">
                    <div class="aui-list-item-label">店铺说明</div>
                    <div class="aui-list-item-input">
                        <input type="text" id="remark" name="remark" placeholder="20字以内,不能有特殊字符" onblur="check(this,0)" onkeydown="removeInfo(this)" maxlength="20">
                    </div>
                </div>
            </li>
		</ul>
		<p id="message" style="margin-top: 10px;height: 15px;color: #fd4142;margin-left: 12px;font-size:16px; " ></p>
		<div class="aui-content-padded" style="margin-top:20px;">
			<button type="button" id="storeaddbtn" class="aui-btn aui-btn-success aui-btn-block"  onclick="saveForm('storeadd')">确认添加</button>
		</div>
	</div>
	</form>
	<div class="pop-success">
		<div class="pop-success-content">
			<div class="pop-content" style="padding-top:28px;" id="addmsg">
				<div><img src="${baseURL}/common/images/new/ic_success_01.png" alt=""></div>
				<div class="">店铺添加成功</div>
			</div>
			<div class="pop-footer">
				<button id="KeepOn" onclick="window.location.href='${baseURL}/sys/store/goadd?merchant=${merchant}'">继续添加</button>
				<button id="Return-index" onclick="window.location.href='${baseURL}/sys/store/list?pageIndex=0&type=0&merchant=${merchant}'">返回商铺</button>
			</div>
		</div>
	</div>
	<div class="pop-ajax" id="timeover">
		<div class="pop-ajax-content" >
			<%-- <img src='${baseURL}/common/css/images/ajax-loader.gif' alt="正在加载..."> --%>
		</div> 
	</div>
</body>
<script>
	session();
</script>
</html>
