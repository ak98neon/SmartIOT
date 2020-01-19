package com.smart.iot.kit;

import static com.smart.iot.supply.SneakyTrow.sneaky;

import com.google.zxing.NotFoundException;
import com.smart.iot.code.BarcodeReader;
import com.smart.iot.code.QrCodeGenerator;
import com.smart.iot.kit.entity.Fridge;
import com.smart.iot.kit.entity.Fridge.Builder;
import com.smart.iot.kit.entity.Product;
import com.smart.iot.kit.entity.Product.ProductCreator;
import com.smart.iot.kit.entity.TypeProduct;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class DefaultIotService implements IotService {

  private final Logger logger = Logger.getLogger(DefaultIotService.class.getName());

  private FridgeRepository fridgeRepository;
  private ProductRepository productRepository;
  private QrCodeGenerator qrCodeGenerator;

  public DefaultIotService(FridgeRepository fridgeRepository,
      ProductRepository productRepository, QrCodeGenerator qrCodeGenerator) {
    this.fridgeRepository = fridgeRepository;
    this.productRepository = productRepository;
    this.qrCodeGenerator = qrCodeGenerator;
  }

  @Transactional
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

  @Transactional
  @Override
  public Product createProductByBarcode(Integer count, String expiredDate, String fridgeId,
      byte[] imageBarcode) {
    InputStream inputStream = new ByteArrayInputStream(imageBarcode);
    Product product = null;

    try {
      Optional<Fridge> byId = fridgeRepository.findByName(fridgeId);
      Fridge fridge = byId.orElseThrow(RuntimeException::new);

      String barcode = BarcodeReader.readBarcodeFromImage(inputStream);
      if (barcode != null) {
        logger.info("Get a product barcode: " + barcode);
        Product byBarcode = productRepository.findByBarcode(barcode);
        if (byBarcode != null) {
          //TODO Must create a new product
          fridge.getProductList().add(byBarcode);
        } else {
          product = new ProductCreator().with(x -> {
            x.barcode(barcode);
            x.count(count);
            x.expiredDate(Product.parseExpiredDate(expiredDate));
            x.price(0L);
            x.typeProduct(TypeProduct.NONE);
            x.name("");
            x.fridge(fridge.getId());
          }).create();
          Product save = productRepository.saveAndFlush(product);
          fridge.getProductList().add(save);
        }
        fridgeRepository.save(fridge);
      }
    } catch (NotFoundException | IOException e) {
      logger.info(e.getMessage());
    }
    return product;
  }

  @Transactional
  @Override
  public Product createProductDefault(Integer count, String expiredDate, Long price,
      TypeProduct typeProduct, String name, String barcode, String fridgeId) {
    Optional<Fridge> byId = fridgeRepository.findByName(fridgeId);
    Fridge fridge = byId.orElseThrow(RuntimeException::new);

    Product product = new ProductCreator().with(x -> {
      x.barcode(barcode);
      x.count(count);
      x.expiredDate(Product.parseExpiredDate(expiredDate));
      x.price(price);
      if (typeProduct == null) {
        x.typeProduct(TypeProduct.NONE);
      } else {
        x.typeProduct(typeProduct);
      }
      x.name(name);
      x.fridge(fridge.getId());
    }).create();
    Product saveAndFlush = productRepository.saveAndFlush(product);
    fridge.getProductList().add(saveAndFlush);
    fridgeRepository.save(fridge);
    return product;
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
}
