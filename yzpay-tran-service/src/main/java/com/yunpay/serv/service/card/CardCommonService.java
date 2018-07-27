package com.yunpay.serv.service.card;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.yunpay.serv.rep.Message;

/**
 * 卡券业务公共接口类
 * 文件名称:     CardCommonService.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年9月12日上午11:49:36 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年9月12日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
@Service
public abstract class CardCommonService {
	
	/**
	 * @Description: 卡券上传
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月3日下午2:54:50 
	 * @param merchantNum
	 * @param file
	 * @return
	 */
	public abstract Message uploadImg(String merchantNum,CommonsMultipartFile file);
}
