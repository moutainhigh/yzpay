package com.yunpay.controller.login;

import java.util.HashMap;
import java.util.Map;
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
import org.springframework.web.bind.annotation.ResponseBody;
import com.yunpay.common.core.utils.io.UtilsConfig;
import com.yunpay.controller.common.BaseController;
import com.yunpay.h5merch.permission.entity.MerchUser;
import com.yunpay.h5merch.permission.entity.ReciveMsg;
import com.yunpay.h5merch.permission.entity.WechatUserInfo;
import com.yunpay.h5merch.permission.service.MerchUserService;
import com.yunpay.h5merch.permission.service.WechatUserService;
import com.yunpay.permission.entity.MerchInfo;
import com.yunpay.permission.exception.PermissionException;
import com.yunpay.permission.service.MerchService;
import com.yunpay.permission.shiro.filter.MySessionContext;
import com.yunpay.permission.utils.WxHttpReq;

@Controller
public class WxLoginContr extends BaseController
{
	@SuppressWarnings("unused")
	private static final Log LOG = LogFactory.getLog(WxLoginContr.class);
	@Autowired
	private MerchUserService merchUserService;
	@Autowired
	private MerchService merchService;
	@Autowired
	private WechatUserService wechatUserService;

	/**
	 * 函数功能说明 ： 进入后台登陆页面.
	 * 
	 * @参数： @return
	 * @return String
	 * @throws
	 */
	@RequestMapping("/wxlogin")
	public String wxLogin(HttpServletRequest req,Model model,HttpServletResponse response) {
		try
		{
			Map<String, Object> map = new HashMap<>();
			Map<String, Object> userInfoMap = new HashMap<>();
			HttpSession session = req.getSession();
			
			String urlType = StringUtils.isNotBlank((String)session.getAttribute("urlType"))?(String)session.getAttribute("urlType"):null;
			MerchUser user = (MerchUser)session.getAttribute("merchUser") == null?null:(MerchUser)session.getAttribute("merchUser");
			Integer sendCodeTime = Integer.valueOf(UtilsConfig.getConfig("login.sendcode.time", "sys"));
			
			if ("wxregister".equals(urlType))
			{
				String openId = (String)session.getAttribute("openId");
				req.setAttribute("openId", openId);
				req.setAttribute("sendCodeTime", sendCodeTime);
				return "login/wxregister";
			} else if(user != null)
			{
				String merchantNo = user.getMerchantNo();
				if (StringUtils.isNotBlank(merchantNo))
				{
					// 获取商户信息(基本信息、账户信息、证件信息)
					MerchInfo merchInfo = merchService.getMerchInfo(merchantNo);
					if(merchInfo != null){
						// 返回商户认证状态:0：待审核，1：审核通过，2：驳回，3：驳回后二次申请,审核中
						int  auditStatus = merchInfo.getAuditStatus();
						String approveStatus = "";
						if(auditStatus == 0){	approveStatus = "未认证"; }
						else if(auditStatus == 1){ approveStatus = "已认证"; }
						else if(auditStatus == 2){ approveStatus = "认证中"; }
						else if(auditStatus == 3){ approveStatus = "认证中"; }
						else{ approveStatus = "未认证";}
						req.setAttribute("merchStatus", approveStatus);
					}
					//向页面传递是否进行微信支付配置参数，1为已配置，0为未配置
					if (merchService.queryConfigById(merchantNo, "wechat") != null)
					{
						req.setAttribute("wx", 1);
					} else
					{
						req.setAttribute("wx", 0);
					}
					req.setAttribute("merchInfo", merchInfo);
					return "login/home";
				} else
				{
					return "login/homeundo";
				}
			}else {
				
				//取得微信登陆平台返回的参数
				String code = StringUtils.isBlank(req.getParameter("code"))?null:req.getParameter("code");
				String state = StringUtils.isBlank(req.getParameter("state"))?null:req.getParameter("state");
				
				//调用取得access_token接口
				if (code != null && "yunpaymerch".equals(state))
				{
					map =  WxHttpReq.getAccess(code);
					String openId = (String)map.get("openid");				
					String accessToken = (String)map.get("access_token");
					System.out.println("openId=="+openId+"==accessToken=="+accessToken);
					if ("SUCCESS".equals((String)map.get("message")))
					{
						WechatUserInfo wechatUserInfo = wechatUserService.findWechatUserByOpenId(openId);
						MerchUser merchUser = merchUserService.findMerchUserByOpenId(openId);
						if (wechatUserInfo == null)
						{
							//调用取得用户信息接口
							userInfoMap = WxHttpReq.getUserInfo(openId, accessToken);
							if ("SUCCESS".equals((String)userInfoMap.get("message"))&&wechatUserService.addUser(userInfoMap)>0){
								req.setAttribute("openId", openId);
								req.setAttribute("sendCodeTime", sendCodeTime);
								session.setAttribute("urlType", "wxregister");
								session.setAttribute("openId", openId);
								return "login/wxregister";
							}else{
								req.setAttribute("message", "获取用户信息失败");
								return "/login/error";
							}
						} else if(wechatUserInfo != null && merchUser == null)
						{
							req.setAttribute("openId", openId);
							session.setAttribute("urlType", "wxregister");
							session.setAttribute("openId", openId);
							return "login/wxregister";
						}else{
							session.setAttribute("merchUser", merchUser);
							String merchantNo = merchUser.getMerchantNo();
							if (StringUtils.isNotBlank(merchantNo))
							{
								// 获取商户信息(基本信息、账户信息、证件信息)
								MerchInfo merchInfo = merchService.getMerchInfo(merchantNo);
								if(merchInfo != null){
									// 返回商户认证状态:0：待审核，1：审核通过，2：驳回，3：驳回后二次申请,审核中
									int  auditStatus = merchInfo.getAuditStatus();
									String approveStatus = "";
									if(auditStatus == 0){	approveStatus = "未认证"; }
									else if(auditStatus == 1){ approveStatus = "已认证"; }
									else if(auditStatus == 2){ approveStatus = "认证中"; }
									else if(auditStatus == 3){ approveStatus = "认证中"; }
									else{ approveStatus = "未认证";}
									req.setAttribute("merchStatus", approveStatus);
								}
								//向页面传递是否进行微信支付配置参数，1为已配置，0为未配置
								if (merchService.queryConfigById(merchantNo, "wechat") != null)
								{
									req.setAttribute("wx", 1);
								} else
								{
									req.setAttribute("wx", 0);
								}
								req.setAttribute("merchInfo", merchInfo);
								
								//取得配置文件中设定的每次登陆可操作页面的最大时间
								Integer staticTime = 3600*Integer.valueOf(UtilsConfig.getConfig("login.static.time", "sys"));
								session.setMaxInactiveInterval(staticTime);
								return "login/home";
							} else
							{
								return "login/homeundo";
							}
						}
					}else {
						req.setAttribute("message", "获取用户信息失败");
						return "/login/error";
					}				
				}else{
					req.setAttribute("message", "获取用户信息失败");
					return "/login/error";
				}				
			}
			
		} catch (Exception e)
		{
			e.printStackTrace();
			req.setAttribute("message", "系统错误");
			return "/login/error";
		}
		
	}
	
