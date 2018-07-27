package com.yunpay.pfbank.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.yunpay.common.utils.CommonUtil;
import com.yunpay.common.utils.Log;
import com.yunpay.common.utils.ResultEnum.ErrorCode;
import com.yunpay.pfbank.rep.PfBaseRep;
import com.yunpay.pfbank.rep.PfMerchAddRep;
import com.yunpay.pfbank.rep.PfMerchBbQryRep;
import com.yunpay.pfbank.rep.PfMerchBbRep;
import com.yunpay.pfbank.rep.PfMerchBjQryRep;
import com.yunpay.pfbank.rep.PfQueryRep;
import com.yunpay.pfbank.rep.PfScanRep;
import com.yunpay.pfbank.rep.PfWikiConfigRep;
import com.yunpay.pfbank.rep.PfWikiPayRep;
import com.yunpay.pfbank.req.PfMerchBbQryReq;
import com.yunpay.pfbank.req.PfMerchBjQryReq;
import com.yunpay.pfbank.req.PfMerchBjReq;
import com.yunpay.pfbank.req.PfMerchBbReq;
import com.yunpay.pfbank.req.PfQueryReq;
import com.yunpay.pfbank.req.PfRefundReq;
import com.yunpay.pfbank.req.PfPayReq;
import com.yunpay.pfbank.req.PfReverseReq;
import com.yunpay.pfbank.req.PfWikiConfigReq;
import com.yunpay.pfbank.req.PfWikiPayReq;
import com.yunpay.pfbank.service.PfPayService;
import com.yunpay.pfbank.util.ConfigUtils;
import com.yunpay.pfbank.util.HttpsRequest;
import com.yunpay.pfbank.util.SignUtils;


