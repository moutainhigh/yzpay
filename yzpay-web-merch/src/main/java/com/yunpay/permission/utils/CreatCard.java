package com.yunpay.permission.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import com.alibaba.fastjson.JSON;
import com.yunpay.common.core.utils.io.UtilsConfig;
import com.yunpay.h5merch.permission.entity.ReciveMsg;
import com.yunpay.h5merch.permission.entity.card.BaseInfo;
import com.yunpay.h5merch.permission.entity.card.Commonfield;
import com.yunpay.h5merch.permission.entity.card.IntegralRule;
import com.yunpay.h5merch.permission.entity.card.Template;
/**
 * 
 * 类名称                     创建微信/支付宝会员卡类
 * 文件名称:     CreatCard.java
 * 内容摘要: 
 * @author:   Zhao Xiaoman
 * @version:  1.0  
 * @Date:     2017年10月10日下午5:09:19
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年10月10日   Zhao Xiaoman       1.0       新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class CreatCard
{
	/**
	 * 创建微信会员卡
	 * @param baseInfo
	 * @param template
	 * @param integralRule
	 * @param commonfieldList
	 * @param salt
	 * @return
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	public static Map<String, String> sendMsg(BaseInfo baseInfo,Template template,IntegralRule integralRule,List<Commonfield> commonfieldList,String salt) {
		String flag = null;
		String cardId = null;
		Map<String, String> map = new HashMap<>();
		//取得微信制卡接口地址
		String url = UtilsConfig.getConfig("memberCard.url");
		/*String str = toLine(baseInfo,template,salt);*/
		//MD5加密生成签名
		String sign = getMD5(template.getMerchantNo(),salt);
		//组装激活字段
		List<String> require_fields = new ArrayList<>();
		List<String> common_fields = new ArrayList<>();
		if (commonfieldList.size()>0)
		{
			for(Commonfield commonfield :commonfieldList){
				if (commonfield.getValue()==1)
				{
					require_fields.add(commonfield.getField());
				} else
				{
					common_fields.add(commonfield.getField());
				}
			}
		}
		Map<String, List<String>> activate_fields = new HashMap<>();
		activate_fields.put("require_fields", require_fields);
		activate_fields.put("common_fields", common_fields);
		//组装积分规则字段
		Map<String,String> bonus_rule = new HashMap<>();
		bonus_rule.put("init_increase_bonus", Integer.toString(integralRule.getInitIncreaseBonus()));
		bonus_rule.put("cost_money_unit", Integer.toString(integralRule.getCostMoneyUnit()));
		bonus_rule.put("increase_bonus", Integer.toString(integralRule.getIncreaseBonus()));
		bonus_rule.put("max_increase_bonus", Integer.toString(integralRule.getMaxIncreaseBonus()));
