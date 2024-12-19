package com.deeper.homework.controller;

import com.deeper.homework.entity.User;
import com.deeper.homework.service.JwtService;
import com.deeper.homework.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private final UserService userService;
  private final JwtService jwtService;

  @Autowired
  public AuthController(UserService userService, JwtService jwtService) {
    this.userService = userService;
    this.jwtService = jwtService;
  }

  @PostMapping("/register")
  public ResponseEntity<String> register(@Valid @RequestBody User user) {
    User registeredUser = userService.registerUser(user.getUsername(),
        new BCryptPasswordEncoder().encode(user.getPassword()));
    if (registeredUser != null) {
      return ResponseEntity.ok("User registered successfully");
    }
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
  }

  @PostMapping("/login")
  public ResponseEntity<String> login(@Valid @RequestBody User user) {
    User existingUser = userService.findByUsername(user.getUsername());
    if (existingUser != null && new BCryptPasswordEncoder().matches(user.getPassword(),
        existingUser.getPassword())) {
      String token = jwtService.generateToken(existingUser);
      return ResponseEntity.ok(token);
    }
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
  }
}