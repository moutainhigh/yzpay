$.Pgater = (function() {
	
	var browser={
		versions:function(){
			  var u = navigator.userAgent, app = navigator.appVersion;
			  return {         //移动终端浏览器版本信息
			        trident: u.indexOf('Trident') > -1, //IE内核
			        presto: u.indexOf('Presto') > -1, //opera内核
			        webKit: u.indexOf('AppleWebKit') > -1, //苹果、谷歌内核
			        gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, //火狐内核
			        mobile: !!u.match(/AppleWebKit.*Mobile.*/), //是否为移动终端
			        ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
			        android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或uc浏览器
			        iPhone: u.indexOf('iPhone') > -1 , //是否为iPhone或者QQHD浏览器
			        iPad: u.indexOf('iPad') > -1, //是否iPad
			        webApp: u.indexOf('Safari') == -1 //是否web应该程序，没有头部与底部
			  };
		}(),       
		language:(navigator.browserLanguage || navigator.language).toLowerCase()
	};
	
	var agent = navigator.userAgent.toLowerCase();
	var iswx = agent.indexOf('qqbrowser') >= 0;
	alert(agent);
	var File = null;
	if (iswx) {
		alert('iswx');
		File = $("<input type='file' id='csl_gater_file' accept='image/*' capture='camera' multiple='multiple'>");
	} else {
		alert('not');
		File = $("<input type='file' id='csl_gater_file' accept='image/*' multiple='multiple'>");
	}
	File.css('display', 'none');
	return function(target, callBack) {
		console.log(File);
		this.ele = File;
		this.parent = target;
		this.parent.append(this.ele);
		this.bindClk(this.parent, this.ele[0]);
		this.bindFuc(this.ele, callBack);
	};
})();
$.Pgater.prototype.bindFuc = function(ele, callBack) {
	ele.on("change", function() {
		console.log(ele[0].files);
		var all = ele[0].files;
		var reader = new FileReader();
		var album = [];
		console.log(all.length);
		var length = all.length;
		var i = 0;
		var recur = function() {
			console.log(all[i]);
			reader.readAsDataURL(all[i]);
			var One = all[i];
			reader.onload = function(e) {
				// alert(One);
				console.log(One);
				One.data = this.result;
				album.push(One);
				i++;
				if (i < length) {
					recur();
				} else {
					ele.value = '';
					// alert(i);
					callBack(album, img);
				}
				;
			};
		};
		recur();
	});
};
$.Pgater.prototype.bindClk = function(ele, tar) {
	ele.on('click', function() {
		tar.click();
	});
};