package com.yunpay.sdk.ctrl;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.yunpay.common.exception.YunPayException;
import com.yunpay.common.utils.CommonUtil;
import com.yunpay.common.utils.EnumUtil.MembercardStatus;
import com.yunpay.common.utils.Log;
import com.yunpay.common.utils.MD5;
import com.yunpay.common.utils.ResultEnum.ErrorCode;
import com.yunpay.common.utils.ResultEnum.ResultCode;
import com.yunpay.serv.model.Membercard;
import com.yunpay.serv.model.Merchant;
import com.yunpay.serv.rep.Message;
import com.yunpay.serv.rep.member.MemberQueryRepDto;
import com.yunpay.serv.req.member.MemActivateReqDto;
import com.yunpay.serv.req.member.MemCardUpdReqDto;
import com.yunpay.serv.req.member.MemQrcodeReqDto;
import com.yunpay.serv.req.member.MemberCardReqDto;
import com.yunpay.serv.req.member.MemberChargeReqDto;
import com.yunpay.serv.req.member.MemberConsumReqDto;
import com.yunpay.serv.req.member.MemberQueryReqDto;
import com.yunpay.serv.service.busi.MembercardService;
import com.yunpay.serv.service.busi.MerchantService;
import com.yunpay.serv.service.member.MemberCardService;
import com.yunpay.serv.service.member.MemberUserService;
import com.yunpay.serv.validate.DataValidateUtil;
import com.yunpay.serv.validate.exception.ValidateException;

