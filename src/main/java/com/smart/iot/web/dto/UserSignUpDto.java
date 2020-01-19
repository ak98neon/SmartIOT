package com.smart.iot.web.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.smart.iot.kit.entity.User;

public class UserSignUpDto {

  private String username;
  private String password;
  private String email;
  @JsonProperty("phone_number")
  private String phoneNumber;

  public UserSignUpDto() {
  }

  @JsonCreator
  public UserSignUpDto(String username, String password, String email, String phoneNumber) {
    this.username = username;
    this.password = password;
    this.email = email;
    this.phoneNumber = phoneNumber;
  }

  public User toUser() {
    return new User(username, password, email, phoneNumber);
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public String getEmail() {
    return email;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }
}
