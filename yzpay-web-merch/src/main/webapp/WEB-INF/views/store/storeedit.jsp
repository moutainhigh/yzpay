<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../common/taglib/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN"> 
	<head>
	<title>商铺修改</title>
	<link rel="stylesheet" type="text/css" href="${baseURL}/common/css/new/aui.css" />
	<link rel="stylesheet" type="text/css" href="${baseURL}/common/css/new/style.css?v=<%=System.currentTimeMillis() %>" />
	<link rel="stylesheet" href="${baseURL}/common/css/mescroll.css">
    <link rel="stylesheet" href="${baseURL}/common/css/mescroll-option.css"> 
	<script src="${baseURL}/common/js/plugin/jquery-2.1.4.min.js"></script> 
    <script src="${baseURL}/common/js/plugin/mescroll.js" type="text/javascript" charset="utf-8"></script>
	<script src="${baseURL}/common/js/plugin/mescroll-option.js?v=<%=System.currentTimeMillis() %>" type="text/javascript" charset="utf-8"></script>
	<script src="${baseURL}/static/js/biz-js/store.js?v=<%=System.currentTimeMillis() %>"></script>
    <script src="${baseURL}/common/js/new/api.js"></script>
    <script src="${baseURL}/common/js/common.js?v=<%=System.currentTimeMillis()%> "></script>
    <script src="${baseURL}/common/js/plugin/jsFunction.js?v=<%=System.currentTimeMillis() %>"></script>
    <script src="${baseURL}/common/js/plugin/jsBridge.js?v=<%=System.currentTimeMillis() %>"></script>
