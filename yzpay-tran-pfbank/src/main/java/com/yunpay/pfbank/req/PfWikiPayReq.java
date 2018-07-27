package com.yunpay.pfbank.req;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 浦发公众号支付
 * 文件名称:     PfWikiPayReq.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年11月21日上午9:37:23 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年11月21日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class PfWikiPayReq extends PfBaseReq{
	private String subOpenId;

	/**
	 * 构造公众号支付请求参数
	 * @param agentId
	 * @param clientIp
	 * @param merNo
	 * @param subMchId
	 * @param orderNo
	 * @param notifyUrl
	 * @param transAmt
	 * @param commodityName
	 * @param autoCode
	 * @param subOpenId
	 */
	public PfWikiPayReq(String agentId,String clientIp,String merNo,String subMchId,
			String orderNo,String notifyUrl,String transAmt,
			String commodityName,String subOpenId){
		super.setAgentId(agentId);
		super.setClientIp(clientIp);
		super.setMerNo(merNo);
		super.setSubMchId(subMchId);
		super.setOrderDate(new SimpleDateFormat("yyyyMMdd").format(new Date()));
		super.setOrderNo(orderNo);
		super.setNotifyUrl(notifyUrl);
		super.setReturnUrl(notifyUrl);
		super.setTransAmt(transAmt);
		super.setCommodityName(commodityName);
		super.setProductId("0112");
		super.setTransId("16");
		this.setSubOpenId(subOpenId);
		
	}
	
	public String getSubOpenId() {
		return subOpenId;
	}

	public void setSubOpenId(String subOpenId) {
		this.subOpenId = subOpenId;
	}
	
	
}
