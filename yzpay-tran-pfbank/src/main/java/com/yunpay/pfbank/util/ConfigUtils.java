package com.yunpay.pfbank.util;


import java.util.ResourceBundle;

public class ConfigUtils {
	public static String PF_TRANS_URL= "";
	public static String PF_MERCH_URL="";
	public static String AGENT_MERCHANT_NO= "";
	public static String AGENT_PUBLIC_KEY_PATH= "";
	public static String AGENT_PRIVATE_KEY_PATH= "";
	public static String AGENT_PRIVATE_KEY_PFX_PATH= "";
	public static String AGENT_PRIVATE_KEY_PWD= "";
    

    static {
    	//微信相关
		ResourceBundle rb = ResourceBundle.getBundle("payconfig/payconfig");
    	//ResourceBundle rb = ResourceBundle.getBundle("config");
		ConfigUtils.PF_TRANS_URL = rb.getString("PF_TRANS_URL").trim();
		ConfigUtils.PF_MERCH_URL = rb.getString("PF_MERCH_URL").trim();
		ConfigUtils.AGENT_MERCHANT_NO = rb.getString("AGENT_MERCHANT_NO").trim();
		ConfigUtils.AGENT_PUBLIC_KEY_PATH = rb.getString("AGENT_PUBLIC_KEY_PATH").trim();
		ConfigUtils.AGENT_PRIVATE_KEY_PATH = rb.getString("AGENT_PRIVATE_KEY_PATH").trim();
		ConfigUtils.AGENT_PRIVATE_KEY_PFX_PATH = rb.getString("AGENT_PRIVATE_KEY_PFX_PATH").trim();
		ConfigUtils.AGENT_PRIVATE_KEY_PWD = rb.getString("AGENT_PRIVATE_KEY_PWD").trim();
    }

  

}
