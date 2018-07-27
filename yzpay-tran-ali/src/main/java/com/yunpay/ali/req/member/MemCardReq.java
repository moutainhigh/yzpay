package com.yunpay.ali.req.member;

import com.yunpay.ali.req.card.CardCommonReq;

/**
 * 文件名称:    AliMemCardReq.java
 * 内容摘要:    会员卡模板参数
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年9月12日下午4:06:06 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年9月12日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class MemCardReq extends CardCommonReq{
	
	private TemplateBaseInfo templateBaseInfo;
	
	/**
	 * 初始化请求参数
	 * @param appId
	 * @param privateKey
	 * @param alipayPublicKey
	 * @param requestId
	 * @param cardType
	 * @param bizNoPrefix
	 * @param bizNoSuffixLen
	 * @param writeOffType
	 * @param templateStyleInfo
	 * @param templateBenefitInfo
	 * @param columnInfoList
	 * @param fieldRuleList
	 */
	public MemCardReq(String appId,String privateKey,String alipayPublicKey,TemplateBaseInfo templateBaseInfo){
		setApp_id(appId);
		setPrivateKey(privateKey);
		setAlipayPublicKey(alipayPublicKey);
		setTemplateBaseInfo(templateBaseInfo);
	}

	public TemplateBaseInfo getTemplateBaseInfo() {
		return templateBaseInfo;
	}

	public void setTemplateBaseInfo(TemplateBaseInfo templateBaseInfo) {
		this.templateBaseInfo = templateBaseInfo;
	}
	
	
}
