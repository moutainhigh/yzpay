package com.yunpay.serv.service.busi.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunpay.common.utils.IdWorker;
import com.yunpay.common.utils.EnumUtil.RefundStatus;
import com.yunpay.serv.dao.RefundTranLsDao;
import com.yunpay.serv.model.PayTranLs;
import com.yunpay.serv.model.RefundTranLs;
import com.yunpay.serv.req.pay.QrRefundReqDto;
import com.yunpay.serv.service.busi.RefundTranLsService;

/**
 * 退款数据处理类
 * 文件名称:     RefundTranLsServiceImpl.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年8月9日下午4:22:02 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年8月9日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
@Service
public class RefundTranLsServiceImpl implements RefundTranLsService{
	
	@Autowired
	private RefundTranLsDao refundTranLsDao;

	/**
	 * @Description: 添加退款流水
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月9日下午4:18:55 
	 * @param refundReq
	 * @param payTranLs
	 * @return
	 */
	@Override
	public RefundTranLs createRefundTranLs(QrRefundReqDto refundReq,
			PayTranLs payTranLs) {
		IdWorker iw = new IdWorker();
		String transNum = iw.getId() + "";
		RefundTranLs refundTranLs = new RefundTranLs();
		refundTranLs.setChannel(payTranLs.getChannel());
		refundTranLs.setMerchantNo(payTranLs.getMerchantNo());
		refundTranLs.setMerchantName(payTranLs.getMerchantName());
		refundTranLs.setUserRefundNo(refundReq.getUser_refund_no());
		refundTranLs.setOldUserOrderNo(payTranLs.getUser_order_no());
		refundTranLs.setTransNum(transNum);
		refundTranLs.setOldTransNum(payTranLs.getTransNum());
		refundTranLs.setTransTime(new Date());
		refundTranLs.setTotalPrice(payTranLs.getTransPrice());//原订单实付金额
		refundTranLs.setRefundFee(Float.valueOf(refundReq.getRefund_fee()));
		refundTranLs.setOrgNo(payTranLs.getOrgNo());
		refundTranLs.setStatus(RefundStatus.UNREFUND.getValue());
		refundTranLs.setRouteId(payTranLs.getRouteId());
		refundTranLsDao.insert(refundTranLs);
		return refundTranLs;
	}

	/**
	 * @Description: 修改退款流水状态 
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月9日下午4:19:09 
	 * @param id
	 * @param status
	 * @return
	 */
	@Override
	public int updTranStatus(int id, int status) {
		return refundTranLsDao.updRefundTranStatus(id, status);
	}

	/**
	 * @Description: 修改退款流水
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月9日下午4:19:23 
	 * @param refundTranLs
	 * @return
	 */
	@Override
	public RefundTranLs updateRefundTranLs(RefundTranLs refundTranLs) {
		refundTranLsDao.updateByPrimaryKeySelective(refundTranLs);
		return refundTranLs;
	}

	/**
	 * @Description:查询退款流水 
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月9日下午4:19:36 
	 * @param userRefundNo  调用方退款
	 * @param merchantNo    商户号
	 * @return
	 */
	@Override
	public RefundTranLs findRefundLsByOrderNo(String userRefundNo,
			String merchantNo) {
		return refundTranLsDao.findLsByUserRefundNo(userRefundNo, merchantNo);
	}

	/**
	 * @Description: 查询退款流水
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月9日下午4:20:11 
	 * @param transNum  平台退款流水号
	 * @return
	 */
	@Override
	public RefundTranLs findRefundLsByTransNum(String transNum) {
		return refundTranLsDao.findRefundLsByTransNum(transNum);
	}

	/**
	 * @Description:查找单位时间内未处理的退款流水 
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月9日下午4:20:48 
	 * @param intervalTime
	 * @return
	 */
	@Override
	public List<RefundTranLs> selectAllSynRefundLs(Integer intervalTime) {
		return refundTranLsDao.selectAllSynRefundLs(intervalTime);
	}
	
	

}
