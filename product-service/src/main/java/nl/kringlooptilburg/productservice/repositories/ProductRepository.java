package nl.kringlooptilburg.productservice.repositories;

import java.util.UUID;
import nl.kringlooptilburg.productservice.domain.entities.ProductEntity;
import nl.kringlooptilburg.productservice.domain.entities.enums.Audience;
import nl.kringlooptilburg.productservice.domain.entities.enums.Brand;
import nl.kringlooptilburg.productservice.domain.entities.enums.Material;
import nl.kringlooptilburg.productservice.domain.entities.enums.ProductCondition;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface ProductRepository extends CassandraRepository<ProductEntity, UUID> {
    Iterable<ProductEntity> findAllByCategory(String category);
    Iterable<ProductEntity> findAllByPriceBetween(Double minPrice, Double maxPrice);
    Iterable<ProductEntity> priceLessThan(Double price);
    Iterable<ProductEntity> findAllByBrand(Brand brand);
    Iterable<ProductEntity> findAllBySize(String size);
    Iterable<ProductEntity> findAllByMaterial(Material material);
    Iterable<ProductEntity> findAllByProductCondition(ProductCondition productCondition);
    Iterable<ProductEntity> findAllByAudience(Audience audience);
}
