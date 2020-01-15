package com.smart.iot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QrCodeConfiguration {

  @Value("${iot.qr.width:350}")
  private Integer width;
  @Value("${iot.qr.height:350}")
  private Integer height;

  public Integer getWidth() {
    return width;
  }

  public Integer getHeight() {
    return height;
  }
}
