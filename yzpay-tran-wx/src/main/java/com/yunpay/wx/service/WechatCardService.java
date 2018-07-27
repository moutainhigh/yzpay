package com.yunpay.wx.service;

import com.yunpay.wx.rep.card.AccessTokenRep;
import com.yunpay.wx.rep.card.AppBaseRep;
import com.yunpay.wx.rep.card.CardConsumeRep;
import com.yunpay.wx.rep.card.CreateQrCodeRep;
import com.yunpay.wx.rep.card.CreateCardRep;
import com.yunpay.wx.rep.card.QrCardCodeRep;
import com.yunpay.wx.rep.card.UpdCardRep;
import com.yunpay.wx.rep.card.UploadImgRep;
import com.yunpay.wx.req.card.CreateQrCodeReq;
import com.yunpay.wx.req.card.CardReq;
import com.yunpay.wx.req.card.QrCardCodeReq;

/**
 * 
 * 文件名称:    WechatCardService.java
 * 内容摘要:    微信卡券业务接口类
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年7月31日下午4:05:38 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年7月31日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public interface WechatCardService {
	
	/**
	 * @Description: 获取微信access_token
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月1日上午9:53:59 
	 * @param appId
	 * @param appSecret
	 * @return
	 */
	public AccessTokenRep doAccessToken(String appId,String appSecret);
	
	
	/**
	 * @Description: 微信图片上传
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月1日上午9:54:40 
	 * @param imgUrl	本地图片路径
	 * @param accessToken 
	 * @return
	 */
	public UploadImgRep doUploadImg(String imgUrl,String accessToken);
	
	/**
	 * @Description: 创建卡券
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月1日下午3:15:55 
	 * @param cardReq
	 * @param accessToken
	 * @return
	 */
	public CreateCardRep doCreateCard(CardReq cardReq,String accessToken);
	
	
	/**
	 * @Description: 卡券信息修改
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月17日上午10:24:30 
	 * @param cardReq
	 * @param accesssToken
	 * @return
	 */
	public UpdCardRep doUpdateCard(CardReq cardReq,String accesssToken);
	
	/**
	 * @Description: 获取卡券投放领取码
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月2日下午2:38:05 
	 * @param codeReq
	 * @param accessToken
	 * @return
	 */
	public CreateQrCodeRep doCreateQrCode(CreateQrCodeReq codeReq,String accessToken);

	
	/**
	 * @Description: 查询卡券核销码状态
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月10日下午4:50:49 
	 * @param qrCodeReq
	 * @param accessToken
	 * @return
	 */
	public QrCardCodeRep doQueryCardCode(QrCardCodeReq qrCodeReq,String accessToken);
	
	/**
	 * @Description: 卡券核销
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月10日下午5:11:55 
	 * @param cardCode 卡券核销码
	 * @param accessToken
	 * @return
	 */
	public CardConsumeRep doCardConsume(String cardCode,String accessToken);
	
	/**
	 * @Description: 卡券删除
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月14日上午9:53:29 
	 * @param cardId
	 * @param accessToken
	 * @return
	 */
	public AppBaseRep doCardDelete(String cardId,String accessToken);
	
	/**
	 * @Description: 公众号推送卡券
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月24日下午5:13:44 
	 * @param cardId
	 * @param accessToken
	 * @return
	 */
	public AppBaseRep doCardPush(String cardId,String accessToken);
	
	
	/**
	 * @Description: 设置微信卡券支持快速买单功能
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月30日上午9:31:07 
	 * @param cardId
	 * @param accessToken
	 * @return
	 */
	public AppBaseRep doCardPaySet(String cardId,String accessToken);
	
}
