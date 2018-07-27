package com.yunpay.serv.service.pay;

import org.springframework.stereotype.Service;

import com.yunpay.serv.model.Merchant;
import com.yunpay.serv.model.PayTranLs;
import com.yunpay.serv.model.RefundTranLs;
import com.yunpay.serv.rep.Message;
import com.yunpay.serv.req.pay.AppPayReqDto;
import com.yunpay.serv.req.pay.AppletReqDto;
import com.yunpay.serv.req.pay.BarPayReqDto;
import com.yunpay.serv.req.pay.QrRefundReqDto;
import com.yunpay.serv.req.pay.ScanPayReqDto;
import com.yunpay.serv.req.pay.WapPayReqDto;

/**
 * 官方渠道二维码支付(条码和扫码)服务接口类
 * 文件名称:     QrPayService.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年6月23日下午2:42:19 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年6月23日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
@Service
public abstract class QrPayService {
	
	/**
	 * @Description: 条码支付
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年6月23日下午2:42:46 
	 * @param qrPayReq
	 * @param merchant
	 * @return
	 */
	public abstract Message doBarPay(BarPayReqDto barPayReq,Merchant merchant);
	
	/**
	 * @Description:扫码支付 
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年6月23日下午2:42:58 
	 * @param qrPayReq
	 * @param merchant
	 * @return
	 */
	public abstract Message doScanPay(ScanPayReqDto scanPayReq,Merchant merchant);
	
	/**
	 * @Description:wap支付 
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年6月27日上午11:30:55 
	 * @param wapPayReq
	 * @param merchant
	 * @return
	 */
	public abstract Message doWapPay(WapPayReqDto wapPayReq,Merchant merchant);
	
	/**
	 * @Description:一码付
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月6日下午5:51:00 
	 * @param payTranLs
	 * @return
	 */
	public abstract Message doOneCodePay(PayTranLs payTranLs,String authCode);
	
	/**
	 * @Description:微信小程序支付 
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月15日下午4:17:04 
	 * @param appletReq
	 * @param merchant
	 * @return
	 */
	public abstract Message doAppletPay(AppletReqDto appletReq,Merchant merchant);
	
	/**
	 * @Description: app支付
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年11月1日上午9:17:13 
	 * @param appReqDto
	 * @param merchant
	 * @return
	 */
	public abstract Message doAppPay(AppPayReqDto appPayReq,Merchant merchant);
	
	/**
	 * @Description: 订单查询
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年6月23日下午2:43:08 
	 * @param payTranLs
	 * @return
	 */
	public abstract Message doOrderQuery(PayTranLs payTranLs);
	
	
	/**
	 * @Description: 订单关闭
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年7月12日下午5:55:46 
	 * @param payTranLs
	 * @return
	 */
	public abstract Message doOrderClose(PayTranLs payTranLs);
	
	/**
	 * @Description: 订单撤销
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年6月23日下午2:43:22 
	 * @param payTranLs
	 * @return
	 */
	public abstract Message doOrderReverse(PayTranLs payTranLs);
	
	
	/**
	 * @Description: 订单退款申请
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年7月10日下午3:22:52 
	 * @param qrRefundReq
	 * @return
	 */
	public abstract Message doOrderRefund(QrRefundReqDto qrRefundReq,PayTranLs payTranLs);
	
	
	/**
	 * @Description:退款查询 
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年7月11日上午11:23:47 
	 * @param refundTranLs
	 * @return
	 */
	public abstract Message doRefundQuery(RefundTranLs refundTranLs);
	
}
