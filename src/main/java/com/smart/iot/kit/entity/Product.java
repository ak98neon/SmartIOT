package com.smart.iot.kit.entity;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.function.Consumer;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "products")
public class Product extends BaseAuditEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false, unique = true)
  private Long id;
  private String name;
  @Column(name = "type_product")
  private TypeProduct typeProduct;
  private String barcode;
  private Long price;

  public Product() {
  }

  public Product(String name, TypeProduct typeProduct, String barcode, Long price) {
    super(
        OffsetDateTime.now(), OffsetDateTime.now()
    );
    this.name = name;
    this.typeProduct = typeProduct;
    this.barcode = barcode;
    this.price = price;
  }

  public Product(OffsetDateTime createdAt, OffsetDateTime updatedAt) {
    super(createdAt, updatedAt);
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

  public String getBarcode() {
    return barcode;
  }

  public void setBarcode(String barcode) {
    this.barcode = barcode;
  }

  public Long getPrice() {
    return price;
  }

  public static class ProductCreator {

    private String name;
    private TypeProduct typeProduct;
    private String barcode;
    private Long price;

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

    public void barcode(String barcode) {
      Objects.requireNonNull(barcode);
      this.barcode = barcode;
    }

    public void price(Long price) {
      Objects.requireNonNull(price);
      this.price = price;
    }

    public Product create() {
      return new Product(
          name, typeProduct, barcode, price
      );
    }
  }
}
