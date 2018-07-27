<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>bootstrap table</title>
<!-- bootstrap -->
<link rel="stylesheet" href="static/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="static/bootstrap/css/bootstrapValidator.min.css">
<link rel="stylesheet" type="text/css" href="static/bootstrap-table/bootstrap-table.css"/> 

<script src="static/bootstrap/js/jquery-1.11.2.min.js"></script>
<script src="static/bootstrap/js/bootstrap.min.js"></script>
<script src="static/bootstrap-table/bootstrap-table.js"></script> 
<!-- bootstrap table中文包 -->
<script src="static/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>

<script type="text/javascript"> 
var currPageIndex = 0;  
var currLimit = 10;  

$(function () {  
    $("#dataShow").bootstrapTable({  
        url: "card/querylist",  
        sortName: "rkey",//排序列  
        striped: true,//條紋行  
        sidePagination: "server",//服务器分页  
        showRefresh: true,//刷新功能  
        //search: true,//搜索功能  
        clickToSelect: true,//选择行即选择checkbox  
        singleSelect: true,//仅允许单选  
        //searchOnEnterKey: true,//ENTER键搜索  
        pagination: true,//启用分页  
        escape: true,//过滤危险字符  
        queryParams: getParams,//携带参数  
        pageCount: 10,//每页行数  
        pageIndex: 0,//其实页  
        method: "post",//请求格式  
        toolbar: "#toolBar",  
        onPageChange: function (number, size) {  
            currPageIndex = number;  
            currLimit = size;  
        },  
        onLoadSuccess: function ()  
        {  
            $("#searchBtn").button('reset');  
        }  
    });  

    //搜索  
    $("#searchBtn").click(function () {  
        $(this).button('loading');  
        var nullparamss = {};  
        $("#dataShow").bootstrapTable("refresh", nullparamss);  
          
    });  
    //enter键搜索  
    $("#searchKey").keydown(function (event) {  
        if (event.keyCode == 13)  
        {  
            $("#searchBtn").click();  
        }  
    });  
    //阻止enter键提交表单  
    $("#mainForm").submit(function () {  
        return false;  
    }); 
});  
//默认加载时携带参数  
function getParams(params) {  
    var searchKey = $("#searchKey").val();  
    return { bysex: 1, limit: params.limit, offset: params.offset, search: searchKey };  
}  
</script> 

</head>
<body>
	<div class="panel-body" style="padding-bottom:0px;"> 
	   <table class="table" id="dataShow" >  
	         <thead>  
	             <tr>  
	                 <th data-checkbox="true">选择</th>  
	                 <th data-field="weixin_card_id">卡券id</th>  
	                 <th data-field="title">卡券标题</th>  
	                 <th data-field="cardColor">卡券颜色</th>  
	                 <th data-field="inventory">卡券库存</th>  
	                 <th data-field="startDate">有效期(起)</th>  
	                 <th data-field="endDate">有效期(止)</th>  
	                 <th data-field="tel">客服电话</th>
	             </tr>  
	         </thead>  
	    </table>  
	 </div> 
</body>
</html>