package nl.kringlooptilburg.businessservice.services;

import nl.kringlooptilburg.businessservice.domain.entities.Business;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;
import java.util.Optional;

public interface BusinessService {
    Business createBusiness(Business business);

    List<Business> findAll();

    Optional<Business> findOne(Integer productId);

    void delete(Integer productId);
}
