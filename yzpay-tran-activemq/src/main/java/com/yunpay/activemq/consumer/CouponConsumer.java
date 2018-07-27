package com.yunpay.activemq.consumer;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.commons.lang.StringUtils;
import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.stereotype.Component;
import com.yunpay.common.utils.HttpClientUtil;
import com.yunpay.common.utils.Log;


/**
 * 消息消费方
 * 文件名称:     ConsumerService.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2018年1月12日上午11:16:49 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2018年1月12日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2018
 * 公司:   深圳市至高通信技术发展有限公司
 */
@Component("couponConsumer")
public class CouponConsumer implements SessionAwareMessageListener<Message>{

	@Override
	public void onMessage(Message message, Session session) throws JMSException {
		Log.info("在onMessage中线程ID是",Thread.currentThread());
		Log.info("重发标志："+message.getJMSRedelivered());
		String ms =((TextMessage)message).getText();
		if (ms != null ) { 
			Log.info("==>Consumer收到的报文为:" + ms); 
			HttpClientUtil serviceRequest = new HttpClientUtil();
			String recvMsg = serviceRequest.doPost(ms, null,"UTF-8");
			Log.info(recvMsg);
			if(StringUtils.isBlank(recvMsg)){
				//如果接收到的消息为空，则抛出异常，让消息重发
				throw new RuntimeException("throw runtimeExcetpion");
			}
        }    
	}

   public static void main(String args[]){
	   String s = "1ssss111";
	   System.out.println(s.indexOf("1"));
   }

}
