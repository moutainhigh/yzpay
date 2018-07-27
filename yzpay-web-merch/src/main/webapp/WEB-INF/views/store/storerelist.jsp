<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../../common/taglib/taglib.jsp"%>  
    <input type="hidden" id="pageindex" value="${pageIndex}">
    <input type="hidden" id="total" value="${total}">
    <input type="hidden" id="merchant" value="${merchant}">
    <input type="hidden" id="count" value="${fn:length(storeList)}">
    <input type="hidden" id="duplicateLogin" autocomplete="off" value="${duplicateLogin}">
    <c:if test="${storeList.size() < 1}">
    	<ul class="aui-list aui-list-in MyShop-list">
			<li class="MyShop-item">
				<div class="MyShop-text" align="center" style="height: 60px; padding-top: 15px;">
					<div align="center" >尚未添加商铺...</div>
				</div>
			</li>
		</ul>
      </c:if>
	<div class="aui-content">
	<c:forEach var="item" items="${requestScope.storeList}" varStatus="i">
		<ul class="aui-list aui-list-in MyShop-list" >
			<li class="MyShop-item">
				<div class="MyShop-text" onclick="window.location.href='${baseURL }/sys/store/goedit?storeNo=${item.storeNo}&merchant=${merchant}'">
					<div class="MyShop-shopName">${item.storeName}</div>
					<div class="MyShop-shopInfo">
						<span>联系人：${item.contactMan}</span>
						<span>电话：${item.contactTel}</span>
					</div>
				</div>
				<div class="MyShop-footer">
					<input type="hidden" value="${item.storeNo}" id="in${i.count}">
					<div class="MyShop-footer-option" onclick="$('#alertDiv1').show();$('#msgset').val('in'+${i.count});">
						<img src="${baseURL}/common/images/new/ic_delete01.png" alt="">
						<span>删除</span>
					</div>
					<div class="MyShop-footer-option" onclick="window.location.href='${baseURL }/sys/store/clerklist?storeNo=${item.storeNo}&pageIndex=0&type=0&merchant=${merchant}'">
						<img src="${baseURL}/common/images/new/ic_set-up.png" alt="">
						<span>店员设置</span>
					</div>
				</div>
			</li>
		</ul>
	</c:forEach>
	</div>