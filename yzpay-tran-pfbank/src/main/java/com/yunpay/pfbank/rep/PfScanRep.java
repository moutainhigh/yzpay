package com.yunpay.pfbank.rep;

/**
 * 扫码支付返回参数
 * 文件名称:     PfScanRep.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年10月9日下午3:18:46 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年10月9日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class PfScanRep extends PfBaseRep{
	private String codeUrl;
	private String imgUrl;
	
	public PfScanRep(){}
	
	public PfScanRep(String respCode,String repsDesc){
		super.setRespCode(respCode);
		super.setRespDesc(repsDesc);
	}
	
	public String getCodeUrl() {
		return codeUrl;
	}
	public void setCodeUrl(String codeUrl) {
		this.codeUrl = codeUrl;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	
}
