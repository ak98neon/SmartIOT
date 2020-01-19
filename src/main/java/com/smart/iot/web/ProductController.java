package com.smart.iot.web;

import static com.smart.iot.web.config.ApiConfig.PREFIX;

import com.smart.iot.kit.FridgeRepository;
import com.smart.iot.kit.IotService;
import com.smart.iot.kit.entity.Fridge;
import com.smart.iot.web.dto.ProductItemDto;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(PREFIX + "/products")
public class ProductController {

  private IotService iotService;
  private FridgeRepository fridgeRepository;

  public ProductController(IotService iotService, FridgeRepository fridgeRepository) {
    this.iotService = iotService;
    this.fridgeRepository = fridgeRepository;
  }

  @GetMapping("/{fridgeId}")
  public String getProductsByFridgeId(@PathVariable String fridgeId, Model model) {
    List<ProductItemDto> allProductsByFridgeId =
        ProductItemDto.ofList(iotService.findAllProductsByFridgeId(fridgeId));
    model.addAttribute("products", allProductsByFridgeId);
    return "product/listProducts";
  }

  @PostMapping("/{fridgeId}")
  public String addNewProduct(@PathVariable String fridgeId, Model model) {
    Optional<Fridge> byId = fridgeRepository.findById(fridgeId);
    model.addAttribute("fridgeName", byId.orElse(null).getName());
    return "product/newProduct";
  }
}
