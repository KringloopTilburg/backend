package nl.kringlooptilburg.businessservice.controller;

import lombok.AllArgsConstructor;
import nl.kringlooptilburg.businessservice.domain.dto.BusinessDto;
import nl.kringlooptilburg.businessservice.domain.entities.Business;
import nl.kringlooptilburg.businessservice.mappers.Mapper;
import nl.kringlooptilburg.businessservice.services.BusinessService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("business-service/owner")
public class BusinessOwnerController {
    private BusinessService businessService;
    private Mapper<Business, BusinessDto> mapper;

    @DeleteMapping(path = "/business/{businessId}")
    public ResponseEntity<Void> deleteBusiness(@PathVariable("businessId") Integer businessId) {
        businessService.delete(businessId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
