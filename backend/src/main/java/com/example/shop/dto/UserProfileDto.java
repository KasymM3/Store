package com.example.shop.dto;

import lombok.Data;

@Data
public class UserProfileDto {
    private Long   id;
    private String name;
    private String email;

    /** Base64-строка картинки */
    private String avatar;

    /** MIME-тип (нужен для <img src="data:…">) */
    private String avatarContentType;
}
