package com.kit.developtest.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Fragment;
import android.app.Service;
import android.content.Context;

/**
 * Created by kit on 15. 7. 7..
 */
public class ServiceUtil {

  /**
   * Is service running.
   *
   * @param service      the service
   * @param serviceClass the service class
   * @return the boolean
   */
  public static boolean isServiceRunning(Service service, Class<?> serviceClass) {
    ActivityManager manager = (ActivityManager) service.getSystemService(Context.ACTIVITY_SERVICE);
    return isServiceRunning(serviceClass, manager);
  }

  /**
   * Is service running.
   *
   * @param context      the context
   * @param serviceClass the service class
   * @return the boolean
   */
  public static boolean isServiceRunning(Context context, Class<?> serviceClass) {
    ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
    return isServiceRunning(serviceClass, manager);
  }

  /**
   * Is service running.
   *
   * @param fragment     the fragment
   * @param serviceClass the service class
   * @return the boolean
   */
  public static boolean isServiceRunning(Fragment fragment, Class<?> serviceClass) {
    return isServiceRunning(fragment.getActivity(), serviceClass);
  }

  /**
   * Is service running.
   *
   * @param activity     the activity
   * @param serviceClass the service class
   * @return the boolean
   */
  public static boolean isServiceRunning(Activity activity, Class<?> serviceClass) {
    ActivityManager manager = (ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE);
    return isServiceRunning(serviceClass, manager);
  }

  /**
   * 서비스 구동중인지 확인.
   *
   * @param serviceClass
   * @param manager
   * @return
   */
  private static boolean isServiceRunning(Class<?> serviceClass, ActivityManager manager) {
    for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
      if (serviceClass.getName().equals(service.service.getClassName())) {
        return true;
      }
    }
    return false;
  }
}
