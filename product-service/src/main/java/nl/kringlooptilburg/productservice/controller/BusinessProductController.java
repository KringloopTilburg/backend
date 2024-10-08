package nl.kringlooptilburg.productservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import nl.kringlooptilburg.productservice.domain.dto.ProductDto;
import nl.kringlooptilburg.productservice.domain.dto.ProductImageDto;
import nl.kringlooptilburg.productservice.domain.entities.ProductEntity;
import nl.kringlooptilburg.productservice.mappers.Mapper;
import nl.kringlooptilburg.productservice.services.ProductService;
import nl.kringlooptilburg.productservice.services.rabbit.RabbitMQSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("product-service/business")
public class BusinessProductController {

    private ProductService productService;

    private Mapper<ProductEntity, ProductDto> productMapper;

    private RabbitMQSender rabbitMQSender;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping(path = "/product")
    public ResponseEntity<ProductDto> createProduct(@RequestPart String productJson, @RequestPart("images") MultipartFile[] images) {
        ProductDto productDto;

        try {
            productDto = objectMapper.readValue(productJson, ProductDto.class);

            if (productDto.getBusinessId() == null) {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
            }

            ProductEntity productEntity = productMapper.mapFrom(productDto);
            ProductEntity savedProductEntity = productService.createProduct(productEntity);

            List<ProductImageDto> productImagesDto = new ArrayList<>();
            for (var image : images) {
                var productImageDto = ProductImageDto.builder()
                        .image(image.getBytes())
                        .productId(savedProductEntity.getProductId())
                        .title(image.getOriginalFilename())
                        .build();
                productImagesDto.add(productImageDto);
            }
            rabbitMQSender.sendImages(productImagesDto);

            return new ResponseEntity<>(productMapper.mapTo(savedProductEntity), HttpStatus.CREATED);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping(path = "/product/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("productId") Integer productId) {
        productService.delete(productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
