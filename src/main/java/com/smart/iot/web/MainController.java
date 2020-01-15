package com.smart.iot.web;

import static com.smart.iot.web.config.ApiConfig.PREFIX;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(PREFIX)
public class MainController {

  @GetMapping
  public String getMainPage() {
    return "main";
  }
}