	/**
	 * 函数功能说明 ：微信用户注册商户
	 * 
	 * @参数： @param request
	 * @参数： @param model
	 * @参数： @return
	 * @return String
	 * @throws PermissionException
	 */
	@RequestMapping("/wxregister")
	public @ResponseBody ReciveMsg<Map<String, Object>> wxRegister(HttpServletRequest req, Model model) {
		try{
			//取得前端请求传递的参数
			String userName=StringUtils.isBlank(req.getParameter("userName"))?null:req.getParameter("userName");
			// 判断是 get 还是post
			String requestMethod = req.getMethod();
			if("get".equalsIgnoreCase(requestMethod)&&userName != null){
				// 设置get请求时的编码格式
				userName = new String(userName.getBytes("ISO-8859-1"), "UTF-8");  // 构造一个UTF-8格式的字符串
			}
			String loginPwd=StringUtils.isBlank(req.getParameter("loginPwd"))?null:req.getParameter("loginPwd");
			String password=StringUtils.isBlank(req.getParameter("password"))?null:req.getParameter("password");
			String phone=StringUtils.isBlank(req.getParameter("phone"))?null:req.getParameter("phone");
			String phoneCode=StringUtils.isBlank(req.getParameter("phoneCode"))?null:req.getParameter("phoneCode");		
			String agreement=StringUtils.isBlank(req.getParameter("agreement"))?null:req.getParameter("agreement");
			String openId=StringUtils.isBlank(req.getParameter("openId"))?null:req.getParameter("openId");
			
			//取得session中的验证码信息
			HttpSession session=req.getSession();
			String sysPhoneCode=StringUtils.isBlank((String)session.getAttribute("sysPhoneCode"))?null
					:(String)session.getAttribute("sysPhoneCode");
			
			//组装业务请求的数据
			Map<String, String> reqMap=new HashMap<>();
			reqMap.put("openId", openId);
			reqMap.put("userName", userName);
			reqMap.put("loginPwd", loginPwd);
			reqMap.put("password", password);
			reqMap.put("phone", phone);
			reqMap.put("phoneCode", phoneCode+phone);
			reqMap.put("sysPhoneCode", sysPhoneCode);
			reqMap.put("agreement", agreement);
			
			//调用注册业务方法
			ReciveMsg<Map<String, Object>> reciveMsg = merchUserService.addUser(reqMap);
			
			//注册成功，则将状态保存在session，并将session保存在同时登录限制的MAP中
			if ("SUCCESS".equals(reciveMsg.getResult_code()))
			{
				Map<String, Object> map = reciveMsg.getData();
				//取得配置文件中设定的每次登陆可操作页面的最大时间
				Integer staticTime = 3600*Integer.valueOf(UtilsConfig.getConfig("login.static.time", "sys"));
				session.setAttribute("sysPhoneCode", null);
				session.setAttribute("urlType", null);
				session.setMaxInactiveInterval(staticTime);
				session.setAttribute("merchUser", (MerchUser)map.get("merchUser"));	
				MySessionContext.AddSession(session);
				//减少返回前端的数据
				reciveMsg.setData(null);
			}
			
			//返回数据到前端
			return reciveMsg;
		}catch(Exception e){
			e.printStackTrace();
			//返回数据到前端
			return new ReciveMsg<Map<String,Object>>(0, "", "注册失败，请待会再试", null);
		}
	}
	
	/**
	 * 函数功能说明 ：非法操作和登陆后超过时长返回的页面
	 * 
	 * @参数： @param request
	 * @参数： @param model
	 * @参数： @return
	 * @return String
	 * @throws PermissionException
	 */
	@RequestMapping("/wxerror")
	public String wxError(HttpServletRequest req, Model model) {
		String flag = StringUtils.isNotBlank(req.getParameter("flag"))?req.getParameter("flag"):null;
		if ("1".equals(flag))
		{
			req.setAttribute("message", "未登陆/登陆状态已过期");
		}else {
			req.setAttribute("message", "异常操作");
		}
		
		return "/login/error";
	}
}
