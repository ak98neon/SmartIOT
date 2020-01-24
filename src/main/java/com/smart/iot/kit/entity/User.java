package com.smart.iot.kit.entity;

import java.time.OffsetDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity(name = "users")
public class User extends BaseAuditEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String username;
  private String password;
  private String email;
  @Column(name = "phone_number")
  private String phoneNumber;
  @ManyToMany
  private List<Fridge> fridges;

  public User() {
  }

  public User(String username, String password, String email, String phoneNumber) {
    this.username = username;
    this.password = password;
    this.email = email;
    this.phoneNumber = phoneNumber;
    super.setCreatedAt(OffsetDateTime.now());
    super.setUpdatedAt(OffsetDateTime.now());
  }

  public User(String username, String password, String email, String phoneNumber,
      List<Fridge> fridges) {
    this.username = username;
    this.password = password;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.fridges = fridges;
    super.setCreatedAt(OffsetDateTime.now());
    super.setUpdatedAt(OffsetDateTime.now());
  }

  public List<Fridge> getFridges() {
    return fridges;
  }

  public Long getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }
}
