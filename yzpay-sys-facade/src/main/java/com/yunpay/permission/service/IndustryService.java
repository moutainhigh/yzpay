package com.yunpay.permission.service;
import java.util.List;
import com.yunpay.permission.entity.Industry;
/**
 * 
 * 文件名称:
 * 内容摘要: 行业信息
 * @author:   duan zhang quan
 * @version:  1.0  
 * @Date:     2017年4月14日上午9:56:12 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年4月14日     duan zhang quan   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public interface IndustryService {

	List<Industry> getIndustry();
}
