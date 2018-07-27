package com.yunpay.serv.req.member;

import com.yunpay.serv.validate.annotation.DataValidate;
import com.yunpay.serv.validate.enums.RegexType;

/**
 * 文件名称:    MemActiveReqDto.java
 * 内容摘要:    微信会员卡激活参数设置请求参数
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年9月5日下午2:17:39 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年9月5日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class MemActivateReqDto {
	@DataValidate(allowNull=false,regexType=RegexType.NOT_CHINESE)
	private String card_id;
	@DataValidate(allowNull=false,regexType=RegexType.NUMBER)
	private String merchant_num;
	
	private String[] require_fields;
	private String[] common_fields;
	
	public String getCard_id() {
		return card_id;
	}
	public void setCard_id(String card_id) {
		this.card_id = card_id;
	}
	
	public String getMerchant_num() {
		return merchant_num;
	}
	public void setMerchant_num(String merchant_num) {
		this.merchant_num = merchant_num;
	}
	public String[] getRequire_fields() {
		return require_fields;
	}
	public void setRequire_fields(String[] require_fields) {
		this.require_fields = require_fields;
	}
	public String[] getCommon_fields() {
		return common_fields;
	}
	public void setCommon_fields(String[] common_fields) {
		this.common_fields = common_fields;
	} 
}
