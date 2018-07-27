package com.yunpay.permission.controller;
import java.util.ArrayList;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.yunpay.common.core.exception.BizException;
import com.yunpay.common.core.page.PageBean;
import com.yunpay.common.core.page.PageParam;
import com.yunpay.common.core.utils.DateUtils;
import com.yunpay.controller.common.BaseController;
import com.yunpay.h5merch.permission.entity.MerchUser;
import com.yunpay.h5merch.permission.entity.SumTransLs;
import com.yunpay.h5merch.permission.service.TransLsService;
import com.yunpay.h5merch.service.impl.SysCashierServiceImpl;
import com.yunpay.permission.entity.PayTranLs;
import com.yunpay.permission.entity.StoreEntity;
import com.yunpay.permission.service.MerchantReportService;
@Controller
@RequestMapping("/sys/reportContr")
public class ReportContr extends BaseController{
	@Autowired
	private MerchantReportService MerchantReportService;
	@Autowired
	private TransLsService transLsService;
	@Autowired
	private SysCashierServiceImpl sysCashierService;
	private int pageSize = 10;
	/**
	 * 
	 * 文件名称:
	 * 内容摘要: 营业报表、账单汇总、日报
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
	/**
	* 保存查询参数
	* @param 
	* @return void
	* @throws
	 */
	public Map<String,Object> setQueryParam(HttpServletRequest req){
		try{
			Map<String,Object> map = new HashMap<String,Object>();
			// 前端查询条件参数
			String merchantName = StringUtils.isEmpty(req.getParameter("merchantName")) == true ? null : req.getParameter("merchantName");
			String timeBegin = StringUtils.isEmpty(req.getParameter("timeBegin")) == true ? null : req.getParameter("timeBegin");
			String timeEnd = StringUtils.isEmpty(req.getParameter("timeEnd")) == true ? null : req.getParameter("timeEnd");
			String transNum = StringUtils.isEmpty(req.getParameter("transNum")) == true ? null : req.getParameter("transNum");
			String channel = StringUtils.isEmpty(req.getParameter("channel")) == true ? null : req.getParameter("channel");
			String status = StringUtils.isEmpty(req.getParameter("status")) == true ? null : req.getParameter("status");
			String transTime = StringUtils.isEmpty(req.getParameter("transTime")) == true ? null : req.getParameter("transTime");
			String upTransTime = StringUtils.isEmpty(req.getParameter("upTransTime")) == true ? null : req.getParameter("upTransTime");
			String todayTransTime = StringUtils.isEmpty(req.getParameter("todayTransTime")) == true ? null : req.getParameter("todayTransTime");
			map.put("timeBegin", timeBegin);
			map.put("timeEnd", timeEnd);
			map.put("merchantName", merchantName);
			map.put("transNum", transNum);
			map.put("channel", channel);
			map.put("status", status);
			map.put("transTime", transTime);
			map.put("upTransTime", upTransTime);
			map.put("todayTransTime", todayTransTime);
			return map;
		}catch(Exception e){
			throw new BizException("获取参数时发生异常!");
			
		}
	}

