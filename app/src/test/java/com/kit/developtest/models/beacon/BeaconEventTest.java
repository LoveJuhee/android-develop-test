package com.kit.developtest.models.beacon;

import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by kit on 15. 11. 11..
 */
public class BeaconEventTest {
  private BeaconEvent beaconEvent1 = null;
  private BeaconEvent beaconEvent2 = null;

  @Before
  public void setUp() throws Exception {
    beaconEvent1 = new BeaconEvent(BeaconEventTest.class, BeaconEventType.info, "message1");
    beaconEvent2 = new BeaconEvent(BeaconEventTest.class,
                                   BeaconEventType.exit,
                                   "message2",
                                   new Beacon(BeaconTest.UUID));
  }

  @Test
  public void testGetData() throws Exception {

  }

  @Test
  public void testSetData() throws Exception {

  }

  @Test
  public void testGetMessage() throws Exception {

  }

  @Test
  public void testSetMessage() throws Exception {

  }

  @Test
  public void testGetType() throws Exception {
    BeaconEventType type1 = beaconEvent1.getType();
    System.out.println(String.format("beaconEvent1\n%s", type1.toString()));
    BeaconEventType type2 = beaconEvent2.getType();
    System.out.println(String.format("beaconEvent2\n%s", type2.toString()));
  }

  @Test
  public void testSetType() throws Exception {

  }

  @Test
  public void testToString() throws Exception {
    Gson gson = new Gson();
    String json1 = gson.toJson(beaconEvent1);
    System.out.println(String.format("beaconEvent1\n%s", json1));
    String json2 = gson.toJson(beaconEvent2);
    System.out.println(String.format("beaconEvent2\n%s", json2));

    BeaconEvent event1 = gson.fromJson(json1, BeaconEvent.class);
    System.out.println(event1.print());
    BeaconEvent event2 = gson.fromJson(json2, BeaconEvent.class);
    System.out.println(event2.print());
  }
}