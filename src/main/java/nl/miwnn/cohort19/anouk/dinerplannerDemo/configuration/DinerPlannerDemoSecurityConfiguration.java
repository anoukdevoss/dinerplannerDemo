package nl.miwnn.cohort19.anouk.dinerplannerDemo.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Author: Anouk de Vos
 * Configuration for security
 */

@Configuration
@EnableWebSecurity
public class DinerPlannerDemoSecurityConfiguration {

    Logger log = LoggerFactory.getLogger(DinerPlannerDemoSecurityConfiguration.class);

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) {

        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/",
                                "/diners",
                                "/diners/{dinerId}",
                                "/images/**",
                                "/guests/images/**",
                                "/guests",
                                "/guests/{id}",
                                "/webjars/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .defaultSuccessUrl("/diners")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/diners")
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}


/**
 * "/diners/delete/{dinerId}"
 * "/diners/edit/{dinerId}"
 * "/diners/save"
 * "/menus/**"
 * "/guests/delete/{id}"
 * "/guests/edit/{id}"
 */