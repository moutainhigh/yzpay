package com.yunpay.h5merch.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.yunpay.h5merch.permission.dao.card.BaseInfoDao;
import com.yunpay.h5merch.permission.dao.card.CommonfieldDao;
import com.yunpay.h5merch.permission.dao.card.IntegralRuleDao;
import com.yunpay.h5merch.permission.dao.card.RechargeRuleDao;
import com.yunpay.h5merch.permission.dao.card.TemplateDao;
import com.yunpay.h5merch.permission.entity.card.BaseInfo;
import com.yunpay.h5merch.permission.entity.card.Commonfield;
import com.yunpay.h5merch.permission.entity.card.IntegralRule;
import com.yunpay.h5merch.permission.entity.card.RechargeRule;
import com.yunpay.h5merch.permission.entity.card.Template;
import com.yunpay.h5merch.permission.service.MemberCardService;
import com.yunpay.permission.dao.MerchDao;
/**
 * 
 * 类名称                     会员卡业务接口实现类
 * 文件名称:     MemberCardServiceImpl.java
 * 内容摘要: 
 * @author:   Zhao Xiaoman
 * @version:  1.0  
 * @Date:     2017年9月7日下午3:28:55
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年9月7日   Zhao Xiaoman       1.0       新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
@Service("memberCardService")
public class MemberCardServiceImpl implements MemberCardService {

	@Autowired
	MerchDao merchDao;
	@Autowired
	TemplateDao templateDao;
	@Autowired
	BaseInfoDao baseInfoDao;
	@Autowired
	IntegralRuleDao integralRuleDao;
	@Autowired
	RechargeRuleDao rechargeRuleDao;
	@Autowired
	CommonfieldDao commonfieldDao;
	
	@Transactional
	@Override
	public int addMemberCard(Template template, BaseInfo baseInfo,List<Commonfield> commonfieldList) 
	{
		baseInfo.setBrandName(merchDao.getMerchInfo(template.getMerchantNo()).getMerchantName());
		try
		{
			
			if(baseInfoDao.insertBaseInfo(baseInfo)>0)
			{
				template.setBaseInfoId(baseInfo.getId());
				if (templateDao.insertTemplate(template)>0)
				{
					Integer templateId = template.getId();
					if (commonfieldList.size()>0)
					{
						for(int i=0;i<commonfieldList.size();i++){
							commonfieldList.get(i).setTemplateId(templateId);
						}
						return commonfieldDao.insertCommonfield(commonfieldList);
					} else
					{
						return 1;
					}
				}else{
					return -1;
				}
			}else{
				return -1;
			}	
		} catch (Exception e)
		{		
			throw e;
		}		
	}
	@Transactional
	@Override
	public String addIntegralRule(IntegralRule integralRule,Template template) 
	{
		String message = null;
		if (integralRuleDao.getIntegralRule(integralRule.getMerchantNo()) == null)
		{
			integralRule.setStatus(1);
			if (integralRuleDao.insertIntegralRule(integralRule)>0)
			{
				message = "SUCCESS";
			}else {
				message = "积分规则设置失败：系统错误，请待会再尝试";
			}
			return message;
		} else
		{
			if (integralRuleDao.updateIntegralRule(integralRule)>0)
			{
				if (template !=null && templateDao.updateTemplate(template) > 0 )
				{
					message = "SUCCESS";					
				}else {
					message = "积分规则修改失败：系统错误，请待会再尝试";
				}
			}else {
				message = "积分规则修改失败：系统错误，请待会再尝试";
			}
			return message;
		}
	}
	
	@Override
	public String addRechargeRule(RechargeRule rechargeRule) 
	{
		String message = null;
		rechargeRule.setStatus(1);
		if (rechargeRuleDao.insertRechargeRule(rechargeRule)>0)
		{
			message = "SUCCESS";
		}else {
			message = "充值规则设置失败：系统错误，请待会再尝试";
		}
		return message;
	}

	@Transactional
	@Override
	public int deleteMemberCard(String merchantNo)
	{
		try
		{
			Template template = templateDao.getTemplate(merchantNo);
			Integer templateId = template.getId();
			Integer baseInfoId = template.getBaseInfoId();
			if (commonfieldDao.deleteCommonfield(templateId)>0)
			{
				if (templateDao.deleteTemplate(merchantNo)>0)
				{
					if (baseInfoDao.deleteBaseInfo(baseInfoId)>0)
					{
						return 1;
					} else
					{
						return -1;
					}
				} else
				{
					return -1;
				}
			} else
			{
				return -1;
			}
		} catch (Exception e)
		{
			throw e;
		}
	}
	
	@Override
	public int deleteIntegralRule(String merchantNo)
	{
		return integralRuleDao.deleteIntegralRule(merchantNo);
	}
	@Override
	public int deleteRechargeRule(String merchantNo)
	{
		return rechargeRuleDao.deleteRechargeRule(merchantNo);
	}
	@Override
	public String deleteRechargeRuleById(Integer id)
	{
		String message = null;
		if (rechargeRuleDao.getRechargeRuleById(id) == null)
		{
			message = "该信息不存在";
		} else
		{
			if (rechargeRuleDao.deleteRechargeRuleById(id) > 0)
			{
				message = "SUCCESS";
			}else {
				message = "删除信息失败，请待会再尝试";
			}
		}
		return message;
	}

	@Override
	public int changeTemplate(Template template)
	{
		return templateDao.updateTemplate(template);
	}
	@Override
	public int changeBaseInfo(BaseInfo baseInfo)
	{
		return baseInfoDao.updateBaseInfo(baseInfo);
	}
	@Override
	public int changeMemberCard(BaseInfo baseInfo,Template template)
	{
		Integer flag = -1;
		if (baseInfoDao.updateBaseInfo(baseInfo)>0 && templateDao.updateTemplate(template) >0)
		{
			flag = 1;
		} 
		return flag;
	}

	@Override
	public int changeIntegralRule(IntegralRule integralRule)
	{
		return integralRuleDao.updateIntegralRule(integralRule);
	}
	@Transactional
	@Override
	public int changeCommonfield(List<Commonfield> commonfieldList,String merchantNo)
	{
		Integer templateId = templateDao.getTemplate(merchantNo).getId();
		try
		{
			if (commonfieldDao.deleteCommonfield(templateId)>0)
			{
				for(int i=0;i<commonfieldList.size();i++){
					commonfieldList.get(i).setTemplateId(templateId);
				}
				return commonfieldDao.insertCommonfield(commonfieldList);
			} else
			{
				return -1;
			}
		} catch (Exception e)
		{
			throw e;
		}
	}
	@Override
	public int changeRechargeRule(RechargeRule rechargeRule)
	{
		return rechargeRuleDao.updateRechargeRule(rechargeRule);
	}

	@Override
	public Template findTemplate(String merchantNo)
	{
		return templateDao.getTemplate(merchantNo);
	}

	@Override
	public BaseInfo findBaseInfo(String merchantNo)
	{
		Integer id = templateDao.getTemplate(merchantNo).getBaseInfoId();
		return baseInfoDao.getBaseInfo(id);
	}

	@Override
	public IntegralRule findIntegralRule(String merchantNo)
	{
		return integralRuleDao.getIntegralRule(merchantNo);
	}

	@Override
	public List<Commonfield> findCommonfield(String merchantNo)
	{
		Integer templateId = templateDao.getTemplate(merchantNo).getId();
		return commonfieldDao.getCommonfield(templateId);
	}		
	@Override
	public List<RechargeRule> findRechargeRule(String merchantNo)
	{
		return rechargeRuleDao.getRechargeRule(merchantNo);
	}		
}
