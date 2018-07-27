package com.yunpay.serv.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.yunpay.database.dao.BaseDao;
import com.yunpay.serv.model.CardInfo;

/**
 * 卡券信息数据处理接口类
 * 文件名称:     CardInfoDao.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年8月16日下午6:02:47 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年8月16日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
@Repository("cardInfoDao")
public interface CardInfoDao extends BaseDao<CardInfo>{
	
	/**
	 * @Description: 查询卡券信息
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月16日下午6:02:21 
	 * @param cardId 卡券id
	 * @return
	 */
	@Select("select * from t_card_info where weixin_card_id=#{cardId}")
	public CardInfo findCardByCardId(@Param("cardId")String cardId);
	
	/**
	 * @Description:查询卡券列表
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月16日下午6:02:05 
	 * @return
	 */
	@Select("select * from t_card_info where status<>4")
	public List<CardInfo> queryCardList();
	
	/**
	 * @Description:设置卡券投放二维码 
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月9日上午10:32:38 
	 * @param cardId  	卡券id
	 * @param qrCodeUrl 微信卡券二维码显示url
	 * @param url 		 微信卡券二维码解析字符
	 * @return
	 */
	@Update("update t_card_info set weixin_show_qrcode_url=#{qrCodeUrl},url=#{url} where weixin_card_id=#{cardId}")
	public int updCardQrCode(@Param("cardId")String cardId,@Param("qrCodeUrl")String qrCodeUrl,@Param("url")String url);
	
	/**
	 * @Description: 修改卡券状态
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月9日上午11:59:26 
	 * @param cardId
	 * @param status
	 * @return
	 */
	@Update("update t_card_info set status=#{status} where weixin_card_id=#{cardId}")
	public int updCardStatus(@Param("cardId")String cardId,@Param("status")int status);
	
	/**
	 * @Description: 修改卡券领取数量+1
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月9日下午4:39:33 
	 * @param cardId
	 * @return
	 */
	@Update("update t_card_info set number=number+1 where weixin_card_id=#{cardId}")
	public int updateCardRecvNum(@Param("cardId")String cardId);

	/**
	 * @Description: 卡券信息修改
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月17日下午1:56:10 
	 * @param cardInfo
	 * @return
	 */
	public int updateByCardId(CardInfo cardInfo);
}
