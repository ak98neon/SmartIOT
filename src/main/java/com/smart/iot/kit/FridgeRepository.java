package com.smart.iot.kit;

import com.smart.iot.kit.entity.Fridge;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FridgeRepository extends MongoRepository<Fridge, String> {

  Optional<Fridge> findById(String id);

  Optional<Fridge> findByName(String name);
}
