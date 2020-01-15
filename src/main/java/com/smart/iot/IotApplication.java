package com.smart.iot;

import org.springframework.boot.Banner.Mode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IotApplication {

  public static void main(String[] args) {
    SpringApplication app = new SpringApplication(IotApplication.class);
    app.setBannerMode(Mode.OFF);
    app.run(args);
  }
}
