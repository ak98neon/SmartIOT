package com.smart.iot.web.security;

import com.smart.iot.kit.UserService;
import com.smart.iot.web.dto.UserLogIn;
import com.smart.iot.web.dto.UserSignUp;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

  private final UserService userService;
  private final JwtUserDetailsService jwtUserDetailsService;
  private final AuthenticationManager authenticationManager;
  @Value("${iot.security.secret:secret}")
  private String secret;

  public AuthController(
      AuthenticationManager authenticationManager, UserService userService,
      JwtUserDetailsService jwtUserDetailsService) {
    this.authenticationManager = authenticationManager;
    this.userService = userService;
    this.jwtUserDetailsService = jwtUserDetailsService;
  }

  @GetMapping
  public String logInPage(Model model) {
    UserLogIn userLogIn = new UserLogIn();
    model.addAttribute("user", userLogIn);
    return "auth/logIn";
  }

  @PostMapping("/logIn")
  public String logIn(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    authenticate(username, password);
    UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);

    byte[] signingKey = secret.getBytes();

    String token = Jwts.builder()
        .signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS256)
        .setHeaderParam("typ", "JWT")
        .setIssuer("IOT_SYSTEM")
        .setAudience("secure-app")
        .setSubject(userDetails.getUsername())
        .setExpiration(new Date(System.currentTimeMillis() + 864000000))
        .compact();

    Cookie cookie = new Cookie("jwt", token);
    cookie.setMaxAge(7 * 24 * 60 * 60);
    cookie.setPath("/");
    response.addCookie(cookie);
    return "redirect:/iot";
  }

  private void authenticate(String username, String password) throws Exception {
    try {
      authenticationManager
          .authenticate(new UsernamePasswordAuthenticationToken(username, password));
    } catch (DisabledException e) {
      throw new Exception("USER_DISABLED", e);
    } catch (BadCredentialsException e) {
      throw new Exception("INVALID_CREDENTIALS", e);
    }
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
