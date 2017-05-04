/*package com.calanger.common.web.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class CookieUtils
{
  public static Cookie getCookie(HttpServletRequest request, String name)
  {
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals(name)) {
          return cookie;
        }
      }
    }
    return null;
  }
  
  public static void addCookie(HttpServletResponse response, String domain, String path, String name, String value)
  {
    addCookie(response, domain, path, name, value, -1);
  }
  
  public static void addCookie(HttpServletResponse response, String domain, String path, String name, String value, int maxAge)
  {
    Cookie cookie = new Cookie(name, value);
    cookie.setDomain(domain);
    cookie.setPath(path);
    cookie.setMaxAge(maxAge);
    response.addCookie(cookie);
  }
  
  public static void removeCookie(HttpServletResponse response, String domain, String path, String name)
  {
    addCookie(response, domain, path, name, null, 0);
  }
}
*/