package com.yunpay.pfbank.rep;

/**
 * 浦发渠道微信公众号配置返回参数
 * 文件名称:     PfWikiConfigRep.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年10月30日上午9:51:38 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年10月30日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class PfWikiConfigRep extends PfBaseRep{
	private String jsApiPath;
	private String subAppId;
	private String subScribeAppid;
	
	public PfWikiConfigRep(){
		
	}
	
	public PfWikiConfigRep(String respCode,String repsDesc){
		super.setRespCode(respCode);
		super.setRespDesc(repsDesc);
	}
	
	public String getJsApiPath() {
		return jsApiPath;
	}
	public void setJsApiPath(String jsApiPath) {
		this.jsApiPath = jsApiPath;
	}
	public String getSubAppId() {
		return subAppId;
	}
	public void setSubAppId(String subAppId) {
		this.subAppId = subAppId;
	}
	public String getSubScribeAppid() {
		return subScribeAppid;
	}
	public void setSubScribeAppid(String subScribeAppid) {
		this.subScribeAppid = subScribeAppid;
	}
	
	
}
