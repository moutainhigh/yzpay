package com.yunpay.ali.service;

import com.yunpay.ali.rep.card.UploadImgRep;
import com.yunpay.ali.req.card.UploadImgReq;

/**
 * 文件名称:    AliCardService.java
 * 内容摘要:    支付宝卡券接口封装类
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年8月21日下午3:34:40 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年8月21日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public interface AliCardService {
	/**
	 * @Description: 图片资源上传接口
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月12日上午11:31:29 
	 * @param uploadImgReq
	 * @return
	 */
	public UploadImgRep doUploadImage(UploadImgReq uploadImgReq);
}
