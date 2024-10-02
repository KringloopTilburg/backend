package nl.kringlooptilburg.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

import java.util.List;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    public enum Role {
        USER,
        ADMIN,

        BUSINESS_OWNER,
        BUSINESS_MANAGER,
        BUSINESS_EDITOR,
        BUSINESS_ORDER_PICKER,
        BUSINESS_VIEWER;
    }

    private static final String[] BUSINESS_MEMBER_ROLES = new String[] {
            Role.BUSINESS_OWNER.name(),
            Role.BUSINESS_MANAGER.name(),
            Role.BUSINESS_EDITOR.name(),
            Role.BUSINESS_ORDER_PICKER.name(),
            Role.BUSINESS_VIEWER.name(),
            Role.ADMIN.name()
    };


    private static final String[] BUSINESS_EDITOR_ROLES = new String[] {
            Role.BUSINESS_OWNER.name(),
            Role.BUSINESS_MANAGER.name(),
            Role.BUSINESS_EDITOR.name(),
            Role.ADMIN.name()
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
                        .hasAnyRole(BUSINESS_EDITOR_ROLES)

                .pathMatchers("/business-service/user/**").permitAll()

                .pathMatchers("/business-service/owner/**")
                        .hasAnyRole(Role.BUSINESS_OWNER.name(), Role.ADMIN.name())

                .pathMatchers("/productimage-service/**")
                        .hasAnyRole(Role.ADMIN.name())

                .anyExchange().authenticated())
            .addFilterAt(authenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION)
            .httpBasic(HttpBasicSpec::disable)
            .formLogin(FormLoginSpec::disable)
            .csrf(CsrfSpec::disable);

        return http.build();
    }
}
