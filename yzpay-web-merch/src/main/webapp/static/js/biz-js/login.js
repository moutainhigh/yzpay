
//按钮置灰
function colorChg (name) {
	if ($("ul").find("p").length<=1&&$("#message").text()=="") {
  		$("#"+name).attr("disabled",false);
  		if ($("#"+name).prop("disabled") == false) {
  			$("#"+name).css("background","#4768f3");
  		} else {
  			$("#"+name).css("background","#bbbbbb");
  		}
  	}else {
  		$("#"+name).attr("disabled",true);
  		if ($("#"+name).prop("disabled") == false) {
  			$("#"+name).css("background","#4768f3");
  		} else {
  			$("#"+name).css("background","#bbbbbb");
  		}
	}	
}
/**
 * 登录界面JS
 */
	$(document).ready(function(){ 
	    var storage = window.localStorage; 
	    var getName = storage["loginName"];  
	    $("#loginName").val(getName); 
	}); 

//去除提示
function removeInfo(obj){
	$(obj).parent("div").parent("div").parent("li").find("p").remove();
	if ($("ul").find("p").length<=1) {
		$("#login-btn").attr("disabled",false);
		$("#message").text("");
		colorChg ("login-btn");
	}else{
		$("#login-btn").attr("disabled",true);
		colorChg ("login-btn");
	}
}

//登录
function loginTo(form)
{
	var flag = checkForm();
	var url = $('form').attr('action');
	var typeFlag = 1;
	if(flag)
	{
		$('#timeover').show();
		var password = $("#pwd").val();
		if(password != ""){
  			$("#loginPwd").val(password);
  			$("#pwd").val("");
  			if($("#pwd").attr("type") == "password"){
  				$("#pwd").attr("type","text");
  				typeFlag = 2;
  			}
  		}
		setTimeout(function(){
			$.ajax({
				  url:url,
				  type:'POST',
				  data:$('form').serialize(),
				  async:false,  
				  timeout:10000,   
				  dataType:'json',   
				  beforeSend:function(xhr){},
				  success:function(result){
						if (result.result_code=="SUCCESS") {
							$("#userCaptchaCode").val("");
							var loginName = $("#loginName").val();
							var storage = window.localStorage;
							//存储到loaclStage   
							storage["loginName"] = loginName;
			            	window.location.href=$("#basePath").val()+result.data.url; 	
						}else {
							if(typeFlag == 2){
  			    				$("#pwd").attr("type","password");
  			    			}
  			    			$("#pwd").val(password);
							$("#message").text(result.err_code_des);
							colorChg ("login-btn");
							changeCode();
						}
						$('#timeover').hide();
				   },
				   error:function(xhr,textStatus){
					   if(typeFlag == 2){
  			    			$("#pwd").attr("type","password");
  			    		}
  			    		$("#pwd").val(password);
					    $("#message").text("系统忙/网络异常,登录失败");
					    $('#timeover').hide();
				   },
				  // complete:function(){
				  // }
				});
		 },200);
	}else{
		return false;
	} 
}
  
// 表单数据验证
function checkForm()
{
	if($("#loginName").val() == ''||!isLoginName($("#loginName").val()))
	{
		$("#loginName").parent("div").parent("div").parent("li").find("p").remove();
		$("#loginName").parent("div").parent("div").after("<p>手机号码为空/格式不正确</p>");
		return false;
	}
	if($("#pwd").val() == ''||!isLoginPwd($("#pwd").val()))
	{
		$("#pwd").parent("div").parent("div").parent("li").find("p").remove();
		$("#pwd").parent("div").parent("div").after("<p>密码为空/格式不正确</p>");
		return false;
	}
	if($("#userCaptchaCode").val() == ''||!isCode($("#userCaptchaCode").val()))
	{
		/* $("#userCaptchaCode").parent("div").parent("div").parent("li").find("p").remove();
		$("#userCaptchaCode").parent("div").parent("div").after("<p>验证码为空/格式不正确</p>"); */
		$("#message").text("验证码为空/格式不正确");
		return false;
	}
	return true;
} 

//验证账号
function checkLoginName(obj)
{
	$(obj).parent("div").parent("div").parent("li").find("p").remove();
	if($(obj).val() == '')
	{
		$(obj).parent("div").parent("div").after("<p >请输入账号</p>");
		colorChg ("login-btn");
	}else if(isLoginName($(obj).val())== false){
		$(obj).parent("div").parent("div").after("<p >非可用的手机号码</p>");
		colorChg ("login-btn"); 
	}else{
		$(obj).parent("div").parent("div").parent("li").find("p").remove();
		colorChg ("login-btn");
	}
}
//验证密码
function checkLoginPwd(obj)
{
	$(obj).parent("div").parent("div").parent("li").find("p").remove();
	if($(obj).val() == '')
	{
		$(obj).parent("div").parent("div").after("<p>请输入密码</p>");
		colorChg ("login-btn");
	}else if(isLoginPwd($(obj).val())== false){
		$(obj).parent("div").parent("div").after("<p>密码应为6-15位数字+字母</p>");
		colorChg ("login-btn"); 
	}else{
		$(obj).parent("div").parent("div").parent("li").find("p").remove();
		colorChg ("login-btn");
	}
}
//验证验证码
function checkCode(obj)
{
	if($(obj).val() == '')
	{
		$("#message").text("请输入验证码");
		colorChg ("login-btn");
	}else if(isCode($(obj).val())== false){
		$("#message").text("验证码应为4位数字");
		colorChg ("login-btn"); 
	}else{
		$("#message").text("");
		colorChg ("login-btn");
	}
}

//账号正则
function isLoginName(LoginName)
{
	var pattern = /^(1[3|4|5|7|8][0-9]{9})$/;
	if(pattern.test(LoginName))
	{
		return true;
	}else{
		return false;
	}
} 
//密码正则
function isLoginPwd(LoginPwd)
{
	var pattern = /^((?!^[0-9]+$)(?!^[^0-9]+$)(?!^[a-zA-Z]+$)(?!^[^a-zA-Z]+$))\w{6,15}$/;
	if(pattern.test(LoginPwd))
	{
		return true;
	}else{
		return false;
	}
} 
//验证码正则
function isCode(userCaptchaCode)
{
	var pattern = /^[0-9]{4}$/;
	if(pattern.test(userCaptchaCode))
	{
		return true;
	}else{
		return false;
	}
} 
//发送验证码
function changeCode(){
	var basePath = $("#basePath").val();
	$("#captcha_img").attr("src",basePath+'/rcCaptcha.jpg?'+ new Date().getTime());	
	$("#userCaptchaCode").val("");
}