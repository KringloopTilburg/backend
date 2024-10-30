package nl.kringlooptilburg.authenticationservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.Table;

@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table("\"user\"")
public class User {

    @Id
    private String email;
    private String password;
    private String role;
}
