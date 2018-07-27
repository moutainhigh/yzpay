package com.yunpay.serv.service.busi;
import com.yunpay.serv.model.MemberCardTemplate;

/**
 * 会员卡模板数据处理接口类
 * 文件名称:     MemCardTemplateService.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年9月19日下午4:39:59 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年9月19日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public interface MemCardTemplateService {
	
	/**
	 * 修改会员卡状态
	 * @Description: 
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月11日上午11:49:51 
	 * @param cardId
	 * @param status
	 * @return
	 */
	public boolean updMemCardStatus(String cardId,int status);
	
	
	/**
	 * @Description: 根据微信card_id查找会员卡模板信息
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月19日下午3:54:35 
	 * @param cardId
	 * @return
	 */
	public MemberCardTemplate findMemCardTempByWxCardId(String cardId);
	
}
