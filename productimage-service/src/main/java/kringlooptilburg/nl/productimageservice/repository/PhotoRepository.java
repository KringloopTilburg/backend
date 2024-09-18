package kringlooptilburg.nl.productimageservice.repository;

import kringlooptilburg.nl.productimageservice.domain.entities.Photo;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhotoRepository extends CassandraRepository<Photo, String> {
    Optional<Photo> findById(String id);

    @Query(value = "SELECT * FROM productimages.photo WHERE productId = ?0 LIMIT 1 ALLOW FILTERING")
    Photo findSinglePhotoByProductId(Integer productId);
}
