package nl.kringlooptilburg.businessservice.repositories;

import nl.kringlooptilburg.businessservice.domain.entities.Business;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessRepository extends CrudRepository<Business, Integer>{}
