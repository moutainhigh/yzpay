package com.yunpay.permission.controller;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yunpay.common.core.exception.BizException;
import com.yunpay.common.core.utils.StringUtil;
import com.yunpay.common.core.utils.http.Response.Code;
import com.yunpay.common.core.utils.io.ImageUtil;
import com.yunpay.common.core.utils.io.UploadConfig;
import com.yunpay.controller.common.BaseController;
import com.yunpay.h5merch.permission.entity.MerchUser;
import com.yunpay.permission.entity.MerchAttach;
import com.yunpay.permission.entity.MerchInfo;
import com.yunpay.permission.entity.Merchant;
import com.yunpay.permission.entity.MerchantAccount;
import com.yunpay.permission.entity.SysAttach;
import com.yunpay.permission.service.AttachmentService;
import com.yunpay.permission.service.IndustryService;
import com.yunpay.permission.service.MerchService;
import com.yunpay.permission.shiro.filter.MySessionContext;
@Controller
@RequestMapping("/sys/merchContr")
public class MerchInfoContr extends BaseController{
	/**
	 * 
	 * 文件名称:
	 * 内容摘要: 商户信息
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
	 */
	
	@Autowired
	private  MerchService MerchService;
	@Autowired
	private AttachmentService attachmentService;
	
	@SuppressWarnings("unused")
	@Autowired
	private IndustryService industryService;
	
	/**
	* @throws IOException 
	* 转发到商户信息页面 
	* @param 
	* @return void
	* @throws
	 */
	@RequestMapping("/merchIndex")
	public String merchIndex(HttpServletRequest req,HttpServletResponse rep){
		HttpSession session = req.getSession();
		MerchUser sessionUser = (MerchUser)session.getAttribute("merchUser");

			String merchantNo =  StringUtils.isEmpty( sessionUser == null ? "" : sessionUser.getMerchantNo()  ) == true ? req.getParameter("merchantNo") : sessionUser.getMerchantNo();
			MerchInfo merchInfo = this.MerchService.getMerchInfo(merchantNo);
			req.setAttribute("merch", merchInfo);
			
			//  审核通过的和审核中的,只能修改部分数据 
			if(merchInfo.getAuditStatus() == 1 || merchInfo.getAuditStatus() == 3){
				return "merchant/merchInfo";
			}
			// 审批状态: 0、2 待审核和被退回的申请可以修改全部数据
			else{
				return "merchant/statusInfo";
			}	
	}
	
	/**
	 * 点击 更改商户资料 按钮转发到更改商户资料页面
	* 
	* @param 
	* @return void
	* @throws
	 */
	@SuppressWarnings("unused")
	@RequestMapping("/toUpdate")
	public String toUpdate(HttpServletRequest req,HttpServletResponse rep){  
		//APP同时登录限制 
		String appToken =StringUtils.isBlank(req.getParameter("appToken"))?null:req.getParameter("appToken");
		if (appToken != null && MySessionContext.getAppSession(appToken) == null)
		{
			HttpSession session = req.getSession();
			session.invalidate();
			//向前端传送标识符，转登录界面
			req.setAttribute("duplicateLogin", "duplicateLogin");
		}
		
		String tabActive = req.getParameter("tabActive");
		String type = req.getParameter("type");
		HttpSession session = req.getSession();
		MerchUser sessionUser = (MerchUser)session.getAttribute("merchUser");
		String userAgent = req.getHeader("user-agent");
		/*if(sessionUser != null || userAgent.indexOf("SiecomWebview") > -1){*/
			String merchantNo =  req.getParameter("merchantNo");
			MerchInfo merchInfo = this.MerchService.getMerchInfo(merchantNo);
			if(merchInfo != null){
				req.setAttribute("merch", merchInfo);
				req.setAttribute("tabActive", tabActive);
			
				//  待审核和被退回的申请可以修改全部数据
				if(type.equals("1")){
					return "merchant/merchAllEdit";
				}
				//  审核通过的和审核中的,只能修改部分数据  
				else{
					return "merchant/merchEdit";
				}	
			}else{
				this.responseMessage(rep, Code.NOT_STORE_USER.toString());
				return null;
			}
		/*}else{
			this.responseMessage(rep,  "the token is invalid");
			return null;
			
		}*/
	}
	
	

