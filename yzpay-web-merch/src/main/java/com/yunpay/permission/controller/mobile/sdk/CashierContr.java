package com.yunpay.permission.controller.mobile.sdk;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.yunpay.common.core.exception.BizException;
import com.yunpay.common.core.utils.DateUtils;
import com.yunpay.common.core.utils.io.SyncDataToggle;
import com.yunpay.controller.common.BaseController;
import com.yunpay.h5merch.permission.entity.ReciveMsg;
import com.yunpay.h5merch.permission.entity.StoreMachine;
import com.yunpay.h5merch.permission.service.MerchUserService;
import com.yunpay.h5merch.service.impl.SysCashierServiceImpl;
import com.yunpay.permission.controller.MerchInfoContr;
import com.yunpay.permission.controller.mobile.sdk.entity.MerchData;
import com.yunpay.permission.entity.LoginFailRecord;
import com.yunpay.permission.entity.MerchAttach;
import com.yunpay.permission.entity.Merchant;
import com.yunpay.permission.entity.MerchantAccount;
import com.yunpay.permission.entity.StoreEntity;
import com.yunpay.permission.entity.SysAttach;
import com.yunpay.permission.entity.SysCashier;
import com.yunpay.permission.service.AttachmentService;
import com.yunpay.permission.service.MerchService;
import com.yunpay.permission.service.StoreService;
import com.yunpay.permission.shiro.filter.MySessionContext;
import com.yunpay.permission.utils.AppDupLog;
import com.yunpay.permission.utils.HttpRequest;
import com.yunpay.permission.utils.MD5Utils;
import com.yunpay.permission.utils.Sha1Utils;
@Controller  
@RequestMapping("/cashierContr")
public class CashierContr extends BaseController { 
	/**
	 * 
	 * 文件名称:
	 * 内容摘要:  聚合支付APP收银员登录接口
	 * @author:   duan zhang quan
	 * @version:  1.0  
	 * @Date:     2017年4月14日上午9:56:12 
	 * 修改历史:  
	 * 修改日期                     修改人员                                   版本	            修改内容  
	 * ----------------------------------------------  
	 * 2017年4月14日     duan zhang quan   1.0     新建
	 *
	 * 版权:   版权所有(C)2017
	 * 公司:   深圳市至高通信技术发展有限公司
	 **/
	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(CashierContr.class);
	@Autowired
	private SysCashierServiceImpl sysCashierService;
	@Autowired
	private AttachmentService attachmentService;
	@Autowired
	private  MerchService MerchService;
	@Autowired
	private MerchUserService merchUserService;
	@Autowired
	private StoreService  storeService;
	/**
	* @param  
	* @return Map<String,Object>
	* @throws
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/mobile/login")
	private @ResponseBody ReciveMsg login(HttpServletRequest request, HttpServletResponse response, String userName,
		String password,String machineNo,String channel) throws JsonProcessingException {
		String loginName = userName;
		String loginPwd = password;
		SysCashier sysCashier = null;
		try {
			if (StringUtils.isEmpty(userName)) {
				return new ReciveMsg(0,"4000","userName参数不能为空.",null);
			}
			if(StringUtils.isEmpty(password)){
				return new ReciveMsg(0,"4000","password参数不能为空.",null);
			}
			if( StringUtils.isEmpty(machineNo)){
				return new ReciveMsg(0,"4000","machineNo参数不能为空.",null);
			}
			// 用户登录
			sysCashier = sysCashierService.serlectByParam(loginName);
			// 该用户不存在时，则调用招手猫app的数据同步接口,并保存到系统中
			if (sysCashier == null) {
				String toggle = SyncDataToggle.getToggle();
				// 开启数据同步接口
				if(toggle.equals("1")){
					return this.syncUser(loginName, loginPwd, request, machineNo, response);
				}else{
					//账号不存在时的提示
					return new ReciveMsg(0,"4006","账号或密码错误.",null);
				}
			}
			else{
				boolean pwdIsTrue = sysCashier.getLoginPwd().equals(MD5Utils.md5Pwd(loginPwd+sysCashier.getPwdSalt()));
				return this.accountToggle(request, response, loginName, loginPwd, machineNo, channel, pwdIsTrue, sysCashier);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ReciveMsg(0,"5000","服务器内部异常",null);
		}
	}
	
	
	/**
	 * 同步招手猫的商户数据到商户用户表中
	* @param 
	* @return Map<String,Object>
	* @throws
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ReciveMsg syncUser(String loginName,String loginPwd,HttpServletRequest request,String machineNo,HttpServletResponse response){
		String url = "http://api.dev.ychpos.com/extApi/getShopsDataByPhone";
		Map<String,String> param = new HashMap<String,String>();
		param.put("phone", loginName);
		// 生成的token由手机号和时间参数字符串拼接而成
		String tokenStr = loginName+DateUtils.getYMDHString();
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
				String phone = merchData.getData().getPhone();
				String pwd = merchData.getData().getPass();
				String md5Pwd = MD5Utils.md5Pwd(loginPwd+smart_key);
				if(md5Pwd.equals(pwd)){
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
					map.put("merchantNo", merchantNo);
					map.put("storeNo", null);
					map.put("userStatus", "1");
					map.put("createTime", new Date());
					map.put("orgNo", null);
					map.put("pwdSalt", smart_key);
					// 保存用户信息
					this.sysCashierService.syncUser(map);
					
					Map<String, Object> sysCashierMap = new HashMap<String, Object>();
					Map<String,Object> json = new HashMap<String,Object>();
					sysCashierMap.put("loginName", phone);//登录名
					sysCashierMap.put("phone", phone);//手机号码
					sysCashierMap.put("merchPhone", phone);//商户手机号
					sysCashierMap.put("userType", "1");//用户类型
					sysCashierMap.put("role", "老板");//账户角色
					sysCashierMap.put("md5Key",smart_key); // 商户密钥
					sysCashierMap.put("approveStatus","未认证");
					sysCashierMap.put("userStatus", "1");//用户状态
					sysCashierMap.put("merchantName", merchName); // 商户名称（公司名称）
					this.responseToken(request, response, sysCashierMap);
					json.put("user", sysCashierMap);
					json.put("bind", "yes"); 
					json.put("url", "http://siecompay.com/merch/");
					// 查询门店和该设备是否绑定
					StoreMachine storeMachine = sysCashierService.serlectStoreMachine(machineNo);
					// 用户未绑定门店机器号则返回门店数据
					if(storeMachine == null){
						json.put("bind", "no");
						 // 商户未添加门店时,app提示未添加门店,无法操作
						json.put("stores",null);
					}
					else{
						// 如果该设备已绑定了某一个商户下的一个门店,则不允许其他商户的用户登录该设备,禁止该用户跨商户登录
						return new ReciveMsg(0,"4004","你不属于该商户,无权限登录.",null);
					}
					return new ReciveMsg(1,"","",json);
				}else{
					return new ReciveMsg(0,"4006","账号或密码错误.",null);
				}
			}else{
				return new ReciveMsg(0,"4006","账号或密码错误.",null);
				
			}
			
		}catch(IOException e) {
			e.printStackTrace();
			return new ReciveMsg(0,"500","服务器内部异常.",null);
		}
	}
	
	
	
	/**
	 * 
	 * 账号保护的开关,判断账号是否锁定.
	 * 当日连续5次都密码错误,则锁定该账号,需要人为修改密码才能解锁
	 * 
	 * 1: 账号锁定时，拒绝用户登录
	 * 2：账号未锁定时判断密码是否正确
	 * 		a：密码正确则允许登入系统
	 * 		b: 密码不正确时则记录错误次数,当连续错误5次时锁定该账号
	 * 		
	* @param 
	* @return ReciveMsg
	* @throws
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ReciveMsg accountToggle(HttpServletRequest req,HttpServletResponse rep,String loginName,String pwd,String machineNo,
			String channel, boolean pwdIsTrue,SysCashier sysCashier){
		try{
			String nowDate = DateUtils.getReqDate(new Date());
			boolean isLock = this.sysCashierService.isLock(loginName);
			if(isLock){
				return new ReciveMsg(0,"6020","账号或密码错误次数过多已被锁定,请修改密码后重新登录.",null);
			}
			else{
				// 密码正确则允许登录,清空之前的登录失败记录
				if(pwdIsTrue){
					this.sysCashierService.delLoginRecord(loginName);
					return this.loginIn(loginName, pwd, machineNo, channel, sysCashier, req, rep);
				}
				// 密码错误则保存登录日期、密码错误次数
				else{
					int userId = sysCashier.getId();
					int loginFailCount = 0;
					// 获取用户登录失败的记录
					LoginFailRecord record = this.sysCashierService.getUserLoginFailRecord(loginName,nowDate);
					record = LoginFailRecord.counter(record, userId, nowDate);
					loginFailCount = record.getLoginFailCount();
					this.sysCashierService.saveErrorRecord(record);
					if(loginFailCount <5){
						return new ReciveMsg(0,"6020","账号或密码错误,还剩"+(5-loginFailCount)+"次机会",null);
					}
					else{
						return new ReciveMsg(0,"6020","账号或密码错误次数过多已被锁定,请修改密码后重新登录.",null);
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			return new ReciveMsg(0,"500","服务器内部异常.",null);
		}
	}
	
	
	

	/**
	 * 
	* 账户未锁定、密码输入正确时允许用户登入系统
	* @param 
	* @return ReciveMsg
	* @throws
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ReciveMsg loginIn(String loginName,String loginPwd,String machineNo,String channel,SysCashier sysCashier,
			HttpServletRequest request,HttpServletResponse response){
		try{
			// 每次登录时都销毁之前的session
			HttpSession session = request.getSession();
			session.invalidate();
			Map<String,Object> json = new HashMap<String,Object>();
			Merchant sysMerchant = null;
			
			String merchantNo = sysCashier.getMerchantNo();
			if(StringUtils.isEmpty(merchantNo)){
				return new ReciveMsg(0,"4002","未完善商户资料.",null);
			}
			// 查询所属商户
			sysMerchant = this.sysCashierService.selectMerchByOrgNo(merchantNo);
			String md5Key = null;
			int merId = 0;
			int auditStatus = 0;
			if(sysMerchant != null){
				md5Key = sysMerchant.getMd5Key();
				auditStatus = sysMerchant.getAuditStatus();
				merId = sysMerchant.getId();
			}
			else{
				return new ReciveMsg(0,"4003","商户不存在",null);
			}
			SysCashier merchUser = this.sysCashierService.getMerchUser(merchantNo);
			String merchPhone = null;
			if(merchUser != null){
				merchPhone =  merchUser.getPhone();
			}
			String userType = sysCashier.getUserType();
			// 更新收银员登录时间
			String date = DateUtils.getTimeStampStr(new Date());
			sysCashierService.updateCashier(sysCashier.getId(), date);
			
			Map<String, Object> sysCashierMap = new HashMap<String, Object>();
			sysCashierMap.put("id", sysCashier.getId());//用户编号
			sysCashierMap.put("userName", sysCashier.getUserName());//用户名称
			sysCashierMap.put("loginName", sysCashier.getLoginName());//登录名
			sysCashierMap.put("phone", sysCashier.getPhone());//手机号码
			sysCashierMap.put("merchPhone",merchPhone);//商户手机号
			sysCashierMap.put("userType", sysCashier.getUserType());//用户类型
			String role = null;
			switch(sysCashier.getUserType()){
			case "1":
				role = "老板";
				break;
			case "2":
				role = "店长";
				break;
			case "3":
				role = "店员";
				break;
			}
			sysCashierMap.put("role", role);  // 账户角色(老板、店长、店员)
			sysCashierMap.put("md5Key",md5Key); // 商户密钥
			// 店长和店员角色
			if(!userType.equals("1")){
				StoreEntity sysStore = this.sysCashierService.selectStoreByOrgNo(sysCashier.getStoreNo());
				sysCashierMap.put("storeNo", sysCashier.getStoreNo());//门店编码
				sysCashierMap.put("storeName", sysStore.getStoreName());//门店名称
				sysCashierMap.put("contactMan", sysStore.getContactMan());//门店联系人
			}
			// 老板角色 返回商户认证状态:0：待审核，1：审核通过，2：驳回，3：驳回后二次申请,审核中
			else{
				String approveStatus = "";
				if(auditStatus == 0){	approveStatus = "未认证"; }
				else if(auditStatus == 1){ approveStatus = "已认证"; }
				else if(auditStatus == 2){ approveStatus = "认证中"; }
				else if(auditStatus == 3){ approveStatus = "认证中"; }
				else{ approveStatus = "未认证";}
				// 查询门店和机器是否绑定
				StoreMachine storeMachine = sysCashierService.serlectStoreMachine(machineNo);
				if(storeMachine != null){
					// 查询该设备绑定的门店
					StoreEntity s = this.sysCashierService.selectStoreByOrgNo(storeMachine.getStoreNo());
					sysCashierMap.put("storeNo",s.getStoreNo());//门店编码
					sysCashierMap.put("storeName", s.getStoreName());//门店名称
					sysCashierMap.put("contactMan", s.getContactMan());//门店联系人
					
				}
				sysCashierMap.put("approveStatus",approveStatus);
			}
			sysCashierMap.put("userStatus", sysCashier.getUserStatus());//用户状态
			sysCashierMap.put("createTime", sysCashier.getCreateTime());//创建时间
			sysCashierMap.put("orgNo", sysCashier.getOrgNo());//组织机构
			sysCashierMap.put("cashierNo", sysCashier.getLoginName());//收银员编号
			sysCashierMap.put("merchantNo",sysCashier.getMerchantNo()) ; // 商户号
			sysCashierMap.put("merchantName", sysMerchant.getRegisterName()); // 商户名称（公司名称）
			this.responseToken(request, response, sysCashierMap);
			json.put("user", sysCashierMap);
			json.put("bind", "yes"); 
			json.put("url", "http://siecompay.com/merch");
			
			List<StoreEntity> storeList = null;
			// 查询该设备是否已绑定过门店
			StoreMachine storeMachine = sysCashierService.serlectStoreMachine(machineNo);
			// 未绑定门店机器号则返回门店数据
			if(storeMachine == null){
				json.put("bind", "no");
				// 店长和店员返回自己所属的门店数据
				if(!userType.equals("1")){
					String storeNo = sysCashier.getStoreNo();
					storeList = new ArrayList<StoreEntity>();
					StoreEntity storeEntity = this.sysCashierService.selectStoreByOrgNo(storeNo);
					storeList.add(storeEntity);
					json.put("stores",storeList);
				}
				else{
					// 老板返回商户下的所有门店数据
					storeList = sysCashierService.selectStoreByMerId(merId);
					if(storeList == null){
						// 商户未添加门店时,app提示未添加门店，无法操作
						json.put("stores",null);
					}else{
						json.put("stores",storeList);
					}
				}
			}
			// 如果该设备已绑定了某一个商户下的一个门店
			else{
				String storeNo = storeMachine.getStoreNo();
				StoreEntity storeEntity = this.sysCashierService.selectStoreByOrgNo(storeNo);
				if(userType.equals("1")){
					// 如果该设备已绑定了某一个商户下的一个门店,则不允许其他商户的用户登录该设备,禁止该用户跨商户登录
					if(!String.valueOf(merId).equals(storeEntity.getMerId().toString())){
						return new ReciveMsg(0,"4004","你不属于该商户,无权限登录",null);
					}
				}else{
					// 查询该用户属于哪一个门店
					String userStoreNo = sysCashier.getStoreNo();
					// 如果该用户不属于这个设备绑定的门店,则禁止该用户跨门店登录
					if(!userStoreNo.equals(storeNo)){
						return new ReciveMsg(0,"4005","你不属于该门店,无权限登录",null);
					}
				}
			}
			//保存登录状态
			Map<String, String> sessionMap = new HashMap<>();
			sessionMap.put("loginName", loginName);
			sessionMap.put("status", "1");
			sessionMap.put("channel", channel);
			HttpSession newSession = request.getSession();
			newSession.setAttribute("duplicate", sessionMap);
			System.out.println("lggg===="+newSession.getId());
			//登录session保存到全局MAP中
			MySessionContext.AddAppSession(newSession);
			return new ReciveMsg(1,"","",json);
		}catch(Exception e){
			e.printStackTrace();
			return new ReciveMsg(0,"500","服务器内部异常.",null);
		}
	}
	
	
	
	/**
	 * 
	* 返回token到app
	* @param 
	* @return void
	* @throws
	 */
	public void responseToken(HttpServletRequest request,HttpServletResponse response,Map<String,Object> sysCashierMap){
		 // token有效期 15天 + 2小时
		int maxAge = (86400 * 15) +  (3600 * 2); 
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(maxAge);
		String token = session.getId();
		sysCashierMap.put("token", token);
		Cookie cookie = new Cookie("JSESSIONID",token);
		cookie.setPath(request.getContextPath()+"/");
		cookie.setMaxAge(maxAge);
		response.addCookie(cookie);
		session.setAttribute("token", token);
	}
	

	
	// 门店和机器号绑定
	@RequestMapping(value = "/bindMachineNo")
	public @ResponseBody ReciveMsg<StoreEntity> savaStoreMachineNo(String storeNo,String machineNo,HttpServletRequest request,HttpServletResponse response){
		if (AppDupLog.checkDup(request) == 0){
			if(StringUtils.isEmpty(storeNo)){
				ReciveMsg<StoreEntity> reciveMsg = new ReciveMsg<StoreEntity>(0,"4000","storeNo参数不能为空.",null);
				return reciveMsg;
			}
			if(StringUtils.isEmpty(machineNo)){
				ReciveMsg<StoreEntity> reciveMsg = new ReciveMsg<StoreEntity>(0,"4000","machineNo参数不能为空.",null);
				return reciveMsg;
			}
			/*if(storeService.selectByStoreNo(storeNo) == null){
				ReciveMsg<StoreEntity> reciveMsg = new ReciveMsg<StoreEntity>(0,"4006","非法操作,门店号"+storeNo+"不存在",null);
				return reciveMsg;
			}*/
			try{
				this.sysCashierService.saveStoreMachineNo(storeNo, machineNo, new Date(), 1);
				StoreEntity s = this.sysCashierService.selectStoreByOrgNo(storeNo);
				ReciveMsg<StoreEntity> reciveMsg = new ReciveMsg<StoreEntity>(1,"","",s);
				return reciveMsg;
			}catch(Exception e){
				ReciveMsg<StoreEntity> reciveMsg = new ReciveMsg<StoreEntity>(0,"5000","服务器内部异常.",null);
				return reciveMsg;
			}

		}else{
			//同时登录控制
			ReciveMsg<StoreEntity> reciveMsg = new ReciveMsg<StoreEntity>(0,"6020","The user account has been logged on to another machine",null);
			return reciveMsg;
		}
	}
	
	

