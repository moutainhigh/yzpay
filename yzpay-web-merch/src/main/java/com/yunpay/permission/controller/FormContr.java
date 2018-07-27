package com.yunpay.permission.controller;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.yunpay.common.core.exception.BizException;
import com.yunpay.common.core.utils.io.ImageUtil;
import com.yunpay.common.core.utils.io.UploadConfig;
import com.yunpay.controller.common.BaseController;
import com.yunpay.h5merch.permission.entity.MerchUser;
import com.yunpay.h5merch.permission.service.MerchUserService;
import com.yunpay.permission.entity.Address;
import com.yunpay.permission.entity.Industry;
import com.yunpay.permission.entity.MerchAttach;
import com.yunpay.permission.entity.Merchant;
import com.yunpay.permission.entity.MerchantAccount;
import com.yunpay.permission.entity.SysAttach;
import com.yunpay.permission.service.AttachmentService;
import com.yunpay.permission.service.IndustryService;
import com.yunpay.permission.service.MerchService;
import com.yunpay.permission.utils.HttpUtil;
import com.yunpay.permission.utils.MD5Utils;
@Controller
@RequestMapping("/sys/formContr")
public class FormContr extends BaseController {
	/**
	 * 
	 * 文件名称:
	 * 内容摘要: 商户资料完善
	 * @author:   duan zhang quan
	 * @version:  1.0  
	 * @Date:     2017年4月14日上午9:56:12 
	 * 
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
	private IndustryService industryService;
	@Autowired
	private MerchUserService merchUserService;
	@SuppressWarnings("unused")
	@Autowired
	private AttachmentService attachmentService;


	/**
	* 进入商户资料完善页面
	* @param 
	* @return void 
	* @throws
	 */
	@RequestMapping("/form")
	public String form(HttpServletRequest req,HttpServletResponse rep) {
		String userIp = HttpUtil.getUserIp(req);
		req.setAttribute("userIp", userIp);
		return "merchant/form";
	}
	
	/**
	 * 查询行业信息数据
	* @param 
	* @return Map<String,Object>
	* @throws
	 */
	@RequestMapping("/getIndustry")
	public @ResponseBody Map<String,Object> getIndustry(){
		Map<String,Object> map = new HashMap<String,Object>();
		List<Industry> industry = this.industryService.getIndustry();
		map.put("industry", industry);
		return map;
	}
	
	
	public void getUserlocation(HttpServletRequest req) throws UnsupportedEncodingException{
		
		
	}
	
