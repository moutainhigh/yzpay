package com.yunpay.serv.req.member;
import com.yunpay.serv.validate.annotation.DataValidate;
import com.yunpay.serv.validate.enums.RegexType;
/**
 * 会员卡
 * 文件名称:     MemberCardReqDto.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年8月31日上午11:05:06 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年8月31日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class MemberCardReqDto {
	/***************必传项部分************/
	//卡券id (修改时用到)
	//private String card_id;
	//商户号
	@DataValidate(allowNull=false,regexType=RegexType.NUMBER)
	private String merchant_num;
	//商户名称
	@DataValidate(allowNull=false,maxLength=12)
	private String merchant_name;
	//卡券logo
	@DataValidate(allowNull=false)
	private String logo_url;
	//卡券领取码类型
	@DataValidate(allowNull=false)
	private String code_type;
	//卡券标题
	@DataValidate(allowNull=false)
	private String title;
	//卡券颜色
	@DataValidate(allowNull=false)
	private String color;
	//卡券使用提醒
	@DataValidate(allowNull=false)
	private String notice;
	//卡券使用说明
	@DataValidate(allowNull=false)
	private String description;
	//发行数量
	@DataValidate(allowNull=false,regexType=RegexType.NUMBER)
	private String quantity;
	//激活方式(1:自动激活，2：微信一键激活)
	@DataValidate(allowNull=false,regexType=RegexType.NUMBER)
	private Integer activate_type;
	//会员卡特权说明
	@DataValidate(allowNull=false,maxLength=200)
	private String prerogative;
	/***************非必传项部分************/
	//客服电话
	private String service_phone;
	//卡券能否分享
	private String can_share="0";
	//卡券可否转赠
	private String can_give_friend="0";
	//卡面图片地址
	private String background_pic_url;
	//是否显示积分
	private String supply_bonus="0";
	//跳转外链查看积分详情
	private String bonus_url;
	//自定义会员信息类目1
	private CustomField custom_field1;
	//自定义会员信息类目2
	private CustomField custom_field2;
	//自定义会员信息类目3
	private CustomField custom_field3;
	public class CustomField{
		private String name;
		private String url;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
	}
	//积分清零规则(文字说明)
	private String bonus_cleared;
	//积分规则(文字说明)
	private String bonus_rules;
	//储值说明
	private String balance_rules;
	//自定义会员信息类目，激活后显示
	private CustomCell custom_cell1;
	public class CustomCell{
		private String name;
		private String tips;
		private String url;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getTips() {
			return tips;
		}
		public void setTips(String tips) {
			this.tips = tips;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
	}
	//积分规则
	private BonusRule bonus_rule;
	public class BonusRule{
		//消费金额(元为单位)
		@DataValidate(regexType=RegexType.NUMBER)
		private String cost_money_unit;
		//对应增加的积分
		@DataValidate(regexType=RegexType.NUMBER)
		private Integer increase_bonus;
		//单次获得的积分上限
		@DataValidate(regexType=RegexType.NUMBER)
		private Integer max_increase_bonus;
		//初始积分
		@DataValidate(regexType=RegexType.NUMBER)
		private Integer init_increase_bonus;
		//每使用积分数
		@DataValidate(regexType=RegexType.NUMBER)
		private Integer cost_bonus_unit;
		//抵扣金额
		@DataValidate(regexType=RegexType.DECIMAL)
		private String reduce_money;
		//抵扣条件(满xx元可用)
		@DataValidate(regexType=RegexType.DECIMAL)
		private String least_money_to_use_bonus;
		//单笔最多使用xx积分
		@DataValidate(regexType=RegexType.NUMBER)
		private Integer max_reduce_bonus;
		public String getCost_money_unit() {
			return cost_money_unit;
		}
		public void setCost_money_unit(String cost_money_unit) {
			this.cost_money_unit = cost_money_unit;
		}
		public Integer getIncrease_bonus() {
			return increase_bonus;
		}
		public void setIncrease_bonus(Integer increase_bonus) {
			this.increase_bonus = increase_bonus;
		}
		public Integer getMax_increase_bonus() {
			return max_increase_bonus;
		}
		public void setMax_increase_bonus(Integer max_increase_bonus) {
			this.max_increase_bonus = max_increase_bonus;
		}
		public Integer getInit_increase_bonus() {
			return init_increase_bonus;
		}
		public void setInit_increase_bonus(Integer init_increase_bonus) {
			this.init_increase_bonus = init_increase_bonus;
		}
		public Integer getCost_bonus_unit() {
			return cost_bonus_unit;
		}
		public void setCost_bonus_unit(Integer cost_bonus_unit) {
			this.cost_bonus_unit = cost_bonus_unit;
		}
		public String getReduce_money() {
			return reduce_money;
		}
		public void setReduce_money(String reduce_money) {
			this.reduce_money = reduce_money;
		}
		public String getLeast_money_to_use_bonus() {
			return least_money_to_use_bonus;
		}
		public void setLeast_money_to_use_bonus(String least_money_to_use_bonus) {
			this.least_money_to_use_bonus = least_money_to_use_bonus;
		}
		public Integer getMax_reduce_bonus() {
			return max_reduce_bonus;
		}
		public void setMax_reduce_bonus(Integer max_reduce_bonus) {
			this.max_reduce_bonus = max_reduce_bonus;
		}
	}
	//折扣
	@DataValidate(regexType=RegexType.DECIMAL)
	private String discount;
	//会员卡激活参数
	private ActivateFields activate_fields;
	public class ActivateFields{
		private String[] require_fields;
		private String[] common_fields;
		public String[] getRequire_fields() {
			return require_fields;
		}
		public void setRequire_fields(String[] require_fields) {
			this.require_fields = require_fields;
		}
		public String[] getCommon_fields() {
			return common_fields;
		}
		public void setCommon_fields(String[] common_fields) {
			this.common_fields = common_fields;
		}
	}
	
	
//	public String getCard_id() {
//		return card_id;
//	}
//	public void setCard_id(String card_id) {
//		this.card_id = card_id;
//	}
	public String getMerchant_num() {
		return merchant_num;
	}
	public void setMerchant_num(String merchant_num) {
		this.merchant_num = merchant_num;
	}
	public String getMerchant_name() {
		return merchant_name;
	}
	public void setMerchant_name(String merchant_name) {
		this.merchant_name = merchant_name;
	}
	public String getLogo_url() {
		return logo_url;
	}
	public void setLogo_url(String logo_url) {
		this.logo_url = logo_url;
	}
	public String getCode_type() {
		return code_type;
	}
	public void setCode_type(String code_type) {
		this.code_type = code_type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getNotice() {
		return notice;
	}
	public void setNotice(String notice) {
		this.notice = notice;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	
	public Integer getActivate_type() {
		return activate_type;
	}
	public void setActivate_type(Integer activate_type) {
		this.activate_type = activate_type;
	}
	public String getPrerogative() {
		return prerogative;
	}
	public void setPrerogative(String prerogative) {
		this.prerogative = prerogative;
	}
	public String getService_phone() {
		return service_phone;
	}
	public void setService_phone(String service_phone) {
		this.service_phone = service_phone;
	}
	public String getCan_share() {
		return can_share;
	}
	public void setCan_share(String can_share) {
		this.can_share = can_share;
	}
	public String getCan_give_friend() {
		return can_give_friend;
	}
	public void setCan_give_friend(String can_give_friend) {
		this.can_give_friend = can_give_friend;
	}
	public String getBackground_pic_url() {
		return background_pic_url;
	}
	public void setBackground_pic_url(String background_pic_url) {
		this.background_pic_url = background_pic_url;
	}
	public String getSupply_bonus() {
		return supply_bonus;
	}
	public void setSupply_bonus(String supply_bonus) {
		this.supply_bonus = supply_bonus;
	}
	public String getBonus_url() {
		return bonus_url;
	}
	public void setBonus_url(String bonus_url) {
		this.bonus_url = bonus_url;
	}
	public CustomField getCustom_field1() {
		return custom_field1;
	}
	public void setCustom_field1(CustomField custom_field1) {
		this.custom_field1 = custom_field1;
	}
	public CustomField getCustom_field2() {
		return custom_field2;
	}
	public void setCustom_field2(CustomField custom_field2) {
		this.custom_field2 = custom_field2;
	}
	public CustomField getCustom_field3() {
		return custom_field3;
	}
	public void setCustom_field3(CustomField custom_field3) {
		this.custom_field3 = custom_field3;
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

	public CustomCell getCustom_cell1() {
		return custom_cell1;
	}
	public void setCustom_cell1(CustomCell custom_cell1) {
		this.custom_cell1 = custom_cell1;
	}
	public BonusRule getBonus_rule() {
		return bonus_rule;
	}
	public void setBonus_rule(BonusRule bonus_rule) {
		this.bonus_rule = bonus_rule;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public ActivateFields getActivate_fields() {
		return activate_fields;
	}
	public void setActivate_fields(ActivateFields activate_fields) {
		this.activate_fields = activate_fields;
	}
}
