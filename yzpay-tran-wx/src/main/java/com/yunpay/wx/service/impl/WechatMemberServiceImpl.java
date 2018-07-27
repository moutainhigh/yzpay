package com.yunpay.wx.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yunpay.common.utils.HttpUtil;
import com.yunpay.common.utils.Log;
import com.yunpay.common.utils.ResultEnum.ErrorCode;
import com.yunpay.common.utils.ResultEnum.ResultCode;
import com.yunpay.wx.config.WechatCardConfig;
import com.yunpay.wx.rep.card.AppBaseRep;
import com.yunpay.wx.rep.card.CreateCardRep;
import com.yunpay.wx.rep.card.UpdCardRep;
import com.yunpay.wx.rep.member.MemUserInfoRep;
import com.yunpay.wx.rep.member.MemberUpdRep;
import com.yunpay.wx.req.member.ActivateFieldReq;
import com.yunpay.wx.req.member.MemCardUpdReq;
import com.yunpay.wx.req.member.MemberCardReq;
import com.yunpay.wx.req.member.MemberUpdReq;
import com.yunpay.wx.service.WechatMemberService;

/**
 * 文件名称:     WechatMemberService.java
 * 内容摘要:    微信会员卡相关接口封装类，封装了调用微信接口创建会员卡、修改、设置激活参数、会员信息查询等相关方法
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年8月25日上午11:57:47 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年8月25日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
@Service("wechatMemberService")
public class WechatMemberServiceImpl implements WechatMemberService{

	/**
	 * @Description:创建会员卡
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月25日下午1:39:04 
	 * @param memberCardReq
	 * @param accessToken
	 * @return
	 */
	@Override
	public CreateCardRep doCreateMemberCard(MemberCardReq memberCardReq,
			String accessToken) {
		String urlString = WechatCardConfig.CARD_CREATE_API.replace("TOKEN", accessToken);
		JSONObject obj = new JSONObject();
		JSONObject cardBody = new JSONObject();
		cardBody.put("card_type", "MEMBER_CARD");
		cardBody.put("member_card", memberCardReq);
		obj.put("card", cardBody);
		String reqParams = obj.toJSONString();
		Log.info("-------doCreateMemberCard send--------"+reqParams);
		try{
			String recvMsg = HttpUtil.sendPostUrl(urlString, reqParams, "utf-8");
			Log.info("-------doCreateMemberCard recv--------"+recvMsg);
			JSONObject jsonObj = JSONObject.parseObject(recvMsg);
			String errCode = jsonObj.getString("errcode");
			if(StringUtils.isNotBlank(errCode) && "0".equals(errCode)){
				return new CreateCardRep(jsonObj.getString("card_id"));
			}
			return new CreateCardRep(errCode,jsonObj.getString("errmsg"));
		}catch(Exception e){
			return new CreateCardRep(ErrorCode.CHANNEL_PAY_ERROR.getCode(),ErrorCode.CHANNEL_PAY_ERROR.getDes());
		}
		
	}
	
	/**
	 * @Description: 会员卡信息修改
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年10月13日下午2:23:44 
	 * @param memCardUpdReq
	 * @param accessToken
	 * @return
	 */
	public UpdCardRep doUpdMemberCard(MemCardUpdReq memCardUpdReq,String accessToken){
		String urlString = WechatCardConfig.CARD_UPD_API.replace("TOKEN", accessToken);
		
		String reqParams = JSONObject.toJSONString(memCardUpdReq);
		Log.info("-------doUpdMemberCard send--------"+reqParams);
		try{
			String recvMsg = HttpUtil.sendPostUrl(urlString, reqParams, "utf-8");
			Log.info("-------doUpdMemberCard recv--------"+recvMsg);
			JSONObject jsonObj = JSONObject.parseObject(recvMsg);
			String errCode = jsonObj.getString("errcode");
			if(StringUtils.isNotBlank(errCode) && "0".equals(errCode)){
				return new UpdCardRep(jsonObj.getBooleanValue("send_check "));
			}
			return new UpdCardRep(errCode,jsonObj.getString("errmsg"));
		}catch(Exception e){
			return new UpdCardRep(ErrorCode.CHANNEL_PAY_ERROR.getCode(),ErrorCode.CHANNEL_PAY_ERROR.getDes());
		}
	}

	/**
	 * @Description:设置激活参数
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月31日下午2:56:00 
	 * @param cardId
	 * @param fieldList
	 * @param accessToken
	 * @return
	 */
	@Override
	public AppBaseRep doActivateSet(ActivateFieldReq activateFieldReq,String accessToken) {
		//封装请求参数
		JSONObject activateReq = new JSONObject();
		activateReq.put("card_id", activateFieldReq.getCard_id());
		//必填项
		JSONObject requireForm = new JSONObject();
		//选填项
		JSONObject optionalForm = new JSONObject();
		//设置必填项不允许修改
		requireForm.put("can_modify", activateFieldReq.isRequire_can_modify());
		requireForm.put("common_field_id_list", activateFieldReq.getRequire_fields());
		//设置选填项允许修改
		optionalForm.put("can_modify", activateFieldReq.isCommon_can_modify());
		optionalForm.put("common_field_id_list", activateFieldReq.getCommon_fields());
		activateReq.put("required_form", requireForm);
		activateReq.put("optional_form", optionalForm);
		
		String urlString = WechatCardConfig.CARD_ACTIVATESET_API.replace("TOKEN", accessToken);
		String reqParams = activateReq.toJSONString();
		Log.info("-------doActivateSet send--------"+reqParams);
		try{
			String recvMsg = HttpUtil.sendPostUrl(urlString, reqParams, "utf-8");
			Log.info("-------doActivateSet recv--------"+recvMsg);
			JSONObject jsonObj = JSONObject.parseObject(recvMsg);
			String errCode = jsonObj.getString("errcode");
			if(StringUtils.isNotBlank(errCode) && "0".equals(errCode)){
				return new AppBaseRep();
			}
			return new AppBaseRep(errCode,jsonObj.getString("errmsg"));
		}catch(Exception e){
			return new AppBaseRep(ErrorCode.CHANNEL_PAY_ERROR.getCode(),ErrorCode.CHANNEL_PAY_ERROR.getDes());
		}
	}

	/**
	 * @Description:获取用户信息
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月19日下午6:42:40 
	 * @param cardId
	 * @param userCardCode
	 * @param accessToken
	 * @return
	 */
	@Override
	public MemUserInfoRep doGetUserInfo(String cardId, String userCardCode,
			String accessToken) {
		JSONObject req = new JSONObject();
		req.put("card_id", cardId);
		req.put("code", userCardCode);
		String urlString = WechatCardConfig.MEMBER_USERINFO_API.replace("TOKEN", accessToken);
		
		String reqParams = req.toJSONString();
		Log.info("-------doGetUserInfo send--------"+reqParams);
		try{
			String recvMsg = HttpUtil.sendPostUrl(urlString, reqParams, "utf-8");
			Log.info("-------doGetUserInfo recv--------"+recvMsg);
			JSONObject jsonObj = JSONObject.parseObject(recvMsg);
			String errCode = jsonObj.getString("errcode");
			if(StringUtils.isNotBlank(errCode) && "0".equals(errCode)){
				MemUserInfoRep rep = new MemUserInfoRep();
				rep.setReturn_code(ResultCode.SUCCESS.getCode());
				rep.setOpenid(jsonObj.getString("openid"));
				rep.setNickname(jsonObj.getString("nickname"));
				rep.setBonus(jsonObj.getInteger("bonus"));
				rep.setSex(jsonObj.getString("sex"));
				rep.setUser_card_status(jsonObj.getString("user_card_status"));
				JSONObject userInfo = jsonObj.getJSONObject("user_info");
				JSONArray commonFieldList = userInfo.getJSONArray("common_field_list");
				Map<String,String> commonFieldMap = new HashMap<String,String>();
				for(int i=0;i<commonFieldList.size();i++){
					JSONObject commonField = (JSONObject)commonFieldList.get(i);
					commonFieldMap.put(commonField.getString("name"), commonField.getString("value"));
				}
				rep.setCommon_field_list(commonFieldMap);
				return rep;
			}
			return new MemUserInfoRep(errCode,jsonObj.getString("errmsg"));
		}catch(Exception e){
			return new MemUserInfoRep(ErrorCode.CHANNEL_PAY_ERROR.getCode(),ErrorCode.CHANNEL_PAY_ERROR.getDes());
		}
	}

	/**
	 * @Description:修改会员信息
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月28日下午5:11:14 
	 * @param memberUpdReq
	 * @return
	 */
	@Override
	public MemberUpdRep doUpdMember(MemberUpdReq memberUpdReq,String accessToken) {
		String urlString = WechatCardConfig.MEMBER_UPDUSER_API.replace("TOKEN", accessToken);
		String reqParams = JSONObject.toJSONString(memberUpdReq);
		Log.info("-------doUpdMember send--------"+reqParams);
		try{
			String recvMsg = HttpUtil.sendPostUrl(urlString, reqParams, "utf-8");
			Log.info("-------doUpdMember recv--------"+recvMsg);
			JSONObject jsonObj = JSONObject.parseObject(recvMsg);
			String errCode = jsonObj.getString("errcode");
			if(StringUtils.isNotBlank(errCode) && "0".equals(errCode)){
				return new MemberUpdRep(jsonObj.getInteger("result_bonus"),
						jsonObj.getInteger("result_balance"),jsonObj.getString("openid"));
			}
			return new MemberUpdRep(errCode,jsonObj.getString("errmsg"));
		}catch(Exception e){
			return new MemberUpdRep(ErrorCode.CHANNEL_PAY_ERROR.getCode(),ErrorCode.CHANNEL_PAY_ERROR.getDes());
		}
	}

}
