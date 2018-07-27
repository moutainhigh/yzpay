package com.yunpay.serv.service.card;

import java.util.Map;

/**
 * 卡券业务回调业务处理类
 * 文件名称:     CardNotifyService.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年8月28日上午9:38:25 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年8月28日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public interface CardNotifyService {
	
	/**
	 * @Description: 卡券审核
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月9日上午11:23:34 
	 * @param recvMap
	 * @return
	 */
	public boolean cardCheck(Map<String, String> recvMap,int count);
	
	/**
	 * @Description: 用户领取
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月9日上午11:23:44 
	 * @param recvMap
	 * @return
	 */
	public boolean userGetCard(Map<String, String> recvMap);
		
	/**
	 * @Description: 用户转赠
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月9日上午11:23:52 
	 * @param recvMap
	 * @return
	 */
	public boolean userGivingCard(Map<String, String> recvMap);
	
	/**
	 * @Description:用户删除 
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月9日上午11:24:03 
	 * @param recvMap
	 * @return
	 */
	public boolean userDelCard(Map<String, String> recvMap);
	
	/**
	 * @Description: 用户卡券核销
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年8月9日下午5:21:13 
	 * @param recvMap
	 * @return
	 */
	public boolean userConsumCard(Map<String, String> recvMap);
	
	
	/**
	 * @Description:会员卡激活事件
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年9月19日下午5:37:59 
	 * @param recvMap
	 * @return
	 */
	public boolean membercardActive(Map<String,String> recvMap);
}
