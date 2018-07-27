package com.yunpay.ali.req.pay;

/**
 * 文件名称:    AliCloseReq.java
 * 内容摘要:    支付宝订单关闭接口请求参数
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2018年3月1日下午4:42:28 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2018年3月1日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2018
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class AliCloseReq extends AliQrReq{
	//支付宝交易号
	private String trade_no;
	
	public AliCloseReq(){
		
	}
	
	/**
	 * 构造订单撤销请求参数
	 * @param appId
	 * @param privateKey
	 * @param alipayPublicKey
	 * @param out_trade_no
	 * @param trade_no
	 */
	public AliCloseReq(String appId,String privateKey,String alipayPublicKey,
			String out_trade_no,String trade_no){
		setAppId(appId);
		setPrivateKey(privateKey);
		setAlipayPublicKey(alipayPublicKey);
		setOut_trade_no(out_trade_no);
		setTrade_no(trade_no);
	}

	public String getTrade_no() {
		return trade_no;
	}

	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}
}