	/**
	 * @throws UnsupportedEncodingException 
	 * 未审核或审核失败的商户，可以修改全部资料
	* 
	* @param MissingServletRequestParameterException e;
	* @return void
	* @throws
	 */
	@RequestMapping(method=RequestMethod.POST,value="/updateAll")
	public String updateAll(HttpServletRequest req,HttpServletResponse rep) throws UnsupportedEncodingException{
		req.setCharacterEncoding("utf-8");
		//APP同时登录限制
		String appToken = StringUtils.isBlank(req.getParameter("appToken"))?null:req.getParameter("appToken");
		if (appToken != null && MySessionContext.getAppSession(appToken) == null)
		{
			HttpSession session = req.getSession();
			session.invalidate();
			//向前端传送标识符，转登录界面
			req.setAttribute("duplicateLogin", "duplicateLogin");
		}
		
		String merchantNo = req.getParameter("merchantNo");
		// 商户基本信息
		Integer merId =  Integer.parseInt(req.getParameter("merId"));
		String merchantName = req.getParameter("merchantName");
		String registerName =  req.getParameter("registerName");
		String prov =  req.getParameter("prov");
		String city = req.getParameter("city");
		String area = req.getParameter("area");
		String cardName =req.getParameter("cardName");
		String cardNo = req.getParameter("cardNo");
		int industryTypeId = Integer.parseInt(req.getParameter("industryTypeId"));
		String address = req.getParameter("address");
		String tel = StringUtils.isEmpty(req.getParameter("tel")) == false ? req.getParameter("tel") : null;
		String email = StringUtils.isEmpty(req.getParameter("email")) == false ? req.getParameter("email") : null;
		Merchant merchant = new Merchant();
		merchant.setMerchantNo(merchantNo);
		merchant.setMerchantName(merchantName);
		merchant.setRegisterName(registerName);
		merchant.setProv(prov);
		merchant.setCity(city);
		merchant.setArea(area);
		merchant.setCardName(cardName);
		merchant.setCardNo(cardNo);
		merchant.setIndustryTypeId(industryTypeId);
		merchant.setAddress(address);
		merchant.setTel(tel);
		merchant.setEmail(email);
		merchant.setUpdatedAt(new Date());
		// 商户银行账户信息
		String accBank = req.getParameter("accBank");
		String accProv = req.getParameter("accProv");
		String accCity = req.getParameter("accCity");
		String accSubBank = req.getParameter("accSubBank");
		String accName = req.getParameter("accName");
		String accNo = req.getParameter("accNo");
		MerchantAccount account = new MerchantAccount();
		account.setAccBank(accBank);
		account.setProv(accProv);
		account.setCity(accCity);
		account.setAccSubBank(accSubBank);
		account.setAccName(accName);
		account.setAccNo(accNo);
		account.setUpdTime(new Date());
		account.setMerId(merId);
		// 创建保存的目录: /usr/local/yunpay/attach/20170825
		String savePath = UploadConfig.getDirs();
		try {
			this.MerchService.updateMerch(merchant);
			this.MerchService.updateAccount(account);
			int [] ids = new int[5];
			// 商户附件信息
			MerchAttach merAttach = this.MerchService.getMerchAttach(merId);
			if(merAttach != null){
				ids[0] = merAttach.getCardNo();
				ids[1] = merAttach.getCardBackNo();
				ids[2] = merAttach.getBusiLicense();
				ids[3] = merAttach.getCyLicense();
				ids[4] = merAttach.getMerLogo();
			}
			this.base64CodeToFile(req, rep, savePath, ids);
			MerchAttach merchAttach = new MerchAttach();
			merchAttach.setMerId(merId);
			merchAttach.setCardNo(ids[0]);
			merchAttach.setCardBackNo(ids[1]);
			merchAttach.setBusiLicense(ids[2]);
			merchAttach.setCyLicense(ids[3]);
			merchAttach.setMerLogo(ids[4]);
			// 更新商户附件信息
			this.MerchService.updateMerchAttach(merchAttach);
			return "merchant/success";
		} catch (Exception e) {
			e.printStackTrace();
			this.responseMessage(rep, e.getMessage());
			return null;
		}
	}
	
