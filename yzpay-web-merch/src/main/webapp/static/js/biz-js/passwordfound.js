
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
  		$("#foundBtn").attr("disabled",false);
  		colorChg ("foundBtn");
  	}else{
  		$("#foundBtn").attr("disabled",true);
  		colorChg ("foundBtn");
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
		$.ajax({
		    type: 'post',
		    url: basePath+'/phoneCode',
		    timeout:5000, 
		    dataType: "json",
		    data:{	    	
		    	   "phone":phone,
		    	   "type":1
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
	}
  //倒计时  
    get_code_time = function (o) {  
        if (wait == 0) {  
            o.attr("disabled",false);  
            o.text("获取验证码");  
            wait = 90;  
        } else {  
            o.attr("disabled", true);  
            o.text("重新获取("+wait +"s)" );  
            wait--; 
            setTimeout(function() {  
                get_code_time(o);
            }, 1000);
        }  
    } 
    //检测手机号码
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
								$("#phone").parent("div").parent("div").after("<p>该手机号码尚未注册!</p>");
								colorChg ("foundBtn");
								break;
							case "0":
								break;
							default:
								$("#message").text("系统错误，待会再试!");
								colorChg ("foundBtn");
								break;
							}
				  	 },
				  	 error:function(xhr,textStatus){
						  // $('#timeover').hide();
						  $("#phone").parent("div").parent("div").parent("li").find("p").remove();
						  $("#phone").parent("div").parent("div").after("<p>系统忙/网络异常</p>");
				 	  },
				  	// complete:function(){
				 	//  }
					});
			 },200);
		} else {
			check(obj,1);
		}	
	}
	//检测手机号码
	function getPhone(){
		var phone = $("#phone").val();
		$("#phone").parent("div").parent("div").parent("li").find("p").remove();
		if (!isTrue("phone",phone)) {
			$("#phone").parent("div").parent("div").after("<p>请先输入正确的手机号码</p>");
			colorChg ("foundBtn");
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
								$("#phone").parent("div").parent("div").after("<p>该手机号码尚未注册!</p>");
								colorChg ("foundBtn");
								break;
							case "0":
								sendCode();
								break;
							default:
								$("#message").text("系统错误，待会再试!");
								colorChg ("foundBtn");
								break;
							}
					  	 },
					  	 error:function(xhr,textStatus){
							   //$('#timeover').hide();
							   $("#phone").parent("div").parent("div").parent("li").find("p").remove();
							   $("#phone").parent("div").parent("div").after("<p>系统忙/网络异常</p>");
					 	  },
					  	// complete:function(){
					 	 //}
						});
				 	},200);
				} else {
					check(obj,1);
				}	
			}
		}
	
	//提交
	function foundTo(form)
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
					  timeout:5000,   
					  dataType:'json',   
					  beforeSend:function(xhr){},
					  success:function(result){
						  	$('#timeover').hide();
						  	if(result.result_code=="SUCCESS"){
								window.location.href=$("#basePath").val()+result.data.url;	
							}else {
								$("#message").text(result.err_code_des);
								$("#phoneCode").val("");
								colorChg ("foundBtn");
							}
					   },
					   error:function(xhr,textStatus){
						   $('#timeover').hide();
						   $("#message").text("系统忙/网络异常");
					   },
					 //  complete:function(){
					 //  }
					});
			 },200);
		}else{
			return false;
		} 
	}
	
	  
	// 表单数据验证
	function checkForm()
	{
		if($("#phone").val() == ''||!isTrue("phone",$("#phone").val()))
		{
			$("#phone").parent("div").parent("div").parent("li").find("p").remove();
			$("#phone").parent("div").parent("div").after("<p>手机号码为空/格式不正确</p>");
			colorChg ("foundBtn");
			return false;
		}
		if($("#phoneCode").val() == ''||!isTrue("phoneCode",$("#phoneCode").val()))
		{
			/* $("#phoneCode").parent("div").parent("div").parent("li").find("p").remove(); */
			/* $("#phoneCode").parent("div").parent("div").after("<p>手机验证码为空/格式不正确</p>"); */
			$("#message").text("手机验证码为空/格式不正确");
			colorChg ("foundBtn");
			return false;
		}
		return true;
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
			msg2="密码格式不正确";
		}
		if ($(obj).attr("id")=="password") {
			msg1="需再次输入密码确认";
			msg2="两次输入的密码不一致";
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
				colorChg ("foundBtn");
			}else{
				$(obj).parent("div").parent("div").parent("li").find("p").eq("0").remove();
				colorChg ("foundBtn");
			}
		}
		if (type==1) {
			if($(obj).val() == '')
			{
				$(obj).parent("div").parent("div").after("<p>"+msg1+"</p>");
				colorChg ("foundBtn");
			}else if(isTrue(id,value)== false){
				$(obj).parent("div").parent("div").after("<p>"+msg2+"</p>");
				colorChg ("foundBtn"); 
			}else{
				$(obj).parent("div").parent("div").parent("li").find("p").eq("0").remove();
				colorChg ("foundBtn");
			}
		}
		if (type==2) {
			if($(obj).val() == '')
			{
				$("#message").text(msg1);
				colorChg ("foundBtn");
			}else if(isTrue(id,value)== false){
				$("#message").text(msg2);
				colorChg ("foundBtn"); 
			}else{
				$("#message").text("");
				colorChg ("foundBtn");
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
			pattern = /^((?!^[0-9]+$)(?!^[^0-9]+$)(?!^[a-zA-Z]+$)(?!^[^a-zA-Z]+$)(?!^[\W_]+$))\S{8,15}$/;
		}
		if (id=="password") {
			pattern = /^((?!^[0-9]+$)(?!^[^0-9]+$)(?!^[a-zA-Z]+$)(?!^[^a-zA-Z]+$)(?!^[\W_]+$))\S{8,15}$/;
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