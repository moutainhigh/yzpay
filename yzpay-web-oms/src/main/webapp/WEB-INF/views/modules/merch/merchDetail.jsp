<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../../../common/taglib/taglib.jsp"%>
<div class="pageContent">
	<ul class="nav nav-tabs" id="payConfig_tab">
		<li class="active"><a href="#baseInfo" data-toggle="tab" style="font-size: 15px;">基本信息</a></li>
		<li><a href="#accountInfo" data-toggle="tab"  style="font-size: 15px;">银行账户信息</a></li>
		<li><a href="#attachInfo" data-toggle="tab" style="font-size: 15px;">证件信息</a></li>
	</ul>
	<div id="approvePage_tabContent" class="tab-content" style="width: 585px; height: 540px; margin-top: -11px;">
		<!-- 基本信息 -->
			<div class="tab-pane fade in active" id="baseInfo" cellpadding="20">  
				<div >
					<table class="table" >
						<tr>
							<td style="width: 30%;height: 40px;font-size: 15px;">营业执照名称:</td>
							<td style="height: 40px;font-size: 15px;">${merch.registerName }</td>
						</tr>
						<tr>
							<td style="width: 30%;height: 40px;font-size: 15px;">品牌名称:</td>
							<td style="height: 40px;font-size: 15px;">${merch.merchantName }</td>
						</tr>
						<tr>
							<td style="width: 30%;height: 40px;font-size: 15px;">省市区:</td>
							<td style="height: 40px;font-size: 15px;">${merch.prov }</td>
						</tr>
						<tr>
							<td style="width: 30%;height: 40px;font-size: 15px;">联系地址:</td>
							<td style="height: 40px;font-size: 15px;">${merch.address }</td>
						</tr>
						<tr>
							<td style="width: 30%;height: 40px;font-size: 15px;">法人姓名:</td>
							<td style="height: 40px;font-size: 15px;">${merch.cardName }</td>
						</tr>
						<tr>
							<td style="width: 30%;height: 40px;font-size: 15px;">法人身份证:</td>
							<td style="height: 40px;font-size: 15px;">${merch.cardNo }</td>
						</tr>
						<tr>
							<td style="width: 30%;height: 40px;font-size: 15px;">所属行业:</td>
							<td style="height: 40px;font-size: 15px;">${merch.industry }</td>
						</tr>
						<tr>
							<td style="width: 30%;height: 40px;font-size: 15px;">座机号码:</td>
							<td style="height: 40px;font-size: 15px;">${merch.tel }</td>
						</tr>
						<tr>
							<td style="width: 30%;height: 40px;font-size: 15px;">联系邮箱:</td>
							<td style="height: 40px;font-size: 15px;">${merch.email }</td>
						</tr>
					</table>
				</div>
			</div>
		<!-- 银行账户信息 -->
			<div class="tab-pane fade" id="accountInfo" >
				<div >
					<table class="table" >
						<tr>
							<td style="width: 30%;height: 40px;font-size: 15px;">开户银行</td>
							<td style="height: 40px;font-size: 15px;">${merch.accBank }</td>
						</tr>
   						<tr>
   							<td style="width: 30%;height: 40px;font-size: 15px;">开户城市</td>
   							<td style="height: 40px;font-size: 15px;">${merch.accProv }</td>
   						</tr>
   						<tr>
   							<td style="width: 30%;height: 40px;font-size: 15px;">支行名称</td>
   							<td style="height: 40px;font-size: 15px;">${merch.accSubBank }</td>
   						</tr>
   						<tr>
   							<td style="width: 30%;height: 40px;font-size: 15px;">开户名称</td>
   							<td style="height: 40px;font-size: 15px;">${merch.accName }</td>
   						</tr>
   						<tr>
   							<td style="width: 30%;height: 40px;font-size: 15px;">银行账号</td>
   							<td style="height: 40px;font-size: 15px;">${merch.accNo }</td>
   						</tr>
					</table>
				</div>
			</div>
			<!-- 证件信息 -->
			<div class="tab-pane fade" id="attachInfo" >
				<div>
					<table class="table" >
						<tr>
						<td>
							<img class="img" src="http://siecompay.com/merch/cashierContr/showAttach?id=${merch.attach1}" style="width: 200px;height: 150px;margin-left: 30px;margin-top: 10px;margin-bottom: 10px;" >
							<c:if test="${merch.attach1 != 0}">
								<br>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" onclick="downloadAttach('${merch.attach1}',1)">下载</a>
							</c:if>
						</td>
						<td>
							<img class="img" src=" http://siecompay.com/merch/cashierContr/showAttach?id=${merch.attach2}" style="width: 200px;height: 150px;margin-top: 10px;margin-left: 10px;margin-bottom: 10px;">
							<c:if test="${merch.attach2 != 0}">
								<br>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" onclick="downloadAttach(${merch.attach2}',2)">下载</a>
							</c:if>
							</td>
						</tr>
						
						<tr>
						<td><img class="img" src=" http://siecompay.com/merch/cashierContr/showAttach?id=${merch.attach3}"style="width: 200px;height: 150px;margin-left: 30px;margin-top: 10px;margin-bottom: 10px;">
							<c:if test="${merch.attach3 != 0}">
								<br>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" onclick="downloadAttach('${merch.attach3}',3)">下载</a>
							</c:if>
						</td>
						<td><img class="img" src=" http://siecompay.com/merch/cashierContr/showAttach?id=${merch.attach4}"style="width: 200px;height: 150px;margin-left: 10px;margin-top: 10px;margin-bottom: 10px;">
							<c:if test="${merch.attach4 != 0}">
								<br>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" onclick="downloadAttach('${merch.attach4}',4)">下载</a>
							</c:if>
						</td>
						</tr>

   						<tr><td colspan="2"><img class="img" src=" http://siecompay.com/merch/cashierContr/showAttach?id=${merch.attach5}"style="width: 200px;height: 150px;margin-left: 30px;margin-top: 10px;">
   							<c:if test="${merch.attach5 != 0}">
   								<br>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" onclick="downloadAttach('${merch.attach5}',5)">下载</a>
							</c:if>
   						</td></tr>
					</table>
					
					
				</div>
			</div>
			<!-- 证件下载时需要的form,下载文件必须提交http post请求 -->
			<form action="" method="post" id="downloadForm" style="display: none;"></form>
			<script>
			function downloadAttach(id,type){
				var downloadCtrl = "http://siecompay.com/merch/cashierContr/downloadAttach?id="+id+"&type="+type; 
				$("#downloadForm").attr("action",downloadCtrl);
				setTimeout($("#downloadForm").submit(),1000);
			}
			</script>
	</div>
	<div style="text-align:right; margin-top: 20px;">
		<button type="button" class="btn btn-green" href="javascript:void(0)" onclick="closeWin()"  data-id="dialog-normal" style="margin-right: 40px;">退出</button>
	</div>
	
</div>