package com.kit.developtest.models.beacon;

import com.kit.developtest.models.event.AbstractEvent;

/**
 * Created by kit on 15. 11. 11..
 */
public class BeaconEvent extends AbstractEvent {
  public BeaconEvent(Class sender, Enum type, String message) {
    this(sender, type, message, "");
  }

  public BeaconEvent(Class sender, Enum type, String message, Object data) {
    super(sender, type, message, data);
  }

  @Override
  public BeaconEventType getType() {
    return (BeaconEventType) super.getType();
  }
}
