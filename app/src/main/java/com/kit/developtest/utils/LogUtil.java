package com.kit.developtest.utils;

import android.util.Log;

import java.util.Formatter;

/**
 * Created by kit on 15. 6. 14..
 */
public class LogUtil {
  private static boolean error = true;
  private static boolean debug = true;

  public static boolean isError() {
    return error;
  }

  public static void setError(boolean error) {
    LogUtil.error = error;
  }

  public static boolean isDebug() {
    return debug;
  }

  public static void setDebug(boolean debug) {
    LogUtil.debug = debug;
  }

  /**
   * Error Print
   */
  public static void e(String tag, String format, Object... args) {
    if (debug == false) {
      return;
    }
    @SuppressWarnings("resource")
    String msg = new Formatter().format(format, args).toString();
    Log.e(tag, msg);
  }

  /**
   * Debug Print
   */
  public static void d(String tag, String format, Object... args) {
    if (debug == false) {
      return;
    }
    @SuppressWarnings("resource")
    String msg = new Formatter().format(format, args).toString();
    Log.d(tag, msg);
  }
}
