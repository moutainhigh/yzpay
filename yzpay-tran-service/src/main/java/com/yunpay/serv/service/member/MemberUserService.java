package com.yunpay.serv.service.member;

import com.yunpay.serv.model.Membercard;
import com.yunpay.serv.rep.Message;
import com.yunpay.serv.req.member.MemberChargeReqDto;
import com.yunpay.serv.req.member.MemberConsumReqDto;

/**
 * 会员用户业务服务接口类
 * 文件名称:     MemberUserService.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年9月20日下午5:40:25 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年9月20日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public interface MemberUserService {
	
	/**
	 * @Description: 会员卡充值
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月20日上午10:27:48 
	 * @param memberChargeReq
	 * @return
	 */
	public Message memberCharge(MemberChargeReqDto memberChargeReq,Membercard membercard);
	
	/**
	 * @Description: 会员卡消费
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月21日上午9:46:20 
	 * @param memberConsumReq
	 * @param membercard
	 * @return
	 */
	public Message memberConsum(MemberConsumReqDto memberConsumReq,Membercard membercard);
	
}
