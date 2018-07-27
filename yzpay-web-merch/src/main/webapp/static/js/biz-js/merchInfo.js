/**
 * 商户资料完善的业务js
 */
var allUrl = "/merch/";

$(document).ready(function(){
	showRegion();
	cacheIndustry();
});


/**
 * 显示该商户的省市区数据到页面
 */
function showRegion(){
	var provValue = $("#provValue").val();
	var cityValue = $("#cityValue").val();
	var areaValue = $("#areaValue").val();
	var accProvValue = $("#accProvValue").val();
	var accCityValue = $("#accCityValue").val();
	var cacheData = localStorage.getItem("cacheAddress");
	// 不存在该缓存,则从数据库中查询数据,然后再添加缓存
	if(cacheData == null){
		$.ajax({
		    url:allUrl+'sys/formContr/getAllProv',
		    type:'POST',
		    async:false,  
		    timeout:5000,   
		    dataType:'json',   
		    beforeSend:function(xhr){},
		    success:function(data,textStatus,jqXHR){
		    	for(var i=0; i<data.address.length; i++){
		    		var id = data.address[i].id;
		    		var name = data.address[i].name;
		    		if(provValue == id){
		    			$("#prov").val(name);
		    		}
		    		if(cityValue == id){
		    			$("#city").val(name);
		    		}
		    		if(areaValue == id){
		    			$("#area").val(name);
		    		}
		    		if(accProvValue == id){
		    			$("#accProv").val(name);
		    		}
		    		if(accCityValue == id){
		    			$("#accCity").val(name);
		    		}
		    	}
		    	 // 缓存所有的行政地区数据到客户端
		    	var str = JSON.stringify(data);
		    	localStorage.setItem("cacheAddress",str);
		    },
		    error:function(xhr,textStatus){
		    }
		});
	}
	// 获取缓存数据
	else{
		var jsonData = JSON.parse(cacheData);
		for(var i=0; i<jsonData.address.length; i++){
			var id = jsonData.address[i].id;
    		var name = jsonData.address[i].name;
    		if(provValue == id){
    			$("#prov").val(name);
    		}
    		if(cityValue == id){
    			$("#city").val(name);
    		}
    		if(areaValue == id){
    			$("#area").val(name);
    		}
    		if(accProvValue == id){
    			$("#accProv").val(name);
    		}
    		if(accCityValue == id){
    			$("#accCity").val(name);
    		}
		}
	}
}


// 获取浏览器信息
function getUserAgent(){
	var UA = window.navigator.userAgent;
	if(UA.indexOf("SiecomWebview") > -1){
	    return "SiecomWebview";	   
	}else{
		return "notSiecom";
	}
}


/**
 * 缓存所有的行业信息数据到客户端
 */
function cacheIndustry(){
	var cacheData = localStorage.getItem("cacheIndustry");
	// 缓存中不存在,则从数据库中查询
	if(cacheData == null){
		$.ajax({
		    url:allUrl+'sys/formContr/getIndustry',
		    type:'POST',
		    timeout:5000,   
		    async:false,  
		    dataType:'json',   
		    success:function(data,textStatus,jqXHR){
		    	var str = JSON.stringify(data);
		    	// 设置缓存
		    	localStorage.setItem("cacheIndustry",str);
		    	for(var i=0; i<data.industry.length; i++){
		    		var id = data.industry[i].id;
		    		var name = data.industry[i].name;
		    		$("#industryTypeId").append("<option value='"+id+"'>"+name+"</option>"); 
		    	}
		    },
		    error:function(xhr,textStatus){
		    }
		});
	}
	// 缓存中存在该数据,则直接从缓存中查询
	else{
		var jsonData = JSON.parse(cacheData);
		for(var i=0; i<jsonData.industry.length; i++){
			var id = jsonData.industry[i].id;
    		var name = jsonData.industry[i].name;
    		$("#industryTypeId").append("<option value='"+id+"'>"+name+"</option>"); 
		}
	}
	// 设置select option的选中
	var options = $("#industryTypeId option"); 
	var industryValue = $("#industryValue").val();
	for(var i=0; i<options.length; i++){
		var val = $(options[i]).val();
		var text =$(options[i]).text();
		if(industryValue == text){
			$("#industryTypeId option[value='"+val+"']").attr("selected",true);
			break;
		}
	}

}



