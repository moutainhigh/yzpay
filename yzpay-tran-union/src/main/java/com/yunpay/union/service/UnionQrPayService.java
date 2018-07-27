package com.yunpay.union.service;
import com.yunpay.union.rep.UnionBarRep;
import com.yunpay.union.rep.UnionQueryRep;
import com.yunpay.union.rep.UnionScanRep;
import com.yunpay.union.req.UnionBarReq;
import com.yunpay.union.req.UnionQueryReq;
import com.yunpay.union.req.UnionScanReq;

/**
 * 文件名称:     UnionQrPayService.java
 * 内容摘要:    银联二维码支付业务接口类
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年7月5日下午1:38:12 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年7月5日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public interface UnionQrPayService {
	
	public UnionBarRep doUnionBarPay(UnionBarReq req);
	
	public UnionScanRep doUnionScanPay(UnionScanReq req);
	
	public UnionQueryRep doUnionQuery(UnionQueryReq req);
}
