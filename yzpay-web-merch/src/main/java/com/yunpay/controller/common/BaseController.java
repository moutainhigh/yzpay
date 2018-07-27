package com.yunpay.controller.common;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.yunpay.common.core.dwz.DWZ;
import com.yunpay.common.core.dwz.DwzAjax;
import com.yunpay.h5merch.permission.entity.MerchUser;


/**
 * controller基类
 * 深圳市前海汇商惠电子商务有限公司
 * 
 */ 
@Controller
public abstract class BaseController{
	// 业务功能的jsp路径
	protected static final String VIEW_PATH = "/WEB-INF/views/";	
	/**
	 * request 请求转发
	* @param 
	* @return void
	* @throws
	 */
	protected final void forward(HttpServletRequest request,HttpServletResponse response,String path){
		try {
			request.getRequestDispatcher(path).forward(request, response);
			return;
		} catch (ServletException e) {
			e.printStackTrace();
			return;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}
	/**
	 * 获取当前在线用户的session
	* 
	* @param 
	* @return MerchUser
	* @throws
	 */
	protected final MerchUser getUserSession (HttpServletRequest req){
		MerchUser sessionUser = (MerchUser)req.getSession().getAttribute("merchUser");
		return sessionUser;
	}
	
	protected void setCookie(HttpServletRequest request,HttpServletResponse response){
		HttpSession session = request.getSession();
		// 设置token有效期为 12小时
		session.setMaxInactiveInterval(86400/2);
		// 返回token信息到app
		String token = session.getId();
		StringBuffer str = new StringBuffer();
		str.append("JSESSIONID="+token +";");
		str.append("Secure;");
		str.append("HttpOnly;");
		Calendar cal = Calendar.getInstance();  
		// 设置cookie有效期为12小时
        cal.add(Calendar.HOUR, 12);  
        Date date = cal.getTime();  
        Locale locale = Locale.CHINA;  
        SimpleDateFormat sdf =   
        new SimpleDateFormat("dd-MM-yyyy HH:mm:ss",locale);  
		str.append("Expires="+ sdf.format(date)+";");
		str.append("Path="+ request.getContextPath()+"/");
		response.addHeader("Set-Cookie", str.toString());
		
	}
	
	/**
	 * 获取servlet 上下文路径
	* 
	* @param 
	* @return String
	* @throws
	 */
	protected final String getContextPath(HttpServletRequest req){
		return req.getSession().getServletContext().getContextPath();
	}
	
	/**
	 * 输出html消息
	* 
	* @param 
	* @return void
	* @throws
	 */
	public final void responseMessage(HttpServletResponse rep,String message){
		try{
			 rep.setCharacterEncoding("utf-8");  
			 rep.setHeader("content-type", "text/html;charset=utf-8");
			 PrintWriter writer = rep.getWriter();
			 StringBuffer htmlData = new StringBuffer();
			 htmlData.append("<!DOCTYPE HTML>");
			 htmlData.append("<html>");
			 htmlData.append("<head>");
			 htmlData.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no\">");
			 htmlData.append("<meta name=\"apple-mobile-web-app-capable\" content=\"yes\">");
			 htmlData.append("</head>");
			 htmlData.append("<body>");  
			 htmlData.append("<small style=\"font-size:12px;font-weight:bold;\">"+message+"</small>"); 
			 htmlData.append("</body>");
			 htmlData.append("</html>");
			 writer.write(htmlData.toString());
			 writer.flush();
			 writer.close();
			 return;
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 以json格式输出错误信息
	* 
	* @param 
	* @return void
	* @throws
	 */
	protected final void responseErr(HttpServletResponse rep,String message){
		try{
			 rep.setCharacterEncoding("utf-8");  
			 rep.setContentType("application/json;charset=utf-8");
			 PrintWriter writer = rep.getWriter(); 
			 String json = "{'error':'"+message+"'}";
			 writer.write(json);
			 writer.flush();
			 writer.close();
			 return;
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 以json格式输出成功信息
	* 
	* @param 
	* @return void
	* @throws
	 */
	protected final void responseSucc(HttpServletResponse rep,String message){
		try{
			 rep.setCharacterEncoding("utf-8");  
			 rep.setContentType("application/json;charset=utf-8");
			 PrintWriter writer = rep.getWriter(); 
			 String json = "{'success':'"+message+"'}";
			 writer.write(json);
			 writer.flush();
			 writer.close();
			 return;
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 响应DWZ的ajax失败请求,跳转到ajaxDone视图.
	 * 
	 * @param message
	 *            提示消息.
	 * @param model
	 *            model.
	 * @return ajaxDone .
	 */
	protected String operateError(String message, Model model) {
		DwzAjax dwz = new DwzAjax();
		dwz.setStatusCode(DWZ.ERROR);
		dwz.setMessage(message);
		model.addAttribute("dwz", dwz);
		return "notice/ajaxDone";
	}

	/**
	 * 响应DWZ的ajax失败成功,跳转到ajaxDone视图.
	 * 
	 * @param model
	 *            model.
	 * @param dwz
	 *            页面传过来的dwz参数
	 * @return ajaxDone .
	 */
	protected String operateSuccess(Model model, DwzAjax dwz) {
		dwz.setStatusCode(DWZ.SUCCESS);
		dwz.setMessage("操作成功");
		model.addAttribute("dwz", dwz);
		return "notice/ajaxDone";
	}
	
	/**
	 * 保存参数到Request作用域中
	 * @param req
	 * @param param
	 */
	protected final void setRequestAttr(HttpServletRequest req,Map<String,Object> paramMap){
		for(Map.Entry<String, Object> entry : paramMap.entrySet()){
			req.setAttribute(entry.getKey(),entry.getValue());
		}
	}


}
