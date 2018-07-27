package com.yunpay.serv.service.busi;

import com.yunpay.serv.model.CardCoupon;

/**
 * 卡券领取数据处理接口类
 * 文件名称:     CardCouponService.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年8月9日下午4:15:05 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年8月9日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public interface CardCouponService {
	
	/**
	 * @Description: 添加卡券信息
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月9日下午4:15:25 
	 * @param cardCoupon
	 * @return
	 */
	public boolean createCardCoupon(CardCoupon cardCoupon);
	
	
	/**
	 * @Description: 修改用户领取的卡券状态
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月9日下午4:34:19 
	 * @param cardId
	 * @param cardCode
	 * @param status
	 * @return
	 */
	public boolean updCardCouponStatus(String cardId,String cardCode,Integer status);
	
	/**
	 * @Description: 用户卡券核销
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月9日下午5:33:54 
	 * @param cardId  卡券id
	 * @param cardCode 用户领取卡券码
	 * @return
	 */
	public boolean cardCouponConsum(String cardId,String cardCode);
	
}
