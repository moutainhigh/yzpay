package com.yunpay.serv.service.busi;

import java.util.List;

import com.yunpay.serv.model.CardInfo;
import com.yunpay.serv.req.card.CardReqDto;
/**
 * 卡券业务数据处理接口类
 * 文件名称:     CardInfoService.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年8月9日上午10:39:33 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年8月9日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public interface CardInfoService {
	/**
	 * @Description: 根据卡券id查找卡券信息
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月9日上午10:38:24 
	 * @param cardId
	 * @return
	 */
	public CardInfo findCardByCardId(String cardId);
	
	/**
	 * @Description:查询所有卡券列表 
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月14日下午3:13:04 
	 * @return
	 */
	public List<CardInfo> queryCardList();
	
	/**
	 * @Description:添加卡券信息
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月8日下午4:23:53 
	 * @param cardReq
	 * @return
	 */
	public CardInfo createCardInfo(CardReqDto cardReq);
	
	
	/**
	 * @Description: 卡券信息修改
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月17日下午1:53:58 
	 * @param cardReq
	 * @return
	 */
	public boolean updateCardInfo(CardInfo cardInfo,CardReqDto cardReq);
	
	/**
	 * @Description:修改卡券信息
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月8日下午4:24:07 
	 * @param cardInfo
	 * @return
	 */
	public CardInfo updateCardInfo(CardInfo cardInfo);
	
	/**
	 * @Description:设置微信卡券投放码信息 
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月9日上午10:42:19 
	 * @param cardId     卡券id
	 * @param qrCodeUrl  微信卡券二维码显示地址
	 * @param url		 微信卡券二维码解析字符
	 * @return
	 */
	public boolean updCardQrCode(String cardId,String qrCodeUrl,String url);
	
	/**
	 * @Description: 修改卡券状态
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月9日下午12:00:07 
	 * @param cardId
	 * @param status
	 * @return
	 */
	public boolean updCardStatus(String cardId,int status);
	
	/**
	 * @Description: 修改卡券领取数量+1
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月9日下午4:39:33 
	 * @param cardId
	 * @return
	 */
	public boolean updateCardRecvNum(String cardId);
}
