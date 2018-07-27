package com.yunpay.serv.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.yunpay.common.exception.YunPayException;
import com.yunpay.common.utils.ResultEnum.ResultCode;
import com.yunpay.wx.rep.card.AccessTokenRep;
import com.yunpay.wx.service.WechatCardService;

/**
 * 
 * 文件名称:    TokenService.java
 * 内容摘要:    获取微信access_token,该token是用来作为其他接口的访问凭证，由于
 * access_token的有效期有7200ms且微信公众平台对获取access_token的访问次数有
 * 限制，故在此对每次获取的access_token通过ehcache进行缓存，缓存过期时间设置为
 * 7000ms,这样既保证access_token在有效期内，又能因频繁调用该接口而超过微信的限制次数
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年9月1日下午3:40:58 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年9月1日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
@Service
public class TokenService {
	
	@Autowired
	private WechatCardService wechatCardService;
	
	/**
	 * @Description:获取微信access_token
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月1日上午11:12:09 
	 * @param appId
	 * @param appSecret
	 * @return
	 * @throws YunPayException
	 */
	@Cacheable(value="wxAccessToken",key="#appId")
	public String recvAccessToken(String appId,String appSecret) throws YunPayException{
		AccessTokenRep accessTokenRep = wechatCardService.doAccessToken(appId, appSecret);
		if(ResultCode.SUCCESS.getCode().equals(accessTokenRep.getReturn_code())){
			return accessTokenRep.getAccess_token();
		}
		throw new YunPayException(accessTokenRep.getErr_code(),accessTokenRep.getErr_code_des());
	}
}
