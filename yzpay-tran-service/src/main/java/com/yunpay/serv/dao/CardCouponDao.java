package com.yunpay.serv.dao;

import java.util.Date;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.yunpay.database.dao.BaseDao;
import com.yunpay.serv.model.CardCoupon;

@Repository("cardCouponDao")
public interface CardCouponDao extends BaseDao<CardCoupon>{
	
	@Update("update t_card_coupon set status=#{status} where appidCardId=#{cardId} and sn=#{cardCode}")
	public int updCardCouponStatus(@Param("cardId")String cardId,@Param("cardCode")String cardCode,@Param("status")Integer status);
	
	@Update("update t_card_coupon set status=1,useTime=#{useTime} where appidCardId=#{cardId} and sn=#{cardCode}")
	public int cardCouponConsum(@Param("cardId")String cardId,@Param("cardCode")String cardCode,@Param("useTime")Date useTime);
	
}
