package nl.kringlooptilburg.authenticationservice.model;

import lombok.*;
import org.springframework.lang.Nullable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    private String accessToken;
    private String refreshToken;
    private Integer id;
    @Nullable
    private Integer businessId;
    private String email;
    private String role;
}
