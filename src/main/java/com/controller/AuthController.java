package com.controller;

import com.entity.User;
import com.model.LoginRequest;
import com.service.CustomUserDetailsService;
import com.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private CustomUserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        User user=userDetailsService.getUserByUsernameAndPass(request.getUsername(), request.getPassword());
        if (user!=null) {
            String token = jwtUtil.generateToken(request.getUsername());
            return ResponseEntity.ok(new HashMap<>() {{
                put("token", token);
                put("role", user.getRole());
                put("username", user.getUsername());
            }});
        }
        return ResponseEntity.status(401).body("Invalid credentials");
    }
    @PostMapping("/getUser")
    public ResponseEntity<?> getUser(@RequestBody LoginRequest request) {

        return ResponseEntity.ok( jwtUtil.extractUsername(request.getUsername()));
    }
}
