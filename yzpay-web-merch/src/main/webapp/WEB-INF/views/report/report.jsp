<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../common/taglib/taglib.jsp"%>  
<input type="hidden" value="${baseURL }" id="allUrl"/>
<!DOCTYPE html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width,initial-scale=1.0"/>
	<meta name="format-detection" content="telephone=no,email=no,date=no,address=no">
	<title>营业报表</title>
	 <script src="${baseURL}/bootstrap/js/jquery-1.11.2.min.js"></script> 
	<link rel="stylesheet" type="text/css" href="${baseURL }/common/css/aui.css" />
	<link rel="stylesheet" type="text/css" href="${baseURL }/common/css/style.css?v=<%=System.currentTimeMillis() %>" />
	<link rel="stylesheet" href="${baseURL}/common/css/mescroll.css">
    <link rel="stylesheet" href="${baseURL}/common/css/mescroll-option.css">
    
    <script src="${baseURL}/common/js/plugin/mescroll.js" type="text/javascript" charset="utf-8"></script>
	<script src="${baseURL}/common/js/plugin/mescroll-option.js?v=<%=System.currentTimeMillis() %>" type="text/javascript" charset="utf-8"></script>
	<script src="${baseURL }/static/js/biz-js/dateUtil.js" type="text/javascript"></script>
	<script src="${baseURL}/common/js/plugin/laydate/laydate.js" type="text/javascript"></script>
	<script src="${baseURL}/common/js/common.js?v=<%=System.currentTimeMillis()%> "></script>
	<script src="${baseURL}/common/js/plugin/echarts-3.0.common.min.js"  charset="UTF-8"></script>
	<script src="${baseURL}/static/js/biz-js/tranReport.js?v=<%=System.currentTimeMillis() %>" type="text/javascript"></script>
	<script src="${baseURL}/common/js/plugin/jsFunction.js?v=<%=System.currentTimeMillis() %>"></script>
	<script src="${baseURL}/common/js/plugin/jsBridge.js?v=<%=System.currentTimeMillis() %>"></script>
	
	<style type="text/css">
	#reportMescroll{
		position: fixed;
		top: 110px; 
		bottom: 5px;
		height: auto;
	}
	#reportMescroll2{
		position: fixed;
		top: 40px; 
		bottom: 5px;
		height: auto;
	}
	#reportMescroll3{
		position: fixed;
		top: 40px; 
		bottom: 5px;
		height: auto;
	}
	#reportMescroll4{
		position: fixed;
		top: 40px; 
		bottom: 5px;
		height: auto;
	}
	</style>  
