package com.kit.developtest.services;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.kit.developtest.models.beacon.Beacon;
import com.kit.developtest.thirdparties.reco.BeaconRecoSdkService;
import com.kit.developtest.utils.ServiceUtil;

import java.io.Console;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * helper methods.
 */
public class BeaconIntentService extends IntentService {
  private static final String TAG = BeaconIntentService.class.getSimpleName();

  private static final String ACTION_START_BEACON_SERVICE = "com.kit.developtest.services.action.START_BEACON_SERVICE";
  private static final String ACTION_STOP_BEACON_SERVICE = "com.kit.developtest.services.action.STOP_BEACON_SERVICE";
  private static final String ACTION_EVENT_MESSAGE = "com.kit.developtest.services.action.EVENT_MESSAGE";
  private static final String ACTION_EVENT_REGIONS_ON = "com.kit.developtest.services.action.EVENT_REGION_ON";
  private static final String ACTION_EVENT_REGION_EXIT = "com.kit.developtest.services.action.EVENT_REGION_EXIT";

  private static final String EXTRA_JSON = "com.kit.developtest.services.extra.JSON";

  public static Class getBeaconServiceClass() {
    return beaconServiceClass;
  }

  public static void setBeaconServiceClass(Class beaconServiceClass) {
    BeaconIntentService.beaconServiceClass = beaconServiceClass;
  }

  public static Class beaconServiceClass = null;

  public static void startBeaconService(Context context) {
    Intent intent = new Intent(context, BeaconIntentService.class);
    intent.setAction(ACTION_START_BEACON_SERVICE);
    context.startService(intent);
  }

  public static void stopBeaconService(Context context) {
    Intent intent = new Intent(context, BeaconIntentService.class);
    intent.setAction(ACTION_STOP_BEACON_SERVICE);
    context.startService(intent);
  }

  public static void eventMessage(Context context, String json) {
    Intent intent = new Intent(context, BeaconIntentService.class);
    intent.setAction(ACTION_EVENT_MESSAGE);
    intent.putExtra(EXTRA_JSON, json);
    context.startService(intent);
  }

  public static void eventBeaconRegionsOn(Context context, List<Beacon> beaconList) {
    Gson gson = new Gson();
    Intent intent = new Intent(context, BeaconIntentService.class);
    intent.setAction(ACTION_EVENT_REGIONS_ON);
    intent.putExtra(EXTRA_JSON, gson.toJson(beaconList));
    context.startService(intent);
  }

  public static void eventBeaconRegionExit(Context context, Beacon beacon) {
    Gson gson = new Gson();
    Intent intent = new Intent(context, BeaconIntentService.class);
    intent.setAction(ACTION_EVENT_REGION_EXIT);
    intent.putExtra(EXTRA_JSON, gson.toJson(beacon));
    context.startService(intent);
  }

  public BeaconIntentService() {
    super("BeaconIntentService");
  }

  @Override
  protected void onHandleIntent(Intent intent) {
    if (intent != null) {
      final String action = intent.getAction();
      if (ACTION_START_BEACON_SERVICE.equals(action)) {
        handleActionStartBeaconService();
      } else if (ACTION_STOP_BEACON_SERVICE.equals(action)) {
        handleActionStopBeaconService();
      } else if (ACTION_EVENT_REGIONS_ON.equals(action)) {
        final String param = intent.getStringExtra(EXTRA_JSON);
        handleActionEventRegionsOn(param);
      } else if (ACTION_EVENT_REGION_EXIT.equals(action)) {
        final String param = intent.getStringExtra(EXTRA_JSON);
        handleActionEventRegionExit(param);
      } else if (ACTION_EVENT_MESSAGE.equals(action)) {
        final String param = intent.getStringExtra(EXTRA_JSON);
        handleActionEventBeaconService(param);
      }
    }
  }

  private void handleActionStartBeaconService() {
    if (getBeaconServiceClass() == null) {
      // TODO: Alert 발생.
    } else {
      startService(new Intent(getApplicationContext(), BeaconRecoSdkService.class));
    }
  }

  private void handleActionEventBeaconService(String param) {
    Log.d(TAG, param);
  }

  private void handleActionEventRegionsOn(String param) {
    Gson gson = new Gson();
    Type listOfBeaconObject = Beacon.getListOfBeaconObject();
    List<Beacon> beaconList = gson.fromJson(param, listOfBeaconObject);
    for (Beacon beacon : beaconList) {
      Log.d(TAG, beacon.toString());
    }
  }

  // TODO: Handle action EventRegionExit
  private void handleActionEventRegionExit(String param) {
    Gson gson = new Gson();
    Beacon beacon = gson.fromJson(param, Beacon.class);
  }

  private void handleActionStopBeaconService() {
    stopService(new Intent(getApplicationContext(), BeaconRecoSdkService.class));
  }

  public static boolean isServiceRunning(Activity activity) {
    if (getBeaconServiceClass() == null) {
      return false;
    }
    return ServiceUtil.isServiceRunning(activity, beaconServiceClass);
  }

  private static List<Beacon> beaconList = new ArrayList<>();

  public static void addMonitoringBeacons(List<Beacon> beacons) {
    // TODO: 15. 11. 9. beaconList 추가.
    for (Beacon beacon : beacons) {
      if (beaconList.contains(beacon)) {
        continue;
      }
      beaconList.add(beacon);
    }
  }

  public static void removeMonitoringBeacons(List<Beacon> beacons) {
    // TODO: 15. 11. 9. beaconList 추가.
    for (Beacon beacon : beacons) {
      if (beaconList.contains(beacon)) {
        continue;
      }
      beaconList.remove(beacon);
    }
  }

  public static List<Beacon> getMonitoringBeacons() {
    return beaconList;
  }
}
