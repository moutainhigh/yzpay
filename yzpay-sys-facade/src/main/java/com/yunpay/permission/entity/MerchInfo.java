package com.yunpay.permission.entity;
import com.yunpay.common.core.utils.annotation.Table;

@Table("t_merchant,t_merchant_industry,t_merchant_acc,t_merchant_attach,t_sys_attach")
public class MerchInfo extends PermissionBaseEntity{
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 * 文件名称:
	 * 内容摘要:  商户信息(基本信息、结算信息、证件信息)
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
	private int id;  // 主键
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	private String merchantNo;// 商户号
	private String merchantName;  //商户名称
	private int auditStatus;  // 审核状态，0：待审核，1：审核通过，2：驳回，3：驳回后二次申请,审核中
	private String registerName;  // 品牌名称
	private String md5Key;  // 商户支付密钥
	private String prov;  // 省
	private String city;  // 市
	private String area;  // 区
	private String address;  // 详细地址
	private String cardName;  // 法人姓名
	private String cardNo;  // 法人身份证
	private String industry; // 所属行业
	private String tel;  // 座机号
	private String email;  // 联系邮箱
	private String accBank;  // 开户银行
	private String accProv;  // 开户所在省
	private String accCity;  // 开始所在市
	private String accSubBank; // 开始支行
	private String accName;  // 开户名称
	private String accNo;  // 银行账户
	private String identityCardNo; // 身份证正面
	private String identityCardNo2; // 身份证反面
	private String busiLicense;  // 营业执照
	private String cyLicense;  // 餐饮许可证
	private String merLogo;  // 店铺logo
	private int attach1;  // 身份证正面附件id
	private int attach2;  // 身份证反面附件id
	private int attach3;  // 营业执照附件id
	private int attach4;  // 餐饮许可证附件id
	private int attach5;  // 店铺logo附件id
	
	private String merchPwd; // 商户登录密码
	private String pwdSalt;  // 安全码(商户密码 + pwdSalt 串接后进行md5加密时需要用到)

	
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
	public String getRegisterName() {
		return registerName;
	}
	public void setRegisterName(String registerName) {
		this.registerName = registerName;
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
	public String getCardName() {
		return cardName;
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
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAccBank() {
		return accBank;
	}
	public void setAccBank(String accBank) {
		this.accBank = accBank;
	}
	public String getAccProv() {
		return accProv;
	}
	public void setAccProv(String accProv) {
		this.accProv = accProv;
	}
	public String getAccCity() {
		return accCity;
	}
	public void setAccCity(String accCity) {
		this.accCity = accCity;
	}
	public String getAccSubBank() {
		return accSubBank;
	}
	public void setAccSubBank(String accSubBank) {
		this.accSubBank = accSubBank;
	}
	public String getAccName() {
		return accName;
	}
	public void setAccName(String accName) {
		this.accName = accName;
	}
	public String getAccNo() {
		return accNo;
	}
	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}
	public String getIdentityCardNo() {
		return identityCardNo;
	}
	public void setIdentityCardNo(String identityCardNo) {
		this.identityCardNo = identityCardNo;
	}
	public String getIdentityCardNo2() {
		return identityCardNo2;
	}
	public void setIdentityCardNo2(String identityCardNo2) {
		this.identityCardNo2 = identityCardNo2;
	}
	public String getBusiLicense() {
		return busiLicense;
	}
	public void setBusiLicense(String busiLicense) {
		this.busiLicense = busiLicense;
	}
	public String getCyLicense() {
		return cyLicense;
	}
	public void setCyLicense(String cyLicense) {
		this.cyLicense = cyLicense;
	}
	public String getMerLogo() {
		return merLogo;
	}
	public void setMerLogo(String merLogo) {
		this.merLogo = merLogo;
	}
	public int getAttach1() {
		return attach1;
	}
	public void setAttach1(int attach1) {
		this.attach1 = attach1;
	}
	public int getAttach2() {
		return attach2;
	}
	public void setAttach2(int attach2) {
		this.attach2 = attach2;
	}
	public int getAttach3() {
		return attach3;
	}
	public void setAttach3(int attach3) {
		this.attach3 = attach3;
	}
	public int getAttach4() {
		return attach4;
	}
	public void setAttach4(int attach4) {
		this.attach4 = attach4;
	}
	public int getAttach5() {
		return attach5;
	}
	public void setAttach5(int attach5) {
		this.attach5 = attach5;
	}
	public int getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(int auditStatus) {
		this.auditStatus = auditStatus;
	}
	public String getMd5Key()
	{
		return md5Key;
	}
	public void setMd5Key(String md5Key)
	{
		this.md5Key = md5Key;
	}
	
	public String getMerchPwd() {
		return merchPwd;
	}
	public void setMerchPwd(String merchPwd) {
		this.merchPwd = merchPwd;
	}
	public String getPwdSalt() {
		return pwdSalt;
	}
	public void setPwdSalt(String pwdSalt) {
		this.pwdSalt = pwdSalt;
	}
}
