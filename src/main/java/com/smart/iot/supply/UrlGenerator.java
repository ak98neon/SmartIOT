package com.smart.iot.supply;

import javax.servlet.http.HttpServletRequest;

public final class UrlGenerator {

  private UrlGenerator() {
    throw new UnsupportedOperationException();
  }

  public static String getBaseUrl(HttpServletRequest req) {
    return req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req
        .getContextPath();
  }
}
