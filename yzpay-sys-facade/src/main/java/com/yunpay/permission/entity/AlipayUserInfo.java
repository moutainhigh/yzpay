package com.yunpay.permission.entity;
/**
 * 
 * 文件名称:
 * 内容摘要: 支付宝用户信息实体类,用于接收支付宝返回的用户信息
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
public class AlipayUserInfo {
	private String userId;  // 支付宝用户的userId 
	private String avatar;  //  	用户头像地址 
	private String province; // 省
	private String city;  // 市
	private String nickName;  // 用户昵称
	private char isStudentCertified;  //  是否是学生  (T是学生)
	private char userType ; // 用户类型（1/2）1代表公司账户2代表个人账户 
	private char userStatus ;// 用户状态（Q/T/B/W）。Q代表快速注册用户 T代表已认证用户 B代表被冻结账户 W代表已注册，未激活的账户
	private char isCertified ; // 是否通过实名认证。T是通过 F是没有实名认证。 
	private String gender;  // 性别 （F：女性；M：男性）
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public char getIsStudentCertified() {
		return isStudentCertified;
	}
	public void setIsStudentCertified(char isStudentCertified) {
		this.isStudentCertified = isStudentCertified;
	}
	public char getUserType() {
		return userType;
	}
	public void setUserType(char userType) {
		this.userType = userType;
	}
	public char getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(char userStatus) {
		this.userStatus = userStatus;
	}
	public char getIsCertified() {
		return isCertified;
	}
	public void setIsCertified(char isCertified) {
		this.isCertified = isCertified;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	
	
	
}
