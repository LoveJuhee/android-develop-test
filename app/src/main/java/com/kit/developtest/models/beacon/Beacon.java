package com.kit.developtest.models.beacon;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.security.InvalidParameterException;
import java.util.List;

import kr.lj1212.IValid;
import kr.lj1212.utils.ObjectUtil;

/**
 * Created by kit on 15. 11. 9..
 */
public class Beacon implements IValid, Comparable {
  public static final String UUID_KEY = "UUID";
  public static final String MAJOR_KEY = "MAJOR";
  public static final String MINOR_KEY = "MINOR";

  String uuid = "";
  int major = 0;
  int minor = 0;

  @Override
  public String toString() {
    return String.format("UUID:%s, MAJOR:%d, MINOR:%d", uuid, major, minor);
  }

  public Beacon(String uuid) {
    this(uuid, 0, 0);
  }

  public Beacon(String uuid, int major) {
    this(uuid, major, 0);
  }

  public Beacon(String uuid, int major, int minor) {
    setUuid(uuid);
    setMajor(major);
    setMinor(minor);
  }

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    if (ObjectUtil.isNullOrSpace(uuid)) {
      return;
    }
    this.uuid = uuid;
  }

  public int getMajor() {
    return major;
  }

  public void setMajor(int major) {
    this.major = major;
  }

  public int getMinor() {
    return minor;
  }

  public void setMinor(int minor) {
    this.minor = minor;
  }

  @Override
  public boolean isValid() {
    return ObjectUtil.isNullOrEmpty(getUuid());
  }

  @Override
  public boolean equals(Object o) {
    try {
      int rtv = compareTo(o);
      return (rtv == 0);
    } catch (Exception e) {
      return false;
    }
  }

  @Override
  public int compareTo(Object another) {
    if (another != null && another instanceof Beacon) {
      Beacon target = (Beacon) another;
      int rtv = getUuid().compareTo(target.getUuid());
      if (rtv != 0) {
        return rtv;
      }
      rtv = getMajor() - target.getMajor();
      if (rtv != 0) {
        return (rtv > 0) ? 1 : -1;
      }
      rtv = getMinor() - target.getMinor();
      if (rtv != 0) {
        return (rtv > 0) ? 1 : -1;
      }
      // 모두 같음.
      return 0;
    }
    throw new InvalidParameterException("another is not Beacon class.");
  }

  public static final Type listOfBeaconObject = new TypeToken<List<Beacon>>() {
  }.getType();

  public static Type getListOfBeaconObject() {
    return listOfBeaconObject;
  }
}
