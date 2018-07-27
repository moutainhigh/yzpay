package com.yunpay.wx.service;
import com.yunpay.wx.rep.card.AppBaseRep;
import com.yunpay.wx.rep.card.CreateCardRep;
import com.yunpay.wx.rep.card.UpdCardRep;
import com.yunpay.wx.rep.member.MemUserInfoRep;
import com.yunpay.wx.rep.member.MemberUpdRep;
import com.yunpay.wx.req.member.ActivateFieldReq;
import com.yunpay.wx.req.member.MemCardUpdReq;
import com.yunpay.wx.req.member.MemberCardReq;
import com.yunpay.wx.req.member.MemberUpdReq;

/**
 * 
 * 文件名称:    WechatMemberService.java
 * 内容摘要:    微信会员卡业务接口类
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年8月31日上午11:26:17 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年8月31日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public interface WechatMemberService {
	
	/**
	 * @Description:创建会员卡 
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月31日上午11:26:53 
	 * @param memberCardReq
	 * @param accessToken
	 * @return
	 */
	public CreateCardRep doCreateMemberCard(MemberCardReq memberCardReq,String accessToken);
	
	
	/**
	 * @Description: 会员卡信息修改
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年10月13日下午2:23:44 
	 * @param memCardUpdReq
	 * @param accessToken
	 * @return
	 */
	public UpdCardRep doUpdMemberCard(MemCardUpdReq memCardUpdReq,String accessToken);
	
	/**
	 * @Description: 设置会员卡激活参数（用于微信一键激活）
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月31日上午11:27:28 
	 * @param accessToken
	 * @return
	 */
	public AppBaseRep doActivateSet(ActivateFieldReq activateFieldReq,String accessToken);
	
	/**
	 * @Description: 获取会员信息
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月19日下午6:34:11 
	 * @param cardId
	 * @param userCardCode
	 * @param accessToken
	 * @return
	 */
	public MemUserInfoRep doGetUserInfo(String cardId,String userCardCode,String accessToken);
	
	/**
	 * @Description: 修改会员信息
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月28日下午5:08:51 
	 * @param memberUpdReq
	 * @return
	 */
	public MemberUpdRep doUpdMember(MemberUpdReq memberUpdReq,String accessToken);
}
