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
	//添加照片
	function takePhoto(f,obj){
		var time = 0;
	   	var images = document.getElementById(obj);  
	    var currentFile = f[0];
		var size = currentFile.size;
		var type = currentFile.type;
		var value = $("#forlogo").val();
		if(/(?:jpg|png|jpeg)$/.test(type) == false) {  
			$("#logo").parent("div").parent("div").after("<p id='logop'>附件格式为jpg|png|jpeg</p>");
			$("#logo").val(value);
			colorChg ("addVipcard-btn"); 
			return;
		}
		// 文件不得大于8MB
		if(size > (1048576*8)){	
			$("#logo").parent("div").parent("div").after("<p id='logop'>图片大小不得大于8MB</p>"); 
			$("#logo").val(value);
			colorChg ("addVipcard-btn");
			return;
		}
		$("#forlogo").val($("#logo").val());
		
		var Orientation = null;
		//获取照片方向角属性，用户旋转控制  
        EXIF.getData(currentFile, function() {    
            EXIF.getAllTags(this);     
            Orientation = EXIF.getTag(this, 'Orientation');  
        }); 
        var oReader = new FileReader(); 
        oReader.readAsDataURL(currentFile); 
        oReader.onload = function(e) {  
            //var blob = URL.createObjectURL(file);  
            //_compress(blob, file, basePath);  
            var image = new Image();  
            image.src = this.result;  
            image.onload = function() {  
                var expectWidth = this.naturalWidth;  
                var expectHeight = this.naturalHeight;  
                var scale = 1;
	    		// 设置图片像素
	    		var px = 800;
	    		if(expectWidth > px || expectHeight > px){
	    			 if(expectWidth > expectHeight){
	    				 scale = px / expectWidth;
	    			 }else{
	    				 scale = px / expectHeight;
	    			 }
	    		 }
                var canvas = document.createElement("canvas");  
                var ctx = canvas.getContext("2d");  
                canvas.width = expectWidth*scale;  // 计算等比缩小后图片宽高
                canvas.height = expectHeight*scale;  
                //alert(canvas.width+","+canvas.height);
               // ctx.drawImage(this, 0, 0, canvas.width,canvas.height);  
                var base64 = null;
                //修复ios  
                if (getTerminalType() == 'IOS') {   
                    //如果方向角不为1，都需要进行旋转 added by lzk  
                    if(Orientation != "" && Orientation != 1&&time==0){   
                        switch(Orientation){  
                            case 6://需要顺时针（向左）90度旋转  
                                //alert('需要顺时针（向左）90度旋转');  
                                rotateImg(this,'left',canvas);  
                                break;  
                            case 8://需要逆时针（向右）90度旋转  
                                //alert('需要顺时针（向右）90度旋转');  
                                rotateImg(this,'right',canvas);  
                                break;  
                            case 3://需要180度旋转  
                                //alert('需要180度旋转');  
                                rotateImg(this,'right',canvas);//转两次  
                                rotateImg(this,'right',canvas);  
                                break;  
                        } 
                      	time++;         
                    }else{
                    	ctx.drawImage(this, 0, 0, canvas.width,canvas.height);
                    }  
                    base64 = canvas.toDataURL(type, 0.8);  
                }else if (getTerminalType() == 'android') {// 修复android   
                   ctx.drawImage(this, 0, 0, canvas.width,canvas.height);
                   base64 = canvas.toDataURL(type, 0.8); 
                }else{  
                    //alert(Orientation); 
                    if(Orientation != "" && Orientation != 1&& Orientation != null &&time==0){  
                        //alert('旋转处理');  
                        time++;
                        switch(Orientation){  
                            case 6://需要顺时针（向左）90度旋转  
                                //alert('111需要顺时针（向左）90度旋转');  
                                rotateImg(this,'left',canvas);  
                                break;  
                            case 8://需要逆时针（向右）90度旋转  
                                //alert('需要顺时针（向右）90度旋转');  
                                rotateImg(this,'right',canvas);  
                                break;  
                            case 3://需要180度旋转  
                                //alert('需要180度旋转');  
                                rotateImg(this,'right',canvas);//转两次  
                                rotateImg(this,'right',canvas);  
                                break;  
                        }         
                    }else{
                    	ctx.drawImage(this, 0, 0, canvas.width,canvas.height);
                    }   
                    base64 = canvas.toDataURL(type, 0.8); 
                } 
                images.src = base64;
	    		$('#baselogo').val(base64);   
            }  
        };   
    }  
        	
	//对图片旋转处理 added by lzk  
	function rotateImg(img, direction,canvas) {    
        //最小与最大旋转方向，图片旋转4次后回到原方向    
        var min_step = 0;    
        var max_step = 3;    
        //var img = document.getElementById(pid);    
        if (img == null)return;    
        //img的高度和宽度不能在img元素隐藏后获取，否则会出错    
        var height = img.height;    
        var width = img.width;
        //var step = img.getAttribute('step');    
        var step = 2;    
        if (step == null) {    
            step = min_step;    
        }    
        if (direction == 'right') {    
            step++;    
            //旋转到原位置，即超过最大值    
            step > max_step && (step = min_step);    
        } else {    
            step--;    
            step < min_step && (step = max_step);    
        }        
        //旋转角度以弧度值为参数    
        var degree = step * 90 * Math.PI / 180;    
        var ctx = canvas.getContext('2d');   
        switch (step) {    
            case 0:    
                canvas.width = width;    
                canvas.height = height;    
                ctx.drawImage(img, 0, 0);    
                break;    
            case 1:    
                canvas.width = height;    
                canvas.height = width;    
                ctx.rotate(degree);    
                ctx.drawImage(img, 0, -height);    
                break;    
            case 2:    
                canvas.width = width;    
                canvas.height = height;    
                ctx.rotate(degree);    
                ctx.drawImage(img, -width, -height);    
                break;    
            case 3:    
                canvas.width = height;    
                canvas.height = width;    
                ctx.rotate(degree);    
                ctx.drawImage(img, -width, 0);    
                break;    
        }    
    }    

