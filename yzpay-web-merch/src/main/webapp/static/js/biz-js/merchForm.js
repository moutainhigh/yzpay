/**
 * 商户资料完善的业务js
 */
var allUrl = "/merch/";
$(document).ready(function(){
	cacheProv();
	setDefaultCity($("#prov"),'city');
	cacheIndustry();
});


/**
 * 缓存所有的省份数据到客户端
 */
function cacheProv(){
	// 缓存中不存在,则从数据库中查询
	var cacheData = localStorage.getItem("cacheProv");
	if(cacheData == null){
		$.ajax({
		    url:allUrl+'sys/formContr/getProv',
		    type:'POST',
		    timeout:5000,   
		    async:false,  
		    dataType:'json',   
		    success:function(data,textStatus,jqXHR){
		    	var str = JSON.stringify(data);
		    	localStorage.setItem("cacheProv",str);
		    	for(var i=0; i<data.address.length; i++){
		    		var id = data.address[i].id;
		    		var name = data.address[i].name;
		    		$("#prov").append("<option value='"+id+"'>"+name+"</option>"); 
		    		$("#accProv").append("<option value='"+id+"'>"+name+"</option>"); 
		    	}
		    },
		    error:function(xhr,textStatus){  // 7500   2550
		    	
		    }
		});
		return;
	}
	// 缓存中存在该数据,则直接从缓存中查询
	else{
		var jsonData = JSON.parse(cacheData);
		for(var i=0; i<jsonData.address.length; i++){
			var id = jsonData.address[i].id;
			var name = jsonData.address[i].name;
			$("#prov").append("<option value='"+id+"'>"+name+"</option>"); 
			$("#accProv").append("<option value='"+id+"'>"+name+"</option>"); 
		}
		return;
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
}


/**
 * 渲染百度地图到页面中
 */
//默认地理位置设置为上海市浦东新区  
var x=121.48789949,y=31.24916171;     
function initBaiduMap(){
    if(navigator.geolocation) {    
        navigator.geolocation.getCurrentPosition(showPosition);
            // 百度地图API功能    
        	$("#mapContainer").show();
            var map = new BMap.Map("mapContainer");    
            var point = new BMap.Point(x,y);    
            map.centerAndZoom(point,18);
            var geolocation = new BMap.Geolocation();    
            geolocation.getCurrentPosition(function(r){    
                if(this.getStatus() == BMAP_STATUS_SUCCESS){    
                    var mk = new BMap.Marker(r.point);    
                    map.addOverlay(mk);    
                    map.panTo(r.point);    
                }    
                else {    
                    alert('failed'+this.getStatus());    
                }            
            },{enableHighAccuracy: true});
            
            // 点击地图展示详细地址
            var geoc = new BMap.Geocoder();    
        	map.addEventListener("click", function(e){        
        		var pt = e.point;
        		geoc.getLocation(pt, function(rs){
        			var addComp = rs.addressComponents;
        			
        			
        			alert(addComp.province + ", " + addComp.city + ", " + addComp.district + ", " + addComp.street + ", " + addComp.streetNumber);
        		});        
        	});
            
        return;  
    }    
    alert("你的浏览器不支持获取地理位置！");  
}

function showPosition(position){  
  x=position.coords.latitude;   
  y=position.coords.longitude;    
}



/**
 * 百度地图定位用户当前城市
 */
 function getUserLocation(callback){
	var location = new Object();
	location.province = "";
	location.city = "";
	var userIp = "218.17.109.74"; //$("#userIp").val(); 
	$.ajax({
		url:'http://api.map.baidu.com/location/ip?ak=F454f8a5efe5e577997931cc01de3974&ip='+userIp,
		type:'get',
		dataType:'jsonp',  // jsonp方式的跨域请求
		jsonp:"callback",  // 跨域时传入的回调函数,jquery默认的回调函数名,可以自定义
		timeout:5000,   
		success:function(data,textStatus,jqXHR){
			var province = data.content.address_detail.province;
			var city = data.content.address_detail.city;
			if(province != '' && city != ''){
				location.province = province;
				location.city = city;
				callback(location);
			}
		}
	});
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


// 获取浏览器信息
function getUserAgent(){
	var UA = window.navigator.userAgent;
	if(UA.indexOf("SiecomWebview") > -1){
	    return "SiecomWebview";	   
	}else{
		return "notSiecom";
	}
}


	getUserLocation(function(location){
		var prov = location.province;
		var city = location.city;
		alert("省："+prov+"***市"+city);
	});



/**
 * 页面加装时，设置默认的省和市为IP地址定位到的城市
 */
function setDefaultCity(prov,_city){
	var cacheData = localStorage.getItem("cacheAddress");
	// 缓存中不存在,则从数据库中查询数据
	if(cacheData == null){
		var value = $(prov).val();
		$.ajax({
		    url:allUrl+'sys/formContr/getCity?id='+value,
		    type:'POST', //GET
		    async:false,  
		    timeout:5000,   
		    dataType:'json',   
		    beforeSend:function(xhr){},
		    success:function(data,textStatus,jqXHR){
		    	$("#"+_city).html("");
		    	for(var i=0; i<data.address.length; i++){  
		    		var id = data.address[i].id;
		    		var name = data.address[i].name;
		    		$("#"+_city).append("<option value='"+id+"'>"+name+"</option>"); 
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
			
				// 定位
				getUserLocation(function(location){
					var prov = location.province;
					var city = location.city;
					// 设置省为当前用户定位到的省
					var pOptions = $("#prov option");
					var t = null;
					var s = new Array();
					for(var i=0; i<pOptions.length; i++){
						if($(pOptions[i]).text() == prov){
							t = $(pOptions[i]).prop("outerHTML");
						}else{
							s.push($(pOptions[i]).prop("outerHTML"));
						}
					}
					$("#prov").html("");
					$("#prov").append(t);
					$("#accProv").html("");
					$("#accProv").append(t);
					for(var i=0; i<s.length; i++){
						$("#prov").append(s[i]);
						$("#accProv").append(s[i]);
					}
					// 设置市为当前用户定位到的市
					$("#city").html("");
					$("#accCity").html("");
					prov =  $("#prov option:selected").val();
					$.ajax({
						url:allUrl+'sys/formContr/getCity?id='+prov,
						type:'post',
						async:false,  
						timeout:5000,   
						dataType:'json',   
						timeout:5000,   
						success:function(data,textStatus,jqXHR){
							for(var i=0; i<data.address.length; i++){  
					    		var id = data.address[i].id;
					    		var name = data.address[i].name;
					    		$("#"+_city).append("<option value='"+id+"'>"+name+"</option>"); 
					    	}
							s = new Array();
							pOptions = $("#city option");
							for(var i=0; i<pOptions.length; i++){
								if($(pOptions[i]).text() == city){
									t = $(pOptions[i]).prop("outerHTML");
								}else{
									s.push($(pOptions[i]).prop("outerHTML"));
								}
							}
							$("#city").html("");
							$("#accCity").html("");
							$("#city").append(t);
							$("#accCity").append(t);
							for(var i=0; i<s.length; i++){
								$("#city").append(s[i]);
								$("#accCity").append(s[i]);
							}
						}
					});
					// 查询市下面的所有区数据,并加装到select中
					$("#area").html("");
					$.ajax({
						url:allUrl+'sys/formContr/getArea?id='+$("#city option:selected").val(),
						type:'post',
						async:false,  
						timeout:5000,   
						dataType:'json',   
						timeout:5000,   
						success:function(data,textStatus,jqXHR){
							for(var i=0; i<data.address.length; i++){  
					    		var id = data.address[i].id;
					    		var name = data.address[i].name;
					    		$("#area").append("<option value='"+id+"'>"+name+"</option>"); 
					    	}
						}
					});
				});
				
		    	cacheAllAddress();
		    },
		    error:function(xhr,textStatus){
		    }
		});
	}
	// 缓存中存在该数据,则从缓存中查询
	else{
		var jsonData = JSON.parse(cacheData);
		var currentProv = $(prov).val();
		$("#city").html("");
		for(var i=0; i<jsonData.address.length; i++){
			var pid = jsonData.address[i].pid;
			var id = jsonData.address[i].id;
			var name = jsonData.address[i].name;
			if(currentProv == pid){
				$("#city").append("<option value='"+id+"'>"+name+"</option>");
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
		
		// 定位
		getUserLocation(function(location){
			var prov = location.province;
			var city = location.city;
			// 设置省为当前用户定位到的省
			var pOptions = $("#prov option");
			var t = null;
			var s = new Array();
			for(var i=0; i<pOptions.length; i++){
				if($(pOptions[i]).text() == prov){
					t = $(pOptions[i]).prop("outerHTML");
				}else{
					s.push($(pOptions[i]).prop("outerHTML"));
				}
			}
			$("#prov").html("");
			$("#prov").append(t);
			$("#accProv").html("");
			$("#accProv").append(t);
			for(var i=0; i<s.length; i++){
				$("#prov").append(s[i]);
				$("#accProv").append(s[i]);
			}
			// 设置市为当前用户定位到的市
			$("#city").html("");
			$("#accCity").html("");
			for(var i=0; i<jsonData.address.length; i++){
				var pid = jsonData.address[i].pid;
				// 如果 pid等于默认选中的省份id,则取pid下的所有子节点,获得该省份下的所有市
				if(pid == $("#prov option:selected").val()){
					var id = jsonData.address[i].id;
					var name = jsonData.address[i].name;
					$("#city").append("<option value='"+id+"'>"+name+"</option>"); 
					$("#accCity").append("<option value='"+id+"'>"+name+"</option>"); 
				}else{
					continue;
				}
			}
			s = new Array();
			pOptions = $("#city option");
			for(var i=0; i<pOptions.length; i++){
				if($(pOptions[i]).text() == city){
					t = $(pOptions[i]).prop("outerHTML");
				}else{
					s.push($(pOptions[i]).prop("outerHTML"));
				}
			}
			
			$("#city").html("");
			$("#accCity").html("");
			$("#city").append(t);
			$("#accCity").append(t);
			for(var i=0; i<s.length; i++){
				$("#city").append(s[i]);
				$("#accCity").append(s[i]);
			}
			// 查询市下面的所有区数据,并加装到select中
			$("#area").html("");
			for(var i=0; i<jsonData.address.length; i++){
				var pid = jsonData.address[i].pid;
				// 如果 pid等于默认选中的市id,则取pid下的所有子节点,获得该市下的区或县
				if(pid == $("#city option:selected").val()){
					var id = jsonData.address[i].id;
					var name = jsonData.address[i].name;
					$("#area").append("<option value='"+id+"'>"+name+"</option>"); 
				}else{
					continue;
				}
			}
		});
	}
	
}


/**
 * 根据省份获取市信息
 */
function getCity(prov,city){
	var cacheData = localStorage.getItem("cacheAddress");
	// 缓存中不存在,则从数据库中查询数据
	if(cacheData == null){
		var value = $(prov).val();
		$.ajax({
		    url:allUrl+'sys/formContr/getCity?id='+value,
		    type:'POST', //GET
		    async:false,  
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
		    	cacheAllAddress();
		    },
		    error:function(xhr,textStatus){
		    }
		});
	}
	// 缓存中存在该数据,则从缓存中查询
	else{
		var jsonData = JSON.parse(cacheData);
		var currentProv = $(prov).val();
		$("#city").html("");
		for(var i=0; i<jsonData.address.length; i++){
			var pid = jsonData.address[i].pid;
			var id = jsonData.address[i].id;
			var name = jsonData.address[i].name;
			if(currentProv == pid){
				$("#city").append("<option value='"+id+"'>"+name+"</option>");
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
		    	// 选择市时自动选择开户银行所在市
		    	var cityValue = $("#city option:selected").val();
				$("#accCity option[value='"+cityValue+"']").attr("selected", true);
				
				cacheAllAddress();
		    },
		    error:function(xhr,textStatus){
		    }
		  
		});
	}
	// 缓存中存在该数据,则从缓存中查询数据
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
		// 选择市时自动选择开户银行所在市
		var cityValue = $("#city option:selected").val();
		$("#accCity option[value='"+cityValue+"']").attr("selected", true);
		
	}

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
		    },
		    error:function(xhr,textStatus){
		    }
		   
		});
	}
	// 否则,从缓存中查询数据
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
	// 否则,从缓存中查询
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

