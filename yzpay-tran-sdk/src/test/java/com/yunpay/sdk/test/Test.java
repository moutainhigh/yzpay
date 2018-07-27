package com.yunpay.sdk.test;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSONObject;
import com.alipay.alipass.sdk.model.request.template.TplCreateRequest;
import com.alipay.alipass.sdk.service.AlipassTransferV2Service;
import com.alipay.alipass.sdk.service.impl.AlipassTransferV2ServiceOpenAPIImpl;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayMobilePublicQrcodeCreateRequest;
import com.alipay.api.request.AlipayPassInstanceAddRequest;
import com.alipay.api.response.AlipayMobilePublicQrcodeCreateResponse;
import com.alipay.api.response.AlipayPassInstanceAddResponse;
import com.alipay.api.response.AlipayPassTemplateAddResponse;
import com.yunpay.ali.config.AliPayConfig;
import com.yunpay.common.utils.AmountUtil;
import com.yunpay.common.utils.CommonUtil;
import com.yunpay.serv.dao.AlipayConfigDao;
import com.yunpay.serv.dao.MembercardDao;
import com.yunpay.serv.model.AlipayConfigKey;
import com.yunpay.serv.model.Membercard;
import com.yunpay.wx.rep.pay.WechatBarRep;
import com.yunpay.wx.rep.pay.WechatRefundQueryRep;
import com.yunpay.wx.rep.pay.WechatRefundRep;
import com.yunpay.wx.rep.pay.WechatScanRep;
import com.yunpay.wx.req.pay.WechatBarReq;
import com.yunpay.wx.req.pay.WechatRefundQueryReq;
import com.yunpay.wx.req.pay.WechatRefundReq;
import com.yunpay.wx.req.pay.WechatScanReq;
import com.yunpay.wx.service.WechatQrPayService;

public class Test {
	
	private static final String APPID = "wx6bef25aa3a28fb20";
	private static final String MERCHID="1297474301";
	private static final String SUBMERCHID="1298031301";
	
	public static WechatQrPayService payService;
	private static AlipayConfigDao alipayConfigDao;
	
	private static MembercardDao membercardDao;
	
	
	@SuppressWarnings("resource")
	public static void main(String args[]){
//		ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:spring/spring-context-service.xml");
//	    payService = (WechatQrPayService) context.getBean("wechatQrPayService");
//	    alipayConfigDao = (AlipayConfigDao)context.getBean("alipayConfigDao");
//		membercardDao = (MembercardDao)context.getBean("membercardDao");;
//	    
//		Test.testMemberdao();
//		Test.testArraySpecil();
		String discountAmt = "9.10";
		String totalFee="13.00";
		
		Float totalPrice = Float.valueOf(totalFee);
		Float disAmt = Float.valueOf(discountAmt);
		Float transPrice = totalPrice-disAmt;
		
		BigDecimal bTotalFee = new BigDecimal(totalFee);
		BigDecimal bDisFee = new BigDecimal(discountAmt);
		bTotalFee.subtract(bDisFee);
		
		int itotalFee = Integer.valueOf(AmountUtil.changeY2F(transPrice.toString()));
		System.out.println(bTotalFee.subtract(bDisFee).floatValue());
	}
	
	
	
	public static void testArrayMultiply(){
		int a[]={2,3,4,5,10};
		int b[]=new int[a.length];
		for(int i=0;i<a.length;i++){
			int x=1;
			for(int j=0;j<a.length;j++){
				if(j==i){
					continue;
				}
				x=x*a[j];
			}
			b[i]=x;
			System.out.println(x);
		}
	}
	
	public static void testArraySpecil(){
		int a[]={1,3,7,9,5,5,9,4,3,6,1,7};
		Arrays.sort(a);
		for(int i=0;i<a.length;i++){
			if(i%2==0){
				if(a[i]!=a[i+1]){
					System.out.println(a[i]);
					break;
				}
			}
		}
	}
	
	
	public static void testMemberdao(){
		Membercard membercard = membercardDao.queryMembercard("75", "642879194479");
		membercard.setOpenId("o_waUv41BRmY4pan57Q_vDO0-0Bg");
		membercardDao.update(membercard);
	}
	
