package com.smart.iot.web;

import static com.smart.iot.web.config.ApiConfig.PREFIX;

import com.smart.iot.kit.IotService;
import com.smart.iot.web.dto.ProductItemDto;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(PREFIX + "/products")
public class ProductController {

  private IotService iotService;

  public ProductController(IotService iotService) {
    this.iotService = iotService;
  }

  @GetMapping("/{fridgeId}")
  public String getProductsByFridgeId(@PathVariable String fridgeId, Model model) {
    List<ProductItemDto> allProductsByFridgeId =
        ProductItemDto.ofList(iotService.findAllProductsByFridgeId(fridgeId));
    model.addAttribute("products", allProductsByFridgeId);
    return "product/listProducts";
  }
}
