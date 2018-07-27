package com.yunpay.serv.validate.exception;

/**
 * 自定义验证异常
 * 文件名称:     ValidateException.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年9月1日下午2:17:37 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年9月1日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class ValidateException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	private String errCode;
	private String errMsg;

	public ValidateException(){
		super();
	}
	
	public ValidateException(String errMsg){
		super(errMsg);
	}
	
	public ValidateException(String errCode,String errMsg){
		this.errCode = errCode;
		this.errMsg = errMsg;
	}
	public ValidateException(String errMsg,Throwable cause){
		super(errMsg,cause);
	}
	
	public ValidateException(Throwable cause){
		super(cause);
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}
}
