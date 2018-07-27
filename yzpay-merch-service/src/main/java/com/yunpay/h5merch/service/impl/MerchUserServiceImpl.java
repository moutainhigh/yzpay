package com.yunpay.h5merch.service.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunpay.common.core.utils.ValidateUtils;
import com.yunpay.h5merch.permission.dao.MerchUserDao;
import com.yunpay.h5merch.permission.entity.MerchUser;
import com.yunpay.h5merch.permission.entity.ReciveMsg;
import com.yunpay.h5merch.permission.service.MerchUserService;
/**
 * 
 * 类名称                     商户用户service接口实现
 * 文件名称:     MerchUserServiceImpl.java
 * 内容摘要: 
 * @author:   Zhao Xiaoman
 * @version:  1.0  
 * @Date:     2017年7月25日上午10:41:41
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年7月25日   Zhao Xiaoman       1.0       新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
@Service("merchUserService")
public class MerchUserServiceImpl implements MerchUserService {
	@Autowired
	private MerchUserDao merchUserDao;	
	
	@Override
	public ReciveMsg<Map<String, Object>> addUser(Map<String, String> reqMap)
	{
		String error=null;
		Integer flag = 0;
		Map<String, Object> map=new HashMap<>();
		ReciveMsg<Map<String, Object>> reciveMsg = null;
		
		if (!Boolean.parseBoolean(reqMap.get("agreement")))
		{
			error="请同意《至高通信商户协议》";
		}
		else if (reqMap.get("sysPhoneCode")==null
				||reqMap.get("phoneCode")==null
				||!reqMap.get("sysPhoneCode").equals(reqMap.get("phoneCode")))
		{
			error="手机验证码填写错误/已过期";
		}	
		else if (!ValidateUtils.isMobile(reqMap.get("phone")))
		{
			error="手机号码为空/格式不正确";
		}else if (StringUtils.isBlank(reqMap.get("openId")))
		{
			error="用户数据出错";
		}else if (reqMap.get("password")==null
				||reqMap.get("loginPwd")==null
				||!reqMap.get("password").equals(reqMap.get("loginPwd")))
		{
			error="两次输入的密码不一致";
		}else if(merchUserDao.findByLoginName(reqMap.get("phone"))!=null)
		{
			error="该手机号码已被注册";			
		}else{
			MerchUser merchUser=new MerchUser();
			String salt = (new SecureRandomNumberGenerator()).nextBytes().toHex();
			merchUser.setOpenId(reqMap.get("openId"));
			merchUser.setUserName(reqMap.get("userName"));
			merchUser.setLoginName(reqMap.get("phone"));
			//密码加密
			merchUser.setLoginPwd(md5Pwd(reqMap.get("loginPwd")+salt));
			merchUser.setPhone(reqMap.get("phone"));
			merchUser.setUserStatus("1");
			merchUser.setUserType("1");
			merchUser.setPwdSalt(salt);
			int redata=merchUserDao.insert(merchUser);
			if(redata<=0){
				error="注册失败，请过会再尝试";
			}else {
				flag = 1;
				merchUser.setPwdSalt(null);
				merchUser.setLoginPwd(null);
				map.put("merchUser", merchUser);
			}
		}
		reciveMsg = new ReciveMsg<Map<String, Object>>(flag, "", error, map);
		return reciveMsg;
	}
	
	@Override
	public ReciveMsg<String> updateMsg(MerchUser merchUser)
	{
		String error = null;
		Integer flag = 0;
		MerchUser user = this.findMerchUserById(merchUser.getId());
		if(user ==null){
			error = "该用户不存在";
		}else {
			if(merchUser.getLoginPwd() != null && merchUser.getLoginPwd() != ""){
				String loginPwd=md5Pwd(merchUser.getLoginPwd()+user.getPwdSalt());
				merchUser.setLoginPwd(loginPwd);
				merchUser.setLoginName(merchUser.getPhone());
			}
			Integer result = merchUserDao.updateMsg(merchUser);
			if (result >0)
			{
				flag = 1;
			}else {
				error = "修改失败，请过会再尝试";
			}
		}
		
		return new ReciveMsg<String>(flag, "", error, null);
	}
	
	@Override
	public List<MerchUser> listAllMerchUser() {
		return merchUserDao.listAllMerchUser();
	}

	@Override
	public MerchUser findMerchUserById(Integer id)
	{
		return merchUserDao.findById(id);
	}
	@Override
	public MerchUser findMerchUserByOpenId(String openId)
	{
		return merchUserDao.findByOpenId(openId);
	}

	@Override
	public MerchUser findMerchUserByLoginName(String loginName)
	{
		return merchUserDao.findByLoginName(loginName);
	}

	@Override
	public ReciveMsg<Map<String, Object>> loginValidate(Map<String, String> reqMap)
	{
		String error = null;
		Integer flag = 0;
		ReciveMsg<Map<String, Object>> reciveMsg = null;
		Map<String, Object> map=new HashMap<>();
		
		String captchaCode=reqMap.get("captchaCode");
		String userCaptchaCode=reqMap.get("userCaptchaCode");
		String loginName=reqMap.get("loginName");
		String loginPwd=reqMap.get("loginPwd");
		Integer overTime=Integer.parseInt(reqMap.get("overTime"));
		
		if (overTime<=0||captchaCode==null||userCaptchaCode==null||!captchaCode.equals(userCaptchaCode))
		{
			error = "验证码错误/过期";
		}else if (loginName==null||loginPwd==null)
		{
			error = "登录名/密码错误";
		}else{
			MerchUser user = merchUserDao.findByLoginName(reqMap.get("loginName"));		
			if (user==null)
			{
				error = "登录名/密码错误";
			}else{
				String loginPwdS = md5Pwd(loginPwd+user.getPwdSalt());
				if (!loginPwdS.equals(user.getLoginPwd()))
				{
					error = "登录名/密码错误";
				}else if(!"1".equals(user.getUserType())){
					error = "非商户类型账号";
				}else{
					flag = 1;
					user.setPwdSalt(null);
					user.setLoginPwd(null);
					map.put("merchUser", user);
					if (StringUtils.isNotBlank(user.getMerchantNo()))
					{
						map.put("url", "/sys/index/index");
					}else if(StringUtils.isBlank(user.getMerchantNo())){
						map.put("url", "/sys/index/indexundo");
					}
				}
			}
		}
		reciveMsg = new ReciveMsg<Map<String, Object>>(flag, "", error, map);
		return reciveMsg;
	}
	
	@Override
	public ReciveMsg<Map<String,Object>> foundPwdValidate(Map<String, String> reqMap)
	{
		String error = null;
		Integer flag = 0;
		Map<String, Object> map=new HashMap<>();
		ReciveMsg<Map<String, Object>> reciveMsg = null;
		
		String phone=reqMap.get("phone");
		String phoneCode=reqMap.get("phoneCode");
		String sysPhoneCode=reqMap.get("sysPhoneCode");
		if (phoneCode==null||sysPhoneCode==null||!sysPhoneCode.equals(phoneCode))
		{
			error = "手机验证码错误/过期";
		}else{
			MerchUser user = merchUserDao.findByPhone(phone);		
			if (user==null)
			{
				error = "该手机号码未注册";
			}else{
				user.setPwdSalt(null);
				user.setLoginPwd(null);
				map.put("merchUser", user);
				map.put("url", "/login/passwordfoundnext");
				flag = 1;
			}
		}
		reciveMsg = new ReciveMsg<Map<String,Object>>(flag, "", error, map);
		return reciveMsg;
	}

	@Override
	public ReciveMsg<Map<String,Object>> changePwd(Map<String, Object> reqMap)
	{
		String error=null;
		Integer flag = 0;
		ReciveMsg<Map<String, Object>> reciveMsg = null;
		Map<String, Object> map=new HashMap<>();
		
		if ((MerchUser)reqMap.get("merchUser")==null)
		{
			error="非法操作";
		} else
		{
			if (reqMap.get("phone")!=null
				&&reqMap.get("password")!=null
				&&reqMap.get("loginPwd")!=null
				&&((String)reqMap.get("password")).equals((String)reqMap.get("loginPwd")))
			{
				MerchUser merchUser=merchUserDao.findByPhone((String)reqMap.get("phone"));
				if (merchUser != null)
				{
					String salt = merchUser.getPwdSalt();
					merchUser.setLoginPwd(md5Pwd((String)reqMap.get("loginPwd")+salt));
					int redata=merchUserDao.updateMsg(merchUser);
					if(redata<=0){
						error="系统出错，请过会再尝试";
					}else {
						merchUser.setPwdSalt(null);
						merchUser.setLoginPwd(null);
						map.put("merchUser", merchUser);
						map.put("url", "/");
						flag = 1;
					}
				} else
				{
					error="用户账户不存在";
				}
				
			}else {
				error="密码修改失败，请重新尝试";
			}
		}		
		reciveMsg = new ReciveMsg<Map<String,Object>>(flag, "", error, map);		
		return reciveMsg;
	}
	

    /**
     * 
    * 将制定字符串md5加密
    * @param plainText:密码明文
    * @return String
    * @throws
     */
    public static String md5Pwd(String plainText) {
        String re_md5 = new String();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++){
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            re_md5 = buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return re_md5;
    }
	
	
	/**
	 * 密码MD5加密
	 * @param loginPwd
	 * @return
	 */
	/*public String getPwd(String loginPwd,String pwdSalt) {	
		int hashIterations = Integer.valueOf("2");
		String newPassword = new SimpleHash("md5", loginPwd, ByteSource.Util.bytes(pwdSalt), hashIterations).toHex();	
		return newPassword;
	}*/

	@Override
	public List<MerchUser> listByEntity(MerchUser merchUser)
	{
		return merchUserDao.listByEntity(merchUser);
	}
	
	@Override
	public Map<String, Object> listByStoreNo(String storeNo,Integer pageIndex)
	{
		Map<String, Object> map = new HashMap<>();
		MerchUser merchUser = new  MerchUser();
		merchUser.setStoreNo(storeNo);
		List<MerchUser> list = merchUserDao.listByEntity(merchUser);
		map.put("total", list.size());
		MerchUser[] array = new MerchUser[list.size()];
		array = list.toArray(array);
		list.clear();
		for(int i=0;i<(pageIndex+1)*10&&i<array.length;i++)
		{
			list.add(array[i]);
		}
		map.put("clerkList", list);
		return map;
	}

	@Override
	public ReciveMsg<String> deleteClerkById(Integer id)
	{
		String error = null;
		Integer flag = 0;
		if (id <= 0||merchUserDao.findById(id) == null)
		{
			error = "该用户不存在";
		} else if("1".equals(merchUserDao.findById(id).getUserType())){
			error = "权限不足，不能删除该用户";
		}else{
			Integer result = merchUserDao.deleteById(id);
			if (result >= 0)
			{
				flag = 1;
			}else{
				error = "删除失败，请再次尝试";
			}
		}
		return new ReciveMsg<String>(flag, "", error, null);
	}



}
