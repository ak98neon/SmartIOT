package com.smart.iot.kit;

import com.smart.iot.kit.entity.Product;

public interface ProductRepositoryCustom {

  Product findByBarcode(String barcode);
}
