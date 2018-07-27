package com.yunpay.permission.entity;

public class SumDayDeal {
	/**
	 * 
	 * 文件名称:
	 * 内容摘要: 日交易汇总数据
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
	private String channel;  // 交易渠道
	private String transTime;  // 交易日期
	private float alipaySumTrans;  // 支付宝交易金额
	private int alipayCountTrans;  // 支付宝交易笔数
	private float wechatSumTrans;  // 微信交易金额
	private int wechatCountTrans;  // 微信交易笔数
	
	public SumDayDeal(String channel,String transTime){
		this.channel = channel;
		this.transTime = transTime;
	}
	
	public SumDayDeal(String channel,String transTime,int wechatCountTrans,int alipayCountTrans){
		this.channel = channel;
		this.transTime = transTime;
		this.wechatCountTrans = wechatCountTrans;
		this.alipayCountTrans = alipayCountTrans;
		
	}
	
	
	public SumDayDeal(){
		
	}
	
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getTransTime() {
		return transTime;
	}
	public void setTransTime(String transTime) {
		this.transTime = transTime;
	}
	public float getAlipaySumTrans() {
		return alipaySumTrans;
	}
	public void setAlipaySumTrans(float alipaySumTrans) {
		this.alipaySumTrans = alipaySumTrans;
	}
	public int getAlipayCountTrans() {
		return alipayCountTrans;
	}
	public void setAlipayCountTrans(int alipayCountTrans) {
		this.alipayCountTrans = alipayCountTrans;
	}
	public float getWechatSumTrans() {
		return wechatSumTrans;
	}
	public void setWechatSumTrans(float wechatSumTrans) {
		this.wechatSumTrans = wechatSumTrans;
	}
	public int getWechatCountTrans() {
		return wechatCountTrans;
	}
	public void setWechatCountTrans(int wechatCountTrans) {
		this.wechatCountTrans = wechatCountTrans;
	}
	
	
	
}
