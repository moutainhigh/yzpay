package com.yunpay.ali.rep.pay;

/**
 * 文件名称:     AliRefundQueryRep.java
 * 内容摘要:    支付宝退款查询接口返回参数封装类
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2018年3月1日下午2:27:42 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2018年3月1日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2018
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class AliRefundQueryRep extends AliQrRep{
	private String trade_no;
	private String out_trade_no ;
	private String out_request_no;
	private String refund_reason;
	private String total_amount;
	private String refund_amount;
	
	public AliRefundQueryRep(){
		
	}
	
	public AliRefundQueryRep(String code,String msg,String sub_code,String sub_msg){
		super(code,msg,sub_code,sub_msg);
	}

	
	public String getTrade_no() {
		return trade_no;
	}
	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public String getOut_request_no() {
		return out_request_no;
	}
	public void setOut_request_no(String out_request_no) {
		this.out_request_no = out_request_no;
	}
	public String getRefund_reason() {
		return refund_reason;
	}
	public void setRefund_reason(String refund_reason) {
		this.refund_reason = refund_reason;
	}
	public String getTotal_amount() {
		return total_amount;
	}
	public void setTotal_amount(String total_amount) {
		this.total_amount = total_amount;
	}
	public String getRefund_amount() {
		return refund_amount;
	}
	public void setRefund_amount(String refund_amount) {
		this.refund_amount = refund_amount;
	}
	
	
}
