package com.yunpay.serv.service.pay.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yunpay.activemq.busi.CouponNotifyService;
import com.yunpay.ali.utils.AlipayUtil;
import com.yunpay.common.utils.Log;
import com.yunpay.common.utils.EnumUtil.PayStatus;
import com.yunpay.common.utils.ResultEnum.ResultCode;
import com.yunpay.serv.dao.AlipayConfigDao;
import com.yunpay.serv.model.AlipayConfigKey;
import com.yunpay.serv.model.PayTranLs;
import com.yunpay.serv.service.busi.PayTranLsService;
import com.yunpay.serv.service.pay.PayNotifyService;
import com.yunpay.union.utils.UnionUtil;
import com.yunpay.wx.rep.pay.WechatNotifyRep;
import com.yunpay.wx.service.WechatQrPayService;

/**
 * 文件名称:    PayNotifyServiceImpl.java
 * 内容摘要:    支付业务回调通知处理类（微信、支付宝、银联等回调通知的业务处理）
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年8月28日上午9:46:05 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年8月28日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
@Service("payNotifyService")
public class PayNotifyServiceImpl implements PayNotifyService{
	
	@Autowired
	private PayTranLsService payTranLsService;
	@Autowired
	private WechatQrPayService wechatQrPayService;
	@Autowired
	private AlipayConfigDao alipayConfigDao;
	@Autowired
	private CouponNotifyService couponNotifyService;
	

	/**
	 * @Description:微信支付异步接收通知业务处理
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年6月27日下午5:52:46 
	 * @param recvInfo
	 * @return
	 */
	@Override
	public boolean wechatScanNotify(String recvInfo) {
		WechatNotifyRep notifyRep = wechatQrPayService.analysisWechatNotify(recvInfo);
		if(notifyRep==null){
			return false;
		}
		if(ResultCode.FAIL.getCode().equals(notifyRep.getReturn_code())){
			return false;
		}
		//查询订单信息
		PayTranLs payTranLs = payTranLsService.findTranLsByTransNum(notifyRep.getOut_trade_no());
		if(payTranLs!=null){
			//只有在原始订单状态为支付中或未支付时，才修改订单支付状态
			//如果为其它状态说明已有其它地方对该订单进行处理，此处则不对订单进行状态处理
			if(PayStatus.PAYING.getValue() == payTranLs.getStatus() || 
					PayStatus.UNPAY.getValue()== payTranLs.getStatus()){
				if(ResultCode.SUCCESS.getCode().equals(notifyRep.getResult_code())){
//					try {
//						payTranLs.setTransPrice(Float.valueOf(AmountUtil.changeF2Y(notifyRep.getCash_fee())));
//					}  catch (Exception e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//						Log.error("cash_fee 转换异常");
//					}
					payTranLs.setStatus(PayStatus.PAYED.getValue());
					payTranLs.setOpenid(notifyRep.getOpenid());
					payTranLs.setTransCardNum(notifyRep.getOpenid());
					payTranLs.setTrade_no(notifyRep.getTransaction_id());
					//支付成功，修改订单信息
					//如果存在卡券，则发送核销通知到activemq
					if(StringUtils.isNotBlank(payTranLs.getCouponCode()) 
							&& StringUtils.isNotBlank(payTranLs.getAsynNotify())
							&& payTranLs.getNotifyStatus()==0){
						//发送卡券核销通知到消息队列activemq
						if(couponNotifyService.sendCouponNotify(payTranLs.getAsynNotify())){
							payTranLs.setNotifyStatus(1);
						}
					}
					payTranLsService.updatePayTranLs(payTranLs);
				}else{
					//支付失败,只修改订单状态为失败
					payTranLsService.updTranStatus(payTranLs.getId(), PayStatus.FAILPAY.getValue());
				}
			}
		}else{
			//订单为空，记录异常
			Log.error("微信异步通知结果处理异常==订单不存在");
			return false;
		}
		return true;
	}
	
	/**
	 * @Description: 微信退款回调通知
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月8日下午3:07:16 
	 * @param recvInfo
	 * @return
	 */
	public boolean wechatRefundNotify(String recvInfo) {
		return true;
	}

	/**
	 * @Description:支付宝支付回调通知业务处理
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年7月4日上午11:28:56 
	 * @param recvParam
	 * @return
	 */
	@Override
	public boolean alipayScanNotify(Map<String, String> recvParam) {
		PayTranLs payTranLs = payTranLsService.findTranLsByTransNum(recvParam.get("out_trade_no"));
		if(payTranLs==null){
			Log.info("支付宝异步通知订单不存在！");
			return false;
		}
		AlipayConfigKey alipayConfig = alipayConfigDao.findConfigByMerNo(payTranLs.getMerchantNo());
		if(AlipayUtil.getRSASignVerify(recvParam, recvParam.get("sign"), alipayConfig.getAlipayPublicKey())){
			Log.info("支付宝异步通知结果验签成功！");
			//只有在原始订单状态为支付中时，才修改订单支付状态
			//如果为其它状态说明已有其它地方对该订单进行处理，此处则不对订单进行状态处理
			if(PayStatus.PAYING.getValue() == payTranLs.getStatus() ||
					PayStatus.UNPAY.getValue()== payTranLs.getStatus()){
				String trade_status = recvParam.get("trade_status");
				if("TRADE_SUCCESS".equals(trade_status) 
						|| "TRADE_FINISHED".equals(trade_status)){
					payTranLs.setStatus(PayStatus.PAYED.getValue());
					// 买家支付宝账号
					payTranLs.setTransCardNum(recvParam.get("buyer_logon_id")); 
					// 实际交易金额
					//payTranLs.setTransPrice(Float.valueOf(recvParam.get("receipt_amount")));
					//买家id
					payTranLs.setOpenid(recvParam.get("buyer_id"));
					//支付宝交易号
					payTranLs.setTrade_no(recvParam.get("trade_no"));
					//如果存在卡券，则发送核销通知到activemq
					if(StringUtils.isNotBlank(payTranLs.getCouponCode()) 
							&& StringUtils.isNotBlank(payTranLs.getAsynNotify())
							&& payTranLs.getNotifyStatus()==0){
						//发送卡券核销通知到消息队列activemq
						if(couponNotifyService.sendCouponNotify(payTranLs.getAsynNotify())){
							payTranLs.setNotifyStatus(1);
						}
					}
					//支付成功，修改订单信息
					payTranLsService.updatePayTranLs(payTranLs);
				}else if("WAIT_BUYER_PAY".equals(trade_status)){
					//支付中
					payTranLsService.updTranStatus(payTranLs.getId(), PayStatus.PAYING.getValue());	
					
				}else{
					//支付失败,只修改订单状态为失败
					payTranLsService.updTranStatus(payTranLs.getId(), PayStatus.FAILPAY.getValue());
				}
			}
		}else{
			Log.error("支付宝异步通知结果验签失败！");
			return false;
		}
		return true;
	}

	/**
	 * @Description:银联二维码支付回调通知业务处理
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年7月7日上午9:09:47 
	 * @param recvParam
	 * @return
	 */
	@Override
	public boolean unionScanNotify(Map<String, String> recvParam) {
		Map<String, String> valideData = null;
		Iterator<Entry<String, String>> it = recvParam.entrySet().iterator();
		valideData = new HashMap<String, String>(recvParam.size());
		while (it.hasNext()) {
			Entry<String, String> e = it.next();
			String key = (String) e.getKey();
			String value = (String) e.getValue();
			valideData.put(key, value);
		}
		//重要！验证签名前不要修改reqParam中的键值对的内容，否则会验签不过
		if (UnionUtil.validate(valideData, "UTF-8")) {
			Log.info("银联异步通知结果验签成功！");
			//根据通知返回的orderId查询交易流水
			PayTranLs payTranLs = payTranLsService.findTranLsByTransNum(recvParam.get("orderId"));
			if(PayStatus.PAYING.getValue() == payTranLs.getStatus() ||
					PayStatus.UNPAY.getValue()== payTranLs.getStatus()){
				String respCode = valideData.get("respCode");
				if("00".equals(respCode) || "A6".equals(respCode)){
					//交易成功
					payTranLs.setTrade_no(recvParam.get("queryId"));
					//账号
					payTranLs.setTransCardNum(recvParam.get("accNo"));
					//银联返回的系统跟踪号
					payTranLs.setOpenid(recvParam.get("traceNo"));
					payTranLs.setStatus(PayStatus.PAYED.getValue());
					//支付成功，修改订单信息
					payTranLsService.updatePayTranLs(payTranLs);
				}else{
					//支付失败,只修改订单状态为失败
					payTranLsService.updTranStatus(payTranLs.getId(), PayStatus.FAILPAY.getValue());
				}
			}
		} else {
			//验签失败，需解决验签问题
			Log.error("银联异步通知结果验签失败！");
			return false;
		}
		return true;
	}

}
