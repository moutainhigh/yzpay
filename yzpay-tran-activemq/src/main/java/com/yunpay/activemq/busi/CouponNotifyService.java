package com.yunpay.activemq.busi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunpay.activemq.producer.CouponProducer;
import com.yunpay.common.utils.Log;

/**
 * 卡券核销异步通知
 * 文件名称:     CouponNotifyService.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2018年1月23日下午4:24:58 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2018年1月23日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2018
 * 公司:   深圳市至高通信技术发展有限公司
 */
@Service("couponNotifyService")
public class CouponNotifyService {
	
	@Autowired
	private CouponProducer couponProducer;
	
	/**
	 * @Description: 发送卡券核销通知到activemq
	 * @author:   Zeng Dongcheng
	 * @Date:     2018年1月23日下午4:26:39 
	 * @param sendMsg
	 * @return
	 */
	public boolean sendCouponNotify(String sendMsg){
		try{
			couponProducer.send(sendMsg);
			return true;
		}catch(RuntimeException e){
			Log.error(e.toString());
			return false;
		}
	}
}
