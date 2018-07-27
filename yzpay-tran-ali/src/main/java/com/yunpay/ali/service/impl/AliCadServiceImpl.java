package com.yunpay.ali.service.impl;

import org.springframework.stereotype.Service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.FileItem;
import com.alipay.api.request.AlipayOfflineMaterialImageUploadRequest;
import com.alipay.api.response.AlipayOfflineMaterialImageUploadResponse;
import com.yunpay.ali.config.AliPayConfig;
import com.yunpay.ali.rep.card.UploadImgRep;
import com.yunpay.ali.req.card.UploadImgReq;
import com.yunpay.ali.service.AliCardService;
import com.yunpay.common.utils.ResultEnum.ErrorCode;
import com.yunpay.common.utils.ResultEnum.ResultCode;

/**
 * 
 * 文件名称:    AliCadServiceImpl.java
 * 内容摘要: 	     支付宝卡券相关接口封装类
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年8月21日下午3:35:14 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年8月21日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
@Service("aliCardService")
public class AliCadServiceImpl implements AliCardService{
	/**
	 * @Description:图片资源上传
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月12日上午11:42:37 
	 * @param uploadImgReq
	 * @return
	 */
	@Override
	public UploadImgRep doUploadImage(UploadImgReq uploadImgReq) {
		AlipayClient alipayClient = new DefaultAlipayClient(AliPayConfig.BAR_PAY_API,uploadImgReq.getApp_id(),
				uploadImgReq.getPrivateKey(),uploadImgReq.getFormat(),uploadImgReq.getCharset(),
				uploadImgReq.getAlipayPublicKey(),uploadImgReq.getSign_type());
		AlipayOfflineMaterialImageUploadRequest request = new AlipayOfflineMaterialImageUploadRequest();
		request.setImageType(uploadImgReq.getImage_type());
		request.setImageName(uploadImgReq.getImage_name());
		FileItem ImageContent = new FileItem(uploadImgReq.getImage_path());
		request.setImageContent(ImageContent);
		UploadImgRep rep = null;
		try {
			AlipayOfflineMaterialImageUploadResponse response = alipayClient.execute(request);
			if(response.isSuccess()){
				rep = new UploadImgRep();
				rep.setCode(response.getCode());
				rep.setMsg(response.getMsg());
				rep.setImage_id(response.getImageId());
				rep.setImage_url(response.getImageUrl());
			} else {
				rep = new UploadImgRep(ResultCode.FAIL.getCode(),ResultCode.FAIL.getDes(),
						response.getSubCode(),response.getSubMsg());
			}
		} catch (AlipayApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rep = new UploadImgRep(ResultCode.FAIL.getCode(),ResultCode.FAIL.getDes(),
					ErrorCode.SYSTEM_EXCEPTION.getCode(),ErrorCode.SYSTEM_EXCEPTION.getDes());
		}
		return rep;
	}
}
