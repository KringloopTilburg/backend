package nl.kringlooptilburg.authenticationservice.model;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("role")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @PrimaryKey
    @CassandraType(type = CassandraType.Name.UUID)
    private UUID id;
    @CassandraType(type = CassandraType.Name.TEXT)
    private String name;
}
