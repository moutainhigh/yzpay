<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../../common/taglib/taglib.jsp"%>
<script type="text/javascript">

// 删除后的回调函数，刷新树形菜单
function navTabAjax(json) {
     $(selector).bjuiajax('refreshLayout', ture);
}

function bringBack(agentId,agentSerialNo,companyName,contactMan,phone){
    $("#companyName").val(companyName);
    $("#parentId").val(agentId);
    $("#agentSerialNo").val(agentSerialNo);
    
    $("#prov option").each(function(){
    	var txt = $(this).text();
    	if(prov==txt){
    		$(this).attr("selected",true);
    		$("#prov").change();
    	}
    });
    
    setTimeout(function(){
    	$("#city option").each(function(index){
            var txt = $(this).text();
            if(city==txt){
                $(this).attr("selected",true);
                $("#city").change();
            }
        });
    }, 500);
    setTimeout(function(){
    	$("#area option").each(function(index){
            var txt = $(this).text();
            if(area==txt){
                $(this).attr("selected",true);
                $("#area").change();
            }
        });
    }, 500);
    
}

</script>

<div class="bjui-pageHeader">
    <form id="pagerForm" data-toggle="ajaxsearch" action="${baseURL }/sys/agent/lookSuper" method="post">
        <div class="bjui-searchBar">
            <label>代理商名称：</label><input type="text" name="companyName" value="${param.companyName }" size="20" alt="模糊查询" class="form-control" />&nbsp;
            <button type="submit" class="btn-default" data-icon="search">查询</button>&nbsp;
        </div>
    </form>
</div>
<div class="bjui-pageContent tableContent">
    <table class="table table-bordered table-hover table-striped table-top" data-toggle="tablefixed">
        <thead>
            <tr>
                <th data-order-field="agentSerialNo" align="center">代理商编号</th>
                <th data-order-field="companyName" align="center">代理商名称</th>
                <th data-order-field="contactMan" align="center">联系人</th>
                <th data-order-field="phone" align="center">手机号码</th>
                <th data-order-field="" align="center">操作</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="item" items="${pageBean.recordList}" varStatus="st">
                    <tr target="merch">
                        <td>${item.agentSerialNo}</td>
                        <td>${item.companyName}</td>
                        <td>${item.contactMan}</td>   
                        <td>${item.phone}</td>
                        <td>
                            <a onclick="javascript:bringBack(&quot;${item.agentId}&quot;,&quot;${item.agentSerialNo}&quot,&quot;${item.companyName}&quot,&quot;${item.prov}&quot;,&quot;${item.city}&quot;,&quot;${item.area}&quot;);" data-args="{}" data-toggle="lookupback" class="btn btn-blue" title="选择本项" data-icon="check">选择</a>
                        </td>
                    </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
<div class="bjui-pageFooter">
    <div class="pages">
        <span>每页 &nbsp;</span>
        <div class="selectPagesize">
            <select data-toggle="selectpicker" data-toggle-change="changepagesize" name="numPerPage"
            value="${pageBean.numPerPage}">
            <c:forEach begin="15" end="90" step="15" varStatus="s">
                <option value="${s.index}" ${pageBean.numPerPage eq s.index ? 'selected="selected"' : ''}>${s.index}</option>
            </c:forEach>
        </select>
        </div>
        <span>&nbsp;条，共 ${pageBean.totalCount}条,共${pageBean.totalPage}页, 当前第${pageBean.currentPage}页</span>
    </div>
    <div class="pagination-box" data-toggle="pagination" data-total="${pageBean.totalCount}" data-page-size="${pageBean.numPerPage}" data-page-current="${pageBean.currentPage}">
    </div>
</div>