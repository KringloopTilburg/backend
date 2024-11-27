package nl.kringlooptilburg.apigateway.config;

import java.util.Arrays;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity.CsrfSpec;
import org.springframework.security.config.web.server.ServerHttpSecurity.FormLoginSpec;
import org.springframework.security.config.web.server.ServerHttpSecurity.HttpBasicSpec;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    // TODO: add roles to common module instead of storing them here
    private static final String ADMIN = "ADMIN";
    private static final String BUSINESS_OWNER = "BUSINESS_OWNER";
    private static final String BUSINESS_MANAGER = "BUSINESS_MANAGER";
    private static final String BUSINESS_EDITOR = "BUSINESS_EDITOR";

    private static final String[] BUSINESS_EDITORS = new String[]{
            ADMIN,
            BUSINESS_OWNER,
            BUSINESS_MANAGER,
            BUSINESS_EDITOR,
    };

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http,
            ReactiveAuthenticationManager authenticationManager,
            ServerAuthenticationConverter authenticationConverter) {
        AuthenticationWebFilter authenticationWebFilter = new AuthenticationWebFilter(
                authenticationManager);
        authenticationWebFilter.setServerAuthenticationConverter(authenticationConverter);

        http.authorizeExchange(authorizeRequests -> authorizeRequests

                        .pathMatchers("/auth/**").permitAll()

                        .pathMatchers("/product-service/user/**").permitAll()

                        .pathMatchers("/product-service/business/**")
                        .hasAnyRole(BUSINESS_EDITORS)

                        .pathMatchers("/business-service/user/**").permitAll()

                        .pathMatchers("/business-service/owner/**")
                        .hasAnyRole(BUSINESS_OWNER, ADMIN)

                        .pathMatchers("/productimage-service/**")
                        .hasAnyRole(ADMIN)

                        .anyExchange().authenticated())
                .addFilterAt(authenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .httpBasic(HttpBasicSpec::disable)
                .formLogin(FormLoginSpec::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(CsrfSpec::disable);

        return http.build();
    }

    @Bean
    @Profile("dev")
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        configuration.setAllowedMethods(
                Arrays.asList(
                        "GET",
                        "POST",
                        "DELETE",
                        "PUT",
                        "PATCH",
                        "OPTIONS"
                ));

        configuration.addAllowedHeader("*");
        configuration.setAllowedOriginPatterns(List.of(
                "http://localhost:3000/"));
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
