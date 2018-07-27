package com.yunpay.wx.service.impl;

import java.io.File;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.WritableResource;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.yunpay.common.utils.HttpUtil;
import com.yunpay.common.utils.Log;
import com.yunpay.wx.config.WechatCardConfig;
import com.yunpay.wx.rep.card.AccessTokenRep;
import com.yunpay.wx.rep.card.AppBaseRep;
import com.yunpay.wx.rep.card.CardConsumeRep;
import com.yunpay.wx.rep.card.CreateQrCodeRep;
import com.yunpay.wx.rep.card.CreateCardRep;
import com.yunpay.wx.rep.card.QrCardCodeRep;
import com.yunpay.wx.rep.card.UpdCardRep;
import com.yunpay.wx.rep.card.UploadImgRep;
import com.yunpay.wx.req.card.CreateQrCodeReq;
import com.yunpay.wx.req.card.CardReq;
import com.yunpay.wx.req.card.QrCardCodeReq;
import com.yunpay.wx.service.WechatCardService;

/**
 * 文件名称:    WechatCardServiceImpl.java
 * 内容摘要:    微信卡券相关接口封装类，封装了调用微信接口获取access_token、卡券创建、修改、查询、核销、删除等相关的方法
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年7月31日上午11:22:55 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年7月31日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
@Service("wechatCardService")
public class WechatCardServiceImpl implements WechatCardService{

	/**
	 * @Description:获取access_token，用于作为其它接口的调用凭证
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年7月31日上午11:23:09 
	 * @param appId      微信公众号appId
	 * @param appSecret  公众号密钥appSecret
	 * @return	access_token
	 */
	@Override
	public AccessTokenRep doAccessToken(String appId, String appSecret) {
		String reqUrl = WechatCardConfig.ACCESS_TOKEN_API.replace("APPID", appId)
				.replace("APPSECRET", appSecret);
		JSONObject jsonObj = JSONObject.parseObject(HttpUtil.doGet(reqUrl, null, "UTF-8", false));
		Log.info("--------doAccessToken recv-------"+jsonObj);
		if(StringUtils.isNotBlank(jsonObj.getString("access_token"))){
			return new AccessTokenRep(jsonObj.getString("access_token"));
		}
		return new AccessTokenRep(jsonObj.getString("errcode"),jsonObj.getString("errmsg"));
	}

	/**
	 * @Description:微信图片上传
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月1日上午9:55:02 
	 * @param accessToken
	 * @return
	 */
	@Override
	public UploadImgRep doUploadImg(String imgUrl,String accessToken) {
		WritableResource resource = new FileSystemResource(new File(imgUrl));
		MultiValueMap<String,WritableResource> data = new LinkedMultiValueMap<String,WritableResource>();
		data.add("buffer", resource);
		String urlString = WechatCardConfig.UPLOAD_IMG_API.replace("TOKEN", accessToken);
		RestTemplate restTemplate = new RestTemplate();
		String recvMsg = restTemplate.postForObject(urlString, data, String.class);
		Log.info("--------doUploadImg recv-------"+recvMsg);
		JSONObject jsonObj = JSONObject.parseObject(recvMsg);
		if(StringUtils.isNotBlank(jsonObj.getString("url"))){
			return new UploadImgRep(jsonObj.getString("url"));
		}
		return new UploadImgRep(jsonObj.getString("errcode"),jsonObj.getString("errmsg"));
	}

	/**
	 * @Description:创建卡券
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月1日下午3:27:41 
	 * @param cardReq
	 * @param accessToken
	 * @return
	 */
	@Override
	public CreateCardRep doCreateCard(CardReq cardReq, String accessToken) {
		String urlString = WechatCardConfig.CARD_CREATE_API.replace("TOKEN", accessToken);
		JSONObject obj = new JSONObject();
		obj.put("card", cardReq);
		String reqParams = obj.toJSONString();
		Log.info("-------doCreateCard send--------"+reqParams);
		String recvMsg = HttpUtil.sendPostUrl(urlString, reqParams, "utf-8");
		Log.info("-------doCreateCard recv--------"+recvMsg);
		JSONObject jsonObj = JSONObject.parseObject(recvMsg);
		String errCode = jsonObj.getString("errcode");
		if(StringUtils.isNotBlank(errCode) && "0".equals(errCode)){
			return new CreateCardRep(jsonObj.getString("card_id"));
		}
		return new CreateCardRep(errCode,jsonObj.getString("errmsg"));
	}
	
	/**
	 * @Description:卡券信息修改
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月17日上午10:24:58 
	 * @param cardReq
	 * @param accesssToken
	 * @return
	 */
	@Override
	public UpdCardRep doUpdateCard(CardReq cardReq, String accessToken) {
		String urlString = WechatCardConfig.CARD_UPD_API.replace("TOKEN", accessToken);
		String reqParams = JSONObject.toJSONString(cardReq);
		Log.info("-------doUpdateCard send--------"+reqParams);
		String recvMsg = HttpUtil.sendPostUrl(urlString, reqParams, "utf-8");
		Log.info("-------doUpdateCard recv--------"+recvMsg);
		JSONObject jsonObj = JSONObject.parseObject(recvMsg);
		String errCode = jsonObj.getString("errcode");
		if(StringUtils.isNotBlank(errCode) && "0".equals(errCode)){
			return new UpdCardRep(jsonObj.getBooleanValue("send_check"));
		}
		return new UpdCardRep(errCode,jsonObj.getString("errmsg"));
	}

	/**
	 * @Description:获取卡券投放领取码
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月2日下午2:38:50 
	 * @param codeReq
	 * @param accessToken
	 * @return
	 */
	@Override
	public CreateQrCodeRep doCreateQrCode(CreateQrCodeReq codeReq, String accessToken) {
		String urlString = WechatCardConfig.CREATE_QRCODE_API.replace("TOKEN", accessToken);
		String reqParams =JSONObject.toJSONString(codeReq);
		Log.info("-------doCreateQrCode send--------"+reqParams);
		String recvMsg = HttpUtil.sendPostUrl(urlString, reqParams, "utf-8");
		Log.info("-------doCreateQrCode recv--------"+recvMsg);
		JSONObject jsonObj = JSONObject.parseObject(recvMsg);
		String errCode = jsonObj.getString("errcode");
		if(StringUtils.isNotBlank(errCode) && "0".equals(errCode)){
			String ticket = jsonObj.getString("ticket");
			String expireSeconds = jsonObj.getString("expire_seconds");
			String url = jsonObj.getString("url");
			String showQrcodeUrl = jsonObj.getString("show_qrcode_url"); 
			return new CreateQrCodeRep(ticket,expireSeconds,url,showQrcodeUrl);
		}
		return new CreateQrCodeRep(errCode,jsonObj.getString("errmsg"));
	}

	/**
	 * @Description:查询卡券核销码状态
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月10日下午5:00:06 
	 * @param qrCodeReq
	 * @param accessToken
	 * @return
	 */
	@Override
	public QrCardCodeRep doQueryCardCode(QrCardCodeReq qrCodeReq,
			String accessToken) {
		String urlString = WechatCardConfig.QR_CARDCODE_API.replace("TOKEN", accessToken);
		String reqParams =JSONObject.toJSONString(qrCodeReq);
		Log.info("-------doQueryCardCode send--------"+reqParams);
		String recvMsg = HttpUtil.sendPostUrl(urlString, reqParams, "utf-8");
		Log.info("-------doQueryCardCode recv--------"+recvMsg);
		JSONObject jsonObj = JSONObject.parseObject(recvMsg);
		String errCode = jsonObj.getString("errcode");
		if(StringUtils.isNotBlank(errCode) && "0".equals(errCode)){
			String openid = jsonObj.getString("openid");
			boolean canConsum = jsonObj.getBoolean("can_consume");
			String userCardStatus = jsonObj.getString("user_card_status");
			String cardId = jsonObj.getJSONObject("card").getString("card_id");
			return new QrCardCodeRep( cardId, openid, canConsum, userCardStatus);
		}
		return new QrCardCodeRep(errCode,jsonObj.getString("errmsg"));
	}

	/**
	 * @Description:卡券核销
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月10日下午5:17:46 
	 * @param cardCode
	 * @param accessToken
	 * @return
	 */
	@Override
	public CardConsumeRep doCardConsume(String cardCode,String accessToken) {
		String urlString = WechatCardConfig.CARD_CONSUME_API.replace("TOKEN", accessToken);
		JSONObject paramObj = new JSONObject();
		paramObj.put("code", cardCode);
		String reqParams =paramObj.toJSONString();
		Log.info("-------doCardConsume send--------"+reqParams);
		String recvMsg = HttpUtil.sendPostUrl(urlString, reqParams, "utf-8");
		Log.info("-------doCardConsume recv--------"+recvMsg);
		JSONObject jsonObj = JSONObject.parseObject(recvMsg);
		String errCode = jsonObj.getString("errcode");
		if(StringUtils.isNotBlank(errCode) && "0".equals(errCode)){
			String openid = jsonObj.getString("openid");
			String cardId = jsonObj.getJSONObject("card").getString("card_id");
			return new CardConsumeRep( cardId, openid,null);
		}
		return new CardConsumeRep(errCode,jsonObj.getString("errmsg"));
	}

	/**
	 * @Description:卡券删除
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月14日上午9:56:41 
	 * @param cardId
	 * @param accessToken
	 * @return
	 */
	@Override
	public AppBaseRep doCardDelete(String cardId, String accessToken) {
		String urlString = WechatCardConfig.CARD_DELETE_API.replace("TOKEN", accessToken);
		JSONObject paramObj = new JSONObject();
		paramObj.put("card_id", cardId);
		String reqParams =paramObj.toJSONString();
		Log.info("-------doCardDelete send--------"+reqParams);
		String recvMsg = HttpUtil.sendPostUrl(urlString, reqParams, "utf-8");
		Log.info("-------doCardDelete recv--------"+recvMsg);
		JSONObject jsonObj = JSONObject.parseObject(recvMsg);
		String errCode = jsonObj.getString("errcode");
		if(StringUtils.isNotBlank(errCode) && "0".equals(errCode)){
			return new AppBaseRep();
		}
		return new AppBaseRep(errCode,jsonObj.getString("errmsg"));
	}

	/**
	 * @Description:卡券推送(公众号)
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月24日下午5:18:52 
	 * @param cardId
	 * @param accessToken
	 * @return
	 */
	@Override
	public AppBaseRep doCardPush(String cardId, String accessToken) {
		String urlString = WechatCardConfig.CARD_PUSH_API.replace("TOKEN", accessToken);
		JSONObject jsonReq = new JSONObject();
		JSONObject filterJson = new JSONObject();
		filterJson.put("is_to_all", true);
		JSONObject wxcardJson = new JSONObject();
		wxcardJson.put("card_id", cardId);
		jsonReq.put("filter", filterJson);
		jsonReq.put("wxcard", wxcardJson);
		jsonReq.put("msgtype", "wxcard");
		String reqParams =jsonReq.toJSONString();
		Log.info("-------doCardPush send--------"+reqParams);
		String recvMsg = HttpUtil.sendPostUrl(urlString, reqParams, "utf-8");
		Log.info("-------doCardPush recv--------"+recvMsg);
		JSONObject jsonObj = JSONObject.parseObject(recvMsg);
		String errCode = jsonObj.getString("errcode");
		if(StringUtils.isNotBlank(errCode) && "0".equals(errCode)){
			return new AppBaseRep();
		}
		return new AppBaseRep(errCode,jsonObj.getString("errmsg"));
	}

	/**
	 * @Description:设置卡券支持快速买单
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月30日上午9:33:14 
	 * @param cardId
	 * @param accessToken
	 * @return
	 */
	@Override
	public AppBaseRep doCardPaySet(String cardId, String accessToken) {
		String urlString = WechatCardConfig.CARD_PAYSET_API.replace("TOKEN", accessToken);
		JSONObject jsonReq = new JSONObject();
		jsonReq.put("card_id", cardId);
		jsonReq.put("is_open", true);
		String reqParams =jsonReq.toJSONString();
		Log.info("-------doCardPaySet send--------"+reqParams);
		String recvMsg = HttpUtil.sendPostUrl(urlString, reqParams, "utf-8");
		Log.info("-------doCardPaySet recv--------"+recvMsg);
		JSONObject jsonObj = JSONObject.parseObject(recvMsg);
		String errCode = jsonObj.getString("errcode");
		if(StringUtils.isNotBlank(errCode) && "0".equals(errCode)){
			return new AppBaseRep();
		}
		return new AppBaseRep(errCode,jsonObj.getString("errmsg"));
	}

	

}
