<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../../common/taglib/taglib.jsp"%>
<%
String  baseURL = "/yunpay/";
%>
<link rel="stylesheet" href="<%=baseURL%>static/BJUI/plugins/bootstrapSelect/bootstrap-select.css">

<script src="<%=baseURL%>static/js/biz-js/merchant.js" type="text/javascript"></script>
<div class="bjui-pageHeader"><br>   
</div>
<div class="bjui-pageContent tableContent">
    <table class="table table-bordered table-hover table-striped table-top" data-toggle="tablefixed" ><!-- data-selected-multi="true" -->
        <thead>
            <tr>
                <th align="center"  >appCode</th>
                <th align="center" >app名称</th>            
                <th align="center" >安装包名称</th>
                <th align="center" >版本号</th>             
                <th align="center" >verCode</th>
                <th align="center" >下载链接</th>
                <th align="center">操作</th>
            </tr>
        </thead>
        <tbody>
        <c:forEach var="item" items="${list}" varStatus="st">
				<tr>
					<td align="center">${item.appCode}</td>
					<td align="center">${item.appName }</td>
					<td align="center">${item.appApk }</td>
					<td align="center">${item.verName }</td>
					<td align="center">${item.verCode}</td>
					<td align="center">${item.appLink}</td>
				
					<td align="center">
						 <button type="button" class="btn btn-green" href="<%=baseURL%>/sys/app/appUpdate?appCode=${item.appCode}" data-toggle="dialog"  data-width="740"  data-height="350" 
					     data-id="dialog-normal" >${item.appName}app版本更新</button>
	                 
					</td>
				</tr>
			</c:forEach>
        </tbody>
    </table>
</div>

<script>
//测试jsonp
function jsonpTest(data){
	alert(data);
}

addScriptTag('http://192.168.0.122:8081/merch/appReleaseContr/jsonpTest?callback=jsonpTest');

/**
 * 动态添加script
 */
function addScriptTag(src) {
    var script = document.createElement("script");
    script.setAttribute("type", "text/javascript");
    script.src = src;
    document.body.appendChild(script);
}

</script>


<!-- 测试jsonP时需要调用的接口
<script type='text/javascript' src='http://192.168.0.122:8081/merch/appReleaseContr/jsonpTest?callback=jsonpTest'/>  -->


