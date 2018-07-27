
$(function(){
	//创建MeScroll对象
	var mescroll = initMeScroll("mescroll", {
		down:{
			auto:false,//是否在初始化完毕之后自动执行下拉回调callback; 默认true
			callback: pullDownAction//下拉刷新的回调
		},
		up: {
		
			isBoth: false, // 上拉加载时,如果滑动到列表顶部是否可以同时触发下拉刷新;默认false,两者不可同时触发; 这里为了演示改为true,不必等列表加载完毕才可下拉;
			callback: pullUpAction//上拉加载的回调
		}
	});		
	/**
	 * 下拉刷新   
	 */
	function pullDownAction(){
		mescroll.lockUpScroll(false);   // 解锁上拉动作
		$(".downwarp-progress").parent("div").find("p").eq(1).html("<font class='f'>正在刷新...</font>");
		var url = "";
		var pageIndex = 0;
		var storeNo = $("#storeNo").val();
		var merchant = $("#merchant").val();
		if($("#pageIndex").val() != ''){
			pageIndex = parseInt($("#pageindex").val());
		}
		url = allUrl+"sys/store/clerklist?type=1&storeNo="+storeNo+"&pageIndex="+pageIndex+"&merchant="+merchant;
	    setTimeout(function(){
	    	$.ajax({
			    url:url,
			    type:'POST',
			    async:true,  
			    timeout:5000,   
			    dataType:'text',   
			    beforeSend:function(xhr){},
			    success:function(data,textStatus){
			    	mescroll.endSuccess();
			    	$("#content").html(data);
			    	$("#downloadTip").css("top","44px");
			    	setTimeout(function () {
						$("#downloadTip").css("top","20px");
					},2000);
			    },
			    error:function(xhr,textStatus){
			    	mescroll.endErr();
			   
			    },
			   // complete:function(){
			   // }
			});
	    }, 300);  
	}
	
	/**
	 * 上拉加载更多,追加数据  
	 */
	function pullUpAction () { 
		mescroll.lockUpScroll(false);   // 解锁上拉动作
		$(".upwarp-progress").after("<p class='f'>正在加载...</p>");
		var url = "";
		var pageIndex = 0;
		var storeNo = $("#storeNo").val();
		var merchant = $("#merchant").val();
		if($("#pageIndex").val() != ''){
			pageIndex = parseInt($("#pageindex").val()) + 1;
		}
		var total = 0;
		if($("#total").val() != ''){
			total = parseInt($("#total").val());
		}
		url = allUrl+"sys/store/clerklist?type=1&storeNo="+storeNo+"&pageIndex="+pageIndex+"&merchant="+merchant;
	    setTimeout(function(){ 
	    	$.ajax({
			    url:url,
			    type:'POST',
			    async:true,  
			    timeout:5000,   
			    dataType:'text',
			    beforeSend:function(xhr){},
			    success:function(data,textStatus){
			    	$("#content").html(data);
			    	var count = parseInt($("#count").val());
			    	if(count>=total){
			    		mescroll.lockUpScroll(true);   // 锁定上拉动作
			    		mescroll.endSuccess();
			    		$(".mescroll-upwarp").html("<font class='f'>没有更多数据了...</font>");
			    		$(".mescroll-upwarp").css("visibility","visible");
			    	}else{
			    		mescroll.endSuccess();		    	
			    	}
			    	$("html").css("height","100%");
			    	$("body").css("height","100%");
			    },
			    error:function(xhr,textStatus){ 
			    	  mescroll.endErr();
			
			    },
			    //complete:function(){
			    //}
			});
	    }, 300);  
	}
	//禁止PC浏览器拖拽图片,避免与下拉刷新冲突;如果仅在移动端使用,可删除此代码
	document.ondragstart=function() {return false;}
});
//店员删除
function deleteObj(url,id){	
		var storeNo = $('#storeNo').val();
		var id = $('#'+id).val();
		var pageIndex = $('#pageindex').val();
		//$('#timeover').show();
		setTimeout(function(){
			$.ajax({
				  url:url,
				  type:'POST',
				  data:{id:id},
				  async:false,  
				  timeout:5000,   
				  dataType:'json',   
				  beforeSend:function(xhr){},
				  success:function(result){
					  //$('#timeover').hide();
					  if (result.result_code=="SUCCESS") {
		            		$('#alertDiv2').show();
						}else {	
							$('#deletemsg').text(result.err_code_des);
							$('#alertDiv2').show();
						}
				   },
				   error:function(xhr,textStatus){
					   //$('#timeover').hide();
					   $('#deletemsg').text("系统忙/网络异常");
					   $('#alertDiv2').show();
				   },
				  // complete:function(){
				  // }
				});
		 },200);			
     }

