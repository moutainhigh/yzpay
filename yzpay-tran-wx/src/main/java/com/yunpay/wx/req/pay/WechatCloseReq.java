package com.yunpay.wx.req.pay;

import com.yunpay.common.utils.CommonUtil;

/**
 * 微信关闭订单接口请求参数类
 * 文件名称:     WechatCloseReq.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年7月7日下午1:46:26 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年7月7日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class WechatCloseReq extends WechatQrReq{
	
	public WechatCloseReq(){
		
	}
	
	/**
	 * 构造微信关闭订单请求参数
	 * @param appId
	 * @param merchId
	 * @param subMerchId
	 * @param outTradeNo
	 * @param nonceStr
	 */
	public WechatCloseReq(String appId,String merchId,String subMerchId,
			String outTradeNo){
		setAppid(appId);
		setMch_id(merchId);
		setSub_mch_id(subMerchId);
		setOut_trade_no(outTradeNo);
		setNonce_str(CommonUtil.getRandomStringByLength(32));
	}
}
