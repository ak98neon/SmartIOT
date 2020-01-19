package com.smart.iot.kit.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.function.Consumer;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("product")
public class ProductItem {

  @Id
  private String id;
  private String name;
  private TypeProduct typeProduct;
  private Integer count;
  private String barcode;
  private Long price;
  private LocalDate expiredDate;
  @DBRef
  private Fridge fridge;

  public ProductItem(String id, String name, TypeProduct typeProduct, Integer count,
      String barcode, Long price, LocalDate expiredDate, Fridge fridge) {
    this.id = id;
    this.name = name;
    this.typeProduct = typeProduct;
    this.count = count;
    this.barcode = barcode;
    this.price = price;
    this.expiredDate = expiredDate;
    this.fridge = fridge;
  }

  public static ProductItem ofProduct(Product product, String id) {
    return new ProductItem(
        id,
        product.getName(), product.getTypeProduct(), null, product.getBarcode(), product.getPrice(),
        null, null
    );
  }

  public static ProductItem ofProductFull(Product product, Integer count, LocalDate expiredDate,
      Fridge fridge, String id) {
    return new ProductItem(
        id,
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

  public String getId() {
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

    public void expiredDate(LocalDate expiredDate) {
      Objects.requireNonNull(expiredDate);
      this.expiredDate = expiredDate;
    }

    public void fridge(Fridge fridge) {
      this.fridge = fridge;
    }

    public ProductItem create() {
      return new ProductItem(
          id, name, typeProduct, count, barcode, price, expiredDate, fridge
      );
    }
  }
}
