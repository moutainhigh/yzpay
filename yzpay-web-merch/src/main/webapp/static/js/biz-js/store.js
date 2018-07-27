
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
		var merchant = $("#merchant").val();
		if($("#pageIndex").val() != ''){
			pageIndex = parseInt($("#pageindex").val());
		}	
		url = allUrl+"sys/store/list?type=1&pageIndex="+pageIndex+"&merchant="+merchant;
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
			    }
			});
	    }, 300);  
	}
	
	/**
	 * 上拉加载更多,追加数据  
	 */
	function pullUpAction () { 
		$(".upwarp-progress").after("<p class='f'>正在加载...</p>");
		var url = "";
		// 当前页数 
		var pageIndex = 1;
		var merchant = $("#merchant").val();
		if($("#pageIndex").val() != ''){
			pageIndex = parseInt($("#pageindex").val()) + 1;
		}
		var total = 0;
		if($("#total").val() != ''){
			total = parseInt($("#total").val());
		}
		if($("#total").val()>0){
			url = allUrl+"sys/store/list?type=1&pageIndex="+pageIndex+"&merchant="+merchant;
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
			    }
			});
	    }, 300);  
	}
}
	//禁止PC浏览器拖拽图片,避免与下拉刷新冲突;如果仅在移动端使用,可删除此代码
	document.ondragstart=function() {return false;}
});

