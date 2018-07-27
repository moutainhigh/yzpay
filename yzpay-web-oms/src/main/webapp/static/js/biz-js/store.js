/**
 * 导出
 */
// 导出门店信息
function outToExcel(obj){
	$("#"+obj).submit();
}

var allUrl = "/yunpay/";
/**
 * 根据省份获取市信息
 */
function getCity(id,form){
	var value = $(id).val();
	var slect = form=="pagerForm"?$.CurrentNavtab.find("#city"):$.CurrentDialog.find("#city");
	
	$.ajax({
	    url:allUrl+'sys/store/getCity?id='+value,
	    type:'POST', //GET
	    async:true,  
	    timeout:5000,   
	    dataType:'json',   
	    beforeSend:function(xhr){},
	    success:function(data,textStatus,jqXHR){
	    	slect.html("");
	    	for(var id in data){
	    		slect.append("<option value='"+id+"'>"+data[id]+"</option>"); 
	    	}
	    	// 选择市时自动查询出下面的区信息
	    	getArea(slect,form);
	    },
	    error:function(xhr,textStatus){
	    },
	    complete:function(){
	    }
	});
}


/**
 * 根据市获取区信息
 */
function getArea(id,form){
		var value = $(id).val();
		var slect = form=="pagerForm"?$.CurrentNavtab.find("#area"):$.CurrentDialog.find("#area");
		$.ajax({
		    url:allUrl+'sys/store/getArea?id='+value,
		    type:'POST',
		    async:false,  
		    timeout:5000,   
		    dataType:'json',   
		    beforeSend:function(xhr){},
		    success:function(data,textStatus,jqXHR){
		    	slect.html("");
		    	for(var id in data){
		    		slect.append("<option value='"+id+"'>"+data[id]+"</option>"); 
		    	}
		    },
		    error:function(xhr,textStatus){
		    },
		    complete:function(){
		    }
		});
}

// 表单数据验证
function checkForm()
{
	if($("#storeName").val() == ''||!isStoreName($("#storeName").val()))
	{
		$("#msg1").find(".f").remove();
		$("#msg1").html("<small class='f'>商铺名称为空/格式不正确</small>");
		$("#message").html("请完善门店信息");
		return false;
	}
	if($("#address").val() != ''&&!isAddress($("#address").val()))
	{
		$("#msg7").find(".f").remove();
		$("#msg7").html("<small class='f'>详细地址格式不正确</small>");
		$("#message").html("请完善门店信息");
		return false;
	}
	if($("#registerName").val() == '')
	{
		$("#msg5").find(".f").remove();
		$("#msg5").html("<small class='f'>所属商户不能为空</small>");
		$("#message").html("请完善门店信息");
		return false;
	}
	if($("#contactMan").val() != ''&& !isContactMan($("#contactMan").val()))
	{
		$("#msg3").find(".f").remove();
		$("#msg3").html("<small class='f'>联系人姓名不规范</small>");
		$("#message").html("请完善门店信息");
		return false;
	}
	if($("#contactTel").val() != ''&& !isContactTel($("#contactTel").val()))
	{
		$("#msg4").find(".f").remove();
		$("#msg4").html("<small class='f'>联系电话格式不正确</small>");
		$("#message").html("请完善门店信息");
		return false;
	}
	return true;
} 

//表单是否为空验证
function checkInput(obj)
{
	var text = $(obj).parent("div").find("label").text();
	if(text == undefined)
	{
		text = $(obj).text();
		if(text == undefined)
		{
			text = $(obj).attr("value");
		}
	}
	$(obj).parent("div").find(".f").remove();
	if($(obj).val() == '')
	{
		$(obj).parent("div").find("div").html("<small class='f'>"+text+'不能为空'+"</small>");
		$("button").attr("disabled",true);
	}else{
		$(obj).parent("div").find(".f").remove();
		$("button").attr("disabled",false);
	}
}

