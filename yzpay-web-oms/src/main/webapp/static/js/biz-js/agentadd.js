/**
 * 省市区三级联动js
 */


var allUrl = "/yunpay/";
/**
 * 根据省份获取市信息
 */
function getAddCity(id){
	var value = $(id).val();
	$.ajax({
	    url:allUrl+'sys/store/getCity?id='+value,
	    type:'POST', //GET
	    async:true,  
	    timeout:5000,   
	    dataType:'json',   
	    beforeSend:function(xhr){},
	    success:function(data,textStatus,jqXHR){
	    	alert("00000000000000000000000");
	    	$("#addform").find("#city").html("");
	    	//$("#city").html("");
	    	for(var id in data){
	    		$("#addform").find("#city").append("<option value='"+id+"'>"+data[id]+"</option>"); 
	    	}
	    	// 选择市时自动查询出下面的区信息
	    	getArea($("#city"));
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
function getAddArea(id){
		var value = $(id).val();
		$.ajax({
		    url:allUrl+'sys/store/getArea?id='+value,
		    type:'POST',
		    async:false,  
		    timeout:5000,   
		    dataType:'json',   
		    beforeSend:function(xhr){},
		    success:function(data,textStatus,jqXHR){
		    	$("#area").html("");
		    	
		    	for(var id in data){
		    		$("#area")
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

//删除后的回调函数，刷新树形菜单
function navTabAjax(json) {
	 $(selector).bjuiajax('refreshLayout', ture);
}
$(document).on('bjui.beforeLoadDialog', function(e) {
    var $dialog = $(e.target);
})

 */



