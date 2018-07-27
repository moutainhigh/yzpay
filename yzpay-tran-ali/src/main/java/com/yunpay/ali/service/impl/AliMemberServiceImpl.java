package com.yunpay.ali.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayMarketingCardActivateformQueryRequest;
import com.alipay.api.request.AlipayMarketingCardActivateurlApplyRequest;
import com.alipay.api.request.AlipayMarketingCardFormtemplateSetRequest;
import com.alipay.api.request.AlipayMarketingCardOpenRequest;
import com.alipay.api.request.AlipayMarketingCardTemplateCreateRequest;
import com.alipay.api.request.AlipayMarketingCardTemplateQueryRequest;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.response.AlipayMarketingCardActivateformQueryResponse;
import com.alipay.api.response.AlipayMarketingCardActivateurlApplyResponse;
import com.alipay.api.response.AlipayMarketingCardFormtemplateSetResponse;
import com.alipay.api.response.AlipayMarketingCardOpenResponse;
import com.alipay.api.response.AlipayMarketingCardTemplateCreateResponse;
import com.alipay.api.response.AlipayMarketingCardTemplateQueryResponse;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yunpay.ali.config.AliPayConfig;
import com.yunpay.ali.rep.member.AccessTokenRep;
import com.yunpay.ali.rep.member.MemCardQrcodeRep;
import com.yunpay.ali.rep.member.MemCardRep;
import com.yunpay.ali.rep.member.MemUserCardOpenRep;
import com.yunpay.ali.rep.member.MemUserFieldQryRep;
import com.yunpay.ali.rep.pay.AliQrRep;
import com.yunpay.ali.req.member.AccessTokenReq;
import com.yunpay.ali.req.member.MemCardQrcodeReq;
import com.yunpay.ali.req.member.MemCardQryReq;
import com.yunpay.ali.req.member.MemCardReq;
import com.yunpay.ali.req.member.MemCardFieldReq;
import com.yunpay.ali.req.member.MemUserCardOpenReq;
import com.yunpay.ali.req.member.MemUserFieldQryReq;
import com.yunpay.ali.service.AliMemberService;
import com.yunpay.common.utils.Log;
import com.yunpay.common.utils.ResultEnum.ErrorCode;
import com.yunpay.common.utils.ResultEnum.ResultCode;