//下一步
function next(id){
	$("#"+id).hide();
	var tableId = parseInt($("#"+id).attr("id").split("_")[1]);
	var nextTableId = tableId +1;
	$("#table_"+nextTableId).show();
}
// 上一步
function up(id){
	$("#"+id).hide();
	var tableId = parseInt($("#"+id).attr("id").split("_")[1]);
	if(tableId == 2){
		$("#headerDiv").html("基本信息");
	}
	if(tableId == 3){
		$("#headerDiv").html("银行账户信息");
	}
	var upTableId = tableId - 1;
	$("#table_"+upTableId).show();
}

// 点击下一步时,验证商户基本信息

function checkTable1(obj){ 
	if($("#registerName").val() == ''){
		$("#btn_1").css("background","#bbbbbb");
		$("#registerName_msg").show();
		return;
	}if($("#merchantName").val() == ''){
		$("#btn_1").css("background","#bbbbbb");
		$("#merchantName_msg").show();
		return;
	}if($("#address").val() == ''){
		$("#btn_1").css("background","#bbbbbb");
		$("#address_msg").show();
		return;
	}if($("#cardName").val() == ''){
		$("#btn_1").css("background","#bbbbbb");
		$("#cardName_msg").show();
		return;
	}if($("#cardNo").val() == ''){
		$("#btn_1").css("background","#bbbbbb");
		$("#cardNo_msg").show();
		return;
	}if(isCardNo($("#cardNo").val()) == false){
		$("#btn_1").css("background","#bbbbbb");
		$("#cardNo_msg").show();
		return;
	}
	if($("#tel").val() != ''){
		if(isPhone($("#tel").val()) == false){
			$("#btn_1").css("background","#bbbbbb");
			$("#tel_msg").show();
			return;
		}
	}
	if($("#email").val() != ''){
		if(checkEmail($("#email").val()) == false){
			$("#btn_1").css("background","#bbbbbb");
			$("#email_msg").show();
			return;
		}else{
			$("#btn_1").css("background","#4768F3");
			$("#email_msg").hide();
			$("#headerDiv").html("银行账户信息");
			$("#accName").val($('#cardName').val());
			next('table_1');
		}
	}else{
		$("#accName").val($('#cardName').val());
		$("#btn_1").css("background","#4768F3");
		$("#headerDiv").html("银行账户信息");
		next('table_1');
	}
}