	//获取img的base64编码并转换为图片
	@SuppressWarnings("unused")
	public void base64CodeToFile(HttpServletRequest req,HttpServletResponse rep,String saveDir,int [] attachIds){
		try{
			String [] base64Input = req.getParameterValues("base64Input");
			int n = 0;
			for(int i=0;i<base64Input.length; i++){
				if(!StringUtils.isEmpty(base64Input[i])){
					File f = ImageUtil.GenerateImage(base64Input[i], saveDir);
					String OriginalFilename = f.getName();
					String saveFileName = OriginalFilename;
					int index = OriginalFilename.lastIndexOf(".");
					String fileSuffix = OriginalFilename.substring(index+1);
					long fileSize = f.length() / 1024;
					SysAttach sysAttach  = this.attachmentService.getAttachById(attachIds[i]); // 得到附件信息
					if(sysAttach != null){
						// 删除服务器上已存在的附件
						String existsFile = sysAttach.getSavePath();
						File file = new File(existsFile);
						file.delete();
						// 更新附件
						sysAttach.setFileName(OriginalFilename);
						sysAttach.setFileSuffix(fileSuffix);
						sysAttach.setFileSize(fileSize);
						sysAttach.setSavePath(saveDir+"/"+saveFileName);
						sysAttach.setSaveName(saveFileName);
						this.attachmentService.updateAttach(sysAttach);
					}
					// 新增附件
					else{
						sysAttach = new SysAttach();
						sysAttach.setCreateTime(new Date());
						sysAttach.setFileName(OriginalFilename);
						sysAttach.setFileSuffix(fileSuffix);
						sysAttach.setFileSize(fileSize);
						sysAttach.setSavePath(saveDir+"/"+saveFileName);
						sysAttach.setSaveName(saveFileName);
						this.MerchService.addSysAttach(sysAttach);
						
						int attachId = sysAttach.getId();
						attachIds[i] = attachId;
					}
				}else{
					continue;
				}
			
			}
		}catch(Exception e){
			throw new BizException("app上传文件时出错.",e);
		}
	}
	

	
	/**
	 * 审核成功后的商户,只能修改部分指定资料
	* 
	* @param 
	* @return String
	* @throws
	 */
	@RequestMapping(method=RequestMethod.POST,value="/update")
	public String update(HttpServletRequest req,HttpServletResponse rep){
		//APP同时登录限制
		String appToken = StringUtils.isBlank(req.getParameter("appToken"))?null:req.getParameter("appToken");
		if (appToken != null && MySessionContext.getAppSession(appToken) == null)
		{
			HttpSession session = req.getSession();
			session.invalidate();
			//向前端传送标识符，转登录界面
			req.setAttribute("duplicateLogin", "duplicateLogin");
		}
		
		String merchantNo = req.getParameter("merchantNo");
		String merchantName = StringUtil.isEmpty(req.getParameter("merchantName")) == false ? req.getParameter("merchantName") : null;
		String address = StringUtil.isEmpty(req.getParameter("address")) == false ? req.getParameter("address") : null;
		String tel = StringUtil.isEmpty(req.getParameter("tel")) == false ? req.getParameter("tel") : null;
		String email = StringUtil.isEmpty(req.getParameter("email")) == false ? req.getParameter("email") : null;
		Merchant merchant = new Merchant();
		merchant.setMerchantNo(merchantNo);
		merchant.setMerchantName(merchantName);
		merchant.setAddress(address);
		merchant.setTel(tel);
		merchant.setUpdatedAt(new Date());
		merchant.setEmail(email);
		try{
			this.MerchService.updateMerch(merchant);
			return "merchant/success";
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * @throws IOException 
	 * 显示或下载文件
	* 
	* @param 
	* @return void
	* @throws
	 */
	@RequestMapping("/outFile")
	public void outFile(HttpServletRequest req,HttpServletResponse rep) throws IOException{
		rep.setContentType("application/octet-stream"); // 设置响应的内容格式为二进制流
		rep.setHeader("Content-disposition", "attachment;filename=yunpay.war");  // 附件下载时的http响应头
		ServletOutputStream outputStream = rep.getOutputStream();  // 获取输出流,用于向客户端输出数据
		File file = new File("W:\\web-war\\yunpay.war");
		if(!file.exists()){
			System.out.println("附件不存在********");
			return;
		}
		FileInputStream  inputStream = new FileInputStream(file);  // 获取要下载的文件的输入流，目的是为了读取这个文件
		byte [] buff = new byte[1024*1024];  // 缓冲,大小为1MB
		int readSize = 1024 * 1024;  // 每次要读取的字节数
		int data = -1;
		while((data = inputStream.read(buff, 0, readSize)) != -1 ){
			outputStream.write(buff, 0, readSize);
		}
		inputStream.close();
		outputStream.flush();
		outputStream.close();
		
	}
}
