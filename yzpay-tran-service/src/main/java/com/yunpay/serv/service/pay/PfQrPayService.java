package com.yunpay.serv.service.pay;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.yunpay.common.utils.AmountUtil;
import com.yunpay.common.utils.DateUtil;
import com.yunpay.common.utils.Log;
import com.yunpay.common.utils.EnumUtil.PayStatus;
import com.yunpay.common.utils.EnumUtil.RefundStatus;
import com.yunpay.common.utils.ResultEnum.ErrorCode;
import com.yunpay.pfbank.rep.PfBaseRep;
import com.yunpay.pfbank.rep.PfQueryRep;
import com.yunpay.pfbank.req.PfQueryReq;
import com.yunpay.pfbank.req.PfRefundReq;
import com.yunpay.pfbank.req.PfReverseReq;
import com.yunpay.pfbank.service.PfPayService;
import com.yunpay.pfbank.util.ConfigUtils;
import com.yunpay.serv.config.PayConfig;
import com.yunpay.serv.dao.PfbankConfigDao;
import com.yunpay.serv.model.Merchant;
import com.yunpay.serv.model.PayTranLs;
import com.yunpay.serv.model.PfbankConfig;
import com.yunpay.serv.model.RefundTranLs;
import com.yunpay.serv.rep.BarPayRepDto;
import com.yunpay.serv.rep.Message;
import com.yunpay.serv.rep.RefundQueryRepDto;
import com.yunpay.serv.rep.RefundRepDto;
import com.yunpay.serv.req.pay.BarPayReqDto;
import com.yunpay.serv.req.pay.QrRefundReqDto;
import com.yunpay.serv.req.pay.ScanPayReqDto;
import com.yunpay.serv.service.busi.PayTranLsService;
import com.yunpay.serv.service.busi.RefundTranLsService;

