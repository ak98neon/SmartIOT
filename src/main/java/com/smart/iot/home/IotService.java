package com.smart.iot.home;

import com.smart.iot.home.entity.Fridge;
import java.util.List;

public interface IotService {

  Fridge registerFridge(String baseUrl);

  List<Fridge> getAllFridges();

  Fridge findFridgeById(String id);
}