// 点击下一步时,验证商户银行账户信息
function checkTable2(obj){
	if($("#accBank").val() == ''){
		$("#accBank_msg").show();
		$("#btn_2").css("background","#bbbbbb");
		return;
	}
	if($("#accSubBank").val() == ''){
		$("#btn_2").css("background","#bbbbbb");
		$("#accSubBank_msg").show();
		return;
	}if($("#accName").val() == ''){
		$("#btn_2").css("background","#bbbbbb");
		$("#accName_msg").show();
		return;
	}
	if($("#accNo").val() == ''){
		$("#btn_2").css("background","#bbbbbb");
		$("#accNo_msg").show();
		return;
	}
	if(isAccountNumber($("#accNo").val()) == false){
		$("#btn_2").css("background","#bbbbbb");
		$("#accNo_msg").show();
		return;
	}
	if($("#confirmAccNo").val() == ''){
		$("#btn_2").css("background","#bbbbbb");
		$("#confirmAccNo_msg").html("请输入确认银行卡号");
		$("#confirmAccNo_msg").show();
		return;
	}if($("#confirmAccNo").val() != $("#accNo").val()){
		$("#btn_2").css("background","#bbbbbb");
		$("#confirmAccNo_msg").html("两次输入的银行卡号不一样");
		$("#confirmAccNo_msg").show();
		return;
	}else{
		$("#btn_2").css("background","#4768F3");
		$("#headerDiv").html("证件信息");
		next('table_2');
	}
}


