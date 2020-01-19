package com.smart.iot.web.security;

import com.smart.iot.kit.UserService;
import com.smart.iot.web.dto.UserSignUpDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

  private UserService userService;

  public AuthController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/login")
  public String logIn() {
    return "auth/logIn";
  }

  @PostMapping("/signup")
  public String signUp(@RequestBody UserSignUpDto userSignUpDto) {
    userService.createUser(userSignUpDto.toUser());
    return "auth/signUp";
  }
}
