package com.yunpay.serv.rep;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.yunpay.ali.rep.pay.AliScanRep;
import com.yunpay.common.utils.EnumUtil.TransType;
import com.yunpay.pfbank.rep.PfScanRep;
import com.yunpay.pfbank.util.Base64;
import com.yunpay.serv.model.PayTranLs;
import com.yunpay.union.rep.UnionScanRep;
import com.yunpay.wx.rep.pay.WechatScanRep;

/**
 * 扫码支付返回结果类
 * 文件名称:     ScanPayRepDto.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年7月11日下午1:48:02 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年7月11日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class ScanPayRepDto {
	//交易类型
	private String trade_type = "";
	//微信生成的预支付回话标识，用于后续接口调用中使用，该值有效期为2小时
	private String prepay_id = "";
	//支付码连接
	private String code_url = "";
	//平台流水号(平台产生)
	private String trace_num = "";
	//支付订单号(SDK提交)
	private String user_order_no = "";
	// 二维码图片地址
	private String pic_url; 
	// 二维码小图地址
	private String small_pic_url; 
	
	public ScanPayRepDto(){}
	
	/**
	 * 构造微信扫码支付返回参数
	 * @param wechatScanRep
	 * @param payTranLs
	 */
	public ScanPayRepDto(WechatScanRep wechatScanRep,PayTranLs payTranLs){
		this.trade_type = TransType.PAY.getValue()+"";
		this.prepay_id = wechatScanRep.getPrepay_id();
		this.code_url = wechatScanRep.getCode_url();
		this.trace_num =payTranLs.getTransNum();
		this.user_order_no = payTranLs.getUser_order_no();
	}
	
	/**
	 * 构造支付宝扫码支付返回参数
	 * @param aliScanRep
	 * @param payTranLs
	 */
	public ScanPayRepDto(AliScanRep aliScanRep,PayTranLs payTranLs){
		this.trade_type =TransType.PAY.getValue()+"";
		this.code_url = aliScanRep.getQr_code();
		this.trace_num =payTranLs.getTransNum();
		this.user_order_no = payTranLs.getUser_order_no();
	}
	
	/**
	 * 构造浦发银行扫码支付返回参数
	 * @param pfScanRep
	 * @param payTranLs
	 */
	public ScanPayRepDto(PfScanRep pfScanRep,PayTranLs payTranLs){
		this.trade_type =TransType.PAY.getValue()+"";
		this.code_url = new String(Base64.decode(pfScanRep.getCodeUrl()));
		this.trace_num =payTranLs.getTransNum();
		this.user_order_no = payTranLs.getUser_order_no();
	}
	
	/**
	 * 构造银联扫码支付返回参数
	 * @param unionScanRep
	 * @param payTranLs
	 */
	public ScanPayRepDto(UnionScanRep unionScanRep,PayTranLs payTranLs){
		this.trade_type =TransType.PAY.getValue()+"";
		this.code_url = unionScanRep.getQrCode();
		this.trace_num =payTranLs.getTransNum();
		this.user_order_no = payTranLs.getUser_order_no();
	}
	
	public Map<String, String> toMap() {
		Map<String, String> map = new HashMap<String, String>();
		Field[] fields = this.getClass().getDeclaredFields();
		for (Field field : fields) {
			Object obj;
			try {
				obj = field.get(this);
				if (obj != null) {
					if (field.getType() == int.class) {
						map.put(field.getName(), String.valueOf(obj));
					} else {
						map.put(field.getName(), (String) obj);
					}
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return map;
	}
	
	public String getTrade_type() {
		return trade_type;
	}
	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}
	public String getPrepay_id() {
		return prepay_id;
	}
	public void setPrepay_id(String prepay_id) {
		this.prepay_id = prepay_id;
	}
	public String getCode_url() {
		return code_url;
	}
	public void setCode_url(String code_url) {
		this.code_url = code_url;
	}
	public String getTrace_num() {
		return trace_num;
	}
	public void setTrace_num(String trace_num) {
		this.trace_num = trace_num;
	}
	public String getUser_order_no() {
		return user_order_no;
	}
	public void setUser_order_no(String user_order_no) {
		this.user_order_no = user_order_no;
	}

	public String getPic_url() {
		return pic_url;
	}

	public void setPic_url(String pic_url) {
		this.pic_url = pic_url;
	}

	public String getSmall_pic_url() {
		return small_pic_url;
	}

	public void setSmall_pic_url(String small_pic_url) {
		this.small_pic_url = small_pic_url;
	}
}
