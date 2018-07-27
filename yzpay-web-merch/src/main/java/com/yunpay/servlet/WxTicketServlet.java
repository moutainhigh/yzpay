package com.yunpay.servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import org.apache.commons.lang.StringUtils;
import com.yunpay.common.core.utils.StringUtil;
import com.yunpay.permission.utils.WxTicketThread;

/**
 * 
 * 文件名称:
 * 内容摘要: 定期获取并存储微信 access_token,JsapiTicket
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
public class WxTicketServlet  extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	 public void init() throws ServletException {
		WxTicketThread.appid = this.getInitParameter("appid");
		WxTicketThread.appsecret = this.getInitParameter("appsecret");
		System.out.println("weixin api appid:"+WxTicketThread.appid);  
		System.out.println("weixin api appsecret:"+WxTicketThread.appsecret);  
		 
		 if(StringUtils.isEmpty(WxTicketThread.appid) || StringUtil.isEmpty(WxTicketThread.appsecret)){
			 throw new ServletException("the servlet initParameter is null");
		 }else{
			 // 启动定时获取access_token的线程 
			 new Thread(new WxTicketThread()).start();
		 }
	 }
}
