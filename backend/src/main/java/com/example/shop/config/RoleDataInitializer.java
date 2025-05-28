// src/main/java/com/example/shop/config/RoleDataInitializer.java
package com.example.shop.config;

import com.example.shop.entity.ERole;
import com.example.shop.entity.Role;
import com.example.shop.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RoleDataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepo;

    public RoleDataInitializer(RoleRepository roleRepo) {
        this.roleRepo = roleRepo;
    }

    @Override
    public void run(String... args) {
        if (roleRepo.findByName(ERole.ROLE_USER).isEmpty()) {
            Role userRole = new Role();
            userRole.setName(ERole.ROLE_USER);
            roleRepo.save(userRole);
        }
        if (roleRepo.findByName(ERole.ROLE_ADMIN).isEmpty()) {
            Role adminRole = new Role();
            adminRole.setName(ERole.ROLE_ADMIN);
            roleRepo.save(adminRole);
        }
    }
}
