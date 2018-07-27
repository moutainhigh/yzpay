
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

function yesfn() {
	$('#agreementshow').hide();
	$('#content').show();
	$('#contenthead').show();
	$('#agreement').prop({'checked':true});
	$("#message").text("");
	colorChg ("registerBtn");
}
function nofn() {
	$('#agreementshow').hide();
	$('#content').show();
	$('#contenthead').show();
	$('#agreement').attr({'checked':false});
	$("#message").text("请阅读并同意《杭州盈承商户协议》");
	colorChg ("registerBtn");
}


//去除提示
  function removeInfo(obj){
  	$(obj).parent("div").parent("div").parent("li").find("p").remove();
  	if ($("ul").find("p").length<=1) {
  		$("#message").text("");
  		$("#registerBtn").attr("disabled",false);
  		colorChg ("registerBtn");
  	}else {
  		$("#registerBtn").attr("disabled",true);
  		colorChg ("registerBtn");
	}	
  }
  var wait = 90;
  //发送验证码
	function sendCode(){
	  	 $("#phoneCode").val(""); 
		var basePath = $("#basePath").val();
		var phone = $("#phone").val();
		var o = $("#obtain-ma");
		$("#obtain-ma").attr("src",basePath+'/phoneCode?'+ phone);
		setTimeout(function(){
			$.ajax({
		    	type: 'post',
		   		url: basePath+'/phoneCode',
		    	timeout:10000, 
		    	dataType: "json",
		    	data:{	    	
		    		   "phone":phone,
		    		   "type":2
		    		 },	    
		   		cache:false, 
		    	async:false,  // 设置为ajax同步请求
		    	success: function(result) {
		    		//$('#timeover').hide();
		    		if(result.result_code=="SUCCESS"){
		    			$("#obtain-ma").text("验证码已发送"); 
		    			$("#obtain-ma").attr("disabled",true);
		    			wait = $("#sendCodeTime").val();
		    			get_code_time(o);
		    		}else{
		    			if("no" == result.err_code_des){
		    				$("#obtain-ma").text("请再次点击");
		    			}else if("much" == result.err_code_des){
		    				$("#obtain-ma").text("短信超上限");
		    				$("#obtain-ma").attr("disabled",true);
		    			}else{
		    				wait = parseInt(result.err_code_des)-1;
		    				$("#obtain-ma").text("操作过于频繁");
		    				setTimeout(function() {  
                				get_code_time(o);
            				}, 1000);
		    			}
		    		}
		    	},
		   		error:function(xhr,textStatus){
				  	 //$('#timeover').hide();
				  	 $("#message").text("网络异常，验证码发送超时");
				}
			});
		},200);
	}
  //倒计时  
    get_code_time = function (o) {  
    	var ee = $("#sendCodeTime").val();
        if (wait == 0) {  
            o.attr("disabled",false);  
            o.text("获取验证码");  
            wait = $("#sendCodeTime").val();  
        } else {  
            o.attr("disabled", true);  
            o.text("重新获取("+wait +"s)" );  
            wait--; 
            setTimeout(function() {  
                get_code_time(o);
            }, 1000);
        }  
    }   
    
	function checkPhone(obj){
		var phone = $("#phone").val();
		$("#phone").parent("div").parent("div").parent("li").find("p").remove();
		if (isTrue("phone",phone)) {
			var basePath = $("#basePath").val();
			var url = basePath+'/checkphone';
			//$('#timeover').show();
			setTimeout(function(){		
					$.ajax({
					 url:url,
					 type:'POST',
				 	 data:{phone:phone},
				 	 async:false,  
					 timeout:5000,   
					 dataType:'json',   
				 	 beforeSend:function(xhr){},
				 	 success:function(result){
						  	//$('#timeover').hide();
						  	$("#phone").parent("div").parent("div").parent("li").find("p").remove();
						  	switch (result.err_code_des) {
							case "1":
								break;
							case "0":
								$("#phone").parent("div").parent("div").after("<p>该手机号码已被注册过,若为本人号码可直接登录</p>");
								colorChg ("registerBtn");
								break;
							default:
								$("#message").text("系统错误，待会再试!");
								colorChg ("registerBtn");
								break;
							}
				  	 },
				  	 error:function(xhr,textStatus){
						   //$('#timeover').hide();
						   $("#phone").parent("div").parent("div").parent("li").find("p").remove();
						   $("#phone").parent("div").parent("div").after("<p>系统忙/网络异常</p>");
				 	  },
				  	// complete:function(){
				 	 // }
					});
			 },200);
		} else {
			check(obj,1);
		}	
	}
	function getPhone(){
		var phone = $("#phone").val();
		$("#phone").parent("div").parent("div").parent("li").find("p").remove();
		if (!isTrue("phone",phone)) {
			$("#phone").parent("div").parent("div").after("<p>请先输入正确的手机号码</p>");
			colorChg ("registerBtn");
		} else {
			if ($("#phone").parent("div").parent("div").parent("li").find("p").length<=0) {
				var basePath = $("#basePath").val();
				var url = basePath+'/checkphone';
				//$('#timeover').show();
				setTimeout(function(){		
						$.ajax({
						 url:url,
						 type:'POST',
					 	 data:{phone:phone},
					 	 async:false,  
						 timeout:5000,   
						 dataType:'json',   
					 	 beforeSend:function(xhr){},
					 	 success:function(result){
							//$('#timeover').hide();
							$("#phone").parent("div").parent("div").parent("li").find("p").remove();
							switch (result.err_code_des) {
							case "1":
								sendCode();
								break;
							case "0":
								$("#phone").parent("div").parent("div").after("<p>该手机号码已被绑定过,请更换</p>");
								colorChg ("registerBtn");
								break;
							default:
								$("#message").text("系统错误，待会再试!");
								colorChg ("registerBtn");
								break;
							}
					  	 },
					  	 error:function(xhr,textStatus){
							   //$('#timeover').hide();
							   $("#phone").parent("div").parent("div").parent("li").find("p").remove();
							   $("#phone").parent("div").parent("div").after("<p>系统忙/网络异常</p>");
					 	  },
					  	// complete:function(){
					 	 // }
						});
				 	},200);
				} else {
					check(obj,1);
				}	
			}
		}
	
	//注册
	function registerTo(form)
	{
		var flag = checkForm();	
		var typeFlag = 1;
  		var password = $("#pwd").val();
		if(flag)
		{
			var password = $("#pwd").val();
			if(password != ""){
  				$("#loginPwd").val(password);
  				$("#pwd").val("");
  				if($("#pwd").attr("type") == "password"){
  					$("#pwd").attr("type","text");
  					typeFlag = 2;
  				}
  			}
			$('#timeover').show();
			setTimeout(function(){
				$.ajax({
					  url:$('form').attr('action'),
					  type:'POST',
					  data:$('form').serialize(),
					  async:false,  
					  timeout:5000,   
					  dataType:'json',   
					  beforeSend:function(xhr){},
					  success:function(result){
						  	$('#timeover').hide();
						  	if(typeFlag == 2){
  			    				$("#pwd").attr("type","password");
  			    			}
						  	if (result.result_code=="SUCCESS") {
						  		$("#password").val("");
						  		$("#loginPwd").val("");
						  		var loginName = $("#phone").val();
								var storage = window.localStorage;
								//存储到loaclStage   
								storage["loginName"] = loginName;
								$('.pop-success').show();	
							}else {
								$("#pwd").val(password);
								$("#message").text(result.err_code_des);
								$("#phoneCode").val("");
								colorChg ("registerBtn");
							}
					   },
					   error:function(xhr,textStatus){
						   $('#timeover').hide();
						   if(typeFlag == 2){
  			    				$("#pwd").attr("type","password");
  			    			}
  			    			$("#pwd").val(password);
						   $("#message").text("系统忙/网络异常");
					   },
					   //complete:function(){
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
		if($("#userName").val() != ''&&!isTrue("userName",$("#userName").val()))
		{
			$("#userName").parent("div").parent("div").parent("li").find("p").remove();
			$("#userName").parent("div").parent("div").after("<p>0-8个文字，不能含有数字<p>");
			$("#registerBtn").attr("disabled",true);
			colorChg ("registerBtn");
			return false;
		}
		if($("#phone").val() == ''||!isTrue("phone",$("#phone").val()))
		{
			$("#phone").parent("div").parent("div").parent("li").find("p").remove();
			$("#phone").parent("div").parent("div").after("<p>手机号码为空/格式不正确</p>");
			$("#registerBtn").attr("disabled",true);
			colorChg ("registerBtn");
			return false;
		}
		if($("#pwd").val() == ''||!isTrue("pwd",$("#pwd").val()))
		{
			$("#pwd").parent("div").parent("div").parent("li").find("p").remove();
			$("#pwd").parent("div").parent("div").after("<p>密码为空/格式不正确</p>");
			$("#registerBtn").attr("disabled",true);
			colorChg ("registerBtn");
			return false;
		}
		if($("#password").val() == ''||!isTrue("password",$("#password").val()))
		{
			$("#password").parent("div").parent("div").parent("li").find("p").remove();
			$("#password").parent("div").parent("div").after("<p>密码为空/格式不正确</p>");
			$("#registerBtn").attr("disabled",true);
			colorChg ("registerBtn");
			return false;
		}
		if($("#password").val() != $("#pwd").val())
		{
			$("#password").parent("div").parent("div").parent("li").find("p").remove();
			$("#password").parent("div").parent("div").after("<p>两次输入的密码不一致</p>");
			$("#registerBtn").attr("disabled",true);
			colorChg ("registerBtn");
			return false;
		}
		if($("#phoneCode").val() == ''||!isTrue("phoneCode",$("#phoneCode").val()))
		{
			$("#phoneCode").parent("div").parent("div").parent("li").find("p").remove();
			$("#phoneCode").parent("div").parent("div").after("<p>手机验证码为空/格式不正确</p>");
			$("#registerBtn").attr("disabled",true);
			colorChg ("registerBtn");
			return false;
		}
		
		if($("#agreement").prop("checked") != true)
		{
			$("#message").text("请阅读并同意《杭州盈承商户协议》");
			$("#registerBtn").attr("disabled",true);
			colorChg ("registerBtn");
			return false;
		}
		return true;
	} 
	//验证密码
	function checkPwd(obj) {
		var pwd = $("#pwd").val();
		var password = $("#password").val();
		if (password=="") {
			check(obj,1);
		} else {
			check(obj,1);
			if (pwd != password) {
				/* $(obj).parent("div").parent("div").parent("li").find("p").remove(); */
				$("#password").parent("div").parent("div").parent("li").find("p").remove();
				$("#password").parent("div").parent("div").after("<p>两次输入的密码不一致</p>");
				$("#registerBtn").attr("disabled",true);
				colorChg ("registerBtn");
			} else {
				$("#password").parent("div").parent("div").parent("li").find("p").remove();
				colorChg ("registerBtn");
			}
		}
	}
	//验证确认密码
	function checkClick(obj) {
		var pwd = $("#pwd").val();
		var password = $("#password").val();
		if (!isTrue("pwd",pwd)) {
			$(obj).parent("div").parent("div").parent("li").find("p").remove();
			$("#pwd").parent("div").parent("div").parent("li").find("p").remove();
			$("#pwd").parent("div").parent("div").after("<p>请先输入正确格式密码</p>");
			$("#registerBtn").attr("disabled",true);
			colorChg ("registerBtn");
			$(obj).parent("div").parent("div").parent("li").find("p").remove();
		} 
	}
	//验证确认密码
	function checkPassword(obj) {
		var pwd = $("#pwd").val();
		var password = $("#password").val();
			if (pwd != password) {
				$(obj).parent("div").parent("div").parent("li").find("p").remove();
				$(obj).parent("div").parent("div").after("<p>两次输入的密码不一致</p>");
				$("#registerBtn").attr("disabled",true);
				colorChg ("registerBtn");
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
			msg2="姓名应为0-8个文字，不能含有数字";
		}
		if ($(obj).attr("id")=="phone") {
			msg1="手机号码不能为空";
			msg2="手机号码格式不正确";
		}
		if ($(obj).attr("id")=="pwd") {
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
			msg1="请阅读并同意《杭州盈承商户协议》";
			msg2="请阅读并同意《杭州盈承商户协议》";
		}
		var id = $(obj).attr("id");
		var value = $(obj).val();
		if (type==0) {
			if($(obj).val() != '' && isTrue(id,value)== false)
			{
				$(obj).parent("div").parent("div").after("<p>"+msg2+"</p>");
				$("#registerBtn").attr("disabled",true);
				colorChg ("registerBtn");
			}else{
				$(obj).parent("div").parent("div").parent("li").find("p").remove();
				$("#registerBtn").attr("disabled",false);
				colorChg ("registerBtn");
			}
		}
		if (type==1) {
			if($(obj).val() == '')
			{
				$(obj).parent("div").parent("div").after("<p>"+msg1+"</p>");
				$("#registerBtn").attr("disabled",true);
				colorChg ("registerBtn");
			}else if(isTrue(id,value)== false){
				$(obj).parent("div").parent("div").after("<p>"+msg2+"</p>");
				$("#registerBtn").attr("disabled",true); 
				colorChg ("registerBtn");
			}else{
				$(obj).parent("div").parent("div").parent("li").find("p").remove();
				$("#registerBtn").attr("disabled",false);
				colorChg ("registerBtn");
			}
		}
		if (type==2) {
			if($(obj).prop("checked") != true)
			{
				$("#message").text(msg1);
				$("#registerBtn").attr("disabled",true);
				colorChg ("registerBtn");
			}else{
				$("#message").text("");
				$("#registerBtn").attr("disabled",false);
				colorChg ("registerBtn");
			}
		}
		
	}

	//正则
	function isTrue(id,value)
	{
		var pattern = "";
		if (id=="userName") {
			pattern = /^([a-zA-Z\s\u4e00-\u9fa5]){0,8}$/;
		}
		if (id=="phone") {
			pattern = /^(1[3|4|5|7|8][0-9]{9})$/;
		}
		if (id=="pwd") {
			//pattern = /^((?!^[0-9]+$)(?!^[^0-9]+$)(?!^[a-zA-Z]+$)(?!^[^a-zA-Z]+$)(?!^[\W_]+$))\S{6,15}$/;
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
