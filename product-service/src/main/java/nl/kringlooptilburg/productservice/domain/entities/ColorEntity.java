package nl.kringlooptilburg.productservice.domain.entities;

import java.util.UUID;
import lombok.*;

import java.util.Set;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.CassandraType.Name;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("color")
public class ColorEntity {
    @PrimaryKey("color_id")
    @Column("color_id")
    @CassandraType(type = Name.UUID)
    private UUID colorId;

    @CassandraType(type = Name.TEXT)
    private String color;

    @EqualsAndHashCode.Exclude
    @Column("products")
    @CassandraType(type = CassandraType.Name.SET, typeArguments = CassandraType.Name.UUID)
    private Set<UUID> products;
}