// 表单数据验证
function checkInput(obj,e){
	if($(obj).val() == ''){
		$("#btn_1").css("background","#bbbbbb");
		$("#btn_2").css("background","#bbbbbb");
		$("#btn_3").css("background","#bbbbbb");
		$("#"+e).show();
	}
	else{
		$("#btn_1").css("background","#4768F3");
		$("#btn_2").css("background","#4768F3");
		$("#btn_3").css("background","#4768F3");
		$("#"+e).hide();
	}
}

// 座机号格式验证
function checkTel(obj,e){
	var val = $(obj).val();
	if(val != ''){
		if(isPhone(val) == false){
			if(val.length == 12){
				$("#btn_1").css("background","#bbbbbb");
				$("#"+e).show();
			}
			else if(val.length == 0){
				$("#btn_1").css("background","#4768F3");
				$("#"+e).hide();
			}
			else{
				$("#btn_1").css("background","#4768F3");
				$("#"+e).hide();
			}
			
		}else{
			$("#btn_1").css("background","#4768F3");
			$("#"+e).hide();
		}
	}
	else{
		$("#btn_1").css("background","#4768F3");
		$("#"+e).hide();
	}
	
}

function checkTel2(obj,e){
	var val = $(obj).val();
	if(val != ''){
		if(isPhone(val) == false){
			$("#btn_1").css("background","#bbbbbb");
			$("#"+e).show();
		}
		else{
			$("#btn_1").css("background","#4768F3");
			$("#"+e).hide();
		}
	}
	else{
		$("#btn_1").css("background","#4768F3");
		$("#"+e).hide();
	}
}

