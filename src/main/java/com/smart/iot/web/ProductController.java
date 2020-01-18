package com.smart.iot.web;

import static com.smart.iot.supply.SneakyTrow.sneaky;
import static com.smart.iot.web.config.ApiConfig.PREFIX;

import com.smart.iot.code.BarcodeReader;
import com.smart.iot.home.IotService;
import com.smart.iot.web.dto.CreateProductRequest;
import java.util.logging.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(PREFIX + "/products")
public class ProductController {

  private Logger logger = Logger.getLogger(ProductController.class.getName());

  private IotService iotService;

  public ProductController(IotService iotService) {
    this.iotService = iotService;
  }

  @PostMapping
  public String createProduct(@RequestBody CreateProductRequest createProductRequest) {
    if (createProductRequest.getFile() != null) {
      String barcode = sneaky(() -> BarcodeReader.readBarcodeFromImage(
          createProductRequest.getFile().getInputStream())
      );
      logger.info("Get a product barcode: " + barcode);
      iotService
          .createProduct(createProductRequest.getCount(), createProductRequest.getExpiredDate(),
              createProductRequest.getPrice(), createProductRequest.getTypeProduct(),
              createProductRequest.getName(),
              barcode, createProductRequest.getFridgeId());
    }
    iotService
        .createProduct(createProductRequest.getCount(), createProductRequest.getExpiredDate(),
            createProductRequest.getPrice(), createProductRequest.getTypeProduct(),
            createProductRequest.getName(),
            createProductRequest.getBarcodeReq(), createProductRequest.getFridgeId());
    return "product/newProduct";
  }
}
