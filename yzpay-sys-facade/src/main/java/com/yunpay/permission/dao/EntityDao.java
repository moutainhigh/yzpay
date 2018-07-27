package com.yunpay.permission.dao;
import java.io.Serializable;
import java.util.List;
import com.yunpay.common.core.page.PageBean;
/**
 * 
 * 功能描述：通用dao接口
 * 版权所有：至高通信技术发展有限公司
 * @author Devin_Yang 新增日期：2015年7月22日
 * @author Devin_Yang 修改日期：2015年7月22日
 *  @author duan zhang quan  修改日期：2017年7月21日
 *
 */
public interface EntityDao<T> extends MybatisBaseDao {
	int deleteByPrimaryKey(Serializable id);

	int insert(T record);

	int insertSelective(T record);

	T selectByPrimaryKey(Serializable id);

	int updateByPrimaryKeySelective(T record);

	int updateByPrimaryKey(T record);
	
	@SuppressWarnings("rawtypes")
	List<T> findByPage(PageBean page);
	
	//List<T> findAll();
	//批量删除
	void batchDeleteByIds(Object[] array);
    //查询组织机构对应的主体信息
	T selectByOrgNo(String orgNo);
	//查询组织机构对应的数据
	List<T> selectDataByOrgNo(String orgNo);
}
