package ir.segroup.unipoll.config.security;

import ir.segroup.unipoll.config.security.filter.JWTTokenGeneratorFilter;
import ir.segroup.unipoll.config.security.filter.JWTTokenValidatorFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collections;
import java.util.List;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
public class AppSecurityConfig {
    private static final String ADMIN = "ADMIN";
    private static final String STUDENT = "STUDENT";
    private static final String INSTRUCTOR = "INSTRUCTOR";
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(httpSecurityCorsConfigurer ->
                        httpSecurityCorsConfigurer.configurationSource(
                                request -> {
                                    CorsConfiguration conf = new CorsConfiguration();
                                    conf.setAllowedOriginPatterns(Collections.singletonList("*"));
                                    conf.setAllowedMethods(Collections.singletonList("*"));
                                    conf.setAllowedHeaders(Collections.singletonList("*"));
                                    conf.setExposedHeaders(List.of("Authorization"));
                                    conf.setAllowCredentials(true);
                                    conf.setMaxAge(24 * 60 * 60L); //1d 24h 60min 60s
                                    return conf;
                                }
                        ))
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class)
                .addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class)
                .authorizeHttpRequests(requests -> {
                    requests.requestMatchers(antMatcher("/user")).hasRole(ADMIN);
                    requests.requestMatchers(antMatcher("/user/one")).hasAnyRole(ADMIN,STUDENT,INSTRUCTOR);
                    requests.requestMatchers(antMatcher("/config")).hasRole(ADMIN);
                    requests.requestMatchers(antMatcher("/doc/**")).permitAll();
                    requests.requestMatchers(antMatcher("/swagger-ui/**")).permitAll();
                    requests.requestMatchers(antMatcher("/v3/api-docs/**")).permitAll();
                    requests.requestMatchers(antMatcher("/contact/**")).permitAll();
                    requests.requestMatchers(antMatcher("/login")).permitAll();
                    requests.requestMatchers(antMatcher("/course")).permitAll();
                    requests.requestMatchers(antMatcher("/college")).permitAll();
                    requests.requestMatchers(antMatcher("/booklet")).permitAll(); //لیست جزوه های برتر
                    requests.requestMatchers(antMatcher("/booklet/file/**")).hasAnyRole(ADMIN,STUDENT,INSTRUCTOR); //آپلود و دانلود جزوه
                    requests.requestMatchers(antMatcher("/booklet/like/**")).hasAnyRole(ADMIN,STUDENT,INSTRUCTOR); // لایک جزوه
                    requests.requestMatchers(antMatcher("/academic-department/**")).permitAll();
                    requests.requestMatchers(antMatcher("/instructor/**")).permitAll();                
                    requests.requestMatchers(antMatcher("/rate/**")).hasAnyRole(ADMIN,STUDENT);
                    requests.requestMatchers(antMatcher("/instructor-course/**")).permitAll(); // لیست درس استاد های برتر
                    requests.requestMatchers(antMatcher("/instructor-course/filter")).permitAll(); // سرچ درس استاد
                    requests.requestMatchers(antMatcher("/instructor-course/edit-description/**")).hasAnyRole(INSTRUCTOR); // ویرایش درس استاد
                    requests.requestMatchers(antMatcher("/comment-c")).hasAnyRole(ADMIN,STUDENT,INSTRUCTOR); // ساخت کامنت درس استاد
                    requests.requestMatchers((antMatcher("/term"))).permitAll();

                })
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

