package com.yunpay.h5merch.permission.dao.card;

import com.yunpay.h5merch.permission.entity.card.Template;
import com.yunpay.permission.dao.PermissionBaseDao;
/**
 * 
 * 类名称                     会员卡模板Dao
 * 文件名称:     TemplateDao
 * 内容摘要: 
 * @author:   Zhao Xiaoman
 * @version:  1.0  
 * @Date:     2017年9月6日下午2:40:10
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
public interface TemplateDao extends PermissionBaseDao {
	/**
	 * 根据商户号查询模板信息
	 * @param merchantNo
	 * @return
	 */
	public Template getTemplate(String merchantNo);
	/**
	 * 增加会员卡模板信息
	 * @param template
	 * @return
	 */
	public int insertTemplate(Template template);

	/**
	 * 根据会员卡商户号删除模板信息
	 * @param merchantNo
	 * @return
	 */
	public int deleteTemplate(String merchantNo);
	/**
	 * 修改会员卡模板信息
	 * @param Template
	 * @return
	 */
	public int updateTemplate(Template template);	
}
