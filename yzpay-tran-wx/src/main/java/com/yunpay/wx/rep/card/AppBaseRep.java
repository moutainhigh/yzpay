package com.yunpay.wx.rep.card;

import com.yunpay.common.utils.ResultEnum.ResultCode;
/**
 * 文件名称:    AppBaseRep.java
 * 内容摘要:    微信返回结果基础类封装
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2018年3月1日上午10:06:19 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2018年3月1日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2018
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class AppBaseRep {
	
	//返回状态码(必须)
	private String return_code;
	//错误代码
	private String err_code;
	//错误代码描述
	private String err_code_des;
	
	public AppBaseRep(){
		setReturn_code(ResultCode.SUCCESS.getCode());
	}
	
	public AppBaseRep(String errCode,String errCodeDes){
		setReturn_code(ResultCode.FAIL.getCode());
		setErr_code(errCode);
		setErr_code_des(errCodeDes);
	}
	
	public String getReturn_code() {
		return return_code;
	}
	public void setReturn_code(String return_code) {
		this.return_code = return_code;
	}
	public String getErr_code() {
		return err_code;
	}
	public void setErr_code(String err_code) {
		this.err_code = err_code;
	}
	public String getErr_code_des() {
		return err_code_des;
	}
	public void setErr_code_des(String err_code_des) {
		this.err_code_des = err_code_des;
	}
	
	
}