</head>
<body>
	<header class="aui-bar aui-bar-nav" style="position: fixed;">
    	<a class="aui-pull-left aui-btn" href='${baseURL }/sys/index/index'  >
        	<span class="aui-iconfont aui-icon-left"></span>首页
    	</a>
    	<div class="aui-title">营业报表</div>
	</header>
	<div class="aui-tab si-aui-tab" id="tab" style="margin-top: 60px; position: fixed; width: 100% ">
    	<div class="aui-tab-item"  onclick="tabShow(this)"><span class="aui-active" >按时间</span></div>
    	<div class="aui-tab-item" onclick="tabShow(this)" ><span>按日</span></div>
    	<div class="aui-tab-item" onclick="tabShow(this)"><span>按周</span></div>
    	<div class="aui-tab-item" onclick="tabShow(this)"><span>按月</span></div>
	</div>
	<input type="hidden" id="duplicateLogin" autocomplete="off" value="${duplicateLogin}">
	<div class="classify_div" >
		<!-- 按时间段展示的图形报表 -->
		<div class="si-tad_div" id="dayReport-info">
		<div class="aui-content-padded" style="position: fixed; width: 100%;   margin-top: 120px;">
		<div class="date-info-SelectDate">
			<input type="text" id="transTime3" size="30" value="${transTime }"  style="border:1px solid #DDDDDD; width:55%; padding-left:10px;"  readonly="readonly"/>
			<button id="SelectDate-today" onclick="showDate('今天','transTime3');sumTrans();time_report();">今天</button>
			<button id="SelectDate-yesterday" onclick="showDate('昨天','transTime3');sumTrans();time_report();">昨天</button>
		</div>
		</div>
		<!-- 上拉、下拉滑动区域 -->
		<div id="reportMescroll" class="mescroll">
		<div class="aui-content aui-margin-b-15" id="dayReport_content">
		<ul class="aui-list aui-list-in si-aui-list"> 
			<li  class="aui-list-item or-Total">
            	<div class="aui-list-item-inner" id="sumTranDiv">
            		<c:if  test="${sumTrans == null}"> 
            			总计&nbsp;&nbsp;&nbsp;0笔&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            			￥0.0
            		</c:if>
            		<c:if test="${sumTrans != null }">
            			总计&nbsp;&nbsp;&nbsp;${sumTrans.countTran }笔&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            			<fmt:formatNumber value="${sumTrans.sumTran}" type="currency"/>
            		</c:if>
            	</div>
        	</li>
			<li  class="aui-list-item or-Total">
            	<div class="aui-list-item-inner" >
            		<!-- 图形报表数据区域 -->
                	<div id="chartContainer" style="width: 100%; height: 280px;">
                	
                	</div>
            	</div>
        	</li>
		</ul>
		</div>
		</div>
		</div> 
		
		<!-- 按日展示的图标报表 -->
		<div class="si-tad_div" id="dayReport2-info">
		<div class="aui-content-padded" style="position: fixed; width: 100%;   margin-top: 10px;">
		</div>
		<!-- 上拉、下拉滑动区域 -->
		<div id="reportMescroll2" class="mescroll">
		<div class="aui-content aui-margin-b-15" id="dayReport2_content">
		<ul class="aui-list aui-list-in si-aui-list"> 
			<li  class="aui-list-item or-Total">
            	<div class="aui-list-item-inner" id="dayReport2Div">
            		
            	</div>
        	</li>
			<li  class="aui-list-item or-Total">
            	<div class="aui-list-item-inner" >
            		<!-- 图形报表数据区域 -->
                	<div id="chartContainer2" style="width: 100%; height: 280px;">
                	
                	</div>
            	</div>
        	</li>
		</ul>
		</div>
		</div>
		</div> 
		
		<!-- 按周展示的图标报表 -->
		<div class="si-tad_div" id="dayReport3-info">
		<div class="aui-content-padded" style="position: fixed; width: 100%;   margin-top: 10px;">
		</div>
		<!-- 上拉、下拉滑动区域 -->
		<div id="reportMescroll3" class="mescroll">
		<div class="aui-content aui-margin-b-15" id="dayReport3_content">
		<ul class="aui-list aui-list-in si-aui-list"> 
			<li  class="aui-list-item or-Total">
            	<div class="aui-list-item-inner" id="dayReport3Div">
            		
            	</div>
        	</li>
			<li  class="aui-list-item or-Total">
            	<div class="aui-list-item-inner" >
            		<!-- 图形报表数据区域 -->
                	<div id="chartContainer3" style="width: 100%; height: 280px;">
                	
                	</div>
            	</div>
        	</li>
		</ul>
		</div>
		</div>
		</div> 
		
		<!-- 按月展示的图标报表 -->
		<div class="si-tad_div" id="dayReport4-info">
		<div class="aui-content-padded" style="position: fixed; width: 100%;   margin-top: 10px;">
		</div>
		<!-- 上拉、下拉滑动区域 -->
		<div id="reportMescroll4" class="mescroll">
		<div class="aui-content aui-margin-b-15" id="dayReport4_content">
		<ul class="aui-list aui-list-in si-aui-list"> 
			<li  class="aui-list-item or-Total">
            	<div class="aui-list-item-inner" id="dayReport4Div">
            		
            	</div>
        	</li>
			<li  class="aui-list-item or-Total">
            	<div class="aui-list-item-inner" >
            		<!-- 图形报表数据区域 -->
                	<div id="chartContainer4" style="width: 100%; height: 280px;" >
                	
                	</div>
            	</div>
        	</li>
		</ul>
		</div>
		</div>
		</div> 
	</div>
</body>
</html>