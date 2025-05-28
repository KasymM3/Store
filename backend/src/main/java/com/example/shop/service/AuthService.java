
package com.example.shop.service;

import com.example.shop.entity.User;
import com.example.shop.entity.ERole;
import com.example.shop.entity.Role;
import com.example.shop.repository.RoleRepository;
import com.example.shop.repository.UserRepository;
import com.example.shop.payload.request.SignupRequest;
import jakarta.transaction.Transactional;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthService implements UserDetailsService {
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder encoder;

    public AuthService(UserRepository userRepo, RoleRepository roleRepo, PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.encoder = encoder;
    }


    @Transactional
    public User registerUser(SignupRequest req) {
        if (userRepo.existsByEmail(req.getEmail()))
            throw new RuntimeException("Email already in use");

        User user = new User();
        user.setName(req.getName());
        user.setEmail(req.getEmail());
        user.setPassword(encoder.encode(req.getPassword()));

        Set<Role> roles = new HashSet<>();
        String requested = req.getRole() == null ? "" : req.getRole().toLowerCase();

        if ("admin".equals(requested)) {
            roles.add(roleRepo.findByName(ERole.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("ROLE_ADMIN not found")));
        } else {                                 // по умолчанию – обычный пользователь
            roles.add(roleRepo.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("ROLE_USER not found")));
        }

        user.setRoles(roles);
        return userRepo.save(user);
    }

    
    public User getByEmail(String email) {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getRoles().stream()
                        .map(r -> new SimpleGrantedAuthority(r.getName().name()))
                        .toList()
        );
    }

    @Transactional
    public void updatePassword(String email, String newPassword) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setPassword(encoder.encode(newPassword));
        userRepo.save(user);
    }

    public void resetPassword(String email, String newPassword) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
        user.setPassword(encoder.encode(newPassword));
        userRepo.save(user);
    }


}
