package com.kit.developtest.models.service;

import com.kit.developtest.models.event.AbstractEvent;
import com.kit.developtest.models.event.EventType;

/**
 * Created by kit on 15. 11. 11..
 */
public class ServiceEvent extends AbstractEvent {
  public ServiceEvent(Class sender, EventType type, String message, Object data) {
    super(sender, type, message, data);
  }

  public ServiceEvent(Class sender, EventType type, String message) {
    super(sender, type, message);
  }
}
