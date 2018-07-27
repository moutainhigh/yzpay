
	function returnpage() {
		var content = /删除成功/;
		var value = $("#deletemsg").text();
		if (content.test(value)) {
			location.href=allUrl+'sys/membercard/goRechargeList';
		} else {
			$("#alertDiv2").hide();
		}
	}
//充值规则删除
function deleteObj(url,id)
{
	var id = $('#'+id).val();
	$('#timeover').show();
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
			  	  $('#timeover').hide();
				  if (result.result_code=="SUCCESS") 
					{
						$("#alertDiv1").hide();
						$('#alertDiv2').show();
					}else {
						$('#deletemsg').text(result.err_code_des);
						$("#alertDiv1").hide();
						$('#alertDiv2').show();
					}
			   },
			   error:function(xhr,textStatus){
			   		$('#timeover').hide();
			   		$("#alertDiv1").hide();
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
	var flag = checkForm();
	if(flag)
	{
		$('#timeover').show();
		setTimeout(function () {
			$.ajax({
				url:$('form').attr('action'),
			    type:'POST', //GET
			    data:$('form').serialize(),
			    async:false,  
			    timeout:5000,   
			    dataType:'json',   
			    beforeSend:function(xhr){},
			    success:function(result){
			    	$('#timeover').hide();
			    	if (result.result_code=="SUCCESS") 
					{
						$('.pop-success').show();
					}else {	
						$("#message").text(result.err_code_des); 
						colorChg ("setrecharge-btn");
					}
		 	   },
		 	   error:function(xhr,textStatus){ 
		 	   		$('#timeover').hide();
					$('#message').text("系统忙/网络异常");
		  	  },
		  	 // complete:function(){}
			});
		}, 200);
	}else{
		return flag;
	} 
}
//去除提示
  function removeInfo(obj){
  	$(obj).parent("div").parent("div").parent("li").find("p").remove();
  	if ($("ul").find("p").length<=0) {
  		$("#message").text("");
  		$("#setrecharge-btn").attr("disabled",false);
  		colorChg ("setrecharge-btn");
  	}else{
  		$("#setrecharge-btn").attr("disabled",true);
  		colorChg ("setrecharge-btn");
  	}
  }  
// 表单数据验证
function checkForm()
{
	if($("#charge").val() == ''||$("#charge").val() == 0)
	{
		$("#charge").parent("div").parent("div").parent("li").find("p").remove();
		$("#charge").parent("div").parent("div").after("<p>请输入充值金额，设置范围为1-99999</p>");
		colorChg ("setrecharge-btn");
		return false;
	}
	if($("#send").val() == ''||$("#send").val() == 0)
	{
		$("#send").parent("div").parent("div").parent("li").find("p").remove();
		$("#send").parent("div").parent("div").after("<p>请输入赠送金额，设置范围为1-99999</p>");
		colorChg ("setrecharge-btn");
		return false;
	}
	return true;
} 

	function check(obj,type) {
		$(obj).parent("div").parent("div").parent("li").find("p").remove();
		var msg1="";
		var msg2="";
		if ($(obj).attr("id")=="charge") {
			msg1="请设置充值金额";
			msg2="设置范围为1-99999";
		}
		if ($(obj).attr("id")=="send") {
			msg1="请设置赠送金额";
			msg2="设置范围为1-99999";
		}
		var id = $(obj).attr("id");
		var value = $(obj).val();
		if (type==1) {
			if($(obj).val() == '')
			{
				$(obj).parent("div").parent("div").after("<p>"+msg1+"</p>");
				colorChg ("setrecharge-btn");
			}else if(!((/^[0-9]{1,5}$/).test(value))||value == 0){
				$(obj).parent("div").parent("div").after("<p>"+msg2+"</p>");
				colorChg ("setrecharge-btn");
			}else{
				$(obj).parent("div").parent("div").parent("li").find("p").remove();
				colorChg ("setrecharge-btn");
			}
		}
	}