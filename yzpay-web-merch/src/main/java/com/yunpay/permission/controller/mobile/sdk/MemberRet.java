package com.yunpay.permission.controller.mobile.sdk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.yunpay.h5merch.permission.entity.ReciveMsg;
import com.yunpay.h5merch.permission.entity.card.BaseInfo;
import com.yunpay.h5merch.permission.entity.card.Template;
import com.yunpay.h5merch.permission.service.MemberCardService;
import com.yunpay.permission.controller.StoreController;
import com.yunpay.permission.utils.AppDupLog;

/**
 * 
 * 类名称                     APP端用的会员信息查询接口类
 * 文件名称:     MemberRet.java
 * 内容摘要: 
 * @author:   Zhao Xiaoman
 * @version:  1.0  
 * @Date:     2017年12月1日下午4:58:35
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年12月1日   Zhao Xiaoman       1.0       新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
@Controller
@RequestMapping("/membercard")
public class MemberRet
{
	private static Log log = LogFactory.getLog(StoreController.class);
	@Autowired
	private MemberCardService memberCardService;
	/**
	 * 函数功能说明 ：会员卡基本信息接口.
	 * @param req
	 * @return
	 */
/*	@RequestMapping("/baseInfo")
	public @ResponseBody ResultMsg<BaseInfo> baseInfo(HttpServletRequest req,@RequestParam("merchant") String merchant,Model model)
	{
		try
		{			
			List<BaseInfo> listBaseInfo = new ArrayList<>();
			if (merchant == null || merchant == "")
			{
				listBaseInfo = null;
				ResultMsg<BaseInfo> resultMsg = new ResultMsg<BaseInfo>(0,"1002","请求参数无商户号",listBaseInfo);
				return resultMsg;
			}
			BaseInfo baseInfo = memberCardService.findBaseInfo(merchant);
			if (baseInfo != null)
			{
				listBaseInfo.add(baseInfo);
				ResultMsg<BaseInfo> resultMsg = new ResultMsg<BaseInfo>(1,"","",listBaseInfo);
				return resultMsg;
			} else
			{
				listBaseInfo = null;
				ResultMsg<BaseInfo> resultMsg = new ResultMsg<BaseInfo>(0,"1001","商户未设置",listBaseInfo);
				return resultMsg;
			}
			
				
		} catch (Exception e)
		{
			log.error("== recharge exception:", e);
			List<BaseInfo> listBaseInfo = new ArrayList<>();
			listBaseInfo = null;
			ResultMsg<BaseInfo> resultMsg = new ResultMsg<BaseInfo>(0,"1003",("系统出错："+e),listBaseInfo);
			return resultMsg;
		}			
	}
	*//**
	 * 函数功能说明 ：会员卡积分信息接口.
	 * @param req
	 * @return
	 *//*
	@RequestMapping("/integral")
	public @ResponseBody ResultMsg<IntegralRule> integral(HttpServletRequest req,@RequestParam("merchant") String merchant,Model model)
	{
		try
		{	
			List<IntegralRule> listIntegral = new ArrayList<>();
			if (merchant == null || merchant == "")
			{
				listIntegral = null;
				ResultMsg<IntegralRule> resultMsg = new ResultMsg<IntegralRule>(0,"1002","请求参数无商户号",listIntegral);
				return resultMsg;
			}
			IntegralRule integralRule = memberCardService.findIntegralRule(merchant);
			if (integralRule != null)
			{
				listIntegral.add(integralRule);
				ResultMsg<IntegralRule> resultMsg = new ResultMsg<IntegralRule>(1,"","",listIntegral);
				return resultMsg;
			} else
			{
				listIntegral = null;
				ResultMsg<IntegralRule> resultMsg = new ResultMsg<IntegralRule>(0,"1001","商户未设置",listIntegral);
				return resultMsg;
			}
			
				
		} catch (Exception e)
		{
			log.error("== recharge exception:", e);
			List<IntegralRule> listIntegral = new ArrayList<>();
			listIntegral = null;
			ResultMsg<IntegralRule> resultMsg = new ResultMsg<IntegralRule>(0,"1003",("系统出错："+e),listIntegral);
			return resultMsg;
		}			
	}
	*//**
	 * 函数功能说明 ：会员卡储值信息接口.
	 * @param req
	 * @return
	 *//*
	@RequestMapping("/recharge")
	public @ResponseBody ResultMsg<RechargeRule> recharge(HttpServletRequest req,@RequestParam("merchant") String merchant,Model model)
	{
		try
		{			
			List<RechargeRule> listRecharge = new ArrayList<>();
			if (merchant == null || merchant == "")
			{
				listRecharge = null;
				ResultMsg<RechargeRule> resultMsg = new ResultMsg<RechargeRule>(0,"1002","请求参数无商户号",listRecharge);
				return resultMsg;
			}
			listRecharge = memberCardService.findRechargeRule(merchant);
			if (listRecharge.size()>0)
			{
				ResultMsg<RechargeRule> resultMsg = new ResultMsg<RechargeRule>(1,"","",listRecharge);
				return resultMsg;
			} else
			{
				listRecharge = null;
				ResultMsg<RechargeRule> resultMsg = new ResultMsg<RechargeRule>(0,"1001","商户未设置",listRecharge);
				return resultMsg;
			}
			
				
		} catch (Exception e)
		{
			log.error("== recharge exception:", e);
			List<RechargeRule> listRecharge = new ArrayList<>();
			listRecharge = null;
			ResultMsg<RechargeRule> resultMsg = new ResultMsg<RechargeRule>(0,"1003",("系统出错："+e),listRecharge);
			return resultMsg;
		}		
	}
	*//**
	 * 函数功能说明 ：会员卡储值模板信息接口.
	 * @param req
	 * @return
	 *//*
	@RequestMapping("/template")
	public @ResponseBody ResultMsg<Template> template(HttpServletRequest req,@RequestParam("merchant") String merchant,Model model)
	{
		try
		{			
			List<Template> listTemplate = new ArrayList<>();
			if (merchant == null || merchant == "")
			{
				listTemplate = null;
				ResultMsg<Template> resultMsg = new ResultMsg<Template>(0,"1002","请求参数无商户号",listTemplate);
				return resultMsg;
			}
			Template template = memberCardService.findTemplate(merchant);
			if (template != null)
			{
				listTemplate.add(template);
				ResultMsg<Template> resultMsg = new ResultMsg<Template>(1,"","",listTemplate);
				return resultMsg;
			} else
			{
				listTemplate = null;
				ResultMsg<Template> resultMsg = new ResultMsg<Template>(0,"1001","商户未设置",listTemplate);
				return resultMsg;
			}
			
			
		} catch (Exception e)
		{
			log.error("== recharge exception:", e);
			List<Template> listTemplate = new ArrayList<>();
			listTemplate = null;
			ResultMsg<Template> resultMsg = new ResultMsg<Template>(0,"1003",("系统出错："+e),listTemplate);
			return resultMsg;
		}		
	}*/
	/**
	 * 函数功能说明 ：会员卡储值模板信息接口.
	 * @param req
	 * @return
	 */
	@RequestMapping("/cardInfo")
	public @ResponseBody ReciveMsg<List<Map<String, Object>>> cardInfo(HttpServletRequest req,@RequestParam("merchant") String merchant,Model model)
	{
		try
		{	
			if (AppDupLog.checkDup(req) == 1)
			{
				//同时登录控制
				ReciveMsg<List<Map<String, Object>>> reciveMsg = new ReciveMsg<List<Map<String, Object>>>(0,"6020","The user account has been logged on to another machine",null);
				return reciveMsg;
			} 
			Map<String, Object> cardInfo = new HashMap<>();
			if (merchant == null || merchant == "")
			{
				ReciveMsg<List<Map<String, Object>>> reciveMsg = new ReciveMsg<List<Map<String, Object>>>(0,"1002","请求参数无商户号",null);
				return reciveMsg;
			}
			Template template = memberCardService.findTemplate(merchant);
			BaseInfo baseInfo = memberCardService.findBaseInfo(merchant);
			if (template != null&&baseInfo != null)
			{
				cardInfo.put("wc_cardId", template.getCardId());
				cardInfo.put("ail_cardId", template.getAlipassCardId());
				cardInfo.put("title", baseInfo.getTitle());
				cardInfo.put("sub_title", baseInfo.getSubTitle());
				cardInfo.put("descript", baseInfo.getDescription());
				cardInfo.put("startTime", baseInfo.getBeginTimes());
				cardInfo.put("endTime", baseInfo.getEndTimes());
				cardInfo.put("bg_color", baseInfo.getColor());
				cardInfo.put("logo", baseInfo.getLogoUrl());
				List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
				data.add(cardInfo);
				ReciveMsg<List<Map<String, Object>>> reciveMsg = new ReciveMsg<List<Map<String, Object>>>(1,"","",data);
				return reciveMsg;
			} else
			{
				ReciveMsg<List<Map<String, Object>>> reciveMsg = new ReciveMsg<List<Map<String, Object>>>(0,"1001","商户未设置",null);
				return reciveMsg;
			}
			
			
		} catch (Exception e)
		{
			log.error("== recharge exception:", e);
			ReciveMsg<List<Map<String, Object>>> reciveMsg = new ReciveMsg<List<Map<String, Object>>>(0,"1003",("系统出错："+e),null);
			return reciveMsg;
		}		
	}
}
