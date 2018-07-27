package com.yunpay.permission.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.yunpay.common.core.dwz.DwzAjax;
import com.yunpay.controller.common.BaseController;
import com.yunpay.h5merch.permission.entity.H5StoreEntity;
import com.yunpay.h5merch.permission.entity.MerchUser;
import com.yunpay.h5merch.permission.entity.ReciveMsg;
import com.yunpay.h5merch.permission.service.H5StoreService;
import com.yunpay.h5merch.permission.service.MerchUserService;
import com.yunpay.h5merch.service.impl.SysCashierServiceImpl;
import com.yunpay.permission.service.AddressService;
/**
 * 
 * 类名称                    门店管理控制器类
 * 文件名称:     StoreController.java
 * 内容摘要: 
 * @author:   Zhao Xiaoman
 * @version:  1.0  
 * @Date:     2017年8月8日下午3:38:49
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年8月8日   Zhao Xiaoman       1.0       新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
@Controller
@RequestMapping("/sys/store")
public class StoreController extends BaseController
{
	private static Log log = LogFactory.getLog(StoreController.class);

	@Autowired
	private H5StoreService storeService;
	@Autowired
	private AddressService addressService;
	@Autowired
	private MerchUserService merchUserService;
	@Autowired
	private SysCashierServiceImpl sysCashierService;

	/**
	 * 函数功能说明 ：商铺列表.
	 * @param req
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/list")
	public String listStore(HttpServletRequest req,
			@RequestParam("pageIndex") Integer pageIndex,
			@RequestParam("type") Integer type,@RequestParam("merchant") String merchant,Model model)
	{
		try
		{
			HttpSession session = req.getSession();
			MerchUser merchUser = (MerchUser)session.getAttribute("merchUser");
			String merchantNo = merchUser==null?null:merchUser.getMerchantNo();
			if (StringUtils.isNotBlank(merchant))
			{
				merchantNo = merchant;
			}
			//分页查找门店
			Map<String, Object> map = storeService.getStoreByMerId(merchantNo,pageIndex);
			List<H5StoreEntity> list = (List<H5StoreEntity>)map.get("storeList");
			//取得总门店数量
			Integer total = (Integer)map.get("total");
			req.setAttribute("storeList", list);
			req.setAttribute("pageIndex", pageIndex);
			req.setAttribute("total", total);
			req.setAttribute("merchant", merchantNo);
			switch (type)
			{
			case 0:
				return "store/storelist"; //返回门店列表页面
			case 1:
				return "store/storerelist"; // 返回门店列表填充页面
			default:
				return "store/storelist";
			}		
		} catch (Exception e)
		{
			log.error("== listStore exception:", e);
			switch (type)
			{
			case 0:
				return "store/storelist";
			case 1:
				return "store/storerelist";
			default:
				return "store/storelist";
			}		
		}		
	}

	/**
	 * 函数功能说明 ：店铺列表页面跳转添加商铺.
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping("/goadd")
	public String goStoreAdd(HttpServletRequest req, Model model,@RequestParam("merchant") String merchant) {
		req.setAttribute("provMap", addressService.getProvList());
		req.setAttribute("merchant", merchant);
		return "store/storeadd";
	}
	
	/**
	 * 增加商铺
	 * @param req
	 * @param model
	 * @param dwz
	 * @param storeEntity
	 * @return
	 */
	@RequestMapping("/add")
	public @ResponseBody ReciveMsg<String> storeAdd(HttpServletRequest req,H5StoreEntity storeEntity,@RequestParam("merchant") String merchant,Model model)
	{
		req.setAttribute("merchant", merchant);
		try
		{
			storeEntity.setMerId(storeService.getIdByMerchNo(merchant).getId());
			storeEntity.setStoreNo(merchant);
			// 插入数据
			return storeService.addInfo(storeEntity);
		} catch (Exception e)
		{
			log.error("== storeAdd exception:", e);
			return new ReciveMsg<String>(0, "", "添加失败，待会再尝试", null);
		}
	}
	/**
	 * 函数功能说明 ：店铺列表页面跳转查看.
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping("/goedit")
	public String goStoreEdit(HttpServletRequest req, Model model,@RequestParam("storeNo") String storeNo,@RequestParam("merchant") String merchant) {
		H5StoreEntity storeEntity = storeService.getByStoreNo(storeNo);
		req.setAttribute("storeEntity", storeEntity);
		req.setAttribute("merchant", merchant);
		req.setAttribute("provMap", addressService.getProvList());
		//取得门店省份下的所有城市
		req.setAttribute("cityMap", addressService.getNextList(Integer.parseInt(storeEntity.getProv())));
		//取得门店省份下的所有区/县
		req.setAttribute("areaMap", addressService.getNextList(Integer.parseInt(storeEntity.getCity())));
		return "store/storeedit";
	}
	
	/**
	 * 修改门店
	 * @param req
	 * @param model
	 * @param dwz
	 * @param storeEntity
	 * @return
	 */
	@RequestMapping("/edit")
	public @ResponseBody ReciveMsg<String> storeEdit(HttpServletRequest req,H5StoreEntity storeEntity,Model model)
	{
		try
		{
			//修改门店
			 return storeService.updateInfo(storeEntity);
		} catch (Exception e)
		{
			log.error("== storeEdit exception:", e);
			return new ReciveMsg<String>(0, "", "修改失败，待会再尝试", null);
		}
	}
	
	/**
	 * 删除门店
	 * @param req
	 * @param model
	 * @param dwz
	 * @param storeEntity
	 * @return
	 */
	@RequestMapping("/delete")
	public @ResponseBody ReciveMsg<String> storeDelete(HttpServletRequest req,@RequestParam("storeNo") String storeNo,Model model)
	{
		try
		{
			//删除门店
			return storeService.deleteByStoreNo(storeNo);
		} catch (Exception e)
		{
			log.error("== storeDelete exception:", e);
			return new ReciveMsg<String>(0, "", "删除失败，待会再尝试", null);
		}
	}
	/**
	 * 函数功能说明 ：店员列表.
	 * @param req
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/clerklist")
	public String listClerk(HttpServletRequest req,
			@RequestParam("storeNo") String storeNo,
			@RequestParam("pageIndex") Integer pageInex,
			@RequestParam("type") Integer type,Model model,
			@RequestParam("merchant") String merchant)
	{
		try
		{
			List<MerchUser> list = new ArrayList<>();
			Integer total = 0;
			if (storeNo==null)
			{
				list = null;
				total = 0;
			} else
			{
				//分页取得店员列表
				Map<String, Object> map = merchUserService.listByStoreNo(storeNo,pageInex);
				list = (List<MerchUser>)map.get("clerkList");
				//取得店员总数
				total = (Integer)map.get("total");
			}
			req.setAttribute("clerkList", list);
			req.setAttribute("storeName", storeService.getByStoreNo(storeNo).getStoreName());
			req.setAttribute("storeNo", storeNo);
			req.setAttribute("pageIndex", pageInex);
			req.setAttribute("total", total);
			req.setAttribute("merchant", merchant);
			switch (type)
			{
			case 0:
				return "store/clerklist";  //返回店员列表页面
			case 1:
				return "store/clerkrelist"; //返回店员列表填充页面
			default:
				return "store/clerklist";
			}		
			
		} catch (Exception e)
		{
			log.error("== ListStore exception:", e);
			req.setAttribute("merchant", merchant);
			switch (type)
			{
			case 0:
				return "store/clerklist";
			case 1:
				return "store/clerkrelist";
			default:
				return "store/clerklist";
			}		
		}		
	}
	/**
	 * 函数功能说明 ：店员列表页面跳转添加店员.
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping("/goclerkadd")
	public String goClerkAdd(HttpServletRequest req, Model model,@RequestParam("storeNo") String storeNo ,@RequestParam("merchant") String merchant) {
		req.setAttribute("storeNo", storeNo);
		req.setAttribute("merchant", merchant);
		return "store/clerkadd";
	}
	
	/**
	 * 增加店员
	 * @param req
	 * @param model
	 * @param dwz
	 * @param storeEntity
	 * @return
	 */
	@RequestMapping("/clerkadd")
	public @ResponseBody ReciveMsg<String> clerkAdd(HttpServletRequest req,MerchUser merchUser,
			@RequestParam("loginPhone") String phone,
			@RequestParam("merchant") String merchant,
			Model model)
	{
		String loginPwd = StringUtils.isBlank(req.getParameter("loginPwd"))?null:req.getParameter("loginPwd");
		merchUser.setPhone(phone);
		merchUser.setLoginPwd(loginPwd);
		req.setAttribute("merchant", merchant);
		try
		{
			merchUser.setMerchantNo(merchant);
			//添加店员
			return storeService.addClerk(merchUser);
		} catch (Exception e)
		{
			log.error("== clerkAdd exception:", e);
			return new ReciveMsg<String>(0, "", "添加失败，待会再尝试", null);
		}
	}
	/**
	 * 函数功能说明 ：跳转到店员修改界面.
	 * @param req
	 * @param 
	 * @return
	 */
	@RequestMapping("/goclerkedit")
	public String goClerkEdit(HttpServletRequest req, Model model,@RequestParam("id") Integer id,@RequestParam("merchant") String merchant) {
		try
		{
			MerchUser merchUser = merchUserService.findMerchUserById(id);
			req.setAttribute("merchUser", merchUser);
			req.setAttribute("merchant", merchant);
			return "store/clerkedit";
		} catch (Exception e)
		{
			log.error("== goClerkEdit exception:", e);
			return null;
		}
		
	}
	/**
	 * 店员修改
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping("/clerkedit")
	public @ResponseBody ReciveMsg<String> clerkEdit(HttpServletRequest req,Model model,MerchUser merchUser,
			@RequestParam("loginPhone") String phone)
	{
		String loginPwd = StringUtils.isBlank(req.getParameter("loginPwd"))?null:req.getParameter("loginPwd");
		merchUser.setLoginPwd(loginPwd);
		merchUser.setPhone(phone);
		try
		{
			if(loginPwd != null){
				// 修改密码成功后清空账号登录失败记录
				this.sysCashierService.delLoginRecord(phone);
			}
			//修改店员
			return merchUserService.updateMsg(merchUser);
		} catch (Exception e)
		{
			log.error("== clerkEdit exception:", e);
			return new ReciveMsg<String>(0, "", "修改失败，待会再尝试", null);
		}
	}
	
	/**
	 * 删除店员
	 * @param req
	 * @param model
	 * @param dwz
	 * @param storeEntity
	 * @return
	 */
	@RequestMapping("/clerkdelete")
	public @ResponseBody ReciveMsg<String> clerkDelete(HttpServletRequest req, @RequestParam("id") Integer id,Model model)
	{
		try
		{
			//删除店员
			return merchUserService.deleteClerkById(id);
		} catch (Exception e)
		{
			log.error("== clerkDelete exception:", e);
			return new ReciveMsg<String>(0, "", "删除失败，待会再尝试", null);
		}
	}
	
	/**
	 * 用于验证数据库中商铺名称是否已经存在
	 * @param storeName
	 * @param 
	 * @return
	 */
	@RequestMapping("/checkstore")
	public @ResponseBody ReciveMsg<String> checkStore(HttpServletRequest req,@RequestParam("merchant") String merchant, Model model) {
		String 	storeName=StringUtils.isBlank(req.getParameter("storeName"))?null:req.getParameter("storeName");
        Integer flag = null;
        Integer merchId = storeService.getIdByMerchNo(merchant).getId();
        try
		{
        	if(storeName != null){
            	if (storeService.getStoreByStoreName(storeName,merchId)!=null)
    			{
            		flag = 1;
    			} else
    			{
    				flag = 0;
    			}
            }else{
            	flag = -1;
    		}        
            return new ReciveMsg<String>(flag, "", flag.toString(), null);
		} catch (Exception e)
		{
			log.error("== checkStore exception:", e);
			return new ReciveMsg<String>(0, "", "-1", null);
		}
        
    }
	
	/**
	 * 下拉查询市/区
	 * @param req
	 * @return
	 */
	@RequestMapping("/getregion")
	public @ResponseBody Map<Integer, String> getRegion(HttpServletRequest req)
	{
		String id = req.getParameter("id");
		return addressService.getNextList(Integer.parseInt(id));
	}

	/**
	 * 密码MD5加密
	 * @param loginPwd
	 * @return
	 */
	public String getPwd(String loginPwd,String pwdSalt) {	
		int hashIterations = Integer.valueOf("2");
		String newPassword = new SimpleHash("md5", loginPwd, ByteSource.Util.bytes(pwdSalt), hashIterations).toHex();	
		return newPassword;
	}
}
