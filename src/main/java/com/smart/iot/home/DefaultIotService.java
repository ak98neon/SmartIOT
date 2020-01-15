package com.smart.iot.home;

import static com.smart.iot.supply.SneakyTrow.sneaky;

import com.smart.iot.home.entity.Fridge;
import com.smart.iot.home.entity.Fridge.Builder;
import com.smart.iot.qr.QrCodeGenerator;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class DefaultIotService implements IotService {

  private FridgeRepository fridgeRepository;
  private QrCodeGenerator qrCodeGenerator;

  public DefaultIotService(FridgeRepository fridgeRepository,
      QrCodeGenerator qrCodeGenerator) {
    this.fridgeRepository = fridgeRepository;
    this.qrCodeGenerator = qrCodeGenerator;
  }

  @Override
  public Fridge registerFridge() {
    String id = generateUniqueId();
    String link = "http://localhost:8080/iot/fridges/" + id;
    Fridge fridge = new Builder().with(x -> {
      x.id(id);
      x.link(sneaky(() -> qrCodeGenerator.generateQRCodeImage(link)));
      x.productList(Collections.emptyList());
    }).createFridge();
    return fridgeRepository.save(fridge);
  }

  @Override
  public List<Fridge> getAllFridges() {
    return fridgeRepository.findAll();
  }

  @Override
  public Fridge findFridgeById(String id) {
    return fridgeRepository.findById(id).orElse(null);
  }

  private String generateUniqueId() {
    String id = UUID.randomUUID().toString();
    if (fridgeRepository.findById(id).isPresent()) {
      return generateUniqueId();
    }
    return id;
  }
}