/**
 * 获取终端设备类型:安卓、苹果
 */
function getTerminalType(){
	var u = navigator.userAgent;
	var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
	var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
	if(isAndroid){
		return "android";
	}else if(isiOS){
		return "IOS";
	}else{
		return "";
	}
}

/**
 * 缓存所有的行政地区数据到客户端
 */
function cacheAllAddress(){
	$.ajax({
	    url:allUrl+'sys/formContr/getAllProv',
	    type:'POST',
	    async:false,  
	    timeout:5000,   
	    dataType:'json',   
	    beforeSend:function(xhr){},
	    success:function(data,textStatus,jqXHR){
	    	 // 缓存所有的行政地区数据到客户端
	    	var str = JSON.stringify(data);
	    	localStorage.setItem("cacheAddress",str);
	    },
	    error:function(xhr,textStatus){
	    }
	});
}

/**
 * 根据省获取市
 * @param prov
 * @param city
 */
function getCity(prov,city){
	var cacheData = localStorage.getItem("cacheAddress");
	// 缓存中不存在,则从数据库中查询数据
	if(cacheData == null){
		var value = $(prov).val();
		$.ajax({
		    url:allUrl+'sys/formContr/getCity?id='+value,
		    type:'POST', 
		    async:true,  
		    timeout:5000,   
		    dataType:'json',   
		    beforeSend:function(xhr){},
		    success:function(data,textStatus,jqXHR){
				$("#"+city).html("");
		    	for(var i=0; i<data.address.length; i++){
		    		var id = data.address[i].id;
		    		var name = data.address[i].name;
		    		$("#"+city).append("<option value='"+id+"'>"+name+"</option>"); 
		    	}
				cacheAllAddress();
		    },
		    error:function(xhr,textStatus){
		    }
		});
	}
	else{
		var jsonData = JSON.parse(cacheData);
		var currentProv = $(prov).val();
		$("#"+city).html("");
		for(var i=0; i<jsonData.address.length; i++){
			var pid = jsonData.address[i].pid;
			var id = jsonData.address[i].id;
			var name = jsonData.address[i].name;
			if(currentProv == pid){
				$("#"+city).append("<option value='"+id+"'>"+name+"</option>");
			}
		}
	}
	// 选择市时自动查询出下面的区信息
    getArea($("#city"));
    // 自动触发change事件,选中开户银行所在省和市
    var provValue = $("#prov option:selected").val();
    $("#accProv").html("");
    var provOptions = $("#prov option");
    for(var i=0; i<provOptions.length; i++){
    	var optVal = $(provOptions[i]).val();
    	var optText = $(provOptions[i]).text();
    	$("#accProv").append("<option value='"+optVal+"'>"+optText+"</option>"); 
    }
    changeAccCity(provValue,'accCity');
	$("#accProv option[value='"+provValue+"']").attr("selected", true);

}



/**
 * 根据市获取区信息
 */
function getArea(id){
	var cacheData = localStorage.getItem("cacheAddress");
	// 缓存中不存在,则从数据库中查询数据
	if(cacheData == null){
		var value = $(id).val();
		$.ajax({
			 url:allUrl+'sys/formContr/getArea?id='+value,
			 type:'POST',
			 async:false,  
			 timeout:5000,   
			 dataType:'json',   
			 beforeSend:function(xhr){},
			 success:function(data,textStatus,jqXHR){
			   $("#area").html("");
			   for(var i=0; i<data.address.length; i++){
				   var id = data.address[i].id;
				   var name = data.address[i].name;
			       $("#area").append("<option value='"+id+"'>"+name+"</option>"); 
			   }
			   cacheAllAddress();
			 },
			 error:function(xhr,textStatus){
			 } 
			});
	}
	else{
		var jsonData = JSON.parse(cacheData);
		var currentCity = $(id).val();
		$("#area").html("");
		for(var i=0; i<jsonData.address.length; i++){
			var pid = jsonData.address[i].pid;
			var id = jsonData.address[i].id;
			var name = jsonData.address[i].name;
			if(currentCity == pid){
				$("#area").append("<option value='"+id+"'>"+name+"</option>");
			}
		}
		
	}
	 // 自动选中开户银行所在市 
	 var cityValue = $("#city option:selected").val();
	 $("#accCity option[value='"+cityValue+"']").attr("selected", true);
}


