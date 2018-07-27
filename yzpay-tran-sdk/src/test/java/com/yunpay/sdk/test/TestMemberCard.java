package com.yunpay.sdk.test;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yunpay.ali.rep.member.AccessTokenRep;
import com.yunpay.ali.rep.member.MemCardQrcodeRep;
import com.yunpay.ali.rep.member.MemCardRep;
import com.yunpay.ali.rep.member.MemUserCardOpenRep;
import com.yunpay.ali.rep.member.MemUserFieldQryRep;
import com.yunpay.ali.rep.pay.AliQrRep;
import com.yunpay.ali.req.member.AccessTokenReq;
import com.yunpay.ali.req.member.MemCardFieldReq;
import com.yunpay.ali.req.member.MemCardQrcodeReq;
import com.yunpay.ali.req.member.MemCardReq;
import com.yunpay.ali.req.member.MemUserCardOpenReq;
import com.yunpay.ali.req.member.MemUserFieldQryReq;
import com.yunpay.ali.req.member.TemplateBaseInfo;
import com.yunpay.ali.req.member.TemplateBenefitInfo;
import com.yunpay.ali.req.member.TemplateColumnInfo;
import com.yunpay.ali.req.member.TemplateFieldRule;
import com.yunpay.ali.req.member.TemplateStyleInfo;
import com.yunpay.ali.service.AliMemberService;




public class TestMemberCard {
	
	private static AliMemberService aliMemberService;
	
	private static ApplicationContext context;
	private static String templateId="20180314000000000858374000300451";//20180305000000000840206000300454
	private static String accessToken = "composeB13c871a8ec7842969069dfb3ea664E10";
	private static String userId = "2088302456750107";
	private static String requestId = "20180314010603676343649365107";
	
