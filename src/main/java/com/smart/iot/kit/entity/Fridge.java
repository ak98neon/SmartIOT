package com.smart.iot.kit.entity;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.thymeleaf.util.StringUtils;

@Document("fridge")
public class Fridge {

  @Id
  private String id;
  private String name;
  private byte[] qrLink;
  @DBRef
  private List<Product> productList;

  public Fridge() {
  }

  public Fridge(String id, String name, byte[] qrLink,
      List<Product> productList) {
    this.id = id;
    this.name = name;
    this.qrLink = qrLink;
    this.productList = productList;
  }

  public String getName() {
    return name;
  }

  public String getId() {
    return id;
  }

  public byte[] getQrLink() {
    return qrLink;
  }

  public List<Product> getProductList() {
    return productList;
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

    private String id;
    private String name;
    private byte[] link;
    private List<Product> productList;

    public Builder with(Consumer<Builder> builderConsumer) {
      builderConsumer.accept(this);
      return this;
    }

    public void name(String name) {
      Objects.requireNonNull(name);
      this.name = name;
    }

    public void id(String id) {
      Objects.requireNonNull(id);
      this.id = id;
    }

    public void link(byte[] link) {
      this.link = link;
    }

    public void productList(List<Product> products) {
      this.productList = products;
    }

    public Fridge createFridge() {
      if (StringUtils.isEmptyOrWhitespace(this.name)) {
        this.name = this.id;
      }
      return new Fridge(id, name, link, productList);
    }
  }
}
