package nl.kringlooptilburg.productservice.domain.entities;

import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("products")
public class ProductEntity {

    @PrimaryKey("product_id")
    @Column("product_id")
    @CassandraType(type = CassandraType.Name.UUID)
    private UUID productId;

    @Column("name")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String name;

    @Column("description")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String description;

    @Column("price")
    @CassandraType(type = CassandraType.Name.DOUBLE)
    private Double price;

    @Column("category")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String category;

    @Column("brand")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String brand;

    @Column("material")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String material;

    @Column("size")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String size;

    @Column("product_condition")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String productCondition;

    @Column("audience")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String audience;

    @Column("business_id")
    @CassandraType(type = CassandraType.Name.INT)
    private Integer businessId;

    @Column("colors")
    @CassandraType(type = CassandraType.Name.SET, typeArguments = CassandraType.Name.UUID)
    private Set<UUID> colors;
}