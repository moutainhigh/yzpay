package com.yunpay.serv.service.pay.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.yunpay.common.utils.Log;
import com.yunpay.common.utils.ResultEnum.ErrorCode;
import com.yunpay.pfbank.rep.PfMerchAddRep;
import com.yunpay.pfbank.rep.PfMerchBbRep;
import com.yunpay.pfbank.rep.PfWikiConfigRep;
import com.yunpay.pfbank.req.PfMerchBbReq;
import com.yunpay.pfbank.req.PfMerchBjReq;
import com.yunpay.pfbank.req.PfWikiConfigReq;
import com.yunpay.pfbank.service.PfPayService;
import com.yunpay.pfbank.util.ConfigUtils;
import com.yunpay.serv.rep.Message;
import com.yunpay.serv.req.pay.PfMerBbReqDto;
import com.yunpay.serv.req.pay.PfMerBjReqDto;
import com.yunpay.serv.req.pay.PfWikiReqDto;
import com.yunpay.serv.service.pay.PfMerSetService;

/**
 * 文件名称:    PfMerSetServiceImpl.java
 * 内容摘要:    浦发渠道商户报件等相关业务处理类（商户报件、商户报备、微信配置等）
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年11月22日上午10:54:24 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年11月22日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
@Service("pfMerSetService")
public class PfMerSetServiceImpl implements PfMerSetService{

	@Autowired
	private PfPayService pfPayService;
	
	/**
	 * @Description:商户保件
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年11月22日上午10:54:44 
	 * @param reqDto
	 * @return
	 */
	@Override
	public Message doMerBaojian(PfMerBjReqDto reqDto) {
		//构造请求参数
		PfMerchBjReq req  = new PfMerchBjReq(ConfigUtils.AGENT_MERCHANT_NO,reqDto.getMerchant_name(),reqDto.getName_alias(),
				reqDto.getMcc_value(),reqDto.getLegal_person(),reqDto.getIdcard_no(),reqDto.getMobile(),
				reqDto.getEmail(),reqDto.getReg_no(),reqDto.getReg_money(),reqDto.getRegister_address(),
				reqDto.getCard_no_cipher(),reqDto.getCard_name(),reqDto.getCard_bank_no(),reqDto.getExpire_time(),
				reqDto.getIs_company(),reqDto.getBusiness());
		Message msg = null;
		try {
			PfMerchAddRep rep = pfPayService.doPfMerchBj(req);
			if("0000".equals(rep.getRespCode())){
				//返回浦发渠道商户号
				JSONObject ret = new JSONObject();
				ret.put("mer_no", rep.getMerNo());
				msg = new Message(ret);
			}else{
				msg = new Message(rep.getRespCode(),rep.getRespDesc());
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			Log.error(e.toString());
			return new Message(ErrorCode.SYSTEM_EXCEPTION.getCode(), ErrorCode.SYSTEM_EXCEPTION.getDes());
		}
		return msg;
	}

	/**
	 * @Description:商户报备
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年11月22日上午10:54:44 
	 * @param reqDto
	 * @return
	 */
	@Override
	public Message doMerBaobei(PfMerBbReqDto reqDto) {
		//构造微信支付宝报备公共参数
		PfMerchBbReq req= new PfMerchBbReq(ConfigUtils.AGENT_MERCHANT_NO,reqDto.getMer_no(),
				reqDto.getMerchant_name(),reqDto.getBusiness(),
			reqDto.getName_alias(),reqDto.getContact(),reqDto.getContact_phone(),
			reqDto.getContact_email(),reqDto.getMerchant_remark(),reqDto.getService_phone());
		//构造支付宝报备专属参数
		if("ALIPAY".equals(reqDto.getPay_way())){
			req.setContactInfo(reqDto.getLegal_person(),reqDto.getContact_email(),reqDto.getName_type(), reqDto.getIdcard_no());
			req.setBankCardInfo(reqDto.getCard_no_cipher(), reqDto.getCard_name());
			req.setAddressInfo(reqDto.getProvince_code(), reqDto.getCity_code(), reqDto.getDistrict_code(), 
					reqDto.getAddress());
		}
		Message msg = null;
		try {
			PfMerchBbRep rep = pfPayService.doPfMerchBb(req);
			if("0000".equals(rep.getRespCode())){
				//返回支付宝或微信渠道商户号
				JSONObject ret = new JSONObject();
				ret.put("sub_mch_id", rep.getSubMchId());
				msg = new Message(ret);
			}else{
				msg = new Message(rep.getRespCode(),rep.getRespDesc());
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			Log.error(e.toString());
			return new Message(ErrorCode.SYSTEM_EXCEPTION.getCode(), ErrorCode.SYSTEM_EXCEPTION.getDes());
		}
		return msg;
	}

	/**
	 * @Description:微信相关设置
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年11月22日上午11:37:23 
	 * @param reqDto
	 * @return
	 */
	@Override
	public Message doWikiSet(PfWikiReqDto reqDto) {
		PfWikiConfigReq req = new PfWikiConfigReq(ConfigUtils.AGENT_MERCHANT_NO,
				reqDto.getMer_no(),reqDto.getSub_mch_id(),reqDto.getConfig_type(),reqDto.getWiki_config());
		Message msg = null;
		try {
			PfWikiConfigRep rep = pfPayService.doPfWikiConfig(req);
			if("0000".equals(rep.getRespCode())){
				//返回支付宝或微信渠道商户号
				JSONObject ret = new JSONObject();
				if(reqDto.getConfig_type()==1){
					ret.put("wiki_config", rep.getJsApiPath());
				}else if(reqDto.getConfig_type()==2){
					ret.put("wiki_config", rep.getSubAppId());
				}else{
					ret.put("wiki_config", rep.getSubScribeAppid());
				}
				msg = new Message(ret);
			}else{
				msg = new Message(rep.getRespCode(),rep.getRespDesc());
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			Log.error(e.toString());
			return new Message(ErrorCode.SYSTEM_EXCEPTION.getCode(), ErrorCode.SYSTEM_EXCEPTION.getDes());
		}
		return msg;
	}

}
