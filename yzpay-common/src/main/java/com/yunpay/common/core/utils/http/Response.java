package com.yunpay.common.core.utils.http;
public class Response {

	//返回枚举值
	public enum Code{
		SUCCESS,NAME_ERROR,PASSWORD_ERROR,EXCEPTION,PARAM_NULL,USER_NOT_EXISTS,NOT_STORE_USER,NOT_STORE_CASHIER,NOT_PERMISSION,NONE_STORE,
		TIMEOUT
	}

	public static int POST_CASHIER = 1;
	
	public static int POST_ADMIN = 2;
	
}
