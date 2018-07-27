package com.yunpay.ali.req.card;

/**
 * 文件名称:    UploadImgReq.java
 * 内容摘要:    图片上传请求参数
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年9月12日上午11:14:29 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年9月12日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class UploadImgReq extends CardCommonReq{
	
	private String image_type;
	private String image_name;
	private String image_path;
	
	
	public UploadImgReq(String appId,String privateKey,String alipayPublicKey,
			String imageType,String imageName,String imagePath){
		setApp_id(appId);
		setPrivateKey(privateKey);
		setAlipayPublicKey(alipayPublicKey);
		setImage_type(imageType);
		setImage_name(imageName);
		setImage_path(imagePath);
	}
	
	public String getImage_type() {
		return image_type;
	}
	public void setImage_type(String image_type) {
		this.image_type = image_type;
	}
	public String getImage_name() {
		return image_name;
	}
	public void setImage_name(String image_name) {
		this.image_name = image_name;
	}

	public String getImage_path() {
		return image_path;
	}

	public void setImage_path(String image_path) {
		this.image_path = image_path;
	}
	
	
}
