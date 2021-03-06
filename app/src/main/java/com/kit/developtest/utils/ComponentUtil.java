package com.kit.developtest.utils;

import android.text.method.ScrollingMovementMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * Created by kit on 15. 6. 29..
 */
public class ComponentUtil {
  /**
   * Is null or empty.
   *
   * @param view the view
   * @return the boolean
   */
  public static boolean isNullOrEmpty(TextView view) {
    if (view == null) {
      return true;
    }
    String message = view.getText().toString();
    return message.isEmpty();
  }

  /**
   * To string.
   *
   * @param view the view
   * @return the string
   */
  public static String toString(TextView view) {
    if (view == null) {
      return "";
    }
    return view.getText().toString();
  }

  public static void setScrollable(TextView view) {
      view.setMovementMethod(new ScrollingMovementMethod());
  }
}
