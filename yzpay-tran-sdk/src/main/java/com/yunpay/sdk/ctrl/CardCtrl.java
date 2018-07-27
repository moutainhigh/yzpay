package com.yunpay.sdk.ctrl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.yunpay.common.exception.YunPayException;
import com.yunpay.common.utils.EnumUtil.CardType;
import com.yunpay.common.utils.CommonUtil;
import com.yunpay.common.utils.Log;
import com.yunpay.common.utils.ResultEnum.ErrorCode;
import com.yunpay.common.utils.ResultEnum.ResultCode;
import com.yunpay.serv.common.TokenService;
import com.yunpay.serv.rep.Message;
import com.yunpay.serv.req.card.CardReqDto;
import com.yunpay.serv.route.CardFactory;
import com.yunpay.serv.service.card.CardCommonService;
import com.yunpay.serv.service.card.CardService;


/**
 * 卡券外部接口服务类
 * 文件名称:     CardTemplateCtrl.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年7月31日下午6:02:23 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年7月31日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
@RestController
public class CardCtrl extends BaseCtrl{
	
	private static final String APPID = "wxcbf364c912dd0987";
	
	private static final String APPSECRET = "824a09a0964de75b98ce54fbddda7ec9";
	
	
	@Autowired
	private CardService cardService;
	@Autowired
	private TokenService tokenService;
	@Autowired
	private CardFactory cardFactory;
	
	
	/**
	 * @Description: 测试获取access_token
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月1日上午11:20:44 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/card/token")
    public Object doAccessToken(HttpServletRequest request, HttpServletResponse response){
		Message message;
		try{
			String accessToken = tokenService.recvAccessToken(APPID, APPSECRET);
			message = new Message(accessToken);
		}catch(YunPayException e){
			message = new Message(e.getErrCode(),e.getErrMsg());
		}
		return message;
    }
	
	/**
	 * @Description: 图片上传
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月1日上午11:20:34 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/card/uploadimg")
	public Object doUploadImg(@RequestParam("file") CommonsMultipartFile file,
			HttpServletRequest request, HttpServletResponse response){
		Map<String,String> reqParamMap = this.getRequestParams(request);
		Log.info("卡券logo上传" + reqParamMap);
		//非空参数验证
		String[] validParam = {"sign","merchant_num","put_channel"};
		String valiString = CommonUtil.paramValidate(reqParamMap,validParam);
		if(StringUtils.isNotEmpty(valiString)){
			return new Message(ErrorCode.PARAM_NULL_ERROR.getCode(),
					String.format(ErrorCode.PARAM_NULL_ERROR.getDes(),valiString));
		} 
		try{
			int putChannel = Integer.valueOf(reqParamMap.get("put_channel"));
			CardCommonService cardCommonService = cardFactory.getCardServiceByChannel(putChannel);
			return cardCommonService.uploadImg(reqParamMap.get("merchant_num"), file);
		}catch(Exception e){
			e.printStackTrace();
			return new Message(ResultCode.EXCEPTION.getCode(),ResultCode.EXCEPTION.getDes());
		}
	}
	
	
	/**
	 * @Description: 创建卡券
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月1日下午5:18:01 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/card/create")
	public Object doCreateCard(HttpServletRequest request, HttpServletResponse response){
		Map<String,String> reqParamMap = this.getRequestParams(request);
		Log.info("创建卡券请求参数" + reqParamMap);
		//必传项
		String[] baseParam = {"sign","merchant_num","merchant_name","card_type","logo_url",
				"code_type","title","color","notice","description",
				"quantity","begin_timestamp","end_timestamp","put_channel"};
		List<String> validParam = new ArrayList<String>();
		for(String param : baseParam){
			validParam.add(param);
		}
		String cardType = reqParamMap.get("card_type");
		if(CardType.GIFT.getValue().equals(cardType)){
			//现金券
			validParam.add("least_cost");
			validParam.add("reduce_cost");
		}else if(CardType.DISCOUNT.getValue().equals(cardType)){
			//折扣券
			validParam.add("discount");
		}else{
			//团购券/兑换券/优惠券
			validParam.add("default_detail");
		}
		//参数验证
		String valiString = CommonUtil.paramValidate(reqParamMap,validParam);
		if(StringUtils.isNotEmpty(valiString)){
			return new Message(ErrorCode.PARAM_NULL_ERROR.getCode(),
					String.format(ErrorCode.PARAM_NULL_ERROR.getDes(),valiString));
		} 
		try {
			CardReqDto cardReq = (CardReqDto)CommonUtil.convertMap(CardReqDto.class,reqParamMap);
			return cardService.cardCreate(cardReq);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new Message(ErrorCode.SYSTEM_EXCEPTION.name(), ErrorCode.SYSTEM_EXCEPTION.getDes());
		}
	}
	
	
	/**
	 * @Description: 创建卡券投放码
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月1日下午5:18:01 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/card/qrcode")
	public Object doCreateQrcode(HttpServletRequest request, HttpServletResponse response){
		Map<String,String> reqParamMap = this.getRequestParams(request);
		Log.info("获取卡券领取二维码" + reqParamMap);
		//非空参数验证
		String[] validParam = {"sign","merchant_num","card_id"};
		String valiString = CommonUtil.paramValidate(reqParamMap,validParam);
		if(StringUtils.isNotEmpty(valiString)){
			return new Message(ErrorCode.PARAM_NULL_ERROR.getCode(),
					String.format(ErrorCode.PARAM_NULL_ERROR.getDes(),valiString));
		}
		try{
			return cardService.qrCodeCreate(reqParamMap.get("merchant_num"),reqParamMap.get("card_id"));
		}catch(Exception e){
			e.printStackTrace();
			return new Message(ResultCode.EXCEPTION.getCode(),ResultCode.EXCEPTION.getDes());
		}
	}
	
	/**
	 * @Description: 卡券核销
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月1日下午5:18:01 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/card/consume")
	public Object doCardConsume(HttpServletRequest request, HttpServletResponse response){
		Map<String,String> reqParamMap = this.getRequestParams(request);
		Log.info("卡券核销" + reqParamMap);
		//非空参数验证
		String[] validParam = {"sign","merchant_num","card_code"};
		String valiString = CommonUtil.paramValidate(reqParamMap,validParam);
		if(StringUtils.isNotEmpty(valiString)){
			return new Message(ErrorCode.PARAM_NULL_ERROR.getCode(),
					String.format(ErrorCode.PARAM_NULL_ERROR.getDes(),valiString));
		}
		try{
			return cardService.cardConsume(reqParamMap.get("merchant_num"),reqParamMap.get("card_code"));
		}catch(Exception e){
			e.printStackTrace();
			return new Message(ResultCode.EXCEPTION.getCode(),ResultCode.EXCEPTION.getDes());
		}
	}
	
	/**
	 * @Description: 卡券删除
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月1日下午5:18:01 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/card/delete")
	public Object doCardDelete(HttpServletRequest request, HttpServletResponse response){
		Map<String,String> reqParamMap = this.getRequestParams(request);
		Log.info("卡券删除" + reqParamMap);
		//非空参数验证
		String[] validParam = {"sign","merchant_num","card_id"};
		String valiString = CommonUtil.paramValidate(reqParamMap,validParam);
		if(StringUtils.isNotEmpty(valiString)){
			return new Message(ErrorCode.PARAM_NULL_ERROR.getCode(),
					String.format(ErrorCode.PARAM_NULL_ERROR.getDes(),valiString));
		}
		try{
			return cardService.cardDelete(reqParamMap.get("merchant_num"),reqParamMap.get("card_id"));
		}catch(Exception e){
			e.printStackTrace();
			return new Message(ResultCode.EXCEPTION.getCode(),ResultCode.EXCEPTION.getDes());
		}
	}
	
	/**
	 * @Description: 查询卡券信息
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月1日下午5:18:01 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/card/query")
	public Object doCardQuery(HttpServletRequest request, HttpServletResponse response){
		String cardId = request.getParameter("card_id");
		return cardService.cardSearch(cardId);
	}
	
	/**
	 * @Description: 卡券修改
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月1日下午5:18:01 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/card/update")
	public Object doCardUpdate(HttpServletRequest request, HttpServletResponse response){
		Map<String,String> reqParamMap = this.getRequestParams(request);
		Log.info("修改卡券请求参数" + reqParamMap);
		//必传项
		String[] baseParam = {"sign","merchant_num","logo_url","code_type","title",
				"color","notice","description","begin_timestamp","end_timestamp","card_id"};
		List<String> validParam = new ArrayList<String>();
		for(String param : baseParam){
			validParam.add(param);
		}
		//参数验证
		String valiString = CommonUtil.paramValidate(reqParamMap,validParam);
		if(StringUtils.isNotEmpty(valiString)){
			return new Message(ErrorCode.PARAM_NULL_ERROR.getCode(),
					String.format(ErrorCode.PARAM_NULL_ERROR.getDes(),valiString));
		}
		try {
			CardReqDto cardReq = (CardReqDto)CommonUtil.convertMap(CardReqDto.class,reqParamMap);
			return cardService.cardUpdate(cardReq);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new Message(ErrorCode.SYSTEM_EXCEPTION.name(), ErrorCode.SYSTEM_EXCEPTION.getDes());
		}
	}
	
	/**
	 * @Description: 卡券推送
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月1日下午5:18:01 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/card/push")
	public Object doCardPush(HttpServletRequest request, HttpServletResponse response){
		Map<String,String> reqParamMap = this.getRequestParams(request);
		Log.info("卡券推送" + reqParamMap);
		//非空参数验证
		String[] validParam = {"sign","merchant_num","card_id"};
		String valiString = CommonUtil.paramValidate(reqParamMap,validParam);
		if(StringUtils.isNotEmpty(valiString)){
			return new Message(ErrorCode.PARAM_NULL_ERROR.getCode(),
					String.format(ErrorCode.PARAM_NULL_ERROR.getDes(),valiString));
		}
		try{
			return cardService.cardPushToWxapp(reqParamMap.get("merchant_num"),reqParamMap.get("card_id"));
		}catch(Exception e){
			e.printStackTrace();
			return new Message(ResultCode.EXCEPTION.getCode(),ResultCode.EXCEPTION.getDes());
		}
	}
	
	/**
	 * @Description: 快速买单设置
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月1日下午5:18:01 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/card/payset")
	public Object doCardPaySet(HttpServletRequest request, HttpServletResponse response){
		Map<String,String> reqParamMap = this.getRequestParams(request);
		Log.info("买单设置" + reqParamMap);
		//非空参数验证
		String[] validParam = {"sign","merchant_num","card_id"};
		String valiString = CommonUtil.paramValidate(reqParamMap,validParam);
		if(StringUtils.isNotEmpty(valiString)){
			return new Message(ErrorCode.PARAM_NULL_ERROR.getCode(),
					String.format(ErrorCode.PARAM_NULL_ERROR.getDes(),valiString));
		}
		try{
			return cardService.cardPaySet(reqParamMap.get("merchant_num"),reqParamMap.get("card_id"));
		}catch(Exception e){
			e.printStackTrace();
			return new Message(ResultCode.EXCEPTION.getCode(),ResultCode.EXCEPTION.getDes());
		}
	}
	
}
