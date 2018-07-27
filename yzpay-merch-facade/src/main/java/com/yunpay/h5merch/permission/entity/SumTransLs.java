package com.yunpay.h5merch.permission.entity;
import com.yunpay.common.core.utils.annotation.Table;
import com.yunpay.permission.entity.PermissionBaseEntity;
/**
 * 商户交易汇总
 * @author duan zhang quan
 *
 */
@SuppressWarnings("serial")
@Table("t_pay_tran_ls,t_merchant,t_store,t_merchant_user")
public class SumTransLs extends PermissionBaseEntity{
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
	private String storeName;  //门店名称
	private String storeNo;  // 门店号
	private String merchantNo;  //商户号
	private int countTran;  // 总交易笔数
	private float sumTran;  // 总交易金额
	private String channel;  // 交易渠道

	private String transTime; // 交易时间段
	
	public SumTransLs(){}
	
	public SumTransLs(String transTime,float sumTran){
		this.transTime = transTime;
		this.sumTran = sumTran;
	}
	

	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public int getCountTran() {
		return countTran;
	}
	public void setCountTran(int countTran) {
		this.countTran = countTran;
	}
	

	public String getStoreNo() {
		return storeNo;
	}
	public void setStoreNo(String storeNo) {
		this.storeNo = storeNo;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getMerchantNo() {
		return merchantNo;
	}
	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}
	public String getTransTime() {
		return transTime;
	}
	public void setTransTime(String transTime) {
		this.transTime = transTime;
	}

	public float getSumTran() {
		return sumTran;
	}

	public void setSumTran(float sumTran) {
		this.sumTran = sumTran;
	}
	
	
	
}
