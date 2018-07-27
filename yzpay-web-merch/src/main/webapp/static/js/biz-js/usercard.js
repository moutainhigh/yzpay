
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
		var page = 0;
		var merchant = $("#merchant").val();
		url = allUrl+"sys/membercard/userlist?type=1&page="+page+"&merchant="+merchant;
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
			    },
			   // complete:function(){
			   // }
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
		var page = 1;
		//每次加载条数
		var size = 10;
		var merchant = $("#merchant").val();
		if($("#page").val() != ''){
			page = parseInt($("#page").val()) + 1;
		}
		var count = parseInt($("#count").val()); 
		if(count>=size){
			url = allUrl+"sys/membercard/userlistadd?page="+page+"&merchant="+merchant;
	    	setTimeout(function(){ 
	    	$.ajax({
			    url:url,
			    type:'POST',
			    async:true,  
			    timeout:5000,   
			    dataType:'json',   
			    beforeSend:function(xhr){},
			    success:function(data,textStatus){
			    	mescroll.endSuccess();
			    	$("#count").val(data.length);
			    	$("#page").val(page);
			    	var apd = "";
			    	for(var i = 0;i<data.length;i++) {
			    		var name = data[i].name;
			    		var mobile = data[i].mobile;
			    		if(data[i].name == null){
			    			name = "匿名";
			    		}
			    		if(data[i].mobile == null){
			    			mobile = "未激活";
			    		}
			    		apd+="<li class=\"aui-list-item\" onclick=\"location.href=\'"+allUrl;
			    		apd+="/sys/membercard/usershow?id="+data[i].id;
			    		apd+="&merchant="+merchant;
			    		apd+="\'\"><div class=\"aui-list-item-inner aui-list-item-arrow\"> <div class=\"aui-list-item-title\">"+name;
			    		apd+="</div><div class=\"aui-list-item-right\"> <span>"+mobile;
			    		apd+="</span></div></div></li>";
			    		apd+="";
			    	}
			    	$("#user_ul li").last().after(apd);
			    	$("html").css("height","100%");
			    	$("body").css("height","100%");
			    },
			    error:function(xhr,textStatus){ 
			    	  mescroll.endErr();
			    },
			   // complete:function(){
			   // }
			});
	    }, 300);  
	}
	else{
		mescroll.lockUpScroll(true);   // 锁定上拉动作
		mescroll.endSuccess();
		$(".mescroll-upwarp").html("<font class='r' >没有更多数据了...</font>");
		$(".mescroll-upwarp").css("visibility","visible");
	}
}
	//禁止PC浏览器拖拽图片,避免与下拉刷新冲突;如果仅在移动端使用,可删除此代码
	document.ondragstart=function() {return false;}
});
