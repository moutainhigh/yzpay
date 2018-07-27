<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../common/taglib/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width,initial-scale=1.0"/>
	<meta name="format-detection" content="telephone=no,email=no,date=no,address=no">
	<title>商家中心</title>

	<link rel="stylesheet" href="${baseURL}/common/css/index-aui.css" />
  	<link rel="stylesheet" href="${baseURL}/common/css/swiper.min.css" />
  	<link rel="stylesheet" href="${baseURL }/common/css/index-style.css?v=<%=System.currentTimeMillis() %>">
  	<script src="${baseURL}/common/js/plugin/jquery-2.1.4.min.js"></script>
  	<script src="${baseURL}/common/js/common.js?v=<%=System.currentTimeMillis()%> "></script> 
	<style>
		body,html{
			background-color: #fff;
		}
	</style>
</head>
<body>  
	<header class="header-user" style="height: 3.7rem;">
    	<a href="${baseURL }/sys/merchContr/merchIndex"><img class="header-user-img"  src="${baseURL }/cashierContr/showAttach?id=${merchInfo.attach5}" ></a>
    	<div style="margin: auto;">
    		<div class="header-user-title" style="margin-top: -0.2rem;font-size: 18px !important;">
				<c:if test="${not empty merchUser.userName}"> ${merchUser.userName} </c:if>
    			<c:if test="${empty merchUser.userName}"> ${merchInfo.cardName} </c:if>
			</div>
    		<div class="header-user-title" style="margin-top: -1.2rem;font-size: 14px !important;">${merchStatus}</div>
    		<div onclick="window.location.href='${baseURL}/logout'" style="float: right;margin-top:-2.5rem;font-size:18px;  z-index: 998;border: none; margin-right: 0.5rem;width: 3rem;">
    			<img alt="" src="${baseURL}/common/images/new/logout.png" id="logout" style="width: 1rem;float: left;"><div style="z-index: 999;float: right;margin-top: -0.8rem;">退出</div>
    		</div>
    	</div>
	</header>
	<!-- 轮播图 -->
	<div class="goods_details_swiper">
			<div class="swiper-container">
    			<div class="swiper-wrapper" style="height: 34% !important;">
    				<div class="swiper-slide" style="height: 100%;">
        				<div class="goods_swiper_img"><img src="${baseURL}/common/images/new/banner.png" alt="" style="height: 100%;"></div>
        			</div>
        			<div class="swiper-slide" style="height: 100%;">
        				<div class="goods_swiper_img" ><img src="${baseURL}/common/images/new/banner.png" alt="" style="height: 100%;"></div>
        			</div>
        			<div class="swiper-slide" onclick="" style="height: 100%;">
        				<div class="goods_swiper_img"><img src="${baseURL}/common/images/new/banner.png" alt="" style="height: 100%;"></div>
        			</div>
    			</div>
				<div class="swiper-pagination"></div>
    		</div>
	</div>
	<div class="home_exp" >2017年3月1号至8月1号刷微信有鼓励金领啦有鼓励金领啦</div>

	
	<ul class="home-icon-list" style="margin-top: 10px;">     							
		<li class="home-icon-item" onclick="window.location.href='${baseURL}/sys/store/list?pageIndex=0&type=0&merchant='">  
			<img src="${baseURL }/common/images/new/shop-icon.png">
			<span>店铺管理</span>
		</li>
		<c:if test="${wx == 1}">
			<li class="home-icon-item" onclick="location.href='${baseURL }/sys/membercard/show'">  
				<img src="${baseURL}/common/images/new/vip-icon.png">
				<span>会员管理</span>
			</li>
		</c:if>
		<c:if test="${wx == 0}">
			<li class="home-icon-item" onclick="$('.pop-success').show();">  
				<img src="${baseURL}/common/images/new/unvip-icon.png">
				<span>会员管理</span>
			</li>
		</c:if>
		<li class="home-icon-item" onclick="location.href='${baseURL}/sys/reportContr/list?pageIndex=1'">
			<img src="${baseURL}/common/images/new/yyls.png">
			<span>营业流水</span>
		</li>
		
		<li class="home-icon-item" style="margin-top: 10px;" onclick="location.href='${baseURL}/sys/reportContr/showReport'">
			<img src="${baseURL}/common/images/new/yy-icon.png">
			<span>营业报表</span>
		</li>
	</ul>
	<div class="pop-success" id="recultAlert">
		<div class="pop-success-content">
			<div class="pop-content" style="height: 7rem;padding-top: 1rem;">
				<div style="font-size:16px;width: 90%;margin: auto;">您的支付信息未配置，不能使用会员管理功能，如有疑问，请到“公众号-联系我们”联系客服人员处理!</div>
				
			</div>
			<div class="pop-one-btn" style="margin-top: -1rem;">
                <button type="button" class="aui-btn aui-btn-success aui-btn-block" id="edit-finish" onclick="$('.pop-success').hide();">返回</button>
            </div>
		</div>
	</div>
	<!-- <p id="message" style="margin-top: 8rem;height: 15px;color: #fd4142;margin-left: 12px;font-size:16px; " hidden="hidden" >
	您的微信商户申请流程尚未完成，不能使用《会员管理》功能，如有疑问请联系我司客服人员处理!</p> -->
</body>
	<script type="text/javascript" src="${baseURL }/common/js/login/swiper.min.js"></script>
 	<script>
		var swiper = new Swiper('.swiper-container', {
        		pagination: '.swiper-pagination',
        		nextButton: '.swiper-button-next',
        		prevButton: '.swiper-button-prev',
        		paginationClickable: true,
        		spaceBetween: 20,
        		centeredSlides : true,
        		autoplay: 2500,
        		autoplayDisableOnInteraction: false
    	});
	</script>
</html>