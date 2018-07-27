<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
  <jsp:include page="../../../common/taglib/taglib.jsp" />  
 <head>
 	<title>商家注册</title> 
	<meta charset="UTF-8">
	<meta name="viewport" content="maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width,initial-scale=1.0"/>
	<meta name="format-detection" content="telephone=no,email=no,date=no,address=no">
	<link rel="stylesheet" type="text/css" href="${baseURL}/common/css/new/aui.css" />
	<link rel="stylesheet" type="text/css" href="${baseURL}/common/css/new/style.css?v=<%=System.currentTimeMillis() %>" />
	<script src="${baseURL}/common/js/plugin/jquery-2.1.4.min.js"></script> 
	<script src="${baseURL}/common/js/new/api.js"></script>
	<script src="${baseURL}/static/js/biz-js/merchantregister.js?v=<%=System.currentTimeMillis()%>"></script>
	<script src="${baseURL}/common/js/common.js?v=<%=System.currentTimeMillis()%> "></script>
	<style>
		body,html{
			background-color: #fff;
		}
	</style>
</head>
<body>
	<div class="aui-bar aui-bar-nav" style="position: fixed;" id="contenthead">
    	<a class="aui-pull-left aui-btn" onclick="$('#loginPwd').val('');$('#pwd').val('');location.href='${baseURL}/'">
        	<span class="aui-iconfont aui-icon-left"></span>登录
    	</a>
    	<div class="aui-title">商家注册</div>
	</div>
	<div class="" id="content" style="margin-top: 45px;">
	<input type="hidden" value="${baseURL}" id="basePath" />
	<input type="hidden" value="${sendCodeTime}" id="sendCodeTime" />
    <form id="register" name="register" role="form"  action="${baseURL}/register" method="post" style="width: 100%">
	<ul class="aui-list aui-form-list register-form">
		<li class="aui-list-item login-list-item">
            <div class="aui-list-item-inner">
                <div class="aui-list-item-label">
                	    姓名
                </div>
                <div class="aui-list-item-input">
                    <input type="text" id="userName" name="userName" placeholder="请填写本人真实姓名" onblur="check(this,0)" onkeydown="removeInfo(this)" maxlength="8">
                </div>
            </div>
        </li>
		<li class="aui-list-item login-list-item">
            <div class="aui-list-item-inner">
                <div class="aui-list-item-label">
                   	 手机号
                   	<i>*</i>
                </div>
                <div class="aui-list-item-input">
                    <input type="tel" id="phone" name="phone" placeholder="请输入手机号码" onblur="checkPhone(this)" onkeydown="removeInfo(this)" maxlength="11">
                </div>
            </div>
        </li>
        <li class="aui-list-item login-list-item">
            <div class="aui-list-item-inner">
                <div class="aui-list-item-label">
                	    密码
                	 <i>*</i>
                </div>
                <div class="aui-list-item-input">
                	<input type="hidden" id="loginPwd" name="loginPwd" autocomplete="off" >
                    <input type="password" autocomplete="off" id="pwd" name="loginPwd" placeholder="6-15位数字+字母"  onblur="checkPwd(this)" onkeydown="removeInfo(this)" maxlength="15">
            		<img src="${baseURL}/common/images/new/password-off.png" alt="" class="password_sh" data-password="0" id="fir" onclick="pwdShow(this)">
                </div>
            </div>
        </li>
        <li class="aui-list-item login-list-item">
            <div class="aui-list-item-inner">
                <div class="aui-list-item-label">
                 	   确认密码
                 	 <i>*</i>
                </div>
                <div class="aui-list-item-input">
                    <input type="password" id="password" name="password" placeholder="请再次输入密码"  onblur="checkPassword(this)" onkeydown="removeInfo(this)" maxlength="15">
                    <img src="${baseURL}/common/images/new/password-off.png" alt="" class="password_sh" data-password="0" id="sen" onclick="pwdShow(this)">
                </div>
            </div>
        </li>
        <li class="aui-list-item login-list-item">
            <div class="aui-list-item-inner">
                <div class="aui-list-item-label">
              	      验证码
              	  <i>*</i>
                </div>
                <div class="aui-list-item-input">
                    <input type="tel" id="phoneCode" name="phoneCode" placeholder="请输入手机验证码" onblur="check(this,1)" onkeydown="removeInfo(this)" maxlength="4">
            		<button type="button" class="aui-btn aui-btn-info aui-btn-block aui-btn-outlined aui-btn-sm" id="obtain-ma" onclick="getPhone()" style="width: 95px;height: 40px;margin-top: -8px;">获取验证码</button>
                </div>
            </div>
        </li>
        <li class="aui-list-item login-list-item">
            <div class="aui-list-item-inner" style="margin-top:10px;border:none;">
                <input class="aui-checkbox" type="checkbox" id="agreement" name="agreement" value="true" checked="checked" onclick="check(this,2)" readonly="readonly">
            </div>
            <div style="float: left;margin-left: 25px;margin-top: -35px;position: relative;z-index: 90;display: inline;"> &nbsp;同意
                <label onclick=" $('#agreementshow').show();$('#content').hide();$('#contenthead').hide();" style="color: #4768f3;margin-left: -10px;">《杭州盈承商户协议》</label>
            </div>
        </li>
        <li class="aui-list-item login-list-item">
        	<p id="message" style="margin-top: 5px;height: 15px;" ></p>
        	<button type="button" id="registerBtn" class="aui-btn aui-btn-success aui-btn-block"  style="border: none;margin-top: 10px;" onclick="registerTo('register')">注册</button>
        </li>
	</ul>
	</form>
	<div class="pop-success">
		<div class="pop-success-content">
			<div class="pop-success-content-title">恭喜您注册成功</div>
			<div class="aui-btn aui-btn-success aui-btn-block" id="go-perfect-info" onclick="location.href='${baseURL}/sys/formContr/form'" >去完善资料开启云收款</div>
			<div class="pop-success-content-exp"><a href="${baseURL}/sys/index/indexundo">不，再看看</a></div>
		</div>
	</div>
	</div>
	<div class="pop-ajax" id="timeover">
		<div class="pop-ajax-content" >
			<%-- <img src='${baseURL}/common/css/images/ajax-loader.gif' alt="正在加载..."> --%>
		</div> 
	</div>
		<div id="agreementshow" style="width: 100%;height: 100%;margin-top: 20px;" hidden="hidden" >
		<h2 align="center">杭州盈承商户协议</h2><br>
		<p> 
			本协议由缔约双方在自愿、平等、公平及诚实信用原则的基础上，根据《中华人民共和国合同法》等相关法律、法规的规定，经友好协商缔结。
		</p>
		<p>
			甲方（入驻商户）：
		</p>
		<p>
			乙方（电商交易平台）：
		</p>
		<p>
			乙方提供电子优惠券的交易平台，甲方自愿入驻，并享受电子优惠券交易而带来的客流、会员积分和抵扣兑换等服务。为了明确双方的权利和义务，特签订本服务协议。
		</p>
		<h4>第一条 相关定义及解释</h4>
		<p>
			1. 商户入驻注册：商户注册指欲成为“本平台”经营者的商户，依据入驻流程和要求完成在线信息提交，继而开展经营活动；
		</p>
		<p>
			2. 电商交易平台：通过互联网为商户提供的商品服务、电子优惠券等营销活动、会员积分和抵扣兑换的电子交易系统。
		</p>
		
		<h4>第二条 服务内容及商户经营方式</h4>
		<p>
			1. 商户主页内优惠券或服务，均以商户自身名义进行商户信息上传、展示，创建商户优惠券等；
		</p>
		<p>
			2. 消费者在拿到商户发出的优惠券时，商户应当提供优惠券上明示的优惠产品或服务。
		</p>
		<p>
			3. 商户销售及服务出现争议、纠纷、国家机关机构调查时，由商户处理。本平台不参与商户的经营，除依据本协议相关约定外，也不直接介入商户与消费者等其他人之间的争议和纠纷。
		</p>
		
		<h4>第三条 入驻条件及证明文件提交</h4>
		<p>
			商户申请成为“电商交易平台”的入驻商户，在本平台开展经营活动，须满足以下条件：
		</p>
		<p>
			1. 商户已依照中华人民共和国法律注册并领取合法有效的营业执照及其他经营许可；
		</p>
		<p>
			2. 商户申请经营的优惠券或服务合法，资质齐全；
		</p>
		<p>
			3. 商户同意本协议及本平台相关规则。
		</p>
		<h4>第四条 争议解决</h4>
		<p>
			履行本协议过程中产生的任何争议，双方应协商解决，协商不成的，可向本协议签署地人民法院提起诉讼。
		</p>
		<p>
			本协议的签订、解释、变更、履行及争议的解决等均适用中华人民共和国大陆地区现行有效的法律。
		</p>
		<p>
			商户入驻服务协议的最终解释权归本平台所有。
		</p><br><br>
		<div style="margin-bottom: 20px;text-align: center;">
			<button type="button"  class="aui-btn aui-btn-success" id="yesBtn" style="border: none;height: 40px;width: 80px;font-size: 16px;" onclick="yesfn()" >同意</button>&nbsp;&nbsp;&nbsp;&nbsp;
			<button type="button"  class="aui-btn aui-btn-success" id="noBtn" style="border: none;height: 40px;width: 80px;background-color: #ff7b1a;font-size: 16px;" onclick="nofn()">返回</button>
		</div><br><br>
	</div>
</body> 
</html>