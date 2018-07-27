package com.yunpay.permission.controller.mobile.sdk;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.yunpay.controller.common.BaseController;
import com.yunpay.h5merch.permission.entity.card.UserCard;
import com.yunpay.h5merch.permission.entity.card.UserReAndCon;
import com.yunpay.h5merch.permission.service.UserCardService;
/**
 * 
 * 类名称                      余额查看类
 * 文件名称:     ReAndCon.java
 * 内容摘要: 	      用于会员查看储值、消费记录，查看余额
 * @author:   Zhao Xiaoman
 * @version:  1.0  
 * @Date:     2017年9月7日下午4:53:34
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年9月7日   Zhao Xiaoman       1.0       新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
@Controller
@RequestMapping("/reandcon")
public class ReAndCon extends BaseController
{
	private static Log log = LogFactory.getLog(ReAndCon.class);

	@Autowired
	private UserCardService userCardService;
	/**
	 * 用于会员查看储值、消费记录，查看余额
	 * @param req
	 * @param model
	 * @param card_id
	 * @param openid
	 * @return
	 */
	@RequestMapping("/show")
	public  String rechargeShow(HttpServletRequest req,Model model,
			@RequestParam("card_id") String card_id,
			@RequestParam("openid") String openid)
	{
		try
		{				
			if (card_id == null || openid == null)
			{
				return null;
			} else
			{
				//保存查询参数
				Map<String, String> data = new HashMap<>();
				data.put("cardId", card_id);
				data.put("openId", openid);
				//查询会员
				UserCard userCard = userCardService.findUserCardByUser(data);
				if (userCard == null)
				{
					return null;
				} else
				{
					Map<String, Double> map = new HashMap<>();
					//用于保存查询参数
					Map<String, String> code = new HashMap<>();
					if (userCard.getUserCardCode() != null)
					{
						//若有微信会员卡号，则使用微信会员卡号查询
						code.put("cardCode", userCard.getUserCardCode());
						code.put("merchantNo", userCard.getMerchantNo());
						
					} else if(userCard.getAliUserCardCode() != null)
					{
						//若有支付宝会员卡号，则使用支付宝会员卡号查询
						code.put("cardCode", userCard.getAliUserCardCode());
						code.put("merchantNo", userCard.getMerchantNo());
					}else {
						
					}
					//查询会员储值、消费的汇总信息
					map = userCardService.findOther(code);
					//查询会员储值、消费的记录列表
					List<UserReAndCon> list = userCardService.findUserReAndCon(code);
					req.setAttribute("userCard",userCard);
					req.setAttribute("userOther", map);
					req.setAttribute("reandconList", list);
					
				}
				return "membercard/reandconshow";
			}		
		} catch (Exception e)
		{
			log.error("== userlist exception:", e);
			return null;
		}	
	}
}
