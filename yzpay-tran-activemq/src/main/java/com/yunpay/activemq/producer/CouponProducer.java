package com.yunpay.activemq.producer;
import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

/**
 * 消息生产方
 * 文件名称:     ProducerService.java
 * 内容摘要: activemq的消息生产方，业务层调用该服务将消息发送到activemq中
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2018年1月12日上午11:16:33 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * 2018年3月1日        Zeng Dongcheng   1.0     修改sendMessage(final String msg)方法第一行
 * ----------------------------------------------  
 * 2018年1月12日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2018
 * 公司:   深圳市至高通信技术发展有限公司
 */
@Service
public class CouponProducer {

    @Resource(name="jmsTemplate")
    private JmsTemplate jmsTemplate;
    
    /**
     * @Description: 指定发送目的地
     * @author:   Zeng Dongcheng
     * @Date:     2018年1月12日上午11:51:20 
     * @param destName
     * @param msg
     */
    public void sendMessage(String destName,final String msg){
        System.out.println("Send " + msg + " to Destination " + destName);
        MessageCreator messageCreator = new MessageCreator(){

            public Message createMessage(Session session) throws JMSException {

                return session.createTextMessage(msg);
            }
            
        };
        jmsTemplate.send(destName, messageCreator);
    }
    
    /**
     * @Description: 发送到默认目的地
     * @author:   Zeng Dongcheng
     * @Date:     2018年1月12日上午11:51:31 
     * @param msg
     */
    public void sendMessage(final String msg){
        String destination = jmsTemplate.getDefaultDestination().toString();
        System.out.println("Send " + msg + " to Destination " + destination);
        MessageCreator messageCreator = new MessageCreator(){
            public Message createMessage(Session session) throws JMSException {
                // TODO Auto-generated method stub
                return session.createTextMessage(msg);
            }
        };
        jmsTemplate.send(messageCreator);
    }
    
    /**
     * @Description: 发送到默认目的地
     * @author:   Zeng Dongcheng
     * @Date:     2018年1月12日上午11:51:31 
     * @param msg
     */
    public void send(final String message){
        jmsTemplate.convertAndSend(message);
    }
}