package com.yunpay.permission.controller.mobile.sdk;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.yunpay.common.core.page.PageBean;
import com.yunpay.common.core.page.PageParam;
import com.yunpay.common.core.utils.DateUtils;
import com.yunpay.controller.common.BaseController;
import com.yunpay.h5merch.permission.entity.ReciveMsg;
import com.yunpay.h5merch.permission.entity.SumTransLs;
import com.yunpay.h5merch.permission.service.TransLsService;
import com.yunpay.h5merch.service.impl.SysCashierServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.yunpay.permission.entity.PayTranLs;
import com.yunpay.permission.entity.SysCashier;
import com.yunpay.permission.utils.AppDupLog;
@Controller
@RequestMapping("/transContr")
/**
 * 文件名称:
 * 内容摘要: 商户营业流水、报表:app端调用
 * @author:   duan zhang quan
 * @version:  1.0  
 * @Date:     2017年4月14日上午9:56:12 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年4月14日     duan zhang quan   1.0     新建
 * 
 * 2017年12月19日     duan zhang quan   1.0     修改
 *
 * 版权:   版权所有(C)2017  
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class TransContr extends BaseController {

	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(TransContr.class);
	@Autowired
	private SysCashierServiceImpl sysCashierService;
	@SuppressWarnings("unused")
	@Autowired
	private TransLsService transLsService;
	public int pageSize = 10;
	

	/**
	 * 收款记录查询 返回josn
	 * @param sysTransaction
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/ls/search")
	public @ResponseBody ReciveMsg<PageBean<PayTranLs>> transLsToJson(HttpServletRequest request,HttpServletResponse rep, PayTranLs sysTransaction) throws Exception {
		SysCashier sysCashier = null;
		if(AppDupLog.checkDup(request) == 0){
			PageBean<PayTranLs> pageBean = new PageBean<PayTranLs>();
			String pageIndex = StringUtils.isEmpty(request.getParameter("pageIndex")) == true ? "1" : request.getParameter("pageIndex");
			String pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) == true ? "10" : request.getParameter("pageSize");
			PageParam page = new PageParam();
			page.setPageCurrent(Integer.parseInt(pageIndex));
			page.setPageSize(Integer.parseInt(pageSize));
			Map<String,Object> params = new HashMap<String,Object>();
			String cashierNo = request.getParameter("cashierNo");
			if(StringUtils.isEmpty(cashierNo)){
				ReciveMsg<PageBean<PayTranLs>> reciveMsg = new ReciveMsg<PageBean<PayTranLs>>(0,"4000","cashierNo parameter cannot be empty",null);
				return reciveMsg;		
			}
			// 查询收银员
			sysCashier = sysCashierService.serlectByParam(cashierNo);
			if (sysCashier == null) {
				ReciveMsg<PageBean<PayTranLs>> reciveMsg = new ReciveMsg<PageBean<PayTranLs>>(0,"4003","the user is not exist",null);
				return reciveMsg;
			}
			else{
				String userType = sysCashier.getUserType();
				params.put("userType", userType);
				params.put("cashierNo", sysCashier.getLoginName());
				params.put("merchantNo", sysCashier.getMerchantNo());
				if(StringUtils.isEmpty(sysCashier.getMerchantNo())){
					ReciveMsg<PageBean<PayTranLs>> reciveMsg = new ReciveMsg<PageBean<PayTranLs>>(0,"4002","NOT_PERMISSION",null);
					return reciveMsg;
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
				pageBean = sysCashierService.listPayTranLs(page,params);
				ReciveMsg<PageBean<PayTranLs>> reciveMsg = new ReciveMsg<PageBean<PayTranLs>>(1,"","",pageBean);
				return reciveMsg;
			}
		}
		else{
			//APP同时登录控制
			ReciveMsg<PageBean<PayTranLs>> reciveMsg = new ReciveMsg<PageBean<PayTranLs>>(0,"6020","The user account has been logged on to another machine",null);
			return reciveMsg;
		}
	}
	
	/**
	 * 根据交易订单号查询单条交易流水
	* 
	* @param 
	* @return PayTranLs
	* @throws
	 */
	@RequestMapping(value = "/ls/getLsByTransNum")
	public @ResponseBody ReciveMsg<PayTranLs> getLsByTransNum(HttpServletRequest request,HttpServletResponse rep,String transNum){
		if (AppDupLog.checkDup(request) == 0)
		{
			if(StringUtils.isEmpty(transNum)){
				ReciveMsg<PayTranLs> reciveMsg = new ReciveMsg<PayTranLs>(0,"4000","transNum参数不能为空",null);
				return reciveMsg;
			}
			try{
				PayTranLs t = this.sysCashierService.getLsByTransNum(transNum);
				if(t == null){
					return new ReciveMsg<PayTranLs>(0,"4003","该笔交易不存在",null);
				}
				ReciveMsg<PayTranLs> reciveMsg = new ReciveMsg<PayTranLs>(1,"","",t);
				return reciveMsg;
			}catch(Exception e){
				e.printStackTrace();
				ReciveMsg<PayTranLs> reciveMsg = new ReciveMsg<PayTranLs>(0,"5000","服务器内部异常",null);
				return reciveMsg;
			}
		}
		else{
			ReciveMsg<PayTranLs> reciveMsg = new ReciveMsg<PayTranLs>(0,"6020","该账号已经在其他设备上登录",null);
			return reciveMsg;
		}
	}
	
	
	/**
	 * 初始化报表页面
	 * @param sysTransaction
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/ls/search/page")
	public  String initReport(HttpServletRequest request, HttpServletResponse response,Model model) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		String cashierNo = request.getParameter("cashierNo");
		try{
				if(StringUtils.isEmpty(cashierNo)){
					throw new Exception("cashierNo不能为空");
				}
				SysCashier sysCashier = sysCashierService.serlectByParam(cashierNo);
				if (sysCashier == null) {
					throw new Exception();
				}
				else{
					String userType = sysCashier.getUserType();
					map.put("userType", userType);
					map.put("merchantNo", sysCashier.getMerchantNo());
					if(StringUtils.isEmpty(sysCashier.getMerchantNo())){
						throw new Exception();
					}
					// 店长角色
					if(userType.equals("2")){
						map.put("storeNo", sysCashier.getStoreNo());
					}
					// 收银员角色
					else if(userType.equals("3")){
						String machineNo = request.getParameter("machineNo");
						map.put("machineNo", machineNo);
						map.put("cashierNo", sysCashier.getLoginName());
					}
				}
				String transTime = StringUtils.isEmpty(request.getParameter("transTime")) == true ? DateUtils.getReqDate(new Date()) : request.getParameter("transTime");
				map.put("transTime", transTime);
				request.setAttribute("cashierNo", cashierNo);
				SumTransLs s = this.sysCashierService.sumTrans(map);
				if(s == null){
					s = new SumTransLs();
					s.setCountTran(0);
					s.setSumTran(0.00f);
				}
				request.setAttribute("transTime", transTime);
				request.setAttribute("sumTrans", s);
				request.setAttribute("cashierNo", cashierNo);
				return "appPage/report";
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * @throws Exception 
	* 按时间段输出日交易图形报表数据 返回json
	* @param 
	* @return List<SumTransLs>
	* @throws
	 */
	@RequestMapping("/printDayReport")
	public @ResponseBody List<SumTransLs> printDayReport(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		String cashierNo = request.getParameter("cashierNo");
		if(StringUtils.isEmpty(cashierNo)){
			throw new Exception();
		}
		try{
				SysCashier sysCashier = sysCashierService.serlectByParam(cashierNo);
				if (sysCashier == null) {
					throw new Exception("");
				}
				else{
					String userType = sysCashier.getUserType();
					map.put("userType", userType);
					map.put("merchantNo", sysCashier.getMerchantNo());
					if(StringUtils.isEmpty(sysCashier.getMerchantNo())){
						throw new Exception();
					}
					// 店长角色
					if(userType.equals("2")){
						map.put("storeNo", sysCashier.getStoreNo());
					}
					// 收银员角色
					else if(userType.equals("3")){
						String machineNo = request.getParameter("machineNo");
						map.put("machineNo", machineNo);
						map.put("cashierNo", sysCashier.getLoginName());
					}
				}
				String transTime = StringUtils.isEmpty(request.getParameter("transTime")) == true ? DateUtils.getReqDate(new Date()) : request.getParameter("transTime");
			
				map.put("transTime", transTime);
				List<SumTransLs> list = this.sysCashierService.printDayReport(map);
				return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	

	
	/**
	 * 按时间段 输出总交易笔数和总交易金额 返回json
	* @param 
	* @return SumTransLs
	* @throws
	 */
	@RequestMapping("/printSumTrans")
	public @ResponseBody SumTransLs printSumTrans(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> map = new HashMap<String,Object>();
		String cashierNo = request.getParameter("cashierNo");
		try{
				SysCashier sysCashier = sysCashierService.serlectByParam(cashierNo);
				if (sysCashier == null) {
					throw new Exception("");
				}
				else{
					String userType = sysCashier.getUserType();
					map.put("userType", userType);
					map.put("merchantNo", sysCashier.getMerchantNo());
					if(StringUtils.isEmpty(sysCashier.getMerchantNo())){
						throw new Exception("");
					
					}
					// 店长角色
					if(userType.equals("2")){
						map.put("storeNo", sysCashier.getStoreNo());
					}
					// 收银员角色
					else if(userType.equals("3")){
						String machineNo = request.getParameter("machineNo");
						map.put("machineNo", machineNo);
						map.put("cashierNo", sysCashier.getLoginName());
					}
				}
				String transTime = StringUtils.isEmpty(request.getParameter("transTime")) == true ? DateUtils.getReqDate(new Date()) : request.getParameter("transTime");
				map.put("transTime", transTime);
				SumTransLs s = this.sysCashierService.sumTrans(map);
				if(s == null){
					s = new SumTransLs();
					s.setCountTran(0);
					s.setSumTran(0.00f);
				}
				return s;
		}catch(Exception e){
			this.responseErr(response,"exception:"+e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 按日统计某个商户的总交易笔数和总交易金额 
	* 
	* @param 
	* @return SumTransLs
	* @throws
	 */
	@RequestMapping("/sumTransByDay")
	public @ResponseBody SumTransLs sumTransByDay(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> map = new HashMap<String,Object>();
		String cashierNo = request.getParameter("cashierNo");
		try{
				SysCashier sysCashier = sysCashierService.serlectByParam(cashierNo);
				if (sysCashier == null) {
					throw new Exception("");
				}
				else{
					String userType = sysCashier.getUserType();
					map.put("userType", userType);
					map.put("merchantNo", sysCashier.getMerchantNo());
					if(StringUtils.isEmpty(sysCashier.getMerchantNo())){
						throw new Exception("");
					}
					// 店长角色
					if(userType.equals("2")){
						map.put("storeNo", sysCashier.getStoreNo());
					}
					// 收银员角色
					else if(userType.equals("3")){
						String machineNo = request.getParameter("machineNo");
						map.put("machineNo", machineNo);
						map.put("cashierNo", sysCashier.getLoginName());
					}
					SumTransLs s = this.sysCashierService.sumTransByDay(map);
					return s;
				}
		}catch(Exception e){
			this.responseErr(response,"exception:"+e.getMessage());
			e.printStackTrace();
			return null;
		}
		
	}
	
	/**
	 * 按周统计某个商户的总交易笔数和总交易金额 
	* 
	* @param 
	* @return SumTransLs
	* @throws
	 */
	@RequestMapping("/sumTransByWeek")
	public @ResponseBody SumTransLs sumTransByWeek(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> map = new HashMap<String,Object>();
		String cashierNo = request.getParameter("cashierNo");
		try{
				SysCashier sysCashier = sysCashierService.serlectByParam(cashierNo);
				if (sysCashier == null) {
					throw new Exception("");
				}
				else{
					String userType = sysCashier.getUserType();
					map.put("userType", userType);
					map.put("merchantNo", sysCashier.getMerchantNo());
					if(StringUtils.isEmpty(sysCashier.getMerchantNo())){
						throw new Exception("");
					}
					// 店长角色
					if(userType.equals("2")){
						map.put("storeNo", sysCashier.getStoreNo());
					}
					// 收银员角色
					else if(userType.equals("3")){
						String machineNo = request.getParameter("machineNo");
						map.put("machineNo", machineNo);
						map.put("cashierNo", sysCashier.getLoginName());
					}
					SumTransLs s =  this.sysCashierService.sumTransByWeek(map);
					return s;
				}
		}catch(Exception e){
			this.responseErr(response,"exception:"+e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 按月统计某个商户的总交易笔数和总交易金额 
	* 
	* @param 
	* @return SumTransLs
	* @throws
	 */
	@RequestMapping("/sumTransByMonth")
	public @ResponseBody SumTransLs sumTransByMonth(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> map = new HashMap<String,Object>();
		String cashierNo = request.getParameter("cashierNo");
		try{
				SysCashier sysCashier = sysCashierService.serlectByParam(cashierNo);
				if (sysCashier == null) {
					throw new Exception("");
				}
				else{
					String userType = sysCashier.getUserType();
					map.put("userType", userType);
					map.put("merchantNo", sysCashier.getMerchantNo());
					if(StringUtils.isEmpty(sysCashier.getMerchantNo())){
						throw new Exception("");
					}
					// 店长角色
					if(userType.equals("2")){
						map.put("storeNo", sysCashier.getStoreNo());
					}
					// 收银员角色
					else if(userType.equals("3")){
						String machineNo = request.getParameter("machineNo");
						map.put("machineNo", machineNo);
						map.put("cashierNo", sysCashier.getLoginName());
					}
					SumTransLs s = this.sysCashierService.sumTransByMonth(map);
					return s;
				}
		
		}catch(Exception e){
			this.responseErr(response,"exception:"+e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * 输出 按日交易的报表  返回json
	* 
	* @param 
	* @return List<SumTransLs>
	* @throws
	 */
	@RequestMapping("/printReportByDay")
	public @ResponseBody List<SumTransLs> printReportByDay(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> map = new HashMap<String,Object>();
		String cashierNo = request.getParameter("cashierNo");
		try{
				SysCashier sysCashier = sysCashierService.serlectByParam(cashierNo);
				if (sysCashier == null) {
					throw new Exception("");
				}
				else{
					String userType = sysCashier.getUserType();
					map.put("userType", userType);
					map.put("merchantNo", sysCashier.getMerchantNo());
					if(StringUtils.isEmpty(sysCashier.getMerchantNo())){
						throw new Exception("");
					}
					// 店长角色
					if(userType.equals("2")){
						map.put("storeNo", sysCashier.getStoreNo());
					}
					// 收银员角色
					else if(userType.equals("3")){
						String machineNo = request.getParameter("machineNo");
						map.put("machineNo", machineNo);
						map.put("cashierNo", sysCashier.getLoginName());
					}
					List<SumTransLs> list = this.sysCashierService.printReportByDay(map);
					return list;
				}
		}catch(Exception e){
			this.responseErr(response,"exception:"+e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 输出 按周交易的报表  返回json
	* 
	* @param 
	* @return List<SumTransLs>
	* @throws
	 */
	@RequestMapping("/printReportByWeek")
	public @ResponseBody List<SumTransLs> printReportByWeek(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> map = new HashMap<String,Object>();
		String cashierNo = request.getParameter("cashierNo");
		try{
				SysCashier sysCashier = sysCashierService.serlectByParam(cashierNo);
				if (sysCashier == null) {
					throw new Exception("");
				}
				else{
					String userType = sysCashier.getUserType();
					map.put("userType", userType);
					map.put("merchantNo", sysCashier.getMerchantNo());
					if(StringUtils.isEmpty(sysCashier.getMerchantNo())){
						throw new Exception("");
					}
					// 店长角色
					if(userType.equals("2")){
						map.put("storeNo", sysCashier.getStoreNo());
					}
					// 收银员角色
					else if(userType.equals("3")){
						String machineNo = request.getParameter("machineNo");
						map.put("machineNo", machineNo);
						map.put("cashierNo", sysCashier.getLoginName());
					}
					List<SumTransLs> list =  this.sysCashierService.printReportByWeek(map);
					return list;
				}
		}catch(Exception e){
			this.responseErr(response,"exception:"+e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * 输出 按月交易的报表  返回json
	* 
	* @param 
	* @return List<SumTransLs>
	* @throws
	 */
	@RequestMapping("/printReportByMonth")
	public @ResponseBody List<SumTransLs> printReportByMonth(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> map = new HashMap<String,Object>();
		String cashierNo = request.getParameter("cashierNo");
		try{
				SysCashier sysCashier = sysCashierService.serlectByParam(cashierNo);
				if (sysCashier == null) {
					throw new Exception("");
				}
				else{
					String userType = sysCashier.getUserType();
					map.put("userType", userType);
					map.put("merchantNo", sysCashier.getMerchantNo());
					if(StringUtils.isEmpty(sysCashier.getMerchantNo())){
						throw new Exception("");
					}
					// 店长角色
					if(userType.equals("2")){
						map.put("storeNo", sysCashier.getStoreNo());
					}
					// 收银员角色
					else if(userType.equals("3")){
						String machineNo = request.getParameter("machineNo");
						map.put("machineNo", machineNo);
						map.put("cashierNo", sysCashier.getLoginName());
					}
					List<SumTransLs> list =  this.sysCashierService.printReportByMonth(map);
					return list;
				}
		}catch(Exception e){
			this.responseErr(response,"exception:"+e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	

}
