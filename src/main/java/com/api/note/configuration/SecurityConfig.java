package com.api.note.configuration;


import com.api.note.user.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



@Configuration
// @EnableWebSecurity
public class SecurityConfig  {
    @Autowired
    private final UserDetailService userDetailService;
    @Autowired
    private final JWTAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(UserDetailService userDetailService,JWTAuthenticationFilter jwtAuthenticationFilter){
        this.userDetailService = userDetailService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @SuppressWarnings("deprecation")
    @Bean
    SecurityFilterChain securityWebFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                    .csrf(req -> req.disable())
                    .authorizeRequests(
                            (req) -> req
                            .requestMatchers("/api/user/createUser/**","/api/user/login/**")
                            .permitAll()
                            .anyRequest()
                            .authenticated()
                    )
                    .userDetailsService(userDetailService)
                    .sessionManagement(sess->sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                    .build();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return  configuration.getAuthenticationManager();
    }
}
