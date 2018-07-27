package com.yunpay.pfbank.req;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.yunpay.common.utils.EnumUtil.SubChannel;

/**
 * 浦发银行通道条码支付请求参数(微信&支付宝)
 * 文件名称:     PfBarReq.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年10月9日下午3:10:57 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年10月9日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class PfPayReq extends PfBaseReq{
	
	private String autoCode;
	
	/**
	 * 构造支付请求参数
	 * @param subChannel
	 * @param clientIp
	 * @param merNo
	 * @param subMchId
	 * @param orderNo
	 * @param notifyUrl
	 * @param transAmt
	 * @param commodityName
	 */
	public PfPayReq(SubChannel subChannel,String agentId,String clientIp,String merNo,String subMchId,
			String orderNo,String notifyUrl,String transAmt,String commodityName,String autoCode){
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
		if(subChannel==SubChannel.WECHAT_BAR){
			//微信条码支付
			super.setProductId("0113");
			super.setTransId("17");
			this.setAutoCode(autoCode);
		}else if(subChannel==SubChannel.ALIPAY_BAR){
			//支付宝条码支付
			super.setProductId("0120");
			super.setTransId("17");
			this.setAutoCode(autoCode);
		}else if(subChannel == SubChannel.WECHAT_SCAN){
			//微信扫码支付
			super.setProductId("0108");
			super.setTransId("10");
		}else if(subChannel == SubChannel.ALIPAY_SCAN){
			//支付宝扫码支付
			super.setProductId("0119");
			super.setTransId("10");
		}
	}
	

	public String getAutoCode() {
		return autoCode;
	}

	public void setAutoCode(String autoCode) {
		this.autoCode = autoCode;
	}
	
	
}
