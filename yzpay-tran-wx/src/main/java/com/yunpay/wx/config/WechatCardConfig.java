package com.yunpay.wx.config;

import java.util.ResourceBundle;

/**
 * 文件名称:     WechatCardConfig.java
 * 内容摘要:    微信卡券全局配置
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年7月31日下午4:04:56 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年7月31日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class WechatCardConfig {
	//获取access_token
	public static String ACCESS_TOKEN_API = "";
	//图片上传
	public static String UPLOAD_IMG_API = "";
	//创建卡券
	public static String CARD_CREATE_API = "";
	//创建卡券投放码
	public static String CREATE_QRCODE_API= "";
	//卡券核销码查询
	public static String QR_CARDCODE_API= "";
	//卡券核销
	public static String CARD_CONSUME_API= "";
	//卡券删除
	public static String CARD_DELETE_API = "";
	//卡券修改
	public static String CARD_UPD_API = "";
	//卡券推送
	public static String CARD_PUSH_API = "";
	//快速买单设置
	public static String CARD_PAYSET_API = "";
	//微信会员卡一键激活参数设置
	public static String CARD_ACTIVATESET_API = "";
	//获取会员信息
	public static String MEMBER_USERINFO_API="";
	//修改会员信息接口
	public static String MEMBER_UPDUSER_API="";
	
	
	static {
		ResourceBundle rb = ResourceBundle.getBundle("payurl/wechatcard");	
		WechatCardConfig.ACCESS_TOKEN_API = rb.getString("ACCESS_TOKEN_API").trim();
		WechatCardConfig.UPLOAD_IMG_API = rb.getString("UPLOAD_IMG_API").trim();
		WechatCardConfig.CARD_CREATE_API = rb.getString("CARD_CREATE_API").trim();
		WechatCardConfig.CREATE_QRCODE_API = rb.getString("CREATE_QRCODE_API").trim();
		WechatCardConfig.QR_CARDCODE_API = rb.getString("QR_CARDCODE_API").trim();
		WechatCardConfig.CARD_CONSUME_API = rb.getString("CARD_CONSUME_API").trim();
		WechatCardConfig.CARD_DELETE_API = rb.getString("CARD_DELETE_API").trim();
		WechatCardConfig.CARD_UPD_API = rb.getString("CARD_UPD_API").trim();
		WechatCardConfig.CARD_PUSH_API = rb.getString("CARD_PUSH_API").trim();
		WechatCardConfig.CARD_PAYSET_API = rb.getString("CARD_PAYSET_API").trim();
		WechatCardConfig.CARD_ACTIVATESET_API = rb.getString("CARD_ACTIVATESET_API").trim();
		WechatCardConfig.MEMBER_USERINFO_API = rb.getString("MEMBER_USERINFO_API").trim();
		WechatCardConfig.MEMBER_UPDUSER_API = rb.getString("MEMBER_UPDUSER_API").trim();
	}
}
