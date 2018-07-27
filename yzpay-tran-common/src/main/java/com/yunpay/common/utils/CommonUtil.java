package com.yunpay.common.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import com.yunpay.common.utils.EnumUtil.PayStatus;
import com.yunpay.common.utils.EnumUtil.RefundStatus;



/**
 * 公共辅助类
 * 文件名称:     CommonUtil.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年6月16日下午3:17:53 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年6月16日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class CommonUtil {
	
	/**
	 * @Description: 将实例类转为Map 
	 * @author:      Zeng Dongcheng
	 * @Date:        2017年6月20日上午10:30:51 
	 * @param obj    实例化类
	 * @return       Map集合    
	 */
    public static Map<String,String> toMap(Object obj){
    	Class<? extends Object> curClass = obj.getClass();
        Map<String,String> map = new HashMap<String, String>();
        Field[] fields = curClass.getDeclaredFields();
        for (Field field : fields) {
            try {
            	field.setAccessible(true);
                Object fieldValue = field.get(obj);
                if (field.getType() == String.class) {
            		map.put(field.getName(), (String) fieldValue);
				} else {
					map.put(field.getName(), String.valueOf(fieldValue));
				}
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        Class<?> superClass = curClass.getSuperclass();// 父类
        Field[] supperfields = superClass.getDeclaredFields();
        for (Field field : supperfields) {
            try {
            	field.setAccessible(true);
            	Object fieldValue = field.get(obj);
                if (field.getType() == String.class) {
            		map.put(field.getName(), (String) fieldValue);
				} else {
					map.put(field.getName(), String.valueOf(fieldValue));
				}
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }
    
    /**
     * @Description: 将Map集合封装到实体类中
     * @author:      Zeng Dongcheng
     * @Date:        2017年6月20日上午10:30:27 
     * @param type   Class
     * @param map    Map集合
     * @return       实体类
     * @throws IntrospectionException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws InvocationTargetException
     */
    @SuppressWarnings("rawtypes")
    public static Object convertMap(Class type, Map map)  
            throws IntrospectionException, IllegalAccessException,  
            InstantiationException, InvocationTargetException {  
        BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性  
        Object obj = type.newInstance(); // 创建 JavaBean 对象  
        // 给 JavaBean 对象的属性赋值  
        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();  
        for (int i = 0; i< propertyDescriptors.length; i++) {  
            PropertyDescriptor descriptor = propertyDescriptors[i];  
            String propertyName = descriptor.getName();  
  
            if (map.containsKey(propertyName)) {  
                // 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。  
                Object value = map.get(propertyName);  
                Object[] args = new Object[1];  
                args[0] = value;  
                descriptor.getWriteMethod().invoke(obj, args);  
            }  
        }  
        return obj;  
    }  
	
    
	/**
	 * @Description:  生成随机字串
	 * @author:       Zeng Dongcheng
	 * @Date:         2017年6月20日上午10:32:51 
	 * @param length  字串长度
	 * @return        随机字串
	 */
	public static String getRandomStringByLength(int length) {
		 String base = "abcdefghijklmnopqrstuvwxyz0123456789";
         Random random = new Random();
         StringBuffer sb = new StringBuffer();
         for (int i = 0; i < length; i++) {
             int number = random.nextInt(base.length());
             sb.append(base.charAt(number));
         }
         return sb.toString();
    }
	 
	/**
	 * @Description: Md5签名
	 * @author:      Zeng Dongcheng
	 * @Date:        2017年6月20日上午10:32:51
	 * @param map    字符map
	 * @param key    签名key
	 * @return       返回签名内容
	 */
	public static String getSign(Map<String,String> map,String key){
	    ArrayList<String> list = new ArrayList<String>();
	    for(Map.Entry<String,String> entry:map.entrySet()){
	        if(StringUtils.isNotEmpty(entry.getValue())){
	            list.add(entry.getKey() + "=" + entry.getValue() + "&");
	        }
	    }
	    int size = list.size();
	    String [] arrayToSort = list.toArray(new String[size]);
	    Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
	    StringBuilder sb = new StringBuilder();
	    for(int i = 0; i < size; i ++) {
	        sb.append(arrayToSort[i]);
	    }
	    String result = sb.toString();
	    result += "key=" + key;
	    Log.info("Sign Before MD5:" + result);
	    result = MD5.MD5Encode(result).toUpperCase();
	    Log.info("Sign Result:" + result);
	    return result;
	}
	
	/**
	 * 获取32位UUID
	 * @Description: 
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年6月22日下午2:08:57 
	 * @return
	 */
	public static String get32UUID() {
		String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
		return uuid;
	}
	
	/**
	 * @Description: 校验reqParamMap中是否含有param中的参数
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年6月22日下午2:12:58 
	 * @param reqParamMap 待校验对象
	 * @param param		     参考指标
	 * @return
	 */
	public static String paramValidate(Map<String, String> reqParamMap,String[] param) {
		StringBuffer buffer = new StringBuffer();
		for(int i=0;i<param.length;i++){
			if(StringUtils.isEmpty(reqParamMap.get(param[i]))){
				if(i==param.length-1){
					buffer.append(param[i]);
				}else{
					buffer.append(param[i]+"|");
				}
			}
		}
		return buffer.toString();
	}
	
	/**
	 * @Description: 校验reqParamMap中是否含有param中的参数
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月7日上午9:09:27 
	 * @param reqParamMap
	 * @param param
	 * @return
	 */
	public static String paramValidate(Map<String, String> reqParamMap,List<String> param) {
		StringBuffer buffer = new StringBuffer();
		int i=0;
		for(String key : param){
			++i;
			if(StringUtils.isBlank(reqParamMap.get(key))){
				if(i==param.size()){
					buffer.append(key);
				}else{
					buffer.append(key+"|");
				}
			}
		}
		return buffer.toString();
	}
	
	/**
	 * 将微信支付状态转为平台支付状态
	 * @param trade_state
	 * @return
	 */
	public static PayStatus convertWxStatus(String trade_state){
		
		if("SUCCESS".equals(trade_state)){
			//支付成功
			return PayStatus.PAYED;
		}else if("REFUND".equals(trade_state)){
			//转入退款 
			return PayStatus.REFUNDING;
		}else if("NOTPAY".equals(trade_state)){
			//未支付
			return PayStatus.UNPAY;
		}else if("CLOSED".equals(trade_state)){
			//已关闭
			return PayStatus.CLOSED;
		}else if("REVOKED".equals(trade_state)){
			//已撤销
			return PayStatus.CANCEL;
		}else if("USERPAYING".equals(trade_state)){
			//用户支付中 
			return PayStatus.PAYING;
		}else if("PAYERROR".equals(trade_state)){
			//支付失败
			return PayStatus.FAILPAY;
		}else{
			return PayStatus.FAILPAY;
		}
	}
	
	/**
	 * @Description: 微信退款状态转换
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年7月11日上午11:36:24 
	 * @param trade_state
	 * @return
	 */
	public static RefundStatus convertWxRefundStatus(String trade_state){
		if("SUCCESS".equals(trade_state)){
			//支付成功
			return RefundStatus.REFUNDED;
		}else if("PROCESSING".equals(trade_state)){
			//转入退款 
			return RefundStatus.REFUNDING;
		}else{
			return RefundStatus.FAILREFUND;
		}
	}
	
	/**
	 * 将支付宝支付状态转为平台支付状态
	 * @param trade_status
	 * @return
	 */
	public static PayStatus convertAliStatus(String trade_state){
		if("TRADE_SUCCESS".equals(trade_state)){
			//交易支付成功
			return PayStatus.PAYED;
		}else if("WAIT_BUYER_PAY".equals(trade_state)){
			//用户支付中
			return PayStatus.PAYING;
		}else if("TRADE_CLOSED".equals(trade_state)){
			//已关闭
			return PayStatus.CLOSED;
		}else if("TRADE_FINISHED".equals(trade_state)){
			//交易结束，不可退款
			return PayStatus.PAYED;
		}else{
			return PayStatus.FAILPAY;
		} 
	}
	
	/**
	 * @Description: 将银联返回的状态码转为平台状态
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年7月6日下午3:27:29 
	 * @param origRespCode
	 * @return
	 */
	public static PayStatus convertUnionStatus(String origRespCode){
		if("00".equals(origRespCode) || "A6".equals(origRespCode)){
			return PayStatus.PAYED;
		}else if("03".equals(origRespCode)|| "04".equals(origRespCode)||
				 "05".equals(origRespCode)){
			return PayStatus.PAYING; 
		}else{
			return PayStatus.FAILPAY;
		}
	}
	
	/**
	 * @Description: 获取时间戳
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年6月27日下午1:57:50 
	 * @return
	 */
	public static String getTimeStamp() {
		return String.valueOf(System.currentTimeMillis() / 1000);
	}
	
	/**
	 * @Description: 读取流信息
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年6月27日下午4:48:01 
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static String inputStreamToString(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int i;
        while ((i = is.read()) != -1) {
            baos.write(i);
        }
        return baos.toString();
    }
	
	/**
	 * @Description: 微信卡券颜色转换
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月8日上午10:30:59 
	 * @param cardColor  微信卡券颜色值
	 * @return 卡券实际颜色值
	 */
	public static String convertWxCardColor(String cardColor){
		String backGround="";
		switch(cardColor){
			case "Color010":
				backGround="#63b359";
				break;
			case "Color020":
				backGround="#2c9f67";
				break;
			case "Color030":
				backGround="#509fc9";
				break;
			case "Color040":
				backGround="#5885cf";
				break;
			case "Color050":
				backGround="#9062c0";
				break;
			case "Color060":
				backGround="#d09a45";
				break;
			case "Color070":
				backGround="#e4b138";
				break;
			case "Color080":
				backGround="#ee903c";
				break;
			case "Color081":
				backGround="#f08500";
				break;
			case "Color082":
				backGround="#a9d92d";
				break;
			case "Color090":
				backGround="#dd6549";
				break;
			case "Color100":
				backGround="#cc463d";
				break;
			case "Color101":
				backGround="#cf3e36";
				break;
			case "Color102":
				backGround="#5E6671";
				break;
			default:
				backGround="#63b359";
		}
		return backGround;
	}
	
	/**
	 * @Description: 将颜色转换为支付宝/微信对应的颜色值
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月19日下午3:02:56 
	 * @param type
	 * @param sourceColor
	 * @return
	 */
	public static String getFinalColor(int type, String sourceColor) {
		String weixin_color;
		String alipass_color="";
		switch (sourceColor) {
		case "#55BD47":
			weixin_color = "Color010";
			alipass_color="RGB(85,189,71)";
			break;
		case "#10AD61":
			weixin_color = "Color020";
			alipass_color="RGB(16,173,97)";
			break;
		case "#35A4DE":
			weixin_color = "Color030";
			alipass_color="RGB(53,164,222)";
			break;
		case "#3D78DA":
			weixin_color = "Color040";
			alipass_color="RGB(61,120,218)";
			break;
		case "#9058CB":
			weixin_color = "Color050";
			alipass_color="RGB(144,88,203)";
			break;
		case "#DE9C33":
			weixin_color = "Color060";
			alipass_color="RGB(222,156,51)";
			break;
		case "#EBAC16":
			weixin_color = "Color070";
			alipass_color="RGB(235,172,22)";
			break;
		case "#F9861F":
			weixin_color = "Color080";
			alipass_color="RGB(249,134,31)";
			break;
		case "#F08500":
			weixin_color = "Color081";
			alipass_color="RGB(231,87,53)";
			break;
		case "#E75735":
			weixin_color = "Color090";
			alipass_color="RGB(231,87,53)";
			break;
		case "#D54036":
			weixin_color = "Color100";
			alipass_color="RGB(213,64,54)";
			break;
		default:
			weixin_color = "Color010";
			alipass_color="RGB(85,189,71)";
			break;
		}
		if (type ==0) {
			return weixin_color;
		}else {
			return alipass_color;
		}
	}
	
	
	/**
     * 生成随机数字，用于生成随机Serialnumber
     * @param codeLength
     * @return
     */
    public static String generateNumber(int codeLength) {
        // 10个数字
        final int maxNum = 8;
        int i; // 生成的随机数
        int count = 0; // 生成的密码的长度
        char[] str = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

        StringBuffer pwd = new StringBuffer("");
        Random r = new Random();
        while (count < codeLength) {
            // 生成随机数，取绝对值，防止生成负数
            i = Math.abs(r.nextInt(maxNum)); // 生成的数最大为36-1

            if (i >= 0 && i < str.length) {
                pwd.append(str[i]);
                count++;
            }
        }
        return pwd.toString();
    }

	 
}
