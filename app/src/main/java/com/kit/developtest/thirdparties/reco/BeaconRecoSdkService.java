package com.kit.developtest.thirdparties.reco;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.kit.developtest.models.beacon.Beacon;
import com.kit.developtest.services.BeaconIntentService;
import com.perples.recosdk.RECOBeacon;
import com.perples.recosdk.RECOBeaconManager;
import com.perples.recosdk.RECOBeaconRegion;
import com.perples.recosdk.RECOBeaconRegionState;
import com.perples.recosdk.RECOErrorCode;
import com.perples.recosdk.RECOMonitoringListener;
import com.perples.recosdk.RECORangingListener;
import com.perples.recosdk.RECOServiceConnectListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * BeaconRecoSdkService is to monitor regions and range regions when the device is inside in
 * the BACKGROUND.
 * <p>
 * RECOBackgroundMonitoringService는 백그라운드에서 monitoring을 수행하며, 특정 region 내부로 진입한 경우 백그라운드 상태에서
 * ranging을 수행합니다.
 */
public class BeaconRecoSdkService extends Service implements RECOMonitoringListener, RECORangingListener, RECOServiceConnectListener {

  /**
   * The constant TAG.
   */
  public static final String TAG = "BeaconRecoSdkService";

  /**
   * We recommend 1 second for scanning, 10 seconds interval between scanning, and 60 seconds for
   * region expiration time.
   * 1초 스캔, 10초 간격으로 스캔, 60초의 region expiration time은 당사 권장사항입니다.
   */
  private long scanDuration = 1 * 1000L;
  private long sleepDuration = 5 * 1000L;
  private long regionExpirationTime = 30 * 1000L;

  /**
   * SCAN_RECO_ONLY:
   * <p>
   * If true, the application scans RECO beacons only, otherwise it scans all beacons.
   * It will be used when the instance of RECOBeaconManager is created.
   * <p>
   * true일 경우 레코 비콘만 스캔하며, false일 경우 모든 비콘을 스캔합니다.
   * RECOBeaconManager 객체 생성 시 사용합니다.
   */
  private static final boolean SCAN_RECO_ONLY = true;

  /**
   * ENABLE_BACKGROUND_RANGING_TIMEOUT:
   * <p>
   * If true, the application stops to range beacons in the entered region automatically in 10
   * seconds (background),
   * otherwise it continues to range beacons. (It affects the battery consumption.)
   * It will be used when the instance of RECOBeaconManager is created.
   * <p>
   * 백그라운드 ranging timeout을 설정합니다.
   * true일 경우, 백그라운드에서 입장한 region에서 ranging이 실행 되었을 때, 10초 후 자동으로 정지합니다.
   * false일 경우, 계속 ranging을 실행합니다. (배터리 소모율에 영향을 끼칩니다.)
   * RECOBeaconManager 객체 생성 시 사용합니다.
   */
  private static final boolean ENABLE_BACKGROUND_RANGING_TIMEOUT = true;

  /**
   * DISCONTINUOUS_SCAN:
   * <p>
   * There is a known android bug that some android devices scan BLE devices only once.
   * (link: http://code.google.com/p/android/issues/detail?id=65863)
   * To resolve the bug in our SDK, you can use setDiscontinuousScan() method of the RECOBeaconManager.
   * This method is to set whether the device scans BLE devices continuously or discontinuously.
   * The default is set as FALSE. Please set TRUE only for specific devices.
   * <p>
   * 일부 안드로이드 기기에서 BLE 장치들을 스캔할 때, 한 번만 스캔 후 스캔하지 않는 버그(참고: http://code.google.com/p/android/issues/detail?id=65863)가 있습니다.
   * 해당 버그를 SDK에서 해결하기 위해, RECOBeaconManager에 setDiscontinuousScan() 메소드를 이용할 수 있습니다.
   * 해당 메소드는 기기에서 BLE 장치들을 스캔할 때(즉, ranging 시에), 연속적으로 계속 스캔할 것인지, 불연속적으로 스캔할 것인지 설정하는 것입니다.
   * 기본 값은 FALSE로 설정되어 있으며, 특정 장치에 대해 TRUE로 설정하시길 권장합니다.
   */
  public static final boolean DISCONTINUOUS_SCAN = true;//false;

  private RECOBeaconManager manager;
  private ArrayList<RECOBeaconRegion> regionList;

  @Override
  public void onCreate() {
    sendMessage("onCreate()");
    super.onCreate();
  }

  private void sendMessage(String message) {
    BeaconIntentService.eventMessage(this, message);
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    sendMessage("onStartCommand()");
    /**
     * Create an instance of RECOBeaconManager (to set scanning target and ranging timeout in the
     * background.)
     * If you want to scan only RECO, and do not set ranging timeout in the backgournd, create an
     * instance:
     * manager = RECOBeaconManager.getInstance(this, true, false);
     * WARNING: False enableRangingTimeout will affect the battery consumption.
     *
     * RECOBeaconManager 인스턴스틀 생성합니다. (스캔 대상 및 백그라운드 ranging timeout 설정)
     * RECO만을 스캔하고, 백그라운드 ranging timeout을 설정하고 싶지 않으시다면, 다음과 같이 생성하시기 바랍니다.
     * manager = RECOBeaconManager.getInstance(this, true, false);
     * 주의: ENABLE_BACKGROUND_RANGING_TIMEOUT 을 false 설정 시, 배터리 소모량이 증가합니다.
     */

    manager = RECOBeaconManager.getInstance(this, SCAN_RECO_ONLY, ENABLE_BACKGROUND_RANGING_TIMEOUT);
    manager.setScanPeriod(this.scanDuration);
    manager.setSleepPeriod(this.sleepDuration);

    this.bindRECOService();
    return START_STICKY;
  }

