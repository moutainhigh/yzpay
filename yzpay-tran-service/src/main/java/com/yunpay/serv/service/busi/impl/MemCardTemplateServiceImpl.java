package com.yunpay.serv.service.busi.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunpay.serv.dao.MemCardTemplateDao;
import com.yunpay.serv.model.MemberCardTemplate;
import com.yunpay.serv.service.busi.MemCardTemplateService;

/**
 * 会员卡业务数据处理实现类
 * 文件名称:     MemCardInfoServiceImpl.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年9月11日下午1:51:44 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年9月11日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
@Service
public class MemCardTemplateServiceImpl implements MemCardTemplateService{
	
	@Autowired
	private MemCardTemplateDao memCardTemplateDao;
	
	/**
	 * @Description:修改会员卡信息状态
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月11日下午1:52:55 
	 * @param cardId
	 * @param status
	 * @return
	 */
	@Override
	public boolean updMemCardStatus(String cardId, int status) {
		return memCardTemplateDao.updMemCardStatus(cardId, status)>0;
	}

	/**
	 * @Description:查找会员卡模板信息
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月19日下午4:09:55 
	 * @param cardId
	 * @return
	 */
	@Override
	public MemberCardTemplate findMemCardTempByWxCardId(String cardId) {
		return memCardTemplateDao.findMemCardTempByWxCardId(cardId);
	}

}