/*		bonus_rule.put("cost_bonus_unit", Integer.toString(integralRule.getCostBonusUnit()));
		bonus_rule.put("reduce_money", Integer.toString(integralRule.getReduceMoney()));
		bonus_rule.put("max_reduce_bonus", Integer.toString(integralRule.getMaxReduceBonus()));*/
		//组装custom_field1
		Map<String,String> custom_field1 = new HashMap<>();
		custom_field1.put("name", template.getCustomField1NameType());
		custom_field1.put("url", template.getCustomField1Url());
		//组装custom_cell1
		Map<String,String> custom_cell1 = new HashMap<>();
		custom_cell1.put("name", template.getCustomCell1Name());
		custom_cell1.put("tips", template.getCustomCell1Tips());
		custom_cell1.put("url", template.getCustomCell1Url());
		//组装接口数据  
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("merchant_num", template.getMerchantNo());
		data.put("title", baseInfo.getTitle());
		data.put("logo_url", baseInfo.getLogoUrl());
		data.put("merchant_name", baseInfo.getBrandName());		
		data.put("notice", baseInfo.getNotice());		
		data.put("description", baseInfo.getDescription());		
		data.put("color", baseInfo.getColor());		
		data.put("code_type", baseInfo.getCodeType());		
		data.put("quantity", Integer.toString(baseInfo.getSkuQuantity()));		
		data.put("service_phone", baseInfo.getServicePhone());		
		data.put("can_share", baseInfo.getCanShare());		
		data.put("can_give_friend", baseInfo.getCanGiveFriend());		
		data.put("background_pic_url", null);		
		data.put("activate_type", Integer.toString(template.getAutoActivate()));		
		data.put("discount", Double.toString(((double)template.getDiscount())/100));		
		data.put("supply_bonus", template.getSupplyBonus());			
		data.put("bonus_rules", template.getBonusRules());
		data.put("prerogative", template.getPrerogative());
		data.put("custom_field1", custom_field1);
		data.put("custom_cell1", custom_cell1);
		data.put("bonus_rule", bonus_rule);
		data.put("activate_fields", activate_fields);
		data.put("sign", sign);
		data.put("sign_type", "MD5");
		try {
			//调用制卡接口，并取得返回数据
			String result = HttpUtil.sendPostUrl(url, JSON.toJSONString(data), "utf-8");
	        System.out.println(result); //打印返回消息状态      
	        //将返回的String数据转化为ReciveMsg实体
			ReciveMsg<Map<String, Object>> reciveMsg = JSON.parseObject(result, ReciveMsg.class);
	        //这里就是返回结果
	        String result_code = reciveMsg.getResult_code();
	        String result_msg = reciveMsg.getResult_msg();
	        String err_code = reciveMsg.getErr_code();
	        String err_code_des = reciveMsg.getErr_code_des();
	        Map<String, Object> datar = reciveMsg.getData();       
			if ("SUCCESS".equals(result_code)) {
				cardId = (String)datar.get("card_id");
				flag = "SUCCESS";
				System.out.println("会员卡创建成功");
			}else{
				flag = err_code_des;
				System.out.println("创建会员卡失败："+ err_code_des);
			}
		} catch (Exception e) {
			flag = "系统错误";
			e.printStackTrace();
		}
		map.put("message", flag);
		map.put("cardId", cardId);
		return map;
	}
	/**
	 * 修改微信会员卡
	 * @param baseInfo
	 * @param template
	 * @param integralRule
	 * @param salt
	 * @return
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	public static Map<String, String> updateMsg(BaseInfo baseInfo,Template template,IntegralRule integralRule,String salt) {
		String flag = null;
		Map<String, String> map = new HashMap<>();
		//取得修改微信会员卡接口地址
		String url = UtilsConfig.getConfig("memberCard.update.url");
		/*String str = toLine(baseInfo,template,salt);*/
		//MD5加密取得签名
		String sign = getMD5(template.getMerchantNo(),salt);
		//组装接口数据
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("merchant_num", template.getMerchantNo());
		data.put("title", baseInfo.getTitle());
		data.put("logo_url", baseInfo.getLogoUrl());			
		data.put("description", baseInfo.getDescription());		
		data.put("color", baseInfo.getColor());			
		data.put("quantity", Integer.toString(baseInfo.getSkuQuantity()));	
		data.put("card_id", template.getCardId());
		data.put("discount", Double.toString(((double)template.getDiscount())/100));
		data.put("activate_type", Integer.toString(template.getAutoActivate()));
		data.put("supply_bonus", template.getSupplyBonus());
		data.put("merchant_name",baseInfo.getBrandName());
		data.put("bonus_rules",template.getBonusRules());
		data.put("sign", sign);
		data.put("sign_type", "MD5");
		//组装积分规则字段
		if (integralRule != null)
		{
			Map<String,String> bonus_rule = new HashMap<>();
			bonus_rule.put("init_increase_bonus", Integer.toString(integralRule.getInitIncreaseBonus()));
			bonus_rule.put("cost_money_unit", Integer.toString(integralRule.getCostMoneyUnit()));
			bonus_rule.put("increase_bonus", Integer.toString(integralRule.getIncreaseBonus()));
			bonus_rule.put("max_increase_bonus", Integer.toString(integralRule.getMaxIncreaseBonus()));
	/*		bonus_rule.put("cost_bonus_unit", Integer.toString(integralRule.getCostBonusUnit()));
			bonus_rule.put("reduce_money", Integer.toString(integralRule.getReduceMoney()));
			bonus_rule.put("max_reduce_bonus", Integer.toString(integralRule.getMaxReduceBonus()));*/
			data.put("bonus_rule", bonus_rule);
		}		
		if (baseInfo.getLogoUrl() != null)
		{
			data.put("logoUrl", baseInfo.getLogoUrl());
		}
		try {
			System.out.println(JSON.toJSONString(data));
			//调用修改微信会员卡接口，并取得返回数据
			String result = HttpUtil.sendPostUrl(url, JSON.toJSONString(data), "UTF-8");
			System.out.println(result); //打印返回消息状态        
			//将返回的String数据转化为ReciveMsg实体
			ReciveMsg<Map<String, Object>> reciveMsg = JSON.parseObject(result, ReciveMsg.class);
			//这里就是返回结果
			String result_code = reciveMsg.getResult_code();
			String result_msg = reciveMsg.getResult_msg();
			String err_code = reciveMsg.getErr_code();
			String err_code_des = reciveMsg.getErr_code_des();
			Map<String, Object> datar = reciveMsg.getData();       
			if ("SUCCESS".equals(result_code)) {
				flag = "SUCCESS";
				System.out.println("会员卡修改成功");
			}else{
				flag = err_code_des;
				System.out.println("会员卡修改失败："+ err_code_des);
			}
		} catch (Exception e) {
			flag = "系统错误";
			e.printStackTrace();
		}
		map.put("message", flag);
		return map;
	}
	/**
	 * 密码MD5加密
	 * @param loginPwd
	 * @return
	 */
	public static String getMD5(String loginPwd,String pwdSalt) {	
		int hashIterations = Integer.valueOf("2");
		String newPassword = new SimpleHash("md5", loginPwd, ByteSource.Util.bytes(pwdSalt), hashIterations).toHex();	
		return newPassword;
	}
	
	public static String toLine(BaseInfo baseInfo,Template template,String salt)
	{
		return null;
	}
}
