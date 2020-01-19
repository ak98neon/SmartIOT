package com.smart.iot.kit;

import com.smart.iot.kit.entity.Fridge;
import com.smart.iot.kit.entity.Product;
import com.smart.iot.kit.entity.TypeProduct;
import java.util.List;

public interface IotService {

  Fridge registerFridge(String baseUrl, String name);

  Product createProductByBarcode(Integer count, String expiredDate,
      String fridgeId, byte[] imageBarcode);

  Product createProductDefault(Integer count, String expiredDate, Long price,
      TypeProduct typeProduct, String name, String barcode, String fridgeId);

  List<Fridge> getAllFridges();

  Fridge findFridgeById(String id);
}
