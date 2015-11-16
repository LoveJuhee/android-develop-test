package com.kit.developtest.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by kit on 15. 7. 12..
 */
public class ToastUtil {
  private static Toast sLastToast = null;

  public static boolean showLongToast(Context context, String message) {
    Toast toast = makeText(context, message, Toast.LENGTH_LONG);
    toast.show();
    sLastToast = toast;
    return true;
  }

  public static boolean showShortToast(Context context, String message) {
    Toast toast = makeText(context, message, Toast.LENGTH_SHORT);
    toast.show();
    sLastToast = toast;
    return true;
  }

  public static boolean showLongToast(Context context, String message, boolean prevStop) {
    if (prevStop) {
      if (sLastToast != null) {
        sLastToast.cancel();
      }
    }
    Toast toast = makeText(context, message, Toast.LENGTH_LONG);
    toast.show();
    sLastToast = toast;
    return true;
  }

  public static boolean showShortToast(Context context, String message, boolean prevStop) {
    if (prevStop) {
      if (sLastToast != null) {
        sLastToast.cancel();
      }
    }
    Toast toast = makeText(context, message, Toast.LENGTH_SHORT);
    toast.show();
    sLastToast = toast;
    return true;
  }

  public static Toast makeText(Context context, String message, int duration) {
    Toast toast = Toast.makeText(context, message, duration);
    return toast;
  }
}
