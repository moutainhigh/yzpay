package com.yunpay.permission.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.yunpay.common.core.dwz.DwzAjax;
import com.yunpay.common.core.page.PageBean;
import com.yunpay.common.core.page.PageParam;
import com.yunpay.common.core.utils.ExcelUtil;
import com.yunpay.controller.common.BaseController;
import com.yunpay.permission.entity.Merchant;
import com.yunpay.permission.entity.StoreEntity;
import com.yunpay.permission.service.AddressService;
import com.yunpay.permission.service.MerchService;
import com.yunpay.permission.service.StoreService;
/**
 * 
 * 类名称                     门店控制器类
 * 文件名称:     StoreCtrl.java
 * 内容摘要: 
 * @author:   Zhao Xiaoman
 * @version:  1.0  
 * @Date:     2017年7月12日上午9:40:13
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年7月12日   Zhao Xiaoman       1.0       新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
@Controller
@RequestMapping("/sys/store")
public class StoreCtrl extends BaseController
{
	private static Log log = LogFactory.getLog(StoreCtrl.class);

	@Autowired
	private StoreService storeService;
	@Autowired
	private AddressService addressService;
	@Autowired
	private MerchService merchService;

	@SuppressWarnings("rawtypes")
	@RequestMapping("/list")
	public String listStore(HttpServletRequest req, PageParam pageParam, StoreEntity storeEntity, Model model)
	{
		try
		{
			PageBean pageBean = storeService.listPage(pageParam, storeEntity);
			Integer provId = StringUtils.isBlank(storeEntity.getProv()) ? null
					: Integer.parseInt(storeEntity.getProv());
			Integer cityId = StringUtils.isBlank(storeEntity.getCity()) ? null
					: Integer.parseInt(storeEntity.getCity());
			Integer areaId = StringUtils.isBlank(storeEntity.getArea()) ? null
					: Integer.parseInt(storeEntity.getArea());
			req.setAttribute("provMap", addressService.getProvList());
			req.setAttribute("cityMap", addressService.getNextList(provId));
			req.setAttribute("areaMap", addressService.getNextList(cityId));
			req.setAttribute("prov", provId);
			req.setAttribute("city", cityId);
			req.setAttribute("area", areaId);
			model.addAttribute(pageBean);
			return "modules/store/storeList";
		} catch (Exception e)
		{
			log.error("== ListStore exception:", e);
			return operateError("获取数据失败", model);
		}
	}

	/**
	 * 下拉查询市
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/getCity")
	public @ResponseBody Map<Integer, String> getCity(HttpServletRequest req)
	{
		String id = req.getParameter("id");
		return addressService.getNextList(Integer.parseInt(id));
	}

	/**
	 * 下拉查询区
	 * @param req
	 * @return
	 */
	/* @RequiresPermissions("sys:store:add") */
	@RequestMapping("/getArea")
	public @ResponseBody Map<Integer, String> getArea(HttpServletRequest req)
	{
		String id = req.getParameter("id");
		return addressService.getNextList(Integer.parseInt(id));
	}
	
	/**
	 * 修改状态码 （停用/启用）
	 * 
	 * @param storeNo
	 *            门店编码
	 * @param status
	 *            状态码
	 * 
	 **/
	/* @RequiresPermissions("sys:store:edit") */
	@RequestMapping("/EditStatus")
	public String editEditStatus(HttpServletRequest req, DwzAjax dwz, @RequestParam("id") Integer id,
			@RequestParam("status") Integer status, Model model)
	{
		try
		{
			if (storeService.updateStatus(id, status) > 0)
			{
				return operateSuccess(model, dwz);
			} else
			{
				return operateError("修改状态失败", model);
			}
		} catch (Exception e)
		{
			log.error("== editSysStoreStatus exception:", e);
			return operateError("获取数据失败", model);
		}
	}

	/**
	 * 根据门店编码删除
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping("/delete")
	public String findinfoById(HttpServletRequest req, @RequestParam("storeNo") String storeNo, DwzAjax dwz,
			Model model)
	{
		try
		{
			if (storeService.selectByStoreNo(storeNo) == null)
			{
				return operateError("无法获取要删除的角色", model);
			}
			storeService.deleteByStoreNo(storeNo);
			return operateSuccess(model, dwz);
		} catch (Exception e)
		{
			log.error("== deleteSysStore exception:", e);
			return operateError("获取数据失败", model);
		}
	}
			
   /**
	 * 跳转到增加门店页面
	 * 
	 * @param req
	 * @param model
	 * @param 
	 * @return
	 */
	@RequestMapping("/addUI")
	public String addStoreUI(HttpServletRequest req, Model model)
	{
		try
		{
			req.setAttribute("provMap", addressService.getProvList());
			return "modules/store/storeAdd";
		} catch (Exception e)
		{
			log.error("== addStore exception:", e);
			return operateError("获取数据失败", model);
		}
	}
	
	/**
	 * 跳转到增加门店页面
	 * 
	 * @param req
	 * @param model
	 * @param 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/lookupmerchId")
	public String lookUp(HttpServletRequest req, PageParam pageParam, Model model, Merchant merchant)
	{
		try
		{
			Map<String, Object> map = new HashMap<>();
			if (StringUtils.isNotBlank(merchant.getRegisterName()))
			{
				map.put("registerName",merchant.getRegisterName());
			} else
			{
				map.put("registerName",null);
			}
			PageBean pageBean = merchService.listPage(pageParam, map);
			model.addAttribute(pageBean);
			return "modules/store/lookup";
		} catch (Exception e)
		{
			log.error("== addStore exception:", e);
			return operateError("获取数据失败", model);
		}
	}
	

	/**
	 * 增加门店
	 * @param req
	 * @param model
	 * @param dwz
	 * @param storeEntity
	 * @return
	 */
	@RequestMapping("/add")
	public String addStore(HttpServletRequest req, Model model, DwzAjax dwz, StoreEntity storeEntity)
	{
		try
		{
			// 插入数据
			if (storeService.addInfo(storeEntity) > 0)
			{
				return operateSuccess(model, dwz);
			} else
			{
				return operateError("保存数据失败", model);
			}
		} catch (Exception e)
		{
			log.error("== addSysStore exception:", e);
			return operateError("获取数据失败", model);
		}
	}

   /**
	 * 跳转到修改页面
	 * 
	 * @param req
	 * @param model
	 * @param demoId
	 * @return
	 */
	@RequestMapping("/editUI")
	public String editStoreUI(HttpServletRequest req, Model model, @RequestParam("storeNo") String storeNo,@RequestParam("type") Integer type)
	{
		try
		{
			// 根据门店编码 查询
			StoreEntity storeEntity = storeService.selectByStoreNo(storeNo);
			if (storeEntity == null)
			{
				return operateError("无法获取需要修改的数据", model);
			}else {
				model.addAttribute("storeInfo",storeEntity);
			}
			// 获取行政区域信息
			req.setAttribute("provMap", addressService.getProvList());
			req.setAttribute("cityMap", addressService.getNextList(Integer.parseInt(storeEntity.getProv())));
			req.setAttribute("areaMap", addressService.getNextList(Integer.parseInt(storeEntity.getCity())));
			req.setAttribute("prov", Integer.parseInt(storeEntity.getProv()));
			req.setAttribute("city", Integer.parseInt(storeEntity.getCity()));
			req.setAttribute("area", Integer.parseInt(storeEntity.getArea()));
			model.addAttribute("storeEntity",storeEntity);	
			if (type==0)
			{
				return "modules/store/storeEdit";
			} else
			{
				return "modules/store/storeLook";
			}
			
		} catch (Exception e)
		{
			log.error("== editSysStore exception:", e);
			return operateError("获取数据失败", model);
		}
	}

   /**
	 * 
	 * 修改
	 * 
	 **/
	@RequestMapping("/edit")
	public String editSysStore(HttpServletRequest req, Model model, DwzAjax dwz, StoreEntity storeEntity)
	{
		try
		{
			String storeNo = storeEntity.getStoreNo();
			if (storeService.selectByStoreNo(storeNo) == null)
			{
				return operateError("无法获取需要修改的数据", model);
			}
			if (storeService.updateInfo(storeEntity) > 0)
			{
				return operateSuccess(model, dwz);
			} else
			{
				return operateError("保存数据失败", model);
			}
		} catch (Exception e)
		{
			log.error("== editSysStore exception:", e);
			return operateError("获取数据失败", model);
		}
	}
	
	/**
	 * 导出数据到Excel
	* @param 
	* @return void
	* @throws
	 */
	@RequestMapping(value = "exportExcel", method = RequestMethod.POST)  
	public void exportExcel(HttpServletRequest req,HttpServletResponse response,StoreEntity storeEntity) { 
		String agent = req.getHeader("USER-AGENT").toLowerCase();
		List<StoreEntity> list = this.storeService.getStoreList(storeEntity);
	    String fileName = "门店信息列表";  
	    try {
			ExcelUtil.writeExcel(response, fileName,agent, list, StoreEntity.class);
		} catch (IllegalArgumentException | IllegalAccessException
				| IOException e) {
			e.printStackTrace();
		}  
	   
	}
}