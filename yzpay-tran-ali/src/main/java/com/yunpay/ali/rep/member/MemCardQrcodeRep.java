package com.yunpay.ali.rep.member;

import com.yunpay.ali.rep.pay.AliQrRep;
/**
 * 文件名称:     MemCardQrcodeRep.java
 * 内容摘要:    会员卡领取链接返回参数封装类
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2018年3月6日下午2:07:59 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2018年3月6日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2018
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class MemCardQrcodeRep extends AliQrRep{
	//会员卡领取链接
	private String apply_card_ur;
	
	public MemCardQrcodeRep(){}
	
	public MemCardQrcodeRep(String code,String msg,String sub_code,String sub_msg){
		super(code,msg,sub_code,sub_msg);
	}

	public String getApply_card_ur() {
		return apply_card_ur;
	}

	public void setApply_card_ur(String apply_card_ur) {
		this.apply_card_ur = apply_card_ur;
	}
	
	
}
