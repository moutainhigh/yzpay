package com.yunpay.pfbank.service;
import com.yunpay.pfbank.rep.PfBaseRep;
import com.yunpay.pfbank.rep.PfMerchAddRep;
import com.yunpay.pfbank.rep.PfMerchBbQryRep;
import com.yunpay.pfbank.rep.PfMerchBbRep;
import com.yunpay.pfbank.rep.PfMerchBjQryRep;
import com.yunpay.pfbank.rep.PfQueryRep;
import com.yunpay.pfbank.rep.PfScanRep;
import com.yunpay.pfbank.rep.PfWikiConfigRep;
import com.yunpay.pfbank.rep.PfWikiPayRep;
import com.yunpay.pfbank.req.PfMerchBbQryReq;
import com.yunpay.pfbank.req.PfMerchBjQryReq;
import com.yunpay.pfbank.req.PfMerchBjReq;
import com.yunpay.pfbank.req.PfMerchBbReq;
import com.yunpay.pfbank.req.PfQueryReq;
import com.yunpay.pfbank.req.PfRefundReq;
import com.yunpay.pfbank.req.PfPayReq;
import com.yunpay.pfbank.req.PfReverseReq;
import com.yunpay.pfbank.req.PfWikiConfigReq;
import com.yunpay.pfbank.req.PfWikiPayReq;


/**
 * 浦发银行支付业务接口类
 * 文件名称:     PfPayService.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年10月9日下午1:42:07 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年10月9日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public interface PfPayService {
	
	/**
	 * 浦发商户报件
	 * @Description: 
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年11月17日上午10:13:31 
	 * @param pfMerchAddReq
	 * @return
	 */
	public PfMerchAddRep doPfMerchBj(PfMerchBjReq pfMerchAddReq);
	
	/**
	 * 商户报件查询
	 * @Description: 
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年11月21日上午11:58:00 
	 * @param pfMerchBjQryReq
	 * @return
	 */
	public PfMerchBjQryRep doPfMerchBjQry(PfMerchBjQryReq pfMerchBjQryReq);
	
	/**
	 * 浦发商户报备
	 * @Description: 
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年11月20日下午5:29:18 
	 * @param pfMerchBbReq
	 * @return
	 */
	public PfMerchBbRep doPfMerchBb(PfMerchBbReq pfMerchBbReq);
	
	
	/**
	 * 商户报备查询
	 * @Description: 
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年11月21日下午4:17:31 
	 * @param pfMerchBbQryReq
	 * @return
	 */
	public PfMerchBbQryRep doPfMerchBbQry(PfMerchBbQryReq pfMerchBbQryReq);
	
	/**
	 * @Description: 条码支付
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年10月9日下午3:20:06 
	 * @param barReq
	 * @return
	 */
	public PfBaseRep doPfBarPay(PfPayReq barReq);
	
	/**
	 * @Description:扫码支付 
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年10月9日下午3:20:19 
	 * @param scanReq
	 * @return
	 */
	public PfScanRep doPfScanPay(PfPayReq scanReq);
	
	/**
	 * @Description: 浦发微信公众号配置
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年10月30日上午9:28:36 
	 * @param pfWikiConfigReq
	 * @return
	 */
	public PfWikiConfigRep doPfWikiConfig(PfWikiConfigReq pfWikiConfigReq);
	
	/**
	 * 公众号配置查询
	 * @Description: 
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年10月30日上午10:24:10 
	 * @param pfWikiConfigReq
	 * @return
	 */
	public PfWikiConfigRep doPfWikiConfigQuery(PfWikiConfigReq pfWikiConfigReq);
		
	/**
	 * 微信公众号支付
	 * @Description: 
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年11月21日上午9:48:12 
	 * @param pfWikiPayReq
	 * @return
	 */
	public PfWikiPayRep doPfWikiPay(PfWikiPayReq pfWikiPayReq);
	
	/**
	 * @Description: 订单查询
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年10月11日下午7:57:06 
	 * @param queryReq
	 * @return
	 */
	public PfQueryRep doPfOrderQuery(PfQueryReq queryReq);
	
	/**
	 * @Description: 退货
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年10月12日上午9:41:17 
	 * @param refundReq
	 * @return
	 */
	public PfBaseRep doPfOrderRefund(PfRefundReq refundReq);
	
	/**
	 * @Description: 订单撤销
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年10月12日上午10:00:47 
	 * @param reverseReq
	 * @return
	 */
	public PfBaseRep doPfOrderReverse(PfReverseReq reverseReq);
	
	
}
