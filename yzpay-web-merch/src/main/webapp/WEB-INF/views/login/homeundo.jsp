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
  	<link rel="stylesheet" type="text/css" href="${baseURL}/common/css/new/style.css?v=<%=System.currentTimeMillis() %>" />
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
    	<a href="${baseURL}/sys/formContr/form"><img class="header-user-img" style="height: 3rem;width: 3rem;" src="${baseURL}/common/images/new/unic_head.png" alt=""></a>
    	<div class="header-user-title" style="margin-top: 0.4rem;font-size:18px !important; ">${merchUser.userName }&nbsp;&nbsp;&nbsp;&nbsp;${merchStatus }</div>
    	<div onclick="window.location.href='${baseURL}/logout'" style="float: right;margin-top:-1.7rem;font-size:18px;  z-index: 998;border: none; margin-right: 0.5rem;width: 3rem;">
    	<img alt="" src="${baseURL}/common/images/new/logout.png" id="logout" style="width: 1rem;float: left;"><div style="z-index: 999;float: right;margin-top: -0.8rem;">退出</div>
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
        					<div class="goods_swiper_img"><img src="${baseURL}/common/images/new/banner.png" alt="" style="height: 100%;"></div>
        				</div>
        				<div class="swiper-slide" onclick="" style="height: 100%;">
        					<div class="goods_swiper_img"><img src="${baseURL}/common/images/new/banner.png" alt="" style="height: 100%;"></div>
        				</div>
    				</div>
				<div class="swiper-pagination"></div>
    		</div>
	</div>

	<div class="home_exp">2017年3月1号至8月1号刷微信有鼓励金领啦有鼓励金领啦</div>
	<ul class="home-icon-list" style="margin-top: 10px;">     							
		<li class="home-icon-item" onclick="$('#alertDiv').show();">  
			<img src="${baseURL }/common/images/new/unshop-icon.png">
			<span>店铺管理</span>
		</li>
		<li class="home-icon-item" onclick="$('#alertDiv').show();">  
			<img src="${baseURL}/common/images/new/unvip-icon.png">
			<span>会员管理</span>
		</li>
		<li class="home-icon-item" onclick="$('#alertDiv').show();">
			<img src="${baseURL}/common/images/new/unyyls-icon.png">
			<span>营业流水</span>
		</li>
		<li class="home-icon-item" style="margin-top: 10px;" onclick="$('#alertDiv').show();">
			<img src="${baseURL}/common/images/new/unyy-icon.png">
			<span>营业报表</span>
		</li>
	</ul>
	<div class="pop-success" id="alertDiv">
		<div class="pop-success-content">
			<input type="hidden" id="msgset" value="">
			<div class="pop-content" style="padding-top:50px;">
				<div class="" style="color: #fd4142;">抱歉,您还未完善资料,<br> 不能使用云收款业务!</div>
			</div>
			<div class="pop-footer">
				<button id="KeepOn" onclick="location.href='${baseURL}/sys/formContr/form'" style="color: #4768f3;">现在完善</button>
				<button id="Return-index" onclick="$('#alertDiv').hide();" style="color: #666666;">不，再看看</button>
			</div>
		</div>
	</div>
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