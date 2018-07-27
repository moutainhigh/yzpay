package com.yunpay.serv.service.pay.impl;

import java.net.InetAddress;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunpay.common.utils.AmountUtil;
import com.yunpay.common.utils.Log;
import com.yunpay.common.utils.EnumUtil.PayStatus;
import com.yunpay.common.utils.EnumUtil.Route;
import com.yunpay.common.utils.EnumUtil.SubChannel;
import com.yunpay.common.utils.ResultEnum.ErrorCode;
import com.yunpay.pfbank.rep.PfBaseRep;
import com.yunpay.pfbank.rep.PfScanRep;
import com.yunpay.pfbank.req.PfPayReq;
import com.yunpay.pfbank.service.PfPayService;
import com.yunpay.pfbank.util.ConfigUtils;
import com.yunpay.serv.config.PayConfig;
import com.yunpay.serv.dao.PfbankConfigDao;
import com.yunpay.serv.model.Merchant;
import com.yunpay.serv.model.PayTranLs;
import com.yunpay.serv.model.PfbankConfig;
import com.yunpay.serv.rep.BarPayRepDto;
import com.yunpay.serv.rep.Message;
import com.yunpay.serv.rep.ScanPayRepDto;
import com.yunpay.serv.req.pay.BarPayReqDto;
import com.yunpay.serv.req.pay.ScanPayReqDto;
import com.yunpay.serv.service.busi.PayTranLsService;
import com.yunpay.serv.service.pay.PfQrPayService;

