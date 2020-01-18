package com.smart.iot.kit;

import com.smart.iot.kit.entity.Product;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

  Optional<Product> findById(String id);

  Optional<Product> findByBarcode(String barcode);
}
