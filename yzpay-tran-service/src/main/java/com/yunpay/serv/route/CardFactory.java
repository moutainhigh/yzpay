package com.yunpay.serv.route;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunpay.common.utils.EnumUtil.PayChannel;
import com.yunpay.serv.service.card.CardCommonService;

@Service("cardFactory")
public class CardFactory {
	
	@Autowired
	private CardCommonService wechatCardServ;
	@Autowired
	private CardCommonService aliCardServ;
	
	/**
	 * 
	 * @Description:	根据支付渠道选择支付服务
	 * @author:   		Zeng Dongcheng
	 * @Date:     		2017年6月20日下午2:37:46 
	 * @param channel   支付渠道
	 * @return			支付服务
	 */
	public CardCommonService getCardServiceByChannel(int channel) {
		if(PayChannel.WECHAT.getValue()==channel){
			return wechatCardServ;
		}else if(PayChannel.ALIPAY.getValue()==channel){
			return aliCardServ;
		}
		return null;
	}
}
