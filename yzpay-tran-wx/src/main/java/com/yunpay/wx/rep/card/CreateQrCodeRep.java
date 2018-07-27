package com.yunpay.wx.rep.card;

import com.yunpay.common.utils.ResultEnum.ResultCode;

/**
 * 文件名称:     CreateQrCodeRep.java
 * 内容摘要: 	     微信卡券二维码结果返回封装类
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2018年3月1日上午10:08:43 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2018年3月1日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2018
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class CreateQrCodeRep extends AppBaseRep{
	
	//二维码ticket,可在有效期内获取二维码图片
	private String ticket;
	//二维码有效期
	private String expire_seconds;
	//二维码图片解析后的地址
	private String url;
	//二维码显示地址，点击后跳转二维码页面
	private String show_qrcode_url;
	
	/**
	 * 构造成功结果
	 * @param ticket
	 * @param expireSeconds
	 * @param url
	 * @param showQrcodeUrl
	 */
	public CreateQrCodeRep(String ticket,String expireSeconds,String url,String showQrcodeUrl){
		setReturn_code(ResultCode.SUCCESS.getCode());
		setTicket(ticket);
		setExpire_seconds(expireSeconds);
		setUrl(url);
		setShow_qrcode_url(showQrcodeUrl);
	}
	
	/**
	 * 构造失败结果
	 * @param errCode
	 * @param errCodeDes
	 */
	public CreateQrCodeRep(String errCode,String errCodeDes){
		super(errCode,errCodeDes);
	}
	
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	public String getExpire_seconds() {
		return expire_seconds;
	}
	public void setExpire_seconds(String expire_seconds) {
		this.expire_seconds = expire_seconds;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getShow_qrcode_url() {
		return show_qrcode_url;
	}
	public void setShow_qrcode_url(String show_qrcode_url) {
		this.show_qrcode_url = show_qrcode_url;
	}
	
	
	
}
