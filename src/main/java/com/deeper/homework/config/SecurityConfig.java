package com.deeper.homework.config;

import com.deeper.homework.filter.JwtAuthFilter;
import com.deeper.homework.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

  // Reduced whitelist for simplicity
  private static final String[] WHITE_LIST_URL = {
      "/auth/register",
      "/auth/login",
      "/intersect"
  };

  private final ApiBasePathConfig apiBasePathConfig;
  private final JwtAuthFilter jwtAuthFilter;
  private final UserService userService;

  @Autowired
  public SecurityConfig(ApiBasePathConfig apiBasePathConfig, JwtAuthFilter jwtAuthFilter,
      UserService userService) {
    this.apiBasePathConfig = apiBasePathConfig;
    this.jwtAuthFilter = jwtAuthFilter;
    this.userService = userService;
  }

  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    String[] fullWhiteListUrls = apiBasePathConfig.getWhiteListUrls(WHITE_LIST_URL);

    return http
        .csrf(
            AbstractHttpConfigurer::disable) // Disabling CSRF as we use JWT which is immune to CSRF
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(fullWhiteListUrls)
            .permitAll() // Whitelisting some paths from authentication
            .anyRequest().authenticated()) // All other requests must be authenticated
        .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless session management
        .addFilterBefore(jwtAuthFilter,
            UsernamePasswordAuthenticationFilter.class).build(); // Registering our JwtAuthFilter
  }

  @Bean
  AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setUserDetailsService(userService); // Setting our custom user details service
    provider.setPasswordEncoder(passwordEncoder()); // Setting the password encoder
    return provider;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
