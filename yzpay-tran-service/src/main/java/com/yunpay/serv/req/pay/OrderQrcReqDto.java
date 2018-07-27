package com.yunpay.serv.req.pay;

import com.yunpay.serv.validate.annotation.DataValidate;
import com.yunpay.serv.validate.enums.RegexType;
/**
 * 订单查询、撤销、关闭请求参数
 * 文件名称:     OrderQrcReqDto.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2018年1月5日上午10:50:47 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2018年1月5日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2018
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class OrderQrcReqDto {
	@DataValidate(allowNull=false,maxLength=20,regexType=RegexType.NOT_CHINESE)
	private String merchant_num ; 		//商户号
	@DataValidate(maxLength=20,regexType=RegexType.NOT_CHINESE)
	private String terminal_unique_no ; // 终端号
	@DataValidate(maxLength=30,regexType=RegexType.NOT_CHINESE)
	private String user_order_no; 		//商户支付单号
	@DataValidate(maxLength=30,regexType=RegexType.NOT_CHINESE)
	private String trace_num; 		//平台流水号
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
	public String getTerminal_unique_no() {
		return terminal_unique_no;
	}
	public void setTerminal_unique_no(String terminal_unique_no) {
		this.terminal_unique_no = terminal_unique_no;
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
