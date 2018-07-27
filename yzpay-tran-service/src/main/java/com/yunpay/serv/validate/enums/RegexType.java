package com.yunpay.serv.validate.enums;


/**
 * 自定义验证支持的类型
 * 文件名称:     RegexType.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年9月1日下午2:11:46 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年9月1日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public enum RegexType {
	NONE,
	NOT_SPECIALCHAR,
	NOT_CHINESE,
	EMAIL,
	IP,
	NUMBER,
	DECIMAL,
	MOBILE,
	DATE,
	DATETIME;
}
