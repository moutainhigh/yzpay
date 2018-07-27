package com.yunpay.permission.entity;
import com.yunpay.common.core.utils.annotation.Table;

@Table("t_merchant_attach,t_merchant,t_sys_attach")
public class MerchAttach extends PermissionBaseEntity{
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 * 文件名称:
	 * 内容摘要: 商户附件信息
	 * @author:   duan zhang quan
	 * @version:  1.0  
	 * @Date:     2017年4月14日上午9:56:12 
	 * 
	 * 修改历史:  
	 * 修改日期                     修改人员                                   版本	            修改内容  
	 * ----------------------------------------------  
	 * 2017年4月14日     duan zhang quan   1.0     新建
	 *
	 * 版权:   版权所有(C)2017
	 * 公司:   深圳市至高通信技术发展有限公司
	 */
	private int id;  //主键
	private int cardNo; // 身份证正面
	private int cardBackNo; // 身份证反面
	private int busiLicense; // 营业执照
	private int cyLicense;  // 餐饮许可证
	private int merLogo;  // 商户logo
	private int merId;    // 商户id
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCardNo() {
		return cardNo;
	}
	public void setCardNo(int cardNo) {
		this.cardNo = cardNo;
	}
	public int getCardBackNo() {
		return cardBackNo;
	}
	public void setCardBackNo(int cardBackNo) {
		this.cardBackNo = cardBackNo;
	}
	public int getBusiLicense() {
		return busiLicense;
	}
	public void setBusiLicense(int busiLicense) {
		this.busiLicense = busiLicense;
	}
	public int getCyLicense() {
		return cyLicense;
	}
	public void setCyLicense(int cyLicense) {
		this.cyLicense = cyLicense;
	}
	public int getMerLogo() {
		return merLogo;
	}
	public void setMerLogo(int merLogo) {
		this.merLogo = merLogo;
	}
	public int getMerId() {
		return merId;
	}
	public void setMerId(int merId) {
		this.merId = merId;
	}
	
	
}
