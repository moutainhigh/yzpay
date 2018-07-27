package com.yunpay.permission.service;
import java.util.List;
import com.yunpay.common.core.page.PageBean;
import com.yunpay.permission.dao.EntityDao;
/**
 * 
 * 功能描述：通用业务接口,开发过程中通用的方法可以在此添加,特殊的方法放到具体的service里面实现
 * 版权所有：至高通信技术发展有限公司
 * @author Devin_Yang 新增日期：2015年7月22日
 * @author Devin_Yang 修改日期：2015年7月22日
 * @author Duan zhang quan 修改日期：2017年7月21日
 */
public abstract class EntityService<T> extends BaseService {
	public abstract EntityDao<T> getDao();

	public T findById(Integer id) {
		return getDao().selectByPrimaryKey(id);
	}

	@SuppressWarnings("rawtypes")
	public List<T> findByPage(PageBean page) {
		return getDao().findByPage(page);
	}

	public void delete(Integer id) {
		getDao().deleteByPrimaryKey(id);
	}

	public void save(T entity) {
		insert(entity);
	}

	// public void save(T entity) {
	// if (entity.getId() != null && entity.getId() > 0) {
	// update(entity);
	// } else {
	// insert(entity);
	// }
	// }

	public void insert(T entity) {
		getDao().insert(entity);
	}

	public void update(T entity) {
		getDao().updateByPrimaryKeySelective(entity);
	}
	
	public void updateByPrimaryKeySelective(T entity) {
		getDao().updateByPrimaryKeySelective(entity);
	}

	public boolean exists(Integer id) {
		if (getDao().selectByPrimaryKey(id) != null) {
			return true;
		}
		return false;
	}

	// 批量删除
	public void batchDeleteByIds(Object[] ids) {
		getDao().batchDeleteByIds(ids);
	}
	
	public T selectByOrgNo(String orgNo){
		return getDao().selectByOrgNo(orgNo);
	}
	
	public List<T> selectDataByOrgNo(String orgNo){
		return getDao().selectDataByOrgNo(orgNo);
	}
}
