
package com.yunpay.permission.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.yunpay.common.core.exception.BizException;
import com.yunpay.common.core.page.PageBean;
import com.yunpay.common.core.page.PageParam;
import com.yunpay.permission.dao.PermissionBaseDao;
import com.yunpay.permission.entity.PermissionBaseEntity;

/**
 * 数据访问层基础支撑类.
 *
 * 深圳市前海汇商惠电子商务有限公司
 * 
 * 
 */
public abstract class PermissionBaseDaoImpl<T extends PermissionBaseEntity> extends SqlSessionDaoSupport implements PermissionBaseDao<T> {

    protected static final Log LOG = LogFactory.getLog(PermissionBaseDaoImpl.class);

    public static final String SQL_INSERT = "insert";
    public static final String SQL_BATCH_INSERT = "batchInsert";
    public static final String SQL_UPDATE_BY_ID = "update";
    public static final String SQL_BATCH_UPDATE_BY_IDS = "batchUpdateByIds";
    public static final String SQL_BATCH_UPDATE_BY_COLUMN = "batchUpdateByColumn";
    public static final String SQL_SELECT_BY_ID = "selectByPrimaryKey";
    public static final String SQL_LIST_BY_COLUMN = "listByColumn";
    public static final String SQL_COUNT_BY_COLUMN = "getCountByColumn";
    public static final String SQL_DELETE_BY_ID = "deleteByPrimaryKey";
    public static final String SQL_BATCH_DELETE_BY_IDS = "batchDeleteByIds";
    public static final String SQL_BATCH_DELETE_BY_COLUMN = "batchDeleteByColumn";
    public static final String SQL_LIST_PAGE = "listPage";
    public static final String SQL_LIST_BY = "listBy";
    public static final String SQL_LIST_ALL = "listAll"; // 查询表的所有数据
    public static final String SQL_LIST_PAGE_COUNT = "listPageCount";
    public static final String SQL_COUNT_BY_PAGE_PARAM = "countByPageParam"; // 根据当前分页参数进行统计
   
    

    /**
     * 注入SqlSessionTemplate实例(要求Spring中进行SqlSessionTemplate的配置).
     * 可以调用sessionTemplate完成数据库操作.
     */
    @Autowired
    private SqlSessionTemplate sessionTemplate;

    public SqlSessionTemplate getSessionTemplate() {
        return sessionTemplate;
    }

    public void setSessionTemplate(SqlSessionTemplate sessionTemplate) {
        this.sessionTemplate = sessionTemplate;
    }

    public SqlSession getSqlSession() {
        return super.getSqlSession();
    }

    /**
     * 单条插入数据.
     */
    public int insert(T entity) {
        int result = sessionTemplate.insert(getStatement(SQL_INSERT), entity);
        if (result <= 0) {
            throw BizException.DB_INSERT_RESULT_0.newInstance("数据库操作,insert返回0.{%s}", getStatement(SQL_INSERT));
        }
        return result;
    }

    /**
     * 批量插入数据.
     */
    public int insert(List<T> list) {
        if (list.isEmpty() || list.size() <= 0) {
            return 0;
        }
        int result = sessionTemplate.insert(getStatement(SQL_BATCH_INSERT), list);
        if (result <= 0) {
            throw BizException.DB_INSERT_RESULT_0.newInstance("数据库操作,batchInsert返回0.{%s}", getStatement(SQL_BATCH_INSERT));
        }
        return result;
    }

    /**
     * 根据id单条更新数据.
     */
    public int update(T entity) {
        entity.setEditTime(new Date());
        int result = sessionTemplate.update(getStatement(SQL_UPDATE_BY_ID), entity);
        if (result <= 0) {
            throw BizException.DB_UPDATE_RESULT_0.newInstance("数据库操作,updateByPrimaryKey返回0.{%s}", getStatement(SQL_UPDATE_BY_ID));
        }
        return result;
    }

    /**
     * 根据id批量更新数据.
     */
    public int update(List<T> list) {
        if (list.isEmpty() || list.size() <= 0) {
            return 0;
        }
        int result = sessionTemplate.update(getStatement(SQL_BATCH_UPDATE_BY_IDS), list);
        if (result <= 0) {
            throw BizException.DB_UPDATE_RESULT_0.newInstance("数据库操作,batchUpdateByIds返回0.{%s}", getStatement(SQL_BATCH_UPDATE_BY_IDS));
        }
        return result;
    }

    /**
     * 根据column批量更新数据.
     */
    public int update(Map<String, Object> paramMap) {
        if (paramMap == null) {
            return 0;
        }
        int result = sessionTemplate.update(getStatement(SQL_BATCH_UPDATE_BY_COLUMN), paramMap);
        if (result <= 0) {
            throw BizException.DB_UPDATE_RESULT_0.newInstance("数据库操作,batchUpdateByColumn返回0.{%s}", getStatement(SQL_BATCH_UPDATE_BY_COLUMN));
        }
        return result;
    }