/**
 * 浦发银行渠道二维码支付服务接口类
 * 文件名称:     PfPayService.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年10月11日下午2:06:11 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年10月11日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public abstract class PfQrPayService {
	
	@Autowired
	private PfbankConfigDao pfbankConfigDao;
	@Autowired
	private PayTranLsService payTranLsService;
	@Autowired
	private PfPayService pfPayService;
	@Autowired
	private RefundTranLsService refundTranLsService;
	
	/**
	 * @Description: 条码支付
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年10月11日下午2:42:46 
	 * @param qrPayReq
	 * @param merchant
	 * @return
	 */
	public abstract Message doBarPay(BarPayReqDto barPayReq,Merchant merchant);
	
	/**
	 * @Description:扫码支付 
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年10月11日下午2:42:58 
	 * @param qrPayReq
	 * @param merchant
	 * @return
	 */
	public abstract Message doScanPay(ScanPayReqDto scanPayReq,Merchant merchant);
	
	/**
	 * @Description: 订单查询
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年10月11日下午2:43:08 
	 * @param payTranLs
	 * @return
	 */
	public Message doOrderQuery(PayTranLs payTranLs){
		Message payMsg = null;
		try {
			//获取浦发银行渠道相关配置
			PfbankConfig pfbankConfig = pfbankConfigDao.findConfigByMerNo(payTranLs.getMerchantNo());
			//配置信息非空判断
			if (pfbankConfig == null || StringUtils.isBlank(pfbankConfig.getWxMchId())) {
				return new Message(ErrorCode.ILLEGAL_PAYCONFIG.getCode(),ErrorCode.ILLEGAL_PAYCONFIG.getDes());
			}
			String orderDate = DateUtil.formatDate(payTranLs.getTransTime(),"yyyyMMdd");
			PfQueryReq queryReq = new PfQueryReq(ConfigUtils.AGENT_MERCHANT_NO,pfbankConfig.getChannelMerNo(),
					 orderDate,payTranLs.getTransNum());
			PfQueryRep rep = pfPayService.doPfOrderQuery(queryReq);
			if(rep!=null){
				if("0000".equals(rep.getRespCode())){
					if("0000".equals(rep.getOrigRespCode())){
						//支付成功
						payTranLs.setStatus(PayStatus.PAYED.getValue());
						payTranLs.setTrade_no(rep.getTransactionId());
						payTranLs.setOrderId(rep.getOrderId());
						payTranLsService.updatePayTranLs(payTranLs);
						BarPayRepDto payResData = new BarPayRepDto(payTranLs);
						payMsg = new Message(payResData); 
					}else if("P000".equals(rep.getOrigRespCode())){
						//支付中
						payTranLs.setStatus(PayStatus.PAYING.getValue());
						payTranLsService.updTranStatus(payTranLs.getId(), PayStatus.PAYING.getValue());
						BarPayRepDto payResData = new BarPayRepDto(payTranLs);
						payMsg = new Message(payResData); 
					}else if("9997".equals(rep.getRespCode()) || "9998".equals(rep.getRespCode())
							||"9999".equals(rep.getRespCode())){
						//不明确的状态，不修改交易状态但返回支付中
						payMsg = new Message(ErrorCode.ORDER_PAYING.getCode(),ErrorCode.ORDER_PAYING.getDes());
					}else{
						//直接返回订单原始状态
						payMsg = new Message(rep.getOrigRespCode(),rep.getOrigRespDesc());
					}
				}else{
					//查询失败，将返回的错误信息封装到结果包中
					payMsg = new Message(rep.getRespCode(),rep.getRespDesc());
				}
			}else{
				//返回结果为空
				payMsg = new Message(ErrorCode.CHANNEL_PAY_ERROR.getCode(),ErrorCode.CHANNEL_PAY_ERROR.getDes());
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			Log.error(e.toString());
			return new Message(ErrorCode.SYSTEM_EXCEPTION.getCode(), ErrorCode.SYSTEM_EXCEPTION.getDes());
		}
		return payMsg;
	}
	
	/**
	 * @Description: 订单撤销
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年10月12日下午2:43:22 
	 * @param payTranLs
	 * @return
	 */
	public Message doOrderReverse(PayTranLs payTranLs){
		Message payMsg = null;
		try {
			//获取浦发银行渠道相关配置
			PfbankConfig pfbankConfig = pfbankConfigDao.findConfigByMerNo(payTranLs.getMerchantNo());
			//配置信息非空判断
			if (pfbankConfig == null || StringUtils.isBlank(pfbankConfig.getWxMchId())) {
				return new Message(ErrorCode.ILLEGAL_PAYCONFIG.getCode(),ErrorCode.ILLEGAL_PAYCONFIG.getDes());
			}
			//订单撤销日期
			String orderDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
			//订单交易金额(转为分)
			String reverseAmt = AmountUtil.changeY2F(payTranLs.getTransPrice().toString());
			PfReverseReq reverseReq = new PfReverseReq(ConfigUtils.AGENT_MERCHANT_NO,pfbankConfig.getChannelMerNo(),
					 orderDate,payTranLs.getUser_order_no(),payTranLs.getTransNum(),reverseAmt,PayConfig.PFBANK_PAY_NOTIFY_URL);
			PfBaseRep rep = pfPayService.doPfOrderReverse(reverseReq);
			if(rep!=null){
				if("0000".equals(rep.getRespCode())){
					Log.info("订单撤销成功！");
					//微信返回订单状态
					payTranLs.setStatus(PayStatus.CANCEL.getValue());
					//修改订单状态
					payTranLsService.updTranStatus(payTranLs.getId(), PayStatus.CANCEL.getValue());
					//封装平台返回参数
					BarPayRepDto queryResData = new BarPayRepDto(payTranLs);
					payMsg = new Message(queryResData); 
				}else{
					//撤销失败，将返回的错误信息封装到结果包中
					payMsg = new Message(rep.getRespCode(),rep.getRespDesc());
				}
			}else{
				//返回结果为空
				payMsg = new Message(ErrorCode.CHANNEL_PAY_ERROR.getCode(),ErrorCode.CHANNEL_PAY_ERROR.getDes());
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			Log.error(e.toString());
			return new Message(ErrorCode.SYSTEM_EXCEPTION.getCode(), ErrorCode.SYSTEM_EXCEPTION.getDes());
		}
		return payMsg;
	}
	
	
	/**
	 * @Description: 订单退款申请
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年10月12日下午3:22:52 
	 * @param qrRefundReq
	 * @return
	 */
	public Message doOrderRefund(QrRefundReqDto qrRefundReq,PayTranLs payTranLs){
		Message payMsg = null;
		try {
			//退款金额(转换为分)
			int refundFee = Integer.valueOf(AmountUtil.changeY2F(qrRefundReq.getRefund_fee()));
			//原订单总额(转换为分)
			int transFee = Integer.valueOf(AmountUtil.changeY2F(payTranLs.getTransPrice().toString()));
			//已退款金额
			int refundedFee = Integer.valueOf(AmountUtil.changeY2F(payTranLs.getRefundPrice().toString()));
			//如果当前退款金额+已退款金额>实际交易金额，直接返回错误提示
			if((refundFee+refundedFee)>transFee){
				return new Message(ErrorCode.ORDER_REFUND_FEE.getCode(),ErrorCode.ORDER_REFUND_FEE.getDes());
			}
			//获取浦发银行渠道相关配置
			PfbankConfig pfbankConfig = pfbankConfigDao.findConfigByMerNo(payTranLs.getMerchantNo());
			//配置信息非空判断
			if (pfbankConfig == null || StringUtils.isBlank(pfbankConfig.getWxMchId())) {
				return new Message(ErrorCode.ILLEGAL_PAYCONFIG.getCode(),ErrorCode.ILLEGAL_PAYCONFIG.getDes());
			}
			//验证退款单号是否存在
			RefundTranLs refundTranLs = refundTranLsService.findRefundLsByOrderNo(
					qrRefundReq.getUser_refund_no(), qrRefundReq.getMerchant_num()); 
			if(refundTranLs!=null){
				return new Message(ErrorCode.REFUND_ORDER_EXIST.getCode(), ErrorCode.REFUND_ORDER_EXIST.getDes());
			}
			//订单退货日期
			String orderDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
			//原订单交易日期
			String origOrderDate = DateUtil.formatDate(payTranLs.getTransTime(),"yyyyMMdd");
			//创建退款流水
			refundTranLs = refundTranLsService.createRefundTranLs(qrRefundReq,payTranLs);
			//构造退款交易请求参数
			PfRefundReq refundReq = new PfRefundReq(ConfigUtils.AGENT_MERCHANT_NO,pfbankConfig.getChannelMerNo(),
					orderDate,refundTranLs.getTransNum(),origOrderDate,payTranLs.getTransNum(),
					refundFee+"",qrRefundReq.getRefund_desc(),PayConfig.PFBANK_PAY_NOTIFY_URL);
			PfBaseRep rep = pfPayService.doPfOrderRefund(refundReq);
			if(rep!=null){
				if("0000".equals(rep.getRespCode())){
					Log.info("浦发渠道退款申请成功！");
					refundTranLs.setStatus(RefundStatus.REFUNDING.getValue());
					refundTranLsService.updTranStatus(refundTranLs.getId(), RefundStatus.REFUNDING.getValue());
					RefundRepDto refundRep = new RefundRepDto(refundTranLs);
					//update by 2017-09-18 19:39
					//增加对原订单的退款信息进行修改(暂时认为退款申请成功就是退款成功，此流程后续需优化)
					if((refundFee+refundedFee)==transFee){
						//如果退款累计金额==付款金额，退款金额累加并修改状态为已退款
						payTranLsService.updTranLsRefund(payTranLs.getId(),PayStatus.REFUNDED.getValue(),Float.valueOf(qrRefundReq.getRefund_fee()));
					}else{
						//累加退款金额
						payTranLsService.updTranLsRefund(payTranLs.getId(),payTranLs.getStatus(),Float.valueOf(qrRefundReq.getRefund_fee()));
					}
					payMsg = new Message(refundRep);
				}else if("9997".equals(rep.getRespCode()) || "9998".equals(rep.getRespCode())
						||"9999".equals(rep.getRespCode())){
					//不明确的状态，不修改交易状态
					payMsg = new Message(ErrorCode.ORDER_RESULT_NOT.getCode(),ErrorCode.ORDER_RESULT_NOT.getDes());
				}else{
					//退款失败，将返回的错误信息封装到结果包中
					Log.info("浦发退款申请失败，err_code_desc="+rep.getRespDesc());
					refundTranLsService.updTranStatus(refundTranLs.getId(), RefundStatus.FAILREFUND.getValue());
					payMsg = new Message(rep.getRespCode(),rep.getRespDesc());
				}
			}else{
				//返回结果为空
				payMsg = new Message(ErrorCode.CHANNEL_PAY_ERROR.getCode(),ErrorCode.CHANNEL_PAY_ERROR.getDes());
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			Log.error(e.toString());
			return new Message(ErrorCode.SYSTEM_EXCEPTION.getCode(), ErrorCode.SYSTEM_EXCEPTION.getDes());
		}
		return payMsg;
	}
	
	/**
	 * @Description:退款查询 
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年10月12日上午11:23:47 
	 * @param refundTranLs
	 * @return
	 */
	public  Message doRefundQuery(RefundTranLs refundTranLs){
		Message payMsg = null;
		try {
			//获取浦发银行渠道相关配置
			PfbankConfig pfbankConfig = pfbankConfigDao.findConfigByMerNo(refundTranLs.getMerchantNo());
			//配置信息非空判断
			if (pfbankConfig == null || StringUtils.isBlank(pfbankConfig.getWxMchId())) {
				return new Message(ErrorCode.ILLEGAL_PAYCONFIG.getCode(),ErrorCode.ILLEGAL_PAYCONFIG.getDes());
			}
			String orderDate = DateUtil.formatDate(refundTranLs.getTransTime(),"yyyyMMdd");
			PfQueryReq queryReq = new PfQueryReq(ConfigUtils.AGENT_MERCHANT_NO,pfbankConfig.getChannelMerNo(),
					 orderDate,refundTranLs.getTransNum());
			PfQueryRep rep = pfPayService.doPfOrderQuery(queryReq);
			if(rep!=null){
				if("0000".equals(rep.getRespCode())){
					if("0000".equals(rep.getOrigRespCode())){
						//退款成功
						refundTranLs.setStatus(RefundStatus.REFUNDED.getValue());
						refundTranLs.setTradeNo(rep.getTransactionId());
						refundTranLsService.updateRefundTranLs(refundTranLs);
						RefundQueryRepDto fefundQueryRep = new RefundQueryRepDto(refundTranLs);
						payMsg = new Message(fefundQueryRep);
					}else if("P000".equals(rep.getOrigRespCode())){
						//退款中
						refundTranLs.setStatus(RefundStatus.REFUNDING.getValue());
						refundTranLsService.updTranStatus(refundTranLs.getId(), RefundStatus.REFUNDING.getValue());
						RefundQueryRepDto fefundQueryRep = new RefundQueryRepDto(refundTranLs);
						payMsg = new Message(fefundQueryRep);
					}else if("9997".equals(rep.getRespCode()) || "9998".equals(rep.getRespCode())
							||"9999".equals(rep.getRespCode())){
						//不明确的状态，不修改交易状态
						payMsg = new Message(ErrorCode.ORDER_REFUNDING.getCode(),ErrorCode.ORDER_REFUNDING.getDes());
					}else{
						//直接返回订单原始状态
						payMsg = new Message(rep.getOrigRespCode(),rep.getOrigRespDesc());
					}
				}else{
					//查询失败，将返回的错误信息封装到结果包中
					payMsg = new Message(rep.getRespCode(),rep.getRespDesc());
				}
			}else{
				//返回结果为空
				payMsg = new Message(ErrorCode.CHANNEL_PAY_ERROR.getCode(),ErrorCode.CHANNEL_PAY_ERROR.getDes());
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			Log.error(e.toString());
			return new Message(ErrorCode.SYSTEM_EXCEPTION.getCode(), ErrorCode.SYSTEM_EXCEPTION.getDes());
		}
		return payMsg;
	}
	
}
