package com.smart.iot.kit.entity;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import org.thymeleaf.util.StringUtils;

@Entity(name = "fridges")
public class Fridge extends BaseAuditEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  @Column(name = "qr_link")
  private byte[] qrLink;
  @OneToMany(mappedBy = "fridge")
  private List<ProductItem> productList;
  @ManyToMany(mappedBy = "fridges")
  private List<User> users;

  public Fridge() {
  }

  Fridge(String name, byte[] qrLink,
      List<ProductItem> productList, List<User> users) {
    this.name = name;
    this.qrLink = qrLink;
    this.productList = productList;
    this.users = users;
    super.setCreatedAt(OffsetDateTime.now());
    super.setUpdatedAt(OffsetDateTime.now());
  }

  public String getName() {
    return name;
  }

  public Long getId() {
    return id;
  }

  public byte[] getQrLink() {
    return qrLink;
  }

  public List<ProductItem> getProductList() {
    return productList;
  }

  public List<User> getUsers() {
    return users;
  }

  @Override
  public String toString() {
    return "Fridge{" +
        "id='" + id + '\'' +
        ", name='" + name + '\'' +
        ", qrLink=" + Arrays.toString(qrLink) +
        ", productList=" + productList +
        '}';
  }

  public static class Builder {

    private String name;
    private byte[] link;
    private List<ProductItem> productList;
    private List<User> users;

    public Builder with(Consumer<Builder> builderConsumer) {
      builderConsumer.accept(this);
      return this;
    }

    public void name(String name) {
      Objects.requireNonNull(name);
      this.name = name;
    }

    public void users(List<User> users) {
      Objects.requireNonNull(users);
      this.users = users;
    }

    public void link(byte[] link) {
      this.link = link;
    }

    public void productList(List<ProductItem> products) {
      this.productList = products;
    }

    public Fridge createFridge() {
      if (StringUtils.isEmptyOrWhitespace(this.name)) {
        this.name = UUID.randomUUID().toString();
      }
      return new Fridge(name, link, productList, users);
    }
  }
}
