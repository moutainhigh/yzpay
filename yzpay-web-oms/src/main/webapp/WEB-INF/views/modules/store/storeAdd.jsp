<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../../common/taglib/taglib.jsp"%>


<script src="${baseURL}/static/js/biz-js/store.js" ></script>
<script type="text/javascript">
var DictAdd = {
    // 删除后的回调函数，刷新当前页面
    navTabAjax: function(json) {
        if(json.statusCode=='200')
        {
            $(this).dialog('closeCurrent');
            $(this).navtab('refresh');
            $(this).alertmsg('ok', '添加成功！');
        }else{
             $(this).alertmsg('error', '添加失败！');
        }
    }
};
</script>
<style>
  .f{
    color: red;
    margin-left: 100px;
    margin-top: 30px;
  }
</style>

<div class="pageContent">
	<form id="addform" method="post" data-toggle="validate" action="${baseURL}/sys/store/add" class="pageForm" data-callback="DictAdd.navTabAjax">
		<div class="pageFormContent" layoutH="60">
			<input type="hidden" name="navTabId" value="jsgl">
			<input type="hidden" name="callbackType" value="closeCurrent">
			<input type="hidden" name="forwardUrl" value="">
			
			<input type="hidden" id="storeNo" name="storeNo" value="">
			<input type="hidden" id="merId" name="merId" value="${merId}"><br><br>
			<div style="height: 50px;">
				<label class="control-label x90">门店名称：</label>
				<input type="text" id="storeName" name="storeName" value="${storeName}"   size="31" onblur="checkStoreName(this)"/> 
                <span class="required" style = "color:red;font-size:15px"><b>*</b></span>
                <div id="msg1"></div>
			</div>
			<div style="height: 50px;">
				<label class="control-label x90">联系人：</label>
				<input type="text" id="contactMan" name="contactMan" value="${contactMan}"  size="31" onblur="checkContactMan(this)"/> 
                <div id="msg3"></div>
			</div>
			<div style="height: 50px;">
				<label class="control-label x90">联系电话：</label>
				<input type="text" id="contactTel" name="contactTel" value="${contactTel }" size="31" onblur="checkContactTel(this)"/> 
                <div id="msg4"></div>
			</div>
			<div style="height: 50px;">
				<label class="control-label x90">所属商户：</label>
				<input type="text" id="registerName" name="registerName" data-title="查找带回" value="${registerName }" readonly 
				data-toggle="lookup" data-url="${baseURL }/sys/store/lookupmerchId" data-width="600" data-height="600" 
				data-title="查找带回" size="31" onblur="checkInput(this)"/> 
				<span class="required" style = "color:red; font-size:15px"><b>*</b></span><br>
				<div id="msg5"></div>
			</div>
			
			<div style="height: 50px;">
				<label class="control-label x90">区域信息：</label>
				<select id="prov" name="prov" class="selectpicker show-menu-arrow form-control" onchange="getCity(this,'addform')" 
				style="width: 100px;" onblur="checkInput(this)">
            		<option value="">请选择省份</option>
        			<c:forEach var="item" items="${requestScope.provMap}" varStatus="i" >
        			<option value="${item.key}" >${item.value}</option>
        			</c:forEach>
        		</select>
        		<select id="city" name="city" class="selectpicker show-menu-arrow form-control" onchange="getArea(this,'addform')" 
        		style="width: 100px;" onblur="checkInput(this)">
        			<option value="">请选择市</option>
        		</select>
        		<select id="area" name="area" class="selectpicker show-menu-arrow form-control" style="width: 100px;" onblur="checkInput(this)">
        			<option value="">请选择区</option>
        		</select>            	
                <span class="required" style = "color:red;font-size:15px"><b>*</b></span>
                <div id="msg6"></div>
			</div>
            <div style="height: 50px;">
                <label class="control-label x90">详细地址：</label>
                <input type="text" id="address" name="address" value="${address}"  size="31" onblur="checkAddress(this)"/> 
                <div id="msg7"></div>
            </div>
		</div>
		<div id="message" align="center" style="color: red;"></div>
		<div class="bjui-pageFooter">
		    <ul>
		        <li><button type="button" class="btn-close" data-icon="close">取消</button></li>
		        <li><button type="submit" class="btn-default" data-icon="save" onclick="return checkForm()">保存</button></li>
		    </ul>
		</div>
	</form>
</div>