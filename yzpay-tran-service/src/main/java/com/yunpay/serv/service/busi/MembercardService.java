package com.yunpay.serv.service.busi;

import com.yunpay.serv.model.Membercard;

/**
 * 用户会员卡数据处理接口类
 * 文件名称:     MembercardService.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年9月19日下午4:40:12 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年9月19日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public interface MembercardService {
	
	/**
	 * @Description: 新增用户会员卡信息
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月19日下午4:38:07 
	 * @param membercard
	 * @return
	 */
	public Membercard saveMembercard(Membercard membercard);
	
	/**
	 * 修改用户会员卡信息
	 * @Description: 
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月19日下午4:38:18 
	 * @param membercard
	 * @return
	 */
	public Membercard updMembercard(Membercard membercard);
	
	/**
	 * @Description: 修改用户会员卡状态
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月19日下午5:05:21 
	 * @param cardId 会员卡模板id
	 * @param userCardCode
	 * @param status
	 * @return
	 */
	public boolean updMembercardStatus(String cardId,String userCardCode,int status);
	
	/**
	 * @Description:修改会员余额信息 
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月20日下午7:48:22 
	 * @param id
	 * @param changeAmt
	 * @return
	 */
	public boolean updMembercardBalance(Integer id,float changeAmt);
	
	/**
	 * @Description:修改会员积分和余额 
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月22日上午9:59:44 
	 * @param id
	 * @param changeAmt
	 * @param changeBonus
	 * @return
	 */
	public boolean updMemBalanceAndBonus(Integer id,float changeAmt,Integer changeBonus);
	
	/**
	 * @Description: 查找用户会员卡信息
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月19日下午7:57:54 
	 * @param cardId 会员卡模板id
	 * @param userCardCode 用户会员卡号
	 * @return
	 */
	public Membercard queryMembercard(String cardId,String userCardCode);
	
	/**
	 * @Description: 查找用户会员卡信息
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月20日下午6:59:08 
	 * @param merchantNo 商户号
	 * @param userCardCode 用户会员卡号
	 * @return
	 */
	public Membercard queryMembercardByMerNo(String merchantNo,String userCardCode);
	
	/**
	 * @Description: 查询商户会员充值赠送金额
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月20日下午6:45:28 
	 * @param merchantNo 商户号
	 * @param chargeAmt  充值金额
	 * @return
	 */
	public float queryChargeGiveamt(String merchantNo,float chargeAmt);

	
}
