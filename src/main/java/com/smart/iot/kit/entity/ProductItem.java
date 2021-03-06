package com.smart.iot.kit.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.function.Consumer;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "product_items")
public class ProductItem extends BaseAuditEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  @Column(name = "type_product")
  private TypeProduct typeProduct;
  private Integer count;
  private String barcode;
  private Long price;
  @Column(name = "expired_date")
  private LocalDate expiredDate;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "fridge_id")
  @JsonIgnore
  private Fridge fridge;

  public ProductItem() {
  }

  private ProductItem(String name, TypeProduct typeProduct, Integer count,
      String barcode, Long price, LocalDate expiredDate, Fridge fridge) {
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

  public static ProductItem ofProduct(Product product) {
    return new ProductItem(
        product.getName(), product.getTypeProduct(), null, product.getBarcode(), product.getPrice(),
        null, null
    );
  }

  public static ProductItem ofProductFull(Product product, Integer count, LocalDate expiredDate,
      Fridge fridge) {
    return new ProductItem(
        product.getName(), product.getTypeProduct(), count, product.getBarcode(),
        product.getPrice(),
        expiredDate, fridge
    );
  }

  public static LocalDate parseExpiredDate(String date) {
    return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
  }

  public static ProductItemBuilder builder() {
    return new ProductItemBuilder();
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public TypeProduct getTypeProduct() {
    return typeProduct;
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

  public Long getPrice() {
    return price;
  }

  public LocalDate getExpiredDate() {
    return expiredDate;
  }

  public void setExpiredDate(LocalDate expiredDate) {
    this.expiredDate = expiredDate;
  }

  public Fridge getFridge() {
    return fridge;
  }

  public void setFridge(Fridge fridge) {
    this.fridge = fridge;
  }

  public static class ProductItemBuilder {

    private String id;
    private String name;
    private TypeProduct typeProduct;
    private Integer count;
    private String barcode;
    private Long price;
    private LocalDate expiredDate;
    private Fridge fridge;

    public ProductItemBuilder with(Consumer<ProductItemBuilder> consumer) {
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

    public void fridge(Fridge fridge) {
      this.fridge = fridge;
    }

    public ProductItem create() {
      return new ProductItem(
          name, typeProduct, count, barcode, price, expiredDate, fridge
      );
    }
  }
}
