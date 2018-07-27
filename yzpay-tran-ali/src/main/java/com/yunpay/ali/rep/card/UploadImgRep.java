package com.yunpay.ali.rep.card;

import com.yunpay.ali.rep.pay.AliQrRep;

/**
 * 文件名称:     UploadImgRep.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2018年3月1日下午2:05:28 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2018年3月1日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2018
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class UploadImgRep extends AliQrRep{
	//图片/视频在商家中心的唯一标识
	private String image_id;
	//图片/视频的访问地址
	private String image_url;
	
	public UploadImgRep(){
		
	}
	
	public UploadImgRep(String code,String msg,String sub_code,String sub_msg){
		super(code,msg,sub_code,sub_msg);
	}

	public String getImage_id() {
		return image_id;
	}

	public void setImage_id(String image_id) {
		this.image_id = image_id;
	}

	public String getImage_url() {
		return image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	
	

}
