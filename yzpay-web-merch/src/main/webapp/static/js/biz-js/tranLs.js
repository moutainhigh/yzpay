/**
 * 交易流水的业务js
 */	

var allUrl = "/merch/";
var ls_nextPage = 2;
var report_nextPage = 2;
var lsMescroll = null;
var reportMescroll = null;
var billMescroll = null;

function pageInit(){
	// 初始化页面时禁用营业日报上拉下拉操作
	$("#reportMescroll").hide();
	// 初始化页面时隐藏营业日报和账单汇总、报表的数据区域
	$("#date-info").hide();
	$("#bill-info").hide();	
}

$(document).ready(function(){
		//创建MeScroll对象
		lsMescroll = initMeScroll("lsMescroll", {
			down:{
				auto:false,//是否在初始化完毕之后自动执行下拉回调callback; 默认true
				callback: lsDownAction//下拉刷新的回调
			},
			up: {
				isBoth: false, // 上拉加载时,如果滑动到列表顶部是否可以同时触发下拉刷新;默认false,两者不可同时触发; 这里为了演示改为true,不必等列表加载完毕才可下拉;
				callback: lsUpAction//上拉加载的回调
			}
		});
		reportMescroll = initMeScroll("reportMescroll", {
			down:{
				auto:false,
				callback: reportDownAction
			},
			up: {
				isBoth: false, 
				callback: reportUpAction
			}
		});
		billMescroll = initMeScroll("billMescroll", {
			down:{
				auto:false,
				callback: billDownAction
			}
		});
		pageInit();
});


/**
 * 营业流水下拉刷新   
 */
function lsDownAction(){
	lsMescroll.lockUpScroll(false);   // 解锁上拉动作
	ls_nextPage = 2;
	$("#lsMescroll .downwarp-progress").parent("div").find("p").eq(1).html("<font class='f'>正在刷新...</font>");
	var url = allUrl+"/sys/reportContr/lsDown";
    setTimeout(function(){ 
    	$.ajax({
		    url:url,
		    type:'POST',
		    async:true,  
		    timeout:5000,   
		    dataType:'text',   
		    beforeSend:function(xhr){},
		    success:function(data,textStatus){
		    	lsMescroll.endSuccess();
		    	// 填充营业流水数据到div
		    	$("#stream-info").html(data);
		    	// 删除报表数据区域
		    	$("#reportSearch_ul").remove();
		    	// 删除账单汇总的数据区域
		    	$("#bill_ul").remove();
		    	// $("#downloadTip").css("top","44px");
		    },
		    error:function(xhr,textStatus){
		    	var t = textStatus;
		    	if(t == 'timeout'){
		    		alert("连接超时,请检查网络连接是否正常");
		    	}
		    	else{
		    		alert('服务器繁忙');
		    	}
		    	lsMescroll.endErr();
		   
		    },
		    
		});
    }, 300);  
}

/**
 * 营业流水上拉翻页
 */