/**
 * 根据省份查询市
 * @param accProv
 * @param accCity
 */
function getAccCity(accProv,accCity){
	var cacheData = localStorage.getItem("cacheAddress");
	// 缓存中不存在,则从数据库中查询数据
	if(cacheData == null){
		var value = $(accProv).val();
		$.ajax({
		    url:allUrl+'sys/formContr/getCity?id='+value,
		    type:'POST', //GET
		    async:true,  
		    timeout:5000,   
		    dataType:'json',   
		    beforeSend:function(xhr){},
		    success:function(data,textStatus,jqXHR){
		    	$("#"+accCity).html("");
		    	for(var i=0; i<data.address.length; i++){
		    		var id = data.address[i].id;
		    		var name = data.address[i].name;
		    		$("#"+accCity).append("<option value='"+id+"'>"+name+"</option>");
		    	}
		    	cacheAllAddress();
		    },
		    error:function(xhr,textStatus){
		    }
		   
		});
	}
	// 从缓存中查询该数据
	else{
		var jsonData = JSON.parse(cacheData);
		var currentProv = $(accProv).val();
		$("#"+accCity).html("");
		for(var i=0; i<jsonData.address.length; i++){
			var pid = jsonData.address[i].pid;
			var id = jsonData.address[i].id;
			var name = jsonData.address[i].name;
			if(currentProv == pid){
				$("#"+accCity).append("<option value='"+id+"'>"+name+"</option>");
			}
		}
	}
}


function changeAccCity(value,accCity){  
	var cacheData = localStorage.getItem("cacheAddress");
	// 缓存中不存在,则从数据库中查询数据
	if(cacheData == null){
		$.ajax({
		    url:allUrl+'sys/formContr/getCity?id='+value,
		    type:'POST', //GET
		    async:true,  
		    timeout:5000,   
		    dataType:'json',   
		    beforeSend:function(xhr){},
		    success:function(data,textStatus,jqXHR){
		    	$("#"+accCity).html("");
		    	for(var i=0; i<data.address.length; i++){
		    		var id = data.address[i].id;
		    		var name = data.address[i].name;
		    		$("#"+accCity).append("<option value='"+id+"'>"+name+"</option>"); 
		    	}
		    	cacheAllAddress();
		    },
		    error:function(xhr,textStatus){
		    }
		   
		});
	}
	else{
		var jsonData = JSON.parse(cacheData);
		var currentProv = value;
		$("#"+accCity).html("");
		for(var i=0; i<jsonData.address.length; i++){
			var pid = jsonData.address[i].pid;
			var id = jsonData.address[i].id;
			var name = jsonData.address[i].name;
			if(currentProv == pid){
				$("#"+accCity).append("<option value='"+id+"'>"+name+"</option>"); 
			}
		}
	}

}


//打开相机
function openCamera(obj1,obj2,obj3){
	// 至高原生App拍照
	if(getUserAgent() == 'SiecomWebview'){
		var base64Input = $("#"+obj2);
		startCamra(base64Input,obj3);
		return;
	}
	// h5拍照
	else{
		 $('#'+obj1).click();
	}
}

