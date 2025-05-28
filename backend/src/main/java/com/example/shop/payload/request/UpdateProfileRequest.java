package com.example.shop.payload.request;


import lombok.Data;

/** Тело запроса PUT /api/profile  */
@Data
public class UpdateProfileRequest {
    private String name;        // можно менять имя
    private String password;    // если не пусто – заменяем пароль


}
