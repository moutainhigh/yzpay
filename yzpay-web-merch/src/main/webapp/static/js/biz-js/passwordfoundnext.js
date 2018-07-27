  
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
	//去除提示
	  function removeInfo(obj){
	  	$(obj).parent("div").parent("div").parent("li").find("p").remove();
	  	if ($("ul").find("p").length<=1) {
	  		$("#message").text("");
	  		$("#nextBtn").attr("disabled",false);
	  		colorChg ("nextBtn");
	  	}else{
	  		$("#nextBtn").attr("disabled",true);
	  		colorChg ("nextBtn");
	  	}	
	  }
	  
		//提交
		function nextTo(form)
		{
			var flag = checkForm();	
			if(flag)
			{
				$('#timeover').show();
				setTimeout(function(){
					$.ajax({
						  url:$('form').attr('action'),
						  type:'POST',
						  data:$('form').serialize(),
						  async:false,  
						  timeout:10000,   
						  dataType:'json',   
						  beforeSend:function(xhr){},
						  success:function(result){
							  	$('#timeover').hide();
							  	if (result.result_code=="SUCCESS") {
							  		var loginName = $("#phone").val();
									var storage = window.localStorage;
									//存储到loaclStage   
									storage["loginName"] = loginName;
									$('.pop-success').show();	
								}else {
									$("#message").text(result.err_code_des);
									colorChg ("nextBtn");
								}
						   },
						   error:function(xhr,textStatus){
							  $('#timeover').hide();
							  $("#message").text("系统忙/网络异常");
						   },
						});
				 },200);
			}else{
				return false;
			} 
		}
		
		// 表单数据验证
		function checkForm()
		{
			if($("#loginPwd").val() == ''||!isTrue("loginPwd",$("#loginPwd").val()))
			{
				$("#loginPwd").parent("div").parent("div").parent("li").find("p").remove();
				$("#loginPwd").parent("div").parent("div").after("<p>密码为空/格式不正确</p>");
				colorChg ("nextBtn");
				return false;
			}
			if($("#password").val() == ''||!isTrue("password",$("#password").val()))
			{
				$("#password").parent("div").parent("div").parent("li").find("p").remove();
				$("#password").parent("div").parent("div").after("<p>密码为空/格式不正确</p>");
				colorChg ("nextBtn");
				return false;
			}
			if($("#password").val() != $("#loginPwd").val())
			{
				$("#password").parent("div").parent("div").parent("li").find("p").remove();
				$("#password").parent("div").parent("div").after("<p>两次输入的密码不一致</p>");
				colorChg ("nextBtn");
				return false;
			}
			return true;
		} 
		//验证密码
		function checkPwd(obj) {
			var loginPwd = $("#loginPwd").val();
			var password = $("#password").val();
			if (password=="") {
				check(obj,1);
			} else {
				check(obj,1);
				if (loginPwd != password) {
					/* $(obj).parent("div").parent("div").parent("li").find("p").remove(); */
					$("#password").parent("div").parent("div").parent("li").find("p").remove();
					$("#password").parent("div").parent("div").after("<p>两次输入的密码不一致</p>");
					colorChg ("nextBtn");
				} else {
					$("#password").parent("div").parent("div").parent("li").find("p").remove();
					colorChg ("nextBtn");
				}
			}
		}
		//验证确认密码
		function checkClick(obj) {
			var loginPwd = $("#loginPwd").val();
			var password = $("#password").val();
			if (!isTrue("loginPwd",loginPwd)) {
				$(obj).parent("div").parent("div").parent("li").find("p").remove();
				$("#loginPwd").parent("div").parent("div").parent("li").find("p").remove();
				$("#loginPwd").parent("div").parent("div").after("<p>请先输入正确格式密码</p>");
				colorChg ("nextBtn");
				$(obj).parent("div").parent("div").parent("li").find("p").remove();
			} 
		}
		//验证确认密码
		function checkPassword(obj) {
			var loginPwd = $("#loginPwd").val();
			var password = $("#password").val();
				if (loginPwd != password) {
					$(obj).parent("div").parent("div").parent("li").find("p").remove();
					$(obj).parent("div").parent("div").after("<p>两次输入的密码不一致</p>");
					colorChg ("nextBtn");
				} else {
					check(obj,1);
				}
		}

		function check(obj,type) {
			$(obj).parent("div").parent("div").parent("li").find("p").remove();
			var msg1="";
			var msg2="";
			if ($(obj).attr("id")=="userName") {
				msg1="姓名不能为空";
				msg2="姓名应为2-8个文字，不能含有数字";
			}
			if ($(obj).attr("id")=="phone") {
				msg1="手机号码不能为空";
				msg2="手机号码格式不正确";
			}
			if ($(obj).attr("id")=="loginPwd") {
				msg1="密码不能为空";
				msg2="密码应为6-15位数字+字母";
			}
			if ($(obj).attr("id")=="password") {
				msg1="需再次输入密码确认";
				msg2="密码应为6-15位数字+字母";
			}
			if ($(obj).attr("id")=="phoneCode") {
				msg1="手机验证码不能为空";
				msg2="手机验证码应为4位数字";
			}
			if ($(obj).attr("id")=="agreement") {
				msg1="请阅读并同意《至高通信商户协议》";
				msg2="请阅读并同意《至高通信商户协议》";
			}
			var id = $(obj).attr("id");
			var value = $(obj).val();
			if (type==0) {
				if($(obj).val() != '' && isTrue(id,value)== false)
				{
					$(obj).parent("div").parent("div").after("<p>"+msg2+"</p>");
					colorChg ("nextBtn");
				}else{
					$(obj).parent("div").parent("div").parent("li").find("p").remove();
					colorChg ("nextBtn");
				}
			}
			if (type==1) {
				if($(obj).val() == '')
				{
					$(obj).parent("div").parent("div").after("<p>"+msg1+"</p>");
					colorChg ("nextBtn");
				}else if(isTrue(id,value)== false){
					$(obj).parent("div").parent("div").after("<p>"+msg2+"</p>");
					colorChg ("nextBtn"); 
				}else{
					$(obj).parent("div").parent("div").parent("li").find("p").remove();
					colorChg ("nextBtn");
				}
			}
			if (type==2) {
				if($(obj).prop("checked") != true)
				{
					$("#message").text(msg1);
					colorChg ("nextBtn");
				}else{
					$("#message").text("");
					colorChg ("nextBtn");
				}
			}
			
		}

		//正则
		function isTrue(id,value)
		{
			var pattern = "";
			if (id=="userName") {
				pattern = /^([a-zA-Z\s\u4e00-\u9fa5]){2,16}$/;
			}
			if (id=="phone") {
				pattern = /^(1[3|4|5|7|8][0-9]{9})$/;
			}
			if (id=="loginPwd") {
				pattern = /^((?!^[0-9]+$)(?!^[^0-9]+$)(?!^[a-zA-Z]+$)(?!^[^a-zA-Z]+$))\w{6,15}$/;
			}
			if (id=="password") {
				pattern = /^((?!^[0-9]+$)(?!^[^0-9]+$)(?!^[a-zA-Z]+$)(?!^[^a-zA-Z]+$))\w{6,15}$/;
			}
			if (id=="phoneCode") {
				pattern = /^[0-9]{4}$/;
			}	

			if(pattern.test(value))
			{
				return true;
			}else{
				return false;
			}
		} 