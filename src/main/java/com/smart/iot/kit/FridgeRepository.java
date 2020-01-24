package com.smart.iot.kit;

import com.smart.iot.kit.entity.Fridge;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FridgeRepository extends JpaRepository<Fridge, Long> {

  Optional<Fridge> findById(String id);

  Optional<Fridge> findByName(String name);
}
