package com.smart.iot.web;

import static com.smart.iot.supply.SneakyTrow.sneaky;
import static com.smart.iot.web.config.ApiConfig.PREFIX;

import com.smart.iot.code.BarcodeReader;
import java.util.logging.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(PREFIX + "/products")
public class ProductController {

  private Logger logger = Logger.getLogger(ProductController.class.getName());

  @PostMapping
  public String createProduct(@RequestParam(value = "file", required = false) MultipartFile file,
      Model model) {
    String barcode = "cannot parse";
    if (file != null) {
      barcode = sneaky(() -> BarcodeReader.readBarcodeFromImage(file.getInputStream()));
      logger.info("Get a product barcode: " + barcode);
    }
    model.addAttribute("barcode", barcode);
    return "product/newProduct";
  }
}