// 银行账户验证
function checkAccountNumber(obj,e){
		var val = $(obj).val();
		if(isAccountNumber(val) == false){
			if(val.length == 19){
				$("#btn_2").css("background","#bbbbbb");
				$("#"+e).show();
			}
			else if(val.length == 21){
				$("#btn_2").css("background","#bbbbbb");
				$("#"+e).show();
			}
			else if(val.length == 22){
				$("#btn_2").css("background","#bbbbbb");
				$("#"+e).show();
			}
			else if(val.length == 23){
				$("#btn_2").css("background","#bbbbbb");
				$("#"+e).show();
			}
			else if(val.length == 0){
				$("#btn_2").css("background","#4768F3");
				$("#"+e).hide();
			}
			else{
				$("#btn_2").css("background","#4768F3");
				$("#"+e).hide();
			}
		}
		else{
			$("#btn_2").css("background","#4768F3");
			$("#"+e).hide();
		}
}

function checkAccountNumber2(obj,e){
	var val = $(obj).val();
	if(isAccountNumber(val) == false){
		$("#btn_2").css("background","#bbbbbb");
		$("#"+e).show();
	}
	else{
		$("#btn_2").css("background","#4768F3");
		$("#"+e).hide();
	}
}


function checkConfirmAccNo(obj,e){
	$("#"+e).hide();
	if($(obj).val() == ''){
		$("#btn_2").css("background","#bbbbbb");
		$("#"+e).html("请输入确认银行卡号");
		$("#"+e).show();
	}
	else{
		if($(obj).val() != $("#accNo").val()){
			if(val.length == 19){
				$("#btn_2").css("background","#bbbbbb");
				$("#"+e).html("两次输入的银行卡号不一样");
				$("#"+e).show();
			}
			else if(val.length == 21){
				$("#btn_2").css("background","#bbbbbb");
				$("#"+e).html("两次输入的银行卡号不一样");
				$("#"+e).show();
			}
			else if(val.length == 22){
				$("#btn_2").css("background","#bbbbbb");
				$("#"+e).html("两次输入的银行卡号不一样");
				$("#"+e).show();
			}
			else if(val.length == 23){
				$("#btn_2").css("background","#bbbbbb");
				$("#"+e).html("两次输入的银行卡号不一样");
				$("#"+e).show();
			}
			else if(val.length == 0){
				$("#btn_2").css("background","#bbbbbb");
				$("#"+e).html("两次输入的银行卡号不一样");
				$("#"+e).show();
			}
			else{
				$("#btn_2").css("background","#bbbbbb");
				$("#"+e).html("两次输入的银行卡号不一样");
				$("#"+e).show();
			}
		}
		else{
			$("#btn_2").css("background","#4768F3");
			$("#"+e).hide();
		}
	}
}

function checkConfirmAccNo2(obj,e){
	if($(obj).val() == ''){
		$("#btn_2").css("background","#bbbbbb");
		$("#"+e).html("请输入确认银行卡号");
		$("#"+e).show();
	}
	else{
		if($(obj).val() != $("#accNo").val()){
			$("#btn_2").css("background","#bbbbbb");
			$("#"+e).html("两次输入的银行卡号不一样");
			$("#"+e).show();
		}
		else{
			$("#btn_2").css("background","#4768F3");
			$("#"+e).hide();
		}
	}
}


