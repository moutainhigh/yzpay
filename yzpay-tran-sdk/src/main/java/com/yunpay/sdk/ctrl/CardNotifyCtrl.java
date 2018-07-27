package com.yunpay.sdk.ctrl;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.xml.sax.SAXException;

import com.yunpay.common.utils.CommonUtil;
import com.yunpay.common.utils.Log;
import com.yunpay.common.utils.XmlUtil;
import com.yunpay.serv.service.card.CardNotifyService;

/**
 * 卡券业务异步通知接收类
 * 文件名称:     CardNotifyCtrl.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年8月2日下午4:55:49 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年8月2日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
@Controller
public class CardNotifyCtrl extends BaseCtrl{
	
	@Autowired
	private CardNotifyService cardNotifyService;
	
	/**
	 * @Description: 微信卡券异步通知入口
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月2日下午4:58:39 
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("/card/wechat/notify")
	public void wechatCardNotify(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String echoStr = request.getParameter("echostr");
		if(StringUtils.isNotBlank(echoStr)){
			super.Print(response, echoStr);
			return;
		}
		String recvInfo = CommonUtil.inputStreamToString(request.getInputStream());
		try {
			Map<String, String> recvMap = XmlUtil.getMapFromXML(recvInfo);
			Log.info("------------card wechat notify recv:"+recvMap.toString());
			if(null!=recvMap && recvMap.size()>0){
				String event = recvMap.get("Event");
				switch (event){
					case "card_pass_check":
					case "card_not_pass_check":
						//卡券核销
						Log.info("卡券审核事件通知");
						cardNotifyService.cardCheck(recvMap,0);
						break;
					case "user_get_card": 
						//卡券领取
						Log.info("用户领取卡券事件通知");
						cardNotifyService.userGetCard(recvMap);
						break;
					case "user_gifting_card":
						Log.info("用户转赠卡券事件通知");
						cardNotifyService.userGivingCard(recvMap);
						break;
					case "user_del_card":
						Log.info("用户删除卡券事件通知");
						cardNotifyService.userDelCard(recvMap);
						break;
					case "user_consume_card":
						Log.info("卡券核销事件通知");
						cardNotifyService.userConsumCard(recvMap);
						break;
					case "submit_membercard_user_info":
						Log.info("会员激活事件通知");
						cardNotifyService.membercardActive(recvMap);
						break;
					default:
						Log.info("暂未设置该事件处理流程");
						break;
				}
			}
			super.Print(response, "ok");
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.error("wechat card exception"+e.toString());
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.error("wechat card exception"+e.toString());
		}
	}
	
	/**
	 * @Description: 微信卡券异步通知入口
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月2日下午4:58:39 
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("/ali/oauth")
	public String aliOauth(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, String> recvParam = getRequestParams(request);
		String[] validParam = {"app_id","scope","auth_code","out_string"};
		String valiString = CommonUtil.paramValidate(recvParam,validParam);
		if(StringUtils.isNotEmpty(valiString)){
			return "member/error";
		}
		return "member/ok";
	}
}
