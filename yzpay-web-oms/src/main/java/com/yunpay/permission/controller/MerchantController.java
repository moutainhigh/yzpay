package com.yunpay.permission.controller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunpay.common.core.dwz.DwzAjax;
import com.yunpay.common.core.exception.BizException;
import com.yunpay.common.core.page.PageBean;
import com.yunpay.common.core.page.PageParam;
import com.yunpay.common.core.utils.ExcelUtil;
import com.yunpay.common.core.utils.OpensslUtil;
import com.yunpay.controller.common.BaseController;
import com.yunpay.controller.login.LoginController;
import com.yunpay.permission.entity.Address;
import com.yunpay.permission.entity.AlipayConfig;
import com.yunpay.permission.entity.Merchant;
import com.yunpay.permission.entity.SysAttach;
import com.yunpay.permission.entity.WechatConfig;
import com.yunpay.permission.service.AttachmentService;
import com.yunpay.permission.service.MerchService;
@Controller
@RequestMapping("/sys/merchant")
public class MerchantController extends BaseController{
	/**
	 * 
	 * 文件名称: 商户管理
	 * 内容摘要: 
	 * @author:   duan zhang quan
	 * @version:  1.0  
	 * @Date:     2017年6月14日上午10:37:30
	 * 
	 * 修改历史:  
	 * 修改日期                     修改人员                   版本	        修改内容  
	 * ----------------------------------------------  
	 * 2017年7月11日     duan zhang quan   2.0      修改
	 *
	 * 版权:   版权所有(C)2017
	 * 公司:   深圳市至高通信技术发展有限公司
	 */
	private static final Log LOG = LogFactory.getLog(LoginController.class);  
	@Autowired
	private MerchService MerchService; 
	@Autowired
	private AttachmentService attachmentService;
	
	/**
	* 保存查询参数
	* @param 
	* @return void
	* @throws
	 */
	public Map<String,Object> setQueryParam(HttpServletRequest req){
		String registerName = StringUtils.isEmpty(req.getParameter("registerName")) == true ? null : req.getParameter("registerName");
		String cardName =   StringUtils.isEmpty(req.getParameter("cardName"))== true ?null : req.getParameter("cardName");
		try{
			// 判断是 get 还是post
			String requestMethod = req.getMethod();
			if("get".equalsIgnoreCase(requestMethod)){
				// 设置get请求时的编码格式
				if(registerName != null){
					registerName = new String(registerName.getBytes("ISO-8859-1"), "UTF-8");  // 构造一个UTF-8格式的字符串
				}if(cardName != null){
					cardName = new String(cardName.getBytes("ISO-8859-1"), "UTF-8");
				} 
			}else if("post".equalsIgnoreCase(requestMethod)){
				
			}
			//前端查询条件参数
			String merchantNo =  StringUtils.isEmpty(req.getParameter("merchantNo")) == true ? null : req.getParameter("merchantNo");
			String tel =  StringUtils.isEmpty(req.getParameter("tel")) == true ? null : req.getParameter("tel");
			String prov = StringUtils.isEmpty(req.getParameter("prov")) == true ? null : req.getParameter("prov");
			String city = StringUtils.isEmpty(req.getParameter("city")) == true ? null : req.getParameter("city");
			String area = StringUtils.isEmpty(req.getParameter("area")) == true ? null : req.getParameter("area");
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("merchantNo", merchantNo);
			map.put("registerName", registerName);
			map.put("cardName", cardName);
			map.put("tel", tel);
			map.put("prov", prov);
			map.put("city", city);
			map.put("area", area);
			return map;
		}catch(Exception e){
			e.printStackTrace();
			return new HashMap<String,Object>();
		}
	}
	

