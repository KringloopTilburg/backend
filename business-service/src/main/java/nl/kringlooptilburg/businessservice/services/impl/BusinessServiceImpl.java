package nl.kringlooptilburg.businessservice.services.impl;

import nl.kringlooptilburg.businessservice.domain.entities.Business;
import nl.kringlooptilburg.businessservice.repositories.BusinessRepository;
import nl.kringlooptilburg.businessservice.services.BusinessService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Primary
public class BusinessServiceImpl implements BusinessService {
    private final BusinessRepository businessRepository;

    public BusinessServiceImpl(BusinessRepository businessRepository) {
        this.businessRepository = businessRepository;
    }

    @Override
    public Business createBusiness(Business business) {
        return businessRepository.save(business);
    }

    @Override
    public List<Business> findAll() {
        return StreamSupport.stream(businessRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public Optional<Business> findOne(Integer BusinessId) {
        return businessRepository.findById(BusinessId);
    }

    @Override
    public void delete(Integer BusinessId) {
        businessRepository.deleteById(BusinessId);
    }
}
