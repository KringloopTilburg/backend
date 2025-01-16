package nl.kringlooptilburg.businessservice.services;

import nl.kringlooptilburg.businessservice.domain.entities.Business;

import java.util.List;
import java.util.Optional;

public interface BusinessService {
    Business createBusiness(Business business);

    boolean existsByEmail(String businessName);

    List<Business> findAll();

    Optional<Business> findOne(Integer productId);

    void delete(Integer productId);
}
