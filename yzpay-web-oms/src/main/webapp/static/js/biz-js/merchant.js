/**
 * 商户管理的业务js
 */

var allUrl = "/yunpay/";
/**
 * 根据省份获取市信息
 */
function getCity(id){
	var value = $(id).val();
	if(value == ''){
		$.CurrentNavtab.find("#city").html("<option value=''>请选择市</option>");
		$.CurrentNavtab.find("#area").html("<option value=''>请选择区</option>");
		return;
	}else{
		$.ajax({
		    url:allUrl+'sys/merchant/getCity?id='+value,
		    type:'POST', //GET
		    async:true,  
		    timeout:5000,   
		    dataType:'json',   
		    beforeSend:function(xhr){},
		    success:function(data,textStatus,jqXHR){
		    	$.CurrentNavtab.find("#city").html("");
		    	for(var id in data){
		    		$.CurrentNavtab.find("#city")
		    		.append("<option value='"+id+"'>"+data[id]+"</option>"); 
		    	}
		    	// 选择市时自动查询出下面的区信息
		    	getArea($.CurrentNavtab.find("#city"));
		    },
		    error:function(xhr,textStatus){
		    },
		    complete:function(){
		    }
		});
	}
}


/**
 * 根据市获取区信息
 */
function getArea(id){
		var value = $(id).val();
		$.ajax({
		    url:allUrl+'sys/merchant/getArea?id='+value,
		    type:'POST',
		    async:false,  
		    timeout:5000,   
		    dataType:'json',   
		    beforeSend:function(xhr){},
		    success:function(data,textStatus,jqXHR){
		    	$.CurrentNavtab.find("#area").html("");
		    	for(var id in data){
		    		$.CurrentNavtab.find("#area")
		    		.append("<option value='"+id+"'>"+data[id]+"</option>"); 
		    	}
		    },
		    error:function(xhr,textStatus){
		    },
		    complete:function(){
		    }
		});
}


/**
 * 生成支付配置的公钥、私钥
 * merchantNo:商户号
 * type : 0-支付 1-卡券
 */
function generateKey(type){
	$("#storeMerchantPublicKey").val("");
	$("#merchantPrivateKey").val("");
	var merchantNo = $("#merchantNo").val();
	$.ajax({
	    url:allUrl+'sys/merchant/alipay/generateKey',
	    type:'POST',
	    async:false,  
	    timeout:5000,   
	    data:{
	    	"merchantNo":merchantNo,
	    	"type":type
	    },
	    dataType:'json',   
	    beforeSend:function(xhr){},
	    success:function(data,textStatus,jqXHR){
	    	var publicKey = data.publicKey;
	    	var privateKey = data.privateKey;
	    	$("#storeMerchantPublicKey").val(publicKey);
	    	$("#merchantPrivateKey").val(privateKey);
	    },
	    error:function(xhr,textStatus){
	    	alert(textStatus);
	    },
	    complete:function(){
	    }
	});
}

// 审核同意
function agree(merchantNo){
	$.ajax({
	    url:allUrl+'sys/merchant/approve/agree',
	    type:'POST',
	    timeout:5000,   
	    data:{
	    	"merchantNo":merchantNo,
	    	"auditMemo":$("#auditMemo").val()
	    },
	    dataType:'json',   
	    beforeSend:function(xhr){},
	    success:function(data,textStatus,jqXHR){
	    	if(data.status == 'success'){
	    		// 成功后关闭dialog,刷新当前页面
		    	$("#bjui-navtab").dialog('closeCurrent');
		    	$("#bjui-navtab").navtab('refresh');
	    	}
	    	
	    },
	    error:function(xhr,textStatus){
	    	
	    },
	    complete:function(){
	    }
	});
}

// 审核退回
function returned(merchantNo){
	$.ajax({
		url:allUrl+'sys/merchant/approve/returned',
	    type:'POST',
	    timeout:5000,   
	    data:{
	    	"merchantNo":merchantNo,
	    	"auditMemo":$("#auditMemo").val()
	    },
	    dataType:'json',   
	    beforeSend:function(xhr){},
	    success:function(data,textStatus,jqXHR){
	    	if(data.status == 'success'){
	    		// 成功后关闭dialog,刷新当前页面
	    		$("#bjui-navtab").dialog('closeCurrent');
		    	$("#bjui-navtab").navtab('refresh');
	    	}
	    },
	    error:function(xhr,textStatus){
	    	alert(textStatus);
	    },
	    complete:function(){
	    }
	});
}

// 退出
function closeWin(){
	$("#bjui-navtab").dialog('closeCurrent');
	$("#bjui-navtab").navtab('refresh');
}

//导出交易流水
function outExcel(obj){
	$("#"+obj).submit();
}
