package com.smart.iot.web.security;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import io.micrometer.core.instrument.util.StringUtils;
import java.io.IOException;
import java.util.Collections;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

  private static final Logger log = LoggerFactory.getLogger(JwtAuthorizationFilter.class);

  private final String secret;

  public JwtAuthorizationFilter(AuthenticationManager authenticationManager, String secret) {
    super(authenticationManager);
    this.secret = secret;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws IOException, ServletException {
    UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
    if (authentication == null) {
      filterChain.doFilter(request, response);
      return;
    }

    SecurityContextHolder.getContext().setAuthentication(authentication);
    filterChain.doFilter(request, response);
  }

  private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
    String token = request.getHeader("Authorization");
    if (StringUtils.isNotEmpty(token) && token.startsWith("Bearer ")) {
      try {
        byte[] signingKey = secret.getBytes();

        Jws<Claims> parsedToken = Jwts.parser()
            .setSigningKey(signingKey)
            .parseClaimsJws(token.replace("Bearer ", ""));

        String username = parsedToken
            .getBody()
            .getSubject();

        if (StringUtils.isNotEmpty(username)) {
          return new UsernamePasswordAuthenticationToken(username, null,
              Collections.emptyList());
        }
      } catch (ExpiredJwtException exception) {
        log.warn("Request to parse expired JWT : {} failed : {}");
      } catch (UnsupportedJwtException exception) {
        log.warn("Request to parse unsupported JWT : {} failed : {}");
      } catch (MalformedJwtException exception) {
        log.warn("Request to parse invalid JWT : {} failed : {}");
      } catch (SignatureException exception) {
        log.warn("Request to parse JWT with invalid signature : {} failed : {}");
      } catch (IllegalArgumentException exception) {
        log.warn("Request to parse empty or null JWT : {} failed : {}");
      }
    }

    return null;
  }
}
