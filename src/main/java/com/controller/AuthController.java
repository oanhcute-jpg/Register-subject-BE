package com.controller;

import com.entity.User;
import com.model.LoginRequest;
import com.service.CustomUserDetailsService;
import com.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
        Map<String,Object> map = new HashMap<>();
        if (user!=null) {
            String token = jwtUtil.generateToken(request.getUsername());
            return ResponseEntity.ok(new HashMap<>() {{
                put("status","success");
                put("token", token);
                put("role", user.getRole());
                put("username", user.getUsername());
            }});
        }

        map.put("status","error");
        map.put("message","Tài khoản hoặc mật khẩu không chính xác.");
        return ResponseEntity.ok().body(map);
    }
    @PostMapping("/getUser")
    public ResponseEntity<?> getUser(@RequestBody LoginRequest request) {

        return ResponseEntity.ok( jwtUtil.extractUsername(request.getUsername()));
    }
}