	/**
	 * @Description: 支付宝卡券测试
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月21日下午4:27:06
	 */
	public static void testAliCardTemplate(){
		String content = "{\"evoucherInfo\":{"
        		+ "\"title\":\"$filmName$\",\"startDate\":\"$startDate$\",\"endDate\":\"$endDate$\","
       // 		+ "\"title\":\"$filmName$\",\"startDate\":\"" + sysCardTemplateSend.getStartDate() +"\",\"endDate\":\"" + sysCardTemplateSend.getEndDate() +"\","
        		+ "\"type\":\"coupon\",\"product\":\"groupon\","
        		+ "\"operation\":["
        	//	+ "{\"message\":\"$ackCode$\",\"messageEncoding\":\"UTF-8\",\"format\":\"wave\",\"altText\":\"$ackCode$\"},"
        		+ "{\"message\":\"$ackCode$\",\"messageEncoding\":\"UTF-8\",\"format\":\"barcode\",\"altText\":\"$ackCode$\"}"
        		+ "],"
        		+ "\"remindInfo\":{\"offset\":1},"
        		+ "\"einfo\":{"
        		+ "\"logoText\":\"$filmName$\","
        		+ "\"secondLogoText\":\"$secondLogoText$\","
        		+ "\"headFields\":["
        		+ "{\"key\":\"filmVersion\",\"value\":\"$filmVersion$\",\"label\":\"类型\",\"type\":\"text\"}"
        		+ "],"
        		+ "\"primaryFields\":["
        		+ "{\"key\":\"dayTime\",\"value\":\"$dayTime$\",\"label\":\"有效期\",\"type\":\"text\"}"
        	//	+ ",{\"key\":\"hourTime\",\"value\":\"$hourTime$\",\"label\":\"时间\",\"type\":\"text\"}"
        		+ "],"
        		+ "\"secondaryFields\":["
        		+ "{\"key\":\"cinemaName\",\"value\":\"$cinemaName$\",\"label\":\"商户\",\"type\":\"text\"}"
        		+ "],"
        		+ "\"auxiliaryFields\":["
        		+ "{\"key\":\"hallName\",\"value\":\"$hallName$\",\"label\":\"2\",\"type\":\"text\"},"
        		+ "{\"key\":\"count\",\"value\":\"$count$\",\"label\":\"3\",\"type\":\"text\"},"
        		+ "{\"key\":\"seatsInfo\",\"value\":\"$seatsInfo$\",\"label\":\"4\",\"type\":\"text\"}"
        		+ "],"
        		+ "\"backFields\":["
        		+ " {\"key\":\"introduction\",\"value\":\"$introduction$\",\"label\":\"详情描述\",\"type\":\"text\"}"
        		+ ",{\"key\":\"operation\",\"value\":\"$operation$\",\"label\":\"使用须知\",\"type\":\"text\"}"
        		+ "]"
        		+ "},"
        		+ "\"locations\":["
        		+ "{\"addr\":\"$addr$\",\"tel\":\"$tel$\",\"relevantText\":\"$relevantText$\",\"longitude\":\"$longitude$\",\"latitude\":\"$latitude$\"}"
        		+ "]"
        		+ "},"
        		+ "\"style\":{\"backgroundColor\":\"$backColor$\"},"
        		+ "\"fileInfo\":{\"canShare\":false,\"formatVersion\":\"2\",\"serialNumber\":\"$serialNumber$\"},"
        		+ "\"merchant\":{\"mname\":\"Alipassprod\",\"mtel\":\"\",\"minfo\":\"\"},"
        		+ "\"platform\":{\"channelID\":\"$channelID$\",\"webServiceUrl\":\"$webServiceUrl$\"},"
        		+ "\"appInfo\":null,\"alipayVerify\":[]}";
		AlipassTransferV2Service transferService = new AlipassTransferV2ServiceOpenAPIImpl();
		TplCreateRequest templateReq = new TplCreateRequest();
        templateReq.setLogo("https://alipass.alipay.com//temps/free/logo.png");
        templateReq.setStrip("https://alipass.alipay.com//temps/free/strip.png");
        templateReq.setContent(content);
        templateReq.setUniqueId(String.valueOf(System.currentTimeMillis()));//外部唯一标识
        AlipayConfigKey alipayConfig = alipayConfigDao.findConfigByMerNo("999910031001993593");
        templateReq.setPrivateKeyData(alipayConfig.getStoreAppPrivateKey());
        templateReq.setAppId(alipayConfig.getAppId());
        templateReq.setAlipayApiUrl(AliPayConfig.BAR_PAY_API);
        try {
			AlipayPassTemplateAddResponse response =  transferService.createTemplate(templateReq);
			//解析tmplateId
	        if ("10000".equals(response.getCode())) {
	        	JSONObject resultObj = JSONObject.parseObject(response.getResult());
	            String templateId = resultObj.getString("tpl_id");
	            System.out.println("----------支付宝卡券id="+templateId);
	        }
		} catch (AlipayApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
	}
	
	/**
	 * @Description: 支付宝服务窗二维码
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月22日下午3:05:22 
	 * @return
	 */
    public static String testAlipayQrcodeURL(){
    	String qrcode_url = "";
    	AlipayConfigKey alipayConfig = alipayConfigDao.findConfigByMerNo("999910031001993593");
    	AlipayClient alipayClient = new DefaultAlipayClient(AliPayConfig.BAR_PAY_API, alipayConfig.getAppId(), 
    			alipayConfig.getStoreAppPrivateKey(), "json", "UTF-8",alipayConfig.getStoreAppPublicKey());
    	System.out.println("privateKey="+alipayConfig.getStoreAppPrivateKey());
    	System.out.println("alipayPublicKey="+alipayConfig.getStoreAppPublicKey());
        // 使用SDK，构建单发请求模型
        AlipayMobilePublicQrcodeCreateRequest request = new AlipayMobilePublicQrcodeCreateRequest();
        request.setBizContent("{\"codeInfo\": "
        		+ "{\"scene\":{\"sceneId\":\"sendAlipass\"}"
        		+ "},\"codeType\":\"PERM\","
        		+ "\"expireSecond\":\"\",\"showLogo\":\"Y\"}");
        try {
            // 使用SDK，调用单发接口发送纯文本消息
        	AlipayMobilePublicQrcodeCreateResponse response = alipayClient.execute(request);
            //这里只是简单的打印，请开发者根据实际情况自行进行处理
            if (null != response && response.isSuccess()) {
                System.out.println("消息发送成功 : response = " + response.getBody());
                JSONObject jsonObject = JSONObject.parseObject(response.getBody());
                qrcode_url = jsonObject.getJSONObject("alipay_mobile_public_qrcode_create_response").getString("code_img");
                System.out.println("卡券二维码地址："+qrcode_url);
            } else {
                System.out.println("消息发送失败 code=" + response.getCode() + "msg=" + response.getMsg());
            }

        } catch (AlipayApiException e) {
        	e.printStackTrace();
            System.out.println("消息发送失败");
        }
		return qrcode_url;

    }
	
	public static void testCardInstance(){
		AlipayConfigKey alipayConfig = alipayConfigDao.findConfigByMerNo("999910031001993593");
    	AlipayClient alipayClient = new DefaultAlipayClient(AliPayConfig.BAR_PAY_API, alipayConfig.getAppId(), 
    			alipayConfig.getMerchantPrivateKey(), "json", "UTF-8",alipayConfig.getAlipayPublicKey());
		AlipayPassInstanceAddRequest request = new AlipayPassInstanceAddRequest();
		request.setBizContent("{" +
		"\"tpl_id\":\"2017082215435486146905776\"," +
		"\"tpl_params\":{\"filmName\":\"券标题\",\"startDate\":\"2017-08-22 18:00:00\","+ 
		"\"endDate\":\"2017-09-22 18:00:00\","+
		"\"ackCode\":\"12312312300001\",\"secondLogoText\":\"secondLogoText\","+ 
		"\"filmVersion\":\"test filmversion\",\"dayTime\":\"2017-10-22 18:00:00\","+ 
		"\"cinemaName\":\"扬州万事通\",\"hallName\":\"test hallname\","+ 
		"\"count\":\"10\",\"seatsInfo\":\"8\","+ 
		"\"introduction\":\"测试卡券\",\"operation\":\"请出示卡券码\","+ 
		"\"addr\":\"测试地址\",\"tel\":\"0755-8888168\","+ 
		"\"relevantText\":\"测试relevantText\",\"longitude\":\"test longitude\","+ 
		"\"latitude\":\"测试latitude\",\"backColor\":\"RGB(255,126,0)\","+ 
		"\"serialNumber\":\"123456789123456\",\"channelID\":\"2088201564809153\","+ 
		"\"webServiceUrl\":\"https://alipass.alipay.com/builder/syncRecord.htm?tempId=2017081809403640628942920\"}," +
		"\"recognition_type\":\"1\"," +
		"\"recognition_info\":{\"partner_id\":\"2088621710164435\",\"out_trade_no\":\"655223276602540032\"}" +
		" }");
		AlipayPassInstanceAddResponse response;
		try {
			response = alipayClient.execute(request);
			if(response.isSuccess()){
				System.out.println("调用成功");
			} else {
				System.out.println("调用失败");
			}
		} catch (AlipayApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
    
	/**
	 * 条码支付测试
	 * @Description: 
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年7月7日下午3:10:37 
	 * @param outTradeNo
	 * @param authCode
	 */
	public static void testBarOrder(String outTradeNo){
		try {
			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(System.in);// 创建输入流扫描器
			String authCode =scanner.nextLine(); 
			String ip = InetAddress.getLocalHost().getHostAddress();
			WechatBarReq barReq = new WechatBarReq(APPID,MERCHID,SUBMERCHID,outTradeNo,
					authCode,1,"bar pay","termid",ip,"test","test bar pay");
			String sign = CommonUtil.getSign(CommonUtil.toMap(barReq),"12345678qwertyuiasdfghjkzxcvbnm0");
			barReq.setSign(sign);
			WechatBarRep rep = payService.doWechatBarPay(barReq);
			System.out.println(rep.getResult_code());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void testScanOrder(String outTradeNo){
		try {
			String ip = InetAddress.getLocalHost().getHostAddress();
			
			WechatScanReq scanReq = new WechatScanReq(APPID,MERCHID,SUBMERCHID,outTradeNo,5,"bar pay",
					"termid",ip,"test","test bar pay","http://www.siecom.cn/pay/wechat/scanpay/notify");
			String sign = CommonUtil.getSign(CommonUtil.toMap(scanReq),"12345678qwertyuiasdfghjkzxcvbnm0");
			scanReq.setSign(sign);
			WechatScanRep rep = payService.doWechatScanPay(scanReq);
			System.out.println(rep.getResult_code());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void testQueryOrder(String outTradeNo){
		
	}
	
	public static void testCloseOrder(String outTradeNo){
		
	}
	
	public static void testRefundOrder(String outTradeNo){
		String outRefundNo = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		System.out.println("-------------退款单号="+outRefundNo);
		WechatRefundReq req = new WechatRefundReq(APPID,MERCHID,SUBMERCHID,"",
				outTradeNo, outRefundNo, 10,1,"测试退款");
		String sign = CommonUtil.getSign(CommonUtil.toMap(req),"12345678qwertyuiasdfghjkzxcvbnm0");
		req.setSign(sign);
		WechatRefundRep rep = payService.doWechatRefund(req, 
				"E:/usr/local/yunpos/cert/1297474301/apiclient_cert.p12", "1297474301");
		System.out.println(rep.getReturn_code());
	}
	
	/**
	 * 根据支付订单查询退款信息
	 * @Description: 
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年7月7日下午5:10:54 
	 * @param outTradeNo
	 */
	public static void testRefundOrderQuery(String outTradeNo){
		String nonceStr = CommonUtil.getRandomStringByLength(32);
		WechatRefundQueryReq req = new WechatRefundQueryReq(APPID,MERCHID,SUBMERCHID,"",
				outTradeNo,"","",nonceStr);
		String sign = CommonUtil.getSign(CommonUtil.toMap(req),"12345678qwertyuiasdfghjkzxcvbnm0");
		req.setSign(sign);
		WechatRefundQueryRep rep = payService.doWechatRefundQuery(req);
		System.out.println(rep.getReturn_code());
	}
	
	public static void testRefundOrderQuery2(String outRefundNo){
		WechatRefundQueryReq req = new WechatRefundQueryReq(APPID,MERCHID,SUBMERCHID,"",
				outRefundNo);
		String sign = CommonUtil.getSign(CommonUtil.toMap(req),"12345678qwertyuiasdfghjkzxcvbnm0");
		req.setSign(sign);
		WechatRefundQueryRep rep = payService.doWechatRefundQuery(req);
		System.out.println(rep.getReturn_code());
	}
	
}
