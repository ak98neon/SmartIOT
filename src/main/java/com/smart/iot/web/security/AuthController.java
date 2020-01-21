package com.smart.iot.web.security;

import com.smart.iot.kit.UserService;
import com.smart.iot.web.dto.UserLogIn;
import com.smart.iot.web.dto.UserSignUp;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

  private UserService userService;

  public AuthController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/logIn")
  public String logInPage(Model model) {
    UserLogIn userLogIn = new UserLogIn();
    model.addAttribute("user", userLogIn);
    return "auth/logIn";
  }

  @GetMapping("/signup")
  public String signUpPage(Model model) {
    UserSignUp userSignUp = new UserSignUp();
    model.addAttribute("user", userSignUp);
    return "auth/signUp";
  }

  @PostMapping("/signup")
  public String signUp(@ModelAttribute("user") UserSignUp userSignUp) {
    userService.createUser(userSignUp.toUser());
    return "redirect:/iot";
  }
}
