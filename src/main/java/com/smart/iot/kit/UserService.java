package com.smart.iot.kit;

import com.smart.iot.kit.entity.User;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private PasswordEncoder passwordEncoder;
  private UserRepository userRepository;

  public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
    this.passwordEncoder = passwordEncoder;
    this.userRepository = userRepository;
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
