<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../../common/taglib/taglib.jsp"%> 
    	<div class="Vip-Statistics aui-bar" style="width: 100%;">
		<div class="Vip-Statistics-item">
			<div>
				<p class="title">会员总数</p>
				<c:if test="${userCardNum.totalNum == null}">0</c:if>
				<c:if test="${userCardNum.totalNum != null}">
					<p class="text">${userCardNum.totalNum}</p>
				</c:if>
			</div>
		</div>
		<div class="Vip-Statistics-item">
			<div>
				<p class="title">微信数量</p>
				<c:if test="${userCardNum.wxNum == null}">0</c:if>
				<c:if test="${userCardNum.wxNum != null}">
					<p class="text">${userCardNum.wxNum}</p>
				</c:if>
			</div>
		</div>
		<div class="Vip-Statistics-item">
			<div>
				<p class="title">支付宝数量</p>
				<c:if test="${userCardNum.aliNum == null}">0</c:if>
				<c:if test="${userCardNum.aliNum != null}">
					<p class="text">${userCardNum.aliNum}</p>
				</c:if>
			</div>
		</div>
	</div>
	  
	<input type="hidden" autocomplete="off" id="page" value="${page}">
    <input type="hidden" autocomplete="off" id="merchant" value="${merchant}">
    <input type="hidden" autocomplete="off" id="count" value="${fn:length(userCardList)}">
    <input type="hidden" id="duplicateLogin" autocomplete="off" value="${duplicateLogin}">
	<div class="aui-content aui-margin-b-15">
		<ul class="aui-list aui-list-in si-aui-list" id="user_ul">
		<c:if test="${requestScope.userCardList == null}">
				<li class="aui-list-item" >
        			<div class="aui-list-item-inner aui-list-item-arrow">
            			<div class="aui-list-item-title">会员卡目前暂无领用信息</div>
            			<div class="aui-list-item-right">
            				<span></span>
            			</div>
        			</div>
    			</li>
    	</c:if>
		<c:if test="${requestScope.userCardList != null}">
			<c:forEach var="item" items="${requestScope.userCardList}" varStatus="i">
				<li class="aui-list-item" onclick="location.href='${baseURL }/sys/membercard/usershow?id=${item.id}&merchant=${merchant}'">
        			<div class="aui-list-item-inner aui-list-item-arrow" >
            			<div class="aui-list-item-title">
            				<c:if test="${item.name == null}">匿名</c:if>
            				<c:if test="${item.name != null}">${item.name}</c:if>
            			</div>
            			<div class="aui-list-item-right">
            				<c:if test="${item.mobile == null}"><span>未激活</span></c:if>
            				<c:if test="${item.mobile != null}"><span>${item.mobile}</span></c:if>
            			</div>
        			</div>
    			</li>
    		</c:forEach>
    	</c:if>
		</ul>
	</div>