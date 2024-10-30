package nl.kringlooptilburg.productservice.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import nl.kringlooptilburg.productservice.domain.entities.ProductEntity;
import nl.kringlooptilburg.productservice.domain.entities.enums.Audience;
import nl.kringlooptilburg.productservice.domain.entities.enums.Brand;
import nl.kringlooptilburg.productservice.domain.entities.enums.Material;
import nl.kringlooptilburg.productservice.domain.entities.enums.ProductCondition;
import nl.kringlooptilburg.productservice.repositories.ProductRepository;
import nl.kringlooptilburg.productservice.services.ColorService;
import nl.kringlooptilburg.productservice.services.ProductService;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ColorService colorService;

    public ProductServiceImpl(ProductRepository productRepository, ColorService colorService) {
        this.productRepository = productRepository;
        this.colorService = colorService;
    }

    @Override
    public ProductEntity createProduct(ProductEntity productEntity) {
        return productRepository.save(productEntity);
    }

    @Override
    public List<ProductEntity> findAll() {
        return new ArrayList<>(productRepository.findAll());
    }

    @Override
    public Optional<ProductEntity> findOne(UUID productId) {
        return productRepository.findById(productId);
    }

    @Override
    public void delete(UUID productId) {
        productRepository.deleteById(productId);
    }

    @Override
    public List<ProductEntity> findAllByCategory(String category) {
        return StreamSupport.stream(productRepository.findAllByCategory(category).spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public List<ProductEntity> findAllByPriceBetween(Double minPrice, Double maxPrice) {
        return StreamSupport.stream(productRepository.findAllByPriceBetween(minPrice, maxPrice).spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public List<ProductEntity> priceLessThan(Double price) {
        return StreamSupport.stream(productRepository.priceLessThan(price).spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public List<ProductEntity> findAllByBrand(String brand) {
        Brand brandEnum = Brand.valueOf(brand);
        return StreamSupport.stream(productRepository.findAllByBrand(brandEnum).spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public List<ProductEntity> findAllBySize(String size) {
        return StreamSupport.stream(productRepository.findAllBySize(size).spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public List<ProductEntity> findAllByMaterial(String material) {
        Material materialEnum = Material.valueOf(material);
        return StreamSupport.stream(productRepository.findAllByMaterial(materialEnum).spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public List<ProductEntity> findAllByProductCondition(String productCondition) {
        ProductCondition productConditionEnum = ProductCondition.valueOf(productCondition);
        return StreamSupport.stream(productRepository.findAllByProductCondition(productConditionEnum).spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public List<ProductEntity> findAllByAudience(String audience) {
        Audience audienceEnum = Audience.valueOf(audience);
        return StreamSupport.stream(productRepository.findAllByAudience(audienceEnum).spliterator(), false).collect(Collectors.toList());
    }
}
