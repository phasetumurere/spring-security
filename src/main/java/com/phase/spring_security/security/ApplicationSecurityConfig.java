package com.phase.spring_security.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import static com.phase.spring_security.security.AppUserRole.*;
import static com.phase.spring_security.security.AppUserPermission.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //Authorising the API using Annotation instead of requestMatchers
public class ApplicationSecurityConfig {

    private final PasswordEncoder passwordEncoder;


    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .authorizeHttpRequests((requests)->requests
                        .requestMatchers("/","index", "/css/*", "/js/*")
                        .permitAll()
                        .requestMatchers("api/**").hasRole(STUDENT.name())
//                        .requestMatchers(HttpMethod.POST,"management/api/**").hasAuthority(COURSE_WRITE.name())
//                        .requestMatchers(HttpMethod.PUT,"management/api/**").hasAuthority(COURSE_WRITE.name())
//                        .requestMatchers(HttpMethod.DELETE,"management/api/**").hasAuthority(COURSE_WRITE.name())
//                        .requestMatchers(HttpMethod.GET,"management/api/**").hasAnyRole(ADMIN.name(), ADMINTRAINEE.name())
                        .anyRequest()
                        .authenticated()

                )
                .httpBasic();
        return httpSecurity.build();
    }
    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails phase = User.builder()
                .username("Phase")
                .password(passwordEncoder.encode("Madede_11"))
//                .roles(STUDENT.name()) //Role_ STUDENT Role based Authentication
                .authorities(STUDENT.getGrantedAuthorities()) //Permission based Authentication
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("Madede_11"))
//                .roles(ADMIN.name()) //Role ADMIN
                .authorities(ADMIN.getGrantedAuthorities()) //Permission based Authentication
                .build();
        UserDetails adminTrainee = User.builder()
                .username("trainee")
                .password(passwordEncoder.encode("Madede_11"))
//                .roles(ADMINTRAINEE.name()) //Role ADMIN_TRAINEE
                .authorities(ADMINTRAINEE.getGrantedAuthorities()) //Permission based Authentication
                .build();
        return new InMemoryUserDetailsManager(
                phase,
                admin,
                adminTrainee
        );
    }
}
