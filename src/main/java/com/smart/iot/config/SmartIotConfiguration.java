package com.smart.iot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SmartIotConfiguration {

  @Value("${iot.api.prefix:/iot}")
  private String apiPrefix;

  public String getApiPrefix() {
    return apiPrefix;
  }
}