/**
 * 浦发银行支付业务接口实现类
 * 文件名称:     PfPayServiceImpl.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年10月9日下午1:42:27 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年10月9日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
@Service("pfPayService")
public class PfPayServiceImpl implements PfPayService{
	
	//接口版本号
	private static final String VERSION="1.3";
	
	/**
	 * @Description:浦发商户报件
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年11月17日上午10:14:14 
	 * @param pfMerchAddReq
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PfMerchAddRep doPfMerchBj(PfMerchBjReq pfMerchAddReq) {
		List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
        nvps.add(new BasicNameValuePair("requestNo",  new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())));
        nvps.add(new BasicNameValuePair("version", VERSION));
        nvps.add(new BasicNameValuePair("transId", "25"));
        nvps.add(new BasicNameValuePair("agentId", pfMerchAddReq.getAgentId()));
        //商户名称
        nvps.add(new BasicNameValuePair("name", pfMerchAddReq.getName()));
        //商户简称
        nvps.add(new BasicNameValuePair("nameAlias", pfMerchAddReq.getNameAlias()));
        //行业类别码
        nvps.add(new BasicNameValuePair("mccValue", pfMerchAddReq.getMccValue()));
        //法人
        nvps.add(new BasicNameValuePair("legalPerson", pfMerchAddReq.getLegalPerson()));
        //法人身份证
        nvps.add(new BasicNameValuePair("idcardNo", pfMerchAddReq.getIdcardNo()));
        //手机号码
        nvps.add(new BasicNameValuePair("mobile", pfMerchAddReq.getMobile()));
        //邮箱
        nvps.add(new BasicNameValuePair("email", pfMerchAddReq.getEmail()));
        //详细地址
        nvps.add(new BasicNameValuePair("registerAddress",pfMerchAddReq.getRegisterAddress()));
        //社会信用代码
        nvps.add(new BasicNameValuePair("regNo", pfMerchAddReq.getRegNo()));
        //注册资金
        nvps.add(new BasicNameValuePair("regMoney", pfMerchAddReq.getRegMoney()));
        //银行账号
        nvps.add(new BasicNameValuePair("cardNoCipher", pfMerchAddReq.getCardNoCipher()));
        //账户名
        nvps.add(new BasicNameValuePair("cardName", pfMerchAddReq.getCardName()));
        //联行号
        nvps.add(new BasicNameValuePair("cardBankNo", pfMerchAddReq.getCardBankNo()));
        //商户失效时间
        nvps.add(new BasicNameValuePair("expireTime", pfMerchAddReq.getExpireTime()));
        //是否对公账号
        nvps.add(new BasicNameValuePair("isCompay", pfMerchAddReq.getIsCompany()));
        //经营类目
        nvps.add(new BasicNameValuePair("business", pfMerchAddReq.getBusiness()));
        try {
        	nvps.add(new BasicNameValuePair("signature", SignUtils.signData(nvps)));
        	HttpsRequest httpRequest = new HttpsRequest();
			String str = httpRequest.sendPost(ConfigUtils.PF_MERCH_URL, nvps);
			Log.info("------商户报件返回结果："+str);
			if(StringUtils.isNotBlank(str)){
		        Map<String,String> responseMap = (Map<String,String>)JSONObject.parse(str);
		        PfMerchAddRep pfMerchAddRep =(PfMerchAddRep) CommonUtil.convertMap(PfMerchAddRep.class, responseMap);
	            return pfMerchAddRep;
			}
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return new PfMerchAddRep(ErrorCode.CHANNEL_PAY_ERROR.getCode(),ErrorCode.CHANNEL_PAY_ERROR.getDes());
	}
	
	/**
	 * 商户报件查询
	 * @Description:
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年11月21日下午3:54:06 
	 * @param req
	 * @return
	 */
	@Override
	public PfMerchBjQryRep doPfMerchBjQry(PfMerchBjQryReq req) {
		List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
        nvps.add(new BasicNameValuePair("requestNo",  new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())));
        nvps.add(new BasicNameValuePair("version", VERSION));
        nvps.add(new BasicNameValuePair("transId", "30"));
        nvps.add(new BasicNameValuePair("agentId", req.getAgentId()));
        if(StringUtils.isNotBlank(req.getMerNo())){
        	nvps.add(new BasicNameValuePair("merNo", req.getMerNo()));
        }
        if(StringUtils.isNotBlank(req.getName())){
        	nvps.add(new BasicNameValuePair("name", req.getName()));
        }
        if(StringUtils.isNotBlank(req.getNameAlias())){
        	nvps.add(new BasicNameValuePair("nameAlias", req.getNameAlias()));
        }
        try {
        	nvps.add(new BasicNameValuePair("signature", SignUtils.signData(nvps)));
        	HttpsRequest httpRequest = new HttpsRequest();
			String str = httpRequest.sendPost(ConfigUtils.PF_TRANS_URL, nvps);
			System.out.println("------商户报件查询返回结果："+str);
			if(StringUtils.isNotBlank(str)){
		        boolean signFlag = SignUtils.verferSignData(str);
		        if (!signFlag) {
		            System.out.println("验签失败");
		            return new PfMerchBjQryRep(ErrorCode.CHANNEL_SIGN_ERROR.getCode(),ErrorCode.CHANNEL_SIGN_ERROR.getDes());
		        }
		        Map<String,String> responseMap = SignUtils.getMapFromStr(str);
		        PfMerchBjQryRep pfMerchBjQryRep =(PfMerchBjQryRep) CommonUtil.convertMap(PfMerchBjQryRep.class, responseMap);
		        
	            return pfMerchBjQryRep;
			}
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return new PfMerchBjQryRep(ErrorCode.CHANNEL_PAY_ERROR.getCode(),ErrorCode.CHANNEL_PAY_ERROR.getDes());
	}

	

	/**
	 * @Description:浦发商户报备
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年11月20日下午5:29:37 
	 * @param pfMerchBbReq
	 * @return
	 */
	@Override
	public PfMerchBbRep doPfMerchBb(PfMerchBbReq pfMerchBbReq) {
		List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
        nvps.add(new BasicNameValuePair("requestNo",  new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())));
        nvps.add(new BasicNameValuePair("version", VERSION));
        nvps.add(new BasicNameValuePair("transId", "18"));
        nvps.add(new BasicNameValuePair("agentId", pfMerchBbReq.getAgentId()));
        nvps.add(new BasicNameValuePair("payWay", pfMerchBbReq.getPayWay()));//类型 WX 或 ALIPAY 必填
        nvps.add(new BasicNameValuePair("merNo", pfMerchBbReq.getMerNo()));//商户号 必填
        nvps.add(new BasicNameValuePair("subMechantName",pfMerchBbReq.getSubMerchantName()));//商户名称 必填
        nvps.add(new BasicNameValuePair("business", pfMerchBbReq.getBusiness()));//类别编码 必填
        nvps.add(new BasicNameValuePair("subMerchantShortname", pfMerchBbReq.getSubMerchantShorName()));//商户简称 必填
        nvps.add(new BasicNameValuePair("contact", pfMerchBbReq.getContact()));//联系人 必填
        nvps.add(new BasicNameValuePair("contactEmail", pfMerchBbReq.getContactEmail()));//邮件 必填
        nvps.add(new BasicNameValuePair("contactPhone",pfMerchBbReq.getContactPhone()));//手机号 必填
        nvps.add(new BasicNameValuePair("merchantRemark", pfMerchBbReq.getMerchantRemark()));//备注 必填
        nvps.add(new BasicNameValuePair("servicePhone", pfMerchBbReq.getServicePhone()));//联系电话 必填
        
        if("ALIPAY".equals(pfMerchBbReq.getPayWay())){
        	 nvps.add(new BasicNameValuePair("contactInfo", pfMerchBbReq.getContactInfo())); //必填
             nvps.add(new BasicNameValuePair("addressInfo", pfMerchBbReq.getAddressInfo())); //选填
             nvps.add(new BasicNameValuePair("bankCardInfo", pfMerchBbReq.getBankCardInfo()));//选填
             nvps.add(new BasicNameValuePair("businessLicenseType", pfMerchBbReq.getBusinessLicenseType()));//商户证件类型  取值：NATIONAL_LEGAL营业执照，NATIONAL_LEGAL_MERGE营业执照多证合一，INST_GRST_CTF事业单位法人证书 选填
             nvps.add(new BasicNameValuePair("businessLicense", pfMerchBbReq.getBusinessLicense()));//商户证件编码 企业或者个体工商户提供营业执照，事业单位提供事证号 选填
        }
        try {
        	nvps.add(new BasicNameValuePair("signature", SignUtils.signData(nvps)));
        	HttpsRequest httpRequest = new HttpsRequest();
			String str = httpRequest.sendPost(ConfigUtils.PF_TRANS_URL, nvps);
			if(StringUtils.isNotBlank(str)){
				boolean signFlag = SignUtils.verferSignData(str);
		        if (!signFlag) {
		            System.out.println("验签失败");
		            return new PfMerchBbRep(ErrorCode.CHANNEL_SIGN_ERROR.getCode(),ErrorCode.CHANNEL_SIGN_ERROR.getDes());
		        }
		        Map<String,String> responseMap = SignUtils.getMapFromStr(str);
		        PfMerchBbRep pfMerchBbRep =(PfMerchBbRep) CommonUtil.convertMap(PfMerchBbRep.class, responseMap);
	            return pfMerchBbRep;
			}
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return new PfMerchBbRep(ErrorCode.CHANNEL_PAY_ERROR.getCode(),ErrorCode.CHANNEL_PAY_ERROR.getDes());
	}

	/**
	 * @Description:商户报备查询
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年11月21日下午4:20:41 
	 * @param req
	 * @return
	 */
	@Override
	public PfMerchBbQryRep doPfMerchBbQry(PfMerchBbQryReq req) {
		List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
        nvps.add(new BasicNameValuePair("requestNo",  new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())));
        nvps.add(new BasicNameValuePair("version", VERSION));
        nvps.add(new BasicNameValuePair("transId", "19"));
        nvps.add(new BasicNameValuePair("agentId", req.getAgentId()));
        nvps.add(new BasicNameValuePair("payWay", req.getPayWay()));
        nvps.add(new BasicNameValuePair("merNo", req.getMerNo()));
        nvps.add(new BasicNameValuePair("subMchId", req.getSubMchId()));
        try {
        	nvps.add(new BasicNameValuePair("signature", SignUtils.signData(nvps)));
        	HttpsRequest httpRequest = new HttpsRequest();
			String str = httpRequest.sendPost(ConfigUtils.PF_TRANS_URL, nvps);
			System.out.println("------商户报备查询返回结果："+str);
			if(StringUtils.isNotBlank(str)){
		        boolean signFlag = SignUtils.verferSignData(str);
		        if (!signFlag) {
		            System.out.println("验签失败");
		            return new PfMerchBbQryRep(ErrorCode.CHANNEL_SIGN_ERROR.getCode(),ErrorCode.CHANNEL_SIGN_ERROR.getDes());
		        }
		        Map<String,String> responseMap = SignUtils.getMapFromStr(str);
		        PfMerchBbQryRep pfMerchBbQryRep =(PfMerchBbQryRep) CommonUtil.convertMap(PfMerchBbQryRep.class, responseMap);
		        
	            return pfMerchBbQryRep;
			}
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return new PfMerchBbQryRep(ErrorCode.CHANNEL_PAY_ERROR.getCode(),ErrorCode.CHANNEL_PAY_ERROR.getDes());
	}

	
	
	/**
	 * @Description:浦发支付通道条码支付(微信&支付宝)
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年10月9日下午4:07:48 
	 * @param barReq
	 * @return
	 */
	@Override
	public PfBaseRep doPfBarPay(PfPayReq barReq) {
		List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
        nvps.add(new BasicNameValuePair("requestNo", new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())));
        nvps.add(new BasicNameValuePair("version", VERSION));
        nvps.add(new BasicNameValuePair("productId", barReq.getProductId()));
        nvps.add(new BasicNameValuePair("transId", barReq.getTransId()));
        nvps.add(new BasicNameValuePair("agentId", barReq.getAgentId()));
        nvps.add(new BasicNameValuePair("merNo", barReq.getMerNo()));
        nvps.add(new BasicNameValuePair("subMchId", barReq.getSubMchId()));//二级编号，由报备接口获取
        nvps.add(new BasicNameValuePair("orderDate", barReq.getOrderDate()));
        nvps.add(new BasicNameValuePair("orderNo", barReq.getOrderNo()));
        nvps.add(new BasicNameValuePair("clientIp", barReq.getClientIp()));
        nvps.add(new BasicNameValuePair("autoCode", barReq.getAutoCode()));//条码编码
        nvps.add(new BasicNameValuePair("returnUrl", barReq.getReturnUrl()));
        nvps.add(new BasicNameValuePair("notifyUrl", barReq.getNotifyUrl()));
        nvps.add(new BasicNameValuePair("transAmt", barReq.getTransAmt()));//单位分
        nvps.add(new BasicNameValuePair("commodityName", barReq.getCommodityName()));
        try {
			nvps.add(new BasicNameValuePair("signature", SignUtils.signData(nvps)));
			HttpsRequest httpRequest = new HttpsRequest();
			String str = httpRequest.sendPost(ConfigUtils.PF_TRANS_URL, nvps);
			if(StringUtils.isNotBlank(str)){
				boolean signFlag = SignUtils.verferSignData(str);
		        if (!signFlag) {
		            System.out.println("验签失败");
		            return new PfBaseRep(ErrorCode.CHANNEL_SIGN_ERROR.getCode(),ErrorCode.CHANNEL_SIGN_ERROR.getDes());
		        }
		        Map<String,String> responseMap = SignUtils.getMapFromStr(str);
	            PfBaseRep pfBaseRep =(PfBaseRep) CommonUtil.convertMap(PfBaseRep.class, responseMap);
	            return pfBaseRep;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return new PfBaseRep(ErrorCode.CHANNEL_PAY_ERROR.getCode(),ErrorCode.CHANNEL_PAY_ERROR.getDes());
	}

	/**
	 * @Description:浦发支付通道扫码支付(微信&支付宝)
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年10月9日下午4:10:00 
	 * @param scanReq
	 * @return
	 */
	@Override
	public PfScanRep doPfScanPay(PfPayReq scanReq) {
		List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
        nvps.add(new BasicNameValuePair("requestNo", new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())));
        nvps.add(new BasicNameValuePair("version", VERSION));
        nvps.add(new BasicNameValuePair("productId", scanReq.getProductId()));
        nvps.add(new BasicNameValuePair("transId", scanReq.getTransId()));
        nvps.add(new BasicNameValuePair("agentId", scanReq.getAgentId()));
        nvps.add(new BasicNameValuePair("merNo", scanReq.getMerNo()));
        nvps.add(new BasicNameValuePair("subMchId", scanReq.getSubMchId()));//二级编号，由报备接口获取
        nvps.add(new BasicNameValuePair("orderDate", scanReq.getOrderDate()));
        nvps.add(new BasicNameValuePair("orderNo", scanReq.getOrderNo()));
        nvps.add(new BasicNameValuePair("clientIp", scanReq.getClientIp()));
       
        nvps.add(new BasicNameValuePair("returnUrl", scanReq.getReturnUrl()));
        nvps.add(new BasicNameValuePair("notifyUrl", scanReq.getNotifyUrl()));
        nvps.add(new BasicNameValuePair("transAmt", scanReq.getTransAmt()));//单位分
        nvps.add(new BasicNameValuePair("commodityName", scanReq.getCommodityName()));
        try {
			nvps.add(new BasicNameValuePair("signature", SignUtils.signData(nvps)));
			HttpsRequest httpRequest = new HttpsRequest();
			String str = httpRequest.sendPost(ConfigUtils.PF_TRANS_URL, nvps);
			if(StringUtils.isNotBlank(str)){
				boolean signFlag = SignUtils.verferSignData(str);
		        if (!signFlag) {
		            System.out.println("验签失败");
		            return new PfScanRep(ErrorCode.CHANNEL_SIGN_ERROR.getCode(),ErrorCode.CHANNEL_SIGN_ERROR.getDes());
		        }
		        Map<String,String> responseMap = SignUtils.getMapFromStr(str);
		        PfScanRep pfScanRep =(PfScanRep) CommonUtil.convertMap(PfScanRep.class, responseMap);
	            return pfScanRep;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return new PfScanRep(ErrorCode.CHANNEL_PAY_ERROR.getCode(),ErrorCode.CHANNEL_PAY_ERROR.getDes());
	}
	
	/**
	 * @Description:微信公众号配置
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年10月30日上午9:29:56 
	 * @param pfWikiConfigReq
	 * @return
	 */
	@Override
	public PfWikiConfigRep doPfWikiConfig(PfWikiConfigReq pfWikiConfigReq) {
		List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
		nvps.add(new BasicNameValuePair("requestNo", new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())));
        nvps.add(new BasicNameValuePair("version", VERSION));
        nvps.add(new BasicNameValuePair("transId", "28"));
        nvps.add(new BasicNameValuePair("agentId", pfWikiConfigReq.getAgentId()));
        nvps.add(new BasicNameValuePair("payWay", pfWikiConfigReq.getPayWay()));
        nvps.add(new BasicNameValuePair("merNo", pfWikiConfigReq.getMerNo()));
        nvps.add(new BasicNameValuePair("subMchId", pfWikiConfigReq.getSubMchId()));
        if(pfWikiConfigReq.getConfigType()==1){
        	nvps.add(new BasicNameValuePair("jsApiPath", pfWikiConfigReq.getJsApiPath()));
        }else if(pfWikiConfigReq.getConfigType()==2){
        	nvps.add(new BasicNameValuePair("subAppId", pfWikiConfigReq.getSubAppId()));
        }else{
        	nvps.add(new BasicNameValuePair("subScribeAppid", pfWikiConfigReq.getSubScribeAppid()));
        }
        try {
        	nvps.add(new BasicNameValuePair("signature", SignUtils.signData(nvps)));
        	HttpsRequest httpRequest = new HttpsRequest();
			String str = httpRequest.sendPost(ConfigUtils.PF_TRANS_URL, nvps);
			if(StringUtils.isNotBlank(str)){
				boolean signFlag = SignUtils.verferSignData(str);
		        if (!signFlag) {
		            System.out.println("验签失败");
		            return new PfWikiConfigRep(ErrorCode.CHANNEL_SIGN_ERROR.getCode(),ErrorCode.CHANNEL_SIGN_ERROR.getDes());
		        }
		        Map<String,String> responseMap = SignUtils.getMapFromStr(str);
		        PfWikiConfigRep wikiConfigRep =(PfWikiConfigRep) CommonUtil.convertMap(PfWikiConfigRep.class, responseMap);
	            return wikiConfigRep;
			}
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return new PfWikiConfigRep(ErrorCode.CHANNEL_PAY_ERROR.getCode(),ErrorCode.CHANNEL_PAY_ERROR.getDes());
	}

	/**
	 * 微信配置查询接口
	 * @Description:
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年11月17日上午9:31:32 
	 * @param pfWikiConfigReq
	 * @return
	 */
	@Override
	public PfWikiConfigRep doPfWikiConfigQuery(PfWikiConfigReq pfWikiConfigReq) {
		List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
		nvps.add(new BasicNameValuePair("requestNo", new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())));
        nvps.add(new BasicNameValuePair("version", VERSION));
        nvps.add(new BasicNameValuePair("transId", "27"));
        nvps.add(new BasicNameValuePair("agentId", pfWikiConfigReq.getAgentId()));
        nvps.add(new BasicNameValuePair("payWay", pfWikiConfigReq.getPayWay()));
        nvps.add(new BasicNameValuePair("merNo", pfWikiConfigReq.getMerNo()));
        nvps.add(new BasicNameValuePair("subMchId", pfWikiConfigReq.getSubMchId()));
        try {
        	nvps.add(new BasicNameValuePair("signature", SignUtils.signData(nvps)));
        	HttpsRequest httpRequest = new HttpsRequest();
			String str = httpRequest.sendPost(ConfigUtils.PF_TRANS_URL, nvps);
			if(StringUtils.isNotBlank(str)){
				boolean signFlag = SignUtils.verferSignData(str);
		        if (!signFlag) {
		            System.out.println("验签失败");
		            return new PfWikiConfigRep(ErrorCode.CHANNEL_SIGN_ERROR.getCode(),ErrorCode.CHANNEL_SIGN_ERROR.getDes());
		        }
		        Map<String,String> responseMap = SignUtils.getMapFromStr(str);
		        PfWikiConfigRep wikiConfigRep =(PfWikiConfigRep) CommonUtil.convertMap(PfWikiConfigRep.class, responseMap);
	            return wikiConfigRep;
			}
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return new PfWikiConfigRep(ErrorCode.CHANNEL_PAY_ERROR.getCode(),ErrorCode.CHANNEL_PAY_ERROR.getDes());
	}

	
	/**
	 * @Description:微信公众号支付
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年11月21日上午9:48:42 
	 * @param pfWikiPayReq
	 * @return
	 */
	@Override
	public PfWikiPayRep doPfWikiPay(PfWikiPayReq pfWikiPayReq) {
        List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
        nvps.add(new BasicNameValuePair("requestNo", new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())));
        nvps.add(new BasicNameValuePair("version", VERSION));
        nvps.add(new BasicNameValuePair("productId", pfWikiPayReq.getProductId()));
        nvps.add(new BasicNameValuePair("transId", pfWikiPayReq.getTransId()));
        nvps.add(new BasicNameValuePair("agentId", pfWikiPayReq.getAgentId()));
        nvps.add(new BasicNameValuePair("merNo", pfWikiPayReq.getMerNo()));//商户号
        nvps.add(new BasicNameValuePair("subMchId", pfWikiPayReq.getSubMchId()));
        nvps.add(new BasicNameValuePair("subOpenId", pfWikiPayReq.getSubOpenId()));
        nvps.add(new BasicNameValuePair("orderDate",pfWikiPayReq.getOrderDate()));
        nvps.add(new BasicNameValuePair("orderNo", pfWikiPayReq.getOrderNo()));
        nvps.add(new BasicNameValuePair("clientIp", pfWikiPayReq.getClientIp()));
        nvps.add(new BasicNameValuePair("returnUrl", pfWikiPayReq.getReturnUrl()));
        nvps.add(new BasicNameValuePair("notifyUrl", pfWikiPayReq.getNotifyUrl()));
        nvps.add(new BasicNameValuePair("transAmt", pfWikiPayReq.getTransAmt()));//交易金额：单位分
        nvps.add(new BasicNameValuePair("commodityName", pfWikiPayReq.getCommodityName()));
        try {
			nvps.add(new BasicNameValuePair("signature", SignUtils.signData(nvps)));
			HttpsRequest httpRequest = new HttpsRequest();
			String str = httpRequest.sendPost(ConfigUtils.PF_TRANS_URL, nvps);
			if(StringUtils.isNotBlank(str)){
				boolean signFlag = SignUtils.verferSignData(str);
		        if (!signFlag) {
		            System.out.println("验签失败");
		            return new PfWikiPayRep(ErrorCode.CHANNEL_SIGN_ERROR.getCode(),ErrorCode.CHANNEL_SIGN_ERROR.getDes());
		        }
		        Map<String,String> responseMap = SignUtils.getMapFromStr(str);
		        PfWikiPayRep pfWikiPayRep =(PfWikiPayRep) CommonUtil.convertMap(PfWikiPayRep.class, responseMap);
	            return pfWikiPayRep;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return new PfWikiPayRep(ErrorCode.CHANNEL_PAY_ERROR.getCode(),ErrorCode.CHANNEL_PAY_ERROR.getDes());
	}
	

	/**
	 * @Description:订单查询
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年10月11日下午8:00:14 
	 * @param queryReq
	 * @return
	 */
	@Override
	public PfQueryRep doPfOrderQuery(PfQueryReq queryReq) {
		List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
		nvps.add(new BasicNameValuePair("requestNo", new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())));
        nvps.add(new BasicNameValuePair("version", VERSION));
        nvps.add(new BasicNameValuePair("transId", "04"));
        nvps.add(new BasicNameValuePair("merNo", queryReq.getMerNo()));
        nvps.add(new BasicNameValuePair("orderDate", queryReq.getOrderDate()));
        nvps.add(new BasicNameValuePair("orderNo", queryReq.getOrderNo()));
        nvps.add(new BasicNameValuePair("agentId", queryReq.getAgentId()));
        try {
        	nvps.add(new BasicNameValuePair("signature", SignUtils.signData(nvps)));
        	HttpsRequest httpRequest = new HttpsRequest();
			String str = httpRequest.sendPost(ConfigUtils.PF_TRANS_URL, nvps);
			if(StringUtils.isNotBlank(str)){
				boolean signFlag = SignUtils.verferSignData(str);
		        if (!signFlag) {
		            System.out.println("验签失败");
		            return new PfQueryRep(ErrorCode.CHANNEL_SIGN_ERROR.getCode(),ErrorCode.CHANNEL_SIGN_ERROR.getDes());
		        }
		        Map<String,String> responseMap = SignUtils.getMapFromStr(str);
		        PfQueryRep queryRep =(PfQueryRep) CommonUtil.convertMap(PfQueryRep.class, responseMap);
	            return queryRep;
			}
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return new PfQueryRep(ErrorCode.CHANNEL_PAY_ERROR.getCode(),ErrorCode.CHANNEL_PAY_ERROR.getDes());
	}

	/**
	 * @Description:退款申请
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年10月12日上午9:44:54 
	 * @param refundReq
	 * @return
	 */
	@Override
	public PfBaseRep doPfOrderRefund(PfRefundReq refundReq) {
		List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
		nvps.add(new BasicNameValuePair("requestNo", new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())));
        nvps.add(new BasicNameValuePair("version", VERSION));
        nvps.add(new BasicNameValuePair("transId", "02"));
        nvps.add(new BasicNameValuePair("merNo", refundReq.getMerNo()));
        nvps.add(new BasicNameValuePair("agentId", refundReq.getAgentId()));
        nvps.add(new BasicNameValuePair("orderDate", refundReq.getOrderDate()));
        nvps.add(new BasicNameValuePair("orderNo", refundReq.getOrderNo()));
        nvps.add(new BasicNameValuePair("origOrderDate", refundReq.getOrigOrderDate()));
        nvps.add(new BasicNameValuePair("origOrderNo", refundReq.getOrigOrderNo()));
        
        nvps.add(new BasicNameValuePair("returnUrl", refundReq.getReturnUrl()));
        nvps.add(new BasicNameValuePair("notifyUrl", refundReq.getNotifyUrl()));
        nvps.add(new BasicNameValuePair("transAmt", refundReq.getTransAmt()));//单位分
        nvps.add(new BasicNameValuePair("refundReason", refundReq.getRefundReson()));
        try {
        	nvps.add(new BasicNameValuePair("signature", SignUtils.signData(nvps)));
        	HttpsRequest httpRequest = new HttpsRequest();
			String str = httpRequest.sendPost(ConfigUtils.PF_TRANS_URL, nvps);
			if(StringUtils.isNotBlank(str)){
				boolean signFlag = SignUtils.verferSignData(str);
		        if (!signFlag) {
		            System.out.println("验签失败");
		            return new PfQueryRep(ErrorCode.CHANNEL_SIGN_ERROR.getCode(),ErrorCode.CHANNEL_SIGN_ERROR.getDes());
		        }
		        Map<String,String> responseMap = SignUtils.getMapFromStr(str);
		        PfBaseRep refundRep =(PfBaseRep) CommonUtil.convertMap(PfBaseRep.class, responseMap);
	            return refundRep;
			}
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return new PfQueryRep(ErrorCode.CHANNEL_PAY_ERROR.getCode(),ErrorCode.CHANNEL_PAY_ERROR.getDes());
	}

	/**
	 * @Description:订单撤销
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年10月12日上午10:01:11 
	 * @param reverseReq
	 * @return
	 */
	@Override
	public PfBaseRep doPfOrderReverse(PfReverseReq reverseReq) {
		List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
		nvps.add(new BasicNameValuePair("requestNo", new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())));
        nvps.add(new BasicNameValuePair("version", VERSION));
        nvps.add(new BasicNameValuePair("transId", "03"));
        nvps.add(new BasicNameValuePair("merNo", reverseReq.getMerNo()));
        nvps.add(new BasicNameValuePair("agentId", reverseReq.getAgentId()));
        nvps.add(new BasicNameValuePair("orderDate", reverseReq.getOrderDate()));
        nvps.add(new BasicNameValuePair("orderNo", reverseReq.getOrderNo()));
        nvps.add(new BasicNameValuePair("origOrderNo", reverseReq.getOrigOrderNo()));
        nvps.add(new BasicNameValuePair("returnUrl", reverseReq.getReturnUrl()));
        nvps.add(new BasicNameValuePair("notifyUrl", reverseReq.getNotifyUrl()));
        nvps.add(new BasicNameValuePair("transAmt", reverseReq.getTransAmt()));//单位分
        try {
        	nvps.add(new BasicNameValuePair("signature", SignUtils.signData(nvps)));
        	HttpsRequest httpRequest = new HttpsRequest();
			String str = httpRequest.sendPost(ConfigUtils.PF_TRANS_URL, nvps);
			if(StringUtils.isNotBlank(str)){
				boolean signFlag = SignUtils.verferSignData(str);
		        if (!signFlag) {
		            System.out.println("验签失败");
		            return new PfQueryRep(ErrorCode.CHANNEL_SIGN_ERROR.getCode(),ErrorCode.CHANNEL_SIGN_ERROR.getDes());
		        }
		        Map<String,String> responseMap = SignUtils.getMapFromStr(str);
		        PfBaseRep reverseRep =(PfBaseRep) CommonUtil.convertMap(PfBaseRep.class, responseMap);
	            return reverseRep;
			}
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return new PfQueryRep(ErrorCode.CHANNEL_PAY_ERROR.getCode(),ErrorCode.CHANNEL_PAY_ERROR.getDes());
	}
}
