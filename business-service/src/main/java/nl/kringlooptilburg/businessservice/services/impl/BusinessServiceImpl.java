package nl.kringlooptilburg.businessservice.services.impl;

import nl.kringlooptilburg.businessservice.domain.entities.Business;
import nl.kringlooptilburg.businessservice.repositories.BusinessRepository;
import nl.kringlooptilburg.businessservice.services.BusinessService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BusinessServiceImpl implements BusinessService {
    private final BusinessRepository productRepository;

    public BusinessServiceImpl(BusinessRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Business createBusiness(Business business) {
        return productRepository.save(business);
    }

    @Override
    public List<Business> findAll() {
        return StreamSupport.stream(productRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public Optional<Business> findOne(Integer productId) {
        return productRepository.findById(productId);
    }

    @Override
    public void delete(Integer productId) {
        productRepository.deleteById(productId);
    }
}
