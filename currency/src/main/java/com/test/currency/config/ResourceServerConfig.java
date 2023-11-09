package com.test.currency.config;

//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.oauth2.jwt.JwtDecoder;
//import org.springframework.security.oauth2.jwt.JwtDecoders;
//import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
//import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
//import org.springframework.security.web.SecurityFilterChain;


//todo Это код для  security

//@EnableWebSecurity
//@RequiredArgsConstructor
//public class ResourceServerConfig {
//
//    private final CurrencyClientConfig currencyClientConfig;
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/currency/**")
//                        .authenticated()
//                        .requestMatchers("/currency/**")
//                        .hasAuthority("resource.read"))
//                .oauth2ResourceServer((oauth2) -> oauth2.jwt(jwt ->
//                        jwt.decoder(JwtDecoders.fromIssuerLocation(currencyClientConfig.getUrl()))));
//        return http.build();
//    }
//}
