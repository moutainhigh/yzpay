<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../../common/taglib/taglib.jsp"%>
	
<div class="bjui-pageHeader">
    <form id="pagerForm" data-toggle="ajaxsearch" action="${baseURL}/sys/role/list" method="post">
        <input type="hidden" name="pageSize" value="${model.pageSize}">
        <input type="hidden" name="pageCurrent" value="${model.pageCurrent}">
        <input type="hidden" name="orderField" value="${param.orderField}">
        <input type="hidden" name="orderDirection" value="${param.orderDirection}">
        <div class="bjui-searchBar">
           <label>角色名称：</label><input type="text" name="roleName" value="${sysRole.roleName}" size="30" class="form-control" alt="模糊查询" />
            <button type="submit" class="btn-default" data-icon="search">查询</button>&nbsp;
            <a class="btn btn-orange" href="javascript:;" data-toggle="reloadsearch" data-clear-query="true" data-icon="undo">清空查询</a>
            <div>
            	<%-- <a class="add" href="${baseURL}/sys/user/addUI" data-toggle="dialog" data-width="800" data-height="400" data-id="dialog-normal" rel="input" title="添加操作员"> --%>
                <button type="button" class="btn-blue" href="${baseURL}/sys/role/addUI" data-toggle="dialog" data-width="800" data-height="400" data-id="dialog-normal" title="添加角色" data-icon="plus">添加角色</button>
                <div class="pull-right">
                <div class="btn-group">
                    <button type="button" class="btn-default dropdown-toggle" data-toggle="dropdown" data-icon="copy">批量操作<span class="caret"></span></button>
                    <ul class="dropdown-menu right" role="menu">
                        <li><a href="book1.xlsx" data-toggle="doexport" data-confirm-msg="确定要导出信息吗？">导出<span style="color: green;">全部</span></a></li>
                        <li><a href="book1.xlsx" data-toggle="doexportchecked" data-confirm-msg="确定要导出选中项吗？" data-idname="expids" data-group="ids">导出<span style="color: red;">选中</span></a></li>
                        <li class="divider"></li>
                        <li><a href="ajaxDone2.html" data-toggle="doajaxchecked" data-confirm-msg="确定要删除选中项吗？" data-idname="delids" data-group="ids">删除选中</a></li>
                    </ul>
                </div>
            </div>
            </div>
        </div>
    </form>
</div>
<div class="bjui-pageContent tableContent">
    <!-- <table class="table table-bordered table-hover table-striped table-top" data-selected-multi="true"> -->
       <table class="table table-bordered table-hover table-striped table-top" data-toggle="tablefixed"> 
        <thead>
            <tr>
                <th data-order-field="index" align="center">序号</th>
                <th data-order-field="roleName">角色名称</th>
                <th data-order-field="roleCode">角色编码</th>
                <th data-order-field="remark">描述</th>
                <th data-order-field="createTime">创建时间</th>
                <!-- <th data-order-field="status"  align="center">状态</th> -->
                <th width="26"><input type="checkbox" class="checkboxCtrl" data-group="ids" data-toggle="icheck"></th>
                <th width="350">操作</th>
            </tr>
        </thead>
        <tbody>
			<c:forEach var="item" items="${pageBean.recordList}" varStatus="st">
				<!-- 普通操作员看不到超级管理员角色 -->
				<tr target="sid_user" rel="${id}">
					<td>${st.index+1}</td>
					<td>${item.roleName }</td>
					<td>${item.roleCode}</td>
					<td>${item.remark}</td>
					<td>
						<fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss" />
					</td>
					<td><input type="checkbox" name="ids" data-toggle="icheck" value="${item.id}"></td>
					<td>
						
						<a href="${baseURL}/sys/role/assignMenuUI?roleId=${item.id}" class="btn btn-green" title="为角色【${item.roleName}】分配菜单" data-toggle="dialog" data-width="800" data-height="400" data-id="dialog-normal" style="color: blue">分配菜单</a>
						 &nbsp;
						<a href="${baseURL}/sys/role/assignPermissionUI?roleId=${item.id}" class="btn btn-green" title="为角色【${item.roleName}】分配权限" data-toggle="dialog" data-width="800" data-height="400" data-id="dialog-normal" style="color: blue">分配权限</a>
						 &nbsp;
						<a href="${baseURL}/sys/role/editUI?roleId=${item.id}" class="btn btn-green" data-confirm-msg="修改角色【${item.roleName}】" data-toggle="dialog" data-width="800" data-height="400" data-id="dialog-normal">修改</a>
						
						<c:if test="${roleType eq RoleTypeEnum.USER.value}">
							&nbsp;<a href="${baseURL}/sys/role/delete?roleId=${item.id}" class="btn btn-red" data-confirm-msg="确定要删除角色【${item.roleName}】吗?" data-toggle="doajax" >删除</a>
							</c:if>
					</td>
				</tr>
			</c:forEach>
		</tbody>
    </table>
</div>
<div class="bjui-pageFooter">
    <div class="pages">
        <span>每页&nbsp;</span>
        <div class="selectPagesize">
            <select data-toggle="selectpicker" data-toggle-change="changepagesize" name="numPerPage"
			value="${pageBean.numPerPage}">
			<c:forEach begin="15" end="90" step="15" varStatus="s">
				<option value="${s.index}" ${pageBean.numPerPage eq s.index ? 'selected="selected"' : ''}>${s.index}</option>
			</c:forEach>
		</select>
        </div>
        <span>&nbsp;条，共 ${pageBean.totalCount} 条,共${pageBean.totalPage}页, 当前第${pageBean.currentPage}页</span>
    </div>
    <div class="pagination-box" data-toggle="pagination" data-total="${pageBean.totalCount}" data-page-size="${pageBean.numPerPage}" data-page-current="${pageBean.currentPage}">
    </div>
</div>


