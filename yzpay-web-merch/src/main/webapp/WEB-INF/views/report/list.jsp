<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../common/taglib/taglib.jsp"%>  
<input type="hidden" value="${baseURL }" id="allUrl"/>
<input type="hidden" id="duplicateLogin" autocomplete="off" value="${duplicateLogin}">
<c:set var="action" value="${action}" />
<!-- 查询数据明细 -->
<c:if test="${action == 'detail'}">
		<!-- 营业流水明细 -->
		<div class="aui-content si-tad_div" style="margin-top: -52px;" id="ls_detail_div">
			<ul class="aui-list aui-media-list">
				<li class="aui-list-item stream-info-item" >
					<div class="aui-media-list-item-inner">
						<div class="aui-list-item-inner">
							<div class="aui-list-item-text">
                        		<div class="aui-list-item-title">付款金额：<fmt:formatNumber type="currency" value="${payTranLs.transPrice}"/></div>
                    		</div>
						</div>
					</div>
				</li>
				<li class="aui-list-item stream-info-item" >
					<div class="aui-media-list-item-inner">
						<div class="aui-list-item-inner">
							<div class="aui-list-item-text">
                        		<div class="aui-list-item-title">订单金额：<fmt:formatNumber type="currency" value="${payTranLs.totalPrice}"/></div>
                    		</div>
						</div>
					</div>
				</li>
				<li class="aui-list-item stream-info-item" >
					<div class="aui-media-list-item-inner">
						<div class="aui-list-item-inner">
							<div class="aui-list-item-text">
                        		<div class="aui-list-item-title">优惠金额：<fmt:formatNumber type="currency" value="${payTranLs.totalPrice - payTranLs.transPrice}"/></div>
                    		</div>
						</div>
					</div>
				</li>
				<li class="aui-list-item stream-info-item" >
					<div class="aui-media-list-item-inner">
						<div class="aui-list-item-inner">
							<div class="aui-list-item-text">
                        		<div class="aui-list-item-title">交易单号：${payTranLs.user_order_no}</div>
                    		</div>
						</div>
					</div>
				</li>
				<li class="aui-list-item stream-info-item" >
					<div class="aui-media-list-item-inner">
						<div class="aui-list-item-inner">
							<div class="aui-list-item-text">
                        		<div class="aui-list-item-title">店铺名称：${payTranLs.storeName}</div>
                    		</div>
						</div>
					</div>
				</li>
				<li class="aui-list-item stream-info-item" >
					<div class="aui-media-list-item-inner">
						<div class="aui-list-item-inner">
							<div class="aui-list-item-text">
                        		<div class="aui-list-item-title">收银员：&nbsp;&nbsp;&nbsp;&nbsp;${payTranLs.cashier }</div>
                    		</div>
						</div>
					</div>
				</li>
				<li class="aui-list-item stream-info-item" >
					<div class="aui-media-list-item-inner">
						<div class="aui-list-item-inner">
							<div class="aui-list-item-text">
                        		<div class="aui-list-item-title">交易时间：<fmt:formatDate value="${payTranLs.transTime}" pattern="yy-MM-dd HH:mm:ss"/></div>
                    		</div>
						</div>
					</div>
				</li>
				<li class="aui-list-item stream-info-item" >
					<div class="aui-media-list-item-inner">
						<div class="aui-list-item-inner">
							<div class="aui-list-item-text">
                        		<div class="aui-list-item-title">交易状态：
									<c:if test="${payTranLs.status == '2'}"><font style="color:ff7b1a">已付款</font></c:if>
									<c:if test="${payTranLs.status == '3'}"><font style="color:#666">已退款</font></c:if>
									<c:if test="${payTranLs.status == '6'}"><font style="color:#666"></font>付款失败</c:if>
								</div>
                    		</div>
						</div>
					</div>
				</li>
				<div class="bb-submit-div" style="margin-top: -8px;">
				<div class="aui-content-padded" >
					<button type="button" class="aui-btn aui-btn-warning aui-btn-block" id="add-clerk" onclick="goback('ls')" >返回</button>
				</div>
				</div>
			</ul>
		</div>
		
		<!-- 营业日报明细 -->
		<div class="aui-content si-tad_div" style="margin-top: -52px;" id="report_detail_div">
			<ul class="aui-list aui-list-in si-aui-list">
				<li class="aui-list-item or-Total" >
					<div class="aui-list-item-inner" >   
                        <div>${store.storeName}</div> 
					</div>
				</li>
			</ul>
			
			<ul class="aui-list aui-list-in si-aui-list">
				<c:forEach var="item" items="${sumTransLs}" varStatus="i">
					<li  <c:if test="${i.index == 0 }"> class="aui-list-item or-Total" </c:if> <c:if test="${i.index > 0 }">  class="aui-list-item or-details" </c:if> >
					<div class="aui-list-item-inner" >   
                        <div>
                        	<c:if test="${item.channel == '总计'}">总计</c:if>
      						<c:if test="${item.channel == '1'}">支付宝</c:if>
            				<c:if test="${item.channel == '2'}">微信</c:if> 
            				<c:if test="${item.channel == '3'}">银联 </c:if>
            				<c:if test="${item.channel == '4'}"> 预存款</c:if>
                        </div>
                        <div>${item.countTran}笔</div>
                        <div><fmt:formatNumber value="${item.sumTran}" type="currency"/></div>
					</div>
					</li>
				</c:forEach>
				<div class="bb-submit-div">
				<div class="aui-row-padded">
					<div class="aui-content-padded" >
						<button type="button" class="aui-btn aui-btn-warning aui-btn-block" id="add-clerk" onclick="goback('report')" >返回</button>
					</div>
				</div>
				</div>
			</ul>
		</div>