// 添加照片
function takePhoto(f,obj,base64Input){
	   	var image =document.getElementById(obj); 
	    var currentFile = f[0];
		var size = currentFile.size;
		var type = currentFile.type;
		$("#"+obj).parent("div").parent("div").next().find("small").eq(2).remove();
		if(/(?:jpg|png|jpeg)$/.test(type) == false) {  
			$('#alertDiv2').show();
			$("#msgTitle").html('图片格式为jpg|png|jpeg');
			$("#btn_submit").css("background-color","#bbbbbb");
			$("#btn_submit").attr("disabled",true);
			return;
		}
		// 文件不得大于8MB
		if(size > (1048576*8)){	
			$('#alertDiv2').show();
			$("#msgTitle").html('文件大小不得大于8MB');
			$("#btn_submit").css("background-color","#bbbbbb");
			$("#btn_submit").attr("disabled",true);
			return;
		}else{
			if($("#"+obj+'_input').length > 0){
				$("#"+obj+'_input').val('haveImg');
			}
			$("#btn_submit").css("background-color","#4768F3");
			$("#btn_submit").attr("disabled",false);
		}
		
		// IOS端的压缩算法
		if(getTerminalType() == 'IOS'){
			imgResize(f,image,base64Input);
		}
		//安卓端的压缩算法
		else{
		    var reader = new FileReader(); 
		    //将文件以Data URL形式读入页面  
		    reader.readAsDataURL(currentFile);  
		    reader.onload=function(e){  
		    	//image.src = this.result;
		    	
		    	var _image = new Image(); 
		    	_image.src = this.result;
		    	_image.onload = function(){ 
		    		 //创建一个image对象，给canvas绘制使用  
		    		var cvs = document.createElement('canvas'); 
		    		// 图片缩放比例
		    		var scale = 1;
		    		// 设置图片像素
		    		var size = 1500;
		    		// 图片的宽或高大于1500px时,进行压缩
		    		if(this.width > size || this.height > size){
		    			 if(this.width > this.height){
		    				 scale = size / this.width;
		    			 }else{
		    				 scale = size / this.height;
		    			 }
		    		 }
		    		 // 计算等比缩小后图片宽高
		    		cvs.width = this.width * scale;
		    		cvs.height = this.height * scale;  
		    		
		    		var ctx = cvs.getContext('2d');
		    		ctx.drawImage(this, 0, 0, cvs.width, cvs.height);     
		    		var newImageData = cvs.toDataURL(type, 0.8);   //重新生成图片
		    		image.src = newImageData;
		    		$('#'+base64Input).val(newImageData);
		    		$("#btn_submit").css("background-color","#4768F3");
		    	};
		    };
		}

}



// 图片压缩
function imgResize(f,img,base64Input){
	var currentFile = f[0];
    lrz(currentFile,{width:800}).then(
    function (rst) {
        var sourceSize = toFixed2(currentFile.size / 1024);
        var resultSize = toFixed2(rst.fileLen / 1024);
        // 计算压缩率
        var scale = parseInt(100 - (resultSize / sourceSize * 100));
       // alert("原始大小："+sourceSize+"**压缩后大小："+resultSize);
        img.src = rst.base64;
        $('#'+base64Input).val(rst.base64);
    });
}

function toFixed2 (num) {
    return parseFloat(+num.toFixed(2));
}





//表单提交时的数据验证
function checkMerchForm(){
		if($("#registerName").val() == ''){
			$("#registerName_msg").show();
			$("#btn_submit").css("background-color","#bbbbbb");
			return;
		}
		if($("#merchantName").val() == ''){
			$("#merchantName_msg").show();
			$("#btn_submit").css("background-color","#bbbbbb");
			return;
		}
		if($("#address").val() == ''){  
			$("#address_msg").show();
			$("#btn_submit").css("background-color","#bbbbbb");
			return;
		}
		if($("#cardName").val() == ''){
			$("#cardName_msg").show();
			$("#btn_submit").css("background-color","#bbbbbb");
			return;
		}
		if($("#cardNo").val() == ''){
			$("#cardNo_msg").show();
			$("#btn_submit").css("background-color","#bbbbbb");
			return;
		}
		if(isCardNo($("#cardNo").val()) == false){
			$("#cardNo_msg").show();
			$("#btn_submit").css("background-color","#bbbbbb");
			return;
		}
		if($("#tel").val() != ''){
			if(isPhone($("#tel").val()) == false){
				$("#tel_msg").show();
				$("#btn_submit").css("background-color","#bbbbbb");
				return;
			}
		}
		if($("#email").val() != ''){
			if(checkEmail($("#email").val()) == false){
				
				$("#email_msg").show();
				$("#btn_submit").css("background-color","#bbbbbb");
				return;
			}
		}
		if($("#accBank").val() == ''){
			$("#accBank_msg").show();
			$("#btn_submit").css("background-color","#bbbbbb");
			return;
		}
		if($("#accSubBank").val() == ''){
			$("#accSubBank_msg").show();
			$("#btn_submit").css("background-color","#bbbbbb");
			return;
		}if($("#accName").val() == ''){
			$("#accName_msg").show();
			$("#btn_submit").css("background-color","#bbbbbb");
			return;
		}if($("#accNo").val() == ''){
			$("#accNo_msg").show();
			$("#btn_submit").css("background-color","#bbbbbb");
			return;
		}if(isAccountNumber($("#accNo").val()) == false){
			$("#accNo_msg").show();
			$("#btn_submit").css("background-color","#bbbbbb");
			return;
		}
		
		if($("#showImg1_input").length > 0 && $("#showImg1_input").val() == 'noImg'){
			$("#btn_submit").css("background","#bbbbbb");
			$('#alertDiv2').show();
			$("#msgTitle").html('请上传身份证正面');
			return;
		}
		if($("#showImg2_input").length > 0 && $("#showImg2_input").val() == 'noImg'){
			$("#btn_submit").css("background","#bbbbbb");
			$('#alertDiv2').show();
			$("#msgTitle").html('请上传身份证反面');
			return;
		}
		if($("#showImg3_input").length > 0 && $("#showImg3_input").val() == 'noImg'){
			$("#btn_submit").css("background","#bbbbbb");
			$('#alertDiv2').show();
			$("#msgTitle").html('请上传营业执照');
			return;
		}
		else{
			$('#alertDiv1').show();
		}
}


