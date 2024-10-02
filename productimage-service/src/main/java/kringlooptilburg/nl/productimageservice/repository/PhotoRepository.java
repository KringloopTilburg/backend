package kringlooptilburg.nl.productimageservice.repository;

import kringlooptilburg.nl.productimageservice.domain.entities.Photo;
import lombok.NonNull;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PhotoRepository extends CassandraRepository<Photo, String> {
    @NonNull
    Optional<Photo> findById(@NonNull String id);

    @Query(value = "SELECT * FROM product_image.photo WHERE productId = ?0 LIMIT 1 ALLOW FILTERING")
    Photo findSinglePhotoByProductId(Integer productId);
}
