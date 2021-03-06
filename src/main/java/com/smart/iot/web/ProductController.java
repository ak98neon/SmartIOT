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
  public String getProductsByFridgeId(@PathVariable Long fridgeId, Model model) {
    List<ProductItemDto> allProductsByFridgeId =
        ProductItemDto.ofList(iotService.findAllProductsByFridgeId(fridgeId));
    model.addAttribute("products", allProductsByFridgeId);
    return "product/listProducts";
  }

  @PostMapping("/{fridgeId}")
  public String addNewProduct(@PathVariable Long fridgeId, Model model) {
    Optional<Fridge> byId = fridgeRepository.findById(fridgeId);
    model.addAttribute("fridgeId", byId.orElseThrow(NullPointerException::new).getId());
    return "product/newProduct";
  }

  @PostMapping("/{id}/delete/fridge/{fridgeId}")
  public String removeProduct(@PathVariable Long id, @PathVariable String fridgeId) {
    iotService.deleteItemFromFridge(id);
    return "redirect:/iot/products/" + fridgeId;
  }
}