/**
 * 会员卡外部接口服务类
 * 文件名称:     MemberCardCtrl.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年8月28日上午10:23:05 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年8月28日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
@RestController
public class MemberCardCtrl extends BaseCtrl{
	
	@Autowired
	private MemberCardService memberCardService;
	@Autowired
	private MerchantService merchantService;
	@Autowired
	private MembercardService membercardService;
	@Autowired
	private MemberUserService memberUserService;
	
	/**
	 * @Description: 创建会员卡
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月28日上午10:24:33 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/memcard/create")
	public Object doCreateCard(@RequestBody String recvParam){
		Message message =null;
		try {
			recvParam = URLDecoder.decode(recvParam, "utf-8");
			Log.info("创建会员卡入口,接收参数："+recvParam);
			MemberCardReqDto memberCard = JSONObject.parseObject(recvParam, MemberCardReqDto.class);
			if(!DataValidateUtil.valid(memberCard)){
				//基础信息验证不通过
				return new Message(ErrorCode.PARAM_FORMAT_ERROR.getCode(),ErrorCode.PARAM_FORMAT_ERROR.getDes());
			}
			if(memberCard.getBonus_rule()!=null){
				//积分相关参数验证不通过
				if(!DataValidateUtil.valid(memberCard.getBonus_rule())){
					return new Message(ErrorCode.PARAM_FORMAT_ERROR.getCode(),ErrorCode.PARAM_FORMAT_ERROR.getDes());
				}
			}
			message = memberCardService.createMemberCard(memberCard);
		} catch (ValidateException e) {
			message = new Message(e.getErrCode(),e.getErrMsg());
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			message = new Message(ErrorCode.PARAM_FORMAT_ERROR.getCode(),ErrorCode.PARAM_FORMAT_ERROR.getDes());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			message= new Message(ErrorCode.PARAM_FORMAT_ERROR.getCode(),ErrorCode.PARAM_FORMAT_ERROR.getDes());
		}catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			message= new Message(ErrorCode.PARAM_FORMAT_ERROR.getCode(),ErrorCode.PARAM_FORMAT_ERROR.getDes());
		}
		return message;
	}
	
	
	/**
	 * @Description: 修改会员卡
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月28日上午10:24:33 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/memcard/update")
	public Object doUpdateCard(@RequestBody String recvParam){
		Message message =null;
		try {
			recvParam = URLDecoder.decode(recvParam, "utf-8");
			Log.info("修改会员卡入口,接收参数："+recvParam);
			MemCardUpdReqDto memberCard = JSONObject.parseObject(recvParam, MemCardUpdReqDto.class);
			if(!DataValidateUtil.valid(memberCard)){
				//基础信息验证不通过
				return new Message(ErrorCode.PARAM_FORMAT_ERROR.getCode(),ErrorCode.PARAM_FORMAT_ERROR.getDes());
			}
			if(memberCard.getBonus_rule()!=null){
				//积分相关参数验证不通过
				if(!DataValidateUtil.valid(memberCard.getBonus_rule())){
					return new Message(ErrorCode.PARAM_FORMAT_ERROR.getCode(),ErrorCode.PARAM_FORMAT_ERROR.getDes());
				}
			}
			message = memberCardService.updMemberCard(memberCard);
		} catch (ValidateException e) {
			message = new Message(e.getErrCode(),e.getErrMsg());
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			message = new Message(ErrorCode.PARAM_FORMAT_ERROR.getCode(),ErrorCode.PARAM_FORMAT_ERROR.getDes());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			message= new Message(ErrorCode.PARAM_FORMAT_ERROR.getCode(),ErrorCode.PARAM_FORMAT_ERROR.getDes());
		}catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			message= new Message(ErrorCode.PARAM_FORMAT_ERROR.getCode(),ErrorCode.PARAM_FORMAT_ERROR.getDes());
		}
		return message;
	}
	
	
	/**
	 * @Description: 设置会员卡激活参数
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月28日上午10:24:33 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/memcard/activate/set")
	public Object doActivateParamSet(@RequestBody String recvParam){
		Message message =null;
		Log.info("设置会员卡激活参数" + recvParam);
		try {
			MemActivateReqDto memActivateReq = JSONObject.parseObject(recvParam, MemActivateReqDto.class);
			if(!DataValidateUtil.valid(memActivateReq)){
				//基础信息验证不通过
				return new Message(ErrorCode.PARAM_FORMAT_ERROR.getCode(),ErrorCode.PARAM_FORMAT_ERROR.getDes());
			}
			message = memberCardService.createActivateParam(memActivateReq);
		} catch (ValidateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			message = new Message(e.getErrCode(),e.getErrMsg());
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			message = new Message(ErrorCode.PARAM_FORMAT_ERROR.getCode(),ErrorCode.PARAM_FORMAT_ERROR.getDes());
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			message= new Message(ErrorCode.PARAM_FORMAT_ERROR.getCode(),ErrorCode.PARAM_FORMAT_ERROR.getDes());
		}
		return message;
	}
	
	/**
	 * @Description: 获取会员卡领取二维码
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月5日下午3:50:51 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/member/qrcode")
	public Object doCreateQrcode(HttpServletRequest request, HttpServletResponse response){
		Map<String,String> reqParamMap = this.getRequestParams(request);
		Log.info("获取会员卡领取二维码" + reqParamMap);
		try{
			MemQrcodeReqDto memQrcodeReq = (MemQrcodeReqDto)CommonUtil.convertMap(MemQrcodeReqDto.class,reqParamMap);
			if(!DataValidateUtil.valid(memQrcodeReq)){
				//基础信息验证不通过
				return new Message(ErrorCode.PARAM_FORMAT_ERROR.getCode(),ErrorCode.PARAM_FORMAT_ERROR.getDes());
			}
			return memberCardService.qrCodeCreate(memQrcodeReq);
		}catch (ValidateException e) {
			return new Message(e.getErrCode(),e.getErrMsg());
		}catch(Exception e){
			e.printStackTrace();
			return new Message(ResultCode.EXCEPTION.getCode(),ResultCode.EXCEPTION.getDes());
		}
	}
	
	/**
	 * @Description: 会员信息查询
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月28日下午1:41:37 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/member/query")
	public Object doMemberQuery(HttpServletRequest request, HttpServletResponse response){
		Map<String,String> reqParamMap = this.getRequestParams(request);
		Log.info("会员信息查询请求参数：" + reqParamMap);
		try{
			MemberQueryReqDto memberQueryReq = (MemberQueryReqDto)CommonUtil.convertMap(
					MemberQueryReqDto.class, reqParamMap);
			if(!DataValidateUtil.valid(memberQueryReq)){
				//基础信息验证不通过
				return new Message(ErrorCode.PARAM_FORMAT_ERROR.getCode(),ErrorCode.PARAM_FORMAT_ERROR.getDes());
			}
			//获取商户信息
			Merchant merchant = merchantService.queryMerchInfo(memberQueryReq.getMerchant_num());
			if (merchant == null) {
				return new Message(ErrorCode.MERCHANT_NOT_EXIST.getCode(), ErrorCode.MERCHANT_NOT_EXIST.getDes());			
			}
			//签名验证
			if(!MD5.verify(reqParamMap, memberQueryReq.getSign(), merchant.getMd5Key(), "utf-8")){
				return new Message(ErrorCode.PARAM_SIGN_ERROR.getCode(),ErrorCode.PARAM_SIGN_ERROR.getDes());
			}
			//查找会员信息
			Membercard membercard = membercardService.queryMembercardByMerNo(
					memberQueryReq.getMerchant_num(), memberQueryReq.getMember_card_code());
			if(membercard==null){
				//会员不存在提示
				return new Message(ErrorCode.MEMBER_NOT_EXIST.getCode(), ErrorCode.MEMBER_NOT_EXIST.getDes());
			}
			return new Message(new MemberQueryRepDto(membercard));
		}catch (ValidateException e) {
			return new Message(e.getErrCode(),e.getErrMsg());
		}catch(Exception e){
			e.printStackTrace();
			return new Message(ResultCode.EXCEPTION.getCode(),ResultCode.EXCEPTION.getDes());
		}
	}
	
	
	/**
	 * @Description: 会员卡充值
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月5日下午3:50:51 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/member/recharge")
	public Object doMemberCharge(HttpServletRequest request, HttpServletResponse response){
		Map<String,String> reqParamMap = this.getRequestParams(request);
		Log.info("会员卡充值请求参数：" + reqParamMap);
		try{
			MemberChargeReqDto memberChargeReq = (MemberChargeReqDto)CommonUtil.convertMap(
					MemberChargeReqDto.class,reqParamMap);
			if(!DataValidateUtil.valid(memberChargeReq)){
				//基础信息验证不通过
				return new Message(ErrorCode.PARAM_FORMAT_ERROR.getCode(),ErrorCode.PARAM_FORMAT_ERROR.getDes());
			}
			//获取商户信息
			Merchant merchant = merchantService.queryMerchInfo(memberChargeReq.getMerchant_num());
			if (merchant == null) {
				return new Message(ErrorCode.MERCHANT_NOT_EXIST.getCode(), ErrorCode.MERCHANT_NOT_EXIST.getDes());			
			}
			//签名验证
			if(!MD5.verify(reqParamMap, memberChargeReq.getSign(), merchant.getMd5Key(), "utf-8")){
				return new Message(ErrorCode.PARAM_SIGN_ERROR.getCode(),ErrorCode.PARAM_SIGN_ERROR.getDes());
			}
			//查找会员信息
			Membercard membercard = membercardService.queryMembercardByMerNo(
					memberChargeReq.getMerchant_num(), memberChargeReq.getMember_card_code());
			if(membercard==null){
				//会员不存在提示
				return new Message(ErrorCode.MEMBER_NOT_EXIST.getCode(), ErrorCode.MEMBER_NOT_EXIST.getDes());
			}
			if(membercard.getStatus()!=MembercardStatus.ACTIVE.getValue()){
				//会员状态处于非激活状态
				return new Message(ErrorCode.MEMBER_STATUS_ERR.getCode(), ErrorCode.MEMBER_STATUS_ERR.getDes());
			}
			return memberUserService.memberCharge(memberChargeReq,membercard);
		}catch (ValidateException e) {
			return new Message(e.getErrCode(),e.getErrMsg());
		}catch(YunPayException e){
			return new Message(e.getErrCode(),e.getErrMsg());
		}catch(Exception e){
			e.printStackTrace();
			return new Message(ResultCode.EXCEPTION.getCode(),ResultCode.EXCEPTION.getDes());
		}
	}
	
	/**
	 * @Description: 会员卡消费
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月5日下午3:50:51 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/member/consum")
	public Object doMemberConsum(HttpServletRequest request, HttpServletResponse response){
		Map<String,String> reqParamMap = this.getRequestParams(request);
		Log.info("会员卡消费请求参数：" + reqParamMap);
		try{
			MemberConsumReqDto memberConsumReq = (MemberConsumReqDto)CommonUtil.convertMap(
					MemberConsumReqDto.class,reqParamMap);
			if(!DataValidateUtil.valid(memberConsumReq)){
				//基础信息验证不通过
				return new Message(ErrorCode.PARAM_FORMAT_ERROR.getCode(),ErrorCode.PARAM_FORMAT_ERROR.getDes());
			}
			//获取商户信息
			Merchant merchant = merchantService.queryMerchInfo(memberConsumReq.getMerchant_num());
			if (merchant == null) {
				return new Message(ErrorCode.MERCHANT_NOT_EXIST.getCode(), ErrorCode.MERCHANT_NOT_EXIST.getDes());			
			}
			//签名验证
			if(!MD5.verify(reqParamMap, memberConsumReq.getSign(), merchant.getMd5Key(), "utf-8")){
				return new Message(ErrorCode.PARAM_SIGN_ERROR.getCode(),ErrorCode.PARAM_SIGN_ERROR.getDes());
			}
			//查找会员信息
			Membercard membercard = membercardService.queryMembercardByMerNo(
					memberConsumReq.getMerchant_num(), memberConsumReq.getMember_card_code());
			if(membercard==null){
				//会员不存在提示
				return new Message(ErrorCode.MEMBER_NOT_EXIST.getCode(), ErrorCode.MEMBER_NOT_EXIST.getDes());
			}
			if(membercard.getStatus()!=MembercardStatus.ACTIVE.getValue()){
				//会员状态处于非激活状态
				return new Message(ErrorCode.MEMBER_STATUS_ERR.getCode(), ErrorCode.MEMBER_STATUS_ERR.getDes());
			}
			return memberUserService.memberConsum(memberConsumReq,membercard);
		}catch (ValidateException e) {
			return new Message(e.getErrCode(),e.getErrMsg());
		}catch(YunPayException e){
			return new Message(e.getErrCode(),e.getErrMsg());
		}catch(Exception e){
			e.printStackTrace();
			return new Message(ResultCode.EXCEPTION.getCode(),ResultCode.EXCEPTION.getDes());
		}
	}
	
}