//按钮置灰
function colorChg (name) {
	if ($("ul").find("p").length<=0&&$("#message").text()=="") {
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
//表单提交
  function saveForm(form)
  {
  	var flag = (form!='clerkedit')?checkForm():checkClerkForm();
  	var typeFlag = 1;
  	var password = $("#pwd").val();
  	if(flag)
  	{
  		$("#loginPwd").val(password);
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
  		setTimeout(function () {
  			$.ajax({
  				url:$('form').attr('action'),
  			    type:'POST', //GET
  			    data:$('form').serialize(),
  			    async:false,  
  			    timeout:10000,   
  			    dataType:'json',   
  			    beforeSend:function(xhr){},
  			    success:function(result){
  			    	$('#timeover').hide();
  			    	if (result.result_code=="SUCCESS") 
					{
						$('.pop-success').show();
					}else {	
						if(typeFlag == 2){
  			    		$("#pwd").attr("type","password");
  			    		}
  			    		$("#pwd").val(password);
						$("#message").text(result.err_code_des); 
						colorChg ("clerkaddbtn");
					}
  		 	   },
  		 	   error:function(xhr,textStatus){ 
  		    		$('#timeover').hide();
  		    		if(typeFlag == 2){
  			    		$("#pwd").attr("type","password");
  			    	}
  			    	$("#pwd").val(password);
  					$('#message').text("系统忙/网络异常");
  		  	  },
  		  	  //complete:function(){}
  			});
  		}, 200);
  	}else{
  		return flag;
  	} 
  }
  
  
  
   // 表单数据验证
   function checkForm()
	{
		if($("#userName").val() == ''||!isTrue("userName",$("#userName").val()))
		{
			$("#userName").parent("div").parent("div").parent("li").find("p").remove();
			$("#userName").parent("div").parent("div").after("<p>中文4位或英文8位以内，不能含有数字<p>");
			colorChg ("clerkaddbtn");
			return false;
		}
		if($("#loginPhone").val() == ''||!isTrue("loginPhone",$("#loginPhone").val()))
		{
			$("#loginPhone").parent("div").parent("div").parent("li").find("p").remove();
			$("#loginPhone").parent("div").parent("div").after("<p>手机号码为空/格式不正确</p>");
			colorChg ("clerkaddbtn");
			return false;
		}
		if($("#pwd").val() == ''||!isTrue("pwd",$("#pwd").val()))
		{
			$("#pwd").parent("div").parent("div").parent("li").find("p").remove();
			$("#pwd").parent("div").parent("div").after("<p>密码为空/格式不正确</p>");
			colorChg ("clerkaddbtn");
			return false;
		}
		return true;
	} 
   // 修改店员表单数据验证
  function checkClerkForm(){
   			if($("#userName").val() == ''||!isTrue("userName",$("#userName").val()))
		{
			$("#userName").parent("div").parent("div").parent("li").find("p").remove();
			$("#userName").parent("div").parent("div").after("<p>中文4位或英文8位，不能含有数字<p>");
			colorChg ("clerkaddbtn");
			return false;
		}
		if($("#loginPhone").val() == ''||!isTrue("loginPhone",$("#loginPhone").val()))
		{
			$("#loginPhone").parent("div").parent("div").parent("li").find("p").remove();
			$("#loginPhone").parent("div").parent("div").after("<p>手机号码为空/格式不正确</p>");
			colorChg ("clerkaddbtn");
			return false;
		}
		if($("#pwd").val() != ''&&!isTrue("pwd",$("#pwd").val()))
		{
			$("#pwd").parent("div").parent("div").parent("li").find("p").remove();
			$("#pwd").parent("div").parent("div").after("<p>密码格式不正确</p>");
			colorChg ("clerkaddbtn");
			return false;
		}
		return true;
  } 
  //去除提示
  function removeInfo(obj){
  	$(obj).parent("div").parent("div").parent("li").find("p").remove();
  	if ($("ul").find("p").length<=0) {
  		$("#message").text("");
  		$("#clerkaddbtn").attr("disabled",false);
  		colorChg ("clerkaddbtn");
  	}else{
  		$("#clerkaddbtn").attr("disabled",true);
  		colorChg ("clerkaddbtn");
  	}
  } 
  //检测手机号码 
  function checkPhone(obj){
	  	var loginPhone = $("#loginPhone").val();
		var phone1 = $("#phone1").val();
		$("#loginPhone").parent("div").parent("div").parent("li").find("p").remove();
		if (isTrue("loginPhone",loginPhone)&&loginPhone != phone1) {
			var basePath = $("#basePath").val();
			var url = basePath+'/checkphone';
			//$('#timeover').show();
			setTimeout(function(){		
					$.ajax({
					 url:url,
					 type:'POST',
				 	 data:{phone:loginPhone},
				 	 async:false,  
					 timeout:5000,   
					 dataType:'json',   
				 	 beforeSend:function(xhr){},
				 	 success:function(result){
						  	//$('#timeover').hide();
						  	switch (result.err_code_des) {
								case "1":
									colorChg ("clerkaddbtn");
									break;
								case "0":									
									$("#loginPhone").parent("div").parent("div").after("<p>该手机号码已被注册过!</p>");
									colorChg ("clerkaddbtn");
									break;
								default:
									$("#message").text("系统错误，待会再试!");
									colorChg ("clerkaddbtn");
									break;
							}
				  	 },
				  	 error:function(xhr,textStatus){
						   //$('#timeover').hide();
						   $("#loginPhone").parent("div").parent("div").parent("li").find("p").remove();
						   $("#loginPhone").parent("div").parent("div").after("<p>系统忙/网络异常</p>");
				 	  },
				  	 //complete:function(){
				 	 // }
					});
			 },200);
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
			msg2="中文4位或英文8位以内，不能含有数字";
		}
		if ($(obj).attr("id")=="loginPhone") {
			msg1="手机号码不能为空";
			msg2="手机号码格式不正确";
		}
		if ($(obj).attr("id")=="pwd") {
			msg1="密码不能为空";
			msg2="密码应为6-15位数字+字母";
		}
		var id = $(obj).attr("id");
		var value = $(obj).val();
		if (type==0) {
			if($(obj).val() != '' && isTrue(id,value)== false)
			{
				$(obj).parent("div").parent("div").after("<p>"+msg2+"</p>");
				colorChg ("clerkaddbtn");
			}else{
				$(obj).parent("div").parent("div").parent("li").find("p").remove();
				colorChg ("clerkaddbtn");
			}
		}
		if (type==1) {
			if($(obj).val() == '')
			{
				$(obj).parent("div").parent("div").after("<p>"+msg1+"</p>");
				colorChg ("clerkaddbtn");
			}else if(isTrue(id,value)== false){
				$(obj).parent("div").parent("div").after("<p>"+msg2+"</p>");
				colorChg ("clerkaddbtn"); 
			}else{
				$(obj).parent("div").parent("div").parent("li").find("p").remove();
				colorChg ("clerkaddbtn");
			}
		}
		if (type==2) {
			if($(obj).prop("checked") != true)
			{
				$("#message").text(msg1);
				colorChg ("clerkaddbtn");
			}else{
				$("#message").text("");
				colorChg ("clerkaddbtn");
			}
		}
		
	}

	//正则
	function isTrue(id,value)
	{
		var pattern = "";
		if (id=="userName") {
			var pattern1 = /^([a-zA-Z\s]){0,8}$/;
			var pattern3 = /^([\u4e00-\u9fa5]){0,4}$/;
			if(pattern1.test(value)||pattern3.test(value))
			{
				return true;
			}else{
				return false;
			}
		}
		if (id=="loginPhone") {
			pattern = /^(1[3|4|5|7|8][0-9]{9})$/;
			if(pattern.test(value))
			{
				return true;
			}else{
				return false;
			}
		}
		if (id=="pwd") {
			pattern = /^((?!^[0-9]+$)(?!^[^0-9]+$)(?!^[a-zA-Z]+$)(?!^[^a-zA-Z]+$))\w{6,15}$/;
			if(pattern.test(value))
			{
				return true;
			}else{
				return false;
			}
		}
	} 