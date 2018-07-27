package com.yunpay.controller.login;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yunpay.common.core.exception.BizException;
import com.yunpay.common.core.utils.DateUtils;
import com.yunpay.common.core.utils.ValidateUtils;
import com.yunpay.common.core.utils.http.Response.Code;
import com.yunpay.common.core.utils.io.SyncDataToggle;
import com.yunpay.common.core.utils.io.UtilsConfig;
import com.yunpay.controller.common.BaseController;
import com.yunpay.h5merch.permission.entity.MerchUser;
import com.yunpay.h5merch.permission.entity.ReciveMsg;
import com.yunpay.h5merch.permission.service.MerchUserService;
import com.yunpay.h5merch.service.impl.MerchUserServiceImpl;
import com.yunpay.h5merch.service.impl.SysCashierServiceImpl;
import com.yunpay.permission.controller.mobile.sdk.entity.MerchData;
import com.yunpay.permission.entity.MerchAttach;
import com.yunpay.permission.entity.Merchant;
import com.yunpay.permission.entity.MerchantAccount;
import com.yunpay.permission.entity.SysAttach;
import com.yunpay.permission.entity.SysCashier;
import com.yunpay.permission.exception.PermissionException;
import com.yunpay.permission.service.MerchService;
import com.yunpay.permission.shiro.filter.MySessionContext;
import com.yunpay.permission.utils.HttpRequest;
import com.yunpay.permission.utils.MD5Utils;
import com.yunpay.permission.utils.Sha1Utils;
import com.yunpay.permission.utils.SmsUtils;
import com.yunpay.permission.utils.phonecore.SmsPhone;
/**
 * 
 * 类名称                      登录界面控制类
 * 文件名称:     LoginContr.java
 * 内容摘要: 	       用于登录控制、注册、找回密码、同步招手猫商户信息
 * @author:   Zhao Xiaoman
 * @version:  1.0  
 * @Date:     2017年7月28日下午5:22:03
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年7月28日   Zhao Xiaoman       1.0       新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */

@Controller
public class LoginContr extends BaseController {
	@SuppressWarnings("unused")
	private static final Log LOG = LogFactory.getLog(LoginContr.class);
	@Autowired
	private MerchUserService merchUserService;
	@Autowired
	private SysCashierServiceImpl sysCashierService;
	@Autowired
	private  MerchService MerchService;

	/**
	 * 函数功能说明 ： 进入后台登陆页面.
	 * 
	 * @参数： @return
	 * @return String
	 * @throws
	 */
	@RequestMapping("/login")
	public @ResponseBody ReciveMsg<Map<String, Object>> login(HttpServletRequest req,Model model,HttpServletResponse response) {
		try
		{
			//取得前端请求参数
			String userCaptchaCode = StringUtils.isBlank(req.getParameter("userCaptchaCode"))?null:req.getParameter("userCaptchaCode");
			String loginName = StringUtils.isBlank(req.getParameter("loginName"))?null:req.getParameter("loginName");
			String loginPwd = StringUtils.isBlank(req.getParameter("loginPwd"))?null:req.getParameter("loginPwd");
			Boolean autoLogin = StringUtils.isBlank(req.getParameter("autoLogin"))?false:true;
				    
			//取得当前请求session及session中保存的数据
		    HttpSession session = req.getSession();	    
			Date setDate = (Date)session.getAttribute("setDate");		
			String captchaCode = (String)session.getAttribute("captchaCode");
			
			//取得配置文件中设定的验证码有效时长，并计算是否过期
		    Integer imgCodeTime = Integer.valueOf(UtilsConfig.getConfig("login.imgcode.time", "sys"));
			Integer overTime=setDate==null?0:imgCodeTime-DateUtils.getSecondDiff(new Date(), setDate);
			
			//组装业务请求数据
			Map<String, String> reqMap=new HashMap<>();
			reqMap.put("captchaCode", captchaCode);
			reqMap.put("userCaptchaCode", userCaptchaCode);
			reqMap.put("loginName", loginName);
			reqMap.put("loginPwd", loginPwd);	
			reqMap.put("overTime", overTime.toString());
			
			//调用登录业务方法
			ReciveMsg<Map<String, Object>> reciveMsg =merchUserService.loginValidate(reqMap);
			
			//当登陆成功后，设置自动登录、同时登陆控制
			if ("SUCCESS".equals(reciveMsg.getResult_code()))
			{
				Map<String, Object> map = reciveMsg.getData();
				if (autoLogin)
				{
					//取得配置文件中自动登陆的时间长度，并从天转化为秒
					Integer autoDate = 86400*Integer.valueOf(UtilsConfig.getConfig("login.auto.time", "sys"));
					session.setAttribute("tokenLog", this.setToken(req, response, autoDate));
					session.setMaxInactiveInterval(autoDate);
				} else
				{
					session.setAttribute("tokenLog", null);
				}
				session.setAttribute("merchUser", (MerchUser)map.get("merchUser"));
				//加入到自动登录和同时登录限制的MAP
				MySessionContext.AddSession(session);
				//减少返回前端的数据量
				map.remove("merchUser");
				reciveMsg.setData(map);
			}
			
			//返回数据到前端
			return reciveMsg;
		} catch (Exception e)
		{
			e.printStackTrace();
			return new ReciveMsg<Map<String,Object>>(0, "", "系统错误，请待会再尝试", null);
		}
		
	}
	
