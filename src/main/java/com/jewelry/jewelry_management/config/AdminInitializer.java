
package com.jewelry.jewelry_management.config;

import com.jewelry.jewelry_management.entity.Admin;
import com.jewelry.jewelry_management.repository.AdminRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminInitializer {

    @Bean
    CommandLineRunner createAdmin(
            AdminRepository adminRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {

            if (adminRepository.findByUsername("admin").isEmpty()) {

                Admin admin = new Admin();

                admin.setUsername("admin");
                admin.setPassword(
                        passwordEncoder.encode(
                                System.getenv("ADMIN_PASSWORD")
                        )
                );

                admin.setRole("ROLE_ADMIN");

                adminRepository.save(admin);

                System.out.println("Admin account created successfully!");
            }
        };
    }
}
