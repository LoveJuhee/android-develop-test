package com.kit.developtest.models.event;

import com.google.gson.Gson;

import com.kit.developtest.utils.GsonUtil;

import kr.lj1212.utils.ObjectUtil;

/**
 * Created by kit on 15. 11. 11..
 */
public abstract class AbstractEvent {
  private static final Gson gson = new Gson();

  private String sender = "EMPTY";
  private EventType type = EventType.none;
  private String message = "";
  private Object data = null;

  public AbstractEvent(Class sender, EventType type, String message) {
    this(sender.getSimpleName(), type, message, null);
  }

  public AbstractEvent(String sender, EventType type, String message) {
    this(sender, type, message, null);
  }

  public AbstractEvent(Class sender, EventType type, String message, Object data) {
    this(sender.getSimpleName(), type, message, data);
  }

  public AbstractEvent(String sender, EventType type, String message, Object data) {
    setSender(sender);
    setType(type);
    setMessage(message);
    setData(data);
  }

  public String getSender() {
    return sender;
  }

  public void setSender(String sender) {
    if (ObjectUtil.isNullOrEmpty(sender)) {
      return;
    }
    this.sender = sender;
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

  public EventType getType() {
    return type;
  }

  public void setType(EventType type) {
    this.type = type;
  }

  @Override
  public String toString() {
    return gson.toJson(this);
  }

  public String print() {
    StringBuilder sb = new StringBuilder();
    sb.append(getClass().getSimpleName() + "\n");
    sb.append(" - sender : " + getSender() + "\n");
    sb.append(" - type   : " + getType() + "\n");
    sb.append(" - message: " + getMessage() + "\n");
    sb.append(" - data   : " + GsonUtil.toJson(getData()));
    return sb.toString();
  }
}
