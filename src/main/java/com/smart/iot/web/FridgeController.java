package com.smart.iot.web;

import com.smart.iot.home.IotService;
import com.smart.iot.home.entity.Fridge;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
  public ResponseEntity<byte[]> createFridge() {
    Fridge fridge = iotService.registerFridge();
    final HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.IMAGE_PNG);

    return new ResponseEntity<>(fridge.getQrLink(), headers, HttpStatus.CREATED);
  }

  @GetMapping
  @ResponseBody
  public List<Fridge> getAllFridges() {
    return iotService.getAllFridges();
  }
}
