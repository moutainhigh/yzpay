package com.yunpay.h5merch.permission.service;

import java.util.List;

import com.yunpay.h5merch.permission.entity.card.BaseInfo;
import com.yunpay.h5merch.permission.entity.card.Commonfield;
import com.yunpay.h5merch.permission.entity.card.IntegralRule;
import com.yunpay.h5merch.permission.entity.card.RechargeRule;
import com.yunpay.h5merch.permission.entity.card.Template;
/**
 * 
 * 类名称                     会员卡业务接口
 * 文件名称:     MemberCardService.java
 * 内容摘要: 
 * @author:   Zhao Xiaoman
 * @version:  1.0  
 * @Date:     2017年9月7日下午3:07:28
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年9月7日   Zhao Xiaoman       1.0       新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public interface MemberCardService {
	
	 /**
	  * 添加会员卡
	  * @param template,baseInfo,commonfieldList
	  * @return
	  */
    public int addMemberCard(Template template, BaseInfo baseInfo,List<Commonfield> commonfieldList);
    /**
     * 添加会员卡积分规则信息
     * @param rechargeRule
     * @return
     */
    public String addRechargeRule(RechargeRule rechargeRule);
    /**
     * 添加会员卡储值规则信息
     * @param IntegralRule,Template
     * @return
     */
    public String addIntegralRule(IntegralRule integralRule,Template template);
    /**
     * 删除会员卡
     * @param merchantNo
     * @return
     */
    public int deleteMemberCard(String merchantNo);
    /**
     * 删除会员卡积分信息
     * @param merchantNo
     * @return
     */
    public int deleteIntegralRule(String merchantNo);
    /**
     * 删除会员卡 储值信息
     * @param merchantNo
     * @return
     */
    public int deleteRechargeRule(String merchantNo);
    /**
     * 根据id删除会员卡 储值信息
     * @param id
     * @return
     */
    public String deleteRechargeRuleById(Integer id);
    /**
     * 修改会员卡模板信息
     * @param memberCardInfo
     * @return
     */
    public int changeTemplate(Template template);
    /**
     * 修改会员卡基本信息
     * @param memberCardInfo
     * @return
     */
    public int changeBaseInfo(BaseInfo baseInfo);
    /**
     * 修改会员卡积分规则信息
     * @param integralRule
     * @return
     */
    public int changeIntegralRule(IntegralRule integralRule);
    /**
     * 修改会员卡激活字段信息
     * @param commonfieldList,merchantNo
     * @return
     */
    public int changeCommonfield(List<Commonfield> commonfieldList,String merchantNo);
    /**
     * 修改会员卡储值信息
     * @param rechargeRule
     * @return
     */
    public int changeRechargeRule(RechargeRule rechargeRule);
    /**
     * 查找会员卡模板信息
     * @param merchantNo
     * @return
     */
    public Template findTemplate(String merchantNo);
    /**
     * 查找会员卡基本信息
     * @param merchantNo
     * @return
     */
    public BaseInfo findBaseInfo(String merchantNo);
    /**
     * 查找会员卡积分规则信息
     * @param merchantNo
     * @return
     */
    public IntegralRule findIntegralRule(String merchantNo);
    /**
     * 查找会员卡激活字段信息
     * @param merchantNo
     * @return
     */
    public List<Commonfield> findCommonfield(String merchantNo);
    /**
     * 查找会员卡储值规则
     * @param merchantNo
     * @return
     */
    public List<RechargeRule> findRechargeRule(String merchantNo);
    /**
     * 修改会员卡信息
     * @param baseInfo template
     * @return
     */
	int changeMemberCard(BaseInfo baseInfo, Template template);
	
}
