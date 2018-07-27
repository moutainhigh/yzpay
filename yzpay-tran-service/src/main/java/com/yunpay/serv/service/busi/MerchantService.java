package com.yunpay.serv.service.busi;

import com.yunpay.serv.model.Merchant;

/**
 * 商户信息数据处理类
 * 文件名称:     MerchantService.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年8月9日下午4:22:28 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年8月9日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public interface MerchantService {
	public Merchant queryMerchInfo(String merchNo);
}
