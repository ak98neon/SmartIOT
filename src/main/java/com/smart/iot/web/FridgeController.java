package com.smart.iot.web;

import static com.smart.iot.supply.UrlGenerator.getBaseUrl;

import com.smart.iot.home.IotService;
import com.smart.iot.home.entity.Fridge;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/iot/fridges")
public class FridgeController {

  private IotService iotService;

  public FridgeController(IotService iotService) {
    this.iotService = iotService;
  }

  @PostMapping
  public ResponseEntity<byte[]> createFridge(HttpServletRequest request) {
    Fridge fridge = iotService.registerFridge(getBaseUrl(request));
    final HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.IMAGE_PNG);

    return new ResponseEntity<>(fridge.getQrLink(), headers, HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public Fridge getById(@PathVariable String id) {
    return iotService.findFridgeById(id);
  }

  @GetMapping
  @ResponseBody
  public List<Fridge> getAllFridges() {
    return iotService.getAllFridges();
  }
}
