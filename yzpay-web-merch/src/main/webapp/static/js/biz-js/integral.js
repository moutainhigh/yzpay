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
			    timeout:10000,   
			    dataType:'json',   
			    beforeSend:function(xhr){},
			    success:function(result){
			    	$('#timeover').hide();
			    	if (result.result_code=="SUCCESS") 
					{
						if($("#merchantNo").val() != ""){
							$('.pop-success').show();
						}else{
							location.href=allUrl+'sys/membercard/goadd'
						}
					}else {	
						$("#message").text(result.err_code_des); 
						colorChg ("settingpay-btn");
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
  	var text = $("#maxIncreaseBonus").parent("div").parent("div").parent("li").find("p").text();
	if("单次上限值不得小于赠送值" == text && parseInt($("#increaseBonus").val()) <= parseInt($("#maxIncreaseBonus").val())){
		$("#maxIncreaseBonus").parent("div").parent("div").parent("li").find("p").remove();
		}
  	$(obj).parent("div").parent("div").parent("li").find("p").remove();
  	if ($("ul").find("p").length<=0) {
  		$("#message").text("");
  		$("#settingpay-btn").attr("disabled",false);
  		colorChg ("settingpay-btn");
  	}else{
  		$("#settingpay-btn").attr("disabled",true);
  		colorChg ("settingpay-btn");
  	}
  }  
// 表单数据验证
function checkForm()
{
	if($("#costMoneyUnit").val() == ''||!((/^[0-9]{1,5}$/).test($("#costMoneyUnit").val())))
	{
		$("#costMoneyUnit").parent("div").parent("div").parent("li").find("p").remove();
		$("#costMoneyUnit").parent("div").parent("div").after("<p>请输入消费金额，设置范围为0-99999</p>");
		colorChg ("settingpay-btn");
		return false;
	}
	if($("#increaseBonus").val() == ''||!((/^[0-9]{1,5}$/).test($("#increaseBonus").val())))
	{
		$("#increaseBonus").parent("div").parent("div").parent("li").find("p").remove();
		$("#increaseBonus").parent("div").parent("div").after("<p>请输入赠送积分数，设置范围为0-99999</p>");
		colorChg ("settingpay-btn");
		return false;
	}
	if(($("#maxIncreaseBonus").val() != 0&&$("#maxIncreaseBonus").val() != ''&&$("#maxIncreaseBonus").val() != null) && parseInt($("#increaseBonus").val()) > parseInt($("#maxIncreaseBonus").val()))
	{
		$("#maxIncreaseBonus").parent("div").parent("div").parent("li").find("p").remove();
		$("#maxIncreaseBonus").parent("div").parent("div").after("<p>单次上限值不得小于赠送值</p>");
		colorChg ("settingpay-btn");
		return false;
	}
	if(($("#maxIncreaseBonus").val() != ''&&$("#maxIncreaseBonus").val() != null) && !((/^[0-9]{1,5}$/).test($("#maxIncreaseBonus").val())))
	{
		$("#maxIncreaseBonus").parent("div").parent("div").parent("li").find("p").remove();
		$("#maxIncreaseBonus").parent("div").parent("div").after("<p>设置范围为0-99999</p>");
		colorChg ("settingpay-btn");
		return false;
	}
	if($("#initIncreaseBonus").val() != ''&&!((/^[0-9]{1,5}$/).test($("#initIncreaseBonus").val())))
	{
		$("#initIncreaseBonus").parent("div").parent("div").parent("li").find("p").remove();
		$("#initIncreaseBonus").parent("div").parent("div").after("<p>设置范围为0-99999</p>");
		colorChg ("settingpay-btn");
		return false;
	}
	return true;
} 

	function check(obj,type) {
		$(obj).parent("div").parent("div").parent("li").find("p").remove();
		var msg1="";
		var msg2="";
		if ($(obj).attr("id")=="costMoneyUnit") {
			msg1="请设置消费金额";
			msg2="设置范围为1-99999";
		}
		if ($(obj).attr("id")=="increaseBonus") {
			msg1="请设置赠送的积分数";
			msg2="设置范围为0-99999";
		}
		if ($(obj).attr("id")=="maxIncreaseBonus") {
			msg1="单次上限值不得小于赠送值";
			msg2="设置范围为0-99999";
		}
		if ($(obj).attr("id")=="initIncreaseBonus") {
			msg1="设置范围为0-99999";
		}
		var id = $(obj).attr("id");
		var value = $(obj).val();
		if (type==0) {
			if(value != '' && !((/^[0-9]{1,5}$/).test(value)))
			{
				$(obj).parent("div").parent("div").after("<p>"+msg1+"</p>");
				colorChg ("settingpay-btn");
			}else{
				$(obj).parent("div").parent("div").parent("li").find("p").remove();
				colorChg ("settingpay-btn");
			}
		}
		if (type==1) {
			if($(obj).val() == '' || $(obj).val() == 0)
			{
				$(obj).parent("div").parent("div").after("<p>"+msg1+"</p>");
				colorChg ("settingpay-btn");
			}else if(!((/^[0-9]{1,5}$/).test(value))){
				$(obj).parent("div").parent("div").after("<p>"+msg2+"</p>");
				colorChg ("settingpay-btn");
			}else{
				$(obj).parent("div").parent("div").parent("li").find("p").remove();
				colorChg ("settingpay-btn");
			}
		}
		if (type==2) {
			if($(obj).val() == '')
			{
				$(obj).parent("div").parent("div").after("<p>"+msg1+"</p>");
				colorChg ("settingpay-btn");
			}else if(!((/^[0-9]{1,5}$/).test(value))){
				$(obj).parent("div").parent("div").after("<p>"+msg2+"</p>");
				colorChg ("settingpay-btn");
			}else if(($("#maxIncreaseBonus").val() != 0&&$("#maxIncreaseBonus").val() != ''&&$("#maxIncreaseBonus").val() != null) && parseInt($("#increaseBonus").val()) > parseInt($("#maxIncreaseBonus").val())){
				$("#maxIncreaseBonus").parent("div").parent("div").parent("li").find("p").remove();
				$("#maxIncreaseBonus").parent("div").parent("div").after("<p>单次上限值不得小于赠送值</p>");
				colorChg ("settingpay-btn");
			}else{
				$(obj).parent("div").parent("div").parent("li").find("p").remove();
				var text = $("#maxIncreaseBonus").parent("div").parent("div").parent("li").find("p").text();
				if("单次上限值不得小于赠送值" == text){
					$("#maxIncreaseBonus").parent("div").parent("div").parent("li").find("p").remove();
				}
				colorChg ("settingpay-btn");
			}
		}
		if (type==3) {
			
			if(($(obj).val() != ''&& $(obj).val() != null) && !((/^[0-9]{1,5}$/).test($(obj).val()))){
				$(obj).parent("div").parent("div").parent("li").find("p").remove();
				$(obj).parent("div").parent("div").after("<p>"+msg2+"</p>");
				colorChg ("settingpay-btn");
			}else if(($(obj).val() != 0&&$(obj).val() != ''&&$(obj).val() != null) && parseInt($("#increaseBonus").val()) > parseInt($(obj).val()))
			{
				$(obj).parent("div").parent("div").parent("li").find("p").remove();
				$(obj).parent("div").parent("div").after("<p>"+msg1+"</p>");
				colorChg ("settingpay-btn");
			}else{
				$(obj).parent("div").parent("div").parent("li").find("p").remove();
				colorChg ("settingpay-btn");
			}
		}
	}