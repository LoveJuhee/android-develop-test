package com.kit.developtest.utils;

import com.google.gson.Gson;

/**
 * Created by kit on 15. 11. 11..
 */
public class GsonUtil {
  private static final Gson gson = new Gson();

  public static String toJson(Object object) {
    Object target = (object == null) ? "" : object;
    return gson.toJson(target);
  }
}
