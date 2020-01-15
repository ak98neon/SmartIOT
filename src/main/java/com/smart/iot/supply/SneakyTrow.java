package com.smart.iot.supply;

import com.smart.iot.SmartIotException;
import java.util.Objects;
import java.util.concurrent.Callable;

public final class SneakyTrow {

  private SneakyTrow() {
    throw new UnsupportedOperationException();
  }

  public static <T> T sneaky(Callable<? extends T> callable) {
    Objects.requireNonNull(callable);
    try {
      return callable.call();
    } catch (Exception e) {
      sneakyTrow(e);
    }
    throw new AssertionError();
  }

  private static <T extends Exception> void sneakyTrow(Exception e) throws T {
    throw new SmartIotException(e.getMessage());
  }
}
