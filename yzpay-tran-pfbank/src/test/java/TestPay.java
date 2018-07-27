import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import com.yunpay.common.utils.EnumUtil.SubChannel;
import com.yunpay.pfbank.rep.PfBaseRep;
import com.yunpay.pfbank.rep.PfMerchAddRep;
import com.yunpay.pfbank.rep.PfMerchBbQryRep;
import com.yunpay.pfbank.rep.PfMerchBbRep;
import com.yunpay.pfbank.rep.PfMerchBjQryRep;
import com.yunpay.pfbank.rep.PfScanRep;
import com.yunpay.pfbank.rep.PfWikiConfigRep;
import com.yunpay.pfbank.rep.PfWikiPayRep;
import com.yunpay.pfbank.req.PfMerchBbQryReq;
import com.yunpay.pfbank.req.PfMerchBbReq;
import com.yunpay.pfbank.req.PfMerchBjQryReq;
import com.yunpay.pfbank.req.PfMerchBjReq;
import com.yunpay.pfbank.req.PfPayReq;
import com.yunpay.pfbank.req.PfWikiConfigReq;
import com.yunpay.pfbank.req.PfWikiPayReq;
import com.yunpay.pfbank.service.PfPayService;
import com.yunpay.pfbank.service.impl.PfPayServiceImpl;
import com.yunpay.pfbank.util.Base64;
import com.yunpay.pfbank.util.ConfigUtils;

public class TestPay {
	
	
	public static void main(String args[]){
		TestPay.testMerchBjQry();
	}
	
	@SuppressWarnings("resource")
	public static void testPay(){
		System.out.print("请选择要测试的支付，1：微信条码，2：支付宝条码，3：微信扫码，4：支付宝扫码：");
		Scanner scan = new Scanner(System.in);
		String read = scan.nextLine();
		String payCode="";
		if(read!=null){
			switch(read){
				case "1":
					System.out.println("------------正在进行微信条码支付测试------------");
					System.out.println("请输入微信支付码：");
					scan = new Scanner(System.in);
					payCode = scan.nextLine();
					TestPay.testWxBarPay(payCode);
					break;
				case "2":
					System.out.println("------------正在进行支付宝条码支付测试------------");
					System.out.println("请输入支付宝支付码：");
					scan = new Scanner(System.in);
					payCode = scan.nextLine();
					TestPay.testAliBarPay(payCode);
					break;
				case "3":
					System.out.println("------------正在进行微信扫码支付测试------------");
					TestPay.testWxScanPay();
					break;
				case "4":
					System.out.println("------------正在进行支付宝扫码支付测试------------");
					TestPay.testAliScanPay();
					break;
				default:
					System.out.println("------------正在进行微信扫码支付测试------------");
					TestPay.testWxScanPay();
					break;
			}
		}
	}
	
