package com.smart.iot.kit;

import com.smart.iot.kit.entity.Product;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, String>, ProductRepositoryCustom {

  @Transactional(value = TxType.REQUIRES_NEW)
  @Override
  <S extends Product> S saveAndFlush(S s);
}
