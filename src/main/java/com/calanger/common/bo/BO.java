package com.calanger.common.bo;

import com.calanger.common.dao.OrderBy;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public abstract interface BO<T, VO, PK extends Serializable>
{
  public abstract void insert(T paramT);
  
  public abstract T get(VO paramVO);
  
  public abstract T get(PK paramPK);
  
  public abstract List<T> find();
  
  public abstract List<T> find(OrderBy paramOrderBy);
  
  public abstract List<T> find(int paramInt1, int paramInt2);
  
  public abstract List<T> find(OrderBy paramOrderBy, int paramInt1, int paramInt2);
  
  public abstract List<T> find(VO paramVO);
  
  public abstract List<T> find(VO paramVO, OrderBy paramOrderBy);
  
  public abstract List<T> find(VO paramVO, int paramInt1, int paramInt2);
  
  public abstract List<T> find(VO paramVO, OrderBy paramOrderBy, int paramInt1, int paramInt2);
  
  public abstract List<T> findByIdList(List<PK> paramList, VO paramVO, OrderBy paramOrderBy);
  
  public abstract List<T> findByIdList(List<PK> paramList, VO paramVO);
  
  public abstract List<T> findByIdList(List<PK> paramList, OrderBy paramOrderBy);
  
  public abstract List<T> findByIdList(List<PK> paramList);
  
  public abstract int count();
  
  public abstract int count(String paramString);
  
  public abstract int count(VO paramVO);
  
  public abstract int count(VO paramVO, String paramString);
  
  public abstract Map<String, Object> aggregate(String[] paramArrayOfString1, String[] paramArrayOfString2);
  
  public abstract Map<String, Object> aggregate(VO paramVO, String[] paramArrayOfString1, String[] paramArrayOfString2);
  
  public abstract int update(T paramT, VO paramVO);
  
  public abstract int update(T paramT, PK paramPK);
  
  public abstract int updateByIdList(T paramT, List<PK> paramList, VO paramVO);
  
  public abstract int updateByIdList(T paramT, List<PK> paramList);
  
  public abstract int update(Map<String, Object> paramMap, VO paramVO);
  
  public abstract int update(Map<String, Object> paramMap, PK paramPK);
  
  public abstract int updateByIdList(Map<String, Object> paramMap, List<PK> paramList, VO paramVO);
  
  public abstract int updateByIdList(Map<String, Object> paramMap, List<PK> paramList);
  
  public abstract int remove(VO paramVO);
  
  public abstract int remove(PK paramPK);
  
  public abstract int removeByIdList(List<PK> paramList, VO paramVO);
  
  public abstract int removeByIdList(List<PK> paramList);
}