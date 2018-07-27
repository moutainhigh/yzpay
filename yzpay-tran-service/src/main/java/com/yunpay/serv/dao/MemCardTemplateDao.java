package com.yunpay.serv.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import com.yunpay.database.dao.BaseDao;
import com.yunpay.serv.model.MemberCardTemplate;


/**
 * 会员卡信息数据处理接口类
 * 文件名称:     MemCardInfoDao.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年9月11日上午11:53:10 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年9月11日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
@Repository("memCardTemplateDao")
public interface MemCardTemplateDao extends BaseDao<MemberCardTemplate>{
	
	/**
	 * @Description: 修改会员卡模板状态
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月11日上午11:54:58 
	 * @param cardId
	 * @param status
	 * @return
	 */
	@Update("update t_membercard_template set status=#{status} where card_id=#{cardId}")
	public int updMemCardStatus(@Param("cardId")String cardId,@Param("status")int status);
	
	
	/**
	 * @Description: 根据微信card_id查找会员卡模板信息
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月19日下午3:54:35 
	 * @param cardId
	 * @return
	 */
	@Select("select id,base_info_id,merchantNo from t_membercard_template where card_id=#{cardId}")
	public MemberCardTemplate findMemCardTempByWxCardId(@Param("cardId")String cardId);
	
}
