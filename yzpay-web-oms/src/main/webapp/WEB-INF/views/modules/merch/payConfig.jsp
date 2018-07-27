<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../../../common/taglib/taglib.jsp"%>
<div class="pageContent">
	<ul class="nav nav-tabs" id="payConfig_tab">
		<li class="active"><a href="#Wechat" data-toggle="tab">微信配置</a></li>
		<li><a href="#alipay" data-toggle="tab">支付宝配置</a></li>
	</ul>
	<div id="payConfig_tabContent" class="tab-content" style="width: 650;">
		<div class="tab-pane fade in active" id="Wechat" style="height: 550px;">  <!--  data-callback="MenuAdd.navTabAjax" -->
			<form method="post" id="Wechat_form" method="post" action="${baseURL}/sys/merchant/savePayConfig?merchantNo=${wechatConfig.merchantNo}&confType=wechat" class="pageForm" data-toggle="validate" >
				<input type="hidden" name="id" value="${wechatConfig.id}">
				<input type="hidden" name="status" value="${wechatConfig.status}">
				<div class="pageFormContent" layoutH="60" style="margin-left: 80px;">
					<p style="width: 99%">
						<label class="control-label x100" style="width: 100px;">Appid：</label>
						<input type="text"    name="appId" value="${wechatConfig.appId}" maxlength="20" style="width: 400px;"/>
						<span class="info"></span>
					</p><br>
					<p style="width: 99%">
						<label class="control-label x100" style="width: 100px;">微信商户号:</label>
						<input type="text" name="mchId" class="required"  maxlength="90" data-rule="required" value="${wechatConfig.mchId}" style="width: 400px;"/>
					</p><br>
					<p style="width: 99%">
						<label class="control-label x100" style="width: 100px;">apiSecret：</label>
						<input type="text" name="apiSecret" id=""  maxlength="90" style="width: 400px;" value="${wechatConfig.apiSecret}" />
					</p><br>
					<p style="width: 99%">
						<label class="control-label x100" style="width:100px;">appSecret：</label>
						<input type="text" name="appSecret" id=""  maxlength="90"  value="${wechatConfig.appSecret}" style="width: 400px;"/>
					</p><br>
					<p style="width: 99%">
						<label class="control-label x100" style="width:100px;">服务商：</label>
						<select id="parentMchNo" name="parentMchNo" class="selectpicker show-menu-arrow form-control" style="width: 400px;" >
            				<option value="" >请选择服务商</option>
        					<c:forEach var="item" items="${requestScope.wxParent}" varStatus="i" >
        					<option value="${item.merchantNo}" <c:if test="${wechatConfig.parentMchNo!=null and wechatConfig.parentMchNo==item.merchantNo}">selected="selected"</c:if> >${item.info}</option>
        					</c:forEach>
        				</select>
					</p><br>
					<p style="width: 99%">
						<label class="control-label x100" style="width: 100px;">微信小程序ID：</label>
						<input type="text" name="wxAppAppId" maxlength="50"  value=" ${wechatConfig.wxAppAppId}" style="width: 400px;" />
					</p><br><br>
					<p align="center">
					<button type="submit" class="btn-default" data-icon="save" style="margin-top: 20px;margin-left: -40px;">保存</button>
					<button type="button" class="btn-close" data-icon="close" style="margin-left: 100px; margin-top:20px; ">取消</button>
					
					</p>
				</div>
				
			</form>
		</div>
		
		<div class="tab-pane fade" id="alipay" style="height: 550px;">
				<form method="post" id="alipay_form" method="post" action="${baseURL}/sys/merchant/savePayConfig?merchantNo=${alipayConfig.merchantNo}&confType=alipay" class="pageForm" data-toggle="validate">
				<input type="hidden" name="id" value="${alipayConfig.id}">
				<input type="hidden" name="status" value="${alipayConfig.status}">
				<input type="hidden" id="merchantNo" name="merchantNo" value="${alipayConfig.merchantNo}">
				<div class="pageFormContent" layoutH="60" >
					<p style="width: 99%">
						<label class="control-label x90" style="width:150px;">支付宝商户号(PID)：</label>
						<input type="text" class="required" name="pid" data-rule="required" value="${alipayConfig.pid}" style="width: 550px;"/>
						<span class="info"></span>
					</p><br>
					<p style="width: 99%">
						<label class="control-label x90" style="width:150px;">门店应用appid:</label>
						<input type="text" class="required" data-rule="required" name="storeAppId" class="required" maxlength="90" data-rule="required" value="${alipayConfig.storeAppId}" style="width: 550px;"/>
					</p><br>
					<p style="width: 99%">
						<label class="control-label x90" style="width:150px;">支付宝公钥：</label>
						<textarea rows="4" class="required"  data-rule="required" id="alipayPublicKey" cols="55" name="alipayPublicKey"  class="required" data-rule="required" >${alipayConfig.alipayPublicKey}</textarea>
					</p><br>
					<p style="width: 99%">
						<label class="control-label x90" style="width:150px;">商户公钥：</label>
						<textarea rows="5" class="required"  data-rule="required" id="storeMerchantPublicKey" cols="55" name="storeMerchantPublicKey"  class="required" data-rule="required" >${alipayConfig.storeMerchantPublicKey}</textarea>
					</p><br>
					<p style="width: 99%">
						<label class="control-label x90" style="width:150px;">商户私钥：</label>
						<textarea rows="6" class="required" data-rule="required" id="merchantPrivateKey" cols="55" name="merchantPrivateKey"  class="required" data-rule="required" >${alipayConfig.merchantPrivateKey}</textarea>
					</p><br>
					<p align="center">
		    			<button type="submit" class="btn-default" data-icon="save">保存</button>
		    				<input type="button" class="btn-default" onclick="generateKey(0)" value="商户公私钥生成">
		    					<button type="button" class="btn-close" data-icon="close">取消</button>
		    		</p>
				</div>
				
			</form>
		</div>
	</div>
</div>