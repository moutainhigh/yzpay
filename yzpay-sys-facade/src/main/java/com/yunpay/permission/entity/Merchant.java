package com.yunpay.permission.entity;
import java.util.Date;
import com.yunpay.common.core.utils.ExcelField;
import com.yunpay.common.core.utils.annotation.Table;

@Table("t_merchant,t_merchant_industry,t_store")
public class Merchant extends PermissionBaseEntity {
	/**
	 * 
	 * 文件名称:
	 * 内容摘要: 商户信息
	 * @author:   duan zhang quan
	 * @version:  1.0  
	 * @Date:     2017年6月16日上午9:56:12 
	 * 修改历史:  
	 * 修改日期                     修改人员                                   版本	            修改内容  
	 * ----------------------------------------------  
	 * 2017年6月16日上午9:56:12   duan zhang quan   2.0     新建
	 *
	 * 版权:   版权所有(C)2017
	 * 公司
	 */
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "序号")
	private int id;
	@ExcelField(title = "商户名称")
	private String registerName; // 商户名称(营业执照名称)
	@ExcelField(title = "商户号")
	private String merchantNo; // 商户编号	
	private String merchantName; // 商户简称(品牌名称)
	private String agentSerialNo;  // 代理商编号
	@ExcelField(title = "所属行业")
	private String industryType;   // 行业类型
	private int industryTypeId; // 行业类型id
	private String md5Key;  // 商户密钥
	@ExcelField(title = "联系人")
	private String contactMan;  // 联系人
	@ExcelField(title = "手机号码")
	private String phone; // 联系人手机
	private String tel; // 联系人电话
	@ExcelField(title = "省市区")
	private String prov;  //省
	private String city;  //市
	private String area;  // 区
	private String address;// 联系地址
	private String email;  // 邮箱
	
	private int auditStatus; // 审核状态，0：待审核，1：审核通过，2：驳回，3：驳回后二次申请,审核中
	
	@ExcelField(title = "审核状态")
	private String auditStatusStr ;
	
	@ExcelField(title = "审核意见")
	private String auditMemo; // 审核意见
	@ExcelField(title = "状态")
	private String status; // 状态 0：停用，1：启用
	@ExcelField(title = "信息来源")
	private String infoFrom;  // 信息来源
	@ExcelField(title = "录入人")
	private String createdBy;  //录入人
	private String cleanType; // 清算类型，0：对公清算,1：对私清算，2：交叉清算，3：双账户清算
	private String orgNo; //组织机构编码
	@ExcelField(title = "录入时间")
	private Date createdAt;  // 录入时间
	@ExcelField(title = "更新时间")
	private Date updatedAt;  // 更新时间
	@ExcelField(title = "门店数量")
	private String storeCount;  //门店数量
	private String cardName; // 法人姓名
	private String cardNo;  //法人身份证
	
	public String getCardName() {
		return cardName;
	}
	
	public String getAuditStatusStr() {
		return auditStatusStr;
	}

	public void setAuditStatusStr(String auditStatusStr) {
		this.auditStatusStr = auditStatusStr;
	}
	
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRegisterName() {
		return registerName;
	}

	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getAgentSerialNo() {
		return agentSerialNo;
	}

	public void setAgentSerialNo(String agentSerialNo) {
		this.agentSerialNo = agentSerialNo;
	}

	

	public String getIndustryType() {
		return industryType;
	}

	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}

	public String getMd5Key() {
		return md5Key;
	}

	public void setMd5Key(String md5Key) {
		this.md5Key = md5Key;
	}

	public String getContactMan() {
		return contactMan;
	}

	public void setContactMan(String contactMan) {
		this.contactMan = contactMan;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getProv() {
		return prov;
	}

	public void setProv(String prov) {
		this.prov = prov;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	

	public int getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(int auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getAuditMemo() {
		return auditMemo;
	}

	public void setAuditMemo(String auditMemo) {
		this.auditMemo = auditMemo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getInfoFrom() {
		return infoFrom;
	}

	public void setInfoFrom(String infoFrom) {
		this.infoFrom = infoFrom;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCleanType() {
		return cleanType;
	}

	public void setCleanType(String cleanType) {
		this.cleanType = cleanType;
	}

	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getStoreCount() {
		return storeCount;
	}

	public void setStoreCount(String storeCount) {
		this.storeCount = storeCount;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getIndustryTypeId() {
		return industryTypeId;
	}

	public void setIndustryTypeId(int industryTypeId) {
		this.industryTypeId = industryTypeId;
	}
	
	
	

}
