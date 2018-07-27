//toast
function showToast(tmsg){
    var data = {};
    data.msg = tmsg;
    JSBridge.call('JsFuncImpl','showToast',data,null);
}

//取得APP信息
function initJsBridge(){
    JSBridge.call('JsFuncImpl','initJsBridge',{},function(res){
    	var appToken = res.result.AppToken;
    	$("#appToken").val(appToken); });
}

//syn
function testThread(){
  JSBridge.call('JsFuncImpl','testThread',{},function(res){alert(JSON.stringify(res));});
}

//拍照
function startCamra(base64Input,obj){
  $(base64Input).hide();
  JSBridge.call('JsFuncImpl','openCamera',{},function(res){
    $(base64Input).attr('value',res.result.imgSrc);
    $("#"+obj).attr('src',res.result.imgSrc);
  
  });
}

//Zxing
function startZxingCamra(){
  JSBridge.call('JsFuncImpl','openZxingCamera',{},function(res){
     $("#zxingtext").attr('value',res.result.scanRes);
  });
}


//退出
function exitWebview(){
  JSBridge.call('JsFuncImpl','exitWebview',{},function(res){
  })
}

//同时登录退出
function sessionPast(){
  JSBridge.call('JsFuncImpl','sessionPast',{},function(res){
  })
}

//android 物理返回按键触发此js方法
function androidBack(){
	 var UA = window.navigator.userAgent;
 	if(UA.indexOf("SiecomWebview") > -1){
 		exitWebview();
 	}else{
 		window.location.href="/merch/sys/index/index";
 	}
	
}