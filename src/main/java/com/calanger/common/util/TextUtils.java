package com.calanger.common.util;

import java.util.regex.Pattern;

public final class TextUtils
{
  private static final String HTML_REGEX = "</?[a-zA-Z][a-zA-Z0-9]*[^<>]*>";
  
  public static String stripHtml(String text)
  {
    return Pattern.compile("</?[a-zA-Z][a-zA-Z0-9]*[^<>]*>", 2).matcher(text).replaceAll("");
  }
}
