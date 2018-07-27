package com.yunpay.serv.service.busi;
import com.yunpay.serv.model.MemberConsumLs;

/**
 * 会员消费流水数据处理接口类
 * 文件名称:     MemConsumLsService.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年9月21日上午9:28:33 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年9月21日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public interface MemConsumLsService {
	
	/**
	 * @Description:添加会员消费流水
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月21日上午9:28:59 
	 * @param memberConsumLs
	 * @return
	 */
	public boolean createMemConsumLs(MemberConsumLs memberConsumLs);
}