	/**
	 * 构造省市区数据, 广东省-深圳市-南山区..
	* @param 
	* @return void
	* @throws
	 */
	public void buildProv(List<Merchant> record,PageBean<Merchant> pageBean){
		List<Merchant> recordList = new ArrayList<Merchant>();
		// 输出所有的省市区数据
		Map<String,String> provMap = new HashMap<String,String>();		
		List<Address> list = MerchService.getAllProv();
		for(int i=0; i<list.size(); i++){
			provMap.put(list.get(i).getId().toString(), list.get(i).getName());
		}
		// 输出商户信息列表数据
		for(int i=0; i<record.size(); i++){
			String str = null;
			Merchant m = record.get(i);
			String prov = StringUtils.isEmpty(m.getProv()) ? "" : provMap.get(m.getProv());
			String city = StringUtils.isEmpty(m.getCity()) ? "" : provMap.get(m.getCity());
			String area = StringUtils.isEmpty(m.getArea()) ? "" : provMap.get(m.getArea());
			
			if(!StringUtils.isEmpty(prov) && !StringUtils.isEmpty(city) && ! StringUtils.isEmpty(area)){
				str =  prov+"-"+city+"-"+area;
			}
			m.setProv(str);
			// 重新赋值
			recordList.add(m);
		}
		// 当存在分页的情况下
		if(pageBean != null){
			pageBean.setRecordList(recordList);
		// 没有分页的情况下
		}else{
			record = recordList;
		}
	
		
	}
	
	
	/**
	 * 函数功能说明 ： 查询商户数据.
	 * @参数： @return
	 * @return String
	 * @throws
	 */
	@RequestMapping("/list")
	public String list(HttpServletRequest req, Model model,PageParam pageParam) {
		// 前端查询条件
		Map<String,Object> map = this.setQueryParam(req);
		super.setRequestAttr(req, map);
		// 输出商户信息列表
		PageBean<Merchant> pageBean = (PageBean<Merchant>) MerchService.listPage(pageParam, map);
		this.buildProv(pageBean.getRecordList(),pageBean);
		req.setAttribute("pageBean", pageBean);
		// 输出省份数据
		Map<Integer,String> province = this.getProvince();
		req.setAttribute("provinceMap", province);
		// select选择项的回填
		req.setAttribute("cityList", this.returnSelected((String)map.get("prov")));
		req.setAttribute("areaList", this.returnSelected((String)map.get("city")));
		return "modules/merch/merchList";
	}
	
	/**
	 * 修改状态码 （停用/启用）
	 * 
	 * @param id
	 *            门店编码
	 * @param status
	 *            状态码
	 * 
	 **/
	@RequestMapping("/editAttachmentUI")
	public String editAttachmentUI(HttpServletRequest req, DwzAjax dwz, @RequestParam("id") Integer id,
			@RequestParam("status") Integer status, Model model)
	{
		try
		{
			Merchant merchant = new Merchant();
			merchant.setId(id);
			merchant.setStatus(status.toString());
			if (MerchService.updateStatus(merchant) > 0)
			{
				return operateSuccess(model, dwz);
			} else
			{
				return operateError("修改状态失败", model);
			}
		} catch (Exception e)
		{
			LOG.error("== editSysMerchantStatus exception:", e);
			return operateError("获取数据失败", model);
		}
	}
	
	/**
	 * 
	 * 商户审核
	 * @param 
	 * @return String
	 * @throws
	 */
	@RequestMapping("/approve")
	public String approve(HttpServletRequest req, HttpServletResponse rep,PageParam pageParam,Model model){
		// 前端查询条件
		Map<String,Object> map = this.setQueryParam(req);
		super.setRequestAttr(req, map);
		// 输出商户信息列表
		PageBean<Merchant> pageBean = (PageBean<Merchant>) MerchService.listPage(pageParam, map);
		this.buildProv(pageBean.getRecordList(),pageBean);
		req.setAttribute("pageBean", pageBean);
		// 输出省份数据
		Map<Integer,String> province = this.getProvince();
		req.setAttribute("provinceMap", province);
		// select选择项的回填
		req.setAttribute("cityList", this.returnSelected((String)map.get("prov")));
		req.setAttribute("areaList", this.returnSelected((String)map.get("city")));
		return "modules/merch/merchApprove";
	}
	
	/**
	* @throws IOException 
	* 转发到商户审批页面
	* @param 
	* @return void
	* @throws
	 */
	@RequestMapping("/approvePage")
	public String approvePage(HttpServletRequest req,HttpServletResponse rep){
		Map<String,Object> params = this.setQueryParam(req);
		Map<String,Object> data = MerchService.merchDetail(params);
		req.setAttribute("merch", data.get("merch"));
		return "modules/merch/approvePage";
	}
	
	/**
	* @throws IOException 
	* 转发到商户数据明细页面
	* @param 
	* @return void
	* @throws
	 */
	@RequestMapping("/merchDetail")
	public String merchDetail(HttpServletRequest req,HttpServletResponse rep){
		Map<String,Object> params = this.setQueryParam(req);
		Map<String,Object> data = MerchService.merchDetail(params);
		req.setAttribute("merch", data.get("merch"));
		return "modules/merch/merchDetail";
	}
	
	// 审批同意
	@RequestMapping("/approve/agree")
	public @ResponseBody  Map<String,Object> approveAgree(HttpServletRequest req,HttpServletResponse rep){
		Map<String,Object> s = new HashMap<String,Object>();
		String merchantNo = req.getParameter("merchantNo");
		String auditMemo = req.getParameter("auditMemo");
		Merchant m = new Merchant();
		// 审核状态，0：待审核，1：审核通过，2：驳回，3：驳回后二次申请,审核中
		int auditStatus = 1;
		m.setAuditStatus(auditStatus);
		m.setAuditMemo(auditMemo);
		m.setMerchantNo(merchantNo);
		try{
			this.MerchService.updateMerch(m);
			s.put("status", "success");
		}catch(Exception e){
			s.put("status", "exception");
			throw new BizException("审批商户时发生异常", e);
		}
		return s;
	}
	
	
	
