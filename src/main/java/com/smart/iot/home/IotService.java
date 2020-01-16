package com.smart.iot.home;

import com.smart.iot.home.entity.Fridge;
import com.smart.iot.home.entity.Product;
import com.smart.iot.home.entity.TypeProduct;
import java.time.OffsetDateTime;
import java.util.List;

public interface IotService {

  Fridge registerFridge(String baseUrl, String name);

  Product createProduct(Integer count, OffsetDateTime expiredDate, Long price,
      TypeProduct typeProduct, String name, String barcode, String fridgeId);

  List<Fridge> getAllFridges();

  Fridge findFridgeById(String id);
}
