package com.yunpay.wx.req.pay;

import com.yunpay.common.utils.CommonUtil;

/**
 * 微信app支付
 * 文件名称:     WechatAppReq.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年10月31日下午3:07:02 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年10月31日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class WechatAppReq extends WechatQrReq{
	//订单描述(必须)
    private String body = "";
    //商品详情
    private String detail = "";
    //附加信息
    private String attach = "";
    //交易金额(必须)
    private int total_fee = 0;
    //提交订单ip(必须)
    private String spbill_create_ip = "";
    //订单优惠标记
    private String goods_tag = "";
    //指定支付方式
   	private String limit_pay = "";
	//交易起始时间
	private String time_start = "";
	//交易结束时间
	private String time_expire = "";
	//交易结果通知地址(必须)
	private String notify_url = "";
	//交易类型(必须)(JSAPI，NATIVE，APP)
	private String trade_type = "APP";
	
	public WechatAppReq(String appId,String merchId,String subMerchId,String subAppId,
			String outTradeNo,int totalFee, String body,String spBillCreateIP,
			String goodsTag,String attach,String notifyUrl) {
		//微信分配的公众号ID（开通公众号之后可以获取到）
		setAppid(appId);
		//微信支付分配的商户号ID（开通公众号的微信支付功能之后可以获取到）
		setMch_id(merchId);
		//微信支付分配的商户号ID（开通公众号的微信支付功能之后可以获取到）
		setSub_mch_id(subMerchId);
		//appid
		setSub_appid(subAppId);
		//随机字符串，不长于32 位
		setNonce_str(CommonUtil.getRandomStringByLength(32));
		//要支付的商品的描述信息，用户会在支付成功页面里看到这个信息
		setBody(body); 
		//商户系统内部的订单号,32个字符内可包含字母, 确保在商户系统唯一
		setOut_trade_no(outTradeNo); // 商户系统内部的订单号,32个字符内可包含字母, 确保在商户系统唯一
		//订单总金额，单位为“分”，只能整数
		setTotal_fee(totalFee);	
		// 订单生成的机器IP
		setSpbill_create_ip(spBillCreateIP);
		//接收微信支付异步通知回调地址
		setNotify_url(notifyUrl);
		//取值如下：JSAPI，NATIVE，APP，WAP,详细说明见参数规定
		setTrade_type("APP");
		//支付订单里面可以填的附加数据，API会将提交的这个附加数据原样返回，有助于商户自己可以注明该笔消费的具体内容，方便后续的运营和记录
		setAttach(attach);
		// 商品标记，微信平台配置的商品标记，用于优惠券或者满减使用
		setGoods_tag(goodsTag);
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public int getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(int total_fee) {
		this.total_fee = total_fee;
	}

	public String getSpbill_create_ip() {
		return spbill_create_ip;
	}

	public void setSpbill_create_ip(String spbill_create_ip) {
		this.spbill_create_ip = spbill_create_ip;
	}

	public String getGoods_tag() {
		return goods_tag;
	}

	public void setGoods_tag(String goods_tag) {
		this.goods_tag = goods_tag;
	}

	public String getLimit_pay() {
		return limit_pay;
	}

	public void setLimit_pay(String limit_pay) {
		this.limit_pay = limit_pay;
	}

	public String getTime_start() {
		return time_start;
	}

	public void setTime_start(String time_start) {
		this.time_start = time_start;
	}

	public String getTime_expire() {
		return time_expire;
	}

	public void setTime_expire(String time_expire) {
		this.time_expire = time_expire;
	}

	public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}

	public String getTrade_type() {
		return trade_type;
	}

	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}
	
	
}
