$(function(){    
	$.ajax({
		type:"post", 
		dataType: "text", 
	    url: consumeTimeurl, 
	    contentType: "application/x-www-form-urlencoded; charset=utf-8",
	    success: function (result){ 
	    	var jsonobj = JSON.parse(result);
	    	var busiNameArray = new Array();
	        var tranNumArray = new Array();
	    	for(var i=0;i<jsonobj.length;i++){
	    		busiNameArray[i] = jsonobj[i].busiName;
		    	tranNumArray[i] = jsonobj[i].tranNum;
	    	}
	        function fetchData(cb) {
	            // 通过 setTimeout 模拟异步加载
	            setTimeout(function () {
	                cb({
	                    categories:busiNameArray,
	                    data: tranNumArray
	                }); 
	            }, 1000);
	        } 
	        
	        var chart = document.getElementById('consume-time-histogram');      
	        var echart = echarts.init(chart);
	        var option = {
	            color: ['#3398DB'],
	            tooltip : {
	                trigger: 'axis',
	                axisPointer : {            // 坐标轴指示器，坐标轴触发有效
	                    type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
	                }
	            },
	            grid: {
	                left: '3%',
	                right: '4%',
	                bottom: '3%',
	                containLabel: true,
	                barWidth:'10%'
	            },
	            xAxis : [
	                {
	                    type : 'category',
	                    data : [],
	                    axisTick: {
	                        alignWithLabel: true
	                    }
	                }
	            ],
	            yAxis : [
	                {
	                    type : 'value'
	                }
	            ],
	            series : [
	                {
	                    name:'刷卡次数',
	                    type:'bar',
	                    barWidth: '20%',
	                    itemStyle: {normal: {
	                        label : {show:true,position:'top',formatter:'{c} %'}
	                    }},
	                    data:[]
	                }
	            ]
	        };
	        echart.showLoading();
	        fetchData(function (data) { 
		     	echart.hideLoading(); 
		     	echart.setOption({
		             xAxis: {
		                 data: data.categories
		             },
		             series: [{
		                 // 根据名字对应到相应的系列
		                 name: '刷卡次数',
		                 data: data.data 
		                 }]
		             });
		        }); 
	         echart.setOption(option);
	        } 
	});
}); 