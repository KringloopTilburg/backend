package nl.kringlooptilburg.productservice.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import nl.kringlooptilburg.productservice.domain.dto.ProductDto;
import nl.kringlooptilburg.productservice.domain.entities.ProductEntity;
import nl.kringlooptilburg.productservice.mappers.Mapper;
import nl.kringlooptilburg.productservice.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("product-service/user")
public class ProductController {

    private ProductService productService;

    private Mapper<ProductEntity, ProductDto> productMapper;

    @GetMapping(path = "/product")
    public List<ProductDto> listProducts() {
        List<ProductEntity> productEntities = productService.findAll();
        return productEntities.stream()
                .map(productMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/product/{productId}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable("productId") String productId) {
        Optional<ProductEntity> foundProduct = productService.findOne(UUID.fromString(productId));
        return foundProduct.map(productEntity -> {
            ProductDto productDto = productMapper.mapTo(productEntity);
            return new ResponseEntity<>(productDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/product/category/{category}")
    public List<ProductDto> listProductsByCategory(@PathVariable("category") String category) {
        List<ProductEntity> productEntities = productService.findAllByCategory(category);
        return productEntities.stream()
                .map(productMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/product/price/{minPrice}/{maxPrice}")
    public List<ProductDto> listProductsByPriceBetween(@PathVariable("minPrice") Double minPrice, @PathVariable("maxPrice") Double maxPrice) {
        List<ProductEntity> productEntities = productService.findAllByPriceBetween(minPrice, maxPrice);
        return productEntities.stream()
                .map(productMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/product/price/{price}")
    public List<ProductDto> listProductsByPriceLessThan(@PathVariable("price") Double price) {
        List<ProductEntity> productEntities = productService.priceLessThan(price);
        return productEntities.stream()
                .map(productMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/product/brand/{brand}")
    public List<ProductDto> listProductsByBrand(@PathVariable("brand") String brand) {
        List<ProductEntity> productEntities = productService.findAllByBrand(brand);
        return productEntities.stream()
                .map(productMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/product/size/{size}")
    public List<ProductDto> listProductsBySize(@PathVariable("size") String size) {
        List<ProductEntity> productEntities = productService.findAllBySize(size);
        return productEntities.stream()
                .map(productMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/product/material/{material}")
    public List<ProductDto> listProductsByMaterial(@PathVariable("material") String material) {
        List<ProductEntity> productEntities = productService.findAllByMaterial(material);
        return productEntities.stream()
                .map(productMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/product/product-condition/{productCondition}")
    public List<ProductDto> listProductsByProductCondition(@PathVariable("productCondition") String productCondition) {
        List<ProductEntity> productEntities = productService.findAllByProductCondition(productCondition);
        return productEntities.stream()
                .map(productMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/product/audience/{audience}")
    public List<ProductDto> listProductsByAudience(@PathVariable("audience") String audience) {
        List<ProductEntity> productEntities = productService.findAllByAudience(audience);
        return productEntities.stream()
                .map(productMapper::mapTo)
                .collect(Collectors.toList());
    }
}
