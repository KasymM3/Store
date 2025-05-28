package com.example.shop.service;


import com.example.shop.dto.UserProfileDto;
import com.example.shop.entity.User;
import com.example.shop.payload.request.UpdateProfileRequest;
import com.example.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepo;
    private final PasswordEncoder encoder;

    public UserProfileDto getProfile(String email) {
        return toDto(userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found")));
    }

    public UserProfileDto updateProfile(String email, UpdateProfileRequest req) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (req.getName() != null && !req.getName().isBlank())
            user.setName(req.getName());

        if (req.getPassword() != null && !req.getPassword().isBlank())
            user.setPassword(encoder.encode(req.getPassword()));

        userRepo.save(user);
        return toDto(user);
    }

    public void saveAvatar(String email, String contentType, byte[] data) throws IOException {
        User u = userRepo.findByEmail(email).orElseThrow();
        u.setAvatarContentType(contentType);
        u.setAvatar(data);
        userRepo.save(u);
    }

    /* ---------- helpers ---------- */
    private UserProfileDto toDto(User u) {
        UserProfileDto dto = new UserProfileDto();
        dto.setId(u.getId());
        dto.setName(u.getName());
        dto.setEmail(u.getEmail());
        dto.setAvatar(u.getAvatarBase64());
        dto.setAvatarContentType(u.getAvatarContentType());
        return dto;
    }
    public User findByEmail(String email) {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public User findById(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with id: " + id)
                );
    }
}