</head>
<body>
	<header class="aui-bar aui-bar-nav">
    	<a class="aui-pull-left aui-btn" onclick="location.href='${baseURL}/sys/store/list?pageIndex=0&type=0&merchant=${merchant}'">
        	<span class="aui-iconfont aui-icon-left"></span>商铺管理
    	</a>
    	<div class="aui-title">商铺修改</div>
	</header>
	<form id="storeedit" name="storeedit" class="form-horizontal" role="form" action="${baseURL}/sys/store/edit" method="post">
	<input type="hidden" value="${baseURL}" id="basePath" />
	<input type="hidden" value="${storeEntity.storeName}" id="storeName1" />
	<input type="hidden" value="${storeEntity.merId}" id="merId" name="merId" />
	<input type="hidden" value="${merchant}" id="merchant" />
	<input type="hidden" value="${storeEntity.id}" id="id" name="id" />
	<input type="hidden" id="duplicateLogin" autocomplete="off" value="${duplicateLogin}">
	<div class="aui-content aui-margin-b-15">
		<ul class="aui-list aui-form-list register-form">
            <li class="aui-list-item">
                <div class="aui-list-item-inner">
                    <div class="aui-list-item-label">店铺名称<i>*</i></div>
                    <div class="aui-list-item-input">
                        <input type="text" id="storeName" name="storeName"  value="${storeEntity.storeName}" placeholder="建议用品牌名+**店命名" onblur="checkStore(this)" onkeydown="removeInfo(this)" maxlength="15">
                    </div>
                </div>
            </li>
			<li class="aui-list-item">
        		<div class="aui-list-item-inner ">
            		<div class="aui-list-item-label">所在省份<i>*</i></div>
            		<div class="aui-list-item-input">
            			<select class="selectpicker" name="prov" id="prov" title="省" style="width: 200px;float: left;" onchange="getCity(this)" onblur="checkInput(this,'省')" onclick="removeInfo(this)" >
    			  			<c:forEach var="item" items="${requestScope.provMap}" varStatus="i"  >
        		   				<option value="${item.key}" <c:if test="${item.key==storeEntity.prov}">selected="selected" </c:if>>${item.value}</option>  
        		  			</c:forEach>
    					</select>
            		</div>
        		</div>
    		</li>
			<li class="aui-list-item">
        		<div class="aui-list-item-inner ">
            		<div class="aui-list-item-label">所在城市<i>*</i></div>
            		<div class="aui-list-item-input">
    					<select class="selectpicker" name="city" id="city" title="市" style="width: 200px;float: left;" onchange="getArea(this)" onblur="checkInput(this,'市')" onclick="removeInfo(this)" >
    			    		<c:forEach var="item" items="${requestScope.cityMap}" varStatus="i"  >
        		      			<option value="${item.key}" <c:if test="${item.key==storeEntity.city}">selected="selected" </c:if>>${item.value}</option>  
        		    		</c:forEach>
    					</select>
            		</div>
        		</div>
    		</li>
			<li class="aui-list-item">
        		<div class="aui-list-item-inner ">
            		<div class="aui-list-item-label">所在区县<i>*</i></div>
            		<div class="aui-list-item-input">
    		    		<select class="selectpicker" title="区" id="area" name="area" id="area" style="width: 200px;float: left;" onblur="checkInput(this,'区')" onclick="removeInfo(this)">
    			    		<c:forEach var="item" items="${requestScope.areaMap}" varStatus="i"  >
        		      			<option value="${item.key}" <c:if test="${item.key==storeEntity.area}">selected="selected" </c:if>>${item.value}</option>  
        		    		</c:forEach>
    					</select>
            		</div>
        		</div>
    		</li>
    		<li class="aui-list-item">
        		<div class="aui-list-item-inner">
            		<div class="aui-list-item-label">详细地址<i>*</i></div>
            		<div class="aui-list-item-input">
            			<input type="text" id="address" name="address" placeholder="详细地址" value="${storeEntity.address}" onblur="check(this,1)" onkeydown="removeInfo(this)" maxlength="30">
            		</div>
        		</div>
    		</li>
    		<li class="aui-list-item">
        		<div class="aui-list-item-inner">
            		<div class="aui-list-item-label">联系人</div>
            		<div class="aui-list-item-input">
            			<input type="text" id="contactMan" name="contactMan" value="${storeEntity.contactMan}" placeholder="中文4位或英文8位以内" onblur="check(this,0)" onkeydown="removeInfo(this)" maxlength="8">
            		</div>
        		</div>
    		</li>
            <li class="aui-list-item">
                <div class="aui-list-item-inner">
                    <div class="aui-list-item-label">联系电话</div>
                    <div class="aui-list-item-input">
                        <input type="tel" id="contactTel" name="contactTel" value="${storeEntity.contactTel}" placeholder="联系电话(座机或手机)" maxlength="18" onblur="check(this,0)" onkeydown="removeInfo(this)">
                    </div>
                </div>
            </li>
            <li class="aui-list-item">
                <div class="aui-list-item-inner">
                    <div class="aui-list-item-label">店铺说明</div>
                    <div class="aui-list-item-input">
                        <input type="text" id="remark" name="remark" value="${storeEntity.remark}" placeholder="商铺说明控制在20字以内" onblur="check(this,0)" onkeydown="removeInfo(this)" maxlength="20">
                    </div>
                </div>
            </li>
		</ul>
		<input type="hidden" id="storeNo" name="storeNo" value="${storeEntity.storeNo}">
		<p id="message" style="margin-top: 10px;height: 15px;color: #fd4142;margin-left: 12px;font-size:16px; " ></p>
		<div class="aui-content-padded" style="margin-top:20px;">
			<button type="button" id="storeaddbtn" class="aui-btn aui-btn-success aui-btn-block"  onclick="saveForm('storeedit')">确认修改</button>
		</div>
	</div>
	</form>
	<div class="pop-success">
		<div class="pop-success-content">
			<div class="pop-content" style="height:120px;padding-top:20px;">
				<div><img src="${baseURL}/common/images/new/ic_success_01.png" alt=""></div>
				<div class="">修改成功！</div>
			</div>
			<div class="pop-one-btn">
                <button class="aui-btn aui-btn-success aui-btn-block" id="edit-finish" onclick="window.location.href='${baseURL }/sys/store/list?pageIndex=0&type=0&merchant=${merchant}'">返回商铺</button>
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
