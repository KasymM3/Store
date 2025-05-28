package com.example.shop.dto;

import lombok.Data;

@Data
public class UserProfileDto {
    private Long   id;
    private String name;
    private String email;

    
    private String avatar;

    
    private String avatarContentType;
}
