package com.yunpay.permission.utils.phonecore;
/**
 * 
 * 类名称		       指定模板单发返回结果接收实体类
 * 文件名称:     SmsSingleSenderResult.java
 * 内容摘要: 	       用于接收短信平台返回结果
 * @author:   Zhao Xiaoman
 * @version:  1.0  
 * @Date:     2017年12月4日上午10:22:21
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年12月4日   Zhao Xiaoman       1.0       新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class SmsSingleSenderResult
{
	/*
	{
	    "result": 0,
	    "errmsg": "OK", 
	    "ext": "", 
	    "sid": "xxxxxxx", 
	    "fee": 1
	}
	 */
		public int result;
		public String errMsg = "";
		public String ext = "";
		public int getResult()
		{
			return result;
		}

		public void setResult(int result)
		{
			this.result = result;
		}

		public String getErrMsg()
		{
			return errMsg;
		}

		public void setErrMsg(String errMsg)
		{
			this.errMsg = errMsg;
		}

		public String getExt()
		{
			return ext;
		}

		public void setExt(String ext)
		{
			this.ext = ext;
		}

		public String getSid()
		{
			return sid;
		}

		public void setSid(String sid)
		{
			this.sid = sid;
		}

		public int getFee()
		{
			return fee;
		}

		public void setFee(int fee)
		{
			this.fee = fee;
		}

		public String sid = "";
		public int fee;
		
		public String toString() {
			return String.format(
					"SmsSingleSenderResult\nresult %d\nerrMsg %s\next %s\nsid %s\nfee %d",
					result, errMsg, ext, sid, fee);		
		}
}
