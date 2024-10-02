package nl.kringlooptilburg.authenticationservice.repository;

import nl.kringlooptilburg.authenticationservice.model.User;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

public interface UserRepository extends CassandraRepository<User, Integer> {

    //boolean existsByEmail(String email);
    User findByEmailAndPassword(String email, String password);
    @Query(value = "SELECT * FROM authentication.user WHERE email = ?0 LIMIT 1 ALLOW FILTERING")
    User findByEmail(String email);
}
