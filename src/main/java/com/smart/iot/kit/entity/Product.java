package com.smart.iot.kit.entity;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.function.Consumer;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("product")
public class Product {

  @Id
  private String id;
  private String name;
  private TypeProduct typeProduct;
  private Integer count;
  private String barcode;
  private Long price;
  private OffsetDateTime expiredDate;
  @DBRef
  private Fridge fridge;

  public Product() {
  }

  public Product(String id, String name, TypeProduct typeProduct, Integer count,
      String barcode, Long price, OffsetDateTime expiredDate, Fridge fridge) {
    this.id = id;
    this.name = name;
    this.typeProduct = typeProduct;
    this.count = count;
    this.barcode = barcode;
    this.price = price;
    this.expiredDate = expiredDate;
    this.fridge = fridge;
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

  public String getBarcode() {
    return barcode;
  }

  public void setBarcode(String barcode) {
    this.barcode = barcode;
  }

  public OffsetDateTime getExpiredDate() {
    return expiredDate;
  }

  public void setExpiredDate(OffsetDateTime expiredDate) {
    this.expiredDate = expiredDate;
  }

  public Long getPrice() {
    return price;
  }

  public static class ProductCreator {

    private String id;
    private String name;
    private TypeProduct typeProduct;
    private Integer count;
    private String barcode;
    private Long price;
    private OffsetDateTime expiredDate;
    private Fridge fridge;

    public ProductCreator with(Consumer<ProductCreator> consumer) {
      consumer.accept(this);
      return this;
    }

    public void id(String id) {
      Objects.requireNonNull(id);
      this.id = id;
    }

    public void name(String name) {
      Objects.requireNonNull(name);
      this.name = name;
    }

    public void typeProduct(TypeProduct typeProduct) {
      Objects.requireNonNull(typeProduct);
      this.typeProduct = typeProduct;
    }

    public void count(Integer count) {
      Objects.requireNonNull(count);
      this.count = count;
    }

    public void barcode(String barcode) {
      Objects.requireNonNull(barcode);
      this.barcode = barcode;
    }

    public void price(Long price) {
      Objects.requireNonNull(price);
      this.price = price;
    }

    public void expiredDate(OffsetDateTime expiredDate) {
      Objects.requireNonNull(expiredDate);
      this.expiredDate = expiredDate;
    }

    public void fridge(Fridge fridge) {
      this.fridge = fridge;
    }

    public Product create() {
      return new Product(
          id, name, typeProduct, count, barcode, price, expiredDate, fridge
      );
    }
  }
}
