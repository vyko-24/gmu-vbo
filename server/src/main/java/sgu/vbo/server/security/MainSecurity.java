package sgu.vbo.server.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class MainSecurity {
    @Bean
    public SecurityFilterChain doFilter(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(c -> c.configurationSource(corsRegistry()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/**").permitAll()
                        .anyRequest().authenticated());

        return http.build();
    }

    private CorsConfigurationSource corsRegistry() {
        CorsConfiguration conf = new CorsConfiguration();
        conf.setAllowedOrigins(List.of("*")); // Si setAllowedCredentials está en true, no se puede usar * aquí
        conf.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE","OPTIONS"));
        conf.setAllowedHeaders(List.of("Accept","Content-Type","Authorization"));
        conf.setAllowCredentials(false); //Cookies

        UrlBasedCorsConfigurationSource src = new UrlBasedCorsConfigurationSource();
        src.registerCorsConfiguration("/**", conf);
        return src;
    }
}
