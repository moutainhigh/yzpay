package com.yunpay.serv.service.card;

import com.yunpay.serv.rep.Message;
import com.yunpay.serv.req.card.CardReqDto;
/**
 * 卡券服务接口类
 * 文件名称:     CardService.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年8月28日上午9:37:57 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年8月28日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public abstract class CardService {
	
	
	
	/**
	 * @Description: 创建微信卡券
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月2日下午2:51:02 
	 * @param req
	 * @param accessToken
	 * @return
	 */
	public abstract Message cardCreate(CardReqDto req);
	
	/**
	 * @Description:修改修改微信卡券 
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月17日上午10:03:27 
	 * @param req
	 * @return
	 */
	public abstract Message cardUpdate(CardReqDto req);
	
	/**
	 * @Description: 创建卡券领取码
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月2日下午2:51:12 
	 * @param req
	 * @param accessToken
	 * @return
	 */
	public abstract Message qrCodeCreate(String merchantNum,String cardId);
		
	/**
	 * @Description: 卡券核销
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月10日下午5:37:12 
	 * @param merchantNum 商户号
	 * @param cardCode  卡券核销码
	 * @return
	 */
	public abstract Message cardConsume(String merchantNum,String cardCode);
	
	/**
	 * @Description: 卡券删除
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月14日上午10:26:27 
	 * @param merchantNum
	 * @param cardId
	 * @return
	 */
	public abstract Message cardDelete(String merchantNum,String cardId);
	
	/**
	 * @Description: 查询卡券信息
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月15日上午10:05:55 
	 * @param cardId
	 * @return
	 */
	public abstract Message cardSearch(String cardId);
	
	/**
	 * @Description: 公众号推送卡券
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月24日下午5:25:01 
	 * @param cardId
	 * @return
	 */
	public abstract Message cardPushToWxapp(String merchantNum,String cardId);
	
	/**
	 * @Description: 设置卡券支持快速买单功能
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月30日上午9:34:53 
	 * @param merchantNum
	 * @param cardId
	 * @return
	 */
	public abstract Message cardPaySet(String merchantNum,String cardId);
	
}
