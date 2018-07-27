package com.yunpay.ali.rep.member;

import com.yunpay.ali.rep.pay.AliQrRep;

/**
 * 文件名称:    AliMemCardRep.java
 * 内容摘要:    创建会员卡模板接口返回参数封装类
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年9月12日下午4:07:56 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年9月12日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class MemCardRep extends AliQrRep {
	private String template_id;
	
	public MemCardRep(){}
	
	public MemCardRep(String code,String msg,String sub_code,String sub_msg){
		super(code,msg,sub_code,sub_msg);
	}


	public String getTemplate_id() {
		return template_id;
	}

	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}
	
	
}
