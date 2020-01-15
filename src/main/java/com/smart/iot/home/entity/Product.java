package com.smart.iot.home.entity;

import java.time.OffsetDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("product")
public class Product {

  @Id
  private String id;
  private String name;
  private TypeProduct typeProduct;
  private Integer count;
  private Integer barCode;
  private OffsetDateTime expiredDate;

  public Product(String id, String name, TypeProduct typeProduct, Integer count,
      Integer barCode, OffsetDateTime expiredDate) {
    this.id = id;
    this.name = name;
    this.typeProduct = typeProduct;
    this.count = count;
    this.barCode = barCode;
    this.expiredDate = expiredDate;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public TypeProduct getTypeProduct() {
    return typeProduct;
  }

  public void setTypeProduct(TypeProduct typeProduct) {
    this.typeProduct = typeProduct;
  }

  public Integer getCount() {
    return count;
  }

  public void setCount(Integer count) {
    this.count = count;
  }

  public Integer getBarCode() {
    return barCode;
  }

  public void setBarCode(Integer barCode) {
    this.barCode = barCode;
  }

  public OffsetDateTime getExpiredDate() {
    return expiredDate;
  }

  public void setExpiredDate(OffsetDateTime expiredDate) {
    this.expiredDate = expiredDate;
  }
}
