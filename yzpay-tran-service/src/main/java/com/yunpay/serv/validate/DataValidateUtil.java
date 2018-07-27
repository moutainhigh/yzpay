package com.yunpay.serv.validate;

import java.lang.reflect.Field;

import org.apache.commons.lang.StringUtils;

import com.yunpay.common.utils.ResultEnum.ErrorCode;
import com.yunpay.serv.validate.annotation.DataValidate;
import com.yunpay.serv.validate.enums.RegexType;
import com.yunpay.serv.validate.exception.ValidateException;
import com.yunpay.serv.validate.utils.RegexUtils;



/**
 * 文件名称:    DataValidateService.java
 * 内容摘要: 
 * @author:   system
 * @version:  1.0  
 * @Date:     2016-3-7 上午10:46:44  
 * 
 * 修改历史:  
 * 修改日期       修改人员   版本	   修改内容  
 * ----------------------------------------------  
 * 2016-3-7    system     1.0    1.0 XXXX
 *
 * 版权:   版权所有(C)2016
 * 公司:   深圳市汇商惠电子商务有限公司
 * 
 * 注解解析
 */
public class DataValidateUtil {
	
	private static DataValidate dataValidate;
	
	public DataValidateUtil() {
		super();
	}
	
	/**
	 * @Description:类验证
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月1日下午5:02:17 
	 * @param object
	 * @return
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public static boolean valid(Object object) throws ValidateException, IllegalArgumentException, IllegalAccessException{
		//获取object的类型
		Class<? extends Object> clazz=object.getClass();
		//获取该类型声明的成员
		Field[] fields=clazz.getDeclaredFields();
		//遍历属性
		for(Field field:fields){
			//对于private私有化的成员变量，通过setAccessible来修改器访问权限
			field.setAccessible(true);
			boolean isValid = validate(field,object);
			//重新设置会私有权限
			field.setAccessible(false);
			if(!isValid){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * @Description: 属性验证
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月1日下午5:02:49 
	 * @param field
	 * @param object
	 * @return
	 * @throws ValidateException
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public static boolean validate(Field field,Object object) throws ValidateException, IllegalArgumentException, IllegalAccessException{
		//获取对象的成员的注解信息
		dataValidate=field.getAnnotation(DataValidate.class);
		Object value=field.get(object);
		if (dataValidate==null){
			//注解标记为空则无需验证，直接返回true
			return true;
		}
		//属性描述
		String fieldDesc=StringUtils.isBlank(dataValidate.description())?field.getName():dataValidate.description();
		/*************注解解析工作开始******************/
		if(value==null || StringUtils.isBlank(String.valueOf(value))){
			//如果属性值为空，判断是否添加非空注解
			if(!dataValidate.allowNull()){
				//抛出非空验证异常
				throw new ValidateException(ErrorCode.PARAM_NULL_ERROR.getCode(),
						String.format(ErrorCode.PARAM_NULL_ERROR.getDes(), fieldDesc));
			}
		}else{
			//属性有值
			if(dataValidate.maxLength()!=0 && value.toString().length()>dataValidate.maxLength()){
				throw new ValidateException(ErrorCode.PARAM_LENGTH_ERROR.getCode(),
						String.format(ErrorCode.PARAM_LENGTH_ERROR.getDes(), fieldDesc));
			}
			if(dataValidate.minLength()!=0 && value.toString().length()<dataValidate.minLength()){
				throw new ValidateException(ErrorCode.PARAM_LENGTH_ERROR.getCode(),
						String.format(ErrorCode.PARAM_LENGTH_ERROR.getDes(), fieldDesc));
			}
			//表达式验证
			if(dataValidate.regexType()!=RegexType.NONE){
				switch (dataValidate.regexType()) {
					case NOT_SPECIALCHAR:
						if(RegexUtils.hasSpecialChar(value.toString())){
							throw new ValidateException(ErrorCode.PARAM_SPECIALCHAR_ERROR.getCode(),
									String.format(ErrorCode.PARAM_SPECIALCHAR_ERROR.getDes(), fieldDesc));
						}
						break;
					case NOT_CHINESE:
						if(RegexUtils.isChinese2(value.toString())){
							throw new ValidateException(ErrorCode.PARAM_CHINESE_ERROR.getCode(),
									String.format(ErrorCode.PARAM_CHINESE_ERROR.getDes(), fieldDesc));
						}
						break;
					case EMAIL:
						if(!RegexUtils.isEmail(value.toString())){
							throw new ValidateException(ErrorCode.PARAM_EMAIL_ERROR.getCode(),
									String.format(ErrorCode.PARAM_EMAIL_ERROR.getDes(), fieldDesc));
						}
						break;
					case IP:
						if(!RegexUtils.isIp(value.toString())){
							throw new ValidateException(ErrorCode.PARAM_IP_ERROR.getCode(),
									String.format(ErrorCode.PARAM_IP_ERROR.getDes(), fieldDesc));
						}
						break;
					case NUMBER:
						if(!RegexUtils.isNumber(value.toString())){
							throw new ValidateException(ErrorCode.PARAM_NUMBER_ERROR.getCode(),
									String.format(ErrorCode.PARAM_NUMBER_ERROR.getDes(), fieldDesc));
						}
						break;
					case DECIMAL:
						if(!RegexUtils.isDecimal(value.toString(),2)){
							throw new ValidateException(ErrorCode.PARAM_DECIMAL_ERROR.getCode(),
									String.format(ErrorCode.PARAM_DECIMAL_ERROR.getDes(), fieldDesc));
						}
						break;
					case MOBILE:
						if(!RegexUtils.isPhoneNumber(value.toString())){
							throw new ValidateException(ErrorCode.PARAM_MOBILE_ERROR.getCode(),
									String.format(ErrorCode.PARAM_MOBILE_ERROR.getDes(), fieldDesc));
						}
						break;
					case DATE:
						if(!RegexUtils.isValidDate(value.toString(), "yyyy-MM-dd")){
							throw new ValidateException(ErrorCode.PARAM_DATE_ERROR.getCode(),
									String.format(ErrorCode.PARAM_DATE_ERROR.getDes(), fieldDesc));
						}
						break;
					case DATETIME:
						if(!RegexUtils.isValidDate(value.toString(), "yyyy-MM-dd hh:mm:ss")){
							throw new ValidateException(ErrorCode.PARAM_DATETIME_ERROR.getCode(),
									String.format(ErrorCode.PARAM_DATETIME_ERROR.getDes(), fieldDesc));
						}
						break;
					default:
						break;
				}
			}
		}
		
		if(StringUtils.isNotBlank(dataValidate.regexExpression())){
			if(value.toString().matches(dataValidate.regexExpression())){
				throw new ValidateException(ErrorCode.PARAM_FORMAT_ERROR.getCode(),
						String.format(ErrorCode.PARAM_FORMAT_ERROR.getDes(), fieldDesc));
			}
		}
		/*************注解解析工作结束******************/
		return true;
	}
}
