package com.example.shop.entity;

import jakarta.persistence.*;

import java.util.Base64;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length=50)
    private String name;

    @Column(nullable=false, unique=true, length=50)
    private String email;

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public String getAvatarContentType() {
        return avatarContentType;
    }

    public void setAvatarContentType(String avatarContentType) {
        this.avatarContentType = avatarContentType;
    }

    @Column(nullable=false)
    private String password;

    @Basic(fetch = FetchType.LAZY)   // (необязательно, но полезно)
    @Column(name = "avatar")         // без @Lob и без columnDefinition
    private byte[] avatar;

    /** MIME-тип загруженного файла (image/png, image/jpeg …) */
    @Column(name = "avatar_content_type", length = 40)
    private String avatarContentType;

    @Transient
    public String getAvatarBase64() {
        return avatar == null ? null
                : Base64.getEncoder().encodeToString(avatar);
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable( name = "user_roles",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id"))
    private Set<Role> roles;

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}