<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../../common/taglib/taglib.jsp"%>
<div class="container-fluid" data-role="page" id="clerkdiv" style="background-color: #FFFFFF;" >
    <input type="hidden" id="pageindex" value="${pageIndex}">
    <input type="hidden" id="storeNo" value="${storeNo}">
    <input type="hidden" id="merchant" value="${merchant}">
    <input type="hidden" id="total" value="${total}">
    <input type="hidden" id="count" value="${fn:length(clerkList)}">
    <input type="hidden" id="duplicateLogin" autocomplete="off" value="${duplicateLogin}">
	<div class="aui-content aui-margin-b-15">
		<ul class="aui-list aui-list-in si-aui-list">
			<li class="aui-list-item">
            	<div class="aui-list-item-inner list-BigTitle">
                	<div class="aui-list-item-title bd-ShopName">${storeName}</div>
                	<div class="aui-list-item-right cm-AddClerk" onclick="window.location.href='${baseURL}/sys/store/goclerkadd?storeNo=${storeNo}&merchant=${merchant}'">
                	<img src="${baseURL}/common/images/new/ic_add_01.png" alt="">添加店员
                	</div>
            	</div>
        	</li>
        	<c:if test="${clerkList.size() < 1}">
    		<li class="" >
				<div class="" style="text-align: center;height: 30px;margin-top: 10px;">
					尚未添加店员...
				</div>
			</li>
   		</c:if>
        	<c:forEach var="item" items="${requestScope.clerkList}" varStatus="i">
    		<li class="aui-list-item clerk-list" >
        		<div class="aui-list-item-inner">
            		<div class="aui-list-item-title cm-list-item-title" onclick="window.location.href='${baseURL }/sys/store/goclerkedit?id=${item.id}&merchant=${merchant}'">
            			<div><c:if test="${item.userType==2}">店长:</c:if> <c:if test="${item.userType==3}">店员:</c:if> ${item.userName}</div>
            			<!-- <div>张三</div> -->
            			<div style="margin-left: -35px;">账号：${item.loginName}</div>
            		</div>
            		<div class="aui-list-item-right" onclick="$('#alertDiv1').show();$('#msgset').val('in'+${i.count});">
            			<input type="hidden" value="${item.id}" id="in${i.count}">
            			<img src="${baseURL}/common/images/new/ic_delete01.png" alt="删除店员" >
            		</div>
        		</div>
    		</li>
    		</c:forEach>
		</ul>
	</div>
	</div>