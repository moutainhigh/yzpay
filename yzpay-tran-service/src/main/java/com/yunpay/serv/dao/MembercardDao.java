package com.yunpay.serv.dao;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.yunpay.database.dao.BaseDao;
import com.yunpay.serv.model.Membercard;

/**
 * 用户会员卡数据处理接口基类
 * 文件名称:     MembercardDao.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年9月19日下午5:14:13 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年9月19日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
@Repository("membercardDao")
public interface MembercardDao extends BaseDao<Membercard>{
	
	/**
	 * @Description: 修改用户会员状态信息
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月20日下午6:35:48 
	 * @param cardId
	 * @param userCardCode
	 * @param status
	 * @return
	 */
	@Update("update t_membercard set status=#{status} where card_id=#{cardId} and userCardCode=#{userCardCode}")
	public int updMembercardStatus(@Param("cardId")String cardId,@Param("userCardCode")String userCardCode,@Param("status")Integer status);
	
	/**
	 * @Description: 修改会员余额
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月20日下午7:52:01 
	 * @param id  会员表id
	 * @param changeAmt 修改金额（可为负数）
	 * @return
	 */
	@Update("update t_membercard set balance=balance+#{changeAmt} where id=#{id}")
	public int updMembercardBalance(@Param("id")Integer id, @Param("changeAmt")float changeAmt); 
	
	/**
	 * @Description:修改会员余额和积分 
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月22日上午9:50:43 
	 * @param id
	 * @param changeAmt
	 * @param changeBonus
	 * @return
	 */
	@Update("update t_membercard set balance=balance+#{changeAmt},bonus=bonus+#{changeBonus} where id=#{id}")
	public int updMemBalanceAndBonus(@Param("id")Integer id, 
			@Param("changeAmt")float changeAmt, @Param("changeBonus") float changeBonus);
	
	/**
	 * @Description: 根据卡券模板id和用户会员卡号查询会员信息
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月20日下午6:36:05 
	 * @param cardId
	 * @param userCardCode
	 * @return
	 */
	@Select("select * from t_membercard where card_id=#{cardId} and userCardCode=#{userCardCode}")
	public Membercard queryMembercard(@Param("cardId")String cardId,@Param("userCardCode")String userCardCode);
	
	
	/**
	 * @Description: 根据商户号和用户会员卡号查询会员信息
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月20日下午6:36:05 
	 * @param merchantNo  商户号
	 * @param userCardCode 会员卡号
	 * @return
	 */
	@Select("select * from t_membercard where merchantNo=#{merchantNo} and (userCardCode=#{userCardCode} or mobile=#{userCardCode})")
	public Membercard queryMembercardByMerNo(@Param("merchantNo")String merchantNo,@Param("userCardCode")String userCardCode);

	
	/**
	 * 
	 * @Description: 
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月20日下午6:42:58 
	 * @param chargeAmt
	 * @return
	 */
	@Select("select IFNULL(max(send),0) from t_membercard_recharge where merchantNo=#{merchantNo} "
			+ "and charge<=#{chargeAmt} and status=1")
	public float queryChargeGiveamt(@Param("merchantNo") String merchantNo,@Param("chargeAmt") float chargeAmt);
	
}
