package com.yunpay.h5merch.permission.service;

import java.util.List;
import java.util.Map;

import com.yunpay.h5merch.permission.entity.card.UserCard;
import com.yunpay.h5merch.permission.entity.card.UserCardNum;
import com.yunpay.h5merch.permission.entity.card.UserReAndCon;
/**
 * 
 * 类名称                     会员的卡的业务类
 * 文件名称:     UserCardService.java
 * 内容摘要: 
 * @author:   Zhao Xiaoman
 * @version:  1.0  
 * @Date:     2017年10月17日下午1:31:33
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年10月17日   Zhao Xiaoman       1.0       新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public interface UserCardService {
	
	 /**
	  * 查找会员的卡列表
	  * @param merchantNo,pageIndex
	  * @return
	  */
    public List<UserCard> findUserCardList(String merchantNo,Integer page);
    /**
     * 查找会员的卡的信息
     * @param merchantNo
     * @return
     */
    public UserCardNum findUserCardNum(String merchantNo);
    /**
     * 查找会员的卡消费充值信息
     * @param Map<String, String> core
     * @return
     */
    public Map<String, Double> findOther(Map<String, String> core);
    /**
     * 根据会员卡的ID查找会员的卡信息
     * @param id
     * @return
     */
    public UserCard findUserCard(Integer id);
    /**
     * 根据会员卡的ID查找会员的卡信息
     * @param data
     * @return
     */
    public UserCard findUserCardByUser(Map<String, String> data);
    /**
     * 根据卡号查找消费充值记录列表
     * @param data
     * @return
     */
    public List<UserReAndCon> findUserReAndCon(Map<String, String> data);
}