// 验证附件是否上传
function checkAttach(){
	if($("#showImg1").attr("src") == '' 
		|| typeof($("#showImg1").attr("src")) == undefined 
		|| $("#showImg1").attr("src") == 'undefined' 
		|| $("#showImg1").attr("src") == 'null'
		|| $("#showImg1").attr("src") == null){
		$("#btn_3").css("background","#bbbbbb");
		$('#alertDiv2').show();
		$("#msgTitle").html('请上传身份证正面');
		return false;
	}
	if($("#showImg2").attr("src") == '' 
		|| typeof($("#showImg2").attr("src")) == undefined 
		|| $("#showImg2").attr("src") == 'undefined' 
		|| $("#showImg2").attr("src") == 'null'
		|| $("#showImg2").attr("src") == null){
		$("#btn_3").css("background","#bbbbbb");
		$('#alertDiv2').show();
		$("#msgTitle").html('请上传身份证反面');
		return false;
	}
	if($("#showImg3").attr("src") == '' 
		|| typeof($("#showImg3").attr("src")) == undefined 
		|| $("#showImg3").attr("src") == 'undefined' 
			|| $("#showImg3").attr("src") == 'null'
				|| $("#showImg3").attr("src") == null){
		$("#btn_3").css("background","#bbbbbb");
		$('#alertDiv2').show();
		$("#msgTitle").html('请上传营业执照');
		return false;
	}else{
		$('#alertDiv1').show();
		return true;
	}	
}


// 表单提交时的数据验证
function checkForm(){
		if($("#registerName").val() == ''){
			$("#registerName_msg").show();
			$("#btn_1").css("background","#bbbbbb");
			return;
		}
		if($("#merchantName").val() == ''){
			$("#merchantName_msg").show();
			$("#btn_1").css("background","#bbbbbb");
			return;
		}
		if($("#address").val() == ''){  
			$("#btn_1").css("background","#bbbbbb");
			$("#address_msg").show();
			return;
		}
		if($("#cardName").val() == ''){
			$("#btn_1").css("background","#bbbbbb");
			$("#cardName_msg").show();
			return;
		}
		if($("#cardNo").val() == ''){
			$("#btn_1").css("background","#bbbbbb");
			$("#cardNo_msg").show();
			return;
		}
		if(isCardNo($("#cardNo").val()) == false){
			
			$("#btn_1").css("background","#bbbbbb");
			$("#cardNo_msg").html("请输入18位身份证");
			$("#cardNo_msg").show();
			return;
		}
		if($("#tel").val() != ''){
			if(isPhone($("#tel").val()) == false){
				$("#btn_1").css("background","#bbbbbb");
				$("#tel_msg").show();
				return;
			}
		}
		if($("#email").val() != ''){
			if(checkEmail($("#email").val()) == false){
				$("#btn_1").css("background","#bbbbbb");
				$("#email_msg").show();
				return ;
			}
		}
		if($("#accBank").val() == ''){
			$("#btn_2").css("background","#bbbbbb");
			$("#accBank_msg").show();
			return ;
		}
		if($("#accSubBank").val() == ''){
			$("#btn_2").css("background","#bbbbbb");
			$("#accSubBank_msg").show();
			return ;
		}if($("#accName").val() == ''){
			$("#btn_2").css("background","#bbbbbb");
			$("#accName_msg").show();
			return ;
		}if($("#accNo").val() == ''){
			$("#btn_2").css("background","#bbbbbb");
			$("#accNo_msg").show();
			return;
		}if(isAccountNumber($("#accNo").val()) == false){
			$("#btn_2").css("background","#bbbbbb");
			$("#accNo_msg").show();
			return;
		}if($("#confirmAccNo").val() != $("#accNo").val()){
			$("#btn_2").css("background","#bbbbbb");
			$("#confirmAccNo_msg").show();
			return ;
		}
		else{
			$("#hiddenfile1").attr("disabled",true);
			$("#hiddenfile2").attr("disabled",true);
			$("#hiddenfile3").attr("disabled",true);
			$("#hiddenfile4").attr("disabled",true);
			$("#hiddenfile5").attr("disabled",true);
			
			$("#alertDiv1").hide();
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
			$("#confirmAccNo_msg").hide();
			$("#upDiv").hide(); 
			$("#alertDiv3").show();
			$("form").submit();
	/*		// 每隔1秒钟读取上传进度，并更新进度值
			var timer = setInterval(function(){
			   	$.ajax({
				    url:allUrl+'/sys/formContr/getProgress',
				    type:'POST',
				    dataType:'text',   
				    beforeSend:function(xhr){},
				    success:function(data,textStatus){
				    	if(data == '100'){
				    		clearInterval(timer);
				    	}
				    	$("#uploadBtn").html("上传进度:"+data+"%");
				    },
				    error:function(xhr,textStatus){
				    	
				    },
				});
			}, 1000);*/
			return true;
		}
	

}
// 验证身份证 
function isCardNo(card){ 
 	var pattern = /(^\d{17}(\d|X|x)$)/; 
	return pattern.test(card); 
}

