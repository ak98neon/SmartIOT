package com.smart.iot.kit;

import static com.smart.iot.supply.SneakyTrow.sneaky;

import com.google.zxing.NotFoundException;
import com.smart.iot.code.BarcodeReader;
import com.smart.iot.code.QrCodeGenerator;
import com.smart.iot.kit.entity.Fridge;
import com.smart.iot.kit.entity.Fridge.Builder;
import com.smart.iot.kit.entity.Product;
import com.smart.iot.kit.entity.Product.ProductCreator;
import com.smart.iot.kit.entity.ProductItem;
import com.smart.iot.kit.entity.TypeProduct;
import com.smart.iot.kit.entity.User;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;
import javax.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class DefaultIotService implements IotService {

  private final Logger logger = Logger.getLogger(DefaultIotService.class.getName());

  private FridgeRepository fridgeRepository;
  private ProductRepository productRepository;
  private ProductItemRepository productItemRepository;
  private QrCodeGenerator qrCodeGenerator;
  private UserRepository userRepository;


  public DefaultIotService(FridgeRepository fridgeRepository,
      ProductRepository productRepository,
      ProductItemRepository productItemRepository, QrCodeGenerator qrCodeGenerator,
      UserRepository userRepository) {
    this.fridgeRepository = fridgeRepository;
    this.productRepository = productRepository;
    this.productItemRepository = productItemRepository;
    this.qrCodeGenerator = qrCodeGenerator;
    this.userRepository = userRepository;
  }

  @Transactional
  @Override
  public Fridge registerFridgeByUser(String baseUrl, String name) {
    final String username = SecurityContextHolder.getContext().getAuthentication().getName();
    User byUsername = userRepository.findByUsername(username);

    String uniqueNameForFridge = generateUniqueNameForFridge();
    String link = baseUrl + "/iot/products/" + uniqueNameForFridge;
    Fridge fridge = new Builder().with(x -> {
      x.name(name);
      x.link(sneaky(() -> qrCodeGenerator.generateQRCodeImage(link)));
      x.productList(Collections.emptyList());
      x.users(Collections.singletonList(byUsername));
    }).createFridge();
    return fridgeRepository.save(fridge);
  }

  @Transactional
  @Override
  public Product createProductByBarcode(String name, Integer count, String expiredDate,
      String fridgeId, byte[] imageBarcode, Long price) {
    InputStream inputStream = new ByteArrayInputStream(imageBarcode);
    Product product = null;

    try {
      Fridge fridge = fridgeRepository.findByName(fridgeId);

      String barcode = BarcodeReader.readBarcodeFromImage(inputStream);
      if (barcode != null) {
        logger.info("Get a product barcode: " + barcode);
        Product byBarcode = productRepository.findByBarcode(barcode);
        if (byBarcode != null) {
          ProductItem productItem = ProductItem.ofProductFull(
              byBarcode, count, ProductItem.parseExpiredDate(expiredDate), fridge
          );
          productItemRepository.save(productItem);
          saveProductItemIntoFridge(productItem, fridge);
        } else {
          product = new ProductCreator().with(x -> {
            x.barcode(barcode);
            x.price(price);
            x.typeProduct(TypeProduct.NONE);
            x.name(name);
          }).create();
          Product save = productRepository.saveAndFlush(product);

          ProductItem productItem = ProductItem.ofProductFull(
              save, count, ProductItem.parseExpiredDate(expiredDate), fridge
          );
          productItemRepository.save(productItem);
          saveProductItemIntoFridge(productItem, fridge);
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
    Fridge fridge = fridgeRepository.findByName(fridgeId);

    Product product = new ProductCreator().with(x -> {
      x.barcode(barcode);
      x.price(price);
      if (typeProduct == null) {
        x.typeProduct(TypeProduct.NONE);
      } else {
        x.typeProduct(typeProduct);
      }
      x.name(name);
    }).create();
    Product saveAndFlush = productRepository.saveAndFlush(product);

    ProductItem productItem = ProductItem.ofProductFull(
        saveAndFlush, count, ProductItem.parseExpiredDate(expiredDate), fridge
    );
    productItemRepository.save(productItem);
    saveProductItemIntoFridge(productItem, fridge);
    return product;
  }

  @Transactional
  @Override
  public ProductItem deleteItemFromFridge(Long id) {
    Optional<ProductItem> productItem = productItemRepository.findById(id);
    if (productItem.isPresent()) {
      Optional<Fridge> fridge = fridgeRepository.findById(productItem.get().getFridge().getId());
      productItemRepository.deleteById(id);

      Optional<ProductItem> first = fridge.get().getProductList().stream()
          .filter(x -> x.getId().equals(id)).findFirst();
      fridge.get().getProductList().remove(first.get());
      fridgeRepository.save(fridge.get());
      return productItem.get();
    }
    return null;
  }

  private void saveProductItemIntoFridge(ProductItem productItem, Fridge fridge) {
    fridge.getProductList().add(productItem);
    fridgeRepository.save(fridge);
  }

  @Override
  public List<Fridge> getAllFridgesByUser() {
    final String username = SecurityContextHolder.getContext().getAuthentication().getName();
    User byUsername = userRepository.findByUsername(username);

    return fridgeRepository.findAllByUsers(byUsername);
  }

  @Override
  public Fridge findFridgeById(Long id) {
    return fridgeRepository.findById(id).orElse(null);
  }

  @Override
  public List<ProductItem> findAllProductsByFridgeId(Long fridgeId) {
    Optional<Fridge> byId = fridgeRepository.findById(fridgeId);
    return byId.isPresent() ? byId.get().getProductList() : Collections.emptyList();
  }

  private String generateUniqueNameForFridge() {
    String id = UUID.randomUUID().toString();
    if (fridgeRepository.findByName(id) != null) {
      return generateUniqueNameForFridge();
    }
    return id;
  }
}
