package com.smart.iot.kit;

import com.smart.iot.kit.entity.Fridge;
import com.smart.iot.kit.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FridgeRepository extends JpaRepository<Fridge, Long> {

  Fridge findByName(String name);

  List<Fridge> findAllByUsers(User user);
}