	/**
	 * 查询营业流水、返回jsp
	* @param 
	* @return String
	* @throws
	 */
	@RequestMapping("/list")
	public String list(HttpServletRequest request,HttpServletResponse response,Model model){
		String transTime = StringUtils.isEmpty(request.getParameter("transTime")) == true ? DateUtils.getReqDate(new Date()) : request.getParameter("transTime");
		HttpSession session = request.getSession();
		// 默认为第一页
		int pageIndex = 1;
		if(!StringUtils.isEmpty(request.getParameter("pageIndex"))){
			pageIndex = Integer.parseInt(request.getParameter("pageIndex").toString());
		}
		// 营业流水
 		PageParam page = new PageParam();
		page.setPageCurrent(pageIndex);
		page.setPageSize(pageSize);
		Map<String,Object> map = this.setQueryParam(request);
		MerchUser user = (MerchUser)session.getAttribute("merchUser");
		String merchantNo =  user.getMerchantNo();
		map.put("merchantNo", merchantNo);
		PageBean<PayTranLs> pageBean = this.MerchantReportService.list(page, map);
		request.setAttribute("transTime", transTime);
		request.setAttribute("pageBean", pageBean);
		try {
			model.addAttribute("action", "init");
			return "report/list";
		}  catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 点击搜索框 查询营业日报 返回jsp
	* @param 
	* @return void
	* @throws
	 */
	@RequestMapping("/reportSearch")
	public String reportSearch(HttpServletRequest request,HttpServletResponse response,Model model){
		String transTime = request.getParameter("transTime");
		HttpSession session = request.getSession();
		MerchUser user = (MerchUser)session.getAttribute("merchUser");
		String merchantNo =  user.getMerchantNo();
		Map<String,Object > map = new HashMap<String,Object>();
		map.put("transTime", transTime);
		map.put("merchantNo", merchantNo);
 		PageParam page = new PageParam();
		page.setPageCurrent(1);
		page.setPageSize(pageSize);
		PageBean<SumTransLs> reportPageBean = this.transLsService.dayReport(page, map);
		request.setAttribute("reportPageBean", reportPageBean);
		model.addAttribute("action", "search");
		return "report/list";
	}
	
	/**
	 * 营业流水下拉刷新 返回jsp
	* @param 
	* @return void
	* @throws
	 */
	@RequestMapping("/lsDown")
	public String lsDown(HttpServletRequest request,HttpServletResponse response,Model model){
		try{
			HttpSession session = request.getSession();
			MerchUser user = (MerchUser)session.getAttribute("merchUser");
			String merchantNo =  user.getMerchantNo();
			Map<String,Object> map = this.setQueryParam(request);
			map.put("merchantNo", merchantNo);
			PageParam page = new PageParam();
			page.setPageCurrent(1);
			page.setPageSize(pageSize);
			PageBean<PayTranLs> pageBean = this.MerchantReportService.list(page, map);
			request.setAttribute("lsPageBean", pageBean);
			model.addAttribute("action", "search");
			return "report/list";
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	

	/**
	 * 
	 * 营业流水上拉翻页 返回json
	* @param 
	* @return void
	* @throws
	 */
	@RequestMapping("/lsUp")
	public @ResponseBody Map<String,Object> lsUp(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> m = new HashMap<String,Object>();
		// 默认为第一页
		int pageIndex = 1;
		try{
			if(!StringUtils.isEmpty(request.getParameter("pageIndex"))){  
				pageIndex = Integer.parseInt(request.getParameter("pageIndex").toString());
			}
	 		PageParam page = new PageParam();
			page.setPageCurrent(pageIndex);
			page.setPageSize(pageSize);
			Map<String,Object> map = this.setQueryParam(request);
			MerchUser user = this.getUserSession(request);
			String merchantNo =  null;
			if(user != null){
				merchantNo =  user.getMerchantNo();
			}
			if(StringUtils.isEmpty(merchantNo)){
				merchantNo = request.getParameter("merchantNo");
				if(StringUtils.isEmpty(merchantNo)){
					throw new BizException("merchantNo不能为空.");
				}
			}
			map.put("merchantNo",merchantNo);
			// 输出当前页数和分页数据
			PageBean<PayTranLs> pageBean = this.MerchantReportService.list(page, map);
			List<PayTranLs> newList = new ArrayList<PayTranLs>();
			for(int i=0; i<pageBean.getRecordList().size(); i++){
				PayTranLs p = pageBean.getRecordList().get(i);
				int channel = Integer.parseInt(p.getChannel());
				switch(channel){
					case 1:p.setChannel("支付宝");break;
					case 2:p.setChannel("微信");  break;
					case 3:p.setChannel("银联");  break;
					case 4:p.setChannel("预存款");break;
				}
				p.setTransTimeStr(DateUtils.dateFormatYY(p.getTransTime()));
				int status = Integer.parseInt(p.getStatus());
				switch(status){
					case 0:p.setStatus("未付款");  break;
					case 1:p.setStatus("付款中");  break;
					case 2:p.setStatus("已付款");  break;
					case 3:p.setStatus("已退款");  break;
					case 4:p.setStatus("退款中");  break;
					case 5:p.setStatus("退款失败");break;
					case 6:p.setStatus("付款失败");break;
					case 7:p.setStatus("取消订单");break;
				}
				newList.add(p);
			}
			pageBean.setRecordList(newList);
			m.put("item", pageBean.getRecordList());
			m.put("currentPage", pageBean.getCurrentPage());
			m.put("totalPage", pageBean.getTotalPage());
			m.put("totalCount", pageBean.getTotalCount());
			return m;
		}catch(Exception e){
			e.printStackTrace();
			m.put("status", "exception{"+e.getMessage()+"}");
			return m;
		}
	
	}
	
	
	/**
	 * 营业日报下拉刷新 返回jsp
	* @param 
	* @return void
	* @throws
	 */
	@RequestMapping("/reportDown")
	public String reportDown(HttpServletRequest request,HttpServletResponse response,Model model){
		PageParam page = new PageParam();
		page.setPageCurrent(1);
		page.setPageSize(pageSize);
		try {
			HttpSession session = request.getSession();
			MerchUser user = (MerchUser)session.getAttribute("merchUser");
			String merchantNo =  user.getMerchantNo();
			// 营业日报
			String transTime = StringUtils.isEmpty(request.getParameter("transTime")) == true ? DateUtils.getReqDate(new Date()) : request.getParameter("transTime");
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("transTime", transTime);
			map.put("merchantNo", merchantNo);
			PageBean<SumTransLs> reportPageBean = this.transLsService.dayReport(page, map);
			request.setAttribute("reportPageBean", reportPageBean);
			request.setAttribute("transTime", transTime);
			model.addAttribute("action", "search");
			return "report/list";
		}  catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 营业日报上拉翻页 返回json
	* 
	* @param 
	* @return void
	* @throws
	 */
	@RequestMapping("/reportUp")
	public @ResponseBody Map<String,Object> reportUp(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> m = new HashMap<String,Object>();
		// 默认为第一页
		String transTime = StringUtils.isEmpty(request.getParameter("transTime")) == true ? DateUtils.getReqDate(new Date()) : request.getParameter("transTime");
		try{
			int pageIndex = 1;
			if(!StringUtils.isEmpty(request.getParameter("pageIndex"))){
				pageIndex = Integer.parseInt(request.getParameter("pageIndex").toString());
			}
			PageParam page = new PageParam();
			page.setPageCurrent(pageIndex);
			page.setPageSize(pageSize);
			Map<String,Object> map = this.setQueryParam(request);
			MerchUser user = this.getUserSession(request);
			map.put("merchantNo", user.getMerchantNo());
			map.put("transTime", transTime);
			PageBean<SumTransLs> pageBean = this.transLsService.dayReport(page, map);
			// 输出当前页数和分页数据
			m = new HashMap<String,Object>();
			m.put("currentPage", pageBean.getCurrentPage());
			m.put("item", pageBean.getRecordList());
			m.put("totalPage", pageBean.getTotalPage());
			m.put("totalCount", pageBean.getTotalCount());
			return m;
		}catch(Exception e){
			e.printStackTrace();
			m.put("status", "exception{"+e.getMessage()+"}");
			return m;
		}
	}
	
	

	
	
	/**
	 * 
	* 查询营业流水详细
	* @param 
	* @return String
	* @throws
	 */
	@RequestMapping("/list/detail")
	public String lsDetail(HttpServletRequest request,HttpServletResponse response,Model model){
		Map<String,Object> param = new HashMap<String,Object>();
		String merchantNo = request.getParameter("merchantNo");
		String transNum = request.getParameter("transNum");
		param.put("merchantNo", merchantNo);
		param.put("transNum", transNum);
		try{
			PayTranLs payTranLs = this.MerchantReportService.listDetail(param);
		    request.setAttribute("payTranLs", payTranLs);
		    model.addAttribute("action", "detail");
			return "report/list";
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	/**
	 * 查询营业日报,返回json数据
	* @param 
	* @return String
	* @throws
	 */
	@RequestMapping("/reportToJson")
	public @ResponseBody Map<String,Object> reportToJson(HttpServletRequest request,HttpServletResponse response){
		// 默认为第一页
		String transTime = StringUtils.isEmpty(request.getParameter("transTime")) == true ? DateUtils.getReqDate(new Date()) : request.getParameter("transTime");
		int pageIndex = 1;
		if(!StringUtils.isEmpty(request.getParameter("pageIndex"))){
			pageIndex = Integer.parseInt(request.getParameter("pageIndex").toString());
		}
 		PageParam page = new PageParam();
		page.setPageCurrent(pageIndex);
		page.setPageSize(pageSize);
		Map<String,Object> map = this.setQueryParam(request);
		MerchUser user = this.getUserSession(request);
		map.put("merchantNo", user.getMerchantNo());
		map.put("transTime", transTime);
		PageBean<SumTransLs> pageBean = this.transLsService.dayReport(page, map);
		// 输出当前页数和分页数据
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("currentPage", pageBean.getCurrentPage());
		m.put("item", pageBean.getRecordList());
		m.put("totalPage", pageBean.getTotalPage());
		m.put("totalCount", pageBean.getTotalCount());
		m.put("type", "report");
		return m;
	}
	

	
	/**
	 * 查询营业日报明细
	* 
	* @param 
	* @return String
	* @throws
	 */
	@RequestMapping("/dayReport/detail")
	public String reportDetail(HttpServletRequest req,HttpServletResponse rep,Model model){
		String transTime = StringUtils.isEmpty(req.getParameter("transTime")) == true ? DateUtils.getReqDate(new Date()) : req.getParameter("transTime");
		String storeNo = req.getParameter("storeNo");
		if(StringUtils.isEmpty(storeNo)){
			throw new BizException("storeNo不能为空");
		}
		// 查询门店信息
		StoreEntity store = this.sysCashierService.selectStoreByOrgNo(storeNo);
		MerchUser user = this.getUserSession(req);
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("storeNo", storeNo);
		param.put("merchantNo", user.getMerchantNo());
		param.put("transTime", transTime);
		List<SumTransLs> sumTransLs = this.transLsService.reportDetail(param);
		req.setAttribute("sumTransLs", sumTransLs);
		req.setAttribute("store", store);
		model.addAttribute("action", "detail");
		return "report/list";
	}
	
	
	/**
	 * 点击搜索框 搜索账单汇总数据 返回jsp
	* 
	* @param 
	* @return String
	* @throws
	 */
	@RequestMapping("/billSearch")
	public String billSearch(HttpServletRequest req,HttpServletResponse rep,Model model){
		String transTime = StringUtils.isEmpty(req.getParameter("transTime2")) == true ? DateUtils.getReqDate(new Date()) : req.getParameter("transTime2");
		MerchUser user = this.getUserSession(req);
		String merchantNo = user.getMerchantNo();
		PageParam page = new PageParam();
		page.setPageCurrent(1);
		page.setPageSize(pageSize);
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("merchantNo", merchantNo);
		param.put("transTime", transTime);
		PageBean<SumTransLs> billPageBean = this.transLsService.bill(page, param);
		req.setAttribute("billPageBean", billPageBean);
		req.setAttribute("transTime", transTime);
		try {
			model.addAttribute("action", "search");
			return "report/list";
		}  catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	/**
	* 输出按时间段交易图形报表数据 返回json
	* @param 
	* @return List<SumTransLs>
	* @throws
	 */
	@RequestMapping("/printDayReport")
	public @ResponseBody List<SumTransLs> printDayReport(HttpServletRequest req,HttpServletResponse rep){
		try{
			String transTime = StringUtils.isEmpty(req.getParameter("transTime")) == true ? DateUtils.getReqDate(new Date()) : req.getParameter("transTime");
			Map<String,Object> param = new HashMap<String,Object>();
			HttpSession session = req.getSession();
			MerchUser user = (MerchUser)session.getAttribute("merchUser");
			param.put("merchantNo", user.getMerchantNo());
			param.put("transTime", transTime);
			List<SumTransLs> list = this.transLsService.printDayReport(param);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 输出按时间段的总交易笔数和总交易金额 返回json
	* @param 
	* @return SumTransLs
	* @throws
	 */
	@RequestMapping("/sumTrans")
	public @ResponseBody SumTransLs sumTrans(HttpServletRequest req,HttpServletResponse rep){
		try{
			String transTime = StringUtils.isEmpty(req.getParameter("transTime")) == true ? DateUtils.getReqDate(new Date()) : req.getParameter("transTime");
			Map<String,Object> param = new HashMap<String,Object>();
			HttpSession session = req.getSession();
			MerchUser user = (MerchUser)session.getAttribute("merchUser");
			param.put("merchantNo", user.getMerchantNo());
			param.put("transTime", transTime);
			SumTransLs s = this.transLsService.sumTrans(param);
			if(s == null){
				s = new SumTransLs();
				s.setCountTran(0);
				s.setSumTran(0.00f);
			}
			return s;
		}catch(Exception e){
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
	public @ResponseBody SumTransLs sumTransByDay(HttpServletRequest req,HttpServletResponse rep){
		try{
			Map<String,Object> param = new HashMap<String,Object>();
			HttpSession session = req.getSession();
			MerchUser user = (MerchUser)session.getAttribute("merchUser");
			param.put("merchantNo", user.getMerchantNo());
			SumTransLs s = this.transLsService.sumTransByDay(param);
			return s;
		}catch(Exception e){
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
	public @ResponseBody SumTransLs sumTransByWeek(HttpServletRequest req,HttpServletResponse rep){
		try{
			Map<String,Object> param = new HashMap<String,Object>();
			HttpSession session = req.getSession();
			MerchUser user = (MerchUser)session.getAttribute("merchUser");
			param.put("merchantNo", user.getMerchantNo());
			SumTransLs s = this.transLsService.sumTransByWeek(param);
			return s;
		}catch(Exception e){
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
	public @ResponseBody SumTransLs sumTransByMonth(HttpServletRequest req,HttpServletResponse rep){
		try{
			Map<String,Object> param = new HashMap<String,Object>();
			HttpSession session = req.getSession();
			MerchUser user = (MerchUser)session.getAttribute("merchUser");
			param.put("merchantNo", user.getMerchantNo());
			SumTransLs s = this.transLsService.sumTransByMonth(param);
			return s;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * 输出 按日交易的报表  返回json
	* 
	* @param 
	* @return SumTransLs
	* @throws
	 */
	@RequestMapping("/printReportByDay")
	public @ResponseBody List<SumTransLs> printReportByDay(HttpServletRequest req,HttpServletResponse rep){
		try{
			HttpSession session = req.getSession();
			MerchUser user = (MerchUser)session.getAttribute("merchUser");
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("merchantNo", user.getMerchantNo());
			List<SumTransLs> list = this.transLsService.printReportByDay(param);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * 输出 按周交易的报表  返回json
	* 
	* @param 
	* @return SumTransLs
	* @throws
	 */
	@RequestMapping("/printReportByWeek")
	public @ResponseBody List<SumTransLs> printReportByWeek(HttpServletRequest req,HttpServletResponse rep){
		try{
		
			HttpSession session = req.getSession();
			MerchUser user = (MerchUser)session.getAttribute("merchUser");
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("merchantNo", user.getMerchantNo());
			List<SumTransLs> list = this.transLsService.printReportByWeek(param);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * 输出 按月交易的报表  返回json
	* 
	* @param 
	* @return SumTransLs
	* @throws
	 */
	@RequestMapping("/printReportByMonth")
	public @ResponseBody List<SumTransLs> printReportByMonth(HttpServletRequest req,HttpServletResponse rep){
		try{
			HttpSession session = req.getSession();
			MerchUser user = (MerchUser)session.getAttribute("merchUser");
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("merchantNo", user.getMerchantNo());
			List<SumTransLs> list = this.transLsService.printReportByMonth(param);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * 初始化报表页面
	* 
	* @param 
	* @return String
	* @throws
	 */
	@RequestMapping("/showReport")
	public String initReport(HttpServletRequest req,HttpServletResponse rep,Model model){
		try{
			String transTime = StringUtils.isEmpty(req.getParameter("transTime")) == true ? DateUtils.getReqDate(new Date()) : req.getParameter("transTime");
			Map<String,Object> param = new HashMap<String,Object>();
			HttpSession session = req.getSession();
			MerchUser user = (MerchUser)session.getAttribute("merchUser");
			param.put("merchantNo", user.getMerchantNo());
			param.put("transTime", transTime);
			
			SumTransLs s = this.transLsService.sumTrans(param);
			if(s == null){
				s = new SumTransLs();
				s.setCountTran(0);
				s.setSumTran(0.00f);
			}
			req.setAttribute("transTime", transTime);
			req.setAttribute("sumTrans", s);
			return "report/report";
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
