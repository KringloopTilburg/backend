package nl.kringlooptilburg.apigateway.config;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;

@Configuration
@RequiredArgsConstructor
public class GatewayConfig {

    @Bean
    public CorsWebFilter corsFilter() {
        CorsConfigurationSource source = request -> {
            CorsConfiguration cors = new CorsConfiguration();
            cors.addAllowedOrigin("http://localhost:3000");
            cors.addAllowedMethod("*");
            cors.addAllowedHeader("*");
            return cors;
        };
        return new CorsWebFilter(source);
    }

    // http://localhost:8081 = voorbeeld lokaal draaiende service
    // http://product-service = voorbeeld kubernetes cluster draaiende service (service referentie)
    // http://productimage-service:8083 = voorbeeld docker container draaiende service
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("authenticationservice", r -> r.
                        path("/auth/**")
                        .uri("http://localhost:8081"))
                .route("productservice", r -> r
                        .path("/product-service/**")
                        .uri("http://localhost:8082"))
                .route("productimageservice", r -> r
                        .path("/productimage-service/**")
                        .uri("http://localhost:8083"))
                .build();
    }
}
