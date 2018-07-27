package com.yunpay.pfbank.req;

/**
 * 公众号配置请求参数
 * 文件名称:     PfWikiConfigReq.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年10月30日上午9:11:46 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年10月30日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class PfWikiConfigReq extends PfBaseReq{
	
	private String payWay="WX";
	private String jsApiPath;
	private String subAppId;
	private String subScribeAppid;
	private int configType=1;
	
	/**
	 * 设置公众号微信配置请求参数
	 * @param agentId
	 * @param merNo
	 * @param subMchId
	 * @param configType
	 * @param wikiConfig
	 */
	public PfWikiConfigReq(String agentId,String merNo,String subMchId,
			int configType,String wikiConfig){
		setAgentId(agentId);
		setMerNo(merNo);
		setSubMchId(subMchId);
		setConfigType(configType);
		if(configType==1){
			setJsApiPath(wikiConfig);
		}else if(configType==2){
			setSubAppId(wikiConfig);
		}else{
			setSubScribeAppid(wikiConfig);
		}
	}
	
	/**
	 * 构造微信配置查询请求参数
	 * @param agentId
	 * @param merNo
	 * @param subMchId
	 */
	public PfWikiConfigReq(String agentId,String merNo,String subMchId){
		setAgentId(agentId);
		setMerNo(merNo);
		setSubMchId(subMchId);
	}
	
	public String getPayWay() {
		return payWay;
	}
	public void setPayWay(String payWay) {
		this.payWay = payWay;
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

	public int getConfigType() {
		return configType;
	}

	public void setConfigType(int configType) {
		this.configType = configType;
	}
	
	
}
