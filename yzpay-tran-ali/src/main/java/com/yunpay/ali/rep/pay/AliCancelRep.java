package com.yunpay.ali.rep.pay;

/**
 * 文件名称:     AliCancelRep.java
 * 内容摘要:    支付宝订单撤销接口返回参数封装类
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2018年3月1日下午2:13:37 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2018年3月1日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2018
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class AliCancelRep extends AliQrRep{
	//支付宝交易号 
	private String trade_no;
	//平台提交订单号(必须)
	private String out_trade_no;
	//是否需要重试
	private String retry_flag;
	//本次撤销触发的交易动作
	/*
	close：关闭交易，无退款
	refund：产生了退款 
	*/
	
	private String action;
	
	public AliCancelRep(){
		
	}
	
	public AliCancelRep(String code,String msg,String sub_code,String sub_msg){
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

	public String getRetry_flag() {
		return retry_flag;
	}
	public void setRetry_flag(String retry_flag) {
		this.retry_flag = retry_flag;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	
	
}
