package com.yunpay.wx.req.card;

/**
 * 文件名称:    QrCardCodeReq.java
 * 内容摘要:    卡券核销码查询请求参数封装类
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年8月10日下午5:04:04 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年8月10日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class QrCardCodeReq {
	//卡券id(非自定义code可不传)
	private String card_id;
	//单张卡券码
	private String code;
	//是否校验code核销状态
	private boolean check_consume;
	
	public QrCardCodeReq(){}
	
	public QrCardCodeReq(String code,boolean checkConsum){
		setCode(code);
		setCheck_consume(checkConsum);
	}

	public String getCard_id() {
		return card_id;
	}

	public void setCard_id(String card_id) {
		this.card_id = card_id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public boolean getCheck_consume() {
		return check_consume;
	}

	public void setCheck_consume(boolean check_consume) {
		this.check_consume = check_consume;
	}
	
	
}
