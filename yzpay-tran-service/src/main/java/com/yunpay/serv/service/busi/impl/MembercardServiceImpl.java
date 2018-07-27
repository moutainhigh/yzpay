package com.yunpay.serv.service.busi.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunpay.serv.dao.MembercardDao;
import com.yunpay.serv.model.Membercard;
import com.yunpay.serv.service.busi.MembercardService;

/**
 * 用户会员卡数据处理实现类
 * 文件名称:     MembercardServiceImpl.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年9月19日下午4:39:34 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年9月19日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
@Service
public class MembercardServiceImpl implements MembercardService{
	
	@Autowired
	private MembercardDao membercardDao;

	/**
	 * @Description: 新增用户会员卡信息
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月19日下午4:38:07 
	 * @param membercard
	 * @return
	 */
	@Override
	public Membercard saveMembercard(Membercard membercard) {
		// TODO Auto-generated method stub
		membercardDao.insert(membercard);
		return membercard;
	}

	/**
	 * 修改用户会员卡信息
	 * @Description: 
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月19日下午4:38:18 
	 * @param membercard
	 * @return
	 */
	@Override
	public Membercard updMembercard(Membercard membercard) {
		// TODO Auto-generated method stub
		membercardDao.update(membercard);
		return membercard;
	}

	/**
	 * 修改用户会员卡状态信息
	 * @Description:
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月19日下午5:16:10 
	 * @param cardId  会员卡模板id
	 * @param userCardCode 用户微信会员卡号
	 * @param status  状态
	 * @return
	 */
	@Override
	public boolean updMembercardStatus(String cardId, String userCardCode,
			int status) {
		// TODO Auto-generated method stub
		return membercardDao.updMembercardStatus(cardId, userCardCode, status)>0;
	}
	
	/**
	 * @Description:修改会员卡余额信息
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月22日上午10:00:16 
	 * @param id
	 * @param changeAmt
	 * @return
	 */
	@Override
	public boolean updMembercardBalance(Integer id, float changeAmt) {
		// TODO Auto-generated method stub
		return membercardDao.updMembercardBalance(id, changeAmt)>0;
	}
	
	/**
	 * @Description:修改会员卡积分信息
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月22日上午10:00:26 
	 * @param id
	 * @param changeAmt
	 * @param changeBonus
	 * @return
	 */
	@Override
	public boolean updMemBalanceAndBonus(Integer id, float changeAmt,
			Integer changeBonus) {
		// TODO Auto-generated method stub
		return membercardDao.updMemBalanceAndBonus(id, changeAmt, changeBonus)>0;
	}


	/**
	 * @Description:查询用户会员卡信息
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月19日下午7:58:31 
	 * @param cardId 会员卡模板id
	 * @param userCardCode 用户微信会员卡号
	 * @return
	 */
	@Override
	public Membercard queryMembercard(String cardId, String userCardCode) {
		// TODO Auto-generated method stub
		return membercardDao.queryMembercard(cardId, userCardCode);
	}

	/**
	 * @Description:查询商户会员充值赠送金额
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月20日下午7:02:08 
	 * @param merchantNo
	 * @param chargeAmt
	 * @return
	 */
	@Override
	public float queryChargeGiveamt(String merchantNo, float chargeAmt) {
		// TODO Auto-generated method stub
		return membercardDao.queryChargeGiveamt(merchantNo, chargeAmt);
	}

	/**
	 * @Description:查询商户会员卡信息
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月20日下午7:02:25 
	 * @param merchantNo    商户号
	 * @param userCardCode  商户会员卡号
	 * @return
	 */
	@Override
	public Membercard queryMembercardByMerNo(String merchantNo,
			String userCardCode) {
		// TODO Auto-generated method stub
		return membercardDao.queryMembercardByMerNo(merchantNo, userCardCode);
	}

	
	

}
