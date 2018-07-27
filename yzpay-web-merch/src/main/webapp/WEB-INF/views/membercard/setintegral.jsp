<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../../common/taglib/taglib.jsp"%>
<!DOCTYPE HTML>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width,initial-scale=1.0"/>
	<meta name="format-detection" content="telephone=no,email=no,date=no,address=no">
	<title>消费设置</title>
	<link rel="stylesheet" type="text/css" href="${baseURL}/common/css/new/aui.css" />
	<link rel="stylesheet" type="text/css" href="${baseURL}/common/css/new/style.css?v=<%=System.currentTimeMillis() %>" />
	<script src="${baseURL}/common/js/plugin/jquery-2.1.4.min.js?v=<%=System.currentTimeMillis() %>"></script> 
	<script src="${baseURL}/common/js/new/api.js"></script>
	<script src="${baseURL}/common/js/plugin/jsFunction.js?v=<%=System.currentTimeMillis() %>"></script>
	<script src="${baseURL}/static/js/biz-js/integral.js?v=<%=System.currentTimeMillis() %>"></script>
	<script src="${baseURL}/common/js/common.js?v=<%=System.currentTimeMillis()%> "></script>
</head>
<body>
	<header class="aui-bar aui-bar-nav">
    	<a class="aui-pull-left aui-btn" onclick="location.href='${baseURL }/sys/membercard/show'">
        	<span class="aui-iconfont aui-icon-left"></span>会员管理
    	</a>
    	<div class="aui-title">消费设置</div>
	</header>
	<div class="aui-content aui-margin-b-15">
	<form  class="form-horizontal"  role="form" id="integral"  method="post"  action="${baseURL}/sys/membercard/setintegral">
	<input type="hidden" id="merchantNo" name="merchantNo" value="${integralRule.merchantNo}">
		<ul class="aui-list aui-form-list register-form">
			<li class="aui-list-item">
        		<div class="aui-list-item-inner">
            		<div class="aui-list-item-label">每消费<i>*</i></div>
            		<div class="aui-list-item-right settingPay-item-right">
            			<input type="tel" id="costMoneyUnit"  name="costMoneyUnit" value="${integralRule.costMoneyUnit}" placeholder="设置范围1-99999" maxlength="5"  onblur="check(this,1)" onkeydown="removeInfo(this)" ><label>元</label>
            		</div>
        		</div>
    		</li>
			<li class="aui-list-item">
        		<div class="aui-list-item-inner">
            		<div class="aui-list-item-label">赠送<i>*</i></div>
            		<div class="aui-list-item-right settingPay-item-right">
            			<input type="tel" id="increaseBonus" name="increaseBonus" value="${integralRule.increaseBonus}" placeholder="设置范围0-99999" maxlength="5"  onblur="check(this,2)" onkeydown="removeInfo(this)" ><label>分</label>
            		</div>
        		</div>
    		</li>
			<li class="aui-list-item">
        		<div class="aui-list-item-inner">
            		<div class="aui-list-item-label">单次上限</div>
            		<div class="aui-list-item-right settingPay-item-right">
            			<input type="tel" id="maxIncreaseBonus" name="maxIncreaseBonus" value="${integralRule.maxIncreaseBonus}" placeholder="单次赠送上限，0为无限制" maxlength="5" onblur="check(this,3)" onkeydown="removeInfo(this)" ><label>分</label>
            		</div>
        		</div>
    		</li>
			<li class="aui-list-item">
        		<div class="aui-list-item-inner">
            		<div class="aui-list-item-label">激活送积分</div>
            		<div class="aui-list-item-right settingPay-item-right">
            			<input type="tel" id="initIncreaseBonus" name="initIncreaseBonus" value="${integralRule.initIncreaseBonus}" placeholder="激活即赠送的积分数" maxlength="5"  onblur="check(this,0)" onkeydown="removeInfo(this)" ><label>分</label>
            		</div>
        		</div>
    		</li>
		</ul>
		<p id="message" style="margin-top: 10px;height: 15px;color: #fd4142;margin-left: 12px;font-size:16px; " ></p>
		<div class="aui-content-padded" style="margin-top:20px;">
			<button type="button" class="aui-btn aui-btn-success aui-btn-block" id="settingpay-btn" onclick="saveForm('integral')"><c:if test="${integralRule == null}">下一步</c:if> <c:if test="${integralRule != null}">保存</c:if></button>
		</div>
		</form>
	</div>
	<div class="pop-success">
		<div class="pop-success-content">
			<div class="pop-content" style="height: 7rem;padding-top: 1rem;">
				<div><img src="${baseURL}/common/images/new/ic_success_01.png" alt=""></div>
				<div class="">保存成功！</div>
			</div>
			<div class="pop-one-btn" style="margin-top: -1rem;">
                <button class="aui-btn aui-btn-success aui-btn-block" id="edit-finish" onclick="location.href='${baseURL }/sys/membercard/show'">返回</button>
            </div>
		</div>
	</div>
	<div class="pop-ajax" id="timeover">
		<div class="pop-ajax-content" >
			<%-- <img src='${baseURL}/common/css/images/ajax-loader.gif' alt="正在加载..."> --%>
		</div> 
	</div>
</body>
</html>