/**
 * 文件名称:     AliMemberServiceImpl.java
 * 内容摘要:    支付宝会员相关接口封装类
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年9月12日上午11:46:39 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年9月12日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
@Service("aliMemberService")
public class AliMemberServiceImpl implements AliMemberService{

	/**
	 * @Description:创建会员卡模板
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月12日下午4:27:01 
	 * @param req
	 * @return
	 */
	@Override
	public MemCardRep doCreateCardTemp(MemCardReq req) {
		AlipayClient alipayClient = new DefaultAlipayClient(AliPayConfig.BAR_PAY_API,req.getApp_id(),
				req.getPrivateKey(),req.getFormat(),req.getCharset(),
				req.getAlipayPublicKey(),req.getSign_type());
		MemCardRep rep=null;
		try{
			AlipayMarketingCardTemplateCreateRequest request = new AlipayMarketingCardTemplateCreateRequest();
			ObjectMapper mapper = new ObjectMapper(); 
			String reqBizContent= mapper.writeValueAsString(req.getTemplateBaseInfo());
			Log.info("创建支付宝会员卡模板参数内容："+reqBizContent);
			request.setBizContent(reqBizContent);
			AlipayMarketingCardTemplateCreateResponse response = alipayClient.execute(request);
			if(response.isSuccess()){
				rep = new MemCardRep();
				rep.setCode(response.getCode());
				rep.setMsg(response.getMsg());
				rep.setTemplate_id(response.getTemplateId());
			}else{
				rep = new MemCardRep(ResultCode.FAIL.getCode(),ResultCode.FAIL.getDes(),
						response.getSubCode(),response.getSubMsg());
			}
		}catch (JsonProcessingException e) {
			e.printStackTrace();
			rep = new MemCardRep(ResultCode.FAIL.getCode(),ResultCode.FAIL.getDes(),
					ErrorCode.SYSTEM_EXCEPTION.getCode(),ErrorCode.SYSTEM_EXCEPTION.getDes());
		} catch (AlipayApiException e) {
			e.printStackTrace();
			rep = new MemCardRep(ResultCode.FAIL.getCode(),ResultCode.FAIL.getDes(),
					ErrorCode.SYSTEM_EXCEPTION.getCode(),ErrorCode.SYSTEM_EXCEPTION.getDes());
		}
		return rep;
	}
	
	/**
	 * @Description:会员卡卡券模板查询
	 * @author:   Zeng Dongcheng
	 * @Date:     2018年3月6日上午10:46:26 
	 * @param req
	 * @return
	 */
	@Override
	public MemCardRep doQueryCardTemp(MemCardQryReq req) {
		AlipayClient alipayClient = new DefaultAlipayClient(AliPayConfig.BAR_PAY_API,req.getApp_id(),
				req.getPrivateKey(),req.getFormat(),req.getCharset(),
				req.getAlipayPublicKey(),req.getSign_type());
		MemCardRep rep=null;
		try {
			AlipayMarketingCardTemplateQueryRequest request = new AlipayMarketingCardTemplateQueryRequest();
			request.setBizContent("{" +
			"\"template_id\":\""+req.getTemplate_id()+"\"" +
			"}");
			AlipayMarketingCardTemplateQueryResponse response = alipayClient.execute(request);
			if(response.isSuccess()){
				rep = new MemCardRep();
				rep.setCode(response.getCode());
				rep.setMsg(response.getMsg());
			}else{
				rep = new MemCardRep(ResultCode.FAIL.getCode(),ResultCode.FAIL.getDes(),
						response.getSubCode(),response.getSubMsg());
			}
		} catch (AlipayApiException e) {
			e.printStackTrace();
			rep = new MemCardRep(ResultCode.FAIL.getCode(),ResultCode.FAIL.getDes(),
					ErrorCode.SYSTEM_EXCEPTION.getCode(),ErrorCode.SYSTEM_EXCEPTION.getDes());
		}
		return rep;
	}


	/**
	 * @Description:会员卡激活参数设置
	 * @author:   Zeng Dongcheng
	 * @Date:     2018年3月6日上午11:42:15 
	 * @param req
	 * @return
	 */
	@Override
	public AliQrRep doCardTempFieldSet(MemCardFieldReq req) {
		AlipayClient alipayClient = new DefaultAlipayClient(AliPayConfig.BAR_PAY_API,req.getApp_id(),
				req.getPrivateKey(),req.getFormat(),req.getCharset(),
				req.getAlipayPublicKey(),req.getSign_type());
		AliQrRep rep=null;
		try{
			AlipayMarketingCardFormtemplateSetRequest request = new AlipayMarketingCardFormtemplateSetRequest();
			request.setBizContent(req.getBizContent());
			AlipayMarketingCardFormtemplateSetResponse response = alipayClient.execute(request);
			if(response.isSuccess()){
				rep = new MemCardRep();
				rep.setCode(response.getCode());
				rep.setMsg(response.getMsg());
			}else{
				rep = new MemCardRep(ResultCode.FAIL.getCode(),ResultCode.FAIL.getDes(),
						response.getSubCode(),response.getSubMsg());
			}
		} catch (AlipayApiException e) {
			e.printStackTrace();
			rep = new AliQrRep(ResultCode.FAIL.getCode(),ResultCode.FAIL.getDes(),
					ErrorCode.SYSTEM_EXCEPTION.getCode(),ErrorCode.SYSTEM_EXCEPTION.getDes());
		}
		return rep;
	}

	/**
	 * @Description:获取会员卡领卡链接
	 * @author:   Zeng Dongcheng
	 * @Date:     2018年3月6日下午2:10:00 
	 * @param req
	 * @return
	 */
	@Override
	public MemCardQrcodeRep doCardTempQrcode(MemCardQrcodeReq req) {
		AlipayClient alipayClient = new DefaultAlipayClient(AliPayConfig.BAR_PAY_API,req.getApp_id(),
				req.getPrivateKey(),req.getFormat(),req.getCharset(),
				req.getAlipayPublicKey(),req.getSign_type());
		AlipayMarketingCardActivateurlApplyRequest request = new AlipayMarketingCardActivateurlApplyRequest();
		Map<String,Object> biz = new HashMap<String,Object>();
		biz.put("template_id", req.getTemplate_id());
		if(StringUtils.isNotBlank(req.getOut_string())){
			biz.put("out_string", req.getOut_string());
		}
		biz.put("callback", req.getCallback());
		if(StringUtils.isNotBlank(req.getFollow_app_id())){
			biz.put("follow_app_id", req.getFollow_app_id());
		}
		ObjectMapper mapper = new ObjectMapper(); 
		MemCardQrcodeRep rep;
		try {
			request.setBizContent(mapper.writeValueAsString(biz));
			AlipayMarketingCardActivateurlApplyResponse response = alipayClient.execute(request);
			if(response.isSuccess()){
				rep = new MemCardQrcodeRep();
				rep.setCode(response.getCode());
				rep.setMsg(response.getMsg());
				rep.setApply_card_ur(response.getApplyCardUrl());
			}else{
				rep = new MemCardQrcodeRep(ResultCode.FAIL.getCode(),ResultCode.FAIL.getDes(),
						response.getSubCode(),response.getSubMsg());
			}
		}catch (JsonProcessingException e) {
			e.printStackTrace();
			rep = new MemCardQrcodeRep(ResultCode.FAIL.getCode(),ResultCode.FAIL.getDes(),
					ErrorCode.SYSTEM_EXCEPTION.getCode(),ErrorCode.SYSTEM_EXCEPTION.getDes());
		}catch (AlipayApiException e) {
			e.printStackTrace();
			rep = new MemCardQrcodeRep(ResultCode.FAIL.getCode(),ResultCode.FAIL.getDes(),
					ErrorCode.SYSTEM_EXCEPTION.getCode(),ErrorCode.SYSTEM_EXCEPTION.getDes());
		}
		return rep;
	}

	/**
	 * @Description:获取access_token和userId
	 * @author:   Zeng Dongcheng
	 * @Date:     2018年3月7日上午11:25:47 
	 * @param req
	 * @return
	 */
	@Override
	public AccessTokenRep doAccessToken(AccessTokenReq req) {
		AlipayClient alipayClient = new DefaultAlipayClient(AliPayConfig.BAR_PAY_API,req.getApp_id(),
				req.getPrivateKey(),req.getFormat(),req.getCharset(),
				req.getAlipayPublicKey(),req.getSign_type()); 
		AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
		request.setCode(req.getAuthCode());
		request.setGrantType("authorization_code");
		AccessTokenRep rep;
		try {
		    AlipaySystemOauthTokenResponse response = alipayClient.execute(request);
		    if(response.isSuccess()){
				rep = new AccessTokenRep();
				rep.setCode("10000");
				rep.setMsg("获取成功！");
				rep.setAccessToken(response.getAccessToken());
				rep.setUserId(response.getUserId());
			}else{
				rep = new AccessTokenRep(ResultCode.FAIL.getCode(),ResultCode.FAIL.getDes(),
						response.getSubCode(),response.getSubMsg());
			}
		} catch (AlipayApiException e) {
		    e.printStackTrace();
		    rep = new AccessTokenRep(ResultCode.FAIL.getCode(),ResultCode.FAIL.getDes(),
					ErrorCode.SYSTEM_EXCEPTION.getCode(),ErrorCode.SYSTEM_EXCEPTION.getDes());
		}
		return rep;
	}

	/**
	 * @Description:查询用户授权时填写的表单信息
	 * @author:   Zeng Dongcheng
	 * @Date:     2018年3月7日下午1:56:34 
	 * @param req
	 * @return
	 */
	@Override
	public MemUserFieldQryRep doMemUserFieldQry(MemUserFieldQryReq req) {
		AlipayClient alipayClient = new DefaultAlipayClient(AliPayConfig.BAR_PAY_API,req.getApp_id(),
				req.getPrivateKey(),req.getFormat(),req.getCharset(),
				req.getAlipayPublicKey(),req.getSign_type()); 
		AlipayMarketingCardActivateformQueryRequest request = new AlipayMarketingCardActivateformQueryRequest();
		Map<String,Object> biz = new HashMap<String,Object>();
		biz.put("biz_type", "MEMBER_CARD");
		biz.put("template_id", req.getTemplate_id());
		biz.put("request_id", req.getRequest_id());
		ObjectMapper mapper = new ObjectMapper(); 
		MemUserFieldQryRep rep;
		try {
			request.setBizContent(mapper.writeValueAsString(biz));
			AlipayMarketingCardActivateformQueryResponse response = alipayClient.execute(request,req.getAccess_token());
			if(response.isSuccess()){
				rep = new MemUserFieldQryRep();
				rep.setCode(response.getCode());
				rep.setMsg(response.getMsg());
				rep.setInfos(response.getInfos());
			}else{
				rep = new MemUserFieldQryRep(ResultCode.FAIL.getCode(),ResultCode.FAIL.getDes(),
						response.getSubCode(),response.getSubMsg());
			}
		} catch (AlipayApiException e) {
			e.printStackTrace();
			rep = new MemUserFieldQryRep(ResultCode.FAIL.getCode(),ResultCode.FAIL.getDes(),
					ErrorCode.SYSTEM_EXCEPTION.getCode(),ErrorCode.SYSTEM_EXCEPTION.getDes());
		}catch (JsonProcessingException e1) {
			e1.printStackTrace();
			rep = new MemUserFieldQryRep(ResultCode.FAIL.getCode(),ResultCode.FAIL.getDes(),
					ErrorCode.SYSTEM_EXCEPTION.getCode(),ErrorCode.SYSTEM_EXCEPTION.getDes());
		}
		return rep;
	}

	/**
	 * @Description:会员卡开卡
	 * @author:   Zeng Dongcheng
	 * @Date:     2018年3月7日下午2:50:00 
	 * @param req
	 * @return
	 */
	@Override
	public MemUserCardOpenRep doMemUserCardOpen(MemUserCardOpenReq req) {
		AlipayClient alipayClient = new DefaultAlipayClient(AliPayConfig.BAR_PAY_API,req.getApp_id(),
				req.getPrivateKey(),req.getFormat(),req.getCharset(),
				req.getAlipayPublicKey(),req.getSign_type());
		MemUserCardOpenRep rep=null;
		try{
			AlipayMarketingCardOpenRequest request = new AlipayMarketingCardOpenRequest();
			request.setBizContent(req.getBizContent());
			AlipayMarketingCardOpenResponse response = alipayClient.execute(request,req.getAccessToken());
			if(response.isSuccess()){
				rep = new MemUserCardOpenRep();
				rep.setCode(response.getCode());
				rep.setMsg(response.getMsg());
				rep.setBizCardNo(response.getCardInfo().getBizCardNo());
				rep.setExternalCardNo(response.getCardInfo().getExternalCardNo());
			}else{
				rep = new MemUserCardOpenRep(ResultCode.FAIL.getCode(),ResultCode.FAIL.getDes(),
						response.getSubCode(),response.getSubMsg());
			}
		} catch (AlipayApiException e) {
			e.printStackTrace();
			rep = new MemUserCardOpenRep(ResultCode.FAIL.getCode(),ResultCode.FAIL.getDes(),
					ErrorCode.SYSTEM_EXCEPTION.getCode(),ErrorCode.SYSTEM_EXCEPTION.getDes());
		}
		return rep;
	}

	
	

}
