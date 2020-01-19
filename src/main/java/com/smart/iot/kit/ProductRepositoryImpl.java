package com.smart.iot.kit;

import com.smart.iot.kit.entity.Product;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepositoryImpl implements ProductRepositoryCustom {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public Product findByBarcode(String barcode) {
    final String query = "SELECT * FROM product p WHERE p.barcode = ?";
    try {
      Object singleResult = entityManager.createNativeQuery(query).setParameter(1, barcode)
          .getSingleResult();
      return (Product) singleResult;
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return null;
  }
}
