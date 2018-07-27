/**
 * ajax 请求重定向
 */
 $.ajaxSetup( {  
	    //设置ajax请求结束后的执行动作  
	    complete:function(xhr,textStatus){
			var redirect = xhr.getResponseHeader("REDIRECT");//若HEADER中含有REDIRECT说明后端想重定向
			var dpLogin = xhr.getResponseHeader("DPLOGIN");//若HEADER中含有DPLOGIN说明是重复登录
			var UA = window.navigator.userAgent;
			if (redirect == "REDIRECT") {
				if(UA.indexOf("SiecomWebview") > -1){
 					sessionPast();
				}else{
					if(dpLogin == "DPLOGIN"){
						window.location.href=allUrl+"?deleteFlag=1";
					}else{
						window.location.href=allUrl;
					}
				}
			}
	    }
	});
//app重复登录提示
$(document).ready(function (){
	if($("#duplicateLogin").val() == "duplicateLogin"){
		$("#duplicateLogin").val("");
		sessionPast();
	}
});
function session(){
	if($("#duplicateLogin").val() == "duplicateLogin"){
		$("#duplicateLogin").val("");
		sessionPast();
	}
}

	
		//显示、隐藏密码
	function pwdShow(obj){
		var basePath = $("#basePath").val();
		//$("#flag").val(2);
		if($(obj).attr('data-password')==0){
			$(obj).attr('src',  basePath+'/common/images/new/password-on.png');
			$(obj).prev('input').attr('type','text');
			$(obj).attr('data-password','1');
		}else{
			$(obj).attr('src',basePath+'/common/images/new/password-off.png');
			$(obj).prev('input').attr('type','password');
			$(obj).attr('data-password','0');
		}
	}

/*获取光标位置*/  
function getCursorPos(obj)  
{   
    var CaretPos = 0;   
    // IE Support   
    if (document.selection) {   
        obj.focus (); //获取光标位置函数   
        var Sel = document.selection.createRange ();   
        Sel.moveStart ('character', -obj.value.length);  
        CaretPos = Sel.text.length;   
    }   
    // Firefox/Safari/Chrome/Opera support   
    else if (obj.selectionStart || obj.selectionStart == '0')   
        CaretPos = obj.selectionEnd;   
    return (CaretPos);   
}   
/*  
定位光标  
*/   
function setCursorPos(obj,pos)   
{   
    if(obj.setSelectionRange) { //Firefox/Safari/Chrome/Opera  
        obj.focus(); //  
        obj.setSelectionRange(pos,pos);   
    } else if (obj.createTextRange) { // IE  
        var range = obj.createTextRange();   
        range.collapse(true);   
        range.moveEnd('character', pos);   
        range.moveStart('character', pos);   
        range.select();   
    }   
}  

/*  
替换后定位光标在原处,可以这样调用onkeyup=replaceAndSetPos(this,/[^/d]/g,'');  
*/   
function replaceAndSetPos(obj,pattern,text){   
  /*if(event.shiftKey||event.altKey||event.ctrlKey||event.keyCode==16||event.keyCode==17||event.keyCode==18||(event.shiftKey&&event.keyCode==36))  
   return; */  
      
    var pos=getCursorPos(obj);//保存原始光标位置   
  
  
    var temp=obj.value; //保存原始值   
  
  
    obj.value=temp.replace(pattern,text);//替换掉非法值   
      
    //截掉超过长度限制的字串（此方法要求已设定元素的maxlength属性值）  
    var max_length = obj.getAttribute? parseInt(obj.getAttribute("maxlength")) : "";  
    if( obj.value.length > max_length){  
        //法一：obj.value = obj.value.substring( 0,max_length);若用户在中间进行输入，此方法则达不到效果  
          
        //法二：可以满足任何情况（当超过输入了，去掉新输入的字符）  
        var str1 = obj.value.substring( 0,pos-1 );  
        var str2 = obj.value.substring( pos,max_length+1 );  
        obj.value = str1 + str2;  
          
        /*法三：在支持keycode等一系列的情况下使用 
        var e=e||event;  
        currKey=e.keyCode||e.which||e.charCode; 
        currKey = 0; 
        或 
        window.onkeydown=function(){  
            if( event.keyCode!=37 && event.keyCode!=39 && event.keyCode!=8 )// 左、右、删除 
                { return false; } 
        }*/  
    }  
      
    pos=pos-(temp.length-obj.value.length);//当前光标位置   
  
  
    setCursorPos(obj,pos);//设置光标   
}    