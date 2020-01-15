package com.smart.iot.home;

import com.google.zxing.WriterException;
import com.smart.iot.SmartIotException;
import com.smart.iot.home.entity.Fridge;
import com.smart.iot.home.entity.Fridge.Builder;
import com.smart.iot.qr.QrCodeGenerator;
import java.io.IOException;
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
    String link = "localhost:8080/iot/fridges/" + id;
    Fridge fridge = new Builder().with(x -> {
      x.id(id);
      try {
        x.link(qrCodeGenerator.generateQRCodeImage(link));
      } catch (WriterException | IOException e) {
        throw new SmartIotException(e.getMessage());
      }
      x.productList(Collections.emptyList());
    }).createFridge();
    return fridgeRepository.save(fridge);
  }

  @Override
  public List<Fridge> getAllFridges() {
    return fridgeRepository.findAll();
  }

  private String generateUniqueId() {
    String id = UUID.randomUUID().toString();
    if (fridgeRepository.findById(id).isPresent()) {
      return generateUniqueId();
    }
    return id;
  }
}
