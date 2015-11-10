package com.kit.developtest.thirdparties.reco;

import com.kit.developtest.models.beacon.Beacon;
import com.perples.recosdk.RECOBeacon;
import com.perples.recosdk.RECOBeaconRegion;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kit on 15. 6. 8..
 */
public class RECOUtil {
  /**
   * region 정보 반환.
   *
   * @param region the region
   * @return the string
   */
  public static String toString(RECOBeaconRegion region) {
    if (region == null) {
      return "";
    }
    return String.format("%s_%d_%d_%s", region.getProximityUuid(), region.getMajor(), region.getMinor(), region.getUniqueIdentifier());
  }

  /**
   * beacon 정보 반환.
   *
   * @param beacon the beacon
   * @return the string
   */
  public static String toString(RECOBeacon beacon) {
    if (beacon == null) {
      return "";
    }
    return String.format("%s_%d_%d", beacon.getProximityUuid(), beacon.getMajor(), beacon.getMinor());
  }

  /**
   * Equals boolean.
   *
   * @param beacon1 the chk 1
   * @param beacon2 the chk 2
   * @return the boolean
   */
  public static boolean equals(RECOBeacon beacon1, RECOBeacon beacon2) {
    if (beacon1 == null || beacon2 == null) {
      return false;
    }
    String check1 = toString(beacon1);
    String check2 = toString(beacon2);
    return check1.equalsIgnoreCase(check2);
  }

  /**
   * RECOBeaconRegion json으로 변환.
   *
   * @param region
   * @return
   */
  public static JSONObject toJSONObject(RECOBeaconRegion region) {
    JSONObject json = new JSONObject();
    if (region == null) {
      return json;
    }
    try {
      json.put(Beacon.UUID_KEY, region.getProximityUuid());
      json.put(Beacon.MAJOR_KEY, region.getMajor());
      json.put(Beacon.MINOR_KEY, region.getMinor());
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return json;
  }

  /**
   * RECOBeacon json으로 변환.
   *
   * @param beacon
   * @return
   */
  public static JSONObject toJSONObject(RECOBeacon beacon) {
    JSONObject json = new JSONObject();
    if (beacon == null) {
      return json;
    }
    try {
      json.put(Beacon.UUID_KEY, beacon.getProximityUuid());
      json.put(Beacon.MAJOR_KEY, beacon.getMajor());
      json.put(Beacon.MINOR_KEY, beacon.getMinor());
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return json;
  }

  public static Beacon toBeacon(RECOBeacon recoBeacon) {
    if (recoBeacon == null) {
      return null;
    }
    String uuid = recoBeacon.getProximityUuid();
    int major = recoBeacon.getMajor();
    int minor = recoBeacon.getMinor();
    return new Beacon(uuid, major, minor);
  }

  public static Beacon toBeacon(RECOBeaconRegion region) {
    if (region == null) {
      return null;
    }
    String uuid = region.getProximityUuid();
    int major = region.getMajor();
    int minor = region.getMinor();
    return new Beacon(uuid, major, minor);
  }
}
