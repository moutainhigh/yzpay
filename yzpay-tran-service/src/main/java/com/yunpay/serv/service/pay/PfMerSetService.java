package com.yunpay.serv.service.pay;

import org.springframework.stereotype.Service;

import com.yunpay.serv.rep.Message;
import com.yunpay.serv.req.pay.PfMerBbReqDto;
import com.yunpay.serv.req.pay.PfMerBjReqDto;
import com.yunpay.serv.req.pay.PfWikiReqDto;

/**
 * 浦发商户设置接口类
 * 文件名称:     PfMerSetService.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年11月22日上午10:26:25 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年11月22日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
@Service
public interface PfMerSetService {
	
	/**
	 * @Description:商户报件 
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年11月22日上午10:26:56 
	 * @param reqDto
	 * @return
	 */
	public Message doMerBaojian(PfMerBjReqDto reqDto);
	
	
	/**
	 * @Description: 商户报备
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年11月22日上午10:53:45 
	 * @param reqDto
	 * @return
	 */
	public Message doMerBaobei(PfMerBbReqDto reqDto);
	
	/**
	 * @Description: 微信相关设置
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年11月22日上午11:36:53 
	 * @param reqDto
	 * @return
	 */
	public Message doWikiSet(PfWikiReqDto reqDto);
	
}
