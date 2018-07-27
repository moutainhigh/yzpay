<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../../../common/taglib/taglib.jsp"%>
<div class="pageContent">
		<div> 
			<form method="post" class="pageForm"  enctype="multipart/form-data">
				<input type="hidden" value="${appCode}"  id="appCode" name="appCode"/>
				<div class="pageFormContent" layoutH="60" style="margin-left: 80px;">
					<p style="width: 99%">
						<label class="control-label x100" style="width: 100px;">版本号：</label>
						<input type="text"   name="verName" id="verName" class="required"  maxlength="10" data-rule="required" style="width: 400px;"/>
						<span class="info"></span>
					</p><br>
					<p style="width: 99%">
						<label class="control-label x100" style="width: 100px;">verCode:</label>
						<input type="text" name="verCode" id="verCode" class="required"  maxlength="10" data-rule="required" style="width: 400px;"/>
					</p><br>
					<p style="width: 99%">
						<label class="control-label x100" style="width: 100px;">app安装包：</label>
						<input type="file"  class="required" data-rule="required" name="appApk" id="appApk"  maxlength="90" style="width: 400px;"/>
					</p><br>
					
					<p style="width: 99%">
						<label class="control-label x100" style="width: 200px;" id="progress"></label>
					</p><br>
					
					<p align="center">
						<button type="button" id="butSubmit" onclick="ajaxUpload()" class="btn-default" data-icon="save" style="margin-top: 20px;margin-left: -40px;">上传</button>
						<button type="button" class="btn-close" data-icon="close" style="margin-left: 100px; margin-top:20px; ">取消</button>
					</p>
				</div>
			</form>
		</div>
		<script>
		function ajaxUpload(){
			//var url =  "http://siecompay.com/merch/appReleaseContr/apkUpload";
			var url =  "http://192.168.0.122:8082/merch/appReleaseContr/apkUpload";
		    var formData = new FormData();
		    var appCode = $("#appCode").val();
		    var verName = $("#verName").val();
		    var verCode = $("#verCode").val();
		    var appApk = $("#appApk").val();
		    var file = $("#appApk").get(0).files[0];
		    if( verName == "" || verCode == ""  ||  appApk == "" ){
		        alert("请完善数据");
		        return;
		    }    
		    formData.append('appCode',appCode);
		    formData.append('verName',verName);
		    formData.append('verCode',verCode);
		    formData.append('appApkFile',file);
		        $.ajax({    
		            url : url,    
		            type : 'POST',    
		            data : formData,    
		            beforeSend: function(){
		            	$('#butSubmit').attr("disabled","disabled");
		            	$("#progress").html("正在上传,请稍后...");	 
		            },
		            processData : false,  // 必须false才会避开jQuery对 formdata 的默认处理   
		            contentType : false,  // 必须false才会自动加上正确的Content-Type  
		            success : function(responseStr) {    
		            	if(responseStr == 'success'){
		            		$("#bjui-navtab").alertmsg('ok', '上传成功');
			               	$("#bjui-navtab").dialog('closeCurrent');
					    	$("#bjui-navtab").navtab('refresh');
		            	}
		            	else{
		            		$("#bjui-navtab").alertmsg('ok', '上传失败');
		            	}
		            },    
		            error : function(responseStr) {    
		                alert("失败:"+responseStr );
		            }    
		        });    
		}
		</script>
</div>