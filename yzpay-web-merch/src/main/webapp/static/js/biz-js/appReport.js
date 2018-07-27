	var allUrl = "/merch/";
	
	/**
	 * 页面组件初始化
	 */
	function pageInit(){
		$("#dayReport-info").show();
		$("#reportMescroll").show();
		
		$("#dayReport2-info").hide();
		$("#reportMescroll2").hide();
		$("#dayReport3-info").hide();
		$("#reportMescroll3").hide();
		$("#dayReport4-info").hide();
		$("#reportMescroll4").hide();
	}
	
	var reportMescroll = null;
	var reportMescroll2 = null;
	var reportMescroll3 = null;
	var reportMescroll4 = null;
	
	/**
	 * 实例化上拉下拉组件
	 */
	$(document).ready(function(){
		pageInit();
		laydate.render({
			  elem: '#transTime3',
			  done: function(value, date, endDate){
				  sumTrans();
				  time_report();
			} 
		});
		time_report();
		
		//创建MeScroll对象
		reportMescroll = initMeScroll("reportMescroll", {
			down:{
				auto:false,//是否在初始化完毕之后自动执行下拉回调callback; 默认true
				callback: reportDownAction//下拉刷新的回调
			}
			
		});
		reportMescroll2 = initMeScroll("reportMescroll2", {
			down:{
				auto:false,
				callback: reportDownAction2
			}
		});
		reportMescroll3 = initMeScroll("reportMescroll3", {
			down:{
				auto:false,
				callback: reportDownAction3
			}
		});
		reportMescroll4 = initMeScroll("reportMescroll4", {
			down:{
				auto:false,
				callback: reportDownAction4
			}
		});
		
		reportMescroll.lockUpScroll(true);   // 锁定上拉动作
		reportMescroll2.lockUpScroll(true);   
		reportMescroll3.lockUpScroll(true); 
		reportMescroll4.lockUpScroll(true);   
	});
	

	/**
	 * 按时间段显示的报表下拉刷新   
	 */
	function reportDownAction(){
		$("#reportMescroll .downwarp-progress").parent("div").find("p").eq(1).html("<font class='f'>正在刷新...</font>");
		sumTrans();
		time_report();
		reportMescroll.endSuccess();
	}
	
	/**
	 * 按日显示的报表下拉刷新   
	 */
	function reportDownAction2(){
		$("#reportMescroll2 .downwarp-progress").parent("div").find("p").eq(1).html("<font class='f'>正在刷新...</font>");
		sumTransByDay();
		day_report();
		reportMescroll2.endSuccess();
	}
	
	/**
	 * 按周显示的报表下拉刷新   
	 */
	function reportDownAction3(){
		$("#reportMescroll3 .downwarp-progress").parent("div").find("p").eq(1).html("<font class='f'>正在刷新...</font>");
		sumTransByWeek();
		week_report();
		reportMescroll3.endSuccess();
	}
	
	/**
	 * 按月显示的报表下拉刷新   
	 */
	function reportDownAction4(){
		$("#reportMescroll4 .downwarp-progress").parent("div").find("p").eq(1).html("<font class='f'>正在刷新...</font>");
		sumTransByMonth();
		month_report();
		reportMescroll4.endSuccess();
	}
	


	/**
	 * tab页面切换
	 */ 
	function tabShow(obj){
		var hasClass = $(obj).find("span").hasClass("aui-active");
		// 避免重复点击菜单
		if(!hasClass){
			$(obj).find("span").addClass("aui-active");
			var divs = $(".aui-tab-item");
			for(var i=0; i<divs.length; i++){
				if(! $(divs[i]).is($(obj))){
					$(divs[i]).find("span").removeClass("aui-active");
				}
			}
			var h = $(obj).find("span").html();
			if(h == '按时间'){
				
				$("#dayReport-info").show();
				$("#reportMescroll").show();
				sumTrans();
				time_report();
				
				$("#dayReport2-info").hide();
				$("#reportMescroll2").hide();
				$("#dayReport3-info").hide();
				$("#reportMescroll3").hide();
				$("#dayReport4-info").hide();
				$("#reportMescroll4").hide();
			}
			else if(h == '按日'){
				$("#dayReport2-info").show();
				$("#reportMescroll2").show();
				sumTransByDay();
				day_report();
				
				$("#dayReport-info").hide();
				$("#reportMescroll").hide();
				$("#dayReport3-info").hide();
				$("#reportMescroll3").hide();
				$("#dayReport4-info").hide();
				$("#reportMescroll4").hide();
			}
			else if(h == '按周'){
				$("#dayReport3-info").show();
				$("#reportMescroll3").show();
				sumTransByWeek();
				week_report();
				
				$("#dayReport2-info").hide();
				$("#reportMescroll2").hide();
				$("#dayReport-info").hide();
				$("#reportMescroll").hide();
				$("#dayReport4-info").hide();
				$("#reportMescroll4").hide();
			}
			else if(h == '按月'){
				$("#dayReport4-info").show();
				$("#reportMescroll4").show();
				sumTransByMonth();
				month_report();
				
				$("#dayReport-info").hide();
				$("#reportMescroll").hide();
				$("#dayReport2-info").hide();
				$("#reportMescroll2").hide();
				$("#dayReport3-info").hide();
				$("#reportMescroll3").hide();
			}
		}else{
			return false;
		}
	}
	
	
	/**
	 * 按时间统计 某个商户的总交易笔数和总交易金额 
	 */
	function sumTrans(){
		var cashierNo = $("#cashierNo").val();
		var transTime = $("#transTime3").val();
		var url = allUrl+'/transContr/printSumTrans?transTime='+transTime;
		$.ajax({
		    url:url,
		    type:'POST',
		    data:{
		    	'cashierNo':cashierNo
		    },
		    timeout:5000,   
		    dataType:'json',   
		    beforeSend:function(xhr){ },
		    success:function(data,textStatus){
		    	var countTran = data.countTran;
		    	var sumTran = data.sumTran;
		    	if(sumTran == '0'){
		    		sumTran = '0.0';
		    	}
		    	$("#sumTranDiv").html("总计&nbsp;&nbsp;&nbsp;"+countTran+"笔&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;￥"+sumTran);
		    },
		    error:function(xhr,textStatus){
		    	var t = textStatus;
		    	if(t == 'timeout'){
		    		alert("连接超时,请检查网络连接是否正常");
		    	}
		    	else{
		    		alert('服务器繁忙');
		    	}
		    }
		
		});
	}
	
	
	
	
	/**
	 * 按时间查询的报表 
	 */
	function time_report(){
		var transTime = $("#transTime3").val();
		$.ajax({
			type:"post", 
			url:allUrl+'/sys/reportContr/printDayReport?transTime='+transTime,
		    dataType: "json",
		    timeout:5000,   
		    success: function (result){
		    	var _data = new Array();
		    	for(var i=0; i<result.length; i++){
		    		_data.push(result[i]);
		    	}
		    	var myChart = echarts.init(document.getElementById('chartContainer'));
		    	myChart.showLoading({  
		    	    text : '正在读取数据中...'  
		    	}); 

		        // 指定图表的配置项和数据
		        var option = {
		            tooltip : {  //提示框
		                trigger: 'axis',  //触发类型(坐标轴触发)
		                alwaysShowContent:false,  //是否永远显示提示框的内容
		                backgroundColor:'rgba(32,174,252,0.7)', //提示框的背景颜色
		                textStyle:{  //提示框浮层的文本样式
		                },
		                formatter: function (params, ticket, callback){ 
		                   // var dataName =  params[0].name;  x坐标的刻度名称
		                    var dataIndex = params[0].dataIndex; 
		                    var transTime = _data[dataIndex].transTime;
		                    var dataName = parseInt(transTime) - 4;
		                   
		                    var value = params[0].value+"";  
		                    var index = value.indexOf(".");
					    	 if(index > -1){
					    		 var s = value.substring(index+1,value.length);
						    	 if(s.length > 2){
						    		 // 金额保留两位小数四舍五入
						    		 value = parseFloat(value).toFixed(2);
						    	 }
					    	 }
		                    return dataName + '点至'+transTime+'点<br/>交易额'+ value+'元'; 
		                },
		                textStyle:{  //提示框浮层的文本样式
		                	fontSize:8,
		                },
		                triggerOn:'click'
		            },
		            calculable : true,
		            xAxis : [
		                {
		                    type : 'category',
		                    name:'',        //X轴名称单位
		                   // nameLocation:'end', //名称的位置
		                    nameTextStyle:{     //名称的样式
		                        color:'#999',
		                        fontSize:'12px',
		                        align:'left'
		                    },
		                   
		                  nameGap:9,  //名称与X轴的距离
		                    boundaryGap: false,//坐标的刻度是否在中间
		                 //   min:'3',//坐标轴刻度最小值
		                 //   max:'dataMax', //坐标轴刻度的最大值
		                    axisLine:{//坐标轴线条相关设置(颜色等)
		                        lineStyle:{
		                            color:'#ccc'
		                        }
		                    },
		                    axisTick:{ //坐标轴刻度相关设置
		                        length:0//坐标轴刻度的长度  //
		                    },
		                    axisLabel:{ //坐标轴刻度标签
		                    	interval:0,   // 显示全部刻度
		                        margin:7, //刻度标签与轴线之间的距离
		                        textStyle:{
		                            color:"#999",  //坐标轴刻度文字的颜色
		                            fontSize:'12px' //坐标轴刻度文字的大小
		                        }
		                    },
		               		data : (function(){
	                        	var res = new Array();
	                        	var length = _data.length;
	                        	for(var i=0; i<length; i++){
	                        		res.push(
	                        			_data[i].transTime+"点"
                            		);
	                        	}
	                        	return res;	
	                        })()
		                

		                }
		            ],//X轴设置
		            yAxis : [
		                {
		                    type : 'value', //类型数值轴
		                    name:'交易额',    //坐标轴名称
		                    nameTextStyle:{     //名称的样式
		                        color:'#999',
		                        fontSize:'12px'
		                    },
		                    nameGap:15,  //名称与Y轴的距离
		                    axisTick:{ //坐标轴刻度相关设置
		                        length:'0' //我设置成0了
		                    },
		                    axisLine:{//坐标轴线条相关设置(颜色等)
		                        lineStyle:{
		                            color:'#ccc'
		                        }
		                    },
		                    axisLabel:{//坐标轴标签相关设置,距离颜色等
		                        margin:7,
		                        formatter: '{value}',//标签内容内置的格式转化器比如这个表示在后面加一个c
		                        textStyle:{
		                            color:"#999",  //坐标轴刻度文字的颜色
		                            fontSize:'12px' //坐标轴刻度文字的大小
		                        },
		                    },
		                    splitLine:{    // 分割线
		                        show:true,
		                        lineStyle:{
		                        	color:'#DDDDDD',
		                        	width: 1
		                        }
		                    }
		                }
		            ],
		            grid:{ //直角坐标系内绘图网格
		            	top:40
		            },
		            series : [  //系列列表
		                {
		                    name:'交易额',   //系列名称 用于tooltip的显示
		                    type:'line',
		                    data: (function(){
	                        	var res = [];
	                        	for(var i=0; i<_data.length; i++){
	                        		res.push({
                            			value:_data[i].sumTran   
                            		});
	                        	}
	                        	return res;
	                        })(),
		                    itemStyle:{  //折线拐点的样式
		                        normal:{
		                            color:'#20aefc',  //折线拐点的颜色
		                        }
		                    },
		                    lineStyle:{  //线条的样式
		                        normal:{
		                            color:'#20aefc',  //折线颜色
		                        }
		                    },
		                    areaStyle:{ //区域填充样式
		                        normal:{
		                            //线性渐变
		                            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
		                                offset: 0, color: '#b1e3fe' // 0% 处的颜色
		                            }, {
		                                offset: 1, color: '#fff' // 100% 处的颜色
		                            }], false)
		                        }
		                    }
		                }

		            ]
		        };
		        
		        myChart.setOption(option);
		        setTimeout(function(){
		        	myChart.hideLoading();
		        },500);
		        
		    },
		    error:function(err){
		    	
		    }
		});
	}
	
	
	/**
	 * 按时间查询的报表 
	 */
	function time_report(){
		var cashierNo = $("#cashierNo").val();
		var transTime = $("#transTime3").val();
		$.ajax({
			url:allUrl+'/transContr/printDayReport?transTime='+transTime,
		    dataType: "json",
		    data:{
		    	'cashierNo':cashierNo
		    },
		    timeout:5000,   
		    success: function (result){
		    	var _data = new Array();
		    	for(var i=0; i<result.length; i++){
		    		_data.push(result[i]);
		    	}
		    	var myChart = echarts.init(document.getElementById('chartContainer'));
		    	myChart.showLoading({  
		    	    text : '正在读取数据中...'  
		    	}); 

		        // 指定图表的配置项和数据
		        var option = {
		            tooltip : {  //提示框
		                trigger: 'axis',  //触发类型(坐标轴触发)
		                alwaysShowContent:false,  //是否永远显示提示框的内容
		                backgroundColor:'rgba(32,174,252,0.7)', //提示框的背景颜色
		                textStyle:{  //提示框浮层的文本样式
		                },
		                formatter: function (params, ticket, callback){ 
		                   // var dataName =  params[0].name;  x坐标的刻度名称
		                    var dataIndex = params[0].dataIndex; 
		                    var transTime = _data[dataIndex].transTime;
		                    var dataName = parseInt(transTime) - 4;
		                   
		                    var value = params[0].value+"";  
		                    var index = value.indexOf(".");
					    	 if(index > -1){
					    		 var s = value.substring(index+1,value.length);
						    	 if(s.length > 2){
						    		 // 金额保留两位小数四舍五入
						    		 value = parseFloat(value).toFixed(2);
						    	 }
					    	 }
		                    return dataName + '点至'+transTime+'点<br/>交易额'+ value+'元'; 
		                },
		                textStyle:{  //提示框浮层的文本样式
		                	fontSize:8,
		                },
		                triggerOn:'click'
		            },
		            calculable : true,
		            xAxis : [
		                {
		                    type : 'category',
		                    name:'',        //X轴名称单位
		                   // nameLocation:'end', //名称的位置
		                    nameTextStyle:{     //名称的样式
		                        color:'#999',
		                        fontSize:'12px',
		                        align:'left'
		                    },
		                   
		                  nameGap:9,  //名称与X轴的距离
		                    boundaryGap: false,//坐标的刻度是否在中间
		                 //   min:'3',//坐标轴刻度最小值
		                 //   max:'dataMax', //坐标轴刻度的最大值
		                    axisLine:{//坐标轴线条相关设置(颜色等)
		                        lineStyle:{
		                            color:'#ccc'
		                        }
		                    },
		                    axisTick:{ //坐标轴刻度相关设置
		                        length:0//坐标轴刻度的长度  //
		                    },
		                    axisLabel:{ //坐标轴刻度标签
		                    	interval:0,   // 显示全部刻度
		                        margin:7, //刻度标签与轴线之间的距离
		                        textStyle:{
		                            color:"#999",  //坐标轴刻度文字的颜色
		                            fontSize:'12px' //坐标轴刻度文字的大小
		                        }
		                    },
		               		data : (function(){
	                        	var res = new Array();
	                        	var length = _data.length;
	                        	for(var i=0; i<length; i++){
	                        		res.push(
	                        			_data[i].transTime+"点"
                            		);
	                        	}
	                        	return res;	
	                        })()
		                

		                }
		            ],//X轴设置
		            yAxis : [
		                {
		                    type : 'value', //类型数值轴
		                    name:'交易额',    //坐标轴名称
		                    nameTextStyle:{     //名称的样式
		                        color:'#999',
		                        fontSize:'12px'
		                    },
		                    nameGap:15,  //名称与Y轴的距离
		                    axisTick:{ //坐标轴刻度相关设置
		                        length:'0' //我设置成0了
		                    },
		                    axisLine:{//坐标轴线条相关设置(颜色等)
		                        lineStyle:{
		                            color:'#ccc'
		                        }
		                    },
		                    axisLabel:{//坐标轴标签相关设置,距离颜色等
		                        margin:7,
		                        formatter: '{value}',//标签内容内置的格式转化器比如这个表示在后面加一个c
		                        textStyle:{
		                            color:"#999",  //坐标轴刻度文字的颜色
		                            fontSize:'12px' //坐标轴刻度文字的大小
		                        },
		                    },
		                    splitLine:{    // 分割线
		                        show:true,
		                        lineStyle:{
		                        	color:'#DDDDDD',
		                        	width: 1
		                        }
		                    }
		                }
		            ],
		            grid:{ //直角坐标系内绘图网格
		            	top:40
		            },
		            series : [  //系列列表
		                {
		                    name:'交易额',   //系列名称 用于tooltip的显示
		                    type:'line',
		                    data: (function(){
	                        	var res = [];
	                        	for(var i=0; i<_data.length; i++){
	                        		res.push({
                            			value:_data[i].sumTran   
                            		});
	                        	}
	                        	return res;
	                        })(),
		                    itemStyle:{  //折线拐点的样式
		                        normal:{
		                            color:'#20aefc',  //折线拐点的颜色
		                        }
		                    },
		                    lineStyle:{  //线条的样式
		                        normal:{
		                            color:'#20aefc',  //折线颜色
		                        }
		                    },
		                    areaStyle:{ //区域填充样式
		                        normal:{
		                            //线性渐变
		                            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
		                                offset: 0, color: '#b1e3fe' // 0% 处的颜色
		                            }, {
		                                offset: 1, color: '#fff' // 100% 处的颜色
		                            }], false)
		                        }
		                    }
		                }

		            ]
		        };
		        
		        myChart.setOption(option);
		        setTimeout(function(){
		        	myChart.hideLoading();
		        },500);
		        
		    },
		    error:function(err){
		    	
		    }
		});
	}
	
	/**
	 * 在某一天的日期上加或者减天数
	 * @param date
	 * @param days
	 * @returns {String}
	 */
    function setDate(y,m,d,n){ 
        var date= new Date(y,m,d); 
       
        date.setDate(date.getDate()+n); 
        var m=date.getMonth(); 
     
        var month = m;
        var _d = date.getDate();
        if(m < 10){
        	month = '0'+m;
        }
        if(date.getDate() < 10){
        	_d = '0'+date.getDate();
        }
        return date.getFullYear()+'.'+month+'.'+_d;
    } 
    
    /**
     * 在某一天的日期上加或者减月数
     * @param date
     * @param days
     * @returns {String}
     */
    function setMonth(y,m,d,n){
    	var date= new Date(y,m,d); 
    	date.setMonth(date.getMonth()+n); 
        var m=date.getMonth(); 
      
        var month = date.getMonth();
        var _d = date.getDate();
        if(m < 10){
        	month = '0'+m;
        }
        if(date.getDate() < 10){
        	_d = '0'+date.getDate();
        }
        return date.getFullYear()+'.'+month+'.'+_d;
    }
    
    
	
	
	/**
	 * 按日统计某个商户的总交易笔数和总交易金额 
	 */
	function sumTransByDay(){
		var cashierNo = $("#cashierNo").val();
		var url = allUrl+'/transContr/sumTransByDay';
		$.ajax({
		    url:url,
		    type:'POST',
		    timeout:5000,   
		    data:{
		    	'cashierNo':cashierNo
		    },
		    dataType:'json',   
		    beforeSend:function(xhr){ },
		    success:function(data,textStatus){
		    	var countTran = data.countTran;
		    	var sumTran = data.sumTran;
		    	if(sumTran == '0'){
		    		sumTran = '0.0';
		    	}
		    	$("#dayReport2Div").html(data.transTime+"<br/>总计&nbsp;&nbsp;&nbsp;"+countTran+"笔&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;￥"+sumTran);
		    },
		    error:function(xhr,textStatus){
		    	var t = textStatus;
		    	if(t == 'timeout'){
		    		alert("连接超时,请检查网络连接是否正常");
		    	}
		    	else{
		    		alert('服务器繁忙');
		    	}
		    }
		});
	}
	
	/**
	 * 按周统计某个商户的总交易笔数和总交易金额 
	 */
	function sumTransByWeek(){
		var cashierNo = $("#cashierNo").val();
		var url = allUrl+'/transContr/sumTransByWeek';
		$.ajax({
		    url:url,
		    type:'POST',
		    timeout:5000,   
		    dataType:'json',   
		    data:{
		    	'cashierNo':cashierNo
		    },
		    beforeSend:function(xhr){ },
		    success:function(data,textStatus){
		    	var countTran = data.countTran;
		    	var sumTran = data.sumTran;
		    	if(sumTran == '0'){
		    		sumTran = '0.0';
		    	}
		    	$("#dayReport3Div").html(data.transTime+"</br>总计&nbsp;&nbsp;&nbsp;"+countTran+"笔&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;￥"+sumTran);
		    },
		    error:function(xhr,textStatus){
		    	var t = textStatus;
		    	if(t == 'timeout'){
		    		alert("连接超时,请检查网络连接是否正常");
		    	}
		    	else{
		    		alert('服务器繁忙');
		    	}
		    }
		});
	}
	
	
	/**
	 * 按月统计某个商户的总交易笔数和总交易金额 
	 */
	function sumTransByMonth(){
		var cashierNo = $("#cashierNo").val();
		var url = allUrl+'/transContr/sumTransByMonth';
		$.ajax({
		    url:url,
		    type:'POST',
		    timeout:5000,   
		    data:{
		    	'cashierNo':cashierNo
		    },
		    dataType:'json',   
		    beforeSend:function(xhr){ },
		    success:function(data,textStatus){
		    	var countTran = data.countTran;
		    	var sumTran = data.sumTran;
		    	if(sumTran == '0'){
		    		sumTran = '0.0';
		    	}
		    	$("#dayReport4Div").html(data.transTime+"</br>总计&nbsp;&nbsp;&nbsp;"+countTran+"笔&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;￥"+sumTran);
		    },
		    error:function(xhr,textStatus){
		    	var t = textStatus;
		    	if(t == 'timeout'){
		    		alert("连接超时,请检查网络连接是否正常");
		    	}
		    	else{
		    		alert('服务器繁忙');
		    	}
		    }
		});
	}
	

	/**
	 * 按日查询的报表
	 */
	function day_report(){
		var cashierNo = $("#cashierNo").val();
		$.ajax({
			type:"post", 
			url:allUrl+'/transContr/printReportByDay',
		    dataType: "json",
		    timeout:5000,   
		    data:{
		    	'cashierNo':cashierNo
		    },
		    success: function (result){
		    	var _data = new Array();
		    	for(var i=0; i<result.length; i++){
		    		_data.push(result[i]);
		    	}
		    	var myChart = echarts.init(document.getElementById('chartContainer2'));
		    	myChart.showLoading({  
		    		
		    	     text : '正在读取数据中...'  
		    	 });
		        // 指定图表的配置项和数据
		        var option = {
		        tooltip : {  //提示框
				      trigger: 'axis',  //触发类型(坐标轴触发)
				      alwaysShowContent:false,  //是否永远显示提示框的内容
				      backgroundColor:'rgba(32,174,252,0.7)', //提示框的背景颜色
				      formatter: function (params, ticket, callback){ 
				    	 // 获取最后一条记录中的transTime
				    	 var lastData =  _data[_data.length - 1];
				    	 var transTime = lastData.transTime;
				    	 // 以.分割年月日的日期
				    	 var s = transTime.split(".");
				    	 var year = parseInt(s[0]);
				    	 var month = parseInt(s[1]);
				    	 var day = parseInt(s[2]);
				    	 
				    	 // 获取当前日期的上一个月的日期
				    	 var n = setMonth(year,month,day,-1);
				    	 
				    	 var dataName = ""; //params[0].name;
				    	 var value = params[0].value+"";  
				    	 var dataIndex = params[0].dataIndex; 
				    	 transTime = _data[dataIndex].transTime;
				    	 var index = value.indexOf(".");
				    	 if(index > -1){
				    		 var a = value.substring(index+1,value.length);
				    		
					    	 if(a.length > 2){
					    		 // 金额保留两位小数四舍五入
					    		 value = parseFloat(value).toFixed(2);
					    	 }
				    	 }
				    	
				    	 if(dataIndex < 1){
				    		 dataName = n + '至<br/>'+ _data[0].transTime+'<br/>交易额'+value+'元';
				    	 }
				    	 else{
				    		 // 以.分割年月日的日期
				    		 var dateArr = _data[dataIndex - 1].transTime.split(".");
				    		 year = parseInt(dateArr[0]);
					    	 month = parseInt(dateArr[1]);
					    	 day = parseInt(dateArr[2]); 
					    	 n = setDate(year,month,day,1);
				    		 dataName = n + '至<br/>'+ _data[dataIndex].transTime+'</br>交易额'+value+'元';
				    	 }
				    	 return dataName; 
				      },
				      textStyle:{  //提示框浮层的文本样式
				          fontSize:8,
				      },
				      triggerOn:'click'
				},
		            calculable : true,
		            xAxis : [
		                {
		                    type : 'category',
		                    name:'',        //X轴名称单位
		                    nameLocation:'end', //名称的位置
		                    nameTextStyle:{     //名称的样式
		                        color:'#999',
		                        fontSize:'12px'
		                    },
		                    nameGap:9,  //名称与X轴的距离
		                    
		                    boundaryGap: false,//坐标的刻度是否在中间
		                 //   min:'3',//坐标轴刻度最小值
		                 //   max:'dataMax', //坐标轴刻度的最大值
		                    axisLine:{//坐标轴线条相关设置(颜色等)
		                        lineStyle:{
		                            color:'#ccc'
		                        }
		                    },
		                    axisTick:{ //坐标轴刻度相关设置
		                        length:'0' //我把长度设置成0了
		                    },
		                    axisLabel:{ //坐标轴刻度标签
		                    	//rotate:45, //刻度旋转45度角
		                    	interval:0,   // 显示全部刻度
		                        margin:7, //刻度标签与轴线之间的距离
		                        textStyle:{
		                            color:"#999",  //坐标轴刻度文字的颜色
		                            fontSize:'12px' //坐标轴刻度文字的大小
		                        }
		                    },  
		                    data : (function(){
	                        	var res = new Array();
	                        	var length = _data.length;
	                        	for(var i=0; i<length; i++){
	                        		var index = _data[i].transTime.indexOf('.');
	                        		res.push(
	                        			_data[i].transTime.substring(index+1,_data[i].transTime.length)
                            		);
	                        	}
	                        	return res;	
	                        })()
	                        

		                }
		            ],//X轴设置
		            yAxis : [
		                {
		                    type : 'value', //类型数值轴
		                    name:'交易额',    //坐标轴名称
		                    nameTextStyle:{     //名称的样式
		                        color:'#999',
		                        fontSize:'12px'
		                    },
		                    nameGap:15,  //名称与Y轴的距离
		                    axisTick:{ //坐标轴刻度相关设置
		                        length:'0' //我设置成0了
		                    },
		                    axisLine:{//坐标轴线条相关设置(颜色等)
		                        lineStyle:{
		                            color:'#ccc'
		                        }
		                    },
		                    axisLabel:{//坐标轴标签相关设置,距离颜色等
		                        margin:7,
		                        //formatter: '{value} °C',//标签内容内置的格式转化器比如这个表示在后面加一个c
		                        textStyle:{
		                            color:"#999",  //坐标轴刻度文字的颜色
		                            fontSize:'12px' //坐标轴刻度文字的大小
		                        },
		                    },
		                    splitLine:{    // 分割线
		                        show:true,
		                        lineStyle:{
		                        	color:'#DDDDDD',
		                        	width: 1
		                        }
		                    }
		                }
		            ],
		            grid:{ //直角坐标系内绘图网格
		            	top:40
		            },
		            series : [  //系列列表
		                {
		                    name:'交易额',   //系列名称 用于tooltip的显示
		                    type:'line',
		                    data: (function(){
	                        	var res = [];
	                        	for(var i=0; i<_data.length; i++){
	                        		res.push({
	                        			value:_data[i].sumTran
                            		});
	                        	}
	                        	return res;
	                        })(),
		                    itemStyle:{  //折线拐点的样式
		                        normal:{
		                            color:'#20aefc',  //折线拐点的颜色
		                        }
		                    },
		                    lineStyle:{  //线条的样式
		                        normal:{
		                            color:'#20aefc',  //折线颜色
		                        }
		                    },
		                    areaStyle:{ //区域填充样式
		                        normal:{
		                            //线性渐变
		                            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
		                                offset: 0, color: '#b1e3fe' // 0% 处的颜色
		                            }, {
		                                offset: 1, color: '#fff' // 100% 处的颜色
		                            }], false)
		                        }
		                    }
		                }

		            ]
		        };
		        myChart.setOption(option);
		        setTimeout(function(){
		        	myChart.hideLoading();
		        },500);
		    },
		    error:function(err){
		    	
		    }
		});
	}
	

	
	/**
	 * 按周查询的报表
	 */
	function week_report(){
		var cashierNo = $("#cashierNo").val();
		$.ajax({
			type:"post", 
			url:allUrl+'/transContr/printReportByWeek',
		    dataType: "json",
		    timeout:5000,   
		    data:{
		    	'cashierNo':cashierNo
		    },
		    async:false,  
		    cache:false,
		    success: function (result){
		    	var _data = new Array();
		    	for(var i=0; i<result.length; i++){
		    		var obj = result[i];
		    		_data.push(obj);
		    	}
		    	var myChart = echarts.init(document.getElementById('chartContainer3'));
		    	myChart.showLoading({  
		    	     text : '正在读取数据中...'  
		    	 });
		        // 指定图表的配置项和数据
		        var option = {
		            tooltip : {  //提示框
		                trigger: 'axis',  //触发类型(坐标轴触发)
		                alwaysShowContent:false,  //是否永远显示提示框的内容
		                backgroundColor:'rgba(32,174,252,0.7)', //提示框的背景颜色
		                textStyle:{  //提示框浮层的文本样式
		                	fontSize:8,
		                },
		      	      formatter: function (params, ticket, callback){ 
					    	 var dataName = ""; //params[0].name;
					    	 var value = params[0].value+"";  
					    	 var dataIndex = params[0].dataIndex; 
					    	 var startTransTime = _data[dataIndex].transTime.split("-")[0];
					    	 var endTransTime = _data[dataIndex].transTime.split("-")[1];
					    	 var index = value.indexOf(".");
					    	 if(index > -1){
					    		 var s = value.substring(index+1,value.length);
					    		
						    	 if(s.length > 2){
						    		 // 金额保留两位小数四舍五入
						    		 value = parseFloat(value).toFixed(2);
						    	 }
					    	 }
					    	
					    	 dataName = startTransTime + '至<br/>'+ endTransTime+'<br/>交易额'+value+'元';
					    	 return dataName; 
					  }
		            },
		            calculable : true,
		            xAxis : [
		                {
		                    type : 'category',
		                    name:'',        //X轴名称单位
		                    nameLocation:'end', //名称的位置
		                    nameTextStyle:{     //名称的样式
		                        color:'#999',
		                        fontSize:'12px'
		                    },
		                    nameGap:9,  //名称与X轴的距离
		                    
		                    boundaryGap: false,//坐标的刻度是否在中间
		                   // min:'3',//坐标轴刻度最小值
		                   // max:'dataMax', //坐标轴刻度的最大值
		                    axisLine:{//坐标轴线条相关设置(颜色等)
		                        lineStyle:{
		                            color:'#ccc'
		                        }
		                    },
		                    axisTick:{ //坐标轴刻度相关设置
		                        length:'0' //我把长度设置成0了
		                    },
		                    axisLabel:{ //坐标轴刻度标签
		                    	interval:0,   // 显示全部刻度
		                        margin:7, //刻度标签与轴线之间的距离
		                        textStyle:{
		                            color:"#999",  //坐标轴刻度文字的颜色
		                            fontSize:'12px' //坐标轴刻度文字的大小
		                        }
		                    },
		                    data : (function(){
	                        	var res = new Array();
	                        	for(var i=0; i<_data.length; i++){
	                        		var transTime = _data[i].transTime.split("-")[1];
	                        		var index = transTime.indexOf('.');
	                        		res.push(
	                        			transTime.substring(index+1,transTime.length)
                            		);
	                        	}
	                        	return res;
	                        })()
	                        

		                }
		            ],//X轴设置
		            yAxis : [
		                {
		                    type : 'value', //类型数值轴
		                    name:'交易额',    //坐标轴名称
		                    nameTextStyle:{     //名称的样式
		                        color:'#999',
		                        fontSize:'12px'
		                    },
		                    nameGap:15,  //名称与Y轴的距离
		                    axisTick:{ //坐标轴刻度相关设置
		                        length:'0' //我设置成0了
		                    },
		                    axisLine:{//坐标轴线条相关设置(颜色等)
		                        lineStyle:{
		                            color:'#ccc'
		                        }
		                    },
		                    axisLabel:{//坐标轴标签相关设置,距离颜色等
		                        margin:7,
		                        //formatter: '{value} °C',//标签内容内置的格式转化器比如这个表示在后面加一个c
		                        textStyle:{
		                            color:"#999",  //坐标轴刻度文字的颜色
		                            fontSize:'12px' //坐标轴刻度文字的大小
		                        },
		                    },
		                    splitLine:{    // 分割线
		                        show:true,
		                        lineStyle:{
		                        	color:'#DDDDDD',
		                        	width: 1
		                        }
		                    }
		                }
		            ],
		            grid:{ //直角坐标系内绘图网格
		            	top:40
		            },
		            series : [  //系列列表
		                {
		                    name:'交易额',   //系列名称 用于tooltip的显示
		                    type:'line',
		                    data: (function(){
	                        	var res = [];
	                        	for(var i=0; i<_data.length; i++){
	                        		res.push({
                            			value:_data[i].sumTran
                            		});
	                        	}
	                        	return res;
	                        })(),
		                    itemStyle:{  //折线拐点的样式
		                        normal:{
		                            color:'#20aefc',  //折线拐点的颜色
		                        }
		                    },
		                    lineStyle:{  //线条的样式
		                        normal:{
		                            color:'#20aefc',  //折线颜色
		                        }
		                    },
		                    areaStyle:{ //区域填充样式
		                        normal:{
		                            //线性渐变
		                            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
		                                offset: 0, color: '#b1e3fe' // 0% 处的颜色
		                            }, {
		                                offset: 1, color: '#fff' // 100% 处的颜色
		                            }], false)
		                        }
		                    }
		                }

		            ]
		        };
		  
		        myChart.setOption(option);
		        setTimeout(function(){
		        	myChart.hideLoading();
		        },500);
		    },
		    error:function(err){
		    	
		    }
		});
	}
	


	/**
	 * 按月查询的报表
	 */
	function month_report(){
		var cashierNo = $("#cashierNo").val();
		$.ajax({
			type:"post", 
			 data:{
			    	'cashierNo':cashierNo
			 },
			url:allUrl+'/transContr/printReportByMonth',
		    dataType: "json",
		    timeout:5000,   
		    async:false,  
		    cache:false,
		    success: function (result){
		    	var _data = new Array();
		    	for(var i=0; i<result.length; i++){
		    		_data.push(result[i]);
		    	}
		    	var myChart = echarts.init(document.getElementById('chartContainer4'));
		    	myChart.showLoading({  
		    	     text : '正在读取数据中...'  
		    	 });
		        // 指定图表的配置项和数据
		        var option = {
		            tooltip : {  //提示框
		                trigger: 'axis',  //触发类型(坐标轴触发)
		                alwaysShowContent:false,  //是否永远显示提示框的内容
		                backgroundColor:'rgba(32,174,252,0.7)', //提示框的背景颜色
		                textStyle:{  //提示框浮层的文本样式
		                	fontSize:8,
		                }
		            },
		            formatter: function (params, ticket, callback){ 
				    	 var dataName = ""; //params[0].name;
				    	 var value = params[0].value;  
				    	 var dataIndex = params[0].dataIndex; 
				    	 if(dataIndex < _data.length - 1){
				    		 var startTransTime = _data[dataIndex].transTime.split("-")[0];
					    	 var endTransTime = _data[dataIndex].transTime.split("-")[1];
					    	 dataName = startTransTime + '至<br/>'+ endTransTime+'<br/>交易额'+value+'元';
					    	
				    	 }else{
				    		 dataName = _data[dataIndex].transTime+'<br/>交易额'+value+'元';
				    	 }
				    	
				    	 return dataName;
				    	
				  },
		            calculable : true,
		            xAxis : [
		                {
		                    type : 'category',
		                    name:'',        //X轴名称单位
		                    nameLocation:'end', //名称的位置
		                    nameTextStyle:{     //名称的样式
		                        color:'#999',
		                        fontSize:'12px'
		                    },
		                    nameGap:9,  //名称与X轴的距离
		                    boundaryGap: false,//坐标的刻度是否在中间
		                   // min:'3',//坐标轴刻度最小值
		                  //  max:'dataMax', //坐标轴刻度的最大值
		                    axisLine:{//坐标轴线条相关设置(颜色等)
		                        lineStyle:{
		                            color:'#ccc'
		                        }
		                    },
		                    axisTick:{ //坐标轴刻度相关设置
		                        length:'0' //我把长度设置成0了
		                    },
		                    axisLabel:{ //坐标轴刻度标签
		                    	//rotate:45, //刻度旋转45度角
		                    	interval:0,   // 显示全部刻度
		                        margin:7, //刻度标签与轴线之间的距离
		                        textStyle:{
		                            color:"#999",  //坐标轴刻度文字的颜色
		                            fontSize:'12px' //坐标轴刻度文字的大小
		                        }
		                    },    
		                    data:(function(){
	                        	var res = new Array();
	                        	for(var i=0; i<_data.length; i++){
	                        		var transTime = null;
	                        		var index = -1;
	                        		var length = -1;
	                        		if(i < _data.length - 1){
	                        			index = _data[i].transTime.split("-")[1].indexOf(".");
	                        			length = _data[i].transTime.split("-")[1].length;
	                        			transTime = parseInt(_data[i].transTime.split("-")[1].substring(index+1,length))+"月";
	                        		}else{
	                        			index = _data[i].transTime.indexOf(".");
	                        			length = _data[i].transTime.length;
	                        			transTime = parseInt(_data[i].transTime.substring(index+1,length))+"月";
	                        		}
	                        		res.push(
	                        			transTime
                            		);
	                        	}
	                        	return res;
	                        })()
		                }
		            ],//X轴设置
		            yAxis : [
		                {
		                    type : 'value', //类型数值轴
		                    name:'交易额',    //坐标轴名称
		                    nameTextStyle:{     //名称的样式
		                        color:'#999',
		                        fontSize:'12px'
		                    },
		                    
		                    nameGap:15,  //名称与Y轴的距离
		                    axisTick:{ //坐标轴刻度相关设置
		                        length:'0' //我设置成0了
		                    },
		                    axisLine:{//坐标轴线条相关设置(颜色等)
		                        lineStyle:{
		                            color:'#ccc'
		                        }
		                    },
		                    axisLabel:{//坐标轴标签相关设置,距离颜色等
		                        margin:7,
		                        //formatter: '{value} °C',//标签内容内置的格式转化器比如这个表示在后面加一个c
		                        textStyle:{
		                            color:"#999",  //坐标轴刻度文字的颜色
		                            fontSize:'12px' //坐标轴刻度文字的大小
		                        },
		                    },
		                    splitLine:{    // 分割线
		                        show:true,
		                        lineStyle:{
		                        	color:'#DDDDDD',
		                        	width: 1
		                        }
		                    }
		                }
		            ],
		            grid:{ //直角坐标系内绘图网格
		              top:40
		              
		            },
		            series : [  //系列列表
		                {
		                    name:'交易额',   //系列名称 用于tooltip的显示
		                    type:'line',
		                    data: (function(){
	                        	var res = [];
	                        	for(var i=0; i<_data.length; i++){ 
	                        		res.push({
                            			value:_data[i].sumTran
                            		});
	                        	}
	                        	return res;
	                        })(),
		                    itemStyle:{  //折线拐点的样式
		                        normal:{
		                            color:'#20aefc',  //折线拐点的颜色
		                        }
		                    },
		                    lineStyle:{  //线条的样式
		                        normal:{
		                            color:'#20aefc',  //折线颜色
		                        }
		                    },
		                    areaStyle:{ //区域填充样式
		                        normal:{
		                            //线性渐变
		                            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
		                                offset: 0, color: '#b1e3fe' // 0% 处的颜色
		                            }, {
		                                offset: 1, color: '#fff' // 100% 处的颜色
		                            }], false)
		                        }
		                    }
		                }

		            ]
		        };
		        myChart.setOption(option);
		        setTimeout(function(){
		        	myChart.hideLoading();
		        },500);
		       
		    },
		    error:function(err){
		    	
		    }
		});
	}