	public static void main(String args[]){
		context = new ClassPathXmlApplicationContext("classpath*:spring/spring-context-service.xml");
		aliMemberService =  (AliMemberService)context.getBean("aliMemberService");
		TestMemberCard.testMemUserCardOpen();
		
	}
	
	
	/**
	 * @Description:测试会员卡模板创建 
	 * @author:   Zeng Dongcheng
	 * @Date:     2018年3月6日下午2:21:48
	 */
	public static void testAliMemberCardCreate(){
		//AlipayConfigKey alipayConfig = alipayConfigDao.findConfigByMerNo("999910031001993593");
		TemplateStyleInfo templateStyleInfo = new TemplateStyleInfo("测试会员卡","1T8Pp00AT7eo9NoAJkMR3AAAACMAAQEC",
				"1T8Pp00AT7eo9NoAJkMR3AAAACMAAQEC","rgb(55,112,179)");
		
		TemplateBenefitInfo[] benefitInfo = new TemplateBenefitInfo[1];
		benefitInfo[0]=new TemplateBenefitInfo("消费即折扣","消费即折扣哈哈","2018-03-05 09:00:00","2020-03-05 09:00:00");
		
		TemplateColumnInfo columnInfo0 = new TemplateColumnInfo("BENEFIT_INFO","openWeb","会员专享","80");
		columnInfo0.setMore_info("会员专享权益", "http://www.baidu.com", "{}", "哈哈哈哈");
		TemplateColumnInfo columnInfo1 = new TemplateColumnInfo("BALANCE","staticinfo","余额",null);
		//columnInfo1.setMore_info("会员余额", null, null, "首次赠送100元");
		TemplateColumnInfo columnInfo2 = new TemplateColumnInfo("POINT","staticinfo","积分",null);
		//columnInfo2.setMore_info("会员积分", null, null, "首次赠送10积分");
		TemplateColumnInfo columnInfo3 = new TemplateColumnInfo("TELEPHONE","staticinfo","电话","0755-2233445");
		TemplateColumnInfo columnInfo4 = new TemplateColumnInfo("LEVEL","openNative","会员等级",null);
		columnInfo4.setMore_info("会员等级", null, null, "会员等级说明：黄金级享有8折优惠");
		TemplateColumnInfo[] columnInfoList = new TemplateColumnInfo[5];
		columnInfoList[0]=columnInfo0;
		columnInfoList[1]=columnInfo1;
		columnInfoList[2]=columnInfo2;
		columnInfoList[3]=columnInfo3;
		columnInfoList[4]=columnInfo4;
		
		TemplateFieldRule fieldRule0 = new TemplateFieldRule("Balance","ASSIGN_FROM_REQUEST","Balance");
		TemplateFieldRule fieldRule1 = new TemplateFieldRule("Point","ASSIGN_FROM_REQUEST","Point");
		TemplateFieldRule[] fieldRuleList = new TemplateFieldRule[2];
		fieldRuleList[0]=fieldRule0;
		fieldRuleList[1]=fieldRule1;
		
		TemplateBaseInfo templateBaseInfo = new TemplateBaseInfo("2018031400000000000000001","OUT_MEMBER_CARD","prex","10",
				"qrcode",templateStyleInfo,benefitInfo,columnInfoList,fieldRuleList);
		
		MemCardReq aliMemCardReq = new MemCardReq("2017040506557166",
				"MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALJR8t0SjDQepj0TzQf+D72lEtuzMW+zBoFzWv8XrOWimFvopcBpoJZO6QKXzOeJ3dbUkh4FE28ymOcR8QkFCRkiuGIiPbxUccFX8v1Del4KCatdMv7JqSTYIZNg8f9zd5SXgWqVQRhejMZ6YCC0Dc3+OXx6ql0kM9O4Vn3FAhZfAgMBAAECgYAETfIc4Au+XlcI9mdmn/1lSIzR/NtepOWtTkmOCtZDnM8suMr3gBU+S51YUK3DkRJn0y3Lx7eWYZRLT6WP4C0+Ab0f4D9t5fdy7kpuk+tdxHC/WPn/DZhAAbejrRMw6r16cBmDsgcC6ewfrOvB723+Bb0XAKeYhhoZBOUeZKFmQQJBAOfEaVnjzUKPGgLAk/gPslaD5UAcJQMas+5GZimXAWI/bQAwl/CeVoMfjSR8l7sPbClIl0s7SrcocFS9rybeDpECQQDE9vJpUDN0s5SajyfcvZLP625zm4nnCISpL2vjEYb6gCLOBMzkR1aUtH10Zx5NeccsypXvQZJQI820zvhocy3vAkEA0+VhJIv/iBDpiQakwjEnra2dFYYl2La7NugqU2/6FedDMt86qwU4t11LX8aBusaY7w2tNV0aLGbOfMuHrZNr4QJBAI2vtyHa96jzpeqpIFvCY2H+Ui4HrWPs1MF/w3RMn3SDyIW7Hkj4qGfAjp61ry68c3LdKI479SyBFPEEEd3RPTECQHoZIQhrtZ2ZREJQu8RdExf2+JYxiwsMKNyhEVrVFNzC43Pc65RceavmGfFmMSVBNw8qa2UfN5VHpcdkv1ggw48=",
				"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB",templateBaseInfo);
		
		MemCardRep rep = aliMemberService.doCreateCardTemp(aliMemCardReq);
		if(rep!=null && StringUtils.isNotBlank(rep.getTemplate_id())){
			System.out.println(rep.getTemplate_id());
		}else{
			System.out.println("错误码："+rep.getSub_code()+"，错误原因："+rep.getSub_msg());
		}
	}
	