// 提交表单
function submitForm(){
	$("#hiddenfile1").attr("disabled",true);
	$("#hiddenfile2").attr("disabled",true);
	$("#hiddenfile3").attr("disabled",true);
	$("#hiddenfile4").attr("disabled",true);
	$("#hiddenfile5").attr("disabled",true);
	$('#alertDiv1').hide();
	$("#btn_submit").hide();
	$("#registerName_msg").hide();
	$("#merchantName_msg").hide();
	$("#address_msg").hide();
	$("#cardName_msg").hide();
	$("#cardNo_msg").hide();
	$("#tel_msg").hide();
	$("#email_msg").hide();
	$("#accBank_msg").hide();
	$("#accSubBank_msg").hide();
	$("#accName_msg").hide();
	$("#accNo_msg").hide();
	$("#merchAllEditForm").submit();
	$("#alertDiv3").show();
}


//验证身份证 
function isCardNo(card) { 
 	var pattern = /(^\d{17}(\d|X|x)$)/; 
	return pattern.test(card); 
}

// 验证身份证
function checkCardNo(obj,e){
	var val = $(obj).val();
	if(val == ''){
		$("#"+e).show();
		$("#btn_submit").css("background-color","#bbbbbb");
		$("#btn_submit").attr("disabled",true);
		return;
	}
	else{
		if(isCardNo(val) == false){
			$("#"+e).show();
			$("#btn_submit").css("background-color","#bbbbbb");
			$("#btn_submit").attr("disabled",true);
			return;
		}else{
			$("#"+e).hide();
			$("#btn_submit").css("background-color","#4768F3");  
			$("#btn_submit").attr("disabled",false);
			return;
			
		}
	}
	
}

// 验证身份证,且去除文本框提示
function checkCardNoAndRemoveMsg(obj){
	$('#cardNo_msg').hide();
	$('#btn_submit').css('background','#4768F3');
	$("#btn_submit").attr("disabled",false);
}



// 验证银行卡
function isAccountNumber(accountNumber){
	var pattern = /^(\d{4}\s)(\d{4}\s)(\d{4}\s)(\d{4})|(\s\d{1-3})$/g;
	return pattern.test(accountNumber);
}
//验证座机号
function isPhone(phone){
	var pattern = /^((\d{2,4})-)?(\d{7,8})(-(\d{3,}))?$/;
	return pattern.test(phone);
}

// 验证邮箱
function checkEmail(email){
	var pattern = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.com|cn$/;
	return pattern.test(email);
}

function isEmail(email,e){  
	var val = $(email).val();
	if(val != ''){
		if(checkEmail(val) == false){
			if(val.length == 30){
				$("#"+e).show();
				$("#btn_submit").css("background-color","#bbbbbb");
				$("#btn_submit").attr("disabled",true);
			}
			else if(val.length == 0){
				$("#"+e).hide();
				$("#btn_submit").css("background-color","#4768F3");
				$("#btn_submit").attr("disabled",false);
			}
			else{
				$("#"+e).hide();
				$("#btn_submit").css("background-color","#4768F3");
				$("#btn_submit").attr("disabled",false);
			}
			
		}else{
			$("#"+e).hide();
			$("#btn_submit").css("background-color","#4768F3");  
			$("#btn_submit").attr("disabled",false);
		
		}
	}else{
		$("#"+e).hide();
		$("#btn_submit").css("background-color","#4768F3");  
		$("#btn_submit").attr("disabled",false);
	
	}
	
}

