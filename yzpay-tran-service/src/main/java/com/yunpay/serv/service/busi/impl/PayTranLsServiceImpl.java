package com.yunpay.serv.service.busi.impl;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunpay.common.utils.EnumUtil.PayChannel;
import com.yunpay.common.utils.EnumUtil.PayStatus;
import com.yunpay.common.utils.EnumUtil.Route;
import com.yunpay.common.utils.EnumUtil.SubChannel;
import com.yunpay.common.utils.EnumUtil.TransType;
import com.yunpay.common.utils.IdWorker;
import com.yunpay.serv.dao.PayTranLsDao;
import com.yunpay.serv.model.Merchant;
import com.yunpay.serv.model.PayTranLs;
import com.yunpay.serv.req.pay.AppPayReqDto;
import com.yunpay.serv.req.pay.AppletReqDto;
import com.yunpay.serv.req.pay.BarPayReqDto;
import com.yunpay.serv.req.pay.ScanPayReqDto;
import com.yunpay.serv.req.pay.WapPayReqDto;
import com.yunpay.serv.service.busi.PayTranLsService;

/**
 * 交易流水业务类
 * 文件名称:     PayTranLsServiceImpl.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年6月22日下午1:37:27 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年6月22日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
@Service
public class PayTranLsServiceImpl implements PayTranLsService{
	
	@Autowired
	private PayTranLsDao payTranLsDao;
	
	/**
	 * 创建条码支付流水
	 * @Description:
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年6月21日下午6:07:01 
	 * @param qrPayReq
	 * @param merchant
	 * @param subChannel
	 * @return
	 */
	@Override
	public PayTranLs createBarTranLs(BarPayReqDto barPayReq, Merchant merchant,
			SubChannel subChannel,Route route) {
		IdWorker iw = new IdWorker();
		PayTranLs payTranLs = new PayTranLs();
		String orderNo = iw.getId() + "";
		if(subChannel==SubChannel.WECHAT_BAR){
			//微信条码
			payTranLs.setChannel(PayChannel.WECHAT.getValue());
			payTranLs.setSubChannel(SubChannel.WECHAT_BAR.getValue());
			payTranLs.setTitle(SubChannel.WECHAT_BAR.getName());
			payTranLs.setInfo(SubChannel.WECHAT_BAR.getName());
			payTranLs.setStatus(PayStatus.PAYING.getValue());
		}else if(subChannel==SubChannel.ALIPAY_BAR){
			//支付宝条码
			payTranLs.setChannel(PayChannel.ALIPAY.getValue());
			payTranLs.setSubChannel(SubChannel.ALIPAY_BAR.getValue());
			payTranLs.setTitle(SubChannel.ALIPAY_BAR.getName());
			payTranLs.setInfo(SubChannel.ALIPAY_BAR.getName());
			payTranLs.setStatus(PayStatus.PAYING.getValue());
		}else if(subChannel==SubChannel.UNION_BAR){
			//银联条码
			payTranLs.setChannel(PayChannel.UNION.getValue());
			payTranLs.setSubChannel(SubChannel.UNION_BAR.getValue());
			payTranLs.setTitle(SubChannel.UNION_BAR.getName());
			payTranLs.setInfo(SubChannel.UNION_BAR.getName());
			payTranLs.setStatus(PayStatus.PAYING.getValue());
		}
		//调用方异步通知地址
		payTranLs.setAsynNotify(barPayReq.getNotify_url());
		payTranLs.setRouteId(route.getValue());
		payTranLs.setMerchantName(merchant.getRegisterName());
		payTranLs.setSerialNo(merchant.getMerchantNo());
		payTranLs.setAgentSerialNo(merchant.getAgentSerialNo());
		payTranLs.setTerminalNum(barPayReq.getTerminal_unique_no());
		payTranLs.setTransNum(orderNo);
		payTranLs.setTransTime(new Date());
		payTranLs.setTotalPrice(Float.valueOf(barPayReq.getTotal_fee()));//订单金额
		payTranLs.setTransPrice(Float.valueOf(barPayReq.getTotal_fee()));//实际支付金额
		//判断是否有优惠金额
		if(StringUtils.isNotBlank(barPayReq.getDiscount_fee())){
			payTranLs.setDiscountPrice(Float.valueOf(barPayReq.getDiscount_fee()));
			//实际金额=订单金额-优惠金额
			//此方式相减后会存在精度丢失问题
			//payTranLs.setTransPrice(payTranLs.getTotalPrice()-payTranLs.getDiscountPrice());
			//改用这种方式，bigDecimal专用于处理金额相关的运算
			BigDecimal bdTotalFee = new BigDecimal(barPayReq.getTotal_fee());
			BigDecimal bdDiscountFee = new BigDecimal(barPayReq.getDiscount_fee());
			payTranLs.setTransPrice(bdTotalFee.subtract(bdDiscountFee).floatValue());
		}
		payTranLs.setTransType(TransType.PAY.getValue()); // 交易类型，0:支付，1:退款
		payTranLs.setUser_order_no(barPayReq.getUser_order_no());
		payTranLs.setSubject(barPayReq.getBody());
		payTranLs.setMerchantNo(merchant.getMerchantNo());
		payTranLs.setOrgNo(merchant.getOrgNo());
		payTranLs.setAttach(barPayReq.getAttach());
		payTranLs.setCashierNo(barPayReq.getCashier_no());
		payTranLs.setCouponCode(barPayReq.getCoupon_code());
		payTranLs.setStoreNum(barPayReq.getStore_num());
		payTranLsDao.insert(payTranLs);
		return payTranLs;
	}
	
	/**
	 * 创建二维码支付流水
	 * @Description:
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年6月21日下午6:07:01 
	 * @param qrPayReq
	 * @param merchant
	 * @param subChannel
	 * @return
	 */
	@Override
	public PayTranLs createScanTranLs(ScanPayReqDto scanPayReq, Merchant merchant,
			SubChannel subChannel,Route route) {
		IdWorker iw = new IdWorker();
		PayTranLs payTranLs = new PayTranLs();
		String orderNo = iw.getId() + "";
		if(subChannel==SubChannel.WECHAT_SCAN){
			//微信扫码
			payTranLs.setChannel(PayChannel.WECHAT.getValue());
			payTranLs.setSubChannel(SubChannel.WECHAT_SCAN.getValue());
			payTranLs.setTitle(SubChannel.WECHAT_SCAN.getName());
			payTranLs.setInfo(SubChannel.WECHAT_SCAN.getName());
			payTranLs.setStatus(PayStatus.UNPAY.getValue());
		}else if(subChannel==SubChannel.ALIPAY_SCAN){
			//支付宝扫码
			payTranLs.setChannel(PayChannel.ALIPAY.getValue());
			payTranLs.setSubChannel(SubChannel.ALIPAY_SCAN.getValue());
			payTranLs.setTitle(SubChannel.ALIPAY_SCAN.getName());
			payTranLs.setInfo(SubChannel.ALIPAY_SCAN.getName());
			payTranLs.setStatus(PayStatus.UNPAY.getValue()); 
		}else if(subChannel==SubChannel.UNION_SCAN){
			//银联扫码
			payTranLs.setChannel(PayChannel.UNION.getValue());
			payTranLs.setSubChannel(SubChannel.UNION_SCAN.getValue());
			payTranLs.setTitle(SubChannel.UNION_SCAN.getName());
			payTranLs.setInfo(SubChannel.UNION_SCAN.getName());
			payTranLs.setStatus(PayStatus.UNPAY.getValue());
		}
		//调用方异步通知地址
		payTranLs.setRouteId(route.getValue());
		payTranLs.setAsynNotify(scanPayReq.getNotify_url());
		payTranLs.setMerchantName(merchant.getRegisterName());
		payTranLs.setSerialNo(merchant.getMerchantNo());
		payTranLs.setAgentSerialNo(merchant.getAgentSerialNo());
		payTranLs.setTerminalNum(scanPayReq.getTerminal_unique_no());
		payTranLs.setTransNum(orderNo);
		payTranLs.setTransTime(new Date());
		payTranLs.setTotalPrice(Float.valueOf(scanPayReq.getTotal_fee()));//订单金额
		payTranLs.setTransPrice(Float.valueOf(scanPayReq.getTotal_fee()));//实际支付金额
		//判断是否有优惠金额
		if(StringUtils.isNotBlank(scanPayReq.getDiscount_fee())){
			payTranLs.setDiscountPrice(Float.valueOf(scanPayReq.getDiscount_fee()));
			//实际金额=订单金额-优惠金额
			//payTranLs.setTransPrice(payTranLs.getTotalPrice()-payTranLs.getDiscountPrice());
			//改用这种方式，bigDecimal专用于处理金额相关的运算
			BigDecimal bdTotalFee = new BigDecimal(scanPayReq.getTotal_fee());
			BigDecimal bdDiscountFee = new BigDecimal(scanPayReq.getDiscount_fee());
			payTranLs.setTransPrice(bdTotalFee.subtract(bdDiscountFee).floatValue());
		}
		payTranLs.setTransType(TransType.PAY.getValue()); // 交易类型，0:支付，1:退款
		payTranLs.setUser_order_no(scanPayReq.getUser_order_no());
		payTranLs.setSubject(scanPayReq.getBody());
		payTranLs.setMerchantNo(merchant.getMerchantNo());
		payTranLs.setOrgNo(merchant.getOrgNo());
		payTranLs.setAttach(scanPayReq.getAttach());
		payTranLs.setCashierNo(scanPayReq.getCashier_no());
		payTranLs.setCouponCode(scanPayReq.getCoupon_code());
		payTranLs.setStoreNum(scanPayReq.getStore_num());
		payTranLsDao.insert(payTranLs);
		return payTranLs;
	}
	
	/**
	 * @Description:创建wap支付流水
	 * @author:   Zeng Dongcheng
	 * @Date: 2017年7月20日下午4:09:22 
	 * @Date: 2017年11月11日上午9:45 修改了payTranLs.setStatus处代码，将if中的设置移到if外163行
	 * @param wapPayReq
	 * @param merchant
	 * @param payChannel
	 * @return
	 */
	public PayTranLs createWapTranLs(WapPayReqDto wapPayReq,Merchant merchant,PayChannel payChannel){
		IdWorker iw = new IdWorker();
		PayTranLs payTranLs = new PayTranLs();
		String orderNo = iw.getId() + "";
		if(payChannel==PayChannel.WECHAT){
			//微信wap支付
			payTranLs.setChannel(PayChannel.WECHAT.getValue());
			payTranLs.setSubChannel(SubChannel.WECHAT_WAP.getValue());
			payTranLs.setTitle(SubChannel.WECHAT_WAP.getName());
			payTranLs.setInfo(SubChannel.WECHAT_WAP.getName());
		}else if(payChannel==PayChannel.ALIPAY){
			//支付宝wap
			payTranLs.setChannel(PayChannel.ALIPAY.getValue());
			payTranLs.setSubChannel(SubChannel.ALIPAY_WAP.getValue());
			payTranLs.setTitle(SubChannel.ALIPAY_WAP.getName());
			payTranLs.setInfo(SubChannel.ALIPAY_WAP.getName());
			//调用方页面跳转地址
			payTranLs.setSyncNotify(wapPayReq.getReturn_url());
		}else{
			//银联wap
			payTranLs.setChannel(PayChannel.UNION.getValue());
			payTranLs.setSubChannel(SubChannel.UNION_WAP.getValue());
			payTranLs.setTitle(SubChannel.UNION_WAP.getName());
			payTranLs.setInfo(SubChannel.UNION_WAP.getName());
		}
		payTranLs.setStatus(PayStatus.UNPAY.getValue());
		//调用方异步通知地址
		payTranLs.setAsynNotify(wapPayReq.getNotify_url());
		payTranLs.setMerchantName(merchant.getRegisterName());
		payTranLs.setSerialNo(merchant.getMerchantNo());
		payTranLs.setAgentSerialNo(merchant.getAgentSerialNo());
		payTranLs.setTransNum(orderNo);
		payTranLs.setTransTime(new Date());
		payTranLs.setTotalPrice(Float.valueOf(wapPayReq.getTotal_fee()));//订单金额
		payTranLs.setTransPrice(Float.valueOf(wapPayReq.getTotal_fee()));//实际支付金额
		//判断是否有优惠金额
		if(StringUtils.isNotBlank(wapPayReq.getDiscount_fee())){
			payTranLs.setDiscountPrice(Float.valueOf(wapPayReq.getDiscount_fee()));
			//实际金额=订单金额-优惠金额
			//payTranLs.setTransPrice(payTranLs.getTotalPrice()-payTranLs.getDiscountPrice());
			//改用这种方式，bigDecimal专用于处理金额相关的运算
			BigDecimal bdTotalFee = new BigDecimal(wapPayReq.getTotal_fee());
			BigDecimal bdDiscountFee = new BigDecimal(wapPayReq.getDiscount_fee());
			payTranLs.setTransPrice(bdTotalFee.subtract(bdDiscountFee).floatValue());
		}
		payTranLs.setTransType(TransType.PAY.getValue()); // 交易类型，0:支付，1:退款
		payTranLs.setUser_order_no(wapPayReq.getUser_order_no());
		payTranLs.setSubject(wapPayReq.getBody());
		payTranLs.setMerchantNo(merchant.getMerchantNo());
		payTranLs.setOrgNo(merchant.getOrgNo());
		payTranLs.setAttach(wapPayReq.getAttach());
		payTranLs.setCashierNo(wapPayReq.getCashier_no());
		payTranLsDao.insert(payTranLs);
		return payTranLs;
	}
	
	/**
	 * @Description:创建微信小程序支付流水
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月15日下午4:53:26 
	 * @param appletReq
	 * @param merchant
	 * @return
	 */
	@Override
	public PayTranLs createAppletTranLs(AppletReqDto appletReq,
			Merchant merchant) {
		IdWorker iw = new IdWorker();
		PayTranLs payTranLs = new PayTranLs();
		String orderNo = iw.getId() + "";
		//微信小程序支付
		payTranLs.setChannel(PayChannel.WECHAT.getValue());
		payTranLs.setSubChannel(SubChannel.WECHAT_APPLET.getValue());
		payTranLs.setTitle(SubChannel.WECHAT_APPLET.getName());
		payTranLs.setInfo(SubChannel.WECHAT_APPLET.getName());
		payTranLs.setStatus(PayStatus.UNPAY.getValue());
		//调用方异步通知地址
		payTranLs.setAsynNotify(appletReq.getNotify_url());
		payTranLs.setMerchantName(merchant.getRegisterName());
		payTranLs.setSerialNo(merchant.getMerchantNo());
		payTranLs.setAgentSerialNo(merchant.getAgentSerialNo());
		payTranLs.setTransNum(orderNo);
		payTranLs.setTransTime(new Date());
		payTranLs.setTotalPrice(Float.valueOf(appletReq.getTotal_fee()));//订单金额
		payTranLs.setTransPrice(Float.valueOf(appletReq.getTotal_fee()));//实际支付金额
		payTranLs.setTransType(TransType.PAY.getValue()); // 交易类型，0:支付，1:退款
		payTranLs.setUser_order_no(appletReq.getUser_order_no());
		payTranLs.setSubject(appletReq.getBody());
		payTranLs.setMerchantNo(merchant.getMerchantNo());
		payTranLs.setOrgNo(merchant.getOrgNo());
		payTranLs.setAttach(appletReq.getAttach());
		payTranLsDao.insert(payTranLs);
		return payTranLs;
	}
	
	/**
	 * @Description:创建一码付预付流水
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月6日下午2:33:36 
	 * @param qapPayReq
	 * @param merchant
	 * @return
	 */
	@Override
	public PayTranLs createOneCodeTranLs(WapPayReqDto wapPayReq,
			Merchant merchant) {
		IdWorker iw = new IdWorker();
		PayTranLs payTranLs = new PayTranLs();
		String orderNo = iw.getId() + "";
		payTranLs.setStatus(PayStatus.UNPAY.getValue());
		//调用方页面跳转地址
		payTranLs.setSyncNotify(wapPayReq.getReturn_url());
		//调用方异步通知地址
		payTranLs.setAsynNotify(wapPayReq.getNotify_url());
		payTranLs.setMerchantName(merchant.getRegisterName());
		payTranLs.setSerialNo(merchant.getMerchantNo());
		payTranLs.setAgentSerialNo(merchant.getAgentSerialNo());
		payTranLs.setTransNum(orderNo);
		payTranLs.setTransTime(new Date());
		payTranLs.setTotalPrice(Float.valueOf(wapPayReq.getTotal_fee()));//订单金额
		payTranLs.setTransPrice(Float.valueOf(wapPayReq.getTotal_fee()));//实际支付金额
		//判断是否有优惠金额
		if(StringUtils.isNotBlank(wapPayReq.getDiscount_fee())){
			payTranLs.setDiscountPrice(Float.valueOf(wapPayReq.getDiscount_fee()));
			//实际金额=订单金额-优惠金额
			//payTranLs.setTransPrice(payTranLs.getTotalPrice()-payTranLs.getDiscountPrice());
			//改用这种方式，bigDecimal专用于处理金额相关的运算
			BigDecimal bdTotalFee = new BigDecimal(wapPayReq.getTotal_fee());
			BigDecimal bdDiscountFee = new BigDecimal(wapPayReq.getDiscount_fee());
			payTranLs.setTransPrice(bdTotalFee.subtract(bdDiscountFee).floatValue());
		}
		payTranLs.setTransType(TransType.PAY.getValue()); // 交易类型，0:支付，1:退款
		payTranLs.setUser_order_no(wapPayReq.getUser_order_no());
		payTranLs.setSubject(wapPayReq.getBody());
		payTranLs.setMerchantNo(merchant.getMerchantNo());
		payTranLs.setOrgNo(merchant.getOrgNo());
		payTranLs.setAttach(wapPayReq.getAttach());
		payTranLs.setCashierNo(wapPayReq.getCashier_no());
		payTranLs.setTerminalNum(wapPayReq.getTerminal_unique_no());
		payTranLs.setCouponCode(wapPayReq.getCoupon_code());
		payTranLs.setStoreNum(wapPayReq.getStore_num());
		payTranLsDao.insert(payTranLs);
		return payTranLs;
	}
	
	
	/**
	 * @Description:app支付
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年11月1日上午9:50:20 
	 * @param appPayReq
	 * @param merchant
	 * @param payChannel
	 * @return
	 */
	@Override
	public PayTranLs createAppTranLs(AppPayReqDto appPayReq, Merchant merchant,PayChannel payChannel) {
		IdWorker iw = new IdWorker();
		PayTranLs payTranLs = new PayTranLs();
		String orderNo = iw.getId() + "";
		
		if(payChannel==PayChannel.WECHAT){
			//微信wap支付
			payTranLs.setChannel(PayChannel.WECHAT.getValue());
			payTranLs.setSubChannel(SubChannel.WECHAT_APP.getValue());
			payTranLs.setTitle(SubChannel.WECHAT_APP.getName());
			payTranLs.setInfo(SubChannel.WECHAT_APP.getName());
			
		}else if(payChannel==PayChannel.ALIPAY){
			//支付宝wap
			payTranLs.setChannel(PayChannel.ALIPAY.getValue());
			payTranLs.setSubChannel(SubChannel.ALIPAY_APP.getValue());
			payTranLs.setTitle(SubChannel.ALIPAY_APP.getName());
			payTranLs.setInfo(SubChannel.ALIPAY_APP.getName());
		}
		payTranLs.setStatus(PayStatus.UNPAY.getValue());
		//调用方异步通知地址
		payTranLs.setAsynNotify(appPayReq.getNotify_url());
		payTranLs.setMerchantName(merchant.getRegisterName());
		payTranLs.setSerialNo(merchant.getMerchantNo());
		payTranLs.setAgentSerialNo(merchant.getAgentSerialNo());
		payTranLs.setTransNum(orderNo);
		payTranLs.setTransTime(new Date());
		payTranLs.setTotalPrice(Float.valueOf(appPayReq.getTotal_fee()));//订单金额
		payTranLs.setTransPrice(Float.valueOf(appPayReq.getTotal_fee()));//实际支付金额
		payTranLs.setTransType(TransType.PAY.getValue()); // 交易类型，0:支付，1:退款
		payTranLs.setUser_order_no(appPayReq.getUser_order_no());
		payTranLs.setSubject(appPayReq.getBody());
		payTranLs.setMerchantNo(merchant.getMerchantNo());
		payTranLs.setOrgNo(merchant.getOrgNo());
		payTranLs.setAttach(appPayReq.getAttach());
		payTranLsDao.insert(payTranLs);
		return payTranLs;
	}
	
	
	/**
	 * @Description: 修改一码付流水信息
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月7日上午8:47:29 
	 * @param payTranLs
	 * @param payChannel
	 * @return
	 */
	public PayTranLs updateOneCodeTranLs(PayTranLs payTranLs,PayChannel payChannel){
		if(payChannel==PayChannel.WECHAT){
			//微信wap支付
			payTranLs.setChannel(PayChannel.WECHAT.getValue());
			payTranLs.setSubChannel(SubChannel.WECHAT_WAP.getValue());
			payTranLs.setTitle(SubChannel.WECHAT_WAP.getName());
			payTranLs.setInfo(SubChannel.WECHAT_WAP.getName());
		}else if(payChannel==PayChannel.ALIPAY){
			//支付宝wap
			payTranLs.setChannel(PayChannel.ALIPAY.getValue());
			payTranLs.setSubChannel(SubChannel.ALIPAY_WAP.getValue());
			payTranLs.setTitle(SubChannel.ALIPAY_WAP.getName());
			payTranLs.setInfo(SubChannel.ALIPAY_WAP.getName());
		}else{
			//银联wap
			payTranLs.setChannel(PayChannel.UNION.getValue());
			payTranLs.setSubChannel(SubChannel.UNION_WAP.getValue());
			payTranLs.setTitle(SubChannel.UNION_WAP.getName());
			payTranLs.setInfo(SubChannel.UNION_WAP.getName());
		}
		payTranLsDao.updateByPrimaryKeySelective(payTranLs);
		return payTranLs;
	}

	/**
	 * @Description:修改交易流水
	 * @author:   	Zeng Dongcheng
	 * @Date:       2017年6月22日下午1:37:49 
	 * @param payTranLs
	 * @return
	 */
	@Override
	public PayTranLs updatePayTranLs(PayTranLs payTranLs) {
		payTranLsDao.updateByPrimaryKeySelective(payTranLs);
		return payTranLs;
	}


	/**
	 * @Description:根据商户订单号和商户号查询订单信息
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年7月20日下午4:13:06 
	 * @param userOrderNo
	 * @param merchantNo
	 * @return
	 */
	@Override
	public PayTranLs findTranLsByOrderNo(String userOrderNo, String merchantNo) {
		return payTranLsDao.findTranLsByOrderNo(userOrderNo, merchantNo);
	}
	
	/**
	 * @Description:根据平台支付流水号查询订单信息
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年7月20日下午4:13:41 
	 * @param transNum
	 * @return
	 */
	@Override
	public PayTranLs findTranLsByTransNum(String transNum) {
		return payTranLsDao.findTranLsByTransNum(transNum);
	}

	/**
	 * @Description:修改交易状态
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年7月20日下午4:14:00 
	 * @param id
	 * @param status
	 * @return
	 */
	@Override
	public int updTranStatus(int id, int status) {
		return payTranLsDao.updTranStatus(id, status);
	}

	/**
	 * @Description: 查询时间区间内的待同步的订单
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年7月20日下午4:14:13 
	 * @param intervalTime
	 * @return
	 */
	@Override
	public List<PayTranLs> selectAllSynPayLs(Integer intervalTime) {
		return payTranLsDao.selectAllSynPayLs(intervalTime);
	}

	@Override
	public int updTranLsRefund(Integer id, Integer status, float refundPrice) {
		// TODO Auto-generated method stub
		return payTranLsDao.updTranLsRefund(id, status, refundPrice);
	}

}
