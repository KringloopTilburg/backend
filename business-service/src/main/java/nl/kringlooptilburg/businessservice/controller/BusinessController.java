package nl.kringlooptilburg.businessservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import nl.kringlooptilburg.businessservice.domain.dto.BusinessDto;
import nl.kringlooptilburg.businessservice.domain.entities.Business;
import nl.kringlooptilburg.businessservice.mappers.Mapper;
import nl.kringlooptilburg.businessservice.services.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/business-service")
public class BusinessController {

    private BusinessService businessService;

    private Mapper<Business, BusinessDto> mapper;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping(path = "/business")
    public ResponseEntity<BusinessDto> createBusiness(@RequestBody BusinessDto businessDto) {
        Business productEntity = mapper.mapFrom(businessDto);
        Business savedProductEntity = businessService.createBusiness(productEntity);
        return new ResponseEntity<>(mapper.mapTo(savedProductEntity), HttpStatus.CREATED);
    }

    @GetMapping(path = "/business")
    public List<BusinessDto> listBusinesses() {
        List<Business> productEntities = businessService.findAll();
        return productEntities.stream()
                .map(mapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/business/{businessId}")
    public ResponseEntity<BusinessDto> getBusiness(@PathVariable("businessId") Integer productId) {
        Optional<Business> foundProduct = businessService.findOne(productId);
        return foundProduct.map(productEntity -> {
            BusinessDto productDto = mapper.mapTo(productEntity);
            return new ResponseEntity<>(productDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(path = "/business/{businessId}")
    public ResponseEntity deleteBusiness(@PathVariable("businessId") Integer productId) {
        businessService.delete(productId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