	/**
	 * @Description: 测试微信条码支付
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年10月10日上午10:40:23 
	 * @param payCode
	 */
	public static void testWxBarPay(String payCode){
		PfPayService payService = new PfPayServiceImpl();
		PfPayReq wxBarReq = new PfPayReq(
				SubChannel.WECHAT_BAR,ConfigUtils.AGENT_MERCHANT_NO,
				"127.0.0.1","310440300003388","57954871",
				new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()),
				"http://localhost:8080","1","test",payCode);
		PfBaseRep rep = payService.doPfBarPay(wxBarReq);
		System.out.println("返回码："+rep.getRespCode()+"，返回信息："+rep.getRespDesc());
	}
	
	/**
	 * @Description: 测试微信扫码支付
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年10月10日上午10:40:33
	 */
	public static void testWxScanPay(){
		PfPayService payService = new PfPayServiceImpl();
		PfPayReq wxScanReq = new PfPayReq(
				SubChannel.WECHAT_SCAN,ConfigUtils.AGENT_MERCHANT_NO,
				"127.0.0.1","310440300003388","57954871",
				new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()),
				"http://localhost:8080","1","test",null);
		PfScanRep rep = payService.doPfScanPay(wxScanReq);
		System.out.println("返回码："+rep.getRespCode()+"，返回信息："+rep.getRespDesc());
		if("0000".equals(rep.getRespCode()) || "P000".equals(rep.getRespCode())){
			System.out.println("codeUrl："+new String(Base64.decode(rep.getCodeUrl())));
			System.out.println("imgUrl："+rep.getImgUrl());
		}
	}
	
	/**
	 * @Description: 测试支付宝条码支付
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年10月10日上午10:40:46 
	 * @param payCode
	 */
	public static void testAliBarPay(String payCode){
		PfPayService payService = new PfPayServiceImpl();
		PfPayReq wxBarReq = new PfPayReq(
				SubChannel.ALIPAY_BAR,ConfigUtils.AGENT_MERCHANT_NO,
				"127.0.0.1","310440300003388","2088621885367952",
				new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()),
				"http://localhost:8080","1","支付宝条码支付",payCode);
		PfBaseRep rep = payService.doPfBarPay(wxBarReq);
		System.out.println("返回码："+rep.getRespCode()+"，返回信息："+rep.getRespDesc());
	}
	
	/**
	 * @Description: 支付宝扫码支付
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年10月10日上午10:41:01
	 */
	public static void testAliScanPay(){
		PfPayService payService = new PfPayServiceImpl();
		PfPayReq wxScanReq = new PfPayReq(
				SubChannel.ALIPAY_SCAN,ConfigUtils.AGENT_MERCHANT_NO,
				"127.0.0.1","310440300003388","2088621885367952",
				new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()),
				"http://localhost:8080","1","test",null);
		PfScanRep rep = payService.doPfScanPay(wxScanReq);
		System.out.println("返回码："+rep.getRespCode()+"，返回信息："+rep.getRespDesc());
		if("0000".equals(rep.getRespCode()) || "P000".equals(rep.getRespCode())){
			System.out.println("codeUrl："+new String(Base64.decode(rep.getCodeUrl())));
			System.out.println("imgUrl："+rep.getImgUrl());
		}
	}
	
	/**
	 * 测试公众号支付
	 * @Description: 
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年11月21日上午10:07:20
	 */
	public static void testWikiPay(){
		PfPayService payService = new PfPayServiceImpl();
		PfWikiPayReq pfWikiPayReq = new PfWikiPayReq(ConfigUtils.AGENT_MERCHANT_NO,
				"127.0.0.1","310440300003388","2088621885367952",
				new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()),
				"http://localhost:8080","1","test","oedkwv3gZTyD8stHZPL2aL1Cxp1k");
		PfWikiPayRep rep = payService.doPfWikiPay(pfWikiPayReq);
		System.out.println("返回码："+rep.getRespCode()+"，返回信息："+rep.getRespDesc());
		if("0000".equals(rep.getRespCode()) || "P000".equals(rep.getRespCode())){
			System.out.println("payInfo："+new String(Base64.decode(rep.getPayInfo())));
			System.out.println("formfield："+rep.getFormfield());
		}
	}
	
	/**
	 * 配置微信支付授权目录
	 * @Description: 
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年10月30日上午10:25:54
	 */
	public static void testConfigJsPath(){
		PfPayService payService = new PfPayServiceImpl();
		PfWikiConfigReq req = new PfWikiConfigReq(ConfigUtils.AGENT_MERCHANT_NO,"310440300003388","57954871",
				1,"http://siecompay.com/yunpay/pay/onepay/");
		PfWikiConfigRep rep = payService.doPfWikiConfig(req);
		if("0000".equals(rep.getRespCode())){
			System.out.println(rep.getJsApiPath());
		}else{
			System.out.println(rep.getRespDesc());
		}
	}
	
	/**
	 * 配置关联的appId
	 * @Description: 
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年10月30日上午10:26:03
	 */
	public static void testConfigSubAppId(){
		PfPayService payService = new PfPayServiceImpl();
		PfWikiConfigReq req = new PfWikiConfigReq(ConfigUtils.AGENT_MERCHANT_NO,"310440300003388","57954871",
				2,"wxcbf364c912dd0987");
		PfWikiConfigRep rep = payService.doPfWikiConfig(req);
		if("0000".equals(rep.getRespCode())){
			System.out.println(rep.getSubAppId());
		}else{
			System.out.println(rep.getRespDesc());
		}
	}
	
	/**
	 * 配置推荐关注的appId
	 * @Description: 
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年10月30日上午10:26:21
	 */
	public static void testConfigScribeAppId(){
		PfPayService payService = new PfPayServiceImpl();
		PfWikiConfigReq req = new PfWikiConfigReq(ConfigUtils.AGENT_MERCHANT_NO,"310440300003388","57954871",
				3,"wxcbf364c912dd0987");
		PfWikiConfigRep rep = payService.doPfWikiConfig(req);
		if("0000".equals(rep.getRespCode())){
			System.out.println(rep.getSubScribeAppid());
		}else{
			System.out.println(rep.getRespDesc());
		}
	}
	
	/**
	 * 查询微信配置
	 * @Description: 
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年11月21日上午10:27:33
	 */
	public static void queryWikiConfig(){
		PfPayService payService = new PfPayServiceImpl();
		PfWikiConfigReq req = new PfWikiConfigReq(ConfigUtils.AGENT_MERCHANT_NO,"310440300003388","57954871");
		PfWikiConfigRep rep = payService.doPfWikiConfigQuery(req);
		if("0000".equals(rep.getRespCode())){
			System.out.println(rep.getJsApiPath());
			System.out.println(rep.getSubAppId());
			System.out.println(rep.getSubScribeAppid());
		}else{
			System.out.println(rep.getRespDesc());
		}
	}
	
	/**
	 * 商户报件测试
	 * @Description: 
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年11月21日上午11:31:32
	 */
	public static void testMerchBaojian(){
		PfPayService payService = new PfPayServiceImpl();
		PfMerchBjReq req  = new PfMerchBjReq(ConfigUtils.AGENT_MERCHANT_NO,"深圳市贝尔加数据信息有限公司2","贝尔加",
				"4900","雷桂林","210204196209210256","15889358195",
				"test@qq.com","91440300335099014R","10000000","深圳市南山区蛇口工业区工业大道路兴华工业大厦7号8栋2层D223房",
				"4000021509200204579","深圳市贝尔加数据信息有限公司","102584002153","2030-11-11","1","92,2015063000020189");
		PfMerchAddRep rep = payService.doPfMerchBj(req);
		if("0000".equals(rep.getRespCode())){
			System.out.println("平台商户号："+rep.getMerNo());
		}else{
			System.out.println(rep.getRespDesc());
		}
	}
	
	/**
	 * 报件查询测试
	 * @Description: 
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年11月21日下午1:46:07
	 */
	public static void testMerchBjQry(){
		PfPayService payService = new PfPayServiceImpl();
		PfMerchBjQryReq req = new PfMerchBjQryReq(ConfigUtils.AGENT_MERCHANT_NO,null,"深圳市贝尔加数据信息有限公司",null);
		PfMerchBjQryRep rep = payService.doPfMerchBjQry(req);
		if("0000".equals(rep.getRespCode())){
			System.out.println("商户信息："+rep.getMerchantList());
		}else{
			System.out.println(rep.getRespDesc());
		}
	}
	
	/**
	 * 微信商户报备
	 * @Description: 
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年11月21日下午2:30:32
	 */
	public static void testMerchWxBb(){
		PfPayService payService = new PfPayServiceImpl();
		PfMerchBbReq req = new PfMerchBbReq(ConfigUtils.AGENT_MERCHANT_NO,"310440300003771","深圳市贝尔加数据信息有限公司","92",
				"贝尔加","雷桂林","15889358195","zdclovejava@163.com","贝尔加","400-6297958");
		PfMerchBbRep rep = payService.doPfMerchBb(req);
		if("0000".equals(rep.getRespCode())){
			System.out.println("报备返回的商户id："+rep.getSubMchId());
			//subMchId=72470809
			//subMchId=72475211
		}else{
			System.out.println(rep.getRespDesc());
		}
	}
	
	/**
	 * 支付宝商户报备
	 * @Description: 
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年11月21日下午2:34:16
	 */
	public static void testMerchAliBb(){
		PfPayService payService = new PfPayServiceImpl();
		PfMerchBbReq req = new PfMerchBbReq(ConfigUtils.AGENT_MERCHANT_NO,"310440300003771",
				"深圳市贝尔加数据信息有限公司","2015063000020189","贝尔加","雷桂林","15889358195",
				"zdclovejava@163.com","贝尔加s","400-6297958","NATIONAL_LEGAL","91440300335099014R");
		req.setContactInfo("雷桂林","zdclovejava@163.com","LEGAL_PERSON", "210204196209210256");
		req.setBankCardInfo("4000021509200204579", "深圳市贝尔加数据信息有限公司");
		req.setAddressInfo("440000", "440300", "440305", "深圳市南山区蛇口工业区工业大道路兴华工业大厦7号8栋2层D223房");
		PfMerchBbRep rep = payService.doPfMerchBb(req);
		if("0000".equals(rep.getRespCode())){
			System.out.println("报备返回的商户id："+rep.getSubMchId());
			//subMchId=2088621888397103
		}else{
			System.out.println(rep.getRespDesc());
		}
	}
	
	/**
	 * @Description: 微信报备查询
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年11月21日下午4:21:24
	 */
	public static void testMerchWxBbQry(){
		PfPayService payService = new PfPayServiceImpl();
		PfMerchBbQryReq req = new PfMerchBbQryReq(ConfigUtils.AGENT_MERCHANT_NO,"WX",
				"310440300003771","72470809");
		PfMerchBbQryRep rep = payService.doPfMerchBbQry(req);
		if("0000".equals(rep.getRespCode())){
			System.out.println("报备查询返回的商户信息："+rep.getMchInfo());
		}else{
			System.out.println(rep.getRespDesc());
		}
	}
	
	
	/**
	 * 支付宝报备查询
	 * @Description: 
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年11月21日下午4:21:36
	 */
	public static void testMerchAliBbQry(){
		PfPayService payService = new PfPayServiceImpl();
		PfMerchBbQryReq req = new PfMerchBbQryReq(ConfigUtils.AGENT_MERCHANT_NO,"ALIPAY",
				"310440300003771","2088621888397103");
		PfMerchBbQryRep rep = payService.doPfMerchBbQry(req);
		if("0000".equals(rep.getRespCode())){
			System.out.println("报备查询返回的商户信息："+rep.getMchInfo());
		}else{
			System.out.println(rep.getRespDesc());
		}
	}
}