	/**
	 * @Description: 会员卡激活参数设置
	 * @author:   Zeng Dongcheng
	 * @Date:     2018年3月6日下午2:22:11
	 */
	public static void testCardParamSet(){
		String requiredFields[]={"OPEN_FORM_FIELD_MOBILE","OPEN_FORM_FIELD_NAME"};
		String commonFields[]={"OPEN_FORM_FIELD_BIRTHDAY","OPEN_FORM_FIELD_EMAIL","OPEN_FORM_FIELD_ADDRESS"};
		MemCardFieldReq req = new MemCardFieldReq("2017040506557166",
				"MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALJR8t0SjDQepj0TzQf+D72lEtuzMW+zBoFzWv8XrOWimFvopcBpoJZO6QKXzOeJ3dbUkh4FE28ymOcR8QkFCRkiuGIiPbxUccFX8v1Del4KCatdMv7JqSTYIZNg8f9zd5SXgWqVQRhejMZ6YCC0Dc3+OXx6ql0kM9O4Vn3FAhZfAgMBAAECgYAETfIc4Au+XlcI9mdmn/1lSIzR/NtepOWtTkmOCtZDnM8suMr3gBU+S51YUK3DkRJn0y3Lx7eWYZRLT6WP4C0+Ab0f4D9t5fdy7kpuk+tdxHC/WPn/DZhAAbejrRMw6r16cBmDsgcC6ewfrOvB723+Bb0XAKeYhhoZBOUeZKFmQQJBAOfEaVnjzUKPGgLAk/gPslaD5UAcJQMas+5GZimXAWI/bQAwl/CeVoMfjSR8l7sPbClIl0s7SrcocFS9rybeDpECQQDE9vJpUDN0s5SajyfcvZLP625zm4nnCISpL2vjEYb6gCLOBMzkR1aUtH10Zx5NeccsypXvQZJQI820zvhocy3vAkEA0+VhJIv/iBDpiQakwjEnra2dFYYl2La7NugqU2/6FedDMt86qwU4t11LX8aBusaY7w2tNV0aLGbOfMuHrZNr4QJBAI2vtyHa96jzpeqpIFvCY2H+Ui4HrWPs1MF/w3RMn3SDyIW7Hkj4qGfAjp61ry68c3LdKI479SyBFPEEEd3RPTECQHoZIQhrtZ2ZREJQu8RdExf2+JYxiwsMKNyhEVrVFNzC43Pc65RceavmGfFmMSVBNw8qa2UfN5VHpcdkv1ggw48=",
				"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB",
				templateId,requiredFields,commonFields);
		AliQrRep rep = aliMemberService.doCardTempFieldSet(req);
		if(rep!=null && "10000".equals(rep.getCode())){
			System.out.println("设置成功！");
		}else{
			System.out.println("错误码："+rep.getSub_code()+"，错误原因："+rep.getSub_msg());
		}
	}
	