function lsUpAction(){ 
	$("#lsMescroll .upwarp-progress").after("<p class='f'>正在加载...</p>");
	var url  = allUrl + "/sys/reportContr/lsUp?pageIndex="+ls_nextPage;
    setTimeout(function(){ 
    	$.ajax({
		    url:url,
		    type:'POST',
		    async:true,  
		    timeout:5000,   
		    dataType:'json',   
		    beforeSend:function(xhr){},
		    success:function(data,textStatus){
		    	lsMescroll.endSuccess(data.totalCount);   //传参:数据的总数; mescroll会自动判断列表,如果无任何数据,则提示空;列表无下一页数据,则提示无更多数据;
		    	if(ls_nextPage > data.totalPage){
		    		lsMescroll.lockUpScroll(true);   // 锁定上拉动作
		    		$("#lsMescroll .mescroll-upwarp").html("<font style='color:#666'>没有更多数据了...</font>");
		    		$("#lsMescroll .mescroll-upwarp").css("visibility","visible");
		    		return;
		    	}else{
		    		ls_nextPage = ls_nextPage + 1;
		    	}
		    	for(var i=0; i<data.item.length; i++){  
		    		var ajaxData = data.item[i];         
		    		var append_li = 
		    		'<li class=\"aui-list-item stream-info-item\" onclick=\"detail(\'ls\','+'\''+ajaxData.merchantNo+'\',\''+ajaxData.transNum+'\',\'\''+') \"> ';
		    		append_li +='<div class=\"aui-media-list-item-inner\">';
		    		append_li +='<div class=\"aui-list-item-inner\">';
		    		append_li +='<div class=\"aui-list-item-text\">';
		    		append_li += '		<div class=\"aui-list-item-title\">'+ajaxData.storeName+'</div>';
		    		append_li += '		<div class=\"aui-list-item-right or-stream-price\">￥'+ajaxData.transPrice+'</div>';
		    		append_li +='</div>';
		    		append_li += '<div class=\"aui-list-item-text or-stream-exp\">';
		    		append_li += '<div class=\"aui-list-item-title\">'+ajaxData.channel+'&nbsp;&nbsp;'+ajaxData.transTimeStr+'</div>';
		    		append_li += '<div class=\"aui-list-item-right or-stream-state\">';
		    		if(ajaxData.status == '已付款'){
		    			append_li += '<font style=\"color:ff7b1a\">已付款</font>';
		    		}
		    		else if(ajaxData.status == '已退款'){
		    			append_li += '<font style=\"color:#666\">已退款</font>';
		    		}
		    		else if(ajaxData.status == '付款失败'){
		    			append_li += '<font style=\"color:#666\">付款失败</font>';
		    		}
		    		append_li += '</div>';
		    		append_li += '</div>';
		    		append_li += '</div>';
		    		append_li += '</div>';
		    		append_li += '</li>';
			    	$("#stream-info ul li:last").after(append_li);
			    } 
		    },
		    error:function(xhr,textStatus){ 
		    	var t = textStatus;
		    	if(t == 'timeout'){
		    		alert("连接超时,请检查网络连接是否正常");
		    	}
		    	else{
		    		alert('服务器繁忙');
		    	}
		    	  mescroll.endErr();
		    }
		    
		});
    }, 300);  
}


/**
 * 营业日报下拉刷新   
 */
function reportDownAction(){
	reportMescroll.lockUpScroll(false);   // 解锁上拉动作
	report_nextPage = 2;
	$("#reportMescroll .downwarp-progress").parent("div").find("p").eq(1).html("<font class='f'>正在刷新...</font>");
	var url = allUrl+"/sys/reportContr/reportDown?transTime="+$("#transTime").val();
    setTimeout(function(){ 
    	$.ajax({
		    url:url,
		    type:'POST',
		    async:true,  
		    timeout:5000,   
		    dataType:'text',   
		    beforeSend:function(xhr){},
		    success:function(data,textStatus){
		    	reportMescroll.endSuccess();
		    	// 填充营业日报数据到div
		    	$("#report_content").html(data);
		    	// 删除营业流水数据区域
		    	$("#lsDownAction_ul").remove();
		    	// 删除账单汇总的数据区域
		    	$("#bill_ul").remove();
		    	// $("#downloadTip").css("top","44px");
		    },
		    error:function(xhr,textStatus){
		    	var t = textStatus;
		    	if(t == 'timeout'){
		    		alert("连接超时,请检查网络连接是否正常");
		    	}
		    	else{
		    		alert('服务器繁忙');
		    	}
		    	reportMescroll.endErr();
		   
		    }
		   
		});
    }, 300);  
}