//添加照片
/*	function takePhoto(f,obj){
		$("#logo").parent("div").parent("div").parent("li").find("p").remove();
	   	var image = document.getElementById(obj);  
	    var currentFile = f[0];
		var size = currentFile.size;
		var type = currentFile.type;
		var value = $("#forlogo").val();
		if(/(?:jpg|png|jpeg)$/.test(type) == false) { 
			$("#logo").parent("div").parent("div").after("<p id='logop'>附件格式为jpg|png|jpeg</p>");
			$("#logo").val(value);
			colorChg ("addVipcard-btn"); 
			return;
		}
		// 文件不得大于1MB
		if(size > 5242880){	
			$("#logo").parent("div").parent("div").after("<p id='logop'>图片大小不得大于5MB</p>"); 
			$("#logo").val(value);
			colorChg ("addVipcard-btn");
			return;
		}
		$("#forlogo").val($("#logo").val());
	    var reader = new FileReader();  
	    //将文件以Data URL形式读入页面  
	    reader.readAsDataURL(currentFile); 
	    reader.onload=function(e){  
	    	var _image = new Image(); 
	    	_image.src = this.result;
	    	_image.onload = function(){ 
	    		 //创建一个image对象，给canvas绘制使用  
	    		var cvs = document.createElement('canvas'); 
	    		// 图片缩放比例
	    		var scale = 1;
	    		// 设置图片像素
	    		var px = 1500;
	    		if(this.width > px || this.height > px){
	    			 if(this.width > this.height){
	    				 scale = px / this.width;
	    			 }else{
	    				 scale = px / this.height;
	    			 }
	    		 }
	    		cvs.width = this.width * scale;
	    		cvs.height = this.height * scale;   // 计算等比缩小后图片宽高
	    		var ctx = cvs.getContext('2d');
	    		ctx.drawImage(this, 0, 0, cvs.width, cvs.height);     
	    		var newImageData = cvs.toDataURL("image/png");   //重新生成图片
	    		image.src = newImageData;
	    	};
	    }; 
	}*/
//设置color输入框值
function selectColor(color) {
	$('.color-pop').hide();
	$('#color').val(color);
	$('#showDiv').html("");
	$('#showDiv').css('background-color',color);
}

