package ir.segroup.seproject.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collections;

@Configuration
public class AppSecurityConfig {
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.
                cors(httpSecurityCorsConfigurer ->
                        httpSecurityCorsConfigurer.configurationSource(
                                request -> {
                                    CorsConfiguration conf = new CorsConfiguration();
                                    conf.setAllowedHeaders(Collections.singletonList("*"));
                                    conf.setAllowedMethods(Collections.singletonList("*"));
                                    conf.setAllowedOrigins(Collections.singletonList("*"));
                                    conf.setAllowCredentials(true);
                                    conf.setMaxAge(24 * 60 * 60L); //1d 24h 60min 60s
                                    return conf;
                                }
                        ))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requests ->{
                    requests.requestMatchers("/test").hasRole("ADMIN");
                    requests.requestMatchers("/users").permitAll();
                })
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}