	/**
	 * @Description: 
	 * @author:   Zeng Dongcheng
	 * @Date:     2018年3月7日上午9:08:29
	 */
	public static void testMemCardQrcode(){
		MemCardQrcodeReq req = new MemCardQrcodeReq("2017040506557166",
				"MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALJR8t0SjDQepj0TzQf+D72lEtuzMW+zBoFzWv8XrOWimFvopcBpoJZO6QKXzOeJ3dbUkh4FE28ymOcR8QkFCRkiuGIiPbxUccFX8v1Del4KCatdMv7JqSTYIZNg8f9zd5SXgWqVQRhejMZ6YCC0Dc3+OXx6ql0kM9O4Vn3FAhZfAgMBAAECgYAETfIc4Au+XlcI9mdmn/1lSIzR/NtepOWtTkmOCtZDnM8suMr3gBU+S51YUK3DkRJn0y3Lx7eWYZRLT6WP4C0+Ab0f4D9t5fdy7kpuk+tdxHC/WPn/DZhAAbejrRMw6r16cBmDsgcC6ewfrOvB723+Bb0XAKeYhhoZBOUeZKFmQQJBAOfEaVnjzUKPGgLAk/gPslaD5UAcJQMas+5GZimXAWI/bQAwl/CeVoMfjSR8l7sPbClIl0s7SrcocFS9rybeDpECQQDE9vJpUDN0s5SajyfcvZLP625zm4nnCISpL2vjEYb6gCLOBMzkR1aUtH10Zx5NeccsypXvQZJQI820zvhocy3vAkEA0+VhJIv/iBDpiQakwjEnra2dFYYl2La7NugqU2/6FedDMt86qwU4t11LX8aBusaY7w2tNV0aLGbOfMuHrZNr4QJBAI2vtyHa96jzpeqpIFvCY2H+Ui4HrWPs1MF/w3RMn3SDyIW7Hkj4qGfAjp61ry68c3LdKI479SyBFPEEEd3RPTECQHoZIQhrtZ2ZREJQu8RdExf2+JYxiwsMKNyhEVrVFNzC43Pc65RceavmGfFmMSVBNw8qa2UfN5VHpcdkv1ggw48=",
				"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB",
				templateId,"999910031001993593",
				"http://siecompay.com/yunpay/pay/alipay/scanpay/notify",null);
		MemCardQrcodeRep rep = aliMemberService.doCardTempQrcode(req);
		if(rep!=null && "10000".equals(rep.getCode())){
			try {
				String applyCardUrl = URLDecoder.decode(rep.getApply_card_ur(),"UTF-8");
				System.out.println("apply_card_url="+applyCardUrl);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else{
			System.out.println("错误码："+rep.getSub_code()+"，错误原因："+rep.getSub_msg());
		}
	}
	
	/**
	 * @Description: 测试获取支付宝access_token
	 * @author:   Zeng Dongcheng
	 * @Date:     2018年3月7日上午11:33:29
	 */
	public static void testAccessToken(String authCode){
		AccessTokenReq req = new AccessTokenReq("2017040506557166",
				"MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALJR8t0SjDQepj0TzQf+D72lEtuzMW+zBoFzWv8XrOWimFvopcBpoJZO6QKXzOeJ3dbUkh4FE28ymOcR8QkFCRkiuGIiPbxUccFX8v1Del4KCatdMv7JqSTYIZNg8f9zd5SXgWqVQRhejMZ6YCC0Dc3+OXx6ql0kM9O4Vn3FAhZfAgMBAAECgYAETfIc4Au+XlcI9mdmn/1lSIzR/NtepOWtTkmOCtZDnM8suMr3gBU+S51YUK3DkRJn0y3Lx7eWYZRLT6WP4C0+Ab0f4D9t5fdy7kpuk+tdxHC/WPn/DZhAAbejrRMw6r16cBmDsgcC6ewfrOvB723+Bb0XAKeYhhoZBOUeZKFmQQJBAOfEaVnjzUKPGgLAk/gPslaD5UAcJQMas+5GZimXAWI/bQAwl/CeVoMfjSR8l7sPbClIl0s7SrcocFS9rybeDpECQQDE9vJpUDN0s5SajyfcvZLP625zm4nnCISpL2vjEYb6gCLOBMzkR1aUtH10Zx5NeccsypXvQZJQI820zvhocy3vAkEA0+VhJIv/iBDpiQakwjEnra2dFYYl2La7NugqU2/6FedDMt86qwU4t11LX8aBusaY7w2tNV0aLGbOfMuHrZNr4QJBAI2vtyHa96jzpeqpIFvCY2H+Ui4HrWPs1MF/w3RMn3SDyIW7Hkj4qGfAjp61ry68c3LdKI479SyBFPEEEd3RPTECQHoZIQhrtZ2ZREJQu8RdExf2+JYxiwsMKNyhEVrVFNzC43Pc65RceavmGfFmMSVBNw8qa2UfN5VHpcdkv1ggw48=",
				"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB",
				authCode);
		AccessTokenRep rep = aliMemberService.doAccessToken(req);
		if(rep!=null && "10000".equals(rep.getCode())){
			accessToken = rep.getAccessToken();
			userId = rep.getUserId();
			System.out.println("access_token="+rep.getAccessToken());
			System.out.println("userId="+rep.getUserId());
		}else{
			System.out.println("错误码："+rep.getSub_code()+"，错误原因："+rep.getSub_msg());
		}
	}
	
	/**
	 * @Description: 查询用户授权时填写的表单信息
	 * @author:   Zeng Dongcheng
	 * @Date:     2018年3月7日下午2:13:04
	 */
	public static void testMemUserFieldQry(){
		MemUserFieldQryReq req = new MemUserFieldQryReq("2017040506557166",
				"MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALJR8t0SjDQepj0TzQf+D72lEtuzMW+zBoFzWv8XrOWimFvopcBpoJZO6QKXzOeJ3dbUkh4FE28ymOcR8QkFCRkiuGIiPbxUccFX8v1Del4KCatdMv7JqSTYIZNg8f9zd5SXgWqVQRhejMZ6YCC0Dc3+OXx6ql0kM9O4Vn3FAhZfAgMBAAECgYAETfIc4Au+XlcI9mdmn/1lSIzR/NtepOWtTkmOCtZDnM8suMr3gBU+S51YUK3DkRJn0y3Lx7eWYZRLT6WP4C0+Ab0f4D9t5fdy7kpuk+tdxHC/WPn/DZhAAbejrRMw6r16cBmDsgcC6ewfrOvB723+Bb0XAKeYhhoZBOUeZKFmQQJBAOfEaVnjzUKPGgLAk/gPslaD5UAcJQMas+5GZimXAWI/bQAwl/CeVoMfjSR8l7sPbClIl0s7SrcocFS9rybeDpECQQDE9vJpUDN0s5SajyfcvZLP625zm4nnCISpL2vjEYb6gCLOBMzkR1aUtH10Zx5NeccsypXvQZJQI820zvhocy3vAkEA0+VhJIv/iBDpiQakwjEnra2dFYYl2La7NugqU2/6FedDMt86qwU4t11LX8aBusaY7w2tNV0aLGbOfMuHrZNr4QJBAI2vtyHa96jzpeqpIFvCY2H+Ui4HrWPs1MF/w3RMn3SDyIW7Hkj4qGfAjp61ry68c3LdKI479SyBFPEEEd3RPTECQHoZIQhrtZ2ZREJQu8RdExf2+JYxiwsMKNyhEVrVFNzC43Pc65RceavmGfFmMSVBNw8qa2UfN5VHpcdkv1ggw48=",
				"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB",
				accessToken,templateId,requestId);
		MemUserFieldQryRep rep = aliMemberService.doMemUserFieldQry(req);
		if(rep!=null && "10000".equals(rep.getCode())){
			//infos=[{"OPEN_FORM_FIELD_BIRTHDAY":"03-07"},{"OPEN_FORM_FIELD_MOBILE":"15889358195"},{"OPEN_FORM_FIELD_NAME":"zhangsan"}]
			System.out.println("infos="+rep.getInfos());
		}else{
			System.out.println("错误码："+rep.getSub_code()+"，错误原因："+rep.getSub_msg());
		}
	}
	
	/**
	 * @Description: 会员卡开卡
	 * @author:   Zeng Dongcheng
	 * @Date:     2018年3月7日下午2:54:58
	 */
	public static void testMemUserCardOpen(){
		MemUserCardOpenReq req =new MemUserCardOpenReq("2017040506557166",
				"MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALJR8t0SjDQepj0TzQf+D72lEtuzMW+zBoFzWv8XrOWimFvopcBpoJZO6QKXzOeJ3dbUkh4FE28ymOcR8QkFCRkiuGIiPbxUccFX8v1Del4KCatdMv7JqSTYIZNg8f9zd5SXgWqVQRhejMZ6YCC0Dc3+OXx6ql0kM9O4Vn3FAhZfAgMBAAECgYAETfIc4Au+XlcI9mdmn/1lSIzR/NtepOWtTkmOCtZDnM8suMr3gBU+S51YUK3DkRJn0y3Lx7eWYZRLT6WP4C0+Ab0f4D9t5fdy7kpuk+tdxHC/WPn/DZhAAbejrRMw6r16cBmDsgcC6ewfrOvB723+Bb0XAKeYhhoZBOUeZKFmQQJBAOfEaVnjzUKPGgLAk/gPslaD5UAcJQMas+5GZimXAWI/bQAwl/CeVoMfjSR8l7sPbClIl0s7SrcocFS9rybeDpECQQDE9vJpUDN0s5SajyfcvZLP625zm4nnCISpL2vjEYb6gCLOBMzkR1aUtH10Zx5NeccsypXvQZJQI820zvhocy3vAkEA0+VhJIv/iBDpiQakwjEnra2dFYYl2La7NugqU2/6FedDMt86qwU4t11LX8aBusaY7w2tNV0aLGbOfMuHrZNr4QJBAI2vtyHa96jzpeqpIFvCY2H+Ui4HrWPs1MF/w3RMn3SDyIW7Hkj4qGfAjp61ry68c3LdKI479SyBFPEEEd3RPTECQHoZIQhrtZ2ZREJQu8RdExf2+JYxiwsMKNyhEVrVFNzC43Pc65RceavmGfFmMSVBNw8qa2UfN5VHpcdkv1ggw48=",
				"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB",
				accessToken,"201803140000001",templateId,
				userId,"EXT0001","2018-03-07 00:00:00","2028-03-06 23:59:59",
				"VIP1","100","200");
		MemUserCardOpenRep rep = aliMemberService.doMemUserCardOpen(req);
		if(rep!=null && "10000".equals(rep.getCode())){
			System.out.println("biz_card_no="+rep.getBizCardNo());
			System.out.println("external_card_no="+rep.getExternalCardNo());
		}else{
			System.out.println("错误码："+rep.getSub_code()+"，错误原因："+rep.getSub_msg());
		}
	}
	
	
}
