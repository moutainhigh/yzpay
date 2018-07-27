<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../../common/taglib/taglib.jsp"%>
<script src="${baseURL}/static/js/biz-js/store.js" ></script>
<div class="pageContent">
	<form method="post" action="" class="pageForm" data-toggle="validate" data-callback="DictEdit.navTabAjax">
		<div class="pageFormContent" layoutH="60">
			<input type="hidden" id="storeNo" name="storeNo" value="${storeEntity.storeNo}" readonly="readonly">
			<input type="hidden" id="merId" name="merId" value=""><br><br>
			<div style="height: 50px;">
				<label class="control-label x90">门店名称：</label>
				<input type="text" id="storeName" name="storeName" value="${storeEntity.storeName}"  readonly="readonly" size="31" /> 
                <span class="required" style = "color:red;font-size:15px"><b>*</b></span>
			</div>
			<div style="height: 50px;">
				<label class="control-label x90">机构编码：</label>
				<input type="text" id="orgNo" name="orgNo" value="${storeEntity.orgNo}"  readonly="readonly" size="31" /> 
                <span class="required" style = "color:red;font-size:15px"><b>*</b></span><br>
			</div>
			<div style="height: 50px;">
				<label class="control-label x90">联系人：</label>
				<input type="text" id="contactMan" name="contactMan" value="${storeEntity.contactMan}" readonly="readonly" size="31"/> 
			</div>
			<div style="height: 50px;">
				<label class="control-label x90">联系电话：</label>
				<input type="text" id="contactTel" name="contactTel" value="${storeEntity.contactTel }" readonly="readonly" size="31" /> 
                <div id="msg4"></div>
			</div>
			<div style="height: 50px;">
				<label class="control-label x90">商户名称：</label>
				<input type="text" id="registerName" name="registerName"  value="${storeEntity.registerName }" readonly  size="31"/> 
				<span class="required" style = "color:red; font-size:15px"><b>*</b></span><br>
			</div>
			
			<div style="height: 50px;">
				<label class="control-label x90">区域信息：</label>
				<select id="prov" name="prov" class="selectpicker show-menu-arrow form-control" onchange="getCity(this,'addform')" style="width: 100px;">
            		<option value="" >请选择省份</option>
        			<c:forEach var="item" items="${requestScope.provMap}" varStatus="i" >
        			<option value="${item.key}" <c:if test="${item.key==prov}"> selected="selected" </c:if> >${item.value}</option>
        			</c:forEach>
        		</select>
        		<select id="city" name="city" class="selectpicker show-menu-arrow form-control" onchange="getArea(this,'addform')" style="width: 100px;">
        			<option value="">请选择市</option>
        			<c:forEach var="item" items="${requestScope.cityMap}" varStatus="i" >
        			<option value="${item.key}" <c:if test="${item.key==city}"> selected="selected" </c:if> >${item.value}</option>
        			</c:forEach>
        		</select>
        		<select id="area" name="area" class="selectpicker show-menu-arrow form-control" style="width: 100px;">
        			<option value="">请选择区</option>
        			<c:forEach var="item" items="${requestScope.areaMap}" varStatus="i" >
        			<option value="${item.key}" <c:if test="${item.key==area}"> selected="selected" </c:if> >${item.value}</option>
        			</c:forEach>
        		</select>            	
                <span class="required" style = "color:red;font-size:15px"><b>*</b></span>
			</div>
            <div style="height: 50px;">
                <label class="control-label x90">详细地址：</label>
                <input type="text" id="address" name="address" value="${storeEntity.address}" readonly="readonly" size="31"/> 
            </div>
		</div>
		<div class="bjui-pageFooter">
		    <ul>
		        <li><button type="button" class="btn-close" data-icon="close">退出</button></li>
		    </ul>
		</div>
	</form>
</div>
