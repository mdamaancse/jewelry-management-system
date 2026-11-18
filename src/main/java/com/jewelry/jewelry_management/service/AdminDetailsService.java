
package com.jewelry.jewelry_management.service;

import com.jewelry.jewelry_management.entity.Admin;
import com.jewelry.jewelry_management.repository.AdminRepository;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AdminDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;

    public AdminDetailsService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {

        Admin admin = adminRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Admin not found"));

        return User.withUsername(admin.getUsername())
                .password(admin.getPassword())
                .authorities(admin.getRole())
                .build();
    }
}
