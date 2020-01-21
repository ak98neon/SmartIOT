package com.smart.iot.web.security;

import com.smart.iot.kit.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final String secret;
  private final AuthenticationManager authenticationManager;

  public JwtAuthenticationFilter(AuthenticationManager authenticationManager, String secret) {
    this.authenticationManager = authenticationManager;
    this.secret = secret;

    setFilterProcessesUrl("/auth/logIn");
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) {
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
        username, password);

    return authenticationManager.authenticate(authenticationToken);
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain, Authentication authentication) {
    User user = ((User) authentication.getPrincipal());

    byte[] signingKey = secret.getBytes();

    String token = Jwts.builder()
        .signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS256)
        .setHeaderParam("typ", "JWT")
        .setIssuer("IOT_SYSTEM")
        .setAudience("secure-app")
        .setSubject(user.getUsername())
        .setExpiration(new Date(System.currentTimeMillis() + 864000000))
        .compact();

    response.addHeader("Authorization", "Bearer " + token);
  }
}
