package com.yunpay.h5merch.dao.impl.card;

import org.springframework.stereotype.Repository;
import com.yunpay.h5merch.permission.dao.card.TemplateDao;
import com.yunpay.h5merch.permission.entity.card.Template;
import com.yunpay.permission.dao.impl.PermissionBaseDaoImpl;
/**
 *            会员卡模板信息DAO实现类
 * 类名称
 * 文件名称:     TemplateDaoImpl.java
 * 内容摘要: 
 * @author:   Zhao Xiaoman
 * @version:  1.0  
 * @Date:     2017年9月6日下午4:13:20
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年9月6日   Zhao Xiaoman       1.0       新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
@SuppressWarnings("rawtypes")
@Repository("templateDao")
public class TemplateDaoImpl extends PermissionBaseDaoImpl implements TemplateDao  {

	@Override
	public Template getTemplate(String merchantNo)
	{
		return super.getSqlSession().selectOne(getStatement("getTemplate"), merchantNo);
	}

	@Override
	public int insertTemplate(Template template)
	{
		return super.getSqlSession().insert(getStatement("insertTemplate"), template);
	}

	@Override
	public int deleteTemplate(String merchantNo)
	{
		return super.getSqlSession().selectOne(getStatement("deleteTemplate"), merchantNo);
	}

	@Override
	public int updateTemplate(Template template)
	{
		return super.getSqlSession().update(getStatement("updateTemplate"), template);
	}

	
}
