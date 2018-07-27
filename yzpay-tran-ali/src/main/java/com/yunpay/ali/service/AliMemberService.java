package com.yunpay.ali.service;

import com.yunpay.ali.rep.member.AccessTokenRep;
import com.yunpay.ali.rep.member.MemCardQrcodeRep;
import com.yunpay.ali.rep.member.MemCardRep;
import com.yunpay.ali.rep.member.MemUserCardOpenRep;
import com.yunpay.ali.rep.member.MemUserFieldQryRep;
import com.yunpay.ali.rep.pay.AliQrRep;
import com.yunpay.ali.req.member.AccessTokenReq;
import com.yunpay.ali.req.member.MemCardQrcodeReq;
import com.yunpay.ali.req.member.MemCardQryReq;
import com.yunpay.ali.req.member.MemCardReq;
import com.yunpay.ali.req.member.MemCardFieldReq;
import com.yunpay.ali.req.member.MemUserCardOpenReq;
import com.yunpay.ali.req.member.MemUserFieldQryReq;


/**
 * 文件名称:    AliMemberService.java
 * 内容摘要:    支付宝会员接口封装类
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年9月12日上午11:31:09 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年9月12日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public interface AliMemberService {
	
	/**
	 * @Description: 创建支付宝会员卡模板信息
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月12日下午4:09:42 
	 * @param req
	 * @return
	 */
	public MemCardRep doCreateCardTemp(MemCardReq req);
	
	/**
	 * @Description: 支付宝卡券模板查询
	 * @author:   Zeng Dongcheng
	 * @Date:     2018年3月6日上午10:45:56 
	 * @param req
	 * @return
	 */
	public MemCardRep doQueryCardTemp(MemCardQryReq req);
	
	/**
	 * @Description: 设置会员卡开卡参数
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月12日下午4:42:33 
	 * @param req
	 * @return
	 */
	public AliQrRep doCardTempFieldSet(MemCardFieldReq req);
	
	
	/**
	 * @Description: 获取会员卡领卡链接 
	 * @author:   Zeng Dongcheng
	 * @Date:     2018年3月6日下午2:09:35 
	 * @param req
	 * @return
	 */
	public MemCardQrcodeRep  doCardTempQrcode(MemCardQrcodeReq req);
	
	/**
	 * @Description: 获取access_token和userId
	 * @author:   Zeng Dongcheng
	 * @Date:     2018年3月7日上午11:25:23 
	 * @param req
	 * @return
	 */
	public AccessTokenRep doAccessToken(AccessTokenReq req);
	
	
	/**
	 * @Description:查询用户授权时填写的表单信息 
	 * @author:   Zeng Dongcheng
	 * @Date:     2018年3月7日下午1:55:58 
	 * @param req
	 * @return
	 */
	public MemUserFieldQryRep doMemUserFieldQry(MemUserFieldQryReq req);
	
	
	/**
	 * @Description: 会员卡开卡
	 * @author:   Zeng Dongcheng
	 * @Date:     2018年3月7日下午2:44:46 
	 * @param req
	 * @return
	 */
	public MemUserCardOpenRep doMemUserCardOpen(MemUserCardOpenReq req);
}