	/**
	 * 显示证件,以a标签的href方式显示
	* @param 
	* @return void
	* @throws
	 */
	@RequestMapping("/showAttach")
	public void  showAttach(HttpServletRequest req,HttpServletResponse rep){
		// 获取serlvet容器绝对路径
		String containerPath = MerchInfoContr.class.getResource("/").getPath(); 
		String noneAttach = containerPath+"/image/userphoto.png";
		// 得到附件id
		int attachId = 0;
		if(StringUtils.isEmpty(req.getParameter("id")) ){
			attachId = 0;
		}
		else{
			attachId = Integer.parseInt(req.getParameter("id"));
		}
		String attachDir = null;
		if(attachId != 0){
			SysAttach attach = this.attachmentService.getAttachById(attachId);
			// 获取附件的保存目录
			attachDir = attach.getSavePath();
		}else{
			attachDir = noneAttach;
		}
		rep.setContentType("image/png");
		try{
		ServletOutputStream servletOutputStream = rep.getOutputStream();
		 //不在网页中打开，而是直接下载该文件，下载后的文件名为Example.pdf
        //response.setHeader("Content-disposition", "attachment;filename=Example.pdf");  
		File f = null;
		FileInputStream fis  = null; 
		byte[] buffer = new byte[1024*1024]; 
			f = new File(attachDir);
			if(!f.exists()){
				System.out.println("位于"+attachDir+"的附件不存在");
				return;
			}
			rep.setContentLength((int)f.length());
			fis = new FileInputStream(f);  
			@SuppressWarnings("unused")
			int readBytes = -1;
			while((readBytes = fis.read(buffer,0,1024*1024)) != -1){
				servletOutputStream.write(buffer,0,1024*1024);
			}
			servletOutputStream.flush();
			servletOutputStream.close();
			fis.close();
		}catch(FileNotFoundException e){
			throw new BizException("未能读取到证件信息", e);
		}catch(IOException e){
			throw new BizException("未能读取到证件信息", e);
		}	
	}
	
	
	/**
	 * 下载证件图片
	* 
	* @param 
	* @return void
	* @throws
	 */
	@RequestMapping("/downloadAttach")   
	public void downloadPic(HttpServletRequest req,HttpServletResponse rep){
		String agent = req.getHeader("USER-AGENT").toLowerCase();
		rep.setContentType("image/png");
		String fileName = "";
		String type = req.getParameter("type");
		if(type == null){
			throw new NullPointerException("type参数不能为空");
		}
		switch(type){
			case "1":
				fileName = "身份证正面";
				break;
			case "2":
				fileName = "身份证反面";
				break;
			case "3":
				fileName = "营业执照";
				break;
			case "4":
				fileName = "店铺logo";
				break;
			case "5":
				fileName = "餐饮许可证";
				break;
		}
		try{
			// 得到servlet的数据输出流,用于向客户端写入数据
			ServletOutputStream outputStream = rep.getOutputStream();
			int attachmentId = Integer.parseInt(req.getParameter("id"));  // 附件id,用于查询服务器中的唯一附件信息
			
			SysAttach sysAttach = this.attachmentService.getAttachById(attachmentId);
			if(sysAttach == null){
				throw new BizException("未能读取到证件信息,在表t_sys_attach中无法找到id为"+attachmentId+"的数据");
			}
			// 得到服务器中的图片的绝对路径
			String savaPath = sysAttach.getSavePath();
			String fileSuffix = sysAttach.getFileSuffix();
			
			// 设置文件下载的响应头
			if(agent.contains("firefox")){
				rep.setHeader("Content-disposition", "attachment;filename="+new String(fileName.getBytes(),"ISO8859-1")+"."+fileSuffix);
			}else{
				String encodeFileName = java.net.URLEncoder.encode(fileName,"utf-8");
				rep.setHeader("Content-disposition", "attachment;filename="+encodeFileName+"."+fileSuffix);
			}
			
			// 根据图片路径,构造File对象
			File file = new File(savaPath);
			if(!file.exists()){
				throw new FileNotFoundException("位于"+savaPath+"上的证件信息不存在");
			}
			rep.setContentLength((int)file.length());
			// 构造文件输入流,准备读取图片文件数据
			FileInputStream fileInputStream = new FileInputStream(file);
			// 每次读取1MB的数据到缓冲
			byte [] buffer = new byte[1024*1024];
			// 循环读取输入流中的数据,并写入客户端
			while(fileInputStream.read(buffer, 0, 1024 * 1024) != -1){
				outputStream.write(buffer, 0, 1024 * 1024);
			}
			// 关闭输出流,输出流
			outputStream.flush();
			outputStream.close();
			fileInputStream.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
	

	
	
}
