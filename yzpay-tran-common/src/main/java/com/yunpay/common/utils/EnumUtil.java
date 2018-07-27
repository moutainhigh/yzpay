package com.yunpay.common.utils;

/**
 * 系统枚举
 * 文件名称:     EnumUtils.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年6月20日下午2:36:05 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年6月20日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class EnumUtil {
	
	/**
	 * 支付渠道
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年6月20日下午1:57:11 
	 */
	public enum PayChannel {
		//支付宝
		ALIPAY("alipay",1),
		//微信
		WECHAT("wechat",2),
		//银联
		UNION("union",3);
		
		private String name;
		private Integer value;
		//构造方法
	    private PayChannel(String name,Integer value) {
	    	this.name = name;
	    	this.value = value;
	    }
	    
		public String getName() {
			return name;
		}
		
		public void setName(String name) {
			this.name = name;
		}
		
		public Integer getValue() {
			return value;
		}
		
		public void setValue(Integer value) {
			this.value = value;
		}
	}
	
	public enum Route{
		NATIVE("原生渠道",1),PF_BANK("浦发银行",2);
		private String name;
		private Integer value;
		
	    private Route(String name,Integer value){
	    	this.value = value;
	    	this.name = name;
	    }
	    //构造方法
	    private Route(Integer value) {
	    	this.value = value;
	    }
	    public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Integer getValue() {
			return value;
		}
		public void setValue(Integer value) {
			this.value = value;
		}
	}
	
	/**
	 * 支付方式
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年6月20日下午1:57:11 
	 */
	public enum  SubChannel {
		ALIPAY_WAP("支付宝Wap",0),ALIPAY_BAR("支付宝条码支付",1),ALIPAY_SCAN("支付宝扫码支付",2),
		WECHAT_WAP("微信Wap",3),WECHAT_BAR("微信条码支付",4),WECHAT_SCAN("微信扫码支付",5),
		WECHAT_APPLET("微信小程序",6),
		UNION_WAP("银联Wap",7),UNION_BAR("银联条码支付",8),UNION_SCAN("银联扫码支付",9),
		ALIPAY_APP("支付宝APP",10),WECHAT_APP("微信APP",11);
		
		private String name;
		private Integer value;
		
	    private SubChannel(String name,Integer value){
	    	this.value = value;
	    	this.name = name;
	    }
	    //构造方法
	    private SubChannel(Integer value) {
	    	this.value = value;
	    }
	    
	    public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		public Integer getValue() {
			return value;
		}
		
		public void setValue(Integer value) {
			this.value = value;
		}

		
	}
	
	/**
	 * 交易类型0：支付 1：退款
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年6月20日下午1:57:11 
	 */
	public enum TransType{
		PAY(0),REFUND(1);
		private Integer value;
		//构造方法
	    private TransType(Integer value) {
	     this.value = value;
	    }
		public Integer getValue() {
			return value;
		}
		public void setValue(Integer value) {
			this.value = value;
		}
	}
	
	/**
	 * 退款状态
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年6月20日下午1:57:11 
	 */
	public enum RefundStatus{
		UNREFUND(0,"发起退款"),REFUNDING(1,"退款中"),REFUNDED(2,"已退款"),FAILREFUND(3,"退款失败");
		
		private Integer value;
		private String name;
		
		//构造方法
	    private RefundStatus(Integer value,String name) {
	    	this.value = value;
	    	this.name = name;
	    }
		
		public Integer getValue() {
			return value;
		}

		public void setValue(Integer value) {
			this.value = value;
		}

		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
	}
	
	public enum PayStatus{
		UNPAY(0,"未支付"),PAYING(1,"支付中"),PAYED(2,"已支付"),REFUNDED(3,"已退款"),
			REFUNDING(4,"退款中"),FAILREFUND(5,"退款失败"),FAILPAY(6,"支付失败"),CANCEL(7,"已撤销"),
			CLOSED(8,"已关闭"),UNKNOWN(9,"状态未知");
		private Integer value;
		private String name;
		//构造方法
	    private PayStatus(Integer value,String name) {
	    	this.value = value;
	    	this.name = name;
	    }
		
		public Integer getValue() {
			return value;
		}

		public void setValue(Integer value) {
			this.value = value;
		}

		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
	}
	
	/**
	 * @Description: 根据value获取支付状态name
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年7月7日上午10:14:06 
	 * @param value
	 * @return
	 */
    public static String getPayStatusNameByValue(int value) {  
    	PayStatus[] enums = PayStatus.values();  
        for (int i = 0; i < enums.length; i++) {  
            if (enums[i].getValue()==value) {  
                return enums[i].getName();  
            }  
        }  
        return "";  
    } 
    
    /**
     * @Description: 根据value获取退款状态name
     * @author:   Zeng Dongcheng
     * @Date:     2017年7月11日下午3:08:40 
     * @param value
     * @return
     */
    public static String getRefundStatusByValue(int value){
    	RefundStatus[] enums = RefundStatus.values();
    	 for (int i = 0; i < enums.length; i++) {  
             if (enums[i].getValue()==value) {  
                 return enums[i].getName();  
             }  
         }  
         return "";  
    }
	
	
	/**
	 * 微信卡券类型
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年08月01日下午1:57:11 
	 */
	public enum CardType{
		GENERAL_COUPON("1","优惠券"),
		DISCOUNT("2","折扣券"),
		CASH("3","现金券"),
		GIFT("4","兑换券"),
		GROUPON("5","团购券");
		
		private String value;
		private String name;
		//构造方法
	    private CardType(String value,String name) {
	    	this.value = value;
	    	this.name = name;
	    }
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}
	
	/**
	 * 微信卡券核销码类型
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年08月01日下午1:57:11 
	 */
	public enum CardCodeType{
		TEXT("CODE_TYPE_TEXT","文本"),
		BARCODE("CODE_TYPE_BARCODE","一维码"),
		QRCODE("CODE_TYPE_QRCODE","二维码"),
		ONLY_QRCODE("CODE_TYPE_ONLY_QRCODE","二维码无code显示"),
		ONLY_BARCODE("CODE_TYPE_ONLY_BARCODE","一维码无code显示"),
		NONE("CODE_TYPE_NONE","不显示code和条形码类型");
		private String code;
		private String name;
		//构造方法
	    private CardCodeType(String code,String name) {
	    	this.code = code;
	    	this.name = name;
	    }
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}
	
	/**
	 * 卡券有效期类型
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年08月01日下午1:57:11 
	 */
	public enum CardDateType{
		FIX_TIME_RANGE("DATE_TYPE_FIX_TIME_RANGE","固定日期区间"),
		FIX_TERM("DATE_TYPE_FIX_TERM","固定时长"),
		PERMANENT("DATE_TYPE_PERMANENT","永久有效");
		
		private String code;
		private String name;
		//构造方法
	    private CardDateType(String code,String name) {
	    	this.code = code;
	    	this.name = name;
	    }
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}
	/**
	 * 卡券有效期类型
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年08月01日下午1:57:11 
	 */
	public enum MemberActive{
		AUTO_ACTIVE(1,"自动激活"),
		WX_ACTIVE(2,"一键激活");
		
		private Integer code;
		private String name;
		//构造方法
	    private MemberActive(Integer code,String name) {
	    	this.code = code;
	    	this.name = name;
	    }
		public Integer getCode() {
			return code;
		}
		public void setCode(Integer code) {
			this.code = code;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}
	
	/**
	 * 卡券有效期类型
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年08月14日下午10:53:11 
	 */
	public enum CardStatus{
		NOT_CMT(0,"未提交"),
		NOT_CHECK(1,"待审核"),
		CHECK_NOTOK(2,"审核未通过"),
		CHECK_OK(3,"审核通过"),
		DEL(4,"已删除"),
		PUSH(5,"已投放");
		
		private Integer value;
		private String name;
		//构造方法
	    private CardStatus(Integer value,String name) {
	    	this.value = value;
	    	this.name = name;
	    }
		public Integer getValue() {
			return value;
		}
		public void setValue(Integer value) {
			this.value = value;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}
	
	/**
	 * 用户领取的卡券状态
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年09月19日下午10:53:11 
	 */
	public enum CouponStatus{
		NOT_USE(0,"未使用"),
		USED(1,"已使用"),
		TIME_OUT(2,"已过期"),
		DELED(3,"已删除"),
		GIVING(4,"赠送中"),
		GIVED(5,"已赠送");
		
		private Integer value;
		private String name;
		//构造方法
	    private CouponStatus(Integer value,String name) {
	    	this.value = value;
	    	this.name = name;
	    }
		public Integer getValue() {
			return value;
		}
		public void setValue(Integer value) {
			this.value = value;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}
	
	/**
	 * 用户领取的会员卡状态
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年09月19日下午10:53:11 
	 */
	public enum MembercardStatus{
		GETED(0,"已领取"),
		ACTIVE(1,"已激活"),
		TIME_OUT(2,"已失效"),
		DELED(3,"已删除"),
		CANCEL(4,"已作废");
		
		private Integer value;
		private String name;
		//构造方法
	    private MembercardStatus(Integer value,String name) {
	    	this.value = value;
	    	this.name = name;
	    }
		public Integer getValue() {
			return value;
		}
		public void setValue(Integer value) {
			this.value = value;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}
	
}
