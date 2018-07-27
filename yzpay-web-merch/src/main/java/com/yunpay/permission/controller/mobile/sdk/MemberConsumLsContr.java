package com.yunpay.permission.controller.mobile.sdk;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.yunpay.common.core.page.PageBean;
import com.yunpay.common.core.page.PageParam;
import com.yunpay.controller.common.BaseController;
import com.yunpay.h5merch.permission.entity.MemberConsumLs;
import com.yunpay.h5merch.permission.entity.ReciveMsg;
import com.yunpay.h5merch.permission.service.MemberConsumLsService;
import com.yunpay.h5merch.service.impl.SysCashierServiceImpl;
import com.yunpay.permission.entity.SysCashier;
import com.yunpay.permission.utils.AppDupLog;

/**
 * 
 * 文件名称:
 * 内容摘要: 会员消费记录
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
@Controller  
@RequestMapping("/MemberContr")
public class MemberConsumLsContr extends BaseController{
	@Autowired
	private SysCashierServiceImpl sysCashierService;
	@Autowired
	private MemberConsumLsService memberConsumLsService;
	
	
	/**
	 * 分页查询会员消费记录
	* 
	* @param 
	* @return Map<String,Object>
	* @throws
	 */
	@RequestMapping("/list")
	public @ResponseBody ReciveMsg<PageBean<MemberConsumLs>> list(HttpServletRequest request, HttpServletResponse response){
		if (AppDupLog.checkDup(request) == 0)
		{
			SysCashier sysCashier = null;
			Map<String,Object> params = new HashMap<String,Object>();
			String cashierNo = request.getParameter("cashierNo");
			PageBean<MemberConsumLs> pageBean = new PageBean<MemberConsumLs>();
			String pageIndex = StringUtils.isEmpty(request.getParameter("pageIndex")) == true ? "1" : request.getParameter("pageIndex");
			String pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) == true ? "10" : request.getParameter("pageSize");
			PageParam page = new PageParam();
			page.setPageCurrent(Integer.parseInt(pageIndex));
			page.setPageSize(Integer.parseInt(pageSize));
			if(StringUtils.isEmpty(cashierNo)){
				return new ReciveMsg<PageBean<MemberConsumLs>>(0,"4000","cashierNo parameter cannot be empty",null);
			}
			// 查询收银员
			sysCashier = sysCashierService.serlectByParam(cashierNo);
			if (sysCashier == null) {	
				return new ReciveMsg<PageBean<MemberConsumLs>>(0,"4003","the user is not exist",null);
			}
			else{
				String userType = sysCashier.getUserType();
				params.put("userType", userType);
				params.put("cashierNo", sysCashier.getLoginName());
				params.put("merchantNo", sysCashier.getMerchantNo());
				if(StringUtils.isEmpty(sysCashier.getMerchantNo())){
					return new ReciveMsg<PageBean<MemberConsumLs>>(0,"4002","not_permission",null);
				}
				// 店长角色
				if(userType.equals("2")){
					params.put("storeNo", sysCashier.getStoreNo());
				}
				// 收银员角色
				else if(userType.equals("3")){
					String machineNo = request.getParameter("machineNo");
					params.put("machineNo", machineNo);
				}
				pageBean = memberConsumLsService.list(page, params);
				return new ReciveMsg<PageBean<MemberConsumLs>>(1,"","",pageBean);
			}
		}
		else{
			// 同时登录控制
			return new ReciveMsg<PageBean<MemberConsumLs>>(0,"6020","the user account has been logged on to another machine",null);
		}
	}
	
	
	
	
	/**
	 * 根据交易订单号查询单条会员交易流水
	* 
	* @param 
	* @return PayTranLs
	* @throws
	 */
	@RequestMapping(value = "/member/getMemberLsByTransNum")
	public @ResponseBody ReciveMsg<MemberConsumLs> getLsByTransNum(String transNum,HttpServletRequest request,HttpServletResponse response){
		if (AppDupLog.checkDup(request) == 0)
		{
			if(StringUtils.isEmpty(transNum)){
				return new  ReciveMsg<MemberConsumLs>(0,"4000","transNum parameter cannot be empty",null);
			}
			try{
				MemberConsumLs t = this.memberConsumLsService.getMemberLsByTransNum(transNum);
				return new ReciveMsg<MemberConsumLs>(1,"","",t);
			}catch(Exception e){
				return new  ReciveMsg<MemberConsumLs>(0,"5000","exception",null);
			}
		}else{
			//同时登录控制
			return new  ReciveMsg<MemberConsumLs>(0,"6020","the user account has been logged on to another machine",null);
		}
	}
}