  @Override
  public void onDestroy() {
    sendMessage("onDestroy()");
    this.tearDown();
    super.onDestroy();
  }

  @Override
  public void onTaskRemoved(Intent rootIntent) {
    sendMessage("onTaskRemoved()");
    super.onTaskRemoved(rootIntent);
  }

  private void bindRECOService() {
    sendMessage("bindRECOService()");

    regionList = new ArrayList<RECOBeaconRegion>();
    this.generateBeaconRegion();

    manager.setMonitoringListener(this);
    manager.setRangingListener(this);
    manager.bind(this);
  }

  private void generateBeaconRegion() {
    sendMessage("generateBeaconRegion()");

    List<String> uuidList = new ArrayList<>();
    List<Beacon> beaconList = BeaconIntentService.getMonitoringBeacons();
    for (Beacon beacon : beaconList) {
      if (uuidList.contains(beacon.getUuid())) {
        continue;
      }
      uuidList.add(beacon.getUuid());
    }

    for (String uuid : uuidList) {
      RECOBeaconRegion recoRegion;
      recoRegion = new RECOBeaconRegion(uuid, "MyPlan");
      recoRegion.setRegionExpirationTimeMillis(this.regionExpirationTime);
      regionList.add(recoRegion);
    }
  }

  private void startMonitoring() {
    sendMessage("startMonitoring()");

    for (RECOBeaconRegion region : regionList) {
      try {
        region.setRegionExpirationTimeMillis(this.regionExpirationTime);
        manager.startMonitoringForRegion(region);
      } catch (RemoteException e) {
        Log.e("RECOBgRangingSvc", "RemoteException has occurred while executing RECOManager.startMonitoringForRegion()");
        e.printStackTrace();
      } catch (NullPointerException e) {
        Log.e("RECOBgRangingSvc", "NullPointerException has occurred while executing RECOManager.startMonitoringForRegion()");
        e.printStackTrace();
      }
    }
  }

  private void stopMonitoring() {
    sendMessage("stopMonitoring()");

    for (RECOBeaconRegion region : regionList) {
      try {
        manager.stopMonitoringForRegion(region);
      } catch (RemoteException e) {
        Log.e("RECOBgRangingSvc", "RemoteException has occurred while executing RECOManager.stopMonitoringForRegion()");
        e.printStackTrace();
      } catch (NullPointerException e) {
        Log.e("RECOBgRangingSvc", "NullPointerException has occurred while executing RECOManager.stopMonitoringForRegion()");
        e.printStackTrace();
      }
    }
  }

  private void startRangingWithRegion(RECOBeaconRegion region) {
    sendMessage("startRangingWithRegion(" + RECOUtil.toString(region) + ")");

    /**
     * There is a known android bug that some android devices scan BLE devices only once. (link:
     * http://code.google.com/p/android/issues/detail?id=65863)
     * To resolve the bug in our SDK, you can use setDiscontinuousScan() method of the RECOBeaconManager.
     * This method is to set whether the device scans BLE devices continuously or discontinuously.
     * The default is set as FALSE. Please set TRUE only for specific devices.
     *
     * manager.setDiscontinuousScan(true);
     */

    try {
      manager.startRangingBeaconsInRegion(region);
    } catch (RemoteException e) {
      Log.e("RECOBgRangingSvc", "RemoteException has occurred while executing RECOManager.startRangingBeaconsInRegion()");
      e.printStackTrace();
    } catch (NullPointerException e) {
      Log.e("RECOBgRangingSvc", "NullPointerException has occurred while executing RECOManager.startRangingBeaconsInRegion()");
      e.printStackTrace();
    }
  }

  private void stopRangingWithRegion(RECOBeaconRegion region) {
    sendMessage("stopRangingWithRegion(" + RECOUtil.toString(region) + ")");

    try {
      manager.stopRangingBeaconsInRegion(region);
    } catch (RemoteException e) {
      Log.e("RECOBgRangingSvc", "RemoteException has occurred while executing RECOManager.stopRangingBeaconsInRegion()");
      e.printStackTrace();
    } catch (NullPointerException e) {
      Log.e("RECOBgRangingSvc", "NullPointerException has occurred while executing RECOManager.stopRangingBeaconsInRegion()");
      e.printStackTrace();
    }
  }

  private void tearDown() {
    sendMessage("tearDown()");
    this.stopMonitoring();

    try {
      manager.unbind();
    } catch (RemoteException e) {
      Log.e("RECOBgRangingSvc", "RemoteException has occurred while executing unbind()");
      e.printStackTrace();
    }
  }