    /**
     * 根据id查询数据.
     */
    public T getById(Long id) {
        return sessionTemplate.selectOne(getStatement(SQL_SELECT_BY_ID), id);
    }
    
    public List<T> listAll(){
    	return this.sessionTemplate.selectList(getStatement(SQL_LIST_ALL));
    }

    /**
     * 根据column查询数据.
     */
    public T getByColumn(Map<String, Object> paramMap) {
        if (paramMap == null) {
            return null;
        }
        return sessionTemplate.selectOne(getStatement(SQL_LIST_BY_COLUMN), paramMap);
    }

    /**
     * 根据条件查询 getBy: selectOne <br/>
     * 
     * @param paramMap
     * @return
     */
    public T getBy(Map<String, Object> paramMap) {
        if (paramMap == null) {
            return null;
        }
        return sessionTemplate.selectOne(getStatement(SQL_LIST_BY), paramMap);
    }

    /**
     * 根据条件查询列表数据.
     */
    public List<T> listBy(Map<String, Object> paramMap) {
        if (paramMap == null) {
            return null;
        }
        return sessionTemplate.selectList(getStatement(SQL_LIST_BY), paramMap);
    }

    /**
     * 根据column查询列表数据.
     */
    public List<T> listByColumn(Map<String, Object> paramMap) {
        if (paramMap == null) {
            return null;
        }
        return sessionTemplate.selectList(getStatement(SQL_LIST_BY_COLUMN), paramMap);
    }

    /**
     * 根据column查询记录数.
     */
    public Long getCountByColumn(Map<String, Object> paramMap) {
        if (paramMap == null) {
            return null;
        }
        return sessionTemplate.selectOne(getStatement(SQL_COUNT_BY_COLUMN), paramMap);
    }

    /**
     * 根据id删除数据.
     */
    public int delete(Long id) {
        return (int) sessionTemplate.delete(getStatement(SQL_DELETE_BY_ID), id);
    }

    /**
     * 根据id批量删除数据.
     */
    public int delete(List<T> list) {
        if (list.isEmpty() || list.size() <= 0) {
            return 0;
        } else {
            return (int) sessionTemplate.delete(getStatement(SQL_BATCH_DELETE_BY_IDS), list);
        }
    }

    /**
     * 根据column批量删除数据.
     */
    public int delete(Map<String, Object> paramMap) {
        if (paramMap == null) {
            return 0;
        } else {
            return (int) sessionTemplate.delete(getStatement(SQL_BATCH_DELETE_BY_COLUMN), paramMap);
        }
    }