// 验证身份证
function checkCardNo(obj,e){
	var val = $(obj).val();
	if(val == ''){
		$("#btn_1").css("background","#bbbbbb");
		$("#"+e).show();
		return;
	}
	else{
		if(isCardNo(val) == false){
			$("#btn_1").css("background","#bbbbbb");
			$("#"+e).show();
			return;
		}else{
			$("#btn_1").css("background","#4768F3");
			$("#"+e).hide();
			return;
		}
	}
}

//验证身份证,且去除文本框提示
function checkCardNoAndRemoveMsg(obj){
	$('#cardNo_msg').hide();
	$("#btn_1").css("background","#4768F3");
}



// 验证银行卡
function isAccountNumber(accountNumber){
	var pattern = /^(\d{4}\s)(\d{4}\s)(\d{4}\s)(\d{4})|(\s\d{1-3})$/g;
	return pattern.test(accountNumber);
}
// 验证座机号
function isPhone(phone){
	var pattern = /^((\d{2,4})-)?(\d{7,8})(-(\d{3,}))?$/;
	return pattern.test(phone);
}





// 验证邮箱
function checkEmail(email){              
	var pattern = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.com|cn$/;
	return pattern.test(email);
}



// 验证邮箱
function isEmail(email,e){  
	var val = $(email).val();
	if(val != ''){
		if(checkEmail(val) == false){
			if(val.length == 30){
				$("#btn_1").css("background","#bbbbbb");
				$("#"+e).show();
			}
			else if(val.length == 0){
				$("#btn_1").css("background","#4768F3");
				$("#"+e).hide();
			}
			else{
				$("#btn_1").css("background","#4768F3");
				$("#"+e).hide();
			}	
		}else{
			$("#btn_1").css("background","#4768F3");
			$("#"+e).hide();
		}
	}
	else{
		$("#btn_1").css("background","#4768F3");
		$("#"+e).hide();
	}
	
}

function isEmail2(email,e){
	var val = $(email).val();
	if(val != ''){
		if(checkEmail(val) == false){
			$("#btn_1").css("background","#bbbbbb");
			$("#"+e).show();
		}else{
			$("#btn_1").css("background","#4768F3");
			$("#"+e).hide();
		}
	}
	else{
		$("#btn_1").css("background","#4768F3");
		$("#"+e).hide();
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

	//添加照片
	function takePhoto(f,obj,base64Input){
	   	var image = document.getElementById(obj);  
	    var currentFile = f[0];
		var size = currentFile.size;
		var type = currentFile.type;
		if(/(?:jpg|png|jpeg)$/.test(type) == false) {  
			$("#btn_3").css("background","#4768F3");
			$('#alertDiv2').show();
			$("#msgTitle").html('图片格式为jpg|png|jpeg');
			return;
		}
		// 文件不得大于8MB
		if(size > (1048576*8)){	
			$("#btn_3").css("background","#4768F3");
			$('#alertDiv2').show();
			$("#msgTitle").html('图片大小不得大于8MB');
			return;
		}
	/*	var _reader = new FileReader();  
		 // 图片预览
		_reader.readAsDataURL(currentFile);  
		_reader.onload=function(e){  
			$(image).attr("data-original",this.result);
		
			
			 * 图片预览
			 * $('.aui-content-padded').viewer({
			    url: 'data-original'
			});
			$(image).trigger("click");
			
			
			 
		 };*/
		
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
		    			 }
		    			 else{
		    				 scale = size / this.height;
		    			 }
		    		 }
		    		cvs.width = this.width * scale;
		    		cvs.height = this.height * scale;   // 计算等比缩小后图片宽高
		    		var ctx = cvs.getContext('2d');
		    		ctx.drawImage(this, 0, 0, cvs.width, cvs.height);    
		    		var newImageData = cvs.toDataURL(type, 0.8);   //重新生成图片
		    		image.src = newImageData;
		    		$('#'+base64Input).val(newImageData);
		    		$("#btn_3").css("background","#4768F3");
		    	}
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
  