// 营业日报上拉翻页
function reportUpAction(){
	$("#reportMescroll .upwarp-progress").after("<p class='f'>正在加载...</p>");
	var url  = allUrl + "/sys/reportContr/reportUp?pageIndex="+report_nextPage;
    setTimeout(function(){ 
    	$.ajax({
		    url:url,
		    type:'POST',
		    async:true,  
		    timeout:5000,   
		    dataType:'json',   
		    beforeSend:function(xhr){},
		    success:function(data,textStatus){
		    	reportMescroll.endSuccess(data.totalCount);   //传参:数据的总数; mescroll会自动判断列表,如果无任何数据,则提示空;列表无下一页数据,则提示无更多数据;
		    	if(report_nextPage > data.totalPage){
		    		reportMescroll.lockUpScroll(true);   // 锁定上拉动作
		    		$("#reportMescroll .mescroll-upwarp").html("<font style='color:#666'>没有更多数据了...</font>");
		    		$("#reportMescroll .mescroll-upwarp").css("visibility","visible");
		    		return;
		    	}else{
		    		report_nextPage = report_nextPage + 1;
		    	}
		    	for(var i=0; i<data.item.length; i++){  
		    		var ajaxData = data.item[i];         
		    		var append_li = '<li class=\"aui-list-item or-details\" onclick=\"detail(\'report\','+'\''+ajaxData.merchantNo+'\',\'\',\''+ajaxData.storeNo+'\'' +')\">';
		    			append_li += '<div class=\"aui-list-item-inner\">';
		    			append_li +='		<div>'+ajaxData.storeName+'</div>';
		    			append_li +='		<div>'+ajaxData.countTran+'笔</div>';
		    			append_li +='		<div>￥'+ajaxData.sumTran+'</div>';
		    			append_li +='</div>';
		    			append_li += '</li>';
		    			$("#report_content ul li:last").after(append_li);
			    } 
		    },
		    error:function(xhr,textStatus){ 
		    	var t = textStatus;
		    	if(t == 'timeout'){
		    		alert("连接超时,请检查网络连接是否正常");
		    	}
		    	else{
		    		alert('服务器繁忙');
		    	}
		    	  mescroll.endErr();
		    }
		    
		});
    }, 300);  
}

/**
 * 账单汇总下拉刷新
 */
function billDownAction(){
	var transTime2 = $("#transTime2").val();
	$("#billMescroll .downwarp-progress").parent("div").find("p").eq(1).html("<font class='f'>正在刷新...</font>");
	var url = allUrl+'/sys/reportContr/billSearch?transTime2='+transTime2;
    setTimeout(function(){ 
    	$.ajax({
		    url:url,
		    type:'POST',
		    async:true,  
		    timeout:5000,   
		    dataType:'text',   
		    beforeSend:function(xhr){},
		    success:function(data,textStatus){
		    	billMescroll.endSuccess();
		    	// 填充账单汇总数据到div
		    	$("#bill_content").html(data);
		    	// 删除营业流水数据区域
		    	$("#lsDownAction_ul").remove();
		    	// 删除营业日报的数据区域
		    	$("#reportSearch_ul").remove();
		    },
		    error:function(xhr,textStatus){
		    	var t = textStatus;
		    	if(t == 'timeout'){
		    		alert("连接超时,请检查网络连接是否正常");
		    	}
		    	else{
		    		alert('服务器繁忙');
		    	}
		    	billMescroll.endErr();
		   
		    }
		   
		});
    }, 300);  
}

