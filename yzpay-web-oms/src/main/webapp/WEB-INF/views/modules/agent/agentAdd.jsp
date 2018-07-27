<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../../common/taglib/taglib.jsp"%>
<script src="${baseURL}/static/js/biz-js/agentadd.js" ></script>
<script type="text/javascript">
var AgentAdd = {
	// 删除后的回调函数，刷新当前页面
	navTabAjax: function(json) {
		//navTabAjaxDone(json);
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

<div class="pageContent">
	<form method="post" action="${baseURL}/sys/agent/add" class="pageForm" data-toggle="validate" data-callback="AgentAdd.navTabAjax">
		<div class="pageFormContent">
			<input type="hidden" name="navTabId" value="jsgl">
			<input type="hidden" name="callbackType" value="closeCurrent">
			<input type="hidden" name="forwardUrl" value=""><br><br>
			<input type="hidden" name="parentId" value="${parentId}"><br><br>
			<div style="height: 50px;">
				<label class="control-label x90">代理商名称：</label>
				<input type="text" name="companyName"   value="${companyName}"  size="31" style="height: 37px;">
				<span class="required" style = "color:red;font-size:15px"><b>*</b></span>
                <div id="msg1"></div>
			</div>
			<div style="height: 50px;">
				<label class="control-label x90">上级代理商：</label>
				<input type="text" id="" name="companyName"  value="${companyName}" data-title="查找带回" size="31" style="height: 37px;" readonly 
				data-toggle="lookup" data-url="${baseURL }/sys/agent/lookSuper" data-width="600" data-height="600" 
				data-title="查找带回" onblur="checkInput(this)">
				<span class="required" style = "color:red;font-size:15px"><b>*</b></span>
                <div id="msg2"></div>
			</div>
			<div style="height: 50px;">
				<label class="control-label x90">联系人：</label>
				<input type="text" name="contactMan" value="${contactMan}" maxlength="90" size="31" style="height: 37px;"/>
				<span class="required" style = "color:red;font-size:15px"><b>*</b></span>
                <div id="msg3"></div>
			</div>
			<div style="height: 50px;">
				<label class="control-label x90">手机号码：</label>
				<input type="text" name="phone"   value="${phone}"  size="31" style="height: 37px;"/>
				<span class="required" style = "color:red;font-size:15px"><b>*</b></span>
                <div id="msg4"></div>
			</div>
			<div style="height: 50px;">
				<label class="control-label x90">区域信息：</label>
				<select id="prov" name="prov" class="selectpicker show-menu-arrow form-control" onchange="getCity(this,'addform')" 
				style="width: 100px;" onblur="checkInput(this)">
            		<option value="" style="height: 37px;">请选择省份</option>
        			<c:forEach var="item" items="${requestScope.provMap}" varStatus="i" >
        			<option value="${item.key}" style="height: 37px;">${item.value}</option>
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
                <div id="msg5"></div>
			</div>
            <div style="height: 50px;">
                <label class="control-label x90">详细地址：</label>
                <input type="text" id="address" name="address" value="${address}"  size="31" onblur="checkAddress(this)" style="height: 37px;"/> 
                <div id="msg6"></div>
            </div>
		</div>
		<div class="bjui-pageFooter">
		    <ul>
		        <li><button type="button" class="btn-close" data-icon="close">退出</button></li>
		        <li><button type="submit" class="btn-default" data-icon="save">保存</button></li>
		    </ul>
		</div>
	</form>
</div>