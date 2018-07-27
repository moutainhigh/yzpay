package com.yunpay.sdk.ctrl;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yunpay.activemq.producer.CouponProducer;
import com.yunpay.common.utils.AmountUtil;
import com.yunpay.common.utils.HttpClientUtil;
import com.yunpay.common.utils.HttpUtil;
import com.yunpay.common.utils.MD5;
import com.yunpay.common.utils.ResultEnum.ErrorCode;
import com.yunpay.serv.model.CardInfo;
import com.yunpay.serv.model.Merchant;
import com.yunpay.serv.model.PayTranLs;
import com.yunpay.serv.rep.Message;
import com.yunpay.serv.req.member.MemActivateReqDto;
import com.yunpay.serv.req.member.MemberCardReqDto;
import com.yunpay.serv.service.busi.CardInfoService;
import com.yunpay.serv.service.busi.MerchantService;
import com.yunpay.serv.service.card.CardService;
import com.yunpay.serv.service.member.MemberCardService;
import com.yunpay.serv.validate.DataValidateUtil;
import com.yunpay.serv.validate.exception.ValidateException;


@Controller
public class TestCtrl extends BaseCtrl{
	
	@Autowired
	private CardInfoService cardInfoService;
	@Autowired
	private MerchantService merchantService;
	@Autowired
	private CardService cardService;
	@Autowired
	private MemberCardService memberCardService;
	@Autowired
	private CouponProducer producerService;
	
	
	/**
	 * @Description: 查询卡券列表信息
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年08月14日上午10:38:09 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/card/querylist",method=RequestMethod.POST)
	@ResponseBody
	public Object doQueryCardList(HttpServletRequest request, HttpServletResponse response) {
		List<CardInfo> cardList = cardInfoService.queryCardList();
		JSONObject jsonResult = new JSONObject();
		jsonResult.put("rows", cardList);
		jsonResult.put("total", cardList.size());
		return jsonResult;
	}
	
	/**
	 * @Description: 支付测试
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月28日下午1:38:40 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/pay/test")
	@ResponseBody
	public Object doPayTest(HttpServletRequest request, HttpServletResponse response){
		Map<String,String> reqParamMap = this.getRequestParams(request);
		//获取商户信息
		Merchant merchant = merchantService.queryMerchInfo(reqParamMap.get("merchant_num"));
		if (merchant == null) {
			return new Message("merchant_not_exist","商户不存在");
		}
		String queryString = MD5.sign(reqParamMap, "MD5", merchant.getMd5Key(), "UTF-8");
		String recvMsg = HttpUtil.sendPostUrl(reqParamMap.get("url"), queryString, "UTF-8");
		JSONObject obj = JSONObject.parseObject(recvMsg);
		return obj;
	}
	
	
	@RequestMapping(value = "/pay/scan/forward")
	public String toAliScanForward(HttpServletRequest request, HttpServletResponse response){
		return "";
	}
	
	/**
	 * @Description: 测试本地目录能否和linux目录一致
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月24日下午2:41:57 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/pay/path")
	@ResponseBody
	public String doPathExist(HttpServletRequest request, HttpServletResponse response){
		String path = "/usr/local/yunpay/cert/1484944302/";
		File pathFolder = new File(path);
		if(!pathFolder.exists()){
			pathFolder.mkdirs();
			return "not exist";
		}else{
			return "yes exist";
		}
	}
	
	/**
	 * @Description: 测试接收json格式数据
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月28日下午1:40:04 
	 * @param recvParam
	 * @return
	 */
	@RequestMapping(value="/memcard/test/create")
	@ResponseBody
	public Object cardJsonReqTest(@RequestBody String recvParam){
		MemberCardReqDto memberCard = JSONObject.parseObject(recvParam, MemberCardReqDto.class);
		try {
			boolean isValid = DataValidateUtil.valid(memberCard);
			if(isValid){
				if(memberCard.getBonus_rule()!=null){
					isValid = DataValidateUtil.valid(memberCard.getBonus_rule());
					if(isValid){
						return new Message("OK");
					}
				}
			}
		} catch (ValidateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new Message(e.getErrCode(),e.getErrMsg());
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(memberCard.getMerchant_name());
//		JSONObject jsonObj = JSONObject.parseObject(recvParam);
//		JSONObject baseInfo = jsonObj.getJSONObject("baseInfo");
//		JSONObject bonusInfo = jsonObj.getJSONObject("bonusInfo");
//		BonusRule bonusRule = new BonusRule(bonusInfo.getInteger("cost_money_unit"),bonusInfo.getInteger("increase_bonus"),
//				bonusInfo.getInteger("max_increase_bonus"),bonusInfo.getInteger("init_increase_bonus"),
//				bonusInfo.getInteger("cost_bonus_unit"),bonusInfo.getInteger("reduce_money"),
//				bonusInfo.getInteger("least_money_to_use_bonus"),bonusInfo.getInteger("max_reduce_bonus"));
//		boolean canShare = "1".equals(baseInfo.getString("can_share"));
//		boolean canGiveFriend = "1".equals(baseInfo.getString("can_give_friend"));
//		MemberBaseInfo memberBaseInfo = new MemberBaseInfo(baseInfo.getString("logo_url"),baseInfo.getString("code_type"),
//				true,true,baseInfo.getString("merchant_name"),baseInfo.getString("title"),
//				baseInfo.getString("color"),baseInfo.getString("notice"),baseInfo.getString("description"),
//				baseInfo.getInteger("quantity"),CardDateType.PERMANENT.getCode(),baseInfo.getString("service_phone"),
//				1,canShare,canGiveFriend);
//		MemberCardReq memberCardReq = new MemberCardReq(jsonObj.getString("backgroundPicUrl"),memberBaseInfo,
//				jsonObj.getString("prerogative"),false,true,true,jsonObj.getString("bonus_url"),
//				jsonObj.getString("bonus_cleared"),jsonObj.getString("bonus_rules"),
//				null,bonusRule,bonusInfo.getInteger("discount"));
//		return memberCardService.createMemberCard(memberCardReq, jsonObj.getString("merchant_num"));
		return new Message(ErrorCode.PARAM_FORMAT_ERROR.getCode(),ErrorCode.PARAM_FORMAT_ERROR.getDes());
	}
	
	/**
	 * 测试设置会员卡一键激活
	 * @Description: 
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月1日上午9:47:13 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/memcard/test/activate")
	@ResponseBody
	public Object cardActivateTest(HttpServletRequest request, HttpServletResponse response){
		String cardId = request.getParameter("card_id");
		String merchantNum = request.getParameter("merchant_num");
		String activateStr = "{require:[\"USER_FORM_INFO_FLAG_MOBILE\",\"USER_FORM_INFO_FLAG_NAME\"],"
				+ "common:[\"USER_FORM_INFO_FLAG_SEX\",\"USER_FORM_INFO_FLAG_BIRTHDAY\",\"USER_FORM_INFO_FLAG_IDCARD\","
				+ "\"USER_FORM_INFO_FLAG_EMAIL\",\"USER_FORM_INFO_FLAG_HABIT\"]}";
		MemActivateReqDto activateReq = JSONObject.parseObject(activateStr,MemActivateReqDto.class);
		activateReq.setCard_id(cardId);
		activateReq.setMerchant_num(merchantNum);
		return memberCardService.createActivateParam(activateReq);
	}
	
	/**
	 * @Description:测试activemq消息发送
	 * @author:   Zeng Dongcheng
	 * @Date:     2018年1月15日上午10:30:37 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/test/activemq")
	@ResponseBody
	public Object testActivemq(HttpServletRequest request, HttpServletResponse response){
		String sendMsg = request.getParameter("send_msg");
		//String destName = request.getParameter("dest_name");
		try{
			producerService.sendMessage(sendMsg);
		}catch(RuntimeException e){
			e.printStackTrace();
			return new Message("1111","发送错误",e.toString());
		}
		
		return new Message(sendMsg);
	}
	
	/**
	 * @Description: 卡券核销测试
	 * @author:   Zeng Dongcheng
	 * @Date:     2018年1月23日上午9:36:43 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/test/coupon")
	@ResponseBody
	public Object testCheckCoupon(HttpServletRequest request, HttpServletResponse response){
		Map<String,String> reqParamMap = this.getRequestParams(request);
		HttpClientUtil serviceRequest = new HttpClientUtil();
		String recvMsg = serviceRequest.doPost(reqParamMap.get("url"), reqParamMap,"UTF-8");
		return new Message(recvMsg);
	}
	
	
	public static void test1(){
		String s = "12345|测试商户|0.01";
		String param[]=s.split("\\|");
		System.out.println(param[0]);
		System.out.println(param[1]);
		System.out.println(param[2]);
		
		String fileName = "test.png";
		System.out.println(fileName.substring(0,fileName.lastIndexOf(".")));
		
		PayTranLs payTranLs = new PayTranLs();
		float a=0;
		payTranLs.setRefundPrice(a);
		System.out.println(AmountUtil.changeY2F(payTranLs.getRefundPrice().toString()));
	}
	
	public static void testFloat(){
		int useBonus = 10;
		int costBonusUnit = 300;
		System.out.println((float)10/300);
		float reduce_money = 2;
		
		float tranAmt = (float)18.52;
		float costMoneyUnit = 10;
		int increaseBonus =2;
		
		float totalReduceMoney1 = (float)Math.round((float)useBonus/costBonusUnit*100)/100*reduce_money;
		System.out.println(totalReduceMoney1);
		
		
		int giveBonus = (int)Math.floor(tranAmt/costMoneyUnit*increaseBonus) ;
		System.out.println("规则说明：每消费："+costMoneyUnit+"元，赠送积分："+increaseBonus);
		System.out.println("此次消费："+tranAmt+",赠送的积分为："+giveBonus);
	}
	
	public static void main(String args[]){
		//testFloat();
		Integer f1=128,f2=128,f3=130,f4=130;
		System.out.println (f1==f2) ;
		System.out.println (f3==f4) ;
	}
	
	public static void jsonTest(){
		String memInfo="{"+
		    "\"errcode\": 0,"+
		    "\"errmsg\": \"ok\","+
		    "\"openid\": \"obLatjjwDolFjRRd3doGIdwNqRXw\","+
		    "\"nickname\": \"Fourier\","+
		    "\"membership_number\": \"316510891298\","+
		    "\"bonus\": 460,"+
		    "\"sex\": \"MALE\","+
		    "\"user_info\": {"+
			"\"common_field_list\": ["+
		            "{"+
		                "\"name\": \"USER_FORM_INFO_FLAG_MOBILE\","+
		                "\"value\": \"15521328888\""+
		            "},"+
		            "{" +
		                "\"name\": \"USER_FORM_INFO_FLAG_NAME\","+
		                "\"value\": \"微信\""+
		            "},"+
		        "],"+
		        "\"custom_field_list\": []"+
		    "},"+
		    "\"user_card_status\": \"NORMAL\""+
		"}";
		JSONObject recvObj = JSONObject.parseObject(memInfo);
		JSONObject userInfo = recvObj.getJSONObject("user_info");
		JSONArray jsonArray = userInfo.getJSONArray("common_field_list");
		Map<String,String> mapFieldList = new HashMap<String,String>();
		for(int i=0;i<jsonArray.size();i++){
			//Map<String,String> obj = (Map<String,String>)jsonArray.get(i);
			JSONObject jsonObj = (JSONObject)jsonArray.get(i);
			mapFieldList.put(jsonObj.getString("name"), jsonObj.getString("value"));
		}
		for (String key : mapFieldList.keySet()) {  
			  
		    System.out.println(key+"="+mapFieldList.get(key));  
		  
		}  
	}
}
