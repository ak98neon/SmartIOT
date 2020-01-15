package com.smart.iot.home;

import com.smart.iot.home.entity.Fridge;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FridgeRepository extends MongoRepository<Fridge, String> {

  Optional<Fridge> findById(String id);
}
