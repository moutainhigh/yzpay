package com.yunpay.serv.service.member;
import com.yunpay.serv.rep.Message;
import com.yunpay.serv.req.member.MemActivateReqDto;
import com.yunpay.serv.req.member.MemCardUpdReqDto;
import com.yunpay.serv.req.member.MemQrcodeReqDto;
import com.yunpay.serv.req.member.MemberCardReqDto;




/**
 * 会员卡服务接口类
 * 文件名称:     MemberCardService.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年8月28日上午9:37:34 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年8月28日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public abstract class MemberCardService {
	
	/**
	 * @Description: 创建会员卡
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月28日上午9:39:08 
	 * @param memberReq
	 * @return
	 */
	public abstract Message createMemberCard(MemberCardReqDto memberCard);
	
	/**
	 * @Description: 创建会员卡
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月28日上午9:39:08 
	 * @param memberReq
	 * @return
	 */
	public abstract Message updMemberCard(MemCardUpdReqDto memberCard);
	
	/**
	 * @Description: 设置微信会员卡一键激活参数
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月1日上午9:38:35 
	 * @param activateStr
	 * @param merchantNum
	 * @return
	 */
	public abstract Message createActivateParam(MemActivateReqDto memActivate);
	
	/**
	 * @Description: 会员卡领取二维码
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月5日下午3:51:40 
	 * @param merchantNum
	 * @param cardId
	 * @return
	 */
	public abstract Message qrCodeCreate(MemQrcodeReqDto memQrcodeReq);
	
}
