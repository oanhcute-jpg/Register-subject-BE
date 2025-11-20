package com.util;


import com.entity.User;
import com.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
public class JwtUtil {

    private final String SECRET = "qJHVuAtf5z5yE4uC9aWYpH89Jq6qplrCwJOE7ZU+mvw=";
    private final long EXPIRATION = 1000 * 60 * 60; // 1 hour
    @Autowired
    UserRepository userRepository;

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    public User getUserByToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);

            try {
                username = extractUsername(token);
               Optional<User>user = userRepository.findByUsername(username);
               if (user.isPresent()) {
                   return user.get();
               }
            } catch (Exception e) {
                System.out.println("JWT ERROR: " + e.getMessage());
            }
        }
        return null;
    }

    public String extractUsername(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
