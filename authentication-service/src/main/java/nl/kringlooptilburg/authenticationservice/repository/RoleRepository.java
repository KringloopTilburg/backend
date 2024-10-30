package nl.kringlooptilburg.authenticationservice.repository;

import java.util.Optional;
import nl.kringlooptilburg.authenticationservice.model.Role;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CassandraRepository<Role, Long> {
    @Query(value = "SELECT * FROM role WHERE name = ?0 LIMIT 1 ALLOW FILTERING")
    Optional<Role> findByName(String name);
}
