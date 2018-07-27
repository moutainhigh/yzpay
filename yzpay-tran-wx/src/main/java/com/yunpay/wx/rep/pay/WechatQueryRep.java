package com.yunpay.wx.rep.pay;

/**
 * 文件名称:    WechatQueryRep.java
 * 内容摘要:    微信订单查询返回结果封装类
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年6月23日下午2:17:23 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年6月23日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class WechatQueryRep extends WechatQrRep{
	//用户标识(必须)
	private String openid;
	//是否关注公众账号(必须)
	private String is_subscribe;
	//用户子标识
	private String sub_openid;
	//是否关注子公众账号
	private String sub_is_subscribe;
	//交易类型MICROPAY(必须)
	private String trade_type;
	//交易状态
	/**
	   trade_state状态说明：
	   SUCCESS—支付成功
	   REFUND—转入退款
	   NOTPAY—未支付
	   CLOSED—已关闭
	   REVOKED—已撤销(刷卡支付)
	   USERPAYING--用户支付中
	   PAYERROR--支付失败(其他原因，如银行返回失败)
	*/
	private String trade_state;
	//订单状态说明
	private String trade_state_desc;
	//付款银行(必须)
	private String bank_type;
	//标价币种(默认：人民币CNY)
	private String fee_type;
	//订单总金额(必须)
	private String total_fee;
	//现金支付金额(必须)
	private String cash_fee;
	//应结订单金额
	private String settlement_total_fee;
	//代金券金额
	private String coupon_fee;
	//微信支付订单号(微信平台产生)
	private String transaction_id;
	//微信支付提交订单号(调用方提交)
	private String out_trade_no;
	//附加信息
	private String attach;
	//支付完成时间
	private String time_end;
	
	public WechatQueryRep(){
		
	}
	
	/**
	 * 用于构造系统中返回的错误信息
	 * @param return_code
	 * @param return_msg
	 * @param err_code
	 * @param err_code_des
	 */
	public WechatQueryRep(String return_code,String return_msg,String err_code,String err_code_des){
		super( return_code, return_msg, err_code, err_code_des);
	}
	
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getIs_subscribe() {
		return is_subscribe;
	}
	public void setIs_subscribe(String is_subscribe) {
		this.is_subscribe = is_subscribe;
	}
	public String getSub_openid() {
		return sub_openid;
	}
	public void setSub_openid(String sub_openid) {
		this.sub_openid = sub_openid;
	}
	public String getSub_is_subscribe() {
		return sub_is_subscribe;
	}
	public void setSub_is_subscribe(String sub_is_subscribe) {
		this.sub_is_subscribe = sub_is_subscribe;
	}
	public String getTrade_type() {
		return trade_type;
	}
	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}
	public String getTrade_state() {
		return trade_state;
	}
	public void setTrade_state(String trade_state) {
		this.trade_state = trade_state;
	}
	public String getTrade_state_desc() {
		return trade_state_desc;
	}
	public void setTrade_state_desc(String trade_state_desc) {
		this.trade_state_desc = trade_state_desc;
	}
	public String getBank_type() {
		return bank_type;
	}
	public void setBank_type(String bank_type) {
		this.bank_type = bank_type;
	}
	public String getFee_type() {
		return fee_type;
	}
	public void setFee_type(String fee_type) {
		this.fee_type = fee_type;
	}
	public String getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}
	public String getCash_fee() {
		return cash_fee;
	}
	public void setCash_fee(String cash_fee) {
		this.cash_fee = cash_fee;
	}
	public String getSettlement_total_fee() {
		return settlement_total_fee;
	}
	public void setSettlement_total_fee(String settlement_total_fee) {
		this.settlement_total_fee = settlement_total_fee;
	}
	public String getCoupon_fee() {
		return coupon_fee;
	}
	public void setCoupon_fee(String coupon_fee) {
		this.coupon_fee = coupon_fee;
	}
	public String getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	public String getTime_end() {
		return time_end;
	}
	public void setTime_end(String time_end) {
		this.time_end = time_end;
	}
	
	
	
}
