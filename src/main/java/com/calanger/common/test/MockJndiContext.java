/*package com.calanger.common.test;

import java.util.HashMap;
import java.util.Map;

import javax.naming.NamingException;

import org.springframework.beans.factory.InitializingBean;

public class MockJndiContext
  implements InitializingBean
{
  private Map<String, Object> jndiObjects = new HashMap();
  
  public void afterPropertiesSet()
    throws IllegalArgumentException, NamingException
  {
    if (this.jndiObjects != null) {
      for (String key : this.jndiObjects.keySet()) {
        JndiMockUtils.bind(key, this.jndiObjects.get(key));
      }
    }
  }
  
  public Map<String, Object> getJndiObjects()
  {
    return this.jndiObjects;
  }
  
  public void setJndiObjects(Map<String, Object> jndiObjects)
  {
    this.jndiObjects = jndiObjects;
  }
}
*/