package com.yunpay.permission.controller.mobile.sdk;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunpay.common.core.exception.BizException;
import com.yunpay.common.core.utils.DateUtils;
import com.yunpay.h5merch.service.impl.SysCashierServiceImpl;
import com.yunpay.permission.controller.MerchInfoContr;
import com.yunpay.permission.entity.Address;
import com.yunpay.permission.entity.MerchInfo;
import com.yunpay.permission.entity.SysAttach;
import com.yunpay.permission.entity.SysCashier;
import com.yunpay.permission.service.AttachmentService;
import com.yunpay.permission.service.MerchService;
import com.yunpay.permission.utils.MD5Utils;
import com.yunpay.permission.utils.Sha1Utils;
/**
 * 
 * 文件名称:
 * 内容摘要: 招手猫商户数据同步接口
 * @author:   duan zhang quan
 * @version:  1.0  
 * @Date:     2017年4月14日上午9:56:12 
 * 修改历史:  
 * 修改日期                     修改人员               版本	          修改内容 
 * ----------------------------------------------  
 * 2017年4月14日     duan zhang quan   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
@Controller  
@RequestMapping("/extApi")
public class ZSMMerchContr {
	@Autowired
	private SysCashierServiceImpl sysCashierService;
	@Autowired
	private  MerchService MerchService;
	@Autowired
	private AttachmentService attachmentService;
	/**
	 * 
	* 通过手机号获取商户数据接口
	* @param 
	* @return Map<String,Object>
	* @throws
	* 
	 */
	
	@RequestMapping("/getShopsDataByPhone")
	public @ResponseBody Map<String,Object> getShopsDataByPhone(HttpServletRequest request,HttpServletResponse rep){
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		String phone = request.getParameter("phone");
		String token = request.getParameter("token");
		String tokenStr = phone+DateUtils.getYMDHString();
		// 先一层SHA1加密后再进行MD5加密后再将整个字符串转为大写格式
		String shalStr = Sha1Utils.getSha1(tokenStr);
		tokenStr = MD5Utils.md5Pwd(shalStr).toUpperCase();
		// 根据手机号查询收银员
		try{
			SysCashier cashier = this.sysCashierService.serlectByPhone(phone);
			if(StringUtils.isEmpty(phone) || StringUtils.isEmpty(token)){
				jsonMap.put("code", 40000);
				jsonMap.put("message", "the parameter cannot be empty");
				return jsonMap;
			}
			else if(!token.equals(tokenStr)){
				jsonMap.put("code", 40001);
				jsonMap.put("message", "the token is invalid");
				return jsonMap;
			}
			else if(cashier == null){
				jsonMap.put("code", 10001);
				jsonMap.put("message", "the merchant is not exist");
				return jsonMap;
			}else{
				String merchantNo = cashier.getMerchantNo();
				if(StringUtils.isEmpty(merchantNo)){
					jsonMap.put("code", 10001);
					jsonMap.put("message", "the merchant is not exist");
					return jsonMap;
				}else{
					MerchInfo merchInfo = this.MerchService.getMerchInfo(merchantNo);
					if(merchInfo == null){
						jsonMap.put("code", 10001);
						jsonMap.put("message", "the merchant is not exist");
						return jsonMap;
					}
					cashier = this.sysCashierService.getMerchUser(merchantNo);
					if(cashier == null){
						jsonMap.put("code", 10001);
						jsonMap.put("message", "the merchant is not exist");
						return jsonMap;
					}
					String url = "http://siecompay.com/merch";
					// 设置附件地址
					merchInfo.setIdentityCardNo(url+"/extApi/showAttach?id="+merchInfo.getAttach1()); 
					merchInfo.setIdentityCardNo2(url+"/extApi/showAttach?id="+merchInfo.getAttach2());
					merchInfo.setBusiLicense(url+"/extApi/showAttach?id="+merchInfo.getAttach3());
					merchInfo.setCyLicense(url+"/extApi/showAttach?id="+merchInfo.getAttach4());
					merchInfo.setMerLogo(url+"/extApi/showAttach?id="+merchInfo.getAttach5());
					merchInfo.setMerchPwd(cashier.getLoginPwd());
					merchInfo.setPwdSalt(cashier.getPwdSalt());
					// 设置省市区的中文名称
					Map<String,String> provMap = new HashMap<String,String>();
					List<Address> list = MerchService.getAllProv();
					for(int i=0; i<list.size(); i++){
						provMap.put(list.get(i).getId().toString(), list.get(i).getName());
					}
					String prov = StringUtils.isEmpty(merchInfo.getProv()) == true ? null : provMap.get(merchInfo.getProv());
					String city = StringUtils.isEmpty(merchInfo.getCity()) == true ? null :  provMap.get(merchInfo.getCity());
					String area = StringUtils.isEmpty(merchInfo.getArea()) == true ? null : provMap.get(merchInfo.getArea());
					String accProv = StringUtils.isEmpty(merchInfo.getAccProv()) == true ? null : provMap.get(merchInfo.getAccProv());
					String accCity = StringUtils.isEmpty(merchInfo.getAccCity()) == true ? null : provMap.get(merchInfo.getAccCity());
					merchInfo.setProv(prov);
					merchInfo.setCity(city);
					merchInfo.setArea(area);
					merchInfo.setAccProv(accProv);
					merchInfo.setAccCity(accCity);
					jsonMap.put("code", 0);
					jsonMap.put("message", "ok");
					jsonMap.put("data", merchInfo);
					return jsonMap;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			jsonMap.put("code", 50001);
			jsonMap.put("message", "exception");
			return jsonMap;
		}
	
	}
	
	/**
	 * 同步修改商户密码接口
	* @param 
	* @return Map<String,Object>
	* @throws
	 */
	@RequestMapping("/resetShopsPwd")
	public @ResponseBody Map<String,Object> resetShopsPwd(HttpServletRequest request,HttpServletResponse rep){
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		String loginName = request.getParameter("phone");
		String pass = request.getParameter("pass");
		String tokenStr = loginName+DateUtils.getYMDHString();
		// 先一层SHA1加密后再进行MD5加密后再将整个字符串转为大写格式
		String shalStr = Sha1Utils.getSha1(tokenStr);
		tokenStr = MD5Utils.md5Pwd(shalStr).toUpperCase();
		// 根据手机号查询收银员
		SysCashier cashier = this.sysCashierService.serlectByPhone(loginName);
		try{
			String token = request.getParameter("token");
			if(StringUtils.isEmpty(token) || StringUtils.isEmpty(loginName) || StringUtils.isEmpty(pass)){
				jsonMap.put("code", 40000);
				jsonMap.put("message", "the parameter cannot be empty");
				return jsonMap;
			}
			else if(pass.length() != 32){
				jsonMap.put("code", 10011);
				jsonMap.put("message", "the password length is not satisfied");
				return jsonMap;
			}
			else if(!token.equals(tokenStr)){
				jsonMap.put("code", 40001);
				jsonMap.put("message", "the token is invalid");
				return jsonMap;
			}
			else if(cashier == null){
				jsonMap.put("code", 10001);
				jsonMap.put("message", "the phone not exist");
				return jsonMap;
			}
			else{
				this.sysCashierService.updatePwdByPhone(loginName, pass);
				jsonMap.put("code", 0);
				jsonMap.put("message", "ok");
				return jsonMap;
			}
		}catch(Exception e){
			e.printStackTrace();
			jsonMap.put("code", 50001);
			jsonMap.put("message", "exception");
			return jsonMap;
			
		}
	}
	
	
	
	/**
	 * 显示附件信息
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
}