	/**
	 * 函数功能说明 ： 注销.
	 * 
	 * @参数： @return
	 * @return String
	 * @throws
	 */
	@RequestMapping("/logout")
	public String logout(HttpServletRequest req,Model model,HttpServletResponse rep) {
		//清除登录的session和cookie
		this.cleanSession(req, rep);
		this.cleanCookie(req, rep);
		return "/login/login";
	}


	/**
	 * 函数功能说明 ：进入登录页面.
	 * 
	 * @参数： @param request
	 * @参数： @param model
	 * @参数： @return
	 * @return String
	 * @throws PermissionException
	 */
	@RequestMapping("/")
	public String welcome(HttpServletRequest req,HttpServletResponse rep, Model model) {
		//取得请求中同时登录的标识
		String deleteFlag = StringUtils.isBlank(req.getParameter("deleteFlag"))?null:req.getParameter("deleteFlag");
		//将标识返回前端
		req.setAttribute("deleteFlag", deleteFlag);
		return "login/login";
	}

	/**
	 * 函数功能说明 ：商户注册页面跳转.
	 * 
	 * @参数： @param request
	 * @参数： @param model
	 * @参数： @return
	 * @return String
	 * @throws PermissionException
	 */
	@RequestMapping("/merchantregister")
	public String merchRegister(HttpServletRequest req, Model model) {
		//取得配置文件中设定的短信验证码发送间隔时长
		Integer sendCodeTime = Integer.valueOf(UtilsConfig.getConfig("login.sendcode.time", "sys"));
		req.setAttribute("sendCodeTime", sendCodeTime);
		return "login/merchantregister";
	}
	/**
	 * 函数功能说明 ：商户注册
	 * 
	 * @参数： @param request
	 * @参数： @param model
	 * @参数： @return
	 * @return String
	 * @throws PermissionException
	 */
	@RequestMapping("/register")
	public @ResponseBody ReciveMsg<Map<String, Object>> register(HttpServletRequest req, Model model) {
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
			
			//取得session中的验证码信息
			HttpSession session=req.getSession();
			String sysPhoneCode=StringUtils.isBlank((String)session.getAttribute("sysPhoneCode"))?null
					:(String)session.getAttribute("sysPhoneCode");
			
			//组装业务请求的数据
			Map<String, String> reqMap=new HashMap<>();
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
	 * 函数功能说明 ：发送短信验证码
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping("/phoneCode")
	public @ResponseBody ReciveMsg<String> sendPhoneCode(HttpServletRequest req, Model model) {
		try
		{
			//取得前端请求传递的参数
			String 	phone=StringUtils.isBlank(req.getParameter("phone"))?null:req.getParameter("phone");
			Integer	type=StringUtils.isBlank(req.getParameter("type"))?2:Integer.valueOf(req.getParameter("type"));
			
			//取得该请求上次发送验证码（未使用）时间
			HttpSession session=req.getSession();
			Date sendDate=(Date)session.getAttribute("sendDate");
			
			//取得配置文件中设定的短信验证码发送间隔时长
			Integer sendCodeTime = Integer.valueOf(UtilsConfig.getConfig("login.sendcode.time", "sys"));
			//计算上次发送到目前的时间（秒）与间隔时长的差值
			Integer overTime=sendDate==null?0:sendCodeTime-DateUtils.getSecondDiff(new Date(), sendDate);
		
			//ok:发送成功；much:该号码发送验证码次数超出平台限制;no:发送失败;overtime值:再次发送的时间
			String 	message=null;
			Integer flag = 0;
			
			//当时间差值小于0时，发送验证码
			if (phone!=null&&ValidateUtils.isMobile(phone)&&overTime<=0)
			{
				//生成验证码
				SmsPhone smsPhone = new SmsPhone();
				String sysPhoneCode = SmsUtils.getSmsCode();
				//后台打印验证码
				System.out.println("sysPhoneCode========="+sysPhoneCode);
				//调用短信平台，发送验证码
				int redata = smsPhone.sendWithParam(phone,type,sysPhoneCode);
				if (redata == 1)
				{
					//取得配置文件中设定的短信验证码有效时长
					Integer phoneCodeTime = Integer.valueOf(UtilsConfig.getConfig("login.phonecode.time", "sys"));
					//将手机号码、验证码、发送时间、有限时长（秒）存在session中，用于验证时取出
					session.setAttribute("sysPhoneCode", sysPhoneCode+phone);								
					session.setAttribute("sendDate", new Date());					
					session.setMaxInactiveInterval(phoneCodeTime);
					message="ok";
					flag = 1;
				}else if(redata == 2){
					//该号码发送验证码次数超出平台限制
					message = "much";
				}else{
					//发送失败
					message = "no";
				}			
			}else{
				//等待（overTime值）秒后才能请求发送短信验证码
				message=overTime.toString();
			}
			//返回数据到前端
			return new ReciveMsg<String>(flag, null, message, null);
		} catch (Exception e)
		{
			e.printStackTrace();			
			return new ReciveMsg<String>(0, null, "no", null);
		}
	}
	
	/**
	 * 用于验证数据库中注册手机是否已经存在
	 * @param loginName
	 * @param userId
	 * @return
	 */
	@RequestMapping("/checkphone")
	public @ResponseBody ReciveMsg<String> checkExist(HttpServletRequest req, Model model) {
		// 数据同步的开关
		String toggle = SyncDataToggle.getToggle();
		Integer flag = 0;
		try
		{
			boolean on = toggle.equals("1");
			String 	phone=StringUtils.isBlank(req.getParameter("phone"))?null:req.getParameter("phone");	        
	        if(phone != null){
	        	MerchUser user = merchUserService.findMerchUserByLoginName(phone);
	        	if (user==null&&!(on&& this.syncUser(phone, req)))
				{   // 未注册过,则调用招手猫app接口,且调用招手猫同步方法未获得数据
	        		flag = 1;
				}
			}
	        return new ReciveMsg<String>(flag,null, flag.toString(), null);
		} catch (Exception e)
		{
			e.printStackTrace();			
			return new ReciveMsg<String>(0, null, "-1", null);
		}
		
    }
	
	/**
	 * 同步招手猫的商户数据到商户用户表中
	* @param 
	* @return Map<String,Object>
	* @throws
	 */
	public boolean syncUser(String phone,HttpServletRequest request){
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		String url = "http://api.dev.ychpos.com/extApi/getShopsDataByPhone";
		Map<String,String> param = new HashMap<String,String>();
		param.put("phone", phone);
		// 生成的token由手机号和时间参数字符串拼接而成
		String tokenStr = phone+DateUtils.getYMDHString();
		// 先一层SHA1加密后再进行MD5加密后再将整个字符串转为大写格式
		String shalStr = Sha1Utils.getSha1(tokenStr);
		tokenStr = MD5Utils.md5Pwd(shalStr).toUpperCase();
		param.put("token", tokenStr);
		String result = null;
		try{
			result = HttpRequest.sendPost(url, param);
			JSONObject object = JSONObject.parseObject(result);
			MerchData merchData = object.toJavaObject(MerchData.class);
			String message = merchData.getMessage();
			if(message != null && "ok".equals(message)){
				String smart_key = merchData.getData().getSmart_key();
				String pwd = merchData.getData().getPass();
				//同步商户基本信息
				Merchant merch = new Merchant();
				merch.setMd5Key(MD5Utils.genRandomNum(36));
				merch.setInfoFrom("merch");
				merch.setAuditStatus(0);
				merch.setProv(null);
				merch.setCreatedAt(new Date());
				merch.setCity(null);
				String merchName = "招手猫商户-"+ DateUtils.getNowTime();
				merch.setRegisterName(merchName);
				merch.setMerchantName(merchName);
				merch.setArea(null);
				merch.setAddress(merchData.getData().getAddress());
				String merchantNo = "99991"+System.currentTimeMillis();
				// 设置为已完善商户资料状态
				merch.setMerchantNo(merchantNo);
				merch.setCardName(null);
				merch.setCardNo(null);
				merch.setIndustryTypeId(0);
				merch.setTel(null);
				merch.setEmail(null);
				this.MerchService.addMerch(merch);
				
				int merId = merch.getId();
				// 保存商户银行账户信息、附近信息，只需要设置主外键关联就可以,其余字段都设置为空,在用户需要更改商户资料时自己填写
				MerchantAccount account = new MerchantAccount();
				account.setMerId(merId);
				account.setAccBank(null);
				account.setAccSubBank(null);
				account.setAccNo(null);
				account.setAccName(null);
				account.setProv(null);
				account.setCity(null);
				account.setCreateTime(new Date());
				this.MerchService.addMerchAcc(account);
				
				int [] attachIds = new int[5]; // 附近id
				for(int i=0; i<5; i++){
					SysAttach sysAttach = new SysAttach();
					sysAttach.setFileName(null);
					sysAttach.setFileSuffix(null);
					sysAttach.setFileSize(0);
					sysAttach.setSavePath(null);
					sysAttach.setSaveName(null);
					sysAttach.setCreateTime(new Date());
					// 保存附近
					this.MerchService.addSysAttach(sysAttach);
					int id = sysAttach.getId();
					attachIds[i] = id;
				}
				MerchAttach merchAttach = new MerchAttach();
				merchAttach.setMerId(merId);
				merchAttach.setCardNo(attachIds[0]);
				merchAttach.setCardBackNo(attachIds[1]);
				merchAttach.setBusiLicense(attachIds[2]);
				merchAttach.setCyLicense(attachIds[3]);
				merchAttach.setMerLogo(attachIds[4]);
				// 保存商户附件
				this.MerchService.addMerchAttach(merchAttach);
				
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("userName", null);
				map.put("loginName", phone);
				map.put("loginPwd", pwd);
				map.put("phone", phone);
				map.put("userType", 1);
				// merchantNo为空时该商户为未完善资料状态
				map.put("merchantNo", null);
				map.put("storeNo", null);
				map.put("userStatus", "1");
				map.put("createTime", new Date());
				map.put("orgNo", null);
				map.put("pwdSalt", smart_key);
				this.sysCashierService.syncUser(map);
				return true;
			}else{
				return false;
			}
		}catch(IOException e) {
			e.printStackTrace();
			jsonMap.put("error", Code.EXCEPTION+":{"+e.getMessage()+"}");
			return false;
		}
	}
	
	/**
	 * 函数功能说明 ：忘记密码页面跳转.
	 * 
	 * @参数： @param request
	 * @参数： @param model
	 * @参数： @return
	 * @return String
	 * @throws 
	 */
	@RequestMapping("/login/passwordfound")
	public String goPasswordFound(HttpServletRequest req, Model model) {
		//取得配置文件中设定的短信验证码发送间隔时长
		Integer sendCodeTime = Integer.valueOf(UtilsConfig.getConfig("login.sendcode.time", "sys"));
		req.setAttribute("sendCodeTime", sendCodeTime);
		return "login/passwordfound";
	}
	/**
	 * 函数功能说明 ：忘记密码页面手机验证码验证.
	 * 
	 * @参数： @param request
	 * @参数： @param model
	 * @参数： @return
	 * @return String
	 * @throws 
	 */
	@RequestMapping("/passwordfound")
	public  @ResponseBody ReciveMsg<Map<String, Object>> passwordFound(HttpServletRequest req, Model model) {
		//取得前端请求参数
		String phone=StringUtils.isBlank(req.getParameter("phone"))?null:req.getParameter("phone");
		String phoneCode=StringUtils.isBlank(req.getParameter("phoneCode"))?null:req.getParameter("phoneCode");
		
		//取得服务器session中保存的手机号和验证码
		HttpSession session=req.getSession();		
		String sysPhoneCode=StringUtils.isBlank((String)session.getAttribute("sysPhoneCode"))?null
				:(String)session.getAttribute("sysPhoneCode");
		
		//组装业务请求数据
		Map<String, String> reqMap=new HashMap<>();
		reqMap.put("phone", phone);
		reqMap.put("phoneCode", phoneCode+phone);
		reqMap.put("sysPhoneCode", sysPhoneCode);
		
		//调用验证码，手机号码校验业务方法
		ReciveMsg<Map<String, Object>> reciveMsg=merchUserService.foundPwdValidate(reqMap);
		if ("SUCCESS".equals(reciveMsg.getResult_code()))
		{
			Map<String, Object> map = reciveMsg.getData();
			//取得配置文件中设定的每次登陆可操作页面的最大时间
			Integer staticTime = 3600*Integer.valueOf(UtilsConfig.getConfig("login.static.time", "sys"));
			session.setAttribute("sysPhoneCode", "");
			session.setMaxInactiveInterval(staticTime);
			session.setAttribute("merchUser",(MerchUser)map.get("merchUser"));
			//减少返回前端的数据
			map.remove("merchUser");
			reciveMsg.setData(map);
			session.setAttribute("phone",phone);
		}
		
		//返回数据到前端
		return reciveMsg;
	}
	
	/**
	 * 函数功能说明 ：忘记密码页面跳转下一步.
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping("/login/passwordfoundnext")
	public String goPasswordfoundnext(HttpServletRequest req, Model model) {
		HttpSession session=req.getSession();
		String phone=StringUtils.isBlank((String)session.getAttribute("phone"))?null:(String)session.getAttribute("phone");
		req.setAttribute("phone", phone);
		return "login/passwordfoundnext";
	}
	/**
	 * 函数功能说明 ：忘记密码页面更改密码.
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping("/passwordfoundnext")
	public  @ResponseBody ReciveMsg<Map<String, Object>> passwordFoundNext(HttpServletRequest req, Model model) {
		//取得前端传递的数据
		String loginPwd=StringUtils.isBlank(req.getParameter("loginPwd"))?null:req.getParameter("loginPwd");
		String password=StringUtils.isBlank(req.getParameter("password"))?null:req.getParameter("password");
		HttpSession session=req.getSession();		
		String phone=StringUtils.isBlank((String)session.getAttribute("phone"))?null:(String)session.getAttribute("phone");
		MerchUser merchUser=session.getAttribute("merchUser")==null?null:(MerchUser)session.getAttribute("merchUser");
		
		//组装业务请求数据
		Map<String, Object> reqMap=new HashMap<>();
		reqMap.put("phone", phone);
		reqMap.put("loginPwd", loginPwd);
		reqMap.put("password", password);
		reqMap.put("merchUser", merchUser);
		
		//调用修改密码业务方法
		ReciveMsg<Map<String, Object>> reciveMsg = merchUserService.changePwd(reqMap);
		
		if ("SUCCESS".equals(reciveMsg.getResult_code()))
		{
			Map<String, Object> map = reciveMsg.getData();
			//取得配置文件中设定的每次登陆可操作页面的最大时间
			Integer staticTime = 3600*Integer.valueOf(UtilsConfig.getConfig("login.static.time", "sys"));
			session.setMaxInactiveInterval(staticTime);
			session.setAttribute("merchUser",(MerchUser)map.get("merchUser"));
			// 查询商户用户数据
			SysCashier sysCashier = sysCashierService.serlectByParam(phone);
			String pwdSalt = sysCashier.getPwdSalt();
			
			String toggle = SyncDataToggle.getToggle();
			// 开启数据同步接口
			if(toggle.equals("1")){
				this.syncPwd(phone, MD5Utils.md5Pwd(loginPwd+pwdSalt));
			}
			// 修改密码成功后清空账号登录失败记录
			this.sysCashierService.delLoginRecord(phone);
			//减少返回前端的数据
			map.remove("merchUser");
			reciveMsg.setData(map);			
		}
				
		//返回数据到前端
		return reciveMsg;
	}
	
	
	/**
	 * 同步修改招手猫商户密码
	* @param 
	* @return Map<String,Object>
	* @throws
	 */
	public void syncPwd(String loginName,String loginPwd){
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		String url = "http://api.dev.ychpos.com/extApi/resetShopsPwd";
		Map<String,String> param = new HashMap<String,String>();
		param.put("phone", loginName);
		param.put("pass", loginPwd);
		// 生成的token由手机号和时间参数字符串拼接而成
		String tokenStr = loginName+DateUtils.getYMDHString();
		// 先一层SHA1加密后再进行MD5加密后再将整个字符串转为大写格式
		String shalStr = Sha1Utils.getSha1(tokenStr);
		tokenStr = MD5Utils.md5Pwd(shalStr).toUpperCase();
		param.put("token", tokenStr);
		String result = null;
		try{
			result = HttpRequest.sendPost(url, param);
			JSONObject object = JSON.parseObject(result);
			MerchData merchData  = object.toJavaObject(MerchData.class);
			String message = merchData.getMessage();
			if(message != null && "ok".equals(message)){
				System.out.println("同步修改招手猫商户"+loginName+"的密码成功");
				return;
			}else{
				throw new BizException("同步修改招手猫商户"+loginName+"的密码时未成功");
			}	
		}catch(IOException e) {
			e.printStackTrace();
			jsonMap.put("error", Code.EXCEPTION+":"+e.getMessage());
			return;
		}
	}

	/**
	 * 清除自动登录的cookie
	 * @param request
	 * @param response
	 */
	private void cleanCookie(HttpServletRequest req, HttpServletResponse rep){
		Cookie c = new Cookie("auto","");
	    c.setMaxAge(0);
	    c.setPath(req.getContextPath());
	    //保存cookie
	    rep.addCookie(c);
	}
	
	/**
	 * 清除自动登录的session
	 * @param req
	 * @param rep
	 */
	private void cleanSession(HttpServletRequest req, HttpServletResponse rep){
		Cookie[] cs = req.getCookies();
		String auto = null;
		HttpSession session = req.getSession();
		//销毁本请求session
		session.invalidate();
		for(Cookie c:cs){
			if(c.getName().equals("auto")){
				//如果存在自动登录的cookie
				auto =StringUtils.isBlank(c.getValue())?null:c.getValue();//用户token
				break;
			}
		}
		if (auto != null)
		{
			String[] str = auto.split(",");
	        String sessionId = str[0];
	        //将自动登录的session取出并销毁
	        HttpSession sessionA = MySessionContext.getSession(sessionId);
	        if (sessionA != null)
			{
	        	sessionA.invalidate();
			}
		}
	}
	
	
	
	/**
	 * 生成自动登录的token
	 * @param request
	 * @param response
	 * @throws 
	 * @throws 
	 */
	public String setToken(HttpServletRequest request,HttpServletResponse response,Integer maxAge){
		String loginName=request.getParameter("loginName");
		String loginPwd=request.getParameter("loginPwd");
		HttpSession session = request.getSession();
		String id = session.getId();
	    //生成MD5加密的TOKEN
	    String token = MerchUserServiceImpl.md5Pwd(loginName+loginPwd+((Long)(new Date()).getTime()).toString());
	    String auto = id+"#"+token;
	     //声明并控制cookie寿命
	    Cookie c = new Cookie("auto",auto);
	    c.setMaxAge(maxAge);
	    c.setPath(request.getContextPath());
	     //保存cookie
	    response.addCookie(c); 
	    return token;
	}
}

