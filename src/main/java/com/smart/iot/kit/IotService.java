package com.smart.iot.kit;

import com.smart.iot.kit.entity.Fridge;
import com.smart.iot.kit.entity.Product;
import com.smart.iot.kit.entity.ProductItem;
import com.smart.iot.kit.entity.TypeProduct;
import java.util.List;

public interface IotService {

  Fridge registerFridgeByUser(String baseUrl, String name);

  Product createProductByBarcode(String name, Integer count, String expiredDate,
      Long fridgeId, byte[] imageBarcode, Long price);

  Product createProductDefault(Integer count, String expiredDate, Long price,
      TypeProduct typeProduct, String name, String barcode, Long fridgeId);

  List<Fridge> getAllFridgesByUser();

  Fridge findFridgeById(Long id);

  List<ProductItem> findAllProductsByFridgeId(Long fridgeId);

  ProductItem deleteItemFromFridge(Long id);
}
