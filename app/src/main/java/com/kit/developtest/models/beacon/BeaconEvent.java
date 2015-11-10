package com.kit.developtest.models.beacon;

import com.google.gson.Gson;

import kr.lj1212.utils.ObjectUtil;

/**
 * Created by kit on 15. 11. 11..
 */
public class BeaconEvent {
  private static final Gson gson = new Gson();

  private String type = "";
  private String message = "";
  private Object data = null;

  public BeaconEvent(String type, String message) {
    this(type, message, null);
  }

  public BeaconEvent(String type, String message, Object data) {
    setType(type);
    setMessage(message);
    setData(data);
  }

  public Object getData() {
    return data;
  }

  public void setData(Object data) {
    this.data = data;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  @Override
  public String toString() {
    return gson.toJson(this);
  }
}
