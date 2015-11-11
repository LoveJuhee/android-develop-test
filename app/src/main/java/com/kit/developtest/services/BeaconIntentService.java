package com.kit.developtest.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.kit.developtest.models.beacon.Beacon;
import com.kit.developtest.models.beacon.BeaconEvent;
import com.kit.developtest.models.event.EventType;
import com.kit.developtest.models.service.ServiceEvent;
import com.kit.developtest.thirdparties.otto.BusProvider;
import com.kit.developtest.utils.ServiceUtil;

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

  private static List<Beacon> monitoringBeaconList = new ArrayList<>();

  public static Class getBeaconServiceClass() {
    return beaconServiceClass;
  }

  public static void setBeaconServiceClass(Class beaconServiceClass) {
    BeaconIntentService.beaconServiceClass = beaconServiceClass;
  }

  public static Class beaconServiceClass = null;

  public static void startBeaconService(Context context) {
    if (getBeaconServiceClass() == null) {
      sendEvent(EventType.error, "BeaconService Class is null");
      return;
    }
    if (isServiceRunning(context)) {
      sendEvent(EventType.error, "BeaconService is already running");
      return;
    }
    Intent intent = new Intent(context, BeaconIntentService.class);
    intent.setAction(ACTION_START_BEACON_SERVICE);
    context.startService(intent);
  }

  public static void stopBeaconService(Context context) {
    if (getBeaconServiceClass() == null) {
      sendEvent(EventType.error, "BeaconService Class is null");
      return;
    }
    if (isServiceRunning(context) == false) {
      sendEvent(EventType.error, "BeaconService is already stopped.");
      return;
    }
    Intent intent = new Intent(context, BeaconIntentService.class);
    intent.setAction(ACTION_STOP_BEACON_SERVICE);
    context.startService(intent);
  }

  public static void eventMessage(String message) {
    BusProvider.getInstance().post(new BeaconEvent(beaconServiceClass, EventType.info, message));

  }

  public static void eventBeaconRegionsOn(List<Beacon> beaconList) {
    BusProvider.getInstance().post(new BeaconEvent(beaconServiceClass, EventType.on, "BeaconRegionsOn", beaconList));
  }

  public static void eventBeaconRegionExit(Beacon beacon) {
    BusProvider.getInstance().post(new BeaconEvent(beaconServiceClass, EventType.exit, "BeaconRegionExit", beacon));
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
      }
    }
  }

  private static void sendEvent(EventType type, String message) {
    sendEvent(type, message, null);
  }

  private static void sendEvent(EventType type, String message, Object data) {
    BusProvider.getInstance().post(new ServiceEvent(BeaconIntentService.class, type, message, data));
  }

  private void handleActionStartBeaconService() {
    startService(new Intent(getApplicationContext(), getBeaconServiceClass()));
  }

  private void handleActionStopBeaconService() {
    stopService(new Intent(getApplicationContext(), getBeaconServiceClass()));
  }

  public static boolean isServiceRunning(Context context) {
    if (getBeaconServiceClass() == null) {
      return false;
    }
    return ServiceUtil.isServiceRunning(context, beaconServiceClass);
  }

  public static void addMonitoringBeacons(List<Beacon> beacons) {
    // TODO: 15. 11. 9. monitoringBeaconList 추가.
    for (Beacon beacon : beacons) {
      if (monitoringBeaconList.contains(beacon)) {
        continue;
      }
      monitoringBeaconList.add(beacon);
    }
  }

  public static List<Beacon> getMonitoringBeacons() {
    return monitoringBeaconList;
  }
}