/**
 * 文件名称:    PfWchtPayServiceImpl.java
 * 内容摘要:    浦发渠道微信支付接口业务实现类（条码支付、扫码支付）
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年10月11日下午2:13:12 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年10月11日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
@Service("pfWchtPayService")
public class PfWchtPayServiceImpl extends PfQrPayService{
	
	@Autowired
	private PfbankConfigDao pfbankConfigDao;
	@Autowired
	private PayTranLsService payTranLsService;
	@Autowired
	private PfPayService pfPayService;
	
	/**
	 * @Description:浦发银行微信条码支付
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年10月11日下午3:30:23 
	 * @param qrPayReq
	 * @param merchant
	 * @return
	 */
	@Override
	public Message doBarPay(BarPayReqDto barPayReq, Merchant merchant) {
		Message payMsg = null;
		try {
			//获取浦发银行渠道相关配置
			PfbankConfig pfbankConfig = pfbankConfigDao.findConfigByMerNo(barPayReq.getMerchant_num());
			//配置信息非空判断
			if (pfbankConfig == null || StringUtils.isBlank(pfbankConfig.getWxMchId())) {
				return new Message(ErrorCode.ILLEGAL_PAYCONFIG.getCode(),ErrorCode.ILLEGAL_PAYCONFIG.getDes());
			}
			//创建订单流水
			PayTranLs payTranLs = payTranLsService.createBarTranLs(barPayReq, merchant, 
					SubChannel.WECHAT_BAR,Route.PF_BANK);
			//金额转换(元转为分)
			String totalFee = AmountUtil.changeY2F(payTranLs.getTransPrice().toString());
			//获取本地ip
			String ip = InetAddress.getLocalHost().getHostAddress();
			PfPayReq wxBarReq = new PfPayReq(
					SubChannel.WECHAT_BAR,ConfigUtils.AGENT_MERCHANT_NO,
					ip,pfbankConfig.getChannelMerNo(),pfbankConfig.getWxMchId(),payTranLs.getTransNum(),
					PayConfig.PFBANK_PAY_NOTIFY_URL,totalFee,barPayReq.getBody(),barPayReq.getDynamic_id());
			//发起支付请求
			PfBaseRep rep = pfPayService.doPfBarPay(wxBarReq);
			if(rep!=null){
				if("0000".equals(rep.getRespCode())){
					//支付成功
					payTranLs.setStatus(PayStatus.PAYED.getValue());
					payTranLsService.updTranStatus(payTranLs.getId(), PayStatus.PAYED.getValue());
					BarPayRepDto payResData = new BarPayRepDto(payTranLs);
					payMsg = new Message(payResData); 
				}else if("P000".equals(rep.getRespCode())){
					//支付中
					payTranLs.setStatus(PayStatus.PAYING.getValue());
					payTranLsService.updTranStatus(payTranLs.getId(), PayStatus.PAYING.getValue());
					payMsg = new Message(ErrorCode.ORDER_PAYING.getCode(),ErrorCode.ORDER_PAYING.getDes());
				}else if("9997".equals(rep.getRespCode()) || "9998".equals(rep.getRespCode())
						||"9999".equals(rep.getRespCode())){
					//不明确的状态，不修改交易状态但返回支付中
					payMsg = new Message(ErrorCode.ORDER_PAYING.getCode(),ErrorCode.ORDER_PAYING.getDes());
				}else{
					//支付失败，将返回的错误信息封装到结果包中
					payTranLsService.updTranStatus(payTranLs.getId(), PayStatus.FAILPAY.getValue());
					payMsg = new Message(rep.getRespCode(),rep.getRespDesc());
				}
			}else{
				//返回结果为空
				payMsg = new Message(ErrorCode.CHANNEL_PAY_ERROR.getCode(),ErrorCode.CHANNEL_PAY_ERROR.getDes());
				payTranLsService.updTranStatus(payTranLs.getId(), PayStatus.FAILPAY.getValue());
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			Log.error(e.toString());
			return new Message(ErrorCode.SYSTEM_EXCEPTION.getCode(), ErrorCode.SYSTEM_EXCEPTION.getDes());
		}
		return payMsg;
	}

	/**
	 * @Description:浦发银行微信扫码支付
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年10月11日下午3:42:27 
	 * @param qrPayReq
	 * @param merchant
	 * @return
	 */
	@Override
	public Message doScanPay(ScanPayReqDto scanPayReq, Merchant merchant) {
		Message payMsg = null;
		try {
			//获取浦发银行渠道相关配置
			PfbankConfig pfbankConfig = pfbankConfigDao.findConfigByMerNo(scanPayReq.getMerchant_num());
			//配置信息非空判断
			if (pfbankConfig == null || StringUtils.isBlank(pfbankConfig.getWxMchId())) {
				return new Message(ErrorCode.ILLEGAL_PAYCONFIG.getCode(),ErrorCode.ILLEGAL_PAYCONFIG.getDes());
			}
			//创建订单流水
			PayTranLs payTranLs = payTranLsService.createScanTranLs(scanPayReq, merchant, 
					SubChannel.WECHAT_SCAN,Route.PF_BANK);
			//金额转换(元转为分)
			String totalFee = AmountUtil.changeY2F(payTranLs.getTransPrice().toString());
			//获取本地ip
			String ip = InetAddress.getLocalHost().getHostAddress();
			PfPayReq wxScanReq = new PfPayReq(
					SubChannel.WECHAT_SCAN,ConfigUtils.AGENT_MERCHANT_NO,
					ip,pfbankConfig.getChannelMerNo(),pfbankConfig.getWxMchId(),payTranLs.getTransNum(),
					PayConfig.PFBANK_PAY_NOTIFY_URL,totalFee,scanPayReq.getBody(),null);
			//发起支付请求
			PfScanRep rep = pfPayService.doPfScanPay(wxScanReq);
			if(rep!=null){
				if("0000".equals(rep.getRespCode())){
					//订单创建成功
					ScanPayRepDto payResData = new ScanPayRepDto(rep,payTranLs);
					payMsg = new Message(payResData); 
				}else{
					//下单失败，将返回的错误信息封装到结果包中
					payTranLsService.updTranStatus(payTranLs.getId(), PayStatus.FAILPAY.getValue());
					payMsg = new Message(rep.getRespCode(),rep.getRespDesc());
				}
			}else{
				//返回结果为空
				payMsg = new Message(ErrorCode.CHANNEL_PAY_ERROR.getCode(),ErrorCode.CHANNEL_PAY_ERROR.getDes());
				payTranLsService.updTranStatus(payTranLs.getId(), PayStatus.FAILPAY.getValue());
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			Log.error(e.toString());
			return new Message(ErrorCode.SYSTEM_EXCEPTION.getCode(), ErrorCode.SYSTEM_EXCEPTION.getDes());
		}
		return payMsg;
	}

}
