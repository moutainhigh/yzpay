package com.yunpay.wx.req.member;

import com.alibaba.fastjson.JSONObject;
import com.yunpay.common.utils.EnumUtil.MemberActive;

/**
 * 会员卡专属信息
 * 文件名称:     MemberCardReq.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年10月13日下午1:56:24 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年10月13日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class MemberCardReq {
	//卡面图片地址
	private String background_pic_url;
	//卡券基本信息
	private MemberBaseInfo base_info;
	//会员卡特权说明
	private String prerogative;
	//是否自动激活
	private Boolean auto_activate;
	//是否支持一键开卡
	private Boolean wx_activate;
	//是否显示积分
	private Boolean supply_bonus=true;
	//跳转外链查看积分详情
	private String bonus_url;
	//是否显示卡余额(需开通储值卡权限)
	private Boolean supply_balance=false;
	//跳转外链查看储值余额
	private String balance_url;
	//自定义会员信息类目1
	private JSONObject custom_field1;
	//自定义会员信息类目2
	private JSONObject custom_field2;
	//自定义会员信息类目3
	private JSONObject custom_field3;
	//积分清零规则(文字说明)
	private String bonus_cleared;
	//积分规则(文字说明)
	private String bonus_rules;
	//储值说明
	private String balance_rules;
	//激活会员卡url
	private String activate_url;
	//自定义会员信息类目，激活后显示
	private JSONObject custom_cell1;
	//积分规则
	private BonusRule bonus_rule;
	//会员享受的折扣(10表示九折)
	private Integer discount;
	
	public MemberCardReq(){
		
	}
	
	/**
	 * 会员卡请求参数初始化(新增)
	 * @param backgroundPicUrl
	 * @param baseInfo
	 * @param preprogative
	 * @param autoActive
	 * @param wxActivate
	 * @param supplyBonus
	 * @param bonusUrl
	 * @param bonusCleared
	 * @param bonusRules
	 * @param bonusRule
	 * @param discount
	 */
	public MemberCardReq(String backgroundPicUrl,MemberBaseInfo baseInfo,String prerogative,
			int activateType,Boolean supplyBonus,String bonusUrl,
			String bonusCleared,String bonusRules,BonusRule bonusRule,Integer discount){
		this.setBackground_pic_url(backgroundPicUrl);
		this.setBase_info(baseInfo);
		this.setPrerogative(prerogative);
		//会员卡激活方式
		if(activateType==MemberActive.AUTO_ACTIVE.getCode()){
			//自动激活
			this.setAuto_activate(true);
			this.setWx_activate(false);
		}else{
			//微信一键激活
			this.setAuto_activate(false);
			this.setWx_activate(true);
		}
		this.setSupply_bonus(supplyBonus);
		this.setBonus_url(bonusUrl);
		this.setBonus_cleared(bonusCleared);
		this.setBonus_rules(bonusRules);
		this.setBonus_rule(bonusRule);
		this.setDiscount(discount);
	}
	
	public String getBackground_pic_url() {
		return background_pic_url;
	}
	public void setBackground_pic_url(String background_pic_url) {
		this.background_pic_url = background_pic_url;
	}
	public MemberBaseInfo getBase_info() {
		return base_info;
	}
	public void setBase_info(MemberBaseInfo base_info) {
		this.base_info = base_info;
	}
	public String getPrerogative() {
		return prerogative;
	}
	public void setPrerogative(String prerogative) {
		this.prerogative = prerogative;
	}
	
	public Boolean getAuto_activate() {
		return auto_activate;
	}

	public void setAuto_activate(Boolean auto_activate) {
		this.auto_activate = auto_activate;
	}

	public Boolean isWx_activate() {
		return wx_activate;
	}
	public void setWx_activate(Boolean wx_activate) {
		this.wx_activate = wx_activate;
	}
	public Boolean isSupply_bonus() {
		return supply_bonus;
	}
	public void setSupply_bonus(Boolean supply_bonus) {
		this.supply_bonus = supply_bonus;
	}
	public String getBonus_url() {
		return bonus_url;
	}
	public void setBonus_url(String bonus_url) {
		this.bonus_url = bonus_url;
	}
	public Boolean isSupply_balance() {
		return supply_balance;
	}
	public void setSupply_balance(Boolean supply_balance) {
		this.supply_balance = supply_balance;
	}
	public String getBalance_url() {
		return balance_url;
	}
	public void setBalance_url(String balance_url) {
		this.balance_url = balance_url;
	}
	public JSONObject getCustom_field1() {
		return custom_field1;
	}
	//
	public void setCustom_field1(String name,String url) {
		this.custom_field1 = new JSONObject();
		this.custom_field1.put("name", name);
		this.custom_field1.put("url", url);
	}
	
	public JSONObject getCustom_field2() {
		return custom_field2;
	}
	public void setCustom_field2(String name,String url) {
		this.custom_field2 = new JSONObject();
		this.custom_field2.put("name", name);
		this.custom_field2.put("url", url);
	}
	public JSONObject getCustom_field3() {
		return custom_field3;
	}
	public void setCustom_field3(String name,String url) {
		this.custom_field3 = new JSONObject();
		this.custom_field3.put("name", name);
		this.custom_field3.put("url", url);
	}
	public String getBonus_cleared() {
		return bonus_cleared;
	}
	public void setBonus_cleared(String bonus_cleared) {
		this.bonus_cleared = bonus_cleared;
	}
	public String getBonus_rules() {
		return bonus_rules;
	}
	public void setBonus_rules(String bonus_rules) {
		this.bonus_rules = bonus_rules;
	}
	public String getBalance_rules() {
		return balance_rules;
	}
	public void setBalance_rules(String balance_rules) {
		this.balance_rules = balance_rules;
	}
	public String getActivate_url() {
		return activate_url;
	}
	public void setActivate_url(String activate_url) {
		this.activate_url = activate_url;
	}
	public JSONObject getCustom_cell1() {
		return custom_cell1;
	}
	//自定义会员信息类目，会员卡激活后显示
	public void setCustom_cell1(String customCellName,String customCellTips,String customCellUrl) {
		this.custom_cell1 = new JSONObject();
		this.custom_cell1.put("name", customCellName);
		this.custom_cell1.put("tips", customCellTips);
		this.custom_cell1.put("url", customCellUrl);
	}
	
	public BonusRule getBonus_rule() {
		return bonus_rule;
	}
	public void setBonus_rule(BonusRule bonus_rule) {
		this.bonus_rule = bonus_rule;
	}

	public Integer getDiscount() {
		return discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

	
	
}
