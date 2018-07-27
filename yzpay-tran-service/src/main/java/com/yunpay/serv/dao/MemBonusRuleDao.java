package com.yunpay.serv.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.yunpay.database.dao.BaseDao;
import com.yunpay.serv.model.MemberBonusRule;

/**
 * 会员积分规则
 * 文件名称:     MemIntegralRuleDao.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年9月21日下午2:38:10 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年9月21日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
@Repository("memBonusRuleDao")
public interface MemBonusRuleDao  extends BaseDao<MemberBonusRule>{
	
	/**
	 * @Description:查找商户积分规则信息 
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月21日下午4:01:42 
	 * @param merchantNo
	 * @return
	 */
	@Select("select * from t_member_bonus_rule where status=1 and merchantNo=#{merchantNo}")
	public MemberBonusRule queryMerchBonusRule(@Param("merchantNo") String merchantNo);
}
