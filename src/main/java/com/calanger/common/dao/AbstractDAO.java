package com.calanger.common.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.mybatis.spring.SqlSessionTemplate;


import com.google.common.collect.Maps;

public abstract class AbstractDAO<T, VO, PK extends Serializable>
  implements DAO<T, VO, PK>
{
  protected abstract SqlSessionTemplate getSqlSessionTemplate();
  
  protected abstract String getNamespace();
  
  public void insert(T entity)
  {
    Map<String, Object> params = Maps.newHashMap();
    params.put("entity", entity);
    
    getSqlSessionTemplate().insert(getNamespace() + ".insert", params);
  }
  
  public T get(VO condition)
  {
    Map<String, Object> params = Maps.newHashMap();
    params.put("condition", condition);
    
    return getSqlSessionTemplate().selectOne(getNamespace() + ".get", params);
  }
  
  public T get(PK id)
  {
    Map<String, Object> params = Maps.newHashMap();
    params.put("id", id);
    
    return getSqlSessionTemplate().selectOne(getNamespace() + ".getById", params);
  }
  
  public List<T> find()
  {
	  OrderBy orderBy = new OrderBy();
    return find(orderBy);
  }
  
  public List<T> find(OrderBy orderBy)
  {
    return find(null, orderBy, -1, -1);
  }
  
  public List<T> find(int pageSize, int pageNumber)
  {
    return find(null, null, pageSize, pageNumber);
  }
  
  public List<T> find(OrderBy orderBy, int pageSize, int pageNumber)
  {
    return find(null, orderBy, pageSize, pageNumber);
  }
  
  public List<T> find(VO condition)
  {
    return find(condition, -1, -1);
  }
  
  public List<T> find(VO condition, OrderBy orderBy)
  {
    return find(condition, orderBy, -1, -1);
  }
  
  public List<T> find(VO condition, int pageSize, int pageNumber)
  {
    return find(condition, null, pageSize, pageNumber);
  }
  
  public List<T> find(VO condition, OrderBy orderBy, int pageSize, int pageNumber)
  {
    Map<String, Object> params = Maps.newHashMap();
    params.put("condition", condition);
    if (orderBy != null) {
      params.put("orderBy", orderBy.toString());
    }
    if ((pageSize > 0) && (pageNumber > 0))
    {
      int startRow = pageSize * (pageNumber - 1) + 1;
      int endRow = pageSize * pageNumber;
      int offset = pageSize * (pageNumber - 1);
      
      params.put("startRow", Integer.valueOf(startRow));
      params.put("endRow", Integer.valueOf(endRow));
      params.put("offset", Integer.valueOf(offset));
      params.put("limit", Integer.valueOf(pageSize));
    }
    return getSqlSessionTemplate().selectList(getNamespace() + ".find", params);
  }
  
  public List<T> findByIdList(List<PK> idList, VO condition, OrderBy orderBy)
  {
    Map<String, Object> params = Maps.newHashMap();
    params.put("idList", idList);
    params.put("condition", condition);
    if (orderBy != null) {
      params.put("orderBy", orderBy.toString());
    }
    return getSqlSessionTemplate().selectList(getNamespace() + ".findByIdList", params);
  }
  
  public List<T> findByIdList(List<PK> idList, VO condition)
  {
    return findByIdList(idList, condition, null);
  }
  
  public List<T> findByIdList(List<PK> idList, OrderBy orderBy)
  {
    return findByIdList(idList, null, orderBy);
  }
  
  public List<T> findByIdList(List<PK> idList)
  {
    return findByIdList(idList, null, null);
  }
  
  public int count()
  {
    return count("*");
  }
  
  public int count(String column)
  {
    return count(null, column);
  }
  
  public int count(VO condition)
  {
    return count(condition, "*");
  }
  
  public int count(VO condition, String column)
  {
    DAOUtils.checkColumn(column);
    
    Map<String, Object> params = Maps.newHashMap();
    params.put("condition", condition);
    params.put("count_column", column);
    
    return ((Integer)getSqlSessionTemplate().selectOne(getNamespace() + ".count", params)).intValue();
  }
  
  public Map<String, Object> aggregate(String[] functions, String[] columns)
  {
    return aggregate(null, functions, columns);
  }
  
  public Map<String, Object> aggregate(VO condition, String[] functions, String[] columns)
  {
    Map<String, Object> params = Maps.newHashMap();
    params.put("condition", condition);
    params.put("aggregate_sql", DAOUtils.buildAggregateSql(functions, columns));
    
    Map<String, Object> processedResult = null;
    Map<String, Object> result = (Map)getSqlSessionTemplate().selectOne(getNamespace() + ".aggregate", params);
    if (result != null)
    {
      processedResult = Maps.newHashMapWithExpectedSize(result.size());
      for (Map.Entry<String, Object> entry : result.entrySet()) {
        processedResult.put(((String)entry.getKey()).toLowerCase(), entry.getValue());
      }
    }
    return (Map<String, Object>) (processedResult != null ? processedResult : Maps.newHashMap());
  }
  
  public int update(T entity, VO condition)
  {
    Map<String, Object> params = Maps.newHashMap();
    params.put("entity", entity);
    params.put("condition", condition);
    
    return getSqlSessionTemplate().update(getNamespace() + ".update", params);
  }
  
  public int update(T entity, PK id)
  {
    Map<String, Object> params = Maps.newHashMap();
    params.put("entity", entity);
    params.put("id", id);
    
    return getSqlSessionTemplate().update(getNamespace() + ".updateById", params);
  }
  
  public int updateByIdList(T entity, List<PK> idList, VO condition)
  {
    Map<String, Object> params = Maps.newHashMap();
    params.put("entity", entity);
    params.put("idList", idList);
    params.put("condition", condition);
    
    return getSqlSessionTemplate().update(getNamespace() + ".updateByIdList", params);
  }
  
  public int updateByIdList(T entity, List<PK> idList)
  {
    return updateByIdList(entity, idList, null);
  }
  
  public int update(Map<String, Object> entity, VO condition)
  {
    Map<String, Object> params = Maps.newHashMap();
    for (Map.Entry<String, Object> entry : entity.entrySet()) {
      params.put("entity_" + (String)entry.getKey(), entry.getValue());
    }
    params.put("condition", condition);
    
    return getSqlSessionTemplate().update(getNamespace() + ".forceUpdate", params);
  }
  
  public int update(Map<String, Object> entity, PK id)
  {
    Map<String, Object> params = Maps.newHashMap();
    for (Map.Entry<String, Object> entry : entity.entrySet()) {
      params.put("entity_" + (String)entry.getKey(), entry.getValue());
    }
    params.put("id", id);
    
    return getSqlSessionTemplate().update(getNamespace() + ".forceUpdateById", params);
  }
  
  public int updateByIdList(Map<String, Object> entity, List<PK> idList, VO condition)
  {
    Map<String, Object> params = Maps.newHashMap();
    for (Map.Entry<String, Object> entry : entity.entrySet()) {
      params.put("entity_" + (String)entry.getKey(), entry.getValue());
    }
    params.put("idList", idList);
    params.put("condition", condition);
    
    return getSqlSessionTemplate().update(getNamespace() + ".forceUpdateByIdList", params);
  }
  
  public int updateByIdList(Map<String, Object> entity, List<PK> idList)
  {
    return updateByIdList(entity, idList, null);
  }
  
  public int remove(VO condition)
  {
    Map<String, Object> params = Maps.newHashMap();
    params.put("condition", condition);
    
    return getSqlSessionTemplate().delete(getNamespace() + ".remove", params);
  }
  
  public int remove(PK id)
  {
    Map<String, Object> params = Maps.newHashMap();
    params.put("id", id);
    
    return getSqlSessionTemplate().delete(getNamespace() + ".removeById", params);
  }
  
  public int removeByIdList(List<PK> idList, VO condition)
  {
    Map<String, Object> params = Maps.newHashMap();
    params.put("idList", idList);
    params.put("condition", condition);
    
    return getSqlSessionTemplate().delete(getNamespace() + ".removeByIdList", params);
  }
  
  public int removeByIdList(List<PK> idList)
  {
    return removeByIdList(idList, null);
  }
}
