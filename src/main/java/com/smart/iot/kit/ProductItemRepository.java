package com.smart.iot.kit;

import com.smart.iot.kit.entity.ProductItem;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductItemRepository extends MongoRepository<ProductItem, String> {

}
