package com.smart.iot.web;

import static com.smart.iot.supply.UrlGenerator.getBaseUrl;
import static com.smart.iot.web.config.ApiConfig.PREFIX;

import com.smart.iot.kit.IotService;
import com.smart.iot.kit.entity.Fridge;
import java.util.Base64;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(PREFIX + "/fridges")
public class FridgeController {

  private IotService iotService;

  public FridgeController(IotService iotService) {
    this.iotService = iotService;
  }

  @PostMapping
  public String createFridge(HttpServletRequest request, Model model,
      @RequestParam(name = "fridge_name", required = false) String fridgeName) {
    Fridge fridge = iotService.registerFridge(getBaseUrl(request), fridgeName);
    model.addAttribute("qrCode", Base64.getEncoder().encodeToString(fridge.getQrLink()));
    return "fridge/newFridge";
  }

  @GetMapping("/{id}")
  public Fridge getById(@PathVariable String id) {
    return iotService.findFridgeById(id);
  }

  @GetMapping
  public String getAllFridges(Model model) {
    model.addAttribute("fridges", iotService.getAllFridges());
    return "fridge/fridges";
  }
}
