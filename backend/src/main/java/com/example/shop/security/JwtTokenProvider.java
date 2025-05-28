package com.example.shop.security;

import com.example.shop.entity.User;
import com.example.shop.repository.UserRepository;
import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration-ms}")
    private long jwtExpirationMs;

    private final UserRepository userRepo;

    /* ======================  PUBLIC API  ====================== */

    /** Генерация токена (используется при логине) */
    public String generateToken(Authentication auth) {
        var user = (org.springframework.security.core.userdetails.User) auth.getPrincipal();
        return Jwts.builder()
                .setSubject(user.getUsername())          // e-mail в subject
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS256, jwtSecret.getBytes(StandardCharsets.UTF_8))
                .compact();
    }

    /** Проверка подписи + даты */
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(jwtSecret.getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            // подпись не сходится, токен просрочен или битый
            return false;
        }
    }

    /** Создаём Authentication, который кладётся в SecurityContext */
    public Authentication getAuthentication(String token) {
        String email = getUsernameFromToken(token);

        // роли можно достать из токена или из БД – здесь берём из базы
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<SimpleGrantedAuthority> authorities = user.getRoles()
                .stream()
                .map(role ->
                                // enum  →  "ROLE_ADMIN"  (или "ROLE_USER" и т.д.)
                                new SimpleGrantedAuthority(role.getName().name())
                        //                       └───────┬────────┘
                        //                                enum → String
                )
                .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(email, null, authorities);
    }

    /* ====================  SERVICE METHODS  ==================== */

    private String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret.getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();               // мы кладём e-mail → достаём e-mail
    }
}