//验证商铺名称
function checkStoreName(obj)
{
	$(obj).parent("div").find(".f").remove();
	if($(obj).val() == '')
	{
		$(obj).parent("div").find("div").html("<small class='f'>商铺名称不能为空</small>");
		$("button").attr("disabled",true);
	}else if(isStoreName($(obj).val())== false){
		$(obj).parent("div").find("div").html("<small class='f'>商铺名称为2-32位</small>");
		$("button").attr("disabled",true); 
	}else{
		$(obj).parent("div").find(".f").remove();
		$("button").attr("disabled",false);
	}
}
//验证机构编码
function checkOrgNo(obj)
{
	$(obj).parent("div").find(".f").remove();
	if($(obj).val() == '')
	{
		$(obj).parent("div").find("div").html("<small class='f'>机构编码不能为空</small>");
		$("button").attr("disabled",true);
	}else if(isOrgNo($(obj).val())==false){
		$(obj).parent("div").find("div").html("<small class='f'>机构编码不正确</small>");
		$("button").attr("disabled",true);
	}else{
		$(obj).parent("div").find(".f").remove();
		$("button").attr("disabled",false);
	}
}
//验证详细地址
function checkAddress(obj)
{
	$(obj).parent("div").find(".f").eq("1").remove();
	if($(obj).val() != ''&& isAddress($(obj).val())==false){
		$(obj).parent("div").find("div").html("<small class='f'>详细地址为2-32位</small>");
		$("button").attr("disabled",true);
	}else{
		$(obj).parent("div").find(".f").remove();	
		$("button").attr("disabled",false);
	}
}
//验证联系人
function checkContactMan(obj)
{
	$(obj).parent("div").find(".f").remove();
	if($(obj).val()!='' && isContactMan($(obj).val())==false)
	{
		$(obj).parent("div").find("div").html("<small class='f'>不能含有数字、符号和特殊字符</small>");
		$("button").attr("disabled",true);
	}else{
		$(obj).parent("div").find(".f").remove();
		$("button").attr("disabled",false);
	}
}
//验证联系电话
function checkContactTel(obj)
{
	$(obj).parent("div").find(".f").remove();
	if($(obj).val()!='' && isContactTel($(obj).val())==false)
	{
		$(obj).parent("div").find("div").html("<small class='f'>联系电话填写不正确</small>");
		$("button").attr("disabled",true);
	}else{
		$(obj).parent("div").find(".f").remove();
		$("button").attr("disabled",false);
	}
} 
  		  
//验证商铺名称
function isStoreName(storeName)
{
	var pattern = /^([0-9a-zA-Z]|[\u4e00-\u9fa5]){2,32}$/;
	return pattern.test(storeName);
}
//验证机构编码
function isOrgNo(orgNo)
{ 
	var reg = /^([0-9A-Z]){8}[-][0-9A-Z]{1}$/;
	if (!reg.test(orgNo))
	{    
		return false;    
	}else{ 
		var values=orgNo.split("-");  
		var ws = [3, 7, 9, 10, 5, 8, 4, 2];    
		var str = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ';
		var sum = 0;    
		for (var i = 0; i < 8; i++) 
		{    
			sum += str.indexOf(values[0].charAt(i)) * ws[i];    
		}    
		var C9 = 11 - (sum % 11);  
		var YC9=values[1]+'';  
		if (C9 == 11) 
		{    
			C9 = '0';    
		} else if (C9 == 10) {    
			C9 = 'X'  ;  
		} else {    
			C9 = C9+'';    
		}   
	return YC9==C9; 
	}
}
//验证详细地址       
function isAddress(address)
{
	var pattern = /^[\S]{2,32}$/;
	return pattern.test(address);
}
//验证联系人       
function isContactMan(contactMan)
{
	var pattern = /^([a-zA-Z]|[\u4e00-\u9fa5]){0,16}$/;
	return pattern.test(contactMan);
}
//验证联系电话       
function isContactTel(contactTel)
{
	var pattern1 = /^(1[3|4|5|7|8][0-9]{9})$/;
	var pattern2 = /^((0\d{2,4})-)?(\d{7,8})(-(\d{3,}))?$/;
	if(pattern1.test(contactTel)||pattern2.test(contactTel))
	{
		return true;
	}else{
		return false;
	}
} 

/**

//删除后的回调函数，刷新树形菜单
function navTabAjax(json) {
	 $(selector).bjuiajax('refreshLayout', ture);
}
$(document).on('bjui.beforeLoadDialog', function(e) {
    var $dialog = $(e.target);
})

 */



