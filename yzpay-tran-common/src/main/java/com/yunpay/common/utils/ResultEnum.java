package com.yunpay.common.utils;

public class ResultEnum {
	
	/**
	 * 外部结果
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年6月20日下午1:57:11 
	 */
	public enum ResultCode{
		SUCCESS("SUCCESS","成功"),	
		FAIL("FAIL","失败"),	//失败
		EXCEPTION("ERR","系统异常"),
		PROCCESSING("PROC","处理中");
		
		//构造方法
	    private ResultCode(String code, String des) {
	    	this.code = code;
	    	this.des = des;
	    }
		
		private String code;
		private String des;
		
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getDes() {
			return des;
		}
		public void setDes(String des) {
			this.des = des;
		}
	}
	
	/**
	 * 错误明细
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年6月20日下午1:57:11 
	 */
	public enum  ErrorCode {
		//参数验证类错误
		//PARAM_VALID_ERROR("VERIFY_ERROR","校验错误"),
		PARAM_SIGN_ERROR("1001","签名错误"),
		PARAM_NULL_ERROR("1002","参数不能为空：%s"),
		PARAM_LENGTH_ERROR("1003","参数长度错误：%s"),
		PARAM_SPECIALCHAR_ERROR("1004","参数不能包含特殊字符：%s"),
		PARAM_CHINESE_ERROR ("1005","参数不能含有中文字符：%s"),
		PARAM_EMAIL_ERROR("1006","参数应为邮箱格式：%s"),
		PARAM_IP_ERROR("1007","参数应为ip地址格式：%s"),
		PARAM_MOBILE_ERROR("1008","参数应为手机号码格式：%s"),
		PARAM_NUMBER_ERROR("1009","参数应为数字格式：%s"),
		PARAM_DATE_ERROR("1010","参数应为yyyy-mm-dd格式：%s"),
		PARAM_DATETIME_ERROR("1011","参数应为yyyy-MM-dd hh:mm:ss格式：%s"),
		PARAM_DECIMAL_ERROR("1012","参数应为最多两位小数的金额格式：%s"),
		PARAM_FORMAT_ERROR("1013","参数格式不正确：%s"),
		PARAM_QRCODE_ERR("1014","支付码未授权，请勿支付！"),
		PARAM_QRCODE_OUTDATE("1015","支付码已过期，请勿支付！"),
		//信息是否存在类错误
		MERCHANT_NOT_EXIST("2001","商户信息不存在"),
		ORDER_EXIST("2002","交易流水已存在"),
		ORDER_NOT_EXIST("2003","交易流水不存在"),
		REFUND_ORDER_EXIST("2004","退款流水已存在"),
		REFUND_ORDER_NOT_EXIST("2005","退款流水不存在"),
		ILLEGAL_PAYCONFIG("2006","支付信息未配置"),
		CASHIER_NOT_EXIST("2007","收银员不存在"),
		STORE_NOT_EXIST("2008","门店不存在"),
		CARD_NOT_EXIST("2009","卡券不存在"),
		MEMBER_NOT_EXIST("2010","抱歉，您还不是会员！"),
		
		//异常类信息
		CHANNEL_PAY_ERROR("3001","渠道返回异常!"),
		PAY_CODE_ERROR("3002","支付码异常!"),
		SYSTEM_EXCEPTION("3003","系统错误!"),
		SIGN_TYPE_ERROR("3004","签名类型错误!"),
		CARD_CONSUME_ERROR("3005","卡券核销异常!"),
		ORDER_REVERSE_ERROR("3006","撤销订单异常!"), 
		SYS_DATA_EXCEPTION("3007","系统数据错误!"),
		CHANNEL_SIGN_ERROR("3008","渠道返回验签错误!"),
		SYS_ACTIVEMQ_ERROR("3009","系统消息队列错误！"),
		//信息状态类错误
		ORDER_REFUNDED("4001","已退款，不可重复退款"),
		ORDER_CANCELED("4002","已撤销,不可重复撤销"),
		ORDER_PAIED("4003","订单已支付"),
		ORDER_CLOSED("4004","订单已关闭!"),
		ORDER_REVERSED("4005","订单已撤销!"),
		ORDER_PAYING("4006","支付中"),
		ORDER_REFUND_FEE("4007","退款金额不能大于订单总额"),
		ORDER_RESULT_NOT("4008","支付结果未知"),
		MEMBER_STATUS_ERR("4009","此状态下的会员不能进行该操作！"),
		ORDER_NOT_REVERSE("4010","该订单不能撤销!"),
		ORDER_REFUNDING("4011","退款处理中"),
		
		//信息超过限制类错误
		MEMBER_BONUS_LESS("5001","抱歉，您的会员积分不够，还剩%s分！"),
		MEMBER_TRANAMT_LESS("5002","抱歉，至少需要交易%s元才能使用积分！"),
		MEMBER_USEBONUS_MORE("5003","抱歉，单笔最多只能抵扣%s积分！"),
		MEMBER_BALANCE_LESS("5003","抱歉，您的余额不足！");
		
		private String code;
		private String des;
		 
		//构造方法
	    private ErrorCode(String code, String des) {
	    	this.code = code;
	    	this.des = des;
	    }

		public String getCode() {
			return code;
		}

		public void setName(String code) {
			this.code = code;
		}

		public String getDes() {
			return des;
		}

		public void setDes(String des) {
			this.des = des;
		}
	}
}