    /**
     * 分页查询数据 .
     */
    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) {
        // 统计总记录数
        int totalCount = sessionTemplate.selectOne(getStatement(SQL_LIST_PAGE_COUNT), paramMap);       
        // 校验当前页数
        int currentPage =  PageBean.checkCurrentPage(totalCount, pageParam.getPageSize(), pageParam.getPageCurrent());
        // 为当前页重新设值
        pageParam.setPageCurrent(currentPage); 
        //校验页面输入的每页记录数numPerPage是否合法
        int numPerPage = PageBean.checkNumPerPage(pageParam.getPageSize()); // 校验每页记录数
        pageParam.setPageSize(numPerPage); // 重新设值        
        // 根据页面传来的分页参数构造SQL分页参数
        paramMap.put("pageIndex", (pageParam.getPageCurrent() - 1) * pageParam.getPageSize());      //当前页数
        paramMap.put("pageSize", pageParam.getPageSize());            //每页显示多少条数据
        // 获取分页数据集
        List<Object> list = sessionTemplate.selectList(getStatement(SQL_LIST_PAGE), paramMap);        
        Object isCount = paramMap.get("isCount"); // 是否统计当前分页条件下的数据：1:是，其他为否
        if (isCount != null && "1".equals(isCount.toString())) {
            Map<String, Object> countResultMap = sessionTemplate.selectOne(getStatement(SQL_COUNT_BY_PAGE_PARAM), paramMap);
            return new PageBean(pageParam.getPageCurrent(), pageParam.getPageSize(), totalCount, list, countResultMap);
        } else {
            // 构造分页对象
            return new PageBean(pageParam.getPageCurrent(), pageParam.getPageSize(), totalCount, list);
        }
    }
    
    /**
     * 按条件分页查询数据
     */
  
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public PageBean listPage(PageParam pageParam,String count,String page, Map<String, Object> paramMap) {
        if (paramMap == null) {
            paramMap = new HashMap<String, Object>();
        }

        // 统计总记录数
        Long totalCount = sessionTemplate.selectOne(getStatement(count), paramMap);
        
        // 校验当前页数
        int currentPage = PageBean.checkCurrentPage(totalCount.intValue(), pageParam.getPageSize(), pageParam.getPageCurrent());
        pageParam.setPageCurrent(currentPage); // 为当前页重新设值
        // 校验页面输入的每页记录数numPerPage是否合法
        int numPerPage = PageBean.checkNumPerPage(pageParam.getPageSize()); // 校验每页记录数
        pageParam.setPageSize(numPerPage); // 重新设值

        // 根据页面传来的分页参数构造SQL分页参数
        paramMap.put("pageFirst", (pageParam.getPageCurrent() - 1) * pageParam.getPageSize());      //从多少条数据开始
        paramMap.put("pageSize", pageParam.getPageSize());                                          //返回多少条数据
        paramMap.put("startRowNum", (pageParam.getPageCurrent() - 1) * pageParam.getPageSize() + 1);//开始的行数
        paramMap.put("endRowNum", pageParam.getPageCurrent() * pageParam.getPageSize());            //结束的行数

        // 获取分页数据集
        List<Object> list = sessionTemplate.selectList(getStatement(page), paramMap);
        
        Object isCount = paramMap.get("isCount"); // 是否统计当前分页条件下的数据：1:是，其他为否
        if (isCount != null && "1".equals(isCount.toString())) {
            Map<String, Object> countResultMap = sessionTemplate.selectOne(getStatement(SQL_COUNT_BY_PAGE_PARAM), paramMap);
            return new PageBean(pageParam.getPageCurrent(), pageParam.getPageSize(), totalCount.intValue(), list, countResultMap);
        } else {
            // 构造分页对象
            return new PageBean(pageParam.getPageCurrent(), pageParam.getPageSize(), totalCount.intValue(), list);
        }
    }
    
    /**
     * 分页查询商户的数据 .
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public PageBean listmerch(PageParam pageParam, Map<String, Object> paramMap) {
        if (paramMap == null) {
            paramMap = new HashMap<String, Object>();
        }
        // 统计总记录数
        Long totalCount = sessionTemplate.selectOne(getStatement("listMerchCount"), paramMap);
        
        // 校验当前页数
        int currentPage = PageBean.checkCurrentPage(totalCount.intValue(), pageParam.getPageSize(), pageParam.getPageCurrent());
        pageParam.setPageCurrent(currentPage); // 为当前页重新设值
        
        // 校验页面输入的每页记录数numPerPage是否合法
        int numPerPage = PageBean.checkNumPerPage(pageParam.getPageSize()); // 校验每页记录数
        pageParam.setPageSize(numPerPage); // 重新设值

        // 根据页面传来的分页参数构造SQL分页参数
        paramMap.put("pageFirst", (pageParam.getPageCurrent() - 1) * pageParam.getPageSize());      //从多少条数据开始
        paramMap.put("pageSize", pageParam.getPageSize());                                          //返回多少条数据
        paramMap.put("startRowNum", (pageParam.getPageCurrent() - 1) * pageParam.getPageSize() + 1);//开始的行数
        paramMap.put("endRowNum", pageParam.getPageCurrent() * pageParam.getPageSize());            //结束的行数

        // 获取分页数据集
        List<Object> list = sessionTemplate.selectList(getStatement("listMerch"), paramMap);
        
        Object isCount = paramMap.get("isCount"); // 是否统计当前分页条件下的数据：1:是，其他为否
        if (isCount != null && "1".equals(isCount.toString())) {
            Map<String, Object> countResultMap = sessionTemplate.selectOne(getStatement(SQL_COUNT_BY_PAGE_PARAM), paramMap);
            return new PageBean(pageParam.getPageCurrent(), pageParam.getPageSize(), totalCount.intValue(), list, countResultMap);
        } else {
            // 构造分页对象
            return new PageBean(pageParam.getPageCurrent(), pageParam.getPageSize(), totalCount.intValue(), list);
        }
    }

    /**
     * 函数功能说明 ： 获取Mapper命名空间. 修改者名字： Along 修改日期： 2016-1-8 修改内容：
     * 
     * @参数：@param sqlId
     * @参数：@return
     * @return：String
     * @throws
     */
    public String getStatement(String sqlId) {
        String name = this.getClass().getName();
        // 单线程用StringBuilder，确保速度；多线程用StringBuffer,确保安全
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(".").append(sqlId);
        System.out.println(sb.toString());
        return sb.toString();
    }
    
    
	@SuppressWarnings("resource")
	public <E> List<E> getMapper(String statement){
		ApplicationContext ac = new FileSystemXmlApplicationContext("classpath:spring/spring-mybatis.xml"); 
		org.mybatis.spring.SqlSessionTemplate session = (org.mybatis.spring.SqlSessionTemplate)ac.getBean("sessionTemplate");
		//List<com.yunpay.permission.entity.Address> address = 
		return session.selectList(getStatement(statement));
		/*for(int i=0; i<address.size(); i++){
			System.out.println("name***"+address.get(i).getName());
		}*/
	}

}
