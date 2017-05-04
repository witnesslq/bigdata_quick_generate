package com.calanger.common.bo;

import com.calanger.common.dao.DAO;
import com.calanger.common.dao.OrderBy;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public abstract class AbstractBO<T, VO, PK extends Serializable>
  implements BO<T, VO, PK>
{
  protected abstract DAO<T, VO, PK> getDAO();
  
  public void insert(T entity)
  {
    getDAO().insert(entity);
  }
  
  public T get(VO condition)
  {
    return getDAO().get(condition);
  }
  
  public T get(PK id)
  {
    return getDAO().get(id);
  }
  
  public List<T> find()
  {
    return getDAO().find();
  }
  
  public List<T> find(OrderBy orderBy)
  {
    return getDAO().find(orderBy);
  }
  
  public List<T> find(int pageSize, int pageNumber)
  {
    return getDAO().find(pageSize, pageNumber);
  }
  
  public List<T> find(OrderBy orderBy, int pageSize, int pageNumber)
  {
    return getDAO().find(orderBy, pageSize, pageNumber);
  }
  
  public List<T> find(VO condition)
  {
    return getDAO().find(condition);
  }
  
  public List<T> find(VO condition, OrderBy orderBy)
  {
    return getDAO().find(condition, orderBy);
  }
  
  public List<T> find(VO condition, int pageSize, int pageNumber)
  {
    return getDAO().find(condition, pageSize, pageNumber);
  }
  
  public List<T> find(VO condition, OrderBy orderBy, int pageSize, int pageNumber)
  {
    return getDAO().find(condition, orderBy, pageSize, pageNumber);
  }
  
  public List<T> findByIdList(List<PK> idList, VO condition, OrderBy orderBy)
  {
    return getDAO().findByIdList(idList, condition, orderBy);
  }
  
  public List<T> findByIdList(List<PK> idList, VO condition)
  {
    return getDAO().findByIdList(idList, condition);
  }
  
  public List<T> findByIdList(List<PK> idList, OrderBy orderBy)
  {
    return getDAO().findByIdList(idList, orderBy);
  }
  
  public List<T> findByIdList(List<PK> idList)
  {
    return getDAO().findByIdList(idList);
  }
  
  public int count()
  {
    return getDAO().count();
  }
  
  public int count(String column)
  {
    return getDAO().count(column);
  }
  
  public int count(VO condition)
  {
    return getDAO().count(condition);
  }
  
  public int count(VO condition, String column)
  {
    return getDAO().count(condition, column);
  }
  
  public Map<String, Object> aggregate(String[] functions, String[] columns)
  {
    return getDAO().aggregate(functions, columns);
  }
  
  public Map<String, Object> aggregate(VO condition, String[] functions, String[] columns)
  {
    return getDAO().aggregate(condition, functions, columns);
  }
  
  public int update(T entity, VO condition)
  {
    return getDAO().update(entity, condition);
  }
  
  public int update(T entity, PK id)
  {
    return getDAO().update(entity, id);
  }
  
  public int updateByIdList(T entity, List<PK> idList, VO condition)
  {
    return getDAO().updateByIdList(entity, idList, condition);
  }
  
  public int updateByIdList(T entity, List<PK> idList)
  {
    return getDAO().updateByIdList(entity, idList);
  }
  
  public int update(Map<String, Object> entity, VO condition)
  {
    return getDAO().update(entity, condition);
  }
  
  public int update(Map<String, Object> entity, PK id)
  {
    return getDAO().update(entity, id);
  }
  
  public int updateByIdList(Map<String, Object> entity, List<PK> idList, VO condition)
  {
    return getDAO().updateByIdList(entity, idList, condition);
  }
  
  public int updateByIdList(Map<String, Object> entity, List<PK> idList)
  {
    return getDAO().updateByIdList(entity, idList);
  }
  
  public int remove(VO condition)
  {
    return getDAO().remove(condition);
  }
  
  public int remove(PK id)
  {
    return getDAO().remove(id);
  }
  
  public int removeByIdList(List<PK> idList, VO condition)
  {
    return getDAO().removeByIdList(idList, condition);
  }
  
  public int removeByIdList(List<PK> idList)
  {
    return getDAO().removeByIdList(idList);
  }
}