//tab页面切换
function tabShow(obj){
	var hasClass = $(obj).find("span").hasClass("aui-active");
	// 避免重复点击菜单
	if(!hasClass){
		$(".mescroll-totop").hide();
		$("#detail_div").hide();
		$(obj).find("span").addClass("aui-active");
		var divs = $(".aui-tab-item");
		for(var i=0; i<divs.length; i++){
			if(! $(divs[i]).is($(obj))){
				$(divs[i]).find("span").removeClass("aui-active");
			}
		}
		var h = $(obj).find("span").html();
		if(h == '流水'){
			lsMescroll.triggerDownScroll();  // 触发下拉刷新操作
			$("#lsMescroll").show();
			$("#stream-info").show();
			$("#date-info").hide();
			$("#reportMescroll").hide();
			$("#bill-info").hide();
			$("#dayReport-info").hide();
		}
		else if(h == '日报'){
			reportMescroll.triggerDownScroll();
			$("#transTime").css("border","1px solid #DDDDDD");
			$("#btnToday1").show();
			$("#btnUpday1").show();
			laydate.render({
				  elem: '#transTime',
				  done: function(value, date, endDate){
					 search();
				}
			});
			$("#date-info").show();
			$("#reportMescroll").show();
			$("#lsMescroll").hide();
			$("#stream-info").hide();
			$("#bill-info").hide();
			$("#dayReport-info").hide();
			
		}
		else if(h == '汇总'){
			billMescroll.triggerDownScroll();
			$("#transTime2").css("border","1px solid #DDDDDD");
			$("#btnToday2").show();
			$("#btnUpday2").show();
			laydate.render({
				  elem: '#transTime2',
				  done: function(value, date, endDate){
					 billSearch();
				}
			});
			$("#bill-info").show();
			$("#lsMescroll").hide();
			$("#stream-info").hide();
			$("#date-info").hide();
			$("#reportMescroll").hide();
			$("#dayReport-info").hide();
		}
	}else{
		return false;
	}
	
}

// 查询明细
function detail(type,merchantNo,transNum,storeNo){
	$(".mescroll-totop").hide();
	// 显示数据明细的数据区域
	$("#detail_div").show();
	var url = "";
	if(type == 'ls'){
		// 禁用营业流水下拉、上拉操作
		$("#lsMescroll").hide();   
		// 隐藏营业流水的数据列表区域
		$("#stream-info").hide();
		$("#report_detail_div").hide();
		url = allUrl + '/sys/reportContr/list/detail?merchantNo='+merchantNo+"&transNum="+transNum;
	}
	if(type == 'report'){
		// 禁用营业日报下拉、上拉操作
		$("#reportMescroll").hide();
	
		// 隐藏营业日报的数据列表区域
		$("#date-info").hide();
		$("#ls_detail_div").hide();
		var transTime = $("#transTime").val();
		url = allUrl + '/sys/reportContr/dayReport/detail?storeNo='+storeNo+"&merchantNo="+merchantNo+"&transTime="+transTime;
	}
	$.ajax({
		url:url,
		type:'POST',
		async:true,  
		timeout:5000,   
		dataType:'text',   
		beforeSend:function(xhr){},
		success:function(data,textStatus){
			$("#detail_div").html(data);
			if(type == 'ls'){
				// 隐藏营业日报 明细的数据区域
				$("#report_detail_div").hide();
			}
				//隐藏营业流水 明细的数据区域
			else if(type == 'report'){
				$("#ls_detail_div").hide();
			}
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

// 点击搜索按钮 查询营业日报数据
function search(){
	var transTime = $("#transTime").val();
	var url = allUrl+'/sys/reportContr/reportSearch?transTime='+transTime;
	$.ajax({
	    url:url,
	    type:'POST',
	    async:true,  
	    timeout:5000,   
	    dataType:'text',   
	    beforeSend:function(xhr){ },
	    success:function(data,textStatus){
	    	$("#report_content").html(data);
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
 *点击搜索按钮 查询账单汇总数据
 */
function billSearch(){
	var transTime2 = $("#transTime2").val();
	var url = allUrl+'/sys/reportContr/billSearch?transTime2='+transTime2;
	$.ajax({
	    url:url,
	    type:'POST',
	    async:true,  
	    timeout:5000,   
	    dataType:'text',   
	    beforeSend:function(xhr){ },
	    success:function(data,textStatus){
	    	$("#bill_content").html(data);
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

// 返回到数据列表页面
function goback(type){
	$(".mescroll-totop").show();
	// 隐藏数据明细的div区域
	$("#detail_div").hide();
	if(type == 'ls'){
		$("#stream-info").show();
		// 解锁上拉下拉操作
		$("#lsMescroll").show();
	}
	if(type == 'report'){
		// 显示数据列表的div区域
		$("#date-info").show();
		// 解锁上拉下拉操作
		$("#reportMescroll").show();
	}
}