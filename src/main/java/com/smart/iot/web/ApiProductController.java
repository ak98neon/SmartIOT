package com.smart.iot.web;

import static com.smart.iot.web.config.ApiConfig.PREFIX;

import com.smart.iot.kit.IotService;
import com.smart.iot.kit.entity.Product;
import com.smart.iot.web.dto.CreateProductRequest;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;

@RestController
@RequestMapping(value = PREFIX + "/products", consumes = MediaType.APPLICATION_JSON_VALUE)
public class ApiProductController {

  private IotService iotService;

  public ApiProductController(IotService iotService) {
    this.iotService = iotService;
  }

  @PostMapping
  public Product createProduct(
      @RequestBody CreateProductRequest createProductRequest) {
    if (!StringUtils.isEmptyOrWhitespace(createProductRequest.getFile())) {
      byte[] imageByte = Base64.decodeBase64(createProductRequest.getFile());
      return iotService
          .createProductByBarcode(createProductRequest.getCount(),
              createProductRequest.getExpiredDate(),
              createProductRequest.getFridgeId(),
              imageByte
          );
    }
    return iotService
        .createProductDefault(createProductRequest.getCount(),
            createProductRequest.getExpiredDate(),
            createProductRequest.getPrice(), createProductRequest.getTypeProduct(),
            createProductRequest.getName(),
            createProductRequest.getBarcodeReq(), createProductRequest.getFridgeId());
  }
}
