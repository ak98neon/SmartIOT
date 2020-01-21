package com.smart.iot.web.security;

import com.smart.iot.kit.UserService;
import com.smart.iot.web.dto.UserLogIn;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class ApiAuthController {

  private UserService userService;

  public ApiAuthController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping(value = "/logIn", consumes = MediaType.APPLICATION_JSON_VALUE)
  public String logIn(@RequestBody UserLogIn userLogIn) {
    return userService.authenticateUser(userLogIn);
  }
}
