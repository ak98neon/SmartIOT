package com.smart.iot.web.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.smart.iot.home.entity.TypeProduct;
import java.time.OffsetDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = Include.NON_NULL)
public class CreateProductRequest {

  @JsonProperty("count")
  private Integer count;
  @JsonProperty("expired_date")
  private OffsetDateTime expiredDate;
  @JsonProperty("price")
  private Long price;
  @JsonProperty("type_product")
  private TypeProduct typeProduct;
  @JsonProperty("name")
  private String name;
  @JsonProperty("barcode")
  private String barcodeReq;
  @JsonProperty("fridge_id")
  private String fridgeId;
  @JsonProperty("file")
  private String file;

  public CreateProductRequest() {
  }

  @JsonCreator
  public CreateProductRequest(Integer count, OffsetDateTime expiredDate, Long price,
      TypeProduct typeProduct, String name, String barcodeReq, String fridgeId,
      String file) {
    this.count = count;
    this.expiredDate = expiredDate;
    this.price = price;
    this.typeProduct = typeProduct;
    this.name = name;
    this.barcodeReq = barcodeReq;
    this.fridgeId = fridgeId;
    this.file = file;
  }

  public String getFile() {
    return file;
  }

  public Integer getCount() {
    return count;
  }

  public OffsetDateTime getExpiredDate() {
    return expiredDate;
  }

  public Long getPrice() {
    return price;
  }

  public TypeProduct getTypeProduct() {
    return typeProduct;
  }

  public String getName() {
    return name;
  }

  public String getBarcodeReq() {
    return barcodeReq;
  }

  public String getFridgeId() {
    return fridgeId;
  }
}
