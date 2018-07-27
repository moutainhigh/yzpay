package com.yunpay.serv.dao;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import com.yunpay.serv.model.PfbankConfig;

@Repository("pfbankConfigDao")
public interface PfbankConfigDao {
	
	@Select("select * from t_pfbank_config where merchantNo=#{merchantNo}")
    public PfbankConfig findConfigByMerNo(String merchantNo);
}