//创建会员卡提交表单
 function saveForm(form)
	{
		var flag = checkForm();
		if(flag){
			$("#timeover").show();
			var fd = new FormData();  
			fd.append("baselogo", $("#baselogo").val());
			fd.append("color", $("#color").val());
			fd.append("title", $("#title").val());
			fd.append("skuQuantity", $("#skuQuantity").val());
			fd.append("description", $("#description").val());
			//fd.append("wechat", $("#wechat").val());
			//fd.append("alipay", $("#alipay").val());
			setTimeout(function () {
				$.ajax({  
					url:$('form').attr('action'),  
					timeout:10000,
					type:"post",  
					processData: false,  
					contentType: false,  
					data:fd,  
					success:function(result){
						$("#timeover").hide();
						if(result.result_code=="SUCCESS"){
							$(".pop-success").show();
						}else{
							$('#message').text(result.err_code_des);
						}
					},
					error:function(xhr,textStatus){ 
						$("#timeover").hide();
						$('#message').text("系统忙/网络异常");
					},
					//complete:function(){}
				});
			}, 200);
	}else{
		return flag;
	} 
} 
//修改会员卡提交表单
 function editForm(form)
	{
		var flag = checkForm();
		if(flag){
			$("#timeover").show();
			var fd = new FormData(); 
			fd.append("baselogo", $("#baselogo").val());
			//fd.append("logo", $("#logo").get(0).files[0]);	
			fd.append("color", $("#color").val());
			fd.append("title", $("#title").val());
			fd.append("skuQuantity", $("#skuQuantity").val());
			fd.append("description", $("#description").val());
			//fd.append("wechat", $("#wechat").val());
			//fd.append("alipay", $("#alipay").val());
			fd.append("discount", $("#discount").val());
			fd.append("cardId", $("#cardId").val());
			fd.append("autoActivate", $("#autoActivate").val());
			fd.append("supplyBonus", $("#supplyBonus").val());
			fd.append("brandName", $("#brandName").val());
			fd.append("id", $("#id").val());
			setTimeout(function () {
				$.ajax({  
					url:$('form').attr('action'),
					timeout:10000,  
					type:"post",  
					processData: false,  
					contentType: false,  
					data:fd,  
					success:function(result){
						$("#timeover").hide();
						if(result.result_code=="SUCCESS"){
							$(".pop-success").show();
						}else{
							$('#message').text(result.err_code_des);
						}
					},
					error:function(xhr,textStatus){ 
						$("#timeover").hide();
						$('#message').text("系统忙/网络异常");
					},
					//complete:function(){}
				});
			}, 200);
	}else{
		return flag;
	} 
} 
//按钮置灰
function colorChg (name) {
	var len = $("ul").find("p").length;
	if($("#logop")){
		--len;
	}
	if (len<=0&&$("#message").text()=="") {
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
  	if ($("ul").find("p").length<=0) {
  		$("#message").text("");
  		$("#addVipcard-btn").attr("disabled",false);
  		colorChg ("addVipcard-btn");
  	}else{
  		$("#addVipcard-btn").attr("disabled",true);
  		colorChg ("addVipcard-btn");
  	}
  }  
// 创建时表单数据验证
function checkForm()
{
	if($("#color").val() == '')
	{
		$("#color").parent("div").parent("div").parent("li").find("p").remove();
		$("#color").parent("div").parent("div").after("<p>请选择会员卡背景颜色</p>");
		colorChg ("addVipcard-btn");
		return false;
	}
	if($("#title").val() == ''||!isTrue("title",$("#title").val()))
	{
		$("#title").parent("div").parent("div").parent("li").find("p").remove();
		$("#title").parent("div").parent("div").after("<p>会员卡标题为空/格式不正确</p>");
		colorChg ("addVipcard-btn");
		return false;
	}
	if($("#description").val() == ''||!isTrue("description",jQuery.trim($("#description").val())))
	{
		$("#description").parent("div").parent("div").parent("li").find("p").remove();
		$("#description").parent("div").parent("div").after("<p>会员卡使用说明为空/格式不正确</p>");
		colorChg ("addVipcard-btn");
		return false;
	}
	/*if($("#skuQuantity").val()==''|| $("#skuQuantity").val()>99999||$("#skuQuantity").val()<=0)
	{
		$("#skuQuantity").parent("div").parent("div").parent("li").find("p").remove();
		$("#skuQuantity").parent("div").parent("div").after("<p>会员卡数量超出1-99999张范围</p>");
		colorChg ("addVipcard-btn");
		return false;
	}*/
	return true;
} 

function checkInput(id,msg)
{

	$("#"+id).parent("div").parent("div").parent("li").find("p").remove();
	if($("#"+id).val() == '')
	{
		$("#"+id).parent("div").parent("div").after("<p>"+msg+"不能为空</p>");
		colorChg ("addVipcard-btn");
	}else{
		$("#"+id).parent("div").parent("div").parent("li").find("p").remove();
		colorChg ("addVipcard-btn");
	}
}

	function check(obj,type) {
		$(obj).parent("div").parent("div").parent("li").find("p").remove();
		var msg1="";
		var msg2="";
		if ($(obj).attr("id")=="title") {
			msg1="会员卡标题为2-9个汉字";
			msg2="会员卡标题为2-9个汉字";
		}
		if ($(obj).attr("id")=="skuQuantity") {
			msg1="请输入会员卡数量";
			msg2="会员卡数量为1-99999张";
		}
		if ($(obj).attr("id")=="description") {
			msg1="请输入使用说明";
			msg2="使用说明为2-100个字符";
		}
		
		var id = $(obj).attr("id");
		var value = jQuery.trim($(obj).val());
		if (type==0) {
			if($(obj).val() != '' && isTrue(id,value)== false)
			{
				$(obj).parent("div").parent("div").after("<p>"+msg2+"</p>");
				colorChg ("addVipcard-btn");
			}else{
				$(obj).parent("div").parent("div").parent("li").find("p").remove();
				colorChg ("addVipcard-btn");
			}
		}
		if (type==1) {
			if(jQuery.trim($(obj).val()) == '')
			{
				$(obj).val("");
				$(obj).parent("div").parent("div").after("<p>"+msg1+"</p>");
				colorChg ("addVipcard-btn");
			}else if(isTrue(id,value)== false){
				$(obj).parent("div").parent("div").after("<p>"+msg2+"</p>");
				colorChg ("addVipcard-btn");
			}else{
				$(obj).parent("div").parent("div").parent("li").find("p").remove();
				colorChg ("addVipcard-btn");
			}
		}
		if (type==2) {
			if($(obj).prop("checked") != true)
			{
				$("#message").text(msg1);
				colorChg ("addVipcard-btn");
			}else{
				$("#message").text("");
				colorChg ("addVipcard-btn");
			}
		}
		if (type==3) {
			if($(obj).val() == null)
			{
				$(obj).parent("div").parent("div").after("<p>"+msg1+"</p>");
				colorChg ("addVipcard-btn");
			}else{
				$(obj).parent("div").parent("div").parent("li").find("p").remove();
				colorChg ("addVipcard-btn");
			}
		}
		
	}

	//正则
	function isTrue(id,value)
	{
		var pattern = "";
		if (id=="title") {
			pattern = /^([\u4e00-\u9fa5]){2,9}$/;
			if(pattern.test(value))
			{
				return true;
			}else{
				return false;
			}
		}
		if (id=="skuQuantity") {
			pattern = /^[0-9]{1,5}$/;
			if(pattern.test(value) && value != "0")
			{
				return true;
			}else{
				return false;
			}
		}
		if (id=="description") {
			//pattern = /^([\S\s]){2,100}$/;
			pattern = /^([\w\u4E00-\u9FA5\%\^\~\`\|\%\……\~\·\｜\$\￥\#\#\>\<\》\《\.\。\,\，\/\、\!\！\:\：\“\”\;\；\-\_\——\?\？\n\b\t\(\)\（\）\[\]\【\】\{\}\｛\｝\ \ \+\=\+\=\'\‘\’\&quot;\*\@]){2,100}$/;
			if(pattern.test(value))
			{
				return true;
			}else{
				return false;
			}
		}
	} 