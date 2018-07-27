package com.yunpay.filter;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import com.yunpay.permission.utils.ModifyParametersWrapper;
/**
 * 
 * 文件名称:
 * 内容摘要:  该过滤器用于修改HttpServletRequest的请求头数据
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
public class ModifyParametersFilter implements Filter{

	@Override
	public void destroy() {}

	@Override
	public void doFilter(ServletRequest request,ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse rep = (HttpServletResponse)response;
		ModifyParametersWrapper mParametersWrapper = new ModifyParametersWrapper(req);
		// 从浏览器的请求中获取请求头数据
		String cookie = req.getHeader("Cookie");
		String userAgent = req.getHeader("User-Agent");
		System.out.println("ModifyParametersFilter.cookie*******"+cookie);
		if(!StringUtils.isEmpty(cookie) && userAgent.indexOf("SiecomWebview") > -1){
			System.out.println("req中设置的请求头数据****"+cookie);
			mParametersWrapper.putHeader("Cookie", cookie);
		}
		filterChain.doFilter(req, rep);
	}

	@Override
	public void init(FilterConfig paramFilterConfig) throws ServletException {}
	
}
