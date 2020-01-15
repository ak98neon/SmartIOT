package com.smart.iot.web;

import static com.smart.iot.supply.UrlGenerator.getBaseUrl;

import com.smart.iot.home.IotService;
import com.smart.iot.home.entity.Fridge;
import java.util.Base64;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/iot/fridges")
public class FridgeController {

  private IotService iotService;

  public FridgeController(IotService iotService) {
    this.iotService = iotService;
  }

  @PostMapping
  public String createFridge(HttpServletRequest request, Model model) {
    Fridge fridge = iotService.registerFridge(getBaseUrl(request));
    model.addAttribute("qrCode", Base64.getEncoder().encodeToString(fridge.getQrLink()));
    return "fridge/newFridge";
  }

  @GetMapping("/{id}")
  public Fridge getById(@PathVariable String id) {
    return iotService.findFridgeById(id);
  }

  @GetMapping
  @ResponseBody
  public String getAllFridges(Model model) {
    model.addAttribute("fridges", iotService.getAllFridges());
    return "fridge/fridges";
  }
}