</c:if>

<!-- 营业日报搜索框搜索到的数据和下拉刷新的数据-->
<c:if test="${action == 'search' }">  
	<ul class="aui-list aui-list-in si-aui-list" id="reportSearch_ul">
		<c:forEach var="item" items="${reportPageBean.recordList}" varStatus="i" > 
			<li  
			<c:if test="${i.index == 0}"> class="aui-list-item or-Total"   	</c:if>  
			<c:if test="${i.index >0 }">  
				class="aui-list-item or-details" onclick="detail('report','${item.merchantNo}','','${item.storeNo}')" 
			</c:if>>
            	<div class="aui-list-item-inner">
                	<div>${item.storeName}</div>
                	<div>${item.countTran}笔</div>
                	<div><fmt:formatNumber value="${item.sumTran}" type="currency"/></div>
            	</div>
        	</li>
        	</c:forEach>	
	</ul>
	
	<!-- 营业流水下拉刷新 查询到的数据 -->
	<ul class="aui-list aui-media-list" id="lsDownAction_ul">
			<c:if test="${lsPageBean.recordList.size() < 1}">
				<li class="aui-list-item stream-info-item">
					<div class="aui-media-list-item-inner">
						<div class="aui-list-item-inner">暂无数据...</div>
					</div>
				</li>
			</c:if>
			<c:if test="${lsPageBean.recordList.size() >= 1}">
				<c:forEach var="item" items="${lsPageBean.recordList}" varStatus="i">
						<li class="aui-list-item stream-info-item" onclick="detail('ls','${item.merchantNo}','${item.transNum}','')">
							<div class="aui-media-list-item-inner">
								<div class="aui-list-item-inner">
									<div class="aui-list-item-text">
                        			<div class="aui-list-item-title">${item.storeName}</div>
                        			<div class="aui-list-item-right or-stream-price"><fmt:formatNumber value="${item.transPrice}" type="currency"/></div>
                    				</div>
									<div class="aui-list-item-text or-stream-exp">
                        				<div class="aui-list-item-title">
                        					<c:if test="${item.channel == '1'}">支付宝</c:if>  
											<c:if test="${item.channel == '2'}">微信</c:if> 
											<c:if test="${item.channel == '3'}">银联</c:if>  
											<c:if test="${item.channel == '4'}">预存款</c:if>  
											&nbsp;&nbsp;
											<fmt:formatDate value="${item.transTime}" pattern="yy-MM-dd HH:mm:ss"/>
										</div>
                        				<div class="aui-list-item-right or-stream-state" ><!-- id="stream-state-s" -->
                        					<c:if test="${item.status == '2'}"><font style="color:ff7b1a">已付款</font></c:if>
											<c:if test="${item.status == '3'}"><font style="color:#666">已退款</font></c:if>
											<c:if test="${item.status == '6'}"><font style="color:#666"></font>付款失败</c:if>
                        				</div>
                    				</div>
								</div>
							</div>
						</li>
				</c:forEach>
			</c:if>
	</ul>
	
		<!-- 账单汇总查询到的数据 -->
		<ul class="aui-list aui-list-in si-aui-list" id="bill_ul"> 
			<c:forEach var="item" items="${billPageBean.recordList}" varStatus="i" > 
			<li <c:if test="${i.index == 0}"> class="aui-list-item or-Total" </c:if>   <c:if test="${i.index > 0}"> class="aui-list-item or-details" </c:if>  >
            	<div class="aui-list-item-inner">
                	<div>
                		<c:if test="${item.channel == '总计'}">总计</c:if>  
      					<c:if test="${item.channel == '1'}">支付宝</c:if>  
						<c:if test="${item.channel == '2'}">微信</c:if> 
						<c:if test="${item.channel == '3'}">银联</c:if>  
						<c:if test="${item.channel == '4'}">预存款</c:if>  
                	</div>
                	<div>${item.countTran}笔</div>
                	<div><fmt:formatNumber value="${item.sumTran}" type="currency"/></div>
            	</div>
        	</li>
        	</c:forEach>
		</ul>
