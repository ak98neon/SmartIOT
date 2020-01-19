package com.smart.iot.kit.entity;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.function.Consumer;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name = "product", schema = "public")
@Entity(name = "product")
public class Product extends BaseAuditEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false, unique = true)
  private Long id;
  private String name;
  @Column(name = "type_product")
  private TypeProduct typeProduct;
  @Transient
  private Integer count;
  private String barcode;
  private Long price;
  @Transient
  private LocalDate expiredDate;
  @Transient
  private String fridge;

  public Product() {
  }

  public Product(String name, TypeProduct typeProduct, Integer count,
      String barcode, Long price, LocalDate expiredDate, String fridge) {
    this.name = name;
    this.typeProduct = typeProduct;
    this.count = count;
    this.barcode = barcode;
    this.price = price;
    this.expiredDate = expiredDate;
    this.fridge = fridge;
    super.setCreatedAt(OffsetDateTime.now());
    super.setUpdatedAt(OffsetDateTime.now());
  }

  public static LocalDate parseExpiredDate(String date) {
    return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
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

  public LocalDate getExpiredDate() {
    return expiredDate;
  }

  public void setExpiredDate(LocalDate expiredDate) {
    this.expiredDate = expiredDate;
  }

  public Long getPrice() {
    return price;
  }

  public String getFridge() {
    return fridge;
  }

  public static class ProductCreator {

    private String name;
    private TypeProduct typeProduct;
    private Integer count;
    private String barcode;
    private Long price;
    private LocalDate expiredDate;
    private String fridge;

    public ProductCreator with(Consumer<ProductCreator> consumer) {
      consumer.accept(this);
      return this;
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

    public void expiredDate(LocalDate expiredDate) {
      Objects.requireNonNull(expiredDate);
      this.expiredDate = expiredDate;
    }

    public void fridge(String fridge) {
      this.fridge = fridge;
    }

    public Product create() {
      return new Product(
          name, typeProduct, count, barcode, price, expiredDate, fridge
      );
    }
  }
}
