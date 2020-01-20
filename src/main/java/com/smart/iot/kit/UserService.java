package com.smart.iot.kit;

import com.smart.iot.kit.entity.User;
import com.smart.iot.web.dto.UserLogIn;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService {

  private PasswordEncoder passwordEncoder;
  private UserRepository userRepository;
  @Value("${iot.security.secret:secret}")
  private String secret;

  public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
    this.passwordEncoder = passwordEncoder;
    this.userRepository = userRepository;
  }

  public String authenticateUser(final UserLogIn userLogIn) {
    User userByUsername = findUserByUsername(userLogIn.getUsername());
    return Jwts.builder()
        .signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)),
            SignatureAlgorithm.HS256)
        .setHeaderParam("typ", "JWT")
        .setIssuer("IOT_SYSTEM")
        .setAudience("secure-app")
        .setSubject(userByUsername.getUsername())
        .setExpiration(new Date(System.currentTimeMillis() + 864000000))
        .compact();
  }

  public void createUser(User user) {
    String password = user.getPassword();
    String encode = passwordEncoder.encode(password);
    user.setPassword(encode);
    userRepository.saveAndFlush(user);
  }

  public Optional<User> findUserById(Long id) {
    return userRepository.findById(id);
  }

  public User findUserByUsername(String username) {
    return userRepository.findByUsername(username);
  }
}
