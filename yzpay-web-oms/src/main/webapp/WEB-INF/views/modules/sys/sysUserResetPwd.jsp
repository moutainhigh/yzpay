<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../../common/taglib/taglib.jsp"%>
<script type="text/javascript">
var userUpdate = {
	// 删除后的回调函数，刷新当前页面
	navTabAjax: function(json) {
		//navTabAjaxDone(json);
		if(json.statusCode=='200')
		{
			$(this).dialog('closeCurrent');
			$(this).navtab('refresh');
			$(this).alertmsg('ok', '密码修改成功！');
		}else{
			 $(this).alertmsg('error', json.message);
		}
	}
};
</script>

<div class="pageContent">
	<form method="post" action="${baseURL}/sys/user/resetPwd" class="pageForm" data-toggle="validate" data-callback="userUpdate.navTabAjax">
		<div class="pageFormContent" layoutH="60">
			<input type="hidden" name="navTabId" value="czygl">
			<input type="hidden" name="callbackType" value="closeCurrent">
			<input type="hidden" name="forwardUrl" value="">
			
				<input type="hidden" name="id" value="${user.id}" />
				
				<div class="unit">
					<label class="control-label x90">操作员登录名：</label>
					<input type="text" value="${user.loginName }" readonly="readonly" size="30" />
				</div>
				<div class="unit" style="margin-top: 10px;">
					<label class="control-label x90">新密码：</label>
					<input type="password" id="newPwd" name="newPwd" class="required" minlength="6" maxlength="20" size="30" />
				</div>
				<div class="unit" style="margin-top: 10px;">
					<label class="control-label x90">确认新密码：</label>
					<input type="password" name="newPwd2" class="required" equalTo="#newPwd" minlength="6" maxlength="20" size="30" />
				</div>
				
				<div class="unit" style="margin-top: 10px; text-align: center;">
					<button type="submit" >保存</button>
				</div>
		</div>

	</form>
</div>