</c:if>

<!-- 页面进入时的初始化数据 -->
<c:if test="${action == 'init'}">
<!DOCTYPE html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width,initial-scale=1.0"/>
	<meta name="format-detection" content="telephone=no,email=no,date=no,address=no">
	<title>营业流水</title>
	 <script src="${baseURL}/bootstrap/js/jquery-1.11.2.min.js"></script> 
	<link rel="stylesheet" type="text/css" href="${baseURL }/common/css/aui.css" />
	<link rel="stylesheet" type="text/css" href="${baseURL }/common/css/style.css?v=<%=System.currentTimeMillis() %>" />
	<link rel="stylesheet" href="${baseURL}/common/css/mescroll.css">
    <link rel="stylesheet" href="${baseURL}/common/css/mescroll-option.css">
    <script src="${baseURL}/common/js/plugin/jsBridge.js?v=<%=System.currentTimeMillis() %>"></script>
    <script src="${baseURL}/common/js/plugin/jsFunction.js?v=<%=System.currentTimeMillis() %>"></script>
    <script src="${baseURL}/common/js/plugin/mescroll.js" type="text/javascript" charset="utf-8"></script>
	<script src="${baseURL}/common/js/plugin/mescroll-option.js?v=<%=System.currentTimeMillis() %>" type="text/javascript" charset="utf-8"></script>
	<script src="${baseURL}/static/js/biz-js/tranLs.js?v=<%=System.currentTimeMillis() %>" type="text/javascript"></script>
	<script src="${baseURL}/common/js/common.js?v=<%=System.currentTimeMillis()%> "></script>
	<style type="text/css">
	#lsMescroll{
		position: fixed;
		top: 20px; 
		bottom: 5px;
		height: auto;
	}	
	#reportMescroll{
		position: fixed;
		top: 110px; 
		bottom: 5px;
		height: auto;
	}
	#billMescroll{
		position: fixed;
		top: 110px; 
		bottom: 5px;
		height: auto;
	}

	/*回到 顶部按钮*/
	.mescroll-totop {
		bottom: 70px;
	}

	</style>  
