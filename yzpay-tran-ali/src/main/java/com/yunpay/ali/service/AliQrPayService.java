package com.yunpay.ali.service;

import com.yunpay.ali.rep.pay.AliBarRep;
import com.yunpay.ali.rep.pay.AliCancelRep;
import com.yunpay.ali.rep.pay.AliCloseRep;
import com.yunpay.ali.rep.pay.AliQueryRep;
import com.yunpay.ali.rep.pay.AliRefundQueryRep;
import com.yunpay.ali.rep.pay.AliRefundRep;
import com.yunpay.ali.rep.pay.AliScanRep;
import com.yunpay.ali.req.pay.AliAppReq;
import com.yunpay.ali.req.pay.AliBarReq;
import com.yunpay.ali.req.pay.AliCancelReq;
import com.yunpay.ali.req.pay.AliCloseReq;
import com.yunpay.ali.req.pay.AliQueryReq;
import com.yunpay.ali.req.pay.AliRefundQueryReq;
import com.yunpay.ali.req.pay.AliRefundReq;
import com.yunpay.ali.req.pay.AliScanReq;
import com.yunpay.ali.req.pay.AliWapReq;

/**
 * 文件名称:    AliQrPayService.java
 * 内容摘要:    支付宝二维码支付接口封装类
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年6月20日下午5:19:16 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年6月20日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public interface AliQrPayService {
	/**
	 * 条码支付
	 * @Description: 
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年6月26日上午10:15:43 
	 * @param aliBarReq
	 * @return
	 */
	public AliBarRep doAliBarPay(AliBarReq aliBarReq);
	
	/**
	 * 扫码支付
	 * @Description: 
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年6月26日上午10:15:50 
	 * @param aliScanReq
	 * @return
	 */
	public AliScanRep doAliScanPay(AliScanReq aliScanReq);
	
	/**
	 * @Description: 支付宝wap支付
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年6月27日上午10:36:21 
	 * @param aliWapReq
	 * @return
	 */
	public String doAliWapPay(AliWapReq aliWapReq);
	
	/**
	 * @Description: 支付宝app支付
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年10月31日下午3:41:18 
	 * @param aliAppReq
	 * @return
	 */
	public String doAliAppPay(AliAppReq aliAppReq);
	
	/**
	 * 订单查询
	 * @Description: 
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年6月26日上午10:15:55 
	 * @param aliQueryReq
	 * @return
	 */
	public AliQueryRep doAliQuery(AliQueryReq aliQueryReq);
	
	/**
	 * 订单撤销
	 * @Description: 
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年6月26日上午10:16:01 
	 * @param aliCancelReq
	 * @return
	 */
	public AliCancelRep doAliCancel(AliCancelReq aliCancelReq);
	
	/**
	 * @Description: 订单关闭
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年7月12日下午6:06:58 
	 * @param aliCloseRep
	 * @return
	 */
	public AliCloseRep doAliClose(AliCloseReq aliCloseReq);
	
	/**
	 * @Description:退款申请
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年7月11日下午4:42:18 
	 * @param aliRefundReq
	 * @return
	 */
	public AliRefundRep doAliRefund(AliRefundReq aliRefundReq);
	
	/**
	 * @Description:退款查询 
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年7月11日下午4:42:32 
	 * @param aliRefundQueryReq
	 * @return
	 */
	public AliRefundQueryRep doAliRefundQuery(AliRefundQueryReq aliRefundQueryReq);
}
