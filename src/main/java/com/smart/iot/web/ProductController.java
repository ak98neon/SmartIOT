package com.smart.iot.web;

import static com.smart.iot.supply.SneakyTrow.sneaky;
import static com.smart.iot.web.config.ApiConfig.PREFIX;

import com.smart.iot.code.BarcodeReader;
import com.smart.iot.kit.IotService;
import com.smart.iot.kit.entity.Product;
import com.smart.iot.web.dto.CreateProductRequest;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.logging.Logger;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;

@RestController
@RequestMapping(value = PREFIX + "/products", consumes = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {

  private Logger logger = Logger.getLogger(ProductController.class.getName());

  private IotService iotService;

  public ProductController(IotService iotService) {
    this.iotService = iotService;
  }

  @PostMapping
  public Product createProduct(
      @RequestBody CreateProductRequest createProductRequest) {
    if (!StringUtils.isEmptyOrWhitespace(createProductRequest.getFile())) {
      byte[] imageByte = Base64.decodeBase64(createProductRequest.getFile());
      InputStream inputStream = new ByteArrayInputStream(imageByte);
      String barcode = sneaky(() -> BarcodeReader.readBarcodeFromImage(
          inputStream
      ));
      logger.info("Get a product barcode: " + barcode);
      iotService
          .createProduct(createProductRequest.getCount(), createProductRequest.getExpiredDate(),
              createProductRequest.getPrice(), createProductRequest.getTypeProduct(),
              createProductRequest.getName(),
              barcode, createProductRequest.getFridgeId());
    }
    return iotService
        .createProduct(createProductRequest.getCount(), createProductRequest.getExpiredDate(),
            createProductRequest.getPrice(), createProductRequest.getTypeProduct(),
            createProductRequest.getName(),
            createProductRequest.getBarcodeReq(), createProductRequest.getFridgeId());
  }
}
