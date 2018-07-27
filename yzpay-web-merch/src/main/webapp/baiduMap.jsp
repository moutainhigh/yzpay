<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@include file="common/taglib/taglib.jsp"%>  
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<title>选择你的商户地址</title>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=5gIzoosabHCdWCcRGUgmk5VB2SojzZFI"></script>  
<script src="${baseURL}/bootstrap/js/jquery-1.11.2.min.js"></script> 

<style type="text/css">
html{height: 100%}
body{height: 100%;margin: 0px; padding: 0px}
#map{height: 83%}
#result{height: 17%}

</style>
</head>
<body>
	<div id="map">  </div>
	<div id="result" style="display: none;">
		<div style="height: 40%">已选择地址:</div>
		<hr>
		<div id="address" style="height: 55%"></div>
	</div>
   <script>
   
  
    // h5定位,获取移动设备GPS经纬度
   function getPoint(callback){
    	if(navigator.geolocation){
    		navigator.geolocation.getCurrentPosition(function (position) {
    	       longitude = position.coords.longitude;  
    	       latitude = position.coords.latitude;  
    	       var p = new Object();
    	       p.longitude = longitude;
    	       p.latitude =  latitude;
    	       callback(p);
    	    });  
    	}else{
    		 alert("你的浏览器不 支持定位功能");
    		 callback("fail");
    	}
	   
   }
   
   // GPS坐标转换为百度坐标,GPS为国际标准坐标系 ,调用百度地图定位时需要将标准坐标转换为百度坐标系
	function convert(ggPoint,callback){
		 var convertor = new BMap.Convertor();
		 var pointArr = [];
		 pointArr.push(ggPoint);
		 convertor.translate(pointArr, 1, 5, function(data){
			 if(data.status === 0){
				 callback(data.points[0]);
			 }else{
				alert("坐标转换失败");
				return;
			 }
		 });
    }
   
   // 根据经纬度解析地址 
   function geocoder(pt,callback){
	   var myGeo = new BMap.Geocoder();
	   myGeo.getLocation(pt,function(result){
		   if(result){
			   callback(result);
		   }
	   }); 
   }
   
   // 添加地图标准 
   function addMarker(pt,map){
	   var marker = new BMap.Marker(pt);
	   map.clearOverlays();
	   map.addOverlay(marker); 
	   map.panTo(pt);
   }
    
   
   // 显示百度地图到页面中 
   function showMap(callback){
	   getPoint(function(data){
		   convert(new BMap.Point(data.longitude,data.latitude),function(data2){
			  var currentLng = data2.lng;
			  var currentLat = data2.lat;
			  // 创建地图实例 
		   	  var map = new BMap.Map("map");
			  var pt = new BMap.Point(currentLng,currentLat);
			  map.enableScrollWheelZoom();
			  map.centerAndZoom(pt,20);
			  
			  // 添加地图控件类型
			  map.addControl(new BMap.MapTypeControl({
					mapTypes:[
			            BMAP_NORMAL_MAP,
			            BMAP_PERSPECTIVE_MAP,
			            BMAP_SATELLITE_MAP,
			 
			            
			            BMAP_HYBRID_MAP
			        ]}));	  
				map.setCurrentCity("深圳");   
			  
			  map.addEventListener("tilesloaded",function(){
				  $("#result").show();
			  });
			  // 自动定位当前地理位置 
			  var geolocation = new BMap.Geolocation();
			  geolocation.getCurrentPosition(function(r){
				if(this.getStatus() == BMAP_STATUS_SUCCESS){
					addMarker(r.point,map);
					geocoder(r.point,function(address){
						 $("#address").html(address.address);
					 });
				}
				else {
					alert('failed'+this.getStatus());
				}        
			  },{enableHighAccuracy: true});
			  
			  // 添加带有定位的导航控件
			  var navigationControl = new BMap.NavigationControl({
			    // 靠左上角位置
			    anchor: BMAP_ANCHOR_TOP_LEFT,
			    // large类型
			    type: BMAP_NAVIGATION_CONTROL_LARGE,
			    // 启用显示定位
			    enableGeolocation: true
			  });
			  map.addControl(navigationControl); 
			 	
			 
			 // 添加定位控件
			  var geolocationControl = new BMap.GeolocationControl({anchor:BMAP_ANCHOR_TOP_LEFT});
 			  geolocationControl.addEventListener("locationSuccess", function(e){
			    // 定位成功事件
			    var address = '';
			    address += e.addressComponent.province;
			    address += e.addressComponent.city;
			    address += e.addressComponent.district;
			    address += e.addressComponent.street;
			    address += e.addressComponent.streetNumber;
			    addMarker(e.point,map);
			    geocoder(e.point,function(g){
			    	$("#address").html(g.address);
			    });
			    
			   
			  });
			  geolocationControl.addEventListener("locationError",function(e){
				   alert("定位失败:"+e.message);
			  });
			  map.addControl(geolocationControl);
			  
			  // 鼠标点击地图获取地址
			  var getAddress = function(e){
				  addMarker(e.point,map);
				  geocoder(e.point,function(address){
					  callback(address);
				  });
				  
			  };
			  map.addEventListener("click", getAddress);
	   	   });
	   });  
   } 
   
   showMap(function(data){  
	   var address = data.address;  //  详细地理位置
	   var business = data.business;   // 商圈 
	   var poi = data.surroundingPois;  //附件的POI点
	   data.surroundingPois[0].title;
	   for(var i=0; i<poi.length; i++){
		    //alert(poi[i].title+"*****isAccurate***"+poi[i].address);
	   }
	   $("#address").html(address);
   });
   
   </script>
</body>
</html>