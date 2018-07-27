package com.yunpay.common.utils;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.commons.lang.StringUtils;

public class HttpUtil {
	public static String doGet(String url, String queryString, String charset, boolean pretty) {
		StringBuffer response = new StringBuffer();
		HttpClient client = new HttpClient();
		HttpMethod method = new GetMethod(url);
		try {
			if (StringUtils.isNotBlank(queryString))
				// 对get请求参数做了http请求默认编码，好像没有任何问题，汉字编码后，就成为%式样的字符串
				method.setQueryString(URIUtil.encodeQuery(queryString));
			client.executeMethod(method);
			if (method.getStatusCode() == HttpStatus.SC_OK) {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(method.getResponseBodyAsStream(), charset));
				String line;
				while ((line = reader.readLine()) != null) {
					if (pretty)
						response.append(line).append(System.getProperty("line.separator"));
					else
						response.append(line);
				}
				reader.close();
			}
		} catch (URIException e) {
			Log.error("执行HTTP Get请求时，编码查询字符串“" + queryString + "”发生异常！",e);
		} catch (IOException e) {
			Log.error("执行HTTP Get请求" + url + "时，发生异常！",e);
		} finally {
			method.releaseConnection();
		}
		return response.toString();
	}

	public static String doPost(String url, Map<String, String> params, String charset, boolean pretty) {
		StringBuffer response = new StringBuffer();
		HttpClient client = new HttpClient();
		HttpMethod method = new PostMethod(url);
		// 设置Http Post数据
		if (params != null) {
			HttpMethodParams p = new HttpMethodParams();
			for (Map.Entry<String, String> entry : params.entrySet()) {
				p.setParameter(entry.getKey(), entry.getValue());
			}
			method.setParams(p);
		}
		try {
			client.executeMethod(method);
			if (method.getStatusCode() == HttpStatus.SC_OK) {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(method.getResponseBodyAsStream(), charset));
				String line;
				while ((line = reader.readLine()) != null) {
					if (pretty)
						response.append(line).append(System.getProperty("line.separator"));
					else
						response.append(line);
				}
				reader.close();
			}
		} catch (IOException e) {
			Log.error("执行HTTP Post请求" + url + "时，发生异常！", e);
		} finally {
			method.releaseConnection();
		}
		return response.toString();
	}
	
	
	
    
    /**  
     * POST请求，字符串形式数据  
     * @param url 请求地址  
     * @param param 请求数据  
     * @param charset 编码方式  
     */  
    public static String sendPostUrl(String url, String param, String charset) {  
  
        PrintWriter out = null;  
        BufferedReader in = null;  
        String result = "";  
        try {  
            URL realUrl = new URL(url);  
            // 打开和URL之间的连接  
            URLConnection conn = realUrl.openConnection();  
            // 设置通用的请求属性  
            conn.setRequestProperty("accept", "*/*");  
            conn.setRequestProperty("connection", "Keep-Alive");  
            conn.setRequestProperty("user-agent",  
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");  
            // 发送POST请求必须设置如下两行  
            conn.setDoOutput(true);  
            conn.setDoInput(true);  
            // 获取URLConnection对象对应的输出流  
            out = new PrintWriter(conn.getOutputStream());  
            // 发送请求参数  
            out.print(param);  
            // flush输出流的缓冲  
            out.flush();  
            // 定义BufferedReader输入流来读取URL的响应  
            in = new BufferedReader(new InputStreamReader(  
                    conn.getInputStream(), charset));  
            String line;  
            while ((line = in.readLine()) != null) {  
                result += line;  
            }  
        } catch (Exception e) {  
            System.out.println("发送 POST 请求出现异常！" + e);  
            e.printStackTrace();  
        }  
        // 使用finally块来关闭输出流、输入流  
        finally {  
            try {  
                if (out != null) {  
                    out.close();  
                }  
                if (in != null) {  
                    in.close();  
                }  
            } catch (IOException ex) {  
                ex.printStackTrace();  
            }  
        }  
        return result;  
    } 
    
	public static void main(String[] args) {
		
//		Map<String, String> params = new HashedMap();
//		String key = "a1h1iqlwkkx2yenfh2wzg3o15lqejya3uvuo";
//		params.put("merchant_num", "201509250001");
//		params.put("terminal_unique_no", "1");
//		params.put("cashier_num", "1");
//		params.put("client_type", "PC");
//		params.put("pay_channel", "wechat");
//		// params.put("trace_num", "15099499712512");
//		// params.put("refund_amount", "0.01");
//		params.put("total_fee", "0.01");
//		params.put("sign_type", "MD5");
//		params.put("dynamic_id", "130529942098982889");
//		params.put("user_order_no", "201509300005");
//		params.put("body", "微信支付商品测试");
//		
//		
//
//		String queryString = MD5Utils.sign(params, "MD5", key, "UTF-8");
//		String y = doGet("http://tool.oschina.net/qr", queryString, "UTF-8", true);
//		System.out.println(y);
		String phpURL = "http://t.yunpuvip.com/ajax/sysCardTemplate";
//		String phpURL = "http://y.yunpuvip.com/index.php?&g=Api&m=Users&a=get";
//		String params = "username=09600041039&appid=wxd1732e33689bd7d4";
		String params = "merchant=09600041039&appId=wxd1732e33689bd7d4&logo=http://t.yunpuvip.com/uploads/e/espedw1445321447/2/2/d/7/thumb_56289872932d3.png&putchannel=1";
		String result =  HttpUtil.sendPostUrl(phpURL, params, "UTF-8");
		System.out.println("result = " + result);
//		
//		String result3 =  HttpTool.sendGet(phpURL + "&" +params, "UTF-8");
//		System.out.println("result3 = " + result3);
		
//		 Map<String, String> param = new HashMap<String, String>();
//		 param.put("merchant", "09600041039");
//	//	 param.put("appId", "wxd1732e33689bd7d4");
//	//	 param.put("logo", "http://t.yunpuvip.com/uploads/e/espedw1445321447/2/2/d/7/thumb_56289872932d3.png");
//		String result1 =  HttpTool.sendPost(phpURL, param, "UTF-8");
//		System.out.println("result1 = " + result1);
//		
//		net.sf.json.JSONObject result2 =  HttpTool.httpRequest(phpURL,  "UTF-8" ,params);
//		System.out.println("result2 = " + result2.toString());
		
//		saveToFile("http://t.yunpuvip.com/uploads/e/espedw1445321447/2/2/d/7/thumb_56289872932d3.png");
		
	}
	
	/** 
     * 使用Get方式获取数据 
     *  
     * @param url 
     *            URL包括参数，http://HOST/XX?XX=XX&XXX=XXX 
     * @param charset 
     * @return 
     */  
    public static String sendGet(String url, String charset) {  
        String result = "";  
        BufferedReader in = null;  
        try {  
            URL realUrl = new URL(url);  
            // 打开和URL之间的连接  
            URLConnection connection = realUrl.openConnection();  
            // 设置通用的请求属性  
            connection.setRequestProperty("accept", "*/*");  
            connection.setRequestProperty("connection", "Keep-Alive");  
            connection.setRequestProperty("user-agent",  
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");  
            // 建立实际的连接  
            connection.connect();  
            // 定义 BufferedReader输入流来读取URL的响应  
            in = new BufferedReader(new InputStreamReader(  
                    connection.getInputStream(), charset));  
            String line;  
            while ((line = in.readLine()) != null) {  
                result += line;  
            }  
        } catch (Exception e) {  
            System.out.println("发送GET请求出现异常！" + e);  
            e.printStackTrace();  
        }  
        // 使用finally块来关闭输入流  
        finally {  
            try {  
                if (in != null) {  
                    in.close();  
                }  
            } catch (Exception e2) {  
                e2.printStackTrace();  
            }  
        }  
        return result;  
    }  
    
}
