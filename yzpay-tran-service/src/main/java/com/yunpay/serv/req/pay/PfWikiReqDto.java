package com.yunpay.serv.req.pay;

import com.yunpay.serv.validate.annotation.DataValidate;
import com.yunpay.serv.validate.enums.RegexType;

/**
 * 微信公众号配置
 * 文件名称:     PfWikiReqDto.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年11月22日上午11:34:09 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年11月22日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class PfWikiReqDto {
	
	@DataValidate(allowNull=false,maxLength=30)
	private String mer_no;
	@DataValidate(allowNull=false,maxLength=30)
	private String sub_mch_id;
	//配置类型(1：支付授权目录，2：关联的appId，3：推荐关注的公众号appid)
	@DataValidate(allowNull=false,regexType=RegexType.NUMBER,maxLength=1)
	private Integer config_type;
	@DataValidate(allowNull=false,maxLength=256)
	private String wiki_config;
	
	
	public String getMer_no() {
		return mer_no;
	}
	public void setMer_no(String mer_no) {
		this.mer_no = mer_no;
	}
	public String getSub_mch_id() {
		return sub_mch_id;
	}
	public void setSub_mch_id(String sub_mch_id) {
		this.sub_mch_id = sub_mch_id;
	}
	public Integer getConfig_type() {
		return config_type;
	}
	public void setConfig_type(Integer config_type) {
		this.config_type = config_type;
	}
	public String getWiki_config() {
		return wiki_config;
	}
	public void setWiki_config(String wiki_config) {
		this.wiki_config = wiki_config;
	}
	
	
	
}
