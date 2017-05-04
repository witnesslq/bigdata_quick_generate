package com.calanger.common.dao;

public class OrderBy {
	  private StringBuilder sb = new StringBuilder();
	  
	  public OrderBy add(String column)
	  {
	    return add(column, true);
	  }
	  
	  public OrderBy add(String column, boolean ascend)
	  {
	    DAOUtils.checkColumn(column);
	    if (this.sb.length() > 0) {
	      this.sb.append(", ");
	    }
	    this.sb.append(column).append(ascend ? " ASC" : " DESC");
	    
	    return this;
	  }
	  
	  public String toString()
	  {
	    return this.sb.toString();
	  }
}
