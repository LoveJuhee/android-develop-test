package com.kit.developtest.thirdparties.otto;

import com.squareup.otto.Bus;

/**
 * Created by kit on 15. 11. 11..
 */
public class BusProvider {
  private static final Bus BUS = new Bus();

  public static Bus getInstance() {
    return BUS;
  }

  private BusProvider() {
    // No instances.
  }
}