  @Override
  public void onServiceConnect() {
    // RECOBeaconService와 연결 시 코드 작성
    sendMessage("onServiceConnect()");

    manager.setDiscontinuousScan(DISCONTINUOUS_SCAN);
    this.startMonitoring();
    // Write the code when RECOBeaconManager is bound to RECOBeaconService
  }

  @Override
  public void didDetermineStateForRegion(RECOBeaconRegionState state, RECOBeaconRegion region) {
    // monitoring 시작 후에 monitoring 중인 region에 들어가거나 나올 경우
    // (region 의 상태에 변화가 생긴 경우) 이 callback 메소드가 호출됩니다.
    // didEnterRegion, didExitRegion callback 메소드와 함께 호출됩니다.
    // region 상태 변화시 코드 작성

    String message = String.format("didDetermineStateForRegion(%s, %s)", state.toString(), RECOUtil.toString(region));

    sendMessage(message);
    // Write the code when the state of the monitored region is changed

    // this.popupNotification(message);
    this.startRangingWithRegion(region);
  }

  @Override
  public void didEnterRegion(RECOBeaconRegion region, Collection<RECOBeacon> beacons) {
    // monitoring 시작 후에 monitoring 중인 region에 들어갈 경우 이 callback 메소드가 호출됩니다.
    // 0.2 버전부터 이 callback 메소드가 호출 될 경우, recoRegion에서 감지된 비콘들을 전달합니다.
    // region 입장시 코드 작성
    /**
     * For the first run, this callback method will not be called.
     * Please check the state of the region using didDetermineStateForRegion() callback method.
     *
     * 최초 실행시, 이 콜백 메소드는 호출되지 않습니다.
     * didDetermineStateForRegion() 콜백 메소드를 통해 region 상태를 확인할 수 있습니다.
     */

    // Get the region and found beacon list in the entered region
    sendMessage("didEnterRegion() - " + region.getUniqueIdentifier());
    // this.popupNotification("Inside of " + region.getUniqueIdentifier());
    // Write the code when the device is enter the region

    this.startRangingWithRegion(region); // start ranging to get beacons inside of the region
    // from now, stop ranging after 10 seconds if the device is not exited
  }

  @Override
  public void didExitRegion(RECOBeaconRegion region) {
    // monitoring 시작 후에 monitoring 중인 region에서 나올 경우 이 callback 메소드가 호출됩니다.
    // region 퇴장시 코드 작성
    /**
     * For the first run, this callback method will not be called.
     * Please check the state of the region using didDetermineStateForRegion() callback method.
     *
     * 최초 실행시, 이 콜백 메소드는 호출되지 않습니다.
     * didDetermineStateForRegion() 콜백 메소드를 통해 region 상태를 확인할 수 있습니다.
     */

    sendMessage("didExitRegion() - " + RECOUtil.toString(region));
    // this.popupNotification("Outside of " + region.getUniqueIdentifier());
    // Write the code when the device is exit the region

    BeaconIntentService.eventBeaconRegionExit(this, RECOUtil.toBeacon(region));

    this.stopRangingWithRegion(region); // stop ranging because the device is outside of the region
    // from now
  }

  @Override
  public void didStartMonitoringForRegion(RECOBeaconRegion region) {
    // monitoring 시작 후에 monitoring을 시작하고 이 callback 메소드가 호출됩니다.
    // monitoring 정상 실행 시 코드 작성
    sendMessage("didStartMonitoringForRegion() - " + region.getUniqueIdentifier());
    // Write the code when starting monitoring the region is started successfully
  }

  @Override
  public void didRangeBeaconsInRegion(Collection<RECOBeacon> beacons, RECOBeaconRegion region) {
    sendMessage("didRangeBeaconsInRegion() - " + region.getUniqueIdentifier() + " with " + beacons.size() + " beacons");
    // Write the code when the beacons inside of the region is received
    List<Beacon> beaconList = new ArrayList<>();
    for(RECOBeacon recoBeacon : beacons) {
      beaconList.add(RECOUtil.toBeacon(recoBeacon));
    }
    BeaconIntentService.eventBeaconRegionsOn(this, beaconList);
  }

  @Override
  public IBinder onBind(Intent intent) {
    // This method is not used
    return null;
  }

  @Override
  public void onServiceFail(RECOErrorCode errorCode) {
    // Write the code when the RECOBeaconService is failed.
    // See the RECOErrorCode in the documents.
    return;
  }

  @Override
  public void monitoringDidFailForRegion(RECOBeaconRegion region, RECOErrorCode errorCode) {
    // monitoring이 정상적으로 시작하지 못했을 경우 이 callback 메소드가 호출됩니다.
    // RECOErrorCode는 "Error Code" 를 확인하시기 바랍니다.
    // monitoring 실패 시 코드 작성
    return;
  }

  @Override
  public void rangingBeaconsDidFailForRegion(RECOBeaconRegion region, RECOErrorCode errorCode) {
    // Write the code when the RECOBeaconService is failed to range beacons in the region.
    // See the RECOErrorCode in the documents.
    return;
  }
}
