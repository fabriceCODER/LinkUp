package com.fabish.LinkUpAPI.controller;

import com.fabish.LinkUpAPI.dto.AuthResponseDTO;
import com.fabish.LinkUpAPI.dto.LoginDTO;
import com.fabish.LinkUpAPI.dto.UserDTO;
import com.fabish.LinkUpAPI.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO userDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(userDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(authService.login(loginDTO));
    }

    @GetMapping("/oauth2/google")
    public void googleLogin(HttpServletResponse response) throws IOException {
        response.sendRedirect("/oauth2/authorization/google");
    }

    @GetMapping("/oauth2/linkedin")
    public void linkedinLogin(HttpServletResponse response) throws IOException {
        response.sendRedirect("/oauth2/authorization/linkedin");
    }
}