	// 审批驳回
	@RequestMapping("/approve/returned")
	public @ResponseBody  Map<String,Object> approveReturn(HttpServletRequest req,HttpServletResponse rep){
		Map<String,Object> s = new HashMap<String,Object>();
		String merchantNo = req.getParameter("merchantNo");
		String auditMemo = req.getParameter("auditMemo");
		Merchant m = new Merchant();
		// 审核状态，0：待审核，1：审核通过，2：驳回，3：驳回后二次申请,审核中
		int auditStatus = 2;
		m.setAuditStatus(auditStatus);
		m.setAuditMemo(auditMemo);
		m.setMerchantNo(merchantNo);
		try{
			this.MerchService.updateMerch(m);
			s.put("status", "success");
		}catch(Exception e){
			s.put("status", "exception");
			throw new BizException("审批商户时发生异常", e);
		}
		return s;
	}
	
	/**
	 * 显示附件信息,使用a标签的href方式显示图片
	* @param 
	* @return void
	* @throws
	 */
	@RequestMapping("/showAttach")
	public void  showAttach(HttpServletRequest req,HttpServletResponse rep){
		// 得到附件id
		int attachId = Integer.parseInt(req.getParameter("id"));
		// 获取serlvet容器绝对路径
		String containerPath = MerchantController.class.getResource("/").getPath(); 
		String noneAttach = containerPath+"/image/noneAttach.png";
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
				System.out.println("位于"+attachDir+"上的附件不存在");
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
	 * 函数功能说明 ： 设置 select 选择项的回填
	 * @参数：
	 * @return 
	 * @throws
	 */
	public List<Address> returnSelected(String id){
		int _id = -1;
		if(id != null && !"".equals(id)){
			_id = Integer.parseInt(id);
		}
		List<Address> address = this.MerchService.getCity(_id);
		return address;
	}
	
	
	/**
	 * 函数功能说明 ： 删除商户数据.
	 * @参数： @return
	 * @return 
	 * @throws
	 */
	public Map<String,String> delete(HttpServletRequest req){
		/*String merchantNo = req.getParameter("merchantNo");*/
		Map<String,String> map = new HashMap<String,String>();
		return map;
	}

	
	/**
	 * 函数功能说明 ： 生成支付宝的公钥、私钥
	 * @param req
	 * // type 0-支付 1-卡券
	 * @return  
	 */
	@RequestMapping("/alipay/generateKey")
	public @ResponseBody Map<String,String> generateKey(HttpServletRequest req){
		int type = Integer.parseInt(req.getParameter("type"));
		String merchantNo = req.getParameter("merchantNo");
		Map<String,String> map = new HashMap<String,String>();
		String reg = "^\\d+$";
		if (!merchantNo.matches(reg)) {
			map.put("info", "error");
		}else{
			try {
				map = OpensslUtil.generateCert(merchantNo, type);
			} catch (Exception e) {
				map.put("info", "syserror");
				e.printStackTrace();
			}
		}
		return map;
	}
	

	/**
	 * 函数功能说明 ： 获取所有的省信息
	 * @参数： @return
	 * @return Map<String,String>
	 * @throws
	 */
	public Map<Integer,String> getProvince(){
		List<Address> province = this.MerchService.getProvince();
		Map<Integer,String> map = new HashMap<Integer,String>();
		for(int i=0; i<province.size(); i++){
			Address c = province.get(i);
			map.put(c.getId(), c.getName());
		}
		return map;
	}
	
	/**
	 * 函数功能说明 ： 根据省输出市信息
	 * @throws
	 */
	@RequestMapping("/getCity")
	public  @ResponseBody Map<Integer,String> getCity(HttpServletRequest req){
		String id = req.getParameter("id");
		List<Address> city = this.MerchService.getCity(Integer.parseInt(id));
		Map<Integer,String> map = new HashMap<Integer,String>();
		for(int i=0; i<city.size(); i++){
			Address c = city.get(i);
			map.put(c.getId(), c.getName());
		}
		return map;
	}
	
	/**
	 * 函数功能说明 ： 根据市输出区信息
	 * @throws
	 */
	@RequestMapping("/getArea")
	private  @ResponseBody Map<Integer,String> getArea(HttpServletRequest req){
		String id = req.getParameter("id");
		Map<Integer,String> map = new HashMap<Integer,String>();
		List<Address> area = this.MerchService.getArea(Integer.parseInt(id));
		for(int i=0; i<area.size(); i++){
			Address c = area.get(i);
			map.put(c.getId(), c.getName());
		}
		return map;
	}
	
	/**
	 * 函数功能说明 ：进入商户支付配置页面
	 * @param req
	 * @param merchantNo：商户号
	 * @param confType:配置类型：微信、支付宝
	 * @return
	 */
	@RequestMapping("/payConfig")
	private String payConfig(HttpServletRequest req,String merchantNo,String confType){
		req.setAttribute("", "");
		WechatConfig wechatConfig = (WechatConfig)this.MerchService.queryConfigById(merchantNo, "wechat");
		AlipayConfig alipayConfig = (AlipayConfig)this.MerchService.queryConfigById(merchantNo, "alipay");
		// 没有进行过支付配置,则关联商户号
		if(wechatConfig == null && alipayConfig == null){
		}
		if(wechatConfig == null){
			wechatConfig = new WechatConfig();
			wechatConfig.setMerchantNo(merchantNo);
		}if(alipayConfig == null){
			alipayConfig = new AlipayConfig();
			alipayConfig.setMerchantNo(merchantNo);
		}
		List<WechatConfig> wxParent = MerchService.getParent(0);
		
		// 输出支付配置数据
		req.setAttribute("wechatConfig", wechatConfig);
		req.setAttribute("alipayConfig", alipayConfig);
		req.setAttribute("wxParent", wxParent);
		return "modules/merch/payConfig";
	}
	
	/**
	 * 函数功能说明 ：保存或更新商户支付配置
	 * @param req
	 * @param merchantNo：商户号
	 * @param confType:配置类型：微信、支付宝
	 * @return
	 */
	@RequestMapping(value="/savePayConfig",method=RequestMethod.POST)
	private @ResponseBody Map<String,String> savePayConfig(HttpServletRequest req,
			@ModelAttribute("wechatConfig") WechatConfig wechatConfig,
			@ModelAttribute("alipayConfig") AlipayConfig alipayConfig){
		Map<String,String> map = new HashMap<String,String>();
		String merchantNo = req.getParameter("merchantNo");
		String confType = req.getParameter("confType");
		Object obj = this.MerchService.isConfig(merchantNo, confType);
	
		map.put("success", "true");
		if(obj instanceof WechatConfig){
			//关联商户号
			Integer status = wechatConfig.getStatus()==null?1:wechatConfig.getStatus();
			wechatConfig.setMerchantNo(merchantNo);
			wechatConfig.setMchType(1);
			wechatConfig.setCertLocalPath("http://www.siecom.cn");
			wechatConfig.setWxAppApiSecret(wechatConfig.getApiSecret());
			wechatConfig.setCertPassword(wechatConfig.getMchId());
			wechatConfig.setStatus(status);
			WechatConfig wechat = (WechatConfig)obj;
			merchantNo = wechat.getMerchantNo();
			if(merchantNo != null){
				// 更新微信支付配置
				this.MerchService.updatePayConfig(wechatConfig);
			}else{
				// 新增
				this.MerchService.savePayConfig(wechatConfig);
			}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
		}else if(obj instanceof AlipayConfig){
			//关联商户号
			Integer status = alipayConfig.getStatus()==null?1:alipayConfig.getStatus();
			alipayConfig.setMerchantNo(merchantNo);
			alipayConfig.setAppId(alipayConfig.getStoreAppId());
			alipayConfig.setStoreAppPrivateKey(alipayConfig.getMerchantPrivateKey());
			alipayConfig.setAlipassPublicKey(alipayConfig.getAlipayPublicKey());
			alipayConfig.setStoreAppPublicKey(alipayConfig.getAlipayPublicKey());
			alipayConfig.setStatus(status);
			AlipayConfig alipay = (AlipayConfig)obj;
			merchantNo = alipay.getMerchantNo();
			if(merchantNo != null){
				// 更新支付宝配置
				this.MerchService.updatePayConfig(alipayConfig);
			}else{
				// 新增
				this.MerchService.savePayConfig(alipayConfig);
			}
		}else{
			map.put("success", "false");
			return null;
		}
		return map;
	}
	
	
	/**
	 * 导出数据到Excel
	* @param 
	* @return void
	* @throws
	 */
	@RequestMapping(value = "exportExcel", method = RequestMethod.POST)  
	public void exportExcel(HttpServletRequest req,HttpServletResponse response) throws Exception {  
		String agent = req.getHeader("USER-AGENT").toLowerCase();
		Map<String,Object> params = this.setQueryParam(req);
		List<Merchant> list = this.MerchService.listBy(params);
		
		this.buildProv(list,null);
	    String fileName = "商户信息.xls";  
	    ExcelUtil.writeExcel(response, fileName, agent,list, Merchant.class);  
	}
	

}