//商铺删除
function deleteObj(url,id)
{
	var storeNo = $('#'+id).val();
	pageIndex = $('#pageindex').val();
	//$('#timeover').show();
	setTimeout(function(){
		$.ajax({
			  url:url,
			  type:'POST',
			  data:{storeNo:storeNo},
			  async:false,  
			  timeout:5000,   
			  dataType:'json',   
			  beforeSend:function(xhr){},
			  success:function(result){
				  //$('#timeover').hide();
				  if (result.result_code=="SUCCESS") 
					{
						$('#alertDiv2').show();
					}else {
						$('#deletemsg').text(result.err_code_des);
						$('#alertDiv2').show();
					}
			   },
			   error:function(xhr,textStatus){
				  // $('#timeover').hide();
				   $('#deletemsg').text("系统忙/网络异常");
				   $('#alertDiv2').show();
			   }
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
/**
 * 根据省份获取市信息
 */
function getCity(id)
{
	var value = $(id).val();
	if(value != ""){
		$('#timeover').show();
		setTimeout(function () {
			$.ajax({
				url:allUrl+'sys/store/getregion?id='+value,
				type:'POST', //GET
		    	async:false,  
		    	timeout:5000,   
		   	 	dataType:'json',   
		    	beforeSend:function(xhr){},
				success:function(data,textStatus,jqXHR){
					$("#city").html("");
		    		for(var id in data)
		    		{
		    			$("#city").append("<option value='"+id+"'>"+data[id]+"</option>"); 
		    		}
			    	// 选择市时自动查询出下面的区信息
			    	getArea($("#city"));
		    		$('#timeover').hide();
		    		},
		    		error:function(xhr,textStatus){ 
		    		$('#timeover').hide();
		    		$("#prov").parent("div").parent("div").after("<p>系统忙/网络异常</p>");
		    	}
			});
		}, 200);
	}
	
}


/**
 * 根据市获取区信息
 */
function getArea(id)
{
	var value = $(id).val();
	if(value != ""){
		$('#timeover').show();
		$.ajax({
			url:allUrl+'sys/store/getregion?id='+value,
			type:'POST',
			async:false,  
			timeout:5000,   
			dataType:'json',   
			beforeSend:function(xhr){},
			success:function(data,textStatus,jqXHR){
				$("#area").html("");
				for(var id in data)
				{
					$("#area").append("<option value='"+id+"'>"+data[id]+"</option>"); 
				}
				$('#timeover').hide();
			},
			error:function(xhr,textStatus){
				$('#timeover').hide();
				$("#prov").parent("div").parent("div").after("<p>系统忙/网络异常</p>");
			}
		});
	}
}
/**
 * 表单验证及提交
 */  
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
						colorChg ("storeaddbtn");
					}
		 	   },
		 	   error:function(xhr,textStatus){ 
		    		$('#timeover').hide();
					$('#message').text("系统忙/网络异常");
		  	  }
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
  		$("#storeaddbtn").attr("disabled",false);
  		colorChg ("storeaddbtn");
  	}else{
  		$("#storeaddbtn").attr("disabled",true);
  		colorChg ("storeaddbtn");
  	}
  } 
  
  	function checkStore(obj){
		var storeName = $("#storeName").val();
		var storeName1 = $("#storeName1").val();
		var merchant = $("#merchant").val();
		$("#storeName").parent("div").parent("div").parent("li").find("p").remove();
		if (isTrue("storeName",storeName)&&storeName != storeName1) {
			var basePath = $("#basePath").val();
			var url = basePath+'/sys/store/checkstore';
			//$('#timeover').show();
			setTimeout(function () {
				$.ajax({
					url:url,
				    type:'POST', //GET
				    data:{storeName:storeName,merchant:merchant},
				    async:false,  
				    timeout:5000,   
				    dataType:'json',   
				    beforeSend:function(xhr){},
				    success:function(result){
				    	switch (result.err_code_des) {
						case "1":
							$("#storeName").parent("div").parent("div").after("<p>您已添加过该商铺!</p>");
							colorChg ("storeaddbtn");
							break;
						case "0":
							break;
						default:
							$("#message").text("系统错误，待会再试!");
							break;
						}
			 	   },
			 	   error:function(xhr,textStatus){ 
			    		$("#storeName").parent("div").parent("div").parent("li").find("p").remove();
			    		$("#storeName").parent("div").parent("div").after("<p>系统忙/网络异常</p>");
			  	  }
				});
			}, 200);
		} else {
			check(obj,1);
		}	
	}
  
  
  
// 表单数据验证
function checkForm()
{
	if($("#storeName").val() == ''||!isTrue("storeName",$("#storeName").val()))
	{
		$("#storeName").parent("div").parent("div").parent("li").find("p").remove();
		$("#storeName").parent("div").parent("div").after("<p>商铺名称不能为空/格式不正确</p>");
		colorChg ("storeaddbtn");
		return false;
	}
	if($("#prov").val() == '')
	{
		$("#prov").parent("div").parent("div").parent("li").find("p").remove();
		$("#prov").parent("div").parent("div").after("<p>省不能为空</p>");
		colorChg ("storeaddbtn");
		return false;
	}
	if($("#city").val() == '')
	{
		$("#city").parent("div").parent("div").parent("li").find("p").remove();
		$("#city").parent("div").parent("div").after("<p>市不能为空</p>");
		colorChg ("storeaddbtn");
		return false;
	}
	if($("#area").val() == '')
	{
		$("#area").parent("div").parent("div").parent("li").find("p").remove();
		$("#area").parent("div").parent("div").after("<p>区不能为空</p>");
		colorChg ("storeaddbtn");
		return false;
	}
	if($("#address").val() == ''||!isTrue("address",$("#address").val()))
	{
		$("#address").parent("div").parent("div").parent("li").find("p").remove();
		$("#address").parent("div").parent("div").after("<p>详细地址不能为空/格式不正确</p>");
		colorChg ("storeaddbtn");
		return false;
	}
	if($("#contactMan").val()!=''&& !isTrue("contactMan",$("#contactMan").val()))
	{
		$("#contactMan").parent("div").parent("div").parent("li").find("p").remove();
		$("#contactMan").parent("div").parent("div").after("<p>联系人姓名格式不正确</p>");
		colorChg ("storeaddbtn");
		return false;
	}
	if($("#contactTel").val()!=''&& !isTrue("contactTel",$("#contactTel").val()))
	{
		$("#contactTel").parent("div").parent("div").parent("li").find("p").remove();
		$("#contactTel").parent("div").parent("div").after("<p>联系电话格式不正确</p>");
		colorChg ("storeaddbtn");
		return false;
	}
	if($("#remark").val()!=''&& !isTrue("remark",$("#remark").val()))
	{
		$("#remark").parent("div").parent("div").parent("li").find("p").remove();
		$("#remark").parent("div").parent("div").after("<p>商户说明格式不正确</p>");
		colorChg ("storeaddbtn");
		return false;
	}
	return true;
} 

function checkInput(obj,msg)
{

	$(obj).parent("div").parent("div").parent("li").find("p").remove();
	if($(obj).val() == '')
	{
		$(obj).parent("div").parent("div").after("<p>"+msg+"不能为空</p>");
		colorChg ("storeaddbtn");
	}else{
		$(obj).parent("div").parent("div").parent("li").find("p").remove();
		colorChg ("storeaddbtn");
	}
}




	function check(obj,type) {
		$(obj).parent("div").parent("div").parent("li").find("p").remove();
		var msg1="";
		var msg2="";
		if ($(obj).attr("id")=="storeName") {
			msg1="请输入商铺名称";
			msg2="商铺名称为2-15位汉字、字母或数字";
		}
		if ($(obj).attr("id")=="prov") {
			msg1="请选择省份";
			msg2="请选择省份";
		}
		if ($(obj).attr("id")=="city") {
			msg1="请选择城市";
			msg2="请选择城市";
		}
		if ($(obj).attr("id")=="area") {
			msg1="请选择区/县";
			msg2="请选择区/县";
		}
		if ($(obj).attr("id")=="address") {
			msg1="请输入详细地址";
			msg2="详细地址为2-30位";
		}
		if ($(obj).attr("id")=="contactMan") {
			msg1="中文4位或英文8位以内";
			msg2="中文4位或英文8位以内";
		}
		if ($(obj).attr("id")=="contactTel") {
			msg1="联系电话填写不正确";
			msg2="联系电话填写不正确";
		}
		if ($(obj).attr("id")=="remark") {
			msg1="商铺说明控制在20字以内";
			msg2="商铺说明控制在20字以内";
		}
		var id = $(obj).attr("id");
		var value = $(obj).val();
		if (type==0) {
			if($(obj).val() != '' && isTrue(id,value)== false)
			{
				$(obj).parent("div").parent("div").after("<p>"+msg2+"</p>");
				colorChg ("storeaddbtn");
			}else{
				$(obj).parent("div").parent("div").parent("li").find("p").remove();
				colorChg ("storeaddbtn");
			}
		}
		if (type==1) {
			if($(obj).val() == '')
			{
				$(obj).parent("div").parent("div").after("<p>"+msg1+"</p>");
				colorChg ("storeaddbtn");
			}else if(isTrue(id,value)== false){
				$(obj).parent("div").parent("div").after("<p>"+msg2+"</p>");
				colorChg ("storeaddbtn");
			}else{
				$(obj).parent("div").parent("div").parent("li").find("p").remove();
				colorChg ("storeaddbtn");
			}
		}
		if (type==2) {
			if($(obj).prop("checked") != true)
			{
				$("#message").text(msg1);
				colorChg ("storeaddbtn");
			}else{
				$("#message").text("");
				colorChg ("storeaddbtn");
			}
		}
		if (type==3) {
			if($(obj).val() == null)
			{
				$(obj).parent("div").parent("div").after("<p>"+msg1+"</p>");
				colorChg ("storeaddbtn");
			}else{
				$(obj).parent("div").parent("div").parent("li").find("p").remove();
				colorChg ("storeaddbtn");
			}
		}
		
	}

	//正则
	function isTrue(id,value)
	{
		var pattern = "";
		if (id=="storeName") {
			pattern = /^([0-9a-zA-Z\s\u4e00-\u9fa5\(\)\（\）]){2,15}$/;
			if(pattern.test(value))
			{
				return true;
			}else{
				return false;
			}
		}
		if (id=="address") {
			pattern = /^[\S\s]{2,30}$/;
			if(pattern.test(value))
			{
				return true;
			}else{
				return false;
			}
		}
		if (id=="contactTel") {
			pattern1 = /^(1[3|4|5|7|8][0-9]{9})$/;
			pattern2 = /^((0\d{2,4})-)?(\d{7,8})(-(\d{3,6}))?$/;
			pattern3 = /^(400(-{0,1})\d{7,9})$/;
			pattern4 = /^(800(-{0,1})\d{7,9})$/;
			if(pattern1.test(value)||pattern2.test(value)||pattern3.test(value)||pattern4.test(value))
			{
				return true;
			}else{
				return false;
			}
		}
		if (id=="contactMan") {
			var pattern1 = /^([a-zA-Z\s]){0,8}$/;
			var pattern3 = /^([\u4e00-\u9fa5]){0,4}$/;
			if(pattern1.test(value)||pattern3.test(value))
			{
				return true;
			}else{
				return false;
			}
		}
		if (id=="remark") {
			pattern = /^([\w\u4E00-\u9FA5\%\^\~\`\|\%\……\~\·\｜\$\￥\#\#\>\<\》\《\.\。\,\，\/\、\!\！\:\：\“\”\;\；\-\_\——\?\？\n\b\t\(\)\（\）\[\]\【\】\{\}\｛\｝\ \ \+\=\+\=\'\‘\’\&quot;\*\@]){0,20}$/;
			if(pattern.test(value))
			{
				return true;
			}else{
				return false;
			}
		}	
	} 