package com.yunpay.permission.entity;
import java.util.Date;
import com.yunpay.common.core.utils.annotation.Table;

/**
 * 商户银行账户信息
 * @author duan zhang quan
 *
 */
@Table("t_merchant_acc,t_merchant")
public class MerchantAccount extends PermissionBaseEntity{
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 * 文件名称:
	 * 内容摘要: 
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
	private int id;   // 主键
	private int merId;  // 商户id
	private String accBank; // 开户银行	
	private String accSubBank; // 开户支行
	private String accNo;  // 银行账号
	private String accName; // 开户人
	private String prov;  // 省
	private String city;  // 市
	private Date createTime;  // 创建人
	private Date updTime;  // 更新人
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMerId() {
		return merId;
	}
	public void setMerId(int merId) {
		this.merId = merId;
	}
	public String getAccBank() {
		return accBank;
	}
	public void setAccBank(String accBank) {
		this.accBank = accBank;
	}
	public String getAccSubBank() {
		return accSubBank;
	}
	public void setAccSubBank(String accSubBank) {
		this.accSubBank = accSubBank;
	}
	public String getAccNo() {
		return accNo;
	}
	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}
	public String getAccName() {
		return accName;
	}
	public void setAccName(String accName) {
		this.accName = accName;
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdTime() {
		return updTime;
	}
	public void setUpdTime(Date updTime) {
		this.updTime = updTime;
	}
	
	
}
