package com.yunpay.pfbank.req;


/**
 * 订单状态查询接口请求参数
 * 文件名称:     PfQueryReq.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年11月21日下午4:01:46 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年11月21日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class PfQueryReq extends PfBaseReq{
	
	/**
	 * 构造订单查询请求参数
	 * @param agentId  
	 * @param merNo
	 * @param orderDate 订单日期
	 * @param orderNo  订单号
	 */
	public PfQueryReq(String agentId,String merNo,String orderDate,String orderNo){
		super.setAgentId(agentId);
		super.setMerNo(merNo);
		super.setOrderDate(orderDate);
		super.setOrderNo(orderNo);
	}
}
