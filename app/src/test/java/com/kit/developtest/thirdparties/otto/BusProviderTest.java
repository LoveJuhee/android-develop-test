package com.kit.developtest.thirdparties.otto;

import com.google.gson.Gson;
import com.kit.developtest.models.beacon.Beacon;
import com.kit.developtest.models.beacon.BeaconEvent;
import com.kit.developtest.models.beacon.BeaconTest;
import com.kit.developtest.models.event.EventType;
import com.squareup.otto.Subscribe;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by kit on 15. 11. 11..
 */
public class BusProviderTest {
  private Gson gson = new Gson();

  @Before
  public void setUp() throws Exception {
    BusProvider.getInstance().register(this);
  }

  @After
  public void tearDown() throws Exception {
    BusProvider.getInstance().unregister(this);
  }

  @Test
  public void pushBeaconEventInfo() {
    BusProvider.getInstance().post(new BeaconEvent(BusProviderTest.class, EventType.info, "info message"));
  }

  @Test
  public void pushBeaconEventOn() {
    BusProvider.getInstance().post(new BeaconEvent(BusProviderTest.class, EventType.on, "on message", new Beacon(BeaconTest.UUID)));
  }

  @Subscribe
  public void catchBeaconEvent(BeaconEvent beaconEvent) {
    System.out.println(gson.toJson(beaconEvent));
  }
}