package com.yunpay.permission.entity;
import com.yunpay.common.core.utils.annotation.Table;

/**
 * 
 * 文件名称:
 * 内容摘要: 用户登录密码错误时登录失败，保存失败记录
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
@Table("t_login_fail_record")
public class LoginFailRecord {
	private int id;   // 主键
	private int userId;  // 用户id
	private String loginFailDate;  // 登录失败日期
	private int loginFailCount; // 登录失败次数
	
	/**
	 * 
	* 密码错误时,保存错误次数、错误日期
	* @param 
	* @return LoginFailRecord
	* @throws
	 */
	public static LoginFailRecord counter(LoginFailRecord record,int userId,String nowDate){
		if(record == null){
			// 第一次登录失败时,设置失败次数为1
			record = new LoginFailRecord();
			record.setUserId(userId);
			record.setLoginFailDate(nowDate);
			record.setLoginFailCount(1);
			return record;
		}else{
		   // 多次连续登录失败时,累加失败次数
			int i = record.getLoginFailCount();
			record.setLoginFailCount(i+1);
			return record;
		}
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getLoginFailDate() {
		return loginFailDate;
	}
	public void setLoginFailDate(String loginFailDate) {
		this.loginFailDate = loginFailDate;
	}
	public int getLoginFailCount() {
		return loginFailCount;
	}
	public void setLoginFailCount(int loginFailCount) {
		this.loginFailCount = loginFailCount;
	}
	
	
	
	
	
}
