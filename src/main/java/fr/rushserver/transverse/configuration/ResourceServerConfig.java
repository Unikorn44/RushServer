package fr.rushserver.transverse.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

//web security configuration
public class ResourceServerConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.mvcMatcher("/rushs/**")
                .authorizeRequests()
                .mvcMatchers("/rushs/**")
                .access("hasAuthority('SCOPE_rushs.read')")
                .and()
                .oauth2ResourceServer()
                .jwt();
        return http.build();
    }
}
