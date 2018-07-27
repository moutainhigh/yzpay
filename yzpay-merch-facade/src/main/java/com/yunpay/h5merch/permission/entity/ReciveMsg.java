package com.yunpay.h5merch.permission.entity;

/**
 * 
 * 类名称                      调用接口接收数据实体类
 * 文件名称:     ReciveMsg.java
 * 内容摘要: 
 * @author:   Zhao Xiaoman
 * @version:  1.0  
 * @Date:     2017年10月11日上午10:28:13
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年10月11日   Zhao Xiaoman       1.0       新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */

public class ReciveMsg<E>{
	public static final String SUCCESS_CODE = "SUCCESS";
	public static final String SUCCESS_MSG = "成功";
	public static final String ERROR_CODE = "FAIL";
	public static final String ERROR_MSG = "失败";
	private String result_code;
	private String result_msg;
	private String err_code;
	private String err_code_des;
	private E data;
	
	public  ReciveMsg(){}
	//构造函数，1为成功，0为失败，其他为默认构造
	public ReciveMsg(Integer flag)
	{
		if (flag == 1)
		{
			this.result_code = ReciveMsg.SUCCESS_CODE;
			this.result_msg = ReciveMsg.SUCCESS_MSG;
		} else if(flag == 0)
		{
			this.result_code = ReciveMsg.ERROR_CODE;
			this.result_msg = ReciveMsg.ERROR_MSG;
		}else{
		}
	}
	public ReciveMsg(Integer flag, String err_code, String err_code_des,
			E data)
	{
		if (flag == 1)
		{
			this.result_code = ReciveMsg.SUCCESS_CODE;
			this.result_msg = ReciveMsg.SUCCESS_MSG;
		} else if(flag == 0)
		{
			this.result_code = ReciveMsg.ERROR_CODE;
			this.result_msg = ReciveMsg.ERROR_MSG;
		}else{
		}
		this.err_code = err_code;
		this.err_code_des = err_code_des;
		this.data = data;
	}
	public String getResult_code()
	{
		return result_code;
	}
	public void setResult_code(String result_code)
	{
		this.result_code = result_code;
	}
	public String getResult_msg()
	{
		return result_msg;
	}
	public void setResult_msg(String result_msg)
	{
		this.result_msg = result_msg;
	}
	public String getErr_code()
	{
		return err_code;
	}
	public void setErr_code(String err_code)
	{
		this.err_code = err_code;
	}
	public String getErr_code_des()
	{
		return err_code_des;
	}
	public void setErr_code_des(String err_code_des)
	{
		this.err_code_des = err_code_des;
	}
	public E getData()
	{
		return data;
	}
	public void setData(E data)
	{
		this.data = data;
	}
	public static String getSuccessCode()
	{
		return SUCCESS_CODE;
	}
	public static String getSuccessMsg()
	{
		return SUCCESS_MSG;
	}
	public static String getErrorCode()
	{
		return ERROR_CODE;
	}
	public static String getErrorMsg()
	{
		return ERROR_MSG;
	}
}
