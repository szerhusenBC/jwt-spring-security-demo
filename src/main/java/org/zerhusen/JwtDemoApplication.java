package org.zerhusen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.zerhusen.security.service.JwtUserDetailsService;


@SpringBootApplication
public class JwtDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(JwtDemoApplication.class, args);
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new JwtUserDetailsService();
    }
}
