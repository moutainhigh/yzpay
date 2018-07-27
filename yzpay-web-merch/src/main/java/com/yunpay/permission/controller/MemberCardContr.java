package com.yunpay.permission.controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.yunpay.common.core.exception.BizException;
import com.yunpay.common.core.utils.io.ImageUtil;
import com.yunpay.common.core.utils.io.UtilsConfig;
import com.yunpay.controller.common.BaseController;
import com.yunpay.h5merch.permission.entity.MerchUser;
import com.yunpay.h5merch.permission.entity.ReciveMsg;
import com.yunpay.h5merch.permission.entity.card.BaseInfo;
import com.yunpay.h5merch.permission.entity.card.Commonfield;
import com.yunpay.h5merch.permission.entity.card.IntegralRule;
import com.yunpay.h5merch.permission.entity.card.RechargeRule;
import com.yunpay.h5merch.permission.entity.card.Template;
import com.yunpay.h5merch.permission.entity.card.UserCard;
import com.yunpay.h5merch.permission.entity.card.UserReAndCon;
import com.yunpay.h5merch.permission.service.MemberCardService;
import com.yunpay.h5merch.permission.service.UserCardService;
import com.yunpay.permission.entity.MerchInfo;
import com.yunpay.permission.service.MerchService;
import com.yunpay.permission.utils.CreatCard;
import com.yunpay.permission.utils.ImageSave;
/**
 * 
 * 类名称                      会员卡控制器类
 * 文件名称:     MemberCardContr.java
 * 内容摘要:     用于会员卡、积分、储值信息的添加、修改、查看，会员信息查看
 * @author:   Zhao Xiaoman
 * @version:  1.0  
 * @Date:     2017年9月7日下午4:53:34
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年9月7日   Zhao Xiaoman       1.0       新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
@Controller
@RequestMapping("/sys/membercard")
public class MemberCardContr extends BaseController
{
	private static Log log = LogFactory.getLog(MemberCardContr.class);

	@Autowired
	private MemberCardService memberCardService;
	@Autowired
	private MerchService merchService;
	@Autowired
	private UserCardService userCardService;

	/**
	 * 函数功能说明 ：会员卡展示.
	 * @param req
	 * @return
	 */
	@RequestMapping("/show")
	public String memberCard(HttpServletRequest req,Model model)
	{
		try
		{			
			String merchantNo = getMerchantNo(req);
			Template template = null;
			BaseInfo baseInfo = null;
			RechargeRule rechargeRule = null;
			IntegralRule integralRule = null;
			if (merchantNo != null)
			{
				//取得会员卡模板信息
				template = memberCardService.findTemplate(merchantNo);
				if (template != null)
				{
					//取得会员卡基本信息
					baseInfo = memberCardService.findBaseInfo(merchantNo);
				}
				//取得会员卡储值规则信息
				List<RechargeRule> list = memberCardService.findRechargeRule(merchantNo);
				if (list.size()>0)
				{
					rechargeRule = list.get(0);
				}
				//取得会员卡积分规则信息
				integralRule = memberCardService.findIntegralRule(merchantNo);
			} 
			req.setAttribute("baseInfo", baseInfo);
			req.setAttribute("template", template);
			req.setAttribute("integralRule", integralRule);
			req.setAttribute("rechargeRule", rechargeRule);
			return "membercard/membercard";		
		} catch (Exception e)
		{
			log.error("== memberCard exception:", e);
			return null;
		}		
	}
	/**
	 * 函数功能说明 ：进入会员卡添加页面.
	 * @param req
	 * @return
	 */
	@RequestMapping("/goadd")
	public String goAddMemberCard(HttpServletRequest req,Model model)
	{
		try
		{	String merchantNo = getMerchantNo(req);
			req.setAttribute("integralRule", memberCardService.findIntegralRule(merchantNo));
			return "membercard/membercardadd";		
		} catch (Exception e)
		{
			log.error("== goAddMemberCard exception:", e);
			return null;
		}		
	}
	/**
	 * 函数功能说明 ：会员卡添加.
	 * @param req
	 * @return
	 */
	@RequestMapping("/add")
	public @ResponseBody ReciveMsg<String> addMemberCard(HttpServletRequest req,Model model,Template template,BaseInfo baseInfo,
			@RequestParam("baselogo") String baseLogo)
	{
		String error = null;
		Integer flag = 0;
		ReciveMsg<String> reciveMsg = null;
		String merchantNo = getMerchantNo(req);
		MerchInfo merchInfo = merchService.getMerchInfo(merchantNo);
		String md5Key = merchInfo.getMd5Key();
		//判断该用户是否已制作会员卡
		if(memberCardService.findTemplate(merchantNo)!=null){
			error = "会员卡制作失败：在此之前您已制作了会员卡";
			reciveMsg = new ReciveMsg<String>(0, "", error, null);
			return reciveMsg;
		}
		try{
			String savePath = null;
			// 当图片参数不为空时，上传图片
			if(StringUtils.isNotBlank(baseLogo)){
				//取得微信上传图片的地址
				savePath = UtilsConfig.getDirs("upload.images",merchantNo);
				//将图片从BASE64转为图片格式，保存在本地，取得本地保存地址
				String imageUrl = base64CodeToFile(baseLogo,savePath);
				if (!"oversize".equals(imageUrl))
				{
					//上传图片到微信，并取得上传结果
					Map<String, String> rMap = ImageSave.sendFile(imageUrl, merchantNo, md5Key,2);
					if ("SUCCESS".equals(rMap.get("message"))){
						//取得微信保存图片地址，并保存在基本信息中
						baseInfo.setLogoUrl(rMap.get("imgUrl"));
						//将图片的本地存储地址保存在基本信息中
						baseInfo.setLogoLocalUrl(imageUrl);
						error = "SUCCESS";
					} else{
						// 上传失败则删除本地保存的图片
						UtilsConfig.delFile(imageUrl);
						error = "图片上传失败："+rMap.get("message");
					}	
				} else
				{
					//删除本地图片
					UtilsConfig.delFile(imageUrl);
					error = "图片上传失败：图片大小超出限制";
				}
			}else{
				//当前端未上传图片时，使用服务器保存的默认LOGO图片
				String url = req.getSession().getServletContext().getRealPath("/");
				savePath =url+"common/images/cardlogo/logo.png";
				//上传默认图片到微信，并取得上传结果
				Map<String, String> rMap = ImageSave.sendFile(savePath, merchantNo, md5Key,2);
				if ("SUCCESS".equals(rMap.get("message"))){
					//取得微信保存图片地址，并保存在基本信息中
					baseInfo.setLogoUrl(rMap.get("imgUrl"));
					//在基本信息的本地保存地址字段中保存“local”
					baseInfo.setLogoLocalUrl("local");
					error = "SUCCESS";
				} else{
					error = "图片上传失败："+rMap.get("message");
				}	
			}
		}catch(Exception e){
			//删除本地保存的图片（非默认图片）
			if (StringUtils.isNotBlank(baseLogo))
			{
				UtilsConfig.delFile(baseInfo.getLogoLocalUrl());
			}
			error = "图片上传失败：系统错误,请待会再尝试";
			log.error("== 上传图片出错:", e);
			reciveMsg = new ReciveMsg<String>(0, "", error, null);
			return reciveMsg;
		}
		if ("SUCCESS".equals(error)){
			try{	
				String reandconUrl = UtilsConfig.getConfig("reandcon.url");
				List<Commonfield> commonfieldList = new ArrayList<>();
				String mechantName = merchService.getMerchInfo(merchantNo).getMerchantName();
				template.setMerchantNo(merchantNo);
				template.setAutoActivate(2);
				template.setSupplyBonus(1);
				template.setPrerogative("享受折扣优惠");
				template.setCustomField1NameType("余额");
				template.setCustomField1Url(reandconUrl);
				template.setCustomCell1Name("");
				template.setCustomCell1Tips("");
				template.setCustomCell1Url("");
				template.setDiscount(100);
				baseInfo.setBrandName(mechantName);
				baseInfo.setNotice("使用时向服务员出示此券");
				baseInfo.setCodeType("CODE_TYPE_BARCODE");
				baseInfo.setServicePhone(merchInfo.getTel());
				baseInfo.setCanShare(1);
				baseInfo.setCanGiveFriend(0);
				Commonfield commonfield1 = new Commonfield();
				commonfield1.setField("USER_FORM_INFO_FLAG_MOBILE");
				commonfield1.setValue(1);
				commonfieldList.add(commonfield1);
				Commonfield commonfield2 = new Commonfield();
				commonfield2.setField("USER_FORM_INFO_FLAG_NAME");
				commonfield2.setValue(0);
				commonfieldList.add(commonfield2);
				Commonfield commonfield3 = new Commonfield();
				commonfield3.setField("USER_FORM_INFO_FLAG_SEX");
				commonfield3.setValue(0);
				commonfieldList.add(commonfield3);
				Commonfield commonfield4 = new Commonfield();
				commonfield4.setField("USER_FORM_INFO_FLAG_BIRTHDAY");
				commonfield4.setValue(0);
				commonfieldList.add(commonfield4);
				//取得积分规则信息
				IntegralRule integralRule = memberCardService.findIntegralRule(merchantNo);
				//制作微信会员卡（制作前需已设置积分规则）
				if (integralRule != null){
					//组装积分规则说明
					String bonusRules = getRules(integralRule);
					template.setBonusRules(bonusRules);
					//制作微信会员卡，并取得返回信息
					Map<String, String> rMap = CreatCard.sendMsg(baseInfo, template,integralRule,commonfieldList,md5Key);
					//制作成功，则将返回的会员卡号保存在模板信息中，将是否是微信会员卡字段值设置为1，将模板信息、基本信息、激活字段信息保存在本地数据库
					if ("SUCCESS".equals(rMap.get("message"))){
						template.setCardId(rMap.get("cardId"));
						template.setIsWeixinMemberCardTemplate(1);
						Integer redata = memberCardService.addMemberCard(template, baseInfo,commonfieldList);
						if (redata>0)
						{
							flag = 1;
						}else{
							error = "会员卡制作失败:数据库异常";
						}
					}else{
						error = "会员卡制作失败："+rMap.get("message");
					}
				}else{
					error = "会员卡制作失败：请先设置积分规则";
				}
				//当添加会员卡失败，且不是使用默认图片时，删除本地上传的图片
				if (flag == 0 && StringUtils.isNotBlank(baseLogo))
				{
					UtilsConfig.delFile(baseInfo.getLogoLocalUrl());
				}
			} catch (Exception e){
				//删除本地保存的图片（非默认图片）
				if (StringUtils.isNotBlank(baseLogo))
				{
					UtilsConfig.delFile(baseInfo.getLogoLocalUrl());
				}
				error = "会员卡制作失败：系统错误,请待会再尝试";
				log.error("== 制作微信/支付宝会员卡失败:", e);
				reciveMsg = new ReciveMsg<String>(0, "", error, null);
				return reciveMsg;
			}
		}
		//将会员卡制作结果返回到前端
		System.out.println("flag=="+flag);
		reciveMsg = new ReciveMsg<String>(flag, "", error, null);
		return reciveMsg;
	}
	
	/**
	 * 函数功能说明 ：进入会员卡修改页面.
	 * @param req
	 * @return
	 */
	@RequestMapping("/goedit")
	public String goEditMemberCard(HttpServletRequest req,Model model)
	{
		try
		{	String merchantNo = getMerchantNo(req);
			BaseInfo baseInfo = memberCardService.findBaseInfo(merchantNo);
			Template template = memberCardService.findTemplate(merchantNo);
			req.setAttribute("baseInfo", baseInfo);
			req.setAttribute("template", template);
			return "membercard/membercardedit";	
		} catch (Exception e)
		{
			log.error("== goEditMemberCard exception:", e);
			return null;
		}		
	}
	/**
	 * 函数功能说明 ：会员卡修改.
	 * @param req
	 * @return
	 */
	@RequestMapping("/edit")
	public @ResponseBody ReciveMsg<String> editMemberCard(HttpServletRequest req,Model model,Template template,BaseInfo baseInfo,
			@RequestParam("baselogo") String baseLogo)
	{		
		String error = null;
		Integer flag = 0;
		ReciveMsg<String> reciveMsg = null;
		String merchantNo = getMerchantNo(req);
		MerchInfo merchInfo = merchService.getMerchInfo(merchantNo);
		String md5Key = merchInfo.getMd5Key();
		template.setId(null);
		//检查会员卡是否存在
		if(memberCardService.findTemplate(merchantNo)==null){
			error = "数据读取错误，请待会再尝试";
			reciveMsg = new ReciveMsg<String>(0, "", error, null);
			return reciveMsg;
		}
		try{
			String savePath = null;
			// 当图片参数不为空时，上传图片
			if(StringUtils.isNotBlank(baseLogo)){
				//取得微信上传图片的地址
				savePath = UtilsConfig.getDirs("upload.images",merchantNo);
				//将图片从BASE64转为图片格式，保存在本地，取得本地保存地址
				String imageUrl = base64CodeToFile(baseLogo,savePath);
				if (!"oversize".equals(imageUrl))
				{
					//上传图片到微信，并取得上传结果
					Map<String, String> rMap = ImageSave.sendFile(imageUrl, merchantNo, md5Key,2);
					if ("SUCCESS".equals(rMap.get("message"))){
						//取得微信保存图片地址，并保存在基本信息中
						baseInfo.setLogoUrl(rMap.get("imgUrl"));
						//将图片的本地存储地址保存在基本信息中
						baseInfo.setLogoLocalUrl(imageUrl);
						//删除本地上次保存的图片
						BaseInfo baseInfoOld = memberCardService.findBaseInfo(merchantNo);
						String logoLocalUrl = baseInfoOld.getLogoLocalUrl();
						UtilsConfig.delFile(logoLocalUrl);
						//当本地上次保存的图片的文件夹为空时，删除文件夹
						String bageUrl = logoLocalUrl.substring(0, logoLocalUrl.lastIndexOf("/"));
						File file=new File(bageUrl);
						File[] listFiles = file.listFiles();
						if(file.exists()&&listFiles.length <= 0){
							file.delete();
						}
						error = "SUCCESS";
					} else{
						// 上传失败则删除本地保存的图片
						UtilsConfig.delFile(imageUrl);
						error = "图片上传失败："+rMap.get("message");
					}	
				} else
				{
					//删除本地图片
					UtilsConfig.delFile(imageUrl);
					error = "图片上传失败：图片大小超出限制";
				}
			}else{
				//当未上传图片时，则不修改微信、数据库中的图片保存地址
				baseInfo.setLogoUrl(null);
				baseInfo.setLogoLocalUrl(null);
				error = "SUCCESS";
			}
		}catch(Exception e){
			//删除本地保存的图片
			if (StringUtils.isNotBlank(baseLogo))
			{
				UtilsConfig.delFile(baseInfo.getLogoLocalUrl());
			}
			error = "图片上传失败：系统错误,请待会再尝试";
			log.error("== 上传图片出错:", e);
			reciveMsg = new ReciveMsg<String>(0, "", error, null);
			return reciveMsg;
		}
		if ("SUCCESS".equals(error)){
			try{	
				template.setMerchantNo(merchantNo);
				IntegralRule integralRule = null;
				//修改微信会员卡，并取得返回信息
				Map<String, String> rMap = CreatCard.updateMsg(baseInfo, template,integralRule,md5Key);
				//修改成功，则修改本地数据库的模板信息、基本信息
				if ("SUCCESS".equals(rMap.get("message")) && memberCardService.changeMemberCard(baseInfo, template) >0){					
					flag = 1;
				}else{
					//删除本地保存的图片
					if (StringUtils.isNotBlank(baseLogo))
					{
						UtilsConfig.delFile(baseInfo.getLogoLocalUrl());
					}
					error = "修改会员卡失败："+rMap.get("message");
				}
			} catch (Exception e){
				//删除本地保存的图片
				if (StringUtils.isNotBlank(baseLogo))
				{
					UtilsConfig.delFile(baseInfo.getLogoLocalUrl());
				}
				error = "修改会员卡失败：系统错误,请待会再尝试";
				log.error("== 修改微信/支付宝会员卡失败:", e);
				reciveMsg = new ReciveMsg<String>(0, "", error, null);
				return reciveMsg;
			}
		}
		//返回数据到前端
		reciveMsg = new ReciveMsg<String>(flag, "", error, null);
		return reciveMsg;
	}
	/**
	 * 函数功能说明 ：进入会员卡积分规则设置页面.
	 * @param req
	 * @return
	 */
	@RequestMapping("/gosetIntegral")
	public String goSetIntegral(HttpServletRequest req,Model model)
	{
		try
		{	
			String merchantNo = getMerchantNo(req);
			IntegralRule integralRule = memberCardService.findIntegralRule(merchantNo);
			req.setAttribute("integralRule", integralRule);
			return "membercard/setintegral";		
		} catch (Exception e)
		{
			log.error("== goSetIntegralRule exception:", e);
			return null;
		}		
	}
	/**
	 * 函数功能说明 ：会员卡积分规则设置.
	 * @param req
	 * @return
	 */
	@RequestMapping("/setintegral")
	public @ResponseBody ReciveMsg<String> setIntegral(HttpServletRequest req,Model model,IntegralRule integralRule)
	{
		Integer flag = 0;
		String error = null;
		try
		{	
			String merchantNo = getMerchantNo(req);
			integralRule.setMerchantNo(merchantNo);
			//当接收单笔最大赠送积分值参数为空时，为该字段赋值0，表示不限制
			if (integralRule.getMaxIncreaseBonus() == null)
			{
				integralRule.setMaxIncreaseBonus(0);
			} 
			//当接收领卡赠送积分值参数为空时，为该字段赋值0，表示领卡不赠送
			if (integralRule.getInitIncreaseBonus() == null)
			{
				integralRule.setInitIncreaseBonus(0);
			} 
			//组装积分说明字段
			String bonusRules = getRules(integralRule);
			//查找数据库，用户已设置积分规则，并已制作会员卡，调用会员卡修改方法，修改微信会员卡信息，修改成功后，修改本地数据库已有数据
			//用户已设置积分规则，但未制作会员卡，则修改本地数据库已有数据
			//未设置积分规则，则在本地数据库插入该积分规则
			if (memberCardService.findIntegralRule(merchantNo) != null)
			{
				MerchInfo merchInfo = merchService.getMerchInfo(merchantNo);
				String md5Key = merchInfo.getMd5Key();
				Template template = memberCardService.findTemplate(merchantNo);
				if (template !=null && template.getCardId() != null)
				{
					BaseInfo baseInfo = memberCardService.findBaseInfo(merchantNo);
					template.setBonusRules(bonusRules);
					//修改微信会员卡，并取得返回信息
					Map<String, String> rMap = CreatCard.updateMsg(baseInfo, template,integralRule,md5Key);
					//修改成功，则修改数据库中积分规则
					if ("SUCCESS".equals(rMap.get("message")) && "SUCCESS".equals(memberCardService.addIntegralRule(integralRule,template)))
					{
						 flag = 1;
					} else
					{
						error = "设置积分规则失败：系统错误,请待会再试";
					}
				} else
				{
					template = null;
					//在本地数据库修改该积分规则
					if ("SUCCESS".equals(memberCardService.addIntegralRule(integralRule,template)))
					{
						flag = 1;
					} else
					{
						error = "设置积分规则失败：系统错误,请待会再试";
					}
				}
			} else
			{
				Template template = null;
				//在本地数据库插入该积分规则
				if ("SUCCESS".equals(memberCardService.addIntegralRule(integralRule,template)))
				{
					flag = 1;
				} else
				{
					error = "设置积分规则失败：系统错误,请待会再试";
				}
			}
			return new ReciveMsg<String>(flag, "", error, null);		
		} catch (Exception e)
		{
			log.error("== setRule exception:", e);
			return new ReciveMsg<String>(0, "", "设置积分规则失败：系统错误，请待会再尝试", null);
		}		
	}
	/**
	 * 函数功能说明 ：进入会员卡 储值规则列表页面.
	 * @param req
	 * @return
	 */
	@RequestMapping("/goRechargeList")
	public String goRechargeList(HttpServletRequest req,Model model)
	{
		try
		{	
			String merchantNo = getMerchantNo(req);
			List<RechargeRule>  rechargeRuleList = memberCardService.findRechargeRule(merchantNo);
			req.setAttribute("rechargeRuleList", rechargeRuleList);
			return "membercard/rechargelist";		
		} catch (Exception e)
		{
			log.error("== goSetRecharge exception:", e);
			return null;
		}		
	}
	/**
	 * 函数功能说明 ：进入会员卡 储值规则设置页面.
	 * @param req
	 * @return
	 */
	@RequestMapping("/gosetRecharge")
	public String goSetRecharge(HttpServletRequest req,Model model)
	{
		try
		{	
			String merchantNo = getMerchantNo(req);
			req.setAttribute("merchantNo", merchantNo);
			return "membercard/setrecharge";		
		} catch (Exception e)
		{
			log.error("== goSetRecharge exception:", e);
			return null;
		}		
	}
	/**
	 * 函数功能说明 ：会员卡储值规则设置.
	 * @param req
	 * @return
	 */
	@RequestMapping("/setRecharge")
	public @ResponseBody ReciveMsg<String> setRecharge(HttpServletRequest req,Model model,RechargeRule rechargeRule)
	{
		try
		{	
			String merchantNo = getMerchantNo(req);
			rechargeRule.setMerchantNo(merchantNo);
			//添加储值规则信息，并保存返回结果
			String message = memberCardService.addRechargeRule(rechargeRule);
			if ("SUCCESS".equals(message))
			{
				return new ReciveMsg<String>(1, "", "", null);
			} else
			{
				return new ReciveMsg<String>(0, "", message, null);
			}		
		} catch (Exception e)
		{
			log.error("== setRecharge exception:", e);
			return new ReciveMsg<String>(0, "", "充值规则设置失败：系统错误，请待会再尝试", null);
		}		
	}
	/**
	 * 函数功能说明 ：删除会员卡储值规则.
	 * @param req
	 * @return
	 */
	@RequestMapping("/delRecharge")
	public @ResponseBody ReciveMsg<String> delRecharge(HttpServletRequest req,Model model,@RequestParam("id") Integer id )
	{
		try
		{	
			//删除会员卡储值规则，并保存返回结果
			String message = memberCardService.deleteRechargeRuleById(id);
			if ("SUCCESS".equals(message))
			{
				return new ReciveMsg<String>(1, "", "", null);
			} else
			{
				return new ReciveMsg<String>(0, "", message, null);
			}	
		} catch (Exception e)
		{
			log.error("== delRecharge exception:", e);
			return new ReciveMsg<String>(0, "", "系统错误，请待会再尝试", null);
		}		
	}
	/**
	 * 函数功能说明 ：进入会员的卡列表页面.
	 * @param merchant
	 * @param page
	 * @param type
	 * @param req
	 * @return
	 */
	@RequestMapping("/userlist")
	public String userList(HttpServletRequest req,Model model,
			@RequestParam("merchant") String merchant,
			@RequestParam("page") Integer page,
			@RequestParam("type") Integer type)
	{
		try
		{	
			HttpSession session = req.getSession();
			String merchantNo = (MerchUser)session.getAttribute("merchUser")==null?null:((MerchUser)session.getAttribute("merchUser")).getMerchantNo();
			if (StringUtils.isNotBlank(merchant))
			{
				merchantNo = merchant;
			}
			//取得分页的会员信息，传到页面
			req.setAttribute("userCardList", userCardService.findUserCardList(merchantNo, page));
			//取得会员数量，传到页面
			req.setAttribute("userCardNum", userCardService.findUserCardNum(merchantNo));
			req.setAttribute("page", page);
			/*req.setAttribute("total", total);*/
			req.setAttribute("merchant", merchantNo);
			switch (type)
			{
			case 0:
				return "membercard/usercard";  //返回会员列表页面
			case 1:
				return "membercard/usercardre"; //返回会员列表填充页面（下拉刷新数据）
			default:
				return "membercard/usercard";
			}
					
		} catch (Exception e)
		{
			log.error("== userlist exception:", e);
			switch (type)
			{
			case 0:
				return "membercard/usercard";
			case 1:
				return "membercard/usercardre";
			default:
				return "membercard/usercard";
			}
		}	
	}
	
	/**
	 * 函数功能说明 ：列表追加（上拉追加数据）.
	 * @param req
	 * @param model
	 * @param merchant
	 * @param page
	 * @return
	 */
	@RequestMapping("/userlistadd")
	public @ResponseBody List<UserCard> userListAdd(HttpServletRequest req,Model model,
			@RequestParam("merchant") String merchant,
			@RequestParam("page") Integer page)
	{
		try
		{	
			HttpSession session = req.getSession();
			MerchUser merchUser = (MerchUser)session.getAttribute("merchUser");
			String merchantNo = merchUser==null?null:merchUser.getMerchantNo();
			if (StringUtils.isNotBlank(merchant))
			{
				merchantNo = merchant;
			}
			//返回分页显示的会员数据（上拉追加数据）
			return userCardService.findUserCardList(merchantNo, page);		
		} catch (Exception e)
		{
			log.error("== userlistadd exception:", e);
			return null;
		}	
	}
	
	/**
	 * 函数功能说明 ：会员详细信息.
	 * @param req
	 * @param id
	 * @param merchant
	 * @return
	 */
	@RequestMapping("/usershow")
	public  String userShow(HttpServletRequest req,Model model,
			@RequestParam("merchant") String merchant,
			@RequestParam("id") Integer id)
	{
		try
		{	
			HttpSession session = req.getSession();
			String merchantNo = (MerchUser)session.getAttribute("merchUser")==null?null:((MerchUser)session.getAttribute("merchUser")).getMerchantNo();
			if (StringUtils.isNotBlank(merchant))
			{
				merchantNo = merchant;
			}
			//会员详细信息
			UserCard userCard = userCardService.findUserCard(id);
			//声明map，用于保存会员积分、储值、消费的汇总信息
			Map<String, Double> map = new HashMap<>();
			//声明code，用于保存查询参数
			Map<String, String> code = new HashMap<>();
			if (userCard.getUserCardCode() != null)
			{
				//当会员的微信卡号不为空时，code保存微信会员卡号
				code.put("cardCode", userCard.getUserCardCode());
				code.put("merchantNo", userCard.getMerchantNo());
				//查询会员积分、储值、消费的汇总信息
				map = userCardService.findOther(code);
			} else if(userCard.getAliUserCardCode() != null)
			{
				//当会员的支付宝卡号不为空时，code保存支付宝会员卡号
				code.put("cardCode", userCard.getAliUserCardCode());
				code.put("merchantNo", userCard.getMerchantNo());
				//查询会员积分、储值、消费的汇总信息
				map = userCardService.findOther(code);
			}else{
				map = null;
			}
			req.setAttribute("userCard",userCard);
			req.setAttribute("userOther", map);
			req.setAttribute("merchant", merchantNo);
			return "membercard/usershow";		
		} catch (Exception e)
		{
			log.error("== userlist exception:", e);
			return null;
		}	
	}
	
	/**
	 * 函数功能说明 ：会员查看积分、储值、消费记录（此方法暂不用）.
	 * @param req
	 * @param card_id
	 * @param openid
	 * @return
	 */
	@RequestMapping("/reandcon")
	public  String rechargeShow(HttpServletRequest req,Model model,
			@RequestParam("card_id") String card_id,
			@RequestParam("openid") String openid)
	{
		try
		{	
			if (card_id == null || openid == null)
			{
				return null;
			} else
			{
				//保存查询参数
				Map<String, String> data = new HashMap<>();
				data.put("cardId", card_id);
				data.put("openId", openid);
				//查询会员
				UserCard userCard = userCardService.findUserCardByUser(data);
				if (userCard == null)
				{
					return null;
				} else
				{
					Map<String, Double> map = new HashMap<>();
					//用于保存查询参数
					Map<String, String> code = new HashMap<>();
					if (userCard.getUserCardCode() != null)
					{
						//若有微信会员卡号，则使用微信会员卡号查询
						code.put("cardCode", userCard.getUserCardCode());
						code.put("merchantNo", userCard.getMerchantNo());
						
					} else if(userCard.getAliUserCardCode() != null)
					{
						//若有支付宝会员卡号，则使用支付宝会员卡号查询
						code.put("cardCode", userCard.getAliUserCardCode());
						code.put("merchantNo", userCard.getMerchantNo());
					}else {
						
					}
					//查询会员储值、消费的汇总信息
					map = userCardService.findOther(code);
					//查询会员储值、消费的记录列表
					List<UserReAndCon> list = userCardService.findUserReAndCon(code);
					req.setAttribute("userCard",userCard);
					req.setAttribute("userOther", map);
					req.setAttribute("reandconList", list);
					
				}
				return "membercard/reandconshow";
			}		
		} catch (Exception e)
		{
			log.error("== userlist exception:", e);
			return null;
		}	
	}
	/**
	 * 函数功能说明 ：显示图片.
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping("/showImg")
	public String showImg(HttpServletRequest req,HttpServletResponse response,Model model)
	{
		try
		{
			String merchantNo = getMerchantNo(req);
			String path= memberCardService.findBaseInfo(merchantNo).getLogoLocalUrl();
			File picFile = new File(path);
			/*if(!picFile.exists()){
			    String uploadDir=WebUtils.getRealPath(request.getSession().getServletContext(), "/");
			    path=uploadDir+"//crmres//images//error.gif" ;
			    picFile = new File(path);
			   }*/
			   response.setContentType("image/jpeg; charset=GBK");
			   //response.setHeader("Content-Disposition", "attachment; filename="+new String("temp.jpg".getBytes("GBK"),"ISO8859_1"));
			   ServletOutputStream outputStream = response.getOutputStream();
			   FileInputStream inputStream = new FileInputStream(picFile);
			   byte[] buffer = new byte[1024];
			   int i = -1;
			   while ((i = inputStream.read(buffer)) != -1) {
			      outputStream.write(buffer, 0, i);
			   }
			   outputStream.flush();
			   outputStream.close();
			   inputStream.close();
			   outputStream = null;
			   return null;
		} catch (Exception e)
		{
			log.error("== showImg exception:", e);
			return null;
		}
	}
	
	/**
	 * 函数功能说明 ：取得商户编号.
	 * @param req
	 * @return
	 */
	public static String getMerchantNo(HttpServletRequest req)
	{
		HttpSession session = req.getSession();
		return ((MerchUser)session.getAttribute("merchUser")).getMerchantNo();
	}
	/**
	 * 函数功能说明 ：组装积分规则说明.
	 * @param integralRule
	 * @return
	 */
	public static String getRules(IntegralRule integralRule)
	{
		String bonusRules = "激活即送"+integralRule.getInitIncreaseBonus()+"积分,每消费"+integralRule.getCostMoneyUnit()+"元赠送"+integralRule.getIncreaseBonus()+"积分";
		if (integralRule.getCostBonusUnit() != null)
		{
			String costBonusUnit = integralRule.getCostBonusUnit().toString();
			String bonusUnit = costBonusUnit.split(".")[0];
			bonusRules = bonusRules+",每"+bonusUnit+"积分兑换"+integralRule.getReduceMoney()+"元";
		}
		return bonusRules;
	}
	
	/**
	 * 函数功能说明 ：获取img的base64编码并转换为图片.
	 * @param baseLogo
	 * @param savePath
	 * @return
	 */
	public String base64CodeToFile(String baseLogo,String savePath){
		try{
				//将img的base64编码转化为图片并保存，返回文件
				File f = ImageUtil.GtImage(baseLogo, savePath);
				String imageUrl = savePath+"/"+f.getName();
				//判断保存后的图片大小，确认是否超出限制
				long fileSize = f.length() / 1024;
				if (fileSize>5*1024)
				{
					imageUrl = "oversize";
				}
				return imageUrl;	
		}catch(Exception e){
			throw new BizException("上传文件时出错.",e);
		}
	}
}
