package com.kit.developtest.models.beacon;

import com.kit.developtest.BuildConfig;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import kr.lj1212.utils.ObjectUtil;

import static org.junit.Assert.assertTrue;

/**
 * Created by kit on 15. 11. 10..
 */
public class BeaconTest {
  public static final String UUID_ERR = "97009700-9700-9700-9700-970097009701";
  public static final String UUID = "97009700-9700-9700-9700-970097009700";
  public static final int MAJOR = 861;

  Beacon beacon1 = null;
  Beacon beacon2 = null;
  Beacon beacon3 = null;
  Beacon beacon4 = null;
  Beacon beacon5 = null;
  Beacon beaconOther = null;

  List<Beacon> beaconList = new ArrayList<>();

  @Before
  public void setup() throws Exception {
    beacon1 = new Beacon(UUID, MAJOR, 1001);
    beacon2 = new Beacon(UUID, MAJOR, 1002);
    beacon3 = new Beacon(UUID, MAJOR, 1003);
    beacon4 = new Beacon(UUID, MAJOR);
    beacon5 = new Beacon(UUID);
    beaconOther = new Beacon(UUID_ERR);

    beaconList.add(beacon1);
    beaconList.add(beacon2);
    beaconList.add(beacon3);
    beaconList.add(beacon4);
    beaconList.add(beacon5);
  }

  @Test
  public void print() {
    System.out.println("\nprocess print()");
    System.out.println("beacon1: " + beacon1.toString());
    System.out.println("beacon2: " + beacon2.toString());
    System.out.println("beacon3: " + beacon3.toString());
    System.out.println("beacon4: " + beacon4.toString());
    System.out.println("beacon5: " + beacon5.toString());
    System.out.println("beaconOther: " + beaconOther.toString());
  }

  @Test
  public void valid() throws Exception {
    System.out.println("\nprocess valid()");

    assertTrue(ObjectUtil.isInvalid(beacon1));
    assertTrue(ObjectUtil.isInvalid(beacon2));
    assertTrue(ObjectUtil.isInvalid(beacon3));
    assertTrue(ObjectUtil.isInvalid(beacon4));
    assertTrue(ObjectUtil.isInvalid(beacon5));
    assertTrue(ObjectUtil.isInvalid(beaconOther));
  }


  @Test
  public void validListContains() throws Exception {
    System.out.println("\nprocess validListContains()");

    assertTrue(beaconList.contains(beacon1));
    assertTrue(beaconList.contains(beacon2));
    assertTrue(beaconList.contains(beacon3));
    assertTrue(beaconList.contains(beacon4));
    assertTrue(beaconList.contains(beacon5));
    assertTrue(!beaconList.contains(beaconOther));
  }
}