function isEmail2(email,e){
	var val = $(email).val();
	if(val != ''){
		if(checkEmail(val) == false){
			$("#"+e).show();
			$("#btn_submit").css("background-color","#bbbbbb");
			$("#btn_submit").attr("disabled",true);
		}else{
			$("#"+e).hide();
			$("#btn_submit").css("background-color","#4768F3");
			$("#btn_submit").attr("disabled",false);
		}
	}else{
		$("#"+e).hide();
		$("#btn_submit").css("background-color","#4768F3");
		$("#btn_submit").attr("disabled",false);
	}
}

//表单数据验证
function checkInput(obj,e){
	if($(obj).val() == ''){
		$("#"+e).show();
		$("#btn_submit").css("background-color","#bbbbbb");
		$("#btn_submit").attr("disabled",true);
	}
	else{
		$("#"+e).hide();
		$("#btn_submit").css("background-color","#4768F3");  
		$("#btn_submit").attr("disabled",false);
	}
}

// 座机号格式验证
function checkTel(obj,e){
	var val = $(obj).val();
	if(val != ''){
		if(isPhone(val) == false){
			if(val.length == 12){
				$("#btn_submit").attr("disabled",true);
				$("#btn_submit").css("background-color","#bbbbbb");
				$("#"+e).show();
			}
			else if(val.length == 0){
				$("#btn_submit").attr("disabled",false);
				$("#btn_submit").css("background-color","#4768F3");
				$("#"+e).hide();
			}
			else{
				$("#btn_submit").attr("disabled",false);
				$("#btn_submit").css("background-color","#4768F3");
				$("#"+e).hide();
			}
			
		}else{
			$("#"+e).hide();
			$("#btn_submit").css("background-color","#4768F3");  
			$("#btn_submit").attr("disabled",false);
		}
	}
	else{
		$("#btn_submit").css("background-color","#4768F3");  
		$("#btn_submit").attr("disabled",false);
		$("#"+e).hide();
	}
}

function checkTel2(obj,e){
	var val = $(obj).val();
	if(val != ''){
		if(isPhone($(obj).val()) == false){
			$("#btn_submit").attr("disabled",true);
			$("#btn_submit").css("background-color","#bbbbbb");
			$("#"+e).show();
		}else{
			$("#btn_submit").css("background-color","#4768F3");  
			$("#btn_submit").attr("disabled",false);
			$("#"+e).hide();
		}
	}
	else{
		$("#btn_submit").css("background-color","#4768F3");  
		$("#btn_submit").attr("disabled",false);
		$("#"+e).hide();
	}
}

// 银行账户验证
function checkAccountNumber(obj,e){
	var val = $(obj).val();
	 if(isAccountNumber($(obj).val()) == false){
		if(val.length == 19){
			$("#"+e).show();
			$("#btn_submit").css("background-color","#bbbbbb");
			$("#btn_submit").attr("disabled",true);
		}
		else if(val.length == 21){
			$("#"+e).show();
			$("#btn_submit").css("background-color","#bbbbbb");
			$("#btn_submit").attr("disabled",true);
		}
		else if(val.length == 22){
			$("#"+e).show();
			$("#btn_submit").css("background-color","#bbbbbb");
			$("#btn_submit").attr("disabled",true);
		}
		else if(val.length == 23){
			$("#"+e).show();
			$("#btn_submit").css("background-color","#bbbbbb");
			$("#btn_submit").attr("disabled",true);
		}
		else if(val.length == 0){
			$("#"+e).hide();
			$("#btn_submit").css("background-color","#4768F3");
			$("#btn_submit").attr("disabled",false);
		}
		else{
			$("#"+e).hide();
			$("#btn_submit").css("background-color","#4768F3");
			$("#btn_submit").attr("disabled",false);
		}
		
	}
	else{
		$("#"+e).hide();
		$("#btn_submit").css("background-color","#4768F3");  
		$("#btn_submit").attr("disabled",false);
	}
}

function checkAccountNumber2(obj,e){
	var val = $(obj).val();
	 if(isAccountNumber($(obj).val()) == false){
		 $("#"+e).show();
		 $("#btn_submit").css("background-color","#bbbbbb");
		 $("#btn_submit").attr("disabled",true);
	 }else{
		 $("#"+e).hide();
		 $("#btn_submit").css("background-color","#4768F3");
		 $("#btn_submit").attr("disabled",false);
	 }
}