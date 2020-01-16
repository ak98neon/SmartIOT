package com.smart.iot.web;

import static com.smart.iot.supply.SneakyTrow.sneaky;
import static com.smart.iot.web.config.ApiConfig.PREFIX;

import com.smart.iot.code.BarcodeReader;
import com.smart.iot.home.IotService;
import com.smart.iot.web.dto.CreateProductRequest;
import java.util.logging.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(PREFIX + "/products")
public class ProductController {

  private Logger logger = Logger.getLogger(ProductController.class.getName());

  private IotService iotService;

  public ProductController(IotService iotService) {
    this.iotService = iotService;
  }

  @PostMapping
  public String createProduct(@RequestParam(value = "file", required = false) MultipartFile file,
      Model model, @ModelAttribute CreateProductRequest createProductRequest) {
    if (file != null) {
      String barcode = sneaky(() -> BarcodeReader.readBarcodeFromImage(file.getInputStream()));
      logger.info("Get a product barcode: " + barcode);
      model.addAttribute("barcode", barcode);
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
    model.addAttribute("barcode", createProductRequest.getBarcodeReq());
    return "product/newProduct";
  }
}
