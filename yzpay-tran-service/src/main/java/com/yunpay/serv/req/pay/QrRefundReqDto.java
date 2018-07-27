package com.yunpay.serv.req.pay;

import com.yunpay.serv.validate.annotation.DataValidate;
import com.yunpay.serv.validate.enums.RegexType;
/**
 * 退款申请请求参数
 * 文件名称:     QrRefundReqDto.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2018年1月5日上午11:05:42 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2018年1月5日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2018
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class QrRefundReqDto {
	
	//商户号（非空）
	@DataValidate(allowNull=false,regexType=RegexType.NUMBER,maxLength=20)
	private String merchant_num ; 
	//原支付流水号（非空）
	@DataValidate(maxLength=30,regexType=RegexType.NOT_CHINESE)
	private String user_order_no; 	
	//平台流水号
	@DataValidate(maxLength=30,regexType=RegexType.NOT_CHINESE)
	private String trace_num; 		
	//退款申请流水号（非空）
	@DataValidate(allowNull=false,maxLength=30,regexType=RegexType.NOT_CHINESE)
	private String user_refund_no;
	//退款金额(单位：元)
	@DataValidate(allowNull=false,maxLength=10,regexType=RegexType.DECIMAL)
	private String refund_fee ;
	//退款原因
	@DataValidate(maxLength=100)
	private String refund_desc;
	@DataValidate(allowNull=false,maxLength=10)
	private String sign_type;			//签名类型（非空）
	@DataValidate(allowNull=false,maxLength=50)
	private String sign;				//签名（非空）
	
	
	public String getMerchant_num() {
		return merchant_num;
	}
	public void setMerchant_num(String merchant_num) {
		this.merchant_num = merchant_num;
	}
	public String getUser_order_no() {
		return user_order_no;
	}
	public void setUser_order_no(String user_order_no) {
		this.user_order_no = user_order_no;
	}
	public String getTrace_num() {
		return trace_num;
	}
	public void setTrace_num(String trace_num) {
		this.trace_num = trace_num;
	}
	public String getUser_refund_no() {
		return user_refund_no;
	}
	public void setUser_refund_no(String user_refund_no) {
		this.user_refund_no = user_refund_no;
	}
	public String getRefund_fee() {
		return refund_fee;
	}
	public void setRefund_fee(String refund_fee) {
		this.refund_fee = refund_fee;
	}
	public String getRefund_desc() {
		return refund_desc;
	}
	public void setRefund_desc(String refund_desc) {
		this.refund_desc = refund_desc;
	}
	public String getSign_type() {
		return sign_type;
	}
	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	
	
	
	
}
