package com.yunpay.serv.service.busi;

import java.util.List;
import com.yunpay.common.utils.EnumUtil.PayChannel;
import com.yunpay.common.utils.EnumUtil.Route;
import com.yunpay.common.utils.EnumUtil.SubChannel;
import com.yunpay.serv.model.Merchant;
import com.yunpay.serv.model.PayTranLs;
import com.yunpay.serv.req.pay.AppPayReqDto;
import com.yunpay.serv.req.pay.AppletReqDto;
import com.yunpay.serv.req.pay.BarPayReqDto;
import com.yunpay.serv.req.pay.ScanPayReqDto;
import com.yunpay.serv.req.pay.WapPayReqDto;


/**
 * 支付流水服务接口类
 * 文件名称:     PayTranLsService.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年7月12日下午5:20:46 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年7月12日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public interface PayTranLsService {
	
	/**
	 * @Description: 创建条码支付流水
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年7月12日下午5:17:36 
	 * @param qrPayReq    二维码请求参数
	 * @param merchant    商户信息
	 * @param subChannel  支付方式
	 * @return
	 */
	public PayTranLs createBarTranLs(BarPayReqDto barPayReq,Merchant merchant,SubChannel subChannel,Route route);
	
	/**
	 * @Description:创建扫码支付流水
	 * @author:   Zeng Dongcheng
	 * @Date:     2018年1月5日上午10:14:57 
	 * @param scanPayReq
	 * @param merchant
	 * @param subChannel
	 * @param route
	 * @return
	 */
	public PayTranLs createScanTranLs(ScanPayReqDto scanPayReq,Merchant merchant,SubChannel subChannel,Route route);
	
	
	
	/**
	 * @Description: 创建wap支付流水
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年7月20日下午3:54:31 
	 * @param qapPayReq
	 * @param merchant
	 * @param channel
	 * @return
	 */
	public PayTranLs createWapTranLs(WapPayReqDto qapPayReq,Merchant merchant,PayChannel payChannel);
	
	/**
	 * @Description: 创建小程序支付流水
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月15日下午4:45:11 
	 * @param appletReq
	 * @param merchant
	 * @return
	 */
	public PayTranLs createAppletTranLs(AppletReqDto appletReq,Merchant merchant);
	
	/**
	 * @Description: 创建一码付预付流水
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月6日下午2:32:31 
	 * @param qapPayReq
	 * @param merchant
	 * @return
	 */
	public PayTranLs createOneCodeTranLs(WapPayReqDto wapPayReq,Merchant merchant);
	
	
	/**
	 * @Description:创建app支付流水 
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年11月1日上午9:40:47 
	 * @param appReqDto
	 * @param merchant
	 * @return
	 */
	public PayTranLs createAppTranLs(AppPayReqDto appPayReq, 
			Merchant merchant,PayChannel payChannel);
	
	/**
	 * @Description:修改一码付交易流水 
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月7日上午8:50:06 
	 * @param payTranLs
	 * @param payChannel
	 * @return
	 */
	public PayTranLs updateOneCodeTranLs(PayTranLs payTranLs,PayChannel payChannel);
	
	/**
	 * @Description: 修改支付流水信息
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年7月12日下午5:18:56 
	 * @param payTranLs
	 * @return
	 */
	public PayTranLs updatePayTranLs(PayTranLs payTranLs);
	
	/**
	 * @Description: 根据商户订单号查询支付流水信息
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年7月12日下午5:19:07 
	 * @param userOrderNo
	 * @param merchantNo
	 * @return
	 */
	public PayTranLs findTranLsByOrderNo(String userOrderNo,String merchantNo);
	
	/**
	 * @Description:根据平台流水号查询支付信息 
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年7月12日下午5:19:26 
	 * @param transNum
	 * @return
	 */
	public PayTranLs findTranLsByTransNum(String transNum);
	
	/**
	 * @Description:修改支付流水状态 
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年7月12日下午5:19:39 
	 * @param id
	 * @param status
	 * @return
	 */
	public int updTranStatus(int id,int status);
	
	/**
	 * @Description: 查询时间内的待同步支付流水
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年7月12日下午5:19:52 
	 * @param intervalTime
	 * @return
	 */
	public List<PayTranLs> selectAllSynPayLs(Integer intervalTime);
	
	/**
	 * @Description: 修改交易流水(退款信息)
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月18日下午7:35:08 
	 * @param id
	 * @param status
	 * @param refundPrice
	 * @return
	 */
	public int updTranLsRefund(Integer id,Integer status,float refundPrice);
}
