package com.smart.iot.home.entity;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("fridge")
public class Fridge {

  @Id
  private String id;
  private byte[] qrLink;
  private List<Product> productList;

  public Fridge() {
  }

  private Fridge(String id, byte[] qrLink, List<Product> productList) {
    this.id = id;
    this.qrLink = qrLink;
    this.productList = productList;
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
        ", qrLink='" + Arrays.toString(qrLink) + '\'' +
        ", productList=" + productList +
        '}';
  }

  public static class Builder {

    private String id;
    private byte[] link;
    private List<Product> productList;

    public Builder with(Consumer<Builder> builderConsumer) {
      builderConsumer.accept(this);
      return this;
    }

    public void id(String id) {
      this.id = id;
    }

    public void link(byte[] link) {
      this.link = link;
    }

    public void productList(List<Product> products) {
      this.productList = products;
    }

    public Fridge createFridge() {
      return new Fridge(id, link, productList);
    }
  }
}
