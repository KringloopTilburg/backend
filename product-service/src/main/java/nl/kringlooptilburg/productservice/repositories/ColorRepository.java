package nl.kringlooptilburg.productservice.repositories;

import java.util.UUID;
import nl.kringlooptilburg.productservice.domain.entities.ColorEntity;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

public interface ColorRepository extends CassandraRepository<ColorEntity, UUID> {
    @Query(value = "SELECT * FROM color WHERE color = ?0 LIMIT 1 ALLOW FILTERING")
    ColorEntity findByColor(String color);
}
