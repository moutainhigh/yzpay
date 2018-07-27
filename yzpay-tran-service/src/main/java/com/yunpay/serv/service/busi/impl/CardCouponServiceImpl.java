package com.yunpay.serv.service.busi.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunpay.serv.dao.CardCouponDao;
import com.yunpay.serv.model.CardCoupon;
import com.yunpay.serv.service.busi.CardCouponService;

/**
 * 文件名称:    CardCouponServiceImpl.java
 * 内容摘要:    卡券领取数据处理类（添加卡券、用户领卡、卡券核销）
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年8月9日下午4:15:58 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年8月9日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
@Service
public class CardCouponServiceImpl implements CardCouponService{
	
	@Autowired
	private CardCouponDao cardCouponDao;

	/**
	 * @Description: 添加卡券信息
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月9日下午4:15:25 
	 * @param cardCoupon
	 * @return
	 */
	@Override
	public boolean createCardCoupon(CardCoupon cardCoupon) {
		return cardCouponDao.insert(cardCoupon)>0;
	}

	/**
	 * @Description:修改用户领取的卡券状态
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月9日下午4:34:56 
	 * @param cardId
	 * @param cardCode
	 * @param status
	 * @return
	 */
	@Override
	public boolean updCardCouponStatus(String cardId, String cardCode,
			Integer status) {
		return cardCouponDao.updCardCouponStatus(cardId, cardCode, status)>0;
	}

	/**
	 * @Description:卡券核销
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月11日上午9:13:52 
	 * @param cardId
	 * @param cardCode
	 * @return
	 */
	@Override
	public boolean cardCouponConsum(String cardId, String cardCode) {
		return cardCouponDao.cardCouponConsum(cardId, cardCode, new Date())>0;
	}

}
