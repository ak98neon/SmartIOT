package com.smart.iot.home;

import com.smart.iot.home.entity.Product;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

  Optional<Product> findById(String id);

  Optional<Product> findByBarcode(String barcode);
}
