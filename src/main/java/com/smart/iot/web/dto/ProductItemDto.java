package com.smart.iot.web.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.smart.iot.kit.entity.ProductItem;
import com.smart.iot.kit.entity.TypeProduct;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ProductItemDto {

  private String id;
  private String name;
  @JsonProperty("type_product")
  private TypeProduct typeProduct;
  private Integer count;
  private String barcode;
  private Long price;
  @JsonProperty("expired_date")
  private LocalDate expiredDate;

  public ProductItemDto() {
  }

  @JsonCreator
  public ProductItemDto(String id, String name, TypeProduct typeProduct, Integer count,
      String barcode, Long price, LocalDate expiredDate) {
    this.id = id;
    this.name = name;
    this.typeProduct = typeProduct;
    this.count = count;
    this.barcode = barcode;
    this.price = price;
    this.expiredDate = expiredDate;
  }

  public static ProductItemDto of(ProductItem productItem) {
    return new ProductItemDto(
        productItem.getId(),
        productItem.getName(), productItem.getTypeProduct(), productItem.getCount(),
        productItem.getBarcode(), productItem.getPrice(), productItem.getExpiredDate()
    );
  }

  public static List<ProductItemDto> ofList(List<ProductItem> productItem) {
    return productItem.stream().map(ProductItemDto::of).collect(Collectors.toList());
  }

  public String getId() {
    return id;
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

  public Long getPrice() {
    return price;
  }

  public void setPrice(Long price) {
    this.price = price;
  }

  public LocalDate getExpiredDate() {
    return expiredDate;
  }

  public void setExpiredDate(LocalDate expiredDate) {
    this.expiredDate = expiredDate;
  }
}
