package com.yunpay.permission.utils;
/**
 * 
 * 类名称		        发送短信验证码返回信息接收实体类（暂不用）
 * 文件名称:     ResultObj.java
 * 内容摘要: 	       用于接收短信平台返回的信息
 * @author:   Zhao Xiaoman
 * @version:  1.0  
 * @Date:     2017年12月4日上午9:36:52
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年12月4日   Zhao Xiaoman       1.0       新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class ResultObj {

	private String batchno;
	private String respcode;
	private String respdesc;

	public String getBatchno() {
		return batchno;
	}

	public void setBatchno(String batchno) {
		this.batchno = batchno;
	}

	public String getRespcode() {
		return respcode;
	}

	public void setRespcode(String respcode) {
		this.respcode = respcode;
	}

	public String getRespdesc() {
		return respdesc;
	}

	public void setRespdesc(String respdesc) {
		this.respdesc = respdesc;
	}

}