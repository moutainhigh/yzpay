package com.yunpay.permission.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.yunpay.h5merch.permission.entity.MerchUser;
import com.yunpay.permission.entity.MerchInfo;
import com.yunpay.permission.exception.PermissionException;
import com.yunpay.permission.service.MerchService;
/**
 * 
 * 类名称		       主界面控制器类
 * 文件名称:     indexContr.java
 * 内容摘要: 	       控制登录后跳转不同的主界面
 * @author:   Zhao Xiaoman
 * @version:  1.0  
 * @Date:     2017年9月4日上午10:19:41
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年9月4日   Zhao Xiaoman       1.0       新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
@Controller
@RequestMapping("/sys/index")
public class IndexContr
	
{
	
	@Autowired
	private  MerchService merchService;
	/**
	 * 函数功能说明 ：进入商户界面-已完善资料.
	 * 
	 * @参数： @param request
	 * @参数： @param model
	 * @参数： @return
	 * @return String
	 * @throws PermissionException
	 */
	@RequestMapping("/index")
	public String index(HttpServletRequest req, Model model) {
		HttpSession session = req.getSession();
		String merchantNo = null;
		MerchUser sessionUser = (MerchUser)session.getAttribute("merchUser");
		if(sessionUser != null){
			merchantNo = sessionUser.getMerchantNo();
		}
		else{
			merchantNo = req.getParameter("merchantNo");
		}
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
		return "/login/home";
	}
	
	/**
	 * 函数功能说明 ：进入商户界面-未完善资料.
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping("/indexundo")
	public String indexundo(HttpServletRequest req, Model model) {
		return "login/homeundo";
	}
}
