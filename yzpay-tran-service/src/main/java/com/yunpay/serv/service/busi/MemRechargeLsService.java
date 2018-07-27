package com.yunpay.serv.service.busi;

import com.yunpay.serv.model.MemberRechargeLs;

/**
 * 会员充值流水数据处理接口类
 * 文件名称:     MemRechargeLsService.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年9月20日下午5:26:15 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年9月20日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public interface MemRechargeLsService {
	
	/**
	 * @Description: 创建充值流水
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月20日下午5:26:37 
	 * @param memberRecharge
	 * @return
	 */
	public boolean createMemRechargeLs(MemberRechargeLs memberRecharge);
	
}