	/**
	 * @throws UnsupportedEncodingException 
	 * 保存基本信息、银行账户信息、附件信息
	* 
	* @param 
	* @return void
	* @throws
	 */
	@SuppressWarnings("unused")
	@RequestMapping(method=RequestMethod.POST,value="/save")
	public String save(HttpServletRequest req,HttpServletResponse rep) throws UnsupportedEncodingException{
		req.setCharacterEncoding("utf-8");
		HttpSession session = req.getSession();
		MerchUser sessionUser = (MerchUser)session.getAttribute("merchUser");
		// 未完善过资料的商户才可以保存
		if(StringUtils.isEmpty(sessionUser.getMerchantNo())){
			String registerName = req.getParameter("registerName");
			String merchantName = req.getParameter("merchantName");
			String prov = req.getParameter("prov");
			String city = req.getParameter("city");
			String area = req.getParameter("area");
			String address = req.getParameter("address");
			String cardName =req.getParameter("cardName");
			String cardNo =req.getParameter("cardNo");
			String industryTypeId =req.getParameter("industryTypeId");
			String tel =req.getParameter("tel");
			String email = req.getParameter("email");
			String accBank = req.getParameter("accBank");
			String accProv = req.getParameter("accProv");
			String accCity = req.getParameter("accCity");
			String accSubBank = req.getParameter("accSubBank");
			String accName = req.getParameter("accName");
			String accNo = req.getParameter("accNo");
			// 商户基本信息
			Merchant merch = new Merchant();
			merch.setMd5Key(MD5Utils.genRandomNum(36));
			merch.setInfoFrom("merch");
			merch.setAuditStatus(0);
			merch.setProv(prov);
			merch.setCreatedAt(new Date());
			merch.setCity(city);
			merch.setRegisterName(registerName);
			merch.setMerchantName(merchantName);
			merch.setArea(area);
			merch.setAddress(address);
			String merchantNo = "99991"+System.currentTimeMillis();
			merch.setMerchantNo(merchantNo);
			merch.setCardName(cardName);
			merch.setCardNo(cardNo);
			merch.setIndustryTypeId(Integer.parseInt(industryTypeId));
			merch.setTel(tel);
			merch.setEmail(email);
			// 银行账户信息
			MerchantAccount account = new MerchantAccount();
			account.setAccBank(accBank);
			account.setProv(accProv);
			account.setCity(accCity);
			account.setAccSubBank(accSubBank);
			account.setAccName(accName);
			account.setAccNo(accNo);
			account.setCreateTime(new Date());
			int [] ids = new int[5];
			// 创建保存的目录: /usr/local/yunpay/attach/20170825
			String savePath = UploadConfig.getDirs();
			String contextPath = session.getServletContext().getContextPath();
			try{
				// 上传附件
				this.base64CodeToFile(req, rep, savePath, ids);
				// 商户基本信息
				this.MerchService.addMerch(merch);
				int merId = merch.getId();
				// 商户银行账户
				account.setMerId(merId);
				this.MerchService.addMerchAcc(account);
				// 商户附件
				MerchAttach merchAttach = new MerchAttach();
				merchAttach.setMerId(merId);
				merchAttach.setCardNo(ids[0]);
				merchAttach.setCardBackNo(ids[1]);
				merchAttach.setBusiLicense(ids[2]);
				merchAttach.setCyLicense(ids[3]);
				merchAttach.setMerLogo(ids[4]);
				this.MerchService.addMerchAttach(merchAttach); 
				// 更新商户用户数据,关联商户号
				Integer userId = null;
				if(sessionUser != null){
					userId = sessionUser.getId();
					// 更新当前在线用户的商户号,并重新设置新的session
					sessionUser.setMerchantNo(merchantNo);
					session.setAttribute("merchUser", sessionUser);
				}else{
					userId = Integer.parseInt(req.getParameter("id"));
				}
				MerchUser user = new MerchUser();
				user.setId(userId);
				user.setMerchantNo(merchantNo);
				// 更新用户信息,表示商户资料已经完善
				merchUserService.updateMsg(user);
				
				return "merchant/success";
			}catch(Exception e){
				e.printStackTrace();
				this.responseMessage(rep, e.getMessage());
				return null;
			}
		}
		else{
			super.responseMessage(rep,"非法操作:该商户已提交过商户资料");
			return null;
		}
	}
	
	
	
	// 获取img的base64编码并转换为图片
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
					SysAttach sysAttach = new SysAttach();
					sysAttach.setCreateTime(new Date());
					sysAttach.setFileName(OriginalFilename);
					sysAttach.setFileSuffix(fileSuffix);
					sysAttach.setFileSize(fileSize);
					sysAttach.setSavePath(saveDir+"/"+saveFileName);
					sysAttach.setSaveName(saveFileName);
				
					this.MerchService.addSysAttach(sysAttach);
					int attachId = sysAttach.getId();
					attachIds[i] = attachId;
				}else{
					continue;
				}
			}
			
		}catch(Exception e){
			throw new BizException("上传文件时出错.",e);
		}
	}
	

	
	
	/**
	 * 函数功能说明 ： 获取所有的行政地区数据包括（省、市、区）
	 * @参数： @return
	 * @return Map<String,String>
	 * @throws
	 */
	@RequestMapping("/getAllProv")
	public @ResponseBody Map<String,Object> getAllProv(){
		List<Address> list =  MerchService.getAllProv();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("address", list);
		return map;
	}
	
	/**
	 * 函数功能说明 ： 获取所有的省信息
	 * @参数： @return
	 * @return Map<String,String>
	 * @throws
	 */
	@RequestMapping("/getProv")
	public @ResponseBody Map<String,Object> getProvince(){
		List<Address> province = this.MerchService.getProvince();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("address", province);
		return map;
	}
	
	/**
	 * 函数功能说明 ： 根据省输出市信息
	 * @throws
	 */
	@RequestMapping("/getCity")
	public  @ResponseBody Map<String,Object> getCity(HttpServletRequest req){
		Map<String,Object> map = new HashMap<String,Object>();
		String id = req.getParameter("id");
		List<Address> city = this.MerchService.getCity(Integer.parseInt(id));
		map.put("address", city);
		return map;
	}
	
	/**
	 * 函数功能说明 ： 根据市输出区信息
	 * @throws
	 */
	@RequestMapping("/getArea")
	private  @ResponseBody Map<String,Object> getArea(HttpServletRequest req){
		String id = req.getParameter("id");
		Map<String,Object> map = new HashMap<String,Object>();
		List<Address> area = this.MerchService.getArea(Integer.parseInt(id));
		map.put("address", area);
		return map;
	}
	
	
}