</head>
<body>
	<header class="aui-bar aui-bar-nav" style="position: fixed;">
    	<a class="aui-pull-left aui-btn" href='${baseURL }/sys/index/index' >
        	<span class="aui-iconfont aui-icon-left"></span>首页
    	</a>
    	<div class="aui-title">营业流水</div>
	</header>
	<div class="aui-tab si-aui-tab" id="tab" style="margin-top: 30px; position: fixed; width: 100% ">
    	<div class="aui-tab-item"  onclick="tabShow(this)"><span class="aui-active" id="ls_title">流水</span></div>
    	<div class="aui-tab-item" onclick="tabShow(this)" ><span id="report_title">日报</span></div>
    	<div class="aui-tab-item" onclick="tabShow(this)"><span id="bill_title">汇总</span></div>
	</div>
	<div class="classify_div" style="margin-top: 15px;">
		
		<!-- 上拉下拉滑动区域 -->
		<div id="lsMescroll" class="mescroll" >
		<!-- 营业流水 -->
		<div class="aui-content si-tad_div" id="stream-info">
			<ul class="aui-list aui-media-list">
				<c:if test="${pageBean.recordList.size() < 1}">
					<li class="aui-list-item stream-info-item">
					<div class="aui-media-list-item-inner">
						<div class="aui-list-item-inner">
							暂无数据...
						</div>
					</div>
					</li>
				</c:if>
				<c:if test="${pageBean.recordList.size() >= 1}">
					<c:forEach var="item" items="${pageBean.recordList}">
					<li class="aui-list-item stream-info-item" onclick="detail('ls','${item.merchantNo}','${item.transNum}','')">
					<div class="aui-media-list-item-inner">
						<div class="aui-list-item-inner">
							<div class="aui-list-item-text">
                        		<div class="aui-list-item-title">${item.storeName}</div>
                        		<div class="aui-list-item-right or-stream-price"><fmt:formatNumber value="${item.transPrice}" type="currency"/></div>
                    		</div>
							<div class="aui-list-item-text or-stream-exp">
                        		<div class="aui-list-item-title">
                        			<c:if test="${item.channel == '1'}">支付宝</c:if>  
									<c:if test="${item.channel == '2'}">微信</c:if> 
									<c:if test="${item.channel == '3'}">银联</c:if>  
									<c:if test="${item.channel == '4'}">预存款</c:if>  
									&nbsp;&nbsp;
									<fmt:formatDate value="${item.transTime}" pattern="yy-MM-dd HH:mm:ss"/>
								</div>
                        		<div class="aui-list-item-right or-stream-state" ><!-- id="stream-state-s" -->
                        			<c:if test="${item.status == '2'}"><font style="color:ff7b1a">已付款</font></c:if>
									<c:if test="${item.status == '3'}"><font style="color:#666">已退款</font></c:if>
									<c:if test="${item.status == '6'}"><font style="color:#666"></font>付款失败</c:if>
                        		</div>
                    		</div>
						</div>
					</div>
					</li>
					</c:forEach>
				</c:if>
			</ul>
		</div>
	</div>

		<!-- 营业日报 -->
		<div class="si-tad_div" id="date-info">
		<div class="aui-content-padded" style="position: fixed; width: 100%; margin-top: 100px;">
		<div class="date-info-SelectDate">
			<input type="text" id="transTime"  value="${transTime }" style=" width:55%; padding-left:10px;"  readonly="readonly" /> 
			<script src="${baseURL }/static/js/biz-js/dateUtil.js" type="text/javascript"></script>
			<button id="btnToday1" style="display: none;background-color: #4768f3;" onclick="showDate('今天','transTime');search()">今天</button>
			<button id="btnUpday1" style="display: none;background-color: #ff7b1a;" onclick="showDate('昨天','transTime');search()">昨天</button>
			<script src="${baseURL}/common/js/plugin/laydate/laydate.js" type="text/javascript"></script>
		</div>
		</div>
			<!-- 上拉下拉滑动区域 -->
		<div id="reportMescroll" class="mescroll">
		<div class="aui-content aui-margin-b-15" id="report_content">

		</div>
		</div>
		</div>
		<!-- 账单汇总 -->
		<div class="si-tad_div" id="bill-info">
		<div class="aui-content-padded" style="position: fixed; width: 100%;  margin-top: 100px;">
		<div class="date-info-SelectDate">
			<input type="text" id="transTime2" size="30" value="${transTime }"  style=" width:55%; padding-left:10px; padding-left: 10px;" readonly="readonly" /> <!-- border:1px solid #DDDDDD; -->
			<button id="btnToday2" style="display:none; background-color: #4768f3;" onclick="showDate('今天','transTime2');billSearch()">今天</button>
			<button id="btnUpday2"  style="display:none; background-color: #ff7b1a;" onclick="showDate('昨天','transTime2');billSearch()">昨天</button>
			

		</div>
		</div>
		<!-- 上拉、下拉滑动区域 -->
		<div id="billMescroll" class="mescroll">
		<div class="aui-content aui-margin-b-15" id="bill_content">
	
		</div>
		</div>
	</div>
	<div id="detail_div" style="display: none; margin-top: 135px; position: fixed;  width: 100%; "></div> <!-- 用于存放明细数据 -->
</div>
</body>
</html>
</c:if>
