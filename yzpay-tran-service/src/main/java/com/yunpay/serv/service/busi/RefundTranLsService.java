package com.yunpay.serv.service.busi;
import java.util.List;

import com.yunpay.serv.model.PayTranLs;
import com.yunpay.serv.model.RefundTranLs;
import com.yunpay.serv.req.pay.QrRefundReqDto;

/**
 * 退款数据处理接口类
 * 文件名称:     RefundTranLsService.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年8月9日下午4:18:46 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年8月9日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public interface RefundTranLsService {
	
	/**
	 * @Description: 添加退款流水
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月9日下午4:18:55 
	 * @param refundReq
	 * @param payTranLs
	 * @return
	 */
	public RefundTranLs createRefundTranLs(QrRefundReqDto refundReq,PayTranLs payTranLs);
	
	/**
	 * @Description: 修改退款流水状态 
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月9日下午4:19:09 
	 * @param id
	 * @param status
	 * @return
	 */
	public int updTranStatus(int id,int status);
	
	/**
	 * @Description: 修改退款流水
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月9日下午4:19:23 
	 * @param refundTranLs
	 * @return
	 */
	public RefundTranLs updateRefundTranLs(RefundTranLs refundTranLs);
	
	/**
	 * @Description:查询退款流水 
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月9日下午4:19:36 
	 * @param userRefundNo  调用方退款
	 * @param merchantNo    商户号
	 * @return
	 */
	public RefundTranLs findRefundLsByOrderNo(String userRefundNo,String merchantNo);
	
	
	/**
	 * @Description: 查询退款流水
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月9日下午4:20:11 
	 * @param transNum  平台退款流水号
	 * @return
	 */
	public RefundTranLs findRefundLsByTransNum(String transNum);
	
	/**
	 * @Description:查找单位时间内未处理的退款流水 
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月9日下午4:20:48 
	 * @param intervalTime
	 * @return
	 */
	public List<RefundTranLs> selectAllSynRefundLs(Integer intervalTime);
}
