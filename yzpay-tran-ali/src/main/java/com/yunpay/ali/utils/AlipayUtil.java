package com.yunpay.ali.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.yunpay.common.utils.Log;

/**
 * 文件名称:     AlipayUtil.java
 * 内容摘要:    支付宝接口工具类，提供数组排序、签名等方法
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2018年3月1日下午4:47:11 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2018年3月1日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2018
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class AlipayUtil {
	/**
	 * @Description: 除去数组中的空值和签名参数
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年7月4日上午11:15:59 
	 * @param sArray
	 * @return
	 */
    public static Map<String, String> paraFilter(Map<String, String> sArray) {
        Map<String, String> result = new HashMap<String, String>();
        if (sArray == null || sArray.size() <= 0) {
            return null;
        }
        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (StringUtils.isEmpty(value) || key.equalsIgnoreCase("sign")
                || key.equalsIgnoreCase("sign_type")) {
                continue;
            }
            result.put(key, value);
        }
        return result;
    }

    /**
     * @Description: 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * @author:   Zeng Dongcheng
     * @Date:     2017年7月4日上午11:16:34 
     * @param params
     * @return
     */
    public static String createLinkString(Map<String, String> params) {
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        String prestr = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }
        return prestr;
    }
    
    /**
     * 根据反馈回来的信息，生成签名结果
     * @param Params 通知返回来的参数数组
     * @param sign 比对的签名结果
     * @return 生成的签名结果
     */
	public static boolean getRSASignVerify(Map<String, String> Params, String sign,String publicKey) {
    	//过滤空值、sign与sign_type参数
    	Map<String, String> sParaNew = AlipayUtil.paraFilter(Params);
        //获取待签名字符串
        String preSignStr = AlipayUtil.createLinkString(sParaNew);
        Log.debug("支付宝异步通知待签名数据："+preSignStr);
        //获得签名验证结果
        boolean isSign = false;
		try {
			isSign = AlipaySignature.rsaCheckContent(preSignStr, sign, publicKey,"UTF-8");
		} catch (AlipayApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return isSign;
    }
	
	public static void main(String[] args){
		String preSignStr = "app_id=2017040506557166&auth_app_id=2017040506557166&buyer_id=2088302456750107&buyer_logon_id=704***@qq.com&charset=UTF-8&gmt_create=2017-09-11 10:02:29&notify_id=d0a81a5c13bb288723965f9cda56d88gru&notify_time=2017-09-11 10:08:17&notify_type=trade_status_sync&out_trade_no=674329315050520576&seller_email=liulianghao@siecom.cn&seller_id=2088621710164435&subject=测试3&total_amount=0.01&trade_no=2017091121001004100255535668&trade_status=WAIT_BUYER_PAY&version=1.0";
		String sign= "JsY4xm767Sc14AJwI8u1xH/VPkyYmdTGtfs6udxZWAcz7wfZD/GwMmf3OUF/eb6RcquqIQ1Mv4tPikYkS30rp0etl8g9IJfM8G/na4KQ6kznKaiUynZ62q66AHVfMx7UAO6bv9gIba/2Gr4NA6Qn8IgO4qn6EhiVV+o2VwtY4f0=";
		String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
		
		try {
			boolean isSign = AlipaySignature.rsaCheckContent(preSignStr, sign, publicKey,"UTF-8");
			System.out.println(isSign);
		} catch (AlipayApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
