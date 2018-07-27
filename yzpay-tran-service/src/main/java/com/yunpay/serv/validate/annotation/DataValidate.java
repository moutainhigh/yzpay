package com.yunpay.serv.validate.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.yunpay.serv.validate.enums.RegexType;



/**
 * 自定义验证注解
 * 文件名称:     DataValidate.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年9月1日下午2:13:02 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年9月1日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
public @interface DataValidate {
	/**
	 * 
	 * @Title: allowNull
	 * @Description: TODO 是否允许为空 
	 * @return
	 */
	boolean allowNull() default true;
	/**
	 * 
	 * @Title: maxLength 
	 * @Description: TODO(最大长度) 
	 * @return
	 */
	int maxLength() default 0;
	/**
	 * 
	 * @Title: minLength 
	 * @Description: TODO(最小长度) 
	 * @return
	 */
	int minLength() default 0;
	/**
	 * 
	 * @Title: regexType 
	 * @Description: TODO(提供几种常用的正则验证) 
	 * @return
	 */
	RegexType regexType() default RegexType.NONE;
	/**
	 * 
	 * @Title: regexExpression 
	 * @Description: TODO(自定义正则验证) 
	 * @return
	 */
	String regexExpression() default "";
	/**
	 * 
	 * @Title: description 
	 * @Description: TODO(参数或者字段描述,这样能够显示友好的异常信息) 
	 * @return
	 */
	String description() default "";
}
