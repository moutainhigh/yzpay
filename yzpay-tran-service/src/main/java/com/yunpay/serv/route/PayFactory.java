package com.yunpay.serv.route;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunpay.common.utils.EnumUtil.PayChannel;
import com.yunpay.serv.service.pay.QrPayService;


/**
 * 支付路由工厂类
 * 文件名称:     PayFactory.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年6月20日下午2:36:40 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年6月20日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
@Service("payFactory")
public class PayFactory implements AbstractFactory{
	
	//微信支付码前两位
	private static final String WECHAT_PREFIX="10,11,12,13,14,15";
	//支付宝支付码前两位
	private static final String ALI_PREFIX="25,26,27,28,29,30";
	//银联支付码前两位
	private static final String UNION_PREFIX="62";
	
	@Autowired
	private QrPayService wechatPayService;
	@Autowired
	private QrPayService aliPayService;
	@Autowired
	private QrPayService unionPayService;
	//@Autowired
	//private PfQrPayService pfWchtPayService;
	//@Autowired
	//private PfQrPayService pfAliPayService;
	

	/**
	 * @Description:		根据支付码选择支付服务
	 * @author:   			Zeng Dongcheng
	 * @Date:     			2017年6月20日下午2:37:01 
	 * @param orderPrefix   支付码前两位
	 * @return              支付服务
	 */
	@Override
	public QrPayService getServiceByOrderfix(String codePrefix) {
		if(codePrefix==null){
			return null;
		}
		if(WECHAT_PREFIX.indexOf(codePrefix)>=0){
			return wechatPayService;
		}else if(ALI_PREFIX.indexOf(codePrefix)>=0){
			return aliPayService;
		}else if(UNION_PREFIX.equals(codePrefix)){
			return unionPayService;
		}else{
			return null;
		}
	}
	
	
	/**
	 * 
	 * @Description:	根据支付渠道选择支付服务
	 * @author:   		Zeng Dongcheng
	 * @Date:     		2017年6月20日下午2:37:46 
	 * @param channel   支付渠道
	 * @return			支付服务
	 */
	@Override
	public QrPayService getServiceByChannel(int channel) {
		if(PayChannel.WECHAT.getValue()==channel){
			return wechatPayService;
		}else if(PayChannel.ALIPAY.getValue()==channel){
			return aliPayService;
		}else if(PayChannel.UNION.getValue()==channel){
			return unionPayService;
		}
		return null;
	}

	
//	@Override
//	public PfQrPayService getPfServiceByOrderfix(String codePrefix) {
//		if(codePrefix==null){
//			return null;
//		}
//		if(WECHAT_PREFIX.indexOf(codePrefix)>=0){
//			return pfWchtPayService;
//		}else if(ALI_PREFIX.indexOf(codePrefix)>=0){
//			return pfAliPayService;
//		}else{
//			return null;
//		}
//	}


//	@Override
//	public PfQrPayService getPfServiceByChannel(int channel) {
//		if(PayChannel.WECHAT.getValue()==channel){
//			return pfWchtPayService;
//		}else if(PayChannel.ALIPAY.getValue()==channel){
//			return pfAliPayService;
//		}
//		return null;
//	}

	public static void main(String args[]){
		System.out.println("10".indexOf(WECHAT_PREFIX));
		System.out.println(ALI_PREFIX.indexOf("10"));
	}


	
}
