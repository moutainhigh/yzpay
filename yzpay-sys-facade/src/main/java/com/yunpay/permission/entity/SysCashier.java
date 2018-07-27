package com.yunpay.permission.entity;
import java.util.Date;

import com.yunpay.common.core.utils.annotation.Table;
/**
 * 收银员实体类 
 * @author duan zhang quan
 *
 */
@Table("t_merchant_user")
public class SysCashier{
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	private Integer id;  // 主键
    private String userName; //用户名
    private String loginName;  // 登录名
    private String loginPwd;  // 登录密码
    private String phone;  //联系电话
    private String userType;  //用户类型  用户类型(1：店主，2：店长，3：店员)
    private String merchantNo;  // 商户号
    private String storeNo ;  // 门店号
    private String userStatus; // 用户状态 1:启用 2:停用
    private Date createTime;  // 创建时间
    private String orgNo;  // 组织结构
    private String pwdSalt; // 加密salt
	
	public int getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getLoginPwd() {
		return loginPwd;
	}
	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getMerchantNo() {
		return merchantNo;
	}
	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}
	public String getStoreNo() {
		return storeNo;
	}
	public void setStoreNo(String storeNo) {
		this.storeNo = storeNo;
	}
	public String getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getOrgNo() {
		return orgNo;
	}
	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}
	public String getPwdSalt() {
		return pwdSalt;
	}
	public void setPwdSalt(String pwdSalt) {
		this.pwdSalt = pwdSalt;
	}
    
    
	
    

  
    
}