package com.yunpay.serv.service.busi.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yunpay.common.utils.DateUtil;
import com.yunpay.common.utils.EnumUtil.CardStatus;
import com.yunpay.common.utils.EnumUtil.CardType;
import com.yunpay.serv.dao.CardInfoDao;
import com.yunpay.serv.model.CardInfo;
import com.yunpay.serv.req.card.CardReqDto;
import com.yunpay.serv.service.busi.CardInfoService;

/**
 * 文件名称:    CardInfoServiceImpl.java
 * 内容摘要:    卡券业务数据处理类
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年8月8日上午10:06:39 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年8月8日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
@Service
public class CardInfoServiceImpl implements CardInfoService{
	
	@Autowired
	private CardInfoDao cardInfoDao;

	/**
	 * @Description: 根据卡券id查找卡券信息
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月9日上午10:38:24 
	 * @param cardId
	 * @return
	 */
	@Override
	public CardInfo findCardByCardId(String cardId){
		return cardInfoDao.findCardByCardId(cardId);
	}
	
	/**
	 * @Description:添加卡券信息
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月8日下午4:23:53 
	 * @param cardReq
	 * @return
	 */
	@Override
	public CardInfo createCardInfo(CardReqDto cardReq) {
		CardInfo cardInfo =new CardInfo();
		cardInfo.setWeixin_card_id(cardReq.getCard_id());
		cardInfo.setTitle(cardReq.getTitle());
		cardInfo.setCardColor(cardReq.getColor());
		cardInfo.setType(Integer.valueOf(cardReq.getCard_type()));
		cardInfo.setNumber(0);
		cardInfo.setInventory(Integer.valueOf(cardReq.getQuantity()));
		cardInfo.setBackground(cardReq.getColor());
		cardInfo.setLogo(cardReq.getLogo_url());
		cardInfo.setStartDate(DateUtil.formatDate(cardReq.getBegin_timestamp(),"yyyy-MM-dd"));
		cardInfo.setEndDate(DateUtil.formatDate(cardReq.getEnd_timestamp(),"yyyy-MM-dd"));
		cardInfo.setTel(cardReq.getService_phone());
		cardInfo.setOperation(cardReq.getDescription());
		cardInfo.setMerchant(cardReq.getMerchant_num());
		cardInfo.setCreatedAt(new Date());
		if(CardType.DISCOUNT.getValue().equals(cardReq.getCard_type())){
			cardInfo.setDiscount(Double.valueOf(cardReq.getDiscount()));
		}
		cardInfo.setLimitNum(Integer.valueOf(cardReq.getGet_limit()));
		cardInfo.setCanShare(cardReq.getCan_share());
		cardInfo.setCanGive(cardReq.getCan_give_friend());
		cardInfo.setNotice(cardReq.getNotice());
		cardInfo.setStatus(CardStatus.NOT_CHECK.getValue());
		cardInfo.setPutchannel(Integer.parseInt(cardReq.getPut_channel()));
		cardInfo.setMerNo(cardReq.getMerchant_num());
		if(CardType.CASH.getValue().equals(cardReq.getCard_type())){
			cardInfo.setDiscountMoney(Double.valueOf(cardReq.getReduce_cost()));
			cardInfo.setLimitMoney(Double.valueOf(cardReq.getLeast_cost()));
		}
		cardInfo.setMerchantName(cardReq.getMerchant_name());
		cardInfo.setDetail(cardReq.getDefault_detail());
		cardInfo.setCodetype(cardReq.getCode_type());
		cardInfo.setCouponsType(0);
		cardInfoDao.insert(cardInfo);
		return cardInfo;
	}
	
	/**
	 * @Description:添加卡券信息
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月8日下午4:23:53 
	 * @param cardReq
	 * @return
	 */
	@Override
	public boolean updateCardInfo(CardInfo cardInfo,CardReqDto cardReq) {
		cardInfo.setTitle(cardReq.getTitle());
		cardInfo.setCardColor(cardReq.getColor());
		cardInfo.setBackground(cardReq.getColor());
		cardInfo.setLogo(cardReq.getLogo_url());
		if(StringUtils.isNotBlank(cardReq.getBegin_timestamp()) && 
				StringUtils.isNotBlank(cardReq.getEnd_timestamp())){
			cardInfo.setStartDate(DateUtil.formatDate(cardReq.getBegin_timestamp(),"yyyy-MM-dd"));
			cardInfo.setEndDate(DateUtil.formatDate(cardReq.getEnd_timestamp(),"yyyy-MM-dd"));
		}
		cardInfo.setTel(cardReq.getService_phone());
		cardInfo.setOperation(cardReq.getDescription());
		cardInfo.setUpdatedAt(new Date());
		cardInfo.setLimitNum(Integer.valueOf(cardReq.getGet_limit()));
		cardInfo.setCanShare(cardReq.getCan_share());
		cardInfo.setCanGive(cardReq.getCan_give_friend());
		cardInfo.setNotice(cardReq.getNotice());
		cardInfo.setDetail(cardReq.getDefault_detail());
		cardInfo.setCodetype(cardReq.getCode_type());
		return cardInfoDao.updateByCardId(cardInfo)>0;
	}

	/**
	 * @Description:修改卡券信息
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月8日下午4:24:07 
	 * @param cardInfo
	 * @return
	 */
	@Override
	public CardInfo updateCardInfo(CardInfo cardInfo) {
		cardInfoDao.updateByPrimaryKeySelective(cardInfo);
		return cardInfo;
	}

	/**
	 * @Description:设置微信卡券投放码信息 
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月9日上午10:42:19 
	 * @param cardId     卡券id
	 * @param qrCodeUrl  微信卡券二维码显示地址
	 * @param url		 微信卡券二维码解析字符
	 * @return
	 */
	@Override
	public boolean updCardQrCode(String cardId, String qrCodeUrl, String url) {
		return cardInfoDao.updCardQrCode(cardId, qrCodeUrl, url)>0;
	}

	/**
	 * @Description: 修改卡券状态
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月9日下午12:00:07 
	 * @param cardId  卡券id
	 * @param status  状态
	 * @return
	 */
	@Override
	public boolean updCardStatus(String cardId, int status) {
		return cardInfoDao.updCardStatus(cardId, status)>0;
	}

	/**
	 * @Description: 修改卡券领取数量+1
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月9日下午4:39:33 
	 * @param cardId
	 * @return
	 */
	@Override
	public boolean updateCardRecvNum(String cardId) {
		return cardInfoDao.updateCardRecvNum(cardId)>0;
	}
	
	/**
	 * @Description:查询卡券列表
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月14日下午3:17:27 
	 * @return
	 */
	@Override
	public List<CardInfo> queryCardList() {
		return cardInfoDao.queryCardList();
	}

}
