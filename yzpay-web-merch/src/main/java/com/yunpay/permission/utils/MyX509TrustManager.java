package com.yunpay.permission.utils;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
/**
 * 
 * 文件名称:
 * 内容摘要: 信任管理器
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
public class MyX509TrustManager implements javax.net.ssl.X509TrustManager {

	@Override
	public void checkClientTrusted(
			X509Certificate[] paramArrayOfX509Certificate, String paramString)
			throws CertificateException {
		
		
	}

	@Override
	public void checkServerTrusted(
			X509Certificate[] paramArrayOfX509Certificate, String paramString)
			throws CertificateException {
		
		
	}

	@Override
	public X509Certificate[] getAcceptedIssuers() {
		
		return null;
	}
	
}
