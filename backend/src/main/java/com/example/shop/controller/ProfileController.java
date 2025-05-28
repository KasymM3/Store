package com.example.shop.controller;

import com.example.shop.dto.UserProfileDto;
import com.example.shop.payload.request.UpdateProfileRequest;
import com.example.shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/profile")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class ProfileController {

    private final UserService userService;

    /** Получить данные текущего пользователя */
    @GetMapping
    public UserProfileDto me(Authentication auth) {
        return userService.getProfile(auth.getName());
    }

    /** Обновить имя / пароль */
    @PutMapping
    public UserProfileDto update(@RequestBody UpdateProfileRequest req,
                                 Authentication auth) {
        return userService.updateProfile(auth.getName(), req);
    }

    /** Загрузить/заменить аватар */
    @PostMapping(path = "/avatar",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> avatar(@RequestParam("file") MultipartFile file,
                                    Authentication auth) throws Exception {

        userService.saveAvatar(auth.getName(),
                file.getContentType(),
                file.getBytes());
        return ResponseEntity.ok().build();
    }
}
