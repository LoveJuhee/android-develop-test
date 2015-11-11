package com.kit.developtest.models.beacon;

import com.kit.developtest.models.event.AbstractEvent;
import com.kit.developtest.models.event.EventType;
import com.kit.developtest.utils.GsonUtil;

/**
 * Created by kit on 15. 11. 11..
 */
public class BeaconEvent extends AbstractEvent {
  public BeaconEvent(Class sender, EventType type, String message) {
    super(sender, type, message);
  }

  public BeaconEvent(Class sender, EventType type, String message, Object data) {
    super(sender, type, message, data);
  }
}
