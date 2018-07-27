package com.yunpay.serv.service.card.impl;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.yunpay.ali.rep.card.UploadImgRep;
import com.yunpay.ali.req.card.UploadImgReq;
import com.yunpay.ali.service.AliCardService;
import com.yunpay.common.utils.FileUtil;
import com.yunpay.common.utils.ResultEnum.ErrorCode;
import com.yunpay.serv.config.PayConfig;
import com.yunpay.serv.dao.AlipayConfigDao;
import com.yunpay.serv.model.AlipayConfigKey;
import com.yunpay.serv.rep.Message;
import com.yunpay.serv.service.card.CardCommonService;

/**
 * 文件名称:    AliCardServiceImpl.java
 * 内容摘要:    支付宝卡券业务相关接口（图片上传接口）
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年9月12日下午1:40:25 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年9月12日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
@Service("aliCardServ")
public class AliCardServImpl extends CardCommonService{
	
	@Autowired
	private AlipayConfigDao alipayConfigDao;
	@Autowired
	private AliCardService aliCardService;
	
	/**
	 * @Description:支付宝图片资源上传
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月12日下午3:11:17 
	 * @param merchantNum
	 * @param file
	 * @return
	 */
	@Override
	public Message uploadImg(String merchantNum, CommonsMultipartFile file) {
		Message message;
		//获取支付配置信息
		AlipayConfigKey alipayConfig = alipayConfigDao.findConfigByMerNo(merchantNum);
		if(alipayConfig == null){
			return new Message(ErrorCode.ILLEGAL_PAYCONFIG.getCode(),ErrorCode.ILLEGAL_PAYCONFIG.getDes());
		}
		//本地保存路径
		String uploadPath = PayConfig.CARD_IMG_PATH+merchantNum+"/";
		try {
			//将文件保存至本地服务器目录中
			Map<String,String> fileInfo = FileUtil.fileUpload(uploadPath, file);
			String fileType = fileInfo.get("extend").substring(1);
			String fileName = fileInfo.get("saveName").substring(0,fileInfo.get("saveName").lastIndexOf("."));
			UploadImgReq uploadImgReq = new UploadImgReq(alipayConfig.getAppId(),
					alipayConfig.getMerchantPrivateKey(),alipayConfig.getAlipayPublicKey(),
					fileType,fileName,fileInfo.get("savePath"));
			UploadImgRep rep = aliCardService.doUploadImage(uploadImgReq);
			if("10000".equals(rep.getCode())){
				//图片上传成功
				JSONObject ret = new JSONObject();
				ret.put("img_url", rep.getImage_url());
				ret.put("img_id", rep.getImage_id());
				message = new Message(ret);
			}else{
				//图片上传失败
				message = new Message(rep.getSub_code(),rep.getSub_msg());
			}
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			message = new Message(ErrorCode.SYSTEM_EXCEPTION.getCode(), ErrorCode.SYSTEM_EXCEPTION.getDes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			message = new Message(ErrorCode.SYSTEM_EXCEPTION.getCode(), ErrorCode.SYSTEM_EXCEPTION.getDes());
		}
		return message;
	}
	

}
