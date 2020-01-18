package com.smart.iot.kit;

import static com.smart.iot.supply.SneakyTrow.sneaky;

import com.smart.iot.code.QrCodeGenerator;
import com.smart.iot.kit.entity.Fridge;
import com.smart.iot.kit.entity.Fridge.Builder;
import com.smart.iot.kit.entity.Product;
import com.smart.iot.kit.entity.Product.ProductCreator;
import com.smart.iot.kit.entity.TypeProduct;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class DefaultIotService implements IotService {

  private FridgeRepository fridgeRepository;
  private ProductRepository productRepository;
  private QrCodeGenerator qrCodeGenerator;

  public DefaultIotService(FridgeRepository fridgeRepository,
      ProductRepository productRepository, QrCodeGenerator qrCodeGenerator) {
    this.fridgeRepository = fridgeRepository;
    this.productRepository = productRepository;
    this.qrCodeGenerator = qrCodeGenerator;
  }

  @Override
  public Fridge registerFridge(String baseUrl, String name) {
    String id = generateUniqueIdForFridge();
    String link = baseUrl + "/iot/fridges/" + id;
    Fridge fridge = new Builder().with(x -> {
      x.id(id);
      x.name(name);
      x.link(sneaky(() -> qrCodeGenerator.generateQRCodeImage(link)));
      x.productList(Collections.emptyList());
    }).createFridge();
    return fridgeRepository.save(fridge);
  }

  @Override
  public Product createProduct(Integer count, OffsetDateTime expiredDate, Long price,
      TypeProduct typeProduct, String name, String barcode, String fridgeId) {
    String uniqueIdForProduct = generateUniqueIdForProduct();

    Optional<Fridge> byId = fridgeRepository.findById(fridgeId);
    Fridge fridge = byId.orElseThrow(RuntimeException::new);

    return new ProductCreator().with(x -> {
      x.id(uniqueIdForProduct);
      x.barcode(barcode);
      x.count(count);
      x.expiredDate(expiredDate);
      x.price(price);
      if (typeProduct == null) {
        x.typeProduct(TypeProduct.NONE);
      } else {
        x.typeProduct(typeProduct);
      }
      x.name(name);
      x.fridge(fridge);
    }).create();
  }

  @Override
  public List<Fridge> getAllFridges() {
    return fridgeRepository.findAll();
  }

  @Override
  public Fridge findFridgeById(String id) {
    return fridgeRepository.findById(id).orElse(null);
  }

  private String generateUniqueIdForFridge() {
    String id = UUID.randomUUID().toString();
    if (fridgeRepository.findById(id).isPresent()) {
      return generateUniqueIdForFridge();
    }
    return id;
  }

  private String generateUniqueIdForProduct() {
    String id = UUID.randomUUID().toString();
    if (productRepository.findById(id).isPresent()) {
      return generateUniqueIdForProduct();
    }
    return id;
  }
}
