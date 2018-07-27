package com.yunpay.serv.service.card.impl;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.yunpay.common.exception.YunPayException;
import com.yunpay.common.utils.FileUtil;
import com.yunpay.common.utils.ResultEnum.ErrorCode;
import com.yunpay.common.utils.ResultEnum.ResultCode;
import com.yunpay.serv.common.TokenService;
import com.yunpay.serv.config.PayConfig;
import com.yunpay.serv.dao.WechatConfigDao;
import com.yunpay.serv.model.WechatConfigKey;
import com.yunpay.serv.rep.Message;
import com.yunpay.serv.service.card.CardCommonService;
import com.yunpay.wx.rep.card.UploadImgRep;
import com.yunpay.wx.service.WechatCardService;

/**
 * 文件名称:    WechatCardServiceImpl.java
 * 内容摘要:    微信卡券业务相关接口(图片上传)
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年9月12日下午1:40:02 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年9月12日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
@Service("wechatCardServ")
public class WechatCardServImpl extends CardCommonService{

	@Autowired
	private WechatConfigDao wechatConfigDao;
	@Autowired
	private TokenService tokenService;
	@Autowired
	private WechatCardService wechatCardService;
	/**
	 * 
	 * @Description:上传卡券图片
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月3日下午2:59:04 
	 * @param merchantNum 商户号
	 * @param file  文件信息
	 * @return
	 */
	@Override
	public Message uploadImg(String merchantNum,CommonsMultipartFile file) {
		//获取商户微信配置
		Message message = null;
		WechatConfigKey wechatConfig = wechatConfigDao.findConfigByMerNo(merchantNum);
		if (wechatConfig == null) {
			return new Message(ErrorCode.ILLEGAL_PAYCONFIG.getCode(),ErrorCode.ILLEGAL_PAYCONFIG.getDes());
		}
		//本地保存路径
		String uploadPath = PayConfig.CARD_IMG_PATH+merchantNum+"/";
		try {
			//将文件保存至本地服务器目录中
			Map<String,String> fileInfo = FileUtil.fileUpload(uploadPath, file);
			//获取微信accessToken
			String accessToken = tokenService.recvAccessToken(wechatConfig.getAppId(), wechatConfig.getAppSecret());
			//图片上传至微信
			UploadImgRep rep = wechatCardService.doUploadImg(fileInfo.get("savePath"), accessToken);
			if(ResultCode.SUCCESS.getCode().equals(rep.getReturn_code())){
				//图片上传成功
				JSONObject ret = new JSONObject();
				ret.put("img_url", rep.getUrl());
				message = new Message(ret);
			}else{
				//图片上传失败
				message = new Message(rep.getErr_code(),rep.getErr_code_des());
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
			message = new Message(ErrorCode.SYSTEM_EXCEPTION.getCode(), ErrorCode.SYSTEM_EXCEPTION.getDes());
		} catch (IOException e) {
			e.printStackTrace();
			message = new Message(ErrorCode.SYSTEM_EXCEPTION.getCode(), ErrorCode.SYSTEM_EXCEPTION.getDes());
		}catch (YunPayException e) {
			e.printStackTrace();
			message = new Message(e.getErrCode(),e.getErrMsg());
		}
		return message;
	}
	
	

}
